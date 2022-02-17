package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.Order;
import com.badals.checkout.domain.pojo.*;
import com.badals.checkout.repository.CartRepository;
import com.badals.checkout.repository.OrderRepository;
import com.badals.checkout.service.mapper.CartMapper;
import com.badals.checkout.xtra.PaymentType;
import com.badals.checkout.xtra.systems.CheckoutCom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.badals.checkout.xtra.PaymentType.*;
import static com.badals.checkout.domain.pojo.PaymentStatus.REDIRECT;
import static com.badals.checkout.domain.pojo.PaymentStatus.SUCCESS;

@Service
public class PaymentService {
    private final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CheckoutCom checkoutCom;

    @Autowired
    CartMapper cartMapper;

    @Value("${app.faceurl}")
    String faceUrl;

    List<PaymentType> list = new ArrayList<PaymentType>() {{
        //add(new PaymentMethod("omannet", "Omannet", "", true));
        add(PaymentType.CHECKOUT);
        add(BANK);
        add(STRIPE);
    }};

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CartService cartService;

    @Transactional(readOnly = true)
    public List<PaymentType> findByCurrency(String currency) {
        return list;
    }

    @Transactional
    public PaymentResponsePayload processPaymentWeb(String paymentMethod, String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        Order order = cartService.createOrder(cart, paymentMethod, false);
        PaymentResponsePayload response = new PaymentResponsePayload("Success",faceUrl+ "order-received?ref="+order.getReference()+"&key="+order.getConfirmationKey(), REDIRECT);
        return response;
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public PaymentResponsePayload processPayment(String token, String ref, String secureKey) throws InvalidCartException {
       Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
       PaymentResponsePayload response = null;

       if(ref.equals(CHECKOUT.ref) && token != null) {
           response = checkoutCom.processPayment(token, secureKey, false);
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
