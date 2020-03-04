package com.badals.checkout.service.query;

import com.badals.checkout.service.CartService;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.service.mutation.Mutation;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

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

    public String test(String param) {
        return "Im a query";
    }

    public CartDTO cart(String secureKey) {
        return cartService.findBySecureKey(secureKey);
    }
}
