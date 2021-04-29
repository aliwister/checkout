package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;

public enum PaymentMethod implements Serializable {
    BANK("bankwire", "Bank Transfer", "", false),
    CHECKOUT("checkoutcom", "Checkout.com", "", true);
    public final String ref;
    public final String name;
    public final String image;
    public final boolean prePay;

    PaymentMethod(String ref, String name, String image, boolean prePay) {
        this.ref = ref;
        this.name = name;
        this.image = image;
        this.prePay = prePay;
    }
}
