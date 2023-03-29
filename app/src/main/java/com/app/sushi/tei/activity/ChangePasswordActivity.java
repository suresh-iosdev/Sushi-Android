package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ChangePasswordActivity extends AppCompatActivity {
    private ImageView img_back;
    private Toolbar toolBar;
    private EditText old_password,new_password,reenter_password;
    private TextView update;
    private Context context;
    public String TAG="ChangePasswordActivity";
    LinearLayout toolbarBack;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        context=this;
        databaseHandler = DatabaseHandler.getInstance(context);
        idview();
    }

    private void idview() {
        toolBar=findViewById(R.id.toolBar);
        img_back=toolBar.findViewById(R.id.img_back);
        toolbarBack=toolBar.findViewById(R.id.toolbarBack);

        update=findViewById(R.id.update);
        new_password=findViewById(R.id.new_password);
        old_password=findViewById(R.id.old_password);
        reenter_password=findViewById(R.id.reenter_password);

        img_back.setVisibility(View.VISIBLE);


        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (old_password.getText().toString().isEmpty()){
                    message(context,"Please enter old password");
                    return;
                }
                if (new_password.getText().toString().isEmpty()){
                    message(context,"Please enter new password");
                    return;
                }
                if (reenter_password.getText().toString().isEmpty()){
                    message(context,"Please enter repassword");
                    return;
                }
                if (!new_password.getText().toString().equals(reenter_password.getText().toString())){
                    message(context,"Entered password mismatch");
                    return;
                }

                if (Utility.networkCheck(context)) {
                    String mobile="";
                    String url = GlobalUrl.MEMBER_CHANGE_PASS;
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("app_id", GlobalValues.APP_ID);
                    params.put("type", "mobile");
        //            params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
        //            params.put("user_id", Utility.readFromSharedPreference(context, GlobalValues.MEMBERSHIP_ID));
                    if (GlobalValues.ACCOUNT_DETAIL.getCustomer_phone() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_phone().equals("null")) {
                       mobile=GlobalValues.ACCOUNT_DETAIL.getCustomer_phone();
                    }
                    params.put("user_id",mobile);
                    params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));

                    params.put("confirm_password", reenter_password.getText().toString());
                    params.put("old_password", old_password.getText().toString());
                    params.put("new_password", new_password.getText().toString());

                    new ChangepaswordTask(params,context).execute(url);
                }

            }
        });
    }

    private void message(Context context, String msg) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
    }

    private class ChangepaswordTask extends AsyncTask<String,Void,String> {
        private Map<String, String> params;
        private Context context;
        ProgressDialog progressDialog;
        public ChangepaswordTask(Map<String, String> params, Context context) {
            this.context=context;
            this.params=params;
        }
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog=new ProgressDialog(context);
            progressDialog.setMessage("Please wait...!!");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            Log.e(TAG,"ChangePassword_Url_parms::"+strings[0]+"\n"+params);
            String responce= WebserviceAssessor.postRequest(context,strings[0],params);
            return responce;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            progressDialog.dismiss();
            Log.e(TAG,"ChangePasswordResponce1::"+s);
            try {
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    message(context,"Successfully update password");
                    Intent intent = new Intent(context, FiveMenuActivityNew.class);
                    intent.putExtra("position", 0);
                    intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                    startActivity(intent);
                    finish();


                    clearCart();
                    // resideMenu.closeMenu();
                    Utility.removeFromSharedPreference(context, GlobalValues.CUSTOMERID);
                    Utility.removeFromSharedPreference(context, GlobalValues.FIRSTNAME);
                    Utility.removeFromSharedPreference(context, GlobalValues.LASTNAME);
                    Utility.removeFromSharedPreference(context, GlobalValues.EMAIL);
                    Utility.removeFromSharedPreference(context, GlobalValues.CUSTOMERPHONE);
                    Utility.removeFromSharedPreference(context, GlobalValues.POSTALCODE);
                    Utility.removeFromSharedPreference(context, GlobalValues.CART_COUNT);
                    Utility.removeFromSharedPreference(context, GlobalValues.BENTO_CART_COUNT);
                    Utility.removeFromSharedPreference(context, GlobalValues.BENTO_CART_COUNT);
                    Utility.removeFromSharedPreference(context, GlobalValues.PROMOTIONCOUNT);
                  
                    

                    try {
                        databaseHandler.deleteAllCartQuantity();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    invalidateOptionsMenu();

                     intent=new Intent(context,MemberLogRegActivity.class);
                     intent.putExtra("from", "Member Sign In");
                     intent.putExtra("value","changepass");
                     startActivity(intent);
                    finish();
                }
                else {
                    message(context,jsonObject.getString("message"));
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    private void clearCart() {
        String url1 = GlobalUrl.DESTROY_CART_URL;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
        params.put("reference_id", "");

        if (Utility.networkCheck(context)) {

            new DestroyCartTask(params).execute(url1);
        }
    }


    private class DestroyCartTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> destroyParams;

        public DestroyCartTask(Map<String, String> destroyParams) {
            this.destroyParams = destroyParams;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(context, params[0], destroyParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject destroyJson = new JSONObject(s);

                if (destroyJson.getString("status").equalsIgnoreCase("ok")) {
                    Utility.removeFromSharedPreference(context, GlobalValues.CART_COUNT);
                    Utility.removeFromSharedPreference(context, GlobalValues.CART_RESPONSE);
                    Utility.removeFromSharedPreference(context, GlobalValues.BENTO_CART_COUNT);


                    try {
                        databaseHandler.deleteAllCartQuantity();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                progressDialog.dismiss();


            }
        }
    }
}