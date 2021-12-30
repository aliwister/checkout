package com.badals.checkout.addressing;

import lombok.Data;

import java.util.List;

@Data
public class AddressFormat {

   String inputFormat;
   String displayFormat;
   List<FieldDescription> descriptions;
   OptionType gmap;
}
