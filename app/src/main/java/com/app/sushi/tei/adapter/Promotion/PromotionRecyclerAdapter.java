package com.app.sushi.tei.adapter.Promotion;

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
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IPromoClick;
import com.app.sushi.tei.Model.Promotion.Promotion;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.OrderSummaryActivity;
import com.app.sushi.tei.fragment.Promotion.PromotionEarnedFragment;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import org.json.JSONArray;
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


public class PromotionRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    static Activity mContext;
    static IPromoClick iPromoClick;
    ArrayList<Promotion> productdatas;
    AlertDialog alertDialog;
    int from;
    Dialog dialogPromo;
    private PromotionEarnedFragment promotionEarnedFragment;
    private OrderSummaryActivity orderSummaryActivity;
    boolean fromHomeNavigation = true;

    //from home navigation
    public PromotionRecyclerAdapter(PromotionEarnedFragment promotionEarnedFragment, Activity activity, ArrayList<Promotion> Productdatas, int from) {
        this.promotionEarnedFragment = promotionEarnedFragment;
        this.productdatas = Productdatas;
        this.mContext = activity;
        this.from = from;
        fromHomeNavigation = true;
        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
        GlobalValues.CURRENT_AVAILABLITY_NAME = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
    }

    //from summary dialog
    public PromotionRecyclerAdapter(OrderSummaryActivity orderSummaryActivity, Activity activity, ArrayList<Promotion> Productdatas, int from, Dialog dialogPromo) {
        this.productdatas = Productdatas;
        this.orderSummaryActivity = orderSummaryActivity;
        this.mContext = activity;
        this.from = from;
        this.dialogPromo = dialogPromo;
        fromHomeNavigation = false;
        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
        GlobalValues.CURRENT_AVAILABLITY_NAME = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_promotion_item_new, parent, false);

        VHItem dataObjectHolder = new VHItem(view);
        return dataObjectHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {


        final VHItem vhItemHolder = (VHItem) holder;
        vhItemHolder.txt_promotion_label.setText(productdatas.get(position).getmPromotionTitle());
        vhItemHolder.txtPromoCode.setText(productdatas.get(position).getmPromoCode());

//        if (from == 2) {
        if (fromHomeNavigation) {
            vhItemHolder.layoutView.setVisibility(View.VISIBLE);
        } else {
            vhItemHolder.layoutView.setVisibility(View.GONE);
        }

        try {
            //vhItemHolder.txtDescription.setText((productdatas.get(position).getmPromotDiscription()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (productdatas.get(position).getmPromotionType().equalsIgnoreCase("fixed")) {
            vhItemHolder.clientCurrency.setVisibility(View.VISIBLE);
            vhItemHolder.txt_promotion.setText(productdatas.get(position).getmPromotionMaxAmt() + " OFF");
        } else {
            vhItemHolder.clientCurrency.setVisibility(View.GONE);
            vhItemHolder.txt_promotion.setText(productdatas.get(position).getmPromotionPercentage() + "% OFF");
        }

        //vhItemHolder.txt_promotion.setText(productdatas.get(position).getmPromotionPercentage() + " OFF");

        if (productdatas.get(position).getmPromotionImage() != null && !productdatas.get(position).getmPromotionImage().isEmpty()) {

            vhItemHolder.txtPromoCode.setText(productdatas.get(position).getmPromoCode());

            /*Picasso.with(activity)
                    .load(productdatas.get(position).getPromotionImage())
                 //   .placeholder(R.drawable.product_no_image)
                    .into(vhItemHolder.imagebg_image);*/
            Calendar cals = Calendar.getInstance();
            SimpleDateFormat outputformat = new SimpleDateFormat("dd-MMM-yyyy");
            DateFormat inputformat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
            String dat = productdatas.get(position).getmPromotionEndDate();
            Date date = null;
            try {
                date = inputformat.parse(productdatas.get(position).getmPromotionEndDate());
            } catch (ParseException e) {
                e.printStackTrace();
            }
            cals.setTime(date);
            String outputDate = outputformat.format(date);
            vhItemHolder.txtDateLeft.setText("Valid till " + outputDate);

            try {
                RequestCreator requestCreator = Picasso.with(mContext).load(productdatas.get(position).getmPromotionImage());
                //   requestCreator.placeholder(R.drawable.product_detail_no_image_org);
                //   requestCreator.error(R.drawable.product_detail_no_image_org);

                requestCreator.into(vhItemHolder.imgBackground, new Callback() {
                    @Override
                    public void onSuccess() {


                    }

                    @Override
                    public void onError() {
                        vhItemHolder.imgBackground.setImageResource(R.drawable.place_holder_sushi_tei);

                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                vhItemHolder.txtPromoCode.setText(productdatas.get(position).getmPromoCode());
                vhItemHolder.imgBackground.setImageResource(R.drawable.place_holder_sushi_tei);
            }
        }


        vhItemHolder.layoutRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (from == 1) {
                    applyCoupon(productdatas.get(position).getmPromoCode());
                    promotionID = productdatas.get(position).getmPromotionId();
                    if (GlobalValues.promoID.equalsIgnoreCase(promotionID)) {
                        orderSummaryActivity.removePromotion();
                    }
                } else {
                    String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    //&& !GlobalValues.DELIVERY_DATE.equals("")
                    //&& cart_sub_total != 0.00

                    if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {
                        /*if(GlobalValues.isFirstTimePromo){
                            if(GlobalValues.firstTimePromoCode.equalsIgnoreCase(productdatas.get(position).getmPromoCode())){
                                GlobalValues.isPromoAdded = true;
                                Intent intent = new Intent(mContext, OrderSummaryActivity.class);
                                intent.putExtra("PROMO_RESPONSE", "");
                                intent.putExtra("notes", "");
                                mContext.startActivity(intent);
                                mContext.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                                mContext.finish();
                            }
                        }else{*/
                        applyCoupon(productdatas.get(position).getmPromoCode());
                        GlobalValues.couponCodeFromFiveMenu = productdatas.get(position).getmPromoCode();
                        //}
                        //showInformationDialog("noCart");
                    } else if (GlobalValues.DELIVERY_DATE.equals("")) {
                        showInformationDialog("noDate");
                    } else {
                        showInformationDialog("noCart");
                    }
                }
            }
        });

        vhItemHolder.layoutView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                promotionEarnedFragment.showViewPromotionDialog(productdatas.get(position).getmPromotionId(),
                        productdatas.get(position).getmPromoCode());

            }
        });

    }


    private void showInformationDialog(String info) {
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        if (info.equalsIgnoreCase("noDate")) {
            alertDialog.setMessage("Please go to checkout and select date and time");
        } else {
            alertDialog.setMessage("Please go to checkout and redeem your Promotion");
        }
        alertDialog.setTitle("Information");

        // Setting OK Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
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

    @Override
    public int getItemCount() {
        return productdatas.size();
    }

    public static class VHItem extends RecyclerView.ViewHolder {

        TextView txt_promotion, txtPromoCode, txtDateLeft, txtDescription, txt_promotion_label, clientCurrency;
        RelativeLayout layoutRedeem, layoutView;
        ImageView imgBackground;

        public VHItem(View itemView) {
            super(itemView);

            txtPromoCode = (TextView) itemView.findViewById(R.id.txtPromoCode);
            txtDescription = (TextView) itemView.findViewById(R.id.txtDescription);
            txtDateLeft = (TextView) itemView.findViewById(R.id.txtDateLeft);
            layoutRedeem = (RelativeLayout) itemView.findViewById(R.id.layoutRedeem);
            layoutView = (RelativeLayout) itemView.findViewById(R.id.layoutView);
            imgBackground = (ImageView) itemView.findViewById(R.id.imgBackground);
            txt_promotion = itemView.findViewById(R.id.txt_promotion);
            txt_promotion_label = itemView.findViewById(R.id.txt_promotion_label);
            clientCurrency = itemView.findViewById(R.id.clientCurrency);
        }
    }


    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }


    public void applyCoupon(String couponCode) {

        try {

            String url = GlobalUrl.COUPON_CODE_URL;

            JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());

            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

            try {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < cartJsonArray.length(); i++) {
                    JSONObject cartItem = cartJsonArray.getJSONObject(i);
                    if (i != (cartJsonArray.length() - 1))
                        stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty") + ";");
                    else {
                        stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty"));
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
                }

                //cartSubTotal = jsonCartDetails.getString("cart_sub_total");

                params.put("cart_amount", cartSubTotal); //cart_sub_total
                params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
                params.put("category_id", stringBuilder.toString());
                params.put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));//list of product ids
                //    try {

                //run(formBody);

                new CouponCodeTask(params, couponCode).execute(url);

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

                new CouponCodeTask(params, couponCode).execute(url);

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
        Log.e("msg", message);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);

        alertDialogBuilder.setMessage(message);


        alertDialogBuilder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                if (msg.equalsIgnoreCase("OK")) {

                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }

                    //mContext.finish();
                    if (dialogPromo != null) {
                        dialogPromo.dismiss();
                    }


                } else {
                    alertDialog.dismiss();
                }


            }
        });
        alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

    public void CallMessagenew(final String message, final String msg, final String response) {


        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


//                activity.getIntent().putExtra("PROMO", couponCode);
                Intent intent;

                if (from == 1) {
                    intent = new Intent();
                    intent.putExtra("PROMO_RESPONSE", response);

                    if (dialogPromo == null) {

                        mContext.setResult(-1, intent);
                        mContext.finish();
                    } else {

                        orderSummaryActivity.promotionApply(response);
                    }

                } else {
                    if (GlobalValues.isFirstTimePromo) {
                        if (GlobalValues.firstTimePromoCode.equalsIgnoreCase(GlobalValues.couponCodeFromFiveMenu)) {

                            GlobalValues.isPromoAdded = true;
                            intent = new Intent(mContext, OrderSummaryActivity.class);
                            intent.putExtra("PROMO_RESPONSE", response);
                            intent.putExtra("notes", "");
                            mContext.startActivity(intent);
                            mContext.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                            mContext.finish();
                        }
                    } else {

                        GlobalValues.isPromoAdded = true;
                        intent = new Intent(mContext, OrderSummaryActivity.class);
                        intent.putExtra("PROMO_RESPONSE", response);
                        intent.putExtra("notes", "");
                        mContext.startActivity(intent);
                        mContext.overridePendingTransition(R.anim.right_in, R.anim.left_out);
                        mContext.finish();
                    }

                    //cartListTask = false;

                }

            /*    Intent i = new Intent(mContext,CartActivity.class);
                i.putExtra("from","promotion");
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(i);
                mContext.finish();*/
                alertDialog.dismiss();
            }

        });
        alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

}