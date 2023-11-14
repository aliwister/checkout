package com.badals.checkout.service;

import com.badals.checkout.domain.*;
import com.badals.checkout.domain.enumeration.DiscountSource;
import com.badals.checkout.domain.pojo.*;
import com.badals.checkout.service.pojo.Message;
import com.badals.checkout.xtra.PaymentType;
import com.badals.checkout.repository.*;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mapper.CartMapper;
import com.badals.checkout.service.mapper.OrderMapper;
import com.badals.enumeration.CartState;
import com.badals.enumeration.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDate;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class TenantCheckoutService {
    private final Logger log = LoggerFactory.getLogger(TenantCheckoutService.class);

    private final CheckoutRepository checkoutRepository;
    private final TenantPaymentRepository paymentRepository;
    private final CartMapper cartMapper;
    private final CarrierService carrierService;
    private final TenantOrderRepository orderRepository;
    private final CarrierRepository carrierRepository;
    private final OrderMapper orderMapper;
    private final TenantRepository tenantRepository;
    private final RewardRepository rewardRepository;
    private final PointUsageHistoryRepository pointUsageHistoryRepository;
    private final PointRepository pointRepository;

    public static int ORDER_REF_SIZE = 7;

    public TenantCheckoutService(CheckoutRepository checkoutRepository, TenantPaymentRepository paymentRepository, CartMapper cartMapper, CarrierService carrierService, TenantOrderRepository orderRepository, CarrierRepository carrierRepository, OrderMapper orderMapper, TenantRepository tenantRepository, RewardRepository rewardRepository, PointUsageHistoryRepository pointUsageHistoryRepository, PointRepository pointRepository) {
        this.checkoutRepository = checkoutRepository;
        this.paymentRepository = paymentRepository;
        this.cartMapper = cartMapper;
        this.carrierService = carrierService;
        this.orderRepository = orderRepository;
        this.carrierRepository = carrierRepository;
        this.orderMapper = orderMapper;
        this.tenantRepository = tenantRepository;
        this.rewardRepository = rewardRepository;
        this.pointUsageHistoryRepository = pointUsageHistoryRepository;
        this.pointRepository = pointRepository;
    }
    public static String buildProfileBaseUrl(Tenant tenant) {
        return tenant.getIsSubdomain()?"https://"+tenant.getSubdomain()+".profile.shop":"https://www."+tenant.getCustomDomain();
    }

    @Transactional(readOnly = true)
    public CartDTO findBySecureKey(String token) {
        log.info("Request to get Cart : {}", token);
        //log.info("Entity {}", checkoutRepository.findBySecureKey(token));
        Optional<CartDTO> cartDTO = checkoutRepository.findBySecureKey(token)
                .map(cartMapper::toTenanteDto);
        log.info("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        log.info("Returning DTO {}", cartDTO);
        return cartDTO.get();
    }

    public CartDTO setDeliveryAddress(Address address, String token) {
        Checkout checkout = checkoutRepository.findBySecureKey(token).get();
        checkout.setDeliveryAddress(address);
        checkout = checkoutRepository.save(checkout);
        return cartMapper.toTenanteDto(checkout);
    }

    @Transactional
    public CartDTO setDeliveryAddressAndEmail(Address address, String email, String token, String carrier) {
        Checkout checkout = checkoutRepository.findBySecureKey(token).get();
        if(address.getId() == null || address.getId() < 0) {
            checkout.setDeliveryAddress(address);
            checkout.setDeliveryAddressId(null);
        }
        else {
            checkout.setDeliveryAddressId(address.getId());
            //checkout.setDeliveryAddress(null);
        }
        if (carrier != null)
            checkout.setCarrier(carrier);
        checkout.setEmail(email);
        checkout = checkoutRepository.save(checkout);
        return cartMapper.toTenanteDto(checkout);
    }
    public CartDTO setCarrier() {
        return null;
    }

   public static Long calculateValue(CartDTO checkout) {
       Long value = checkout.getItems().stream().mapToLong(x -> useSubtotalAsLong(x)).sum();
       //sum = sum.add(carrierService.getCarrierCost(checkout.getCarrier())).multiply(BigDecimal.valueOf(1000L));

       return value;
   }

    public static Long useSubtotalAsLong(LineItem lineItem) {
        return lineItem.getPrice().multiply(BigDecimal.TEN.multiply(BigDecimal.TEN).multiply(lineItem.getQuantity())).setScale(0).longValue();
    }

    public static  BigDecimal calculateTotal(Checkout checkout) {
        BigDecimal sum = BigDecimal.valueOf(checkout.getItems().stream().mapToDouble(x -> x.getPrice().doubleValue() * x.getQuantity().doubleValue()).sum());
        sum = sum.add(checkout.getCarrierRate());
        return sum;
    }

    public static BigDecimal calculateSubtotal(Checkout checkout) {
        BigDecimal sum = BigDecimal.valueOf(checkout.getItems().stream().mapToDouble(x -> x.getPrice().doubleValue() * x.getQuantity().doubleValue()).sum());
        return sum;
    }

    @Transactional
    public void setPaymentToken(Long cartId, String paymentType, String paymentToken) {
        Checkout checkout = checkoutRepository.findById(cartId).get();
        checkout.setPaymentToken(paymentToken);
        checkout.setPayment(paymentType);
        checkoutRepository.save(checkout);
    }

    @Transactional
    public TenantOrder createOrder(Checkout checkout, String paymentMethod, boolean isPaid) {
        TenantOrder order = new TenantOrder();
        order.setCurrency(checkout.getCurrency());
        //order.setCustomerId(checkout.get);
        order.setInvoiceAddress(checkout.getInvoiceAddress());
        order.setDeliveryAddressId(checkout.getDeliveryAddressId());
        order.setDeliveryAddress(checkout.getDeliveryAddress());
        order.setEmail(checkout.getEmail());

        if(isPaid) {
            order.setOrderState(OrderState.PAYMENT_ACCEPTED);
            order.setInvoiceDate(LocalDate.now());
        }
        else order.setOrderState(OrderState.AWAITING_PAYMENT);

        String orderRef = generateOrderId(1);
        String uiud = createUIUD();

        order.setReference(orderRef);
        order.setConfirmationKey( uiud );
        order.setSubtotal(calculateSubtotal(checkout));
        order.setTotal(calculateTotal(checkout));
        order.setCart(checkout);
        order.setCarrier(checkout.getCarrier());
        order.setDeliveryTotal(checkout.getCarrierRate());
        order.setPaymentMethod(paymentMethod);

        int i = 1;
        for(LineItem item : checkout.getItems()) {
            TenantOrderItem orderItem = new TenantOrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProductName(item.getName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.sequence(i++);
            orderItem.setImage(item.getImage());
            orderItem.setWeight(item.getWeight());
            orderItem.setUnit(item.getUnit());
            orderItem.setLineTotal(item.getPrice().multiply(item.getQuantity()));
            orderItem.setSku(item.getSku());
            orderItem.setRef(item.getRef());
            if(item.getCost() != null)
                orderItem.setCost(item.getCost());
            order.addOrderItem(orderItem);

        }
        checkoutRepository.closeCart(checkout.getSecureKey(), CartState.CLOSED);
        order = orderRepository.saveAndFlush(order);
        return order;
    }
    @Transactional
    public String generateOrderId(int attempt) {
        String ret =  orderRepository.getFirstUnused(TenantCheckoutService.generateRandomNumber(),generateRandomNumber(),generateRandomNumber(),generateRandomNumber(),generateRandomNumber(),generateRandomNumber(),generateRandomNumber(),generateRandomNumber(),generateRandomNumber(),generateRandomNumber());
        if (ret == null) {
            log.error("ORDER ID is null");
            if(attempt > 3) {
                ORDER_REF_SIZE++;
            }
            return generateOrderId(++attempt);
        }
        return ret;
    }
    private static String generateRandomNumber() {
        //Random generator = new Random();
        //int num = generator. nextInt(8999999) + 1000000;
        long min = (long) Math.pow(10, ORDER_REF_SIZE - 1);
        Long rand = ThreadLocalRandom.current().nextLong(min, min * 10);
        return String.valueOf(rand);
    }

    public static String createUIUD() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    @Transactional
    public OrderConfirmation createOrderWithPaymentByPaymentToken(String paymentKey) throws InvalidCartException {
        Checkout checkout = checkoutRepository.findByPaymentToken(paymentKey).orElse(null);
        if(checkout == null)
            throw new InvalidCartException("No checkout found");
        OrderConfirmation orderConfirmation = new OrderConfirmation();
        TenantOrder order = null;
        if(!checkout.getCheckedOut()) {
            PaymentType p = Arrays.stream(PaymentType.values()).filter(x -> x.ref.equalsIgnoreCase(checkout.getPayment())).findFirst().get();
            order = createOrder(checkout, checkout.getPayment(), p.prePay);
            if (p.prePay) {
                TenantPayment payment = new TenantPayment();
                payment.setAmount(order.getTotal());
                payment.setOrder(order);
                payment.setPaymentMethod(checkout.getPayment());
                payment.setTransactionId(paymentKey);
                payment.setCreated_date(Instant.now());
                payment.setTrackId(checkout.getId());
                payment.setCurrency(order.getCurrency());
                paymentRepository.save(payment);
            }
            checkout.setRef(order.getReference());
            checkout.setCheckedOut(true);
            checkoutRepository.save(checkout);
        }
        else {
            order = orderRepository.findByCartId(checkout.getId()).get();
        }
        orderConfirmation.setCart(cartMapper.toTenanteDto(checkout));
        orderConfirmation.setReference(order.getReference());
        orderConfirmation.setConfirmationKey(order.getConfirmationKey());
        orderConfirmation.setCurrency(order.getCurrency());
        return orderConfirmation;
    }
    @Transactional
    public CartDTO findBySecureKeyWithLock(String token) throws InvalidCartException, LockedCartException {
        log.info("Request to get Cart : {}", token);
       // log.info("Entity {}", checkoutRepository.findBySecureKey(token));

        Checkout checkout = checkoutRepository.findBySecureKey(token).orElseThrow(() -> new InvalidCartException("Cart Not Found"));
        if(checkout.getLock())
            throw new LockedCartException("Already locked");
        checkout.setLock(true);
        checkout = checkoutRepository.saveAndFlush(checkout);
        return cartMapper.toTenanteDto(checkout);
    }
    @Transactional
    public void unlock(String token) {
        Checkout checkout = checkoutRepository.findBySecureKey(token).get();
        checkout.setLock(false);
        checkoutRepository.saveAndFlush(checkout);
    }
    @Transactional(readOnly = true)
    public Tenant getTenant() {
        Tenant tenant = tenantRepository.findAll().get(0);
        return tenant;
    }

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional
    public Message useReward(String secureKey, String reward){
        Checkout checkout = checkoutRepository.findBySecureKey(secureKey).orElse(null);
        if(checkout == null)
            return new Message("Cart not found", "404");

        Reward r = rewardRepository.findByRewardType(reward);

        if(r == null)
            return new Message("Reward not found", "404");

        PointUsageHistory existingPointUsageHistory = pointUsageHistoryRepository.findByCheckoutIdAndRewardId(checkout.getId(), r.getId());
        if(existingPointUsageHistory != null)
            return new Message("Reward already used", "400");
        if(!r.getActive())
            return new Message("Reward not active", "400");
        if(!checkRewardRules(checkout, r))
            return new Message("Reward not applicable", "400");

        //check if we have enough points
        // todo fix1: customer to user
        // todo fix2: get user_Id from security context
        Integer points = getPointsForCustomer(1L);
        if(points < r.getPoints())
            return new Message("Not enough points", "400");
//        SecurityContextHolder.getContext().getAuthentication().getName();

        // add reward discount to adjustments of the checkout
        AdjustmentProfile adjustmentProfile = rewardToAdjustment(r);
        if(checkout.getAdjustments() == null)
            checkout.setAdjustments(new ArrayList<>());
        checkout.getAdjustments().add(adjustmentProfile);
        checkoutRepository.save(checkout);
        //add to reward usage count
        r.setTimesExchanged(r.getTimesExchanged() + 1);
        rewardRepository.save(r);
//        add to history
        PointUsageHistory pointUsageHistory = new PointUsageHistory();
        pointUsageHistory.setCustomerId(1L);
        pointUsageHistory.setPoints(r.getPoints());
        pointUsageHistory.setCreatedDate(Date.from(Instant.now()));
        pointUsageHistory.setRewardId(r.getId());
        pointUsageHistory.setCheckoutId(checkout.getId());
        pointUsageHistoryRepository.save(pointUsageHistory);

        return new Message("reward added successfully", "200");
    }

    @Transactional
    public Message removeReward(String secureKey, String reward_type){
        Checkout checkout = checkoutRepository.findBySecureKey(secureKey).orElse(null);
        if(checkout == null)
            return new Message("Cart not found", "404");
        if(checkout.getAdjustments() == null)
            return new Message("adjustments empty", "200");
        for (int i = 0; i < checkout.getAdjustments().size(); i++) {
            AdjustmentProfile adjustmentProfile = checkout.getAdjustments().get(i);
            if(adjustmentProfile.getDiscountSource() == DiscountSource.REWARD
                    && adjustmentProfile.getSourceRef().equals(reward_type)){
                checkout.getAdjustments().remove(i);
                break;
            }

        }
        checkoutRepository.save(checkout);

        Reward reward = rewardRepository.findByRewardType(reward_type);
        reward.setTimesExchanged(reward.getTimesExchanged() - 1);
        rewardRepository.save(reward);
        pointUsageHistoryRepository.removeByCheckoutIdAndRewardId(checkout.getId(), reward.getId());

        return new Message("successfully removed reward","200");
    }

//    @Transactional
//    public List<Reward> getAffordableRewards(){
//        Integer points = getPointsForCustomer(1L);
//        return rewardRepository.findAllByPointsLessThanEqual(points);
//    }

    private Boolean checkRewardRules(Checkout checkout, Reward reward){
        Double itemCount = checkout.getItems().stream().mapToDouble((x) -> x.getQuantity().doubleValue()).sum();
        for (RewardRules rule: reward.getRewardRules()) {
            if(rule.getMinCartAmount() > itemCount)
                return false;
        }
        return true;
    }

    private AdjustmentProfile rewardToAdjustment(Reward reward){
        AdjustmentProfile adjustmentProfile = new AdjustmentProfile();
        adjustmentProfile.setDiscount(reward.getDiscountReductionValue());
        adjustmentProfile.setDiscountReductionType(reward.getDiscountReductionType());
        adjustmentProfile.setDiscountSource(DiscountSource.REWARD);
        adjustmentProfile.setSourceRef(reward.getRewardType());
        return adjustmentProfile;
    }

    private Integer getPointsForCustomer(Long customerId){
        List<Point> earnedPoints = pointRepository.findAllByCustomerId(customerId);
        List<PointUsageHistory> usedPoints = pointUsageHistoryRepository.findAllByCustomerId(customerId);
        Integer earned = earnedPoints.stream().mapToInt(Point::getAmount).sum();
        Integer used = usedPoints.stream().mapToInt(PointUsageHistory::getPoints).sum();
        return earned - used;
    }
}
