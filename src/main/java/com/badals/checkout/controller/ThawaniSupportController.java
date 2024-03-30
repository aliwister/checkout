package com.badals.checkout.controller;

import com.badals.checkout.domain.Tenant;
import com.badals.checkout.domain.pojo.PaymentDef;
import com.badals.checkout.domain.pojo.PaymentProfile;
import com.badals.checkout.domain.pojo.PaymentResponsePayload;
import com.badals.checkout.service.CheckoutException;
import com.badals.checkout.service.TenantCheckoutService;
import com.badals.checkout.xtra.PaymentType;
import com.badals.checkout.xtra.systems.ThawaniPay;

import com.stripe.exception.StripeException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.URISyntaxException;

@Controller
@RequestMapping("api/thawani-support")
public class ThawaniSupportController {

    private final ThawaniPay thawaniPay;
    private final TenantCheckoutService tenantCheckoutService;

    public ThawaniSupportController(ThawaniPay thawaniPay, TenantCheckoutService tenantCheckoutService) {
        this.thawaniPay = thawaniPay;
        this.tenantCheckoutService = tenantCheckoutService;
    }

    @GetMapping(value="/test-thawani", produces = "application/json")
    public @ResponseBody Boolean test() {
        return false;
    }

    @GetMapping(value="/create-checkout-session", produces = "application/json")
    public @ResponseBody PaymentResponsePayload request(@RequestParam String token) throws URISyntaxException, StripeException, CheckoutException {
        Tenant tenant = tenantCheckoutService.getTenant();
        PaymentProfile profile = tenant.getPaymentProfile();
        PaymentDef def =  profile.getType(PaymentType.THAWANI);
        return thawaniPay.processPayment(def.getSk(), token, true);
    }

}
