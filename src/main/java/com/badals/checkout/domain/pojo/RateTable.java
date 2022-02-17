package com.badals.checkout.domain.pojo;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
public class RateTable implements Serializable {
   Map<String, ZoneTable> zoneTable = new HashMap<>();
}

