package com.badals.checkout.domain.pojo;

import lombok.Data;

@Data
public class PaymentResponsePayload {
    String message;
    String payload;
    PaymentStatus status;
    String ref;

    public PaymentResponsePayload(String message, String payload, PaymentStatus status) {
        this.message = message;
        this.payload = payload;
        this.status = status;
    }

    public PaymentResponsePayload(String message) {
        this.message = message;
    }
}
