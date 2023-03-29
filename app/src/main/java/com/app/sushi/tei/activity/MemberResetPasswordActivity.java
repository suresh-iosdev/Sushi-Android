package com.app.sushi.tei.activity;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.vkey.android.internal.vguard.util.Log;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class MemberResetPasswordActivity extends AppCompatActivity {

    private Context mContext;
    private EditText edtNewPassword, edtConfirmPassword;
    private TextView txtSubmit;
    private String mNewPassword = "", mConfirmPassword = "";
    private String mValidationMessage = "";
    private String mKey = "";

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reset_password_member);

        mContext = MemberResetPasswordActivity.this;

        edtNewPassword = (EditText) findViewById(R.id.edtNewPassword);
        edtConfirmPassword = (EditText) findViewById(R.id.edtConfirmPassword);
        txtSubmit = (TextView) findViewById(R.id.txtSubmit);


        Uri uri = getIntent().getData();
        Log.e("TAG","MemberResetPassTest00::"+uri.toString());
        if (uri != null) {
            mKey = uri.getQueryParameter("key");
            Log.e("TAG","MemberResetPassTest11::"+mKey);
        }

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mNewPassword = edtNewPassword.getText().toString();
                mConfirmPassword = edtConfirmPassword.getText().toString();


                if (validate()) {

                    //service call

                    String url = GlobalUrl.MEMBER_RESET_PASSWORD_URL;

                    Map<String, String> param = new HashMap<String, String>();

                    param.put("app_id", GlobalValues.APP_ID);
                    param.put("password", mNewPassword);
                    param.put("confirmpassword", mConfirmPassword);
                    param.put("member_key", mKey);
                    param.put("type", "Membership");
                    Log.e("TAG","MemberResetPassTest_1::"+param.toString());
                    new ResetPasswordTask(param).execute(url);

                } else {

                    Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    private boolean validate() {

        boolean validate = false;

        if (mNewPassword.length() <= 0) {
            validate = false;
            mValidationMessage = "Please enter new password";
        } else if (mConfirmPassword.length() <= 0) {
            validate = false;
            mValidationMessage = "Please enter confirm password";
        } else if (!mNewPassword.equals(mConfirmPassword)) {
            validate = false;
            mValidationMessage = "New Password and Confirm password must be same";
        } else {
            validate = true;
        }

        return validate;

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

                    Intent intent=new Intent(mContext,MemberLogRegActivity.class);
                    intent.putExtra("from", "Member Sign In");
                    intent.putExtra("value","Splash");
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

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
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
