package com.app.sushi.tei.activity;

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
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
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
import com.app.sushi.tei.Model.CompassOutlet.OutletResultSetItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.OutletListAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class RegistrationActivity extends AppCompatActivity {

    private Context mContext;
    private Intent intent;
    private TextView txtSubmit;
    //  private TextView txtPolicy,termsConditionTextView,privacyTextView;
    private EditText edtFirstName,edtLastName,edtPhoneNumber,
            edtEmail, edtRPassword, edtConfirmPassword;
    private String mMessage = "", mFirstName = "", mLastName = "", mPhoneNumber = "", mDOB = "",
            mREmailAddress = "", mRPassword = "", mConfirmPassword = "", format = "";
    private ImageView imgChecked;
    private boolean isLoginValidation = false, isChecked = false, isDateDisplay = false;
    // private SpannableString spannableString;
    private CheckBox checkBoxs;
    private RelativeLayout chooseOutletLayout;
    private TextView outletName;
    private Toolbar toolbar;
    private LinearLayout toolbarBack;
    private RelativeLayout dateOfBirthLayout,gender_Layout;
    private Calendar myCalendar;
    private TextView DateOfBirth;
    private DatePickerDialog mDatePickerDialog;
    private TextView termsndconditions;
    private EditText defaultcode;

    private RecyclerView recycle_outletList;
    private OutletListAdapter outletListAdapter;
    public Dialog dialog;
    private List<OutletResultSetItem> outletList;
    private Date dobDate;
    public String[] genders = {"Male", "Female","Unspecified"};
    private TextView gender;
    private String outlet_id="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_register_layout);
        mContext = RegistrationActivity.this;

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbarBack = findViewById(R.id.toolbarBack);
        toolbarBack.setVisibility(View.GONE);
        defaultcode = findViewById(R.id.DefaultCode);
        defaultcode.setClickable(false);
        defaultcode.setEnabled(false);

        edtFirstName = (EditText) findViewById(R.id.your_name);
        edtLastName = (EditText) findViewById(R.id.last_name);
        edtPhoneNumber = (EditText) findViewById(R.id.PhoneNumber);
        edtEmail = (EditText) findViewById(R.id.EmailId);
        edtRPassword = (EditText) findViewById(R.id.password);
        edtConfirmPassword = (EditText) findViewById(R.id.ReEnterpassword);
        checkBoxs = findViewById(R.id.checkBoxs);
        txtSubmit = (TextView) findViewById(R.id.signup1);
        chooseOutletLayout = findViewById(R.id.chooseOutletLayout);
        outletName = findViewById(R.id.outletName);
        termsndconditions = findViewById(R.id.termsandconditions);
      /*  txtPolicy = (TextView) findViewById(R.id.txtPolicy);
        termsConditionTextView = (TextView) findViewById(R.id.termsConditionTextView);
        privacyTextView = (TextView) findViewById(R.id.privacyTextView);*/
        myCalendar = Calendar.getInstance();

        final DatePickerDialog.OnDateSetListener datepick = new DatePickerDialog.OnDateSetListener() {

            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                view.setMinDate(System.currentTimeMillis());
                view.setMaxDate(System.currentTimeMillis());
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, monthOfYear);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                updateLabel();
            }

        };
        setDateTimeField();
        DateOfBirth = findViewById(R.id.DateOfBirth);
        dateOfBirthLayout = findViewById(R.id.dateOfBirthLayout);
        gender_Layout=findViewById(R.id.gender_Layout);
        gender=findViewById(R.id.gender);

        final ArrayAdapter<String> spinner_genders = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, genders);
        gender_Layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog.Builder(mContext)
                        .setTitle("Gender")
                        .setAdapter(spinner_genders, new DialogInterface.OnClickListener() {

                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();


                                gender.setText(genders[which].toString());


                            }
                        }).create().show();
            }
        });
        dateOfBirthLayout.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
               // mDatePickerDialog.show();

                hideKeyboard();


                if (dobDate == null) {


                    Calendar calendar = Calendar.getInstance();


                    final DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.MyDatePicker, new DatePickerDialog.OnDateSetListener() {
                        @Override
                        public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                            Calendar cal = Calendar.getInstance();
                            cal.set(Calendar.YEAR, year);
                            cal.set(Calendar.MONTH, month);
                            cal.set(Calendar.DAY_OF_MONTH, day);

                            dobDate = cal.getTime();

                            SimpleDateFormat simpleDisplayDateFormat = new SimpleDateFormat("dd/MM/yyyy");

                            DateOfBirth.setText(simpleDisplayDateFormat.format(dobDate));


                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();

                } else {

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

                            DateOfBirth.setText(simpleDisplayDateFormat.format(dobDate));


                        }
                    }, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));
                    datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis() - 1000);
                    datePickerDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    datePickerDialog.show();


                }



            }
        });


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
        chooseOutletLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOutletService();
            }
        });

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mFirstName = edtFirstName.getText().toString().trim();
                mLastName = edtLastName.getText().toString().trim();
                mPhoneNumber = edtPhoneNumber.getText().toString().trim();
                mDOB = DateOfBirth.getText().toString();
                mREmailAddress = edtEmail.getText().toString().trim();
                mRPassword = edtRPassword.getText().toString().trim();
                mConfirmPassword = edtConfirmPassword.getText().toString().trim();

                String genderString = gender.getText().toString();

                if (registrationValidation()) {


                    if (Utility.networkCheck(mContext)) {


                        String url = GlobalUrl.REGISTRATION_URL;

                        Map<String, String> params = new HashMap<String, String>();

                        params.put("customer_first_name", mFirstName);
                        params.put("customer_last_name", mLastName);
                        params.put("customer_phone", mPhoneNumber);
                        params.put("customer_email", mREmailAddress);
                        params.put("customer_password", mRPassword);
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("customer_birthdate", mDOB);
                        params.put("type", GlobalValues.TYPE);
                        params.put("outlet_id", outlet_id);

                        if (genderString.equals(genders[0])) {
                            params.put("customer_gender", "M");
                        } else if (genderString.equals(genders[1])) {
                            params.put("customer_gender", "F");
                        } else if (genderString.equals(genders[2])) {
                            params.put("customer_gender", "O");
                        }

//                    params.put("site_url","");

                        new RegistrationTask(params).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }


                } else {

                    Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
                }

            }
        });

        termsndconditions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, CmsActivity.class);
                intent.putExtra("TITLE", "cmspage_slug");
                intent.putExtra("landingPage", true);
                intent.putExtra("SEARCH_KEY", "privacy-policy");
                startActivity(intent);
        }
    });
/*

        String mPolicy = getResources().getString(R.string.terms_condition);


        */
        /*Spannable string is used to change the color of specific string of a sentence and make it clickable *//*


        spannableString = new SpannableString(mPolicy);
        spannableString.setSpan(new UnderlineSpan(), 182, 193, 0);
        spannableString.setSpan(new UnderlineSpan(), 198, mPolicy.length(), 0);
        */
/*spannableString.setSpan(new StyleSpan(Typeface.BOLD), 45, 69, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 74, mPolicy.length()-1, 0);*//*

        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorWhite)), 192, 192, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorWhite)), 198, mPolicy.length(), Spanned.SPAN_INCLUSIVE_INCLUSIVE);


        ClickableSpan clickPolicy = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

//                Toast.makeText(mContext, "Policy clicked", Toast.LENGTH_SHORT).show();

                intent=new Intent(mContext,CmsActivity.class);
                intent.putExtra("TITLE", "");
                intent.putExtra("SEARCH_KEY", "Privacy");
                startActivity(intent);

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        ClickableSpan clickCondition = new ClickableSpan() {
            @Override
            public void onClick(View textView) {

//                Toast.makeText(mContext, "Terms clicked", Toast.LENGTH_SHORT).show();

                intent=new Intent(mContext,CmsActivity.class);
                intent.putExtra("TITLE", "");
                intent.putExtra("SEARCH_KEY", "terms&conditions");
                startActivity(intent);
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(true);
            }
        };

        spannableString.setSpan(clickPolicy, 179, 193, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        spannableString.setSpan(clickCondition, 198, mPolicy.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        txtPolicy.setText(spannableString);
        txtPolicy.setMovementMethod(LinkMovementMethod.getInstance());


        privacyTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent=new Intent(mContext,CmsActivity.class);
                intent.putExtra("TITLE", "");
                intent.putExtra("SEARCH_KEY", "Privacy");
                startActivity(intent);

            }
        });

        termsConditionTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                intent=new Intent(mContext,CmsActivity.class);
                intent.putExtra("TITLE", "");
                intent.putExtra("SEARCH_KEY", "terms&conditions");
                startActivity(intent);
            }
        });




        imgChecked = (ImageView) findViewById(R.id.imgChecked);

        imgChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isChecked) {
                    isChecked = true;
                    imgChecked.setImageResource(R.drawable.icon_register_checked);
                } else {
                    isChecked = false;
                    imgChecked.setImageResource(R.drawable.icon_register_unchecked);
                }

            }
        });
*/


    }

    private void updateLabel() {
        String myFormat = "MM/dd/yy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        DateOfBirth.setText(sdf.format(myCalendar.getTime()));
    }


    public boolean registrationValidation() {


        if (outlet_id.length() <= 0) {

            isLoginValidation = false;
            mMessage = "Please Select Your Outlet";

        }else

        if (mFirstName.length() <= 0) {

            isLoginValidation = false;
            mMessage = "Please enter first name";

        }/* else if (mPhoneNumber.length() <= 0) {
            isLoginValidation = false;
            mMessage = "Please enter phone number";

        } else if (mDOB.length() <= 0) {
            isLoginValidation = false;
            mMessage = "Please enter Date of Birth";

        }*/ else if (mREmailAddress.length() <= 0) {

            isLoginValidation = false;
            mMessage = "Please enter email address";

        } else if (!Patterns.EMAIL_ADDRESS.matcher(mREmailAddress).matches()) {

            isLoginValidation = false;
            mMessage = "Please enter valid email address";

        } else if (mRPassword.length() <= 0) {
            isLoginValidation = false;
            mMessage = "Please enter password";

        } else if (mConfirmPassword.length() <= 0) {
            isLoginValidation = false;
            mMessage = "Please enter confirm password";
        } else if (!mRPassword.equals(mConfirmPassword)) {
            isLoginValidation = false;
            mMessage = "Password and Confirm password did not match";
        } else if (!isChecked) {
            isLoginValidation = false;
            mMessage = "Please accept terms and conditions";
        } else {
            isLoginValidation = true;
        }

        return isLoginValidation;
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

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    finish();
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);

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


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObject.getJSONObject("result_set");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.getString("promotionwithoutuqc");

                   /* if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
                        txtOrderCount.setVisibility(View.VISIBLE);
                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
                    } else {
                        txtOrderCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.GONE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }*/

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
                finish();
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
        }
    }

    private void setDateTimeField() {

        final Calendar newCalendar = Calendar.getInstance();
        mDatePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {

            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                Calendar newDate = Calendar.getInstance();
                // Calendar futurecalender=Calendar.getInstance();


                //futurecalender.set(Calendar.YEAR,Calendar.MONTH+1,Calendar.DAY_OF_MONTH+1);


                newDate.set(year, monthOfYear, dayOfMonth);
                SimpleDateFormat sd = new SimpleDateFormat("dd-MM-yyyy");
                final Date startDate = newDate.getTime();


                // Date fudate=newDate.getTime();
                String fdate = sd.format(startDate);

                DateOfBirth.setText(fdate);

            }
        }, newCalendar.get(Calendar.YEAR), newCalendar.get(Calendar.MONTH), newCalendar.get(Calendar.DAY_OF_MONTH));
       // Date mydate = new Date(System.currentTimeMillis() - (1000 * 60 * 60 * 24));
        mDatePickerDialog.getDatePicker().setMaxDate(newCalendar.getTimeInMillis());

    }

    private void getOutletService() {


        if (Utility.networkCheck(mContext)) {
            String url = GlobalUrl.COMPASSOUTLET_URL + "?app_id=" + GlobalValues.APP_ID;

            new OutletTask().execute(url);

        } else {
            Toast.makeText(mContext, "Please check your interner connection.", Toast.LENGTH_SHORT).show();
        }


    }
    private class OutletTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        OutletTask() {

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

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONArray arrayResult = jsonObject.getJSONArray("result_set");

                    if (arrayResult.length() > 0) {
                        outletList = new ArrayList<>();

                        for (int j = 0; j < arrayResult.length(); j++) {

                            JSONObject jsonOutlet = arrayResult.getJSONObject(j);

                            OutletResultSetItem outlet = new OutletResultSetItem();

                            outlet.setOaOutletId(jsonOutlet.getString("oa_outlet_id"));
                            outlet.setOaAvailabilityId(jsonOutlet.optString("oa_availability_id"));
                            outlet.setOutletName(jsonOutlet.optString("outlet_name"));
                            outlet.setOutletId(jsonOutlet.getString("outlet_id"));
                            outlet.setOutletPhone(jsonOutlet.getString("outlet_phone"));
                            outlet.setOutletDeliveryTiming(jsonOutlet.getString("outlet_delivery_timing"));
                            outlet.setOutletUnitNumber1(jsonOutlet.getString("outlet_unit_number1"));
                            outlet.setOutletUnitNumber2(jsonOutlet.getString("outlet_unit_number2"));
                            outlet.setOutletAddressLine1(jsonOutlet.getString("outlet_address_line1"));
                            outlet.setOutletAddressLine2(jsonOutlet.getString("outlet_address_line2"));
                            outlet.setOutletPostalCode(jsonOutlet.getString("outlet_postal_code"));
                            outlet.setOutletDefalutValue(jsonOutlet.getString("outlet_defalut_value"));
                            outlet.setOutletOpenDate(jsonOutlet.getString("outlet_open_date"));
                            outlet.setOutletCloseDate(jsonOutlet.getString("outlet_close_date"));
                            outlet.setOutletOpenTime(jsonOutlet.getString("outlet_open_time"));
                            outlet.setOutletCloseTime(jsonOutlet.getString("outlet_close_time"));
                            outlet.setOutletRouteId(jsonOutlet.getString("outlet_route_id"));
                            outlet.setOutletMarkerLatitude(jsonOutlet.getString("outlet_marker_latitude"));
                            outlet.setOutletMarkerLongitude(jsonOutlet.getString("outlet_marker_longitude"));
                            outlet.setOutletDineTat(jsonOutlet.getString("outlet_dine_tat"));
                            outlet.setOutletDeliveryTat(jsonOutlet.getString("outlet_delivery_tat"));
                            outlet.setOutletPickupTat(jsonOutlet.getString("outlet_pickup_tat"));
                            outlet.setOutletServiceCharge(jsonOutlet.getString("outlet_service_charge"));
                            outletList.add(outlet);




                        }
                        showOutletPopup(outletList);




                    } else {

                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private void showOutletPopup(final List<OutletResultSetItem> outletList) {
        dialog = new Dialog(mContext, R.style.custom_dialog_theme1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_outlet);
        dialog.show();
        recycle_outletList =dialog.findViewById(R.id.recycle_outletList);

        ImageView closeOutlet=dialog.findViewById(R.id.closeOutlet);

        closeOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycle_outletList.setLayoutManager(linearLayoutManager);
        outletListAdapter = new OutletListAdapter(mContext, outletList);
        recycle_outletList.setAdapter(outletListAdapter);


        outletListAdapter.setOnClickListeners(new OutletListAdapter.OnOutletClick() {
            @Override
            public void OnClick(View v, int position) {
                outlet_id=  outletList.get(position).getOaOutletId();
                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_ID, outletList.get(position).getOaOutletId());
                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_NAME, outletList.get(position).getOutletName());
                Utility.writeToSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS, outletList.get(position).getOutletAddressLine1());
                String outname = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME);

                if (outname != null && !outname.isEmpty() && outname != "null") {
                    outletName.setText(outname);

                }
                if (dialog.isShowing())
                    dialog.dismiss();


            }

        });

    }

    public void hideKeyboard() {
        View view = RegistrationActivity.this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }

    }

}

