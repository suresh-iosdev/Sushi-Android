package com.app.sushi.tei.dialog;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.LoginActivity;
import com.app.sushi.tei.activity.SubCategoryActivity;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LogoutDialogFragment extends DialogFragment {
    private Button btnOk, btnCancel;
    private Context mContext;
    private DatabaseHandler databaseHandler;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_show_alert_logout, container,false);
        intiView(view);
        return view;
    }

    private void intiView(View view){
        mContext = getActivity();
        btnOk = view.findViewById(R.id.btnOk);
        databaseHandler = DatabaseHandler.getInstance(mContext);
        btnCancel = view.findViewById(R.id.btnCancel);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearCart();

                Utility.removeFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                Utility.removeFromSharedPreference(mContext, GlobalValues.FIRSTNAME);
                Utility.removeFromSharedPreference(mContext, GlobalValues.LASTNAME);
                Utility.removeFromSharedPreference(mContext, GlobalValues.EMAIL);
                Utility.removeFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE);
                Utility.removeFromSharedPreference(mContext, GlobalValues.POSTALCODE);
                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                Utility.removeFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);
                Utility.removeFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);
                Utility.removeFromSharedPreference(mContext, GlobalValues.PROMOTIONCOUNT);

                try {
                    databaseHandler.deleteAllCartQuantity();
                } catch (Exception e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                mContext.startActivity(intent);
                getActivity().finish();
                Toast.makeText(mContext, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SubCategoryActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
    }

    private void clearCart() {
        String url1 = GlobalUrl.DESTROY_CART_URL;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
        params.put("reference_id", "");

        if (Utility.networkCheck(mContext)) {

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

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
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
                    Utility.removeFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);


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
