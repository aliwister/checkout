package com.badals.checkout.controller;

import lombok.Data;

@Data
public class ShipmentStripePaymentDto {

    private String secureKey;
    private Double amount;

}
