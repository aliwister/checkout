package com.badals.checkout.service;

import com.badals.checkout.domain.Checkout;

import java.util.function.Supplier;

public class InvalidCartException extends Exception implements Supplier<Checkout> {
   public InvalidCartException(String msg) {
      super(msg);
   }

   @Override
   public Checkout get() {
      return null;
   }
}
