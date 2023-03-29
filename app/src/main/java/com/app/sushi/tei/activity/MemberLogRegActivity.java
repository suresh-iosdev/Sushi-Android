package com.app.sushi.tei.activity;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.DE_REGISTER;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.PURCHASE;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.REGISTER;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.StrictMode;
import android.os.SystemClock;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.ClearCartMessageDialog;
import com.app.sushi.tei.netsclicktest.Table53;
import com.app.sushi.tei.shadow.ShadowLinearLayout;
import com.facebook.CallbackManager;
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
import com.whiteelephant.monthpicker.MonthPickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.GeneralSecurityException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
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

public class MemberLogRegActivity extends AppCompatActivity {
    private Button member_login_view, member_reg_view;
    private TextView txt_loginLabel;
    private ConstraintLayout rl_overall_login_layout;
    private RelativeLayout rl_overall_register_layout_new;
    private LinearLayout login_bg, register_bg;
    private LinearLayout login_bg_white2, login_bg_white;
    private RelativeLayout rl_overall_register_layout;
    private LinearLayoutCompat lnrlayout_register_new;
    private Toolbar toolbar;
    private TextView txt_terms,txt_by_submit;
    private String emailAddress;
    private String mValidationMessage = "";
    private EditText edtMobileLogin, edtPinLogin, edt_mobile_reg, edt_otp;
    private Button txtSubmit;
    private String mobileNumber = "", mPassword = "", mMessage = "";
    private boolean isLoginValidation = false, isRegValid = false;
    private CallbackManager callbackManager;
    private Intent intent;
    private Activity activity;
    private LinearLayout toolbarBack;

    private Button member_login_reg;
    private TextView txt_resend, txt_otpSeconds, Register;
    private ShadowLinearLayout layout_reg;
    ConstraintLayout layout_login;
    LinearLayoutCompat layout_register;
    private LinearLayout lly_login, lly_registration, lly_registration_;
    private Button LoginView, registrationView, LoginView1, registrationView1;
    private EditText fname, lname, email, edt_pin, referral_code;
    private Button back, createAccountsView;
    RelativeLayout rl_overall_bg;
    private String mFirstName, mLastName, mPhoneNumber, mREmailAddress, mRPassword, mDob, refCode = "";
    private String mobile;
    private Boolean getOTP = false;
    private static int TIME_OUT = 4000;
    long timeInMilliseconds = 0L;
    long timeSwapBuff = 0L;
    long updatedTime = 0L;
    private long startTime = 0L;
    private Handler customHandler = new Handler();
    int initial = 60;
    int count = 0;
    private LinearLayout lly_SignupConformation;
    private CheckBox checkBoxs;
    private boolean isChecked = false;
    private TextView termsndconditions;
    private boolean disableLoginLater;
    private boolean isRegister;
    private boolean isFromSplash;

    Boolean dobSelected = false;
    int mYear, mMonth, mDay;
    EditText fname_new, email_new, edt_mobile_register_new, edt_pin_new, edt_pin_reenter_new, referral_code_new, otp_verify;
    TextView dateOfBirthNew, txt_reward,txt_reward_new, txt_communication, txt_checkBox_sms_new,
            txt_enter_phone_details, otp_validate, gender;
    CheckBox checkBox_terms, checkBox_email_new;
    Button Register_new, getotp, btnBack, register,member_renewal;
    private Dialog dialog;
    LinearLayout lin_otp;

    private String success_otp = "";
    private String forgot_success_otp = "";
    private String forget_member_id = "";
    public String[] genders = {"Male", "Female", "Unspecified"};
    private LinearLayout nets_pay_lyt,
            nets_pay_lyt_register, corporate_wallet_lyt, nets_pay_lyt_deregister, card_details;
    private ImageView credit_debit_check_box, shopee_pay_check_box, nets_pay_check_box, corporate_wallet_check_box;
    private TextView card_number, de_register, exp_date,back_to_login,back_to_login_1;
    public static final String NETS_PAYMENT = "NETS_PAY";
    BottomSheetDialog paymentpopup;
    private ProgressDialog progress;
    public static String BASE_URL = "https://ceres.ninjaos.com/";  //LIVE
    public static final String NETS_REGISTRATION = BASE_URL + "api/netsclickpay/tokenRegister";
 //   public static final String NETS_PURCHASE = BASE_URL + "api/netsclickpay/purchaseOrder";
    public static final String NETS_PURCHASE = BASE_URL + "api/netsclickpay/ascentisPurchaseOrder";

    private String trans_reference_number;
    String mCustomerId="",mReferenceId="";
    String membership_amt="68";
    String value_from="";
    private Context context, mContext;

    private EditText edtCardName_new;
    private EditText edtCardNumber_new;
    private TextView txt_expDate_new;
    private EditText edtCVVNumber_new;
    private Button btn_continue;
    private Button btn_continue_disabled;
    Calendar today;

    private long mLastClickTime = 0;
    private boolean onlineValidation = false;
    private String cardName = "", cardNumber = "", cvvNumber = "";
    Client client;
    WebAuthotizeTask webAuthotizeTask;
    private String mPrevCaptureId;
    private String mYear1 = "", mMonth1 = "", mCVV = "";
    Dialog dialogBottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_member_log_reg_new);
        context = this;
        mContext = this;
        today = Calendar.getInstance();
        String paymentKey = Utility.readFromSharedPreference(mContext, GlobalValues.PAYMENTKEY);
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
        try {
            client = new Client(paymentKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }
        view();

        if (getIntent().getExtras() != null) {
            if (getIntent().getStringExtra("from").equalsIgnoreCase("Member Sign Up")) {
                value_from=getIntent().getStringExtra("value");
                rl_overall_login_layout.setVisibility(View.GONE);
                rl_overall_register_layout_new.setVisibility(View.VISIBLE);
                txt_reward.setVisibility(View.GONE);
                txt_reward_new.setVisibility(View.VISIBLE);

                member_renewal.setVisibility(View.GONE);
                txt_loginLabel.setText("NEW MEMBERSHIP REGISTRATION FORM");

                member_login_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_new));
                member_login_view.setTextColor(ContextCompat.getColor(context, R.color.greenRegisterText));
                member_reg_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_fill_new));
                member_reg_view.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            } else {
                value_from=getIntent().getStringExtra("value");
                txt_loginLabel.setText("Member Sign In");
                rl_overall_login_layout.setVisibility(View.VISIBLE);
                rl_overall_register_layout_new.setVisibility(View.GONE);
                txt_reward.setVisibility(View.VISIBLE);
                txt_reward_new.setVisibility(View.GONE);
                member_renewal.setVisibility(View.GONE);

                member_reg_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_new));
                member_reg_view.setTextColor(ContextCompat.getColor(context, R.color.greenRegisterText));
                member_login_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_fill_new));
                member_login_view.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));
            }
        }

    }

    private void view() {
        member_reg_view = (Button) findViewById(R.id.member_reg_view);
        member_login_view = (Button) findViewById(R.id.member_login_view);
        txt_loginLabel = (TextView) findViewById(R.id.txt_loginLabel);
        rl_overall_login_layout = findViewById(R.id.rl_overall_login_layout);
        rl_overall_register_layout_new = (RelativeLayout) findViewById(R.id.rl_overall_register_layout_new);
        lin_otp = findViewById(R.id.lin_otp);
        otp_validate = findViewById(R.id.otp_validate);
        otp_verify = findViewById(R.id.otp_verify);
        gender = findViewById(R.id.gender);
        dateOfBirthNew = findViewById(R.id.dateOfBirthNew);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbarBack = findViewById(R.id.toolbarBack);
        toolbarBack.setVisibility(View.GONE);
        txt_loginLabel = findViewById(R.id.txt_loginLabel);
        member_login_reg = findViewById(R.id.member_login_reg);
        txt_terms=findViewById(R.id.txt_terms);
        txt_by_submit=findViewById(R.id.txt_by_submit);

        lly_login = findViewById(R.id.lly_login);
        lly_registration = findViewById(R.id.lly_registration);
        LoginView = findViewById(R.id.LoginView);
        registrationView = findViewById(R.id.registrationView);
        LoginView1 = findViewById(R.id.LoginView1);
        registrationView1 = findViewById(R.id.registrationView1);
        layout_login = findViewById(R.id.layout_login);
        layout_register = findViewById(R.id.layout_register);
        lly_registration_ = findViewById(R.id.lly_registration_);
        layout_reg = findViewById(R.id.layout_reg);
        edt_mobile_reg = findViewById(R.id.edt_mobile_reg);
        edt_otp = findViewById(R.id.edt_otp);
        Register = findViewById(R.id.Register);
        txt_resend = findViewById(R.id.txt_resend);
        txt_otpSeconds = findViewById(R.id.txt_otpSeconds);
        callbackManager = CallbackManager.Factory.create();
//        openLoginDialog(mContext);
        edtMobileLogin = (EditText) findViewById(R.id.edt_mobile_login);
        edtPinLogin = (EditText) findViewById(R.id.edt_pin_login);
        txtSubmit = findViewById(R.id.Login);
        TextView layoutForgot = findViewById(R.id.forgotPassword);
        Button layoutCreateAccount = findViewById(R.id.createAccounts);

        back_to_login=findViewById(R.id.back_to_login);
        back_to_login_1=findViewById(R.id.back_to_login_1);

        fname = findViewById(R.id.fname);
        lname = findViewById(R.id.lname);
        email = findViewById(R.id.email);
        edt_pin = findViewById(R.id.edt_pin);
        referral_code = findViewById(R.id.referral_code);
        back = findViewById(R.id.back);
        rl_overall_bg = findViewById(R.id.rl_overall_bg);
        createAccountsView = findViewById(R.id.createAccountsView);
        lly_SignupConformation = findViewById(R.id.lly_SignupConformation);
        checkBoxs = findViewById(R.id.checkBoxs);
        termsndconditions = findViewById(R.id.termsandconditions);

        lnrlayout_register_new = findViewById(R.id.lnrlayout_register_new);
        rl_overall_register_layout_new = findViewById(R.id.rl_overall_register_layout_new);
        rl_overall_register_layout = findViewById(R.id.rl_overall_register_layout);
        rl_overall_login_layout = findViewById(R.id.rl_overall_login_layout);
        edt_mobile_register_new = findViewById(R.id.edt_mobile_register_new);
        fname_new = findViewById(R.id.fname_new);
        email_new = findViewById(R.id.email_new);
        edt_pin_new = findViewById(R.id.edt_pin_new);
        edt_pin_reenter_new = findViewById(R.id.edt_pin_reenter_new);
        txt_reward = findViewById(R.id.txt_reward);
        txt_reward_new = findViewById(R.id.txt_reward_new);
        member_renewal = findViewById(R.id.member_renewal);
        checkBox_terms = findViewById(R.id.checkBox_terms);
        txt_communication = findViewById(R.id.txt_communication);
        txt_checkBox_sms_new = findViewById(R.id.txt_checkBox_sms_new);
        checkBox_email_new = findViewById(R.id.checkBox_email_new);
        referral_code_new = findViewById(R.id.referral_code_new);
        Register_new = findViewById(R.id.Register_new);
        register = findViewById(R.id.register);
        getotp = findViewById(R.id.getotp);
        btnBack = findViewById(R.id.back_btn);
        txt_enter_phone_details = findViewById(R.id.txt_enter_phone_details);

        login_bg = findViewById(R.id.login_bg);
        login_bg_white2 = findViewById(R.id.login_bg_white2);
        login_bg_white = findViewById(R.id.login_bg_white);
        register_bg = findViewById(R.id.register_bg);

        txt_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CmsActivity.class);
                intent.putExtra("TITLE", "Terms and Condition");
                intent.putExtra("SEARCH_KEY", "https://sushitei.com/terms-and-conditions/");
                startActivity(intent);
            }
        });
        txt_by_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, CmsActivity.class);
                intent.putExtra("TITLE", "Privacy policy");
                intent.putExtra("SEARCH_KEY", "https://sushitei.com/privacy-policy/");
                startActivity(intent);
            }
        });




        back_to_login_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                back_to_login.performClick();
            }
        });

        layoutForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               /* intent = new Intent(mContext, MemberForgotPasswordActivity.class);
                startActivity(intent);*/

                Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            //    dialog.setContentView(R.layout.dialog_forget_view);
                dialog.setContentView(R.layout.activity_forgot_password_member);
                dialog.show();
                EditText edtMobileNumber_new;

                edtMobileNumber_new =dialog.findViewById(R.id.edtMobileNumber_new);


               TextView txtSubmit_new =dialog.findViewById(R.id.txtSubmit_new);
               RelativeLayout layoutClose =dialog.findViewById(R.id.layoutClose);

                txtSubmit_new.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        emailAddress = edtMobileNumber_new.getText().toString();
                        if (validateEmail()) {
                            if (Utility.networkCheck(mContext)) {

                                Map<String, String> params = new HashMap<>();
//                        if (edtMobileNumber.getText().toString().trim().equalsIgnoreCase("")) {
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("email_address", emailAddress);
                                params.put("type", "mobile");
                                params.put("site_url", GlobalUrl.BASE_URL);
//                            params.put("verify_type", "forgot");
                                String url = GlobalUrl.MEMBER_FORGOT_PASSWORD_URL;
                                new ForgotPassword(params,dialog).execute(url);

                            } else {
                                Toast.makeText(mContext, "You are offline please check your internet connection", Toast.LENGTH_SHORT).show();
                            }

                        } else {

                            Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
                        }
                    }
                });

              /*  txt_resend.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utility.networkCheck(mContext)) {

                            String url = GlobalUrl.RESEND_OTP_URL;
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("app_id", GlobalValues.APP_ID);
//                    params.put("customer_mobile", mMobileNumber);
                            params.put("email_address", emailAddress);
                            params.put("verify_type", "forgot");


                            //params.put("site_url","");

                            new ResendOTP(params).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

                layoutClose.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });


              /*  img_exit.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                forgot_back_login.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });

                forget_getotp.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {

                        if (forgot_mobile_num.getText().toString().isEmpty()) {
                            Toast.makeText(mContext, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (Utility.networkCheck(mContext)) {
                            String url = GlobalUrl.MEMBER_MOBILE_VALIDATE_URL;
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("member_phone", forgot_mobile_num.getText().toString());
                            params.put("response_enc","yes");
                            params.put("memberphone_validate","yes");

                            new ForgetGetOtp(params,new_password,confirm_password, forgot_otp_validate
                                    ,forgot_otp_verify,lin_otp_validate).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

                forgot_otp_validate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if (!forgot_otp_verify.getText().toString().isEmpty()){
                            if (forgot_success_otp.equalsIgnoreCase(forgot_otp_verify.getText().toString())) {
                                forgot_otp_validate.setText("VALIDATED");
                            } else {
                                Toast.makeText(mContext, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(mContext, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                        }

                    }
                });

                btn_forgot.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Log.e("TAG","ValidateTest::"+forgot_otp_validate.getText().toString());
                        if (forgot_otp_validate.getText().toString().equalsIgnoreCase("VALIDATE")){
                            Toast.makeText(mContext, "Please verify OTP", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (new_password.getText().toString().isEmpty()) {
                            Toast.makeText(mContext, "Please enter password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (confirm_password.getText().toString().isEmpty()){
                            Toast.makeText(mContext, "Please enter confirm password", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        if (!confirm_password.getText().toString().
                                equalsIgnoreCase(new_password.getText().toString())){
                            Toast.makeText(mContext, "Please enter same password", Toast.LENGTH_SHORT).show();
                            return;
                        }

                        if (Utility.networkCheck(mContext)) {
                            String url = GlobalUrl.MEMBER_FORGOTPASS_URL;
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("member_id", forget_member_id);
                            params.put("member_password", new_password.getText().toString());
                            new ForgetSubmit(params,dialog).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });*/

            }
        });

        back_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,LoginActivity.class);
                intent.putExtra("isFromSplash", true);
                startActivity(intent);
            }
        });

        otp_validate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp_verify.getText().toString().isEmpty()){
                    Toast.makeText(mContext, "Please Enter OTP", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (success_otp.equalsIgnoreCase(otp_verify.getText().toString())) {
                    otp_validate.setText("Validated");
                } else {
                    Toast.makeText(mContext, "Please Enter Valid OTP", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MemberBenefitActivity.class);
                startActivity(intent);
            }
        });


        gender.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (email_new.getText().toString().isEmpty()) {
                    Toast.makeText(mContext, "Please enter email", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (value_from.equalsIgnoreCase("Splash")){
                   if (Utility.networkCheck(mContext)) {
                    String url = GlobalUrl.MEMBER_EMAIL_EXIST_URL;
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("app_id", GlobalValues.APP_ID);
                    params.put("type", "mobile");
                    params.put("customer_email", email_new.getText().toString());

                    new EmailAlreadyExist(params).execute(url);
                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
                }else {
                    final ArrayAdapter<String> spinner_genders = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, genders);

                    new AlertDialog.Builder(mContext)
                            .setTitle("Gender")
                            .setAdapter(spinner_genders, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    gender.setText(genders[which].toString());

                                }
                            }).create().show();
                }

            }
        });

        dateOfBirthNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(context, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
                    public void onDateSet(DatePicker datepicker, int selectedyear, int selectedmonth, int selectedday) {
                        Calendar myCalendar = Calendar.getInstance();
                        myCalendar.set(Calendar.YEAR, selectedyear);
                        myCalendar.set(Calendar.MONTH, selectedmonth);
                        myCalendar.set(Calendar.DAY_OF_MONTH, selectedday);
                        String myFormat = "dd-MM-yyyy"; //Change as you need
                        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
                        dateOfBirthNew.setText(sdf.format(myCalendar.getTime()));

                        mDay = selectedday;
                        mMonth = selectedmonth;
                        mYear = selectedyear;
                        dobSelected = true;
                    }
                }, mYear, mMonth, mDay);

                //mDatePicker.setTitle("Select date");
                mDatePicker.show();
            }
        });

        member_renewal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.layout_inprogress);
                dialog.show();

                RelativeLayout layoutClose=dialog.findViewById(R.id.layoutClose);
                Button btn_okay=dialog.findViewById(R.id.btn_okay);

                btn_okay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
            }
        });


        member_login_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_loginLabel.setText("Member Sign In");
                rl_overall_login_layout.setVisibility(View.VISIBLE);
                rl_overall_register_layout_new.setVisibility(View.GONE);

                txt_reward.setVisibility(View.VISIBLE);
                txt_reward_new.setVisibility(View.GONE);
                member_renewal.setVisibility(View.GONE);

                member_reg_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_new));
                member_reg_view.setTextColor(ContextCompat.getColor(context, R.color.greenRegisterText));
                member_login_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_fill_new));
                member_login_view.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));


                login_bg.setVisibility(View.VISIBLE);
                login_bg_white2.setVisibility(View.VISIBLE);
                login_bg_white.setVisibility(View.VISIBLE);
                register_bg.setVisibility(View.GONE);

                rl_overall_login_layout.setVisibility(View.VISIBLE);
                rl_overall_register_layout.setVisibility(View.GONE);
                rl_overall_register_layout_new.setVisibility(View.GONE);

                lnrlayout_register_new.setVisibility(View.GONE);


            }
        });

        member_reg_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_loginLabel.setText("NEW MEMBERSHIP REGISTRATION FORM");
                rl_overall_login_layout.setVisibility(View.GONE);
                rl_overall_register_layout_new.setVisibility(View.VISIBLE);
                txt_reward.setVisibility(View.GONE);
                txt_reward_new.setVisibility(View.VISIBLE);
                member_renewal.setVisibility(View.GONE);

                member_login_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_new));
                member_login_view.setTextColor(ContextCompat.getColor(context, R.color.greenRegisterText));
                member_reg_view.setBackground(ContextCompat.getDrawable(context, R.drawable.login_register_bg_fill_new));
                member_reg_view.setTextColor(ContextCompat.getColor(context, R.color.colorWhite));


                login_bg.setVisibility(View.GONE);
                login_bg_white2.setVisibility(View.GONE);
                login_bg_white.setVisibility(View.GONE);
                register_bg.setVisibility(View.VISIBLE);


                rl_overall_login_layout.setVisibility(View.GONE);
                rl_overall_register_layout.setVisibility(View.GONE);
                rl_overall_register_layout_new.setVisibility(View.VISIBLE);

                lnrlayout_register_new.setVisibility(View.GONE);


            }
        });

        getotp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mPhoneNumber = edt_mobile_register_new.getText().toString().trim();
                if (mPhoneNumber.isEmpty()) {
                    Toast.makeText(mContext, "Please enter mobile number", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (value_from.equalsIgnoreCase("Splash")){
                    if (Utility.networkCheck(mContext)) {
                        String url = GlobalUrl.MEMBER_MOBILE_EXIST_URL;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("type", "mobile");
                        params.put("customer_phone", mPhoneNumber);
                        new MobileAlreadyExist(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    if (Utility.networkCheck(mContext)) {
                        String url = GlobalUrl.MEMBER_MOBILE_VALIDATE_URL;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("member_phone", mPhoneNumber);

                        new GetOtp(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }



            }
        });
        register.setOnClickListener(new View.OnClickListener() {
            //     Register_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (otp_validate.getText().toString().equalsIgnoreCase("VALIDATE")){
                    Toast.makeText(mContext, "Please validate OTP", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!checkBox_terms.isChecked()){
                    Toast.makeText(mContext, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (Utility.readFromSharedPreference(context,GlobalValues.PaymentSuccess).equalsIgnoreCase("Yes")){

                    if (registrationNewValidation()) {

                    mFirstName = fname_new.getText().toString().trim();
                    mPhoneNumber = edt_mobile_register_new.getText().toString().trim();
                    mREmailAddress = email_new.getText().toString().trim();
                    mRPassword = edt_pin_new.getText().toString().trim();
                    mDob = dateOfBirthNew.getText().toString().trim();
                    refCode = referral_code_new.getText().toString(); //referral code

                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.MEMBER_REGISTRATION_URL;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("register_type", "Mobile");
                        params.put("member_name", mFirstName);
                        params.put("member_email", mREmailAddress);
                        params.put("member_phone", mPhoneNumber);
                        params.put("member_password", mRPassword);


                        if(gender.getText().toString().equalsIgnoreCase("Male")){
                            params.put("member_gender", "M");
                        }else if (gender.getText().toString().equalsIgnoreCase("Female")){
                            params.put("member_gender", "F");
                        }else {
                            params.put("member_gender", "");
                        }
                        params.put("member_referrer_code", refCode); //referral code
                        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));


                        Log.e("Member_register_params:", String.valueOf(params));
                        //params.put("site_url","");

                        new RegistrationNewTask(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(context, mMessage, Toast.LENGTH_SHORT).show();
                }

                }

                else {
                    String message = "Please pay membership payment";
                    new CheckOutMessageDialog(mContext, message, new CheckOutMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                Payment();
                            }
                        }
                    });
                }


            }
        });

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    InputMethodManager imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                } catch (Exception e) {
                    // TODO: handle exception
                }
                mobileNumber = edtMobileLogin.getText().toString().trim();
                mPassword = edtPinLogin.getText().toString().trim();

                if (loginValidation()) {
                    if (!Patterns.PHONE.matcher(mobileNumber).matches()) {
                        Toast.makeText(mContext, "Please enter valid mobile number", Toast.LENGTH_SHORT).show();
                    } else {
                        if (Utility.networkCheck(mContext)) {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("register_type", "Mobile");
                            params.put("member_phone", mobileNumber);
                            params.put("member_password", mPassword);
               //             params.put("customer_id", GlobalValues.MEMBER_CUSTOMER_ID);

                            new LoginTask(params).execute(GlobalUrl.MEMBER_LOGIN_URL);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean validateEmail() {

        boolean isValidate = false;

        if (emailAddress.length() <= 0) {
            isValidate = false;
            mValidationMessage = "Please enter valid email";

        } else if ((!Patterns.EMAIL_ADDRESS.matcher(emailAddress).matches())) {
            isValidate = false;
            mValidationMessage = "Please enter valid email";
        } else {
            isValidate = true;
        }

        return isValidate;


    }

    private class LoginTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> loginParams;

        public LoginTask(Map<String, String> loginParams) {
            this.loginParams = loginParams;
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

            Log.e("Member_Login_url::",params[0] + "\t" + loginParams.toString());

            String response = WebserviceAssessor.postRequest(mContext, params[0], loginParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {
                Log.e("TAG","member_login_response::"+ s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    JSONObject jsonResultset = jsonObject.getJSONObject("result_set");

                    GlobalValues.ACCESS_TOKEN=jsonResultset.getString("access_token");
                    Utility.writeToSharedPreference(mContext,GlobalValues.ACCESS_TOKEN_KEY,jsonResultset.getString("access_token"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMERID, jsonResultset.optString("customer_id"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.FIRSTNAME, jsonResultset.optString("customer_first_name"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.LASTNAME, jsonResultset.optString("customer_last_name"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.EMAIL, jsonResultset.optString("customer_email"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.CUSTOMERPHONE, jsonResultset.optString("customer_phone"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.POSTALCODE, jsonResultset.optString("customer_postal_code"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.FB_LOGIN, "0");

                    Utility.writeToSharedPreference(mContext, GlobalValues.MEMBERSHIP_ID, jsonResultset.optString("ascentis_memberid"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.ASCENTIS_CARD_NO, jsonResultset.optString("ascentis_card_no"));


                    GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("app_id", GlobalValues.APP_ID);
                    mapData.put("reference_id", Utility.getReferenceId(mContext));
                    mapData.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                    mapData.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                    String url = GlobalUrl.UPDATE_USER_INFO_URL;
                    progressDialog.dismiss();
                    //TODO
    //                getActiveCount();
                    {//real
                        intent = new Intent(mContext, ChooseOutletActivity.class);
                        intent.putExtra("fromLogin",true);
                        startActivity(intent);
                        finish();
                    }//test
//                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                // finish();
            } finally {
                progressDialog.dismiss();
                //finish();
            }
        }
    }

    public boolean loginValidation() {

        if (mobileNumber.length() <= 0) {
            isLoginValidation = false;
            mMessage = "Please enter valid Mobile Number";
        } else if (mPassword.length() == 0) {
            isLoginValidation = false;
            mMessage = "Please enter valid PIN";
        }/* else if (mPassword.length() < 6) {
            isLoginValidation = false;
            mMessage = "Please enter 6-digit PIN";
        } */else if (!Patterns.PHONE.matcher(mobileNumber).matches()) {
            isLoginValidation = false;
            mMessage = "Please enter valid mobile number";

        } else {
            isLoginValidation = true;
        }

        return isLoginValidation;
    }

    private boolean emailValidation(String Email) {
        return (!TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches());
    }

    public boolean registrationNewValidation() {
        if (fname_new.getText().toString().length() <= 0) {
            isRegValid = false;
            mMessage = "Please enter Name";
        } else if (email_new.getText().toString().length() <= 0) {
            isRegValid = false;
            mMessage = "Please enter E-mail";
        } else if (!emailValidation(email_new.getText().toString())) {
            isRegValid = false;
            mMessage = "Please enter a valid E-mail";
        } else if (edt_mobile_register_new.getText().toString().isEmpty()) {
            isRegValid = false;
            mMessage = "Please enter Mobile Number";
        } else if (edt_pin_new.getText().toString().isEmpty()) {
            isRegValid = false;
            mMessage = "Please enter PIN";
        } else if (edt_pin_reenter_new.getText().toString().isEmpty()) {
            isRegValid = false;
            mMessage = "Please ReEnter PIN";
        } else if (!edt_pin_new.getText().toString().equals(edt_pin_reenter_new.getText().toString())) {
            isRegValid = false;
            mMessage = "Please enter same PIN";
        }/* else if (dateOfBirthNew.getText().toString().isEmpty()) {
            isRegValid = false;
            mMessage = "Please enter Date Of Birth";

        } else if (gender.getText().toString().isEmpty()) {
            isRegValid = false;
            mMessage = "Please select gender";

        }*/ else {
            isRegValid = true;
        }

        return isRegValid;
    }

    class RegistrationNewTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        private Map<String, String> registrationParams;

        RegistrationNewTask(Map<String, String> params) {
            registrationParams = params;
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

            Log.e("member_registration_url", params[0] + "\n" + registrationParams.toString());
            String response = WebserviceAssessor.postRequest(mContext, params[0], registrationParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG","member_Registration_response::"+ s);

                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    JSONObject regjson = jsonObject.getJSONObject("result_set");
                    GlobalValues.MEMBER_CUSTOMER_ID = regjson.getString("customer_id");
                    Utility.writeToSharedPreference(mContext, GlobalValues.MEMBERSHIP_ID, regjson.optString("ascentis_memberid"));
                    Utility.writeToSharedPreference(mContext, GlobalValues.ASCENTIS_CARD_NO, regjson.optString("ascentis_card_no"));
                    Utility.writeToSharedPreference(context,GlobalValues.PaymentSuccess,"No");
                    if (value_from.equalsIgnoreCase("Splash")) {
                        showLoginDialog();
                    }else {
                        intent = new Intent(context, FiveMenuActivityNew.class);
                        intent.putExtra("position", 0);
                        startActivity(intent);
                    }
                    clearRegistration();
                    updateUI();
                } else {

                    String message = jsonObject.getString("message");
                    String form_error = jsonObject.optString("form_error");

                    if (form_error != null || !form_error.equals("")) {
                        Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();

        }
    }

    class GetOtp extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        private Map<String, String> mobilenumParams;

        GetOtp(Map<String, String> params) {
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

            Log.e("registration_url::", params[0] + "\n" + mobilenumParams.toString());
            String response = WebserviceAssessor.postRequest(mContext, params[0], mobilenumParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG", "Mobile_validate_response:" + s);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    //             showLoginDialog();
                    lin_otp.setVisibility(View.VISIBLE);
                    JSONObject otpJson = jsonObject.getJSONObject("result_set");
                    success_otp = otpJson.getString("OTPcode");
                } else {
                    String message = jsonObject.getString("message");
                   /* String form_error = jsonObject.optString("form_error");
                    if (form_error != null || !form_error.equals("")) {
                        Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_SHORT).show();
                    } else {*/
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
               //     }
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();

        }
    }

    class ForgetGetOtp extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        private Map<String, String> mobilenumParams;
        EditText new_password;
        TextView forgot_otp_validate;
        EditText forgot_otp_verify;
        LinearLayout lin_otp_validate;
        EditText confirm_password;

        ForgetGetOtp(Map<String, String> params, EditText new_password, EditText confirm_password,
                     TextView forgotOtpValidate, EditText forgot_otp_verify, LinearLayout lin_otp_validate) {
            mobilenumParams = params;
            this.new_password=new_password;
            this.forgot_otp_validate=forgotOtpValidate;
            this.forgot_otp_verify=forgot_otp_verify;
            this.lin_otp_validate=lin_otp_validate;
            this.confirm_password=confirm_password;
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

            Log.e("forgot_pass_url::", params[0] + "\n" + mobilenumParams.toString());
            String response = WebserviceAssessor.postRequest(mContext, params[0], mobilenumParams);
            return response;
        }

        @RequiresApi(api = Build.VERSION_CODES.O)
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG", "otp_response:" + s);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    new_password.setVisibility(View.VISIBLE);
                    confirm_password.setVisibility(View.VISIBLE);
                    lin_otp_validate.setVisibility(View.VISIBLE);
                    JSONObject otpJson = jsonObject.getJSONObject("result_set");
                    forget_member_id = otpJson.getString("Memberid");

                    try {
                        String otp=otpJson.getString("RequestTimeEnc");
                        Base64.Decoder decoder = null;
                        String dStr="";
                        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
                            decoder = Base64.getDecoder();
                            dStr = new String(decoder.decode(otp));
                            forgot_success_otp = new String(decoder.decode(otp));
                        }else {
                            decoder = Base64.getDecoder();
                            forgot_success_otp = new String(decoder.decode(otp));
                        }

                        Log.e("TAG","Password_decryption::"+forgot_success_otp);
                    }catch (Exception e){
                        e.printStackTrace();
                    }


                } else {
                    new_password.setVisibility(View.GONE);
                    lin_otp_validate.setVisibility(View.GONE);
                    confirm_password.setVisibility(View.GONE);
                    try {
                        String message = jsonObject.getString("message");
                        String form_error = jsonObject.optString("form_error");
                        if (form_error != null || !form_error.equals("")) {
                            Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                      e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Please try again later", Toast.LENGTH_SHORT).show();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                new_password.setVisibility(View.GONE);
                confirm_password.setVisibility(View.GONE);
                lin_otp_validate.setVisibility(View.GONE);
                progressDialog.dismiss();
                Toast.makeText(mContext, "Please try again later", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();

        }
    }

    class ForgetSubmit extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        private Map<String, String> mobilenumParams;
        Dialog dialog;

        ForgetSubmit(Map<String, String> params, Dialog dialog) {
            mobilenumParams = params;
            this.dialog=dialog;
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

            Log.e("forgot_pass_url::", params[0] + "\n" + mobilenumParams.toString());
            String response = WebserviceAssessor.postRequest(mContext, params[0], mobilenumParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG", "otp_response:" + s);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext,"Password Succesfully Changed", Toast.LENGTH_SHORT).show();
                    dialog.dismiss();
                    JSONObject otpJson = jsonObject.getJSONObject("result_set");
           //         forget_member_id = otpJson.getString("Memberid");
                } else {
                    dialog.dismiss();
                    try {
                        String message = jsonObject.getString("message");
                        String form_error = jsonObject.optString("form_error");
                        if (form_error != null || !form_error.equals("")) {
                            Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Toast.makeText(mContext, "Please try again later", Toast.LENGTH_SHORT).show();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                Toast.makeText(mContext, "Please try again later", Toast.LENGTH_SHORT).show();
            }

            progressDialog.dismiss();

        }
    }


    class EmailAlreadyExist extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        private Map<String, String> emailParams;

        EmailAlreadyExist(Map<String, String> params) {
            emailParams = params;
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

            Log.e("TAG", "email_already_exist_url::" + params[0] + "\n" + emailParams.toString());
            String response = WebserviceAssessor.postRequest(mContext, params[0], emailParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG", "email_already_exist_response:" + s);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    //             showLoginDialog();
                    final ArrayAdapter<String> spinner_genders = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, genders);

                    new AlertDialog.Builder(mContext)
                            .setTitle("Gender")
                            .setAdapter(spinner_genders, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    gender.setText(genders[which].toString());

                                }
                            }).create().show();

                } else {
                    String message = jsonObject.getString("message");
                    String form_error = jsonObject.optString("form_error");
                    email_new.setText("");

                    if (form_error != null || !form_error.equals("")) {
                        Log.e("TAG", "error:" );
                        Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_LONG).show();
                    } else {
                        Log.e("TAG", "error_1" );
                        Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();
                    }

                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();

        }
    }

    class MobileAlreadyExist extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        private Map<String, String> mobilenumexistParams;

        MobileAlreadyExist(Map<String, String> params) {
            mobilenumexistParams = params;
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

            Log.e("TAG", "mobilenumexistParams_url::" + params[0] + "\n" + mobilenumexistParams.toString());
            String response = WebserviceAssessor.postRequest(mContext, params[0], mobilenumexistParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG", "mobilenumexistParams_response:" + s);
                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    if (Utility.networkCheck(mContext)) {
                        String url = GlobalUrl.MEMBER_MOBILE_VALIDATE_URL;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("member_phone", mPhoneNumber);

                        new GetOtp(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                    //             showLoginDialog();
                } else {
                    edt_mobile_register_new.setText("");
                    String message = jsonObject.getString("message");
                    String form_error = jsonObject.optString("form_error");
                    if (form_error != null || !form_error.equals("")) {
                        Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }
                }


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();

        }
    }


    public void showLoginDialog() {     //test

        dialog = new Dialog(context, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_continue_login_member);


        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(mContext, MemberLogRegActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intent = new Intent(mContext, MemberLogRegActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();

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

        if (Utility.readFromSharedPreference(mContext,GlobalValues.PAYMENTKEYENABLE).contains("OMISE")){
            rel_card.setVisibility(View.VISIBLE);
        }else {
            rel_card.setVisibility(View.GONE);
        }

            nets_pay_lyt = paymentpopup.findViewById(R.id.nets_pay_lyt);
            nets_pay_lyt_register = paymentpopup.findViewById(R.id.nets_pay_lyt_register);
            nets_pay_lyt_deregister = paymentpopup.findViewById(R.id.nets_pay_lyt_deregister);


            card_number = paymentpopup.findViewById(R.id.card_number);
            de_register = paymentpopup.findViewById(R.id.de_register);
            exp_date = paymentpopup.findViewById(R.id.exp_date);
            card_details = paymentpopup.findViewById(R.id.card_details);

            TextView layoutContinue = paymentpopup.findViewById(R.id.layoutContinue);
            ImageView img_close = paymentpopup.findViewById(R.id.img_close);
            Log.e("TAG","FrommyaccountTest::"+value_from);
            if (value_from.equalsIgnoreCase("MyAccount")){
                clearRegistration();
            }

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
                        mMonth1 = String.format("%02d", (selectedMonth + 1));
                        mYear1 = String.valueOf(selectedYear);

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
                                mMonth1 = month;
                                if (mMonth1.equalsIgnoreCase("")) {
                                    mMonth1 = "1";
                                }
                                if (mYear1.equalsIgnoreCase("")) {
                                    mYear1 = String.valueOf(today.get(Calendar.YEAR));
                                }
                                if (!(mYear1.equalsIgnoreCase("") || mMonth1.equalsIgnoreCase(""))) {
                                    txt_expDate_new.setText(String.valueOf(String.format("%02d", (Integer.parseInt(mMonth1))) + "/" + mYear1.substring(2, 4)));
                                }
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {

                                String exp_year = String.valueOf(selectedYear).substring(2, 4);
                                mYear1 = String.valueOf(selectedYear);
                                if (mMonth1.equalsIgnoreCase("")) {
                                    mMonth1 = "1";
                                }
                                if (mYear1.equalsIgnoreCase("")) {
                                    mYear1 = String.valueOf(today.get(Calendar.YEAR));
                                }
                                if (!(mYear1.equalsIgnoreCase("") || mMonth1.equalsIgnoreCase(""))) {
                                    txt_expDate_new.setText(String.valueOf(String.format("%02d", (Integer.parseInt(mMonth1))) + "/" + mYear1.substring(2, 4)));
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
            request.expirationMonth = Integer.parseInt(mMonth1);
            request.expirationYear = Integer.parseInt(mYear1);
            request.securityCode = cvvNumber;
            client.send(request, new TokenRequestListener() {
                @Override
                public void onTokenRequestSucceed(TokenRequest request, Token token) {


                    String customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                    FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);
//Todo surya
                    if (!Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).isEmpty()) {
                        formBuilder.add("customer_id", customerId);
                    }else {
                        if (value_from.equalsIgnoreCase("Splash")) {
                            formBuilder.add("memberRegister", "1");
                        }
                    }

                    formBuilder.add("paid_amount", "68.00");

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

                Gson gson = new Gson();
                String json = gson.toJson(formBody);
                Log.e("TAG","FormDataTest11::"+json);

                Log.e("TAG","WebauthTest::"+response);
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
                        if (!Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).isEmpty()) {
                            formBuilder.add("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                        }
                        //TODO surya
                        formBuilder.add("token", mPrevCaptureId);

                        formBuilder.add("log_id", result_set.getString("log_id"));
                        //formBuilder.add("paid_amount", loyality_point_price);

                        formBuilder.add("paid_amount", "68.00");

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
                    Log.e("TAG","PaymentSuccess::"+response);
                    if (status.equals("ok")) {
                        dialogBottom.dismiss();
                        Utility.writeToSharedPreference(context,GlobalValues.PaymentSuccess,"Yes");
                        register.performClick();

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                    Toast.makeText(context, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
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
        } else if ((mMonth1.length() <= 0) || (mYear1.length() <= 0)) {
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
                "raw", this.getPackageName());
    }

   /* private int getCaResource() {
        return this.getResources().getIdentifier("ca_uat",
                "raw", this.getPackageName());
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

    private void clearRegistration () {
        Utility.writeToSharedPreference(mContext,GlobalValues.NETS_CARD_NUMBER_NEW, "");
        Utility.writeToSharedPreference(mContext, GlobalValues.NETS_EXPIRY_NEW, "");
        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO, ""); //
        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER, ""); //
        Utility.writeToSharedPreference(mContext,GlobalValues.NETS_REGISTERED_NEW, "0");

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
        Debit debit = new Debit(formatAmountInCents(membership_amt.replace("$", "")));
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

    private void validatePurchase (String token, boolean is_pin_pad_request) {
        Map<String, String> params = new HashMap<>();


   //     params.put("muid", Utility.getReferenceId(mContext));
        params.put("muid", "0");
        params.put("customer_id", "0");
        params.put("mid", NETSServices.MID);
        params.put("t0205", token);
        params.put("amt", formatAmountInCents(membership_amt.replace("$", "")));
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
                            //                      Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO, object.optString("trans_reference_no")); //
                            //                      Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER, object.optString("trans_reference_no")); //
      //Hide 04_11_2022                      placeCashOnDeliveryOrder();
                            Utility.writeToSharedPreference(context,GlobalValues.PaymentSuccess,"Yes");
                            register.performClick();
                            paymentpopup.dismiss();
                        }else if(responseCode.equalsIgnoreCase("U9") || responseCode.equalsIgnoreCase("55")) { //invoke pin pad
                            trans_reference_number=object.optString("trans_reference_no");
                            //                     Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO, object.optString("trans_reference_no")); //
                            //                     Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER, object.optString("trans_reference_no")); //
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
                "CUSTOM : " +"Amount:"+formatAmountInCents(membership_amt.replace("$", ""))+"\n"+"ResponceCode"
                +responseCode+"\n"+"Crypto:"+table53.getData());
        Log.e("TAG","CryptoTest::"+table53);

        Debit debit = new Debit(formatAmountInCents(membership_amt), responseCode, table53.getData());
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
            showAlertDialogCard();
        } else {
            doRegistration();
        }
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
    public String formatCustomerId (String customer_id) {
        return customer_id;
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

    private void updateUI( ) {

        Log.e("TAG","RegisterpopupTest::"+isRegistrationSuccess());
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

    private class ForgotPassword extends AsyncTask<String, Void, String> {

        private Map<String, String> forgotParams;
        private ProgressDialog progressDialog;
        Dialog dialog;

        public ForgotPassword(Map<String, String> params, Dialog dialog) {
            forgotParams = params;
            this.dialog=dialog;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Sending email...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], forgotParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);

//                Toast.makeText(getApplicationContext(), jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(getApplicationContext(), jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//                    getOTP = true;
//                    edtOTP.setVisibility(View.VISIBLE);
//                    edtOTP.setVisibility(View.GONE);
//                    txt_otpSeconds.setVisibility(View.GONE);
//                    customHandler.postDelayed(updateTimerThread, 1000);
//                    Intent intent = new Intent(mContext, LoginActivity.class);
//                  intent.putExtra(FROM_KEY, FROM_RESET);
//                    startActivity(intent);
                dialog.dismiss();
                } else {

                    String message = jsonObject.getString("message");
                    String form_error = jsonObject.optString("form_error");
                    String messageCom = message + "" + Html.fromHtml(form_error);
                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                dialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }

        }
    }
}
