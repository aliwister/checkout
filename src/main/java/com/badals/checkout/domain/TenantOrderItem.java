package com.badals.checkout.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * A OrderItem.
 */
@Entity
@Table(catalog="profileshop", name = "order_item")
public class TenantOrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name")
    private String productName;

    @Column(name = "quantity")
    private BigDecimal quantity;

    @Column(name = "price", precision = 21, scale = 2)
    private BigDecimal price;

    public void setCost(BigDecimal cost) {
        this.cost = cost;
    }

    @Column(name = "cost", precision = 21, scale = 2)
    private BigDecimal cost;

    @Column(name = "comment")
    private String comment;

    @Column(name = "sequence")
    private Integer sequence;

    @Column(name = "shipping_instructions")
    private String shippingInstructions;

    @Column
    private String image;

    @Column
    private BigDecimal weight;

    @Column
    private String unit;

    @Column String sku;

    @Column(name="product_id") String ref;

    @Column(name="line_total")
    private BigDecimal lineTotal;

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getRef() {
        return ref;
    }

    public void setRef(String ref) {
        this.ref = ref;
    }

    public BigDecimal getLineTotal() {
        return lineTotal;
    }

    public void setLineTotal(BigDecimal lineTotal) {
        this.lineTotal = lineTotal;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public void setWeight(BigDecimal weight) {
        this.weight = weight;
    }

    @ManyToOne(optional = false)
    @NotNull
    @JsonIgnoreProperties("orderItems")
    private TenantOrder order;

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public TenantOrderItem productName(String productName) {
        this.productName = productName;
        return this;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public BigDecimal getQuantity() {
        return quantity;
    }

    public TenantOrderItem quantity(BigDecimal quantity) {
        this.quantity = quantity;
        return this;
    }

    public void setQuantity(BigDecimal quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public TenantOrderItem price(BigDecimal price) {
        this.price = price;
        return this;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getComment() {
        return comment;
    }

    public TenantOrderItem comment(String comment) {
        this.comment = comment;
        return this;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getSequence() {
        return sequence;
    }

    public TenantOrderItem sequence(Integer sequence) {
        this.sequence = sequence;
        return this;
    }

    public void setSequence(Integer sequence) {
        this.sequence = sequence;
    }

    public String getShippingInstructions() {
        return shippingInstructions;
    }

    public TenantOrderItem shippingInstructions(String shippingInstructions) {
        this.shippingInstructions = shippingInstructions;
        return this;
    }

    public void setShippingInstructions(String shippingInstructions) {
        this.shippingInstructions = shippingInstructions;
    }

    public TenantOrder getOrder() {
        return order;
    }

    public TenantOrderItem order(TenantOrder order) {
        this.order = order;
        return this;
    }

    public void setOrder(TenantOrder order) {
        this.order = order;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantOrderItem)) {
            return false;
        }
        return id != null && id.equals(((TenantOrderItem) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
            "id=" + getId() +
            ", productName='" + getProductName() + "'" +
            ", quantity=" + getQuantity() +
            ", price=" + getPrice() +
            ", comment='" + getComment() + "'" +
            ", sequence=" + getSequence() +
            ", shippingInstructions='" + getShippingInstructions() + "'" +
            "}";
    }

    public BigDecimal getCost() {
        return cost;
    }
}
