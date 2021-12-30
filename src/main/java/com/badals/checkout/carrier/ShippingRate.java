package com.badals.checkout.carrier;

import java.util.Date;

public class ShippingRate {
   public String name;
   public String label;
   public String code;
   public String price;
   public String markup;
   public String source;
   public String icon;
   public Date delivery_date;
   //public List<Object> delivery_range;
   //public List<Object> delivery_days;
   public Object compare_price;
   public boolean phone_required;
   public String currency;
   public String carrier_identifier;
   public Object delivery_category;
   public Object using_merchant_account;
   public Object carrier_service_id;
   public String description;
   /*  public int api_client_id;
   public Object requested_fulfillment_service_id;
    public Object shipment_options;
    public Object charge_items;
    public Object has_restrictions;
    public Object rating_classification;*/
   public boolean accepts_instructions;
}
