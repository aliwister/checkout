package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;

@Data
public class PaymentDef implements Serializable {
   String name;
   String pk;
   String sk;
}
