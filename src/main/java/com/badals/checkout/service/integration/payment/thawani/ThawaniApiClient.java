package com.badals.checkout.service.integration.payment.thawani;

import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutSessionBody;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCustomerBody;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreatePaymentIntentBody;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCheckoutSessionResponse;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCustomer;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreatePaymentIntentResponse;
import com.badals.checkout.service.integration.payment.thawani.dto.response.ThawaniResponse;
import feign.Headers;
import feign.Param;
import feign.RequestLine;

@Headers("thawani-api-key: {secretKey}")
public interface ThawaniApiClient {

    @RequestLine("POST /customers")
    ThawaniResponse<CreateCustomer> createCustomer(CreateCustomerBody request);

    @RequestLine("POST /checkout/session")
    ThawaniResponse<CreateCheckoutSessionResponse> createCheckoutSession(@Param("secretKey") String secretKey, CreateCheckoutSessionBody request);

    @RequestLine("POST /payment_intents")
    ThawaniResponse<CreatePaymentIntentResponse> createPaymentIntent(CreatePaymentIntentBody request);
}
