package com.badals.checkout.service;

import com.badals.checkout.domain.Checkout;
import com.badals.checkout.domain.TenantOrder;
import com.badals.checkout.domain.pojo.*;
import com.badals.checkout.repository.CheckoutRepository;

import com.badals.checkout.service.integration.payment.thawani.ThawaniPaymentService;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutSessionBody;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutMetaData;
import com.badals.checkout.service.integration.payment.thawani.dto.request.Product;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCheckoutSessionResponse;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCustomer;
import com.badals.checkout.service.integration.payment.thawani.dto.response.ThawaniResponse;
import com.badals.checkout.xtra.PaymentType;
import com.badals.checkout.xtra.systems.CheckoutCom;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

import static com.badals.checkout.domain.pojo.PaymentStatus.REDIRECT;
import static com.badals.checkout.xtra.PaymentType.*;
import static com.badals.checkout.domain.pojo.PaymentStatus.SUCCESS;

@Service
public class PaymentService {
    private final Logger log = LoggerFactory.getLogger(PaymentService.class);

    private final CheckoutRepository checkoutRepository;
    private final CheckoutCom checkoutCom;
    private final TenantCheckoutService checkoutService;
    private final ThawaniPaymentService thawaniPaymentService;


    @Value("${app.faceurl}")
    String faceUrl;

    List<PaymentType> list = new ArrayList<PaymentType>() {{
        //add(new PaymentMethod("omannet", "Omannet", "", true));
        add(PaymentType.CHECKOUT);
        add(BANK);
        add(STRIPE);
    }};

    public PaymentService(CheckoutRepository checkoutRepository, CheckoutCom checkoutCom, TenantCheckoutService checkoutService, ThawaniPaymentService thawaniPaymentService) {
        this.checkoutRepository = checkoutRepository;
        this.checkoutCom = checkoutCom;
        this.checkoutService = checkoutService;
        this.thawaniPaymentService = thawaniPaymentService;
    }

    @Transactional(readOnly = true)
    public List<PaymentType> findByCurrency(String currency) {
        return list;
    }

    @Transactional
    public PaymentResponsePayload processPaymentWeb(String paymentMethod, String secureKey) throws InvalidCartException {
        Checkout cart = checkoutRepository.findBySecureKey(secureKey).orElse(null);
        TenantOrder order = checkoutService.createOrder(cart, paymentMethod, false);
        PaymentResponsePayload response = new PaymentResponsePayload("Success",order.getConfirmationKey(), SUCCESS, order.getReference());
        return response;
    }
    @PreAuthorize("hasRole('ROLE_USER')")
    public PaymentResponsePayload processPayment(String token, String ref, String secureKey) throws InvalidCartException {
        Checkout cart = checkoutRepository.findBySecureKey(secureKey).orElse(null);
       PaymentResponsePayload response = null;

       if(ref.equals(CHECKOUT.ref) && token != null) {
           response = checkoutCom.processPayment(token, secureKey, false);
       }

       else if(ref.equals(BANK.ref) ) {
           UUID idOne = UUID.randomUUID();
           String guid = idOne.toString();
           checkoutService.setPaymentToken(cart.getId(), BANK.ref, guid);
           response = new PaymentResponsePayload("Success", guid, SUCCESS);
       }
       return response;
   }
}
