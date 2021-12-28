package com.badals.checkout.domain;

import com.badals.checkout.domain.pojo.Address;
import com.badals.enumeration.OrderState;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

/**
 * A Order.
 */
@Entity
@Table(catalog="profileshop", name = "jhi_order")
public class TenantOrder extends Auditable<String> implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "invoice_date")
    private LocalDate invoiceDate;

    @Column(name = "delivery_date")
    private LocalDate deliveryDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "state")
    private OrderState orderState;

    @Column(name = "currency")
    private String currency;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    //@ManyToOne
    //@JsonIgnoreProperties("orders")
    @Column(name = "customer_id")
    private Integer customerId;

    @OneToOne
    @JoinColumn(unique = true)
    private TenantCart cart;

    @Type(type = "json")
    @Column(name = "delivery_address", columnDefinition = "string")
    private Address deliveryAddress;

    @Column(name = "delivery_address_id")
    private Long deliveryAddressId;

    @Type(type = "json")
    @Column(name = "invoice_address", columnDefinition = "string")
    private Address invoiceAddress;

    @Column(name = "confirmation_key")
    private String confirmationKey;

    @Column(name = "subtotal")
    private BigDecimal subtotal;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "taxes_total")
    private BigDecimal taxesTotal;

    @Column(name = "delivery_total")
    private BigDecimal deliveryTotal;

    @Column(name = "discounts_total")
    private BigDecimal discountsTotal;

    @Column(name = "coupon_name")
    private BigDecimal couponName;


    @Column
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    @Column
    private String carrier;

    @Column
    private String paymentMethod;

    public String getCarrier() {
        return carrier;
    }

    public void setCarrier(String carrier) {
        this.carrier = carrier;
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }


    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(BigDecimal subtotal) {
        this.subtotal = subtotal;
    }

    public Long getDeliveryAddressId() {
        return deliveryAddressId;
    }

    public void setDeliveryAddressId(Long deliveryAddressId) {
        this.deliveryAddressId = deliveryAddressId;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    public BigDecimal getTaxesTotal() {
        return taxesTotal;
    }

    public void setTaxesTotal(BigDecimal taxesTotal) {
        this.taxesTotal = taxesTotal;
    }

    public BigDecimal getDeliveryTotal() {
        return deliveryTotal;
    }

    public void setDeliveryTotal(BigDecimal deliveryTotal) {
        this.deliveryTotal = deliveryTotal;
    }

    public BigDecimal getDiscountsTotal() {
        return discountsTotal;
    }

    public void setDiscountsTotal(BigDecimal discountsTotal) {
        this.discountsTotal = discountsTotal;
    }

    public BigDecimal getCouponName() {
        return couponName;
    }

    public void setCouponName(BigDecimal couponName) {
        this.couponName = couponName;
    }

    public String getConfirmationKey() {
        return confirmationKey;
    }

    public void setConfirmationKey(String confirmationKey) {
        this.confirmationKey = confirmationKey;
    }

    /*@ManyToOne
        @JsonIgnoreProperties("orders")
        private Address deliveryAddress;

        @ManyToOne
        @JsonIgnoreProperties("orders")
        private Address invoiceAddress;
        */
    @OneToMany(mappedBy = "order", cascade=CascadeType.ALL, orphanRemoval = true)
    private Set<TenantOrderItem> orderItems = new HashSet<>();

    // jhipster-needle-entity-add-field - JHipster will add fields here, do not remove
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getReference() {
        return reference;
    }

    public TenantOrder reference(String reference) {
        this.reference = reference;
        return this;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public LocalDate getInvoiceDate() {
        return invoiceDate;
    }

    public TenantOrder invoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
        return this;
    }

    public void setInvoiceDate(LocalDate invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public LocalDate getDeliveryDate() {
        return deliveryDate;
    }

    public TenantOrder deliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
        return this;
    }

    public void setDeliveryDate(LocalDate deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public OrderState getOrderState() {
        return orderState;
    }

    public void setOrderState(OrderState orderState) {
        this.orderState = orderState;
    }

    public TenantOrder orderState(OrderState orderState) {
        this.orderState = orderState;
        return this;
    }


    public String getCurrency() {
        return currency;
    }

    public TenantOrder currency(String currency) {
        this.currency = currency;
        return this;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public TenantCart getCart() {
        return cart;
    }

    public TenantOrder cart(TenantCart cart) {
        this.cart = cart;
        return this;
    }

    public void setCart(TenantCart cart) {
        this.cart = cart;
    }

    public Address getDeliveryAddress() {
        return deliveryAddress;
    }

    public TenantOrder deliveryAddress(Address address) {
        this.deliveryAddress = address;
        return this;
    }

    public void setDeliveryAddress(Address address) {
        this.deliveryAddress = address;
    }

    public Address getInvoiceAddress() {
        return invoiceAddress;
    }

    public TenantOrder invoiceAddress(Address address) {
        this.invoiceAddress = address;
        return this;
    }

    public void setInvoiceAddress(Address address) {
        this.invoiceAddress = address;
    }

    public Set<TenantOrderItem> getOrderItems() {
        return orderItems;
    }

    public TenantOrder orderItems(Set<TenantOrderItem> orderItems) {
        this.orderItems = orderItems;
        return this;
    }

    public TenantOrder addOrderItem(TenantOrderItem orderItem) {
        this.orderItems.add(orderItem);
        orderItem.setOrder(this);
        return this;
    }

    public TenantOrder removeOrderItem(TenantOrderItem orderItem) {
        this.orderItems.remove(orderItem);
        orderItem.setOrder(null);
        return this;
    }

    public void setOrderItems(Set<TenantOrderItem> orderItems) {
        this.orderItems = orderItems;
    }
    // jhipster-needle-entity-add-getters-setters - JHipster will add getters and setters here, do not remove

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof TenantOrder)) {
            return false;
        }
        return id != null && id.equals(((TenantOrder) o).id);
    }

    @Override
    public int hashCode() {
        return 31;
    }

    @Override
    public String toString() {
        return "Order{" +
            "id=" + getId() +
            ", reference='" + getReference() + "'" +
            ", invoiceDate='" + getInvoiceDate() + "'" +
            ", deliveryDate='" + getDeliveryDate() + "'" +
            ", state='" + getOrderState() + "'" +
            ", currency='" + getCurrency() + "'" +
            "}";
    }
}
