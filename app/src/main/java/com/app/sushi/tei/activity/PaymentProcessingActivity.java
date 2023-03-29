package com.app.sushi.tei.activity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import pl.droidsonroids.gif.GifTextView;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.time_slot_type;
import static com.app.sushi.tei.activity.OrderSummaryActivity.gstAmount;
import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyPromo;
import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyRedeem;


public class PaymentProcessingActivity extends AppCompatActivity {
    public static String PUBLISHABLE_KEY = "";
    String from = "", customerId = "";
    Context mContext;
    double grandTotal = 0.00;
    int amount = 0;
    private SpannableString spannableTime;

    private TextView txtTotal;
    private GifTextView imgGifPaymentProcess, imgGifOrderProcess;
    private ImageView imgPaymentProcess, imgOrderProcess;

    WebAuthotizeTask webAuthotizeTask;
    private DatabaseHandler databaseHandler;

    public String mPrevCaptureId;

    public static String revelJSONString;
    private String mOrderNo = "", orderPrimaryId = "";
    private String mOrderDate = "";
    private Map<String, String> params;
    private String r_applied,
            r_point,
            r_amount, sub_total, grand_total, p_code, p_amount, eWalletAmount;
    private String mOrderComments = "";

    private String mUnitNo1 = "", mUnitNo2 = "", mCardNumber = "", mCardName = "",
            mYear = "", mMonth = "", mCVV = "", mBillingAddress = "", mBillingPincode = "",
            mBillingUnitNo1 = "", mBillingUnitNo2 = "";
    JSONObject jsonPostalCodeInfo;

    Handler networkhandler;
    Runnable networkRunnable;
    RelativeLayout netWorkStatusLayout;
    TextView networkTextView, orderprocessing_textview, networkSubTextView, payprocessing_textview;
    ImageView networkImageView;
    public int currentRetry = 0;
    public static final int chargeRetry = 4;

    AlertDialog chargeAlertDialog;

    public boolean chargeRetryShowing = false;
    public boolean isVisible = false;
    public String OmiseToken, toalAmount;
    private String customerNote, orderRemarks, cardId;
    private Boolean saveCard;
    private String reward_subTotal = "", promo_subTotal = "";
    private String cartVoucherDiscountAmt = "0", orderItemId = "", cartItemVoucherId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nelson_payment_process);

        mContext = PaymentProcessingActivity.this;

        initView();

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        Intent intent = getIntent();

        if (intent != null) {
            mUnitNo1 = getIntent().getStringExtra("unit_no1");
            mUnitNo2 = getIntent().getStringExtra("unit_no2");

            r_applied = getIntent().getStringExtra("REDEEM_APPLIED");
            r_point = getIntent().getStringExtra("REDEEM_POINT");
            r_amount = getIntent().getStringExtra("redeem_amount");
            cartVoucherDiscountAmt = getIntent().getStringExtra("cartVoucherDiscountAmt");
            p_code = getIntent().getStringExtra("promo_code");
            p_amount = getIntent().getStringExtra("promo_amount");

            customerNote = getIntent().getStringExtra("AdditionalNotes");
            orderRemarks = getIntent().getStringExtra("order_remarks");
            toalAmount = intent.getStringExtra("Total");
            OmiseToken = intent.getStringExtra("OMISE_TOKEN");
            sub_total = getIntent().getStringExtra("sub_total");
            reward_subTotal = getIntent().getStringExtra("reward_subTotal");
            promo_subTotal = getIntent().getStringExtra("promo_subTotal");
            saveCard = intent.getBooleanExtra("SAVE_CARD", false);

            eWalletAmount = intent.getStringExtra("ewallet_amount");

            cardId = intent.getStringExtra("CARD_NUMBER");
            /*try {
                loyality_point_price = intent.getStringExtra("loyality_point_price");
            } catch (Exception e) {
                loyality_point_price = "";
                e.printStackTrace();
            }*/



            grandTotal = Double.parseDouble(toalAmount);
            Log.e("TAG","GrandTotalTest::"+grandTotal);
            final String price = String.format("%.2f", grandTotal);
            SpannableString cs = new SpannableString("" + price);
            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

            txtTotal.setText(price);


            amount = (int) (grandTotal * 100);
            customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            String type = intent.getStringExtra("TYPE");  // new Card or Saved Card


            if (type.equals("NEW_CARD")) {

                FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);
//Todo surya
                formBuilder.add("customer_id", customerId);

                formBuilder.add("paid_amount", String.format("%.2f", grandTotal) + "");

                formBuilder.add("token", OmiseToken);
                if (saveCard) {
                    formBuilder.add("create_customer", "yes");
                    formBuilder.add("card_id", "");
                } else {
                    formBuilder.add("create_customer", "no");
                    formBuilder.add("card_id", "");
                }
                formBuilder.add("request_type", "");
                formBuilder.add("outlet_name", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));

                FormBody formBody = formBuilder.build();

                webAuthotizeTask = new WebAuthotizeTask(formBody);
                webAuthotizeTask.execute();


            } else {

                FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);
                formBuilder.add("customer_id", customerId);

                formBuilder.add("paid_amount", String.format("%.2f", grandTotal) + "");

                formBuilder.add("token", OmiseToken);
                formBuilder.add("create_customer", "no");
                formBuilder.add("card_id", cardId);
                formBuilder.add("request_type", "");
                formBuilder.add("outlet_name", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));


                FormBody formBody = formBuilder.build();

                webAuthotizeTask = new WebAuthotizeTask(formBody);
                webAuthotizeTask.execute();

            }
        }
    }

    private void initView() {
        txtTotal = (TextView) findViewById(R.id.txtTotal);

        imgGifOrderProcess = (GifTextView) findViewById(R.id.imgGigOrderProcess);
        imgGifPaymentProcess = (GifTextView) findViewById(R.id.imgGifPaymentProcess);

        imgPaymentProcess = (ImageView) findViewById(R.id.imgPaymentProcess);
        imgOrderProcess = (ImageView) findViewById(R.id.imgOrderProcess);


        orderprocessing_textview = (TextView) findViewById(R.id.orderprocessing_textview);
        payprocessing_textview = (TextView) findViewById(R.id.payprocessing_textview);


        customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
        databaseHandler = DatabaseHandler.getInstance(mContext);
        params = new HashMap<>();

        netWorkStatusLayout = (RelativeLayout) findViewById(R.id.netWorkStatusLayout);
        networkTextView = (TextView) findViewById(R.id.networkTextView);
        networkSubTextView = (TextView) findViewById(R.id.networkSubTextView);
        networkImageView = (ImageView) findViewById(R.id.networkImageView);

        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
        //getIntentValues();

        isVisible = true;


        networkhandler = new Handler();


        networkRunnable = new Runnable() {
            @Override
            public void run() {

                if (networkCheck()) {

                    netWorkStatusLayout.setBackgroundColor(Color.parseColor("#00B439"));  //#00B339
                    networkTextView.setText("Internet Connected");
                    networkSubTextView.setText("Internet connection established.");
                    networkImageView.setImageResource(R.drawable.alert_complete_icon);

                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {


                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    netWorkStatusLayout.setVisibility(View.GONE);
                                }
                            });

                        }
                    }, 850);


                    if (currentRetry == chargeRetry) {


                        if (networkCheck()) {

                            if (chargeRetryShowing) {
                                if (chargeAlertDialog != null && chargeAlertDialog.isShowing()) {
                                    chargeAlertDialog.dismiss();
                                }
                            }

                            startChargeRetry();
                        }


                    } /*.... else if (currentRetry == pushToRevelRetry) {



                        if (networkCheck()) {

                            if (pushToRevelRetryShowing) {
                                if (pushToRevelAlertDialog != null && pushToRevelAlertDialog.isShowing()) {
                                    pushToRevelAlertDialog.dismiss();
                                }
                            }
                            startPushToRevelRetry();

                        }

                    }*/ else {  //0


                    }


                } else {

                    netWorkStatusLayout.setBackgroundColor(Color.parseColor("#E95D40")); //CC0000
                    networkTextView.setText("No Connectivity");//No Internet Connection. Please turn on network connectivity to continue order process.
                    networkSubTextView.setText("Please check your internet connection.");
                    networkImageView.setImageResource(R.drawable.alert_error_icon);

                    netWorkStatusLayout.setVisibility(View.VISIBLE);
                }


                networkhandler.postDelayed(networkRunnable, 1000);
            }
        };

        networkhandler.postDelayed(networkRunnable, 1000);

    }

    public boolean networkCheck() {
        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();



        return isConnected;

    }

    //WebAuthorize Client
    final OkHttpClient webAuthorizeClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public String webAuthorizeRun(RequestBody formBody) throws Exception {
        Request request;
        if(!GlobalValues.ACCESS_TOKEN.isEmpty()){
            request = new Request.Builder()
                    .url(GlobalUrl.webAuthorizeURL)
                    .addHeader("Authorization",GlobalValues.ACCESS_TOKEN)
                    .post(formBody)
                    .build();
        }else {
            request = new Request.Builder()
                    .url(GlobalUrl.webAuthorizeURL)
                    .post(formBody)
                    .build();
        }



        Response response = webAuthorizeClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;
    }

    public class WebAuthotizeTask extends AsyncTask<String, String, String> {

        FormBody formBody;
        String response, status, message;

        WebAuthotizeTask(FormBody formBody) {
            this.formBody = formBody;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                 response = webAuthorizeRun(formBody);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (response != null) {

                try {



                    JSONObject jsonObject = new JSONObject(response);


                    String status = jsonObject.getString("status");

                    if (status.equals("ok")) {

                        //      paymentProcessedPlaceOrder("", "", "", "", "", false);  //Validation
                        //  get
                        try {
                            JSONObject result_set = jsonObject.getJSONObject("result_set");

                            mPrevCaptureId = result_set.getString("captureId");


                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        PaymentSucess();

                        placeCashOnDeliveryOrder();

                    } else {


                        if (jsonObject.has("form_error")) {

                            String formError = jsonObject.getString("form_error");
                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                        } else {

                            message = jsonObject.getString("message");
                            Toast.makeText(PaymentProcessingActivity.this, "Payment Failed", Toast.LENGTH_LONG).show();

                        }

                        paymentSectionFailure();

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(PaymentProcessingActivity.this, "There seems to be a network issue.", Toast.LENGTH_SHORT).show();
                    paymentSectionFailure();

                }
            } else {


                Toast.makeText(PaymentProcessingActivity.this, "There seems to be a network issue.", Toast.LENGTH_SHORT).show();
                paymentSectionFailure();

            }


        }
    }

    private void PaymentSucess() {

        payprocessing_textview.setText("Payment Completed");
        imgGifPaymentProcess.setVisibility(View.GONE);
        imgPaymentProcess.setVisibility(View.VISIBLE);
    }


    //Card Payment
    private void finishProgress(String token) {  // Dont Save Card


        try {


            try {


                try {


                    if (networkCheck()) {  //Dont save card


                        customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);



                        FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);

                        formBuilder.add("customer_id", customerId);

                        formBuilder.add("paid_amount", String.format("%.2f", grandTotal) + "");

                        formBuilder.add("token", OmiseToken);

                        formBuilder.add("request_type", "");

                        formBuilder.add("outlet_name", GlobalValues.OUTLET_ID);


                        FormBody formBody = formBuilder.build();

                        webAuthotizeTask = new WebAuthotizeTask(formBody);
                        webAuthotizeTask.execute();


                    } else {
                        Toast.makeText(PaymentProcessingActivity.this, "There seems to be a network issue.", Toast.LENGTH_LONG).show();
                        paymentSectionFailure();
                    }

                } catch (Exception e) {
                    // Something else happened, completely unrelated to Stripe
                    System.out.println("Exception Message is: " + e.getMessage());
                    System.out.println("Exception Message is: " + e);

                    Toast.makeText(PaymentProcessingActivity.this, "There seems to be a network issue.", Toast.LENGTH_LONG).show();
                    paymentSectionFailure();
                }


            } catch (Exception e) {
                // Something else happened, completely unrelated to Stripe
                System.out.println("Exception Message is: " + e.getMessage());
                System.out.println("Exception Message is: " + e);

                //   dismisProgress();
                //..         enablePlaceOrder();

                Toast.makeText(PaymentProcessingActivity.this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
                paymentSectionFailure();
            }


        } catch (Exception e) {
            e.printStackTrace();
            // The card has been declined

            //    dismisProgress();
//..            enablePlaceOrder();

            Toast.makeText(PaymentProcessingActivity.this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
            paymentSectionFailure();
        }
    }


    private void paymentSectionFailure() {


        imgGifPaymentProcess.setVisibility(View.GONE);
        imgGifOrderProcess.setVisibility(View.VISIBLE);

        imgPaymentProcess.setVisibility(View.VISIBLE);
        imgPaymentProcess.setImageResource(R.drawable.delete);


        imgOrderProcess.setVisibility(View.GONE);
        imgOrderProcess.setImageResource(R.drawable.icon_process_complete);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);

    }


    public void showToastMessage(final String message) {

        runOnUiThread(new Runnable() {
            public void run() {
                Toast.makeText(PaymentProcessingActivity.this, message, Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void placeCashOnDeliveryOrder() {

        try {
            JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());


            try {
                JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));


                JSONObject jsonPostalCodeInfo = outletJson.getJSONObject("postal_code_information");

            } catch (Exception e) {

                e.printStackTrace();
            }


            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

            StringBuilder productIdsStringBuilder = new StringBuilder();

            for (int i = 0; i < cartJsonArray.length(); i++) {
                JSONObject cartItem = cartJsonArray.getJSONObject(i);
                if (i != (cartJsonArray.length() - 1))
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty") + ";");
                else {
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty"));
                }
            }




            mOrderDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(new Date());


            Map<String, String> params = new HashMap<String, String>();

            params.put("app_id", GlobalValues.APP_ID);
            params.put("sub_total", sub_total);
            params.put("grand_total", "" + grandTotal);
            params.put("order_source", "Mobile");
            params.put("order_status", "1");
            params.put("products", getProductJSONArray(cartJsonArray).toString());
            params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
            params.put("customer_email", Utility.readFromSharedPreference(mContext, GlobalValues.EMAIL));
            params.put("customer_mobile_no", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));
            params.put("customer_fname", Utility.readFromSharedPreference(mContext, GlobalValues.FIRSTNAME));
            params.put("customer_lname", Utility.readFromSharedPreference(mContext, GlobalValues.LASTNAME));
            params.put("category_id", productIdsStringBuilder.toString());
            params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
            params.put("order_voucher_discount_amount", cartVoucherDiscountAmt);
            params.put("order_remarks", orderRemarks);
            params.put("order_someone_remarks", customerNote);
            params.put("zero_processing", "No");
            params.put("allow_order", "Yes");
            params.put("device_type", "Android");

            params.put("ewallet_amount", eWalletAmount);

            params.put("reward_point_status", "Yes");

            if (GlobalValues.is_cutlery_checked) {
                params.put("cutleryOption", "YES");
            } else {
                params.put("cutleryOption", "NO");
            }

//todo tax Charge
          /*  Double gstchr = Double.parseDouble(Utility.readFromSharedPreference(mContext,GlobalValues.GSTCHARGE));


            int GstCharge = gstchr.intValue();*/

            params.put("tax_charge", "0");
            params.put("order_tax_calculate_amount", "0.00");
            params.put("order_capture_token", mPrevCaptureId);




//
///*
//            Double srchr = Double.parseDouble(String.valueOf(GlobalValues.SERVICE_CHARGE));
//
//            int SrChanger = srchr.intValue();
//
//
//        */
//
//
///*
//
//             if(GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.DINEIN_ID))
//            {
//*/
///*                params.put("customer_address_line1", jsonPostalCodeInfo.optString("zip_buno") + ","
//                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
//                        jsonPostalCodeInfo.optString("zip_buname") + ",");
//                params.put("customer_postal_code", jsonPostalCodeInfo.optString("zip_code"));
//                params.put("customer_unit_no1", "test");
//                params.put("customer_unit_no2","test");*//*
//
//
//             */
///* params.put("billing_address_line1", "test");
//                params.put("billing_postal_code", "test");
//                params.put("billing_unit_no1", "test");
//                params.put("billing_unit_no2", "test");*//*
//
//
//
//
//                params.put("table_number",GlobalValues.dineInTableName );
//                params.put("outlet_id", Utility.readFromSharedPreference(mContext,GlobalValues.OUTLET_ID));
//                params.put("order_service_charge", String.valueOf(GlobalValues.SERVICE_CHARGE));
//                params.put("order_service_charge_amount", ""+GlobalValues.SERVICE_CHARGE_AMOUNT);
//
//          */
///*
//                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
//                        jsonPostalCodeInfo.optString("zip_buname") + ",");
//
//
//
//
//
//
//
//
//                try {
//                    params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);
//
//                    params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);
//                    params.put("delivery_charge", GlobalValues.DELEIVERY_CHARGES);
//
//
//
//                }
//                catch (NumberFormatException e)
//                {
//
//                    params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);
//                    params.put("delivery_charge","0.00");
//                    params.put("additional_delivery","0.00");
//
//
//                    e.printStackTrace();
//                }
//
//
//            }
//*/

            if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {


                params.put("customer_unit_no1", mUnitNo1);
                params.put("customer_unit_no2", mUnitNo2);
                params.put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));

                if (time_slot_type == 2) {
//                    params.put("order_delivery_time_slot_from", getFromTime(GlobalValues.DELIVERY_TIME));
//                    params.put("order_delivery_time_slot_to", getToTime(GlobalValues.DELIVERY_TIME));
                    params.put("order_is_timeslot", "Yes");
                    params.put("order_pickup_time_slot_from", getFromTime(GlobalValues.DELIVERY_TIME));
                    params.put("order_pickup_time_slot_to", getToTime(GlobalValues.DELIVERY_TIME));
                } else {
                    params.put("order_is_timeslot", "No");
                }
            } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {


                JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                params.put("outlet_id", outletZoneJson.getString("outlet_id"));

                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");


                params.put("customer_address_line1", jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
                        jsonPostalCodeInfo.optString("zip_buname") + "");
                params.put("customer_postal_code", jsonPostalCodeInfo.getString("zip_code"));


                params.put("customer_unit_no1", mUnitNo1);
                params.put("customer_unit_no2", mUnitNo2);
                params.put("order_tat_time", outletZoneJson.getString("outlet_delivery_timing"));

                if (time_slot_type == 2) {
                    params.put("order_delivery_time_slot_from", getFromTime(GlobalValues.DELIVERY_TIME));
                    params.put("order_delivery_time_slot_to", getToTime(GlobalValues.DELIVERY_TIME));
                    params.put("order_is_timeslot", "Yes");
                } else {
                    params.put("order_is_timeslot", "No");
                }

                params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);

                params.put("additional_delivery", "" + GlobalValues.ADDITIONAL_DELEIVERY_CHARGES);


                if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {

                    params.put("delivery_charge", "0.00");




                } else {
                    params.put("delivery_charge", "" + GlobalValues.DELEIVERY_CHARGES);


                }

                if (GlobalValues.isFoodVoucher) {
                    params.put("delivery_charge", "" + "0.00");
                }


                if (time_slot_type == 2) {
                    params.put("order_delivery_time_slot_from", getFromTime(GlobalValues.DELIVERY_TIME));
                    params.put("order_delivery_time_slot_to", getToTime(GlobalValues.DELIVERY_TIME));
                    params.put("order_is_timeslot", "Yes");
//                    params.put("order_pickup_time_slot_from", getFromTime(GlobalValues.DELIVERY_TIME));
//                    params.put("order_pickup_time_slot_to", getToTime(GlobalValues.DELIVERY_TIME));
                } else {
                    params.put("order_is_timeslot", "No");
                }

            }


            try {

                JSONObject jsonObject_paymentdetails = new JSONObject();
                jsonObject_paymentdetails.put("payment_type", "Omise");
                jsonObject_paymentdetails.put("payment_reference_1", "Sushi Tei");
                jsonObject_paymentdetails.put("payment_reference_2", "");
                jsonObject_paymentdetails.put("payment_transaction_amount", amount);
                jsonObject_paymentdetails.put("payment_date", mOrderDate);
                jsonObject_paymentdetails.put("payment_currency", "sg");



                /*    jsonObject_paymentdetails.put("payment_type", "Omise");
                    jsonObject_paymentdetails.put("payment_reference_1",mPrevCaptureId);
                    jsonObject_paymentdetails.put("payment_transaction_amount", amount);
                    jsonObject_paymentdetails.put("payment_date", mOrderDate);
                    jsonObject_paymentdetails.put("payment_currency", "SGD");
                    jsonObject_paymentdetails.put("captureId", mPrevCaptureId);
                      jsonObject_paymentdetails.put("payment_card_last_digit", "1111");
                    jsonObject_paymentdetails.put("payment_card_user_name", "test");
                    jsonObject_paymentdetails.put("log_id", "901");
*/



                           /* JSONArray jsonArray=new JSONArray();
                            jsonArray.put(jsonObject);*/

                params.put("payment_getway_details", jsonObject_paymentdetails.toString());
                ///.formBuilder.add("payment_getway_status", "Success");
                params.put("payment_getway_status", "");


                //New For Revel id
                if (revelJSONString != null) {
                    params.put("revel_json", revelJSONString);

                } else {

                }



                           /* formBuilder.add("payment_type", "Stripe");
                            formBuilder.add("payment_reference_1", id);
                            formBuilder.add("payment_transaction_amount", amount);
                            formBuilder.add("payment_reference_2", balance_transaction);
                            formBuilder.add("payment_date", craeted_date);
                            formBuilder.add("payment_currency", currency);*/
            } catch (Exception e) {
                e.printStackTrace();
            }

              /*  params.put("card_all_digits","4111111111111111");
                params.put("card_first_digits","4111");
                params.put("card_last_digits", "1111");
                params.put("card_first_name", "Test");*/


            if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CARD")) {
                params.put("payment_mode", "3");

            } else if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CASH")) {
                params.put("payment_mode", "1");

            }

            if (GlobalValues.DISCOUNT_APPLIED) {
                params.put("discount_applied", "Yes");
                params.put("discount_amount", r_amount);

                if (isApplyPromo) {

                    if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {

                        params.put("promotion_delivery_charge_applied", "Yes");
                        params.put("promo_code", p_code);
                        params.put("coupon_amount", "0.00");

                    } else {

                        params.put("promo_code", p_code);
                        params.put("coupon_amount", p_amount);
                        params.put("coupon_applied", "Yes");
                        params.put("order_promo_sub_total", promo_subTotal);

                    }

                }

                if (isApplyRedeem) {
                    params.put("redeem_applied", "Yes");
                    params.put("redeem_point", r_point);
                    params.put("redeem_amount", r_amount);
                    params.put("order_reward_sub_total", reward_subTotal);

                    params.put("ascentiscrm_pointsredeem_applied", "Yes");
                    params.put("ascentiscrm_redeemed_points", r_point);
                    params.put("ascentiscrm_discount_amount", r_amount);

                }

                if (isApplyPromo && isApplyRedeem) {
                    params.put("order_discount_both", "1");
                }

            } else {
                params.put("discount_applied", "No");

            }


            if (GlobalValues.IS_ADVANCE_ORDER.equalsIgnoreCase("no")) {

                try {
                    String d[] = GlobalValues.DELIVERY_DATE.split("-");
                    String date = d[2] + "-" + d[1] + "-" + d[0] + " " + GlobalValues.DELIVERY_TIME;

                    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                    Date d1 = simpleDateFormat.parse(date);

                    mOrderDate = simpleDateFormat.format(d1);

                } catch (ParseException e) {
                    e.printStackTrace();
                }


                params.put("order_date", mOrderDate);


            } else {

                try {



                    try {
                        String d[] = GlobalValues.DELIVERY_DATE.split("-");
                        String date = d[2] + "-" + d[1] + "-" + d[0] + " " + GlobalValues.DELIVERY_TIME;

                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                        Date d1 = simpleDateFormat.parse(date);

                        mOrderDate = simpleDateFormat.format(d1);

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }


                    params.put("order_is_advanced", "yes");
                    params.put("order_advanced_date", mOrderDate);
                    params.put("order_date", mOrderDate);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            params.put("tax_charge", GlobalValues.GstChargers);
            params.put("order_tax_calculate_amount", String.valueOf(gstAmount));
            params.put("packaging_charge", Utility.readFromSharedPreference(mContext,GlobalValues.PACKING_CHARGE));

             System.out.print(params.toString());

            if (Utility.networkCheck(mContext)) {
                String url = GlobalUrl.SUBMIT_ORDER_URL;


                new CashOnDeliverySubmitOrderTask(params).execute(url);
            } else {
                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private class CashOnDeliverySubmitOrderTask extends AsyncTask<String, Void, String> {
        String message = "";

        private Map<String, String> orderParams;

        public CashOnDeliverySubmitOrderTask(Map<String, String> orderParams) {
            this.orderParams = orderParams;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], orderParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                try {


                    JSONObject responseJSONObject = new JSONObject(s);

                    if (responseJSONObject.getString("status").equalsIgnoreCase("ok")) {
                        isApplyRedeem=false; //new
                        JSONObject jsonCommon = responseJSONObject.getJSONObject("common");

                        mOrderNo = jsonCommon.getString("local_order_no");

                        orderPrimaryId = jsonCommon.getString("order_primary_id");




                        FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);

                        formBuilder.add("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                        //TODO surya
                        formBuilder.add("token", mPrevCaptureId);

                        formBuilder.add("order_id", orderPrimaryId);

                        formBuilder.add("log_id", "1");

                        formBuilder.add("paid_amount", toalAmount);


                        formBuilder.add("outlet_name", "test");


                        FormBody formBody = formBuilder.build();

                        new WebChargeTask(formBody).execute();

                    } else {
                        if (responseJSONObject.has("retry")) {

                            String retry = responseJSONObject.getString("retry");


                            if (retry.equals("Yes")) {

                                String formError = responseJSONObject.getString("form_error");
                                submitOrderInterrupted("300", formError);

                            } else {

                                if (responseJSONObject.has("refund")) {

                                    String refund = responseJSONObject.getString("refund");

                                    if (refund.equals("Yes")) {

                                        String formError = responseJSONObject.getString("form_error");
                                        Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                                        initiateRefund(3);

                                    } else {

                                        if (responseJSONObject.has("form_error")) {

                                            String formError = responseJSONObject.getString("form_error");
                                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                                            paymentSectionFailure();

                                        } else {

                                            message = responseJSONObject.getString("message");
                                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();
                                            paymentSectionFailure();

                                        }

                                    }

                                } else if (responseJSONObject.has("form_error")) {

                                    String formError = responseJSONObject.getString("form_error");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                                    paymentSectionFailure();

                                } else {

                                    message = responseJSONObject.getString("message");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();
                                    paymentSectionFailure();

                                }

                            }


                        } else if (responseJSONObject.has("refund")) {

                            String refund = responseJSONObject.getString("refund");

                            if (refund.equals("Yes")) {

                                String formError = responseJSONObject.getString("form_error");
                                Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();

                                initiateRefund(3);

                            } else {

                                if (responseJSONObject.has("form_error")) {

                                    String formError = responseJSONObject.getString("form_error");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();

                                    paymentSectionFailure();

                                } else {

                                    message = responseJSONObject.getString("message");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();

                                    paymentSectionFailure();

                                }

                            }

                        } else if (responseJSONObject.has("form_error")) {

                            String formError = responseJSONObject.getString("form_error");
                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();

                            paymentSectionFailure();

                        } else {

                            message = responseJSONObject.getString("message");
                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();


                            paymentSectionFailure();

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();


                    paymentSectionFailure();

                }


            } catch (Exception e) {
                e.printStackTrace();

                paymentSectionFailure();

            }
        }
    }


    public void submitOrderInterrupted(final String errorCode, final String formError) {

        //show popup
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PaymentProcessingActivity.this);
        alertDialog.setMessage(formError);
        alertDialog.setTitle("Error " + errorCode);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (networkCheck()) {

                    dialog.cancel();
                    placeCashOnDeliveryOrder();
                } else {

                    Toast.makeText(PaymentProcessingActivity.this, "Limited internet connectivity, please check if you are online.", Toast.LENGTH_SHORT).show();
                    submitOrderInterrupted(errorCode, formError);

                }
            }
        });
        alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();

                /*Toast.makeText(PaymentProcessingActivity.this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
                paymentSectionFailure();*/


                if (networkCheck()) {
                    //initiateRefund(3);


                    FormBody.Builder formBodyBuilder = new FormBody.Builder()
                            .add("app_id", GlobalValues.APP_ID);

                    // cart_item_id,product_id,product_qty

                    formBodyBuilder.add("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

                    formBodyBuilder.add("token", mPrevCaptureId);

                    formBodyBuilder.add("payment_reference", "Sushi Tei");
                    //formBodyBuilder.add("stripe_envir", GlobalValues.APP_MODE_NELSON);

                    FormBody formBody = formBodyBuilder.build();


                    new WebRefundTask(formBody).execute();


                } else {

                    Toast.makeText(PaymentProcessingActivity.this, "Limited internet connectivity, please check if you are online.", Toast.LENGTH_SHORT).show();
                    submitOrderInterrupted(errorCode, formError);

                }


            }
        });

        alertDialog.show();

    }

    public void initiateRefund(int type) {

        //2 - Authorization


        //3 - Submit Order

        //4 - Charge

        //5 - Close Payment


        if (networkCheck()) {

            Map<String, Object> refundParams = new HashMap<String, Object>();
            refundParams.put("charge", mPrevCaptureId);
            //   asdfsd

//TODO refund
            // new RefundTask(refundParams, type).execute();
        } else {

            Toast.makeText(PaymentProcessingActivity.this, "Uncapture release api. Not refund.", Toast.LENGTH_SHORT).show();


            if (type == 2) {  //Auth

                paymentSectionFailure();

            } else if (type == 3) { //Submit Order
                paymentSectionFailure();
            } else if (type == 4) { //Capture
                orderSectionFailure();
            } else if (type == 5) { //Close Payment
                orderSectionFailure();
                //Something went wrong. Please contact us.
            } else {
                paymentSectionFailure();
            }

        }

    }


    public JSONArray getProductJSONArray(JSONArray cartJSONArray) {

        JSONArray submitOrderJSONArray = new JSONArray();

        try {

            for (int i = 0; i < cartJSONArray.length(); i++) {

                JSONObject cartJSONObject = cartJSONArray.getJSONObject(i);
                JSONObject productJSONObject = new JSONObject();


                String cart_item_id = cartJSONObject.getString("cart_item_id");
                String cart_item_product_id = cartJSONObject.getString("cart_item_product_id");
                String cart_item_product_name = cartJSONObject.getString("cart_item_product_name");
                String cart_item_product_sku = cartJSONObject.getString("cart_item_product_sku");
                String cart_item_product_image = cartJSONObject.getString("cart_item_product_image");
                String cart_item_qty = cartJSONObject.getString("cart_item_qty");
                String cart_item_unit_price = cartJSONObject.getString("cart_item_unit_price");
                String cart_item_total_price = cartJSONObject.getString("cart_item_total_price");
                String cart_item_type = cartJSONObject.getString("cart_item_type");

                String cart_item_special_notes = cartJSONObject.getString("cart_item_special_notes");
                String cart_item_product_voucher_gift_name = cartJSONObject.getString("cart_item_product_voucher_gift_name");
                String cart_item_product_voucher_gift_email = cartJSONObject.getString("cart_item_product_voucher_gift_email");
                String cart_item_product_voucher_gift_mobile = cartJSONObject.getString("cart_item_product_voucher_gift_mobile");
                String cart_item_product_voucher_gift_message = cartJSONObject.getString("cart_item_product_voucher_gift_message");
                String cartVoucherDiscountAmount = cartVoucherDiscountAmt;
                String cartItemVoucherId = cartJSONObject.getString("cart_item_voucher_id");
                String orderItemId = cartJSONObject.getString("cart_voucher_order_item_id");
                String cart_item_voucher_product_free = cartJSONObject.getString("cart_item_voucher_product_free");

                //kaki discount
                try {

                    String cart_item_promotion_discount = cartJSONObject.getString("cart_item_promotion_discount");
                    productJSONObject.put("product_special_amount", cart_item_promotion_discount);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Modifiers

                JSONArray cartModifierJSONArray = cartJSONObject.getJSONArray("modifiers");
                JSONArray reviewModifierJSONArray = new JSONArray();
                //  if (cart_item_type.equals("Modifier")) {
                try {
                    if (cartModifierJSONArray != null && cartModifierJSONArray.length() > 0) {

                        for (int j = 0; j < cartModifierJSONArray.length(); j++) {

                            JSONObject cartModifierJSONObject = cartModifierJSONArray.getJSONObject(j);
                            JSONObject modifierJSONObject = new JSONObject();


                            String cart_modifier_id = cartModifierJSONObject.getString("cart_modifier_id");
                            String cart_modifier_name = cartModifierJSONObject.getString("cart_modifier_name");

                            JSONArray cartModifierValuesJSONArray = cartModifierJSONObject.getJSONArray("modifiers_values");
                            JSONArray reviewModifierValuesJSONArray = new JSONArray();

                            for (int k = 0; k < cartModifierValuesJSONArray.length(); k++) {

                                JSONObject cartModifierSingleValueJObject = cartModifierValuesJSONArray.getJSONObject(k);
                                JSONObject modifierSingleValueJObject = new JSONObject();

                                String cart_modifiervalue_id = cartModifierSingleValueJObject.getString("cart_modifier_id");
                                String cart_modifiervalue_name = cartModifierSingleValueJObject.getString("cart_modifier_name");
                                String cart_modifiervalue_qty = cartModifierSingleValueJObject.getString("cart_modifier_qty");
                                String cart_modifiervalue_price = cartModifierSingleValueJObject.getString("cart_modifier_price");

                                modifierSingleValueJObject.put("modifier_value_id", cart_modifiervalue_id);
                                modifierSingleValueJObject.put("modifier_value_name", cart_modifiervalue_name);
                                modifierSingleValueJObject.put("modifier_value_qty", cart_modifiervalue_qty);
                                modifierSingleValueJObject.put("modifier_value_price", cart_modifiervalue_price);

                                reviewModifierValuesJSONArray.put(modifierSingleValueJObject);

                            }

                            modifierJSONObject.put("modifier_id", cart_modifier_id);
                            modifierJSONObject.put("modifier_name", cart_modifier_name);
                            modifierJSONObject.put("modifiers_values", reviewModifierValuesJSONArray);

                            reviewModifierJSONArray.put(modifierJSONObject);
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                //modifiers end

                //Submenu Component
                JSONArray cartSubMenuCompJSONArray = cartJSONObject.getJSONArray("set_menu_component");
                JSONArray reviewSubMenuCompJSONArray = new JSONArray();


                for (int j = 0; j < cartSubMenuCompJSONArray.length(); j++) {


                    JSONObject cartMenuComponentJSONObj = cartSubMenuCompJSONArray.getJSONObject(j);
                    JSONObject reviewMenuComponentJSONObj = new JSONObject();

                    String menu_component_id = cartMenuComponentJSONObj.getString("menu_component_id");
                    String menu_component_name = cartMenuComponentJSONObj.getString("menu_component_name");


                    JSONArray product_detailsJSONArray = cartMenuComponentJSONObj.getJSONArray("product_details");
                    JSONArray reviewProduct_detailsJSONArray = new JSONArray();

                    for (int k = 0; k < product_detailsJSONArray.length(); k++) {

                        JSONObject subProductJSONObject = product_detailsJSONArray.getJSONObject(k);
                        JSONObject reviewSubProductJSONObject = new JSONObject();

                        String product_id = subProductJSONObject.getString("cart_menu_component_product_id");
                        String product_name = subProductJSONObject.getString("cart_menu_component_product_name");
                        String product_sku = subProductJSONObject.getString("cart_menu_component_product_sku");

                        String product_qty = subProductJSONObject.getString("cart_menu_component_product_qty");
                        String product_price = subProductJSONObject.getString("cart_menu_component_product_price");

                        //  JSONArray subModifiersJSONArray =subProductJSONObject.getJSONArray("modifiers");


                        //Modifiers
                        JSONArray subModifiersJSONArray = subProductJSONObject.getJSONArray("modifiers");
                        JSONArray reviewsubModifierJSONArray = new JSONArray();
                        for (int l = 0; l < subModifiersJSONArray.length(); l++) {

                            JSONObject cartModifierJSONObject = subModifiersJSONArray.getJSONObject(l);
                            JSONObject modifierJSONObject = new JSONObject();


                            String cart_modifier_id = cartModifierJSONObject.getString("cart_modifier_id");
                            String cart_modifier_name = cartModifierJSONObject.getString("cart_modifier_name");

                            JSONArray cartModifierValuesJSONArray = cartModifierJSONObject.getJSONArray("modifiers_values");
                            JSONArray reviewModifierValuesJSONArray = new JSONArray();

                            for (int m = 0; m < cartModifierValuesJSONArray.length(); m++) {

                                JSONObject cartModifierSingleValueJObject = cartModifierValuesJSONArray.getJSONObject(m);
                                JSONObject modifierSingleValueJObject = new JSONObject();

                                String cart_modifiervalue_id = cartModifierSingleValueJObject.getString("cart_modifier_id");
                                String cart_modifiervalue_name = cartModifierSingleValueJObject.getString("cart_modifier_name");
                                String cart_modifiervalue_qty = cartModifierSingleValueJObject.getString("cart_modifier_qty");
                                String cart_modifiervalue_price = cartModifierSingleValueJObject.getString("cart_modifier_price");

                                modifierSingleValueJObject.put("modifier_value_id", cart_modifiervalue_id);
                                modifierSingleValueJObject.put("modifier_value_name", cart_modifiervalue_name);
                                modifierSingleValueJObject.put("modifier_value_qty", cart_modifiervalue_qty);
                                modifierSingleValueJObject.put("modifier_value_price", cart_modifiervalue_price);

                                reviewModifierValuesJSONArray.put(modifierSingleValueJObject);

                            }

                            modifierJSONObject.put("modifier_id", cart_modifier_id);   //key dount modifier
                            modifierJSONObject.put("modifier_name", cart_modifier_name);
                            modifierJSONObject.put("modifiers_values", reviewModifierValuesJSONArray);

                            reviewsubModifierJSONArray.put(modifierJSONObject);
                        }
                        //modifiers end


                        reviewSubProductJSONObject.put("product_id", product_id);
                        reviewSubProductJSONObject.put("product_name", product_name);
                        reviewSubProductJSONObject.put("product_sku", product_sku);

                        reviewSubProductJSONObject.put("product_qty", product_qty);
                        reviewSubProductJSONObject.put("product_price", product_price);

                        reviewSubProductJSONObject.put("modifiers", reviewsubModifierJSONArray);


                        reviewProduct_detailsJSONArray.put(reviewSubProductJSONObject);
                    }

                    reviewMenuComponentJSONObj.put("menu_component_id", menu_component_id);
                    reviewMenuComponentJSONObj.put("menu_component_name", menu_component_name);
                    reviewMenuComponentJSONObj.put("product_details", reviewProduct_detailsJSONArray);
                    //menu_component_min_max_appy
                    if (cartMenuComponentJSONObj.has("cart_menu_component_min_max_appy") &&
                            cartMenuComponentJSONObj.getString("cart_menu_component_min_max_appy") != null) {
                        reviewMenuComponentJSONObj.put("menu_component_min_max_appy", cartMenuComponentJSONObj.getString("cart_menu_component_min_max_appy"));

                    }


                    reviewSubMenuCompJSONArray.put(reviewMenuComponentJSONObj);

                }


                //submenu component end


                //Condiments

                JSONArray condimentsJSONArray = cartJSONObject.getJSONArray("condiments");
                JSONArray reviewCondimentsJSONArray = new JSONArray();

                for (int j = 0; j < condimentsJSONArray.length(); j++) {

                    JSONObject condimentJSONObject = condimentsJSONArray.getJSONObject(j);
                    JSONObject newCondimentJSONObject = new JSONObject();


                    String product_id = condimentJSONObject.getString("product_id");
                    String product_name = condimentJSONObject.getString("product_name").replace("\\", "");
                    String product_qty = condimentJSONObject.getString("product_qty");
                    String product_sku = condimentJSONObject.getString("product_sku");

                    Integer productQuantity = Integer.parseInt(product_qty);
                    Integer cartItemQuantity = Integer.parseInt(cart_item_qty);

                    newCondimentJSONObject.put("product_id", product_id);
                    newCondimentJSONObject.put("product_name", product_name);
                    newCondimentJSONObject.put("product_qty", productQuantity * cartItemQuantity);
                    newCondimentJSONObject.put("product_sku", product_sku);
                    newCondimentJSONObject.put("product_price", 0.00);

                    reviewCondimentsJSONArray.put(newCondimentJSONObject);

                }
                //condiments end


                productJSONObject.put("product_id", cart_item_product_id);
                productJSONObject.put("product_name", cart_item_product_name);
                productJSONObject.put("product_image", cart_item_product_image);
                productJSONObject.put("product_sku", cart_item_product_sku);
                productJSONObject.put("product_qty", cart_item_qty);
                productJSONObject.put("product_unit_price", cart_item_unit_price);
                productJSONObject.put("product_total_amount", cart_item_total_price);
                productJSONObject.put("modifiers", reviewModifierJSONArray);
                productJSONObject.put("menu_set_components", reviewSubMenuCompJSONArray);
                productJSONObject.put("condiments", reviewCondimentsJSONArray);
                productJSONObject.put("product_special_notes", cart_item_special_notes);
                productJSONObject.put("voucher_gift_name", cart_item_product_voucher_gift_name);
                productJSONObject.put("voucher_gift_email", cart_item_product_voucher_gift_email);
                productJSONObject.put("voucher_gift_mobile", cart_item_product_voucher_gift_mobile);
                productJSONObject.put("voucher_gift_message", cart_item_product_voucher_gift_message);
                productJSONObject.put("product_item_voucher_id", cartItemVoucherId);
                productJSONObject.put("order_item_id", orderItemId);
                productJSONObject.put("voucher_free_product", cart_item_voucher_product_free);

                submitOrderJSONArray.put(productJSONObject);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return submitOrderJSONArray;

    }

//TODO REFUND
/*    public class RefundTask extends AsyncTask<String, Refund, Refund> {

        Map<String, Object> refundParams;
        Refund refund;
        ProgressDialog progressDialog;
        int type;

        RefundTask(Map<String, Object> refundParams, int type) {
            this.refundParams = refundParams;
            this.type = type;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(PaymentProcessingActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected Refund doInBackground(String... params) {

            try {
                refund = Refund.create(refundParams);

            } catch (CardException e) {
                // Since it's a decline, CardException will be caught
                System.out.println("CardException Status is: " + e.getCode());
                System.out.println("CardException Message is: " + e.getMessage());
                showToastMessage(e.getMessage());

            } catch (RateLimitException e) {
                // Too many requests made to the API too quickly

                System.out.println("RateLimitException Message is: " + e.getMessage());
                showToastMessage(e.getMessage());

            } catch (InvalidRequestException e) {
                // Invalid parameters were supplied to Stripe's API
                System.out.println("InvalidRequestException Message is: " + e.getMessage());
                showToastMessage(e.getMessage());

            } catch (AuthenticationException e) {
                // Authentication with Stripe's API failed
                System.out.println("AuthenticationException Message is: " + e.getMessage());

                showToastMessage(e.getMessage());

                // (maybe you changed API keys recently)
            } catch (APIConnectionException e) {
                // Network communication with Stripe failed
                System.out.println("APIConnectionException Message is: " + e.getMessage());

                showToastMessage(e.getMessage());

            } catch (StripeException e) {
                // Display a very generic error to the user, and maybe send
                System.out.println("StripeException Message is: " + e.getMessage());

                showToastMessage(e.getMessage());

                // yourself an email
            } catch (Exception e) {
                // Something else happened, completely unrelated to Stripe
                System.out.println("Exception Message is: " + e.getMessage());
                //  System.out.println("Exception Message is: " + e);

                showToastMessage(e.getMessage());

                //    Toast.makeText(PaymentProcessingActivity.this,"Stripe Payment Unsuccessful"  +  e.getMessage() ,Toast.LENGTH_LONG).show();

            }


            return refund;
        }

        @Override
        protected void onPostExecute(Refund s) {
            super.onPostExecute(s);
            progressDialog.dismiss();

            if (refund != null) {


                if (refund.getStatus().equals("succeeded")) {

                    // paymentProcessedPlaceOrder("", "", "", "", "", false);  //Validation


                    Toast.makeText(PaymentProcessingActivity.this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();

                    if (type == 2) {  //Auth
                        paymentSectionFailure();
                    } else if (type == 3) { //Submit Order
                        paymentSectionFailure();
                    } else if (type == 4) { //Capture
                        orderSectionFailure();
                    } else if (type == 5) { //Close Payment
                        orderSectionFailure();

                        // new CloseOrderTask().execute();

                    } else {
                        paymentSectionFailure();
                    }

                } else {

                    Toast.makeText(PaymentProcessingActivity.this, "Uncapture release api. Not refund.", Toast.LENGTH_SHORT).show();

                    if (type == 2) {  //Auth

                        paymentSectionFailure();

                    } else if (type == 3) { //Submit Order
                        paymentSectionFailure();
                    } else if (type == 4) { //Capture
                        orderSectionFailure();
                    } else if (type == 5) { //Close Payment
                        orderSectionFailure();
                        //Something went wrong. Please contact us.
                    } else {
                        paymentSectionFailure();
                    }

                }


            } else {

                Toast.makeText(PaymentProcessingActivity.this, "Uncapture release api. Not refund.", Toast.LENGTH_SHORT).show();

                if (type == 2) {  //Auth
                    paymentSectionFailure();
                } else if (type == 3) { //Submit Order
                    paymentSectionFailure();
                } else if (type == 4) { //Capture
                    orderSectionFailure();
                } else if (type == 5) { //Close Payment
                    orderSectionFailure();
                    //Something went wrong. Please contact us.
                } else {
                    paymentSectionFailure();
                }

            }

        }

    }*/

    private class DestroyCartTask extends AsyncTask<String, Void, String> {

        private Map<String, String> destroyParams;

        public DestroyCartTask(Map<String, String> destroyParams) {
            this.destroyParams = destroyParams;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], destroyParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                JSONObject destroyJson = new JSONObject(s);

                if (destroyJson.getString("status").equalsIgnoreCase("ok")) {
                     Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_RESPONSE);


                    try {
                        databaseHandler.deleteAllCartQuantity();


                        imgGifPaymentProcess.setVisibility(View.GONE);
                        imgGifOrderProcess.setVisibility(View.GONE);
                        imgPaymentProcess.setVisibility(View.VISIBLE);
                        imgPaymentProcess.setImageResource(R.drawable.icon_process_complete);
                        imgOrderProcess.setVisibility(View.VISIBLE);
                        imgOrderProcess.setImageResource(R.drawable.icon_process_complete);

                        orderprocessing_textview.setText("Order Completed");


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(mContext, ThanksActivity.class);
                                intent.putExtra("local_order_no", mOrderNo);
                                startActivity(intent);
                            }
                        }, 500);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {


                     Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_RESPONSE);


                    try {
                        databaseHandler.deleteAllCartQuantity();

                        imgGifOrderProcess.setVisibility(View.GONE);
                        imgGifPaymentProcess.setVisibility(View.GONE);
                        imgPaymentProcess.setVisibility(View.VISIBLE);
                        imgPaymentProcess.setImageResource(R.drawable.icon_process_complete);

                        imgOrderProcess.setVisibility(View.VISIBLE);
                        imgOrderProcess.setImageResource(R.drawable.icon_process_complete);

                        orderprocessing_textview.setText("Order  Completed");


                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(mContext, ThanksActivity.class);
                                intent.putExtra("local_order_no", mOrderNo);
                                startActivity(intent);
                            }
                        }, 500);


                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();

            }
        }
    }

    public void chargeInterrupted(final String errorCode, final String formError) {

        //show popup
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(PaymentProcessingActivity.this);
        alertDialog.setMessage(formError);
        alertDialog.setTitle("Error " + errorCode);
        alertDialog.setCancelable(false);
        alertDialog.setPositiveButton("Retry", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {

                if (networkCheck()) {

                    dialog.cancel();
                    //..   new CaptureChargeTask().execute();

                    chargeRetryShowing = false;
                    currentRetry = 0;

                    startChargeRetry();


                } else {
                    Toast.makeText(PaymentProcessingActivity.this, "Limited internet connectivity, please check if you are online.", Toast.LENGTH_SHORT).show();
                    chargeInterrupted(errorCode, formError);
                }

            }
        });


        chargeAlertDialog = alertDialog.show();


        chargeRetryShowing = true;
        currentRetry = chargeRetry;


    }

    public void startChargeRetry() {

        chargeRetryShowing = false;
        currentRetry = 0;

        ///////////////////////////////
        FormBody.Builder formBuilder = new FormBody.Builder()
                .add("app_id", GlobalValues.APP_ID);

        formBuilder.add("token", mPrevCaptureId);

        formBuilder.add("order_id", orderPrimaryId);

        if (GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {

            /*formBuilder.add("outlet_name", GlobalValues.outletAddress.getOutlet_name());
          */

        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.TAKEAWAY_ID)) {

         /*   formBuilder.add("outlet_name", GlobalValues.pickupOutlet.getOutlet_name());
            */

        }

        formBuilder.add("payment_reference", "Sushi Tei");
        //formBuilder.add("stripe_envir", "live");

        formBuilder.add("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

        FormBody formBody = formBuilder.build();

        new WebChargeTask(formBody).execute();

    }


    //WebCharge Client
    final OkHttpClient webChargeClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public String webChargeRun(RequestBody formBody) throws Exception {
        Request request=null;
        if(!GlobalValues.ACCESS_TOKEN.isEmpty()){
            request = new Request.Builder()
                    .url(GlobalUrl.webChargeURL)
                    .addHeader("Authorization",GlobalValues.ACCESS_TOKEN)
                    .post(formBody)
                    .build();
        }else {
             request = new Request.Builder()
                    .url(GlobalUrl.webChargeURL)
                    .post(formBody)
                    .build();
        }


        Response response = webChargeClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;
    }

    public class WebChargeTask extends AsyncTask<String, String, String> {

        FormBody formBody;
        String response, status, message;

        WebChargeTask(FormBody formBody) {
            this.formBody = formBody;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = webChargeRun(formBody);
                Gson gson = new Gson();
                String json = gson.toJson(formBody);
                Log.e("TAG","FormDataTest_33::"+json+"\n.."+response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (response != null) {

                try {

                    JSONObject jsonObject = new JSONObject(response);


                    String status = jsonObject.getString("status");

                    if (status.equals("ok")) {
                        isApplyRedeem=false; //new 1
                        FormBody.Builder formBodyBuilder = new FormBody.Builder()
                                .add("app_id", GlobalValues.APP_ID);
                        formBodyBuilder.add("order_id", orderPrimaryId);
                        formBodyBuilder.add("request_device", "Android");


                        RequestBody formBody = formBodyBuilder.build();


                        //...    new PushToRevelTask(formBody).execute(); //not needed in george
                        moveToThankYouPage();

                    } else {


                        if (jsonObject.has("capture_status")) {

                            String captureStatus = jsonObject.getString("capture_status");

                            if (captureStatus.equals("Yes")) {

                                FormBody.Builder formBodyBuilder = new FormBody.Builder()
                                        .add("app_id", GlobalValues.APP_ID);
                                formBodyBuilder.add("order_id", orderPrimaryId);
                                formBodyBuilder.add("request_device", "Android");


                                RequestBody formBody = formBodyBuilder.build();

                                //...   new PushToRevelTask(formBody).execute();

                                moveToThankYouPage();

                            } else {


                                if (jsonObject.has("retry")) {

                                    String retry = jsonObject.getString("retry");


                                    if (retry.equals("Yes")) {

                                        String message = "";

                                        if (jsonObject.has("form_error")) {
                                            message = jsonObject.getString("form_error");
                                        } else if (jsonObject.has("form_error")) {
                                            message = jsonObject.getString("message");
                                        }

                                        if (message == null || message.equals("")) {
                                            message = "We couldn't charge your card. Can we retry?";
                                        }

                                        chargeInterrupted("403", message);


                                    } else {

                                        if (jsonObject.has("form_error")) {

                                            String formError = jsonObject.getString("form_error");
                                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                                            orderSectionFailure();


                                        } else {

                                            message = jsonObject.getString("message");
                                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();
                                            orderSectionFailure();

                                        }

                                    }


                                } else if (jsonObject.has("form_error")) {

                                    String formError = jsonObject.getString("form_error");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                                    orderSectionFailure();


                                } else {

                                    message = jsonObject.getString("message");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();
                                    orderSectionFailure();

                                }


                            }


                        } else if (jsonObject.has("retry")) {

                            String retry = jsonObject.getString("retry");


                            if (retry.equals("Yes")) {

                                String message = "";

                                if (jsonObject.has("form_error")) {
                                    message = jsonObject.getString("form_error");
                                } else if (jsonObject.has("form_error")) {
                                    message = jsonObject.getString("message");
                                }

                                if (message == null || message.equals("")) {
                                    message = "We couldn't charge your card. Can we retry?";
                                }

                                chargeInterrupted("402", message);


                            } else {

                                if (jsonObject.has("form_error")) {

                                    String formError = jsonObject.getString("form_error");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                                    orderSectionFailure();


                                } else {

                                    message = jsonObject.getString("message");
                                    Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();
                                    orderSectionFailure();

                                }

                            }


                        } else if (jsonObject.has("form_error")) {

                            String formError = jsonObject.getString("form_error");
                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                            orderSectionFailure();


                        } else {

                            message = jsonObject.getString("message");
                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();
                            orderSectionFailure();

                        }

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(PaymentProcessingActivity.this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
                    chargeInterrupted("401", "Unexpected error has occured, your order is not complete. Would you like to try again?");
                    //We couldn't charge your card. Can we retry?


                }
            } else {


                chargeInterrupted("400", "Unexpected error has occured, your order is not complete. Would you like to try again?");

            }


        }
    }

    public void orderSectionFailure() {

        imgGifOrderProcess.setVisibility(View.GONE);
        imgOrderProcess.setVisibility(View.VISIBLE);
        imgOrderProcess.setImageResource(R.drawable.delete);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                finish();
            }
        }, 2000);
    }


    public void moveToThankYouPage() {
        String url = GlobalUrl.DESTROY_CART_URL;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
        isApplyRedeem=false;
//                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

        if (Utility.networkCheck(mContext)) {

            new DestroyCartTask(params).execute(url);
        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        //cancel all the handler if it is not null
        //  unregisterReceiver(broadcastReceiver);

        isVisible = false;

        networkhandler.removeCallbacksAndMessages(null);
        // networkhandler.removeCallbacks(networkRunnable);


    }

    final OkHttpClient webRefundClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public String webRefundRun(RequestBody formBody) throws Exception {
        Request request=null;
        if(!GlobalValues.ACCESS_TOKEN.isEmpty()){
            request = new Request.Builder()
                    .url(GlobalUrl.webRefundURL)
                    .addHeader("Authorization",GlobalValues.ACCESS_TOKEN)
                    .post(formBody)
                    .build();
        }else {
             request = new Request.Builder()
                    .url(GlobalUrl.webRefundURL)
                    .post(formBody)
                    .build();
        }


        Response response = webRefundClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;

    }

    public class WebRefundTask extends AsyncTask<String, String, String> {

        FormBody formBody;
        String response, status, message;
        ProgressDialog progressDialog;

        WebRefundTask(FormBody formBody) {
            this.formBody = formBody;
        }

        @Override
        protected void onPreExecute() {

            super.onPreExecute();
            progressDialog = new ProgressDialog(PaymentProcessingActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = webRefundRun(formBody);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            progressDialog.dismiss();

            if (response != null) {

                try {



                    JSONObject jsonObject = new JSONObject(response);

                    String status = jsonObject.getString("status");

                    if (status.equals("ok")) {

                        //      paymentProcessedPlaceOrder("", "", "", "", "", false);  //Validation

                        //previous page

                        paymentSectionFailure();


                    } else {

                       /* if (jsonObject.has("form_error")) {
                            String formError = jsonObject.getString("form_error");
                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                        } else {

                            message = jsonObject.getString("message");
                            Toast.makeText(PaymentProcessingActivity.this, Html.fromHtml(message), Toast.LENGTH_LONG).show();

                        }*/
                        Toast.makeText(PaymentProcessingActivity.this, "Uncapture release api. Not refund.", Toast.LENGTH_SHORT).show();

                        paymentSectionFailure();

                    }


                } catch (Exception e) {
                    e.printStackTrace();

                    Toast.makeText(PaymentProcessingActivity.this, "Uncapture release api. Not refund.", Toast.LENGTH_SHORT).show();

                    paymentSectionFailure();

                }
            } else {


                Toast.makeText(PaymentProcessingActivity.this, "Uncapture release api. Not refund.", Toast.LENGTH_SHORT).show();

                paymentSectionFailure();


            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    private String setTime(String time) {
        if (time.toUpperCase().contains("PM") || time.toUpperCase().contains("AM")) {
            spannableTime = SpannableString.valueOf(time);
        } else {
            spannableTime = new SpannableString(Utility.convertTime(time));
        }
        // spannableTime.setSpan(new UnderlineSpan(), 0, time.length(), 0);
        if (time_slot_type == 2) {
            if (time.toUpperCase().contains("PM") || time.toUpperCase().contains("AM")) {
                spannableTime = SpannableString.valueOf(time);
            } else {
                try {
                    spannableTime = new SpannableString(Utility.convertTime(time.split(" - ")[0]) + " - " + Utility.convertTime(time.split(" - ")[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return String.valueOf((spannableTime));

    }

    private String getFromTime(String time) {

        try {
            // spannableTime.setSpan(new UnderlineSpan(), 0, time.length(), 0);
            if (time_slot_type == 2) {
                if (!(time.toUpperCase().contains("PM") || time.toUpperCase().contains("AM"))) {
                    spannableTime = SpannableString.valueOf(time.split(" - ")[0]);
                } else {
                    try {
                        spannableTime = new SpannableString(Utility.convertTimeTo24hrFormat(time.split(" - ")[0]));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return String.valueOf((spannableTime));

    }

    private String getToTime(String time) {

        // spannableTime.setSpan(new UnderlineSpan(), 0, time.length(), 0);
        if (time_slot_type == 2) {
            if (!time.toUpperCase().contains("PM") || time.toUpperCase().contains("AM")) {
                spannableTime = SpannableString.valueOf(time.split(" - ")[1]);
            } else {
                try {
                    spannableTime = new SpannableString(Utility.convertTimeTo24hrFormat(time.split(" - ")[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return String.valueOf((spannableTime));

    }
}
