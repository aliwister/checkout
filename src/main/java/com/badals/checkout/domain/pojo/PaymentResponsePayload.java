package com.badals.checkout.domain.pojo;

import lombok.Data;

@Data
public class PaymentResponsePayload {
    String message;
    String redirect;
    PaymentStatus status;

    public PaymentResponsePayload(String message, String redirect, PaymentStatus status) {
        this.message = message;
        this.redirect = redirect;
        this.status = status;
    }
}
