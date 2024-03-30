package com.badals.checkout.service.integration.payment.thawani;

import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutSessionBody;
import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCustomerBody;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCheckoutSessionResponse;
import com.badals.checkout.service.integration.payment.thawani.dto.response.CreateCustomer;
import com.badals.checkout.service.integration.payment.thawani.dto.response.ThawaniResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class ThawaniPaymentService {

    private final Logger log = LoggerFactory.getLogger(ThawaniPaymentService.class);
    private final ThawaniApiClient thawaniApiClient;

    ThawaniPaymentService(ThawaniApiClient thawaniApiClient) {
        this.thawaniApiClient = thawaniApiClient;
    }

    public ThawaniResponse<CreateCustomer> createCustomer(String clientCustomerId){
        return thawaniApiClient.createCustomer(new CreateCustomerBody(clientCustomerId));
    }

    public ThawaniResponse<CreateCheckoutSessionResponse> createCheckoutSession(String secretKey, CreateCheckoutSessionBody request){
        return thawaniApiClient.createCheckoutSession(secretKey, request);
    }
}
