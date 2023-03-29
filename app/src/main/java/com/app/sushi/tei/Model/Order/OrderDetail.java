package com.app.sushi.tei.Model.Order;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

/**
 * Created by root on 31/8/16.
 */
public class OrderDetail implements Parcelable {


    private String outlet_name;
    private String outlet_address="";
    private String outlet_pincode="";
    private String status_name;
    private String callcenter_admin_name;
    private String order_method_name;
    private String order_promocode_name;
    private String unitNo="";

    private String eWalletAmount;

    private String order_table_number;

    private String order_customer_fname;
    private String order_customer_lname;
    private String order_customer_email;
    private String order_customer_mobile_no;
    private String order_customer_address_line1;
    private String order_customer_address_line2;
    private String order_customer_city;
    private String order_customer_state;
    private String order_customer_country;
    private String order_customer_postal_code;
    private String order_customer_unit_no1;
    private String order_customer_unit_no2;

    private String order_primary_id;
    private String order_id;
    private String order_local_no;
    private String order_outlet_id;
    private String order_delivery_charge;
    private String order_tax_charge;
    private String order_tax_calculate_amount;
    private String order_discount_applied;
    private String order_discount_amount;
    private String order_sub_total;
    private String order_total_amount;
    private String order_payment_mode;
    private String order_date;

    private String order_status;
    private String order_availability_id;
    private String order_availability_name;
    private String order_pickup_time;
    private String order_pickup_outlet_id;
    private String order_source;
    private String order_callcenter_admin_id;

    private String order_cancel_source;
    private String order_cancel_by;
    private String order_cancel_remark;
    private String order_tat_time;
    private String order_discount_type;
    private String order_delivery_waver;
    private String order_pickup_time_slot_from;
    private String order_pickup_time_slot_to;

    private String order_created_date="";


    private String order_additional_delivery;

    private String order_remarks;

    //private String order_closed_status;

    private String order_used_new_api;
    private String order_payment_retrieved;
    private String order_discount_both="";
    private String order_points_discount_amount;


    private JSONArray items_json_array_string;

    private List<ItemResponse> items;
    private String order_qNumber;
    private String operational_hr;
    private String order_someone_remarks="";
    private String order_voucher_discount_amount;

    private String order_is_timeslot="";
    //    private String order_pickup_time_slot_from;
//    private String order_pickup_time_slot_to;
    private String order_delivery_time_slot_from;
    private String order_delivery_time_slot_to;
    private String order_packaging_charge;

    public String getOrder_packaging_charge() {
        return order_packaging_charge;
    }

    public void setOrder_packaging_charge(String order_packaging_charge) {
        this.order_packaging_charge = order_packaging_charge;
    }

    public String geteWalletAmount() {
        return eWalletAmount;
    }

    public void seteWalletAmount(String eWalletAmount) {
        this.eWalletAmount = eWalletAmount;
    }

    public String getOrder_promocode_name() {
        return order_promocode_name;
    }

    public void setOrder_promocode_name(String order_promocode_name) {
        this.order_promocode_name = order_promocode_name;
    }

    public String getOrder_payment_retrieved() {
        return order_payment_retrieved;
    }

    public void setOrder_payment_retrieved(String order_payment_retrieved) {
        this.order_payment_retrieved = order_payment_retrieved;
    }

    public String getOrder_qNumber() {
        return order_qNumber;
    }

    public void setOrder_qNumber(String order_qNumber) {
        this.order_qNumber = order_qNumber;
    }

    public static Creator<OrderDetail> getCREATOR() {
        return CREATOR;
    }

    public String getOrder_customer_unit_no1() {
        return order_customer_unit_no1;
    }

    public void setOrder_customer_unit_no1(String order_customer_unit_no1) {
        this.order_customer_unit_no1 = order_customer_unit_no1;
    }

    public String getOrder_customer_unit_no2() {
        return order_customer_unit_no2;
    }

    public void setOrder_customer_unit_no2(String order_customer_unit_no2) {
        this.order_customer_unit_no2 = order_customer_unit_no2;
    }

    public String getOutlet_address() {
        return outlet_address;
    }

    public void setOutlet_address(String outlet_address) {
        this.outlet_address = outlet_address;
    }

    public String getOutlet_pincode() {
        return outlet_pincode;
    }

    public void setOutlet_pincode(String outlet_pincode) {
        this.outlet_pincode = outlet_pincode;
    }

    public String getUnitNo() {
        return unitNo;
    }

    public void setUnitNo(String unitNo) {
        this.unitNo = unitNo;
    }

    public String getOrder_payment_retrived() {
        return order_payment_retrieved;
    }

    public void setOrder_payment_retrived(String order_payment_retrived) {
        this.order_payment_retrieved = order_payment_retrived;
    }

    public String getOrder_used_new_api() {
        return order_used_new_api;
    }

    public void setOrder_used_new_api(String order_used_new_api) {
        this.order_used_new_api = order_used_new_api;
    }

 /*   public String getOrder_closed_status() {
        return order_closed_status;
    }

    public void setOrder_closed_status(String order_closed_status) {
        this.order_closed_status = order_closed_status;
    }*/

    public String getOrder_created_date() {
        return order_created_date;
    }

    public void setOrder_created_date(String order_created_date) {
        this.order_created_date = order_created_date;
    }

    public String getOrder_remarks() {
        return order_remarks;
    }

    public void setOrder_remarks(String order_remarks) {
        this.order_remarks = order_remarks;
    }

    public String getOrder_additional_delivery() {
        return order_additional_delivery;
    }

    public void setOrder_additional_delivery(String order_additional_delivery) {
        this.order_additional_delivery = order_additional_delivery;
    }

    public String getOrder_table_number() {
        return order_table_number;
    }

    public void setOrder_table_number(String order_table_number) {
        this.order_table_number = order_table_number;
    }

    public JSONArray getItems_json_array_string() {
        return items_json_array_string;
    }

    public void setItems_json_array_string(JSONArray items_json_array_string) {
        this.items_json_array_string = items_json_array_string;
    }

    public String getOutlet_name() {
        return outlet_name;
    }

    public void setOutlet_name(String outlet_name) {
        this.outlet_name = outlet_name;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getCallcenter_admin_name() {
        return callcenter_admin_name;
    }

    public void setCallcenter_admin_name(String callcenter_admin_name) {
        this.callcenter_admin_name = callcenter_admin_name;
    }

    public String getOrder_method_name() {
        return order_method_name;
    }

    public void setOrder_method_name(String order_method_name) {
        this.order_method_name = order_method_name;
    }

    public String getOrder_tax_calculate_amount() {
        return order_tax_calculate_amount;
    }

    public void setOrder_tax_calculate_amount(String order_tax_calculate_amount) {
        this.order_tax_calculate_amount = order_tax_calculate_amount;
    }

    public String getOrder_customer_fname() {
        return order_customer_fname;
    }

    public void setOrder_customer_fname(String order_customer_fname) {
        this.order_customer_fname = order_customer_fname;
    }

    public String getOrder_customer_lname() {
        return order_customer_lname;
    }

    public void setOrder_customer_lname(String order_customer_lname) {
        this.order_customer_lname = order_customer_lname;
    }

    public String getOrder_customer_email() {
        return order_customer_email;
    }

    public void setOrder_customer_email(String order_customer_email) {
        this.order_customer_email = order_customer_email;
    }

    public String getOrder_customer_mobile_no() {
        return order_customer_mobile_no;
    }

    public void setOrder_customer_mobile_no(String order_customer_mobile_no) {
        this.order_customer_mobile_no = order_customer_mobile_no;
    }

    public String getOrder_customer_address_line1() {
        return order_customer_address_line1;
    }

    public void setOrder_customer_address_line1(String order_customer_address_line1) {
        this.order_customer_address_line1 = order_customer_address_line1;
    }

    public String getOrder_customer_address_line2() {
        return order_customer_address_line2;
    }

    public void setOrder_customer_address_line2(String order_customer_address_line2) {
        this.order_customer_address_line2 = order_customer_address_line2;
    }

    public String getOrder_customer_city() {
        return order_customer_city;
    }

    public void setOrder_customer_city(String order_customer_city) {
        this.order_customer_city = order_customer_city;
    }

    public String getOrder_customer_state() {
        return order_customer_state;
    }

    public void setOrder_customer_state(String order_customer_state) {
        this.order_customer_state = order_customer_state;
    }

    public String getOrder_customer_country() {
        return order_customer_country;
    }

    public void setOrder_customer_country(String order_customer_country) {
        this.order_customer_country = order_customer_country;
    }

    public String getOrder_customer_postal_code() {
        return order_customer_postal_code;
    }

    public void setOrder_customer_postal_code(String order_customer_postal_code) {
        this.order_customer_postal_code = order_customer_postal_code;
    }

    public String getOrder_primary_id() {
        return order_primary_id;
    }

    public void setOrder_primary_id(String order_primary_id) {
        this.order_primary_id = order_primary_id;
    }

    public String getOrder_id() {
        return order_id;
    }

    public void setOrder_id(String order_id) {
        this.order_id = order_id;
    }

    public String getOrder_local_no() {
        return order_local_no;
    }

    public void setOrder_local_no(String order_local_no) {
        this.order_local_no = order_local_no;
    }

    public String getOrder_outlet_id() {
        return order_outlet_id;
    }

    public void setOrder_outlet_id(String order_outlet_id) {
        this.order_outlet_id = order_outlet_id;
    }

    public String getOrder_delivery_charge() {
        return order_delivery_charge;
    }

    public void setOrder_delivery_charge(String order_delivery_charge) {
        this.order_delivery_charge = order_delivery_charge;
    }

    public String getOrder_tax_charge() {
        return order_tax_charge;
    }

    public void setOrder_tax_charge(String order_tax_charge) {
        this.order_tax_charge = order_tax_charge;
    }

    public String getOrder_discount_applied() {
        return order_discount_applied;
    }

    public void setOrder_discount_applied(String order_discount_applied) {
        this.order_discount_applied = order_discount_applied;
    }

    public String getOrder_discount_amount() {
        return order_discount_amount;
    }

    public void setOrder_discount_amount(String order_discount_amount) {
        this.order_discount_amount = order_discount_amount;
    }

    public String getOrder_sub_total() {
        return order_sub_total;
    }

    public void setOrder_sub_total(String order_sub_total) {
        this.order_sub_total = order_sub_total;
    }

    public String getOrder_total_amount() {
        return order_total_amount;
    }

    public void setOrder_total_amount(String order_total_amount) {
        this.order_total_amount = order_total_amount;
    }

    public String getOrder_payment_mode() {
        return order_payment_mode;
    }

    public void setOrder_payment_mode(String order_payment_mode) {
        this.order_payment_mode = order_payment_mode;
    }

    public String getOrder_date() {
        return order_date;
    }

    public void setOrder_date(String order_date) {
        this.order_date = order_date;
    }

    public String getOrder_status() {
        return order_status;
    }

    public void setOrder_status(String order_status) {
        this.order_status = order_status;
    }

    public String getOrder_availability_id() {
        return order_availability_id;
    }

    public void setOrder_availability_id(String order_availability_id) {
        this.order_availability_id = order_availability_id;
    }

    public String getOrder_availability_name() {
        return order_availability_name;
    }

    public void setOrder_availability_name(String order_availability_name) {
        this.order_availability_name = order_availability_name;
    }

    public String getOrder_pickup_time() {
        return order_pickup_time;
    }

    public void setOrder_pickup_time(String order_pickup_time) {
        this.order_pickup_time = order_pickup_time;
    }

    public String getOrder_pickup_outlet_id() {
        return order_pickup_outlet_id;
    }

    public void setOrder_pickup_outlet_id(String order_pickup_outlet_id) {
        this.order_pickup_outlet_id = order_pickup_outlet_id;
    }

    public String getOrder_source() {
        return order_source;
    }

    public void setOrder_source(String order_source) {
        this.order_source = order_source;
    }

    public String getOrder_callcenter_admin_id() {
        return order_callcenter_admin_id;
    }

    public void setOrder_callcenter_admin_id(String order_callcenter_admin_id) {
        this.order_callcenter_admin_id = order_callcenter_admin_id;
    }

    public String getOrder_cancel_source() {
        return order_cancel_source;
    }

    public void setOrder_cancel_source(String order_cancel_source) {
        this.order_cancel_source = order_cancel_source;
    }

    public String getOrder_cancel_by() {
        return order_cancel_by;
    }

    public void setOrder_cancel_by(String order_cancel_by) {
        this.order_cancel_by = order_cancel_by;
    }

    public String getOrder_cancel_remark() {
        return order_cancel_remark;
    }

    public void setOrder_cancel_remark(String order_cancel_remark) {
        this.order_cancel_remark = order_cancel_remark;
    }

    public String getOrder_tat_time() {
        return order_tat_time;
    }

    public void setOrder_tat_time(String order_tat_time) {
        this.order_tat_time = order_tat_time;
    }

    public String getOrder_discount_type() {
        return order_discount_type;
    }

    public void setOrder_discount_type(String order_discount_type) {
        this.order_discount_type = order_discount_type;
    }

    public String getOrder_delivery_waver() {
        return order_delivery_waver;
    }

    public void setOrder_delivery_waver(String order_delivery_waver) {
        this.order_delivery_waver = order_delivery_waver;
    }

    public String getOrder_pickup_time_slot_from() {
        return order_pickup_time_slot_from;
    }

    public void setOrder_pickup_time_slot_from(String order_pickup_time_slot_from) {
        this.order_pickup_time_slot_from = order_pickup_time_slot_from;
    }

    public String getOrder_pickup_time_slot_to() {
        return order_pickup_time_slot_to;
    }

    public void setOrder_pickup_time_slot_to(String order_pickup_time_slot_to) {
        this.order_pickup_time_slot_to = order_pickup_time_slot_to;
    }

    public String getOrder_discount_both() {
        return order_discount_both;
    }

    public void setOrder_discount_both(String order_discount_both) {
        this.order_discount_both = order_discount_both;
    }

    public String getOrder_points_discount_amount() {
        return order_points_discount_amount;
    }

    public void setOrder_points_discount_amount(String order_points_discount_amount) {
        this.order_points_discount_amount = order_points_discount_amount;
    }

    public String getOperational_hr() {
        return operational_hr;
    }

    public void setOperational_hr(String operational_hr) {
        this.operational_hr = operational_hr;
    }

    public String getOrder_someone_remarks() {
        return order_someone_remarks;
    }

    public void setOrder_someone_remarks(String order_someone_remarks) {
        this.order_someone_remarks = order_someone_remarks;
    }

    public String getOrder_voucher_discount_amount() {
        return order_voucher_discount_amount;
    }

    public void setOrder_voucher_discount_amount(String order_voucher_discount_amount) {
        this.order_voucher_discount_amount = order_voucher_discount_amount;
    }

    public OrderDetail() {

    }

    public String getOrder_is_timeslot() {
        return order_is_timeslot;
    }

    public void setOrder_is_timeslot(String order_is_timeslot) {
        this.order_is_timeslot = order_is_timeslot;
    }

    public String getOrder_delivery_time_slot_from() {
        return order_delivery_time_slot_from;
    }

    public void setOrder_delivery_time_slot_from(String order_delivery_time_slot_from) {
        this.order_delivery_time_slot_from = order_delivery_time_slot_from;
    }

    public String getOrder_delivery_time_slot_to() {
        return order_delivery_time_slot_to;
    }

    public void setOrder_delivery_time_slot_to(String order_delivery_time_slot_to) {
        this.order_delivery_time_slot_to = order_delivery_time_slot_to;
    }

    protected OrderDetail(Parcel in) {
        outlet_name = in.readString();
        status_name = in.readString();
        callcenter_admin_name = in.readString();
        order_method_name = in.readString();
        order_table_number = in.readString();
        order_customer_fname = in.readString();
        order_customer_lname = in.readString();
        order_customer_email = in.readString();
        order_customer_mobile_no = in.readString();
        order_customer_address_line1 = in.readString();
        order_customer_address_line2 = in.readString();
        order_customer_city = in.readString();
        order_customer_state = in.readString();
        order_customer_country = in.readString();
        order_customer_postal_code = in.readString();
        order_primary_id = in.readString();
        order_id = in.readString();
        order_local_no = in.readString();
        order_outlet_id = in.readString();
        order_delivery_charge = in.readString();
        order_tax_charge = in.readString();
        order_tax_calculate_amount = in.readString();
        order_discount_applied = in.readString();
        order_discount_amount = in.readString();
        order_sub_total = in.readString();
        order_total_amount = in.readString();
        order_payment_mode = in.readString();
        order_date = in.readString();
        order_status = in.readString();
        order_availability_id = in.readString();
        order_availability_name = in.readString();
        order_pickup_time = in.readString();
        order_pickup_outlet_id = in.readString();
        order_source = in.readString();
        order_callcenter_admin_id = in.readString();
        order_cancel_source = in.readString();
        order_cancel_by = in.readString();
        order_cancel_remark = in.readString();
        order_tat_time = in.readString();
        order_discount_type = in.readString();
        order_delivery_waver = in.readString();
        order_pickup_time_slot_from = in.readString();
        order_pickup_time_slot_to = in.readString();
        order_discount_both = in.readString();
        order_points_discount_amount = in.readString();
        order_additional_delivery = in.readString();
        order_remarks = in.readString();
        order_used_new_api= in.readString();
        order_promocode_name = in.readString();
        eWalletAmount = in.readString();
        order_payment_retrieved = in.readString();
        order_created_date= in.readString();
        outlet_address= in.readString();
        outlet_pincode= in.readString();
        order_customer_unit_no1 = in.readString();
        order_customer_unit_no2=in.readString();
        order_qNumber = in.readString();
        operational_hr = in.readString();
        order_someone_remarks = in.readString();
        order_voucher_discount_amount = in.readString();

        order_is_timeslot = in.readString();
        order_pickup_time_slot_from = in.readString();
        order_pickup_time_slot_to = in.readString();
        order_delivery_time_slot_from = in.readString();
        order_delivery_time_slot_to = in.readString();


        this.items = in.createTypedArrayList(ItemResponse.CREATOR);


        //order_closed_status= in.readString();
        try {
            items_json_array_string = in.readByte() == 0x00 ? null : new JSONArray(in.readString());
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(outlet_name);
        dest.writeString(status_name);
        dest.writeString(callcenter_admin_name);
        dest.writeString(order_method_name);
        dest.writeString(order_table_number);
        dest.writeString(order_customer_fname);
        dest.writeString(order_customer_lname);
        dest.writeString(order_customer_email);
        dest.writeString(order_customer_mobile_no);
        dest.writeString(order_customer_address_line1);
        dest.writeString(order_customer_address_line2);
        dest.writeString(order_customer_city);
        dest.writeString(order_customer_state);
        dest.writeString(order_customer_country);
        dest.writeString(order_customer_postal_code);
        dest.writeString(order_primary_id);
        dest.writeString(order_id);
        dest.writeString(order_local_no);
        dest.writeString(order_outlet_id);
        dest.writeString(order_delivery_charge);
        dest.writeString(order_tax_charge);
        dest.writeString(order_tax_calculate_amount);
        dest.writeString(order_discount_applied);
        dest.writeString(order_discount_amount);
        dest.writeString(order_sub_total);
        dest.writeString(order_total_amount);
        dest.writeString(order_payment_mode);
        dest.writeString(order_date);
        dest.writeString(order_status);
        dest.writeString(order_availability_id);
        dest.writeString(order_availability_name);
        dest.writeString(order_pickup_time);
        dest.writeString(order_pickup_outlet_id);
        dest.writeString(order_source);
        dest.writeString(order_callcenter_admin_id);
        dest.writeString(order_cancel_source);
        dest.writeString(order_cancel_by);
        dest.writeString(order_cancel_remark);
        dest.writeString(order_tat_time);
        dest.writeString(order_discount_type);
        dest.writeString(order_delivery_waver);
        dest.writeString(order_pickup_time_slot_from);
        dest.writeString(order_pickup_time_slot_to);
        dest.writeString(order_discount_both);
        dest.writeString(order_points_discount_amount);
        dest.writeString(order_additional_delivery);
        dest.writeString(order_remarks);
        dest.writeString(order_used_new_api);
        dest.writeString(order_promocode_name);
        dest.writeString(eWalletAmount);
        dest.writeString(order_payment_retrieved);
        dest.writeString(order_created_date);
        dest.writeString(outlet_address);
        dest.writeString(outlet_pincode);
        dest.writeString(order_customer_unit_no1);
        dest.writeString(order_customer_unit_no2);
        dest.writeString(order_qNumber);
        dest.writeString(operational_hr);
        dest.writeString(order_someone_remarks);
        dest.writeString(order_voucher_discount_amount);


        dest.writeString(order_is_timeslot);
        dest.writeString(order_pickup_time_slot_from);
        dest.writeString(order_pickup_time_slot_to);
        dest.writeString(order_delivery_time_slot_from);
        dest.writeString(order_delivery_time_slot_to);

        dest.writeTypedList(this.items);


//        dest.writeString(order_closed_status);

        if (items_json_array_string == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeString(items_json_array_string.toString());
        }
    }

    @SuppressWarnings("unused")
    public static final Creator<OrderDetail> CREATOR = new Creator<OrderDetail>() {
        @Override
        public OrderDetail createFromParcel(Parcel in) {
            return new OrderDetail(in);
        }

        @Override
        public OrderDetail[] newArray(int size) {
            return new OrderDetail[size];
        }
    };

    public List<ItemResponse> getItems() {
        return items;
    }

    public void setItems(List<ItemResponse> items) {
        this.items = items;
    }


}