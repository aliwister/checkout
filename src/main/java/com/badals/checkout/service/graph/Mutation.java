package com.badals.checkout.service.graph;

import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.*;
import com.badals.checkout.service.dto.CartDTO;

import com.badals.checkout.service.mapper.CartMapper;
import com.badals.checkout.service.pojo.Message;
import com.badals.checkout.xtra.systems.CheckoutCom;
import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

/*
mutation {
  updateShippingAddress(address: {name:"Ali", line1:"line1", line2:"line2", city:"city", country:"oman", postalCode:"133"}) {
    value
  }
}
 */

@Component
public class Mutation implements GraphQLMutationResolver {
    private final Logger log = LoggerFactory.getLogger(Mutation.class);

    private final CartMapper cartMapper;

    private final TenantCheckoutService tenantCheckoutService;

    private final CarrierService carrierService;

    private final CheckoutCom checkoutCom;
    private final PaymentService paymentService;

    public Mutation(CartMapper cartMapper, TenantCheckoutService tenantCheckoutService, CarrierService carrierService, CheckoutCom checkoutCom, PaymentService paymentService) {
        this.cartMapper = cartMapper;
        this.tenantCheckoutService = tenantCheckoutService;
        this.carrierService = carrierService;
        this.checkoutCom = checkoutCom;
        this.paymentService = paymentService;
    }

    public CartDTO setTenantInfo(String email, Address address, String secureKey, String carrier) {
        log.info("REST request to save Address : {}", address);
        return tenantCheckoutService.setDeliveryAddressAndEmail(address, email, secureKey, carrier);
        //return new Message("success");
    }

    public PaymentResponsePayload processPayment(String token, String ref, String secureKey) throws Exception{
        if(ref.equals("checkoutcom") && token != null)
           return checkoutCom.processPayment(token, secureKey, true); //, name);
        return paymentService.processPaymentWeb(ref, secureKey);
    }

    public PaymentResponsePayload processTenantPayment(String token, String ref, String secureKey) throws Exception{
        if(ref.equals("checkoutcom") && token != null)
           return checkoutCom.processTenantPayment(token, secureKey, true); //, name);
        return paymentService.processPaymentWeb(ref, secureKey);
    }

    public CartDTO setTenantCarrier(Long value, String secureKey) throws InvalidCartException {
        return carrierService.setTenantCarrier(value, secureKey);
    }

    public PaymentResponsePayload chargePayment(String token, String ref, String secureKey) throws Exception {
        return paymentService.processPayment(token, ref, secureKey);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Message useReward(String secureKey, String reward){
        return tenantCheckoutService.useReward(secureKey, reward);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    public Message removeReward(String secureKey, String reward){
        return tenantCheckoutService.removeReward(secureKey, reward);
    }
}
