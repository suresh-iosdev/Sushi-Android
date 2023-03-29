package com.app.sushi.tei.fragment.RewardsFragment;

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
import android.widget.Toast;

import com.app.sushi.tei.CustomViews.CustomTextView.Opensans.OpenSansBoldTextView;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Rewards.Rewards;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Rewards.RewardsAdapters;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class RewardRedeemedFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private RecyclerView recyclerviewRedeemed;
    private ArrayList<Rewards> rewardsearnedlist;

    private OpenSansBoldTextView txtEmpty;
    private String mCustomerId = "";
    private RewardsAdapters mRewardsAdapters;

    public RewardRedeemedFragment() {
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
        mView = inflater.inflate(R.layout.fragment_reward_redeemed, container, false);

        txtEmpty =  mView.findViewById(R.id.txtEmpty);

        recyclerviewRedeemed = (RecyclerView) mView.findViewById(R.id.recyclerviewRedeemed);


        return mView;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        if (isVisibleToUser) {

        }

    }
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (networkCheck()) {

           /* mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            new RewardsRedeenedTask().execute(GlobalUrl.REWARD_POINTS_REDEEMED_URL + "?status=A&app_id=" +
                    GlobalValues.APP_ID + "&customer_id=" + mCustomerId);*/

           /* String url="https://ceres.ninjaos.com/api/ascentiscrm/membertranshistory?app_id=48F27C1E-A55A-4DCE-89EC-674F1DA5C960" +
                    "&crm_memberid=2d0bl4053591&crm_cardno=65000622";*/
            String url=  GlobalUrl.MEMBER_HISTORY_URL + "?app_id=" +
                    GlobalValues.APP_ID +
                    "&crm_memberid=" + Utility.readFromSharedPreference(mContext, GlobalValues.MEMBERSHIP_ID) +
                    "&crm_cardno=" + Utility.readFromSharedPreference(mContext, GlobalValues.ASCENTIS_CARD_NO);

            new RewardsRedeenedTask().execute(url);

        } else {

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    private class RewardsRedeenedTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
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
                        rewardsearnedlist=new ArrayList<>();

                        JSONObject jsonObject=responseJSONObject.getJSONObject("result_set");

                        JSONArray jsonArrayresult = jsonObject.getJSONArray("redeemed_points_list");

                        if (jsonArrayresult.length() > 0) {

                            for (int i = 0; i < jsonArrayresult.length(); i++) {

                                JSONObject earned_points_jsonObject = jsonArrayresult.getJSONObject(i);

                                Rewards mRewards = new Rewards();

                                mRewards.setAutoID(earned_points_jsonObject.getString("AutoID"));
                                mRewards.setOrderAmount(earned_points_jsonObject.getString("OrderAmount"));
                                mRewards.setPoints(earned_points_jsonObject.getString("Points"));
                                mRewards.setReceiptNo(earned_points_jsonObject.getString("ReceiptNo"));
                                mRewards.setRemark(earned_points_jsonObject.getString("Remark"));
                                mRewards.setTransactDate(earned_points_jsonObject.getString("TransactDate"));
                                mRewards.setTransactTime(earned_points_jsonObject.getString("TransactTime"));

                               /* JSONObject jsonObject = jsonArrayresult.getJSONObject(i);

                                Rewards mRewards = new Rewards();

                                mRewards.setmOrderPrimaryId(jsonObject.getString("order_local_no"));
                                mRewards.setmOrderDate(jsonObject.getString("order_date"));
                                mRewards.setMlhRedeemAmount(jsonObject.getString("order_total_amount"));
                                mRewards.setMlhRedeemPoint(jsonObject.getString("lh_redeem_point"));
                                mRewards.setmOrderId(jsonObject.getString("order_id"));
                                mRewards.setmOrderAvailabilityName(jsonObject.getString("order_availability_name"));
                                mRewards.setmDiscountType(jsonObject.optString("lh_redeem_status"));
                                mRewards.setlh_reason(jsonObject.optString("lh_reason"));*/
                              /*  mRewards.setlh_from(jsonObject.optString("lh_from"));
                                mRewards.setlh_created_on(jsonObject.optString("lh_created_on"));*/

                                rewardsearnedlist.add(mRewards);

                            }

                             mRewardsAdapters = new RewardsAdapters(getActivity(), rewardsearnedlist, RewardsAdapters.REWARD_TYPE_REDEEMED);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerviewRedeemed.setLayoutManager(mLayoutManager);
                            recyclerviewRedeemed.setItemAnimator(new DefaultItemAnimator());
                            recyclerviewRedeemed.setAdapter(mRewardsAdapters);

                            txtEmpty.setVisibility(View.GONE);

                            recyclerviewRedeemed.setVisibility(View.VISIBLE);

                        } else {

                            txtEmpty.setVisibility(View.VISIBLE);

                            recyclerviewRedeemed.setVisibility(View.GONE);
                        }

                    } else {

                        message = responseJSONObject.getString("message");
                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart

                        txtEmpty.setVisibility(View.VISIBLE);

                        recyclerviewRedeemed.setVisibility(View.GONE);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }


            } else {


            }


        }
    }

}
