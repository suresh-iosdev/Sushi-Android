package com.app.sushi.tei.Model.CompassAllProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ProductDetailsItemItem implements Parcelable {

	public ProductDetailsItemItem(){

	}
	private boolean hasModifier=false;
	private boolean isChecked=false;
	@SerializedName("product_slug")
	private String productSlug;

	@SerializedName("product_updated_on")
	private Object productUpdatedOn;

	@SerializedName("subcatgory_short_description")
	private Object subcatgoryShortDescription;

	@SerializedName("catgory_description")
	private Object catgoryDescription;

	@SerializedName("product_brand_id")
	private String productBrandId;

	@SerializedName("product_lead_time")
	private Object productLeadTime;

	@SerializedName("product_price")
	private String productPrice;

	@SerializedName("product_sequence")
	private String productSequence;

	@SerializedName("product_special_price_to_date")
	private Object productSpecialPriceToDate;

	@SerializedName("product_is_bagel")
	private String productIsBagel;

	@SerializedName("product_min_pax")
	private Object productMinPax;

	@SerializedName("product_category_id")
	private String productCategoryId;

	@SerializedName("product_created_on")
	private String productCreatedOn;

	@SerializedName("subcatgory_sequence")
	private String subcatgorySequence;

	@SerializedName("catgory_sequence")
	private String catgorySequence;

	@SerializedName("extra_modifiers")
	private Object extraModifiers;

	@SerializedName("subcatgory_name")
	private String subcatgoryName;

	@SerializedName("product_id")
	private String productId;

	@SerializedName("product_availibility_timeing")
	private ProductAvailibilityTimeing productAvailibilityTimeing;

	@SerializedName("product_primary_id")
	private String productPrimaryId;

	@SerializedName("product_alias")
	private String productAlias;

	@SerializedName("image_gallery")
	private List<Object> imageGallery;

	@SerializedName("catgory_image")
	private Object catgoryImage;

	@SerializedName("product_barcode")
	private Object productBarcode;

	@SerializedName("product_company_id")
	private String productCompanyId;

	@SerializedName("product_is_babypack")
	private String productIsBabypack;

	@SerializedName("product_special_price")
	private Object productSpecialPrice;

	@SerializedName("product_name")
	private String productName;

	@SerializedName("product_is_combo")
	private String productIsCombo;

	@SerializedName("product_minimum_quantity")
	private Object productMinimumQuantity;

	@SerializedName("product_short_description")
	private String productShortDescription;

	@SerializedName("product_alias_enabled")
	private String productAliasEnabled;

	@SerializedName("subcat_availibility_timeing")
	private SubcatAvailibilityTimeing subcatAvailibilityTimeing;

	@SerializedName("catgory_name")
	private String catgoryName;

	@SerializedName("subcatgory_description")
	private Object subcatgoryDescription;

	@SerializedName("product_reward_allowed_purchase")
	private String productRewardAllowedPurchase;

	@SerializedName("product_department")
	private List<Object> productDepartment;

	@SerializedName("cat_slug")
	private String catSlug;

	@SerializedName("set_menu_component")
	private List<Object> setMenuComponent;

	@SerializedName("product_status")
	private String productStatus;

	@SerializedName("product_company_app_id")
	private String productCompanyAppId;

	@SerializedName("product_thumbnail")
	private String productThumbnail;

	@SerializedName("modifiers")
	private Object modifiers;

	@SerializedName("product_apply_minmax_select")
	private Object productApplyMinmaxSelect;

	@SerializedName("product_is_alias")
	private String productIsAlias;

	@SerializedName("product_sku")
	private String productSku;

	@SerializedName("product_meta_keywords")
	private String productMetaKeywords;

	@SerializedName("product_reward_point")
	private String productRewardPoint;

	@SerializedName("product_alias_is_default")
	private String productAliasIsDefault;

	@SerializedName("product_tag")
	private List<Object> productTag;

	@SerializedName("product_bagel_min_select")
	private String productBagelMinSelect;

	@SerializedName("subcatgory_image")
	private Object subcatgoryImage;

	@SerializedName("product_long_description")
	private String productLongDescription;

	@SerializedName("product_stock")
	private Object productStock;

	@SerializedName("product_alt_price")
	private Object productAltPrice;

	@SerializedName("product_menu_set_component_id")
	private Object productMenuSetComponentId;

	@SerializedName("product_special_price_from_date")
	private Object productSpecialPriceFromDate;

	@SerializedName("product_meta_description")
	private String productMetaDescription;

	@SerializedName("product_class_id")
	private Object productClassId;

	@SerializedName("product_meta_title")
	private String productMetaTitle;

	@SerializedName("product_subcategory_id")
	private String productSubcategoryId;

	@SerializedName("product_sub_modifier_qty")
	private Object productSubModifierQty;

	@SerializedName("product_parent_id")
	private String productParentId;

	@SerializedName("catgory_short_description")
	private Object catgoryShortDescription;

	@SerializedName("product_cost")
	private String productCost;

	@SerializedName("product_overall_rating")
	private String productOverallRating;

	@SerializedName("product_bagel_max_select")
	private String productBagelMaxSelect;

	@SerializedName("product_type")
	private String productType;

	protected ProductDetailsItemItem(Parcel in) {
		productSlug = in.readString();
		productBrandId = in.readString();
		productPrice = in.readString();
		productSequence = in.readString();
		productIsBagel = in.readString();
		productCategoryId = in.readString();
		productCreatedOn = in.readString();
		subcatgorySequence = in.readString();
		catgorySequence = in.readString();
		subcatgoryName = in.readString();
		productId = in.readString();
		productAvailibilityTimeing = in.readParcelable(ProductAvailibilityTimeing.class.getClassLoader());
		productPrimaryId = in.readString();
		productAlias = in.readString();
		productCompanyId = in.readString();
		productIsBabypack = in.readString();
		productName = in.readString();
		productIsCombo = in.readString();
		productShortDescription = in.readString();
		productAliasEnabled = in.readString();
		catgoryName = in.readString();
		productRewardAllowedPurchase = in.readString();
		catSlug = in.readString();
		productStatus = in.readString();
		productCompanyAppId = in.readString();
		productThumbnail = in.readString();
		productIsAlias = in.readString();
		productSku = in.readString();
		productMetaKeywords = in.readString();
		productRewardPoint = in.readString();
		productAliasIsDefault = in.readString();
		productBagelMinSelect = in.readString();
		productLongDescription = in.readString();
		productMetaDescription = in.readString();
		productMetaTitle = in.readString();
		productSubcategoryId = in.readString();
		productParentId = in.readString();
		productCost = in.readString();
		productOverallRating = in.readString();
		productBagelMaxSelect = in.readString();
		productType = in.readString();
	}

	public static final Creator<ProductDetailsItemItem> CREATOR = new Creator<ProductDetailsItemItem>() {
		@Override
		public ProductDetailsItemItem createFromParcel(Parcel in) {
			return new ProductDetailsItemItem(in);
		}

		@Override
		public ProductDetailsItemItem[] newArray(int size) {
			return new ProductDetailsItemItem[size];
		}
	};

	public boolean getHasModifier() {
		return hasModifier;
	}

	public void setHasModifier(boolean hasModifier) {
		this.hasModifier = hasModifier;
	}

	public boolean isChecked() {
		return isChecked;
	}



	public void setChecked(boolean checked) {
		isChecked = checked;
	}
	public void setProductSlug(String productSlug){
		this.productSlug = productSlug;
	}

	public String getProductSlug(){
		return productSlug;
	}

	public void setProductUpdatedOn(Object productUpdatedOn){
		this.productUpdatedOn = productUpdatedOn;
	}

	public Object getProductUpdatedOn(){
		return productUpdatedOn;
	}

	public void setSubcatgoryShortDescription(Object subcatgoryShortDescription){
		this.subcatgoryShortDescription = subcatgoryShortDescription;
	}

	public Object getSubcatgoryShortDescription(){
		return subcatgoryShortDescription;
	}

	public void setCatgoryDescription(Object catgoryDescription){
		this.catgoryDescription = catgoryDescription;
	}

	public Object getCatgoryDescription(){
		return catgoryDescription;
	}

	public void setProductBrandId(String productBrandId){
		this.productBrandId = productBrandId;
	}

	public String getProductBrandId(){
		return productBrandId;
	}

	public void setProductLeadTime(Object productLeadTime){
		this.productLeadTime = productLeadTime;
	}

	public Object getProductLeadTime(){
		return productLeadTime;
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

	public void setProductSpecialPriceToDate(Object productSpecialPriceToDate){
		this.productSpecialPriceToDate = productSpecialPriceToDate;
	}

	public Object getProductSpecialPriceToDate(){
		return productSpecialPriceToDate;
	}

	public void setProductIsBagel(String productIsBagel){
		this.productIsBagel = productIsBagel;
	}

	public String getProductIsBagel(){
		return productIsBagel;
	}

	public void setProductMinPax(Object productMinPax){
		this.productMinPax = productMinPax;
	}

	public Object getProductMinPax(){
		return productMinPax;
	}

	public void setProductCategoryId(String productCategoryId){
		this.productCategoryId = productCategoryId;
	}

	public String getProductCategoryId(){
		return productCategoryId;
	}

	public void setProductCreatedOn(String productCreatedOn){
		this.productCreatedOn = productCreatedOn;
	}

	public String getProductCreatedOn(){
		return productCreatedOn;
	}

	public void setSubcatgorySequence(String subcatgorySequence){
		this.subcatgorySequence = subcatgorySequence;
	}

	public String getSubcatgorySequence(){
		return subcatgorySequence;
	}

	public void setCatgorySequence(String catgorySequence){
		this.catgorySequence = catgorySequence;
	}

	public String getCatgorySequence(){
		return catgorySequence;
	}

	public void setExtraModifiers(Object extraModifiers){
		this.extraModifiers = extraModifiers;
	}

	public Object getExtraModifiers(){
		return extraModifiers;
	}

	public void setSubcatgoryName(String subcatgoryName){
		this.subcatgoryName = subcatgoryName;
	}

	public String getSubcatgoryName(){
		return subcatgoryName;
	}

	public void setProductId(String productId){
		this.productId = productId;
	}

	public String getProductId(){
		return productId;
	}

	public void setProductAvailibilityTimeing(ProductAvailibilityTimeing productAvailibilityTimeing){
		this.productAvailibilityTimeing = productAvailibilityTimeing;
	}

	public ProductAvailibilityTimeing getProductAvailibilityTimeing(){
		return productAvailibilityTimeing;
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

	public void setImageGallery(List<Object> imageGallery){
		this.imageGallery = imageGallery;
	}

	public List<Object> getImageGallery(){
		return imageGallery;
	}

	public void setCatgoryImage(Object catgoryImage){
		this.catgoryImage = catgoryImage;
	}

	public Object getCatgoryImage(){
		return catgoryImage;
	}

	public void setProductBarcode(Object productBarcode){
		this.productBarcode = productBarcode;
	}

	public Object getProductBarcode(){
		return productBarcode;
	}

	public void setProductCompanyId(String productCompanyId){
		this.productCompanyId = productCompanyId;
	}

	public String getProductCompanyId(){
		return productCompanyId;
	}

	public void setProductIsBabypack(String productIsBabypack){
		this.productIsBabypack = productIsBabypack;
	}

	public String getProductIsBabypack(){
		return productIsBabypack;
	}

	public void setProductSpecialPrice(Object productSpecialPrice){
		this.productSpecialPrice = productSpecialPrice;
	}

	public Object getProductSpecialPrice(){
		return productSpecialPrice;
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

	public void setProductMinimumQuantity(Object productMinimumQuantity){
		this.productMinimumQuantity = productMinimumQuantity;
	}

	public Object getProductMinimumQuantity(){
		return productMinimumQuantity;
	}

	public void setProductShortDescription(String productShortDescription){
		this.productShortDescription = productShortDescription;
	}

	public String getProductShortDescription(){
		return productShortDescription;
	}

	public void setProductAliasEnabled(String productAliasEnabled){
		this.productAliasEnabled = productAliasEnabled;
	}

	public String getProductAliasEnabled(){
		return productAliasEnabled;
	}

	public void setSubcatAvailibilityTimeing(SubcatAvailibilityTimeing subcatAvailibilityTimeing){
		this.subcatAvailibilityTimeing = subcatAvailibilityTimeing;
	}

	public SubcatAvailibilityTimeing getSubcatAvailibilityTimeing(){
		return subcatAvailibilityTimeing;
	}

	public void setCatgoryName(String catgoryName){
		this.catgoryName = catgoryName;
	}

	public String getCatgoryName(){
		return catgoryName;
	}

	public void setSubcatgoryDescription(Object subcatgoryDescription){
		this.subcatgoryDescription = subcatgoryDescription;
	}

	public Object getSubcatgoryDescription(){
		return subcatgoryDescription;
	}

	public void setProductRewardAllowedPurchase(String productRewardAllowedPurchase){
		this.productRewardAllowedPurchase = productRewardAllowedPurchase;
	}

	public String getProductRewardAllowedPurchase(){
		return productRewardAllowedPurchase;
	}

	public void setProductDepartment(List<Object> productDepartment){
		this.productDepartment = productDepartment;
	}

	public List<Object> getProductDepartment(){
		return productDepartment;
	}

	public void setCatSlug(String catSlug){
		this.catSlug = catSlug;
	}

	public String getCatSlug(){
		return catSlug;
	}

	public void setSetMenuComponent(List<Object> setMenuComponent){
		this.setMenuComponent = setMenuComponent;
	}

	public List<Object> getSetMenuComponent(){
		return setMenuComponent;
	}

	public void setProductStatus(String productStatus){
		this.productStatus = productStatus;
	}

	public String getProductStatus(){
		return productStatus;
	}

	public void setProductCompanyAppId(String productCompanyAppId){
		this.productCompanyAppId = productCompanyAppId;
	}

	public String getProductCompanyAppId(){
		return productCompanyAppId;
	}

	public void setProductThumbnail(String productThumbnail){
		this.productThumbnail = productThumbnail;
	}

	public String getProductThumbnail(){
		return productThumbnail;
	}

	public void setModifiers(Object modifiers){
		this.modifiers = modifiers;
	}

	public Object getModifiers(){
		return modifiers;
	}

	public void setProductApplyMinmaxSelect(Object productApplyMinmaxSelect){
		this.productApplyMinmaxSelect = productApplyMinmaxSelect;
	}

	public Object getProductApplyMinmaxSelect(){
		return productApplyMinmaxSelect;
	}

	public void setProductIsAlias(String productIsAlias){
		this.productIsAlias = productIsAlias;
	}

	public String getProductIsAlias(){
		return productIsAlias;
	}

	public void setProductSku(String productSku){
		this.productSku = productSku;
	}

	public String getProductSku(){
		return productSku;
	}

	public void setProductMetaKeywords(String productMetaKeywords){
		this.productMetaKeywords = productMetaKeywords;
	}

	public String getProductMetaKeywords(){
		return productMetaKeywords;
	}

	public void setProductRewardPoint(String productRewardPoint){
		this.productRewardPoint = productRewardPoint;
	}

	public String getProductRewardPoint(){
		return productRewardPoint;
	}

	public void setProductAliasIsDefault(String productAliasIsDefault){
		this.productAliasIsDefault = productAliasIsDefault;
	}

	public String getProductAliasIsDefault(){
		return productAliasIsDefault;
	}

	public void setProductTag(List<Object> productTag){
		this.productTag = productTag;
	}

	public List<Object> getProductTag(){
		return productTag;
	}

	public void setProductBagelMinSelect(String productBagelMinSelect){
		this.productBagelMinSelect = productBagelMinSelect;
	}

	public String getProductBagelMinSelect(){
		return productBagelMinSelect;
	}

	public void setSubcatgoryImage(Object subcatgoryImage){
		this.subcatgoryImage = subcatgoryImage;
	}

	public Object getSubcatgoryImage(){
		return subcatgoryImage;
	}

	public void setProductLongDescription(String productLongDescription){
		this.productLongDescription = productLongDescription;
	}

	public String getProductLongDescription(){
		return productLongDescription;
	}

	public void setProductStock(Object productStock){
		this.productStock = productStock;
	}

	public Object getProductStock(){
		return productStock;
	}

	public void setProductAltPrice(Object productAltPrice){
		this.productAltPrice = productAltPrice;
	}

	public Object getProductAltPrice(){
		return productAltPrice;
	}

	public void setProductMenuSetComponentId(Object productMenuSetComponentId){
		this.productMenuSetComponentId = productMenuSetComponentId;
	}

	public Object getProductMenuSetComponentId(){
		return productMenuSetComponentId;
	}

	public void setProductSpecialPriceFromDate(Object productSpecialPriceFromDate){
		this.productSpecialPriceFromDate = productSpecialPriceFromDate;
	}

	public Object getProductSpecialPriceFromDate(){
		return productSpecialPriceFromDate;
	}

	public void setProductMetaDescription(String productMetaDescription){
		this.productMetaDescription = productMetaDescription;
	}

	public String getProductMetaDescription(){
		return productMetaDescription;
	}

	public void setProductClassId(Object productClassId){
		this.productClassId = productClassId;
	}

	public Object getProductClassId(){
		return productClassId;
	}

	public void setProductMetaTitle(String productMetaTitle){
		this.productMetaTitle = productMetaTitle;
	}

	public String getProductMetaTitle(){
		return productMetaTitle;
	}

	public void setProductSubcategoryId(String productSubcategoryId){
		this.productSubcategoryId = productSubcategoryId;
	}

	public String getProductSubcategoryId(){
		return productSubcategoryId;
	}

	public void setProductSubModifierQty(Object productSubModifierQty){
		this.productSubModifierQty = productSubModifierQty;
	}

	public Object getProductSubModifierQty(){
		return productSubModifierQty;
	}

	public void setProductParentId(String productParentId){
		this.productParentId = productParentId;
	}

	public String getProductParentId(){
		return productParentId;
	}

	public void setCatgoryShortDescription(Object catgoryShortDescription){
		this.catgoryShortDescription = catgoryShortDescription;
	}

	public Object getCatgoryShortDescription(){
		return catgoryShortDescription;
	}

	public void setProductCost(String productCost){
		this.productCost = productCost;
	}

	public String getProductCost(){
		return productCost;
	}

	public void setProductOverallRating(String productOverallRating){
		this.productOverallRating = productOverallRating;
	}

	public String getProductOverallRating(){
		return productOverallRating;
	}

	public void setProductBagelMaxSelect(String productBagelMaxSelect){
		this.productBagelMaxSelect = productBagelMaxSelect;
	}

	public String getProductBagelMaxSelect(){
		return productBagelMaxSelect;
	}

	public void setProductType(String productType){
		this.productType = productType;
	}

	public String getProductType(){
		return productType;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(productSlug);
		dest.writeString(productBrandId);
		dest.writeString(productPrice);
		dest.writeString(productSequence);
		dest.writeString(productIsBagel);
		dest.writeString(productCategoryId);
		dest.writeString(productCreatedOn);
		dest.writeString(subcatgorySequence);
		dest.writeString(catgorySequence);
		dest.writeString(subcatgoryName);
		dest.writeString(productId);
		dest.writeParcelable(productAvailibilityTimeing, flags);
		dest.writeString(productPrimaryId);
		dest.writeString(productAlias);
		dest.writeString(productCompanyId);
		dest.writeString(productIsBabypack);
		dest.writeString(productName);
		dest.writeString(productIsCombo);
		dest.writeString(productShortDescription);
		dest.writeString(productAliasEnabled);
		dest.writeString(catgoryName);
		dest.writeString(productRewardAllowedPurchase);
		dest.writeString(catSlug);
		dest.writeString(productStatus);
		dest.writeString(productCompanyAppId);
		dest.writeString(productThumbnail);
		dest.writeString(productIsAlias);
		dest.writeString(productSku);
		dest.writeString(productMetaKeywords);
		dest.writeString(productRewardPoint);
		dest.writeString(productAliasIsDefault);
		dest.writeString(productBagelMinSelect);
		dest.writeString(productLongDescription);
		dest.writeString(productMetaDescription);
		dest.writeString(productMetaTitle);
		dest.writeString(productSubcategoryId);
		dest.writeString(productParentId);
		dest.writeString(productCost);
		dest.writeString(productOverallRating);
		dest.writeString(productBagelMaxSelect);
		dest.writeString(productType);
	}
}