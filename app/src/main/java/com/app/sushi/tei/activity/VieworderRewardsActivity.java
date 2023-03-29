package com.app.sushi.tei.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Order.ItemResponse;
import com.app.sushi.tei.Model.Order.OrderDetail;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Order.ViewrewardsAdaptersnew;

import org.json.JSONArray;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


/**
 * Created by gowtham on 25/4/18.
 */

public class VieworderRewardsActivity extends Activity {
    OrderDetail orderDetail;


    ImageView img_close;
    private RelativeLayout layoutSendMail,layoutpackingcharge;
    TextView tv_order_date, tv_order_time, tv_order_no, textView,
            tv_order_placed_at, tv_order_pay_by, tv_order_status, includeheadersub, tv_delivery_date, tv_edelivery,
            tv_sub_total_cost, tv_total_cost, deliverytext_cost, tv_gst, gstvaluetextview, txtUnitNo, tv_name1,
            tv_name, tv_handler_name, tv_address, tv_edelivery_time, tv_tax_cost, tv_delivery_date_time,
            discountLeftTextView, discountTextView, txtOutletAddress,
            txtDiscountLabel, txtDiscountTotalVouchers, txtGstLabel,tv_q_number,txtpacking_charge;


    RecyclerView recyler_view_ordered_products;
    LinearLayout layoutDelivery, layoutTakeaway;

    RelativeLayout rel_delivery, rel_promotion_charges, linear_dine,layoutdiscountVouchers;

    TextView tv_yours, order_type, txtorder_table_number;
    private double discountapplied_sub_total, discountapplied_grand_total;
    private RelativeLayout layoutdiscount, layoutAdditionalDeliveryCharge;
    private TextView txtDiscountTotal, txtAdditionalDeliveryCharge, txtGSTLabel, txtGST,txt_insulsive_gst1;

    private String mOrderNo = "";
    private TextView includeheader;
    List<OrderDetail> ordersArrayList;
    private TextView methodName;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_detail_new);

        this.setFinishOnTouchOutside(false);

        //layoutGST = (RelativeLayout) findViewById(R.id.layoutGST);
        layoutdiscount = (RelativeLayout) findViewById(R.id.layoutdiscount);
        txtDiscountTotal = (TextView) findViewById(R.id.txtDiscountTotal);
        includeheader = (TextView) findViewById(R.id.includeheader);

        layoutAdditionalDeliveryCharge = (RelativeLayout) findViewById(R.id.layoutAdditionalDeliveryCharge);
        txtAdditionalDeliveryCharge = (TextView) findViewById(R.id.txtAdditionalDeliveryCharge);
        textView = (TextView) findViewById(R.id.textView);
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
        layoutSendMail = (RelativeLayout) findViewById(R.id.layoutSendMail);
        layoutpackingcharge=findViewById(R.id.layoutpackingcharge);
        gstvaluetextview = (TextView) findViewById(R.id.gstvaluetextview);
        txtOutletAddress = (TextView) findViewById(R.id.txtOutletAddress);
        txtDiscountLabel = (TextView) findViewById(R.id.txtDiscountLabel);
        layoutdiscountVouchers = findViewById(R.id.layoutdiscountVouchers);
        txtDiscountTotalVouchers = findViewById(R.id.txtDiscountTotalVouchers);
        tv_q_number = findViewById(R.id.tv_q_number);
        txtpacking_charge=findViewById(R.id.txtpacking_charge);
        txtGstLabel = findViewById(R.id.txtGstLabel);
        tv_yours = (TextView) findViewById(R.id.tv_yours);
        txtUnitNo = (TextView) findViewById(R.id.txtUnitNo);
        txtGST = (TextView) findViewById(R.id.txtGST);
        txt_insulsive_gst1=findViewById(R.id.txt_insulsive_gst1);
        txtGSTLabel = (TextView) findViewById(R.id.txtGSTLabel);
        methodName = (TextView) findViewById(R.id.methodName);

        order_type = (TextView) findViewById(R.id.order_type);

        txtorder_table_number = (TextView) findViewById(R.id.order_table_number);
        tv_edelivery = (TextView) findViewById(R.id.tv_edelivery);

        img_close = (ImageView) findViewById(R.id.img_close);

        includeheadersub = (TextView) findViewById(R.id.includeheadersub);

        linear_dine = (RelativeLayout) findViewById(R.id.linear_dine);

        tv_order_date = (TextView) findViewById(R.id.tv_order_date);
        tv_order_time = (TextView) findViewById(R.id.tv_order_time);

        rel_promotion_charges = (RelativeLayout) findViewById(R.id.rel_promotion_charges);

        tv_gst = (TextView) findViewById(R.id.tv_gst);

        discountLeftTextView = (TextView) findViewById(R.id.discountLeftTextView);
        discountTextView = (TextView) findViewById(R.id.discountTextView);

        tv_order_no = (TextView) findViewById(R.id.tv_order_no);
        tv_handler_name = (TextView) findViewById(R.id.tv_handler_name);
        tv_address = (TextView) findViewById(R.id.tv_address);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_name1 = (TextView) findViewById(R.id.tv_name1);
        tv_edelivery_time = (TextView) findViewById(R.id.tv_edelivery_time);

        deliverytext_cost = (TextView) findViewById(R.id.deliverytext_cost);
        tv_order_status = (TextView) findViewById(R.id.tv_order_status);
        tv_order_placed_at = (TextView) findViewById(R.id.tv_order_placed_at);

        tv_delivery_date_time = (TextView) findViewById(R.id.tv_delivery_date_time);

        tv_delivery_date = (TextView) findViewById(R.id.tv_delivery_date);

        recyler_view_ordered_products = (RecyclerView) findViewById(R.id.recyler_view_ordered_products);
        tv_order_pay_by = (TextView) findViewById(R.id.tv_order_pay_by);

        tv_sub_total_cost = (TextView) findViewById(R.id.tv_sub_total_cost);

        tv_total_cost = (TextView) findViewById(R.id.tv_total_cost);

        rel_delivery = (RelativeLayout) findViewById(R.id.rel_delivery);

        layoutTakeaway = (LinearLayout) findViewById(R.id.layoutTakeaway);
        layoutDelivery = (LinearLayout) findViewById(R.id.layoutDelivery);
        GlobalValues.GstChargers = Utility.readFromSharedPreference(this, GlobalValues.GstCharger);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        discountLeftTextView.setVisibility(View.GONE);
        txtGstLabel.setText("GST Inclusive (" + GlobalValues.GstChargers + "%): ");
        try {

            mOrderNo = getIntent().getStringExtra("OrderId");

        } catch (Exception e) {
            e.printStackTrace();
            Log.e("error", "errorr.......");
        }
        if (Utility.networkCheck(this)) {
            String url = GlobalUrl.ORDER_HISTORY_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&order_id=" + mOrderNo +
                    "&customer_id=" + Utility.readFromSharedPreference(this, GlobalValues.CUSTOMERID);

            new OrderHistoryTask().execute(url);
        } else {
            Toast.makeText(this, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private class OrderHistoryTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(VieworderRewardsActivity.this);
            progressDialog.setMessage("Loading..");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @SuppressLint("SetTextI18n")
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);

                    String status = responseJSONObject.getString("status");


                    ordersArrayList = new ArrayList<>();


                    if (status.equals("ok")) {    //Success

                        List<OrderDetail> orderDetailList = new ArrayList<>();

                        JSONObject common = responseJSONObject.getJSONObject("common");
                        String totalRecords = common.getString("total_records");

                      /*  if(getActivity() instanceof OrderActivity) {

                            if (orderStatus.equals("C")) {
//                                ((OrderActivity) getActivity()).orderCountTextView.setText(totalRecords);
//                                ((OrderActivity) getActivity()).orderBadgeLayout.setVisibility(View.VISIBLE);
                            }
                        }*/

                        JSONArray resultSetJSONArray = responseJSONObject.getJSONArray("result_set");


                        for (int i = 0; i < resultSetJSONArray.length(); i++) {

                            final OrderDetail orderDetail = new OrderDetail();

                            JSONObject jsonObject = resultSetJSONArray.getJSONObject(i);

                            String outlet_name = jsonObject.getString("outlet_name");

                            String status_name = jsonObject.getString("status_name");
                            String order_method_name = jsonObject.getString("order_method_name");

                            String order_table_number = jsonObject.getString("order_table_number");

                            String order_customer_fname = jsonObject.getString("order_customer_fname");
                            String order_customer_lname = jsonObject.getString("order_customer_lname");
                            String order_customer_email = jsonObject.getString("order_customer_email");
                            String order_customer_mobile_no = jsonObject.getString("order_customer_mobile_no");
                            String order_customer_address_line1 = jsonObject.getString("order_customer_address_line1");
                            String order_customer_postal_code = jsonObject.getString("order_customer_postal_code");

                            String order_customer_unit_no1 = jsonObject.getString("order_customer_unit_no1");
                            String order_customer_unit_no2 = jsonObject.getString("order_customer_unit_no2");


                            String order_primary_id = jsonObject.getString("order_primary_id");
                            String order_id = jsonObject.getString("order_id");
                            String order_local_no = jsonObject.getString("order_local_no");
                            String order_outlet_id = jsonObject.getString("order_outlet_id");
                            String order_delivery_charge = jsonObject.getString("order_delivery_charge");

                            String order_tax_charge = jsonObject.getString("order_tax_charge_inclusive");
                            String order_discount_applied = jsonObject.getString("order_discount_applied");
                            String order_discount_amount = jsonObject.getString("order_discount_amount");
                            String order_sub_total = jsonObject.getString("order_sub_total");
                            String order_total_amount = jsonObject.getString("order_total_amount");
                            String order_payment_mode = jsonObject.getString("order_payment_mode");
                            String order_date = jsonObject.getString("order_date");
                            String order_status = jsonObject.getString("order_status");
                            String order_availability_id = jsonObject.getString("order_availability_id");

                            String order_remarks = jsonObject.getString("order_remarks");
                            String order_qnumber = jsonObject.optString("order_qnumber");
                            String order_availability_name = jsonObject.getString("order_availability_name");
                            String order_pickup_time = jsonObject.getString("order_pickup_time");
                            String order_pickup_outlet_id = jsonObject.getString("order_pickup_outlet_id");
                            String order_source = jsonObject.getString("order_source");
                            String order_callcenter_admin_id = jsonObject.getString("order_callcenter_admin_id");
                            String order_cancel_source = jsonObject.getString("order_cancel_source");
                            String order_cancel_by = jsonObject.getString("order_cancel_by");
                            String order_cancel_remark = jsonObject.getString("order_cancel_remark");
                            String order_tat_time = jsonObject.getString("order_tat_time");
                            String order_discount_type = jsonObject.getString("order_discount_type");
                            String order_delivery_waver = jsonObject.getString("order_delivery_waver");
                            String order_pickup_time_slot_from = jsonObject.getString("order_pickup_time_slot_from");
                            String order_pickup_time_slot_to = jsonObject.getString("order_pickup_time_slot_to");
                            String order_discount_both = jsonObject.optString("order_discount_both");
                            String order_points_discount_amount = jsonObject.getString("order_points_discount_amount");
                            String order_additional_delivery = jsonObject.optString("order_additional_delivery");

                            JSONArray itemValueArray = jsonObject.getJSONArray("items");

                            List<ItemResponse> itemValueArrayList = new ArrayList<>();

                            if (itemValueArray.length() > 0) {
                                for (int v = 0; v < itemValueArray.length(); v++) {
                                    JSONObject value = itemValueArray.getJSONObject(v);

                                    ItemResponse items = new ItemResponse();

                                    items.setItemName(value.getString("item_name"));
                                    items.setItemQty(value.getString("item_qty"));
                                    items.setItemTotalAmount(value.getString("item_total_amount"));


                                    itemValueArrayList.add(items);

                                }
                            }

                            orderDetail.setItems(itemValueArrayList);

                            //     String order_closed_status = jsonObject.getString("order_closed_status");

//                            String order_used_new_api = jsonObject.getString("order_used_new_api");
                            //                           String order_payment_retrieved = jsonObject.getString("order_payment_retrieved");

                            orderDetail.setOutlet_name(outlet_name);

                            if (jsonObject.getString("outlet_address_line2").length() > 0) {
                                orderDetail.setOutlet_address(jsonObject.getString("outlet_address_line1") + "\n" +
                                        jsonObject.getString("outlet_address_line2"));
                            } else {
                                orderDetail.setOutlet_address(jsonObject.getString("outlet_address_line1"));
                            }

                            orderDetail.setOutlet_pincode(jsonObject.getString("outlet_postal_code"));

                            orderDetail.setStatus_name(status_name);
                            orderDetail.setOrder_method_name(order_method_name);
                            orderDetail.setOrder_table_number(order_table_number);
                            orderDetail.setOrder_customer_fname(order_customer_fname);
                            orderDetail.setOrder_customer_lname(order_customer_lname);
                            orderDetail.setOrder_customer_email(order_customer_email);
                            orderDetail.setOrder_customer_mobile_no(order_customer_mobile_no);
                            orderDetail.setOrder_customer_address_line1(order_customer_address_line1);
                            orderDetail.setOrder_customer_postal_code(order_customer_postal_code);
                            orderDetail.setOrder_primary_id(order_primary_id);
                            orderDetail.setOrder_id(order_id);
                            orderDetail.setOrder_qNumber(order_qnumber);
                            orderDetail.setOrder_tax_calculate_amount(jsonObject.getString("order_tax_calculate_amount_inclusive"));

                            orderDetail.setOrder_created_date(jsonObject.getString("order_created_on"));

                            orderDetail.setUnitNo(jsonObject.getString("order_customer_unit_no1") +
                                    jsonObject.getString("order_customer_unit_no2"));

                            orderDetail.setOrder_packaging_charge(jsonObject.getString("order_packaging_charge"));
                            orderDetail.setOrder_local_no(order_local_no);
                            orderDetail.setOrder_outlet_id(order_outlet_id);
                            orderDetail.setOrder_delivery_charge(order_delivery_charge);
                            orderDetail.setOrder_additional_delivery(jsonObject.getString("order_additional_delivery"));
                            orderDetail.setOrder_tax_charge(order_tax_charge);
                            orderDetail.setOrder_discount_applied(order_discount_applied);
                            orderDetail.setOrder_discount_amount(order_discount_amount);
                            orderDetail.setOrder_sub_total(order_sub_total);
                            orderDetail.setOrder_total_amount(order_total_amount);
                            orderDetail.setOrder_payment_mode(order_payment_mode);
                            orderDetail.setOrder_date(order_date);
                            orderDetail.setOrder_status(order_status);
                            orderDetail.setOrder_availability_id(order_availability_id);
                            orderDetail.setOrder_availability_name(order_availability_name);
                            orderDetail.setOrder_pickup_time(order_pickup_time);
                            orderDetail.setOrder_pickup_outlet_id(order_pickup_outlet_id);
                            orderDetail.setOrder_source(order_source);
                            orderDetail.setOrder_callcenter_admin_id(order_callcenter_admin_id);
                            orderDetail.setOrder_cancel_source(order_cancel_source);
                            orderDetail.setOrder_cancel_by(order_cancel_by);
                            orderDetail.setOrder_cancel_remark(order_cancel_remark);
                            orderDetail.setOrder_tat_time(order_tat_time);
                            orderDetail.setOrder_discount_type(order_discount_type);
                            orderDetail.setOrder_delivery_waver(order_delivery_waver);
                            orderDetail.setOrder_pickup_time_slot_from(order_pickup_time_slot_from);
                            orderDetail.setOrder_pickup_time_slot_to(order_pickup_time_slot_to);
                            orderDetail.setOrder_discount_both(order_discount_both);
                            orderDetail.setOrder_points_discount_amount(order_points_discount_amount);
                            orderDetail.setOrder_additional_delivery(order_additional_delivery);
                            orderDetail.setOrder_remarks(order_remarks);
                            orderDetail.setOrder_customer_unit_no1(order_customer_unit_no1);
                            orderDetail.setOrder_customer_unit_no2(order_customer_unit_no2);


                            //      orderDetail.setOrder_closed_status(order_closed_status);

                            //..       orderDetail.setOrder_used_new_api(order_used_new_api);
                            //..       orderDetail.setOrder_payment_retrived(order_payment_retrieved);


                            try {
                                JSONArray items_json_array_string = jsonObject.getJSONArray("items");
//                                JSONArray jsonArray=new JSONArray(items_json_array_string);
                                orderDetail.setItems_json_array_string(items_json_array_string);

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            orderDetailList.add(orderDetail);

                            ordersArrayList = orderDetailList;

                            if (orderDetail.getOrder_availability_name().equalsIgnoreCase("Pickup")) {

                                layoutTakeaway.setVisibility(View.VISIBLE);

                            } else {

                                layoutDelivery.setVisibility(View.VISIBLE);

                            }

                            if ((orderDetail.getOrder_qNumber() != null) && (!(orderDetail.getOrder_qNumber().equalsIgnoreCase("")))) {
                                if (orderDetail.getOrder_availability_name().equalsIgnoreCase("Pickup")) {
                                    tv_q_number.setVisibility(View.VISIBLE);
                                    tv_q_number.setText("Q No - " + orderDetail.getOrder_qNumber());
                                }else{
                                    tv_q_number.setVisibility(View.INVISIBLE);
                                }
                            } else {
                                tv_q_number.setVisibility(View.INVISIBLE);
                            }

                            Calendar cal = Calendar.getInstance();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            try {
                                cal.setTime(sdf.parse(orderDetail.getOrder_date()));// all done

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy hh:mm a");

//            tv_order_placed_at.setText("Order Place at : " + simpleDateFormat.format(cal.getTime()));
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            Calendar cals = Calendar.getInstance();
                            SimpleDateFormat sdfs = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                            try {

                                cals.setTime(sdfs.parse(orderDetail.getOrder_date())); //getCreate_date all done

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                                SimpleDateFormat simpleDatetimess = new SimpleDateFormat("hh:mm a");


                                tv_order_date.setText(simpleDateFormat.format(cals.getTime()));

                                tv_order_time.setText(simpleDatetimess.format(cals.getTime()));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            try {

                                Calendar calss = Calendar.getInstance();
                                SimpleDateFormat sdfss = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                                calss.setTime(sdfss.parse(orderDetail.getOrder_date()));// all done

                                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                                SimpleDateFormat simpleDatetimess = new SimpleDateFormat("hh:mm a");

                                tv_delivery_date_time.setText("" + simpleDateFormat.format(calss.getTime()) + "\u0020" + simpleDatetimess.format(calss.getTime()));


                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                            String Availablename = orderDetail.getOrder_availability_name();

                            if (Availablename.equals("Dine In")) {

                                linear_dine.setVisibility(View.GONE);
                                txtorder_table_number.setText("Table No: " + orderDetail.getOrder_table_number());

                                txtorder_table_number.setVisibility(View.VISIBLE);
                            } else if (Availablename.equalsIgnoreCase("Pickup")) {

                                txtorder_table_number.setText("Table No: -");

                                tv_delivery_date.setText("Pickup Date");

                                tv_edelivery.setText("Estimated Pickup Time");

                                txtorder_table_number.setVisibility(View.GONE);

                                linear_dine.setVisibility(View.VISIBLE);
                            } else {

                                txtorder_table_number.setText("Table No: -");

                                tv_delivery_date.setText("Delivery Date");


                                tv_edelivery.setText("Estimated Delivery Time");

                                txtorder_table_number.setVisibility(View.GONE);

                                linear_dine.setVisibility(View.VISIBLE);

                            }

        /*getOrder_special_discount_amount,
                getOrder_special_discount_type,
                getOrder_tax_calculate_amount*/

                            if (orderDetail.getOrder_discount_amount().equals("0.00")) {

                                includeheadersub.setText("* " + orderDetail.getOrder_discount_amount() + " " + orderDetail.getOrder_discount_type());

                                includeheadersub.setVisibility(View.GONE);
                            } else {

                                includeheadersub.setText("* " + orderDetail.getOrder_discount_amount() + " " + orderDetail.getOrder_discount_type());

                                includeheadersub.setVisibility(View.GONE);

                            }

//        gstvaluetextview.setText("$ " + orderDetail.getOrder_total_amount());

                            tv_order_no.setText("ORDER NO - " + orderDetail.getOrder_local_no());

                            tv_order_pay_by.setText("" + orderDetail.getOrder_method_name());

                            methodName.setText(orderDetail.getOrder_method_name());

                            tv_order_status.setText("Order Status : " + orderDetail.getStatus_name());

                            tv_yours.setText("Your " + Availablename + " Outlet");

                            order_type.setText("Order Type: " + orderDetail.getOrder_availability_name());

                            /*getOrder_tax_calculate_amount_inclusive*/
                            //   tv_gst.setText(" \u0024 " + orderDetail.getOrder_tax_charge());

/*
        tv_order_date.setText(""+orderDetail.getOrder_date());
*/

                            tv_name.setText("" + orderDetail.getOutlet_name());
                            txtOutletAddress.setText("" + orderDetail.getOutlet_address() + " \n Singapore - " + orderDetail.getOutlet_pincode());
                            tv_name1.setText("" + orderDetail.getOutlet_name());
                          /*  tv_name1.setText("" + orderDetail.getOutlet_name()+"\n"+
                                    orderDetail.getOutlet_address() + " \n Singapore - " + orderDetail.getOutlet_pincode());
*/
                            tv_handler_name.setText("" + orderDetail.getOutlet_name());

     /*   tv_address.setText("" + orderDetail.getOrder_customer_address_line1() +
                orderDetail.getOrder_customer_postal_code() + "");*/


                            if (orderDetail.getOrder_customer_unit_no1().toString().isEmpty()) {

                                tv_address.setText("" + Utility.getValidString(Utility.getValidString(orderDetail.getOrder_customer_address_line1()) + ", " +
                                        " Singapore " + Utility.getValidString(orderDetail.getOrder_customer_postal_code())));


                            } else {

                                tv_address.setText("" + Utility.getValidString("#" + orderDetail.getOrder_customer_unit_no1()) + "-" + orderDetail.getOrder_customer_unit_no2() + " " + Utility.getValidString(orderDetail.getOrder_customer_address_line1()) +
                                        " Singapore " + Utility.getValidString(orderDetail.getOrder_customer_postal_code()));

                            }

                            if (orderDetail.getUnitNo().length() > 0) {
                                txtUnitNo.setText(orderDetail.getUnitNo());
                                txtUnitNo.setVisibility(View.GONE);

                            } else {
                                txtUnitNo.setVisibility(View.GONE);
                            }

                            tv_edelivery_time.setText("" + orderDetail.getOrder_tat_time() + "");


                            if (orderDetail.getOrder_created_date() != null) {


                                String ordercreated[] = orderDetail.getOrder_created_date().split(" ");

                                SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                                SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
                                Date date;
                                try {
                                    date = originalFormat.parse(ordercreated[0]);
                                    ordercreated[0] = targetFormat.format(date);

                                    System.out.println("Old Format :   " + originalFormat.format(date));
                                    System.out.println("New Format :   " + targetFormat.format(date));


                                    if (ordercreated != null) {
                                        tv_order_placed_at.setText("Order placed at : " + Utility.convertDate(ordercreated[0]) +
                                                " " + Utility.convertTime(ordercreated[1]));
                                    }

                                } catch (ParseException ex) {
                                    ex.printStackTrace();
                                }

                            }

//        check whether discount applied
                            if (orderDetail.getOrder_discount_applied().equalsIgnoreCase("Yes")) {

//            layoutdiscount.setVisibility(View.VISIBLE);

                                try {

                                    if (orderDetail.getOrder_discount_amount().equalsIgnoreCase("null") ||
                                            orderDetail.getOrder_discount_amount().equals("0.00")) {

                                        layoutdiscount.setVisibility(View.GONE);

                                    } else {

                                        layoutdiscount.setVisibility(View.VISIBLE);
                                        if (orderDetail.getOrder_discount_type().equalsIgnoreCase("redeem")) {
                                            txtDiscountLabel.setText("Redeem Points");
                                        } else {
                                            txtDiscountLabel.setText("Promotion");
                                        }
                                        //txtDiscountLabel.setText("DISCOUNT("+orderDetail.getOrder_discount_type()+")");
                                        txtDiscountTotal.setText(String.format("%.2f", new BigDecimal(orderDetail.getOrder_discount_amount())) + ")");

                                    }
                                    discountapplied_sub_total = Double.valueOf(orderDetail.getOrder_sub_total())
                                            - Double.valueOf(orderDetail.getOrder_discount_amount());

                                    discountapplied_grand_total = Double.valueOf(orderDetail.getOrder_total_amount());

                                    tv_sub_total_cost.setText(orderDetail.getOrder_sub_total());
                                    if (!orderDetail.getOrder_packaging_charge().isEmpty()) {
                                        layoutpackingcharge.setVisibility(View.VISIBLE);
                                        txtpacking_charge.setText(orderDetail.getOrder_packaging_charge());
                                    }else {
                                        layoutpackingcharge.setVisibility(View.GONE);
                                    }
                                    tv_total_cost.setText(orderDetail.getOrder_total_amount());

                                    InclusiveGst(discountapplied_grand_total, orderDetail.getOrder_availability_id());


//                tv_sub_total_cost.setText("$" + String.format("%.2f", new BigDecimal(orderDetail.getOrder_sub_total())));
//                tv_total_cost.setText("$" + String.format("%.2f", new BigDecimal(orderDetail.getOrder_total_amount())));

                                } catch (NumberFormatException e) {
                                    e.printStackTrace();
                                }


                            } else if (orderDetail.getOrder_discount_applied().equalsIgnoreCase("No")) {
                                layoutdiscount.setVisibility(View.GONE);
                                txtDiscountTotal.setText("");
                                tv_sub_total_cost.setText(orderDetail.getOrder_sub_total());
                                if (!orderDetail.getOrder_packaging_charge().isEmpty()) {
                                    layoutpackingcharge.setVisibility(View.VISIBLE);
                                    txtpacking_charge.setText(orderDetail.getOrder_packaging_charge());
                                }else {
                                    layoutpackingcharge.setVisibility(View.GONE);
                                }
                                tv_total_cost.setText(orderDetail.getOrder_total_amount());

                                InclusiveGst(Double.valueOf(orderDetail.getOrder_total_amount()), orderDetail.getOrder_availability_id());


                            }


                            layoutSendMail.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    sendMail(orderDetail);
                                }
                            });


                            if (orderDetail.getOrder_availability_id().equals(GlobalValues.DELIVERY_ID) ||
                                    orderDetail.getOrder_availability_id().equals(GlobalValues.BENTO_ID)) {

                                if (orderDetail.getOrder_delivery_charge().equals("0.00")) {

                                    rel_delivery.setVisibility(View.GONE);

                                } else {
                                    rel_delivery.setVisibility(View.VISIBLE);

                                    deliverytext_cost.setText("$" + orderDetail.getOrder_delivery_charge());
                                }

                                if (orderDetail.getOrder_additional_delivery().equalsIgnoreCase("0.00")) {

                                    layoutAdditionalDeliveryCharge.setVisibility(View.GONE);

                                } else {
                                    layoutAdditionalDeliveryCharge.setVisibility(View.VISIBLE);
                                    txtAdditionalDeliveryCharge.setText("$" + orderDetail.getOrder_additional_delivery());
                                }

                                if (orderDetail.getOrder_availability_id().equals(GlobalValues.BENTO_ID)) {
                                    //layoutGST.setVisibility(View.VISIBLE);
                                    txt_insulsive_gst1.setText("GST Inclusive (" + Double.valueOf(orderDetail.getOrder_tax_charge()).intValue() + "%) "+orderDetail.getOrder_tax_calculate_amount());
                                    txtGST.setText("$" + orderDetail.getOrder_tax_calculate_amount());
                                    txtGSTLabel.setText("GST (" + Double.valueOf(orderDetail.getOrder_tax_charge()).intValue() + "%)");

                                } else {
                                    //layoutGST.setVisibility(View.VISIBLE);
                                    txt_insulsive_gst1.setText("GST Inclusive (" + Double.valueOf(orderDetail.getOrder_tax_charge()).intValue() + "%) "+orderDetail.getOrder_tax_calculate_amount());
                                    txtGST.setText("$" + orderDetail.getOrder_tax_calculate_amount());
                                    txtGSTLabel.setText("GST (" + Double.valueOf(orderDetail.getOrder_tax_charge()).intValue() + "%)");

                                }


                            } else {

                                rel_delivery.setVisibility(View.GONE);
                                layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                                //layoutGST.setVisibility(View.VISIBLE);
                                txt_insulsive_gst1.setText("GST Inclusive (" + Double.valueOf(orderDetail.getOrder_tax_charge()).intValue() + "%) "+orderDetail.getOrder_tax_calculate_amount());
                                txtGST.setText("$" + orderDetail.getOrder_tax_calculate_amount());
                                txtGSTLabel.setText("GST (" + Double.valueOf(orderDetail.getOrder_tax_charge()).intValue() + "%)");

                            }


//        tv_total_cost.setText("$" + orderDetail.getOrder_total_amount());

/*        ViewrewardsAdaptersnew viewOrderListAdapter = new ViewrewardsAdaptersnew(ViewOrderDetailNewActivity.this, orderDetail.getItems_json_array_string());
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(ViewOrderDetailNewActivity.this);
        recyler_view_ordered_products.setLayoutManager(linearLayoutManager);
        recyler_view_ordered_products.setNestedScrollingEnabled(false);  //aa
        recyler_view_ordered_products.setAdapter(viewOrderListAdapter);*/

                            ViewrewardsAdaptersnew viewOrderListAdapter = new ViewrewardsAdaptersnew(VieworderRewardsActivity.this, orderDetail.getItems_json_array_string());
                            final LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VieworderRewardsActivity.this, LinearLayoutManager.VERTICAL, false);
                            recyler_view_ordered_products.setLayoutManager(linearLayoutManager);
                            recyler_view_ordered_products.setAdapter(viewOrderListAdapter);
                            recyler_view_ordered_products.setNestedScrollingEnabled(false);
                            recyler_view_ordered_products.setHasFixedSize(true);
                            recyler_view_ordered_products.setItemAnimator(new DefaultItemAnimator());


                            //Discount Section
                            if (orderDetail.getOrder_discount_applied().equals("Yes")) {

                                rel_promotion_charges.setVisibility(View.GONE);

                                if (orderDetail.getOrder_discount_type().equals("coupon")) {  // Promo/Coupon code

                                    if (orderDetail.getOrder_delivery_waver().equals("Yes")) {  //delivery discount

                                        discountLeftTextView.setText("Voucher Points Applied (Free Delivery)");


                                        try {
                                            double value = Double.parseDouble(orderDetail.getOrder_delivery_charge());
                                            discountTextView.setText("( $" + String.format("%.2f", value) + ")");
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    } else {  //normal promo code

                                        discountLeftTextView.setText("Voucher Points Applied");
                                        double value = Double.parseDouble(orderDetail.getOrder_discount_amount());
                                        discountTextView.setText("- $" + String.format("%.2f", value) + "");


                                    }

                                } else if (orderDetail.getOrder_discount_type().equals("voucher")) {

                                    discountLeftTextView.setText("Voucher Code Applied");
                                    double value = Double.parseDouble(orderDetail.getOrder_discount_amount());
                                    discountTextView.setText("- $" + String.format("%.2f", value) + "  ");

                                } else if (orderDetail.getOrder_discount_type().equals("redeem")) {

                                    //   discountLeftTextView.setText("Reward Points Redeemed - " + "");
                                    //   discountLeftTextView.setText("Reward Points Redeemed " + "");
                                    discountLeftTextView.setText("Redeem Points Applied " + "");
                                    double value = Double.parseDouble(orderDetail.getOrder_discount_amount());
                                    discountTextView.setText("- $" + String.format("%.2f", value) + "");

                                }
                            }


                        }


                    } else {


                    }


                } catch (Exception e) {
                    e.printStackTrace();

                }


            } else {

            }
        }

    }

    public void sendMail(OrderDetail orderDetail) {

        String url = GlobalUrl.SEND_MAIL_URL + "?app_id=" + GlobalValues.APP_ID + "&order_id=" + orderDetail.getOrder_id();

        if (orderDetail.getOrder_id() != null) {

            new SendMailTask().execute(url);
        }
    }

    private class SendMailTask extends AsyncTask<String, Void, String> {


        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(VieworderRewardsActivity.this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(VieworderRewardsActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(VieworderRewardsActivity.this, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }


    public void InclusiveGst(Double GrandTotal, String current) {

        Double Grandamt = 0.0;


        Double Ca1 = 1.0 + Double.parseDouble(GlobalValues.GstChargers) / 100;
        Double Ca2 = Double.parseDouble(GlobalValues.GstChargers) / 100;
        Double productc1 = GrandTotal / Ca1;
        Grandamt = productc1 * Ca2;

        if (current.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
            includeheader.setVisibility(View.GONE);
        } else {
            includeheader.setVisibility(View.VISIBLE);
            includeheader.setText(String.format("%.2f", Grandamt));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Utility.writeToSharedPreference(VieworderRewardsActivity.this, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(VieworderRewardsActivity.this, "OREO_UPDATE", "1");
        }
//        GlobalValues.currentVisibleActivity = this;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(VieworderRewardsActivity.this, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(VieworderRewardsActivity.this, "OREO_UPDATE", "1");
        }
    }
}
