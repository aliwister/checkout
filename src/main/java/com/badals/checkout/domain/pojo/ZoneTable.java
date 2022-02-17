package com.badals.checkout.domain.pojo;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class ZoneTable implements Serializable {
   Map<String, String> rate = new HashMap<>();
   Long zoneId;

   public Map<String, String> getRate() {
      return rate;
   }

   public void setRate(Map<String, String> rate) {
      this.rate = rate;
   }

   public Long getZoneId() {
      return zoneId;
   }

   public void setZoneId(Long zoneId) {
      this.zoneId = zoneId;
   }
}

