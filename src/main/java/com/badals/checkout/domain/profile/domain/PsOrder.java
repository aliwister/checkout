package com.badals.checkout.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ps_orders database table.
 * 
 */
@Entity
@Table(schema="prestashop7",name="ps_orders")
public class PsOrder implements Serializable {
	private static final long serialVersionUID = 1L;

	/*@Id @Getter @Setter
	@GeneratedValue(strategy=GenerationType.AUTO)
	private int id;
	*/
	
	
	@Id
	@Column(name="id_order")
	private long id;

	@Column(name="carrier_tax_rate")
	private BigDecimal carrierTaxRate;

	@Column(name="conversion_rate")
	private BigDecimal conversionRate;

	@Column(name="current_state")
	private int currentState;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_add")
	private Date dateAdd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="date_upd")
	private Date dateUpd;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="delivery_date")
	private Date deliveryDate;

	@Column(name="delivery_number")
	private int deliveryNumber;

	private byte gift;

	@Lob
	@Column(name="gift_message")
	private String giftMessage;

	@Column(name="id_address_delivery")
	private int idAddressDelivery;

	@Column(name="id_address_invoice")
	private int idAddressInvoice;

	@Column(name="id_carrier")
	private int idCarrier;

	@Column(name="id_cart")
	private int idCart;

	@Column(name="id_currency")
	private int idCurrency;

	@Column(name="id_lang")
	private int idLang;

	@Column(name="id_shop")
	private int idShop;

	@Column(name="id_shop_group")
	private int idShopGroup;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="invoice_date")
	private Date invoiceDate;

	@Column(name="invoice_number")
	private int invoiceNumber;

	@Column(name="mobile_theme")
	private byte mobileTheme;

	private String module;

	private String payment;

	private byte recyclable;

	private String reference;

	@Column(name="round_mode")
	private byte roundMode;

	@Column(name="round_type")
	private byte roundType;

	@Column(name="secure_key")
	private String secureKey;

	@Column(name="shipping_number")
	private String shippingNumber;

	@Column(name="total_discounts")
	private BigDecimal totalDiscounts;

	@Column(name="total_discounts_tax_excl")
	private BigDecimal totalDiscountsTaxExcl;

	@Column(name="total_discounts_tax_incl")
	private BigDecimal totalDiscountsTaxIncl;

	@Column(name="total_paid")
	private BigDecimal totalPaid;

	@Column(name="total_paid_real")
	private BigDecimal totalPaidReal;

	@Column(name="total_paid_tax_excl")
	private BigDecimal totalPaidTaxExcl;

	@Column(name="total_paid_tax_incl")
	private BigDecimal totalPaidTaxIncl;

	@Column(name="total_products")
	private BigDecimal totalProducts;

	@Column(name="total_products_wt")
	private BigDecimal totalProductsWt;

	@Column(name="total_shipping")
	private BigDecimal totalShipping;

	@Column(name="total_shipping_tax_excl")
	private BigDecimal totalShippingTaxExcl;

	@Column(name="total_shipping_tax_incl")
	private BigDecimal totalShippingTaxIncl;

	@Column(name="total_wrapping")
	private BigDecimal totalWrapping;

	@Column(name="total_wrapping_tax_excl")
	private BigDecimal totalWrappingTaxExcl;

	@Column(name="total_wrapping_tax_incl")
	private BigDecimal totalWrappingTaxIncl;

	private int valid;

	/*@Getter
   @Setter
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    @JoinColumn(name = "id_customer", nullable = false, insertable = false, updatable = false)
    private PsCustomer customer;
	*/
	private Long idCustomer;


}