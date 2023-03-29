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

public class RewardEarnedFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private RecyclerView recyclerViewRewards;
    private ArrayList<Rewards> rewardsearnedlist;
    private OpenSansBoldTextView txtEmpty;
    private String mCustomerId = "";
    private String rewards_count = "";

    public RewardEarnedFragment() {
        // Required empty public constructor
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
        mView = inflater.inflate(R.layout.fragment_reward_rewards, container, false);


        recyclerViewRewards = (RecyclerView) mView.findViewById(R.id.recyclerViewRewards);

        txtEmpty =  mView.findViewById(R.id.txtEmpty);
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
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

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            /*new RewardsEarnedTask().execute(GlobalUrl.REWARD_POINTS_EARNED_URL + "?app_id=" +
                    GlobalValues.APP_ID + "&customer_id=" +
                    Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) + "&limit=&offset=" + "status=A");

                    &crm_memberid=2d0bl4053591&crm_cardno=65000622
                    */
           /* String url="https://ceres.ninjaos.com/api/ascentiscrm/membertranshistory?app_id=48F27C1E-A55A-4DCE-89EC-674F1DA5C960" +
                    "&crm_memberid=2d0bl4053591&crm_cardno=65000622";*/
            String url=  GlobalUrl.MEMBER_HISTORY_URL + "?app_id=" +
                    GlobalValues.APP_ID +
                    "&crm_memberid=" + Utility.readFromSharedPreference(mContext, GlobalValues.MEMBERSHIP_ID) +
                    "&crm_cardno=" + Utility.readFromSharedPreference(mContext, GlobalValues.ASCENTIS_CARD_NO);
            Log.e("TAG","Member_Earned_Url::"+url);
            new RewardsEarnedTask().execute(url);

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

    private class RewardsEarnedTask extends AsyncTask<String, String, String> {

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

                    Log.e("TAG","Member_Earned_Rew::"+ response);

                    responseJSONObject = new JSONObject(response);


                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {
                        //Success

                        JSONObject jsonObject=responseJSONObject.getJSONObject("result_set");

                        JSONArray jsonArrayresult = jsonObject.getJSONArray("earned_points_list");


                        if (jsonArrayresult.length() > 0) {

                            rewardsearnedlist = new ArrayList<>();

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


                              /*  mRewards.setmOrderPrimaryId(jsonObject.getString("order_local_no"));
                                mRewards.setmOrderDate(jsonObject.getString("order_date"));
                                mRewards.setMlhRedeemAmount(jsonObject.getString("order_total_amount"));
                                mRewards.setMlhRedeemPoint(jsonObject.getString("lup_customer_points"));
                                mRewards.setmOrderId(jsonObject.getString("order_id"));
                                mRewards.setmOrderAvailabilityName(jsonObject.getString("order_availability_name"));
                                mRewards.setmDiscountType(jsonObject.optString("lh_status"));
                                mRewards.setlh_from(jsonObject.optString("lh_from"));
                                mRewards.setlh_created_on(jsonObject.optString("lh_created_on"));
                                mRewards.setLh_expiry_on(jsonObject.optString("lh_expiry_on"));
                                mRewards.setlh_reason(jsonObject.optString("lh_reason"));*/
                                rewardsearnedlist.add(mRewards);

                            }

                            RewardsAdapters mRewardsAdapters = new RewardsAdapters(mContext, rewardsearnedlist, RewardsAdapters.REWARD_TYPE_EARNED);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerViewRewards.setLayoutManager(mLayoutManager);
                            recyclerViewRewards.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewRewards.setAdapter(mRewardsAdapters);

                            txtEmpty.setVisibility(View.GONE);

                            recyclerViewRewards.setVisibility(View.VISIBLE);

                        } else {

                            txtEmpty.setVisibility(View.VISIBLE);

                            recyclerViewRewards.setVisibility(View.GONE);


                        }
                    } else {

                        message = responseJSONObject.getString("message");
                         //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart
                        txtEmpty.setText("No Rewards Earned!");
                        txtEmpty.setVisibility(View.VISIBLE);

                        recyclerViewRewards.setVisibility(View.GONE);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

            }

        }
    }
}
