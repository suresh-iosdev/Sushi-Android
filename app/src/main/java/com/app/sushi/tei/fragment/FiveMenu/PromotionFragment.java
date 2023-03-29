package com.app.sushi.tei.fragment.FiveMenu;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AlertDialog;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.OrderSummaryActivity;
import com.app.sushi.tei.adapter.Promotion.PromotionPagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyRedeem;
import static com.app.sushi.tei.activity.OrderSummaryActivity.mPromotionAmount;
import static com.app.sushi.tei.activity.OrderSummaryActivity.promotionAmount;
import static com.app.sushi.tei.activity.OrderSummaryActivity.promotionID;
import static com.app.sushi.tei.activity.SubCategoryActivity.cart_sub_total;

/**
 * Created by jenifas on 12/21/2017.
 */

public class PromotionFragment extends Fragment {

    private ViewPager viewPagerPromotion;
    private TabLayout tabLayoutPromotion;
    private PromotionPagerAdapter promotionAdapter;
    private Context mContext;
    RelativeLayout layoutApply;
    LinearLayout claimLayout;
    private String[] promotionTabs = {"Promo Earned", "Promo Redeemed"};
    EditText edtPromotion;
    private View view1, view3;
    String mPromocode = "";

    static Context context;
    private int mFrom = 0;
    TextView toolbar_title, cartCountTextView;
    AlertDialog alertDialog;

    String from = "";
    private Bundle bundle;

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_promotion, container, false);


        viewPagerPromotion = (ViewPager) view.findViewById(R.id.viewPagerPromotion);

        edtPromotion = (EditText) view.findViewById(R.id.edtPromotion);


        tabLayoutPromotion = (TabLayout) view.findViewById(R.id.tabLayoutPromotion);
        // tabLayoutPromotion.setupWithViewPager(viewPagerPromotion);
        try {
            bundle = getArguments();
            if (bundle != null) {

                mFrom = bundle.getInt(GlobalValues.FROM_KEY);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        claimLayout = (LinearLayout) view.findViewById(R.id.claimLayout);

        layoutApply = (RelativeLayout) view.findViewById(R.id.layoutApply);


        view1 = (View) view.findViewById(R.id.view1);
        view3 = (View) view.findViewById(R.id.view3);
        view1.setVisibility(View.VISIBLE);

        setTabItem();

        viewPagerPromotion.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayoutPromotion));



        /*if (getIntent() != null) {


            if (getIntent().hasExtra("FROM")) {

                from = getIntent().getStringExtra("FROM");

                if (from.equals("CHECKOUT")) {

                    searcImageView.setVisibility(View.GONE);
                    // cartImageView.setVisibility(View.GONE);
                    cartLayout.setVisibility(View.GONE);

                    claimLayout.setVisibility(View.GONE);

                }

            }

        }*/


        layoutApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);

                if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty() && !GlobalValues.DELIVERY_DATE.equals("") && cart_sub_total != 0.00) {
                    //cartListTask = false;
                    if (edtPromotion.getText().toString().length() > 0) {

                        mPromocode = edtPromotion.getText().toString();
                        GlobalValues.couponCodeFromFiveMenu = mPromocode;

                        if (networkCheck()) {
                            applyCoupon(mPromocode, null);
                        } else {
                            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(mContext, "Please enter your  Promo/Invite code !", Toast.LENGTH_LONG).show();
                    }
                } else if (GlobalValues.DELIVERY_DATE.equals("")) {
                    showInformationDialog("noDate");
                } else {
                    showInformationDialog("noCart");
                }
                /*if (mFrom == GlobalValues.FROM_CHECKOUT) {

                    if (edtPromotion.getText().toString().length() > 0) {

                        mPromocode = edtPromotion.getText().toString();


                        if (networkCheck()) {

                            applyCoupon(mPromocode, null);

                        } else {

                            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(mContext, "Please enter your  Promo/Invite code !", Toast.LENGTH_LONG).show();
                    }

                } else {
                    if (edtPromotion.getText().toString().length() > 0) {

                        mPromocode = edtPromotion.getText().toString();


                        if (networkCheck()) {

                            applyCoupon(mPromocode, null);

                        } else {

                            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                        }

                    } else {
                        Toast.makeText(mContext, "Please enter your  Promo/Invite code !", Toast.LENGTH_LONG).show();
                    }
                    //showInformationDialog();
                }*/
            }
        });


        tabLayoutPromotion.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_promotion);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
                viewPagerPromotion.setCurrentItem(tab.getPosition(), true);
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
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_promotion);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        viewPagerPromotion.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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


        return view;
    }


    private void showInformationDialog(String info) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
        if (info.equalsIgnoreCase("noDate")) {
            alertDialog.setMessage("Please go to checkout and select date and time");
        } else {
            alertDialog.setMessage("Please go to checkout and redeem your Promotion");
        }
        //alertDialog.setMessage("Please go to checkout and redeem your voucher");
        alertDialog.setTitle("Information");

        // Setting OK Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog1, int which) {
//                dialog.dismiss();
                dialog1.dismiss();

            }
        });

                  /*  alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });*/

        // Showing Alert Message
        alertDialog.show();
    }

    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }

    private void setTabItem() {

        for (int k = 0; k < promotionTabs.length; k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tablayout_item_promotion, null);

            TextView txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_promotion);

            txtTabItem.setText(promotionTabs[k]);
            // txtTabItem.setTextColor(getResources().getColor(R.color.colorBlack));


            txtTabItem.setText(promotionTabs[k]);


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
            tabLayoutPromotion.addTab(tabLayoutPromotion.newTab().setCustomView(tabView));
        }
        promotionAdapter = new PromotionPagerAdapter(getChildFragmentManager(), mFrom);
        viewPagerPromotion.setAdapter(promotionAdapter);
    }

    public void applyCoupon(String couponCode, Dialog mDialog) {


        String url = GlobalUrl.COUPON_CODE_URL;

        if (couponCode.trim().equals("")) {
            Toast.makeText(mContext, "Please Enter Valid Coupon Code", Toast.LENGTH_LONG).show();
        } else {

            edtPromotion.setText(couponCode);

//            if(CommonClassApplication.cartJSONArray.length()>0)
//            {
//            }
//
//            else
//            {
///            }

            try {


                JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());

                JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

                JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < cartJsonArray.length(); i++) {
                    JSONObject cartItem = cartJsonArray.getJSONObject(i);
                    if (i != (cartJsonArray.length() - 1))
                        stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                cartItem.getString("cart_item_total_price") + "|"  + cartItem.getString("cart_item_qty") + ";");
                    else {
                        stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                cartItem.getString("cart_item_total_price") + "|"  + cartItem.getString("cart_item_qty"));
                    }
                }


                Map<String, String> params = new HashMap<String, String>();
                params.put("app_id", GlobalValues.APP_ID);
                params.put("promo_code", couponCode);
                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                String cartSubTotal = "";

                if (GlobalValues.promoID.equalsIgnoreCase("") && isApplyRedeem) {
                    if (mPromotionAmount.equalsIgnoreCase("")) {
                        cartSubTotal = String.valueOf(cart_sub_total);
                    } else {
                        cartSubTotal = String.valueOf(cart_sub_total) + Double.parseDouble(mPromotionAmount);
                    }

                } else if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID)) && !(GlobalValues.promoID.equalsIgnoreCase(""))) {
                     cartSubTotal = String.valueOf(cart_sub_total) + promotionAmount;
                } else {
                     cartSubTotal = jsonCartDetails.getString("cart_sub_total");
                    //cartSubTotal = String.valueOf(cart_sub_total);
                }

                params.put("cart_amount", String.valueOf(cartSubTotal));  //cart_sub_total
                params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
                params.put("category_id", stringBuilder.toString());  //list of product ids
                //    try {

                //run(formBody);

                new CouponCodeTask(params, couponCode, mDialog).execute(url);

            } catch (Exception e) {
                e.printStackTrace();

                Map<String, String> params = new HashMap<String, String>();
                params.put("app_id", GlobalValues.APP_ID);
                params.put("promo_code", couponCode);
                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                params.put("cart_amount", ""); //cart_sub_total
                params.put("cart_quantity", "");
                params.put("category_id", "");  //list of product ids

                new CouponCodeTask(params, couponCode, mDialog).execute(url);
            }

        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private class CouponCodeTask extends AsyncTask<String, String, String> {

        String response, status, message, type;

        ProgressDialog progressDialog;

        Map<String, String> couponParams;

        String couponCode = "";

        Dialog mDialog;

        CouponCodeTask(Map<String, String> params, String couponCode, Dialog mDialog) {
            this.couponParams = params;
            this.couponCode = couponCode;
            this.mDialog = mDialog;
            Log.e("params", params.toString());
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                response = WebserviceAssessor.postRequest(mContext, strings[0], couponParams);
            } catch (Exception e) {
                e.printStackTrace();
            }

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

                    status = responseJSONObject.getString("status");

                    if (status.equals("success")) {    //Success

                        message = responseJSONObject.getString("message");

                        type = responseJSONObject.getString("type");

                        if (type.equals("promo")) {

                            JSONObject resultSetJSONObj = responseJSONObject.getJSONObject("result_set");
                            //    JSONObject resultSetJSONObj = resultSetJSONArray.getJSONObject(0);

                            String deliveryChargeDiscount = resultSetJSONObj.getString("promotion_delivery_charge_applied");

                            //CommonClass.discountCode=couponCodeEditText.getText().toString();
                            GlobalValues.DISCOUNT_CODE = resultSetJSONObj.getString("promotion_code");


                            if (deliveryChargeDiscount.equals("Yes")) {

                                if (GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {

                                    GlobalValues.DISCOUNT_APPLIED = true;
                                    GlobalValues.DISCOUNT_TYPE = "COUPON";

                                 /*   GlobalValues.deliveryCharge = 0.0;
                                    GlobalValues.deliveryChargeDiscount = true;*/

//                                    Toast.makeText(PromotionActivity.this, message, Toast.LENGTH_LONG).show();

//                                    CallMessagenew(message, "Thanks!");
//                                    CallMessagenew(message, "Thanks!", GlobalValues.DISCOUNT_CODE, resultSetJSONObj.getString("promotion_amount"));
                                    CallMessagenew(message, "Thanks!", responseJSONObject.toString());

                                } else {

                                    Toast.makeText(mContext, "Coupon valid only for Delivery.", Toast.LENGTH_LONG).show();

                                }

                            } else {


                                GlobalValues.DISCOUNT_APPLIED = true;
                                GlobalValues.DISCOUNT_TYPE = "COUPON";

                                // GlobalValues.deliveryChargeDiscount = false;

                                String discountAmount = resultSetJSONObj.getString("promotion_amount");

                                // GlobalValues.promotionDiscountAmount = Double.parseDouble(discountAmount);

//                                CallMessagenew(message, "Thanks!");
//                                CallMessagenew(message, "Thanks!", GlobalValues.DISCOUNT_CODE, resultSetJSONObj.getString("promotion_amount"));
                                CallMessagenew(message, "Thanks!", responseJSONObject.toString());
                            }

                            //calculateGrandTotal();
                            //    addPromoCodeEditText.setText("");

                            //validating
                            //  paymentSuccessPlaceOrder("", "", "", "", "", true);

                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }

                        } else if (type.equals("referal")) {

                            //getPromo();

                        } else if (type.equals("claim")) {

                            //getPromo();

//                            CallMessagenew(message, "Thanks!");
//                            CallMessagenew(message, "Thanks!", GlobalValues.DISCOUNT_CODE, resultSetJSONObj.getString("promotion_amount"));
                        } else {

                            if (mDialog != null && mDialog.isShowing()) {
                                mDialog.dismiss();
                            }
                        }

                    } else {

                        edtPromotion.setText("");

//                            message = responseJSONObject.optString("message");
//
//                            String formerror = responseJSONObject.optString("form_error");
//                            Toast.makeText(PromotionActivity.this, "" + message + "\n" + Html.fromHtml(formerror), Toast.LENGTH_LONG).show();
//
                        if (responseJSONObject.has("form_error")) {
                            String form_error = responseJSONObject.getString("form_error");
//                                Toast.makeText(PromotionActivity.this,Html.fromHtml(form_error), Toast.LENGTH_LONG).show();
                            CallMessage(form_error);

                        } else {
                            message = responseJSONObject.getString("message");
//                                Toast.makeText(PromotionActivity.this,message, Toast.LENGTH_LONG).show();

                            CallMessage(message);
                        }
                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals(""))
                        edtPromotion.setText("");
                }

            } else {

                if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals(""))
                    edtPromotion.setText("");
            }
        }
    }

    public void CallMessage(String message) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setMessage(Html.fromHtml(message));

        alertDialogBuilder.setNegativeButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                alertDialog.dismiss();
                //finish();
            }
        });
        alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    AlertDialog alertDIalog;

    public void CallMessagenew(String message, final String msg, final String response) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setMessage(Html.fromHtml(message.replace("\n", "")));


        alertDialogBuilder.setPositiveButton(msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

              /*  Intent i = new Intent(mContext, PromotionFragment.class);
                i.putExtra("FROM", from);
                startActivity(i);
//                mContext.finish();
*/

                /*Intent intent = new Intent();
                intent.putExtra("PROMO_RESPONSE", response);
                getActivity().setResult(-1, intent);
                getActivity().finish();

                if (alertDIalog != null)
                    alertDIalog.dismiss();*/

                if(GlobalValues.isFirstTimePromo){

                    if(GlobalValues.firstTimePromoCode.equalsIgnoreCase(GlobalValues.couponCodeFromFiveMenu)){

                        GlobalValues.isPromoAdded = true;
                        Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
                        intent.putExtra("PROMO_RESPONSE", response);
                        intent.putExtra("notes", "");
                        getContext().startActivity(intent);
                    }else{

                        GlobalValues.isPromoAdded = true;
                        Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
                        intent.putExtra("PROMO_RESPONSE", response);
                        intent.putExtra("notes", "");
                        getContext().startActivity(intent);
                    }
                }else{

                    GlobalValues.isPromoAdded = true;
                    Intent intent = new Intent(getContext(), OrderSummaryActivity.class);
                    intent.putExtra("PROMO_RESPONSE", response);
                    intent.putExtra("notes", "");
                    getContext().startActivity(intent);
                }

            }
        });

        final AlertDialog alertDIalog = alertDialogBuilder.create();
        // alertDialog.setMessage(message);

        alertDIalog.show();

    }
}
