package com.badals.checkout.controller;

import com.badals.checkout.domain.Tenant;
import com.badals.checkout.domain.pojo.PaymentDef;
import com.badals.checkout.domain.pojo.PaymentProfile;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.exception.CheckoutException;
import com.badals.checkout.repository.TenantRepository;

import com.badals.checkout.xtra.PaymentType;
import com.badals.checkout.xtra.systems.StripePay;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.List;

import com.stripe.exception.StripeException;

@Controller
@RequestMapping("stripe-support")
public class StripeSupportController {

   @Autowired
   StripePay stripe;

   @Autowired
   TenantRepository tenantRepository;

   @GetMapping(value="/test-stripe", produces = "application/json")
   public @ResponseBody Boolean test() {
      return true;
   }

   @PostMapping(value = "/create-payment-intent", produces = "application/json")
   public @ResponseBody
   PaymentResponsePayload request(@RequestParam String token) throws URISyntaxException, StripeException, CheckoutException {
      List<Tenant> tenants = tenantRepository.findAll();
      Tenant tenant = null;
      if(tenants.size() < 1)
         throw new CheckoutException("Invalid Payment");

      tenant = tenants.get(0);
      PaymentProfile profile = tenant.getPaymentProfile();
      PaymentDef def =  profile.getType(PaymentType.STRIPE);
      return stripe.processPayment(def.getSk(), token, true);
   }

   @PostMapping(value = "/create-payment-profile", produces = "application/json")
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
   }


}