package com.badals.checkout.service.integration.payment.thawani.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomer {
    private String id;
    private String customer_client_id;
}
