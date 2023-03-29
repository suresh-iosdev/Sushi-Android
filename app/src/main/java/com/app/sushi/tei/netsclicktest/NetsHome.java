package com.app.sushi.tei.netsclicktest;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;
import com.nets.nofsdk.exception.CardNotRegisteredException;
import com.nets.nofsdk.exception.ServiceAlreadyInitializedException;
import com.nets.nofsdk.exception.ServiceNotInitializedException;
import com.nets.nofsdk.model.PublicKeyComponent;
import com.nets.nofsdk.model.PublicKeySet;
import com.nets.nofsdk.model.Table53Data;
import com.nets.nofsdk.request.Debit;
import com.nets.nofsdk.request.Deregistration;
import com.nets.nofsdk.request.NofService;
import com.nets.nofsdk.request.Registration;
import com.nets.nofsdk.request.StatusCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.net.ssl.SSLContext;

/**
 * Created on 21st October 2021.
 */
public class NetsHome extends AppCompatActivity {

    private static final String TAG = NetsHome.class.getSimpleName();

    String APP_ID = "00011000001";
    String MID = "11136198900";
    String APP_SECRET = "B4C110917EABBF699F08C52AF3A7BF71B3E754C2687C8313C9198C31E8B62CAF";
    private static final String MAP_PUB_KEY_ID = "2";
    private static final String EXPONENT = "03";
    private static final String MODULUS = "cfbb65135256d4e525fc6aada10ff7a78e0f239d6f4ac7ed0ef2b883e1b4cba8ec1c49208142952cdc530380024d6ca7bae28f7d82" +
            "a36e8b95baad64df079b368d17dce484e00e88ba008e41576da8e9aaa102d4287f07f0edd89a76df8eeb02e08498c01b6a9fd5014e" +
            "3b73fd49b0c76ba32180894fe1da728c858bad96db9191e7c8244bf0649f09577ebe4bd1d0a1640dc2b8ad6f64b2a2f8715777b669" +
            "703f3fcb8023dbe024fcb21ca0697044400dcdc288b335ccb254e8d98bb93eb4c71b84141467e35cb284d13c62099ba90367d49058" +
            "1dabdf33744898a54a81bf05451288ec4c1065df003efa51aab502acadba022ee6d48b91901140e00d5eb20b";


   /* private static final String APP_ID = "00011000001";
    private static final String APP_SECRET = "B4C110917EABBF699F08C52AF3A7BF71B3E754C2687C8313C9198C31E8B62CAF";
    private static final String MAP_PUB_KEY_ID = "2";
    private static final String MODULUS = "cfbb65135256d4e525fc6aada10ff7a78e0f239d6f4ac7ed0ef2b883e1b4cba8ec1c49208142952cdc530380024d6ca7bae28f7d82" +
            "a36e8b95baad64df079b368d17dce484e00e88ba008e41576da8e9aaa102d4287f07f0edd89a76df8eeb02e08498c01b6a9fd5014e" +
            "3b73fd49b0c76ba32180894fe1da728c858bad96db9191e7c8244bf0649f09577ebe4bd1d0a1640dc2b8ad6f64b2a2f8715777b669" +
            "703f3fcb8023dbe024fcb21ca0697044400dcdc288b335ccb254e8d98bb93eb4c71b84141467e35cb284d13c62099ba90367d49058" +
            "1dabdf33744898a54a81bf05451288ec4c1065df003efa51aab502acadba022ee6d48b91901140e00d5eb20b";
    private static final String EXPONENT = "03";*/
    private Context context;

    private ProgressDialog progress;

  //  private static final String MID = "11136196600";
    //private static final String TID = "36196601";
    private final String MUID = "15";
    //private final String MUUID = null;

    private final String amount = "10.00";

    //private static final String REGISTER_URL = "https://mtls.uat-api.nets.com.sg/uat/nof/v2/merchantservices/token/register?mid="+MID+"&tid="+TID;

    //private static final String REG_URL = "https://nets-mh.hopto.org/sit/nof/gmt";

    //private static final String REG_URL_NEW = "https://venus.ninjaos.com/netsclick/netsclickpaynotify";

    private TextView card_number, expiry_date;

    //s5762841c

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nets_home);
        context = NetsHome.this;

        TextView add_card = findViewById(R.id.addcard);
        TextView paynow = findViewById(R.id.paynow);
        TextView deletecard = findViewById(R.id.deletecard);

        card_number = findViewById(R.id.card_number);
        expiry_date = findViewById(R.id.exp_date);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
            SSLContext sslContext;
            sslContext = SSLContext.getInstance("TLSv1.2");
            sslContext.init(null, null, null);
            sslContext.createSSLEngine();
        } catch (GooglePlayServicesRepairableException | GooglePlayServicesNotAvailableException
                | NoSuchAlgorithmException | KeyManagementException e) {
            e.printStackTrace();
        }

        add_card.setOnClickListener(v -> doRegistration());

        paynow.setOnClickListener(v -> {
           if(isRegistrationSuccess())
                doDebit();
           else
               showDialogue("Message", "Please register card first!..");
        });

        deletecard.setOnClickListener(v -> {
            if (isRegistrationSuccess())
                deleteCard();
        });

        if(isRegistrationSuccess()) {
            updateUI();
        }

        if(NofService.isMainProcess(context)) {
            showProgressDialogue();
            initializeNOF();
        }
    }

    /**
     * Initializing the NOF SDK.
     */
    private void initializeNOF() {
        // NOF already initialized
        if(NofService.isInitialized()){
            hideProgressDialogue();

            showDialogue("Initialization", "already initialized");
            return;
        }

        //initializing NOF if not initialized already
        NofService.setSdkDebuggable(false); //for debugging

        try {
            NofService.initialize(
                    this.context,
                    "https://uatnetspay.nets.com.sg",
                    "https://uat-api.nets.com.sg/uat",
                    APP_ID,
                    APP_SECRET,
                    getAppPublicKeySet(),
                    getCaResource(),
                    new StatusCallback<String, String>() {
                        @Override
                        public void success(String s) {
                            hideProgressDialogue();
                            showDialogue("Initialization", "success :" + s);
                        }

                        @Override
                        public void failure(String s) {
                            hideProgressDialogue();
                            showDialogue("Initialization", "failed :" + s);
                        }
                    }
            );
        } catch (ServiceAlreadyInitializedException e) {
            e.printStackTrace();
            hideProgressDialogue();
            showDialogue("Initialization", "Exception : " + e.getMessage());
        }
    }

    /**
     * @return
     * Certificate
     */
    private int getCaResource() {
        return this.context.getResources().getIdentifier("netspay_nets_com_sg",
                "raw", this.context.getPackageName());
    }

    /**
     * @return
     * Public Key Sets.
     */
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

    /**
     * show a progress dialogue
     */
    private void showProgressDialogue() {
        progress = new ProgressDialog(context);
        progress.setMessage("initializing sdk...");
        progress.show();
    }

    /**
     * hide a progress dialogue
     */
    private void hideProgressDialogue() {
        if(progress != null && progress.isShowing())
            progress.dismiss();
    }

    /**
     * Register a new card for making payment.
     */
    private void doRegistration() {
        Registration registration = new Registration(MID, "15");
        try {
             registration.invoke(new StatusCallback<String, String>() {
                 @Override
                 public void success(String s) {
                     //showDialogue("Registration", "success :" + s);
                     sendRegisterOTP(s);

                 }

                 @Override
                 public void failure(String s) {
                     showDialogue("Registration", "failed :" + s);
                 }
             });
        }catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            showDialogue("Registration", "Exception : " + e.getMessage());
        }
    }

    /**
     * Deletes a registered card
     */
    private void deleteCard() {
        Deregistration deregistration = new Deregistration();
        try {
            deregistration.invoke(new StatusCallback() {
                @Override
                public void success(Object o) {
                    clearRegistration();
                    updateUI();
                    showDialogue("Deregistration success: ", "Result: " + o);
                }

                @Override
                public void failure(Object o) {
                    showDialogue("Deregistration failed: ", "Result: " + o);
                }
            });
        } catch (ServiceNotInitializedException | CardNotRegisteredException e) {
            e.printStackTrace();
            showDialogue("DeRegistration failed", "Exception : " + e.getMessage());
        }
    }

    /**
     *
     * @param amountStr amounts in dollars
     * @return amount in cents
     */
    private String formatAmountInCents(String amountStr){
        String amtInCents = "";
        DecimalFormat df2 = new DecimalFormat("0.00");
        amountStr = df2.format(Double.valueOf(amountStr));
        System.out.println("Format>>"+ amountStr);
        amountStr = amountStr.replaceAll("\\.","");
        amtInCents = "000000000000".substring(0,12-amountStr.length()) + amountStr;
        //amtInCents = amountStr.replaceAll("\\.","");
        return amtInCents;
    }

    private void doDebitWithPin (String t5253, String responseCode) {
        Table53Data table53Data = new Table53Data(t5253);   //nets sdk
        Table53 table53 = new Table53(t5253); //custom
        table53.parse();
        try {
            table53Data.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }


        Debit debit = new Debit(formatAmountInCents(amount), responseCode, table53.getData());
        try {
            debit.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {

                    sendPurchaseRequest(s, true);
                }

                @Override
                public void failure(String s) {
                    showDialogue("Debit failed", s);
                }
            });
        } catch (ServiceNotInitializedException e) {
            e.printStackTrace();
        }

    }

    /**
     * Debit amount from the added nets card.
     */
    private void doDebit() {
        Debit debit = new Debit(formatAmountInCents(amount));
        try{
            debit.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {

                    sendPurchaseRequest(s, false);
                }

                @Override
                public void failure(String s) {
                    showDialogue("Debit failed", s);
                }
            });
        }catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            showDialogue("Debit failed", "Exception" + e.getMessage());
        }
    }

    /**
     * @param token
     */
    private void sendPurchaseRequest (String token, boolean is_pin_pad_request) {
        Map<String, String> params = new HashMap<>();
        params.put("muid", MUID);
        params.put("t0205", token);
        params.put("amt", formatAmountInCents(amount));
        params.put("trans_reference_no", Utility.readFromSharedPreference(context,"trans_ref_no"));
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", "3175");  //98667827
        if (is_pin_pad_request) {
            params.put("action_to_text", "pinpad");
        }
        new NETSPurchase(params).execute(GlobalUrl.NETS_PURCHASE);
    }

    /**
     * @param title
     * @param message
     * simple dialogue to show messages.
     */
    private void showDialogue(String title, String message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(context)
                        .setTitle(title)
                        .setMessage(message)
                        .setPositiveButton("ok", (dialog, which) -> dialog.dismiss());
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });
    }

    /**
     * @param otp
     * sending authentication code came from Registration and send to Merchant host
     */
    private void sendRegisterOTP(String otp) {
        Map<String, String> params = new HashMap<>();
        //params.put("t0102", otp);
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", "3175");  //98667827
        params.put("order_amount", amount);
        params.put("gmt", otp);
        params.put("muid", MUID);
        //params.put("muuid", MUUID = (MUUID == null || MUUID.isEmpty()) ? generateMUUID() : MUUID);
        params.put("mid", MID);
        //new NETSCardRegistration(params).execute(GlobalUrl.NETS_REGISTRATION);
        new NETSCardRegistration(params).execute(GlobalUrl.NETS_REGISTRATION);
    }

    /**
     * @return a Unique ID
     */
    public static String generateMUUID(){
        String uuid = UUID.randomUUID().toString();
        uuid = uuid.replaceAll("-","");
        uuid = uuid.toUpperCase();
        return uuid;
    }

    /**
     * Get Generated Merchant Token by sending Registration Authentication code
     */
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

            /*BaseTask task = new BaseTask(context, cardDetails.toString(), MID);
            task.setUrl(params[0]);
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return WebserviceAssessor.postRequest(context, params[0], cardDetails);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!= null) {
                try {
                    JSONObject object = new JSONObject(s);
                    if(object.optString("status").equalsIgnoreCase("ok")) {
                        Utility.writeToSharedPreference(context,"trans_ref_no", object.optString("trans_reference_no")); //trans_reference_no
                        JSONObject result_set = object.getJSONObject("result_set");
                        JSONArray txn_specific_data = result_set.optJSONArray("txn_specific_data");
                        if (txn_specific_data != null && txn_specific_data.length() > 0) {
                            JSONObject txn_data = txn_specific_data.getJSONObject(0);
                            if (txn_data.optString("response_code").equalsIgnoreCase("00")) {
                                Utility.writeToSharedPreference(context,"NetsCardNumber", txn_data.optString("last_4_digits_fpan"));
                                Utility.writeToSharedPreference(context, "NetsExpiry", txn_data.optString("mtoken_expiry_date"));
                                updateUI();
                            } else if (txn_data.optString("response_code").equalsIgnoreCase("90")) {
                                clearRegistration();
                                showDialogue("Register Failed", "registeration failed.....");
                            } else {
                                clearRegistration();
                                showDialogue("Register Failed", "registeration failed.....");
                            }
                        }
                    } else {
                        showDialogue("Register Failed", object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showDialogue("Register Failed", "exception : " + e.getMessage());
                }
            }
            progressDialog.dismiss();
        }
    }

    /**
     * clears locally saved registration details
     */
    private void clearRegistration () {
        Utility.writeToSharedPreference(context,"NetsCardNumber", "");
        Utility.writeToSharedPreference(context, "NetsExpiry", "");
        Utility.writeToSharedPreference(context,"trans_ref_no", ""); //trans_reference_no
    }

    /**
     * Updates UI
     */
    private void updateUI () {
        String card = "card number : ";
        String card_num = "<font color='#fd9a0f'>" + Utility.readFromSharedPreference(context,"NetsCardNumber") + "</font>";
        card_number.setText(Html.fromHtml(card + card_num));

        String exp = "Exp Date : ";
        String exp_date = "<font color='#fd9a0f'>" + Utility.readFromSharedPreference(context,"NetsExpiry") + "</font>";
        expiry_date.setText(Html.fromHtml(exp + exp_date));
    }

    /**
     * checks if registration already done or not
     * @return
     */
    private boolean isRegistrationSuccess () {
        if(!(Utility.readFromSharedPreference(context,"NetsCardNumber") == null && Utility.readFromSharedPreference(context, "NetsCardNumber").isEmpty()) &&
                !(Utility.readFromSharedPreference(context, "NetsExpiry") == null && Utility.readFromSharedPreference(context, "NetsExpiry").isEmpty()))
                 return true;
        else
            return false;
    }

    /**
     * sends request to the merchant host with debit token.
     */
    @SuppressLint("StaticFieldLeak")
    private class NETSPurchase extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private final Map<String, String> prichaseDetails;

        public NETSPurchase(Map<String, String> prichaseDetails) {
            this.prichaseDetails = prichaseDetails;
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

            /*BaseTask task = new BaseTask(context, prichaseDetails.toString(), MID);
            task.setUrl(params[0]);
            try {

            } catch (Exception e) {
                e.printStackTrace();
            }*/
            return WebserviceAssessor.postRequest(context, params[0], prichaseDetails);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!= null) {
                try {
                    JSONObject object = new JSONObject(s);
                    if(object.optString("status").equalsIgnoreCase("ok")){
                        String responseCode = object.getString("response_code");
                       if(responseCode.equalsIgnoreCase("00")){ //transaction success
                           showDialogue("success", "transaction success");
                       }else if(responseCode.equalsIgnoreCase("U9") || responseCode.equalsIgnoreCase("55")) { //invoke pin pad
                           JSONArray txt_data = object.getJSONObject("result_set").getJSONArray("txn_specific_data");
                           if(txt_data.length() > 0){
                               JSONObject t5253_Obj = txt_data.getJSONObject(1);
                               String t5253 = t5253_Obj.getString("sub_id") + t5253_Obj.getString("sub_length") + t5253_Obj.getString("sub_data");
                               doDebitWithPin(t5253, responseCode);
                           }
                       }
                    }else {
                        showDialogue("failed", object.optString("message"));
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            progressDialog.dismiss();
        }
    }
}
