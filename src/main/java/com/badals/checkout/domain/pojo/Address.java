package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    Long id;
    String name;
    String line1;
    String line2;
    String city;
    String state;
    String country;
    String postalCode;
}
