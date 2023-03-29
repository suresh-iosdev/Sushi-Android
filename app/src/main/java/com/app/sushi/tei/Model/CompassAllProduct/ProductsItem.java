package com.app.sushi.tei.Model.CompassAllProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductsItem implements Parcelable {

	public ProductsItem(){

	}
	@SerializedName("product_department")
	private List<Object> productDepartment;

	@SerializedName("product_slug")
	private String productSlug;

	@SerializedName("set_menu_component")
	private List<SetMenuComponentItem> setMenuComponent;

	@SerializedName("product_status")
	private String productStatus;

	@SerializedName("product_price")
	private String productPrice;

	@SerializedName("product_sequence")
	private String productSequence;

	@SerializedName("product_thumbnail")
	private String productThumbnail;

	@SerializedName("modifiers")
	private List<Object> modifiers;

	@SerializedName("product_sku")
	private String productSku;

	@SerializedName("product_tag")
	private List<Object> productTag;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("product_long_description")
	private String productLongDescription;

	@SerializedName("product_stock")
	private String productStock;

	@SerializedName("product_primary_id")
	private String productPrimaryId;

	@SerializedName("product_alias")
	private String productAlias;

	@SerializedName("product_menu_set_component_id")
	private Object productMenuSetComponentId;

	@SerializedName("product_parent_id")
	private String productParentId;

	@SerializedName("product_name")
	private String productName;

	@SerializedName("product_is_combo")
	private String productIsCombo;

	@SerializedName("product_overall_rating")
	private String productOverallRating;

	@SerializedName("product_type")
	private String productType;

	@SerializedName("product_minimum_quantity")
	private String productMinimumQuantity;

	@SerializedName("product_alias_enabled")
	private String productAliasEnabled;

	@SerializedName("product_short_description")
	private String productShortDescription;

	@SerializedName("product_availability_id")
	private String productAvailabilityId;

	protected ProductsItem(Parcel in) {
		productSlug = in.readString();
		setMenuComponent = in.createTypedArrayList(SetMenuComponentItem.CREATOR);
		productStatus = in.readString();
		productPrice = in.readString();
		productSequence = in.readString();
		productThumbnail = in.readString();
		productSku = in.readString();
		productId = in.readString();
		productLongDescription = in.readString();
		productStock = in.readString();
		productPrimaryId = in.readString();
		productAlias = in.readString();
		productParentId = in.readString();
		productName = in.readString();
		productIsCombo = in.readString();
		productOverallRating = in.readString();
		productType = in.readString();
		productMinimumQuantity = in.readString();
		productAliasEnabled = in.readString();
		productShortDescription = in.readString();
		productAvailabilityId = in.readString();
	}

	public static final Creator<ProductsItem> CREATOR = new Creator<ProductsItem>() {
		@Override
		public ProductsItem createFromParcel(Parcel in) {
			return new ProductsItem(in);
		}

		@Override
		public ProductsItem[] newArray(int size) {
			return new ProductsItem[size];
		}
	};

	public void setProductDepartment(List<Object> productDepartment){
		this.productDepartment = productDepartment;
	}

	public List<Object> getProductDepartment(){
		return productDepartment;
	}

	public void setProductSlug(String productSlug){
		this.productSlug = productSlug;
	}

	public String getProductSlug(){
		return productSlug;
	}

	public void setSetMenuComponent(List<SetMenuComponentItem> setMenuComponent){
		this.setMenuComponent = setMenuComponent;
	}

	public List<SetMenuComponentItem> getSetMenuComponent(){
		return setMenuComponent;
	}

	public void setProductStatus(String productStatus){
		this.productStatus = productStatus;
	}

	public String getProductStatus(){
		return productStatus;
	}

	public void setProductPrice(String productPrice){
		this.productPrice = productPrice;
	}

	public String getProductPrice(){
		return productPrice;
	}

	public void setProductSequence(String productSequence){
		this.productSequence = productSequence;
	}

	public String getProductSequence(){
		return productSequence;
	}

	public void setProductThumbnail(String productThumbnail){
		this.productThumbnail = productThumbnail;
	}

	public String getProductThumbnail(){
		return productThumbnail;
	}

	public void setModifiers(List<Object> modifiers){
		this.modifiers = modifiers;
	}

	public List<Object> getModifiers(){
		return modifiers;
	}

	public void setProductSku(String productSku){
		this.productSku = productSku;
	}

	public String getProductSku(){
		return productSku;
	}

	public void setProductTag(List<Object> productTag){
		this.productTag = productTag;
	}

	public List<Object> getProductTag(){
		return productTag;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setProductLongDescription(String productLongDescription){
		this.productLongDescription = productLongDescription;
	}

	public String getProductLongDescription(){
		return productLongDescription;
	}

	public void setProductStock(String productStock){
		this.productStock = productStock;
	}

	public String getProductStock(){
		return productStock;
	}

	public void setProductPrimaryId(String productPrimaryId){
		this.productPrimaryId = productPrimaryId;
	}

	public String getProductPrimaryId(){
		return productPrimaryId;
	}

	public void setProductAlias(String productAlias){
		this.productAlias = productAlias;
	}

	public String getProductAlias(){
		return productAlias;
	}

	public void setProductMenuSetComponentId(Object productMenuSetComponentId){
		this.productMenuSetComponentId = productMenuSetComponentId;
	}

	public Object getProductMenuSetComponentId(){
		return productMenuSetComponentId;
	}

	public void setProductParentId(String productParentId){
		this.productParentId = productParentId;
	}

	public String getProductParentId(){
		return productParentId;
	}

	public void setProductName(String productName){
		this.productName = productName;
	}

	public String getProductName(){
		return productName;
	}

	public void setProductIsCombo(String productIsCombo){
		this.productIsCombo = productIsCombo;
	}

	public String getProductIsCombo(){
		return productIsCombo;
	}

	public void setProductOverallRating(String productOverallRating){
		this.productOverallRating = productOverallRating;
	}

	public String getProductOverallRating(){
		return productOverallRating;
	}

	public void setProductType(String productType){
		this.productType = productType;
	}

	public String getProductType(){
		return productType;
	}

	public void setProductMinimumQuantity(String productMinimumQuantity){
		this.productMinimumQuantity = productMinimumQuantity;
	}

	public String getProductMinimumQuantity(){
		return productMinimumQuantity;
	}

	public void setProductAliasEnabled(String productAliasEnabled){
		this.productAliasEnabled = productAliasEnabled;
	}

	public String getProductAliasEnabled(){
		return productAliasEnabled;
	}

	public void setProductShortDescription(String productShortDescription){
		this.productShortDescription = productShortDescription;
	}

	public String getProductShortDescription(){
		return productShortDescription;
	}

	public void setProductAvailabilityId(String productAvailabilityId){
		this.productAvailabilityId = productAvailabilityId;
	}

	public String getProductAvailabilityId(){
		return productAvailabilityId;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(productSlug);
		dest.writeTypedList(setMenuComponent);
		dest.writeString(productStatus);
		dest.writeString(productPrice);
		dest.writeString(productSequence);
		dest.writeString(productThumbnail);
		dest.writeString(productSku);
		dest.writeString(productId);
		dest.writeString(productLongDescription);
		dest.writeString(productStock);
		dest.writeString(productPrimaryId);
		dest.writeString(productAlias);
		dest.writeString(productParentId);
		dest.writeString(productName);
		dest.writeString(productIsCombo);
		dest.writeString(productOverallRating);
		dest.writeString(productType);
		dest.writeString(productMinimumQuantity);
		dest.writeString(productAliasEnabled);
		dest.writeString(productShortDescription);
		dest.writeString(productAvailabilityId);
	}
}