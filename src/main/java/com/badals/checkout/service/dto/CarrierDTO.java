package com.badals.checkout.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class CarrierDTO {
   public CarrierDTO() {
   }

   private Integer id;
   private String name;
   private String value;
   private BigDecimal cost;
   private String logo;
}
