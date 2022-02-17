package com.badals.checkout.domain.pojo.projection;

import com.stripe.model.Price;

import java.math.BigDecimal;

public interface ShipRate {
   Long getId();
   String getCarrierRef();
   String getCarrierZoneCode();
   String getRateName();
   String getCarrierName();
   String getCarrierLogo();
   BigDecimal getPrice();
   Double getConditionMin();
   Double getConditionMax();
   Double getHandlingFee();
   Boolean isFree();
}
