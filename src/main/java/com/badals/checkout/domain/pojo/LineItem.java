package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LineItem implements Serializable {
    Integer productId;
    String sku;
    String unit;
    String image;
    String name;
    BigDecimal price;
    BigDecimal quantity;
    BigDecimal weight;
    BigDecimal subTotal;
}
