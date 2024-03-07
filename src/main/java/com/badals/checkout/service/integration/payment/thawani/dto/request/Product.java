package com.badals.checkout.service.integration.payment.thawani.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Product {
    private String name;
    private int quantity;
    private int unit_amount;
}
