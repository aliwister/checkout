package com.badals.checkout.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ps_order_payment database table.
 * 
 */
@Entity
@Table(schema="prestashop7",name="ps_order_payment")
@NamedQuery(name="OrderPayment.findAll", query="SELECT p FROM OrderPayment p")
public class OrderPayment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy= GenerationType.AUTO)
	@Column(name="id_order_payment")
	private int idOrderPayment;

	private BigDecimal amount;

	@Column(name="card_brand")
	private String cardBrand;

	@Column(name="card_expiration")
	private String cardExpiration;

	@Column(name="card_holder")
	private String cardHolder;

	@Column(name="card_number")
	private String cardNumber;

	@Column(name="conversion_rate")
	private BigDecimal conversionRate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_add")
	private Date dateAdd;

	@Column(name="id_currency")
	private int idCurrency;

	@Column(name="order_reference")
	private String orderReference;

	@Column(name="payment_method")
	private String paymentMethod;

	@Column(name="transaction_id")
	private String transactionId;

	public OrderPayment() {
	}

	public int getIdOrderPayment() {
		return this.idOrderPayment;
	}

	public void setIdOrderPayment(int idOrderPayment) {
		this.idOrderPayment = idOrderPayment;
	}

	public BigDecimal getAmount() {
		return this.amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public String getCardBrand() {
		return this.cardBrand;
	}

	public void setCardBrand(String cardBrand) {
		this.cardBrand = cardBrand;
	}

	public String getCardExpiration() {
		return this.cardExpiration;
	}

	public void setCardExpiration(String cardExpiration) {
		this.cardExpiration = cardExpiration;
	}

	public String getCardHolder() {
		return this.cardHolder;
	}

	public void setCardHolder(String cardHolder) {
		this.cardHolder = cardHolder;
	}

	public String getCardNumber() {
		return this.cardNumber;
	}

	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}

	public BigDecimal getConversionRate() {
		return this.conversionRate;
	}

	public void setConversionRate(BigDecimal conversionRate) {
		this.conversionRate = conversionRate;
	}

	public Date getDateAdd() {
		return this.dateAdd;
	}

	public void setDateAdd(Date dateAdd) {
		this.dateAdd = dateAdd;
	}

	public int getIdCurrency() {
		return this.idCurrency;
	}

	public void setIdCurrency(int idCurrency) {
		this.idCurrency = idCurrency;
	}

	public String getOrderReference() {
		return this.orderReference;
	}

	public void setOrderReference(String orderReference) {
		this.orderReference = orderReference;
	}

	public String getPaymentMethod() {
		return this.paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getTransactionId() {
		return this.transactionId;
	}

	public void setTransactionId(String transactionId) {
		this.transactionId = transactionId;
	}

}