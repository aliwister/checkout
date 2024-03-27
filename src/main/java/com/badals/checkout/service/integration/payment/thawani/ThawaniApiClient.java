package com.badals.checkout.service.integration.payment.thawani;

import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutBody;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCustomerBody;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreatePaymentIntentBody;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCheckoutSession;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCustomer;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreatePaymentIntentResponse;
import com.badals.checkout.service.integration.payment.thawani.dto.response.ThawaniResponse;
import feign.RequestLine;

public interface ThawaniApiClient {

    @RequestLine("POST /customers")
    ThawaniResponse<CreateCustomer> createCustomer(CreateCustomerBody request);

    @RequestLine("POST /checkout/session")
    ThawaniResponse<CreateCheckoutSession> createCheckoutSession(CreateCheckoutBody request);

    @RequestLine("POST /payment_intents")
    ThawaniResponse<CreatePaymentIntentResponse> createPaymentIntent(CreatePaymentIntentBody request);
}
