package com.app.sushi.tei.activity;



import static com.app.sushi.tei.GlobalMembers.GlobalValues.PURCHASE;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.REGISTER;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.nets.nofsdk.exception.CardNotRegisteredException;
import com.nets.nofsdk.exception.ServiceAlreadyInitializedException;
import com.nets.nofsdk.exception.ServiceNotInitializedException;
import com.nets.nofsdk.model.PublicKeyComponent;
import com.nets.nofsdk.model.PublicKeySet;
import com.nets.nofsdk.request.Deregistration;
import com.nets.nofsdk.request.NofService;
import com.nets.nofsdk.request.Registration;
import com.nets.nofsdk.request.StatusCallback;
import com.nets.nofsdk.request.VGuardService;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class NetsTestActivity extends AppCompatActivity {
    Button nets;
    Context context;
    String APP_ID = "00011000002";
    String MID = "11136198900";
    String APP_SECRET = "c72df325-bca0-4400-bd6c-50d2dbb76bfe";
    private static final String MAP_PUB_KEY_ID = "2";
    private static final String EXPONENT = "03";
    private static final String MODULUS = "cfbb65135256d4e525fc6aada10ff7a78e0f239d6f4ac7ed0ef2b883e1b4cba8ec1c49208142952cdc530380024d6ca7bae28f7d82" +
            "a36e8b95baad64df079b368d17dce484e00e88ba008e41576da8e9aaa102d4287f07f0edd89a76df8eeb02e08498c01b6a9fd5014e" +
            "3b73fd49b0c76ba32180894fe1da728c858bad96db9191e7c8244bf0649f09577ebe4bd1d0a1640dc2b8ad6f64b2a2f8715777b669" +
            "703f3fcb8023dbe024fcb21ca0697044400dcdc288b335ccb254e8d98bb93eb4c71b84141467e35cb284d13c62099ba90367d49058" +
            "1dabdf33744898a54a81bf05451288ec4c1065df003efa51aab502acadba022ee6d48b91901140e00d5eb20b";
    public static String BASE_URL = "https://venus.ninjaos.com/";  //LIVE

    public static final String NETS_REGISTRATION = BASE_URL + "api/netsclickpay/tokenRegister";

    public static final String NETS_PURCHASE = BASE_URL + "api/netsclickpay/purchaseOrder";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nets_test);
        context=NetsTestActivity.this;
        nets=findViewById(R.id.nets);

        nets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(NofService.isInitialized()){
          //          checkAndProceedNetsPay();
                }else {
                    initializeNOF(REGISTER);
                }




               /* NofService.setSdkDebuggable(true); //for debugging
                try {
                    NofService.initialize(
                            context,
                            "https://uatnetspay.nets.com.sg",
                            "https://uat-api.nets.com.sg/uat",
                            APP_ID,
                            APP_SECRET,
                            getAppPublicKeySet(),
                            getCaResource(),
                            new StatusCallback<String, String>() {
                                @Override
                                public void success(String s) {

                                    Log.e("Initialization", "Initialization successful : \n" + s);
                                    //showDialogue("Initialization", "success :" + s);
                                }

                                @Override
                                public void failure(String s) {
                                    if (s.contains("20035")){
                                        VGuardService.getINSTANCE().clearVos();
                                       *//* finish();
                                        System.exit(1);*//*
                                        Log.e("Initialization", "Initialization_failed_inside: \n" + s);
                                    }
                                    Log.e("Initialization", "Initialization failed : \n" + s);
                                    //showDialogue("Initialization", "failed :" + s);
                                }
                            }
                    );
                } catch (ServiceAlreadyInitializedException e) {
                    e.printStackTrace();
                    Log.e("Initialization", "some error occured");

                    //showDialogue("Initialization", "Exception : " + e.getMessage());
                }*/
            }
        });

    }
    public void initializeNOF(String type) {
    //    showProgressDialogue();
        // NOF already initialized
        if(NofService.isInitialized()){
     //       hideProgressDialogue();
            if(type.equalsIgnoreCase(REGISTER) || type.equalsIgnoreCase(PURCHASE)) {
                checkAndProceedNetsPay();
            }
            else
        //        showAlertDialogDelete();
            Log.e("TAG", "NOF Already initialized....");
            return;
        }

        //initializing NOF if not initialized already
        NofService.setSdkDebuggable(false); //for debugging
        try {
            NofService.initialize(
                    context,
                    "https://uatnetspay.nets.com.sg",
                    "https://uat-api.nets.com.sg/uat",
                    APP_ID,
                    APP_SECRET,
                    getAppPublicKeySet(),
                    getCaResource(),
                    new StatusCallback<String, String>() {
                        @Override
                        public void success(String s) {
          //                  hideProgressDialogue();
                            Log.e("TAG", "Initialization successful : \n" + s+"\n"+type);
                            if(type.equalsIgnoreCase(REGISTER) || type.equalsIgnoreCase(PURCHASE)) {
                              checkAndProceedNetsPay();
                            }
                            else{
                                //                      showAlertDialogDelete();
                            }
                        }

                        @Override
                        public void failure(String s) {
         //                   hideProgressDialogue();
         //                   showRetry(type);
                            if (s.contains("20035")){



                            }
                            Log.e("TAG", "Initialization_failed_responce : \n" + s);
                        }
                    }
            );
        } catch (ServiceAlreadyInitializedException e) {
            e.printStackTrace();
            Log.e("TAG", "some error occured");
  //          hideProgressDialogue();
   //         showRetry(type);
            //showDialogue("Initialization", "Exception : " + e.getMessage());
        }
    }

    private void checkAndProceedNetsPay () {
        if(isRegistrationSuccess()) {
   //         showAlertDialogCard();
        } else {
            doRegistration();
        }
    }
    private void deRegisterNets () {
  //      showProgressDialogue();
        Deregistration deregistration = new Deregistration();
        try {
            deregistration.invoke(new StatusCallback() {
                @Override
                public void success(Object o) {
                    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    Log.e("time", timeStamp);
                    clearRegistration();
          //          updateUI();
                    //showDialogue("Deregistration success: ", "Result: " + o);
         //           hideProgressDialogue();
                }

                @Override
                public void failure(Object o) {
         //           hideProgressDialogue();
         //           showDialogue("Deregistration failed: ", "Result: " + o, false);
                }
            });
        } catch (ServiceNotInitializedException | CardNotRegisteredException e) {
            e.printStackTrace();
            Log.e("TAG", "failed.." + e.getMessage());
      //      hideProgressDialogue();
     //       showDialogue("DeRegistration failed", "Exception : " + e.getMessage(), false);
        }
    }

    public void doRegistration() {
        Registration registration = new Registration(MID, "15");
        try {
            registration.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {
                    Log.e("TAG", "Registration_successful : \n" + s);
                    validateRegistration(s);
                }

                @Override
                public void failure(String s) {
                    Log.e("TAG", "Registration_failed : \n" + s);
         //           showDialogue("Registration", "failed :" + s, false);
                }
            });
        }catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            Log.e("TAG", "service not initialized.." + e.getMessage());
   //         showDialogue("Registration", "Exception : " + e.getMessage(), false);
        }
    }

    private void validateRegistration (String token) {
        Map<String, String> params = new HashMap<>();
        //params.put("t0102", otp);
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
        params.put("order_amount", "00");
        params.put("gmt", token);
        params.put("muid", formatCustomerId(Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID)));
        //params.put("muuid", MUUID = (MUUID == null || MUUID.isEmpty()) ? generateMUUID() : MUUID);
        params.put("mid", MID);
        Log.e("TAG", "Register_params : " + params.toString());
        //new NETSCardRegistration(params).execute(GlobalUrl.NETS_REGISTRATION);
        new NETSCardRegistration(params).execute(NETS_REGISTRATION);
    }

    public String formatCustomerId (String customer_id) {
        //return "000000000000".substring(0,12-customer_id.length()) + customer_id;
        return customer_id;
    }

    private boolean isRegistrationSuccess () {

        if((Utility.readFromSharedPreference(context, GlobalValues.NETS_REGISTERED) == null || Utility.readFromSharedPreference(context, GlobalValues.NETS_REGISTERED).isEmpty() ||
                Utility.readFromSharedPreference(context, GlobalValues.NETS_REGISTERED).equalsIgnoreCase("0")))
            return false;

        if(!(Utility.readFromSharedPreference(context, GlobalValues.TRANS_REF_NO) == null && Utility.readFromSharedPreference(context, GlobalValues.TRANS_REF_NO).isEmpty()) &&
                !(Utility.readFromSharedPreference(context, GlobalValues.NETS_CARD_NUMBER) == null && Utility.readFromSharedPreference(context, GlobalValues.NETS_CARD_NUMBER).isEmpty()) &&
                !(Utility.readFromSharedPreference(context, GlobalValues.NETS_EXPIRY) == null && Utility.readFromSharedPreference(context, GlobalValues.NETS_EXPIRY).isEmpty()))
            return true;
        else
            return false;
    }

    private int getCaResource() {
        return NetsTestActivity.this.getResources().getIdentifier("netspay_nets_com_sg",
                "raw", NetsTestActivity.this.getPackageName());
    }
    public static PublicKeySet getAppPublicKeySet() {
        PublicKeyComponent mapPubKey = new PublicKeyComponent(
                MAP_PUB_KEY_ID,
                EXPONENT,
                MODULUS
        );
        PublicKeySet pks = new PublicKeySet(mapPubKey);

        PublicKeyComponent hppPubKeyDBS = new PublicKeyComponent(
                "1",
                "03",
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        PublicKeyComponent hppPubKeyUOB = new PublicKeyComponent(
                "2",
                "03",
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        PublicKeyComponent hppPubKeyOCBC = new PublicKeyComponent(
                "3",
                "03",
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        PublicKeyComponent hppPubKeyNOFOCBC = new PublicKeyComponent(
                "4",
                "03",
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        PublicKeyComponent hppPubKeySIM = new PublicKeyComponent(
                "9",
                "03",
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");

        pks.addHppPublicKey("0001", hppPubKeyDBS);
        pks.addHppPublicKey("0002", hppPubKeyUOB);
        pks.addHppPublicKey("0003", hppPubKeyOCBC);
        pks.addHppPublicKey("0004", hppPubKeyNOFOCBC);
        pks.addHppPublicKey("9999", hppPubKeySIM);
        return pks;
    }

    @SuppressLint("StaticFieldLeak")
    private class NETSCardRegistration extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private final Map<String, String> cardDetails;

        public NETSCardRegistration(Map<String, String> cardDetails) {
            this.cardDetails = cardDetails;
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
            Log.e("TAG", "NETS-Registration : " + params[0] + "\n" + cardDetails.toString());
            return WebserviceAssessor.postRequest(context, params[0], cardDetails);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!= null) {
                try {
                    Log.e("TAG", "Registeration Response :" + s);
                    JSONObject object = new JSONObject(s);
                    if(object.optString("status").equalsIgnoreCase("ok")) {
                        Utility.writeToSharedPreference(context,GlobalValues.TRANS_REF_NO, object.optString("trans_reference_no")); //trans_reference_no
                        JSONObject result_set = object.getJSONObject("result_set");
                        JSONArray txn_specific_data = result_set.optJSONArray("txn_specific_data");
                        if (txn_specific_data != null && txn_specific_data.length() > 0) {
                            JSONObject txn_data = txn_specific_data.getJSONObject(0);
                            if (txn_data.optString("response_code").equalsIgnoreCase("00")) {
                                Utility.writeToSharedPreference(context,GlobalValues.NETS_CARD_NUMBER, txn_data.optString("last_4_digits_fpan"));
                                Utility.writeToSharedPreference(context, GlobalValues.NETS_EXPIRY, txn_data.optString("mtoken_expiry_date"));
                                Utility.writeToSharedPreference(context, GlobalValues.CARD_TYPE, txn_data.optString("issuer_short_name")); //bank_fiid
                                Utility.writeToSharedPreference(context, GlobalValues.NETS_REGISTERED, "1");
            //                    updateUI();
                                //doPurchase();
                            } else if (txn_data.optString("response_code").equalsIgnoreCase("90")) {
                                clearRegistration();
           //                     showDialogue(REGISTER, object.optString("message"), true);
                            } else {
           //                     clearRegistration();
            //                    showDialogue(REGISTER, object.optString("message"), true);
                            }
                        }
                    } else {
           //             showDialogue(REGISTER, object.optString("message"), true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
        //            showDialogue(REGISTER, "Registration failed", true);
                }
            }
            progressDialog.dismiss();
        }
    }

    private void clearRegistration () {
        Utility.writeToSharedPreference(context,GlobalValues.NETS_CARD_NUMBER, "");
        Utility.writeToSharedPreference(context, GlobalValues.NETS_EXPIRY, "");
        Utility.writeToSharedPreference(context,GlobalValues.TRANS_REF_NO, ""); //trans_reference_no
        Utility.writeToSharedPreference(context,GlobalValues.NETS_REGISTERED, "0");
    }
}