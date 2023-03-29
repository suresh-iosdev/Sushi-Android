package com.app.sushi.tei.fragment.FiveMenu;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Account.AccountDetail;
import com.app.sushi.tei.Model.Account.SecondaryAddress;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.ChangePasswordActivity;
import com.app.sushi.tei.activity.FiveMenuActivityNew;
import com.app.sushi.tei.activity.MemberBenefitActivity;
import com.app.sushi.tei.activity.MemberLogRegActivity;
import com.app.sushi.tei.activity.NETSServices;
import com.app.sushi.tei.activity.PaymentActivity;
import com.app.sushi.tei.adapter.EwalletAdapter;
import com.app.sushi.tei.adapter.MyAccount.OtherAddressRecyclerAdapter;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.ClearCartMessageDialog;
import com.app.sushi.tei.netsclicktest.Table53;
import com.app.sushi.tei.shadow.ShadowLinearLayout;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.gson.Gson;
import com.nets.nofsdk.exception.CardNotRegisteredException;
import com.nets.nofsdk.exception.ServiceAlreadyInitializedException;
import com.nets.nofsdk.exception.ServiceNotInitializedException;
import com.nets.nofsdk.model.Table53Data;
import com.nets.nofsdk.request.Debit;
import com.nets.nofsdk.request.Deregistration;
import com.nets.nofsdk.request.NofService;
import com.nets.nofsdk.request.Registration;
import com.nets.nofsdk.request.StatusCallback;
import com.squareup.picasso.Picasso;
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import co.omise.android.Client;
import co.omise.android.TokenRequest;
import co.omise.android.TokenRequestListener;
import co.omise.android.models.Token;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.CURRENT_AVAILABLITY_ID;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.DE_REGISTER;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.PURCHASE;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.REGISTER;
import static com.app.sushi.tei.fragment.FiveMenu.FragmentReward.edt_enterAmount;

public class MyAccountFragmentNew extends Fragment {
    private View mView;

    private Activity mContext;
    ProgressDialog progress;
    Dialog dialogBottom;
    private TextView noOfUse_textview, earned_points_textview, redeemed_textview, createdOnTextView,
            button_update;

    private EditText firstname_editview, lastname_editview,
            mobilenumber_editview, emailaddress_editview;
    private TextView birthday_editview, gender_editview,txt_changepass;
    public static final int RESULT_OK = -1;
    int CAMERA_PIC_REQUEST = 0;

    private Date dobDate, customerCreatedDate;

    private String CommonImageurl = "";

    private boolean isFbLogin;
    private String from = "";

    public String[] genders = {"Male", "Female", "Unspecified"};

    public static String BASE_URL = "https://ceres.ninjaos.com/";  //LIVE
    public static final String NETS_REGISTRATION = BASE_URL + "api/netsclickpay/tokenRegister";
    //   public static final String NETS_PURCHASE = BASE_URL + "api/netsclickpay/purchaseOrder";
    public static final String NETS_PURCHASE = BASE_URL + "api/netsclickpay/ascentisPurchaseOrder";
    String renewal_amt="40";
    private LinearLayout nets_pay_lyt,
            nets_pay_lyt_register, corporate_wallet_lyt, nets_pay_lyt_deregister, card_details;
    private ImageView credit_debit_check_box, shopee_pay_check_box, nets_pay_check_box, corporate_wallet_check_box;
    private TextView card_number, de_register, exp_date,back_to_login,back_to_login_1;
    public static final String NETS_PAYMENT = "NETS_PAY";
    BottomSheetDialog paymentpopup;
    private String trans_reference_number;

    private TextView divingTextView, soccerTextView, basketBallTextView, runningTextView, fitnessTextView,
            cyclingTextView, dancingTextView, motorCyclingTextView, photographyTextView,
            martialArtsTextView, dogsAndPetsTextView, rollerBladeTextView, hobbyInfoLabelText;
    private String mCustomerId = "", mReferenceId = "";
    private RecyclerView recyclerviewOtherAddress;
    private RecyclerView.LayoutManager layoutManager;
    private OtherAddressRecyclerAdapter otherAddressRecyclerAdapter;
    private String userImgString = "";

    private TextView mess_redwards;
    private ArrayList<SecondaryAddress> secondaryAddressList;

    private RelativeLayout layoutMenmbershipId;
    private EditText dfault_mobile_code;
    private TextView txt_addMore;
    private View view_addMore;
    private EditText edt_address, edt_postal, edt_unitNo1, edt_unitNo2;
    private TextView txt_Continue;
    private TextView txt_topup;
    String zip_id, zip_code, zip_addtype, zip_buno, zip_sname, zip_buname;
    private ConstraintLayout cly_savedAddress;
    private ShadowLinearLayout shadow_layout;
    private LinearLayout layoutProgress;
    private ProgressBar progressBar;
    private ImageView img_gold_member;
    private TextView txt_gold_member_label, txt_become_gold_member;
    private LinearLayout lly_label;
    private TextView txt_completePfrl,upgrade_acc,renew_acc;
    private TextView birthday_text;
    private TextView txt_reward,txt_myAccount;
    private EwalletAdapter ewalletAdapter;
    private TextView txt_walletBalance;
    private String walletBalance;
    private TextView referral_code, generate_referral_code;
    private LinearLayout ref_code_lyt;
    private String customer_referral_code = "";
    private EditText edtCardName_new;
    private EditText edtCardNumber_new;
    private TextView txt_expDate_new;
    private EditText edtCVVNumber_new;
    private Button btn_continue;
    private Button btn_continue_disabled;
    Calendar today;
    private String mUnitNo1 = "", mUnitNo2 = "", mCardNumber = "", mCardName = "",
            mYear = "", mMonth = "", mCVV = "";
    private long mLastClickTime = 0;
    private String cardName = "", cardNumber = "", cvvNumber = "", mMessage = "";
    private boolean onlineValidation = false;
    Client client;
    WebAuthotizeTask webAuthotizeTask;
    private String mPrevCaptureId;


    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_my_account_new, container, false);
        intiView(mView);
        FragmentReward.pos = 10;

        txt_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MemberBenefitActivity.class);
                startActivity(intent);
            }
        });
        button_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstName = firstname_editview.getText().toString();
                String lastName = lastname_editview.getText().toString();
                String mobileNumber = mobilenumber_editview.getText().toString();
                //String nickName = preferredname_editview.getText().toString();
                String email = emailaddress_editview.getText().toString();
                String gender = gender_editview.getText().toString();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

                String birthday = "";
                if (dobDate != null) {
                    birthday = sdf.format(dobDate);        // birthDayEditText.getText().toString();   //change this
                } else {
                    birthday = "";
                }

                // String companyName = companyNameEditText.getText().toString();
                /* String addressLineOne = addressLineOneEditText.getText().toString();
                String postalCode = postalCodeEditText.getText().toString();
                String unitNumberOne = unitNumEditText.getText().toString();
                String unitNumberTwo = unitNumEditTextTwo.getText().toString();*/


                if (firstName.equals("")) {

                    Toast.makeText(mContext, "Please enter name", Toast.LENGTH_SHORT).show();

                } else if (mobileNumber.equals("")) {

                    Toast.makeText(mContext, "Please enter mobile number", Toast.LENGTH_SHORT).show();

                } else if (mobileNumber.length() < 8) {

                    Toast.makeText(mContext, "Please enter a 8-digit Mobile Number", Toast.LENGTH_LONG).show();

                } else if (email.equals("")) {

                    Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();

                } else {

                    //run update task
                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("app_id", GlobalValues.APP_ID);
                    mapData.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                    mapData.put("customer_first_name", firstName);
                    mapData.put("customer_last_name", lastName);
                    mapData.put("customer_email", email);
                    mapData.put("customer_phone", mobileNumber);
                    mapData.put("customer_birthdate", birthday);
                    mapData.put("customer_nick_name", "");

                    mapData.put("customer_address_line1", "");
                    mapData.put("customer_postal_code", "");
                    mapData.put("customer_address_name", "");
                    mapData.put("customer_address_name2", "");
                    mapData.put("customer_photo", "");

                    mapData.put("customer_hobby", "");

                    if (gender.equals(genders[0])) {
                        mapData.put("customer_gender", "M");

                    } else if (gender.equals(genders[1])) {
                        mapData.put("customer_gender", "F");

                    } else if (gender.equals(genders[2])) {
                        mapData.put("customer_gender", "O");

                    }

                    mapData.put("customer_address_line1", "");
                    mapData.put("customer_postal_code", "");
                    mapData.put("customer_company_name", "");

                    mapData.put("customer_address_name", "");
                    mapData.put("customer_address_name2", "");
                    mapData.put("type", "App");




                    try {
                        //run(formBody);
                        new UpdateTask(mapData).execute();
                        hideKeyboard();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        txt_changepass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext, ChangePasswordActivity.class);
                startActivity(intent);
            }
        });

        txt_addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Dialog dialog = new Dialog(mContext);
                dialog.setContentView(R.layout.dialog_add_address);
                dialog.setCanceledOnTouchOutside(true);
                if (!dialog.isShowing())
                    dialog.show();
                WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                Window window = dialog.getWindow();

                if (window != null) {
                    lp.copyFrom(window.getAttributes());

                    lp.gravity = Gravity.CENTER;
                    lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
                    lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
                    window.setAttributes(lp);
                }

                ImageView img_close = dialog.findViewById(R.id.img_close);
                txt_Continue = dialog.findViewById(R.id.txt_Continue);

                edt_address = dialog.findViewById(R.id.edt_address);
                edt_postal = dialog.findViewById(R.id.edt_postal);
                edt_unitNo1 = dialog.findViewById(R.id.edt_unitNo1);
                edt_unitNo2 = dialog.findViewById(R.id.edt_unitNo2);
                img_close = dialog.findViewById(R.id.img_close);

                edt_postal.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                        String data = edt_postal.getText().toString();
                        int length = data.length();

                        if (length == 6) {

                            new GetAddressTask(edt_postal, null).execute(data);

                        } else {

                            edt_address.setText("");

                        }


                    }
                });


                txt_Continue.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (edt_postal.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(mContext, "Please enter postal code", Toast.LENGTH_SHORT).show();
                        } else if (edt_address.getText().toString().equalsIgnoreCase("")) {
                            Toast.makeText(mContext, "Please enter address", Toast.LENGTH_SHORT).show();
                        } else {

                            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");
                            dateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
                            Date today = Calendar.getInstance().getTime();
                            dateFormat.format(today);

                            Map<String, String> addAddressParams = new HashMap<>();
                            addAddressParams.put("app_id", GlobalValues.APP_ID);
                            addAddressParams.put("refrence", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                            addAddressParams.put("customer_address_line1", edt_address.getText().toString());
                            addAddressParams.put("customer_postal_code", zip_code);
                            addAddressParams.put("customer_address_name", edt_unitNo1.getText().toString());
                            addAddressParams.put("customer_address_name2", edt_unitNo2.getText().toString());
                            addAddressParams.put("customer_status", "A");
                            addAddressParams.put("customer_order_status", "order");
                            addAddressParams.put("created_on", dateFormat.format(today));


                            new AddAddressTask(addAddressParams).execute(GlobalUrl.ADDADDRESS_URL);

                            dialog.dismiss();
                        }
                    }
                });

                img_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

            }
        });

        view_addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_addMore.performClick();
            }
        });

        final ArrayAdapter<String> spinner_genders = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, genders);

        gender_editview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Utility.networkCheck(mContext)){
                    if (GlobalValues.ACCOUNT_DETAIL.getCustomer_gender() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_gender().equals("null")
                            && !GlobalValues.ACCOUNT_DETAIL.getCustomer_gender().isEmpty()) {
                        return;
                    }else{
                        new AlertDialog.Builder(mContext)
                                .setTitle("Gender")
                                .setAdapter(spinner_genders, new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        gender_editview.setText(genders[which].toString());

                                        if (birthday_editview.getText().toString().trim().equalsIgnoreCase("")
                                                || gender_editview.getText().toString().trim().equalsIgnoreCase("")) {
                                     //       txt_completePfrl.setVisibility(View.VISIBLE);
                                            txt_completePfrl.setVisibility(View.GONE);
                                        } else {
                                            txt_completePfrl.setVisibility(View.GONE);
                                        }
                                    }
                                }).create().show();
                    }
                } else {
                   Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        birthday_editview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(Utility.networkCheck(mContext)){
                    hideKeyboard();
                    if (GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate().equals("0000-00-00")) {
                        return;
                    }else{
                        if (dobDate == null) {

                            Calendar calendar = Calendar.getInstance();


//                    new DatePickerDialog(MyProfileActivity.this, new DatePickerDialog.OnDateSetListener() {
//                        @Override
//                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
//
//

//
//
//                        }
//                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
//                            .show();


                            final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.set(Calendar.YEAR, year);
                                    cal.set(Calendar.MONTH, month);
                                    cal.set(Calendar.DAY_OF_MONTH, day);

                                    dobDate = cal.getTime();

                                    SimpleDateFormat simpleDisplayDateFormat = new SimpleDateFormat("dd/MM/yyyy");


                                    birthday_editview.setText(simpleDisplayDateFormat.format(dobDate));
                                    //birthday_editview.setEnabled(false);
                                    if (birthday_editview.getText().toString().trim().equalsIgnoreCase("")
                                            || gender_editview.getText().toString().trim().equalsIgnoreCase("")) {
                               //         txt_completePfrl.setVisibility(View.VISIBLE);
                                        txt_completePfrl.setVisibility(View.GONE);
                                    } else {
                                        txt_completePfrl.setVisibility(View.GONE);
                                    }

                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            datePickerDialog.show();

                        }
                        else {

                            Calendar calendar = Calendar.getInstance();
                            calendar.set(Calendar.YEAR, dobDate.getYear() + 1900);
                            calendar.set(Calendar.MONTH, dobDate.getMonth());
                            calendar.set(Calendar.DAY_OF_MONTH, dobDate.getDate());

                            final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                                @Override
                                public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                                    Calendar cal = Calendar.getInstance();
                                    cal.set(Calendar.YEAR, year);
                                    cal.set(Calendar.MONTH, month);
                                    cal.set(Calendar.DAY_OF_MONTH, day);

                                    dobDate = cal.getTime();

                                    SimpleDateFormat simpleDisplayDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                                    birthday_editview.setText(simpleDisplayDateFormat.format(dobDate));
                                    //birthday_editview.setEnabled(false);
                                    if (birthday_editview.getText().toString().trim().equalsIgnoreCase("")
                                            || gender_editview.getText().toString().trim().equalsIgnoreCase("")) {
                             //           txt_completePfrl.setVisibility(View.VISIBLE);
                                        txt_completePfrl.setVisibility(View.GONE);
                                    } else {
                                        txt_completePfrl.setVisibility(View.GONE);
                                    }

                                }
                            }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                            datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                            datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                            datePickerDialog.show();

                        }
                    }
                }else{
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        new CustomerDetailsTask().execute();
        new PointsTask().execute();

        getActiveCount();

        txt_topup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openEwalletDialog();
            }
        });

        //share referral code to friends.
        ref_code_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.app_name));
                    String shareMessage= customer_referral_code;
                    //shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID +"\n\n";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                    startActivity(Intent.createChooser(shareIntent, customer_referral_code));
                } catch(Exception e) {
                    e.printStackTrace();
                }
            }
        });

        //generate a referral code
        generate_referral_code.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new GenerateReferralCode().execute();
            }
        });

        //getSecondaryAddress();
        return mView;
    }

    /**
     * @param ref_code
     * display client referral code details in UI.
     */
    @SuppressLint("SetTextI18n")
    private void showReferralDetails(String ref_code){
       if(ref_code == null || ref_code.isEmpty() || ref_code.equalsIgnoreCase("null")) {
           generate_referral_code.setVisibility(View.VISIBLE);
           ref_code_lyt.setVisibility(View.GONE);
       }else {
           ref_code_lyt.setVisibility(View.VISIBLE);
           referral_code.setText(mContext.getString(R.string.referral_code) + ref_code);
           customer_referral_code = ref_code;
           generate_referral_code.setVisibility(View.GONE);
       }
    }

    private void getActiveCount() {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            //Promotion count and Reawrd points
            Map<String, String> mapData = new HashMap<>();
            mapData.put("app_id", GlobalValues.APP_ID);

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                mapData.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

            } else {
                mapData.put("customer_id", Utility.getReferenceId(mContext));
            }

            JSONArray jsonArray = new JSONArray();
            jsonArray.put("order");
            jsonArray.put("promotionwithoutuqc");
            jsonArray.put("reward_monthly");
            jsonArray.put("order_all");
            jsonArray.put("notify");
            jsonArray.put("reward");
            mapData.put("act_arr", jsonArray.toString());

            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&availability_id=" + CURRENT_AVAILABLITY_ID + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            new ActivCountTask(mapData).execute(url);

        }
    }

    private class ActivCountTask extends AsyncTask<String, Void, String> {

        private Map<String, String> countParams;

        private ProgressDialog progressDialog;

        public ActivCountTask(Map<String, String> mapData) {
            countParams = mapData;
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


            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObjects = new JSONObject(s);

                if (jsonObjects.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObjects.getJSONObject("result_set");
                    walletBalance = String.valueOf(countJson.optDouble("wallet_balnace"));

                    if(!(walletBalance == null && walletBalance.equalsIgnoreCase(""))) {
                        if(Double.parseDouble(walletBalance) > 0){
                            txt_walletBalance.setText("Wallet Balance : $" +  walletBalance);
                        }else{
                            txt_walletBalance.setText("Wallet Balance : $0.00");
                        }
                    }else{
                        txt_walletBalance.setText("Wallet Balance : $0.00");
                    }
                } else {

                }
                progressDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }

    }

    private void openEwalletDialog() {
        final Dialog dialog = new Dialog(mContext);
        dialog.setContentView(R.layout.fragment_ewallet);
        dialog.setCanceledOnTouchOutside(true);
        if (!dialog.isShowing())
            dialog.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialog.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity = Gravity.CENTER;
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }

        RecyclerView recyclerview_ewalletAmount = dialog.findViewById(R.id.recyclerview_ewalletAmount);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        edt_enterAmount = dialog.findViewById(R.id.edt_enterAmount);
        TextView txtContinue = dialog.findViewById(R.id.txt_Continue);



        final ArrayList<String> arrayList = new ArrayList<>();

        arrayList.add("10");
        arrayList.add("20");
        arrayList.add("50");
        arrayList.add("100");
        arrayList.add("150");
        arrayList.add("500");

        ewalletAdapter = new EwalletAdapter(mContext, arrayList);
        recyclerview_ewalletAmount.setLayoutManager(new GridLayoutManager(mContext, 3, GridLayoutManager.VERTICAL, false));
        recyclerview_ewalletAmount.setAdapter(ewalletAdapter);

        edt_enterAmount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentReward.pos = 10;
                ewalletAdapter.notifyDataSetChanged();
            }
        });

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                if (!(edt_enterAmount.getText().toString().trim().equalsIgnoreCase("") || edt_enterAmount.getText().toString().trim().equalsIgnoreCase("0"))) {
                    GlobalValues.eWalletAmount = edt_enterAmount.getText().toString();

                }
                if (GlobalValues.eWalletAmount.equalsIgnoreCase("") || GlobalValues.eWalletAmount.equalsIgnoreCase("0")) {
                    Toast.makeText(mContext, "Please select topup amount", Toast.LENGTH_SHORT).show();
                } else {
                   /* new PriceForPoints().execute(GlobalUrl.PRICE_FOR_POINTS_URL + "?app_id=" +
                            GlobalValues.APP_ID + "&loyality_points=" + GlobalValues.eWalletAmount.replace("$",""));*/

                    Intent intent = new Intent(mContext, PaymentActivity.class);
                    intent.putExtra("loyality_point_price", GlobalValues.eWalletAmount.replace("$",""));
                    intent.putExtra("rewardPointsPayment", true);
                    startActivity(intent);

                }
            }
        });

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private class PriceForPoints extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(getActivity(), ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = WebserviceAssessor.getData(params[0]);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);
                    status = responseJSONObject.getString("status");
                    if (status.equals("ok")) {
                        Intent intent = new Intent(mContext, PaymentActivity.class);
                        intent.putExtra("loyality_point_price", responseJSONObject.getString("loyality_point_price"));
                        intent.putExtra("rewardPointsPayment", true);
                        startActivity(intent);
                    } else {}

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            } else {

            }

        }
    }

    private void intiView(View view) {
        mContext = getActivity();
        txt_myAccount=view.findViewById(R.id.txt_myAccount);
        firstname_editview = view.findViewById(R.id.firstname_editview);
        mobilenumber_editview = view.findViewById(R.id.mobilenumber_editview);
        emailaddress_editview = view.findViewById(R.id.emailaddress_editview);
        birthday_editview = view.findViewById(R.id.birthday_editview);
        gender_editview = view.findViewById(R.id.gender_editview);
        txt_changepass = view.findViewById(R.id.txt_changepass);
        button_update = view.findViewById(R.id.button_update);
        recyclerviewOtherAddress = view.findViewById(R.id.recyclerView_address);
        txt_addMore = view.findViewById(R.id.txt_addMore);
        view_addMore = view.findViewById(R.id.view_addMore);
        shadow_layout = view.findViewById(R.id.shadow_layout);
        img_gold_member = view.findViewById(R.id.img_gold_member);
        txt_gold_member_label = view.findViewById(R.id.txt_gold_member_label);
        txt_become_gold_member = view.findViewById(R.id.txt_become_gold_member);
        layoutProgress = mView.findViewById(R.id.lly_gold_member_progress_bar);
        lly_label = mView.findViewById(R.id.lly_label);
        txt_completePfrl = mView.findViewById(R.id.txt_completePfrl);
        birthday_text = mView.findViewById(R.id.birthday_text);
        txt_reward = mView.findViewById(R.id.txt_reward);
        lastname_editview = view.findViewById(R.id.lastname_editview);
        txt_topup = view.findViewById(R.id.txt_topup);
        txt_walletBalance = view.findViewById(R.id.txt_walletBalance);
        referral_code = view.findViewById(R.id.referral_code);
        generate_referral_code = view.findViewById(R.id.generate_referral_code);
        ref_code_lyt = view.findViewById(R.id.ref_code_lyt);
        upgrade_acc = view.findViewById(R.id.upgrade_acc);
        renew_acc=view.findViewById(R.id.renew_acc);

        if (GlobalValues.is_card_expired.equalsIgnoreCase("Yes")){
            renew_acc.setVisibility(View.VISIBLE);
        }else {
            renew_acc.setVisibility(View.GONE);
        }
        today = Calendar.getInstance();
        String paymentKey = Utility.readFromSharedPreference(mContext, GlobalValues.PAYMENTKEY);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
//        initializeCyberSource();

        try {
            Log.e("TAG","PaymentKeyTest::"+paymentKey);
            client = new Client(paymentKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        Log.e("TAG","MembershipIdTest::"+Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID));
        if (!Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID).isEmpty()){
            upgrade_acc.setText("Member Id:"+Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID));
            txt_reward.setVisibility(View.GONE);
            txt_myAccount.setVisibility(View.GONE);
            txt_changepass.setVisibility(View.VISIBLE);
        }else {
            txt_changepass.setVisibility(View.GONE);
        }

        upgrade_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID).isEmpty()){
                    Intent intent=new Intent(mContext, MemberLogRegActivity.class);
                    intent.putExtra("value","MyAccount");
                    intent.putExtra("from", "Member Sign Up");
                    startActivity(intent);
                }
            }
        });
        renew_acc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String message = "Please pay membership payment";
                new CheckOutMessageDialog(mContext, message, new CheckOutMessageDialog.OnSlectedMethod() {
                    @Override
                    public void selectedAction(String action) {
                        if (action.equalsIgnoreCase("YES")) {
                   //         Toast.makeText(mContext,"On going developement",Toast.LENGTH_SHORT).show();
                            Payment();

                 //           openPaymentDialog();


                        }
                    }
                });
            }
        });
    }

    public void Payment() {

        paymentpopup = new BottomSheetDialog(mContext, R.style.custom_dialog_theme);
        paymentpopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
        paymentpopup.setContentView(R.layout.payment_type_popup);
        paymentpopup.setCancelable(true);
        paymentpopup.show();
        final String[] selectedPaymentMode = {""};

        final RelativeLayout rel_wallet = paymentpopup.findViewById(R.id.rel_wallet);
        final LinearLayout rel_card = paymentpopup.findViewById(R.id.rel_card);
        final RelativeLayout rel_nets = paymentpopup.findViewById(R.id.rel_nets);

        nets_pay_lyt = paymentpopup.findViewById(R.id.nets_pay_lyt);
        nets_pay_lyt_register = paymentpopup.findViewById(R.id.nets_pay_lyt_register);
        nets_pay_lyt_deregister = paymentpopup.findViewById(R.id.nets_pay_lyt_deregister);


        card_number = paymentpopup.findViewById(R.id.card_number);
        de_register = paymentpopup.findViewById(R.id.de_register);
        exp_date = paymentpopup.findViewById(R.id.exp_date);
        card_details = paymentpopup.findViewById(R.id.card_details);

        if (Utility.readFromSharedPreference(mContext,GlobalValues.PAYMENTKEYENABLE).contains("OMISE")){
            rel_card.setVisibility(View.VISIBLE);
        }else {
            rel_card.setVisibility(View.GONE);
        }

        TextView layoutContinue = paymentpopup.findViewById(R.id.layoutContinue);
        ImageView img_close = paymentpopup.findViewById(R.id.img_close);

   //     if (value_from.equalsIgnoreCase("MyAccount")){
            clearRegistration();
   //     }

        updateUI();


        nets_pay_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_nets.performClick();
                nets_pay_lyt_register.performClick();

            }
        });


        card_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalValues.PAYMENT_TYPE = "NETS";
                if (NofService.isInitialized()) {
                    checkAndProceedNetsPay(paymentpopup);
                } else {
                    initializeNOF(PURCHASE, paymentpopup);
                }
            }
        });

        de_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (NofService.isInitialized()) {
                    showAlertDialogDelete();
                } else {
                    initializeNOF(DE_REGISTER, paymentpopup);
                }
            }
        });

        nets_pay_lyt_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalValues.PAYMENT_TYPE = "NETS";
                Log.e("TAG", "InitializeTest_1::" + NofService.isInitialized());
                if (NofService.isInitialized()) {
                    paymentpopup.dismiss();
                    checkAndProceedNetsPay(paymentpopup);
                } else {
                    initializeNOF(REGISTER, paymentpopup);
                }
            }
        });


        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paymentpopup.dismiss();
            }
        });
        rel_wallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rel_wallet.setBackgroundResource(R.drawable.payment_border_green);
                rel_card.setBackgroundResource(R.drawable.payment_border);
                rel_nets.setBackgroundResource(R.drawable.payment_border);
                selectedPaymentMode[0] = "wallet";
            }
        });

        rel_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_card.setBackgroundResource(R.drawable.payment_border_green);
                rel_wallet.setBackgroundResource(R.drawable.payment_border);
                rel_nets.setBackgroundResource(R.drawable.payment_border);
                selectedPaymentMode[0] = "card";
            }
        });

        rel_nets.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rel_nets.setBackgroundResource(R.drawable.payment_border_green);
                rel_card.setBackgroundResource(R.drawable.payment_border);
                rel_wallet.setBackgroundResource(R.drawable.payment_border);
                selectedPaymentMode[0] = "Nets";
            }
        });

        layoutContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (selectedPaymentMode[0].isEmpty()) {
                    return;
                }

                if (selectedPaymentMode[0].equalsIgnoreCase("wallet")) {
                    paymentpopup.dismiss();
                    return;
                }

                if (selectedPaymentMode[0].equalsIgnoreCase("Nets")) {
                    paymentpopup.dismiss();
                    //       NetsPayservice(paymentpopup);
                    return;
                }

                if (selectedPaymentMode[0].equalsIgnoreCase("card")) {
                    paymentpopup.dismiss();
                    openPaymentDialog();
                   /* Intent intent=new Intent(mContext,PaymentActivityRenewal.class);
                    intent.putExtra("PAYMENT_TYPE","COD");
                    startActivity(intent);*/

                    // Hide 04_11_2022              validateAndPlaceOrder();
                }
            }
        });



    }

    private void openPaymentDialog() {
        dialogBottom = new Dialog(mContext);
        dialogBottom.setContentView(R.layout.dialog_payment_bottom);
        dialogBottom.setCanceledOnTouchOutside(false);
        dialogBottom.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        edtCardName_new = dialogBottom.findViewById(R.id.edtCardName_new);
        edtCardNumber_new = dialogBottom.findViewById(R.id.edtCardNumber_new);
        txt_expDate_new = dialogBottom.findViewById(R.id.txt_expDate_new);
        edtCVVNumber_new = dialogBottom.findViewById(R.id.edtCVVNumber_new);
        btn_continue = dialogBottom.findViewById(R.id.btn_continue);
        btn_continue_disabled = dialogBottom.findViewById(R.id.btn_continue_disabled);
        ImageView img_close = dialogBottom.findViewById(R.id.img_close);

        btn_continue_disabled.setVisibility(View.VISIBLE);
        btn_continue.setVisibility(View.GONE);

        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialogBottom.dismiss();
            }
        });
        txt_expDate_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //getCardExpDate();
                //createDialogWithoutDateField().show();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(mContext, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        mMonth = String.format("%02d", (selectedMonth + 1));
                        mYear = String.valueOf(selectedYear);

                        txt_expDate_new.setText(String.valueOf(String.format("%02d", (selectedMonth + 1)) + "/" + String.valueOf(selectedYear).substring(2, 4)));


                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JANUARY)
                        .setMinYear(today.get(Calendar.YEAR))
                        .setActivatedYear(today.get(Calendar.YEAR))
                        .setMaxYear(today.get(Calendar.YEAR) + 20)
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {

                                String month = String.format("%02d", (selectedMonth + 1));
                                mMonth = month;
                                if (mMonth.equalsIgnoreCase("")) {
                                    mMonth = "1";
                                }
                                if (mYear.equalsIgnoreCase("")) {
                                    mYear = String.valueOf(today.get(Calendar.YEAR));
                                }
                                if (!(mYear.equalsIgnoreCase("") || mMonth.equalsIgnoreCase(""))) {
                                    txt_expDate_new.setText(String.valueOf(String.format("%02d", (Integer.parseInt(mMonth))) + "/" + mYear.substring(2, 4)));
                                }
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {

                                String exp_year = String.valueOf(selectedYear).substring(2, 4);
                                mYear = String.valueOf(selectedYear);
                                if (mMonth.equalsIgnoreCase("")) {
                                    mMonth = "1";
                                }
                                if (mYear.equalsIgnoreCase("")) {
                                    mYear = String.valueOf(today.get(Calendar.YEAR));
                                }
                                if (!(mYear.equalsIgnoreCase("") || mMonth.equalsIgnoreCase(""))) {
                                    txt_expDate_new.setText(String.valueOf(String.format("%02d", (Integer.parseInt(mMonth))) + "/" + mYear.substring(2, 4)));
                                }
                            }
                        })
                        .build()
                        .show();
            }
        });
        dialogBottom.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                dialogBottom.dismiss();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();

                cardName = edtCardName_new.getText().toString();
                cardNumber = edtCardNumber_new.getText().toString();
                cvvNumber = edtCVVNumber_new.getText().toString();

                if (onlinePaymentValidation()) {
                    btn_continue_disabled.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.GONE);
                    if (Utility.networkCheck(mContext)) {

                        callingOnlinePaymentOldOmise();

                        btn_continue.setEnabled(false);
                    } else {

                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();

                }
            }
        });



        edtCardName_new.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (onlinePaymentValidationForButton()) {
                    btn_continue_disabled.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.VISIBLE);
                } else {
                    btn_continue_disabled.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.GONE);
                }
            }
        });
        edtCardNumber_new.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (onlinePaymentValidationForButton()) {
                    btn_continue_disabled.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.VISIBLE);
                } else {
                    btn_continue_disabled.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.GONE);
                }
            }
        });
        txt_expDate_new.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (onlinePaymentValidationForButton()) {
                    btn_continue_disabled.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.VISIBLE);
                } else {
                    btn_continue_disabled.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.GONE);
                }
            }
        });
        edtCVVNumber_new.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (onlinePaymentValidationForButton()) {
                    btn_continue_disabled.setVisibility(View.GONE);
                    btn_continue.setVisibility(View.VISIBLE);
                } else {
                    btn_continue_disabled.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.GONE);
                }
            }
        });

        dialogBottom.show();
        Window window = dialogBottom.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
    }

    public boolean onlinePaymentValidationForButton() {
        if (edtCardName_new.getText().toString().length() <= 0) {
            onlineValidation = false;
            mMessage = "Please enter Name";
        } else if (edtCardNumber_new.getText().toString().length() <= 0) {
            onlineValidation = false;
            mMessage = "Please enter Valid Card number";
        } else if (edtCardNumber_new.getText().toString().length() < 16) {
            onlineValidation = false;
            mMessage = "Please enter Valid Card number";
        } else if (txt_expDate_new.getText().toString().length()<=0) {
            onlineValidation = false;
            mMessage = "Please Enter Expiry Month and Year";
        } else if (edtCVVNumber_new.getText().toString().length() <= 0) {
            onlineValidation = false;
            mMessage = "Please enter CVV";
        } else {
            onlineValidation = true;
        }
        return onlineValidation;
    }

    public void callingOnlinePaymentOldOmise() {
        try {
            TokenRequest request = new TokenRequest();
            request.number = cardNumber;
            request.name = cardName;
            request.expirationMonth = Integer.parseInt(mMonth);
            request.expirationYear = Integer.parseInt(mYear);
            request.securityCode = cvvNumber;

            client.send(request, new TokenRequestListener() {
                @Override
                public void onTokenRequestSucceed(TokenRequest request, Token token) {


                    String customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                    FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);
//Todo surya
                    formBuilder.add("customer_id", customerId);

                    formBuilder.add("paid_amount", "40.00");

                    formBuilder.add("token", token.id);
                  /*  if (saveCard) {
                        formBuilder.add("create_customer", "yes");
                        formBuilder.add("card_id", "");
                    } else {*/
                        formBuilder.add("create_customer", "no");
                        formBuilder.add("card_id", "");
               //     }
                    formBuilder.add("request_type", "");
                    formBuilder.add("outlet_name", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));

                    FormBody formBody = formBuilder.build();
                    Gson gson = new Gson();
                    String json = gson.toJson(formBody);
                    Log.e("TAG","FormDataTest::"+json);
                    webAuthotizeTask = new WebAuthotizeTask(formBody);
                    webAuthotizeTask.execute();
                }

                @Override
                public void onTokenRequestFailed(TokenRequest request, Throwable throwable) {
                    Log.e("TAG","WebauthTest00::"+throwable.getMessage());
                    Toast.makeText(mContext, throwable.getMessage(), Toast.LENGTH_SHORT).show();


                    btn_continue.setEnabled(true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public class WebAuthotizeTask extends AsyncTask<String, String, String> {

        FormBody formBody;
        String response, status, message;
        ProgressDialog progressDialog;

        WebAuthotizeTask(FormBody formBody) {
            this.formBody = formBody;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = webAuthorizeRun(formBody);
                Log.e("TAG","WebauthTest::"+formBody.toString()+"\n.."+response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            if (response != null) {

                try {

                    JSONObject jsonObject = new JSONObject(response);


                    String status = jsonObject.getString("status");

                    if (status.equals("ok")) {

                        JSONObject result_set = jsonObject.getJSONObject("result_set");
                        mPrevCaptureId = result_set.getString("captureId");

                        FormBody.Builder formBuilder = new FormBody.Builder()
                                .add("app_id", GlobalValues.APP_ID);

                        formBuilder.add("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                        //TODO surya
                        formBuilder.add("token", mPrevCaptureId);

                        formBuilder.add("log_id", result_set.getString("log_id"));
                        //formBuilder.add("paid_amount", loyality_point_price);

                        formBuilder.add("paid_amount", "40.00");

                        formBuilder.add("outlet_name", Utility.readFromSharedPreference(mContext,GlobalValues.OUTLET_NAME));

                        FormBody formBody = formBuilder.build();

                        new WebChargeTask(formBody).execute();

                    } else {
                        progressDialog.dismiss();
                        if (jsonObject.has("form_error")) {
                            String formError = jsonObject.getString("form_error");
                            Toast.makeText(mContext, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                        } else {
                            message = jsonObject.getString("message");
                            Toast.makeText(mContext, "Something went wrong!", Toast.LENGTH_LONG).show();

                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(mContext, "There seems to be a network issue.", Toast.LENGTH_SHORT).show();
                }
            } else {
                progressDialog.dismiss();

                Toast.makeText(mContext, "There seems to be a network issue.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    public String webAuthorizeRun(RequestBody formBody) throws Exception {
        Request request;
        if (!GlobalValues.ACCESS_TOKEN.isEmpty()) {
            request = new Request.Builder()
                    .url(GlobalUrl.webAuthorizeURL)
                    .addHeader("Authorization", GlobalValues.ACCESS_TOKEN)
                    .post(formBody)
                    .build();
        } else {
            request = new Request.Builder()
                    .url(GlobalUrl.webAuthorizeURL)
                    .post(formBody)
                    .build();
        }
        Response response = webAuthorizeClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;
    }

    //WebAuthorize Client
    final OkHttpClient webAuthorizeClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public class WebChargeTask extends AsyncTask<String, String, String> {

        FormBody formBody;
        String response, status, message;
        ProgressDialog progressDialog;

        WebChargeTask(FormBody formBody) {
            this.formBody = formBody;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = webChargeRun(formBody);
                Gson gson = new Gson();
                String json = gson.toJson(formBody);
                Log.e("TAG","FormDataTest_22::"+json+"\n.."+response);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();
            if (response != null) {

                try {


                    JSONObject jsonObject = new JSONObject(response);
                    String status = jsonObject.getString("status");
                    Log.e("TAG","PaymentSuccessRenew::"+response);
                    if (status.equals("ok")) {
                        dialogBottom.dismiss();
                        if (Utility.networkCheck(mContext)) {
                            String url = GlobalUrl.MEMBER_RENEWAL;
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("member_id", Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID));
                            params.put("member_card_no",Utility.readFromSharedPreference(mContext,GlobalValues.ASCENTIS_CARD_NO));

                            new MemberRenewal(params).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                    //Toast.makeText(PaymentActivity.this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public String webChargeRun(RequestBody formBody) throws Exception {
        Request request=null;
        if(!GlobalValues.ACCESS_TOKEN.isEmpty()){
            request = new Request.Builder()
                    .url(GlobalUrl.webChargeRenewURL)
                    .addHeader("Authorization",GlobalValues.ACCESS_TOKEN)
                    .post(formBody)
                    .build();
        }else {
            request = new Request.Builder()
                    .url(GlobalUrl.webChargeRenewURL)
                    .post(formBody)
                    .build();
        }


        Response response = webChargeClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;
    }

    //WebCharge Client
    final OkHttpClient webChargeClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public boolean onlinePaymentValidation() {
        if (cardName.length() <= 0) {
            onlineValidation = false;
            mMessage = "Please enter Name";
        } else if (cardNumber.length() <= 0) {
            onlineValidation = false;
            mMessage = "Please enter Valid Card number";
        } else if (cardNumber.length() < 16) {
            onlineValidation = false;
            mMessage = "Please enter Valid Card number";
        } else if ((mMonth.length() <= 0) || (mYear.length() <= 0)) {
            onlineValidation = false;
            mMessage = "Please Enter Expiry Month and Year";
        } else if (cvvNumber.length() <= 0) {
            onlineValidation = false;
            mMessage = "Please enter CVV";
        } else {
            onlineValidation = true;
        }
        return onlineValidation;
    }

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
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("TAG", "NETS-Purchase : " + params[0] + "\n" + prichaseDetails.toString());
            return WebserviceAssessor.postRequest(mContext, params[0], prichaseDetails);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!= null) {
                try {
                    Log.e("TAG", "Purchase_Response_new:" + s);
                    JSONObject object = new JSONObject(s);
                    if(object.optString("status").equalsIgnoreCase("ok")){
                        String responseCode = object.getString("response_code");
                        if(responseCode.equalsIgnoreCase("00")){ //transaction success
                            Log.e("success", "transaction success");
                            trans_reference_number=object.optString("trans_reference_no");

                            Utility.writeToSharedPreference(mContext,GlobalValues.PaymentSuccess,"Yes");
                    //        register.performClick();
                            renew_acc.setVisibility(View.GONE);
                            paymentpopup.dismiss();

                            if (Utility.networkCheck(mContext)) {
                                String url = GlobalUrl.MEMBER_RENEWAL;
                                Map<String, String> params = new HashMap<String, String>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("member_id", Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID));
                                params.put("member_card_no",Utility.readFromSharedPreference(mContext,GlobalValues.ASCENTIS_CARD_NO));

                                new MemberRenewal(params).execute(url);
                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }

                        }else if(responseCode.equalsIgnoreCase("U9") || responseCode.equalsIgnoreCase("55")) { //invoke pin pad
                            trans_reference_number=object.optString("trans_reference_no");

                            JSONArray txt_data = object.getJSONObject("result_set").getJSONArray("txn_specific_data");
                            Log.e("TAG","DebitwithPin_issue::"+txt_data);
                            if(txt_data.length() > 0){
                                JSONObject t5253_Obj = txt_data.getJSONObject(1);
                                String t5253 = t5253_Obj.getString("sub_id") + t5253_Obj.getString("sub_length") + t5253_Obj.getString("sub_data");
                                Log.e("TAG","DebitwithPin_issue_1::"+t5253_Obj+"\n"+t5253);
                                doDebitWithPin(t5253, responseCode);
                            }
                        }else {
                            Log.e("failed", object.optString("message"));
                            showDialogue(PURCHASE, object.optString("message"), true);
                        }
                    }else {
                        Log.e("failed", object.optString("message"));
                        showDialogue(PURCHASE, object.optString("message"), true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showDialogue(PURCHASE, "some error occurred", true);
                }
            }
            progressDialog.dismiss();
        }
    }

    private void showDialogue(String title, String message, boolean show_message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(mContext)
                    .setTitle("NETS Bank Card")
                    .setMessage(show_message ? message : "Unexpected error has occurred.")
                    .setPositiveButton("ok", (dialog, which) -> dialog.dismiss());
            AlertDialog dialog = builder.create();
            dialog.show();
        });
    }

    public void doDebitWithPin (String t5253, String responseCode) {
        Log.e("TAG", "t5253 : " + t5253 + "\n" + "responseCode : " + responseCode);
        Table53Data table53Data = new Table53Data(t5253);   //nets sdk
        Table53 table53 = new Table53(t5253); //custom
        table53.parse();
        try {
            table53Data.parse();
        } catch (Exception e) {
            e.printStackTrace();
        }

        Log.e("TAG", "NETS_SDK : " + table53Data.toString() + "\n" +
                "CUSTOM : " +"Amount:"+formatAmountInCents(renewal_amt.replace("$", ""))+"\n"+"ResponceCode"
                +responseCode+"\n"+"Crypto:"+table53.getData());
        Log.e("TAG","CryptoTest::"+table53);

        Debit debit = new Debit(formatAmountInCents(renewal_amt), responseCode, table53.getData());
        try {
            debit.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {
                    Log.e("TAG", "Debit_with_pin_successful : \n" + s);
                    validatePurchase(s, true);
                }

                @Override
                public void failure(String s) {
                    Log.e("TAG", "Debit_with_pin_failed : \n" + s);
                    showDialogue("Debit failed", s, false);
                }
            });
        } catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            Log.e("TAG", "Debit_with_pin_failed_1 : \n" +e );
            showDialogue("Debit failed", "Exception" + e.getMessage(), false);
        }
    }

    private void validatePurchase (String token, boolean is_pin_pad_request) {
        Map<String, String> params = new HashMap<>();


        //     params.put("muid", Utility.getReferenceId(mContext));
        params.put("muid", "0");
        params.put("customer_id", "0");
        params.put("mid", NETSServices.MID);
        params.put("t0205", token);
        params.put("amt", formatAmountInCents(renewal_amt.replace("$", "")));
        params.put("app_id", GlobalValues.APP_ID);


        if (is_pin_pad_request) {
            params.put("action_to_text", "pinpad");
            //      params.put("trans_reference_no", Utility.readFromSharedPreference(mContext,GlobalValues.TRANS_REF_NO));

            //       params.put("trans_reference_no", Utility.readFromSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER));
            params.put("trans_reference_no", trans_reference_number);

        }else {
            //        params.put("trans_reference_no", Utility.readFromSharedPreference(mContext,GlobalValues.TRANS_REF_NO));

            params.put("trans_reference_no", Utility.readFromSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER));
            //       params.put("trans_reference_no", trans_reference_number);
        }
        params.put("customer_id","0");
        //     params.put("ascentis_registration", "yes");

        Log.e("TAG","NetsParamTest_11::"+params);
        new NETSPurchase(params).execute(NETS_PURCHASE);


    }

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

    private void checkAndProceedNetsPay(BottomSheetDialog dialog) {
        if(isRegistrationSuccess()) {
            dialog.dismiss();
            showAlertDialogCard();
        } else {
            doRegistration();
        }
    }

    private void showAlertDialogCard () {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(() -> {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext);
            alertDialog.setMessage("Would you like to use this card?");
            alertDialog.setTitle("Payment");
            alertDialog.setPositiveButton("Yes Proceed", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    doDebit();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        });
    }

    public void doDebit() {
        showProgressDialogue();
        Debit debit = new Debit(formatAmountInCents(renewal_amt.replace("$", "")));
        try{
            debit.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {
                    Log.e("TAG", "Debit_successful: \n" + s);
                    hideProgressDialogue();
                    validatePurchase(s, false);
                }

                @Override
                public void failure(String s) {
                    Log.e("TAG", "Debit failed : \n" + s);
                    hideProgressDialogue();
                    showDialogue("Debit failed", s, false);
                }
            });
        }catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            hideProgressDialogue();
            Log.e("TAG", "service not initialized.." + e.getMessage());
            showDialogue("Debit failed", "Exception" + e.getMessage(), false);
        }
    }

    private void hideProgressDialogue() {
        if(progress != null && progress.isShowing())
            progress.dismiss();
    }

    private void showProgressDialogue() {
        progress = new ProgressDialog(mContext);
        progress.setMessage("Loading...");
        progress.show();
    }

    public void doRegistration( ) {

        Registration registration = new Registration(NETSServices.MID, "0");
        try {
            registration.invoke(new StatusCallback<String, String>() {
                @Override
                public void success(String s) {
                    Log.e("RegistrationTest", "Registration successful : \n" + s);
                    validateRegistration(s);
                }

                @Override
                public void failure(String s) {
                    Log.e("RegistrationTest", "Registration failed : \n" + s);
                    //                showDialogue("Registration", "failed :" + s, false);
                }
            });
        }catch (ServiceNotInitializedException e) {
            e.printStackTrace();
            Log.e("RegistrationTest", "service not initialized.." + e.getMessage());
            //         showDialogue("Registration", "Exception : " + e.getMessage(), false);
        }
    }

    private void validateRegistration(String token) {
        Map<String, String> params = new HashMap<>();

        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id","0");
        params.put("order_amount", "00");
        params.put("gmt", token);
        params.put("muid", "0");
        //params.put("muuid", MUUID = (MUUID == null || MUUID.isEmpty()) ? generateMUUID() : MUUID);
        params.put("mid", NETSServices.MID);
        params.put("ascentis_registration", "yes");
        Log.e("TAG", "Register_params : " + params.toString());
        new NETSCardRegistration(params).execute(NETS_REGISTRATION);
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
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            Log.e("TAG", "NETS_Registration: " + params[0] + "\n" + cardDetails.toString());
            return WebserviceAssessor.postRequest(mContext, params[0], cardDetails);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if(s!= null) {
                try {
                    Log.e("TAG", "Registeration_Response :" + s);
                    JSONObject object = new JSONObject(s);
                    if(object.optString("status").equalsIgnoreCase("ok")) {
                        //                    trans_reference_number=object.optString("trans_reference_no");
                        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO, object.optString("trans_reference_no")); //
                        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER, object.optString("trans_reference_no")); //
                        JSONObject result_set = object.getJSONObject("result_set");
                        JSONArray txn_specific_data = result_set.optJSONArray("txn_specific_data");
                        if (txn_specific_data != null && txn_specific_data.length() > 0) {
                            JSONObject txn_data = txn_specific_data.getJSONObject(0);
                            if (txn_data.optString("response_code").equalsIgnoreCase("00")) {
                                Utility.writeToSharedPreference(mContext,GlobalValues.NETS_CARD_NUMBER_NEW, txn_data.optString("last_4_digits_fpan"));
                                Utility.writeToSharedPreference(mContext, GlobalValues.NETS_EXPIRY_NEW, txn_data.optString("mtoken_expiry_date"));
                                Utility.writeToSharedPreference(mContext, GlobalValues.CARD_TYPE_NEW, txn_data.optString("issuer_short_name")); //bank_fiid
                                Utility.writeToSharedPreference(mContext, GlobalValues.NETS_REGISTERED_NEW, "1");
                                updateUI();
                                //        doPurchase();
                            } else if (txn_data.optString("response_code").equalsIgnoreCase("90")) {
                                clearRegistration();
                                showDialogue(REGISTER, object.optString("message"), true);
                            } else {
                                //                     clearRegistration();
                                showDialogue(REGISTER, object.optString("message"), true);
                            }
                        }
                    } else {
                        showDialogue(REGISTER, object.optString("message"), true);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    showDialogue(REGISTER, "Registration failed", true);
                }
            }
            progressDialog.dismiss();
        }
    }

    private void updateUI( ) {


        if(isRegistrationSuccess()) {
            nets_pay_lyt_deregister.setVisibility(View.VISIBLE);
            nets_pay_lyt_register.setVisibility(View.GONE);
            String card_num = getCardType(Utility.readFromSharedPreference(mContext, GlobalValues.CARD_TYPE_NEW)) + "(•••• " + Utility.readFromSharedPreference(mContext, GlobalValues.NETS_CARD_NUMBER_NEW) + ")";
            exp_date.setText("Valid until " + formatDate(Utility.readFromSharedPreference(mContext, GlobalValues.NETS_EXPIRY_NEW)));
            card_number.setText(card_num);
        }else {
            nets_pay_lyt_register.setVisibility(View.VISIBLE);
            nets_pay_lyt_deregister.setVisibility(View.GONE);
        }
    }

    public void initializeNOF(String type, BottomSheetDialog dialog) {
        showProgressDialogue();
        // NOF already initialized
        if(NofService.isInitialized()){
            hideProgressDialogue();
            if(type.equalsIgnoreCase(REGISTER) || type.equalsIgnoreCase(PURCHASE)) {
                checkAndProceedNetsPay(dialog);
            }
            else
                showAlertDialogDelete();
            Log.e("TAG", "NOF Already initialized....");
            return;
        }

        //initializing NOF if not initialized already
        NofService.setSdkDebuggable(false); //for debugging
        try {
            NofService.initialize(
                    mContext,
                    "https://netspay.nets.com.sg", //Dev "https://uatnetspay.nets.com.sg",
                    "https://api.nets.com.sg/",//Dev "https://uat-api.nets.com.sg/uat",
                   /* "https://uatnetspay.nets.com.sg",
                    "https://uat-api.nets.com.sg/uat",*/
                    NETSServices.getAppId(),
                    NETSServices.getAppSecret(),
                    NETSServices.getAppPublicKeySet(),
                    getCaResource(),
                    new StatusCallback<String, String>() {
                        @Override
                        public void success(String s) {
                            hideProgressDialogue();
                            Log.e("InitializationTest", "Initialization_successful : \n" + s+"\n"+type);
                            if(type.equalsIgnoreCase(REGISTER) || type.equalsIgnoreCase(PURCHASE)) {

                                checkAndProceedNetsPay(dialog);
                            }
                            else{
                                showAlertDialogDelete();
                            }
                        }

                        @Override
                        public void failure(String s) {
                            hideProgressDialogue();
                            showRetry(type,dialog);

                            Log.e("InitializationTest", "Initialization_failed_responce : \n" + s);
                        }
                    }
            );
        } catch (ServiceAlreadyInitializedException e) {
            e.printStackTrace();
            Log.e("TAG", "some error occured");
            hideProgressDialogue();
            showRetry(type,paymentpopup);
            //showDialogue("Initialization", "Exception : " + e.getMessage());
        }
    }

    private int getCaResource() {
        return this.getResources().getIdentifier("netspay_nets_com_sg",
                "raw", getActivity().getPackageName());
    }
    /*private int getCaResource() {
        return this.getResources().getIdentifier("ca_uat",
                "raw", getActivity().getPackageName());
    }*/

    private void showRetry(String type, BottomSheetDialog dialog) {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(() -> {
            String message = "NETS payment initialize failed do you want to retry?";
            new ClearCartMessageDialog(mContext, message, action -> {
                if (action.equalsIgnoreCase("YES")) {
                    initializeNOF(type, dialog);
                }
            });
        });
    }

    private void showAlertDialogDelete() {
        Handler mainHandler = new Handler(Looper.getMainLooper());
        mainHandler.post(() -> {
            android.app.AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext);
            alertDialog.setMessage("Are you sure, want to delete this card?");
            alertDialog.setTitle("Delete");
            alertDialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    deRegisterNets();
                }
            });
            alertDialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            alertDialog.show();
        });
    }

    private void deRegisterNets () {
        showProgressDialogue();
        Deregistration deregistration = new Deregistration();
        try {
            deregistration.invoke(new StatusCallback() {
                @Override
                public void success(Object o) {
                    String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
                    Log.e("time", timeStamp);
                    clearRegistration();
                    updateUI();
                    hideProgressDialogue();
                }

                @Override
                public void failure(Object o) {
                    hideProgressDialogue();
                    showDialogue("Deregistration failed: ", "Result: " + o, false);
                }
            });
        } catch (ServiceNotInitializedException | CardNotRegisteredException e) {
            e.printStackTrace();
            Log.e("TAG", "failed.." + e.getMessage());
            hideProgressDialogue();
            showDialogue("DeRegistration failed", "Exception : " + e.getMessage(), false);
        }
    }

    private String formatDate (String exp_date) {
        Calendar cals = Calendar.getInstance();
        SimpleDateFormat outputformat = new SimpleDateFormat("MM/yyyy");
        DateFormat inputformat = new SimpleDateFormat("yyMM", Locale.ENGLISH);
        Date date = null;
        try {
            date = inputformat.parse(exp_date);
        } catch (ParseException e) {
            e.printStackTrace();
            return exp_date;
        }
        cals.setTime(date);
        return outputformat.format(date);
    }

    private String getCardType (String fiid) {
        /*switch (fiid) {
            case "NCLC" :
                return "NETS Card";

            case "DBSC" :
                return "DBS Card";

            case "UOBC" :
                return "UOB Card";

            case "OCBC" :
                return "OCBC Card";

            default:
                return "Card";
        }*/
        return fiid;
    }

    private boolean isRegistrationSuccess () {

        if((Utility.readFromSharedPreference(mContext, GlobalValues.NETS_REGISTERED_NEW) == null || Utility.readFromSharedPreference(mContext, GlobalValues.NETS_REGISTERED_NEW).isEmpty() ||
                Utility.readFromSharedPreference(mContext, GlobalValues.NETS_REGISTERED_NEW).equalsIgnoreCase("0")))
            return false;

        if(!(Utility.readFromSharedPreference(mContext, GlobalValues.TRANS_REF_NO) == null && Utility.readFromSharedPreference(mContext, GlobalValues.TRANS_REF_NO).isEmpty()) &&
                !(Utility.readFromSharedPreference(mContext, GlobalValues.NETS_CARD_NUMBER_NEW) == null && Utility.readFromSharedPreference(mContext, GlobalValues.NETS_CARD_NUMBER_NEW).isEmpty()) &&
                !(Utility.readFromSharedPreference(mContext, GlobalValues.NETS_EXPIRY_NEW) == null && Utility.readFromSharedPreference(mContext, GlobalValues.NETS_EXPIRY_NEW).isEmpty()))
            return true;
        else
            return false;
    }

    private void clearRegistration () {
        Utility.writeToSharedPreference(mContext,GlobalValues.NETS_CARD_NUMBER_NEW, "");
        Utility.writeToSharedPreference(mContext, GlobalValues.NETS_EXPIRY_NEW, "");
        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO, ""); //
        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER, ""); //
        Utility.writeToSharedPreference(mContext,GlobalValues.NETS_REGISTERED_NEW, "0");

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        otherAddressRecyclerAdapter = new OtherAddressRecyclerAdapter(getContext());
        recyclerviewOtherAddress.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerviewOtherAddress.setAdapter(otherAddressRecyclerAdapter);
    }

    public class CustomerDetailsTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog availibilityDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            availibilityDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            availibilityDialog.setMessage("Loading...");
            availibilityDialog.setCancelable(false);
            availibilityDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                //Promotion count and Reawrd points

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                    mReferenceId = "";

                } else {
                    mReferenceId = Utility.getReferenceId(mContext);
                    mCustomerId = "";
                }

                String cartDetailURL = GlobalUrl.DEFAULT_BILLING_URL + "?app_id=" + GlobalValues.APP_ID
                        + "&refrence=" + mCustomerId;

                response = WebserviceAssessor.GetRequest(cartDetailURL);


            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            availibilityDialog.dismiss();


            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);


/*
                    "common":{"image_source":"http:\/\/ccpl.ninjaos.com\/media\/dev_team\/profile-pictures\/customers\/"}}
*/


                    JSONObject jsonObjectCommonImgurl = responseJSONObject.getJSONObject("common");

                    CommonImageurl = jsonObjectCommonImgurl.optString("image_source");


                    status = responseJSONObject.getString("status");

                    if (status.equals("ok")) {    //Success


                        Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMER_INFO, responseJSONObject.toString());

                        AccountDetail accountDetail = new AccountDetail();

                        JSONObject resultJSON = responseJSONObject.getJSONObject("result_set");

                        String customer_id = resultJSON.getString("customer_id");
                        String customer_company_id = resultJSON.getString("customer_company_id");
                        String customer_first_name = resultJSON.getString("customer_first_name").replace("\\", "");
                        String customer_last_name = resultJSON.getString("customer_last_name").replace("\\", "");
                        String customer_email = resultJSON.getString("customer_email");
                        String customer_phone = resultJSON.getString("customer_phone");
                        String customer_birthdate = resultJSON.getString("customer_birthdate");
                        String customer_company_name = resultJSON.getString("customer_company_name").replace("\\", "");
                        String customer_address_name = resultJSON.getString("customer_address_name");
                        String customer_address_name2 = resultJSON.getString("customer_address_name2");
                        String customer_address_line1 = resultJSON.getString("customer_address_line1").replace("\\", "");
                        String customer_address_line2 = resultJSON.getString("customer_address_line2").replace("\\", "");
                        String customer_city = resultJSON.getString("customer_city");
                        String customer_state = resultJSON.getString("customer_state");
                        String customer_country = resultJSON.getString("customer_country");
                        String customer_postal_code = resultJSON.getString("customer_postal_code");
                        String customer_photo = resultJSON.getString("customer_photo");
                        String customer_notes = resultJSON.getString("customer_notes");
                        String customer_status = resultJSON.getString("customer_status");
                        String customer_company_address = resultJSON.getString("customer_company_address");
                        String customer_company_phone = resultJSON.getString("customer_company_phone");

                        String customer_reward_point = resultJSON.getString("customer_reward_point");
                        String client_reward_point = resultJSON.getString("client_reward_point");

                        String customer_nick_name = resultJSON.getString("customer_nick_name");
                        String customer_gender = resultJSON.getString("customer_gender");
                        String customer_hobby = resultJSON.getString("customer_hobby");
                        String customer_created_on = resultJSON.getString("customer_created_on");
                        String customer_membership_type = resultJSON.getString("customer_membership_type");
                        String customer_type = resultJSON.getString("customer_type");

                        // String customer_hobby = resultJSON.getString("customer_hobby");

                        String customer_unique_id = "";
                        if (resultJSON.has("customer_unique_id")) {
                            customer_unique_id = resultJSON.getString("customer_unique_id");
                        }

                        String customer_pasta_stripe_id_dev = "";
                        if (resultJSON.has("customer_stripe_id_dev")) {
                            customer_pasta_stripe_id_dev = resultJSON.getString("customer_stripe_id_dev");
                        }

                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CLIENT_ENABLE_MEMBERSHIP).equalsIgnoreCase("1")) {
                            img_gold_member.setVisibility(View.VISIBLE);
                            txt_gold_member_label.setVisibility(View.VISIBLE);
                            txt_become_gold_member.setVisibility(View.VISIBLE);
                            layoutProgress.setVisibility(View.VISIBLE);
                            lly_label.setVisibility(View.VISIBLE);
                            /*img_gold_member.setVisibility(View.GONE);
                            txt_gold_member_label.setVisibility(View.GONE);
                            txt_become_gold_member.setVisibility(View.GONE);
                            layoutProgress.setVisibility(View.GONE);*/
                            //txt_gold_member_label.setText(resultJSON.getString("customer_membership_type") + " Member");
                            txt_gold_member_label.setText(capitaliseFirstLetter(resultJSON.getString("customer_membership_displayname")));

                            /*if (resultJSON.getString("customer_membership_type").equalsIgnoreCase("Gold")) {
                                layoutProgress.setVisibility(View.GONE);
                                Picasso.with(mContext).load(R.drawable.gold_member_icon).into(img_gold_member);
                            } else if (resultJSON.getString("customer_membership_type").equalsIgnoreCase("Sliver")) {
                                layoutProgress.setVisibility(View.VISIBLE);
                                Picasso.with(mContext).load(R.drawable.sliver_member_icon).into(img_gold_member);
                            } else {
                                txt_gold_member_label.setText("Bronze Member");
                                layoutProgress.setVisibility(View.VISIBLE);
                                Picasso.with(mContext).load(R.drawable.bronze_member_icon).into(img_gold_member);
                            }*/

                            if(resultJSON.has("membership_spent_msg")){
                                txt_become_gold_member.setText(resultJSON.getString("membership_spent_msg"));
                            }
                            progressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyleHorizontal);
                            progressBar.setIndeterminate(false);

                            if(resultJSON.has("membership_max_amount") && !resultJSON.optString("membership_max_amount").equalsIgnoreCase("") ) {
                                if (Double.parseDouble(resultJSON.optString("membership_max_amount")) > 0) {

                                    progressBar.setProgress(Double.valueOf(((Double.parseDouble(resultJSON.optString("membership_spent_amount")))
                                            / (Double.parseDouble(resultJSON.optString("membership_max_amount")))) * 100).intValue());
                                    progressBar.setSecondaryProgress((int) Double.parseDouble(resultJSON.optString("membership_max_amount")));
                                } else {
                                    progressBar.setProgress(1000);
                                    progressBar.setSecondaryProgress(1000);
                                }
                            }

                            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 25);
                            progressBar.setLayoutParams(layout_params);

                            if (resultJSON.getString("customer_membership_type").equalsIgnoreCase("Gold")) {
                                layoutProgress.setVisibility(View.GONE);
                                Picasso.with(mContext).load(R.drawable.gold_member_icon).into(img_gold_member);
                            } else if (resultJSON.getString("customer_membership_type").equalsIgnoreCase("Silver")) {
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_shape_sliver));
                                layoutProgress.setVisibility(View.VISIBLE);
                                Picasso.with(mContext).load(R.drawable.sliver_member_icon).into(img_gold_member);
                            } else {
                                progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_shape_bronze));
                                txt_gold_member_label.setText("Bronze Member");
                                layoutProgress.setVisibility(View.VISIBLE);
                                Picasso.with(mContext).load(R.drawable.bronze_member_icon).into(img_gold_member);
                            }

                            if (layoutProgress != null) {
                                layoutProgress.removeAllViews();
                            }

                            layoutProgress.addView(progressBar);

                        } else {
                            img_gold_member.setVisibility(View.GONE);
                            txt_gold_member_label.setVisibility(View.GONE);
                            txt_become_gold_member.setVisibility(View.GONE);
                            layoutProgress.setVisibility(View.GONE);
                            lly_label.setVisibility(View.GONE);
                        }

                      /*  String customer_porto_stripe_id_dev = "";
                        if (resultJSON.has("customer_porto_stripe_id_dev")) {
                            customer_porto_stripe_id_dev = resultJSON.getString("customer_porto_stripe_id_dev");
                        } else {

                        }

                        String customer_kraft_stripe_id_dev = "";
                        if (resultJSON.has("customer_kraft_stripe_id_dev")) {
                            customer_kraft_stripe_id_dev = resultJSON.getString("customer_kraft_stripe_id_dev");
                        } else {

                        }*/

                        //Live
                        String customer_pasta_stripe_id_live = "";
                        if (resultJSON.has("customer_stripe_id_live")) {

                            customer_pasta_stripe_id_live = resultJSON.getString("customer_stripe_id_live");
                        }
                     /*   String customer_porto_stripe_id_live = "";
                        if (resultJSON.has("customer_porto_stripe_id_live")) {
                            customer_porto_stripe_id_live = resultJSON.getString("customer_porto_stripe_id_live");
                        } else {

                        }
                        String customer_kraft_stripe_id_live = "";
                        if (resultJSON.has("customer_kraft_stripe_id_live")) {
                            customer_kraft_stripe_id_live = resultJSON.getString("customer_kraft_stripe_id_live");

                        } else {

                        }*/

                        /**
                         * Refferral Update implemented on
                         * 24 Sept  2021
                         */

                        String is_referral_enabled = Utility.readFromSharedPreference(mContext, GlobalValues.IS_REFERRAL_ENABLE);
                        if(is_referral_enabled != null && is_referral_enabled.equalsIgnoreCase("1")) {
                            resultJSON.optString("customer_referrar_code");
                            showReferralDetails(resultJSON.optString("customer_referrar_code")); // referral enabled
                        }

                        accountDetail.setCustomer_id(customer_id);
                        accountDetail.setCustomer_company_id(customer_company_id);
                        accountDetail.setCustomer_first_name(customer_first_name);
                        accountDetail.setCustomer_last_name(customer_last_name);
                        accountDetail.setCustomer_email(customer_email);
                        accountDetail.setCustomer_phone(customer_phone);
                        accountDetail.setCustomer_birthdate(customer_birthdate);
                        accountDetail.setCustomer_company_name(customer_company_name);
                        accountDetail.setCustomer_address_name(customer_address_name);
                        accountDetail.setCustomer_address_name2(customer_address_name2);
                        accountDetail.setCustomer_address_line1(customer_address_line1);
                        accountDetail.setCustomer_address_line2(customer_address_line2);
                        accountDetail.setCustomer_city(customer_city);
                        accountDetail.setCustomer_state(customer_state);
                        accountDetail.setCustomer_country(customer_country);
                        accountDetail.setCustomer_postal_code(customer_postal_code);
                        accountDetail.setCustomer_photo(customer_photo);
                        accountDetail.setCustomer_notes(customer_notes);
                        accountDetail.setCustomer_status(customer_status);
                        accountDetail.setCustomer_company_address(customer_company_address);
                        accountDetail.setCustomer_company_phone(customer_company_phone);
                        accountDetail.setCustomer_reward_point(customer_reward_point);
                        accountDetail.setClient_reward_point(client_reward_point);

                        accountDetail.setIsFbLogin(false);

                        accountDetail.setcustomer_pasta_stripe_id_dev(customer_pasta_stripe_id_dev);
              /*          accountDetail.setcustomer_porto_stripe_id_dev(customer_porto_stripe_id_dev);
                        accountDetail.setcustomer_kraft_stripe_id_dev(customer_kraft_stripe_id_dev);*/

                        accountDetail.setCustomer_pasta_stripe_id_live(customer_pasta_stripe_id_live);
                    /*    accountDetail.setCustomer_porto_stripe_id_live(customer_porto_stripe_id_live);
                        accountDetail.setCustomer_kraft_stripe_id_live(customer_kraft_stripe_id_live);*/

                        accountDetail.setCustomer_nick_name(customer_nick_name);
                        accountDetail.setCustomer_gender(customer_gender);
                        accountDetail.setCustomer_hobby(customer_hobby);
                        accountDetail.setCustomer_created_on(customer_created_on);
                        accountDetail.setCustomer_membership_type(customer_membership_type);
                        accountDetail.setCustomer_type(customer_type);

                        accountDetail.setCustomer_unique_id(customer_unique_id);


                        GlobalValues.ACCOUNT_DETAIL = accountDetail;


                        //Update data to my account section after retrived data


                        updateUserDetails();


                    } else {

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {

            }
        }

    }

    private class PointsTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String app_id = "?app_id=" + GlobalValues.APP_ID;
                String customer_id = "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                String url = GlobalUrl.REDEEMPOINTS_URL + app_id + customer_id;

                response = WebserviceAssessor.GetRequest(url);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);

                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {    //Success

                        JSONObject resultJSONObject = responseJSONObject.getJSONObject("result_set");

                        String promocode_used = resultJSONObject.getString("promocode_used");
                        String earned_points = resultJSONObject.getString("earned_points");
                        String reedem_points = resultJSONObject.getString("reedem_points");



                          /* try {
                         double x_reedempoints = Double.parseDouble(reedem_points);
                            //  int reedemPoints = (int) x_reedempoints;


                            double x_earnedpoints = Double.parseDouble(earned_points);

                            //   int earnedPoints = (int) x_earnedpoints;

                            redeemed_textview.setText(String.valueOf(reedem_points));

                            String value = String.format("%.2f", new BigDecimal((GlobalValues.CUSTOMER_REWARD_POINT)));

                            SpannableStringBuilder cs = new SpannableStringBuilder(value);
//                            cs.setSpan(new SuperscriptSpan(), value.length()-3, value.length()-1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.6f), value.length() - 2, value.length(), 0);


                            earned_points_textview.setText(cs);

                            if (CUSTOMER_MONTHLY_REWARD_POINT > 0) {

                                mess_redwards.setText(String.valueOf(CUSTOMER_MONTHLY_REWARD_POINT) + " Points Expiring In 30 Days");
                            } else {
                                mess_redwards.setText("0.00 Points Expiring In 30 Days");
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        noOfUse_textview.setText(promocode_used);*/

                    } else {

                      /*  message = responseJSONObject.getString("form_error");
                        Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();*/
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {



            }


        }
    }


    private class UpdateTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;

        Map<String, String> mapData;

        UpdateTask(Map<String, String> mapData) {
            this.mapData = mapData;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                response = WebserviceAssessor.postRequest(mContext, GlobalUrl.UPDATEPROFILE_URL, mapData);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);


                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {    //Success

                        message = responseJSONObject.getString("message");

                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();


                        try {
                            JSONObject jsonObjectCommonImgurl = responseJSONObject.getJSONObject("common");
                            CommonImageurl = jsonObjectCommonImgurl.optString("image_source");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        //updating new account details
                        AccountDetail accountDetail = new AccountDetail();

                        JSONObject resultJSON = responseJSONObject.getJSONObject("result_set");

                        String customer_id = resultJSON.getString("customer_id");
                        String customer_company_id = resultJSON.getString("customer_company_id");
                        String customer_first_name = resultJSON.getString("customer_first_name").replace("\\", "");
                        String customer_last_name = resultJSON.getString("customer_last_name").replace("\\", "");
                        String customer_email = resultJSON.getString("customer_email");
                        String customer_phone = resultJSON.getString("customer_phone");
                        String customer_birthdate = resultJSON.getString("customer_birthdate");
                        String customer_company_name = resultJSON.getString("customer_company_name").replace("\\", "");
                        String customer_address_name = resultJSON.getString("customer_address_name");
                        String customer_address_name2 = resultJSON.getString("customer_address_name2");
                        String customer_address_line1 = resultJSON.getString("customer_address_line1").replace("\\", "");
                        String customer_address_line2 = resultJSON.getString("customer_address_line2").replace("\\", "");
                        String customer_city = resultJSON.getString("customer_city");
                        String customer_state = resultJSON.getString("customer_state");
                        String customer_country = resultJSON.getString("customer_country");
                        String customer_postal_code = resultJSON.getString("customer_postal_code");
                        String customer_photo = resultJSON.getString("customer_photo");
                        String customer_notes = resultJSON.getString("customer_notes");
                        String customer_status = resultJSON.getString("customer_status");
                        String customer_company_address = resultJSON.getString("customer_company_address");
                        String customer_company_phone = resultJSON.getString("customer_company_phone");

                        String customer_reward_point = resultJSON.getString("customer_reward_point");
                        String client_reward_point = resultJSON.getString("client_reward_point");

                        String customer_nick_name = resultJSON.getString("customer_nick_name");
                        String customer_gender = resultJSON.getString("customer_gender");
                        String customer_hobby = resultJSON.getString("customer_hobby");
                        String customer_created_on = resultJSON.getString("customer_created_on");
                        String customer_membership_type = resultJSON.getString("customer_membership_type");
                        String customer_type = resultJSON.getString("customer_type");
                        String customer_unique_id = "";
                        if (resultJSON.has("customer_unique_id")) {
                            customer_unique_id = resultJSON.getString("customer_unique_id");
                        }


                        Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMERID, resultJSON.optString("customer_id"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.FIRSTNAME, resultJSON.optString("customer_first_name"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.LASTNAME, resultJSON.optString("customer_last_name"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.EMAIL, resultJSON.optString("customer_email"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMERPHONE, resultJSON.optString("customer_phone"));
                        Utility.writeToSharedPreference(mContext, GlobalValues.POSTALCODE, resultJSON.optString("customer_postal_code"));


                        String customer_pasta_stripe_id_dev = "";
                        if (resultJSON.has("customer_stripe_id_dev")) {
                            customer_pasta_stripe_id_dev = resultJSON.getString("customer_stripe_id_dev");
                        }
/*
                        String customer_porto_stripe_id_dev = "";
                        if (resultJSON.has("customer_porto_stripe_id_dev")) {
                            customer_porto_stripe_id_dev = resultJSON.getString("customer_porto_stripe_id_dev");
                        } else {

                        }

                        String customer_kraft_stripe_id_dev = "";
                        if (resultJSON.has("customer_kraft_stripe_id_dev")) {
                            customer_kraft_stripe_id_dev = resultJSON.getString("customer_kraft_stripe_id_dev");
                        } else {

                        }*/

                        //Live
                        String customer_pasta_stripe_id_live = "";
                        if (resultJSON.has("customer_stripe_id_live")) {

                            customer_pasta_stripe_id_live = resultJSON.getString("customer_stripe_id_live");
                        }
                      /*  String customer_porto_stripe_id_live = "";
                        if (resultJSON.has("customer_porto_stripe_id_live")) {
                            customer_porto_stripe_id_live = resultJSON.getString("customer_porto_stripe_id_live");
                        } else {

                        }
                        String customer_kraft_stripe_id_live = "";
                        if (resultJSON.has("customer_kraft_stripe_id_live")) {
                            customer_kraft_stripe_id_live = resultJSON.getString("customer_kraft_stripe_id_live");

                        } else {
                        }*/


                        accountDetail.setCustomer_id(customer_id);
                        accountDetail.setCustomer_company_id(customer_company_id);
                        accountDetail.setCustomer_first_name(customer_first_name);
                        accountDetail.setCustomer_last_name(customer_last_name);
                        accountDetail.setCustomer_email(customer_email);
                        accountDetail.setCustomer_phone(customer_phone);
                        accountDetail.setCustomer_birthdate(customer_birthdate);
                        accountDetail.setCustomer_company_name(customer_company_name);
                        accountDetail.setCustomer_address_name(customer_address_name);
                        accountDetail.setCustomer_address_name2(customer_address_name2);
                        accountDetail.setCustomer_address_line1(customer_address_line1);
                        accountDetail.setCustomer_address_line2(customer_address_line2);
                        accountDetail.setCustomer_city(customer_city);
                        accountDetail.setCustomer_state(customer_state);
                        accountDetail.setCustomer_country(customer_country);
                        accountDetail.setCustomer_postal_code(customer_postal_code);
                        accountDetail.setCustomer_photo(customer_photo);
                        accountDetail.setCustomer_notes(customer_notes);
                        accountDetail.setCustomer_status(customer_status);
                        accountDetail.setCustomer_company_address(customer_company_address);
                        accountDetail.setCustomer_company_phone(customer_company_phone);
                        accountDetail.setCustomer_reward_point(customer_reward_point);
                        accountDetail.setClient_reward_point(client_reward_point);

                        accountDetail.setIsFbLogin(isFbLogin);

                        accountDetail.setCustomer_nick_name(customer_nick_name);
                        accountDetail.setCustomer_gender(customer_gender);
                        accountDetail.setCustomer_hobby(customer_hobby);
                        accountDetail.setCustomer_created_on(customer_created_on);
                        accountDetail.setCustomer_membership_type(customer_membership_type);
                        accountDetail.setCustomer_type(customer_type);
                        accountDetail.setCustomer_unique_id(customer_unique_id);


                        accountDetail.setcustomer_pasta_stripe_id_dev(customer_pasta_stripe_id_dev);
                  /*      accountDetail.setcustomer_porto_stripe_id_dev(customer_porto_stripe_id_dev);
                        accountDetail.setcustomer_kraft_stripe_id_dev(customer_kraft_stripe_id_dev);*/

                        accountDetail.setCustomer_pasta_stripe_id_live(customer_pasta_stripe_id_live);
                       /* accountDetail.setCustomer_porto_stripe_id_live(customer_porto_stripe_id_live);
                        accountDetail.setCustomer_kraft_stripe_id_live(customer_kraft_stripe_id_live);
*/

                        accountDetail.setCustomer_nick_name(customer_nick_name);

                        GlobalValues.ACCOUNT_DETAIL = accountDetail;


                        //storing new details in shared preference
                        SharedPreferences loginSharedPref = mContext.getSharedPreferences(
                                "Login_Credentials", Context.MODE_PRIVATE);

                        SharedPreferences.Editor editor = loginSharedPref.edit();
                        editor.putString("LoginDetails", responseJSONObject.toString());
                        editor.commit();

                        if (from.equals("Checkout_Activity")) {

                            /*onBackPressed();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();*/

                        } else if (from.equals("Order_Process")) {
                            /*onBackPressed();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();*/

                        } else if (from.equals("Order_Reservation")) {

                            /*onBackPressed();
                            overridePendingTransition(R.anim.enter, R.anim.exit);
                            finish();*/

                            /*Intent intent = new Intent(mContext, ReservationActivity.class);
                            startActivity(intent);*/


                        } else {

                            //dashboard

                            updateUserDetails();


                        }


                    } else {

                        message = responseJSONObject.getString("form_error");


                        String data = "";

                        if (message.length() > 2) {

                            data = message.substring(0, message.length() - 1);


                        } else {

                            data = message;
                        }

                        Toast.makeText(mContext, data.replace("<p>", "").replace("</p>", ""), Toast.LENGTH_LONG).show();


                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {


                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_LONG).show();

            }


        }
    }

    public void updateUserDetails() {

      /*
//TODO
        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_photo() != null
                && GlobalValues.ACCOUNT_DETAIL.getCustomer_photo().length() > 0) {
            Picasso.with(mContext)
                    .load(CommonImageurl + GlobalValues.ACCOUNT_DETAIL.getCustomer_photo()).
                    error(R.drawable.icon_myprofile).into(imageView_profileview);

        } else {

            Picasso.with(mContext)
                    .load(R.drawable.icon_myprofile).
                    placeholder(R.drawable.icon_myprofile).
                    into(imageView_profileview);

        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name().equals("")
                && !GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name().equals("null")) {

            user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name() + "!");

        } else {

            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name().equals("null")) {


                if (GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name().equals("null")) {
                    user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + " " + GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name() + "!");
                    // firstname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + " " + GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name());
                } else {

                    user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + "!");

                }
            } else {
                user_textview.setText("Hello,");
            }

        }
*/
        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name().equals("null")) {
            //firstname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + " " + GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name());
            firstname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name());
        }

        if(GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name().equals("null")){
            lastname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name());
        }

       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name().equals("null")) {
            lastname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name());

            lastname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_last_name());

        }*/

                            /*if (GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name().equals("null")) {
                                user_textview.setText("Hello, " + GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name() + "!");
                                contactNameTextView.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_first_name());
                            } else {
                                user_textview.setText("Hello,");
                                contactNameTextView.setVisibility(View.GONE);
                            }*/

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_email() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_email().equals("null")) {
            emailaddress_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_email());
        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_phone() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_phone().equals("null")) {
            mobilenumber_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_phone());
        }

       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name().equals("null")) {

            preferredname_editview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_nick_name());
        }*/

       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_line1() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_line1().equals("null"))
            addressLineOneEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_line1());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_postal_code() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_postal_code().equals("null"))
            postalCodeEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_postal_code());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name().equals("null"))
            unitNumEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name().equals("null"))
            unitNumEditText.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name());

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name2() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name2().equals("null"))
            unitNumEditTextTwo.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_address_name2());*/


       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_created_on() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_created_on().equals("null")) {

            try {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cal.setTime(sdf.parse(GlobalValues.ACCOUNT_DETAIL.getCustomer_created_on()));// all done

                customerCreatedDate = cal.getTime();


                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
                createdOnTextView.setText(simpleDateFormat.format(customerCreatedDate));
            } catch (Exception e) {
                e.printStackTrace();
            }

        }*/


        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate().equals("0000-00-00")) {

            try {
                Calendar cal = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                cal.setTime(sdf.parse(GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate()));// all done

                dobDate = cal.getTime();

            } catch (Exception e) {
                e.printStackTrace();
            }


            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

            if (dobDate != null && !dobDate.equals("0000-00-00")) {
                birthday_editview.setText(simpleDateFormat.format(dobDate));
                birthday_editview.setEnabled(false);
                birthday_text.setVisibility(View.GONE);
            } else {
                birthday_editview.setText("");
                birthday_text.setVisibility(View.VISIBLE);
            }

            if(!(birthday_editview.getText().toString().trim().equalsIgnoreCase(""))){
                //birthday_editview.setEnabled(false);
             }
            //    birthDayEditText.setText( new StringBuilder(GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate()).reverse().t  );

        }else{
            birthday_text.setVisibility(View.VISIBLE);
        }


      /*  if (GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point() != null &&
                !GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point().equals("")) {
            try {

                redeemed_textview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point());

            } catch (Exception e) {
                e.printStackTrace();
                redeemed_textview.setText("0");
            }

        } else {
            redeemed_textview.setText("0");
        }*/


        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_gender() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_gender().equals("null")) {

            String gender = GlobalValues.ACCOUNT_DETAIL.getCustomer_gender();
            if (gender.equals("M")) {
                gender_editview.setText(genders[0]);
            } else if (gender.equals("F")) {
                gender_editview.setText(genders[1]);
            } else if (gender.equals("O")) {
                gender_editview.setText(genders[2]);
            }
        }

        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_birthdate().equals("0000-00-00")
                && GlobalValues.ACCOUNT_DETAIL.getCustomer_gender() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_gender().equals("null")) {
            txt_completePfrl.setVisibility(View.GONE);
            birthday_text.setVisibility(View.GONE);
        } else {
    //        txt_completePfrl.setVisibility(View.VISIBLE);
            txt_completePfrl.setVisibility(View.GONE);
            birthday_text.setVisibility(View.VISIBLE);
        }

        //Staff
       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_type() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_type().equals("null")) {
            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_type().equals("Staff")) {
                staffTextView.setVisibility(View.VISIBLE);
            } else {
                staffTextView.setVisibility(View.GONE);
            }
        }


        //Kakis
        if (GlobalValues.ACCOUNT_DETAIL.getCustomer_membership_type() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_membership_type().equals("null")) {
            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_membership_type().equals("Kakis")) {
                kakiLayout.setVisibility(View.VISIBLE);
            } else {
                kakiLayout.setVisibility(View.GONE);

            }
        }


        try {
            // layoutMenmbershipId.setVisibility(View.VISIBLE);
            memberIdTextView.setVisibility(View.VISIBLE);
            if (GlobalValues.ACCOUNT_DETAIL.getCustomer_unique_id() != null && !GlobalValues.ACCOUNT_DETAIL.getCustomer_unique_id().equals("null")) {
                String memberId = GlobalValues.ACCOUNT_DETAIL.getCustomer_unique_id();

                //  layoutMenmbershipId.setVisibility(View.VISIBLE);
                memberIdTextView.setText(memberId);

            } else {
                // layoutMenmbershipId.setVisibility(View.VISIBLE);
                memberIdTextView.setText("N/A");

            }

        } catch (Exception e) {
            e.printStackTrace();
            memberIdTextView.setText("null");
        }*/


       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_hobby() != null) {
            setHobbies(GlobalValues.ACCOUNT_DETAIL.getCustomer_hobby());
        } else {
            unselectAll();
        }*/


       /* if (GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point() != null &&
                !GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point().equals("")) {
            try {

                redeemed_textview.setText(GlobalValues.ACCOUNT_DETAIL.getCustomer_reward_point());

            } catch (Exception e) {
                e.printStackTrace();
                redeemed_textview.setText("0");
            }

        } else {
            redeemed_textview.setText("0");
        }*/

    }

    private void getSecondaryAddress() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
        }

        String url = GlobalUrl.OTHER_ADDRESS_URL + "?app_id=" + GlobalValues.APP_ID + "&status=A" + "&refrence=" + mCustomerId;

        new OtherAddressTask().execute(url);
    }

    private class OtherAddressTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

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


            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    // layoutOtherAddress.setVisibility(View.VISIBLE);

                    secondaryAddressList = new ArrayList<>();
                    // layoutOtherAddress.setVisibility(View.VISIBLE);
                    JSONArray jsonArray = jsonObject.getJSONArray("result_set");

                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject resultObj = jsonArray.getJSONObject(i);
                        SecondaryAddress secondaryAddress = new SecondaryAddress();

                        secondaryAddress.setSecondary_address_id(resultObj.getString("secondary_address_id"));
                        secondaryAddress.setAddress(resultObj.getString("address"));
                        secondaryAddress.setPostal_code(resultObj.getString("postal_code"));
                        secondaryAddressList.add(secondaryAddress);
                    }

                    otherAddressRecyclerAdapter = new OtherAddressRecyclerAdapter(mContext, secondaryAddressList);
                    recyclerviewOtherAddress.setAdapter(otherAddressRecyclerAdapter);
                    recyclerviewOtherAddress.setNestedScrollingEnabled(false);
                    otherAddressRecyclerAdapter.notifyAdapter();

                    if (secondaryAddressList.size() > 0) {
                        shadow_layout.setVisibility(View.GONE);
                    } else {
                        shadow_layout.setVisibility(View.GONE);
                    }

                    otherAddressRecyclerAdapter.setOnItemClick(new IOnItemClick() {
                        @Override
                        public void onItemClick(View v, int position) {

                            if (Utility.networkCheck(mContext)) {

                                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null) {

                                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                                    String url = GlobalUrl.DELETE_SECONDARY_ADDRESS_URL;
                                    Map<String, String> params = new HashMap<String, String>();
                                    params.put("app_id", GlobalValues.APP_ID);
                                    params.put("refrence", mCustomerId);
                                    params.put("secondary_address_id", secondaryAddressList.get(position).getSecondary_address_id());
                                    //new DeleteAddressTask(params).execute(url);
                                }
                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                } else {
                    //layoutOtherAddress.setVisibility(View.GONE);
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }

    private class GetAddressTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;

        EditText addressEditText;
        android.app.AlertDialog alertDialog;

        GetAddressTask(EditText addressEditText, android.app.AlertDialog alertDialog) {
            this.addressEditText = addressEditText;
            this.alertDialog = alertDialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                String app_id = "?app_id=" + GlobalValues.APP_ID;
                String zipCode = "&zip_code=" + strings[0];

                String url = GlobalUrl.GETADDRESS + app_id + zipCode;

                response = WebserviceAssessor.GetRequest(url);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {



                    responseJSONObject = new JSONObject(response);

                    status = responseJSONObject.getString("status");

                   /* JSONObject jsonObjectCommonImgurl = responseJSONObject.getJSONObject("common");

                    CommonImageurl = jsonObjectCommonImgurl.optString("image_source");*/


                    if (status.equals("ok")) {    //Success

                        JSONObject resultJSONObject = responseJSONObject.getJSONObject("result_set");

                        zip_id = resultJSONObject.getString("zip_id");
                        zip_code = resultJSONObject.getString("zip_code");
                        zip_addtype = resultJSONObject.getString("zip_addtype");
                        zip_buno = resultJSONObject.getString("zip_buno");
                        zip_sname = resultJSONObject.getString("zip_sname");
                        zip_buname = resultJSONObject.getString("zip_buname");

                        //     postalAddressTextView.setText(zip_buno + ", " + zip_sname);


                        edt_address.setText(zip_buno + " " + zip_sname);


                    } else {

                        edt_address.setText("");

                      /*  message = responseJSONObject.getString("form_error");
                        Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();*/
                    }


                } catch (JSONException e) {
                    e.printStackTrace();
                }


            } else {



                //    Toast.makeText(getActivity(), "Please check your internet connection.", Toast.LENGTH_LONG).show();

            }
        }
    }

    private class AddAddressTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;
        Map<String, String> hashMap;

        AddAddressTask(Map<String, String> hashMap) {
            this.hashMap = hashMap;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {
            String response = "";
            try {

                response = WebserviceAssessor.postRequest(mContext, params[0], hashMap);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;

        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);
                    status = responseJSONObject.getString("status");

                    if (status.equals("ok")) {
                        Toast.makeText(mContext, responseJSONObject.getString("message"), Toast.LENGTH_LONG).show();
                        getSecondaryAddress();
                    } else {

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }

    public static String capitaliseFirstLetter(String name) {
        String username[] = name.split(" ");
        String custName = "";
        for (String un : username) {
            StringBuffer s = new StringBuffer();
            for (int i = 0; i < un.length(); i++){
                if(i == 0){
                    s.append(Character.toUpperCase(un.charAt(i)));
                }else{
                    s.append(Character.toLowerCase(un.charAt(i)));
                }
            }
            custName = custName.concat(s.toString() + " ");
        }

        return custName;
    }

    public void hideKeyboard() {
        View view = mContext.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    /**
     * Generates a new refferal code for client
     * if no referral code found.
     */
    private class GenerateReferralCode extends AsyncTask<String, String, String> {

        String response, status;
        ProgressDialog availibilityDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            availibilityDialog = new ProgressDialog(mContext, ProgressDialog.THEME_HOLO_LIGHT);
            availibilityDialog.setMessage("Loading...");
            availibilityDialog.setCancelable(false);
            availibilityDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            try {
                //Generate a referral code

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                    mReferenceId = "";
                } else {
                    mReferenceId = Utility.getReferenceId(mContext);
                    mCustomerId = "";
                }

                String generate_ref_code = GlobalUrl.GENERATE_REFERRAL_CODE + "?app_id=" + GlobalValues.APP_ID
                        + "&refrence=" + mCustomerId;



                response = WebserviceAssessor.GetRequest(generate_ref_code);

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            availibilityDialog.dismiss();
            if (response != null) {

                JSONObject responseJSONObject = null;

                try {
                    responseJSONObject = new JSONObject(response);
                    status = responseJSONObject.getString("status");

                    if (status.equals("ok")) {    //Success
                        new CustomerDetailsTask().execute();
                    } else { //error

                        Toast.makeText(mContext, responseJSONObject.optString("message") + "", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }

            } else {

            }
        }
    }


        class MemberRenewal extends AsyncTask<String, Void, String> {

            ProgressDialog progressDialog;
            private Map<String, String> mobilenumParams;
            EditText new_password;
            TextView forgot_otp_validate;
            EditText forgot_otp_verify;
            LinearLayout lin_otp_validate;
            EditText confirm_password;

            MemberRenewal(Map<String, String> params) {
                mobilenumParams = params;
            }

            @Override
            protected void onPreExecute() {

                super.onPreExecute();
                progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Please wait...");
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.setCancelable(false);
                progressDialog.show();
            }

            @Override
            protected String doInBackground(String... params) {

                Log.e("memberrewal_url::", params[0] + "\n" + mobilenumParams.toString());
                String response = WebserviceAssessor.postRequest(mContext, params[0], mobilenumParams);
                return response;
            }

            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);

                try {

                    Log.e("TAG", "memnerRenewal_response:" + s);
                    JSONObject jsonObject = new JSONObject(s);
                    if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                        Intent intent = new Intent(mContext, FiveMenuActivityNew.class);
                        intent.putExtra("position", 0);
                        intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                        startActivity(intent);

                    } else {

                        String message = jsonObject.getString("message");
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();

                    }

                } catch (Exception e) {
                    progressDialog.dismiss();
                    Toast.makeText(mContext, "Please try again later", Toast.LENGTH_SHORT).show();
                }

                progressDialog.dismiss();

            }
        }
}
