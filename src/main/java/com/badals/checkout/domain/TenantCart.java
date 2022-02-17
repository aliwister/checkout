package com.badals.checkout.domain;

import com.badals.checkout.aop.tenant.TenantSupport;
import com.badals.enumeration.CartState;
import lombok.Data;
import org.hibernate.annotations.Filter;
import org.hibernate.annotations.FilterDef;
import org.hibernate.annotations.ParamDef;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;


/**
 * A Cart.
 */
@Entity
@Data
@Table(name = "cart", catalog = "profileshop")
@FilterDef(name = "tenantFilter", parameters = {@ParamDef(name = "tenantId", type = "string")})
@Filter(name = "tenantFilter", condition = "tenant_id = :tenantId")
public class TenantCart implements Serializable, TenantSupport {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(name = "secure_key", nullable = false, unique = true)
    private String secureKey;

    //@NotNull
    @Enumerated(EnumType.STRING)
    @Column(name = "cart_state", nullable = false)
    private CartState cartState = CartState.UNCLAIMED;


    @Column(name="tenant_id")
    private String tenantId;

    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantCart)) {
            return false;
        }
        return id != null && id.equals(((TenantCart) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Cart{" +
            "id=" + getId() +
            ", secureKey='" + getSecureKey() + "'" +
            ", cartState='" + getCartState() + "'" +
            "}";
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }
}
