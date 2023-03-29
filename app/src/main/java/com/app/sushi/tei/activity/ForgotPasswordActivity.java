package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.appcompat.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ForgotPasswordActivity extends AppCompatActivity {


    private Context mContext;
    private EditText edtMobileNumber, edtOTP, edtPin, edtConfirmPin;
    private TextView txtSubmit, txt_resend, txt_otpSeconds;
    private String emailAddress, mOTP, mPIN, mConfirmPIN;
    private RelativeLayout layoutClose;
    private String mValidationMessage = "";
    private Boolean getOTP = false;
    private Handler customHandler = new Handler();
    int initial = 60;
    int count = 0;
    private String key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot_password);

        mContext = ForgotPasswordActivity.this;
        edtMobileNumber = (EditText) findViewById(R.id.edtMobileNumber);
        edtOTP = (EditText) findViewById(R.id.edtOTP);
        edtPin = (EditText) findViewById(R.id.edtPin);
        edtConfirmPin = (EditText) findViewById(R.id.edtConfirmPin);

        txt_resend = findViewById(R.id.txt_resend);
        txt_otpSeconds = findViewById(R.id.txt_otpSeconds);

        txtSubmit = (TextView) findViewById(R.id.txtSubmit);
        layoutClose = (RelativeLayout) findViewById(R.id.layoutClose);

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                emailAddress = edtMobileNumber.getText().toString();
                mOTP = edtOTP.getText().toString();
                mPIN = edtPin.getText().toString();
                mConfirmPIN = edtConfirmPin.getText().toString();

                if (validateEmail()) {
                    if (Utility.networkCheck(mContext)) {


                        Map<String, String> params = new HashMap<>();

//                        if (edtMobileNumber.getText().toString().trim().equalsIgnoreCase("")) {
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("email_address", emailAddress);
                        params.put("type", "mobile");
                        params.put("site_url", GlobalUrl.BASE_URL);
  //                      params.put("site_url", "https://sushitei.promobuddy.asia/");
//                            params.put("verify_type", "forgot");
                        String url = GlobalUrl.FORGOT_PASSWORD_URL;
                        new ForgotPassword(params).execute(url);
//                        } else {
//                            Toast.makeText(mContext, "Get OTP and Continue", Toast.LENGTH_SHORT).show();
//                        }
//                        if(edtPin.isShown()){
//                            if(edtPin.getText().toString().trim().equalsIgnoreCase("")){
//                                Toast.makeText(mContext, "Please enter valid PIN", Toast.LENGTH_SHORT).show();
//                            }else if(edtConfirmPin.getText().toString().trim().equalsIgnoreCase("")){
//                                Toast.makeText(mContext, "PIN does not match", Toast.LENGTH_SHORT).show();
//                            }else if(!edtPin.getText().toString().trim().equalsIgnoreCase(edtConfirmPin.getText().toString().trim())){
//                                Toast.makeText(mContext, "PIN does not match", Toast.LENGTH_SHORT).show();
//                            }else if(edtConfirmPin.getText().toString().length() < 6){
//                                Toast.makeText(mContext, "Please enter 6-digit PIN", Toast.LENGTH_SHORT).show();
//                            }else if(edtPin.getText().toString().length() < 6){
//                                Toast.makeText(mContext, "Please enter 6-digit PIN", Toast.LENGTH_SHORT).show();
//                            }else{
//                                if(validateReset()){
//                                    String url = GlobalUrl.RESET_PASSWORD_URL;
//
//                                    Map<String, String> param = new HashMap<String, String>();
//
//                                    param.put("app_id", GlobalValues.APP_ID);
//                                    param.put("password", mPIN);
//                                    param.put("confirmpassword", mConfirmPIN);
//                                    param.put("key", key);
//                                    param.put("type", "pin");
//
//                                    new ResetPasswordTask(param).execute(url);
//                                }else {
//                                    Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
//                                }
//                            }
//                            return;
//                        }

//                        if (!getOTP) {
//                            if (edtOTP.getText().toString().trim().equalsIgnoreCase("")) {
//                                params.put("app_id", GlobalValues.APP_ID);
//                                params.put("customer_phone", mMobileNumber);
//                                params.put("verify_type", "forgot");
//                                String url = GlobalUrl.SEND_OTP;
//                                new SendOTP(params).execute(url);
//                            } else {
//                                Toast.makeText(mContext, "Get OTP and Continue", Toast.LENGTH_SHORT).show();
//                            }
//                        }else {
//                            if (!(edtOTP.getText().toString().trim().equalsIgnoreCase(""))) {
//                                if (!(edtOTP.length() > 5)) {
//                                    Toast.makeText(mContext, "Please enter 6 digit pin number", Toast.LENGTH_SHORT).show();
//                                    return;
//                                }
//                                params.put("app_id", GlobalValues.APP_ID);
//                                params.put("customer_phone", mMobileNumber);
//                                params.put("customer_otp_val", edtOTP.getText().toString());
//                                params.put("verify_type", "forgot");
//                                String url = GlobalUrl.OTP_VERIFICATION;
//                                new VerifyOTP(params).execute(url);
//                            } else {
//                                Toast.makeText(mContext, "Enter Received OTP", Toast.LENGTH_SHORT).show();
//                            }
//                        }
                    } else {
                        Toast.makeText(mContext, "You are offline please check your internet connection", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.networkCheck(mContext)) {

                    String url = GlobalUrl.RESEND_OTP_URL;
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("app_id", GlobalValues.APP_ID);
//                    params.put("customer_mobile", mMobileNumber);
                    params.put("email_address", emailAddress);
                    params.put("verify_type", "forgot");


                    //params.put("site_url","");

                    new ResendOTP(params).execute(url);
                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private boolean validateEmail() {

        boolean isValidate = false;

        if (emailAddress.length() <= 0) {
            isValidate = false;
            mValidationMessage = "Please enter valid email";

        } else if ((!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())) {
            isValidate = false;
            mValidationMessage = "Please enter valid email";
        } else {
            isValidate = true;
        }

        return isValidate;


    }

    private class SendOTP extends AsyncTask<String, Void, String> {

        private Map<String, String> forgotParams;
        private ProgressDialog progressDialog;


        public SendOTP(Map<String, String> params) {

            forgotParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Retrieving OTP...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {



            String response = WebserviceAssessor.postRequest(mContext, params[0], forgotParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);

                Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getOTP = true;
                    edtOTP.setVisibility(View.VISIBLE);
                    txt_otpSeconds.setVisibility(View.GONE);
                    customHandler.postDelayed(updateTimerThread, 1000);
                } else {

                    String message = jsonObject.getString("message");
                    String form_error = jsonObject.optString("form_error");
                    String messageCom = message + "" + Html.fromHtml(form_error);
                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }

    private class ForgotPassword extends AsyncTask<String, Void, String> {

        private Map<String, String> forgotParams;
        private ProgressDialog progressDialog;


        public ForgotPassword(Map<String, String> params) {

            forgotParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Sending email...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], forgotParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);

//                Toast.makeText(getApplicationContext(), jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    getOTP = true;
//                    edtOTP.setVisibility(View.VISIBLE);
//                    edtOTP.setVisibility(View.GONE);
//                    txt_otpSeconds.setVisibility(View.GONE);
//                    customHandler.postDelayed(updateTimerThread, 1000);
//                    Intent intent = new Intent(mContext, LoginActivity.class);
//                  intent.putExtra(FROM_KEY, FROM_RESET);
//                    startActivity(intent);
                    finish();
                } else {

                    String message = jsonObject.getString("message");
                    String form_error = jsonObject.optString("form_error");
                    String messageCom = message + "" + Html.fromHtml(form_error);
                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }

    Runnable updateTimerThread = new Runnable() {

        public void run() {

            if (count < 60) {
                count++;
                int secs = initial - count;
                txt_otpSeconds.setText(String.format(secs + " Seconds Remaining"));
            } else {
                customHandler.removeCallbacks(updateTimerThread);
                txt_otpSeconds.setText(String.format("0 Seconds Remaining"));
                if (!(edtPin.isShown())) {
                    txt_resend.setVisibility(View.GONE);
                }
            }
            customHandler.postDelayed(this, 1000);
        }
    };

    private class VerifyOTP extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> userInfoParams;

        public VerifyOTP(Map<String, String> params) {
            userInfoParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Verify OTP...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], userInfoParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    key = jsonObject.getString("key");
                    txt_resend.setVisibility(View.GONE);
                    txt_otpSeconds.setVisibility(View.GONE);
                    edtMobileNumber.setVisibility(View.GONE);
                    edtOTP.setVisibility(View.GONE);
                    edtConfirmPin.setVisibility(View.VISIBLE);
                    edtPin.setVisibility(View.VISIBLE);

                } else {

                    String message = jsonObject.getString("message");
                    String form_error = jsonObject.optString("form_error");
                    String messageCom = message + "" + Html.fromHtml(form_error);
                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
            progressDialog.dismiss();
        }
    }

    private class ResendOTP extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> userInfoParams;

        public ResendOTP(Map<String, String> params) {
            userInfoParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Retrieving OTP...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], userInfoParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getOTP = true;
//                    edtOTP.setVisibility(View.VISIBLE);
                    edtOTP.setVisibility(View.GONE);
                    txt_otpSeconds.setVisibility(View.GONE);
                    customHandler.postDelayed(updateTimerThread, 1000);

                } else {

                    String message = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//


                    String form_error = jsonObject.optString("form_error");

                    String messageCom = message + "" + Html.fromHtml(form_error);

                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();
        }
    }

    private class ResetPasswordTask extends AsyncTask<String, Void, String> {

        private Map<String, String> resetParams;
        private ProgressDialog progressDialog;

        public ResetPasswordTask(Map<String, String> param) {
            resetParams = param;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], resetParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();


                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    //  intent.putExtra(FROM_KEY, FROM_RESET);
                    startActivity(intent);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }

    private class ResetPasswordTaskOld extends AsyncTask<String, Void, String> {

        private Map<String, String> resetParams;
        private ProgressDialog progressDialog;

        public ResetPasswordTaskOld(Map<String, String> param) {
            resetParams = param;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], resetParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();


                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Intent intent = new Intent(mContext, LoginActivity.class);
                    //  intent.putExtra(FROM_KEY, FROM_RESET);
                    startActivity(intent);
                    finish();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }
//
//    private boolean validateReset() {
//
//        boolean validate = false;
//
//        if (edtPin.length() <= 0) {
//            validate = false;
//            mValidationMessage = "Please enter valid PIN";
//        } else if (edtConfirmPin.length() <= 0) {
//            validate = false;
//            mValidationMessage = "Please enter confirm password";
//        } else if (!mPIN.equals(mConfirmPIN)) {
//            validate = false;
//            mValidationMessage = "New Password and Confirm password must be same";
//        } else {
//            validate = true;
//        }
//
//        return validate;
//
//    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
   /*     imgLogo.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.GONE)*/
        ;
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }

    }
}
