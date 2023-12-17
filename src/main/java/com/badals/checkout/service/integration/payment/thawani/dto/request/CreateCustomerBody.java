package com.badals.checkout.service.integration.payment.thawani.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCustomerBody {
    private String client_customer_id;
}
