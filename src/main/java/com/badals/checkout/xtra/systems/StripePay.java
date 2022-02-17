package com.badals.checkout.xtra.systems;

import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.InvalidCartException;
import com.badals.checkout.service.LockedCartException;
import com.badals.checkout.service.TenantCheckoutService;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.xtra.PaymentSystem;

/*
import static spark.Spark.get;
import static spark.Spark.post;
import static spark.Spark.staticFiles;
import static spark.Spark.port;
*/

import com.badals.checkout.xtra.PaymentType;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/*
import com.stripe.Stripe;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
*/

@Component
public class StripePay extends PaymentSystem {
   @Autowired
   TenantCheckoutService cartService;

   public synchronized PaymentResponsePayload processPayment(String sk, String secureKey, boolean isWebsite) {

      CartDTO cart = null;
      try {
         cart = cartService.findBySecureKeyWithLock(secureKey);
         Stripe.apiKey = sk;
         PaymentIntentCreateParams params =
                 PaymentIntentCreateParams
                         .builder()
                         .setAmount(cartService.calculateValue(cart))
                         .setCurrency(cart.getCurrency())
                         .setCustomer(cart.getName())
                         .setAutomaticPaymentMethods(
                                 PaymentIntentCreateParams.AutomaticPaymentMethods
                                         .builder()
                                         .setEnabled(true)
                                         .build()
                         )
                         .build();

         PaymentIntent paymentIntent = null;
         paymentIntent = PaymentIntent.create(params);
         cartService.setPaymentToken(cart.getId(), PaymentType.STRIPE.ref, paymentIntent.getClientSecret());
         return paymentSucessful(paymentIntent.getClientSecret());
      } catch (InvalidCartException e) {
         return paymentDeclined(e.getMessage());
      } catch (LockedCartException e) {
         return paymentDeclined(e.getMessage());
      } catch (StripeException e) {
         return paymentDeclined(e.getUserMessage());
      } finally {
         if(cart != null)
            cartService.unlock(secureKey);
      }

   }
}
