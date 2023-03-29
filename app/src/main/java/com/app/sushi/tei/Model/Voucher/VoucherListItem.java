package com.app.sushi.tei.Model.Voucher;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class VoucherListItem implements Serializable {

	@SerializedName("order_outlet_id")
	private String orderOutletId;

	@SerializedName("voucher_reedmed")
	private String voucherReedmed;

	@SerializedName("order_availability_id")
	private String orderAvailabilityId;

	@SerializedName("product_voucher")
	private String productVoucher;

	@SerializedName("item_sku")
	private String itemSku;

	@SerializedName("expiry_date")
	private String expiryDate;

	@SerializedName("item_unit_price")
	private String itemUnitPrice;

	@SerializedName("product_status")
	private String productStatus;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("product_thumbnail")
	private String productThumbnail;

	@SerializedName("order_item_id")
	private String orderItemId;

	@SerializedName("order_item_product_id")
	private String orderItemProductId;

	@SerializedName("product_type")
	private String productType;

	@SerializedName("product_short_description")
	private String productShortDescription;

	@SerializedName("item_product_id")
	private String itemProductId;

	@SerializedName("product_long_description")
	private String productLongDescription;

	@SerializedName("product_voucher_points")
	private String productVoucherPoints;

	@SerializedName("item_qty")
	private String itemQty;

	@SerializedName("order_item_voucher_balance_qty")
	private String orderItemVoucherBalanceQty;

	public void setOrderOutletId(String orderOutletId){
		this.orderOutletId = orderOutletId;
	}

	public String getOrderOutletId(){
		return orderOutletId;
	}

	public void setVoucherReedmed(String voucherReedmed){
		this.voucherReedmed = voucherReedmed;
	}

	public String getVoucherReedmed(){
		return voucherReedmed;
	}

	public void setOrderAvailabilityId(String orderAvailabilityId){
		this.orderAvailabilityId = orderAvailabilityId;
	}

	public String getOrderAvailabilityId(){
		return orderAvailabilityId;
	}

	public void setProductVoucher(String productVoucher){
		this.productVoucher = productVoucher;
	}

	public String getProductVoucher(){
		return productVoucher;
	}

	public void setItemSku(String itemSku){
		this.itemSku = itemSku;
	}

	public String getItemSku(){
		return itemSku;
	}

	public void setExpiryDate(String expiryDate){
		this.expiryDate = expiryDate;
	}

	public String getExpiryDate(){
		return expiryDate;
	}

	public void setItemUnitPrice(String itemUnitPrice){
		this.itemUnitPrice = itemUnitPrice;
	}

	public String getItemUnitPrice(){
		return itemUnitPrice;
	}

	public void setProductStatus(String productStatus){
		this.productStatus = productStatus;
	}

	public String getProductStatus(){
		return productStatus;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setProductThumbnail(String productThumbnail){
		this.productThumbnail = productThumbnail;
	}

	public String getProductThumbnail(){
		return productThumbnail;
	}

	public void setOrderItemId(String orderItemId){
		this.orderItemId = orderItemId;
	}

	public String getOrderItemId(){
		return orderItemId;
	}

	public void setOrderItemProductId(String orderItemProductId){
		this.orderItemProductId = orderItemProductId;
	}

	public String getOrderItemProductId(){
		return orderItemProductId;
	}

	public void setProductType(String productType){
		this.productType = productType;
	}

	public String getProductType(){
		return productType;
	}

	public void setProductShortDescription(String productShortDescription){
		this.productShortDescription = productShortDescription;
	}

	public String getProductShortDescription(){
		return productShortDescription;
	}

	public void setItemProductId(String itemProductId){
		this.itemProductId = itemProductId;
	}

	public String getItemProductId(){
		return itemProductId;
	}

	public void setProductLongDescription(String productLongDescription){
		this.productLongDescription = productLongDescription;
	}

	public String getProductLongDescription(){
		return productLongDescription;
	}

	public void setProductVoucherPoints(String productVoucherPoints){
		this.productVoucherPoints = productVoucherPoints;
	}

	public String getProductVoucherPoints(){
		return productVoucherPoints;
	}

	public void setItemQty(String itemQty){
		this.itemQty = itemQty;
	}

	public String getItemQty(){
		return itemQty;
	}

	public String getOrderItemVoucherBalanceQty() {
		return orderItemVoucherBalanceQty;
	}

	public void setOrderItemVoucherBalanceQty(String orderItemVoucherBalanceQty) {
		this.orderItemVoucherBalanceQty = orderItemVoucherBalanceQty;
	}
}