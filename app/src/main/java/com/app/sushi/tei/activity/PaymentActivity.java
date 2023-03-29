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
import android.os.SystemClock;
import com.google.android.material.tabs.TabLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.Editable;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextWatcher;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.Window;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Cards;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.CreditCardAdapter;
import com.bumptech.glide.Glide;
import com.whiteelephant.monthpicker.MonthPickerDialog;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.security.GeneralSecurityException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
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

import static com.app.sushi.tei.GlobalMembers.GlobalValues.CURRENT_TAT_TIME;
import static com.app.sushi.tei.activity.OrderSummaryActivity.gstAmount;
import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyPromo;
import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyRedeem;



public class PaymentActivity extends AppCompatActivity {

    private Context mContext;
    private Toolbar toolbar;
    ImageView txtTitle;
    private TextView saveCardTextBox;
    private LinearLayout imgBack;
    private LinearLayout paymentLayout, layoutPayment1, layoutCashPayment;
    private Intent intent;
    private ImageView imgGifPaymentProcess, imgGifOrderProcess, gifLoading;
    private ImageView imgPaymentProcess, imgOrderProcess;
    private EditText edtCardName, edtCardNumber, edtCVVNumber;
    private RelativeLayout layoutVisa;
    private ImageView imgCashOnDelivery, imgVisaCard;
    private TextView txtPrice, txtMonth, txtYear;
    private Spinner spinnerMonth, spinnerYear;
    private TextView txtCashPrice, txtTotal;
    private String mOrderComments = "";
    private String mOrderNo = "";
    private String mOrderDate = "";
    private DatabaseHandler databaseHandler;
    private List<String> yearList, monthList;
    private Map<String, String> params;
    private SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
    private String mUnitNo1 = "", mUnitNo2 = "", mCardNumber = "", mCardName = "",
            mYear = "", mMonth = "", mCVV = "", mBillingAddress = "", mBillingPincode = "",
            mBillingUnitNo1 = "", mBillingUnitNo2 = "";

    private int expDay, expMonth, expYear;

    private String r_applied,
            r_point,
            r_amount, sub_total, grand_total, p_code, p_amount, reward_subTotal, promo_subTotal, eWalletAmount;
    private boolean isMonthSelected = false, isYearSelected = false;
    private String mValidationMessage = "";
    private Dialog dialog;

    String starttime, endtime;
    private String customerNote;
    Client client;
    private boolean onlineValidation = false;

    private String cardName = "", cardNumber = "", cvvNumber = "", mMessage = "";

    private CheckBox saveCardCheckBox;

    private LinearLayout addCardLinLayout;

    private RelativeLayout view_card_detials;
    private TextView savedCard;

    private RecyclerView cardRecyclerView;
    private String Customer_Id = "";
    ArrayList<Cards> cardsArrayLists = new ArrayList<>();
    private RecyclerView.LayoutManager layoutManager;
    private long mLastClickTime = 0;
    private TabLayout tabLayout;
    private TextView txt_noSavedCards;
    private RelativeLayout rly_year, rly_month, rly_expDate;
    private TextView txt_expDate;
    private String[] ordertabs = {"SAVED CARDS", "CREDIT/DEBIT CARDS"};
    private String loyality_point_price = "";
    WebAuthotizeTask webAuthotizeTask;
    private String mPrevCaptureId;
    private boolean rewardPointsPayment = false;
    private String cartVoucherDiscountAmt = "0", orderItemId = "", cartItemVoucherId = "";

    //    private BottomSheetDialog dialogBottom;
    private Dialog dialogBottom;
    private EditText edtCardName_new;
    private EditText edtCardNumber_new;
    private TextView txt_expDate_new;
    private EditText edtCVVNumber_new;
    private Button btn_continue;
    private Button btn_continue_disabled;
    Calendar today;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);

        mContext = PaymentActivity.this;
        databaseHandler = DatabaseHandler.getInstance(mContext);

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        txtTitle = toolbar.findViewById(R.id.toolbartxtTitle);
        imgBack = toolbar.findViewById(R.id.toolbarBack);

        String paymentKey = Utility.readFromSharedPreference(mContext, GlobalValues.PAYMENTKEY);

//        initializeCyberSource();

        try {
            client = new Client(paymentKey);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        }

        if (getIntent().getExtras() != null) {
            loyality_point_price = getIntent().getStringExtra("loyality_point_price");
            rewardPointsPayment = getIntent().getBooleanExtra("rewardPointsPayment", false);

        }


        paymentLayout = (LinearLayout) findViewById(R.id.layoutPayment);
        layoutPayment1 = findViewById(R.id.layoutPayment1);
        layoutCashPayment = (LinearLayout) findViewById(R.id.layoutCashPayment);

        cardRecyclerView = (RecyclerView) findViewById(R.id.cardRecyclerView);
        addCardLinLayout = (LinearLayout) findViewById(R.id.addCardLinLayout);
        view_card_detials = (RelativeLayout) findViewById(R.id.view_card_detials);

        savedCard = (TextView) findViewById(R.id.savedCard);
        txtPrice = (TextView) findViewById(R.id.txtPrice);
        imgCashOnDelivery = (ImageView) findViewById(R.id.imgCashOnDelivery);
        imgVisaCard = (ImageView) findViewById(R.id.imgVisaCard);
        layoutVisa = (RelativeLayout) findViewById(R.id.layoutVisa);
        txtCashPrice = (TextView) findViewById(R.id.txtCashPrice);
        txtMonth = (TextView) findViewById(R.id.txtMonth);
        txtYear = (TextView) findViewById(R.id.txtYear);
        spinnerYear = (Spinner) findViewById(R.id.spinnerYear);
        spinnerMonth = (Spinner) findViewById(R.id.spinnerMonth);
        edtCardName = (EditText) findViewById(R.id.edtCardName);
        edtCardNumber = (EditText) findViewById(R.id.edtCardNumber);
        edtCVVNumber = (EditText) findViewById(R.id.edtCVVNumber);
        saveCardCheckBox = (CheckBox) findViewById(R.id.saveCardCheckBox);
        saveCardTextBox = (TextView) findViewById(R.id.saveCardTextBox);
        rly_year = findViewById(R.id.rly_year);
        rly_month = findViewById(R.id.rly_month);
        rly_expDate = findViewById(R.id.rly_expDate);
        txt_expDate = findViewById(R.id.txt_expDate);
        txt_noSavedCards = findViewById(R.id.txt_noSavedCards);
        saveCardTextBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveCardCheckBox.toggle();
            }
        });
        yearList = addYear();
        monthList = addMonth();

        params = new HashMap<>();

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_processing_order);

        txtTotal = (TextView) dialog.findViewById(R.id.txtTotal);


        imgGifOrderProcess = (ImageView) dialog.findViewById(R.id.imgGifPaymentProcess);
        imgGifPaymentProcess = (ImageView) dialog.findViewById(R.id.imgGifPaymentProcess);

        gifLoading = (ImageView) dialog.findViewById(R.id.gifLoading);


        Glide.with(PaymentActivity.this).load(R.drawable.loading_big).into(imgGifPaymentProcess);
        Glide.with(PaymentActivity.this).load(R.drawable.loading_big).into(imgGifOrderProcess);
        Glide.with(PaymentActivity.this).load(R.drawable.loading_big).into(gifLoading);


        // loading_big

        imgPaymentProcess = (ImageView) dialog.findViewById(R.id.imgPaymentProcess);
        imgOrderProcess = (ImageView) dialog.findViewById(R.id.imgOrderProcess);


        ArrayAdapter<String> yearAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, yearList);

        spinnerYear.setAdapter(yearAdapter);

        ArrayAdapter<String> monthAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_item, monthList);

        spinnerMonth.setAdapter(monthAdapter);

        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);

        starttime = Utility.readFromSharedPreference(mContext, GlobalValues.COD_START_TIME);
        endtime = Utility.readFromSharedPreference(mContext, GlobalValues.COD_END_TIME);

        today = Calendar.getInstance();
        GlobalValues.ZoneID = Utility.readFromSharedPreference(mContext, GlobalValues.ZONE_ID);

        rly_expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCardExpDate();
                //createDialogWithoutDateField().show();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(PaymentActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {

                        Toast.makeText(PaymentActivity.this, "Date set with month" + selectedMonth + " year " + selectedYear, Toast.LENGTH_SHORT).show();
                    }
                }, today.get(Calendar.YEAR), today.get(Calendar.MONTH));

                builder.setActivatedMonth(Calendar.JULY)
                        .setMinYear(1990)
                        .setActivatedYear(2017)
                        .setMaxYear(2030)
                        .setMinMonth(Calendar.FEBRUARY)
                        .setTitle("Select trading month")
                        .setMonthRange(Calendar.FEBRUARY, Calendar.NOVEMBER)
                        // .setMaxMonth(Calendar.OCTOBER)
                        // .setYearRange(1890, 1890)
                        // .setMonthAndYearRange(Calendar.FEBRUARY, Calendar.OCTOBER, 1890, 1890)
                        //.showMonthOnly()
                        // .showYearOnly()
                        .setOnMonthChangedListener(new MonthPickerDialog.OnMonthChangedListener() {
                            @Override
                            public void onMonthChanged(int selectedMonth) {

                                Toast.makeText(PaymentActivity.this, " Selected month : " + selectedMonth, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .setOnYearChangedListener(new MonthPickerDialog.OnYearChangedListener() {
                            @Override
                            public void onYearChanged(int selectedYear) {

                                Toast.makeText(PaymentActivity.this, " Selected year : " + selectedYear, Toast.LENGTH_SHORT).show();
                            }
                        })
                        .build()
                        .show();
            }
        });

        txt_expDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //getCardExpDate();
                //createDialogWithoutDateField().show();
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(PaymentActivity.this, new MonthPickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(int selectedMonth, int selectedYear) {
                        mMonth = String.format("%02d", (selectedMonth + 1));
                        mYear = String.valueOf(selectedYear);

                        txt_expDate.setText(String.valueOf(String.format("%02d", (selectedMonth + 1)) + "/" + String.valueOf(selectedYear).substring(2, 4)));


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
                                    txt_expDate.setText(String.valueOf(String.format("%02d", (Integer.parseInt(mMonth))) + "/" + mYear.substring(2, 4)));
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
                                    txt_expDate.setText(String.valueOf(String.format("%02d", (Integer.parseInt(mMonth))) + "/" + mYear.substring(2, 4)));
                                }
                            }
                        })
                        .build()
                        .show();
            }
        });

        try {
            mOrderComments = getIntent().getStringExtra("order_remarks");
            sub_total = getIntent().getStringExtra("sub_total");
            grand_total = getIntent().getStringExtra("total_price");
            cartVoucherDiscountAmt = getIntent().getStringExtra("cartVoucherDiscountAmt");
            mUnitNo1 = getIntent().getStringExtra("unit_no1");
            mUnitNo2 = getIntent().getStringExtra("unit_no2");

            r_applied = getIntent().getStringExtra("REDEEM_APPLIED");
            r_point = getIntent().getStringExtra("REDEEM_POINT");
            r_amount = getIntent().getStringExtra("redeem_amount");

            p_code = getIntent().getStringExtra("promo_code");
            p_amount = getIntent().getStringExtra("promo_amount");
            reward_subTotal = getIntent().getStringExtra("reward_subTotal");
            promo_subTotal = getIntent().getStringExtra("promo_subTotal");

            eWalletAmount = getIntent().getStringExtra("ewallet_amount");

            customerNote = getIntent().getStringExtra("AdditionalNotes");


            mBillingAddress = getIntent().getStringExtra("billing_address");
            mBillingPincode = getIntent().getStringExtra("billing_pincode");
            mBillingUnitNo1 = getIntent().getStringExtra("billing_unit_no1");
            mBillingUnitNo2 = getIntent().getStringExtra("billing_unit_no2");


            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_COMMENTS, mOrderComments);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_SUBTOTAL, sub_total);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_GRANDTOTAL, grand_total);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_UNITNO_ONE, mUnitNo1);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_UNITNO_TWO, mUnitNo2);


            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_RAPPLIED, r_applied);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_RPOINT, r_point);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_RAMOUNT, r_amount);


            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_PCODE, p_code);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_PAMOUNT, p_amount);


            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_BILLADRESS, mBillingAddress);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_BILLPINCODE, mBillingPincode);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_BILUNITNO1, mBillingUnitNo1);
            Utility.writeToSharedPreference(mContext, GlobalValues.SU_ORDER_BILLUNITNO2, mBillingUnitNo2);


        } catch (Exception e) {
            e.printStackTrace();
            txtCashPrice.setText("$0.0");
        }

        try {
            SpannableStringBuilder cs = new SpannableStringBuilder("$" + String.format("%.2f", Double.valueOf(grand_total)));
            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.6f), 0, 1, 0);
            txtPrice.setText(cs);
            txtCashPrice.setText(cs);
            txtTotal.setText(cs);
        } catch (Exception e) {
            e.printStackTrace();
        }

        tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_order);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));

              /*  if (txtTabItem.getText().toString().equalsIgnoreCase("SAVED CARDS")) {

                } else {

                }*/

                switch (tab.getPosition()) {

                    case 0:
                        if (cardsArrayLists.size() > 0) {
                            view_card_detials.setVisibility(View.VISIBLE);
                            cardRecyclerView.setVisibility(View.VISIBLE);
                            txt_noSavedCards.setVisibility(View.GONE);
                        } else {
                            cardRecyclerView.setVisibility(View.GONE);
                            txt_noSavedCards.setVisibility(View.VISIBLE);

                        }

                        layoutVisa.setVisibility(View.GONE);
                        break;

                    case 1:
                        txt_noSavedCards.setVisibility(View.GONE);
                        cardRecyclerView.setVisibility(View.GONE);
                        layoutVisa.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_order);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        for (int k = 0; k < ordertabs.length; k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_orders_item, null);

            TextView txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_order);

            txtTabItem.setText(ordertabs[k]);


            if (k == 1) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }
            if (k == 0) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }


            tabLayout.addTab(tabLayout.newTab().setCustomView(tabView));

        }

        if (getIntent().getStringExtra("PAYMENT_TYPE").equalsIgnoreCase("NETS")) {
            placeCashOnDeliveryOrder();
        } else if (getIntent().getStringExtra("PAYMENT_TYPE").equalsIgnoreCase("UOB")) {
            UOBServiceValidate();
        } else {
            openPaymentDialog();
            checkCardDetails();
        }
        paymentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();*/
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();
//                cardName = edtCardName.getText().toString();
//                cardNumber = edtCardNumber.getText().toString();
//                cvvNumber = edtCVVNumber.getText().toString();

                cardName = edtCardName_new.getText().toString();
                cardNumber = edtCardNumber_new.getText().toString();
                cvvNumber = edtCVVNumber_new.getText().toString();

                if (onlinePaymentValidation()) {
                    layoutPayment1.setVisibility(View.VISIBLE);
                    paymentLayout.setVisibility(View.GONE);
                    if (Utility.networkCheck(mContext)) {

                        callingOnlinePaymentOldOmise();
                        paymentLayout.setEnabled(false);
                    } else {
                       /* if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
                    /*if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }*/
                }


            }
        });

        rly_month.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtMonth.performClick();
            }
        });

        rly_year.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtYear.performClick();
            }
        });

        txtMonth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                spinnerMonth.setVisibility(View.VISIBLE);
                spinnerYear.setVisibility(View.GONE);
                txtMonth.setFocusable(true);
                spinnerMonth.performClick();

            }
        });

        txtYear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                spinnerYear.setVisibility(View.VISIBLE);
                spinnerMonth.setVisibility(View.GONE);
                txtYear.setFocusable(true);
                spinnerYear.performClick();
            }
        });


        spinnerYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                 txtYear.setText(yearList.get(position));
                mYear = yearList.get(position);
                isYearSelected = true;
                //  spinnerYear.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        spinnerMonth.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 txtMonth.setText(monthList.get(position));
                mMonth = monthList.get(position);
                isMonthSelected = true;
                // spinnerMonth.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        imgCashOnDelivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                layoutVisa.setVisibility(View.GONE);
                layoutCashPayment.setVisibility(View.VISIBLE);
                imgCashOnDelivery.setImageResource(R.drawable.icon_cash_delivery_select);
                imgVisaCard.setImageResource(R.drawable.icon_visacard_unselect);
            }
        });

        savedCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                layoutVisa.setVisibility(View.GONE);
                view_card_detials.setVisibility(View.VISIBLE);
                savedCard.setTextColor(getResources().getColor(R.color.colorWhite));
                savedCard.setBackgroundResource(R.drawable.button_back_darkorange);
                imgVisaCard.setImageResource(R.drawable.icon_visacard_unselect);
                checkCardDetails();
            }
        });

        imgVisaCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  Toast.makeText(mContext, " PaymentAct option not available for this order.", Toast.LENGTH_SHORT).show();

                view_card_detials.setVisibility(View.GONE);
                layoutCashPayment.setVisibility(View.GONE);
                layoutVisa.setVisibility(View.VISIBLE);
                savedCard.setBackgroundResource(R.drawable.button_back_border);
                savedCard.setTextColor(getResources().getColor(R.color.colorBlack));
                imgVisaCard.setImageResource(R.drawable.icon_visacard_select);


            }
        });

        addCardLinLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view_card_detials.setVisibility(View.GONE);
                layoutCashPayment.setVisibility(View.GONE);
                layoutVisa.setVisibility(View.VISIBLE);
                savedCard.setBackgroundResource(R.drawable.button_back_darkorange);
                savedCard.setTextColor(getResources().getColor(R.color.colorWhite));
                imgVisaCard.setImageResource(R.drawable.icon_visacard_unselect);
            }
        });

        layoutCashPayment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                GlobalValues.PAYMENT_TYPE = "CASH";
                placeCashOnDeliveryOrder();
            }
        });




        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void UOBServiceValidate() {
        try {
            JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());

           /* JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));



            JSONObject jsonPostalCodeInfo = outletJson.getJSONObject("postal_code_information");*/

            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

            StringBuilder productIdsStringBuilder = new StringBuilder();

            for (int i = 0; i < cartJsonArray.length(); i++) {
                JSONObject cartItem = cartJsonArray.getJSONObject(i);
                if (i != (cartJsonArray.length() - 1))
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty") + ";");
                else {
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty"));
                }
            }

           /* try {
                mGrandTotal = Double.parseDouble(jsonCartDetails.getString("cart_sub_total")) +
                        Double.parseDouble(outletJson.getString("zone_delivery_charge"));
            } catch (Exception e) {
                mGrandTotal = 0.0;
                e.printStackTrace();
            }*/



            try {
                String d[] = GlobalValues.DELIVERY_DATE.split("-");
                String date = d[2] + "-" + d[1] + "-" + d[0] + " " + GlobalValues.DELIVERY_TIME;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Date d1 = simpleDateFormat.parse(date);

                mOrderDate = simpleDateFormat.format(d1);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            params.put("app_id", GlobalValues.APP_ID);
            params.put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));
            params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
            params.put("amount", grand_total);
            params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            params.put("sub_total", sub_total);
            params.put("grand_total", grand_total);
            params.put("order_voucher_discount_amount", cartVoucherDiscountAmt);
            params.put("table_number", "");
            params.put("order_status", "1");
            params.put("order_source", "Mobile");
            if (GlobalValues.IS_ADVANCE_ORDER.equalsIgnoreCase("no")) {
                params.put("order_date", mOrderDate);
            } else {
                params.put("order_date", mOrderDate);
                params.put("order_advanced_date", mOrderDate);
            }
            //order_is_timeslot,app_id,outlet_id,order_pickup_time_slot_from,order_pickup_time_slot_to
            params.put("order_remarks", mOrderComments);
            params.put("products", getProductJSONArray(cartJsonArray).toString());
            //customer_id
            params.put("customer_fname", Utility.readFromSharedPreference(mContext, GlobalValues.FIRSTNAME));
            params.put("customer_lname", Utility.readFromSharedPreference(mContext, GlobalValues.LASTNAME));
            params.put("customer_mobile_no", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));
            params.put("customer_email", Utility.readFromSharedPreference(mContext, GlobalValues.EMAIL));

            if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                    GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
                JSONObject jsonPostalCodeInfo = outletJson.getJSONObject("postal_code_information");
                params.put("customer_address_line1", jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
                        jsonPostalCodeInfo.optString("zip_buname") + ",");
                params.put("customer_postal_code", jsonPostalCodeInfo.optString("zip_code"));

                params.put("customer_unit_no1", mUnitNo1);
                params.put("customer_unit_no2", mUnitNo2);
                //customer_birthdate_update,customer_birthdate,customer_gender
                params.put("billing_address_line1", mBillingAddress);
                params.put("billing_postal_code", mBillingPincode);
                params.put("billing_unit_no1", mBillingUnitNo1);
                params.put("billing_unit_no2", mBillingUnitNo2);

                params.put("zone_id", GlobalValues.ZoneID);
            }


            params.put("payment_mode", "2");
            params.put("payment_reference", "sushitei");
            params.put("stripe_envir",GlobalValues.ENVIR);
            params.put("payment_type", "CYBERSOURCE");
            //order_capture_token,order_payment_getway_type
            params.put("order_capture_token","");
            params.put("order_payment_getway_type","");
            params.put("order_payment_getway_status","Yes");
            if (GlobalValues.isFoodVoucher) {
                params.put("delivery_charge", "0.0");
            } else {
                params.put("delivery_charge", GlobalValues.DELEIVERY_CHARGES);
            }
            params.put("additional_delivery", GlobalValues.ADDITIONAL_DELEIVERY_CHARGES);
            if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                    GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                params.put("order_tat_time", CURRENT_TAT_TIME);
            }
            params.put("tax_charge", Utility.readFromSharedPreference(mContext, GlobalValues.GstCharger));
            params.put("order_tax_calculate_amount", String.valueOf(GlobalValues.GST));
            params.put("order_data_encode","Yes");
            if (GlobalValues.DISCOUNT_APPLIED) {

                params.put("order_discount_applied", "Yes");
                params.put("order_discount_amount", r_amount);
                //order_discount_type,order_special_discount_amount,order_special_discount_type,order_customer_send_gift

            } else {
                params.put("order_discount_applied", "No");
            }

            //order_recipient_name,order_recipient_contact_no,order_gift_message
            params.put("validation_only", "Yes");
            params.put("reward_point_status", "Yes");




            if (isApplyPromo) {

                if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {
                    params.put("promotion_delivery_charge_applied", "Yes");

                } else {

                    params.put("promo_code", p_code);
                    params.put("coupon_amount", p_amount);
                    params.put("coupon_applied", "Yes");
                    params.put("order_promo_sub_total", promo_subTotal);

                }
            }

            if (isApplyRedeem) {
                params.put("redeem_applied", "Yes");
                params.put("redeem_point", r_point);
                params.put("redeem_amount", r_amount);
                params.put("order_reward_sub_total", reward_subTotal);

                params.put("ascentiscrm_pointsredeem_applied", "Yes");
                params.put("ascentiscrm_redeemed_points", r_point);
                params.put("ascentiscrm_discount_amount", r_amount);
            }

            if (isApplyPromo && isApplyRedeem) {
                params.put("order_discount_both", "1");
            }

            params.put("category_id", productIdsStringBuilder.toString());
            params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
            params.put("order_someone_remarks", customerNote);
            params.put("product_leadtime", Utility.readFromSharedPreference(mContext, GlobalValues.PRODUCT_LEAD_TIME));
            params.put("zero_processing", "No");
            params.put("allow_order", "Yes");
            params.put("device_type", "Android");
            params.put("ewallet_amount", eWalletAmount);



            params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);


            params.put("packaging_charge", Utility.readFromSharedPreference(mContext,GlobalValues.PACKING_CHARGE));


            Log.e("TAG","submit_order_params_paymentactivity::"+params.toString());

            if (Utility.networkCheck(mContext)) {

                String url = GlobalUrl.CYBER_SOURCE_FORM;

                new CyberSourceForm(params).execute(url);
            } else {
                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private boolean validateCard() {

        boolean validate = false;

        if (mCardName.length() <= 0) {
            validate = false;
            mValidationMessage = "Please enter card holder name";

        } else if (mCardNumber.length() <= 0) {
            validate = false;
            mValidationMessage = "Please enter card number";

        } else if (mCVV.length() <= 0) {
            validate = false;
            mValidationMessage = "Please enter cvv number";

        } else if (!isMonthSelected) {
            validate = false;
            mValidationMessage = "Please select month";

        } else if (!isYearSelected) {
            validate = false;
            mValidationMessage = "Please select year";
        } else {
            validate = true;
        }

        return validate;
    }

    private void placeCashOnDeliveryOrder() {

        try {
            JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());

           /* JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));



            JSONObject jsonPostalCodeInfo = outletJson.getJSONObject("postal_code_information");*/

            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

            StringBuilder productIdsStringBuilder = new StringBuilder();

            for (int i = 0; i < cartJsonArray.length(); i++) {
                JSONObject cartItem = cartJsonArray.getJSONObject(i);
                if (i != (cartJsonArray.length() - 1))
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty") + ";");
                else {
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty"));
                }
            }

           /* try {
                mGrandTotal = Double.parseDouble(jsonCartDetails.getString("cart_sub_total")) +
                        Double.parseDouble(outletJson.getString("zone_delivery_charge"));
            } catch (Exception e) {
                mGrandTotal = 0.0;
                e.printStackTrace();
            }*/



            try {
                String d[] = GlobalValues.DELIVERY_DATE.split("-");
                String date = d[2] + "-" + d[1] + "-" + d[0] + " " + GlobalValues.DELIVERY_TIME;

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

                Date d1 = simpleDateFormat.parse(date);

                mOrderDate = simpleDateFormat.format(d1);

            } catch (ParseException e) {
                e.printStackTrace();
            }


            params.put("app_id", GlobalValues.APP_ID);
            params.put("sub_total", sub_total);
            params.put("grand_total", grand_total);
            params.put("order_source", "Mobile");
            params.put("order_status", "1");
            params.put("products", getProductJSONArray(cartJsonArray).toString());
            params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
            params.put("customer_email", Utility.readFromSharedPreference(mContext, GlobalValues.EMAIL));
            params.put("customer_mobile_no", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));
            params.put("customer_fname", Utility.readFromSharedPreference(mContext, GlobalValues.FIRSTNAME));
            params.put("customer_lname", Utility.readFromSharedPreference(mContext, GlobalValues.LASTNAME));
            params.put("category_id", productIdsStringBuilder.toString());
            params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
            params.put("order_remarks", mOrderComments);
            params.put("order_someone_remarks", customerNote);
            params.put("product_leadtime", Utility.readFromSharedPreference(mContext, GlobalValues.PRODUCT_LEAD_TIME));
            params.put("order_voucher_discount_amount", cartVoucherDiscountAmt);
            params.put("zero_processing", "No");
            params.put("allow_order", "Yes");
            params.put("device_type", "Android");
            params.put("validation_only", "No");
            params.put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));
            params.put("reward_point_status", "Yes");
            params.put("ewallet_amount", eWalletAmount);

            if (GlobalValues.is_cutlery_checked) {
                params.put("cutleryOption", "YES");
            } else {
                params.put("cutleryOption", "NO");
            }


            if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                    GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                params.put("order_tat_time", CURRENT_TAT_TIME);
                JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));


                JSONObject jsonPostalCodeInfo = outletJson.getJSONObject("postal_code_information");

                params.put("customer_address_line1", jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
                        jsonPostalCodeInfo.optString("zip_buname") + ",");
                params.put("customer_postal_code", jsonPostalCodeInfo.optString("zip_code"));
                params.put("customer_unit_no1", mUnitNo1);
                params.put("customer_unit_no2", mUnitNo2);

                params.put("billing_address_line1", mBillingAddress);
                params.put("billing_postal_code", mBillingPincode);
                params.put("billing_unit_no1", mBillingUnitNo1);
                params.put("billing_unit_no2", mBillingUnitNo2);

                params.put("zone_id", GlobalValues.ZoneID);

            }

            params.put("tax_charge", Utility.readFromSharedPreference(mContext, GlobalValues.GstCharger));
            params.put("order_tax_calculate_amount", String.valueOf(GlobalValues.GST));


            params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);
            if (GlobalValues.isFoodVoucher) {
                params.put("delivery_charge", "0.0");
            } else {
                params.put("delivery_charge", GlobalValues.DELEIVERY_CHARGES);
            }
            params.put("additional_delivery", GlobalValues.ADDITIONAL_DELEIVERY_CHARGES);

            if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CARD")) {
                params.put("payment_mode", "3");

            } else if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CASH")) {
                params.put("payment_mode", "1");

            }else {
                JSONObject jsonObject_paymentdetails = new JSONObject();
                jsonObject_paymentdetails.put("payment_type", "netsclick");
                jsonObject_paymentdetails.put("payment_status", "Success");
                jsonObject_paymentdetails.put("trans_reference_no", Utility.readFromSharedPreference(mContext,GlobalValues.TRANS_REF_NO));
                jsonObject_paymentdetails.put("payment_reference_1", "Sushi Tei");
                jsonObject_paymentdetails.put("payment_reference_2", "");
                jsonObject_paymentdetails.put("payment_currency", "sg");

                params.put("payment_getway_details", jsonObject_paymentdetails.toString());
                params.put("payment_mode", "3");
            }

            if (GlobalValues.DISCOUNT_APPLIED) {


                params.put("discount_applied", "Yes");
                params.put("discount_amount", r_amount);

                if (isApplyPromo) {

                    if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {

                        params.put("promotion_delivery_charge_applied", "Yes");
                       /* params.put("promo_code", p_code);
                        params.put("coupon_amount", GlobalValues.DELEIVERY_CHARGES);*/

                    } else {

                        params.put("promo_code", p_code);
                        params.put("coupon_amount", p_amount);
                        params.put("coupon_applied", "Yes");
                        params.put("order_promo_sub_total", promo_subTotal);

                    }

                }
                if (isApplyRedeem) {
                    params.put("redeem_applied", "Yes");
                    params.put("redeem_point", r_point);
                    params.put("redeem_amount", r_amount);
                    params.put("order_reward_sub_total", reward_subTotal);

                    params.put("ascentiscrm_pointsredeem_applied", "Yes");
                    params.put("ascentiscrm_redeemed_points", r_point);
                    params.put("ascentiscrm_discount_amount", r_amount);
                }

                if (isApplyPromo && isApplyRedeem) {
                    params.put("order_discount_both", "1");
                }

            } else {
                params.put("discount_applied", "No");
            }


            if (GlobalValues.IS_ADVANCE_ORDER.equalsIgnoreCase("no")) {
                params.put("order_date", mOrderDate);


            } else {
                params.put("order_date", mOrderDate);
                params.put("order_advanced_date", mOrderDate);


            }
            params.put("tax_charge", GlobalValues.GstChargers);
            params.put("order_tax_calculate_amount", String.valueOf(gstAmount));
            params.put("packaging_charge", Utility.readFromSharedPreference(mContext,GlobalValues.PACKING_CHARGE));

            /*params.put("order_voucher_discount_amount", cartVoucherDiscountAmount);
            params.put("product_item_voucher_id", cartItemVoucherId);
            params.put("order_item_id", orderItemId);*/

            Log.e("TAG","submit_order_params_paymentactivity::"+params.toString());
            System.out.print(params.toString());

            if (Utility.networkCheck(mContext)) {

                String url = GlobalUrl.SUBMIT_ORDER_URL;

                new CashOnDeliverySubmitOrderTask(params).execute(url);
            } else {
                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public List<String> addYear() {

        List<String> yearList = new ArrayList<>();

        int count = 0;
        for (int i = 0; i < 25; i++) {
            Calendar cal = Calendar.getInstance();
            cal.setTime(cal.getTime());

            cal.add(Calendar.YEAR, count++);
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String now = formatter.format(cal.getTime());
            yearList.add(now.split("-")[0]);


        }
        return yearList;
    }

    public List<String> addMonth() {

        List<String> montList = new ArrayList<>();

        for (int m = 1; m <= 12; m++) {

            if (m <= 9) {
                montList.add("0" + String.valueOf(m));
            } else {
                montList.add(String.valueOf(m));
            }
        }

        return montList;
    }


    @Override
    protected void onResume() {
        super.onResume();
       /* if (!getIntent().getStringExtra("PAYMENT_TYPE").equalsIgnoreCase("NETS")) {
            btn_continue.setEnabled(true);
        }*/

        // txtTitle.setText("PAYMENT");
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }

//        btn_continue_disabled.setVisibility(View.GONE);
//        btn_continue.setVisibility(View.VISIBLE);
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_empty, menu);

        return super.onCreateOptionsMenu(menu);
    }


    private class CashOnDeliverySubmitOrderTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> orderParams;

        public CashOnDeliverySubmitOrderTask(Map<String, String> orderParams) {
            this.orderParams = orderParams;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CASH")) {

                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("TAG","cash_on_delivery_paymentactivity::"+ params[0] + "\n" + orderParams.toString());

            String response = WebserviceAssessor.postRequest(mContext, params[0], orderParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG","cash_delivery_response_paymentactivity::"+ s);
                try {

                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                        JSONObject jsonCommon = jsonObject.getJSONObject("common");

                        mOrderNo = jsonCommon.getString("local_order_no");

                        String url = GlobalUrl.DESTROY_CART_URL;
                        Map<String, String> params = new HashMap<>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

//                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (Utility.networkCheck(mContext)) {

                            new DestroyCartTask(params).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                {
                    if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CASH")) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    } else {
                        if (dialog != null) {

                            dialog.dismiss();
                        }

                        imgGifOrderProcess.setVisibility(View.GONE);
                        imgOrderProcess.setVisibility(View.VISIBLE);
                        imgOrderProcess.setImageResource(R.drawable.icon_process_complete);

                    }
                }
            }
        }
    }

    private class CyberSourceForm extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> orderParams;

        public CyberSourceForm(Map<String, String> orderParams) {
            this.orderParams = orderParams;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CASH")) {

                progressDialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("TAG","cash_on_delivery_paymentactivity::"+ params[0] + "\n" + orderParams.toString());

            String response = WebserviceAssessor.postRequest(mContext, params[0], orderParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG","cash_delivery_response_paymentactivity::"+ s);
                try {

                    JSONObject jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                        isApplyRedeem=false;
                        Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                        dialog.setContentView(R.layout.layout_uob_payment);
                        dialog.show();

                        WebView webview_uob_form=dialog.findViewById(R.id.webview_uob_form);
                        WebSettings webSettings = webview_uob_form.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        RelativeLayout layoutClose=dialog.findViewById(R.id.layoutClose);

                        webview_uob_form.setWebViewClient(new WebViewClient() {
                            @Override
                            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                                Log.e("TAG","WebviewTest::"+url);
                                return false;
                            }
                        });

                        webview_uob_form.loadData(jsonObject.getString("result_set"), "text/html; charset=UTF-8", null);

                        layoutClose.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialog.dismiss();
                            }
                        });
                       /* JSONObject jsonCommon = jsonObject.getJSONObject("common");

                        mOrderNo = jsonCommon.getString("local_order_no");*/

                        String url = GlobalUrl.DESTROY_CART_URL;
                        Map<String, String> params = new HashMap<>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

//                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (Utility.networkCheck(mContext)) {

              //              new DestroyCartTask(params).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    } else {
                        Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } catch (Exception e) {
                e.printStackTrace();
            }
           /* finally {
                {
                    if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("CASH")) {
                        if (progressDialog != null) {
                            progressDialog.dismiss();
                        }
                    } else {
                        if (dialog != null) {

                            dialog.dismiss();
                        }

                        imgGifOrderProcess.setVisibility(View.GONE);
                        imgOrderProcess.setVisibility(View.VISIBLE);
                        imgOrderProcess.setImageResource(R.drawable.icon_process_complete);

                    }
                }
            }*/
        }
    }

    public JSONArray getProductJSONArray(JSONArray cartJSONArray) {

        JSONArray submitOrderJSONArray = new JSONArray();

        try {

            for (int i = 0; i < cartJSONArray.length(); i++) {

                JSONObject cartJSONObject = cartJSONArray.getJSONObject(i);
                JSONObject productJSONObject = new JSONObject();



                String cart_item_id = cartJSONObject.getString("cart_item_id");
                String cart_item_product_id = cartJSONObject.getString("cart_item_product_id");
                String cart_item_product_name = cartJSONObject.getString("cart_item_product_name");
                String cart_item_product_sku = cartJSONObject.getString("cart_item_product_sku");
                String cart_item_product_image = cartJSONObject.getString("cart_item_product_image");
                String cart_item_qty = cartJSONObject.getString("cart_item_qty");
                String cart_item_unit_price = cartJSONObject.getString("cart_item_unit_price");
                String cart_item_total_price = cartJSONObject.getString("cart_item_total_price");
                String cart_item_type = cartJSONObject.getString("cart_item_type");
                String cart_item_special_notes = cartJSONObject.getString("cart_item_special_notes");
                String cart_item_product_voucher_gift_name = cartJSONObject.getString("cart_item_product_voucher_gift_name");
                String cart_item_product_voucher_gift_email = cartJSONObject.getString("cart_item_product_voucher_gift_email");
                String cart_item_product_voucher_gift_mobile = cartJSONObject.getString("cart_item_product_voucher_gift_mobile");
                String cart_item_product_voucher_gift_message = cartJSONObject.getString("cart_item_product_voucher_gift_message");

                String cartItemVoucherId = cartJSONObject.getString("cart_item_voucher_id");
                String orderItemId = cartJSONObject.getString("cart_voucher_order_item_id");
                String cart_item_voucher_product_free = cartJSONObject.getString("cart_item_voucher_product_free");

                //kaki discount
                try {

                    String cart_item_promotion_discount = cartJSONObject.getString("cart_item_promotion_discount");
                    productJSONObject.put("product_special_amount", cart_item_promotion_discount);

                } catch (Exception e) {
                    e.printStackTrace();
                }

                //Modifiers

                JSONArray cartModifierJSONArray = cartJSONObject.getJSONArray("modifiers");
                JSONArray reviewModifierJSONArray = new JSONArray();
                //  if (cart_item_type.equals("Modifier")) {
                try {
                    if (cartModifierJSONArray != null && cartModifierJSONArray.length() > 0) {

                        for (int j = 0; j < cartModifierJSONArray.length(); j++) {

                            JSONObject cartModifierJSONObject = cartModifierJSONArray.getJSONObject(j);
                            JSONObject modifierJSONObject = new JSONObject();


                            String cart_modifier_id = cartModifierJSONObject.getString("cart_modifier_id");
                            String cart_modifier_name = cartModifierJSONObject.getString("cart_modifier_name");

                            JSONArray cartModifierValuesJSONArray = cartModifierJSONObject.getJSONArray("modifiers_values");
                            JSONArray reviewModifierValuesJSONArray = new JSONArray();

                            for (int k = 0; k < cartModifierValuesJSONArray.length(); k++) {

                                JSONObject cartModifierSingleValueJObject = cartModifierValuesJSONArray.getJSONObject(k);
                                JSONObject modifierSingleValueJObject = new JSONObject();

                                String cart_modifiervalue_id = cartModifierSingleValueJObject.getString("cart_modifier_id");
                                String cart_modifiervalue_name = cartModifierSingleValueJObject.getString("cart_modifier_name");
                                String cart_modifiervalue_qty = cartModifierSingleValueJObject.getString("cart_modifier_qty");
                                String cart_modifiervalue_price = cartModifierSingleValueJObject.getString("cart_modifier_price");

                                modifierSingleValueJObject.put("modifier_value_id", cart_modifiervalue_id);
                                modifierSingleValueJObject.put("modifier_value_name", cart_modifiervalue_name);
                                modifierSingleValueJObject.put("modifier_value_qty", cart_modifiervalue_qty);
                                modifierSingleValueJObject.put("modifier_value_price", cart_modifiervalue_price);

                                reviewModifierValuesJSONArray.put(modifierSingleValueJObject);

                            }

                            modifierJSONObject.put("modifier_id", cart_modifier_id);
                            modifierJSONObject.put("modifier_name", cart_modifier_name);
                            modifierJSONObject.put("modifiers_values", reviewModifierValuesJSONArray);

                            reviewModifierJSONArray.put(modifierJSONObject);
                        }
                    }
                } catch (Exception e) {

                    e.printStackTrace();
                }
                //modifiers end

                //Submenu Component
                JSONArray cartSubMenuCompJSONArray = cartJSONObject.getJSONArray("set_menu_component");
                JSONArray reviewSubMenuCompJSONArray = new JSONArray();


                for (int j = 0; j < cartSubMenuCompJSONArray.length(); j++) {


                    JSONObject cartMenuComponentJSONObj = cartSubMenuCompJSONArray.getJSONObject(j);
                    JSONObject reviewMenuComponentJSONObj = new JSONObject();

                    String menu_component_id = cartMenuComponentJSONObj.getString("menu_component_id");
                    String menu_component_name = cartMenuComponentJSONObj.getString("menu_component_name");


                    JSONArray product_detailsJSONArray = cartMenuComponentJSONObj.getJSONArray("product_details");
                    JSONArray reviewProduct_detailsJSONArray = new JSONArray();

                    for (int k = 0; k < product_detailsJSONArray.length(); k++) {

                        JSONObject subProductJSONObject = product_detailsJSONArray.getJSONObject(k);
                        JSONObject reviewSubProductJSONObject = new JSONObject();

                        String product_id = subProductJSONObject.getString("cart_menu_component_product_id");
                        String product_name = subProductJSONObject.getString("cart_menu_component_product_name");
                        String product_sku = subProductJSONObject.getString("cart_menu_component_product_sku");

                        String product_qty = subProductJSONObject.getString("cart_menu_component_product_qty");
                        String product_price = subProductJSONObject.getString("cart_menu_component_product_price");

                        //  JSONArray subModifiersJSONArray =subProductJSONObject.getJSONArray("modifiers");


                        //Modifiers
                        JSONArray subModifiersJSONArray = subProductJSONObject.getJSONArray("modifiers");
                        JSONArray reviewsubModifierJSONArray = new JSONArray();
                        for (int l = 0; l < subModifiersJSONArray.length(); l++) {

                            JSONObject cartModifierJSONObject = subModifiersJSONArray.getJSONObject(l);
                            JSONObject modifierJSONObject = new JSONObject();


                            String cart_modifier_id = cartModifierJSONObject.getString("cart_modifier_id");
                            String cart_modifier_name = cartModifierJSONObject.getString("cart_modifier_name");

                            JSONArray cartModifierValuesJSONArray = cartModifierJSONObject.getJSONArray("modifiers_values");
                            JSONArray reviewModifierValuesJSONArray = new JSONArray();

                            for (int m = 0; m < cartModifierValuesJSONArray.length(); m++) {

                                JSONObject cartModifierSingleValueJObject = cartModifierValuesJSONArray.getJSONObject(m);
                                JSONObject modifierSingleValueJObject = new JSONObject();

                                String cart_modifiervalue_id = cartModifierSingleValueJObject.getString("cart_modifier_id");
                                String cart_modifiervalue_name = cartModifierSingleValueJObject.getString("cart_modifier_name");
                                String cart_modifiervalue_qty = cartModifierSingleValueJObject.getString("cart_modifier_qty");
                                String cart_modifiervalue_price = cartModifierSingleValueJObject.getString("cart_modifier_price");

                                modifierSingleValueJObject.put("modifier_value_id", cart_modifiervalue_id);
                                modifierSingleValueJObject.put("modifier_value_name", cart_modifiervalue_name);
                                modifierSingleValueJObject.put("modifier_value_qty", cart_modifiervalue_qty);
                                modifierSingleValueJObject.put("modifier_value_price", cart_modifiervalue_price);

                                reviewModifierValuesJSONArray.put(modifierSingleValueJObject);

                            }

                            modifierJSONObject.put("modifier_id", cart_modifier_id);   //key dount modifier
                            modifierJSONObject.put("modifier_name", cart_modifier_name);
                            modifierJSONObject.put("modifiers_values", reviewModifierValuesJSONArray);

                            reviewsubModifierJSONArray.put(modifierJSONObject);
                        }
                        //modifiers end


                        reviewSubProductJSONObject.put("product_id", product_id);
                        reviewSubProductJSONObject.put("product_name", product_name);
                        reviewSubProductJSONObject.put("product_sku", product_sku);

                        reviewSubProductJSONObject.put("product_qty", product_qty);
                        reviewSubProductJSONObject.put("product_price", product_price);

                        reviewSubProductJSONObject.put("modifiers", reviewsubModifierJSONArray);


                        reviewProduct_detailsJSONArray.put(reviewSubProductJSONObject);
                    }

                    reviewMenuComponentJSONObj.put("menu_component_id", menu_component_id);
                    reviewMenuComponentJSONObj.put("menu_component_name", menu_component_name);
                    reviewMenuComponentJSONObj.put("product_details", reviewProduct_detailsJSONArray);
                    //menu_component_min_max_appy
                    if (cartMenuComponentJSONObj.has("cart_menu_component_min_max_appy") &&
                            cartMenuComponentJSONObj.getString("cart_menu_component_min_max_appy") != null) {
                        reviewMenuComponentJSONObj.put("menu_component_min_max_appy", cartMenuComponentJSONObj.getString("cart_menu_component_min_max_appy"));

                    }


                    reviewSubMenuCompJSONArray.put(reviewMenuComponentJSONObj);

                }


                //submenu component end


                //Condiments

                JSONArray condimentsJSONArray = cartJSONObject.getJSONArray("condiments");
                JSONArray reviewCondimentsJSONArray = new JSONArray();

                for (int j = 0; j < condimentsJSONArray.length(); j++) {

                    JSONObject condimentJSONObject = condimentsJSONArray.getJSONObject(j);
                    JSONObject newCondimentJSONObject = new JSONObject();


                    String product_id = condimentJSONObject.getString("product_id");
                    String product_name = condimentJSONObject.getString("product_name").replace("\\", "");
                    String product_qty = condimentJSONObject.getString("product_qty");
                    String product_sku = condimentJSONObject.getString("product_sku");

                    Integer productQuantity = Integer.parseInt(product_qty);
                    Integer cartItemQuantity = Integer.parseInt(cart_item_qty);

                    newCondimentJSONObject.put("product_id", product_id);
                    newCondimentJSONObject.put("product_name", product_name);
                    newCondimentJSONObject.put("product_qty", productQuantity * cartItemQuantity);
                    newCondimentJSONObject.put("product_sku", product_sku);
                    newCondimentJSONObject.put("product_price", 0.00);

                    reviewCondimentsJSONArray.put(newCondimentJSONObject);

                }
                //condiments end

                productJSONObject.put("product_id", cart_item_product_id);
                productJSONObject.put("product_name", cart_item_product_name);
                productJSONObject.put("product_image", cart_item_product_image);
                productJSONObject.put("product_sku", cart_item_product_sku);
                productJSONObject.put("product_qty", cart_item_qty);
                productJSONObject.put("product_unit_price", cart_item_unit_price);
                productJSONObject.put("product_total_amount", cart_item_total_price);
                productJSONObject.put("modifiers", reviewModifierJSONArray);
                productJSONObject.put("menu_set_components", reviewSubMenuCompJSONArray);
                productJSONObject.put("condiments", reviewCondimentsJSONArray);
                productJSONObject.put("product_special_notes", cart_item_special_notes);
                productJSONObject.put("voucher_gift_name", cart_item_product_voucher_gift_name);
                productJSONObject.put("voucher_gift_email", cart_item_product_voucher_gift_email);
                productJSONObject.put("voucher_gift_mobile", cart_item_product_voucher_gift_mobile);
                productJSONObject.put("voucher_gift_message", cart_item_product_voucher_gift_message);
                productJSONObject.put("product_item_voucher_id", cartItemVoucherId);
                productJSONObject.put("order_item_id", orderItemId);
                productJSONObject.put("voucher_free_product", cart_item_voucher_product_free);

                submitOrderJSONArray.put(productJSONObject);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return submitOrderJSONArray;

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
                intent = new Intent(mContext, ThanksActivity.class);
                intent.putExtra("local_order_no", mOrderNo);
                startActivity(intent);
            }
        }
    }

    private class PaymaneTask extends AsyncTask<String, Void, String> {

        private Map<String, String> payParams;

        public PaymaneTask(Map<String, String> payParams) {
            this.payParams = payParams;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (dialog != null) {
                dialog.show();
            }
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], payParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("success")) {

                    params.put("closePayment", "yes");

                    imgGifPaymentProcess.setVisibility(View.VISIBLE);
                    imgPaymentProcess.setVisibility(View.GONE);


                    imgPaymentProcess.setImageResource(R.drawable.icon_process_complete);

                    JSONObject jsonObject_responseData = jsonObject.getJSONObject("responseData");
//TODO
                 /*   Intent i = new Intent(PaymentActivity.this,PaymentwebviewActivity.class);
                    i.putExtra("payment_url",jsonObject_responseData.optString("payment_url"));
                    startActivity(i);
                    finish();*/

/*
                    placeCashOnDeliveryOrder();

                    {
    "status": "success",
    "message": null,
    "responseData": {
        "created_timestamp": 1546519224,
        "expired_timestamp": 1546520123,
        "mid": "1007777937",
        "order_id": "OR20190103204023",
        "transaction_id": "OR20190103204023_130396649234378",
        "payment_url": "https://secure-dev.reddotpayment.com/service/payment/--SERVER--/759a653bbe35c2e9f324fb6d91ad08dfe1f8bfbe8bc83026d7a921ec36e3431039dbdce83a8613a7d75700d81c8b1e1a685675aa09d44a0121841dd48f6a8f53",
        "response_code": 0,
        "response_msg": "successful",
        "signature": "2f88795b165e8f63b6b91c5bca99ba67708374d81fbc84d6608a3a5b176bbf1072ecd6a7a0f0eaefe09c75ff8587808ce9f0703914998dbad3c464bb92cb0c0e"
    }
}
*/

                } else {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    if (dialog != null) {
                        dialog.dismiss();
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
                if (dialog != null) {
                    dialog.dismiss();
                }
            } finally {

            }

        }
    }

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

  /*  public void callingOnlinePaymentNewCyberSource() {
//        makeCyberSourcePayment("4111111111111111","12","2023","123");
        makeCyberSourcePayment(cardNumber,"12","2023","123");
    }
    private void makeCyberSourcePayment(String accountNo,String exMonth,String exYear,String cvv) {
        try {
            cardData = new SDKCardData.Builder(accountNo,
                    exMonth, // MM
                    exYear) // YYYY
                    .cvNumber(cvv) // Optional
//                    .type(SDKCardAccountNumberType.PAN) // Optional if unencrypted. If the value is set to a token then it is not optional and must be set to SDKCardType.TOKEN
                    .type(SDKCardAccountNumberType.TOKEN) // Optional if unencrypted. If the value is set to a token then it is not optional and must be set to SDKCardType.TOKEN
                    .build();
        } catch (SDKInvalidCardException e) {
            e.printStackTrace();
            Toast.makeText(PaymentActivity.this, "" + e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
        }

        SDKBillTo billTo = new SDKBillTo.Builder()
                .firstName("First Name")
//                .lastName("Last Name")
//                .postalCode("98052")
                .build();

        SDKTransactionObject sdkTransactionObject =
                SDKTransactionObject
                        // Type of transaction object
                        .createTransactionObject(SDKTransactionType.SDK_TRANSACTION_ENCRYPTION)
                        // Merchant reference code can be set to anything meaningful
                        .merchantReferenceCode("Android_Sample_Code")
                        // Card data to be encrypted
                        .cardData(cardData)
                        // Optional billing info
                        .billTo(billTo)
                        .build();
        // Parameters:
// 1. InAppSDKApiClient.Api - Type of API to make a request
// 2. SDKTransactionObject - The transaction object for current transaction
// 3. Signature String - Fresh message signature for this transaction. The signature generation should always occur outside of a mobile application, for security reasons.  The sample code shows this process occurring inside the application for simplicity, but that workflow should not be used in production systems.
        apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, sdkTransactionObject, generateSignature(sdkTransactionObject));

//                apiClient.performApi(InAppSDKApiClient.Api.API_ENCRYPTION, SDKTransactionObject.
//                        createTransactionObject(SDKTransactionType.SDK_TRANSACTION_ENCRYPTION) // type of transaction object
//                        .merchantReferenceCode("Android_Sample_Code" + "_" + Long.toString(System.currentTimeMillis())) // you can set it to anything meaningful
////                        .cardData(prepareDummyCardData()) // card data to be encrypted
//                        .cardData(null) // card data to be encrypted
//                        .build(), "sdfskjdfs");


    }

    private String generateSignature(SDKTransactionObject sdkTransactionObject) {
        return "test" + new Random(100000);
    }*/

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


                    if (!rewardPointsPayment) {

                        GlobalValues.PAYMENT_TYPE = "CARD";
                        Intent intent = new Intent(PaymentActivity.this, PaymentProcessingActivity.class);

                        intent.putExtra("FROM", "CARD_PAYMENT");

                        String customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);


                        intent.putExtra("TYPE", "NEW_CARD");
                        intent.putExtra("OMISE_TOKEN", token.id);
                        intent.putExtra("CARD_NUMBER", cardNumber);
                        intent.putExtra("CARD_EXP_MONTH", mMonth);
                        intent.putExtra("CARD_EXP_YEAR", mYear);
                        intent.putExtra("CARD_CVC", cvvNumber);
                        intent.putExtra("CUSTOMER_NAME", edtCVVNumber.getText().toString());
                        intent.putExtra("Total", grand_total);
                        intent.putExtra("sub_total", sub_total);
                        intent.putExtra("reward_subTotal", reward_subTotal);
                        intent.putExtra("promo_subTotal", promo_subTotal);


                        boolean saveCard = saveCardCheckBox.isChecked();

                        if (saveCard) {
                            intent.putExtra("SAVE_CARD", true);
                        } else {
                            intent.putExtra("SAVE_CARD", false);
                        }


                        intent.putExtra("sub_total", sub_total);
                        intent.putExtra("total_price", grand_total);
                        intent.putExtra("unit_no1", mUnitNo1);
                        intent.putExtra("unit_no2", mUnitNo2);
                        intent.putExtra("order_remarks", mOrderComments);
                        intent.putExtra("REDEEM_APPLIED", r_applied);
                        intent.putExtra("REDEEM_POINT", r_point);
                        intent.putExtra("redeem_amount", r_amount);

                        intent.putExtra("ewallet_amount", eWalletAmount);

                        intent.putExtra("promo_code", p_code);
                        intent.putExtra("promo_amount", p_amount);
                        intent.putExtra("AdditionalNotes", customerNote);
                        intent.putExtra("cartVoucherDiscountAmt", cartVoucherDiscountAmt);

                        //intent.putExtra("loyality_point_price", loyality_point_price);
                        startActivity(intent);
                    } else {
                        String customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                        FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);
//Todo surya
                        formBuilder.add("customer_id", customerId);

                        formBuilder.add("paid_amount", loyality_point_price);

                        formBuilder.add("token", token.id);

                        boolean saveCard = saveCardCheckBox.isChecked();

                        if (saveCard) {
                            formBuilder.add("create_customer", "yes");
                            formBuilder.add("card_id", "");
                        } else {
                            formBuilder.add("create_customer", "no");
                            formBuilder.add("card_id", "");
                        }
                        formBuilder.add("request_type", "");
                        formBuilder.add("outlet_name", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));

                        FormBody formBody = formBuilder.build();

                        webAuthotizeTask = new WebAuthotizeTask(formBody);
                        webAuthotizeTask.execute();
                    }
                }

                @Override
                public void onTokenRequestFailed(TokenRequest request, Throwable throwable) {

                    Toast.makeText(PaymentActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();


                    btn_continue.setEnabled(true);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //WebAuthorize Client
    final OkHttpClient webAuthorizeClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public String webAuthorizeRun(RequestBody formBody) throws Exception {
        Request request;
        request = new Request.Builder()
                .url(GlobalUrl.walletAuthorizeURL)
                .post(formBody)
                .build();

        Response response = webAuthorizeClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;
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

                        FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);

                        formBuilder.add("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                        //TODO surya
                        formBuilder.add("token", mPrevCaptureId);


                        // formBuilder.add("loyality_points", GlobalValues.eWalletAmount.replace("$", ""));

                        formBuilder.add("log_id", result_set.getString("log_id"));
                        //formBuilder.add("paid_amount", loyality_point_price);

                        formBuilder.add("paid_amount", GlobalValues.eWalletAmount.replace("$", ""));

                        formBuilder.add("outlet_name", "test");

                        FormBody formBody = formBuilder.build();

                        new WebChargeTask(formBody).execute();

                    } else {
                        progressDialog.dismiss();
                        if (jsonObject.has("form_error")) {
                            String formError = jsonObject.getString("form_error");
                            Toast.makeText(PaymentActivity.this, Html.fromHtml(formError), Toast.LENGTH_LONG).show();
                        } else {
                            message = jsonObject.getString("message");
                            Toast.makeText(PaymentActivity.this, "Something went wrong!", Toast.LENGTH_LONG).show();

                        }
                    }
                } catch (Exception e) {
                    progressDialog.dismiss();
                    e.printStackTrace();
                    Toast.makeText(PaymentActivity.this, "There seems to be a network issue.", Toast.LENGTH_SHORT).show();
                }
            } else {
                progressDialog.dismiss();

                Toast.makeText(PaymentActivity.this, "There seems to be a network issue.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    //WebCharge Client
    final OkHttpClient webChargeClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public String webChargeRun(RequestBody formBody) throws Exception {

        Request request = new Request.Builder()
                .url(GlobalUrl.walletChargeURL)
                .post(formBody)
                .build();


        Response response = webChargeClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;
    }

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
                        Toast.makeText(PaymentActivity.this, "Payment done Wallet points updated successfully", Toast.LENGTH_SHORT).show();
                        intent = new Intent(mContext, FiveMenuActivityNew.class);
                        //intent.putExtra("position", 2);
                        intent.putExtra("position", 0);
                        intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                        startActivity(intent);
                        finish();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();

                    //Toast.makeText(PaymentActivity.this, "Payment UnSuccessful", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    public void passSavedCard(ArrayList<Cards> cardsArrayLists, int position) {

        final String Cardnumbers = cardsArrayLists.get(position).getId();
        String name = cardsArrayLists.get(position).getName();
        String month = cardsArrayLists.get(position).getExp_month();
        String year = cardsArrayLists.get(position).getExp_year();
        // String cvv=cardsArrayLists.get(position).getCvc_check();

        if (!rewardPointsPayment) {
            GlobalValues.PAYMENT_TYPE = "CARD";
            Intent intent = new Intent(PaymentActivity.this, PaymentProcessingActivity.class);

            intent.putExtra("FROM", "CARD_PAYMENT");

            String customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);


            intent.putExtra("TYPE", "SAVEDCARD");
            intent.putExtra("OMISE_TOKEN", "");
            intent.putExtra("CARD_NUMBER", Cardnumbers);
            intent.putExtra("CARD_EXP_MONTH", mMonth);
            intent.putExtra("CARD_EXP_YEAR", mYear);
            intent.putExtra("CARD_CVC", cvvNumber);
            intent.putExtra("CUSTOMER_NAME", edtCVVNumber.getText().toString());
            intent.putExtra("Total", grand_total);
            intent.putExtra("sub_total", sub_total);

            intent.putExtra("SAVE_CARD", false);

            intent.putExtra("sub_total", sub_total);
            intent.putExtra("total_price", grand_total);
            intent.putExtra("unit_no1", mUnitNo1);
            intent.putExtra("unit_no2", mUnitNo2);
            intent.putExtra("order_remarks", mOrderComments);
            intent.putExtra("REDEEM_APPLIED", r_applied);
            intent.putExtra("REDEEM_POINT", r_point);
            intent.putExtra("redeem_amount", r_amount);

            intent.putExtra("ewallet_amount", eWalletAmount);


            intent.putExtra("promo_code", p_code);
            intent.putExtra("promo_amount", p_amount);
            intent.putExtra("AdditionalNotes", customerNote);
            intent.putExtra("reward_subTotal", reward_subTotal);
            intent.putExtra("promo_subTotal", promo_subTotal);
            intent.putExtra("cartVoucherDiscountAmt", cartVoucherDiscountAmt);
            //intent.putExtra("loyality_point_price", loyality_point_price);

            startActivity(intent);
        } else {

            String customerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            FormBody.Builder formBuilder = new FormBody.Builder().add("app_id", GlobalValues.APP_ID);
//Todo surya
            formBuilder.add("customer_id", customerId);

            formBuilder.add("paid_amount", loyality_point_price);

            //formBuilder.add("token", token.id);
            formBuilder.add("create_customer", "no");
            formBuilder.add("card_id", Cardnumbers);
            formBuilder.add("request_type", "");
            formBuilder.add("outlet_name", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));

            FormBody formBody = formBuilder.build();

            webAuthotizeTask = new WebAuthotizeTask(formBody);
            webAuthotizeTask.execute();

        }
    }

    private void checkCardDetails() {


        if (Utility.networkCheck(mContext)) {


            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("app_id", GlobalValues.APP_ID);
            formBuilder.add("reference", "Nelsonbar");

            Customer_Id = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            formBuilder.add("customer_id", Customer_Id);



            FormBody formBody = formBuilder.build();
            GlobalValues.PAYMENT_TYPE = "CARD";

            GetSaveCardsTask getSaveCardsTask = new GetSaveCardsTask(formBody);
            getSaveCardsTask.execute();

            //  new ChargeListTask().execute();
        } else {
            Toast.makeText(PaymentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }
    }

    //SaveCardClient
    final OkHttpClient SaveCardClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(50, TimeUnit.SECONDS)
            .build();

    public String webSavecardRun(RequestBody formBody) throws Exception {

        Request request = new Request.Builder()
                .url(GlobalUrl.webSavecardURL)
                .post(formBody)
                .build();


        Response response = SaveCardClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;
    }

    /* public void saveCreditCard(PaymentForm form) {

         Card card = new Card(
                 form.getCardNumber(),
                 form.getExpMonth(),
                 form.getExpYear(),
                 form.getCvc());
         card.setName(edtCardName.getText().toString());

         boolean validation = card.validateCard();

         if (validation) {
             GlobalValues.PAYMENT_TYPE = "CARD";


             Intent intent = new Intent(PaymentSelectActivity.this, PaymentProcessingActivity.class);

             intent.putExtra("FROM", "CARD_PAYMENT");

             SharedPreferences stripeCustomerSharedPref = getSharedPreferences(
                     "Customer_card_detail", Context.MODE_PRIVATE);

             String customerId = stripeCustomerSharedPref.getString("CUSTOMER_ID", "");


             if (customerId == null || customerId.equals(""))
             {
                 intent.putExtra("TYPE", "CARD_BASED");
             } else {
                 intent.putExtra("TYPE", "CUSTOMER_BASED");
             }


             intent.putExtra("TYPE", "CARD_BASED");

             intent.putExtra("CARD_NUMBER", form.getCardNumber());
             intent.putExtra("CARD_EXP_MONTH", form.getExpMonth());
             intent.putExtra("CARD_EXP_YEAR", form.getExpYear());
             intent.putExtra("CARD_CVC", form.getCvc());
             intent.putExtra("CUSTOMER_NAME", edtCVVNumber.getText().toString());
             intent.putExtra("Total", grand_total);
             intent.putExtra("sub_total", sub_total);

             boolean saveCard = saveCardCheckBox.isChecked();

             if (saveCard) {
                 intent.putExtra("SAVE_CARD", true);
             } else {
                 intent.putExtra("SAVE_CARD", false);
             }


             startActivity(intent);

         } else if (!card.validateNumber()) {
             handleError(getResources().getString(R.string.cardnumberyouenteredisinvaild));
             dismisProgress();
         } else if (!card.validateExpiryDate()) {
             handleError(getResources().getString(R.string.expdatedisinvaild));
             dismisProgress();
         } else if (!card.validateCVC()) {
             handleError(getResources().getString(R.string.cvvcodeisinvaild));
             dismisProgress();
         } else {
             handleError(getResources().getString(R.string.cardisinvaild));
             dismisProgress();
         }


     }


     private void handleError(String error) {
         DialogFragment fragment = ErrorDialogFragment.newInstance(R.string.validationErrors, error);
         fragment.show(getSupportFragmentManager(), "error");
     }

     public void dismisProgress() {
         if (progressFragment != null) {
             //  progressFragment.dismiss();
         }
     }
 */
    public class GetSaveCardsTask extends AsyncTask<String, String, String> {

        FormBody formBody;
        String response, status, message;
        ProgressDialog progressDialog;

        GetSaveCardsTask(FormBody formBody) {
            this.formBody = formBody;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(PaymentActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = webSavecardRun(formBody);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (response != null) {

                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();

                cardsArrayLists.clear();

                try {
                    JSONObject jsonObject = new JSONObject(response);

                    status = jsonObject.getString("status");

                    if (status.equalsIgnoreCase("ok")) {

                        JSONObject jsonObjectresultSet = jsonObject.getJSONObject("result_set");


                        JSONArray jsonArray = jsonObjectresultSet.getJSONArray("cards_list");


                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject jsonObjectcarddetails = jsonArray.getJSONObject(i);

                            Cards cards = new Cards();

                            String id = jsonObjectcarddetails.getString("id");
                         /*   String object = jsonObjectcarddetails.getString("object");
                            String address_city = jsonObjectcarddetails.getString("address_city");
                            String address_country = jsonObjectcarddetails.getString("address_country");
                            String address_line1 = jsonObjectcarddetails.getString("address_line1");
                            String address_line1_check = jsonObjectcarddetails.getString("address_line1_check");
                            String address_line2 = jsonObjectcarddetails.getString("address_line2");
                            String address_state = jsonObjectcarddetails.getString("address_state");
                            String address_zip = jsonObjectcarddetails.getString("address_zip");
                            String address_zip_check = jsonObjectcarddetails.getString("address_zip_check");
                            String brand = jsonObjectcarddetails.getString("brand");
                            String country = jsonObjectcarddetails.getString("country");*/
                            String name = jsonObjectcarddetails.getString("name");
                            // String cvc_check = jsonObjectcarddetails.getString("cvc_check");
                            String exp_month = jsonObjectcarddetails.getString("expiration_month");
                            String exp_year = jsonObjectcarddetails.getString("expiration_year");
                            String last4 = jsonObjectcarddetails.getString("last_digits");

                            cards.setId(id);
                            cards.setName(name);
                            cards.setExp_month(exp_month);
                            cards.setExp_year(exp_year);
                            cards.setLast4(last4);


                            cardsArrayLists.add(cards);
                        }

                        CreditCardAdapter creditCardAdapter = new CreditCardAdapter(PaymentActivity.this, cardsArrayLists);
                        cardRecyclerView.setAdapter(creditCardAdapter);
                        layoutManager = new LinearLayoutManager(mContext);
                        cardRecyclerView.setLayoutManager(layoutManager);
                        cardRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        cardRecyclerView.setNestedScrollingEnabled(false);


                        if (cardsArrayLists.size() > 0) {
                            layoutVisa.setVisibility(View.GONE);
                            view_card_detials.setVisibility(View.VISIBLE);
                            cardRecyclerView.setVisibility(View.VISIBLE);
                            txt_noSavedCards.setVisibility(View.GONE);
                            tabLayout.getTabAt(0).select();
                            savedCard.setTextColor(getResources().getColor(R.color.colorWhite));
                            savedCard.setBackgroundResource(R.drawable.button_back_darkorange);
                            imgVisaCard.setImageResource(R.drawable.icon_visacard_unselect);
                        } else {

                            layoutCashPayment.setVisibility(View.GONE);
                            cardRecyclerView.setVisibility(View.GONE);
                            txt_noSavedCards.setVisibility(View.VISIBLE);
                            tabLayout.getTabAt(1).select();
                            savedCard.setBackgroundResource(R.drawable.button_back_border);
                            savedCard.setTextColor(getResources().getColor(R.color.colorBlack));
                            imgVisaCard.setImageResource(R.drawable.icon_visacard_select);
                        }
                    } else {
                        layoutCashPayment.setVisibility(View.GONE);
                        layoutVisa.setVisibility(View.VISIBLE);
                        tabLayout.getTabAt(1).select();
                        savedCard.setBackgroundResource(R.drawable.button_back_border);
                        savedCard.setTextColor(getResources().getColor(R.color.colorBlack));
                        imgVisaCard.setImageResource(R.drawable.icon_visacard_select);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    layoutCashPayment.setVisibility(View.GONE);
                    layoutVisa.setVisibility(View.VISIBLE);
                    tabLayout.getTabAt(1).select();
                    savedCard.setBackgroundResource(R.drawable.button_back_border);
                    savedCard.setTextColor(getResources().getColor(R.color.colorBlack));
                    imgVisaCard.setImageResource(R.drawable.icon_visacard_select);
                }
            } else {

                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();
                layoutCashPayment.setVisibility(View.GONE);
                layoutVisa.setVisibility(View.VISIBLE);
                tabLayout.getTabAt(1).select();
                savedCard.setBackgroundResource(R.drawable.button_back_border);
                savedCard.setTextColor(getResources().getColor(R.color.colorBlack));
                imgVisaCard.setImageResource(R.drawable.icon_visacard_select);
            }
        }
    }

    public void deleteCard(String id, int position) {
        if (Utility.networkCheck(mContext)) {


            FormBody.Builder formBuilder = new FormBody.Builder()
                    .add("app_id", GlobalValues.APP_ID);
            formBuilder.add("payment_reference", "Sushi Tei");

            Customer_Id = Utility.readFromSharedPreference(PaymentActivity.this, GlobalValues.CUSTOMERID);
            formBuilder.add("customer_id", Customer_Id);
            formBuilder.add("card_id", id);

            FormBody formBody = formBuilder.build();

            DeleteCardsTask getSaveCardsTask = new DeleteCardsTask(formBody);
            getSaveCardsTask.execute();

            //  new ChargeListTask().execute();
        } else {
            Toast.makeText(PaymentActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();
        }


    }

    public class DeleteCardsTask extends AsyncTask<String, String, String> {

        FormBody formBody;
        String response, status, message;
        ProgressDialog progressDialog;

        DeleteCardsTask(FormBody formBody) {
            this.formBody = formBody;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(PaymentActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            try {
                response = webDeletRun(formBody);
            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (response != null) {

                if (progressDialog != null && progressDialog.isShowing())
                    progressDialog.dismiss();

                checkCardDetails();


            }


        }
    }

    public String webDeletRun(RequestBody formBody) throws Exception {

        Request request = new Request.Builder()
                .url(GlobalUrl.DELET_CARD_URL)
                .post(formBody)
                .build();


        Response response = SaveCardClient.newCall(request).execute();
        //   if (!response.isSuccessful()) throw new IOException("Unexpected code " + response);
        String resp = response.body().string();

        return resp;

    }

    void getCardExpDate() {

        /*
        // Get Current Date
        final Calendar c = Calendar.getInstance();
        expYear = c.get(Calendar.YEAR);
        expMonth = c.get(Calendar.MONTH);
        expDay = c.get(Calendar.DAY_OF_MONTH);
        final String[] MONTH = {"Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov", "Dec"};

        DatePickerDialog datePickerDialog = new DatePickerDialog(mContext, R.style.DatePickerTheme,
                new DatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        String month = String.format("%02d", (monthOfYear + 1));
                        String exp_year = String.valueOf(year).substring(2, 4);
                        mYear = String.valueOf(year);
                        mMonth = month;
                        txt_expDate.setText(String.valueOf((month) + "/" + exp_year));
                    }
                }, expYear, expMonth, expDay);
        datePickerDialog.getDatePicker().setMinDate(c.getTimeInMillis());

        datePickerDialog.show();
        datePickerDialog.getButton(DatePickerDialog.BUTTON_POSITIVE).setTextColor(Color.BLACK);
        datePickerDialog.getButton(DatePickerDialog.BUTTON_NEGATIVE).setTextColor(Color.BLACK);*/
    }

    private DatePickerDialog createDialogWithoutDateField() {
        DatePickerDialog dpd = new DatePickerDialog(this, null, 2014, 1, 24);
        try {
            java.lang.reflect.Field[] datePickerDialogFields = dpd.getClass().getDeclaredFields();
            for (java.lang.reflect.Field datePickerDialogField : datePickerDialogFields) {
                if (datePickerDialogField.getName().equals("mDatePicker")) {
                    datePickerDialogField.setAccessible(true);
                    DatePicker datePicker = (DatePicker) datePickerDialogField.get(dpd);
                    java.lang.reflect.Field[] datePickerFields = datePickerDialogField.getType().getDeclaredFields();
                    for (java.lang.reflect.Field datePickerField : datePickerFields) {

                        if ("mDaySpinner".equals(datePickerField.getName())) {
                            datePickerField.setAccessible(true);
                            Object dayPicker = datePickerField.get(datePicker);
                            ((View) dayPicker).setVisibility(View.GONE);
                        }
                    }
                }
            }
        } catch (Exception ex) {
        }
        return dpd;
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    private void openPaymentDialog() {
        dialogBottom = new Dialog(PaymentActivity.this);
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
                MonthPickerDialog.Builder builder = new MonthPickerDialog.Builder(PaymentActivity.this, new MonthPickerDialog.OnDateSetListener() {
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
                finish();
            }
        });

        btn_continue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                /*progressDialog = new ProgressDialog(mContext);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();*/
                if (SystemClock.elapsedRealtime() - mLastClickTime < 1000) {
                    return;
                }

                mLastClickTime = SystemClock.elapsedRealtime();
//                cardName = edtCardName.getText().toString();
//                cardNumber = edtCardNumber.getText().toString();
//                cvvNumber = edtCVVNumber.getText().toString();

                cardName = edtCardName_new.getText().toString();
                cardNumber = edtCardNumber_new.getText().toString();
                cvvNumber = edtCVVNumber_new.getText().toString();

                if (onlinePaymentValidation()) {
                    btn_continue_disabled.setVisibility(View.VISIBLE);
                    btn_continue.setVisibility(View.GONE);
                    if (Utility.networkCheck(mContext)) {

                        callingOnlinePaymentOldOmise();
//                        callingOnlinePaymentNewCyberSource();
                        btn_continue.setEnabled(false);
                    } else {
                       /* if (progressDialog.isShowing()) {
                            progressDialog.dismiss();
                        }*/
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, mMessage, Toast.LENGTH_SHORT).show();
                    /*if (progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }*/
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

   /* public void initializeCyberSource(){
        // Parameters:
// 1) Context - current context
// 2) InAppSDKApiClient.Environment - CYBS ENVIRONMENT
// 3) API_LOGIN_ID String - merchant's API LOGIN ID
        apiClient = new InAppSDKApiClient.Builder(PaymentActivity.this,
                InAppSDKApiClient.Environment.ENV_TEST, "test")
                .sdkApiProdEndpoint(PAYMENTS_PROD_URL) // option to configure PROD Endpoint
                .sdkApiTestEndpoint(PAYMENTS_TEST_URL) // option to configure TEST Endpoint
                .sdkConnectionTimeout(5000) // optional connection time out in milliseconds
//                .transactionNamespace(TRANSACT_NAMESPACE) // optional
                .sdkConnectionCallback(new SDKApiConnectionCallback() {
                    @Override
                    public void onErrorReceived(SDKError sdkError) {
                        Toast.makeText(PaymentActivity.this,
                                sdkError.getErrorExtraMessage().toString(),
                                Toast.LENGTH_LONG)
                                .show();
                    }

                    @Override
                    public void onApiConnectionFinished(SDKGatewayResponse sdkGatewayResponse) {
                        Toast.makeText(PaymentActivity.this,
                                sdkGatewayResponse.getType() + " : " + sdkGatewayResponse.getDecision().toString(),
                                Toast.LENGTH_LONG)
                                .show();
                    }
                }) // receive callbacks for connection results

                .build();
    }*/
}
