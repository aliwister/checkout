package com.badals.checkout.service.integration.payment.thawani.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@Builder
@NoArgsConstructor
@Data
public class CreateCheckoutSessionBody {
    private String client_reference_id;
    private String mode;
    private List<Product> products;
    private String success_url;
    private String cancel_url;
    private CreateCheckoutMetaData metadata;

}
