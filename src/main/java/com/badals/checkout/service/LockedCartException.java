package com.badals.checkout.service;

import com.badals.checkout.domain.Checkout;

import java.util.function.Supplier;

public class LockedCartException extends Exception implements Supplier<Checkout> {
   public LockedCartException(String msg) {
      super(msg);
   }

   @Override
   public Checkout get() {
      return null;
   }
}
