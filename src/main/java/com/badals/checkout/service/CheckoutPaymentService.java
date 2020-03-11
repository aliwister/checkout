package com.badals.checkout.service;

import com.badals.checkout.domain.Cart;
import com.badals.checkout.domain.pojo.LineItem;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.domain.pojo.PaymentStatus;
import com.badals.checkout.service.dto.CartDTO;
import com.checkout.*;
import com.checkout.api.services.charge.request.CardTokenCharge;
import com.checkout.api.services.charge.response.Charge;
import com.checkout.api.services.shared.Product;
import com.checkout.api.services.shared.Response;
import com.checkout.common.Address;
import com.checkout.common.Currency;
import com.checkout.helpers.Environment;
import com.checkout.payments.*;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

class CardTokenChargeChild extends CardTokenCharge {
   public String successUrl;
   public String failUrl;
}
class CheckoutProduct extends Product {
   public CheckoutProduct(String name, String description, String sku, Double price, Integer quantity) {
      this.name = name;
      this.description =         description;
      this.sku = sku;
      this.price =         price;
      this.quantity = quantity;
   }
}

@Service
public class CheckoutPaymentService {
   private final Logger log = LoggerFactory.getLogger(CheckoutPaymentService.class);
   CheckoutApi api;

   @Autowired
   CartService cartService;

   @Value("${checkoutcom.sk}")
   private String _sk;

   @Value("${app.baseurl}")
   private String baseUrl;

   public CheckoutPaymentService(CheckoutApi checkoutApi) {
       this.api = checkoutApi;
   }

   public PaymentResponsePayload processPayment(String cardToken, String secureKey) {
      //String cardToken = "card_tok_CB9C10E3-24CC-4A82-B50A-4DEFDCB15580";

      CartDTO cart = cartService.findBySecureKey(secureKey);

      log.info("Token sent: "+cardToken);
      CardTokenChargeChild cardTokenChargePayload = new CardTokenChargeChild();
      cardTokenChargePayload.autoCapTime=24;
      cardTokenChargePayload.autoCapture="Y";
      //cardTokenChargePayload.chargeMode=1;
      cardTokenChargePayload.email = "testuser@email.com";
      cardTokenChargePayload.description = "charge description";
      cardTokenChargePayload.value=String.valueOf(cartService.calculateValue(cart).doubleValue() * 100.0);

      cardTokenChargePayload.currency="OMR";
      cardTokenChargePayload.trackId= "TRK12345";
      cardTokenChargePayload.transactionIndicator = "1";
      //cardTokenChargePayload.customerIp= "96.125.185.51";
      cardTokenChargePayload.cardToken = cardToken;
      cardTokenChargePayload.successUrl = baseUrl + "/checkout/checkout-com-confirmation";;
      cardTokenChargePayload.failUrl = baseUrl + "/checkout/checkout-com-confirmation";;


      cardTokenChargePayload.metadata = new HashMap<String,String>();
      cardTokenChargePayload.metadata.put("key1", "value1");

      cardTokenChargePayload.udf1="ali";
      cardTokenChargePayload.udf2="is";
      cardTokenChargePayload.udf3="here";
      cardTokenChargePayload.udf4="udf 4 value";
      cardTokenChargePayload.udf5="udf 5 value";

      cardTokenChargePayload.products = new ArrayList<Product>();
      for (LineItem item: cart.getItems())
         cardTokenChargePayload.products.add(new CheckoutProduct(item.getName(), "", item.getSku(), item.getPrice().doubleValue(), item.getQuantity().intValue()));


      try {
         // Create APIClient instance with your secret key
         APIClient ckoAPIClient= new APIClient(_sk, Environment.LIVE);
         // Submit your request and receive an apiResponse
         Response<Charge> apiResponse = ckoAPIClient.chargeService.chargeWithCardToken(cardTokenChargePayload);

         if(!apiResponse.hasError){
            // Access the response object retrieved from the api

            if(apiResponse.httpStatus == 200) {
               cartService.setPaymentToken(cart.getId(), apiResponse.model.id);
               if(apiResponse.model.redirectUrl != null)
                  return redirect(apiResponse.model.redirectUrl);
            }

            Charge charge = apiResponse.model;
         } else {
            // Api has returned an error object. You can access the details in the error property of the apiResponse.
            // apiResponse.error
            return paymentDeclined("Payment declined " + apiResponse.model.responseCode + " " + apiResponse.model.responseMessage);
         }
      } catch (Exception e) {}

      return new PaymentResponsePayload("");
   }

   @Deprecated
   public PaymentResponsePayload processPayment(String token) throws InterruptedException, ExecutionException {
      log.info("Token sent: "+token);
      TokenSource tokenSource = new TokenSource(token);
      PaymentRequest<TokenSource> paymentRequest = PaymentRequest.fromSource(tokenSource, Currency.OMR, 500L);
      paymentRequest.setReference("12321322");
      paymentRequest.setCapture(true);
      paymentRequest.setThreeDS(ThreeDSRequest.from(false));
      CustomerRequest x = new CustomerRequest();
      x.setName("Ali Hussain Mohsin");
      x.setEmail("aliwister@gmail.com");
      paymentRequest.setCustomer(x);
      paymentRequest.getSource().setBillingAddress(new Address("Al Ansab", "Bawshar", "Muscat","","","OM"));

      try {
         PaymentResponse response = api.paymentsClient().requestAsync(paymentRequest).get();

         if (response.isPending() && response.getPending().requiresRedirect()) {
            return redirect(response.getPending().getRedirectLink().getHref());
         }

         if (response.getPayment().isApproved())
            return paymentSucessful(response.getPayment());

         return paymentDeclined("Declined");
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

   private PaymentResponsePayload paymentDeclined(String message) {
      return new PaymentResponsePayload(message, null, PaymentStatus.DECLINED);
   }

   private PaymentResponsePayload redirect(String href) {
      return new PaymentResponsePayload("Payment Successful", href, PaymentStatus.REDIRECT);
   }
   
}
