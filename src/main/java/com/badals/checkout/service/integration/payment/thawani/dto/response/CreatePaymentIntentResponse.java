package com.badals.checkout.service.integration.payment.thawani.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePaymentIntentResponse {
    private String id;
    private String client_reference_id;
    private double amount;
    private String currency;
    private String payment_method;
    private NextAction next_action;
    private String status;
    private Map<String, String> metadata;
    private String created_at;
    private String expire_at;
}

@AllArgsConstructor
@NoArgsConstructor
@Data
class NextAction {
    private String url;
    private String return_url;
}
