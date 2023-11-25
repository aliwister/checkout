package com.badals.checkout.service.graph;

import lombok.Data;

@Data
abstract public class MutationResponse {
   private String code;
   private Boolean success;
   private String message;
}
