package com.badals.checkout.service.integration.payment.thawani.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreatePaymentIntentBody {
    private String payment_method_id;
    private int amount;
    private String client_reference_id;
    private String return_url;
    private Map<String, String> metadata;
}