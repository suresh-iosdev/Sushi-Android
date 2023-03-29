package com.app.sushi.tei.Model.outlet.takeaway;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultSetItem implements Serializable {

	@SerializedName("outlet_open_time")
	private Object outletOpenTime;

	@SerializedName("distance")
	private String distance;

	@SerializedName("outlet_delivery_timing")
	private String outletDeliveryTiming;

	@SerializedName("oa_outlet_id")
	private String oaOutletId;

	@SerializedName("outlet_pickup_tat")
	private String outletPickupTat;

	@SerializedName("outlet_route_id")
	private String outletRouteId;

	@SerializedName("outlet_name")
	private String outletName;

	@SerializedName("outlet_marker_latitude")
	private String outletMarkerLatitude;

	@SerializedName("outlet_unit_number1")
	private String outletUnitNumber1;

	@SerializedName("outlet_postal_code")
	private String outletPostalCode;

	@SerializedName("outlet_close_time")
	private Object outletCloseTime;

	@SerializedName("outlet_dine_tat")
	private String outletDineTat;

	@SerializedName("outlet_unit_number2")
	private String outletUnitNumber2;

	@SerializedName("outlet_delivery_tat")
	private String outletDeliveryTat;

	@SerializedName("outlet_marker_longitude")
	private String outletMarkerLongitude;

	@SerializedName("outlet_customer_level_enable")
	private String outletCustomerLevelEnable;

	@SerializedName("outlet_phone")
	private String outletPhone;

	@SerializedName("outlet_service_charge")
	private String outletServiceCharge;

	@SerializedName("outlet_close_date")
	private String outletCloseDate;

	@SerializedName("oa_availability_id")
	private String oaAvailabilityId;

	@SerializedName("outlet_id")
	private String outletId;

	@SerializedName("outlet_open_date")
	private String outletOpenDate;

	@SerializedName("outlet_address_line1")
	private String outletAddressLine1;

	@SerializedName("outlet_address_line2")
	private String outletAddressLine2;

	@SerializedName("outlet_defalut_value")
	private String outletDefalutValue;

	@SerializedName("outlet_timing")
	private List<OutletTimingItem> outletTiming;

	@SerializedName("operational_hr")
	private String operationHrs;

	@SerializedName("outlet_image")
	private String outlet_image;

	public void setOutletOpenTime(Object outletOpenTime){
		this.outletOpenTime = outletOpenTime;
	}

	public Object getOutletOpenTime(){
		return outletOpenTime;
	}

	public void setDistance(String distance){
		this.distance = distance;
	}

	public String getDistance(){
		return distance;
	}

	public void setOutletDeliveryTiming(String outletDeliveryTiming){
		this.outletDeliveryTiming = outletDeliveryTiming;
	}

	public String getOutletDeliveryTiming(){
		return outletDeliveryTiming;
	}

	public void setOaOutletId(String oaOutletId){
		this.oaOutletId = oaOutletId;
	}

	public String getOaOutletId(){
		return oaOutletId;
	}

	public void setOutletPickupTat(String outletPickupTat){
		this.outletPickupTat = outletPickupTat;
	}

	public String getOutletPickupTat(){
		return outletPickupTat;
	}

	public void setOutletRouteId(String outletRouteId){
		this.outletRouteId = outletRouteId;
	}

	public String getOutletRouteId(){
		return outletRouteId;
	}

	public void setOutletName(String outletName){
		this.outletName = outletName;
	}

	public String getOutletName(){
		return outletName;
	}

	public void setOutletMarkerLatitude(String outletMarkerLatitude){
		this.outletMarkerLatitude = outletMarkerLatitude;
	}

	public String getOutletMarkerLatitude(){
		return outletMarkerLatitude;
	}

	public void setOutletUnitNumber1(String outletUnitNumber1){
		this.outletUnitNumber1 = outletUnitNumber1;
	}

	public String getOutletUnitNumber1(){
		return outletUnitNumber1;
	}

	public void setOutletPostalCode(String outletPostalCode){
		this.outletPostalCode = outletPostalCode;
	}

	public String getOutletPostalCode(){
		return outletPostalCode;
	}

	public void setOutletCloseTime(Object outletCloseTime){
		this.outletCloseTime = outletCloseTime;
	}

	public Object getOutletCloseTime(){
		return outletCloseTime;
	}

	public void setOutletDineTat(String outletDineTat){
		this.outletDineTat = outletDineTat;
	}

	public String getOutletDineTat(){
		return outletDineTat;
	}

	public void setOutletUnitNumber2(String outletUnitNumber2){
		this.outletUnitNumber2 = outletUnitNumber2;
	}

	public String getOutletUnitNumber2(){
		return outletUnitNumber2;
	}

	public void setOutletDeliveryTat(String outletDeliveryTat){
		this.outletDeliveryTat = outletDeliveryTat;
	}

	public String getOutletDeliveryTat(){
		return outletDeliveryTat;
	}

	public void setOutletMarkerLongitude(String outletMarkerLongitude){
		this.outletMarkerLongitude = outletMarkerLongitude;
	}

	public String getOutletMarkerLongitude(){
		return outletMarkerLongitude;
	}

	public void setOutletCustomerLevelEnable(String outletCustomerLevelEnable){
		this.outletCustomerLevelEnable = outletCustomerLevelEnable;
	}

	public String getOutletCustomerLevelEnable(){
		return outletCustomerLevelEnable;
	}

	public void setOutletPhone(String outletPhone){
		this.outletPhone = outletPhone;
	}

	public String getOutletPhone(){
		return outletPhone;
	}

	public void setOutletServiceCharge(String outletServiceCharge){
		this.outletServiceCharge = outletServiceCharge;
	}

	public String getOutletServiceCharge(){
		return outletServiceCharge;
	}

	public void setOutletCloseDate(String outletCloseDate){
		this.outletCloseDate = outletCloseDate;
	}

	public String getOutletCloseDate(){
		return outletCloseDate;
	}

	public void setOaAvailabilityId(String oaAvailabilityId){
		this.oaAvailabilityId = oaAvailabilityId;
	}

	public String getOaAvailabilityId(){
		return oaAvailabilityId;
	}

	public void setOutletId(String outletId){
		this.outletId = outletId;
	}

	public String getOutletId(){
		return outletId;
	}

	public void setOutletOpenDate(String outletOpenDate){
		this.outletOpenDate = outletOpenDate;
	}

	public String getOutletOpenDate(){
		return outletOpenDate;
	}

	public void setOutletAddressLine1(String outletAddressLine1){
		this.outletAddressLine1 = outletAddressLine1;
	}

	public String getOutletAddressLine1(){
		return outletAddressLine1;
	}

	public void setOutletAddressLine2(String outletAddressLine2){
		this.outletAddressLine2 = outletAddressLine2;
	}

	public String getOutletAddressLine2(){
		return outletAddressLine2;
	}

	public void setOutletDefalutValue(String outletDefalutValue){
		this.outletDefalutValue = outletDefalutValue;
	}

	public String getOutletDefalutValue(){
		return outletDefalutValue;
	}

	public void setOutletTiming(List<OutletTimingItem> outletTiming){
		this.outletTiming = outletTiming;
	}

	public List<OutletTimingItem> getOutletTiming(){
		return outletTiming;
	}

	public String getOperationHrs() {
		return operationHrs;
	}

	public void setOperationHrs(String operationHrs) {
		this.operationHrs = operationHrs;
	}

	public String getOutlet_image() {
		return outlet_image;
	}

	public void setOutlet_image(String outlet_image) {
		this.outlet_image = outlet_image;
	}
}