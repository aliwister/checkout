package com.badals.checkout.service.integration.payment.thawani.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThawaniResponse<D> {
    private boolean success;
    private int code;
    private String description;
    private D data;
}
