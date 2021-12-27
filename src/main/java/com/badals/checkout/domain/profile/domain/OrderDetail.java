package com.badals.checkout.domain;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ps_order_detail database table.
 * 
 */
@Entity
@Table(schema="prestashop7",name="ps_order_detail")
@NamedQuery(name="OrderDetail.findAll", query="SELECT p FROM OrderDetail p")
public class OrderDetail implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@Column(name="id_order_detail")
	private int idOrderDetail;

	@Column(name="discount_quantity_applied")
	private byte discountQuantityApplied;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="download_deadline")
	private Date downloadDeadline;

	@Column(name="download_hash")
	private String downloadHash;

	@Column(name="download_nb")
	private int downloadNb;

	private BigDecimal ecotax;

	@Column(name="ecotax_tax_rate")
	private BigDecimal ecotaxTaxRate;

	@Column(name="group_reduction")
	private BigDecimal groupReduction;

	@Column(name="id_order")
	private int idOrder;

	@Column(name="id_order_invoice")
	private int idOrderInvoice;

	@Column(name="id_shop")
	private int idShop;

	@Column(name="id_tax_rules_group")
	private int idTaxRulesGroup;

	@Column(name="id_warehouse")
	private int idWarehouse;

	@Column(name="original_product_price")
	private BigDecimal originalProductPrice;

	@Column(name="original_wholesale_price")
	private BigDecimal originalWholesalePrice;

	@Column(name="product_attribute_id")
	private int productAttributeId;

	@Column(name="product_ean13")
	private String productEan13;

	@Column(name="product_id")
	private int productId;

	@Column(name="product_name")
	private String productName;

	@Column(name="product_price")
	private BigDecimal productPrice;

	@Column(name="product_quantity")
	private int productQuantity;

	@Column(name="product_quantity_discount")
	private BigDecimal productQuantityDiscount;

	@Column(name="product_quantity_in_stock")
	private int productQuantityInStock;

	@Column(name="product_quantity_refunded")
	private int productQuantityRefunded;

	@Column(name="product_quantity_reinjected")
	private int productQuantityReinjected;

	@Column(name="product_quantity_return")
	private int productQuantityReturn;

	@Column(name="product_reference")
	private String productReference;

	@Column(name="product_supplier_reference")
	private String productSupplierReference;

	@Column(name="product_upc")
	private String productUpc;

	@Column(name="product_weight")
	private BigDecimal productWeight;

	@Column(name="purchase_supplier_price")
	private BigDecimal purchaseSupplierPrice;

	@Column(name="reduction_amount")
	private BigDecimal reductionAmount;

	@Column(name="reduction_amount_tax_excl")
	private BigDecimal reductionAmountTaxExcl;

	@Column(name="reduction_amount_tax_incl")
	private BigDecimal reductionAmountTaxIncl;

	@Column(name="reduction_percent")
	private BigDecimal reductionPercent;

	@Column(name="tax_computation_method")
	private byte taxComputationMethod;

	@Column(name="tax_name")
	private String taxName;

	@Column(name="tax_rate")
	private BigDecimal taxRate;

	@Column(name="total_price_tax_excl")
	private BigDecimal totalPriceTaxExcl;

	@Column(name="total_price_tax_incl")
	private BigDecimal totalPriceTaxIncl;

	@Column(name="total_shipping_price_tax_excl")
	private BigDecimal totalShippingPriceTaxExcl;

	@Column(name="total_shipping_price_tax_incl")
	private BigDecimal totalShippingPriceTaxIncl;

	@Column(name="unit_price_tax_excl")
	private BigDecimal unitPriceTaxExcl;

	@Column(name="unit_price_tax_incl")
	private BigDecimal unitPriceTaxIncl;

	public OrderDetail() {
	}

	public int getIdOrderDetail() {
		return this.idOrderDetail;
	}

	public void setIdOrderDetail(int idOrderDetail) {
		this.idOrderDetail = idOrderDetail;
	}

	public byte getDiscountQuantityApplied() {
		return this.discountQuantityApplied;
	}

	public void setDiscountQuantityApplied(byte discountQuantityApplied) {
		this.discountQuantityApplied = discountQuantityApplied;
	}

	public Date getDownloadDeadline() {
		return this.downloadDeadline;
	}

	public void setDownloadDeadline(Date downloadDeadline) {
		this.downloadDeadline = downloadDeadline;
	}

	public String getDownloadHash() {
		return this.downloadHash;
	}

	public void setDownloadHash(String downloadHash) {
		this.downloadHash = downloadHash;
	}

	public int getDownloadNb() {
		return this.downloadNb;
	}

	public void setDownloadNb(int downloadNb) {
		this.downloadNb = downloadNb;
	}

	public BigDecimal getEcotax() {
		return this.ecotax;
	}

	public void setEcotax(BigDecimal ecotax) {
		this.ecotax = ecotax;
	}

	public BigDecimal getEcotaxTaxRate() {
		return this.ecotaxTaxRate;
	}

	public void setEcotaxTaxRate(BigDecimal ecotaxTaxRate) {
		this.ecotaxTaxRate = ecotaxTaxRate;
	}

	public BigDecimal getGroupReduction() {
		return this.groupReduction;
	}

	public void setGroupReduction(BigDecimal groupReduction) {
		this.groupReduction = groupReduction;
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

	public int getIdShop() {
		return this.idShop;
	}

	public void setIdShop(int idShop) {
		this.idShop = idShop;
	}

	public int getIdTaxRulesGroup() {
		return this.idTaxRulesGroup;
	}

	public void setIdTaxRulesGroup(int idTaxRulesGroup) {
		this.idTaxRulesGroup = idTaxRulesGroup;
	}

	public int getIdWarehouse() {
		return this.idWarehouse;
	}

	public void setIdWarehouse(int idWarehouse) {
		this.idWarehouse = idWarehouse;
	}

	public BigDecimal getOriginalProductPrice() {
		return this.originalProductPrice;
	}

	public void setOriginalProductPrice(BigDecimal originalProductPrice) {
		this.originalProductPrice = originalProductPrice;
	}

	public BigDecimal getOriginalWholesalePrice() {
		return this.originalWholesalePrice;
	}

	public void setOriginalWholesalePrice(BigDecimal originalWholesalePrice) {
		this.originalWholesalePrice = originalWholesalePrice;
	}

	public int getProductAttributeId() {
		return this.productAttributeId;
	}

	public void setProductAttributeId(int productAttributeId) {
		this.productAttributeId = productAttributeId;
	}

	public String getProductEan13() {
		return this.productEan13;
	}

	public void setProductEan13(String productEan13) {
		this.productEan13 = productEan13;
	}

	public int getProductId() {
		return this.productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return this.productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public BigDecimal getProductPrice() {
		return this.productPrice;
	}

	public void setProductPrice(BigDecimal productPrice) {
		this.productPrice = productPrice;
	}

	public int getProductQuantity() {
		return this.productQuantity;
	}

	public void setProductQuantity(int productQuantity) {
		this.productQuantity = productQuantity;
	}

	public BigDecimal getProductQuantityDiscount() {
		return this.productQuantityDiscount;
	}

	public void setProductQuantityDiscount(BigDecimal productQuantityDiscount) {
		this.productQuantityDiscount = productQuantityDiscount;
	}

	public int getProductQuantityInStock() {
		return this.productQuantityInStock;
	}

	public void setProductQuantityInStock(int productQuantityInStock) {
		this.productQuantityInStock = productQuantityInStock;
	}

	public int getProductQuantityRefunded() {
		return this.productQuantityRefunded;
	}

	public void setProductQuantityRefunded(int productQuantityRefunded) {
		this.productQuantityRefunded = productQuantityRefunded;
	}

	public int getProductQuantityReinjected() {
		return this.productQuantityReinjected;
	}

	public void setProductQuantityReinjected(int productQuantityReinjected) {
		this.productQuantityReinjected = productQuantityReinjected;
	}

	public int getProductQuantityReturn() {
		return this.productQuantityReturn;
	}

	public void setProductQuantityReturn(int productQuantityReturn) {
		this.productQuantityReturn = productQuantityReturn;
	}

	public String getProductReference() {
		return this.productReference;
	}

	public void setProductReference(String productReference) {
		this.productReference = productReference;
	}

	public String getProductSupplierReference() {
		return this.productSupplierReference;
	}

	public void setProductSupplierReference(String productSupplierReference) {
		this.productSupplierReference = productSupplierReference;
	}

	public String getProductUpc() {
		return this.productUpc;
	}

	public void setProductUpc(String productUpc) {
		this.productUpc = productUpc;
	}

	public BigDecimal getProductWeight() {
		return this.productWeight;
	}

	public void setProductWeight(BigDecimal productWeight) {
		this.productWeight = productWeight;
	}

	public BigDecimal getPurchaseSupplierPrice() {
		return this.purchaseSupplierPrice;
	}

	public void setPurchaseSupplierPrice(BigDecimal purchaseSupplierPrice) {
		this.purchaseSupplierPrice = purchaseSupplierPrice;
	}

	public BigDecimal getReductionAmount() {
		return this.reductionAmount;
	}

	public void setReductionAmount(BigDecimal reductionAmount) {
		this.reductionAmount = reductionAmount;
	}

	public BigDecimal getReductionAmountTaxExcl() {
		return this.reductionAmountTaxExcl;
	}

	public void setReductionAmountTaxExcl(BigDecimal reductionAmountTaxExcl) {
		this.reductionAmountTaxExcl = reductionAmountTaxExcl;
	}

	public BigDecimal getReductionAmountTaxIncl() {
		return this.reductionAmountTaxIncl;
	}

	public void setReductionAmountTaxIncl(BigDecimal reductionAmountTaxIncl) {
		this.reductionAmountTaxIncl = reductionAmountTaxIncl;
	}

	public BigDecimal getReductionPercent() {
		return this.reductionPercent;
	}

	public void setReductionPercent(BigDecimal reductionPercent) {
		this.reductionPercent = reductionPercent;
	}

	public byte getTaxComputationMethod() {
		return this.taxComputationMethod;
	}

	public void setTaxComputationMethod(byte taxComputationMethod) {
		this.taxComputationMethod = taxComputationMethod;
	}

	public String getTaxName() {
		return this.taxName;
	}

	public void setTaxName(String taxName) {
		this.taxName = taxName;
	}

	public BigDecimal getTaxRate() {
		return this.taxRate;
	}

	public void setTaxRate(BigDecimal taxRate) {
		this.taxRate = taxRate;
	}

	public BigDecimal getTotalPriceTaxExcl() {
		return this.totalPriceTaxExcl;
	}

	public void setTotalPriceTaxExcl(BigDecimal totalPriceTaxExcl) {
		this.totalPriceTaxExcl = totalPriceTaxExcl;
	}

	public BigDecimal getTotalPriceTaxIncl() {
		return this.totalPriceTaxIncl;
	}

	public void setTotalPriceTaxIncl(BigDecimal totalPriceTaxIncl) {
		this.totalPriceTaxIncl = totalPriceTaxIncl;
	}

	public BigDecimal getTotalShippingPriceTaxExcl() {
		return this.totalShippingPriceTaxExcl;
	}

	public void setTotalShippingPriceTaxExcl(BigDecimal totalShippingPriceTaxExcl) {
		this.totalShippingPriceTaxExcl = totalShippingPriceTaxExcl;
	}

	public BigDecimal getTotalShippingPriceTaxIncl() {
		return this.totalShippingPriceTaxIncl;
	}

	public void setTotalShippingPriceTaxIncl(BigDecimal totalShippingPriceTaxIncl) {
		this.totalShippingPriceTaxIncl = totalShippingPriceTaxIncl;
	}

	public BigDecimal getUnitPriceTaxExcl() {
		return this.unitPriceTaxExcl;
	}

	public void setUnitPriceTaxExcl(BigDecimal unitPriceTaxExcl) {
		this.unitPriceTaxExcl = unitPriceTaxExcl;
	}

	public BigDecimal getUnitPriceTaxIncl() {
		return this.unitPriceTaxIncl;
	}

	public void setUnitPriceTaxIncl(BigDecimal unitPriceTaxIncl) {
		this.unitPriceTaxIncl = unitPriceTaxIncl;
	}

}