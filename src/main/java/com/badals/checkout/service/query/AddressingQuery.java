package com.badals.checkout.service.query;

import com.badals.checkout.addressing.*;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;

@Component
public class AddressingQuery implements GraphQLQueryResolver {
   private final Logger log = LoggerFactory.getLogger(com.badals.checkout.service.query.Query.class);

   public AddressFormat addressDescription(String isoCode, String lang) {
      AddressFormat addressFormat = new AddressFormat();
      addressFormat.setInputFormat("{alias}_{firstName}{lastName}_{line1}_{line2}_{city}{state}{postalCode}_{country}{mobile}_{save}");
      addressFormat.setDisplayFormat(  "({alias}){firstName}{lastName}_{line1}_{line2}_{city}{state}_{mobile}");
      addressFormat.setGmap( OptionType.REQUIRED);
      addressFormat.setDescriptions( new ArrayList<>() {{
         add(new FieldDescription(AddressField.firstName, "First Name", true, "/^[a-zA-Z]{3,10}$/", FieldType.TEXT, null, 2, 15));
         add(new FieldDescription(AddressField.lastName, "Last Name", true, "/^[a-zA-Z]{3,10}$/", FieldType.TEXT, null, 2, 15));
         add(new FieldDescription(AddressField.line1, "Address", true, "/^.{3,50}$/", FieldType.TEXT, null, 5, 30));
         add(new FieldDescription(AddressField.line2, "Landmark", true, "/^.{3,50}$/", FieldType.TEXT, null, 5, 30));
         add(new FieldDescription(AddressField.postalCode, "Postal Code", false, "/^[0-9]{3}$/", FieldType.TEXT, null, 3, 5));
         add(new FieldDescription(AddressField.state, "State", true, "", FieldType.SELECT,
                 new ArrayList<>(){{
                    add(new Option("Muscat", "MA"));
                    add(new Option("Ad Dakhiliyah", "DA"));
                    add(new Option("Al Batinah North", "BS"));
                    add(new Option("Al Batinah South", "BJ"));
                    add(new Option("Al Wusta", "WU"));
                    add(new Option("Ash Sharqiyah North", "SS"));
                    add(new Option("Ash Sharqiyah South", "SJ"));
                    add(new Option("Ad Dhahirah", "ZA"));
                    add(new Option("Al Buraymi", "BU"));
                    add(new Option("Musandam", "MU"));
                    add(new Option("Dhofar", "ZU"));
                 }}, null, null));
         add(new FieldDescription(AddressField.country, "Country", true, "", FieldType.SELECT,
                 new ArrayList<>(){{
                    add(new Option("Oman", "OM"));
                 }}, null, null));
         add(new FieldDescription(AddressField.city, "City", true, "/(.*[a-z]){3}/i", FieldType.TEXT, null, 2, 15));
         add(new FieldDescription(AddressField.mobile, "Mobile", true, "/(.*[a-z]){3}/i", FieldType.MOBILE, null, 11, 11));
         add(new FieldDescription(AddressField.alias, "Alias", true, "/(.*[a-z]){3}/i", FieldType.SELECT, new ArrayList<>(){{
            add(new Option("Home", "home"));
            add(new Option("Work", "work"));
            add(new Option("Other", "other"));
         }}, null, null));
         add(new FieldDescription(AddressField.save, "Save this information for next time", false, null, FieldType.CHECKBOX, null, null, null));
      }});

      return addressFormat;
   }
}