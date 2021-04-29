package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.Order;
import com.badals.checkout.domain.OrderItem;
import com.badals.checkout.domain.pojo.*;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.repository.OrderRepository;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.dto.OrderDTO;
import com.badals.checkout.service.mapper.CartMapper;
import com.badals.enumeration.OrderState;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

import static com.badals.checkout.domain.pojo.PaymentMethod.BANK;
import static com.badals.checkout.domain.pojo.PaymentMethod.CHECKOUT;
import static com.badals.checkout.domain.pojo.PaymentStatus.SUCCESS;

@Service
public class PaymentService {
    private final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CheckoutPaymentService checkoutPaymentService;

    @Autowired
    CartMapper cartMapper;

    @Value("${app.faceurl}")
    String faceUrl;

    List<PaymentMethod> list = new ArrayList<PaymentMethod>() {{
        //add(new PaymentMethod("omannet", "Omannet", "", true));
        add(PaymentMethod.CHECKOUT);
        add(BANK);
    }};

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Transactional(readOnly = true)
    public List<PaymentMethod> findByCurrency(String currency) {
        return list;
    }

    public PaymentResponsePayload processPayment(String paymentMethod, String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        Order order = cartService.createOrder(cart, paymentMethod, false);
        return new PaymentResponsePayload("Success",faceUrl+ "order-received?ref="+order.getReference()+"&key="+order.getConfirmationKey(), SUCCESS);
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public PaymentResponsePayload processPayment(String token, String ref, String secureKey) throws InvalidCartException {
       Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
       PaymentResponsePayload response = null;

       if(ref.equals(CHECKOUT.ref) && token != null) {
           response = checkoutPaymentService.processPayment(token, secureKey, false);
       }

       else if(ref.equals(BANK.ref) ) {
           UUID idOne = UUID.randomUUID();
           String guid = idOne.toString();
           cartService.setPaymentToken(cart.getId(), BANK.ref, guid);
           response = new PaymentResponsePayload("Success", guid, SUCCESS);
       }
       return response;
   }
}
