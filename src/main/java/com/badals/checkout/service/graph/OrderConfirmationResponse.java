package com.badals.checkout.service.graph;

import lombok.Data;

@Data
public class OrderConfirmationResponse extends MutationResponse {
   //CartDTO cart;
   String orderRef;
   String confirmationKey;
}
