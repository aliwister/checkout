package com.badals.checkout.xtra;

import com.badals.checkout.domain.Payment;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.domain.pojo.PaymentStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class PaymentSystem {
   private final Logger log = LoggerFactory.getLogger(PaymentSystem.class);

   protected PaymentResponsePayload paymentSucessful(String payload) {
      return new PaymentResponsePayload("Payment Successful", payload, PaymentStatus.SUCCESS);
   }

   protected PaymentResponsePayload paymentDeclined(String message) {
      log.warn("DECLINE MESSAGE: " + message);
      return new PaymentResponsePayload(message, null, PaymentStatus.DECLINED);
   }

   protected PaymentResponsePayload paymentIgnored(String message) {
      log.warn("IGNORED MESSAGE: " + message);
      return new PaymentResponsePayload(message, null, PaymentStatus.IGNORED);
   }

   protected PaymentResponsePayload redirect(String href) {
      return new PaymentResponsePayload("Payment Successful", href, PaymentStatus.REDIRECT);
   }

   public PaymentResponsePayload processTenantPayment(String token, String secureKey, boolean b) {
      return new PaymentResponsePayload("Payment Successful", "http://www.google.com", PaymentStatus.REDIRECT);

   }
}
