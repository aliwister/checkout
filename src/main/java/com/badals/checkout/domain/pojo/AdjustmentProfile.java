package com.badals.checkout.domain.pojo;

import com.badals.checkout.domain.enumeration.DiscountReductionType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class AdjustmentProfile {
    PriceMap discount;
    DiscountReductionType discountReductionType;

    public AdjustmentProfile(PriceMap discount, DiscountReductionType discountReductionType) {
        this.discount = discount;
        this.discountReductionType = discountReductionType;
    }
}
