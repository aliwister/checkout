package com.badals.checkout.service.mutation;

import com.badals.checkout.service.dto.CartDTO;
import lombok.Data;

@Data
public class OrderConfirmationResponse extends MutationResponse {
   CartDTO cart;
   String orderRef;
}
