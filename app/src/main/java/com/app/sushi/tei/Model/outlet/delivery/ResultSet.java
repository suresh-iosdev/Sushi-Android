package com.app.sushi.tei.Model.outlet.delivery;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResultSet implements Serializable {

	@SerializedName("outlet_open_time")
	private Object outletOpenTime;

	@SerializedName("distance")
	private double distance;

	@SerializedName("outlet_delivery_timing")
	private String outletDeliveryTiming;

	@SerializedName("outlet_pickup_tat")
	private String outletPickupTat;

	@SerializedName("outlet_route_id")
	private String outletRouteId;

	@SerializedName("zone_name")
	private String zoneName;

	@SerializedName("outlet_name")
	private String outletName;

	@SerializedName("zone_delivery_charge")
	private String zoneDeliveryCharge;

	@SerializedName("postal_code_information")
	private PostalCodeInformation postalCodeInformation;

	@SerializedName("route_for")
	private String routeFor;

	@SerializedName("zone_id")
	private String zoneId;

	@SerializedName("outlet_unit_number1")
	private String outletUnitNumber1;

	@SerializedName("outlet_postal_code")
	private String outletPostalCode;

	@SerializedName("outlet_close_time")
	private Object outletCloseTime;

	@SerializedName("outlet_dine_tat")
	private String outletDineTat;

	@SerializedName("zone_postal_code")
	private String zonePostalCode;

	@SerializedName("zone_free_delivery")
	private String zoneFreeDelivery;

	@SerializedName("zone_min_amount")
	private String zoneMinAmount;

	@SerializedName("outlet_unit_number2")
	private String outletUnitNumber2;

	@SerializedName("outlet_delivery_tat")
	private String outletDeliveryTat;

	@SerializedName("posatl_code_lat_lang")
	private String posatlCodeLatLang;

	@SerializedName("is_route")
	private String isRoute;

	@SerializedName("outlet_close_date")
	private String outletCloseDate;

	@SerializedName("outlet_id")
	private String outletId;

	@SerializedName("zone_additional_delivery_charge")
	private String zoneAdditionalDeliveryCharge;

	@SerializedName("outlet_open_date")
	private String outletOpenDate;

	@SerializedName("outlet_address_line1")
	private String outletAddressLine1;

	@SerializedName("outlet_address_line2")
	private String outletAddressLine2;

	@SerializedName("zone_address_line1")
	private String zoneAddressLine1;

	public void setOutletOpenTime(Object outletOpenTime){
		this.outletOpenTime = outletOpenTime;
	}

	public Object getOutletOpenTime(){
		return outletOpenTime;
	}

	public void setDistance(double distance){
		this.distance = distance;
	}

	public double getDistance(){
		return distance;
	}

	public void setOutletDeliveryTiming(String outletDeliveryTiming){
		this.outletDeliveryTiming = outletDeliveryTiming;
	}

	public String getOutletDeliveryTiming(){
		return outletDeliveryTiming;
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

	public void setZoneName(String zoneName){
		this.zoneName = zoneName;
	}

	public String getZoneName(){
		return zoneName;
	}

	public void setOutletName(String outletName){
		this.outletName = outletName;
	}

	public String getOutletName(){
		return outletName;
	}

	public void setZoneDeliveryCharge(String zoneDeliveryCharge){
		this.zoneDeliveryCharge = zoneDeliveryCharge;
	}

	public String getZoneDeliveryCharge(){
		return zoneDeliveryCharge;
	}

	public void setPostalCodeInformation(PostalCodeInformation postalCodeInformation){
		this.postalCodeInformation = postalCodeInformation;
	}

	public PostalCodeInformation getPostalCodeInformation(){
		return postalCodeInformation;
	}

	public void setRouteFor(String routeFor){
		this.routeFor = routeFor;
	}

	public String getRouteFor(){
		return routeFor;
	}

	public void setZoneId(String zoneId){
		this.zoneId = zoneId;
	}

	public String getZoneId(){
		return zoneId;
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

	public void setZonePostalCode(String zonePostalCode){
		this.zonePostalCode = zonePostalCode;
	}

	public String getZonePostalCode(){
		return zonePostalCode;
	}

	public void setZoneFreeDelivery(String zoneFreeDelivery){
		this.zoneFreeDelivery = zoneFreeDelivery;
	}

	public String getZoneFreeDelivery(){
		return zoneFreeDelivery;
	}

	public void setZoneMinAmount(String zoneMinAmount){
		this.zoneMinAmount = zoneMinAmount;
	}

	public String getZoneMinAmount(){
		return zoneMinAmount;
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

	public void setPosatlCodeLatLang(String posatlCodeLatLang){
		this.posatlCodeLatLang = posatlCodeLatLang;
	}

	public String getPosatlCodeLatLang(){
		return posatlCodeLatLang;
	}

	public void setIsRoute(String isRoute){
		this.isRoute = isRoute;
	}

	public String getIsRoute(){
		return isRoute;
	}

	public void setOutletCloseDate(String outletCloseDate){
		this.outletCloseDate = outletCloseDate;
	}

	public String getOutletCloseDate(){
		return outletCloseDate;
	}

	public void setOutletId(String outletId){
		this.outletId = outletId;
	}

	public String getOutletId(){
		return outletId;
	}

	public void setZoneAdditionalDeliveryCharge(String zoneAdditionalDeliveryCharge){
		this.zoneAdditionalDeliveryCharge = zoneAdditionalDeliveryCharge;
	}

	public String getZoneAdditionalDeliveryCharge(){
		return zoneAdditionalDeliveryCharge;
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

	public void setZoneAddressLine1(String zoneAddressLine1){
		this.zoneAddressLine1 = zoneAddressLine1;
	}

	public String getZoneAddressLine1(){
		return zoneAddressLine1;
	}
}