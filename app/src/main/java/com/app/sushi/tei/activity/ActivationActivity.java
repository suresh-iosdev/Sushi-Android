package com.app.sushi.tei.activity;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.R;
import com.app.sushi.tei.WebService.WebserviceAssessor;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ActivationActivity extends AppCompatActivity {
    private Context mContext;
    private Toolbar toolbar;
    private LinearLayout imgBack;
    private TextView  txtHome, txtText;
    ImageView txtTitle;
    private Intent intent;
    private String mKey = "", mAppId = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_activation);

        mContext = ActivationActivity.this;

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        txtTitle =  toolbar.findViewById(R.id.toolbartxtTitle);
        imgBack = toolbar.findViewById(R.id.toolbarBack);
        imgBack.setVisibility(View.GONE);

        txtHome = (TextView) findViewById(R.id.txtHome);
        txtText = (TextView) findViewById(R.id.txtText);


        Uri uri = getIntent().getData();

        if (uri != null) {

            mKey = uri.getQueryParameter("key");
            mAppId = uri.getQueryParameter("app_id");


            String url = GlobalUrl.ACTIVATION_URL;

            Map<String, String> param = new HashMap<>();
            param.put("key", mKey);
            param.put("app_id", mAppId);

            new ActivationTask(param).execute(url);
        }

        txtHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();

            }
        });


    }

    @Override
    protected void onRestart() {
        super.onRestart();

        txtTitle.setVisibility(View.VISIBLE);
//        txtTitle.setText("Account Activation");

    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();

        intent = new Intent(mContext, SubCategoryActivity.class);
        startActivity(intent);
        finish();

    }

    private class ActivationTask extends AsyncTask<String, Void, String> {
        private Map<String, String> activatioParams;

        public ActivationTask(Map<String, String> param) {

            activatioParams = param;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            txtText.setText("Activating your account. Please wait...");
        }

        @Override
        protected String doInBackground(String... params) {

            String response = WebserviceAssessor.postRequest(mContext, params[0], activatioParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    txtText.setText("Your account has been activated successfully.");
                } else {
                    txtText.setText("Link Expired");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {

            }
        }
    }
}
