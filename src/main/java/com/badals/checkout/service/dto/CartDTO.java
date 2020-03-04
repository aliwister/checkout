package com.badals.checkout.service.dto;

import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.LineItem;
import com.badals.checkout.domain.pojo.PaymentMethod;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class CartDTO {
    private Long id;
    private String ref;
    private String name;
    private String phone;
    private String email;

    private String secureKey;

    private Address deliveryAddress;
    private Address invoiceAddress;

    List<Address> addresses;
    List<PaymentMethod> paymentMethods;

    private String carrier;
    private String currency;

    List<LineItem> items;

    private Long tenantId;

    @Override
    public String toString() {
        return "CartDTO{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", secureKey='" + secureKey + '\'' +
                ", deliveryAddress=" + deliveryAddress +
                ", invoiceAddress=" + invoiceAddress +
                ", addresses=" + addresses +
                ", paymentMethods=" + paymentMethods +
                ", carrier='" + carrier + '\'' +
                ", currency='" + currency + '\'' +
                ", items=" + items +
                ", tenantId=" + tenantId +
                '}';
    }
}