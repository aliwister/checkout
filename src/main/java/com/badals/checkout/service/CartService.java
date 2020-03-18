package com.badals.checkout.service;

import com.badals.checkout.domain.*;
import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.LineItem;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.repository.OrderRepository;
import com.badals.checkout.repository.PaymentRepository;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.dto.OrderDTO;
import com.badals.checkout.service.mapper.CartMapper;
import com.badals.checkout.service.mapper.OrderMapper;
import com.badals.enumeration.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.Random;
import java.util.UUID;

@Service
public class CartService {
    private final Logger log = LoggerFactory.getLogger(CartService.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    CartMapper cartMapper;

    @Autowired
    CarrierService carrierService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderMapper orderMapper;

    @Transactional(readOnly = true)
    public CartDTO findBySecureKey(String secureKey) {
        log.info("Request to get Cart : {}", secureKey);
        log.info("Entity {}", cartRepository.findBySecureKey(secureKey));
        Optional<CartDTO> cartDTO = cartRepository.findBySecureKey(secureKey)
                .map(cartMapper::toDto);
        log.info("HHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHHH");
        log.info("Returning DTO {}", cartDTO);
        return cartDTO.get();
    }

    public CartDTO setDeliveryAddress(Address address, String secureKey) {
        Cart cart = cartRepository.findBySecureKey(secureKey).get();
        cart.setDeliveryAddress(address);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }

    public CartDTO setDeliveryAddressAndEmail(Address address, String email, String secureKey) {
        Cart cart = cartRepository.findBySecureKey(secureKey).get();
        if(address.getId() == null || address.getId() < 0)
            cart.setDeliveryAddress(address);
        else
            cart.setDeliveryAddressId(address.getId());
        cart.setEmail(email);
        cart = cartRepository.save(cart);
        return cartMapper.toDto(cart);
    }
    public CartDTO setCarrier() {
        return null;
    }

   public BigDecimal calculateValue(CartDTO cart) {
       BigDecimal sum = BigDecimal.valueOf(cart.getItems().stream().mapToDouble(x -> x.getPrice().doubleValue() * x.getQuantity().doubleValue()).sum());
       sum.add(carrierService.getCarrierCost(cart.getCarrier()));
       return sum;
   }

    public BigDecimal calculateTotal(Cart cart) {
        BigDecimal sum = BigDecimal.valueOf(cart.getItems().stream().mapToDouble(x -> x.getPrice().doubleValue() * x.getQuantity().doubleValue()).sum());
        sum.add(carrierService.getCarrierCost(cart.getCarrier()));
        return sum;
    }

    public BigDecimal calculateSubtotal(Cart cart) {
        BigDecimal sum = BigDecimal.valueOf(cart.getItems().stream().mapToDouble(x -> x.getPrice().doubleValue() * x.getQuantity().doubleValue()).sum());
        return sum;
    }

    public void setPaymentToken(Long cartId, String paymentToken) {
        Cart cart = cartRepository.findById(cartId).get();
        cart.setPaymentToken(paymentToken);
        cartRepository.save(cart);
    }

    public Order createOrder(Cart cart, String paymentMethod) {
        Order order = new Order();
        order.setCurrency(cart.getCurrency());
        //order.setCustomerId(cart.get);
        order.setInvoiceAddress(cart.getInvoiceAddress());
        order.setDeliveryAddressId(cart.getDeliveryAddressId());
        order.setDeliveryAddress(cart.getDeliveryAddress());
        order.setOrderState(OrderState.AWAITING_PAYMENT);
        String orderRef = generateOrderId();
        String uiud = createUIUD();

        order.setReference(orderRef);
        order.setConfirmationKey(uiud);
        order.setSubtotal(calculateSubtotal(cart));
        order.setTotal(calculateTotal(cart));

        order.setCarrier(cart.getCarrier());
        order.setDeliveryTotal(carrierService.getCarrierCost(cart.getCarrier()));
        order.setPaymentMethod(paymentMethod);

        //order.s

        int i = 1;
        for(LineItem item : cart.getItems()) {
            OrderItem orderItem = new OrderItem();
            orderItem.setPrice(item.getPrice());
            orderItem.setProductName(item.getName());
            orderItem.setQuantity(item.getQuantity());
            orderItem.sequence(i++);
            orderItem.setImage(item.getImage());
            orderItem.setWeight(item.getWeight());
            orderItem.setUnit(item.getUnit());
            orderItem.setLineTotal(item.getPrice().multiply(item.getQuantity()));
            order.addOrderItem(orderItem);
        }
        order = orderRepository.save(order);
        cart.setSecureKey(cart.getSecureKey() + " DONE");
        cartRepository.save(cart);

        return order;
    }

    private String generateOrderId() {
        Random generator = new Random();
        int num = generator. nextInt(8999999) + 1000000;
        return String.valueOf(num);
    }
    public static String createUIUD() {
        // Creating a random UUID (Universally unique identifier).
        UUID uuid = UUID.randomUUID();
        return uuid.toString();
    }

    public OrderDTO createOrderWithPaymentByPaymentToken(String paymentKey) {
        Cart cart = cartRepository.findByPaymentToken(paymentKey).get();
        Order order = createOrder(cart, "checkoutcom");
        Payment payment = new Payment();
        payment.setAmount(order.getTotal());
        payment.setOrder(order);
        payment.setPaymentMethod("checkoutcom");
        payment.setTransactionId(paymentKey);
        payment.setCreated_date(Instant.now());
        paymentRepository.save(payment);

        return orderMapper.toDto(order);
    }
}
