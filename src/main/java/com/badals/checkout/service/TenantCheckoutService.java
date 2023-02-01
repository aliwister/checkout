package com.badals.checkout.service;

import com.badals.checkout.domain.*;
import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.OrderConfirmation;
import com.badals.checkout.domain.pojo.LineItem;
import com.badals.checkout.domain.pojo.projection.ShipRate;
import com.badals.checkout.xtra.PaymentType;
import com.badals.checkout.repository.*;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mapper.CartMapper;
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
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
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
    private final TenantRepository tenantRepository;


    public static int ORDER_REF_SIZE = 7;

    public TenantCheckoutService(CheckoutRepository checkoutRepository, TenantPaymentRepository paymentRepository, CartMapper cartMapper, CarrierService carrierService, TenantOrderRepository orderRepository, CarrierRepository carrierRepository, TenantRepository tenantRepository) {
        this.checkoutRepository = checkoutRepository;
        this.paymentRepository = paymentRepository;
        this.cartMapper = cartMapper;
        this.carrierService = carrierService;
        this.orderRepository = orderRepository;
        this.carrierRepository = carrierRepository;
        this.tenantRepository = tenantRepository;
    }
    public static String buildProfileBaseUrl(Tenant tenant) {
        return tenant.getIsSubdomain()?"https://"+tenant.getSubdomain()+".profile.shop":"https://www."+tenant.getCustomDomain();
    }

    @Transactional(readOnly = true)
    public CartDTO findBySecureKey(String token) {
        log.info("Request to get Cart : {}", token);
        //log.info("Entity {}", checkoutRepository.findBySecureKey(token));
        Optional<CartDTO> cartDTO = checkoutRepository.findBySecureKey(token)
                .map(cartMapper::toDto);
        log.info("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        log.info("Returning DTO {}", cartDTO);
        return cartDTO.get();
    }

    public CartDTO setDeliveryAddress(Address address, String token) {
        Checkout checkout = checkoutRepository.findBySecureKey(token).get();
        checkout.setDeliveryAddress(address);
        checkout = checkoutRepository.save(checkout);
        return cartMapper.toDto(checkout);
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
        if (carrier != null) {
            checkout.setCarrier(carrier);
            setCarrierRate(checkout, carrier);
        }

        checkout.setEmail(email);
        checkout = checkoutRepository.save(checkout);
        return cartMapper.toDto(checkout);
    }

    private void setCarrierRate(Checkout checkout, String carrier) {
        if (carrier.equals("pickup")) {
            List<ShipRate> rates = carrierRepository.getRate(carrier, checkout.getCartWeight(), calculateSubtotal(checkout).toPlainString());
            if (rates != null && rates.size() > 0) {
                checkout.setCarrierRate(rates.get(0).getPrice());
            }
            return;
        }
        checkout.setCarrierRate(null);
    }

    public CartDTO setCarrier() {
        return null;
    }

   public static Long calculateValue(CartDTO checkout) {
       Long value = checkout.getItems().stream().mapToLong(x -> useSubtotalAsLong(x)).sum();
       value += (long) (1000*checkout.getCarrierRate());

       return value;
   }

    public static Long useSubtotalAsLong(LineItem lineItem) {
        return lineItem.getPrice().multiply(BigDecimal.TEN.multiply(BigDecimal.TEN).multiply(BigDecimal.TEN).multiply(lineItem.getQuantity())).setScale(0).longValue();
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
        orderConfirmation.setCart(cartMapper.toDto(checkout));
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
        return cartMapper.toDto(checkout);
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

    public void setCarrierRate(int i) {
    }
}
