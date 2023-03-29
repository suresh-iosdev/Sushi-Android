package com.app.sushi.tei.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Cart.CartModifier;
import com.app.sushi.tei.Model.Cart.CartModifierValue;
import com.app.sushi.tei.Model.OrderHistory;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.ThanksRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ThanksActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar toolbar;
    private LinearLayout imgBack;
    private ImageView txtTitle;
    private RecyclerView orderRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private ThanksRecyclerAdapter thanksRecyclerAdapter;
    //private LinearLayout layoutDeliveryCharge;
    private LinearLayout layoutCheckOrders, layoutTakeaway, layoutDelivery;
    private TextView txtOrderNo, txtOutletName, txtOrderDate, txtOutletAddress,
            txtAddress, txtCity, txtUnitNo, txtTime, txtDate, txtSubTotal, txtSubTotalSymbol, txtDeliveryCharge, txtTotal, txtTotalSymbol,
            txtTakeawayName, txtDiscountLabel, pickupTxt, txtWalletTotal, txtWalletTotalSymbol1, txtWalletTotalSymbol2;
    private Intent intent;
    private String mOrderNo = "";
    private List<OrderHistory> orderHistoryList;
    private DatabaseHandler databaseHandler;
    private double discountapplied_sub_total, discountapplied_grand_total;
    private LinearLayout layoutAdditionalDeliveryCharge,layoutpackingcharge;
    private LinearLayout layoutdiscount, layoutdiscountRedeem, layoutdiscountVouchers, layoutewallet;
    //  private LinearLayout layoutGST;
    private TextView txtDiscountTotal, txtAdditionalDeliveryCharge, CustomerNotes,
            txtDiscountTotalRedeem, txtDiscountTotalVouchers, txt_someoneRemarks, txt_label_remarks,
            txtpacking_charge;
       private TextView txt_insulsive_gst,txt_insulsive_gst1;
    private TextView outletText, paymentMethodName;
    private TextView txt_ordercount, txt_orderNumber, txtGst, txtGSTLabel, txtGstLabel;
    private LinearLayout layoutDeliveryCharge;
    private LinearLayout lnrGst;
    private TextView txt_cartTimeLabel, txt_cartDateLabel, txt_address;
    private static Double gstAmount;
    private View view;

    private View moveIconFromLeftThanks;
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanks);

        mContext = ThanksActivity.this;
        databaseHandler = DatabaseHandler.getInstance(mContext);
        layoutdiscount = findViewById(R.id.layoutdiscount);
        layoutdiscountRedeem = findViewById(R.id.layoutdiscountRedeem);
        layoutdiscountVouchers = findViewById(R.id.layoutdiscountVouchers);
        layoutewallet = findViewById(R.id.layoutewallet);
        // layoutGST =  findViewById(R.id.layoutGST);
        txtDiscountTotal = (TextView) findViewById(R.id.txtDiscountTotal);
        txtDiscountTotalRedeem = findViewById(R.id.txtDiscountTotalRedeem);
        txtDiscountTotalVouchers = findViewById(R.id.txtDiscountTotalVouchers);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
//        moveIconFromLeftThanks = findViewById(R.id.moveIconFromLeftThanks);
//        moveIconFromLeftThanks.setVisibility(View.VISIBLE);
        ImageView toolbartxtTitleSummary;
        ImageView toolbartxtTitle;
        toolbartxtTitleSummary = findViewById(R.id.toolbartxtTitleSummary);
        toolbartxtTitle = findViewById(R.id.toolbartxtTitle);
        toolbartxtTitleSummary.setVisibility(View.VISIBLE);
        toolbartxtTitle.setVisibility(View.GONE);

        txtWalletTotal = findViewById(R.id.txtWalletTotal);
        txtWalletTotalSymbol1 = findViewById(R.id.txtWalletTotalSymbol1);
        txtWalletTotalSymbol2 = findViewById(R.id.txtWalletTotalSymbol2);

        imgBack = toolbar.findViewById(R.id.toolbarBack);
        txtTitle = toolbar.findViewById(R.id.toolbartxtTitle);
        paymentMethodName = findViewById(R.id.paymentMethodName);
        CustomerNotes = findViewById(R.id.CustomerNotes);
        pickupTxt = findViewById(R.id.pickupTxt);
        txt_address = findViewById(R.id.txt_address);
        txt_someoneRemarks = findViewById(R.id.txt_someoneRemarks);
        txt_label_remarks = findViewById(R.id.txt_label_remarks);
        txt_ordercount = findViewById(R.id.txt_ordercount);
        txt_orderNumber = findViewById(R.id.txt_orderNumber);
        txtpacking_charge=findViewById(R.id.txtpacking_charge);

        imgBack.setVisibility(View.GONE);

        // txt_insulsive_gst =(TextView)findViewById(R.id.txt_insulsive_gst);
        txt_insulsive_gst1=findViewById(R.id.txt_insulsive_gst1);
        txt_cartTimeLabel = findViewById(R.id.txt_cartTimeLabel);
        txt_cartDateLabel = findViewById(R.id.txt_cartDateLabel);

        orderRecyclerView = (RecyclerView) findViewById(R.id.ordersRecyclerView);
        layoutCheckOrders = (LinearLayout) findViewById(R.id.layoutCheckOrders);
        layoutDelivery = (LinearLayout) findViewById(R.id.layoutDelivery);
        layoutTakeaway = (LinearLayout) findViewById(R.id.layoutTakeaway);
        layoutDeliveryCharge = findViewById(R.id.layoutDeliveryCharge);
        lnrGst = findViewById(R.id.lnrGst);
        layoutAdditionalDeliveryCharge = findViewById(R.id.layoutAdditionalDeliveryCharge);
        layoutpackingcharge=findViewById(R.id.layoutpackingcharge);
        txtOrderNo = (TextView) findViewById(R.id.txtOrderNo);
        txtOutletName = (TextView) findViewById(R.id.txtOutletName);
        txtOutletName = (TextView) findViewById(R.id.txtOutletName);
        txtOrderDate = (TextView) findViewById(R.id.txtOrderDate);
        // txtPaymentMode = (TextView) findViewById(R.id.txtPaymentMode);
        txtOutletAddress = (TextView) findViewById(R.id.txtOutletAddress);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtCity = (TextView) findViewById(R.id.txtCity);
        txtUnitNo = (TextView) findViewById(R.id.txtUnitNo);
        txtTime = (TextView) findViewById(R.id.txtTime);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtSubTotal = (TextView) findViewById(R.id.txtSubTotal);
        txtSubTotalSymbol = (TextView) findViewById(R.id.txtSubTotalSymbol);
        txtDeliveryCharge = (TextView) findViewById(R.id.txtDeliveryCharge);
        txtAdditionalDeliveryCharge = (TextView) findViewById(R.id.txtAdditionalDeliveryCharge);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotalSymbol = (TextView) findViewById(R.id.txtTotalSymbol);
        txtTakeawayName = (TextView) findViewById(R.id.txtTakeawayName);
        txtDiscountLabel = (TextView) findViewById(R.id.txtDiscountLabel);
        txtGst = findViewById(R.id.txtGst);
        txtGstLabel = findViewById(R.id.txtGstLabel);
        view = findViewById(R.id.view);
       /* txtGST = (TextView) findViewById(R.id.txtGST);
        txtGSTLabel = (TextView) findViewById(R.id.txtGSTLabel);*/
        txt_address.setText(Utility.readFromSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS) + ", Singapore, " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_PINCODE));
        layoutManager = new LinearLayoutManager(mContext);
        orderRecyclerView.setLayoutManager(layoutManager);
        GlobalValues.GstChargers = Utility.readFromSharedPreference(ThanksActivity.this, GlobalValues.GstCharger);
        txtGstLabel.setText("GST Charges (" + GlobalValues.GstChargers + "%) ");

        try {
            if (GlobalValues.GstChargers != null && !GlobalValues.GstChargers.isEmpty() && Double.parseDouble(GlobalValues.GstChargers) != 0.00) {
                lnrGst.setVisibility(View.GONE); //Hide
            } else {
                lnrGst.setVisibility(View.GONE);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        try {

            mOrderNo = getIntent().getStringExtra("local_order_no");

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
            pickupTxt.setText("DELIVERY FROM");
            txt_cartTimeLabel.setText("DELIVERY TIME");
            txt_cartDateLabel.setText("DATE OF DELIVERY");
        } else {
            //       pickupTxt.setText("PICKUP FROM");
//            pickupTxt.setText("COLLECTION FROM");
            pickupTxt.setText("PICKUP AT");
            txt_cartTimeLabel.setText("COLLECTION TIME");
            txt_cartDateLabel.setText("DATE OF PICKUP");
        }

        if (Utility.networkCheck(mContext)) {
            String url = GlobalUrl.ORDER_HISTORY_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&local_order_no=" + mOrderNo +
                    "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            new OrderHistoryTask().execute(url);
        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

        outletText = (TextView) findViewById(R.id.outletNames);
        outletText.setText(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));

        layoutCheckOrders.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalValues.CURRENT_OUTLET_ID = "";
                GlobalValues.CURRENT_AVAILABLITY_NAME = "";
                GlobalValues.CURRENT_AVAILABLITY_ID = "";


                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_RESPONSE);

             /*   Utility.removeFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
                Utility.removeFromSharedPreference(mContext, GlobalValues.OUTLET_ID);*/


                GlobalValues.CURRENT_AVAILABLITY_ID = "";
                GlobalValues.CURRENT_OUTLET_ID = "";

                try {
                    databaseHandler.deleteAllCartQuantity();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                intent = new Intent(mContext, MenuCategoryActivity.class);
                intent.putExtra("view_order", 1);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        GlobalValues.CURRENT_OUTLET_ID = "";
        GlobalValues.CURRENT_AVAILABLITY_NAME = "";
        GlobalValues.CURRENT_AVAILABLITY_ID = "";


        Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
        Utility.removeFromSharedPreference(mContext, GlobalValues.CART_RESPONSE);

       /* Utility.removeFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
        Utility.removeFromSharedPreference(mContext, GlobalValues.OUTLET_ID);*/


        GlobalValues.CURRENT_AVAILABLITY_ID = "";
        GlobalValues.CURRENT_OUTLET_ID = "";


        try {
            databaseHandler.deleteAllCartQuantity();
        } catch (Exception e) {
            e.printStackTrace();
        }

        intent = new Intent(mContext, SubCategoryActivity.class);
        intent.putExtra("view_order", 0);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private class OrderHistoryTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
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

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("thanks_response", s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONArray jsonResult = jsonObject.getJSONArray("result_set");

                    //  JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));


                    for (int i = 0; i < jsonResult.length(); i++) {
                        JSONObject jsonThanks = jsonResult.getJSONObject(i);
                        Log.e("TAG","OrderLocalNumberTest::"+jsonThanks.getString("order_local_no"));
                        txtOrderNo.setText(jsonThanks.getString("order_local_no"));
                        if (jsonThanks.getString("order_remarks").equalsIgnoreCase("")) {
                            CustomerNotes.setVisibility(View.GONE);
                            txt_label_remarks.setVisibility(View.GONE);
                        } else {
                            txt_label_remarks.setVisibility(View.VISIBLE);
                            CustomerNotes.setVisibility(View.VISIBLE);
                            CustomerNotes.setText(jsonThanks.getString("order_remarks"));
                        }

//                        if (jsonThanks.getString("order_someone_remarks").equalsIgnoreCase("")) {
                        txt_someoneRemarks.setVisibility(View.GONE);
//                        } else {
//                            txt_someoneRemarks.setVisibility(View.VISIBLE);
//                            txt_someoneRemarks.setText("Message: " + jsonThanks.getString("order_someone_remarks"));
//                        }

                        if (jsonThanks.getString("order_remarks").equalsIgnoreCase("") /*&& jsonThanks.getString("order_someone_remarks").equalsIgnoreCase("")*/) {
                            view.setVisibility(View.GONE);
                        } else {
                            view.setVisibility(View.VISIBLE);
                        }

//                        String wallet_used_amt = jsonThanks.getString("order_ewallet_amount");

//                        if(!(wallet_used_amt == null && wallet_used_amt.equalsIgnoreCase(""))){
//                            if(Double.parseDouble(wallet_used_amt) > 0){
//                                layoutewallet.setVisibility(View.VISIBLE);
//                                txtWalletTotal.setText(wallet_used_amt + ")");
//                            }else{
                        layoutewallet.setVisibility(View.GONE);
//                            }
//                        }

//                        paymentMethodName.setText(jsonThanks.getString("order_method_name"));

//                        if (jsonThanks.getString("order_frontofyou").equalsIgnoreCase("0")) {
                        txt_ordercount.setVisibility(View.GONE);
//                        } else {
//                            txt_ordercount.setVisibility(View.VISIBLE);
//                            txt_ordercount.setText("You have " + jsonThanks.getString("order_frontofyou") + " orders in front of you");
//                        }

//                        txt_orderNumber.setText("Q Number - " + jsonThanks.getString("order_qnumber"));

                        String orderdate[] = jsonThanks.getString("order_date").split(" ");
                        String ordercreated[] = jsonThanks.getString("order_created_on").split(" ");
                        SimpleDateFormat originalFormat = new SimpleDateFormat("yyyy-MM-dd");
                        SimpleDateFormat targetFormat = new SimpleDateFormat("dd-MM-yyyy");
                        Date date;

                        try {
                            date = originalFormat.parse(orderdate[0]);
                            orderdate[0] = targetFormat.format(date);

                            date = originalFormat.parse(ordercreated[0]);
                            ordercreated[0] = targetFormat.format(date);

                            System.out.println("Old Format :   " + originalFormat.format(date));
                            System.out.println("New Format :   " + targetFormat.format(date));

                        } catch (ParseException ex) {
                            ex.printStackTrace();
                        }
                        String order_is_timeslot = jsonThanks.optString("order_is_timeslot");

                        String order_pickup_time_slot_from = jsonThanks.optString("order_pickup_time_slot_from");
                        String order_pickup_time_slot_to = jsonThanks.optString("order_pickup_time_slot_to");

                        String order_delivery_time_slot_from = jsonThanks.optString("order_delivery_time_slot_from");
                        String order_delivery_time_slot_to = jsonThanks.optString("order_delivery_time_slot_to");
                        String order_availability_name = jsonThanks.getString("order_availability_name");

                        if (order_is_timeslot.equalsIgnoreCase("Yes")) {
                            if (order_availability_name.equalsIgnoreCase("Pickup")) {
                                txtTime.setText(Utility.convertTime(order_pickup_time_slot_from) + " - " +
                                        Utility.convertTime(order_pickup_time_slot_to));
                            } else {
                                txtTime.setText(Utility.convertTime(order_delivery_time_slot_from) + " - " +
                                        Utility.convertTime(order_delivery_time_slot_to));
                            }
                            if (orderdate != null) {
                                txtDate.setText(Utility.convertDate(orderdate[0]));
                            }
//                            if (order_availability_name.equalsIgnoreCase("Delivery")) {
//                                txtTime.setText(Utility.convertTime(orderdate[1]));
//                            } else {
//                                txtTime.setText(Utility.convertTime(orderdate[1]));
//                            }
                        } else {
                            if (orderdate != null) {
                                txtDate.setText(Utility.convertDate(orderdate[0]));
                                txtTime.setText(Utility.convertTime(orderdate[1]));
                            }
                        }
                        if (ordercreated != null) {
                            txtOrderDate.setText(Utility.convertDate(ordercreated[0]) + "|" + Utility.convertTime(ordercreated[1]));
                        }
//                        check whether discount applied
                        if (jsonThanks.getString("order_discount_applied").equalsIgnoreCase("Yes")) {
                            layoutdiscount.setVisibility(View.VISIBLE);

                            if (jsonThanks.optString("order_discount_type").equalsIgnoreCase("redeem")) {
                                txtDiscountLabel.setText("Redeem Points");
                            } else {
                                txtDiscountLabel.setText("Promotion" + "(" + jsonThanks.optString("order_promocode_name") + ")");
                            }

                            if (jsonThanks.optString("order_discount_both").equalsIgnoreCase("1")) {
                                layoutdiscountRedeem.setVisibility(View.VISIBLE);
                                txtDiscountTotalRedeem.setText(String.format("%.2f", new BigDecimal(jsonThanks.getString("order_points_discount_amount"))) + ")");
                            } else {
                                layoutdiscountRedeem.setVisibility(View.GONE);
                            }

                            txtDiscountTotal.setText(String.format("%.2f", new BigDecimal(jsonThanks.getString("order_discount_amount"))) + ")");
                            try {

                                discountapplied_sub_total = Double.valueOf(jsonThanks.getString("order_sub_total")) -
                                        Double.valueOf(jsonThanks.getString("order_discount_amount"));

                                if (jsonThanks.optString("order_discount_both").equalsIgnoreCase("1")) {
                                    discountapplied_sub_total = Double.valueOf(jsonThanks.getString("order_sub_total")) -
                                            Double.valueOf(jsonThanks.getString("order_discount_amount")) - Double.valueOf(jsonThanks.getString("order_points_discount_amount"));
                                }
                                //discountapplied_grand_total = Double.valueOf(jsonThanks.getString("order_total_amount")) - Double.valueOf(jsonThanks.getString("order_discount_amount"));
                                if (discountapplied_sub_total < 0) {
                                    discountapplied_sub_total = 0.00;
                                }

                                if (!jsonThanks.isNull("order_packaging_charge")){
                                    layoutpackingcharge.setVisibility(View.VISIBLE);
                                    txtpacking_charge.setText(jsonThanks.getString("order_packaging_charge"));

                                }else {
                                    layoutpackingcharge.setVisibility(View.GONE);
                                }

                                txtSubTotal.setText(jsonThanks.getString("order_sub_total"));
                                txtSubTotalSymbol.setVisibility(View.VISIBLE);
                                txtTotal.setText(jsonThanks.getString("order_total_amount"));
                                txtTotalSymbol.setVisibility(View.VISIBLE);

                                txt_insulsive_gst1.setText("GST Inclusive (" + GlobalValues.GstChargers +" %)"+jsonThanks.getString("order_tax_calculate_amount"));
            //                    txtGst.setText(jsonThanks.getString("order_tax_calculate_amount"));
                                InclusiveGst(Double.valueOf(discountapplied_sub_total));

// txtTotal.setText("$" + jsonThanks.getString("order_total_amount"));

                            } catch (NumberFormatException e) {
                                e.printStackTrace();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                        } else if (jsonThanks.getString("order_discount_applied").equalsIgnoreCase("No")) {
                            layoutdiscount.setVisibility(View.GONE);
                            layoutdiscountRedeem.setVisibility(View.GONE);
                            txtDiscountTotal.setText("");
                            txtDiscountTotalRedeem.setText("");

                            if (!jsonThanks.isNull("order_packaging_charge")){
                                layoutpackingcharge.setVisibility(View.VISIBLE);
                                txtpacking_charge.setText(jsonThanks.getString("order_packaging_charge"));

                            }else {
                                layoutpackingcharge.setVisibility(View.GONE);
                            }

                            txtSubTotal.setText(jsonThanks.getString("order_sub_total"));
                            txtSubTotalSymbol.setVisibility(View.VISIBLE);
                            txtTotal.setText(jsonThanks.getString("order_total_amount"));
                            txtTotalSymbol.setVisibility(View.VISIBLE);

                            txt_insulsive_gst1.setText("GST Inclusive (" + GlobalValues.GstChargers +" %)"+jsonThanks.getString("order_tax_calculate_amount"));
                            txtGst.setText(jsonThanks.getString("order_tax_calculate_amount"));
                            InclusiveGst(Double.valueOf(jsonThanks.getString("order_total_amount")));
                        }

//

                        txt_insulsive_gst1.setText("GST Inclusive (" + GlobalValues.GstChargers +" %)"+jsonThanks.getString("order_tax_calculate_amount"));
                        txtGst.setText(jsonThanks.getString("order_tax_calculate_amount"));
                        //txtGSTLabel.setText("Gst Charges(" + jsonThanks.getString("order_tax_charge_inclusive") + "%)");
                        if (jsonThanks.getString("order_availability_id").equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                                jsonThanks.getString("order_availability_id").equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                            layoutDelivery.setVisibility(View.VISIBLE);
                            layoutTakeaway.setVisibility(View.GONE);
                            txt_ordercount.setVisibility(View.GONE);
//                            txt_orderNumber.setVisibility(View.GONE);

                            txtOutletName.setText(jsonThanks.optString("outlet_name"));

                            txtOutletAddress.setText(jsonThanks.optString("outlet_address_line1") + "\n" + "Singapore, " +
                                    jsonThanks.optString("outlet_postal_code") + "\n" + "#" +
                                    Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_UNITNO1)
                                    + "-" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_UNITNO2));


                            txtAddress.setText(jsonThanks.optString("order_customer_address_line1"));


                            if (jsonThanks.getString("order_customer_unit_no1").length() > 0 && jsonThanks.getString("order_customer_unit_no2").length() > 0) {
                                txtCity.setText("Singapore, " + jsonThanks.optString("order_customer_postal_code") + "\n" + "#" + jsonThanks.getString("order_customer_unit_no1") + "-" +
                                        jsonThanks.getString("order_customer_unit_no2"));
                            } else if (jsonThanks.getString("order_customer_unit_no1").length() > 0) {
                                txtCity.setText("Singapore, " + jsonThanks.optString("order_customer_postal_code") + "\n" + "#" + jsonThanks.getString("order_customer_unit_no1"));
                            } else {
                                txtCity.setText("Singapore, " + jsonThanks.optString("order_customer_postal_code"));
                            }

                            if (jsonThanks.getString("order_delivery_charge").equalsIgnoreCase("null") ||
                                    jsonThanks.getString("order_delivery_charge").equalsIgnoreCase("0.00")) {

                                layoutDeliveryCharge.setVisibility(View.GONE);

                            } else {

                                layoutDeliveryCharge.setVisibility(View.VISIBLE);
                                txtDeliveryCharge.setText(jsonThanks.getString("order_delivery_charge"));
                            }


                            if (jsonThanks.getString("order_additional_delivery").equalsIgnoreCase("null") ||
                                    jsonThanks.getString("order_additional_delivery").equalsIgnoreCase("0.00")) {
                                layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                            } else {

                                layoutAdditionalDeliveryCharge.setVisibility(View.VISIBLE);
                                txtAdditionalDeliveryCharge.setText(jsonThanks.getString("order_additional_delivery"));
                            }



//                            txtTotal.setText("$" + String.format("%.2f", Double.parseDouble(jsonThanks.getString("order_sub_total")) +
//                                    Double.parseDouble(jsonThanks.getString("order_delivery_charge"))));


                            /*if (jsonThanks.getString("order_customer_unit_no1").length() > 0) {
                                txtUnitNo.setVisibility(View.VISIBLE);
                                txtUnitNo.setText(jsonThanks.getString("order_customer_unit_no1") + "-" +
                                        jsonThanks.getString("order_customer_unit_no2"));
                            } else {
                                txtUnitNo.setVisibility(View.GONE);
                            }*/


                       /*     if (jsonThanks.getString("order_availability_id").equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                                layoutGST.setVisibility(View.VISIBLE);
                                txtGST.setText("$"+jsonThanks.getString("order_tax_calculate_amount"));
                                txtGSTLabel.setText("GST (" + Double.valueOf(jsonThanks.getString("order_tax_charge")).intValue() + "%)");

                            } else {
                                layoutGST.setVisibility(View.VISIBLE);
                                txtGST.setText("$"+jsonThanks.getString("order_tax_calculate_amount"));
                                txtGSTLabel.setText("GST (" + Double.valueOf(jsonThanks.getString("order_tax_charge")).intValue() + "%)");
                            }*/

                        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            layoutDeliveryCharge.setVisibility(View.GONE);
                            layoutDelivery.setVisibility(View.GONE);
                            layoutTakeaway.setVisibility(View.GONE);
                            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                            //                        txt_ordercount.setVisibility(View.VISIBLE);
//                            txt_orderNumber.setVisibility(View.VISIBLE);

                        /*    txtTakeawayName.setText(jsonThanks.optString("outlet_name") + "\n" +
                                    outletZoneJson.optString("outlet_address_line1") + "\n Singapore - " +
                                    outletZoneJson.optString("outlet_postal_code"));*/


                            txtTotal.setText(String.format("%.2f",
                                    Double.parseDouble(jsonThanks.getString("order_total_amount"))));
                            txtTotalSymbol.setVisibility(View.VISIBLE);

                            txt_insulsive_gst1.setText("GST Inclusive (" + GlobalValues.GstChargers +" %)"+jsonThanks.getString("order_tax_calculate_amount"));
                            txtGst.setText(jsonThanks.getString("order_tax_calculate_amount"));
                            InclusiveGst(Double.valueOf(jsonThanks.getString("order_total_amount")));

                            //  layoutGST.setVisibility(View.VISIBLE);
                            //  txtGST.setText("$"+jsonThanks.getString("order_tax_calculate_amount"));
                            //  txtGSTLabel.setText("GST (" + Double.valueOf(jsonThanks.getString("order_tax_charge")).intValue() + "%)");
                        }
                        if (jsonThanks.optString("order_voucher_discount_amount").equalsIgnoreCase("null") ||
                                jsonThanks.optString("order_voucher_discount_amount").equalsIgnoreCase("0.00")) {
                            layoutdiscountVouchers.setVisibility(View.GONE);
                        } else {
                            layoutdiscountVouchers.setVisibility(View.VISIBLE);
                            txtDiscountTotalVouchers.setText(jsonThanks.getString("order_voucher_discount_amount") + ")");
                        }


                        JSONArray jsonItem = jsonThanks.getJSONArray("items");

                        orderHistoryList = new ArrayList<>();

                        if (jsonItem.length() > 0) {
                            for (int k = 0; k < jsonItem.length(); k++) {

                                JSONObject item = jsonItem.getJSONObject(k);

                                OrderHistory orderHistory = new OrderHistory();

                                orderHistory.setmProductId(item.getString("item_product_id"));
                                orderHistory.setmProductName(item.getString("item_name"));
                                orderHistory.setmProductQty(item.getString("item_qty"));
                                orderHistory.setmProductImage(item.getString("item_image"));
                                orderHistory.setmProductTotalPrice(item.getString("item_total_amount"));
                                orderHistory.setmProductSpecification(item.getString("item_specification"));
                                orderHistory.setmItemVoucherName(item.getString("item_voucher_name"));
                                orderHistory.setmItemVoucherOrderId(item.getString("item_voucher_order_id"));
                                orderHistory.setmItemVoucherFreeProduct(item.getString("item_voucher_free_product"));
                                JSONArray modifierArray = item.getJSONArray("modifiers");


                                List<CartModifier> cartModifierList = new ArrayList<>();

                                if (modifierArray.length() > 0) {
                                    for (int m = 0; m < modifierArray.length(); m++) {
                                        JSONObject modifier = modifierArray.getJSONObject(m);

                                        CartModifier cartModifier = new CartModifier();

                                        cartModifier.setmModifierId(modifier.getString("order_modifier_id"));
                                        cartModifier.setmModifierName(modifier.getString("order_modifier_name"));

                                        JSONArray modifierValueArray = modifier.getJSONArray("modifiers_values");

                                        List<CartModifierValue> cartModifierValueList = new ArrayList<>();

                                        if (modifierValueArray.length() > 0) {
                                            for (int v = 0; v < modifierValueArray.length(); v++) {
                                                JSONObject value = modifierValueArray.getJSONObject(v);

                                                CartModifierValue cartModifierValue = new CartModifierValue();

                                                cartModifierValue.setmModifierValueId(value.getString("order_modifier_id"));
                                                cartModifierValue.setmModifierValueName(value.getString("order_modifier_name"));

                                                cartModifierValue.setmModifierValuePrice(value.getString("order_modifier_price"));
                                                cartModifierValue.setmModifierValueQuantity(value.getString("order_modifier_qty"));

                                                cartModifierValueList.add(cartModifierValue);
                                            }
                                        }
                                        cartModifier.setCartModifierValueList(cartModifierValueList);

                                        cartModifierList.add(cartModifier);


                                    }
                                } else {
                                }

                                orderHistory.setCartModifierList(cartModifierList);

                                //set menu product
                                JSONArray setmenuJsonArray = item.getJSONArray("set_menu_component");

                                List<SetMenuTitle> setMenuTitleList = new ArrayList<>();
                                if (setmenuJsonArray.length() > 0) {

                                    for (int t = 0; t < setmenuJsonArray.length(); t++) {

                                        JSONObject setmenuObject = setmenuJsonArray.getJSONObject(t);

                                        SetMenuTitle setMenuTitle = new SetMenuTitle();

                                        setMenuTitle.setmTitleMenuName(setmenuObject.optString("menu_component_name"));
                                        setMenuTitle.setmultipleselection_apply(setmenuObject.optString("menu_component_multipleselection_apply"));
                                        setMenuTitle.setmenu_component_modifier_apply(setmenuObject.optString("menu_component_modifier_apply"));
                                        GlobalValues.MULTIPLESLECTIONAPPLY = setmenuObject.optString("menu_component_multipleselection_apply");
                                        GlobalValues.MODIFIERAPPLY = setmenuObject.optString("menu_component_modifier_apply");
                                        setMenuTitle.setmTitleMenuId(setmenuObject.optString("menu_component_id"));

                                        JSONArray productJsonArray = setmenuObject.getJSONArray("product_details");

                                        List<SetMenuModifier> setMenuModifierList = new ArrayList<>();

                                        if (productJsonArray.length() > 0) {
                                            for (int p = 0; p < productJsonArray.length(); p++) {
                                                JSONObject setmenuModifireObject = productJsonArray.getJSONObject(p);

                                                SetMenuModifier setMenuModifier = new SetMenuModifier();

                                                setMenuModifier.setmModifierName(setmenuModifireObject.optString("menu_product_name"));
                                                setMenuModifier.setmModifierPrice(setmenuModifireObject.optString("menu_product_price"));
                                                setMenuModifier.setmModifierSku(setmenuModifireObject.optString("menu_product_sku"));
                                                setMenuModifier.setmModifierId(setmenuModifireObject.optString("menu_product_id"));
                                                setMenuModifier.setmQuantity(Integer.parseInt(setmenuModifireObject.optString("menu_product_qty")));

                                                JSONArray modifierJsonArray = setmenuModifireObject.getJSONArray("modifiers");

                                                List<ModifierHeading> modifierHeadingList = new ArrayList<>();

                                                if (modifierJsonArray.length() > 0) {

                                                    for (int m = 0; m < modifierJsonArray.length(); m++) {
                                                        JSONObject modifierObject = modifierJsonArray.getJSONObject(m);

                                                        ModifierHeading modifierHeading = new ModifierHeading();

                                                        modifierHeading.setmModifierHeading(modifierObject.optString("order_modifier_name"));
                                                        modifierHeading.setmModifierHeadingId(modifierObject.optString("order_modifier_id"));


                                                        JSONArray valueJsonArray = modifierObject.getJSONArray("modifiers_values");

                                                        List<ModifiersValue> modifiersValueList = new ArrayList<>();

                                                        if (valueJsonArray.length() > 0) {
                                                            for (int v = 0; v < valueJsonArray.length(); v++) {
                                                                JSONObject valueObject = valueJsonArray.getJSONObject(v);

                                                                ModifiersValue modifiersValue = new ModifiersValue();

                                                                modifiersValue.setmModifierName(valueObject.optString("order_modifier_name"));
                                                                modifiersValue.setmModifierId(valueObject.optString("order_modifier_id"));
                                                                modifiersValue.setmSubModifierTotal(Integer.valueOf(valueObject.optString("order_modifier_qty")));
                                                                modifiersValue.setmModifierValuePrice(valueObject.optString("order_modifier_price"));

                                                                modifiersValueList.add(modifiersValue);
                                                            }
                                                        }

                                                        modifierHeading.setModifiersList(modifiersValueList);

                                                        modifierHeadingList.add(modifierHeading);

                                                    }
                                                }

                                                setMenuModifier.setModifierHeadingList(modifierHeadingList);
                                                setMenuModifierList.add(setMenuModifier);
                                            }
                                        }

                                        setMenuTitle.setSetMenuModifierList(setMenuModifierList);

                                        setMenuTitleList.add(setMenuTitle);
                                    }


                                } else {

                                }

                                orderHistory.setSetMenuTitleList(setMenuTitleList);

                                orderHistoryList.add(orderHistory);
                            }

                            thanksRecyclerAdapter = new ThanksRecyclerAdapter(mContext, orderHistoryList);
                            orderRecyclerView.setAdapter(thanksRecyclerAdapter);
                            orderRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            orderRecyclerView.setNestedScrollingEnabled(false);

                        } else {
                        }
                    }
                } else {

                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {

                progressDialog.dismiss();
            }
        }
    }

    public void InclusiveGst(Double GrandTotal) {
        Double Grandamt = 0.0;

        Double Ca1 = 1.0 + Double.parseDouble(GlobalValues.GstChargers) / 100;
        Double Ca2 = Double.parseDouble(GlobalValues.GstChargers) / 100;
        Double productc1 = GrandTotal / Ca1;
        Grandamt = productc1 * Ca2;



        /*txtGst.setVisibility(View.VISIBLE);
            //includeheader.setText("GST Inclusive (7%): $ " + String.format("%.2f", Grandamt));
        txtGst.setText(String.format("%.2f", Grandamt));*/
        gstAmount = Double.parseDouble(String.format("%.2f", Grandamt));
        //txtTotal.setText(String.format("%.2f", Grandamt + GrandTotal));
        GlobalValues.GST = Grandamt;


        // txt_insulsive_gst.setText( "GST Inclusive (7%): $ " + String.format("%.2f",Grandamt));
        // txtGST.setText( "$ " + String.format("%.2f",Grandamt));

    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }


    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }


}
