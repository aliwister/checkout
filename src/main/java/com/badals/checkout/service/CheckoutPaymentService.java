package com.badals.checkout.service;

import com.checkout.CheckoutApi;
import com.checkout.CheckoutApiException;
import com.checkout.CheckoutApiImpl;
import com.checkout.CheckoutValidationException;
import com.checkout.common.Currency;
import com.checkout.payments.PaymentRequest;
import com.checkout.payments.PaymentResponse;
import com.checkout.payments.ThreeDSRequest;
import com.checkout.payments.TokenSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class CheckoutPaymentService {
   private final Logger log = LoggerFactory.getLogger(CheckoutPaymentService.class);
   CheckoutApi api;

   public CheckoutPaymentService() {
      this.api = CheckoutApiImpl.create("sk_56109822-a7d8-41f7-ae5d-c332b6bcd995", false, "pk_932e59ef-2d33-448d-80cd-8668691640fe");
   }

   public PaymentResponse processPayment(String token) throws InterruptedException, ExecutionException {
      TokenSource tokenSource = new TokenSource(token);
      PaymentRequest<TokenSource> paymentRequest = PaymentRequest.fromSource(tokenSource, Currency.OMR, 10L);
      paymentRequest.setReference("ORD-090857");
      paymentRequest.setCapture(false);
      paymentRequest.setThreeDS(ThreeDSRequest.from(true));

      try {
         PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();

         if (response.isPending() && response.getPending().requiresRedirect()) {
            return response; //redirect(response.getPending().getRedirectLink().getHref());
         }

         if (response.getPayment().isApproved())
            return response; //paymentSucessful(response.getPayment());

         return response; //paymentDeclined(response.getPayment());
      } catch (CheckoutValidationException e) {
         e.printStackTrace();
         //return validationError(e.getError());
         throw e;
      } catch (CheckoutApiException e) {
         log.error("Payment request failed with status code " + e.getMessage());
         throw e;
      } catch (InterruptedException e) {
         e.printStackTrace();
         throw e;
      } catch (ExecutionException e) {
         e.printStackTrace();
         throw e;
      }
   }
}
