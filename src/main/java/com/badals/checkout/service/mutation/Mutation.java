package com.badals.checkout.service.mutation;

import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.Carrier;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.*;
import com.badals.checkout.service.dto.CartDTO;

import com.coxautodev.graphql.tools.GraphQLMutationResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    CartService cartService;

    @Autowired
    private CarrierService carrierService;

    @Autowired
    CheckoutPaymentService checkoutPaymentService;

    @Autowired
    private PaymentService paymentService;

    public CartDTO updateInfo(Address address, String secureKey) {
        log.info("REST request to save Address : {}", address);
        return cartService.setDeliveryAddress(address, secureKey);
        //return new Message("success");
    }
    public CartDTO setInfo(String email, Address address, String secureKey, String carrier) {
        log.info("REST request to save Address : {}", address);
        return cartService.setDeliveryAddressAndEmail(address, email, secureKey, carrier);
        //return new Message("success");
    }
    public PaymentResponsePayload processPayment(String token, String ref, String secureKey) throws Exception{

        if(ref.equals("checkoutcom") && token != null)
           return checkoutPaymentService.processPayment(token, secureKey); //, name);
        return paymentService.processPayment(ref, secureKey);
    }
    public CartDTO setCarrier(String value, String secureKey) throws InvalidCartException {
        return carrierService.setCarrier(value, secureKey);
    }
}
