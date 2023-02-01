package com.badals.checkout.service.graph;

import com.badals.checkout.addressing.*;
import com.badals.checkout.domain.pojo.OrderConfirmation;
import com.badals.checkout.service.dto.CarrierDTO;
import com.badals.checkout.service.*;
import com.badals.checkout.service.dto.CartDTO;
import com.coxautodev.graphql.tools.GraphQLQueryResolver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/*
query {
  cart(secureKey: "abc") {
    items {
      name
    }
    name
    email
    phone
  }
}
 */


@Component
public class Query implements GraphQLQueryResolver {
    private final Logger log = LoggerFactory.getLogger(Query.class);

    private final TenantCheckoutService tenantCheckoutService;

    private final CarrierService carrierService;

    private final PaymentService paymentService;

    public Query(TenantCheckoutService tenantCheckoutService, CarrierService carrierService, PaymentService paymentService) {
        this.tenantCheckoutService = tenantCheckoutService;
        this.carrierService = carrierService;
        this.paymentService = paymentService;
    }

    public String test(String param) {
        return "Im a query";
    }

    public CartDTO tenantCheckout(String secureKey) {
        return tenantCheckoutService.findBySecureKey(secureKey);
    }

    public List<CarrierDTO> carriers(String secureKey) throws InvalidCartException {
        return carrierService.getCarriers(secureKey);
    }

    public AddressFormat addressDescription(String isoCode, String lang) {
        AddressFormat addressFormat = new AddressFormat();
        addressFormat.setInputFormat("{alias}_{firstName}{lastName}_{line1}_{line2}_{city}{state}{postalCode}_{country}{mobile}_{save}");
        addressFormat.setDisplayFormat(  "({alias}){firstName} {lastName}_{line1}_{line2}_{city} {state}_{mobile}");
        addressFormat.setGmap( OptionType.REQUIRED);
        addressFormat.setDescriptions( new ArrayList<>() {{
            add(new FieldDescription(AddressField.firstName, "First Name", true, "/^[a-zA-Z]{3,10}$/", FieldType.TEXT, null, 2, 15));
            add(new FieldDescription(AddressField.lastName, "Last Name", true, "/^[a-zA-Z]{3,10}$/", FieldType.TEXT, null, 2, 15));
            add(new FieldDescription(AddressField.line1, "Address", true, "/^.{3,50}$/", FieldType.TEXT, null, 5, 30));
            add(new FieldDescription(AddressField.line2, "Landmark", false, "/^.{3,50}$/", FieldType.TEXT, null, 5, 30));
            add(new FieldDescription(AddressField.postalCode, "Postal Code", false, "/^[0-9]{3}$/", FieldType.TEXT, null, 3, 5));
            add(new FieldDescription(AddressField.state, "State", true, "", FieldType.SELECT,
                    new ArrayList<>(){{
                        add(new Option("Muscat", "OM-MA"));
                        add(new Option("Ad Dakhiliyah", "OM-DA"));
                        add(new Option("Al Batinah North", "OM-BS"));
                        add(new Option("Al Batinah South", "OM-BJ"));
                        add(new Option("Al Wusta", "OM-WU"));
                        add(new Option("Ash Sharqiyah North", "OM-SS"));
                        add(new Option("Ash Sharqiyah South", "OM-SJ"));
                        add(new Option("Ad Dhahirah", "OM-ZA"));
                        add(new Option("Al Buraymi", "OM-BU"));
                        add(new Option("Musandam", "OM-MU"));
                        add(new Option("Dhofar", "OM-ZU"));
                    }}, null, null));
            add(new FieldDescription(AddressField.country, "Country", true, "", FieldType.SELECT,
                    new ArrayList<>(){{
                        add(new Option("Oman", "OM"));
                    }}, null, null));
            add(new FieldDescription(AddressField.city, "City", true, "/(.*[a-z]){3}/i", FieldType.TEXT, null, 2, 25));
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
   public OrderConfirmation orderConfirmation(String paymentKey) throws InvalidCartException {
      return tenantCheckoutService.createOrderWithPaymentByPaymentToken(paymentKey);
   }
}
