package com.badals.checkout.domain.pojo;

import com.badals.checkout.domain.enumeration.DiscountReductionType;
import com.badals.checkout.domain.enumeration.DiscountSource;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class AdjustmentProfile implements Serializable {
    PriceMap discount;
    DiscountReductionType discountReductionType;
    DiscountSource discountSource;
    String sourceRef;

    public AdjustmentProfile(PriceMap discount, DiscountReductionType discountReductionType) {
        this.discount = discount;
        this.discountReductionType = discountReductionType;
    }

    public AdjustmentProfile(PriceMap discount, DiscountReductionType discountReductionType, DiscountSource discountSource, String sourceRef) {
        this.discount = discount;
        this.discountReductionType = discountReductionType;
        this.discountSource = discountSource;
        this.sourceRef = sourceRef;
    }
}
