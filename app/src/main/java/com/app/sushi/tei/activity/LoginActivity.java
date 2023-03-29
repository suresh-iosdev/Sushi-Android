package com.app.sushi.tei.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.LinearLayoutCompat;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
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
import com.app.sushi.tei.Utils.Prefs;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.shadow.ShadowLinearLayout;
import com.facebook.CallbackManager;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static com.app.sushi.tei.activity.SubCategoryActivity.txtNotificationCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtOrderCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtPromotionCount;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtVoucherCount;

public class LoginActivity extends AppCompatActivity {

    private Context mContext;
    private EditText edtMobileLogin, edtPinLogin, edt_mobile_reg, edt_otp;
    private Button txtSubmit;
    private String mobileNumber = "", mPassword = "", mMessage = "";
    private boolean isLoginValidation = false, isRegValid = false;
    private CallbackManager callbackManager;
    private Intent intent;
    private Activity activity;
    private Toolbar toolbar;
    private LinearLayout toolbarBack;
    //    Button LoginasGuest;
    private TextView LoginLater, txt_loginLabel,txt_loginLabel_1;
    private Button member_login_reg,member_renewal;
    private TextView txt_resend, txt_otpSeconds, Register,text_or,txt_terms,txt_by_submit,back_txt;
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
    private LinearLayoutCompat lnrlayout_register_new;
    private RelativeLayout rl_overall_register_layout_new;
    private RelativeLayout rl_overall_register_layout;
    private ConstraintLayout rl_overall_login_layout;
    private LinearLayout login_bg, register_bg;
    private LinearLayout login_bg_white2, login_bg_white;


    Boolean dobSelected = false;
    int mYear, mMonth, mDay;
    EditText fname_new, email_new, edt_mobile_register_new, edt_pin_new, edt_pin_reenter_new, referral_code_new;
    TextView dateOfBirthNew, txt_reward, txt_communication, txt_checkBox_sms_new, txt_enter_phone_details;
    CheckBox checkBox_new, checkBox_email_new;
    Button Register_new, btnBack;
    private Dialog dialog;

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        if (isFromSplash) {
            intent = new Intent(mContext, SplashActivity.class);
            startActivity(intent);
            finish();
        } else {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_layout);
//        setContentView(R.layout.login_layout_new);
        mContext = LoginActivity.this;


        if (getIntent().getExtras() != null) {
            isRegister = getIntent().getBooleanExtra("isRegister", false);
            isFromSplash = getIntent().getBooleanExtra("isFromSplash", false);
            disableLoginLater = getIntent().getBooleanExtra("disableLoginLater", false);
        }

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbarBack = findViewById(R.id.toolbarBack);
        txt_loginLabel = findViewById(R.id.txt_loginLabel);
        txt_loginLabel_1 = findViewById(R.id.txt_loginLabel_1);
        member_login_reg=findViewById(R.id.member_login_reg);
        member_renewal=findViewById(R.id.member_renewal);
        text_or=findViewById(R.id.text_or);
        txt_terms=findViewById(R.id.txt_terms);
        txt_by_submit=findViewById(R.id.txt_by_submit);
        back_txt=findViewById(R.id.back_txt);
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
        LoginLater = findViewById(R.id.LoginLater);
//        LoginasGuest = findViewById(R.id.LoginasGuest);
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
        checkBox_new = findViewById(R.id.checkBox_new);
        txt_communication = findViewById(R.id.txt_communication);
        txt_checkBox_sms_new = findViewById(R.id.txt_checkBox_sms_new);
        checkBox_email_new = findViewById(R.id.checkBox_email_new);
        referral_code_new = findViewById(R.id.referral_code_new);
        Register_new = findViewById(R.id.Register_new);
        btnBack = findViewById(R.id.back_btn);
        txt_enter_phone_details = findViewById(R.id.txt_enter_phone_details);

        login_bg = findViewById(R.id.login_bg);
        login_bg_white2 = findViewById(R.id.login_bg_white2);
        login_bg_white = findViewById(R.id.login_bg_white);
        register_bg = findViewById(R.id.register_bg);

//        SpannableString content = new SpannableString("I'II log in later");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        LoginLater.setText(content);

        toolbarBack.setVisibility(View.GONE);
//        showLoginDialog();

        disableLoginLater = false;
        if (disableLoginLater) {
            LoginLater.setVisibility(View.GONE);
        }

        if (isRegister) {
            layout_reg.setVisibility(View.VISIBLE);
            layout_login.setVisibility(View.GONE);
            txtSubmit.setVisibility(View.GONE);
            LoginLater.setVisibility(View.GONE);
            lly_login.setVisibility(View.GONE);
            member_renewal.setVisibility(View.GONE);
            txt_loginLabel.setText("Register");
            txt_loginLabel_1.setText("Quick Checkout Account");
            member_login_reg.setText("ST Rewards Member Sign Up");
            text_or.setText("Or register for quick check out");
            lly_registration.setVisibility(View.VISIBLE);
            layout_register.setVisibility(View.GONE);
            Register.setVisibility(View.VISIBLE);
            lly_registration_.setVisibility(View.GONE);
            lly_SignupConformation.setVisibility(View.GONE);
            edtMobileLogin.setText("");
            edtPinLogin.setText("");
            fname.setText("");
            lname.setText("");
            email.setText("");
            edt_pin.setText("");

            login_bg.setVisibility(View.GONE);
            login_bg_white2.setVisibility(View.GONE);
            login_bg_white.setVisibility(View.GONE);
            register_bg.setVisibility(View.VISIBLE);

            rl_overall_login_layout.setVisibility(View.GONE);
            rl_overall_register_layout.setVisibility(View.GONE);
            rl_overall_register_layout_new.setVisibility(View.VISIBLE);
            lnrlayout_register_new.setVisibility(View.VISIBLE);

//            rl_overall_bg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_label));
        }

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

        member_login_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(mContext,MemberLogRegActivity.class);
                if (member_login_reg.getText().toString().equalsIgnoreCase("Member Sign Up")) {
                    intent.putExtra("from", "Member Sign Up");
                    intent.putExtra("value","Splash");
                }else {
                    intent.putExtra("from", "Member Sign In");
                    intent.putExtra("value","Splash");
                }
                startActivity(intent);
                finish();
            }
        });

        txt_reward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, MemberBenefitActivity.class);
                startActivity(intent);
            }
        });
        edtMobileLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtPinLogin.getText().toString().isEmpty() && charSequence.length() > 0) {
                    txtSubmit.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.checkout_paynow_background));
                } else {
                    txtSubmit.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.checkout_paynow_background_grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edtPinLogin.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (!edtMobileLogin.getText().toString().isEmpty() && charSequence.length() > 0) {
                    txtSubmit.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.checkout_paynow_background));
                } else {
                    txtSubmit.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.checkout_paynow_background_grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        edt_mobile_reg.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (/*!edtMobileLogin.getText().toString().isEmpty() &&*/ charSequence.length() > 0) {
                    Register.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.checkout_paynow_background));
                } else {
                    Register.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.checkout_paynow_background_grey));
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        /*toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });*/

        checkBoxs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkBoxs.isChecked()) {
                    isChecked = true;
                } else {
                    isChecked = false;
                }
            }
        });

        termsndconditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CmsActivity.class);
                intent.putExtra("TITLE", "Privacy Policy");
                intent.putExtra("SEARCH_KEY", "privacy-policy");
                startActivity(intent);

            }
        });

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

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mobile = edt_mobile_reg.getText().toString();

                if (!(mobile.length() > 0)) {
                    Toast.makeText(mContext, "Please enter valid Mobile no.", Toast.LENGTH_SHORT).show();
                    return;
                }

                if ((!Patterns.PHONE.matcher(mobile).matches()) && (mobile.length() < 7)) {
                    Toast.makeText(mContext, "Please enter valid Mobile no. ", Toast.LENGTH_SHORT).show();
                } else {
                    if (Utility.networkCheck(mContext)) {
                        Map<String, String> params = new HashMap<>();
                        if (!getOTP) {
                            if (edt_otp.getText().toString().equalsIgnoreCase("")) {
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("customer_phone", mobile);
                                params.put("verify_type", "register");
                                String url = GlobalUrl.SEND_OTP;
                                new SendOTP(params).execute(url);
                            } else {
                                Toast.makeText(mContext, "Get OTP and Continue", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            if (!(edt_otp.getText().toString().equalsIgnoreCase(""))) {
                                if (!(edt_otp.length() > 5)) {
                                    Toast.makeText(mContext, "Please enter pin number", Toast.LENGTH_SHORT).show();
                                    return;
                                }
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("customer_phone", mobile);
                                params.put("customer_otp_val", edt_otp.getText().toString());
                                params.put("verify_type", "register");
                                String url = GlobalUrl.OTP_VERIFICATION;
                                new VerifyOTP(params).execute(url);
                            } else {
                                Toast.makeText(mContext, "Enter Received OTP", Toast.LENGTH_SHORT).show();
                            }
                        }
                    } else {
                        Toast.makeText(mContext, "You are offline please check your internet connection", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });

        back_txt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        registrationView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                LoginView.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_register_bg_new));
                LoginView.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.greenRegisterText));
                registrationView.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_register_bg_fill_new));
                registrationView.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorWhite));

                login_bg.setVisibility(View.GONE);
                login_bg_white2.setVisibility(View.GONE);
                login_bg_white.setVisibility(View.GONE);
                register_bg.setVisibility(View.VISIBLE);


                rl_overall_login_layout.setVisibility(View.GONE);
                rl_overall_register_layout.setVisibility(View.GONE);
                rl_overall_register_layout_new.setVisibility(View.VISIBLE);
                lnrlayout_register_new.setVisibility(View.VISIBLE);
                member_renewal.setVisibility(View.GONE);

                txt_loginLabel.setText("Register");
                txt_loginLabel_1.setText("Quick Checkout Account");
                member_login_reg.setText("ST Rewards Member Sign Up");
                text_or.setText("Or sign in with quick check out");

//                txt_enter_phone_details.setText("Enter your phone number to get your one time pin via sms to login/singup an account");
                txt_enter_phone_details.setVisibility(View.GONE);

//                rl_overall_bg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_label));

            }
        });

        LoginView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                LoginView.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_register_bg_fill_new));
                LoginView.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.colorWhite));
                registrationView.setBackground(ContextCompat.getDrawable(LoginActivity.this, R.drawable.login_register_bg_new));
                registrationView.setTextColor(ContextCompat.getColor(LoginActivity.this, R.color.greenRegisterText));

                login_bg.setVisibility(View.VISIBLE);
                login_bg_white2.setVisibility(View.VISIBLE);
                login_bg_white.setVisibility(View.VISIBLE);
                register_bg.setVisibility(View.GONE);

                rl_overall_login_layout.setVisibility(View.VISIBLE);
                rl_overall_register_layout.setVisibility(View.GONE);
                rl_overall_register_layout_new.setVisibility(View.GONE);
                lnrlayout_register_new.setVisibility(View.GONE);
                member_renewal.setVisibility(View.GONE);

                member_login_reg.setText("ST Rewards Member Sign In");
                txt_loginLabel.setText("Login / Verify");
                txt_loginLabel_1.setText("Quick Checkout Account");
                text_or.setText("Or sign in with quick check out");
                txt_enter_phone_details.setVisibility(View.GONE);

//                rl_overall_bg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_label));

            }
        });

        LoginView1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lly_login.setVisibility(View.VISIBLE);
                lly_registration.setVisibility(View.GONE);
                layout_register.setVisibility(View.GONE);
                layout_login.setVisibility(View.VISIBLE);
                txtSubmit.setVisibility(View.VISIBLE);
                member_renewal.setVisibility(View.GONE);
   //             LoginLater.setVisibility(view.VISIBLE);
                member_login_reg.setText("ST Rewards Member Sign In");
                txt_loginLabel.setText("Login / Verify");
                txt_loginLabel_1.setText("Quick Checkout Account");
                text_or.setText("Or sign in with quick check out");
                lly_registration_.setVisibility(View.GONE);
                Register.setVisibility(View.GONE);
                layout_reg.setVisibility(View.GONE);
                lly_SignupConformation.setVisibility(View.GONE);
                edt_otp.setText("");
                //edt_mobile_reg.setText("");
                fname.setText("");
                lname.setText("");
                email.setText("");
                edt_pin.setText("");

                rl_overall_login_layout.setVisibility(View.VISIBLE);
                rl_overall_register_layout.setVisibility(View.GONE);
                rl_overall_register_layout_new.setVisibility(View.GONE);
                lnrlayout_register_new.setVisibility(View.GONE);
//                layout.setBackground(ContextCompat.getDrawable(context, R.drawable.ready));

                txt_enter_phone_details.setVisibility(View.GONE);

//                rl_overall_bg.setBackground(ContextCompat.getDrawable(mContext, R.drawable.bg_label));
            }
        });

        layoutCreateAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mContext, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        layoutForgot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                intent = new Intent(mContext, ForgotPasswordActivity.class);
                startActivity(intent);
            }
        });

        LoginLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (disableLoginLater) {
                    Toast.makeText(mContext, "Please Login to Continue", Toast.LENGTH_SHORT).show();
                } else {
                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    intent = new Intent(mContext, ChooseOutletActivity.class);
                    intent.putExtra("fromLogin",true);
                    startActivity(intent);
                    finish();


//                    Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
//                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//                    intent = new Intent(mContext, MenuCategoryActivity.class);
//                    startActivity(intent);
//                    finish();
                }
            }
        });
//        LoginasGuest.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                if (disableLoginLater) {
////                    Toast.makeText(mContext, "Please Login to Continue", Toast.LENGTH_SHORT).show();
////                } else {
//                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//                    intent = new Intent(mContext, ChooseOutletActivity.class);
//                    startActivity(intent);
//                    finish();
//
//
////                    Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
////                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
////                    intent = new Intent(mContext, MenuCategoryActivity.class);
////                    startActivity(intent);
////                    finish();
////                }
//            }
//        });

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
                            params.put("login_username", mobileNumber);
//                            params.put("customer_pin", mPassword);
                            params.put("login_password", mPassword);
                            params.put("logintype", "mobile");
                            params.put("passwordtype", "pin");

                            params.put("android_key", Utility.getDeviceId(mContext));
//                            params.put("type", "App");
                            params.put("type", "web");
                            //97775663
                            Log.e("TAG","LoginTest::"+params.toString());
                            new LoginTask(params).execute(GlobalUrl.LOGIN_URL);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registrationView.performClick();
            }
        });

        createAccountsView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registrationValidation()) {
                    if (!isChecked) {
                        Toast.makeText(mContext, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    mFirstName = fname.getText().toString().trim();
                    mLastName = lname.getText().toString().trim();
                    mPhoneNumber = mobile;
                    mREmailAddress = email.getText().toString().trim();
                    mRPassword = edt_pin.getText().toString().trim();
                    refCode = referral_code.getText().toString(); //referral code

                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.REGISTRATION_URL;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("customer_first_name", mFirstName);
                        params.put("customer_last_name", mLastName);
                        params.put("customer_phone", mPhoneNumber);
                        params.put("customer_email", mREmailAddress);
                        params.put("customer_pin", mRPassword);
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("customer_birthdate", "");
                        params.put("type", GlobalValues.TYPE);
                        params.put("outlet_id", "");
                        params.put("customer_gender", "");
                        params.put("customer_ref_code", refCode); //referral code


                        //params.put("site_url","");

                        new RegistrationTask(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        txt_resend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.networkCheck(mContext)) {

                    String url = GlobalUrl.RESEND_OTP_URL;
                    Map<String, String> params = new HashMap<String, String>();
                    params.put("app_id", GlobalValues.APP_ID);
                    params.put("customer_mobile", mobile);
                    params.put("verify_type", "register");


                    //params.put("site_url","");

                    new ResendOTP(params).execute(url);
                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });

        Register_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (registrationNewValidation()) {

//                        if (!isChecked) {
//                            Toast.makeText(mContext, "Please accept Terms and Conditions", Toast.LENGTH_SHORT).show();
//                            return;
//                        }
                    mFirstName = fname_new.getText().toString().trim();
//                        mLastName = lname.getText().toString().trim();
                    mPhoneNumber = edt_mobile_register_new.getText().toString().trim();
//                    mPhoneNumber = mobile;
                    mREmailAddress = email_new.getText().toString().trim();
                    mRPassword = edt_pin_new.getText().toString().trim();
                    mDob = dateOfBirthNew.getText().toString().trim();
                    refCode = referral_code_new.getText().toString(); //referral code

                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.REGISTRATION_URL;
                        Map<String, String> params = new HashMap<String, String>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("type", "Mobile");
//                        params.put("type", "web");
                        params.put("registertype", GlobalValues.TYPE);
                        params.put("passwordtype", "pin");
                        params.put("customer_first_name", mFirstName);
//                            params.put("customer_last_name", mLastName);
                        params.put("customer_email", mREmailAddress);
                        params.put("customer_pin", mRPassword);
                        params.put("customer_phone", mPhoneNumber);
                        params.put("customer_pdpa_consent", "no");
  //                      params.put("site_url", "https://sushitei.promobuddy.asia");
                        params.put("site_url", "https://ceres.ninjaos.com/");
                        params.put("customer_birthdate", mDob);
//                        params.put("outlet_id", "");
                        params.put("customer_gender", "");
                        params.put("android_key", "12345");
                        params.put("customer_ref_code", refCode); //referral code


                        //params.put("site_url","");

                        new RegistrationNewTask(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }


                    if (false) {     //test

                        dialog = new Dialog(LoginActivity.this, R.style.custom_dialog_theme1);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setCanceledOnTouchOutside(false);
                        dialog.setContentView(R.layout.dialog_continue_login);

//                        Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();

                        Button btn_continue = dialog.findViewById(R.id.btn_continue);
                        btn_continue.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                //test


                                Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
                                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                                intent = new Intent(mContext, MenuCategoryActivity.class);
                                startActivity(intent);
                                finish();

//                                Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
//                                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//                                intent = new Intent(mContext, ChooseOutletActivity.class);
//                                startActivity(intent);
//                                finish();
                            }
                        });
                        dialog.show();

                    }
                } else {
                    Toast.makeText(LoginActivity.this, mMessage, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });


//        if (true) {//test
//            edt_otp.setText("");
//            edt_mobile_reg.setText("");
//            edtMobileLogin.setText("");
//            edtPinLogin.setText("");
//            layout_register.setVisibility(View.VISIBLE);
//            lly_SignupConformation.setVisibility(View.VISIBLE);
//            layout_reg.setVisibility(View.GONE);
//            layout_login.setVisibility(View.GONE);
//            txtSubmit.setVisibility(View.GONE);
//            LoginLater.setVisibility(View.GONE);
//            lly_login.setVisibility(View.GONE);
//            txt_loginLabel.setText("Register your account");
//            lly_registration.setVisibility(View.VISIBLE);
//            Register.setVisibility(View.GONE);
//            lly_registration_.setVisibility(View.VISIBLE);
//            txt_resend.setVisibility(View.GONE);
//        }

//        rl_overall_login_layout.setVisibility(View.GONE);
//        rl_overall_register_layout.setVisibility(View.GONE);
//        rl_overall_register_layout_new.setVisibility(View.VISIBLE);

//        {//test
//             dialog = new Dialog(mContext, R.style.custom_dialog_theme1);
//            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//            dialog.setCanceledOnTouchOutside(false);
//            dialog.setContentView(R.layout.dialog_continue_login);
//
//            Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
//
//            Button btn_continue = dialog.findViewById(R.id.btn_continue);
//            btn_continue.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    //test
//                    Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
//                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//                    intent = new Intent(mContext, ChooseOutletActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            });
//            dialog.show();
//        }

        dateOfBirthNew = findViewById(R.id.dateOfBirthNew);
        dateOfBirthNew.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                // To show current date in the datepicker
                Calendar mcurrentDate = Calendar.getInstance();
                mYear = mcurrentDate.get(Calendar.YEAR);
                mMonth = mcurrentDate.get(Calendar.MONTH);
                mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);

                DatePickerDialog mDatePicker = new DatePickerDialog(LoginActivity.this, R.style.DatePickerTheme, new DatePickerDialog.OnDateSetListener() {
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
    }

    public boolean loginValidation() {

        if (mobileNumber.length() <= 0) {
            isLoginValidation = false;
            mMessage = "Please enter valid Mobile Number";
        } else if (mPassword.length() == 0) {
            isLoginValidation = false;
            mMessage = "Please enter valid PIN";
        } /*else if (mPassword.length() < 6) {
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

    public boolean registrationValidation() {
        if (fname.length() <= 0) {
            isRegValid = false;
            mMessage = "Please enter First Name";
        } else if (lname.length() <= 0) {
            isRegValid = false;
            mMessage = "Please enter Last Name";
        } else if (email.length() <= 0) {
            isRegValid = false;
            mMessage = "Please enter E-mail";
        } else if (!emailValidation(email.getText().toString())) {
            isRegValid = false;
            mMessage = "Please enter a valid E-mail";
        } else if (edt_pin.getText().toString().isEmpty()) {
            isRegValid = false;
            mMessage = "Please enter PIN";
        } else if (edt_pin.getText().toString().length() <= 5) {
            isRegValid = false;
            mMessage = "Please enter pin";
        } else {
            isRegValid = true;
        }

        return isRegValid;
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
        /*} else if (dateOfBirthNew.getText().toString().isEmpty()) {
            isRegValid = false;
            mMessage = "Please enter Date Of Birth";

//        } else if (!checkBox_new.isChecked()) {
//            isRegValid = false;
//            mMessage = "Please accept Terms and Conditions";
//        } else if (referral_code_new.getText().toString().isEmpty()) {
//            isRegValid = false;
//            mMessage = "Please enter Referral code";
       */ } else {
            isRegValid = true;
        }

        return isRegValid;
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //Facebook
        callbackManager.onActivityResult(requestCode, resultCode, data);

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


            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&customer_id=" +
                    Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            finish();

            new ActivCountTask(mapData).execute(url);

        }
    }

    class ActivCountTask extends AsyncTask<String, Void, String> {

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

                Log.e("count_response", s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObject.getJSONObject("result_set");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.getString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.getString("vouchers");
                    GlobalValues.CUSTOMER_REWARD_POINT_NEW = countJson.getString("reward_ponits");

                    if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
                        txtOrderCount.setVisibility(View.VISIBLE);
                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
                    } else {
                        txtOrderCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {
                        txtVoucherCount.setVisibility(View.VISIBLE);
                        txtVoucherCount.setText(GlobalValues.VOUCHERCOUNT);
                    } else {
                        txtVoucherCount.setVisibility(View.GONE);
                    }


                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {

                        txtVoucherCount.setVisibility(View.VISIBLE);
                        txtVoucherCount.setText(GlobalValues.VOUCHERCOUNT);

                    } else {
                        txtVoucherCount.setVisibility(View.GONE);
                    }


                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.GONE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }

                } else {

                }
                progressDialog.dismiss();
                finish();

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                finish();

            } finally {
                progressDialog.dismiss();
                finish();
            }
        }
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


            String response = WebserviceAssessor.postRequest(mContext, params[0], loginParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

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

                    if(!jsonResultset.isNull("ascentis_memberid")) {
                        Utility.writeToSharedPreference(mContext, GlobalValues.MEMBERSHIP_ID, jsonResultset.optString("ascentis_memberid"));
                    }
                    if(!jsonResultset.isNull("ascentis_card_no")) {
                        Utility.writeToSharedPreference(mContext, GlobalValues.ASCENTIS_CARD_NO, jsonResultset.optString("ascentis_card_no"));
                    }

                    GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("app_id", GlobalValues.APP_ID);
                    mapData.put("reference_id", Utility.getReferenceId(mContext));
                    mapData.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                    mapData.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                    String url = GlobalUrl.UPDATE_USER_INFO_URL;
                    progressDialog.dismiss();
                    //TODO
                    getActiveCount();
                    {//real
                        intent = new Intent(mContext, ChooseOutletActivity.class);
                        intent.putExtra("fromLogin",true);
                        startActivity(intent);
//                    // new UpdateCustomerInfoTask(mapData).execute(url);
                        finish();
                    }//test
                    {
                        //intent = new Intent(mContext, MenuCategoryActivity.class);
                        //startActivity(intent);
                        //finish();
                    }
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

    private class SendOTP extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> userInfoParams;

        public SendOTP(Map<String, String> params) {
            userInfoParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Retrieving OTP...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], userInfoParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getOTP = true;
                    edt_otp.setVisibility(View.VISIBLE);
                    txt_otpSeconds.setVisibility(View.VISIBLE);
                    customHandler.postDelayed(updateTimerThread, 1000);

                } else {

                    String message = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//


                    String form_error = jsonObject.optString("form_error");

                    String messageCom = message + "" + Html.fromHtml(form_error);

                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();
        }
    }

    Runnable updateTimerThread = new Runnable() {

        public void run() {

            if (count < 60) {
                count++;
                int secs = initial - count;
                txt_otpSeconds.setText(String.format(secs + " Seconds Remaining"));
            } else {
                customHandler.removeCallbacks(updateTimerThread);
                txt_otpSeconds.setText(String.format("0 Seconds Remaining"));
                txt_resend.setVisibility(View.VISIBLE);
            }
            customHandler.postDelayed(this, 1000);
        }
    };

    private class VerifyOTP extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> userInfoParams;

        public VerifyOTP(Map<String, String> params) {
            userInfoParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Verify OTP...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], userInfoParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    edt_otp.setText("");
                    edt_mobile_reg.setText("");
                    edtMobileLogin.setText("");
                    edtPinLogin.setText("");
                    layout_register.setVisibility(View.VISIBLE);
                    lly_SignupConformation.setVisibility(View.VISIBLE);
                    layout_reg.setVisibility(View.GONE);
                    layout_login.setVisibility(View.GONE);
                    txtSubmit.setVisibility(View.GONE);
                    LoginLater.setVisibility(View.GONE);
                    lly_login.setVisibility(View.GONE);
                    member_renewal.setVisibility(View.GONE);
                    txt_loginLabel.setText("Register");
                    txt_loginLabel_1.setText("Quick Checkout Account");
                    lly_registration.setVisibility(View.VISIBLE);
                    Register.setVisibility(View.GONE);
                    lly_registration_.setVisibility(View.VISIBLE);
                    txt_resend.setVisibility(View.GONE);
                } else {
                    String message = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//
                    String form_error = jsonObject.optString("form_error");
                    String messageCom = message + "" + Html.fromHtml(form_error);
                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }
            progressDialog.dismiss();
        }
    }

    class RegistrationTask extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;
        private Map<String, String> registrationParams;

        RegistrationTask(Map<String, String> params) {
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

             String response = WebserviceAssessor.postRequest(mContext, params[0], registrationParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                 JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    Toast.makeText(mContext, "Registered Successfully", Toast.LENGTH_SHORT).show();
                    finish();
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);

                } else {

                    String message = jsonObject.getString("message");
//                  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//
                    String form_error = jsonObject.optString("form_error");

                    if (form_error != null || !form_error.equals("")) {
                        Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }

                    //String messageCom = message + "" + Html.fromHtml(form_error);
                    //Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();

        }
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

             String response = WebserviceAssessor.postRequest(mContext, params[0], registrationParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                 JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
//                    String message = jsonObject.getString("message");
//                    Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG).show();//

//                    Toast.makeText(mContext, "Registered Successfully", Toast.LENGTH_SHORT).show();
//                    finish();
//                    intent = new Intent(mContext, LoginActivity.class);
//                    startActivity(intent);
                    showLoginDialog();
                } else {

                    String message = jsonObject.getString("message");
//                  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//
                    String form_error = jsonObject.optString("form_error");

                    if (form_error != null || !form_error.equals("")) {
                        Toast.makeText(mContext, Html.fromHtml(form_error), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(mContext, message, Toast.LENGTH_SHORT).show();
                    }

                    //String messageCom = message + "" + Html.fromHtml(form_error);
                    //Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();

        }
    }

    private class ResendOTP extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> userInfoParams;

        public ResendOTP(Map<String, String> params) {
            userInfoParams = params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Retrieving OTP...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], userInfoParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    getOTP = true;
                    edt_otp.setVisibility(View.VISIBLE);
                    txt_otpSeconds.setVisibility(View.VISIBLE);
                    customHandler.postDelayed(updateTimerThread, 1000);

                } else {

                    String message = jsonObject.getString("message");
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//


                    String form_error = jsonObject.optString("form_error");

                    String messageCom = message + "" + Html.fromHtml(form_error);

                    Toast.makeText(mContext, messageCom, Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            }

            progressDialog.dismiss();
        }
    }


    private class UpdateCustomerInfoTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> userInfoParams;

        public UpdateCustomerInfoTask(Map<String, String> userInfoParams) {
            this.userInfoParams = userInfoParams;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Updating info...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], userInfoParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            try {
                 progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
                finish();

            }
        }
    }

    private boolean emailValidation(String Email) {
        return (!TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches());
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    public void showLoginDialog() {     //test

        dialog = new Dialog(LoginActivity.this, R.style.custom_dialog_theme1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setContentView(R.layout.dialog_continue_login);

//        Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();

        Button btn_continue = dialog.findViewById(R.id.btn_continue);
        ImageView img_close = dialog.findViewById(R.id.img_close);
        img_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

//                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                intent = new Intent(mContext, LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
        dialog.show();

    }
}
