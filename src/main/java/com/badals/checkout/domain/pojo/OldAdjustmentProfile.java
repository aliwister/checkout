package com.badals.checkout.domain.pojo;

import com.badals.checkout.domain.enumeration.DiscountReductionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class OldAdjustmentProfile {
    PriceMap discount;
    DiscountReductionType discountReductionType;

    public OldAdjustmentProfile(PriceMap discount, DiscountReductionType discountReductionType) {
        this.discount = discount;
        this.discountReductionType = discountReductionType;
    }
}