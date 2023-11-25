package com.badals.checkout.service.dto;

import com.badals.checkout.domain.enumeration.DiscountReductionType;
import com.badals.checkout.domain.enumeration.DiscountSource;
import lombok.Data;

@Data
public class AdjustmentProfileDto {
    String discount;
    DiscountReductionType discountReductionType;
    DiscountSource discountSource;
    String sourceRef;
}
