package com.badals.checkout.service.dto;

import lombok.Data;

@Data
public class PackageLineItemInput {
    private WeightInput weight;
    private DimensionsInput dimensions;
}
