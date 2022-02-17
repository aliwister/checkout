package com.badals.checkout.service.dto;

import com.badals.checkout.domain.pojo.Address;
import com.badals.checkout.domain.pojo.LineItem;
import com.badals.checkout.xtra.PaymentType;
import lombok.Data;

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

    private Long deliveryAddressId;

    List<Address> addresses;
    List<PaymentType> paymentTypes;

    private String carrier;
    private Double carrierRate;
    private String carrierService;
    private String currency;

    List<LineItem> items;

    private String tenantId;

    private Boolean allowPickup;
    private Boolean guest;

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
                ", paymentMethods=" + paymentTypes +
                ", carrier='" + carrier + '\'' +
                ", currency='" + currency + '\'' +
                ", items=" + items +
                ", tenantId=" + tenantId +
                '}';
    }


}