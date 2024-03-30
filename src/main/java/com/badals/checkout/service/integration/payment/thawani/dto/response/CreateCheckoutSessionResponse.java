package com.badals.checkout.service.integration.payment.thawani.dto.response;

import com.badals.checkout.service.integration.payment.thawani.dto.request.CreateCheckoutMetaData;
import com.badals.checkout.service.integration.payment.thawani.dto.request.Product;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateCheckoutSessionResponse {
    private String session_id;
    private String client_reference_id;
    private String customer_id;
    private List<Product> products;
    private double total_amount;
    private String currency;
    private String success_url;
    private String cancel_url;
    private String payment_status;
    private String mode;
    private String invoice;
    private CreateCheckoutMetaData metadata;
    private String created_at;
    private String expire_at;
}
