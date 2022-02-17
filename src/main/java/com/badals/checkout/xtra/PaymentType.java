package com.badals.checkout.xtra;

import java.io.Serializable;

public enum PaymentType implements Serializable {
    BANK("bankwire", "Bank Muscat Transfer", "bank-transfer.svg", false),
    STRIPE("stripe", "Stripe", "bank-transfer.svg", true),

    CHECKOUT("checkoutcom", "Credit/Debit Card", "credit-card.svg", true);
    public final String ref;
    public final String label;
    public final String image;
    public final boolean prePay;

    PaymentType(String ref, String label, String image, boolean prePay) {
        this.ref = ref;
        this.label = label;
        this.image = image;
        this.prePay = prePay;
    }
}
