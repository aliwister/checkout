package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;

public enum PaymentMethod implements Serializable {
    BANK("bankwire", "Bank Muscat Transfer", "bank-transfer.svg", false),
    CHECKOUT("checkoutcom", "Credit/Debit Card via Checkout.com", "credit-card.svg", true);
    public final String ref;
    public final String label;
    public final String image;
    public final boolean prePay;

    PaymentMethod(String ref, String label, String image, boolean prePay) {
        this.ref = ref;
        this.label = label;
        this.image = image;
        this.prePay = prePay;
    }
}
