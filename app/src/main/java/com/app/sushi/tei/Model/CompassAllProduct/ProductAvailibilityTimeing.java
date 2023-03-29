package com.app.sushi.tei.Model.CompassAllProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class ProductAvailibilityTimeing implements Parcelable {

	public ProductAvailibilityTimeing() {

	}
	@SerializedName("saturday_end_time")
	private Object saturdayEndTime;

	@SerializedName("saturday_available")
	private Object saturdayAvailable;

	@SerializedName("sunday_start_time")
	private Object sundayStartTime;

	@SerializedName("monday_end_time")
	private Object mondayEndTime;

	@SerializedName("monday_start_time")
	private Object mondayStartTime;

	@SerializedName("friday_available")
	private Object fridayAvailable;

	@SerializedName("sunday_available")
	private Object sundayAvailable;

	@SerializedName("tuesday_start_time")
	private Object tuesdayStartTime;

	@SerializedName("wednesday_end_time")
	private Object wednesdayEndTime;

	@SerializedName("wednesday_start_time")
	private Object wednesdayStartTime;

	@SerializedName("friday_start_time")
	private Object fridayStartTime;

	@SerializedName("saturday_start_time")
	private Object saturdayStartTime;

	@SerializedName("thursday_available")
	private Object thursdayAvailable;

	@SerializedName("wednesday_available")
	private Object wednesdayAvailable;

	@SerializedName("thursday_end_time")
	private Object thursdayEndTime;

	@SerializedName("thursday_start_time")
	private Object thursdayStartTime;

	@SerializedName("tuesday_available")
	private Object tuesdayAvailable;

	@SerializedName("sunday_end_time")
	private Object sundayEndTime;

	@SerializedName("tuesday_end_time")
	private Object tuesdayEndTime;

	@SerializedName("monday_available")
	private Object mondayAvailable;

	@SerializedName("friday_end_time")
	private Object fridayEndTime;

	protected ProductAvailibilityTimeing(Parcel in) {
	}

	public static final Creator<ProductAvailibilityTimeing> CREATOR = new Creator<ProductAvailibilityTimeing>() {
		@Override
		public ProductAvailibilityTimeing createFromParcel(Parcel in) {
			return new ProductAvailibilityTimeing(in);
		}

		@Override
		public ProductAvailibilityTimeing[] newArray(int size) {
			return new ProductAvailibilityTimeing[size];
		}
	};

	public void setSaturdayEndTime(Object saturdayEndTime){
		this.saturdayEndTime = saturdayEndTime;
	}

	public Object getSaturdayEndTime(){
		return saturdayEndTime;
	}

	public void setSaturdayAvailable(Object saturdayAvailable){
		this.saturdayAvailable = saturdayAvailable;
	}

	public Object getSaturdayAvailable(){
		return saturdayAvailable;
	}

	public void setSundayStartTime(Object sundayStartTime){
		this.sundayStartTime = sundayStartTime;
	}

	public Object getSundayStartTime(){
		return sundayStartTime;
	}

	public void setMondayEndTime(Object mondayEndTime){
		this.mondayEndTime = mondayEndTime;
	}

	public Object getMondayEndTime(){
		return mondayEndTime;
	}

	public void setMondayStartTime(Object mondayStartTime){
		this.mondayStartTime = mondayStartTime;
	}

	public Object getMondayStartTime(){
		return mondayStartTime;
	}

	public void setFridayAvailable(Object fridayAvailable){
		this.fridayAvailable = fridayAvailable;
	}

	public Object getFridayAvailable(){
		return fridayAvailable;
	}

	public void setSundayAvailable(Object sundayAvailable){
		this.sundayAvailable = sundayAvailable;
	}

	public Object getSundayAvailable(){
		return sundayAvailable;
	}

	public void setTuesdayStartTime(Object tuesdayStartTime){
		this.tuesdayStartTime = tuesdayStartTime;
	}

	public Object getTuesdayStartTime(){
		return tuesdayStartTime;
	}

	public void setWednesdayEndTime(Object wednesdayEndTime){
		this.wednesdayEndTime = wednesdayEndTime;
	}

	public Object getWednesdayEndTime(){
		return wednesdayEndTime;
	}

	public void setWednesdayStartTime(Object wednesdayStartTime){
		this.wednesdayStartTime = wednesdayStartTime;
	}

	public Object getWednesdayStartTime(){
		return wednesdayStartTime;
	}

	public void setFridayStartTime(Object fridayStartTime){
		this.fridayStartTime = fridayStartTime;
	}

	public Object getFridayStartTime(){
		return fridayStartTime;
	}

	public void setSaturdayStartTime(Object saturdayStartTime){
		this.saturdayStartTime = saturdayStartTime;
	}

	public Object getSaturdayStartTime(){
		return saturdayStartTime;
	}

	public void setThursdayAvailable(Object thursdayAvailable){
		this.thursdayAvailable = thursdayAvailable;
	}

	public Object getThursdayAvailable(){
		return thursdayAvailable;
	}

	public void setWednesdayAvailable(Object wednesdayAvailable){
		this.wednesdayAvailable = wednesdayAvailable;
	}

	public Object getWednesdayAvailable(){
		return wednesdayAvailable;
	}

	public void setThursdayEndTime(Object thursdayEndTime){
		this.thursdayEndTime = thursdayEndTime;
	}

	public Object getThursdayEndTime(){
		return thursdayEndTime;
	}

	public void setThursdayStartTime(Object thursdayStartTime){
		this.thursdayStartTime = thursdayStartTime;
	}

	public Object getThursdayStartTime(){
		return thursdayStartTime;
	}

	public void setTuesdayAvailable(Object tuesdayAvailable){
		this.tuesdayAvailable = tuesdayAvailable;
	}

	public Object getTuesdayAvailable(){
		return tuesdayAvailable;
	}

	public void setSundayEndTime(Object sundayEndTime){
		this.sundayEndTime = sundayEndTime;
	}

	public Object getSundayEndTime(){
		return sundayEndTime;
	}

	public void setTuesdayEndTime(Object tuesdayEndTime){
		this.tuesdayEndTime = tuesdayEndTime;
	}

	public Object getTuesdayEndTime(){
		return tuesdayEndTime;
	}

	public void setMondayAvailable(Object mondayAvailable){
		this.mondayAvailable = mondayAvailable;
	}

	public Object getMondayAvailable(){
		return mondayAvailable;
	}

	public void setFridayEndTime(Object fridayEndTime){
		this.fridayEndTime = fridayEndTime;
	}

	public Object getFridayEndTime(){
		return fridayEndTime;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
	}
}