package com.app.sushi.tei.fragment.FiveMenu;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Rewards.Rewards;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.PaymentActivity;
import com.app.sushi.tei.adapter.EwalletAdapter;
import com.app.sushi.tei.fragment.RewardsFragment.RewardEarnedFragment;
import com.app.sushi.tei.fragment.RewardsFragment.RewardRedeemedFragment;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.sushi.tei.activity.SubCategoryActivity.txtNotificationCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtOrderCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtPromotionCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtVoucherCount;


public class FragmentReward extends Fragment {

    private Activity mContext;
    private View mView;
    private TabLayout tablayoutReward;
    private View view1, view3;
    private ViewPager pagerReward;
    private TextView txtRewardPoint;
    private LinearLayout lly_earnedPoints;
    private TextView txt_ewallet;
    private String[] ordertabs = {"Points Earned", "Points Redeemed"};
    private RewardsAdapter mRewardsAdapter;
    private TextView txtTabItem;
    public static EditText edt_enterAmount;
    public static int pos = 10;
    private EwalletAdapter ewalletAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_reward, container, false);

        tablayoutReward = (TabLayout) mView.findViewById(R.id.tablayoutReward);
        pagerReward = (ViewPager) mView.findViewById(R.id.pagerReward);
        txtRewardPoint = (TextView) mView.findViewById(R.id.txtRewardPoint);
        lly_earnedPoints = mView.findViewById(R.id.lly_earnedPoints);
        txt_ewallet = mView.findViewById(R.id.txt_ewallet);
        view1 = (View) mView.findViewById(R.id.view1);
        view3 = (View) mView.findViewById(R.id.view3);
        view1.setVisibility(View.VISIBLE);

        setTabItem();
        getActiveCount();
        getRewardsPoints();



        try {
            //double x_reedempoints = Double.parseDouble(GlobalValues.Customer_reward_point);
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (Utility.networkCheck(mContext)) {
            // showRewardDialog();
            String value = String.format("%.2f", new BigDecimal((GlobalValues.CUSTOMER_REWARD_POINT)));
            lly_earnedPoints.setVisibility(View.VISIBLE);
 //           txtRewardPoint.setText("" + String.valueOf(value) + "  Points");
            String mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            new RewardsEarnedTask().execute(GlobalUrl.REWARD_POINTS_EARNED_URL + "?app_id=" +
                    GlobalValues.APP_ID + "&customer_id=" +
                    Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) + "&limit=&offset=" + "status=A");

        } else {

            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();


        }

        pagerReward.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tablayoutReward));


        tablayoutReward.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_reward);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
                pagerReward.setCurrentItem(tab.getPosition(), true);
                switch (tab.getPosition()) {


                    case 0:

                        view1.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.GONE);
                        break;

                    case 1:

                        view1.setVisibility(View.GONE);
                        view3.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_reward);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        pagerReward.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {
                    case 0:
                        view1.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.GONE);
                        break;

                    case 1:
                        view1.setVisibility(View.GONE);
                        view3.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        txt_ewallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEwalletDialog();
            }
        });
        return mView;
    }

    private void getRewardsPoints() {
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("member_id", Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID));
        String reward_url = GlobalUrl.MEMBER_ENQUIRY_URL;

        new MemberRewards(params).execute(reward_url);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
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

            ;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("count_service::", params[0] + "\n" + countParams);

            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("count_response::", s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObject.getJSONObject("result_set");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");

            //        double x_reedempoints = countJson.optDouble("reward_ponits");

            //        GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;

            //        txtRewardPoint.setText("" + String.valueOf(x_reedempoints) + "  Points");

                  /*  if (!countJson.isNull("reward_ponits_monthly")) {

                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = Double.valueOf(countJson.optString("reward_ponits_monthly"));

                    } else {
                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = 0.00;
                    }*/

                    if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
                        txtOrderCount.setVisibility(View.VISIBLE);
                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
                    } else {
                        txtOrderCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {
                        txtVoucherCount.setVisibility(View.VISIBLE);
                        txtVoucherCount.setText(GlobalValues.VOUCHERCOUNT);
                    } else {
                        txtVoucherCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
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

    private class MemberRewards extends AsyncTask<String, Void, String> {

        private Map<String, String> countParams;

        private ProgressDialog progressDialog;

        public MemberRewards(Map<String, String> mapData) {
            countParams = mapData;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("Member_reward_service::", params[0] + "\n" + countParams);

            String response = WebserviceAssessor.postRequest(mContext,params[0],countParams);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG","Member_reward_response::"+ s);

                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    JSONObject rewardJson = jsonObject.getJSONObject("result_set");
                    JSONArray CardListArray=rewardJson.getJSONArray("CardLists");
                    for (int i=0;i<CardListArray.length();i++){
                        JSONObject cardlistobj=CardListArray.getJSONObject(i);

                        double x_reedempoints = cardlistobj.optDouble("TotalPointsBAL");
                        GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;
                        txtRewardPoint.setText("" + String.valueOf(x_reedempoints) + "  Points");
                    }
                }else {
                    txtRewardPoint.setText("" + 0 + " Points");
                }
               /* if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObject.getJSONObject("result_set");
                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");

                    double x_reedempoints = countJson.optDouble("reward_ponits");

                    GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;

                    txtRewardPoint.setText("" + String.valueOf(x_reedempoints) + "  Points");

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

                    if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {
                        txtVoucherCount.setVisibility(View.VISIBLE);
                        txtVoucherCount.setText(GlobalValues.VOUCHERCOUNT);
                    } else {
                        txtVoucherCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.VISIBLE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }

                } else {

                }*/
                progressDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private void openEwalletDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.fragment_ewallet);
        dialog.setCanceledOnTouchOutside(true);
        if (!dialog.isShowing())
            dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity = Gravity.CENTER;
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        RecyclerView recyclerview_ewalletAmount = dialog.findViewById(R.id.recyclerview_ewalletAmount);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        edt_enterAmount = dialog.findViewById(R.id.edt_enterAmount);
        TextView txtContinue = dialog.findViewById(R.id.txt_Continue);



        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("10");
        arrayList.add("20");
        arrayList.add("50");
        arrayList.add("100");
        arrayList.add("150");
        arrayList.add("500");

        ewalletAdapter = new EwalletAdapter(mContext, arrayList);
        recyclerview_ewalletAmount.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        recyclerview_ewalletAmount.setAdapter(ewalletAdapter);

        edt_enterAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                pos = 10;
                ewalletAdapter.notifyDataSetChanged();
            }
        });

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!(edt_enterAmount.getText().toString().trim().equalsIgnoreCase("") || edt_enterAmount.getText().toString().trim().equalsIgnoreCase("0"))) {
                    GlobalValues.eWalletAmount = edt_enterAmount.getText().toString();
                }
                if (GlobalValues.eWalletAmount.equalsIgnoreCase("") || GlobalValues.eWalletAmount.equalsIgnoreCase("0")) {
                    Toast.makeText(mContext, "Please select topup amount", Toast.LENGTH_SHORT).show();
                } else {
                    new PriceForPoints().execute(GlobalUrl.PRICE_FOR_POINTS_URL + "?app_id=" +
                            GlobalValues.APP_ID + "&loyality_points=" + GlobalValues.eWalletAmount.replace("$",""));
                }
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private class PriceForPoints extends AsyncTask<String, String, String> {

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
                Log.e("send_data", params[0]);
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
                        Intent intent = new Intent(mContext, PaymentActivity.class);
                        intent.putExtra("loyality_point_price", responseJSONObject.getString("loyality_point_price"));
                        intent.putExtra("rewardPointsPayment", true);
                        startActivity(intent);
                    } else {}

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

            }

        }
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

                    responseJSONObject = new JSONObject(response);


                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {
                        //Success


                        JSONArray jsonArrayresult = responseJSONObject.getJSONArray("result_set");


                        if (jsonArrayresult.length() > 0) {


                            for (int i = 0; i < jsonArrayresult.length(); i++) {

                                JSONObject jsonObject = jsonArrayresult.getJSONObject(i);

                                Rewards mRewards = new Rewards();

                                mRewards.setmOrderPrimaryId(jsonObject.getString("order_local_no"));
                                mRewards.setmOrderDate(jsonObject.getString("order_date"));
                                mRewards.setMlhRedeemAmount(jsonObject.getString("order_total_amount"));
                                mRewards.setMlhRedeemPoint(jsonObject.getString("lup_customer_points"));
                                mRewards.setmOrderId(jsonObject.getString("order_id"));
                                mRewards.setmOrderAvailabilityName(jsonObject.getString("order_availability_name"));
                                mRewards.setmDiscountType(jsonObject.optString("lh_status"));
                                mRewards.setlh_from(jsonObject.optString("lh_from"));
                                //mRewards.setlh_created_on(jsonObject.optString("lh_created_on"));
                                mRewards.setlh_reason(jsonObject.optString("lh_reason"));
                                GlobalValues.REWARDS_STATUS = jsonObject.optString("lh_status");
                               /* if(Double.parseDouble(mRewards.getMlhRedeemPoint()) > 0.0) {
                                    lly_earnedPoints.setVisibility(View.VISIBLE);
                                    txtRewardPoint.setText("" + String.valueOf(mRewards.getMlhRedeemPoint()) + "  Points");
                                }*/

                            }
                        } else {

                        }
                    } else {


                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {



            }

        }
    }


    public void showRewardDialog() {

        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext);
        alertDialog.setTitle("Note");
        alertDialog.setMessage("Spend $500 to become a Kaki Member Today !! Earn points for your purchases & more !!");
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
       /* alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });*/
        alertDialog.show();

    }


    public class RewardsAdapter extends FragmentStatePagerAdapter {

        public RewardsAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int index) {

            switch (index) {
                case 0:
                    return new RewardEarnedFragment();

                case 1:
                    return new RewardRedeemedFragment();
            }

            return null;
        }

        @Override
        public int getCount() {
            return ordertabs.length;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void setTabItem() {

        for (int k = 0; k < ordertabs.length; k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_rewards_item, null);

            txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_reward);

            txtTabItem.setText(ordertabs[k]);

            if (k == 1) {

                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {

                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }

            if (k == 0) {

                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {

                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }


            tablayoutReward.addTab(tablayoutReward.newTab().setCustomView(tabView));
        }


        mRewardsAdapter = new RewardsAdapter(getChildFragmentManager());
        pagerReward.setAdapter(mRewardsAdapter);

    }

}
