package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
public class LineItem implements Serializable {
    Long productId;
    String sku;
    String unit;
    String image;
    String name;
    String ref;
    String url;
    Long availability;
    BigDecimal price;
    BigDecimal cost;
    BigDecimal quantity;
    BigDecimal weight;
    BigDecimal subTotal;
}
