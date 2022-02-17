package com.badals.checkout.domain;


import com.badals.checkout.aop.tenant.TenantSupport;

import com.badals.checkout.domain.pojo.I18String;
import com.badals.checkout.domain.pojo.RateTable;
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
@Table(catalog = "profileshop", name = "carrier")
//@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
//@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class Carrier implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Transient
    private String cost;

    @NaturalId
    private String ref;

    @Type(type = "json")
    @Column(name = "name", columnDefinition = "string")
    private List<I18String> name;

    private String logo;
    private Double maxWeight;



    private String api;

    @Type(type = "json")
    @Column(name="rate_table", columnDefinition = "string")
    private RateTable rateTable;

    @Column(name="tenant_id")
    private String tenantId;

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Carrier)) {
            return false;
        }
        return id != null && id.equals(((Carrier) o).id);
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
