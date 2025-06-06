package com.badals.checkout.service.query;

import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.Carrier;
import com.badals.checkout.domain.pojo.PaymentMethod;
import com.badals.checkout.service.CarrierService;
import com.badals.checkout.service.CartService;
import com.badals.checkout.service.InvalidCartException;
import com.badals.checkout.service.PaymentService;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mutation.Mutation;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

/*
query {
  cart(secureKey: "abc") {
    items {
      name
    }
    name
    email
    phone
  }
}
 */


@Component
public class Query implements GraphQLQueryResolver {
    private final Logger log = LoggerFactory.getLogger(Query.class);

    @Autowired
    private CartService cartService;

    @Autowired
    private CarrierService carrierService;

    @Autowired
    private PaymentService paymentService;

    public String test(String param) {
        return "Im a query";
    }

    public CartDTO cart(String secureKey) {
        return cartService.findBySecureKey(secureKey);
    }

    public List<Carrier> carriers(String secureKey) throws InvalidCartException {
        return carrierService.findByStateCityWeight(secureKey);
    }
    public List<PaymentMethod> paymentMethods(String currency) {
        return paymentService.findByCurrency(currency);
    }
}
