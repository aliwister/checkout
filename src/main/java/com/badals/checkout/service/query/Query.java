package com.badals.checkout.service.query;

import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.Carrier;
import com.badals.checkout.domain.pojo.PaymentMethod;
import com.badals.checkout.service.*;
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

    private final CartService cartService;

    private final TenantCartService tenantCartService;

    private final CarrierService carrierService;

    private final PaymentService paymentService;

    public Query(CartService cartService, TenantCartService tenantCartService, CarrierService carrierService, PaymentService paymentService) {
        this.cartService = cartService;
        this.tenantCartService = tenantCartService;
        this.carrierService = carrierService;
        this.paymentService = paymentService;
    }

    public String test(String param) {
        return "Im a query";
    }

    public CartDTO cart(String secureKey) {
        return cartService.findBySecureKey(secureKey);
    }
    public CartDTO tenantCart(String secureKey) {
        return tenantCartService.findBySecureKey(secureKey);
    }

    public List<Carrier> carriers(String secureKey) throws InvalidCartException {
        return carrierService.findByStateCityWeight(secureKey);
    }
    public List<PaymentMethod> paymentMethods(String currency) {
        return paymentService.findByCurrency(currency);
    }
}
