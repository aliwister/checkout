package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Carrier {
   public Carrier(String name, String value, BigDecimal cost, String image) {
      this.name = name;
      this.value = value;
      this.cost = cost;
      this.image = image;
   }

   String name;
      String value;
      BigDecimal cost;
      String image;
}
