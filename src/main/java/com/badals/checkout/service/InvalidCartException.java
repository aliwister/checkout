package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;

import java.util.function.Supplier;

public class InvalidCartException extends Exception implements Supplier<Cart> {
   public InvalidCartException(String msg) {
      super(msg);
   }

   @Override
   public Cart get() {
      return null;
   }
}
