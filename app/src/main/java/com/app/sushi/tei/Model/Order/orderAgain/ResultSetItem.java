package com.app.sushi.tei.Model.Order.orderAgain;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class ResultSetItem implements Serializable {

	@SerializedName("order_outlet_id")
	private String orderOutletId;

	@SerializedName("order_created_on")
	private String orderCreatedOn;

	@SerializedName("outlet_unit_number2")
	private String outletUnitNumber2;

	@SerializedName("av_id")
	private String avId;

	@SerializedName("order_customer_fname")
	private String orderCustomerFname;

	@SerializedName("outlet_name")
	private String outletName;

	@SerializedName("order_method_name")
	private String orderMethodName;

	@SerializedName("order_customer_postal_code")
	private String orderCustomerPostalCode;

	@SerializedName("order_customer_city")
	private String orderCustomerCity;

	@SerializedName("av_name")
	private String avName;

	@SerializedName("order_customer_mobile_no")
	private String orderCustomerMobileNo;

	@SerializedName("order_customer_email")
	private String orderCustomerEmail;

	@SerializedName("order_customer_unit_no1")
	private String orderCustomerUnitNo1;

	@SerializedName("order_customer_unit_no2")
	private String orderCustomerUnitNo2;

	@SerializedName("outlet_unit_number1")
	private String outletUnitNumber1;

	@SerializedName("order_customer_address_line1")
	private String orderCustomerAddressLine1;

	@SerializedName("outlet_postal_code")
	private String outletPostalCode;

	@SerializedName("order_customer_address_line2")
	private String orderCustomerAddressLine2;

	@SerializedName("order_customer_lname")
	private String orderCustomerLname;

	@SerializedName("outlet_address_line1")
	private String outletAddressLine1;

	@SerializedName("order_table_number")
	private String orderTableNumber;

	@SerializedName("outlet_address_line2")
	private String outletAddressLine2;

	public void setOrderOutletId(String orderOutletId){
		this.orderOutletId = orderOutletId;
	}

	public String getOrderOutletId(){
		return orderOutletId;
	}

	public void setOrderCreatedOn(String orderCreatedOn){
		this.orderCreatedOn = orderCreatedOn;
	}

	public String getOrderCreatedOn(){
		return orderCreatedOn;
	}

	public void setOutletUnitNumber2(String outletUnitNumber2){
		this.outletUnitNumber2 = outletUnitNumber2;
	}

	public String getOutletUnitNumber2(){
		return outletUnitNumber2;
	}

	public void setAvId(String avId){
		this.avId = avId;
	}

	public String getAvId(){
		return avId;
	}

	public void setOrderCustomerFname(String orderCustomerFname){
		this.orderCustomerFname = orderCustomerFname;
	}

	public String getOrderCustomerFname(){
		return orderCustomerFname;
	}

	public void setOutletName(String outletName){
		this.outletName = outletName;
	}

	public String getOutletName(){
		return outletName;
	}

	public void setOrderMethodName(String orderMethodName){
		this.orderMethodName = orderMethodName;
	}

	public String getOrderMethodName(){
		return orderMethodName;
	}

	public void setOrderCustomerPostalCode(String orderCustomerPostalCode){
		this.orderCustomerPostalCode = orderCustomerPostalCode;
	}

	public String getOrderCustomerPostalCode(){
		return orderCustomerPostalCode;
	}

	public void setOrderCustomerCity(String orderCustomerCity){
		this.orderCustomerCity = orderCustomerCity;
	}

	public String getOrderCustomerCity(){
		return orderCustomerCity;
	}

	public void setAvName(String avName){
		this.avName = avName;
	}

	public String getAvName(){
		return avName;
	}

	public void setOrderCustomerMobileNo(String orderCustomerMobileNo){
		this.orderCustomerMobileNo = orderCustomerMobileNo;
	}

	public String getOrderCustomerMobileNo(){
		return orderCustomerMobileNo;
	}

	public void setOrderCustomerEmail(String orderCustomerEmail){
		this.orderCustomerEmail = orderCustomerEmail;
	}

	public String getOrderCustomerEmail(){
		return orderCustomerEmail;
	}

	public void setOrderCustomerUnitNo1(String orderCustomerUnitNo1){
		this.orderCustomerUnitNo1 = orderCustomerUnitNo1;
	}

	public String getOrderCustomerUnitNo1(){
		return orderCustomerUnitNo1;
	}

	public void setOrderCustomerUnitNo2(String orderCustomerUnitNo2){
		this.orderCustomerUnitNo2 = orderCustomerUnitNo2;
	}

	public String getOrderCustomerUnitNo2(){
		return orderCustomerUnitNo2;
	}

	public void setOutletUnitNumber1(String outletUnitNumber1){
		this.outletUnitNumber1 = outletUnitNumber1;
	}

	public String getOutletUnitNumber1(){
		return outletUnitNumber1;
	}

	public void setOrderCustomerAddressLine1(String orderCustomerAddressLine1){
		this.orderCustomerAddressLine1 = orderCustomerAddressLine1;
	}

	public String getOrderCustomerAddressLine1(){
		return orderCustomerAddressLine1;
	}

	public void setOutletPostalCode(String outletPostalCode){
		this.outletPostalCode = outletPostalCode;
	}

	public String getOutletPostalCode(){
		return outletPostalCode;
	}

	public void setOrderCustomerAddressLine2(String orderCustomerAddressLine2){
		this.orderCustomerAddressLine2 = orderCustomerAddressLine2;
	}

	public String getOrderCustomerAddressLine2(){
		return orderCustomerAddressLine2;
	}

	public void setOrderCustomerLname(String orderCustomerLname){
		this.orderCustomerLname = orderCustomerLname;
	}

	public String getOrderCustomerLname(){
		return orderCustomerLname;
	}

	public void setOutletAddressLine1(String outletAddressLine1){
		this.outletAddressLine1 = outletAddressLine1;
	}

	public String getOutletAddressLine1(){
		return outletAddressLine1;
	}

	public void setOrderTableNumber(String orderTableNumber){
		this.orderTableNumber = orderTableNumber;
	}

	public String getOrderTableNumber(){
		return orderTableNumber;
	}

	public void setOutletAddressLine2(String outletAddressLine2){
		this.outletAddressLine2 = outletAddressLine2;
	}

	public String getOutletAddressLine2(){
		return outletAddressLine2;
	}
}