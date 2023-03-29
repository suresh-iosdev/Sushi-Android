package com.app.sushi.tei.netsclicktest;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;


import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.nets.nofsdk.exception.ServiceAlreadyInitializedException;
import com.nets.nofsdk.exception.ServiceNotInitializedException;
import com.nets.nofsdk.model.PublicKeyComponent;
import com.nets.nofsdk.model.PublicKeySet;
import com.nets.nofsdk.model.Table53Data;
import com.nets.nofsdk.request.Debit;
import com.nets.nofsdk.request.NofService;
import com.nets.nofsdk.request.Registration;
import com.nets.nofsdk.request.StatusCallback;

import java.text.DecimalFormat;

public class NETSServices {

    private static final String TAG = NETSServices.class.getSimpleName();
    public static NETSServices instance;
    private Context context;
    private ProgressDialog progress;

    private static final String APP_ID = "00015000109"; //use this "00011000001" for UAT
    private static final String APP_SECRET = "BCC600A6EC113EEB2438BD701D3B48E2BA6E7BD59989D41448F6622C456AAAD6"; //use this "B4C110917EABBF699F08C52AF3A7BF71B3E754C2687C8313C9198C31E8B62CAF" for UAT
  //  public static final String MID = "11152913400"; //use this "11136196600" for UAT
    public static final String MID = "11136209500";
    private static final String MAP_PUB_KEY_ID = "2";
    private static final String EXPONENT = "03";
    /*private static final String MODULUS = "cfbb65135256d4e525fc6aada10ff7a78e0f239d6f4ac7ed0ef2b883e1b4cba8ec1c49208142952cdc530380024d6ca7bae28f7d82" +
            "a36e8b95baad64df079b368d17dce484e00e88ba008e41576da8e9aaa102d4287f07f0edd89a76df8eeb02e08498c01b6a9fd5014e" +
            "3b73fd49b0c76ba32180894fe1da728c858bad96db9191e7c8244bf0649f09577ebe4bd1d0a1640dc2b8ad6f64b2a2f8715777b669" +
            "703f3fcb8023dbe024fcb21ca0697044400dcdc288b335ccb254e8d98bb93eb4c71b84141467e35cb284d13c62099ba90367d49058" +
            "1dabdf33744898a54a81bf05451288ec4c1065df003efa51aab502acadba022ee6d48b91901140e00d5eb20b";*/ //for testing

    private static final String MODULUS = "a8d687ac064ee2ce75a51a6351e1b4906366173bd53bba3dea9b8ee9cb57d8028b496cc860e3205f983afdac338b1f4a76a89ba4e832811cb6b8a581b961af256ba53f64cac3153a78922d5142667d5ca31766deb965f2f178e3e5622377d9fa24602e3e3d50bd82c40a9491065260c0f7bcef8a24425ae07acbde658caecb5d56ed2cd67f07bece6bdcefa733b8ab815c4c0d249f9d0784cd2a2a839ba650ae5359bc83b9c38539681603833ef05eeb6ca9b138b9abf3da592f443e6d90467d4a261864cc5de87d54cafcb4ed227e9c649674dd7bbdfd96c7ea25210216f0e7b484b00d39e019a62c8552b2f7b48f1b41be91e5f9262d6c4f75db39f5ea3f73";

    private NetsResultListener netsResultListener;

    public NETSServices(Context context, NetsResultListener netsResultListener) {
        this.context = context;
        this.netsResultListener = netsResultListener;
        initializeNOF(context);
    }

    public interface NetsResultListener {
        void isSuccess (String type, String message);
        void isFailed (String type, String message);
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public static NETSServices getInstance (Context context, NetsResultListener netsResultListener) {
        if(instance == null) {
            instance = new NETSServices(context, netsResultListener);
        }
        return instance;
    }

    /**
     * show a progress dialogue
     */
    private void showProgressDialogue() {
        progress = new ProgressDialog(context);
        progress.setMessage("Loading...");
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

        //for UAT
        /*PublicKeyComponent hppPubKeyDBS = new PublicKeyComponent(
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
                "00000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");*/

        //for Live
        PublicKeyComponent hppPubKeyDBS = new PublicKeyComponent(
                "4",
                "03",
                "CA83F1C917C072B70757A6D88ED94C8E94AF7F142A2F8A52AC491243BF33F30C3FF18FEBED5D49AB5315057EBA3D3045D1B297F6893F2596CC08E6C5664F324993D553E690C48C9E7342673B3D94F167396E969017B0C80C980C3E8F8F32F28240ADBAA5AD4CA3619110575164738F50504C0CDA43A08B15F7D47A99F91DE2AE6773296C4794B56A989A1BF646FA5F2B3F03FAF20035CD0B0BF701FE977297B946100952BD07D7E73B6AC26B3964CB65831893690C8002B8568FAE0AEDF4DED213FEBCA081F4AEF5BE57B45C1C7EC98CF84D0F1CBCCEF707FDE8CC29821637F6E37653EC989D96AC18253DDC2FB2987616151EE6084653CAB85417A37E06FE1B");

        PublicKeyComponent hppPubKeyUOB = new PublicKeyComponent(
                "4",
                "03",
                "CA83F1C917C072B70757A6D88ED94C8E94AF7F142A2F8A52AC491243BF33F30C3FF18FEBED5D49AB5315057EBA3D3045D1B297F6893F2596CC08E6C5664F324993D553E690C48C9E7342673B3D94F167396E969017B0C80C980C3E8F8F32F28240ADBAA5AD4CA3619110575164738F50504C0CDA43A08B15F7D47A99F91DE2AE6773296C4794B56A989A1BF646FA5F2B3F03FAF20035CD0B0BF701FE977297B946100952BD07D7E73B6AC26B3964CB65831893690C8002B8568FAE0AEDF4DED213FEBCA081F4AEF5BE57B45C1C7EC98CF84D0F1CBCCEF707FDE8CC29821637F6E37653EC989D96AC18253DDC2FB2987616151EE6084653CAB85417A37E06FE1B");

        PublicKeyComponent hppPubKeyOCBC = new PublicKeyComponent(
                "4",
                "03",
                "CA83F1C917C072B70757A6D88ED94C8E94AF7F142A2F8A52AC491243BF33F30C3FF18FEBED5D49AB5315057EBA3D3045D1B297F6893F2596CC08E6C5664F324993D553E690C48C9E7342673B3D94F167396E969017B0C80C980C3E8F8F32F28240ADBAA5AD4CA3619110575164738F50504C0CDA43A08B15F7D47A99F91DE2AE6773296C4794B56A989A1BF646FA5F2B3F03FAF20035CD0B0BF701FE977297B946100952BD07D7E73B6AC26B3964CB65831893690C8002B8568FAE0AEDF4DED213FEBCA081F4AEF5BE57B45C1C7EC98CF84D0F1CBCCEF707FDE8CC29821637F6E37653EC989D96AC18253DDC2FB2987616151EE6084653CAB85417A37E06FE1B");

        PublicKeyComponent hppPubKeyNOFOCBC = new PublicKeyComponent(
                "4",
                "03",
                "CA83F1C917C072B70757A6D88ED94C8E94AF7F142A2F8A52AC491243BF33F30C3FF18FEBED5D49AB5315057EBA3D3045D1B297F6893F2596CC08E6C5664F324993D553E690C48C9E7342673B3D94F167396E969017B0C80C980C3E8F8F32F28240ADBAA5AD4CA3619110575164738F50504C0CDA43A08B15F7D47A99F91DE2AE6773296C4794B56A989A1BF646FA5F2B3F03FAF20035CD0B0BF701FE977297B946100952BD07D7E73B6AC26B3964CB65831893690C8002B8568FAE0AEDF4DED213FEBCA081F4AEF5BE57B45C1C7EC98CF84D0F1CBCCEF707FDE8CC29821637F6E37653EC989D96AC18253DDC2FB2987616151EE6084653CAB85417A37E06FE1B");

        PublicKeyComponent hppPubKeySIM = new PublicKeyComponent(
                "4",
                "03",
                "CA83F1C917C072B70757A6D88ED94C8E94AF7F142A2F8A52AC491243BF33F30C3FF18FEBED5D49AB5315057EBA3D3045D1B297F6893F2596CC08E6C5664F324993D553E690C48C9E7342673B3D94F167396E969017B0C80C980C3E8F8F32F28240ADBAA5AD4CA3619110575164738F50504C0CDA43A08B15F7D47A99F91DE2AE6773296C4794B56A989A1BF646FA5F2B3F03FAF20035CD0B0BF701FE977297B946100952BD07D7E73B6AC26B3964CB65831893690C8002B8568FAE0AEDF4DED213FEBCA081F4AEF5BE57B45C1C7EC98CF84D0F1CBCCEF707FDE8CC29821637F6E37653EC989D96AC18253DDC2FB2987616151EE6084653CAB85417A37E06FE1B");


        pks.addHppPublicKey("0001", hppPubKeyDBS);
        pks.addHppPublicKey("0002", hppPubKeyUOB);
        pks.addHppPublicKey("0003", hppPubKeyOCBC);
        pks.addHppPublicKey("0004", hppPubKeyNOFOCBC);
        pks.addHppPublicKey("9999", hppPubKeySIM);
        return pks;
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
     * Initializing the NOF SDK.
     */
    public void initializeNOF(Context context) {
        this.context = context;
        showProgressDialogue();
        // NOF already initialized
        if(NofService.isInitialized()){
            hideProgressDialogue();

            netsResultListener.isSuccess(GlobalValues.INITIALIZE, "already initialized");
            //showDialogue("Initialization", "already initialized");
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
                            hideProgressDialogue();

                            netsResultListener.isSuccess(GlobalValues.INITIALIZE, s);
                            //showDialogue("Initialization", "success :" + s);
                        }

                        @Override
                        public void failure(String s) {
                            hideProgressDialogue();
                            netsResultListener.isFailed(GlobalValues.INITIALIZE, s);
                            //showDialogue("Initialization", "failed :" + s);
                        }
                    }
            );
        } catch (ServiceAlreadyInitializedException e) {
            e.printStackTrace();

            hideProgressDialogue();
            //showDialogue("Initialization", "Exception : " + e.getMessage());
        }
    }

    /**
     * Register a new card for making payment.
     */
    public void doRegistration(String MUID) {
        //showProgressDialogue();
        Registration registration = new Registration(MID, MUID);
        try {
            registration.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {
                    //hideProgressDialogue();
                    //showDialogue("Registration", "success :" + s);
                    //sendRegisterOTP(s);
                    netsResultListener.isSuccess(GlobalValues.REGISTER, s);
                }

                @Override
                public void failure(String s) {
                    //hideProgressDialogue();
                    //showDialogue("Registration", "failed :" + s);
                    netsResultListener.isFailed(GlobalValues.REGISTER, s);
                }
            });
        }catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            //hideProgressDialogue();
            netsResultListener.isFailed(GlobalValues.REGISTER, "Exception" + e.getMessage());
            //showDialogue("Registration", "Exception : " + e.getMessage());
        }
    }

    /**
     *
     * @param amountStr amounts in dollars
     * @return amount in cents
     */
    public String formatAmountInCents(String amountStr){
        String amtInCents = "";
        DecimalFormat df2 = new DecimalFormat("0.00");
        amountStr = df2.format(Double.valueOf(amountStr));
        System.out.println("Format>>"+ amountStr);
        amountStr = amountStr.replaceAll("\\.","");
        amtInCents = "000000000000".substring(0,12-amountStr.length()) + amountStr;
        //amtInCents = amountStr.replaceAll("\\.","");
        return amtInCents;
    }

    public void doDebit(String amount, Context context) {
        this.context = context;
        showProgressDialogue();
        Debit debit = new Debit(formatAmountInCents(amount));
        try{
            debit.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {
                    //sendPurchaseRequest(s, false);
                    hideProgressDialogue();
                    netsResultListener.isSuccess(GlobalValues.PURCHASE, s);
                }

                @Override
                public void failure(String s) {
                    //showDialogue("Debit failed", s);
                    hideProgressDialogue();
                    netsResultListener.isFailed(GlobalValues.PURCHASE, s);
                }
            });
        }catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            hideProgressDialogue();

            netsResultListener.isFailed(GlobalValues.PURCHASE, "Exception" + e.getMessage());
            //showDialogue("Debit failed", "Exception" + e.getMessage());
        }
    }

    public void doDebitWithPin (String t5253, String responseCode, String amount, Context context) {
        this.context = context;
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
                    //sendPurchaseRequest(s, true);
                    netsResultListener.isSuccess(GlobalValues.PURCHASE_WITH_PIN, s);
                }

                @Override
                public void failure(String s) {
                    //showDialogue("Debit failed", s);
                    netsResultListener.isFailed(GlobalValues.PURCHASE_WITH_PIN, s);
                }
            });
        } catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            netsResultListener.isFailed(GlobalValues.PURCHASE_WITH_PIN, "Exception" + e.getMessage());
        }
    }

    public static String getAppId() {
        return APP_ID;
    }

    public static String getAppSecret() {
        return APP_SECRET;
    }
}
