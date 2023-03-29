package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class VerificationActivity extends AppCompatActivity {

    EditText edt_email, edt_otp;
    String user_email = "", user_otp = "", error = "";
    private Context mContext;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verification);
        mContext = VerificationActivity.this;
        edt_email = findViewById(R.id.edt_email);
        edt_otp = findViewById(R.id.edt_otp);

        Intent intent = getIntent();
        String action = intent.getAction();
        Uri data = intent.getData();

        if(data != null){
            //Toast.makeText(this, data.getQueryParameter("otp"), Toast.LENGTH_SHORT).show();

            /*user_otp = data.getQueryParameter("otp");
            if(user_otp != null && !user_otp.isEmpty()){
                edt_otp.setText(user_otp);
            }*/
        }



        findViewById(R.id.verify).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_email = edt_email.getText().toString();
                user_otp = edt_otp.getText().toString();
                if(isValidData()){
                    if (Utility.networkCheck(mContext)) {
                        String url = GlobalUrl.EMAIL_VERIFICATION;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("verify_type", "corporate_verify");
                        params.put("customer_email", user_email);
                        params.put("customer_otp", user_otp);

                        new VerifyEmail(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(mContext, error, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidData() {
        if (user_email.isEmpty()){
            error = "Please Enter Email";
            return false;
        }else if (!emailValidation(user_email)){
            error = "Please Enter Valid Email";
            return false;
        }else if (user_otp.isEmpty()){
            error = "Please Enter Your OTP";
            return false;
        }else if(user_otp.length() != 6){
            error = "Please Enter Valid OTP";
            return false;
        }else{
            return true;
        }
    }

    private boolean emailValidation(String Email) {
        return (!TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches());
    }

    private class VerifyEmail extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> userInfoParams;

        public VerifyEmail(Map<String, String> params) {
            userInfoParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return WebserviceAssessor.postRequest(mContext, params[0], userInfoParams);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if(jsonObject.optString("status").equalsIgnoreCase("ok")){
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    if(isLoggedIn()){ // user logged in
                        intent = new Intent(mContext, FiveMenuActivityNew.class);
                        intent.putExtra("position", 0);
                        intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                    }else{ // user not logged in
                        intent = new Intent(mContext, LoginActivity.class);
                    }
                    startActivity(intent);
                    finish();
                }else{
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
            progressDialog.dismiss();
        }
    }

    private boolean isLoggedIn() {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            return true;
        } else {
            return false;
        }
    }
}