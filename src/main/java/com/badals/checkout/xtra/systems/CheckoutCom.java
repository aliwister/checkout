package com.badals.checkout.xtra.systems;

import com.badals.checkout.domain.Tenant;
import com.badals.checkout.domain.pojo.LineItem;
import com.badals.checkout.domain.pojo.PaymentProfile;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.InvalidCartException;
import com.badals.checkout.service.LockedCartException;
import com.badals.checkout.service.TenantCheckoutService;
import com.badals.checkout.service.dto.CartDTO;
import com.badals.checkout.xtra.PaymentSystem;
import com.checkout.APIClient;
import com.checkout.api.services.charge.request.CardTokenCharge;
import com.checkout.api.services.charge.request.ChargeRefund;
import com.checkout.api.services.charge.response.Charge;
import com.checkout.api.services.charge.response.Refund;
import com.checkout.api.services.shared.Product;
import com.checkout.api.services.shared.Response;

import com.checkout.helpers.Environment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.HashMap;

import static com.badals.checkout.xtra.PaymentType.CHECKOUT;

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
public class CheckoutCom extends PaymentSystem {
   private final Logger log = LoggerFactory.getLogger(CheckoutCom.class);
   //CheckoutApi api;
   private final TenantCheckoutService checkoutService;


   @Value("${checkoutcom.live}")
   private Boolean _isLive;

   @Value("${app.baseurl}")
   private String baseUrl;

   public CheckoutCom(TenantCheckoutService checkoutService) {
      this.checkoutService = checkoutService;
   }

   //public CheckoutPaymentService(CheckoutApi checkoutApi) {
    //   this.api = checkoutApi;
  // }

   public synchronized PaymentResponsePayload refundPayment(String chargeId) throws Exception {
      ChargeRefund refundPayload =new ChargeRefund();
      refundPayload.value = "100";
      refundPayload.trackId = "TRK12345";
      refundPayload.description =  "Sample product";

      refundPayload.metadata = new HashMap<String,String>();
      refundPayload.metadata.put("testKey", "value");

      refundPayload.products = new ArrayList<Product>();
      Product product1 =new Product();
      product1.description= "Tablet 2 gold limited";
      product1.name="Tablet 32gb cellular";
      product1.price=90.0;
      product1.quantity=1;
      product1.shippingCost=10.0;
      product1.sku= "1aab";
      product1.trackingUrl="https://www.tracker.com";
      refundPayload.products.add(product1);

      try {
         // Create APIClient instance with your secret key
         APIClient ckoAPIClient= new APIClient("sk_test_55aedccc-7f53-4ccc-b0a6-d943decc3c31",Environment.LIVE);
         // Submit your request and receive an apiResponse
         Response<Refund> apiResponse = ckoAPIClient.chargeService.refundRequest(chargeId,refundPayload);

         if(!apiResponse.hasError){
            // Access the response object retrieved from the api
            Refund refund = apiResponse.model;
         } else {
            // Api has returned an error object. You can access the details in the error property of the apiResponse.
            // apiResponse.error
         }
      } catch (Exception e) {}
      return null;
   }

   public synchronized PaymentResponsePayload processPayment(String cardToken, String secureKey, boolean isWebsite) throws InvalidCartException {
      //String cardToken = "card_tok_CB9C10E3-24CC-4A82-B50A-4DEFDCB15580";
      try {
         CartDTO cart = checkoutService.findBySecureKeyWithLock(secureKey);

         log.info("=========================================================================: "+cardToken);
         log.info("==========================NEW CC PAYMENT ================================: "+cardToken);
         log.info("==========================Token sent: "+cardToken);
         CardTokenChargeChild cardTokenChargePayload = new CardTokenChargeChild();
         cardTokenChargePayload.autoCapTime=1;
         cardTokenChargePayload.autoCapture="Y";
         //cardTokenChargePayload.chargeMode=1;
         cardTokenChargePayload.email = cart.getEmail();
         cardTokenChargePayload.description = "charge description";
         cardTokenChargePayload.value= String.valueOf(TenantCheckoutService.calculateValue(cart));

         cardTokenChargePayload.currency="OMR";
         cardTokenChargePayload.trackId= cart.getId().toString();
         cardTokenChargePayload.transactionIndicator = "1";
         //cardTokenChargePayload.customerIp= "96.125.185.51";
         cardTokenChargePayload.cardToken = cardToken;
         cardTokenChargePayload.successUrl = baseUrl + "checkout/callback/checkoutcom/success";;
         cardTokenChargePayload.failUrl = baseUrl + "checkout/callback/checkoutcom/failure";;


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

         Tenant tenant = checkoutService.getTenant();
         PaymentProfile profile = tenant.getPaymentProfile();
         String sk = PaymentProfile.findSkByName(profile, "checkoutcom");
         _isLive = true;
         // Create APIClient instance with your secret key
         APIClient ckoAPIClient = null;
         if(_isLive)
            ckoAPIClient= new APIClient(sk, Environment.LIVE);
         else {
            cardTokenChargePayload.attemptN3D = false;
            cardTokenChargePayload.chargeMode=2;
            ckoAPIClient = new APIClient(sk, Environment.SANDBOX);
         }
         // Submit your request and receive an apiResponse
         Response<Charge> apiResponse = ckoAPIClient.chargeService.chargeWithCardToken(cardTokenChargePayload);

         if(!apiResponse.hasError){
            // Access the response object retrieved from the api
            log.info("No Error HTTP Status " + apiResponse.httpStatus);
            if(apiResponse.model == null)
               log.info("No Model");
            log.info("No Error HTTP Status " + apiResponse.model);

            if(apiResponse.httpStatus == 200) {
               Charge charge = apiResponse.model;
               if(charge.status != null && (charge.status.equalsIgnoreCase("declined") || charge.status.equalsIgnoreCase("flagged")))
                  return paymentDeclined("Payment declined with message " + apiResponse.model.responseCode + " " + apiResponse.model.responseMessage);

               checkoutService.setPaymentToken(cart.getId(), CHECKOUT.ref, apiResponse.model.id);
               if(apiResponse.model.redirectUrl != null)
                  return redirect(apiResponse.model.redirectUrl);

               if(apiResponse.model.responseCode.equalsIgnoreCase("10000")) {
                  if(isWebsite)
                     return redirect(baseUrl + "checkout/checkout-com-confirmation?cko-payment-token="+apiResponse.model.id);
                  else
                     return paymentSucessful(apiResponse.model.id);
               }

               return paymentDeclined("Payment declined " + charge.status + apiResponse.model.responseCode + " " + apiResponse.model.responseMessage);
            }
            else {
               log.info("NO ERROR/INVALID RESPONSE DECLINING----------------------------------------------");
               return paymentDeclined("Payment declined " + apiResponse.model.responseCode + " " + apiResponse.model.responseMessage);
            }


         } else {
            // Api has returned an error object. You can access the details in the error property of the apiResponse.
            // apiResponse.error
            log.info("DECLINING----------------------------------------------");
            return paymentDeclined("Payment declined: " + ((apiResponse.hasError)?apiResponse.error.errorCode +  "-" + apiResponse.error.message:"no code"));
         }
      } catch (LockedCartException e) {
         return paymentIgnored("Exception Occurred for " + cardToken);
      } catch (Exception e) {
         e.printStackTrace();
      } finally {
         checkoutService.unlock(secureKey);
      }

      return paymentDeclined("Exception Occurred for " + cardToken);
   }

/*   @Deprecated
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
*/
}
