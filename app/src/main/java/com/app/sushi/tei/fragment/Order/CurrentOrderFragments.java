package com.app.sushi.tei.fragment.Order;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Order.ItemResponse;
import com.app.sushi.tei.Model.Order.OrderDetail;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.SubCategoryActivity;
import com.app.sushi.tei.adapter.Order.OrderSubAdapter;
import com.app.sushi.tei.dialog.ClearCartMessageDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class CurrentOrderFragments extends Fragment {
    RecyclerView recyclerView;
    OrderSubAdapter orderSubAdapter;
    List<OrderDetail> ordersArrayList;

    private static final String order_status = "order_status";
    private static final String order_type = "id";
    private boolean mFlag = false;

    //Order Status --> CURRENT , PAST
    //Order TYPE --> ORDER, RESERVATION and CATERING

    private Context mContext;
    public String orderStatus;
    public String mAvailId = "";

    TextView noOrderTextView;
    private int bentoCount = 0, deliveryCount = 0;
    private String mCustomerId;
    private DatabaseHandler databaseHandler;



    public static CurrentOrderFragments newInstance(String orderStatus, String orderType) {

        Bundle args = new Bundle();

        CurrentOrderFragments fragment = new CurrentOrderFragments();
        args.putString(order_status, orderStatus);
        args.putString(order_type, orderType);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        orderStatus = getArguments().getString(order_status);
        mAvailId = getArguments().getString(order_type);


        if (mAvailId.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
            mFlag = true;
        } else if (mAvailId.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
            mFlag = false;
        }

        View view = inflater.inflate(R.layout.fragment_orderlist, container, false);
        recyclerView = (RecyclerView) view.findViewById(R.id.pastorderlistrecyclerview);
        noOrderTextView = (TextView) view.findViewById(R.id.noOrderTextView);
        databaseHandler = DatabaseHandler.getInstance(mContext);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setNestedScrollingEnabled(false);

        if (orderStatus.equalsIgnoreCase("P")) {
            noOrderTextView.setText("No Past Orders");
        } else {
            noOrderTextView.setText("No Current Orders");
        }


        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


        if (isVisibleToUser) {
            if (getActivity() != null) {


            }
        }
    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (networkCheck()) {
            String OrderUrl = GlobalUrl.ORDER_HISTORY_URL + "?app_id=" + GlobalValues.APP_ID
                    + "&order_status=C&" +
                    "customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);



            new OrdersTask().execute(OrderUrl);
        }
    }

    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private class OrdersTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        String status;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

            deliveryCount = 0;
            bentoCount = 0;
        }

        @Override
        protected String doInBackground(String... params) {

            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    Log.e("Orders_Response", response);

                    responseJSONObject = new JSONObject(response);

                    status = responseJSONObject.getString("status");


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

                            OrderDetail orderDetail = new OrderDetail();

                            JSONObject jsonObject = resultSetJSONArray.getJSONObject(i);

                            String outlet_name = jsonObject.getString("outlet_name");
//                            String outlet_operation_hr = jsonObject.getString("operational_hr");

                            String status_name = jsonObject.getString("status_name");
                            String packaging_charge="";
                            if (!jsonObject.isNull("order_packaging_charge")) {
                                packaging_charge = jsonObject.getString("order_packaging_charge");
                            }

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

//                            String order_tax_charge = jsonObject.getString("order_tax_charge_inclusive");
                            String order_tax_charge = jsonObject.getString("order_tax_charge");
                            String order_discount_applied = jsonObject.getString("order_discount_applied");
                            String order_discount_amount = jsonObject.getString("order_discount_amount");
                            String order_sub_total = jsonObject.getString("order_sub_total");
                            String order_total_amount = jsonObject.getString("order_total_amount");
                            String order_payment_mode = jsonObject.getString("order_payment_mode");
                            String order_date = jsonObject.getString("order_date");
                            String order_status = jsonObject.getString("order_status");
                            String order_availability_id = jsonObject.getString("order_availability_id");
                            String order_remarks = jsonObject.getString("order_remarks");
//                            String order_someone_remarks = jsonObject.getString("order_someone_remarks");
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

                            String order_is_timeslot = jsonObject.optString("order_is_timeslot");

                            String order_pickup_time_slot_from = jsonObject.getString("order_pickup_time_slot_from");
                            String order_pickup_time_slot_to = jsonObject.getString("order_pickup_time_slot_to");
                            String order_delivery_time_slot_from = jsonObject.optString("order_delivery_time_slot_from");
                            String order_delivery_time_slot_to = jsonObject.optString("order_delivery_time_slot_to");

//                            String order_discount_both = jsonObject.getString("order_discount_both");
//                            String order_points_discount_amount = jsonObject.getString("order_points_discount_amount");
                            String order_additional_delivery = jsonObject.optString("order_additional_delivery");
                            String order_promocode_name = jsonObject.optString("order_promocode_name");

                            String order_eWallet = jsonObject.optString("order_ewallet_amount");



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
                            Log.e("TAG","OrderPackChargeTest::"+packaging_charge);

                            orderDetail.setOrder_packaging_charge(packaging_charge);

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
                            orderDetail.setOrder_voucher_discount_amount(jsonObject.getString("order_voucher_discount_amount"));
//                            orderDetail.setOrder_tax_calculate_amount(jsonObject.getString("order_tax_calculate_amount_inclusive"));
                            orderDetail.setOrder_tax_calculate_amount(jsonObject.getString("order_tax_calculate_amount"));

                            orderDetail.setOrder_created_date(jsonObject.getString("order_created_on"));

                            orderDetail.setUnitNo(jsonObject.getString("order_customer_unit_no1") +
                                    jsonObject.getString("order_customer_unit_no2"));

                            orderDetail.setOrder_promocode_name(order_promocode_name);
                            orderDetail.seteWalletAmount(order_eWallet);
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

                            orderDetail.setOrder_is_timeslot(order_is_timeslot);
                            orderDetail.setOrder_pickup_time_slot_from(order_pickup_time_slot_from);
                            orderDetail.setOrder_pickup_time_slot_to(order_pickup_time_slot_to);
                            orderDetail.setOrder_delivery_time_slot_from(order_delivery_time_slot_from);
                            orderDetail.setOrder_delivery_time_slot_to(order_delivery_time_slot_to);

//                            orderDetail.setOrder_discount_both(order_discount_both);
//                            orderDetail.setOrder_points_discount_amount(order_points_discount_amount);
//                            orderDetail.setOrder_qNumber(jsonObject.getString("order_qnumber"));
                            orderDetail.setOrder_additional_delivery(order_additional_delivery);
                            orderDetail.setOrder_remarks(order_remarks);
//                            orderDetail.setOrder_someone_remarks(order_someone_remarks);
                            orderDetail.setOrder_customer_unit_no1(order_customer_unit_no1);
                            orderDetail.setOrder_customer_unit_no2(order_customer_unit_no2);
//                            orderDetail.setOperational_hr(outlet_operation_hr);



                            JSONArray itemValueArray = jsonObject.getJSONArray("items");


                            List<ItemResponse> itemValueArrayList = new ArrayList<>();

                            if (itemValueArray.length() > 0) {
                                for (int v = 0; v < itemValueArray.length(); v++) {
                                    JSONObject value = itemValueArray.getJSONObject(v);

                                    ItemResponse items = new ItemResponse();

                                    items.setItemName(value.getString("item_name"));
                                    items.setItemQty(value.getString("item_qty"));
                                    items.setItemTotalAmount(value.getString("item_total_amount"));
                                    items.setItemSpecification(value.getString("item_specification"));


                                    itemValueArrayList.add(items);

                                }

                            }

                            orderDetail.setItems(itemValueArrayList);

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


                            if (mFlag) {
                                if (orderDetail.getOrder_availability_id().equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                    orderDetailList.add(orderDetail);
                                    deliveryCount++;
                                } else if (orderDetail.getOrder_availability_id().equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
                                    orderDetailList.add(orderDetail);
                                    deliveryCount++;
                                }

                            }

                            if (!mFlag) {
                                if (orderDetail.getOrder_availability_id().equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    orderDetailList.add(orderDetail);
                                    bentoCount++;

                                }
                            }

                            ordersArrayList = orderDetailList;


                            if (ordersArrayList.size() > 0) {
                                noOrderTextView.setVisibility(View.GONE);
                                recyclerView.setVisibility(View.VISIBLE);
                                orderSubAdapter = new OrderSubAdapter(getActivity(), ordersArrayList);
                                RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
                                recyclerView.setLayoutManager(layoutManager);
                                recyclerView.setHasFixedSize(true);
                                recyclerView.setItemAnimator(new DefaultItemAnimator());
                                recyclerView.setNestedScrollingEnabled(true);
                                recyclerView.setAdapter(orderSubAdapter);

                            } else {
                                recyclerView.setVisibility(View.GONE);
                                noOrderTextView.setVisibility(View.VISIBLE);
                            }

                            orderSubAdapter.setOnItemClick(new IOnItemClick() {
                                @Override
                                public void onItemClick(View v, final int position) {


                                    String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                                    if (minQual.equalsIgnoreCase("")) {
                                        minQual = "0";
                                    }
                                    if (Integer.parseInt(minQual) >= 1) {
                                        String message = "You are about to clear your cart by clicking Order Again";
                                        new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
                                            @Override
                                            public void selectedAction(String action) {
                                                if (action.equalsIgnoreCase("YES")) {
                                                    Map<String, String> mapString = new HashMap<>();
                                                    mapString.put("app_id", GlobalValues.APP_ID);
                                                    mapString.put("customer_id", Utility.readFromSharedPreference(mContext,GlobalValues.CUSTOMERID));
                                                    mapString.put("order_id", ordersArrayList.get(position).getOrder_id());
                                                    String url = GlobalUrl.ORDER_AGAIN_URL;
                                                    new OrderAgainTask(mapString).execute(url);
                                                }
                                            }
                                        });
                                    } else {
                                        Map<String, String> mapString = new HashMap<>();
                                        mapString.put("app_id", GlobalValues.APP_ID);
                                        mapString.put("customer_id", Utility.readFromSharedPreference(mContext,GlobalValues.CUSTOMERID));
                                        mapString.put("order_id", ordersArrayList.get(position).getOrder_id());
                                        String url = GlobalUrl.ORDER_AGAIN_URL;
                                        new OrderAgainTask(mapString).execute(url);
                                    }
                                }
                            });
                        }

//TODO
                      /*  if(mFlag) {
                            orderCountTextView.setText(deliveryCount + "");
                        }else {
                            txtBentoCount.setText(bentoCount+"");
                        }*/

                    } else {

                        recyclerView.setVisibility(View.GONE);
                        noOrderTextView.setVisibility(View.VISIBLE);
                        //TODO
                      /*  orderCountTextView.setText("0");
                        txtBentoCount.setText("0");*/
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                    recyclerView.setVisibility(View.GONE);
                    noOrderTextView.setVisibility(View.VISIBLE);
                    //TODO
                  /*  orderCountTextView.setText("0");
                    txtBentoCount.setText("0");*/
                }


            } else {
                recyclerView.setVisibility(View.GONE);
                noOrderTextView.setVisibility(View.VISIBLE);
                //TODO
              /*  orderCountTextView.setText("0");
                txtBentoCount.setText("0");*/
            }
        }
    }

    private class OrderAgainTask extends AsyncTask<String, Void, String>{
        ProgressDialog progressDialog;
        private Map<String, String> orderAgainParams;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }


        public OrderAgainTask(Map<String, String> params) {
            orderAgainParams = params;
        }

        @Override
        protected String doInBackground(String... params) {

            String response = WebserviceAssessor.postRequest(mContext, params[0], orderAgainParams);


            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                    try {

                        responseJSONObject = new JSONObject(response);
                        String status = responseJSONObject.getString("status");
                        if (status.equals("ok")) {//Success
                            getCartCount();
                            Intent intent = new Intent(mContext, SubCategoryActivity.class);
                            startActivity(intent);
                            getActivity().finish();
                        }
                    }catch (Exception e){

                    }
            }
        }
    }

    private void getCartCount() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            //mReferenceId = "";
        } else {
            mCustomerId = "";
            //mReferenceId = Utility.getReferenceId(mContext);
        }

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + mCustomerId +
                    "&reference_id=" + "" +
                    "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;


            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                new CartListTask().execute(url);
            }

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private class CartListTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
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

                    if (jsonObject.has("result_set")) {
                        JSONObject jsonContent = jsonObject.getJSONObject("result_set");

                        JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");


                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                                jsonCartDetails.optString("cart_total_items"));

                        if (Integer.valueOf(jsonCartDetails.optString("cart_total_items")) > 0) {
                            Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonContent.toString());

                        } else {
                            Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");
                        }

                        /*cart_availability_id = jsonCartDetails.optString("cart_availability_id");

                        cart_availability_name = jsonCartDetails.optString("cart_availability_name");
*/

                        //ProductListActivity.isInvalidated = false;

                        getActivity().invalidateOptionsMenu();

                        JSONArray jsonCartItem = jsonContent.getJSONArray("cart_items");

                        if (jsonCartItem.length() > 0) {

                            for (int c = 0; c < jsonCartItem.length(); c++) {

                                JSONObject jsonItem = null;
                                jsonItem = jsonCartItem.getJSONObject(c);

                                databaseHandler.updateQty(jsonItem.getString("cart_item_product_id"), jsonItem.getString("cart_item_qty"));
                            }
                        }
                    } else {
                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, "");
                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");
                        Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT, "");
                        getActivity().invalidateOptionsMenu();
                    }
                } else {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
                 String id = Utility.getCartAvailabilityId(mContext);
            }
        }
    }
}
