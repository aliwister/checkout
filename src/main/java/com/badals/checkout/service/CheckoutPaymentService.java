package com.badals.checkout.service;

import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.domain.pojo.PaymentStatus;
import com.checkout.CheckoutApi;
import com.checkout.CheckoutApiException;
import com.checkout.CheckoutApiImpl;
import com.checkout.CheckoutValidationException;
import com.checkout.common.Currency;
import com.checkout.payments.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class CheckoutPaymentService {
   private final Logger log = LoggerFactory.getLogger(CheckoutPaymentService.class);
   CheckoutApi api;

   public CheckoutPaymentService(CheckoutApi checkoutApi) {
       this.api = checkoutApi;
   }

   public PaymentResponsePayload processPayment(String token) throws InterruptedException, ExecutionException {
      TokenSource tokenSource = new TokenSource(token);
      PaymentRequest<TokenSource> paymentRequest = PaymentRequest.fromSource(tokenSource, Currency.OMR, 10L);
      paymentRequest.setReference("ORD-090857");
      paymentRequest.setCapture(false);
      paymentRequest.setThreeDS(ThreeDSRequest.from(true));

      try {
         PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();

         if (response.isPending() && response.getPending().requiresRedirect()) {
            return redirect(response.getPending().getRedirectLink().getHref());
         }

         if (response.getPayment().isApproved())
            return paymentSucessful(response.getPayment());

         return paymentDeclined(response.getPayment());
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

   private PaymentResponsePayload paymentSucessful(PaymentProcessed payment) {
      return new PaymentResponsePayload("Payment Successful", null, PaymentStatus.SUCCESS);
   }

   private PaymentResponsePayload paymentDeclined(PaymentProcessed payment) {
      return new PaymentResponsePayload("Payment Declined", null, PaymentStatus.DECLINED);
   }

   private PaymentResponsePayload redirect(String href) {
      return new PaymentResponsePayload("Payment Successful", href, PaymentStatus.REDIRECT);
   }
   
}
