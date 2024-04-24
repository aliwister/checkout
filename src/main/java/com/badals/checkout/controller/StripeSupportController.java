package com.badals.checkout.controller;

import com.badals.checkout.domain.Tenant;
import com.badals.checkout.domain.pojo.PaymentDef;
import com.badals.checkout.domain.pojo.PaymentProfile;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.CheckoutException;

import com.badals.checkout.service.TenantCheckoutService;
import com.badals.checkout.xtra.PaymentType;
import com.badals.checkout.xtra.systems.StripePay;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;

import com.stripe.exception.StripeException;

@Controller
@RequestMapping("api/stripe-support")
public class StripeSupportController {

   private final StripePay stripe;
   private final TenantCheckoutService tenantCheckoutService;

   public StripeSupportController(StripePay stripe, TenantCheckoutService TenantCheckoutService) {
      this.stripe = stripe;
      this.tenantCheckoutService = TenantCheckoutService;
   }

   @GetMapping(value="/test-stripe", produces = "application/json")
   public @ResponseBody Boolean test() {
      return false;
   }

   @PostMapping(value = "/create-payment-intent", produces = "application/json")
   public @ResponseBody PaymentResponsePayload request(@RequestParam String token) throws URISyntaxException, StripeException, CheckoutException {
      Tenant tenant = tenantCheckoutService.getTenant();
      PaymentProfile profile = tenant.getPaymentProfile();
      PaymentDef def =  profile.getType(PaymentType.STRIPE);
      return stripe.processPayment(def.getSk(), token, true);
   }

   @PostMapping(value = "/create-payment-intent-shipment", produces = "application/json")
   public @ResponseBody PaymentResponsePayload shipmentStripePayment(@RequestBody ShipmentStripePaymentDto shipmentStripePayementDto) throws URISyntaxException, StripeException, CheckoutException {
      Tenant tenant = tenantCheckoutService.getTenant();
      PaymentProfile profile = tenant.getPaymentProfile();
      PaymentDef def =  profile.getType(PaymentType.STRIPE);
      return stripe.processPayment(def.getSk(), shipmentStripePayementDto.getAmount());
   }

/*   @PostMapping(value = "/create-payment-profile", produces = "application/json")
   public @ResponseBody
   String create(String token) throws URISyntaxException, StripeException, CheckoutException {
      Tenant tenant = new Tenant();
      PaymentProfile paymentProfile = new PaymentProfile();
      PaymentDef def = new PaymentDef();
      def.setName("stripe");
      def.setPk("asdfdsaf");
      def.setSk("aldkjladsf");
      paymentProfile.addPayment(def);
      tenant.setTenantId("badals");
      tenant.setActive(true);
      tenant.setDomain("badals.com");
      tenant.setPaymentProfile(paymentProfile);
      tenantRepository.save(tenant);
      return "success";
   }*/


}