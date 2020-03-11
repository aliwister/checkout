package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentMethod implements Serializable {
    String ref;
    String name;
    String image;
    boolean prePay;

    public PaymentMethod(String ref, String name, String image, boolean prePay) {
        this.ref = ref;
        this.name = name;
        this.image = image;
        this.prePay = prePay;
    }
}
