package com.app.sushi.tei.Model.Order;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ItemResponse implements Parcelable {

	@SerializedName("item_order_primary_id")
	private String itemOrderPrimaryId;

	@SerializedName("item_sku")
	private String itemSku;

	@SerializedName("set_menu_component")
	private List<Object> setMenuComponent;

	@SerializedName("modifiers")
	private List<Object> modifiers;

	@SerializedName("item_user_rating_message")
	private Object itemUserRatingMessage;

	@SerializedName("item_placed_on")
	private String itemPlacedOn;

	@SerializedName("item_created_on")
	private String itemCreatedOn;

	@SerializedName("extra_modifiers")
	private List<Object> extraModifiers;

	@SerializedName("item_breaktime_indexflag")
	private Object itemBreaktimeIndexflag;

	@SerializedName("item_user_rating")
	private Object itemUserRating;

	@SerializedName("item_id")
	private String itemId;

	@SerializedName("item_kitchen_status")
	private String itemKitchenStatus;

	@SerializedName("item_specification")
	private String itemSpecification;

	@SerializedName("item_unit_price")
	private String itemUnitPrice;

	@SerializedName("item_name")
	private String itemName;

	@SerializedName("item_order_id")
	private String itemOrderId;

	@SerializedName("item_special_amount")
	private String itemSpecialAmount;

	@SerializedName("condiments")
	private List<Object> condiments;

	@SerializedName("item_breaktime_started")
	private Object itemBreaktimeStarted;

	@SerializedName("item_original_unit_price")
	private String itemOriginalUnitPrice;

	@SerializedName("item_total_amount")
	private String itemTotalAmount;

	@SerializedName("item_product_id")
	private String itemProductId;

	@SerializedName("item_image")
	private String itemImage;

	@SerializedName("item_breaktime_ended")
	private Object itemBreaktimeEnded;

	@SerializedName("item_qty")
	private String itemQty;

	public ItemResponse(Parcel in) {
		itemOrderPrimaryId = in.readString();
		itemSku = in.readString();
		itemPlacedOn = in.readString();
		itemCreatedOn = in.readString();
		itemId = in.readString();
		itemKitchenStatus = in.readString();
		itemSpecification = in.readString();
		itemUnitPrice = in.readString();
		itemName = in.readString();
		itemOrderId = in.readString();
		itemSpecialAmount = in.readString();
		itemOriginalUnitPrice = in.readString();
		itemTotalAmount = in.readString();
		itemProductId = in.readString();
		itemImage = in.readString();
		itemQty = in.readString();
	}

	public static final Creator<ItemResponse> CREATOR = new Creator<ItemResponse>() {
		@Override
		public ItemResponse createFromParcel(Parcel in) {
			return new ItemResponse(in);
		}

		@Override
		public ItemResponse[] newArray(int size) {
			return new ItemResponse[size];
		}
	};

	public ItemResponse() {

	}

	public void setItemOrderPrimaryId(String itemOrderPrimaryId){
		this.itemOrderPrimaryId = itemOrderPrimaryId;
	}

	public String getItemOrderPrimaryId(){
		return itemOrderPrimaryId;
	}

	public void setItemSku(String itemSku){
		this.itemSku = itemSku;
	}

	public String getItemSku(){
		return itemSku;
	}

	public void setSetMenuComponent(List<Object> setMenuComponent){
		this.setMenuComponent = setMenuComponent;
	}

	public List<Object> getSetMenuComponent(){
		return setMenuComponent;
	}

	public void setModifiers(List<Object> modifiers){
		this.modifiers = modifiers;
	}

	public List<Object> getModifiers(){
		return modifiers;
	}

	public void setItemUserRatingMessage(Object itemUserRatingMessage){
		this.itemUserRatingMessage = itemUserRatingMessage;
	}

	public Object getItemUserRatingMessage(){
		return itemUserRatingMessage;
	}

	public void setItemPlacedOn(String itemPlacedOn){
		this.itemPlacedOn = itemPlacedOn;
	}

	public String getItemPlacedOn(){
		return itemPlacedOn;
	}

	public void setItemCreatedOn(String itemCreatedOn){
		this.itemCreatedOn = itemCreatedOn;
	}

	public String getItemCreatedOn(){
		return itemCreatedOn;
	}

	public void setExtraModifiers(List<Object> extraModifiers){
		this.extraModifiers = extraModifiers;
	}

	public List<Object> getExtraModifiers(){
		return extraModifiers;
	}

	public void setItemBreaktimeIndexflag(Object itemBreaktimeIndexflag){
		this.itemBreaktimeIndexflag = itemBreaktimeIndexflag;
	}

	public Object getItemBreaktimeIndexflag(){
		return itemBreaktimeIndexflag;
	}

	public void setItemUserRating(Object itemUserRating){
		this.itemUserRating = itemUserRating;
	}

	public Object getItemUserRating(){
		return itemUserRating;
	}

	public void setItemId(String itemId){
		this.itemId = itemId;
	}

	public String getItemId(){
		return itemId;
	}

	public void setItemKitchenStatus(String itemKitchenStatus){
		this.itemKitchenStatus = itemKitchenStatus;
	}

	public String getItemKitchenStatus(){
		return itemKitchenStatus;
	}

	public void setItemSpecification(String itemSpecification){
		this.itemSpecification = itemSpecification;
	}

	public String getItemSpecification(){
		return itemSpecification;
	}

	public void setItemUnitPrice(String itemUnitPrice){
		this.itemUnitPrice = itemUnitPrice;
	}

	public String getItemUnitPrice(){
		return itemUnitPrice;
	}

	public void setItemName(String itemName){
		this.itemName = itemName;
	}

	public String getItemName(){
		return itemName;
	}

	public void setItemOrderId(String itemOrderId){
		this.itemOrderId = itemOrderId;
	}

	public String getItemOrderId(){
		return itemOrderId;
	}

	public void setItemSpecialAmount(String itemSpecialAmount){
		this.itemSpecialAmount = itemSpecialAmount;
	}

	public String getItemSpecialAmount(){
		return itemSpecialAmount;
	}

	public void setCondiments(List<Object> condiments){
		this.condiments = condiments;
	}

	public List<Object> getCondiments(){
		return condiments;
	}

	public void setItemBreaktimeStarted(Object itemBreaktimeStarted){
		this.itemBreaktimeStarted = itemBreaktimeStarted;
	}

	public Object getItemBreaktimeStarted(){
		return itemBreaktimeStarted;
	}

	public void setItemOriginalUnitPrice(String itemOriginalUnitPrice){
		this.itemOriginalUnitPrice = itemOriginalUnitPrice;
	}

	public String getItemOriginalUnitPrice(){
		return itemOriginalUnitPrice;
	}

	public void setItemTotalAmount(String itemTotalAmount){
		this.itemTotalAmount = itemTotalAmount;
	}

	public String getItemTotalAmount(){
		return itemTotalAmount;
	}

	public void setItemProductId(String itemProductId){
		this.itemProductId = itemProductId;
	}

	public String getItemProductId(){
		return itemProductId;
	}

	public void setItemImage(String itemImage){
		this.itemImage = itemImage;
	}

	public String getItemImage(){
		return itemImage;
	}

	public void setItemBreaktimeEnded(Object itemBreaktimeEnded){
		this.itemBreaktimeEnded = itemBreaktimeEnded;
	}

	public Object getItemBreaktimeEnded(){
		return itemBreaktimeEnded;
	}

	public void setItemQty(String itemQty){
		this.itemQty = itemQty;
	}

	public String getItemQty(){
		return itemQty;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(itemOrderPrimaryId);
		dest.writeString(itemSku);
		dest.writeString(itemPlacedOn);
		dest.writeString(itemCreatedOn);
		dest.writeString(itemId);
		dest.writeString(itemKitchenStatus);
		dest.writeString(itemSpecification);
		dest.writeString(itemUnitPrice);
		dest.writeString(itemName);
		dest.writeString(itemOrderId);
		dest.writeString(itemSpecialAmount);
		dest.writeString(itemOriginalUnitPrice);
		dest.writeString(itemTotalAmount);
		dest.writeString(itemProductId);
		dest.writeString(itemImage);
		dest.writeString(itemQty);
	}
}