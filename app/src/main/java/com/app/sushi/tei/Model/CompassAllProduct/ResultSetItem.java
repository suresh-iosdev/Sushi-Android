package com.app.sushi.tei.Model.CompassAllProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class ResultSetItem implements Parcelable {

public ResultSetItem(){

}

	@SerializedName("pro_subcate_image")
	private Object proSubcateImage;

	@SerializedName("subcat_wednesday_start_time")
	private Object subcatWednesdayStartTime;

	@SerializedName("pro_subcate_id")
	private String proSubcateId;

	@SerializedName("cate_availability_id")
	private String cateAvailabilityId;

	@SerializedName("pro_cate_description")
	private String proCateDescription;

	@SerializedName("pro_cate_status")
	private String proCateStatus;

	@SerializedName("pro_subcate_slug")
	private String proSubcateSlug;

	@SerializedName("products")
	private List<ProductsItem> products;

	@SerializedName("pro_cate_name")
	private String proCateName;

	@SerializedName("pro_cate_primary_id")
	private String proCatePrimaryId;

	@SerializedName("subcat_wednesday_end_time")
	private Object subcatWednesdayEndTime;

	@SerializedName("pro_subcate_default_image")
	private Object proSubcateDefaultImage;

	@SerializedName("pro_cate_short_description")
	private String proCateShortDescription;

	@SerializedName("pro_subcate_sequence")
	private String proSubcateSequence;

	@SerializedName("pro_subcate_active_image")
	private Object proSubcateActiveImage;

	@SerializedName("pro_cate_id")
	private String proCateId;

	@SerializedName("cat_wednesday_check")
	private Object catWednesdayCheck;

	@SerializedName("pro_subcate_name")
	private String proSubcateName;

	@SerializedName("pro_subcate_status")
	private String proSubcateStatus;

	@SerializedName("cat_wednesday_end_time")
	private Object catWednesdayEndTime;

	@SerializedName("pro_subcate_category_primary_id")
	private String proSubcateCategoryPrimaryId;

	@SerializedName("pro_cate_slug")
	private String proCateSlug;

	@SerializedName("cat_wednesday_start_time")
	private Object catWednesdayStartTime;

	@SerializedName("subcat_wednesday_check")
	private Object subcatWednesdayCheck;

	@SerializedName("pro_subcate_primary_id")
	private String proSubcatePrimaryId;

	@SerializedName("pro_cate_sequence")
	private String proCateSequence;

	public ResultSetItem(Parcel in) {
		proSubcateId = in.readString();
		cateAvailabilityId = in.readString();
		proCateDescription = in.readString();
		proCateStatus = in.readString();
		proSubcateSlug = in.readString();
		products = in.createTypedArrayList(ProductsItem.CREATOR);
		proCateName = in.readString();
		proCatePrimaryId = in.readString();
		proCateShortDescription = in.readString();
		proSubcateSequence = in.readString();
		proCateId = in.readString();
		proSubcateName = in.readString();
		proSubcateStatus = in.readString();
		proSubcateCategoryPrimaryId = in.readString();
		proCateSlug = in.readString();
		proSubcatePrimaryId = in.readString();
		proCateSequence = in.readString();
	}

	public static final Creator<ResultSetItem> CREATOR = new Creator<ResultSetItem>() {
		@Override
		public ResultSetItem createFromParcel(Parcel in) {
			return new ResultSetItem(in);
		}

		@Override
		public ResultSetItem[] newArray(int size) {
			return new ResultSetItem[size];
		}
	};

	public void setProSubcateImage(Object proSubcateImage){
		this.proSubcateImage = proSubcateImage;
	}

	public Object getProSubcateImage(){
		return proSubcateImage;
	}

	public void setSubcatWednesdayStartTime(Object subcatWednesdayStartTime){
		this.subcatWednesdayStartTime = subcatWednesdayStartTime;
	}

	public Object getSubcatWednesdayStartTime(){
		return subcatWednesdayStartTime;
	}

	public void setProSubcateId(String proSubcateId){
		this.proSubcateId = proSubcateId;
	}

	public String getProSubcateId(){
		return proSubcateId;
	}

	public void setCateAvailabilityId(String cateAvailabilityId){
		this.cateAvailabilityId = cateAvailabilityId;
	}

	public String getCateAvailabilityId(){
		return cateAvailabilityId;
	}

	public void setProCateDescription(String proCateDescription){
		this.proCateDescription = proCateDescription;
	}

	public String getProCateDescription(){
		return proCateDescription;
	}

	public void setProCateStatus(String proCateStatus){
		this.proCateStatus = proCateStatus;
	}

	public String getProCateStatus(){
		return proCateStatus;
	}

	public void setProSubcateSlug(String proSubcateSlug){
		this.proSubcateSlug = proSubcateSlug;
	}

	public String getProSubcateSlug(){
		return proSubcateSlug;
	}

	public void setProducts(List<ProductsItem> products){
		this.products = products;
	}

	public List<ProductsItem> getProducts(){
		return products;
	}

	public void setProCateName(String proCateName){
		this.proCateName = proCateName;
	}

	public String getProCateName(){
		return proCateName;
	}

	public void setProCatePrimaryId(String proCatePrimaryId){
		this.proCatePrimaryId = proCatePrimaryId;
	}

	public String getProCatePrimaryId(){
		return proCatePrimaryId;
	}

	public void setSubcatWednesdayEndTime(Object subcatWednesdayEndTime){
		this.subcatWednesdayEndTime = subcatWednesdayEndTime;
	}

	public Object getSubcatWednesdayEndTime(){
		return subcatWednesdayEndTime;
	}

	public void setProSubcateDefaultImage(Object proSubcateDefaultImage){
		this.proSubcateDefaultImage = proSubcateDefaultImage;
	}

	public Object getProSubcateDefaultImage(){
		return proSubcateDefaultImage;
	}

	public void setProCateShortDescription(String proCateShortDescription){
		this.proCateShortDescription = proCateShortDescription;
	}

	public String getProCateShortDescription(){
		return proCateShortDescription;
	}

	public void setProSubcateSequence(String proSubcateSequence){
		this.proSubcateSequence = proSubcateSequence;
	}

	public String getProSubcateSequence(){
		return proSubcateSequence;
	}

	public void setProSubcateActiveImage(Object proSubcateActiveImage){
		this.proSubcateActiveImage = proSubcateActiveImage;
	}

	public Object getProSubcateActiveImage(){
		return proSubcateActiveImage;
	}

	public void setProCateId(String proCateId){
		this.proCateId = proCateId;
	}

	public String getProCateId(){
		return proCateId;
	}

	public void setCatWednesdayCheck(Object catWednesdayCheck){
		this.catWednesdayCheck = catWednesdayCheck;
	}

	public Object getCatWednesdayCheck(){
		return catWednesdayCheck;
	}

	public void setProSubcateName(String proSubcateName){
		this.proSubcateName = proSubcateName;
	}

	public String getProSubcateName(){
		return proSubcateName;
	}

	public void setProSubcateStatus(String proSubcateStatus){
		this.proSubcateStatus = proSubcateStatus;
	}

	public String getProSubcateStatus(){
		return proSubcateStatus;
	}

	public void setCatWednesdayEndTime(Object catWednesdayEndTime){
		this.catWednesdayEndTime = catWednesdayEndTime;
	}

	public Object getCatWednesdayEndTime(){
		return catWednesdayEndTime;
	}

	public void setProSubcateCategoryPrimaryId(String proSubcateCategoryPrimaryId){
		this.proSubcateCategoryPrimaryId = proSubcateCategoryPrimaryId;
	}

	public String getProSubcateCategoryPrimaryId(){
		return proSubcateCategoryPrimaryId;
	}

	public void setProCateSlug(String proCateSlug){
		this.proCateSlug = proCateSlug;
	}

	public String getProCateSlug(){
		return proCateSlug;
	}

	public void setCatWednesdayStartTime(Object catWednesdayStartTime){
		this.catWednesdayStartTime = catWednesdayStartTime;
	}

	public Object getCatWednesdayStartTime(){
		return catWednesdayStartTime;
	}

	public void setSubcatWednesdayCheck(Object subcatWednesdayCheck){
		this.subcatWednesdayCheck = subcatWednesdayCheck;
	}

	public Object getSubcatWednesdayCheck(){
		return subcatWednesdayCheck;
	}

	public void setProSubcatePrimaryId(String proSubcatePrimaryId){
		this.proSubcatePrimaryId = proSubcatePrimaryId;
	}

	public String getProSubcatePrimaryId(){
		return proSubcatePrimaryId;
	}

	public void setProCateSequence(String proCateSequence){
		this.proCateSequence = proCateSequence;
	}

	public String getProCateSequence(){
		return proCateSequence;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(proSubcateId);
		dest.writeString(cateAvailabilityId);
		dest.writeString(proCateDescription);
		dest.writeString(proCateStatus);
		dest.writeString(proSubcateSlug);
		dest.writeTypedList(products);
		dest.writeString(proCateName);
		dest.writeString(proCatePrimaryId);
		dest.writeString(proCateShortDescription);
		dest.writeString(proSubcateSequence);
		dest.writeString(proCateId);
		dest.writeString(proSubcateName);
		dest.writeString(proSubcateStatus);
		dest.writeString(proSubcateCategoryPrimaryId);
		dest.writeString(proCateSlug);
		dest.writeString(proSubcatePrimaryId);
		dest.writeString(proCateSequence);
	}
}