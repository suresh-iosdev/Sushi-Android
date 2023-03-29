package com.app.sushi.tei.adapter.FiveMenu;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import android.util.Log;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.fragment.FiveMenu.FiveMenuOrderFragment;
import com.app.sushi.tei.fragment.FiveMenu.FragmentReward;
import com.app.sushi.tei.fragment.FiveMenu.FragmentVoucher;
import com.app.sushi.tei.fragment.FiveMenu.MyAccountFragmentNew;
import com.app.sushi.tei.fragment.FiveMenu.PromotionFragment;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class FiveMenuPagerAdapter extends FragmentPagerAdapter {

    private Context mContext;
    private int mTabCount = 0;
    private Fragment fragment;
    private int mFrom=0;
    private DatabaseHandler databaseHandler;

    public FiveMenuPagerAdapter(FragmentManager fm, Context context, int length, int from) {
        super(fm);
        this.mContext = context;
        this.mTabCount = length;
        this.mFrom=from;
        databaseHandler = DatabaseHandler.getInstance(context);
    }

    @Override
    public Fragment getItem(int position) {
        Bundle bundle=null;
        fragment = null;

        switch (position) {

            case 0:
                fragment = new MyAccountFragmentNew();
                bundle=new Bundle();
                bundle.putInt(GlobalValues.FROM_KEY, mFrom);
                fragment.setArguments(bundle);
                break;
            case 1:
                fragment = new FiveMenuOrderFragment();
                break;
            case 2:
                fragment = new FragmentReward();
                break;
            case 3:
                fragment = new FragmentVoucher();
                bundle=new Bundle();
                bundle.putInt(GlobalValues.FROM_KEY, mFrom);
                fragment.setArguments(bundle);
                break;
            case 4:
                fragment = new PromotionFragment();
                bundle=new Bundle();
                bundle.putInt(GlobalValues.FROM_KEY, mFrom);
                fragment.setArguments(bundle);
               /* new AlertDialog(mContext, new AlertDialog.OnSlectedString() {
                   @Override
                   public void selectedAction(String action) {
                       if(action.equalsIgnoreCase("Ok")){
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
                           Toast.makeText(mContext, "Logged out successfully", Toast.LENGTH_SHORT).show();
                       }else{
                           fragment = new FiveMenuOrderFragment();
                       }
                   }
               });*/
                break;

        }

        return fragment;
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

    @Override
    public int getCount() {
        return mTabCount;
    }
}
