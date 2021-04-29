package com.badals.checkout.service.mutation;

import lombok.Data;

@Data
abstract public class MutationResponse {
   private String code;
   private Boolean success;
   private String message;
}
