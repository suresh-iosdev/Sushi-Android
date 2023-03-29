package com.app.sushi.tei.fragment.VoucherFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
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

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Voucher.VoucherUsedList;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Vouchers.VoucherUsedAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class EVoucherUsedFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private RecyclerView recyclerViewVouchers;
    private ArrayList<VoucherUsedList> voucherListItems;
    private TextView txtEmpty;

    public EVoucherUsedFragment() {
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
                        //Success
                        JSONObject jsonCommon = responseJSONObject.getJSONObject("common");
                        String imageUrl = jsonCommon.getString("image_source");
                        JSONObject jsonresultSet = responseJSONObject.getJSONObject("result_set");
                        JSONArray jsonArrayresult = jsonresultSet.getJSONArray("used_vouchers");

                        if (jsonArrayresult.length() > 0) {
                            voucherListItems = new ArrayList<>();
                            for (int i = 0; i < jsonArrayresult.length(); i++) {
                                JSONObject jsonObject = jsonArrayresult.getJSONObject(i);
                                VoucherUsedList vouchers = new VoucherUsedList();
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
                                vouchers.setVoucherUsedCount(jsonObject.optString("voucher_used_count"));
                                vouchers.setOrderItemVoucherBalanceQty(jsonObject.optString("order_item_voucher_balance_qty"));
                                voucherListItems.add(vouchers);
                            }

                            VoucherUsedAdapter mVoucherAdapter = new VoucherUsedAdapter(mContext, voucherListItems, imageUrl);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerViewVouchers.setLayoutManager(mLayoutManager);
                            recyclerViewVouchers.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewVouchers.setAdapter(mVoucherAdapter);
                            txtEmpty.setVisibility(View.GONE);
                            recyclerViewVouchers.setVisibility(View.VISIBLE);
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
}
