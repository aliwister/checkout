package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class Address implements Serializable {
    Long id;
    String firstName;
    String lastName;
    String line1;
    String line2;
    String city;
    String state;
    String country;
    String postalCode;
    String mobile;
    Boolean save;
    String alias;
    Boolean isMap;
    String plusCode;
    String lat;
    String lng;;

    public Address(Long id, String firstName, String lastName, String line1, String line2, String city, String state, String country, String postalCode, String mobile, Boolean save, String alias) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.line1 = line1;
        this.line2 = line2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.mobile = mobile;
        this.save = save;
        this.alias = alias;

    }
}
