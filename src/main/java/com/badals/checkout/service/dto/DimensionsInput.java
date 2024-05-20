package com.badals.checkout.service.dto;

import lombok.Data;

@Data
public class DimensionsInput {
    private Float length;
    private Float width;
    private Float height;
    private String units;
}
