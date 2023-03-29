package com.app.sushi.tei.fragment.VoucherFragment;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Voucher.VoucherListItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Vouchers.VouchersAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.sushi.tei.activity.SubCategoryActivity.txtNotificationCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtOrderCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtPromotionCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtVoucherCount;

public class EVoucherFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private RecyclerView recyclerViewVouchers;
    private ArrayList<VoucherListItem> voucherList;
    private TextView txtEmpty;
    Map<String, String> params;
    private String mCustomerId = "", mReferenceId = "";

    public EVoucherFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_e_voucher, container, false);


        recyclerViewVouchers = (RecyclerView) mView.findViewById(R.id.recyclerViewVouchers);

        txtEmpty =  mView.findViewById(R.id.txtEmpty);
        return mView;
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

            new VouchersTask().execute(GlobalUrl.VOUCHER_URL + "?app_id=" +
                    GlobalValues.APP_ID + "&customer_id=" +
                    Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

        } else {

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

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

    private class VouchersTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = WebserviceAssessor.getData(params[0]);


            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);


                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {
                        JSONObject jsonCommon = responseJSONObject.getJSONObject("common");

                        String imageUrl = jsonCommon.getString("image_source");

                        JSONObject jsonresultSet = responseJSONObject.getJSONObject("result_set");

                        JSONArray jsonArrayresult = jsonresultSet.getJSONArray("voucher_list");

                        if (jsonArrayresult.length() > 0) {

                            voucherList = new ArrayList<>();

                            for (int i = 0; i < jsonArrayresult.length(); i++) {

                                JSONObject jsonObject = jsonArrayresult.getJSONObject(i);

                                VoucherListItem vouchers = new VoucherListItem();

                                vouchers.setOrderItemProductId(jsonObject.getString("order_item_product_id"));
                                vouchers.setOrderItemId(jsonObject.getString("order_item_id"));
                                vouchers.setVoucherReedmed(jsonObject.getString("voucher_reedmed"));
                                vouchers.setExpiryDate(jsonObject.getString("expiry_date"));
                                vouchers.setItemName(jsonObject.getString("item_name"));
                                vouchers.setItemQty(jsonObject.getString("item_qty"));
                                vouchers.setItemUnitPrice(jsonObject.optString("item_unit_price"));
                                vouchers.setItemSku(jsonObject.optString("item_sku"));
                                vouchers.setItemProductId(jsonObject.optString("item_product_id"));
                                vouchers.setOrderAvailabilityId(jsonObject.optString("order_availability_id"));
                                vouchers.setOrderOutletId(jsonObject.optString("order_outlet_id"));
                                vouchers.setProductThumbnail(jsonObject.optString("product_thumbnail"));
                                vouchers.setProductShortDescription(jsonObject.optString("product_short_description"));
                                vouchers.setProductLongDescription(jsonObject.optString("product_long_description"));
                                vouchers.setProductVoucherPoints(jsonObject.optString("product_voucher_points"));
                                vouchers.setProductVoucher(jsonObject.optString("product_voucher"));
                                vouchers.setProductType(jsonObject.optString("product_type"));
                                vouchers.setProductStatus(jsonObject.optString("product_status"));
                                vouchers.setOrderItemVoucherBalanceQty(jsonObject.optString("order_item_voucher_balance_qty"));
                                voucherList.add(vouchers);
                            }

                            VouchersAdapter mVoucherAdapter = new VouchersAdapter(mContext, voucherList, imageUrl, "1");
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerViewVouchers.setLayoutManager(mLayoutManager);
                            recyclerViewVouchers.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewVouchers.setAdapter(mVoucherAdapter);

                            txtEmpty.setVisibility(View.GONE);

                            recyclerViewVouchers.setVisibility(View.VISIBLE);

                            mVoucherAdapter.SetOnItemClickListener(new VouchersAdapter.VoucherItemClick() {
                                @Override
                                public void onItemClick(View v, String productQty, final int i) {

                                    if (voucherList.get(i).getProductVoucher().equalsIgnoreCase("f")) {
                                        final String url = GlobalUrl.ADD_CART_VOUCHER_URL;
                                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                                            mReferenceId = Utility.getReferenceId(mContext);

                                        } else {
                                            try {
                                                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                                                if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                                    // TODO:
                                                    //    ActivityCompat#requestPermissions
                                                    // here to request the missing permissions, and then overriding
                                                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                                    //                                          int[] grantResults)
                                                    // to handle the case where the user grants the permission. See the documentation
                                                    // for ActivityCompat#requestPermissions for more details.
                                                    return;
                                                }
                                                GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                                                mReferenceId = GlobalValues.DEVICE_ID;

                                            } catch (Exception e) {
                                                GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                                                mReferenceId = GlobalValues.DEVICE_ID;

                                            } finally {
                                                mCustomerId = "";
                                            }
                                        }
                                        params = new HashMap<String, String>();

                                        if (Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID).equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                            params.put("availability_name", "DELIVERY");
                                        } else {
                                            params.put("availability_name", "TAKEAWAY");
                                        }

                                        params.put("order_availability_id", voucherList.get(i).getOrderAvailabilityId());
                                        params.put("order_item_id", voucherList.get(i).getOrderItemId());
                                        params.put("order_outlet_id", voucherList.get(i).getOrderOutletId());
                                        params.put("app_id", GlobalValues.APP_ID);
                                        params.put("product_id", voucherList.get(i).getItemProductId());
                                        //params.put("product_type", voucherList.get(i).getProductType());
                                        params.put("product_name", voucherList.get(i).getItemName());
                                        params.put("product_sku", voucherList.get(i).getItemSku());
                                        params.put("product_image", voucherList.get(i).getProductThumbnail());
                                        params.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                                        params.put("product_unit_price", "0");
                                        params.put("product_qty", productQty);
                                        params.put("product_total_price", "0");
                                        params.put("voucher_gift_name", "");
                                        params.put("voucher_gift_email", "");
                                        params.put("voucher_gift_message", "");
                                        params.put("product_voucher_points", voucherList.get(i).getProductVoucherPoints());
                                        params.put("reference_id", "");
                                        params.put("customer_id", mCustomerId);

                                        new VoucherListTask(params, i).execute(url);

                                    } else {
                                        String url = GlobalUrl.VOUCHER_REDEEM_URL;
                                        Map<String, String> params = new HashMap<String, String>();
                                        params.put("app_id", GlobalValues.APP_ID);
                                        params.put("product_qty", productQty);
                                        params.put("product_voucher_points", voucherList.get(i).getProductVoucherPoints());
                                        params.put("customer_id", mCustomerId);
                                        params.put("order_item_id", voucherList.get(i).getOrderItemId());

                                        new VoucherListTask(params, i).execute(url);
                                    }
                                }
                            });

                        } else {

                            txtEmpty.setVisibility(View.VISIBLE);

                            recyclerViewVouchers.setVisibility(View.GONE);


                        }
                    } else {

                        message = responseJSONObject.getString("message");

                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart
                        txtEmpty.setText("No Rewards Earned!");
                        txtEmpty.setVisibility(View.VISIBLE);

                        recyclerViewVouchers.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

            }

        }
    }

    public class VoucherListTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        private Map<String, String> voucherParams;
        private int i;

        public VoucherListTask(Map<String, String> voucherParams, int i) {
            this.voucherParams = voucherParams;
            this.i = i;
        }

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

            String response = WebserviceAssessor.postRequest(mContext, params[0], voucherParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                getActiveCount();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private void getActiveCount() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            //Promotion count and Reawrd points
            Map<String, String> mapData = new HashMap<>();
            mapData.put("app_id", GlobalValues.APP_ID);

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                mapData.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

            } else {
                mapData.put("customer_id", Utility.getReferenceId(mContext));
            }

            JSONArray jsonArray = new JSONArray();
            jsonArray.put("order");
            jsonArray.put("promotionwithoutuqc");
            jsonArray.put("reward_monthly");
            jsonArray.put("order_all");
            jsonArray.put("notify");
            jsonArray.put("reward");
            mapData.put("act_arr", jsonArray.toString());

            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            new ActivCountTask(mapData).execute(url);
        }
    }

    private class ActivCountTask extends AsyncTask<String, Void, String> {

        private Map<String, String> countParams;

        private ProgressDialog progressDialog;

        public ActivCountTask(Map<String, String> mapData) {
            countParams = mapData;

        }

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

                    JSONObject countJson = jsonObject.getJSONObject("result_set");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");

                    double x_reedempoints = countJson.optDouble("reward_ponits");

                    GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;


                    if (!countJson.isNull("reward_ponits_monthly")) {

                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = Double.valueOf(countJson.optString("reward_ponits_monthly"));

                    } else {
                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = 0.00;
                    }

                    if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
                        txtOrderCount.setVisibility(View.VISIBLE);
                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
                    } else {
                        txtOrderCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {

                        txtVoucherCount.setVisibility(View.VISIBLE);
                        txtVoucherCount.setText(GlobalValues.VOUCHERCOUNT);

                    } else {
                        txtVoucherCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.VISIBLE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }

                } else {

                }
                progressDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }
}
