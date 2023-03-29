package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import com.google.android.material.tabs.TabLayout;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.FiveMenu.FiveMenuPagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import static com.app.sushi.tei.activity.SubCategoryActivity.txtNotificationCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtOrderCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtPromotionCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtVoucherCount;

public class FiveMenuActivity extends AppCompatActivity {

    private Context mContext;
    private TabLayout tabLayoutFiveMenu;
    public ViewPager viewpagerFiveMenu;
    private Toolbar toolbar;
    private ImageView txtTitle;
    private ImageView imgLogo;
    private LinearLayout imgBack;
    private int mTabPosition = 0;
    private TextView txtTabItem = null;
    private FiveMenuPagerAdapter fiveMenuPagerAdapter;

    private String mFiveMenu[] = {"Account", "Orders", "Rewards", "Promotions", "Logout"};
    private int mFiveMenuImage[] = {R.drawable.account_select, R.drawable.orders_unselect, R.drawable.rewards_unselect, R.drawable.promotions_unselect, R.drawable.logout_unselect};
    private int mFiveMenuImgSelect[] = {R.drawable.account_select, R.drawable.orders_select, R.drawable.rewards_select, R.drawable.promotions_select, R.drawable.logout_select};
    private int mFiveMenuImgUnselect[] = {R.drawable.account_unselect, R.drawable.orders_unselect, R.drawable.rewards_unselect, R.drawable.promotions_unselect, R.drawable.logout_unselect};
    private String mFiveMenuBadge[] = {"", "", "", "", ""};
    private int mFrom = 0;

    private View moveIconFromLeft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_five_menu);

        mContext = FiveMenuActivity.this;

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        moveIconFromLeft = findViewById(R.id.moveIconFromLeft);
        moveIconFromLeft.setVisibility(View.VISIBLE);

        imgBack = toolbar.findViewById(R.id.toolbarBack);
        imgLogo = (ImageView) toolbar.findViewById(R.id.toolbarLogo);
        txtTitle =  toolbar.findViewById(R.id.toolbartxtTitle);


        tabLayoutFiveMenu = (TabLayout) findViewById(R.id.tablayoutFiveMenu);
        viewpagerFiveMenu = (ViewPager) findViewById(R.id.viewpagerFiveMenu);


        try {
            mTabPosition = getIntent().getIntExtra("position", 0);
            mFrom = getIntent().getIntExtra(GlobalValues.FROM_KEY, 0);

        } catch (Exception e) {
            e.printStackTrace();
        }

        tabLayoutFiveMenu.setTabGravity(TabLayout.GRAVITY_CENTER);

        viewpagerFiveMenu.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutFiveMenu));

        getActiveCount();

        tabLayoutFiveMenu.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                viewpagerFiveMenu.setCurrentItem(tab.getPosition());

                View v = tab.getCustomView();

                TextView txtView = (TextView) v.findViewById(R.id.txtTabItem);
                TextView txtBadge = (TextView) v.findViewById(R.id.txtBadge);
                ImageView imgTabItem = v.findViewById(R.id.imgTabItem);

                txtView.setTextColor(getResources().getColor(R.color.greendark));
                imgTabItem.setImageResource(mFiveMenuImgSelect[tab.getPosition()]);

                if (tab.getPosition() == 0 || tab.getPosition() == 4) {
                   txtBadge.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v = tab.getCustomView();

                TextView txtView = v.findViewById(R.id.txtTabItem);
                TextView txtBadge = v.findViewById(R.id.txtBadge);
                ImageView imgTabItem = v.findViewById(R.id.imgTabItem);

                txtView.setTextColor(getResources().getColor(R.color.text_color));
                imgTabItem.setImageResource(mFiveMenuImgUnselect[tab.getPosition()]);

                if (tab.getPosition() == 0) {
                   txtBadge.setVisibility(View.GONE);
                }
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }




    @Override
    protected void onResume() {
        super.onResume();
   /*     imgLogo.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.GONE)*/;
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return super.onCreateOptionsMenu(menu);
    }


    private void setTabItem() {

        tabLayoutFiveMenu.setTabGravity(TabLayout.GRAVITY_CENTER);

        for (int k = 0; k < mFiveMenu.length; k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_item1, null);

            txtTabItem =  tabView.findViewById(R.id.txtTabItem);
            TextView txtBadge = tabView.findViewById(R.id.txtBadge);
            ImageView imgTabItem =  tabView.findViewById(R.id.imgTabItem);


            imgTabItem.setVisibility(View.VISIBLE);
            txtBadge.setVisibility(View.VISIBLE);

            txtTabItem.setText(mFiveMenu[k]);
            imgTabItem.setImageResource(mFiveMenuImage[k]);
            txtTabItem.setTextColor(getResources().getColor(R.color.text_color));


            if (k == 0) {
                txtBadge.setVisibility(View.GONE);
                txtTabItem.setTextColor(getResources().getColor(R.color.text_color));

            } else if (k == 1) {
                if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {

                    if (Integer.parseInt(GlobalValues.ORDERCOUNT) > 99) {

                        txtBadge.setText("99+");

                    } else {
                        txtBadge.setText(GlobalValues.ORDERCOUNT);
                    }
                } else {
                    txtBadge.setVisibility(View.GONE);
                }
            } else if (k == 2) {
                if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {

                    txtBadge.setVisibility(View.GONE);
                    txtBadge.setText(GlobalValues.VOUCHERCOUNT);

                } else {
                    txtBadge.setVisibility(View.GONE);
                }
            } else if (k == 3) {
                if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                    txtBadge.setVisibility(View.VISIBLE);
                    txtBadge.setText(GlobalValues.PROMOTIONCOUNT);

                } else {
                    txtBadge.setVisibility(View.GONE);
                }
            } /*else if (k == 4) {
                if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                   txtBadge.setVisibility(View.VISIBLE);
                   txtBadge.setText(GlobalValues.NOTIFYCOUNT);

                } else {
                    txtBadge.setVisibility(View.GONE);
                }
            }*/ else {
                txtBadge.setVisibility(View.GONE);
            }


            tabLayoutFiveMenu.addTab(tabLayoutFiveMenu.newTab().setCustomView(tabView));

        }

        fiveMenuPagerAdapter = new FiveMenuPagerAdapter(getSupportFragmentManager(), mContext, mFiveMenu.length, mFrom);
        viewpagerFiveMenu.setAdapter(fiveMenuPagerAdapter);
        viewpagerFiveMenu.setCurrentItem(mTabPosition);

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

                    setTabItem();

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

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
        }
    }

}







