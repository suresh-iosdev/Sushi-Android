package com.app.sushi.tei.fragment.Promotion;

import android.annotation.SuppressLint;
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
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Promotion.Promotion;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.OrderSummaryActivity;
import com.app.sushi.tei.adapter.Promotion.PromotionRecyclerAdapter;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyRedeem;
import static com.app.sushi.tei.activity.OrderSummaryActivity.mPromotionAmount;
import static com.app.sushi.tei.activity.OrderSummaryActivity.promotionAmount;
import static com.app.sushi.tei.activity.OrderSummaryActivity.promotionID;
import static com.app.sushi.tei.activity.SubCategoryActivity.cart_sub_total;

public class PromotionEarnedFragment extends Fragment {

    private Activity mContext;
    private RecyclerView recyclerviewPromotionEarned;
    RecyclerView.LayoutManager layoutManager;

    AlertDialog alertDialog;
    private Dialog dialog;

    String customerId = "";
    ArrayList<Promotion> promotionmodels;


    TextView txtEmpty;

    private int from = 0;
    private PromotionRecyclerAdapter promotionRecyclerAdapter;

    public PromotionEarnedFragment() {
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    public static PromotionEarnedFragment newInstance(int from) {

        Bundle args = new Bundle();
        args.putInt(GlobalValues.FROM_KEY, from);

        PromotionEarnedFragment fragment = new PromotionEarnedFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        from = getArguments().getInt(GlobalValues.FROM_KEY);

        View view = inflater.inflate(R.layout.fragment_promotion_earned, container, false);
        recyclerviewPromotionEarned = (RecyclerView) view.findViewById(R.id.recyclerviewPromotionEarned);
        txtEmpty = (TextView) view.findViewById(R.id.txtEmpty);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
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
        if (getActivity() != null) {
            if (networkCheck()) {
                new PromotionInfoTask().execute(GlobalUrl.PROMOTION_REDEEM_URL + "?app_id=" + GlobalValues.APP_ID +
                        "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
            } else {
                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
            }
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

    public void showViewPromotionDialog(String promotion_id, String couponcode) {

        if (networkCheck()) {

            new ViewPromotionInfoTask(promotion_id, couponcode).execute(GlobalUrl.PROMOTION_VIEW_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&promotion_id=" + promotion_id);

        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();
        }
    }

    private class PromotionInfoTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
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

                        JSONObject resultcommon = responseJSONObject.getJSONObject("common");

                        String CommonImageUrl = resultcommon.getString("promo_image_source");




                        JSONObject resultjsonobject = responseJSONObject.getJSONObject("result_set");

                        JSONArray jsonArraymypromo = resultjsonobject.getJSONArray("my_promo");

                        promotionmodels = new ArrayList<>();

                        if (jsonArraymypromo.length() > 0) {

                            for (int i = 0; i < jsonArraymypromo.length(); i++) {

                                JSONObject jsonObjectpromo = jsonArraymypromo.getJSONObject(i);

                                Promotion promotionmodel = new Promotion();
                                promotionmodel.setmPromotionId(jsonObjectpromo.optString("promotion_id"));
                                promotionmodel.setmPromoCode(jsonObjectpromo.optString("promo_code"));

                                promotionmodel.setmPromoDaysleft(jsonObjectpromo.optString("promo_days_left"));

                                promotionmodel.setmPromotionImage(CommonImageUrl + "/" + jsonObjectpromo.getString("promotion_image"));

                                promotionmodel.setmPromotionTitle(jsonObjectpromo.optString("promotion_title"));

                                promotionmodel.setmPromotDiscription(jsonObjectpromo.getString("promo_desc"));

                                promotionmodel.setmPromotionPercentage(jsonObjectpromo.getString("promotion_percentage"));

                                promotionmodel.setmPromotionEndDate(jsonObjectpromo.getString("promotion_end_date"));

                                promotionmodel.setmPromotionMaxAmt(jsonObjectpromo.getString("promotion_max_amt"));

                                promotionmodel.setmPromotionType(jsonObjectpromo.getString("promotion_type"));

                                promotionmodels.add(promotionmodel);



                                Utility.writeToSharedPreference(mContext,GlobalValues.PROMOTIONCOUNT,"1");


                            }
                        }


                        if (promotionmodels.size() > 0) {


                            promotionRecyclerAdapter = new PromotionRecyclerAdapter(PromotionEarnedFragment.this, getActivity(), promotionmodels, from);
                            layoutManager = new LinearLayoutManager(mContext);
                            recyclerviewPromotionEarned.setLayoutManager(layoutManager);
                            recyclerviewPromotionEarned.setHasFixedSize(true);
                            recyclerviewPromotionEarned.setItemAnimator(new DefaultItemAnimator());
                            recyclerviewPromotionEarned.setAdapter(promotionRecyclerAdapter);
                            recyclerviewPromotionEarned.setNestedScrollingEnabled(false);
                            recyclerviewPromotionEarned.setVisibility(View.VISIBLE);
                            txtEmpty.setVisibility(View.GONE);

                        } else {

                            recyclerviewPromotionEarned.setVisibility(View.GONE);
                            txtEmpty.setVisibility(View.VISIBLE);


                        }


                    } else {

                        message = responseJSONObject.getString("message");
                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart
                        recyclerviewPromotionEarned.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }


            } else {


            }


        }
    }

    private class ViewPromotionInfoTask extends AsyncTask<String, String, String> {

        String response, status, message, mCouponcode = "", commonImageurl = "", mPromotionId = "";
        ;

        ProgressDialog progressDialog;

        public ViewPromotionInfoTask(String id, String couponcode) {
            this.mPromotionId = id;
            mCouponcode = couponcode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity());
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

        @SuppressLint("SetTextI18n")
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
                        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.layout_redeem_dialog);

                        dialog.show();
                        JSONObject resultcommon = responseJSONObject.getJSONObject("common");
                        String CommonImageUrl = resultcommon.getString("promo_image_source");

                        JSONObject resultjsonobject = responseJSONObject.getJSONObject("result_set");
                        String title=resultjsonobject.getString("promotion_title");
                        String promo_desc=resultjsonobject.getString("promo_desc");
                        String promotion_name=resultjsonobject.getString("promotion_name");
                        String promotion_start_date=resultjsonobject.getString("promotion_start_date");
                        String promotion_end_date=resultjsonobject.getString("promotion_end_date");
                        String startDate="";
                        String enddate="";
                        if(promotion_start_date.length()>0){
                            startDate=promotion_start_date.split(" ")[0];
                         }
                        if(promotion_end_date.length()>0){
                            enddate=promotion_end_date.split(" ")[0];
                        }

                        Calendar cals = Calendar.getInstance();
                        SimpleDateFormat outputformat = new SimpleDateFormat("dd-MMM-yyyy");
                        DateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                        Date date = null;
                        try {
                            date = inputformat.parse(promotion_end_date);
                        } catch (ParseException e) {
                            e.printStackTrace();
                        }
                        cals.setTime(date);
                        enddate = outputformat.format(date);


                        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
                        TextView txtTitle = (TextView) dialog.findViewById(R.id.txtTitle);
                        TextView heading = (TextView) dialog.findViewById(R.id.heading);
                        TextView txtRedeemDesc = (TextView) dialog.findViewById(R.id.txtRedeemDesc);
                        txtRedeemDesc.setMovementMethod(new ScrollingMovementMethod());
                        TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
                        TextView validDate = (TextView) dialog.findViewById(R.id.validDate);
                        TextView promoValueText = (TextView) dialog.findViewById(R.id.promoValueText);
                        WebView webView=dialog.findViewById(R.id.termswebview);
                        webView.getSettings().setDefaultFontSize(11);

                        try {

                            Picasso.with(mContext).load(CommonImageUrl + "/" + resultjsonobject.getString("promotion_image")).placeholder(R.drawable.place_holder_sushi_tei).into(imgProduct);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                        txtTitle.setText(title);
                        txtRedeemDesc.setText(Html.fromHtml(promo_desc));
                        heading.setText(title);
                        promoValueText.setText(promotion_name);

                        if(!startDate.equalsIgnoreCase("")){
                            //validDate.setText("Valid From "+startDate+" to "+enddate);
                            validDate.setText("Expiry " + enddate);
                        }

                        //webView.loadData(promo_desc,"text/html; charset=UTF-8", null);
                        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
                        RelativeLayout layoutRedeem = (RelativeLayout) dialog.findViewById(R.id.layoutRedeem);

                        layoutRedeem.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog.isShowing()) {
                                    String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                                    if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty() && !GlobalValues.DELIVERY_DATE.equals("") && cart_sub_total != 0.00) {
                                        applyCoupon(mCouponcode);
                                    } else if(GlobalValues.DELIVERY_DATE.equals("")) {
                                        showInformationDialog("noDate");
                                    } else {
                                        showInformationDialog("noCart");
                                    }
                                }
                            }
                        });

                        layoutClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                if (dialog.isShowing()) {
                                    dialog.dismiss();
                                }
                            }
                        });


                    } else {

                        message = responseJSONObject.getString("message");
                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart
                        recyclerviewPromotionEarned.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);

                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }


            } else {

            }


        }
    }

    private void showInformationDialog(String info) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
         if(info.equalsIgnoreCase("noDate")){
            alertDialog.setMessage("Please go to checkout and select date and time");
        }else{
            alertDialog.setMessage("Please go to checkout and redeem your voucher");
        }
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

    public void applyCoupon(String couponCode) {

        try {

            String url = GlobalUrl.COUPON_CODE_URL;

            GlobalValues.couponCodeFromFiveMenu = couponCode;

                   String result=Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE);

            JSONObject jsonObject = new JSONObject(result);

            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

            try {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < cartJsonArray.length(); i++) {
                    JSONObject cartJson = cartJsonArray.getJSONObject(i);
                    stringBuilder.append(cartJson.getString("cart_item_product_id") + "|" +
                            cartJson.getString("cart_item_total_price")+ "|" +
                            cartJson.getString("cart_item_qty"));
                }


                Map<String, String> params = new HashMap<String, String>();
                params.put("app_id", GlobalValues.APP_ID);
                params.put("promo_code", couponCode);
                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

                String cartSubTotal = "";

                if(GlobalValues.promoID.equalsIgnoreCase("") && isApplyRedeem){
                    if(mPromotionAmount.equalsIgnoreCase("")){
                        cartSubTotal = String.valueOf(cart_sub_total);
                    }else{
                        cartSubTotal = String.valueOf(cart_sub_total) + Double.parseDouble(mPromotionAmount);
                    }

                } else if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID)) && !(GlobalValues.promoID.equalsIgnoreCase(""))) {
                     cartSubTotal = String.valueOf(cart_sub_total) + promotionAmount;
                } else {
                     cartSubTotal = jsonCartDetails.getString("cart_sub_total");
                }

                params.put("cart_amount", jsonCartDetails.getString("cart_sub_total"));//cart_sub_total
                params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
                params.put("category_id", stringBuilder.toString());
                params.put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));//list of product ids
                //    try {

                //run(formBody);

                new CouponCodeTask(params, couponCode).execute(url);

            } catch (Exception e) {
                e.printStackTrace();
/*
                Map<String, String> params = new HashMap<String, String>();
                params.put("app_id", GlobalValues.APP_ID);
                params.put("promo_code", couponCode);
                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                params.put("cart_amount", ""); //cart_sub_total
                params.put("cart_quantity", "");
                params.put("category_id", "");  //list of product ids

                new CouponCodeTask(params, couponCode).execute();*/

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private class CouponCodeTask extends AsyncTask<String, String, String> {

        String response, status, message, type;

        ProgressDialog progressDialog;

        Map<String, String> couponParams;

        String couponCode = "";

        Dialog mDialog;

        CouponCodeTask(Map<String, String> params, String couponCode) {
            couponParams = params;
            this.couponCode = couponCode;
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

                    if (status.equals("success")) {

                        //Success

                        message = responseJSONObject.getString("message");
/*
                        type = responseJSONObject.getString("type");
*/
                        JSONObject resultSetJSONObj = responseJSONObject.getJSONObject("result_set");
                        //    JSONObject resultSetJSONObj = resultSetJSONArray.getJSONObject(0);

                        String deliveryChargeDiscount = resultSetJSONObj.getString("promotion_delivery_charge_applied");

                        //CommonClass.discountCode=couponCodeEditText.getText().toString();
                        GlobalValues.DISCOUNT_CODE = resultSetJSONObj.getString("promotion_code");


                        if (deliveryChargeDiscount.equals("Yes")) {

                            if (GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {

                                GlobalValues.DISCOUNT_APPLIED = true;
                                GlobalValues.DISCOUNT_TYPE = "COUPON";

                                GlobalValues.DELIVERYCHARGEDISCOUNT = true;
                                GlobalValues.COMMON_DELIVERY_CHARGE = 0.0;

                                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

//                                CallMessagenew(message, "Thanks!", GlobalValues.DISCOUNT_CODE, resultSetJSONObj.getString("promotion_amount"));
                                CallMessagenew(message, "Thanks!", responseJSONObject.toString());

                                //CallMessage(message);

                            } else {

                                Toast.makeText(mContext, "Coupon valid only for Delivery.", Toast.LENGTH_LONG).show();

                            }

                        } else {

                            GlobalValues.DISCOUNT_APPLIED = true;
                            GlobalValues.DISCOUNT_TYPE = "COUPON";

                            GlobalValues.DELIVERYCHARGEDISCOUNT = false;

                            String discountAmount = resultSetJSONObj.getString("promotion_amount");

                            //GlobalValues.promotionDiscountAmount = Double.parseDouble(discountAmount);

                            //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//

//                            CallMessagenew(message, "Thanks!", GlobalValues.DISCOUNT_CODE,resultSetJSONObj.getString("promotion_amount"));
                            CallMessagenew(message, "Thanks!", responseJSONObject.toString());
                            // FinishedActivity();

                        }

                    } else {

                        if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals("")) {

                        }

                        message = responseJSONObject.getString("message");
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//

                        String form_error = responseJSONObject.optString("form_error");

                        String messageCom = message + "" + Html.fromHtml(form_error);

                        CallMessage(messageCom, "OK");

                    }

                } catch (Exception e) {
                    e.printStackTrace();

                    if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals("")) {

                    }
                }

            } else {

                if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals("")) {
                }

                CallMessage(message, "OK");

            }
        }
    }

    public void CallMessage(String message, final String msg) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());

        alertDialogBuilder.setMessage(message);


        alertDialogBuilder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                if (msg.equalsIgnoreCase("OK")) {

                    alertDialog.dismiss();
                    getActivity().finish();

                } else {

                    alertDialog.dismiss();

                }

            }
        });
        alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

    public void CallMessagenew(String message, final String msg, final String response) {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


//                activity.getIntent().putExtra("PROMO", couponCode);

              /*  Intent i = new Intent(activity,CheckoutActivity.class);
                i.putExtra("from","promotion");
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                activity.startActivity(i);
                 activity.finish();*/
                /*cartListTask = false;
                Intent intent = new Intent();
                intent.putExtra("PROMO_RESPONSE", response);
                mContext.setResult(-1, intent);
                mContext.finish();
                alertDialog.dismiss();*/

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
        alertDialog = alertDialogBuilder.create();

        alertDialog.show();

    }

}