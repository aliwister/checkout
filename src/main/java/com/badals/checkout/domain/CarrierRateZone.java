package com.badals.checkout.domain;


import com.badals.checkout.aop.tenant.TenantSupport;
import com.badals.checkout.domain.pojo.I18String;
import com.badals.checkout.domain.pojo.RateTable;
import com.stripe.model.Price;
import com.vladmihalcea.hibernate.type.json.JsonBinaryType;
import com.vladmihalcea.hibernate.type.json.JsonStringType;
import lombok.Data;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * A Product.
 */
@TypeDefs({
        @TypeDef(name = "json", typeClass = JsonStringType.class),
        @TypeDef(name = "jsonb", typeClass = JsonBinaryType.class)
})
@Entity
@Data
@Table(catalog = "profileshop", name = "carrier_rate_zone")
//@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
//@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class CarrierRateZone implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Type(type = "json")
    @Column(name="price", columnDefinition = "string")
    private Price price;

    @NaturalId
    private String ref;

    @Column(name = "name")
    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="carrier_ref", referencedColumnName = "ref", insertable = false, updatable = false)
    private Carrier carrier;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="zone_code", referencedColumnName = "code", insertable = false, updatable = false)
    private Zone zone;

    private String conditionMin;
    private String conditionMax;



    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof CarrierRateZone)) {
            return false;
        }
        return id != null && id.equals(((CarrierRateZone) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "id=" + id +
                ", ref='" + ref + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}
