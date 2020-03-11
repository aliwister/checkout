package com.badals.checkout.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ps_order_carrier database table.
 * 
 */
@Entity
@Table(schema="prestashop7",name="ps_order_carrier")
@NamedQuery(name="OrderCarrier.findAll", query="SELECT p FROM OrderCarrier p")
public class OrderCarrier implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id_order_carrier")
	private int idOrderCarrier;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_add")
	private Date dateAdd;

	@Column(name="id_carrier")
	private int idCarrier;

	@Column(name="id_order")
	private int idOrder;

	@Column(name="id_order_invoice")
	private int idOrderInvoice;

	@Column(name="shipping_cost_tax_excl")
	private BigDecimal shippingCostTaxExcl;

	@Column(name="shipping_cost_tax_incl")
	private BigDecimal shippingCostTaxIncl;

	@Column(name="tracking_number")
	private String trackingNumber;

	private BigDecimal weight;

	public OrderCarrier() {
	}

	public int getIdOrderCarrier() {
		return this.idOrderCarrier;
	}

	public void setIdOrderCarrier(int idOrderCarrier) {
		this.idOrderCarrier = idOrderCarrier;
	}

	public Date getDateAdd() {
		return this.dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public int getIdCarrier() {
		return this.idCarrier;
	}

	public void setIdCarrier(int idCarrier) {
		this.idCarrier = idCarrier;
	}

	public int getIdOrder() {
		return this.idOrder;
	}

	public void setIdOrder(int idOrder) {
		this.idOrder = idOrder;
	}

	public int getIdOrderInvoice() {
		return this.idOrderInvoice;
	}

	public void setIdOrderInvoice(int idOrderInvoice) {
		this.idOrderInvoice = idOrderInvoice;
	}

	public BigDecimal getShippingCostTaxExcl() {
		return this.shippingCostTaxExcl;
	}

	public void setShippingCostTaxExcl(BigDecimal shippingCostTaxExcl) {
		this.shippingCostTaxExcl = shippingCostTaxExcl;
	}

	public BigDecimal getShippingCostTaxIncl() {
		return this.shippingCostTaxIncl;
	}

	public void setShippingCostTaxIncl(BigDecimal shippingCostTaxIncl) {
		this.shippingCostTaxIncl = shippingCostTaxIncl;
	}

	public String getTrackingNumber() {
		return this.trackingNumber;
	}

	public void setTrackingNumber(String trackingNumber) {
		this.trackingNumber = trackingNumber;
	}

	public BigDecimal getWeight() {
		return this.weight;
	}

	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}

}