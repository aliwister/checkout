package com.badals.checkout.service.mutation;

import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.CartService;
import com.badals.checkout.service.CheckoutPaymentService;
import com.badals.checkout.service.dto.CartDTO;
import com.checkout.payments.PaymentResponse;
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
    CheckoutPaymentService checkoutPaymentService;

    public CartDTO updateInfo(Address address, String secureKey) {
        log.info("REST request to save Address : {}", address);
        return cartService.setDeliveryAddress(address, secureKey);
        //return new Message("success");
    }
    public CartDTO setInfo(String email, Address address, String secureKey) {
        log.info("REST request to save Address : {}", address);
        return cartService.setDeliveryAddressAndEmail(address, email, secureKey);
        //return new Message("success");
    }
    public PaymentResponsePayload processPayment(String token) throws Exception{
        PaymentResponsePayload response = checkoutPaymentService.processPayment(token);
        return  response;
    }
}
