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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.*;

@Service
public class PaymentService {
    private final Logger log = LoggerFactory.getLogger(PaymentService.class);

    @Autowired
    CartRepository cartRepository;

    @Autowired
    CartMapper cartMapper;

    List<PaymentMethod> list = new ArrayList<PaymentMethod>() {{
        add(new PaymentMethod("omannet", "Omannet", "", true));
        add(new PaymentMethod("checkoutcom", "Checkout.com", "https://www.checkout.com/static/img/logos/cko/checkout-logo.svg", true));
        add(new PaymentMethod("bankwire", "Deposit to Bank Account", "",false));
    }};

    @Autowired
    private OrderRepository orderRepository;
    private CartService cartService;

    @Transactional(readOnly = true)
    public List<PaymentMethod> findByCurrency(String currency) {
        return list;
    }

    public PaymentResponsePayload processPayment(String ref, String secureKey) throws InvalidCartException {
        Cart cart = cartRepository.findBySecureKey(secureKey).orElse(null);
        Order order = cartService.createOrder(cart);
        return new PaymentResponsePayload("Success","/checkout/confirmation?ref="+order.getReference()+"&uiud="+order.getConfirmationKey(), PaymentStatus.SUCCESS);
    }
}
