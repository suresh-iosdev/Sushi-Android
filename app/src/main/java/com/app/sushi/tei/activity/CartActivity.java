package com.app.sushi.tei.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.ICartItemClick;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Account.SecondaryAddress;
import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.Model.Cart.CartModifier;
import com.app.sushi.tei.Model.Cart.CartModifierValue;
import com.app.sushi.tei.Model.Home.Holidays;
import com.app.sushi.tei.Model.Home.SlotTime;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.SelectedModifierHeading;
import com.app.sushi.tei.Model.ProductList.SelectedModifierValue;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuProduct;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.AddOnsRecyclerAdapter;
import com.app.sushi.tei.adapter.Cart.CartRecyclerAdapter;
import com.app.sushi.tei.adapter.Cart.SetMenuTitleRecyclerCartAdapter;
import com.app.sushi.tei.adapter.GridAdapter;
import com.app.sushi.tei.adapter.MyAccount.SingleSelectAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuChildRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuTitleRecyclerAdapter;
import com.app.sushi.tei.adapter.SetMenuAdapter.SetMenuTitleRecyclerAdapterNew;
import com.app.sushi.tei.adapter.TimeAdapter;
import com.app.sushi.tei.dialog.ChangeOutletDialog;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.ClearCartMessageDialog;
import com.app.sushi.tei.dialog.MessageDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.CURRENT_AVAILABLITY_ID;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.OUTLET_DELIVERY_TAT;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.OUTLET_DELIVERY_TIMING;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.OUTLET_PICKUP_TAT;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.is_cutlery_checked;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.time_slot_type;
import static com.app.sushi.tei.Model.ProductList.ModifierHeading.subModifierPrice;
import static com.app.sushi.tei.activity.HomeActivity.mModifierPrice;
import static com.app.sushi.tei.activity.HomeActivity.mSearchProuductprise;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuBasePrice;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuPrice;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuQuantity;
import static com.app.sushi.tei.activity.HomeActivity.mquanititycost_src;
import static com.app.sushi.tei.activity.HomeActivity.quantityCost;
import static com.app.sushi.tei.activity.HomeActivity.switchoutletfromCartActivity;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetmenuoverallprices;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtModifierPrice;
import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyPromo;
import static com.app.sushi.tei.activity.OrderSummaryActivity.isApplyRedeem;
import static com.app.sushi.tei.activity.SubCategoryActivity.value;
import static java.text.DateFormat.MEDIUM;

public class CartActivity extends AppCompatActivity implements View.OnClickListener {

    public static final int INITIAL = 0;
    public static final int DIALOG = 1;
    public int flow = INITIAL;

    private int posSetMenu = 0, posModifier = 0;
    // private ProgressBar progressBar;
    private Context mContext;
    private String cartCount = "";
    SingleSelectAdapter singleSelectAdapter;
    ArrayList<SecondaryAddress> secondaryAddressArrayList = new ArrayList<>();
    public static int pos = -1;
    private SimpleDateFormat twelvetimeformat = new SimpleDateFormat("HH:mm");
    private String mValidationMessage = "";
    Boolean iEnableSecondarylist = false;
    private Toolbar toolbar;
    private ImageView imgChecked;
    private LinearLayout imgBack;
    private ImageView txtTitle;
    private EditText edtComments, edtBillingAddress, edtPincode, edtBillingUnitNo1, edtBillingUnitNo2;
    private RecyclerView orderRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartRecyclerAdapter cartAdapter;
    private TextView txtDate, txtTime;
    private EditText edtPromotion;
    public TextView txtSubTotal, txtSubTotalSymbol, txtDeliveryCharge, txtAdditionalDeliveryCharge, txtTotal, txtTotalSymbol;
    private TextView layoutContinue;
    private LinearLayout layoutCartDate, layoutCartTime, layoutSubTotal, layoutDeliveryCharge, layoutAdditionalDeliveryCharge, layoutGst, layoutGrandTotal;
    private SpannableString spannableDate, spannableTime, promotionspannableString;
    private boolean isSelectTime = false, is_time_shown = false, is_date_shown = false;
    private RecyclerView timeRecyclerView;
    private Intent intent;
    private String mPromotion = "";
    private ArrayList<SlotTime> slotTimeArrayList;
    private ArrayList<SlotTime> sundayArrayList;
    private ArrayList<SlotTime> mondayArrayList;
    private ArrayList<SlotTime> tuesdayArrayList;
    private ArrayList<SlotTime> wednesdayArrayList;
    private ArrayList<SlotTime> thursdayArrayList;
    private ArrayList<SlotTime> fridayArrayList;
    private ArrayList<SlotTime> satdayArrayList;
    private List<Cart> cartList;
    private List<String> timeList;
    private Date currentDate, selecteddate;
    private SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
    private SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
    int intervaltime;
    private String mCustomerId = "", mReferenceId = "";
    private String mOrderDate = "";
    private String mUnitNo1 = "", mUnitNo2 = "";
    public static double mGrandTotal = 0.0;
    public static double mGrandTotalGst = 0.0;
    private JSONObject outletZoneJson;
    private DatabaseHandler databaseHandler;
    public static int cutOffTime;
    private int mTatTime = 0;
    private boolean is_break = false;
    private String r_applied, r_point, r_amount;
    private double cart_sub_total, cart_deleivery_charge, cart_adddeleivery_charge;
    //Calendar
    TextView previousButton, nextButton;
    private TextView currentDateText;
    private GridView calendarGridView;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    //private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    List<Date> dayValueInCells;
    public static Date currentday, maxDate;
    public int mMinDate, mMaxDate;
    public List<Holidays> holidaysList;
    public List<String> slotDaysList;
    private static String todayString = "";
    private boolean isHoliday = false, isSlotDay = false;
    public int mselectedDay;
    private Calendar min;
    //by gowtham
    // private TextView txtRedeem, txtPromotions, txtDiscountTotal, txtRedeemPoints, txtRedeemApply, txtPromoCode, txtPromoApply;
    // private RelativeLayout layoutdiscount;
    private String subtotal_value;
    private String r_sub_total;
    private String mSecondaryAddressId = "";
    private String mPromoCoupon = "", mPromotionAmount = "", mPromoCouponResponse = "", mOrderNo = "";
    private double mrewardPoint = 0.0;
    private double mCarttotal = 0.0;
    private JSONObject jsonCartObject;
    private int fromChangeAddress = 0;
    private boolean isAddOnsVisited = false;
    private double mGST = 0.0;
    private String mProductId = "", mProductQuantity = "1";
    private SetMenuProduct setMenuProduct;
    public Dialog dialog;
    private String mBasePath = "", galleryBasePath = "";
    private int CurrentPosition = 0;
    private Cart productCartDetails;
    private TextView txtModi;
    private TextView kitchennote, clearcart;
    private TextView txt_insulsive_gst, txt_insulsive_gstSymbol;
    private static Double gstAmount;
    private String kicthen_notes = "";
    public RelativeLayout layoutTime;
    public RelativeLayout layoutCalendar;
    public TextView txtDeliveryDate, txtDeliveryTime, outletText;
    public boolean isDateDisplay = false;
    private RelativeLayout layoutCustomTime;
    private GridAdapter mAdapter;
    private boolean oncreateTimeOnly = true, oncreateTimeOnly2 = true;
    private LinearLayout favouriteLayout;
    private TextView favouriteText;
    private EditText notesText, notesText1;
    private String mProductFavPrimaryId = "null", subCatString;
    private String StatusFav = "0";
    private SetMenuTitleRecyclerAdapterNew setMenuTitleRecyclerAdapternew;
    private String TotalPriceSetMenu;
    private ImageView img_chooseOutlet;
    Double total_unitprices = 0.0;
    public static String mAliasProductPrimaryId = "";
    public int counting = 1;
    private TextView txt_delivery_disable, txt_takeaway, txt_delivery, txt_takeaway_disable;
    private TextView txtOutletName, txtAddress, txtFreeDelivery, txtEmpty, txtTakeawayName, txtDiscountLabel, txtGSTLabel;
    private EditText edtUnitNo2, edtUnitNo1;
    private LinearLayout layoutDelivery, layoutTakeaway, layout_changeAddress, layout_delivery_enable, layout_takeaway_enable;
    private LinearLayout specialinstructionlayout;
    private RelativeLayout layout_spclInstruction;
    private TextView txtGstLabel, txt_address;
    private TextView txt_cartTimeLabel, txt_cartDateLabel;
    private String subTotal;
    public static boolean foodVoucher = false;
    private boolean OutletPage = false;
    private String cartVoucherDiscountAmt;
    private LinearLayout layoutVoucher;
    private TextView voucherPrice;
    private double deliveryCharges = 0.0;
    private double additionalDeliveryCharges = 0.0;
    private int tQuantity;
    private double plusminusPrice;
    private String cartSplNotes;
    private ToggleButton cutlery_check;
    private String cutlery_value = "";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        mContext = CartActivity.this;
        databaseHandler = DatabaseHandler.getInstance(mContext);
        GlobalValues.DISCOUNT_APPLIED = false;
        GlobalValues.PRMOTION_DELIVERY_APPLIED = false;
        isAddOnsVisited = false;

        flow = INITIAL;

        CheckAddresList();
        cutlery_check = findViewById(R.id.cutlery_check);
        is_cutlery_checked = false;

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        txt_address = findViewById(R.id.txt_address);
        imgBack = findViewById(R.id.toolbarBack);
        txtTitle = toolbar.findViewById(R.id.toolbartxtTitle);
        txt_cartTimeLabel = findViewById(R.id.txt_cartTimeLabel);
        txt_cartDateLabel = findViewById(R.id.txt_cartDateLabel);
        orderRecyclerView = (RecyclerView) findViewById(R.id.ordersRecyclerView);
        layoutContinue = findViewById(R.id.continues);
        layoutDelivery = findViewById(R.id.layoutDelivery);
        layoutTakeaway = findViewById(R.id.layoutTakeaway);
        layoutDeliveryCharge = findViewById(R.id.layoutDeliveryCharge);
        layoutAdditionalDeliveryCharge = findViewById(R.id.layoutAdditionalDeliveryCharge);
        layoutGst = findViewById(R.id.layoutGst);
        layoutSubTotal = findViewById(R.id.layoutSubTotal);
        layoutGrandTotal = findViewById(R.id.layoutGrandTotal);
        layoutCartDate = findViewById(R.id.layoutCartDate);
        layoutCartTime = findViewById(R.id.layoutCartTime);
        txt_insulsive_gst = (TextView) findViewById(R.id.txt_insulsive_gst);
        txt_insulsive_gstSymbol = (TextView) findViewById(R.id.txt_insulsive_gstSymbol);
        txtTakeawayName = findViewById(R.id.outletText);
        txtSubTotal = (TextView) findViewById(R.id.txtSubTotal);
        txtSubTotalSymbol = (TextView) findViewById(R.id.txtSubTotalSymbol);
        txtDeliveryCharge = findViewById(R.id.txtDeliveryCharge);
        txtAdditionalDeliveryCharge = findViewById(R.id.txtAdditionalDeliveryCharge);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtTotalSymbol = (TextView) findViewById(R.id.txtTotalSymbol);
        img_chooseOutlet = findViewById(R.id.img_chooseOutlet);
        edtPromotion = (EditText) findViewById(R.id.edtPromotion);
        txtOutletName = findViewById(R.id.txtOutletName);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtFreeDelivery = (TextView) findViewById(R.id.txtFreeDelivery);
        txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        imgChecked = (ImageView) findViewById(R.id.imgChecked);
        clearcart = findViewById(R.id.clearallcartitems);
        kitchennote = findViewById(R.id.kitchennote11);
        txt_takeaway = findViewById(R.id.txt_takeaway);
        txt_delivery = findViewById(R.id.txt_delivery);
        txt_takeaway_disable = findViewById(R.id.txt_takeaway_disable);
        txt_delivery_disable = findViewById(R.id.txt_delivery_disable);
        edtUnitNo1 = (EditText) findViewById(R.id.edtUnitNo1);
        edtUnitNo2 = (EditText) findViewById(R.id.edtUnitNo2);
        edtComments = (EditText) findViewById(R.id.edtComments);
        edtBillingAddress = (EditText) findViewById(R.id.edtBillingAddress);
        edtPincode = (EditText) findViewById(R.id.edtPincode);
        layout_changeAddress = findViewById(R.id.layout_changeAddress);
        specialinstructionlayout = findViewById(R.id.specialinstructionlayout);
        layout_spclInstruction = findViewById(R.id.layout_spclInstruction);
        layout_delivery_enable = findViewById(R.id.layout_delivery_enable);
        layout_takeaway_enable = findViewById(R.id.layout_takeaway_enable);
        txtGSTLabel = findViewById(R.id.txtGSTLabel);
        txtGstLabel = findViewById(R.id.txtGstLabel);
        txtDate = (TextView) findViewById(R.id.txtDate);
        txtTime = (TextView) findViewById(R.id.txtTime);

        voucherPrice = findViewById(R.id.txtDiscountTotalVouchers);
        layoutVoucher = findViewById(R.id.layoutVoucher);

        layoutManager = new LinearLayoutManager(mContext);
        orderRecyclerView.setLayoutManager(layoutManager);


        //outletText.setText(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));
        //setDeliveryAddress();
//ToDO UDAYA IMAPORTANT
        GlobalValues.DELIVERY_DATE = Utility.readFromSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE);
        GlobalValues.DELIVERY_TIME = Utility.readFromSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME);
        CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);

        try {
//            if (time_slot_type == 2) {
                SimpleDateFormat sdatef = new SimpleDateFormat("dd-MM-yyyy");
                //    SimpleDateFormat stimef = new SimpleDateFormat("hh.mm aa");
                SimpleDateFormat stimef = new SimpleDateFormat("hh:mm aa");

                int delayMinute = mTatTime;
                long includingTATtime = Calendar.getInstance().getTimeInMillis();
                includingTATtime = includingTATtime + (delayMinute) * 60 * 1000;

                Calendar cal = Calendar.getInstance();
                cal.setTimeInMillis(includingTATtime);

                String current_Date = sdatef.format(cal.getTime());
                String current_Time = stimef.format(cal.getTime());
                GlobalValues.DELIVERY_DATE = current_Date;
                GlobalValues.DELIVERY_TIME = "" + current_Time;

                setDate( GlobalValues.DELIVERY_DATE);
                setTime( GlobalValues.DELIVERY_TIME);
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        GlobalValues.GstChargers = Utility.readFromSharedPreference(mContext, GlobalValues.GstCharger);

        if (CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {
            txt_cartTimeLabel.setText("DELIVERY TIME");
            txt_cartDateLabel.setText("DATE OF DELIVERY");
        } else {
            //    txt_cartTimeLabel.setText("PICKUP TIME");
            txt_cartTimeLabel.setText("COLLECTION TIME");
            txt_cartDateLabel.setText("DATE OF PICKUP");
        }
        txt_address.setText(Utility.readFromSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS) + ", Singapore, " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_PINCODE));


        cutlery_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_cutlery_checked = b;
            }
        });

        /*kitchennote.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

            if ((keyCode == KeyEvent.KEYCODE_ENTER))
                {

                    InputMethodManager imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(kitchennote.getWindowToken(), 0);
                    return true;
                }
                return false;
            }
        });*/


//        txtGstLabel.setText("GST Inclusive (" + GlobalValues.GstChargers + "%): ");
        txtGstLabel.setText("GST Charges(" + GlobalValues.GstChargers + "%)");
        layoutGst.setVisibility(View.VISIBLE);


        txt_delivery_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                hide below for not implementing delivery
                Toast.makeText(CartActivity.this, "This feature is not available", Toast.LENGTH_SHORT).show();

//                {
//                    String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//                    if (minQual.equalsIgnoreCase("")) {
//                        minQual = "0";
//                    }
//                    if (Integer.parseInt(minQual) >= 1) {
//                        String message = "You are about to clear your cart by changing pick-up to delivery!";
//                        new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
//                            @Override
//                            public void selectedAction(String action) {
//                                if (action.equalsIgnoreCase("YES")) {
//                                    String url1 = GlobalUrl.DESTROY_CART_URL;
//                                    Map<String, String> params = new HashMap<>();
//                                    params.put("app_id", GlobalValues.APP_ID);
//                                    params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
//                                    params.put("reference_id", "");
//
//                                    if (Utility.networkCheck(mContext)) {
//                                        new DestroyCartTask(params).execute(url1);
//                                        OutletPage = true;
//                                    }
//                                    invalidateOptionsMenu();
//                                /*txt_delivery_disable.setVisibility(View.GONE);
//                                txt_takeaway.setVisibility(View.GONE);
//                                txt_delivery.setVisibility(View.VISIBLE);
//                                txt_takeaway_disable.setVisibility(View.VISIBLE);*/
//                                    Intent intent = new Intent(mContext, ChooseOutletActivity.class);
//                                    intent.putExtra("availability", "delivery");
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
//                        });
//                    } else {
//                    /*txt_delivery_disable.setVisibility(View.GONE);
//                    txt_takeaway.setVisibility(View.GONE);
//                    txt_delivery.setVisibility(View.VISIBLE);
//                    txt_takeaway_disable.setVisibility(View.VISIBLE);*/
//                        Intent intent = new Intent(mContext, ChooseOutletActivity.class);
//                        intent.putExtra("availability", "delivery");
//                        startActivity(intent);
//                        finish();
//                    }
//                }
            }
        });

        txt_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                if (minQual.equalsIgnoreCase("")) {
                    minQual = "0";
                }
                if (Integer.parseInt(minQual) >= 1) {
                    String message = "You are about to clear your cart by switching from one outlet to another!";
                    new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                String url1 = GlobalUrl.DESTROY_CART_URL;
                                Map<String, String> params = new HashMap<>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                params.put("reference_id", "");

                                if (Utility.networkCheck(mContext)) {
                                    new DestroyCartTask(params).execute(url1);
                                    OutletPage = true;
                                }
                                invalidateOptionsMenu();
                                /*txt_delivery_disable.setVisibility(View.GONE);
                                txt_takeaway.setVisibility(View.GONE);
                                txt_delivery.setVisibility(View.VISIBLE);
                                txt_takeaway_disable.setVisibility(View.VISIBLE);*/
                                Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                                intent.putExtra("availability", "delivery");
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    /*txt_delivery_disable.setVisibility(View.GONE);
                    txt_takeaway.setVisibility(View.GONE);
                    txt_delivery.setVisibility(View.VISIBLE);
                    txt_takeaway_disable.setVisibility(View.VISIBLE);*/
                    Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                    intent.putExtra("availability", "delivery");
                    startActivity(intent);
                    finish();
                }
            }
        });

        txt_takeaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                if (minQual.equalsIgnoreCase("")) {
                    minQual = "0";
                }
                if (Integer.parseInt(minQual) >= 1) {
                    String message = "You are about to clear your cart by switching from one outlet to another!";
                    new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                String url1 = GlobalUrl.DESTROY_CART_URL;
                                Map<String, String> params = new HashMap<>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                params.put("reference_id", "");

                                if (Utility.networkCheck(mContext)) {
                                    new DestroyCartTask(params).execute(url1);
                                    OutletPage = true;
                                }
                                invalidateOptionsMenu();
                                /*txt_delivery_disable.setVisibility(View.VISIBLE);
                                txt_takeaway.setVisibility(View.VISIBLE);
                                txt_delivery.setVisibility(View.GONE);
                                txt_takeaway_disable.setVisibility(View.GONE);*/
                                Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                                intent.putExtra("availability", "takeaway");
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    /*txt_delivery_disable.setVisibility(View.VISIBLE);
                    txt_takeaway.setVisibility(View.VISIBLE);
                    txt_delivery.setVisibility(View.GONE);
                    txt_takeaway_disable.setVisibility(View.GONE);*/
                    Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                    intent.putExtra("availability", "takeaway");
                    startActivity(intent);
                    finish();
                }
            }
        });

        txt_takeaway_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                if (minQual.equalsIgnoreCase("")) {
                    minQual = "0";
                }
                if (Integer.parseInt(minQual) >= 1) {
                    String message = "You are about to clear your cart by changing delivery to pick-up!";
                    new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                String url1 = GlobalUrl.DESTROY_CART_URL;
                                Map<String, String> params = new HashMap<>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                params.put("reference_id", "");

                                if (Utility.networkCheck(mContext)) {
                                    new DestroyCartTask(params).execute(url1);
                                    OutletPage = true;
                                }
                                invalidateOptionsMenu();
                                /*txt_delivery_disable.setVisibility(View.VISIBLE);
                                txt_takeaway.setVisibility(View.VISIBLE);
                                txt_delivery.setVisibility(View.GONE);
                                txt_takeaway_disable.setVisibility(View.GONE);*/
                                Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                                intent.putExtra("availability", "takeaway");
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    /*txt_delivery_disable.setVisibility(View.VISIBLE);
                    txt_takeaway.setVisibility(View.VISIBLE);
                    txt_delivery.setVisibility(View.GONE);
                    txt_takeaway_disable.setVisibility(View.GONE);*/
                    Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                    intent.putExtra("availability", "takeaway");
                    startActivity(intent);
                    finish();
                }
            }
        });

        layout_changeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txt_delivery_disable.performClick();
            }
        });

        img_chooseOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                if (minQual.equalsIgnoreCase("")) {
                    minQual = "0";
                }
                if (Integer.parseInt(minQual) >= 1) {
                    String message = "You are about to clear your cart by switching from one oulet to another!";
                    new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {

                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                    intent.putExtra("availability", "delivery");
                                } else {
                                    intent.putExtra("availability", "takeaway");
                                }
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        clearcart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Are you sure?";
                new CheckOutMessageDialog(mContext, "Cancel", message, new CheckOutMessageDialog.OnSlectedMethod() {
                    @Override
                    public void selectedAction(String action) {
                        if (action.equalsIgnoreCase("YES")) {

                            String url1 = GlobalUrl.DESTROY_CART_URL;
                            Map<String, String> params = new HashMap<>();
                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                            params.put("reference_id", "");

                            if (Utility.networkCheck(mContext)) {
                                new DestroyCartTask(params).execute(url1);
                            }
                        }
                    }
                });
            }
        });

/*

        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
            lyoutBilling.setVisibility(View.GONE);
        } else {
            lyoutBilling.setVisibility(View.VISIBLE);
        }

        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
            layoutGST.setVisibility(View.VISIBLE);
        } else {
            layoutGST.setVisibility(View.GONE);
        }
*/


        String date = GlobalValues.DELIVERY_DATE;
        setDate(date);


        if (GlobalValues.GstChargers.equals("0")) {

            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
/*
                txtGSTLabel.setText("GST ("+"7"+"%)" );

                layoutGST.setVisibility(View.VISIBLE);*/
            } else {
             /*   txtGSTLabel.setText("GST ("+GlobalValues.GstChargers+"%)" );
                layoutGST.setVisibility(View.GONE);*/
                txtGSTLabel.setText("Gst Charges(" + GlobalValues.GstChargers + "%)");
            }


        } else {

            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
/*
                txtGSTLabel.setText("GST ("+"7"+"%)" );
                layoutGST.setVisibility(View.VISIBLE);*/
            } else {

/*
                txtGSTLabel.setText("GST ("+GlobalValues.GstChargers+"%)" );
                layoutGST.setVisibility(View.VISIBLE);*/
            }


        }


        String time = GlobalValues.DELIVERY_TIME;


        setTime(time);




     /*   edtPincode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

                String data = edtPincode.getText().toString();
                int length = data.length();

                if (length == 6) {

                    new GetAddressTask(edtPincode, null).execute(data);

                } else {

                    edtBillingAddress.setText("");

                }


            }
        });




        mPromotion = txtPromotions.getText().toString();*/

        setPromotionSpan(mPromotion);

/*
        promotionspannableString = new SpannableString(txtChangeAddress.getText().toString());
        promotionspannableString.setSpan(new UnderlineSpan(), 0, txtChangeAddress.getText().toString().length(), 0);
        *//*spannableString.setSpan(new StyleSpan(Typeface.BOLD), 45, 69, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 74, mPolicy.length()-1, 0);*//*
        promotionspannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)),
                9, txtChangeAddress.getText().toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        txtChangeAddress.setText(promotionspannableString);




        promotionspannableString = new SpannableString(txtChangeAddress_pickup.getText().toString());
        promotionspannableString.setSpan(new UnderlineSpan(), 0, txtChangeAddress_pickup.getText().toString().length(), 0);
        *//*spannableString.setSpan(new StyleSpan(Typeface.BOLD), 45, 69, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 74, mPolicy.length()-1, 0);*//*
        promotionspannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)),
                9, txtChangeAddress_pickup.getText().toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        txtChangeAddress_pickup.setText(promotionspannableString);*/


        layoutContinue.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (!isCustomerLoggedIn()) {

                                                      new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                                                          @Override
                                                          public void selectedAction(String action) {

                                                              if (action.equalsIgnoreCase("Ok")) {
                                                                  Intent intent = new Intent(mContext, LoginActivity.class);
                                                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                  mContext.startActivity(intent);
                                                                  ((CartActivity) mContext).finish();
                                                              }
                                                          }
                                                      });

                                                  } else {

                                                      String message = "You are placing an order with " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME);
                                                      new ChangeOutletDialog(mContext, "Cancel", message, new ChangeOutletDialog.OnSlectedMethod() {
                                                          @Override
                                                          public void selectedAction(String action) {

                                                              if (action.equalsIgnoreCase("YES")) {




                                                      /*if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null &&
                                                              Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                                                          if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {



                                                              if (!Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).equalsIgnoreCase("null") ||
                                                                      Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).length() > 0) {

                                                                  placeCashOnDeliveryOrder();

                                                              } else {
                                                                  openFiveMenuActivity(0);
                                                              }

                                                          } else {

                                                              Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
                                                          }
                                                      } else {

                                                          intent = new Intent(mContext, LoginActivity.class);
                                                          startActivity(intent);
                                                      }*/


                                                  /*GlobalValues.BILLING_ADDRESS=edtBillingAddress.getText().toString();
                                                  GlobalValues.BILLING_PINCODE=edtPincode.getText().toString();
                                                  GlobalValues.BILLING_UNITNO1=edtBillingUnitNo1.getText().toString();
                                                  GlobalValues.BILLING_UNITNO2=edtBillingUnitNo2.getText().toString();

                                                  if(edtUnitNo1.getText().toString().equalsIgnoreCase("") && edtUnitNo2.getText().toString().equalsIgnoreCase("")){
                                                      Toast.makeText(mContext, "Please enter unit no.", Toast.LENGTH_SHORT).show();
                                                      return;
                                                  }


                                                  GlobalValues.Unit_no_1 = edtUnitNo1.getText().toString();

                                                  GlobalValues.Unit_no_2 = edtUnitNo2.getText().toString();*/



                                                                  if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

                                                                      if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                                                                          String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);


                                                                          String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.MinimumQuality);

                                                                          if (Integer.parseInt(MinQual) <= Integer.parseInt(cartCount)) {




                                                                              if (Integer.parseInt(cartCount) != 0) {

                                                            /*  Intent intent = new Intent(mContext, AddOnsActivity.class);
                                                              startActivity(intent);*/

                                                                              }


                                                                          } else {

                                                                              //openDialogbox();

                                                                          }

                                                                      } else {

                                                                          if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                                                              if (edtUnitNo1.getText().toString().equalsIgnoreCase("")
                                                                                      && edtUnitNo2.getText().toString().equalsIgnoreCase("")) {
                                                                                  Toast.makeText(mContext, "Please enter unit number", Toast.LENGTH_SHORT).show();
                                                                                  return;
                                                                              }
                                                                          }

                                                                          kicthen_notes = kitchennote.getText().toString();
                                                                          if (kicthen_notes != null && !kicthen_notes.isEmpty() && !kicthen_notes.equals("null")) {

                                                                              kicthen_notes = kicthen_notes;
                                                                          } else {

                                                                              kicthen_notes = "";
                                                                          }
                                                                          if (cutlery_check.isChecked()) {
                                                                              is_cutlery_checked = true;
                                                                              cutlery_value = "YES";
                                                                          } else {
                                                                              is_cutlery_checked = false;
                                                                              cutlery_value = "NO";
                                                                          }
                                                                          Log.e("TAG", "Cutlery_value::" + cutlery_value);
                                                                          Intent i = new Intent(mContext, OrderSummaryActivity.class);
                                                                          i.putExtra("cutlery", cutlery_value);
                                                                          i.putExtra("notes", kicthen_notes);
                                                                          i.putExtra("PROMO_RESPONSE", "");
                                                                          i.putExtra("unitno1", edtUnitNo1.getText().toString());
                                                                          i.putExtra("unitno2", edtUnitNo2.getText().toString());
                                                   /*   i.putExtra("sub", subtotaltextview.getText().toString());
                                                      i.putExtra("total", totaltextview.getText().toString());*/
                                                                          startActivity(i);

                                                      /*  Intent intent = new Intent(mContext, AddOnsActivity.class);
                                                      startActivity(intent);*/


                                                                      }


                                                                  } else {

                                                                      Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
                                                                  }


                                                              } else {
                                                                  String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                                                                  if (minQual.equalsIgnoreCase("")) {
                                                                      minQual = "0";
                                                                  }
                                                                  if (Integer.parseInt(minQual) >= 1) {
                                                                      String message;
                                                                      if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                                                          message = "You are about to clear your cart by switching from delivery to takeaway!";
                                                                      } else {
                                                                          message = "You are about to clear your cart by switching from pickup to delivery!";
                                                                      }

                                                                      new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
                                                                          @Override
                                                                          public void selectedAction(String action) {
                                                                              if (action.equalsIgnoreCase("YES")) {
                                                                                  switchoutletfromCartActivity = "show";
                                                                                  intent = new Intent(mContext, ChooseOutletActivity.class);
                                                                                  intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                                  intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                                  if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                                                                      intent.putExtra("availability", "delivery");
                                                                                  } else {
                                                                                      intent.putExtra("availability", "takeaway");
                                                                                  }
                                                                                  startActivity(intent);
                                                                                  finish();
                                                                              }
                                                                          }
                                                                      });
                                                                  } else {

                                                                      switchoutletfromCartActivity = "show";
                                                                      intent = new Intent(mContext, ChooseOutletActivity.class);
                                                                      intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                      intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                      if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                                                          intent.putExtra("availability", "delivery");
                                                                      } else {
                                                                          intent.putExtra("availability", "takeaway");
                                                                      }
                                                                      startActivity(intent);
                                                                      finish();
                                                                  }

                                                                  // Showing Alert Message
                                                             /* alertDialog.show();
                                                              switchoutletfromCartActivity = "show";
                                                              intent = new Intent(mContext, ChooseOutletActivity.class);
                                                              intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                              intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                              startActivity(intent);
                                                              finish();*/
                                                              }
                                                          }
                                                      });
                                                  }
                                              }
                                          }
        );

        layoutCartDate.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  if (Utility.networkCheck(mContext)) {
                                                      oncreateTimeOnly = false;
                                                      flow = DIALOG;
                                                      getTAT();
                                                  } else {
                                                      Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                                                  }
                                              }
                                          }
        );

        layoutCartTime.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  layoutCartDate.performClick();
                                              }
                                          }
        );

     /*   txtChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // openDeliveryDialog(mContext);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

                alertDialog.setMessage("You are about to clear your cart by switching from one menu to another");
                alertDialog.setTitle("Warning");

                // Setting OK Button
                alertDialog.setPositiveButton("Yes Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        fromChangeAddress = 1;
                        destroyCartItem();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Showing Alert Message
                alertDialog.show();


            }
        });*/


   /*     txtChangeAddress_pickup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                // openDeliveryDialog(mContext);

                final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

                alertDialog.setMessage("You are about to clear your cart by switching from one menu to another");
                alertDialog.setTitle("Warning");

                // Setting OK Button
                alertDialog.setPositiveButton("Yes Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        fromChangeAddress = 1;
                        destroyCartItem();
                    }
                });

                alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                // Showing Alert Message
                alertDialog.show();


            }
        });*/


        imgBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           Intent intent = new Intent(mContext, SubCategoryActivity.class);
                                           startActivity(intent);
                                           finish();
                                       }
                                   }
        );


     /*   layoutSameDeliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (BILLING_CHECKED) {
                    imgChecked.setImageResource(R.drawable.asset54);
                    BILLING_CHECKED = false;

                 *//*   GradientDrawable bgShape = (GradientDrawable) edtBillingAddress.getBackground();
                    bgShape.setColor(mContext.getResources().getColor(R.color.colorWhite));

                    GradientDrawable bgShape1 = (GradientDrawable) edtPincode.getBackground();
                    bgShape1.setColor(mContext.getResources().getColor(R.color.colorWhite));*//*

                    layoutBillingAddress.setVisibility(View.VISIBLE);

                } else {
                    imgChecked.setImageResource(R.drawable.asset53);
                    BILLING_CHECKED = true;

                   *//* GradientDrawable bgShape = (GradientDrawable) edtBillingAddress.getBackground();
                    bgShape.setColor(Color.parseColor("#efeeee"));

                    GradientDrawable bgShape1 = (GradientDrawable) edtPincode.getBackground();
                    bgShape1.setColor(Color.parseColor("#efeeee"));*//*

                    layoutBillingAddress.setVisibility(View.GONE);
                }
            }
        });

        layoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/

        if (GlobalValues.DELIVERY_ID.equalsIgnoreCase(CURRENT_AVAILABLITY_ID) ||
                GlobalValues.BENTO_ID.equalsIgnoreCase(CURRENT_AVAILABLITY_ID)) {

            addSecondaryAddress();

        }
        getTAT();
    }

    public void makeUpdateApiCall(String productId, Cart cartProduct, String type) {
        productCartDetails = cartProduct;

        if (Utility.networkCheck(mContext)) {

            mProductId = productId;

            String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&availability=" + CURRENT_AVAILABLITY_ID +
                    "&product_id=" + mProductId + "&fav_cust_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

            new SetMenuProductDetailsTaskNew(mProductId, cartProduct.getmProductQty()).execute(url);

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private class GetAddressTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;

        EditText addressEditText;
        AlertDialog alertDialog;

        GetAddressTask(EditText addressEditText, AlertDialog alertDialog) {
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

                        String zip_id = resultJSONObject.getString("zip_id");
                        String zip_code = resultJSONObject.getString("zip_code");
                        String zip_addtype = resultJSONObject.getString("zip_addtype");
                        String zip_buno = resultJSONObject.getString("zip_buno");
                        String zip_sname = resultJSONObject.getString("zip_sname");
                        String zip_buname = resultJSONObject.getString("zip_buname");

                        //     postalAddressTextView.setText(zip_buno + ", " + zip_sname);


                        //  edtBillingAddress.setText(zip_buno + " " + zip_sname);


                    } else {

                        //  edtBillingAddress.setText("");

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


    @Override
    protected void onResume() {
        super.onResume();
        isApplyPromo = false;
        isApplyRedeem = false;
        cutlery_check.setChecked(is_cutlery_checked);
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }

        if (!GlobalValues.DELIVERY_DATE.equalsIgnoreCase("")) {
            setDate(GlobalValues.DELIVERY_DATE);
        }

        if (!GlobalValues.DELIVERY_TIME.equalsIgnoreCase("")) {
            setTime(GlobalValues.DELIVERY_TIME);
        }

        if (Utility.networkCheck(mContext)) {
            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = "";

            } else {
                mCustomerId = "";
                mReferenceId = Utility.getReferenceId(mContext);
            }

            CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);

            String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + mCustomerId +
                    "&reference_id=" + "" +
                    "&availability_id=" + CURRENT_AVAILABLITY_ID;


            if (!GlobalValues.DISCOUNT_APPLIED) {
                new CartListTask().execute(url);
            } else {
                new CartListTask().execute(url);


            }

        } else {

            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }
        GlobalValues.promoID = "";
        getActiveCount();
    }

    private void setPromotionSpan(String mPromotion) {


        promotionspannableString = new SpannableString(mPromotion);
        promotionspannableString.setSpan(new UnderlineSpan(), 0, mPromotion.length(), 0);
        /*spannableString.setSpan(new StyleSpan(Typeface.BOLD), 45, 69, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 74, mPolicy.length()-1, 0);*/
        promotionspannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorWhite)),
                0, mPromotion.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
//        txtPromotions.setText("You have "+promotionspannableString+" Available");
        //  txtPromotions.setText(promotionspannableString);
    }

    private void getTAT() {

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.CART_TAT_URL +
                    "?app_id=" + GlobalValues.APP_ID +
                    "&outletId=" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID) +
                    "&availability=" + CURRENT_AVAILABLITY_ID;
            new CartTATtask().execute(url);
        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

//    private void setTime(String time) {
//        spannableTime = new SpannableString(Utility.convertTime(time));
//        // spannableTime.setSpan(new UnderlineSpan(), 0, time.length(), 0);
//
//        if (spannableDate.length() > 0) {
//            txtTime.setText(spannableTime);
//        } else {
//            txtTime.setText("SelectTime");
//        }
//
//
//    }

    private void setTime(String time) {
        if (time.toUpperCase().contains("PM") || time.toUpperCase().contains("AM")) {
            spannableTime = SpannableString.valueOf(time);
        } else {
            spannableTime = new SpannableString(Utility.convertTime(time));
        }
        // spannableTime.setSpan(new UnderlineSpan(), 0, time.length(), 0);
        if (time_slot_type == 2) {
            if (time.toUpperCase().contains("PM") || time.toUpperCase().contains("AM")) {
                spannableTime = SpannableString.valueOf(time);
            } else {
                try {
                    spannableTime = new SpannableString(Utility.convertTime(time.split(" - ")[0]) + " - " + Utility.convertTime(time.split(" - ")[1]));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (spannableDate.length() > 0) {
            txtTime.setText(spannableTime);
        } else {
            txtTime.setText("SelectTime");
        }


    }

    private void setDate(String date) {

        spannableDate = new SpannableString(Utility.convertDate(date));
        // spannableDate.setSpan(new UnderlineSpan(), 0, date.length(), 0);


        if (spannableDate.length() > 0) {
            txtDate.setText(spannableDate);
        } else {
            txtDate.setText("SelectDate");
        }
    }

    private void addSecondaryAddress() {


        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
        } else {

            mCustomerId = Utility.getReferenceId(mContext);
        }

        try {
            JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

            String url = GlobalUrl.ADD_SECONDARY_ADDRESS_URL;

            JSONObject jsonObject_postinfromation = outletJson.getJSONObject("postal_code_information");


            Map<String, String> params = new HashMap<>();
            params.put("app_id", GlobalValues.APP_ID);
            params.put("refrence", mCustomerId);
            params.put("customer_address_line1", jsonObject_postinfromation.getString("zip_sname"));
            params.put("customer_postal_code", jsonObject_postinfromation.getString("zip_code"));
            params.put("customer_address_name", Utility.readFromSharedPreference(mContext, GlobalValues.FIRSTNAME));
            params.put("customer_address_name2", Utility.readFromSharedPreference(mContext, GlobalValues.LASTNAME));
            params.put("created_on", Utility.getCurrentTime());
            params.put("customer_status", "A");
            params.put("customer_order_status", "order");


            new AddSecondaryAddressTask(params).execute(url);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void destroyCartItem() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";
        } else {
            mCustomerId = "";
            mReferenceId = Utility.getReferenceId(mContext);
        }

        String url = GlobalUrl.DESTROY_CART_URL;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", mCustomerId);
        params.put("reference_id", "");

        if (Utility.networkCheck(mContext)) {

            new DestroyCartTask(params).execute(url);
        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }


    public boolean checkTime(String selectedDate, String selectedTime) {

        boolean is_success = false;
        int cutoffTime = 0;

        try {

            Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
            Date dCutOffTime = null, SelectedTime;

            Date currentDate, dselectedDate;

            currentDate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
            dselectedDate = dateformat.parse(selectedDate);
            SelectedTime = timeformat.parse(selectedTime);

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF).length() > 0) {
                cutoffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));


            } else {
                cutoffTime = 0;
            }

            if (dselectedDate.equals(currentDate)) {

                if (cutoffTime > 0) {
                    if (!String.valueOf(cutoffTime).contains(":")) {
                        dCutOffTime = timeformat.parse(cutoffTime + ":00");

                    } else {
                        dCutOffTime = timeformat.parse(String.valueOf(cutoffTime));
                    }
                }

                if (cutoffTime > 0 && dCutOffTime.before(dCurrentTime)) {

                    Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                    is_success = false;
                } else {
//                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                    if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).after(SelectedTime)) {
                        // Toast.makeText(mContext, "Time exired", Toast.LENGTH_SHORT).show();
                        is_success = false;

                    } else {
//                        Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                        is_success = true;

                    }
                }

            } else {
//                Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                is_success = true;

            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return is_success;
    }


    private void setDeliveryAddress() {

        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

            try {

                layoutDelivery.setVisibility(View.VISIBLE);
                layoutTakeaway.setVisibility(View.GONE);
                layout_delivery_enable.setVisibility(View.VISIBLE);
                layout_takeaway_enable.setVisibility(View.GONE);
                txt_delivery.setVisibility(View.VISIBLE);
                txt_takeaway_disable.setVisibility(View.VISIBLE);
                txt_takeaway.setVisibility(View.GONE);
//                txt_delivery_disable.setVisibility(View.GONE);
                layout_spclInstruction.setVisibility(View.VISIBLE);
                specialinstructionlayout.setVisibility(View.VISIBLE);
                //layoutFreeDelivery.setVisibility(View.VISIBLE);
                //layoutDeliveryCharge.setVisibility(View.VISIBLE);


                txtOutletName.setText(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME) + "\n" + Utility.readFromSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS)
                        + "\n" + "Singapore, " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_PINCODE) + "\n" + "#" +
                        Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_UNITNO1)
                        + "-" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_UNITNO2));

                outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");

                Utility.writeToSharedPreference(mContext, GlobalValues.ZONE_ID, outletZoneJson.getString("zone_id"));

                GlobalValues.ZoneID = Utility.readFromSharedPreference(mContext, GlobalValues.ZONE_ID);


                if (outletZoneJson.getString("zone_delivery_charge").equalsIgnoreCase("null")
                        || outletZoneJson.getString("zone_delivery_charge").equalsIgnoreCase("0.00")) {
                    layoutDeliveryCharge.setVisibility(View.GONE);
                } else {
                    layoutDeliveryCharge.setVisibility(View.VISIBLE);
                    txtDeliveryCharge.setText(String.format("%.2f", Double.valueOf(outletZoneJson.getString("zone_delivery_charge"))));
                    deliveryCharges = Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));
                }


                //txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));


                txtAddress.setText(jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname") + "\n" + " Singapore, " +
                        jsonPostalCodeInfo.optString("zip_code")
                );

                /*edtBillingAddress.setText(jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname"));

                edtPincode.setText(jsonPostalCodeInfo.optString("zip_code"));*/

                if (outletZoneJson.getString("zone_additional_delivery_charge").equalsIgnoreCase("null") ||
                        outletZoneJson.getString("zone_additional_delivery_charge").equalsIgnoreCase("0.00")) {
                    layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                } else {
                    layoutAdditionalDeliveryCharge.setVisibility(View.VISIBLE);
                    txtAdditionalDeliveryCharge.setText(String.format("%.2f", Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                    additionalDeliveryCharges = Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));
                }

                if (foodVoucher) {
                    layoutDeliveryCharge.setVisibility(View.GONE);
                    layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                    txtDeliveryCharge.setText("0.00");
                    deliveryCharges = 0.00;
                    txtAdditionalDeliveryCharge.setText("0.00");
                    additionalDeliveryCharges = 0.00;
                }

                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");


               /* if (outletZoneJson.getString("zone_free_delivery").equalsIgnoreCase("0.00") ||
                        outletZoneJson.getString("zone_free_delivery").equalsIgnoreCase("null") ||
                        outletZoneJson.getString("zone_free_delivery").length() <= 0) {

                    //layoutFreeDelivery.setVisibility(View.GONE);

                } else {
                    txtFreeDelivery.setText("$" + outletZoneJson.getString("zone_free_delivery") + " more to FREE delivery!");
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

            //layoutFreeDelivery.setVisibility(View.GONE);
            layoutDelivery.setVisibility(View.GONE);
            layoutTakeaway.setVisibility(View.VISIBLE);
            layoutDeliveryCharge.setVisibility(View.GONE);
            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
            txt_delivery.setVisibility(View.GONE);
            txt_takeaway_disable.setVisibility(View.GONE);
            txt_takeaway.setVisibility(View.VISIBLE);
//            txt_delivery_disable.setVisibility(View.VISIBLE);
            layout_spclInstruction.setVisibility(View.GONE);
            specialinstructionlayout.setVisibility(View.GONE);
            layout_delivery_enable.setVisibility(View.GONE);
            layout_takeaway_enable.setVisibility(View.VISIBLE);


            txtTakeawayName.setText(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));
        }
    }

    @Override
    public void onBackPressed() {
//        super.onBackPressed();
        Intent intent = new Intent(mContext, SubCategoryActivity.class);
        startActivity(intent);
        finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_empty, menu);

        return super.onCreateOptionsMenu(menu);
    }

    private void setCustomProgress() {
        try {
            outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

            int max_value = 0;

       /* DisplayMetrics displaymetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        int height = displaymetrics.heightPixels;
        System.out.println("height:" + height);

        if (height > 1600) {
            max_value = 350;
            layout_params= new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    15);
            progress_params = new FrameLayout.LayoutParams(max_value, 10);
        } else {
            max_value = 200;
            LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    15);
            progress_params = new FrameLayout.LayoutParams(max_value, 7);
        }*/


          /*  LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    15);

            progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
            progressBar.setIndeterminate(false);

            if (Double.parseDouble(outletZoneJson.optString("zone_free_delivery")) > 0) {
                progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
                progressBar.setSecondaryProgress((int) Double.parseDouble
                        (outletZoneJson.optString("zone_free_delivery")));
            } else {

                progressBar.setProgress(1000);
                progressBar.setSecondaryProgress(1000);
            }*/


         /*   double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(r_sub_total);

            if (d_progress_limit > 0) {

                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");

                mGrandTotal = Double.parseDouble(r_sub_total) +
                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

//                progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
//                progressBar.setSecondaryProgress((int) Double.parseDouble
//                        (outletZoneJson.optString("zone_free_delivery")));

            } else {

                GlobalValues.DELEIVERY_CHARGES = "0.00";
                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";
                txtDeliveryCharge.setText("$0.00");
                txtAdditionalDeliveryCharge.setText("$0.00");
                txtFreeDelivery.setText("FREE delivery!");

                mGrandTotal = Double.parseDouble(r_sub_total);
//
//                progressBar.setProgress(1000);
//                progressBar.setSecondaryProgress(1000);

            }
*/
        /*    progressBar.setLayoutParams(layout_params);

            progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_shape_bronze));*/

       /*     if (layoutProgress != null) {
                layoutProgress.removeAllViews();
            }

            layoutProgress.addView(progressBar);*/
        } catch (JSONException e) {
            e.printStackTrace();
        }


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
           /* case R.id.txtRedeemApply:
                if (txtRedeemApply.getText().toString().equalsIgnoreCase("APPLY NOW")) {

                    if (txtRedeemPoints != null && txtRedeemPoints.length() > 0) {
                        try {
                            Map<String, String> rewardsParams = new HashMap<>();
//                        FormBody.Builder formBuilder = new FormBody.Builder()
                            rewardsParams.put("app_id", GlobalValues.APP_ID);
                            rewardsParams.put("redeem_point", txtRedeemPoints.getText().toString());
                            //          .add("category_id", stringBuilder.toString())  //list of product ids
                            //    .add("cart_quantity", CommonClass.cartDetailsJSONObject.getString("cart_total_items")
                            rewardsParams.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                            rewardsParams.put("cart_amount", txtSubTotal.getText().toString().replace("$", ""));  //cart_sub_total

//                        try {
//
//                            if (GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {  //Delivery
//
//                                //outlet from postalcode for delivery
//                                rewardsParams .put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));
//
//                            } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.TAKEAWAY_ID)) {  //Pick up
//
//                                //outlet from outletlist for pickup
//                                rewardsParams .put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));
//
//                            }
//
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }

//                        RequestBody formBody = formBuilder.build();
                            String url = GlobalUrl.APPLY_REWARD_POINT_URL;

                            if (Utility.networkCheck(mContext)) {
                                new RewardPointTask(rewardsParams).execute(url);
                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

                            }


                        } catch (Exception e) {

                            e.printStackTrace();
                        }

                    } else {

                        Toast.makeText(mContext, "Please enter the redeem points", Toast.LENGTH_SHORT).show();
                    }
                } *//*else if (txtRedeemApply.getText().toString().equalsIgnoreCase("REMOVE")) {

                    removeRewardPoints();

                }*//*

                break;
*/
          /*  case R.id.txtPromoApply:

                if (txtPromoApply.getText().toString().equalsIgnoreCase("APPLY NOW")) {
                    if (edtPromotion != null && edtPromotion.getText().toString().length() > 0) {
                        try {

                            String url = GlobalUrl.COUPON_CODE_URL;

                            JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());

                            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

                            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

                            try {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < cartJsonArray.length(); i++) {
                                    JSONObject cartJson = cartJsonArray.getJSONObject(i);
                                    stringBuilder.append(cartJson.getString("cart_item_product_id") + "|" +
                                            cartJson.getString("cart_item_total_price"));
                                }

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("promo_code", edtPromotion.getText().toString().trim());
                                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                params.put("cart_amount", txtSubTotal.getText().toString().replace("$", "")); //cart_sub_total
                                params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
                                params.put("category_id", stringBuilder.toString());  //list of product ids
                                //    try {

                                //run(formBody);

                                new CouponCodeTask(params, edtPromotion.getText().toString().trim()).execute(url);

                            } catch (Exception e) {
                                e.printStackTrace();

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("promo_code", edtPromotion.getText().toString().trim());
                                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                params.put("cart_amount", ""); //cart_sub_total
                                params.put("cart_quantity", "");
                                params.put("category_id", "");  //list of product ids

                                new CouponCodeTask(params, edtPromotion.getText().toString().trim()).execute(url);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {

                        Toast.makeText(mContext, "Please enter promotion code", Toast.LENGTH_SHORT).show();
                    }
                } else if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                    removePromotion();
                }
*/

         /*       break;

            case R.id.txtRedeem:
//                openFiveMenuActivity(2);
                moveonRewardAppliedScreen();
                break;

            case R.id.txtPromotions:
                openFiveMenuActivity(3);
                break;
*/

        }
    }

 /*   private void removePromotion() {

        try {
            GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");

          //  txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

            setOverallTotal(Double.valueOf(txtDiscountTotal.getText().toString().replace("-$", "")), "REMOVE");
        } catch (Exception e) {
            e.printStackTrace();
        }

        mPromoCoupon = "";
        mPromotionAmount = "";
        layoutdiscount.setVisibility(View.GONE);
        edtPromotion.setEnabled(true);
        txtDiscountTotal.setText("");
        edtPromotion.setText("");
        txtPromoApply.setText("APPLY NOW");
    }*/

  /*  private void removeRewardPoints() {
        try {
            setOverallTotal(Double.valueOf(txtDiscountTotal.getText().toString().replace("-$", "")), "REMOVE");
        } catch (Exception e) {
            e.printStackTrace();
        }
        r_applied = "No";
        r_point = "";
        r_amount = "";
        layoutdiscount.setVisibility(View.GONE);
        txtRedeemPoints.setEnabled(true);
        txtDiscountTotal.setText("");
        txtRedeemPoints.setText("");
        txtRedeemApply.setText("APPLY NOW");
    }*/

  /*  private void moveonRewardAppliedScreen() {

        if (!Utility.isCustomerLoggedIn(mContext)) {

            intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);

        } else {

            intent = new Intent(mContext, RewardsAppliedActivity.class);
            intent.putExtra("cart_amount", txtSubTotal.getText().toString().replace("$", ""));
            intent.putExtra("rewardpoints", mrewardPoint);
            startActivityForResult(intent, GlobalValues.REWARDS_REQUEST_CODE);

        }

    }*/

    private void openFiveMenuActivity(int position) {

        if (!Utility.isCustomerLoggedIn(mContext)) {

            intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);

        } else {

            intent = new Intent(mContext, FiveMenuActivityNew.class);
            intent.putExtra("position", position);
            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.FROM_CHECKOUT);
            startActivityForResult(intent, GlobalValues.PROMO_REQUEST_CODE);

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GlobalValues.PROMO_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                if (data != null) {

                    mPromoCouponResponse = data.getStringExtra("PROMO_RESPONSE");

                    if (mPromoCouponResponse != null && mPromoCouponResponse.length() > 0) {

                        //  layoutdiscount.setVisibility(View.VISIBLE);

                        try {
                            parseCouponPointResponse(mPromoCouponResponse);
                               /* txtDiscountTotal.setText("-$" + String.format("%.2f", Double.valueOf(mPromotionAmount)));
                                setOverallTotal(Double.valueOf(mPromotionAmount), "APPLY");*/

                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                      /*  if (GlobalValues.DISCOUNT_APPLIED && GlobalValues.DISCOUNT_TYPE.equalsIgnoreCase("coupon")) {
                            edtPromotion.setFocusable(false);
                            edtPromotion.setClickable(false);
                            edtPromotion.setClickable(false);

                            txtPromoApply.setText("Remove");

                        } else {

                            txtPromoApply.setText("Apply Now");
                        }
*/
                    }

                } else {

                }


            }
        } else if (requestCode == GlobalValues.REWARDS_REQUEST_CODE) {

            if (resultCode == RESULT_OK) {
                if (data != null) {
                    try {

                        String response = data.getStringExtra("response");

                        if (response != null && response.length() > 0) {

                            parseRewardPointResponse(response);
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }
    }

    private class RewardPointTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;
        private Map<String, String> Params;

        RewardPointTask(Map<String, String> Params) {
            this.Params = Params;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = new ProgressDialog(CartActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected String doInBackground(String... strings) {



            String response = WebserviceAssessor.postRequest(mContext, strings[0], Params);

            return response;


        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                try {
                    parseRewardPointResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

//

        }
    }

    private void parseRewardPointResponse(String response) throws JSONException {
        JSONObject responseObj = new JSONObject(response);

        if (responseObj.optString("status").equalsIgnoreCase("success")) {


            if (edtPromotion.getText().toString().length() > 0) {
                try {
               /*     setOverallTotal(Double.valueOf(txtDiscountTotal.getText().toString().replace("-$", "")), "REMOVE");
                    txtDiscountTotal.setText("");
                    txtPromoApply.setText("APPLY NOW");*/
                    edtPromotion.setText("");
                    edtPromotion.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }

            GlobalValues.DISCOUNT_APPLIED = true;
            GlobalValues.DISCOUNT_TYPE = "REWARD";
           /* txtDiscountLabel.setText("DISCOUNT(Redeem)");

            // Toast.makeText(mContext, "" + responseObj.optString("message"), Toast.LENGTH_SHORT).show();
            layoutdiscount.setVisibility(View.VISIBLE);
            JSONObject resultObj = responseObj.getJSONObject("result_set");
            txtDiscountTotal.setText("-$" + String.valueOf(resultObj.optDouble("points_amount")));*/

       /*     try {
                setOverallTotal(resultObj.optDouble("points_amount"), "APPLY");
            } catch (Exception e) {
                e.printStackTrace();
            }
            r_applied = "Yes";
            r_point = resultObj.optString("points_used");
            txtRedeemPoints.setText(resultObj.optDouble("points_used") + "");
            r_amount = String.valueOf(resultObj.optDouble("points_amount"));
            txtRedeemPoints.setEnabled(false);
            txtRedeemApply.setText("REMOVE");*/
        } else {

            Toast.makeText(mContext, responseObj.optString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    private void setOverallTotal(double points_amount, String val) throws Exception {

        if (val.equalsIgnoreCase("APPLY")) {


            if (txtSubTotal.getText().toString().length() != 0) {
                cart_sub_total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) - points_amount;
                txtSubTotal.setText(String.format("%.2f", cart_sub_total));
                txtSubTotalSymbol.setVisibility(View.VISIBLE);
            } else {
                cart_sub_total = 0.0;
            }

      /*      if (txtDeliveryCharge.getText().toString().length() != 0)
            {

                double delivery = 0.0;

                if (GlobalValues.PRMOTION_DELIVERY_APPLIED)
                {
                    delivery = 0.0;
                } else {
                    delivery = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
                }

                if (delivery > 0)
                {
                    cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
                } else {
                    cart_deleivery_charge = 0.0;
                }

            } else {

                cart_deleivery_charge = 0.0;
            }
*/

          /*  if (txtAdditionalDeliveryCharge.getText().toString().length() != 0) {
                cart_adddeleivery_charge = Double.valueOf(txtAdditionalDeliveryCharge.getText().toString().replace("$", ""));

            } else {

                cart_adddeleivery_charge = 0.0;
            }*/
            InclusiveGst(cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge);
            //txtTotal.setText(String.format("%.2f", cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge + gstAmount));
            txtTotal.setText(String.format("%.2f", cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge));
            txtTotalSymbol.setVisibility(View.VISIBLE);
        } else if (val.equalsIgnoreCase("REMOVE")) {

            if (txtSubTotal.getText().toString().length() != 0) {

                cart_sub_total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) + points_amount;
                txtSubTotal.setText(String.format("%.2f", cart_sub_total));
                txtSubTotalSymbol.setVisibility(View.VISIBLE);

            } else {
                cart_sub_total = 0.0;
            }

         /*   if (txtDeliveryCharge.getText().toString().length() != 0) {

                double delivery = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));

                if (delivery > 0) {

                    cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));

                } else {

                    cart_deleivery_charge = 0.0;

                }

//                cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));

            } else {

                cart_deleivery_charge = 0.0;
            }
*/

         /*   if (txtAdditionalDeliveryCharge.getText().toString().length() != 0) {
                cart_adddeleivery_charge = Double.valueOf(txtAdditionalDeliveryCharge.getText().toString().replace("$", ""));

            } else {

                cart_adddeleivery_charge = 0.0;
            }
*/
            InclusiveGst(cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge);
            txtTotal.setText(String.format("%.2f", cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge + gstAmount));
//            txtTotal.setText(String.format("%.2f", cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge));
            txtTotalSymbol.setVisibility(View.VISIBLE);


        }

    }

    private class CartListTask extends AsyncTask<String, Void, String> {

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

                    JSONObject jsonResult = jsonObject.getJSONObject("result_set");

                    JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");


                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, jsoncartDetails.optString("cart_total_items"));

                    SubCategoryActivity.cart_sub_total = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));

                    txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));
                    txtSubTotalSymbol.setVisibility(View.VISIBLE);
                    r_sub_total = jsoncartDetails.getString("cart_sub_total");
                    subTotal = jsoncartDetails.getString("cart_sub_total");
                    setDeliveryAddress();
                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));



                        /*setCustomProgress();

                        double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(r_sub_total);

                        if (d_progress_limit > 0) {

                            GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");


                            Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();


                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                            if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {

                                GlobalValues.DELEIVERY_CHARGES = "0.00";
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");


                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                                //  txtDeliveryCharge.setText("$0.00");

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;


                                } else {

                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();
                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;


                                }


                            } else {


                                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                                //  txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

                                // Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();


                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                            Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));


                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;
                                    // txtGSTLabel.setText("GST ("+"7"+"%)" );
                                    // txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));


                                } else {

                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();

                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;
                                    //  txtGSTLabel.setText("GST ("+"7"+"%)" );


                                }

                            }

                            // txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");

                            //progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());


                        } else {

                            GlobalValues.DELEIVERY_CHARGES = "0.00";
                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                            txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                    Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                            //  txtDeliveryCharge.setText("$0.00");
                            // txtFreeDelivery.setText("FREE delivery!");

                            // Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();


//                            progressBar.setProgress(1000);

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));


                                mGST = (mGrandTotal * 7) / 100;
                                GlobalValues.GST = mGST;
                                mGrandTotal += mGST;
                                //  txtGSTLabel.setText("GST ("+"7"+"%)" );
                                // txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));


                            } else {

                                int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                mGST = (mGrandTotal * gst_values) / 100;
                                GlobalValues.GST = mGST;
                                mGrandTotal += mGST;


                            }


                        }

//                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                         */
                    } else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));


                  /*    int gst_values = (int) mGrandTotal;


                        mGST = (mGrandTotal * gst_values) / 100;
                        GlobalValues.GST = mGST;
                        mGrandTotal += mGST;*/

                    }


                    JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("result_set").toString());


                    setCartAdapter(jsonCartItem);
                    if (foodVoucher) {
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                    }


                    if (!(jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0")
                            || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("null") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0.00"))) {
                        layoutVoucher.setVisibility(View.VISIBLE);
                        voucherPrice.setText(jsoncartDetails.getString("cart_voucher_discount_amount") + ")");
                        cartVoucherDiscountAmt = jsoncartDetails.getString("cart_voucher_discount_amount");
                        setVoucherTotal(Double.parseDouble(cartVoucherDiscountAmt));
                    } else {
                        layoutVoucher.setVisibility(View.GONE);
                        InclusiveGst(mGrandTotal);
                        txtTotal.setText(String.format("%.2f", mGrandTotal + mGrandTotalGst));
                        txtTotalSymbol.setVisibility(View.VISIBLE);
                    }

                    //InclusiveGst(mGrandTotal);
                    // txtGST.setText("$" + String.format("%.2f", mGST));
                    //txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
                    //txtTotal.setText(String.format("%.2f", mGrandTotal));

                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
                /*if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                      if (foodVoucher) {
                        layoutDeliveryCharge.setVisibility(View.GONE);
                        layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                        txtDeliveryCharge.setText("0.00");
                        deliveryCharges = 0.00;
                        txtAdditionalDeliveryCharge.setText("0.00");
                        additionalDeliveryCharges = 0.00;
                      }
                }*/
                //setDeliveryAddress();
            }
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


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObject.getJSONObject("result_set");

                    mrewardPoint = countJson.optDouble("reward_ponits");

                    // txtRedeem.setText("You have " + String.valueOf(countJson.optDouble("reward_ponits")) + " points available");
                    // txtRedeemPoints.setHint("You can Redeem " + String.valueOf(countJson.optDouble("reward_ponits")) + " points");
                    //txtPromotions.setText("You have " + String.valueOf(countJson.optInt("promotionwithoutuqc")) + " Promotions Available");

                    setPromotionSpan(" " + countJson.optString("promotionwithoutuqc") + " Promotions");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");
//                    double x_reedempoints = countJson.optDouble("reward_ponits");
//                    GlobalValues.Customer_reward_point=x_reedempoints;
//
//czzxfcv
//
//                    if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
//                        txtOrderCount.setVisibility(View.VISIBLE);
//                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
//                    } else {
//                        txtOrderCount.setVisibility(View.GONE);
//                    }
//
//                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {
//
//                        txtPromotionCount.setVisibility(View.VISIBLE);
//                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);
//
//                    } else {
//                        txtPromotionCount.setVisibility(View.GONE);
//                    }
//
//                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {
//
//                        txtNotificationCount.setVisibility(View.GONE);
//                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
//                    } else {
//                        txtNotificationCount.setVisibility(View.GONE);
//                    }
//
//                    setTabItem();
//
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

            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            new ActivCountTask(mapData).execute(url);

        }
    }


    private void setCartAdapter(JSONArray jsonCartItem) {

        try {
            cartList = new ArrayList<>();
            String Matched = "";

            String Added = "";

            int BENTO_Count = 0;


            if (jsonCartItem.length() > 0) {
                txtEmpty.setVisibility(View.GONE);
                orderRecyclerView.setVisibility(View.VISIBLE);

                for (int c = 0; c < jsonCartItem.length(); c++) {

                    JSONObject jsonItem = null;
                    jsonItem = jsonCartItem.getJSONObject(c);

                    Cart cart = new Cart();

                    cart.setmCartItemId(jsonItem.getString("cart_item_id"));
                    cart.setmProductId(jsonItem.getString("cart_item_product_id"));
                    cart.setmProductName(jsonItem.getString("cart_item_product_name"));

                    cart.setmProductImage(jsonItem.getString("cart_item_product_image"));
                    cart.setmProductType(jsonItem.getString("cart_item_type"));
                    cart.setmProductQty(jsonItem.getString("cart_item_qty"));
                    cart.setmProductQtyPrice(jsonItem.getString("cart_item_unit_price"));
                    cart.setmProductTotalPrice(jsonItem.getString("cart_item_total_price"));
                    cart.setmBasePath(jsonItem.getString("baby_image_path"));
                    cart.setmSpecialNotes(jsonItem.getString("cart_item_special_notes"));
                    cart.setmProductSku(jsonItem.getString("cart_item_product_sku"));
                    cart.setmProductVoucherGiftName(jsonItem.getString("cart_item_product_voucher_gift_name"));
                    cart.setmProductVoucherGiftEmail(jsonItem.getString("cart_item_product_voucher_gift_email"));
                    cart.setmProductVoucherGiftMobile(jsonItem.getString("cart_item_product_voucher_gift_mobile"));
                    cart.setmProductVoucherGiftMessage(jsonItem.getString("cart_item_product_voucher_gift_message"));
                    cart.setmVoucherOrderItemId(jsonItem.getString("cart_voucher_order_item_id"));
                    cart.setmProductVoucher(jsonItem.getString("cart_product_voucher"));
                    cart.setmProductVoucherIncreaseQty(jsonItem.getString("product_voucher_increase_qty"));
                    cart.setmProductMinQty(jsonItem.getString("cart_item_min_qty"));
                    cart.setmProductDiscountVoucherName(jsonItem.getString("product_discount_voucher_name"));
                    cart.setmCartItemVoucherId(jsonItem.getString("cart_item_voucher_id"));
                    cart.setmCashVoucherOrderItemId(jsonItem.getString("cart_voucher_order_item_id"));
                    cart.setmOrderItemVoucherBalanceQty(jsonItem.getString("order_item_voucher_balance_qty"));
                    cart.setmCartItemVoucherProductFree(jsonItem.getString("cart_item_voucher_product_free"));
                    cart.setCart_item_promotion_discount(jsonItem.optString("cart_item_promotion_discount"));
                    cart.setCart_item_promotion_type(jsonItem.optString("cart_item_promotion_type"));

                    if (jsonItem.getString("cart_voucher_order_item_id").equalsIgnoreCase("0") &&
                            jsonItem.getString("cart_item_type").equalsIgnoreCase("Simple")) {
                        foodVoucher = true;
                    } else {
                        foodVoucher = false;
                    }

                    String cart_item_isaddon = jsonItem.optString("cart_item_isaddon");

                    if (cart_item_isaddon.equalsIgnoreCase("No")) {

                        BENTO_Count = BENTO_Count + jsonItem.optInt("cart_item_qty");

                    }


                    if (c == 0) {
                        Matched = jsonItem.getString("cart_item_product_id");

                        Added = jsonItem.getString("cart_item_qty");

                        updateCurrentCartQuantity(jsonItem.getString("cart_item_product_id"), "" + Added);

                    } else {
                        if (Matched.equals(jsonItem.getString("cart_item_product_id"))) {
                            int add = Integer.valueOf(Added) + Integer.valueOf(jsonItem.getString("cart_item_qty"));

                            updateCurrentCartQuantity(jsonItem.getString("cart_item_product_id"), "" + add);

                        } else {

                            updateCurrentCartQuantity(jsonItem.getString("cart_item_product_id"), jsonItem.getString("cart_item_qty"));

                        }

                    }


                    JSONArray modifierArray = jsonItem.getJSONArray("modifiers");

                    List<CartModifier> cartModifierList = new ArrayList<>();

                    if (modifierArray.length() > 0) {
                        for (int m = 0; m < modifierArray.length(); m++) {
                            JSONObject modifier = modifierArray.getJSONObject(m);

                            CartModifier cartModifier = new CartModifier();

                            cartModifier.setmModifierId(modifier.getString("cart_modifier_id"));
                            cartModifier.setmModifierName(modifier.getString("cart_modifier_name"));


                            JSONArray modifierValueArray = modifier.getJSONArray("modifiers_values");

                            List<CartModifierValue> cartModifierValueList = new ArrayList<>();

                            if (modifierValueArray.length() > 0) {
                                for (int v = 0; v < modifierValueArray.length(); v++) {
                                    JSONObject value = modifierValueArray.getJSONObject(v);

                                    CartModifierValue cartModifierValue = new CartModifierValue();

                                    cartModifierValue.setmModifierValueId(value.getString("cart_modifier_id"));
                                    cartModifierValue.setmModifierValueName(value.getString("cart_modifier_name"));
                                    cartModifierValue.setmModifierValuePrice(value.getString("cart_modifier_price"));
                                    cartModifierValue.setmModifierValueQuantity(value.getString("cart_modifier_qty"));

                                    cartModifierValueList.add(cartModifierValue);

                                }

                            }

                            cartModifier.setCartModifierValueList(cartModifierValueList);

                            cartModifierList.add(cartModifier);

                        }

                    } else {

                    }
                    cart.setCartModifierList(cartModifierList);

                    //set menu product
                    JSONArray setmenuJsonArray = jsonItem.getJSONArray("set_menu_component");

                    List<SetMenuTitle> setMenuTitleList = new ArrayList<>();
                    if (setmenuJsonArray.length() > 0) {

                        for (int t = 0; t < setmenuJsonArray.length(); t++) {

                            JSONObject setmenuObject = setmenuJsonArray.getJSONObject(t);

                            SetMenuTitle setMenuTitle = new SetMenuTitle();

                            setMenuTitle.setmTitleMenuName(setmenuObject.optString("menu_component_name"));
                            setMenuTitle.setmultipleselection_apply(setmenuObject.optString("menu_component_multipleselection_apply"));
                            setMenuTitle.setmenu_component_modifier_apply(setmenuObject.optString("menu_component_modifier_apply"));
                            GlobalValues.MULTIPLESLECTIONAPPLY = setmenuObject.optString("menu_component_multipleselection_apply");
                            GlobalValues.MODIFIERAPPLY = setmenuObject.optString("menu_component_modifier_apply");
                            setMenuTitle.setmTitleMenuId(setmenuObject.optString("menu_component_id"));
                            cart.setpType(setmenuObject.optString("cart_menu_component_min_max_appy"));

                            JSONArray productJsonArray = setmenuObject.getJSONArray("product_details");

                            List<SetMenuModifier> setMenuModifierList = new ArrayList<>();

                            if (productJsonArray.length() > 0) {
                                for (int p = 0; p < productJsonArray.length(); p++) {
                                    JSONObject setmenuModifireObject = productJsonArray.getJSONObject(p);

                                    SetMenuModifier setMenuModifier = new SetMenuModifier();

                                    setMenuModifier.setmModifierName(setmenuModifireObject.optString("cart_menu_component_product_name"));
                                    setMenuModifier.setmModifierPrice(setmenuModifireObject.optString("cart_menu_component_product_price"));
                                    setMenuModifier.setmModifierSku(setmenuModifireObject.optString("cart_menu_component_product_sku"));
                                    setMenuModifier.setmModifierId(setmenuModifireObject.optString("cart_menu_component_product_id"));
                                    setMenuModifier.setmQuantity(Integer.parseInt(setmenuModifireObject.optString("cart_menu_component_product_qty")));
                                    setMenuModifier.setmMin_Max_apply(setmenuModifireObject.optString("cart_menu_component_min_max_appy"));
                                    setMenuModifier.setmQuantityUpdates(Integer.parseInt(setmenuModifireObject.optString("cart_menu_component_product_qty")));

                                    // setMenuModifier.setmQuantity(Integer.parseInt(setmenuModifireObject.optString("cart_menu_component_product_qty")));


                                    JSONArray modifierJsonArray = setmenuModifireObject.getJSONArray("modifiers");

                                    List<ModifierHeading> modifierHeadingList = new ArrayList<>();

                                    if (modifierJsonArray.length() > 0) {

                                        for (int m = 0; m < modifierJsonArray.length(); m++) {
                                            JSONObject modifierObject = modifierJsonArray.getJSONObject(m);

                                            ModifierHeading modifierHeading = new ModifierHeading();

                                            modifierHeading.setmModifierHeading(modifierObject.optString("cart_modifier_name"));
                                            modifierHeading.setmModifierHeadingId(modifierObject.optString("cart_modifier_id"));


                                            JSONArray valueJsonArray = modifierObject.getJSONArray("modifiers_values");

                                            List<ModifiersValue> modifiersValueList = new ArrayList<>();

                                            if (valueJsonArray.length() > 0) {
                                                for (int v = 0; v < valueJsonArray.length(); v++) {
                                                    JSONObject valueObject = valueJsonArray.getJSONObject(v);

                                                    ModifiersValue modifiersValue = new ModifiersValue();

                                                    modifiersValue.setmModifierName(valueObject.optString("cart_modifier_name"));
                                                    modifiersValue.setmModifierId(valueObject.optString("cart_modifier_id"));
                                                    modifiersValue.setmModifierValuePrice(valueObject.optString("cart_modifier_price"));
                                                    modifiersValue.setmSubModifierTotal(Integer.valueOf(valueObject.optString("cart_modifier_qty")));

                                                    modifiersValueList.add(modifiersValue);
                                                }
                                            }

                                            modifierHeading.setModifiersList(modifiersValueList);

                                            modifierHeadingList.add(modifierHeading);

                                        }
                                    }

                                    setMenuModifier.setModifierHeadingList(modifierHeadingList);
                                    setMenuModifierList.add(setMenuModifier);
                                }
                            }

                            setMenuTitle.setSetMenuModifierList(setMenuModifierList);

                            setMenuTitleList.add(setMenuTitle);
                        }


                    } else {

                    }

                    cart.setSetMenuTitleList(setMenuTitleList);

                    cartList.add(cart);

                }

                GlobalValues.isFoodVoucher = foodVoucher;
                setDeliveryAddress();
                Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT, "" + BENTO_Count);

                cartAdapter = new CartRecyclerAdapter(mContext, cartList);
                orderRecyclerView.setAdapter(cartAdapter);
                orderRecyclerView.setItemAnimator(new DefaultItemAnimator());
                orderRecyclerView.setNestedScrollingEnabled(false);

                cartAdapter.setOnDeleteClickListener(new ICartItemClick() {
                    @Override
                    public void updateOverallCartItems(View view, int position, Cart cart) {
                        tQuantity = 0;
                        plusminusPrice = 0.00;
                        cartSplNotes = "";
                        mSetmenuoverallprices = 0.00;
                        if (foodVoucher) {
                            layoutDeliveryCharge.setVisibility(View.GONE);
                            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                            txtDeliveryCharge.setText("0.00");
                            deliveryCharges = 0.00;
                            txtAdditionalDeliveryCharge.setText("0.00");
                            additionalDeliveryCharges = 0.00;
                        }
                        CurrentPosition = position;
                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            mReferenceId = "";
                        } else {
                            mCustomerId = "";
                            mReferenceId = Utility.getReferenceId(mContext);
                        }

                        if (cartList.get(position).getmProductType().equals("Component")) {
                            plusminusPrice = 0.00;
                            if (Utility.networkCheck(mContext)) {
                                GlobalValues.SELECTEDMODIFIRESUBLIST.clear();
                                GlobalValues.SELECTEDMODIFIRELIST.clear();
                                GlobalValues.SELECTEDMODIFIERTITLES.clear();
                                GlobalValues.SELECTEDMODIFIERVALUES.clear();

                                if (cartList.get(position).getmSpecialNotes() != null && !cartList.get(position).getmSpecialNotes().equalsIgnoreCase("") &&
                                        !cartList.get(position).getmSpecialNotes().equalsIgnoreCase("null")) {
                                    cartSplNotes = cartList.get(position).getmSpecialNotes();
                                }


                                if (cartList.get(position).getSetMenuTitleList() != null && cartList.get(position).getSetMenuTitleList().size() > 0) {

                                    String name = "";

                                    List<SetMenuTitle> setMenuTitleList = cartList.get(position).getSetMenuTitleList();

                                    for (int t = 0; t < setMenuTitleList.size(); t++) {
                                        SetMenuTitle setMenuTitle = setMenuTitleList.get(t);

                                        Log.e("apply_price", setMenuTitle.getmAppliedPrice() + "----");

                                        name += setMenuTitle.getmTitleMenuName() + ":";

                                        List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                                        if (setMenuModifierList != null && setMenuModifierList.size() > 0) {

                                            for (int sm = 0; sm < setMenuTitle.getSetMenuModifierList().size(); sm++) {
                                                SetMenuModifier setMenuModifier = setMenuTitle.getSetMenuModifierList().get(sm);

                                                if (setMenuModifier.getmQuantity() > 0) {
                                                    name += "" + setMenuModifier.getmQuantity() + "x";
                                                } else {
                                                    name += "";
                                                }
                                                GlobalValues.SELECTEDMODIFIRELIST.add(setMenuModifier.getmModifierId());
                                                GlobalValues.SELECTEDMODIFIRE = setMenuModifier.getmModifierId();
                                                name += setMenuModifier.getmModifierName() + "\n";

                                                List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();


                                                if (modifierHeadingList != null && modifierHeadingList.size() > 0) {
                                                    for (int h = 0; h < modifierHeadingList.size(); h++) {
                                                        ModifierHeading modifierHeading = modifierHeadingList.get(h);

                                                        SelectedModifierHeading smh = new SelectedModifierHeading();
                                                        smh.setId(modifierHeading.getmModifierHeadingId());

                                                        tQuantity = 0;

                                                        name += modifierHeading.getmModifierHeading() + ":" + "\n";

                                                        List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                                        if (modifiersValueList.size() > 0) {

                                                            for (int v = 0; v < modifiersValueList.size(); v++) {
                                                                ModifiersValue modifiersValue = modifiersValueList.get(v);

                                                                name += modifiersValue.getmModifierName();

                                                                GlobalValues.SELECTEDMODIFIRESUB = modifiersValue.getmModifierId();
                                                                GlobalValues.SELECTEDMODIFIRESUB = modifiersValue.getmModifierId();
                                                                GlobalValues.SELECTEDMODIFIRESUBLIST.add(modifiersValue.getmModifierId());
                                                                GlobalValues.SETMENUMODIFIREVALUE = modifiersValue.getmModifierValuePrice();

                                                                SelectedModifierValue smv = new SelectedModifierValue();
                                                                smv.setModifierId(modifiersValue.getmModifierId());
                                                                smv.setModifierQty(modifiersValue.getmSubModifierTotal());
                                                                GlobalValues.SELECTEDMODIFIERVALUES.add(smv);

                                                                tQuantity += modifiersValue.getmSubModifierTotal();

                                                                Log.e("mod", modifiersValue.getmModifierValuePrice() + modifiersValue.getmSubModifierTotal());
                                                                plusminusPrice += (Double.parseDouble(modifiersValue.getmModifierValuePrice()) * modifiersValue.getmSubModifierTotal());
                                                            }

                                                            smh.setQuantity(tQuantity);
                                                            GlobalValues.SELECTEDMODIFIERTITLES.add(smh);
                                                        }
                                                    }


                                                }
                                            }
                                        }
                                    }
                                }
                                Log.e("plusminu", String.valueOf(plusminusPrice));
                                mProductId = cartList.get(position).getmProductId();
                                TotalPriceSetMenu = cartList.get(position).getmProductTotalPrice();

                                subModifierPrice = 0.00;
                                String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                                        "&availability=" + CURRENT_AVAILABLITY_ID +
                                        "&product_id=" + mProductId + "&fav_cust_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                                new SetMenuProductDetailsTask(mProductId, cart.getmProductQty()).execute(url);


                                // new SetMenuProductDetailsTaskNew(mProductId).execute(url);

                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        } else if (cartList.get(position).getmProductType().equals("Simple")) {

                            if (Utility.networkCheck(mContext)) {
                                GlobalValues.SELECTEDMODIFIRESUBLIST.clear();
                                GlobalValues.SELECTEDMODIFIRELIST.clear();
                                if (cartList.get(position).getSetMenuTitleList() != null && cartList.get(position).getSetMenuTitleList().size() > 0) {

                                    String name = "";

                                    List<SetMenuTitle> setMenuTitleList = cartList.get(position).getSetMenuTitleList();

                                    for (int t = 0; t < setMenuTitleList.size(); t++) {
                                        SetMenuTitle setMenuTitle = setMenuTitleList.get(t);

                                        name += setMenuTitle.getmTitleMenuName() + ":";

                                        List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                                        if (setMenuModifierList != null && setMenuModifierList.size() > 0) {

                                            for (int sm = 0; sm < setMenuTitle.getSetMenuModifierList().size(); sm++) {
                                                SetMenuModifier setMenuModifier = setMenuTitle.getSetMenuModifierList().get(sm);

                                                if (setMenuModifier.getmQuantity() > 0) {
                                                    name += "" + setMenuModifier.getmQuantity() + "x";


                                                } else {
                                                    name += "";

                                                }
                                                GlobalValues.SELECTEDMODIFIRELIST.add(setMenuModifier.getmModifierId());
                                                GlobalValues.SELECTEDMODIFIRE = setMenuModifier.getmModifierId();
                                                name += setMenuModifier.getmModifierName() + "\n";

                                                List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();


                                                if (modifierHeadingList != null && modifierHeadingList.size() > 0) {
                                                    for (int h = 0; h < modifierHeadingList.size(); h++) {
                                                        ModifierHeading modifierHeading = modifierHeadingList.get(h);


                                                        name += modifierHeading.getmModifierHeading() + ":" + "\n";

                                                        List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                                        if (modifiersValueList.size() > 0) {

                                                            for (int v = 0; v < modifiersValueList.size(); v++) {
                                                                ModifiersValue modifiersValue = modifiersValueList.get(v);

                                                                name += modifiersValue.getmModifierName();

                                                                GlobalValues.SELECTEDMODIFIRESUB = modifiersValue.getmModifierId();
                                                                GlobalValues.SELECTEDMODIFIRESUBLIST.add(modifiersValue.getmModifierId());
                                                                GlobalValues.SETMENUMODIFIREVALUE = modifiersValue.getmModifierValuePrice();
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }


                                }

                                mProductId = cartList.get(position).getmProductId();
                                TotalPriceSetMenu = cartList.get(position).getmProductTotalPrice();


                                String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                                        "&availability=" + CURRENT_AVAILABLITY_ID +
                                        "&product_id=" + mProductId + "&fav_cust_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                                new SetMenuProductDetailsTask(mProductId, cart.getmProductQty()).execute(url);

                                // new SetMenuProductDetailsTaskNew(mProductId).execute(url);

                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }

                        }
                    }

                    @Override
                    public void makeapicall(String productId, Cart cartProduct, String type, int position) {
                        if (foodVoucher) {
                            layoutDeliveryCharge.setVisibility(View.GONE);
                            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                            txtDeliveryCharge.setText("0.00");
                            deliveryCharges = 0.00;
                            txtAdditionalDeliveryCharge.setText("0.00");
                            additionalDeliveryCharges = 0.00;
                        }
                        CurrentPosition = position;

                        makeUpdateApiCall(productId, cartProduct, type);
                        Cart cart = cartList.get(position);
                        Log.e("cart", cart.toString() + "--------");
                    }

                    @Override
                    public void deleteCartItem(View view, int position) {
                        if (foodVoucher) {
                            layoutDeliveryCharge.setVisibility(View.GONE);
                            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                            txtDeliveryCharge.setText("0.00");
                            deliveryCharges = 0.00;
                            txtAdditionalDeliveryCharge.setText("0.00");
                            additionalDeliveryCharges = 0.00;
                        }
                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            mReferenceId = "";

                        } else {
                            mCustomerId = "";
                            mReferenceId = Utility.getReferenceId(mContext);
                        }

                        if (Utility.networkCheck(mContext)) {

                            String url = GlobalUrl.DELETE_SINGLE_CART_URL;

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("cart_item_id", cartList.get(position).getmCartItemId());
                            params.put("customer_id", mCustomerId);
                            params.put("reference_id", "");

                            new DeleteCartItemTask(params, cartList.get(position).getmProductId()).execute(url);

                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void updateCartItem(View view, int position, int quantity) {
                        if (foodVoucher) {
                            layoutDeliveryCharge.setVisibility(View.GONE);
                            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                            txtDeliveryCharge.setText("0.00");
                            deliveryCharges = 0.00;
                            txtAdditionalDeliveryCharge.setText("0.00");
                            additionalDeliveryCharges = 0.00;
                        }
                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            mReferenceId = "";

                        } else {
                            mCustomerId = "";
                            mReferenceId = Utility.getReferenceId(mContext);
                        }

                        if (Utility.networkCheck(mContext)) {

                            String url = GlobalUrl.UPDATE_CART_URL;

                            Map<String, String> params = new HashMap<String, String>();

                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("cart_item_id", cartList.get(position).getmCartItemId());
                            params.put("customer_id", mCustomerId);
                            params.put("reference_id", "");
                            params.put("product_id", cartList.get(position).getmProductId());
                            params.put("product_qty", String.valueOf(quantity));
                            if (!(cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase("null") || cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase("0.00")
                                    || cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase("0") || cartList.get(position).getmOrderItemVoucherBalanceQty().equalsIgnoreCase(""))
                                    && cartList.get(position).getmProductType().equalsIgnoreCase("Simple")) {
                                params.put("voucher_order_id", cartList.get(position).getmCashVoucherOrderItemId());
                            }

                            new UpdateCartItemTask(params, position).execute(url);


                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

            } else {
                txtEmpty.setVisibility(View.VISIBLE);
                orderRecyclerView.setVisibility(View.GONE);
                Intent start = new Intent(mContext, SubCategoryActivity.class);
                startActivity(start);
                finish();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  /*  public void updateQuality(String matched, int position)
    {

        ArrayList<Integer> integerArrayList  = new ArrayList<>();


        for(int i=0;i<cartList.size();i++)
        {

                if(matched.equalsIgnoreCase(cartList.get(i).getmProductId()))
                {

                    *//*int add = Integer.valueOf(cartList.get(i).getmProductQty()) + Integer.valueOf(cartList.get(i).getmProductQty());

                    updateCurrentCartQuantity(cartList.get(i).getmProductId(), ""+add);*//*

                    integerArrayList.add(Integer.v)
                }

        }


    }*/

    private void updateCurrentCartQuantity(String id, String qty) {

        try {
            databaseHandler.updateQty(id, qty);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void placeCashOnDeliveryOrder() {

        try {
            jsonCartObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());


            JSONObject jsonCartDetails = jsonCartObject.getJSONObject("cart_details");

            JSONArray cartJsonArray = jsonCartObject.getJSONArray("cart_items");

            StringBuilder productIdsStringBuilder = new StringBuilder();

            for (int i = 0; i < cartJsonArray.length(); i++) {
                JSONObject cartItem = cartJsonArray.getJSONObject(i);
                if (i != (cartJsonArray.length() - 1))
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price") + ";");
                else {
                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                            cartItem.getString("cart_item_total_price"));
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

            Map<String, String> params = new HashMap<>();
            params.put("app_id", GlobalValues.APP_ID);

            params.put("order_source", "Mobile");
            params.put("order_status", "1");
            params.put("products", getProductJSONArray(cartJsonArray).toString());
            params.put("availability_id", CURRENT_AVAILABLITY_ID);
            params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
            params.put("customer_email", Utility.readFromSharedPreference(mContext, GlobalValues.EMAIL));
            params.put("customer_mobile_no", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));
            params.put("customer_fname", Utility.readFromSharedPreference(mContext, GlobalValues.FIRSTNAME));
            params.put("customer_lname", Utility.readFromSharedPreference(mContext, GlobalValues.LASTNAME));
            params.put("category_id", productIdsStringBuilder.toString());
            params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
            //  params.put("order_remarks", edtComments.getText().toString());

            mCarttotal = Double.parseDouble(txtTotal.getText().toString().replace("$", ""));


            if (mCarttotal > 0) {

                params.put("zero_processing", "No");
                params.put("allow_order", "No");
                params.put("validation_only", "Yes");
                params.put("payment_mode", "1");
                params.put("sub_total", jsonCartDetails.getString("cart_sub_total"));
                params.put("grand_total", txtTotal.getText().toString().replace("$", ""));
            } else {
                params.put("zero_processing", "Yes");
                params.put("allow_order", "Yes");
                params.put("validation_only", "No");
                params.put("payment_mode", "4");
                params.put("sub_total", jsonCartDetails.getString("cart_sub_total"));
                params.put("grand_total", txtTotal.getText().toString().replace("$", ""));
            }

            params.put("device_type", "Android");
            params.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);

            params.put("reward_point_status", "Yes");


            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                    CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                JSONObject jsonObject_postinfromation = outletJson.getJSONObject("postal_code_information");


                params.put("customer_address_line1", jsonObject_postinfromation.getString("zip_sname"));
                params.put("customer_postal_code", jsonObject_postinfromation.getString("zip_code"));

                params.put("billing_address_line1", edtBillingAddress.getText().toString());
                params.put("billing_postal_code", edtPincode.getText().toString());
                params.put("billing_unit_no1", edtBillingUnitNo1.getText().toString());
                params.put("billing_unit_no2", edtBillingUnitNo2.getText().toString());

                params.put("order_tat_time", mTatTime + "");
                params.put("customer_unit_no1", mUnitNo1);
                params.put("customer_unit_no2", mUnitNo2);

            }

            params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);
            params.put("delivery_charge", String.valueOf(GlobalValues.COMMON_DELIVERY_CHARGE));
            params.put("additional_delivery", GlobalValues.ADDITIONAL_DELEIVERY_CHARGES);


            if (GlobalValues.DISCOUNT_APPLIED) {


                params.put("discount_applied", "Yes");

                if (GlobalValues.DISCOUNT_TYPE.equalsIgnoreCase("COUPON")) {

                    if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {

                        params.put("promotion_delivery_charge_applied", "Yes");

                    } else {

                     /*   params.put("promo_code", p_code);
                        params.put("coupon_amount", p_amount);
                        params.put("coupon_applied", "Yes");*/

                    }

                } else if (GlobalValues.DISCOUNT_TYPE.equalsIgnoreCase("REWARD")) {
                    params.put("redeem_applied", "Yes");
                    params.put("redeem_point", r_point);
                    params.put("redeem_amount", r_amount);

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
//            params.put("tax_charge",  GlobalValues.GstChargers);
//            params.put("order_tax_calculate_amount", String.valueOf(gstAmount));

//
            System.out.print(params.toString());

            if (Utility.networkCheck(mContext)) {

                String url = GlobalUrl.SUBMIT_ORDER_URL;

                new CashOnDeliveryValidateOrderTask(params).execute(url);
            } else {
                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }

        } catch (JSONException e) {
            e.printStackTrace();
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


                submitOrderJSONArray.put(productJSONObject);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

        return submitOrderJSONArray;

    }


    private class CashOnDeliveryValidateOrderTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> orderParams;

        public CashOnDeliveryValidateOrderTask(Map<String, String> orderParams) {
            this.orderParams = orderParams;
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


            String response = WebserviceAssessor.postRequest(mContext, params[0], orderParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                 try {

                    JSONObject jsonObject = new JSONObject(s);

                    // JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    if (mCarttotal > 0) {

                        if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
//TODO
                            intent = new Intent(mContext, PaymentActivity.class);
                            intent.putExtra("sub_total", jsonCartObject.getJSONObject("cart_details").getString("cart_sub_total"));
                            intent.putExtra("total_price", String.valueOf(txtTotal.getText().toString().replace("$", "")));
                            intent.putExtra("unit_no1", edtUnitNo1.getText().toString());
                            intent.putExtra("unit_no2", edtUnitNo2.getText().toString());
                            intent.putExtra("billing_address", edtBillingAddress.getText().toString());
                            intent.putExtra("billing_pincode", edtPincode.getText().toString());
                            intent.putExtra("billing_unit_no1", edtBillingUnitNo1.getText().toString());
                            intent.putExtra("billing_unit_no2", edtBillingUnitNo2.getText().toString());
                            intent.putExtra("order_remarks", edtComments.getText().toString());
                            intent.putExtra("REDEEM_APPLIED", r_applied);
                            intent.putExtra("REDEEM_POINT", r_point);
                            intent.putExtra("redeem_amount", r_amount);

                            intent.putExtra("promo_code", mPromoCoupon);
                            intent.putExtra("promo_amount", mPromotionAmount);

                            startActivity(intent);

                        } else {

                            Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        JSONObject jsonCommon = jsonObject.getJSONObject("common");

                        mOrderNo = jsonCommon.getString("local_order_no");

                        String url = GlobalUrl.DESTROY_CART_URL;
                        Map<String, String> params = new HashMap<>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

//                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        if (Utility.networkCheck(mContext)) {

                            fromChangeAddress = 0;
                            new DestroyCartTask(params).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    //    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                } catch (Exception e) {
                    e.printStackTrace();
                    progressDialog.dismiss();
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                {
                    progressDialog.dismiss();
                }
            }
        }
    }

    private class DeleteCartItemTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> deleteParams;
        private String mProductId = "";

        public DeleteCartItemTask(Map<String, String> deleteParams, String id) {
            this.deleteParams = deleteParams;
            mProductId = id;
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


            String response = WebserviceAssessor.postRequest(mContext, params[0], deleteParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                ///  Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    deleteCurrentQuantity(mProductId);

                    if (!jsonObject.isNull("contents")) {

                        JSONObject jsonResult = jsonObject.getJSONObject("contents");


                        JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");

/*

                        if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removePromotion();
                        }


                        if (txtRedeemApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removeRewardPoints();
                        }
*/

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, jsoncartDetails.getString("cart_total_items"));

                        SubCategoryActivity.cart_sub_total = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));
                        txtSubTotalSymbol.setVisibility(View.VISIBLE);

                        r_sub_total = jsoncartDetails.getString("cart_sub_total");


                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                                CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                           /* setCustomProgress();
                            double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery"))
                                    - Double.valueOf(r_sub_total);

                            if (d_progress_limit > 0) {

                                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");


                                //txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                             *//*   txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                                txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
*//*
                                //progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;
                                } else {


                                    GlobalValues.GstChargers = Utility.readFromSharedPreference(mContext, GlobalValues.GstCharger);

                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;

                                }


                            } else {


                                GlobalValues.DELEIVERY_CHARGES = "0.00";
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
                                //  txtDeliveryCharge.setText("$0.00");
                              *//*  txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                                txtFreeDelivery.setText("FREE delivery!");*//*

//                            progressBar.setProgress(1000);

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                            Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));


                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;

                                    //    txtGSTLabel.setText("GST ("+"7"+"%)" );
                                    //  txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));


                                } else {

                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;


                                }


                            }

//                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                            */

                        } else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            GlobalValues.DELEIVERY_CHARGES = "0.00";
                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));

                        }


//                        subtotal_value = jsoncartDetails.getString("cart_sub_total");

                     /*   if (Double.parseDouble(outletZoneJson.optString("zone_free_delivery")) > 0) {

                            progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
                            progressBar.setSecondaryProgress((int) Double.parseDouble(
                                    outletZoneJson.optString("zone_free_delivery")));
                        } else {

                            progressBar.setProgress(1000);
                            progressBar.setSecondaryProgress(1000);
                        }
*/

                      /*  if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

//                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }*/
                        // txtGST.setText("$" + String.format("%.2f", mGST));


                        JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());


//                        Remove applied coupon or reward points

                        setCartAdapter(jsonCartItem);

                        if (foodVoucher) {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }

                        if (!(jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0")
                                || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("null") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0.00"))) {
                            layoutVoucher.setVisibility(View.VISIBLE);
                            voucherPrice.setText(jsoncartDetails.getString("cart_voucher_discount_amount") + ")");
                            cartVoucherDiscountAmt = jsoncartDetails.getString("cart_voucher_discount_amount");
                            setVoucherTotal(Double.parseDouble(cartVoucherDiscountAmt));
                        } else {
                            layoutVoucher.setVisibility(View.GONE);
                            InclusiveGst(mGrandTotal);
                            txtTotal.setText(String.format("%.2f", mGrandTotal));
                            txtTotalSymbol.setVisibility(View.VISIBLE);
                        }

                        //InclusiveGst(mGrandTotal);
                        //txtTotal.setText(String.format("%.2f", gstAmount + mGrandTotal));
                        //txtTotal.setText(String.format("%.2f", mGrandTotal));
                    } else {

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");

                        Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT, "");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, "");

                        invalidateOptionsMenu();

                        Intent start = new Intent(mContext, SubCategoryActivity.class);
                        startActivity(start);
                        finish();

                        try {
                            databaseHandler.deleteAllCartQuantity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

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

    public void setVoucherTotal(double points_amount) {

        double total = 0.00;
        if (txtSubTotal.getText().toString().length() != 0) {
            total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) - points_amount;

            //txtTotal.setText(String.format("%.2f", total));
        } else {
            total = 0.0;
            //txtTotal.setText("0.00");
        }
        if (total > 0.0) {
            InclusiveGst(total + deliveryCharges + additionalDeliveryCharges);
            txtTotal.setText(String.format("%.2f", total + deliveryCharges + additionalDeliveryCharges));
            txtTotalSymbol.setVisibility(View.VISIBLE);
        } else {
            InclusiveGst(deliveryCharges + additionalDeliveryCharges);
            txtTotal.setText(String.format("%.2f", deliveryCharges + additionalDeliveryCharges));
            txtTotalSymbol.setVisibility(View.VISIBLE);
        }

    }

    private void deleteCurrentQuantity(String mProductId) {

        try {

            databaseHandler.deleteCartQuantity(mProductId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openDeliveryDateDialog(final Context context) {
        // Create custom dialog object


        isSelectTime = false;
        is_time_shown = false;
        is_date_shown = false;

        final Dialog dialog = new Dialog(context, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        // Include dialog.xml file
        dialog.setContentView(R.layout.layout_delivery_date_dialog);
        dialog.show();

        txtDeliveryDate = (TextView) dialog.findViewById(R.id.txtDeliveryDate);
        txtDeliveryTime = (TextView) dialog.findViewById(R.id.txtDeliveryTime);
        TextView selectdataandtimeinput = (TextView) dialog.findViewById(R.id.selectdataandtimeinput);

        final TextView txtContinue = (TextView) dialog.findViewById(R.id.txtContinue);
        layoutCalendar = (RelativeLayout) dialog.findViewById(R.id.layoutCalendar);
        final RelativeLayout layoutDate = (RelativeLayout) dialog.findViewById(R.id.layoutDate);
        TextView txtGoBack = (TextView) dialog.findViewById(R.id.txtGoBack);
        TextView txtDeliveryAddress = (TextView) dialog.findViewById(R.id.txtDeliveryAddress);
        layoutTime = (RelativeLayout) dialog.findViewById(R.id.layoutTime);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);

        layoutCustomTime = (RelativeLayout) dialog.findViewById(R.id.layoutCustomTime);
        timeRecyclerView = (RecyclerView) dialog.findViewById(R.id.timeRecyclerView);
        TextView txtDeliveryAddressLabel = (TextView) dialog.findViewById(R.id.txtDeliveryAddressLabel);

        previousButton = (TextView) dialog.findViewById(R.id.PreviousMonth);
        nextButton = (TextView) dialog.findViewById(R.id.nextMonth);
        currentDateText = (TextView) dialog.findViewById(R.id.currentMonth);  /*display_current_date*/
        calendarGridView = (GridView) dialog.findViewById(R.id.calendar_grid);
        LinearLayout layoutDeliveryAddress = (LinearLayout) dialog.findViewById(R.id.layoutDeliveryAddress);
        ImageView image_log = (ImageView) dialog.findViewById(R.id.image_log);

//        todayString = dateformat.format(Calendar.getInstance().getTime());
        todayString = dateformat.format(getMinDateCal());
        if (GlobalValues.DELIVERY_DATE != null) {
            txtDeliveryDate.setText(GlobalValues.DELIVERY_DATE);
        } else {

            txtDeliveryDate.setText(todayString);
        }
        if (time_slot_type != 2)
//        mselectedDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
            mselectedDay = toCalendar(getMinDateCal()).get(Calendar.DAY_OF_WEEK);
        isDateDisplay = true;
        setTime();


  /*      try {
            JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
            txtDeliveryAddress.setText(outletZoneJson.getString("zone_address_line1") + ","
                    + outletZoneJson.getString("zone_postal_code"));

        } catch (JSONException e) {
            e.printStackTrace();
        }
*/


        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
            layoutDeliveryAddress.setVisibility(View.VISIBLE);
            try {
                txtDeliveryAddressLabel.setText("Your Delivery Address :");
                JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");


                txtDeliveryAddress.setText(jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
                        jsonPostalCodeInfo.optString("zip_buname") + "," +
                        jsonPostalCodeInfo.optString("zip_code")
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }
        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
            layoutDeliveryAddress.setVisibility(View.VISIBLE);
            try {
                txtDeliveryAddressLabel.setText("Your Delivery Address :");
                JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");


                txtDeliveryAddress.setText(jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
                        jsonPostalCodeInfo.optString("zip_buname") + "," +
                        jsonPostalCodeInfo.optString("zip_code")
                );

            } catch (JSONException e) {
                e.printStackTrace();
            }


        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

            try {
                //  JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

            } catch (Exception e) {
                e.printStackTrace();
            }


            image_log.setImageResource(R.drawable.thumbs_aag);

            txtDeliveryAddressLabel.setText("Your Pickup location is:");

            selectdataandtimeinput.setText("Select Your Pickup Date & Time");

            txtDeliveryAddress.setText(Utility.readFromSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS) +
                    "\n" + "Singapore, " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_PINCODE));
        }


        layoutDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                is_time_shown = false;
                layoutCustomTime.setVisibility(View.GONE);

                if (is_date_shown) {
                    is_date_shown = false;
                    layoutCalendar.setVisibility(View.GONE);
                } else {
                    is_date_shown = true;
                    layoutCalendar.setVisibility(View.VISIBLE);
//                    layoutCustomTime.setVisibility(View.GONE);
                }

               /* if (!isDateDisplay) {
                    isDateDisplay = true;
                    txtContinue.setEnabled(false);

                } else {
                    isDateDisplay = false;
                    txtContinue.setEnabled(true);
                    layoutCalendar.setVisibility(View.GONE);
                }*/
            }
        });

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //openWarningDialog(1);

                if (isDateDisplay) {

                    if (isSelectTime) {

                        GlobalValues.DELIVERY_DATE = txtDeliveryDate.getText().toString();
                        GlobalValues.DELIVERY_TIME = txtDeliveryTime.getText().toString();

                        if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

                            setDate(GlobalValues.DELIVERY_DATE);
                            setTime(GlobalValues.DELIVERY_TIME);

                            Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE, GlobalValues.DELIVERY_DATE);
                            Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME, GlobalValues.DELIVERY_TIME);


                            dialog.dismiss();

                        } else {
                            Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        Toast.makeText(mContext, "Please select Time", Toast.LENGTH_SHORT).show();
                    }

                } else {

                    Toast.makeText(mContext, "Please select Date", Toast.LENGTH_SHORT).show();
                }

            }
        });

        txtGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

              /*  if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                    openDeliveryDialog(mContext, moutletPosition);
                } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                    openOutletSelectionDialog(mContext);
                }*/
            }
        });

        layoutTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                is_date_shown = false;
                layoutCalendar.setVisibility(View.GONE);

                if (isDateDisplay) {

                    try {
                        if (is_time_shown) {
                            is_time_shown = false;
                            layoutCustomTime.setVisibility(View.GONE);
                        } else {
                            setTime();
                            is_time_shown = true;
                            layoutCustomTime.setVisibility(View.VISIBLE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(mContext, "Please select Date", Toast.LENGTH_SHORT).show();
                }


            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        initializeUILayout();
        setUpCalendarAdapter();
        setPreviousButtonClickEvent();
        setNextButtonClickEvent();
        setGridCellClickEvents(mContext);

        setMargin();

    }


    private void setMargin() {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 250);
        int density = getResources().getDisplayMetrics().densityDpi;

        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, ViewGroup.LayoutParams.WRAP_CONTENT);
        if (density >= 320 && density < 480) {
            layoutParams.height = 500;
            layoutParams.setMargins(2, 800, 2, 2);

            params.setMargins(2, 800, 2, 2);
            params.weight = 0.5f;


        } else {
            layoutParams.height = 800;
            layoutParams.setMargins(2, 900, 2, 2);

            params.setMargins(2, 900, 2, 2);
            params.weight = 0.5f;

        }
        layoutCalendar.setLayoutParams(layoutParams);
        layoutCustomTime.setLayoutParams(params);

    }

/*    private void setTime() {

        timeList = new ArrayList<>();
        slotTimeArrayList = new ArrayList<>();



//        mTatTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.TAT_TIME));


        *//*if (outletList.size() > 0) {
            if (!outletList.get(0).getOutlet_delivery_tat().isEmpty()) {
                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_tat());
            } else if (!outletList.get(0).getOutlet_delivery_timing().isEmpty()) {
                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_timing());
            }
        }*//*

        try {
            currentDate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
            selecteddate = dateformat.parse(txtDeliveryDate.getText().toString());
        } catch (Exception e) {
            e.printStackTrace();
        }

        slotTimeArrayList = getSelectedDayTimeSlot(mselectedDay);
        try {
            for (int i = 0; i < slotTimeArrayList.size(); i++) {
                int count = 0;
                is_break = false;
                String start_time = slotTimeArrayList.get(i).getmStartTime();
                String end_time = slotTimeArrayList.get(i).getmEndTime();

                Date dstartTime = timeformat.parse(start_time);

                Date dendTime = null;
                if (mTatTime != 0) {
                    dendTime = setTatEndTime(timeformat.parse(end_time));
                } else {

                    dendTime = timeformat.parse(end_time);
                }

                loop:while (dstartTime.before(dendTime) || dstartTime.equals(dendTime)) {

                    if (selecteddate.equals(currentDate)) {

                        if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dstartTime)) {
                            if (count == 0 && mTatTime != 0) {
                                start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                                count++;
                            } else {
                                timeList.add(start_time);
                            }
                        }
                    } else {
                        if (count == 0 && mTatTime != 0) {
                            start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                            count++;
                        } else {
                            timeList.add(start_time);
                        }
                    }

                    if (is_break) {
                        if (timeList.size() == 1) {
                            timeList.remove(0);
                            timeList.add(start_time);
                        }
                        break;
                    }

                    dstartTime = timeformat.parse(start_time);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dstartTime);
                    cal.add(Calendar.MINUTE, intervaltime);
                    start_time = timeformat.format(cal.getTime());
                    dstartTime = timeformat.parse(start_time);



                    Date stst_static=timeformat.parse("00:00");
                    if (dstartTime.equals(stst_static))
                    {
//                            timeList.add(start_time);

                        break loop;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (timeList.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            timeRecyclerView.setLayoutManager(layoutManager);
            TimeAdapter timeAdapter = new TimeAdapter(mContext, timeList);
            timeRecyclerView.setAdapter(timeAdapter);


            if (timeList.size() > 0) {

                txtDeliveryTime.setText(timeList.get(0));
                isSelectTime = true;
            }

            timeAdapter.SetOnItemClickListioner(new TimeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    try {
                        Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
                        Date dCutOffTime;

                        if (selecteddate.equals(currentDate)) {

                            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF).length() > 0) {
                                cutOffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));


                            } else {
                                cutOffTime = 0;
                            }

                            if (cutOffTime > 0) {

                                if (!String.valueOf(cutOffTime).contains(":")) {
                                    dCutOffTime = timeformat.parse(cutOffTime + ":00");

                                } else {
                                    dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                                }

                                if (dCutOffTime.before(dCurrentTime)) {

                                    Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                                } else {
                                    txtDeliveryTime.setText(timeList.get(position));
                                    layoutCustomTime.setVisibility(View.GONE);
                                    is_time_shown = false;
                                    isSelectTime = true;
                                }
                            } else {
                                txtDeliveryTime.setText(timeList.get(position));
                                layoutCustomTime.setVisibility(View.GONE);
                                is_time_shown = false;
                                isSelectTime = true;
                            }
                        } else {
                            txtDeliveryTime.setText(timeList.get(position));
                            layoutCustomTime.setVisibility(View.GONE);
                            isSelectTime = true;
                            is_time_shown = false;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });

        } else {
        *//*    GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setGregorianChange(Calendar.getInstance().getTime());
            Date date1 = gregorianCalendar.getGregorianChange();
            GregorianCalendar calendar1 = new GregorianCalendar();
            calendar1.set(Calendar.DATE, date1.getDate());

            calendar1.add(Calendar.DATE, 1);


            Date date = calendar1.getTime();
            todayString = dateformat.format(date);


            GlobalValues.DELIVERY_DATE = todayString;
            txtDeliveryDate.setText(todayString);


            mselectedDay =5;


            isDateDisplay = true;
            //TODO

            //setTime1();
            setTime();


            if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

                Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE,GlobalValues.DELIVERY_DATE);
                Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME,GlobalValues.DELIVERY_TIME);
                setDate(GlobalValues.DELIVERY_DATE);
                setTime(GlobalValues.DELIVERY_TIME);

            }*//*
            Toast.makeText(mContext, "Slot not available for this date", Toast.LENGTH_SHORT).show();
        }
    }*/

    public String setTatTime(Date dstartTime, Date dendTime, Date dendTatTime) {
        Calendar cale = Calendar.getInstance();
        cale.setTime(dstartTime);
        cale.add(Calendar.MINUTE, mTatTime);
        timeList.add(timeformat.format(cale.getTime()));


        try {

            if (dendTatTime.after(timeformat.parse(timeformat.format(cale.getTime())))
                    || dendTime.equals(timeformat.parse(timeformat.format(cale.getTime())))) {
                is_break = false;
                return timeformat.format(cale.getTime());
            } else {
                is_break = true;
                return timeformat.format(dendTime);

            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return timeformat.format(cale.getTime());
    }

    public Date setTatEndTime(Date endate) {
       /* try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endate);
            cal.add(Calendar.MINUTE, mTatTime);

            return timeformat.parse(Utility.checkBefore11(timeformat.format(cal.getTime())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return endate;*/

        try {
            Calendar cal = Calendar.getInstance();
            cal.setTime(endate);
            cal.add(Calendar.MINUTE, mTatTime);


            Date stsTime = timeformat.parse("12:00");



//todo
            SimpleDateFormat sdf3 = new SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH);

            Date d1 = null;
            try {
                d1 = sdf3.parse(String.valueOf(cal.getTime()));

            } catch (Exception e) {
                e.printStackTrace();
            }


            try {

                if ((twelvetimeformat.parse(String.valueOf(d1))).after(stsTime)) {

                    return timeformat.parse(GlobalValues.SUBSTRATED_TIME);
                } else {

                    return timeformat.parse(String.valueOf(cal.getTime()));
                }
            } catch (Exception e) {

            }

//            return timeformat.parse(Utility.checkBefore11(timeformat.format(cal.getTime())));

        } catch (Exception e) {
            e.printStackTrace();
        }
        return endate;
    }


    private ArrayList<SlotTime> getSelectedDayTimeSlot(int mselectedDay) {

        ArrayList<SlotTime> slotTimes = new ArrayList<>();

        switch (mselectedDay) {
            case 1:
                slotTimes = sundayArrayList;
                break;
            case 2:
                slotTimes = mondayArrayList;
                break;
            case 3:
                slotTimes = tuesdayArrayList;
                break;
            case 4:
                slotTimes = wednesdayArrayList;
                break;
            case 5:
                slotTimes = thursdayArrayList;
                break;
            case 6:
                slotTimes = fridayArrayList;
                break;
            case 7:
                slotTimes = satdayArrayList;
                break;

        }
        return slotTimes;

    }

    private class CheckDayAvailability extends AsyncTask<String, Void, String> {

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

                if (jsonObject.getString("status").equalsIgnoreCase("success") || jsonObject.getString("status").equalsIgnoreCase("ok")) {

//                    JSONObject jsonResultSetInitial = jsonObject.getJSONObject("result_set");
                    JSONObject jsonTimeslotData = jsonObject.getJSONObject("timeslot_data");
                    JSONArray jsonResult = jsonTimeslotData.getJSONArray("result_set");

//                    JSONArray jsonResult = jsonObject.getJSONArray("result_set");

                    if (jsonResult.getJSONObject(0) != null) {
                        JSONObject jsonResultset = jsonResult.getJSONObject(0);

                        if (jsonResultset != null) {

                            if (jsonResultset.getString("interval_time").length() > 0) {
//get json interval data
                                intervaltime = Integer.parseInt(jsonResultset.getString("interval_time"));
//                                intervaltime = 15;
                                GlobalValues.INERVAL_TIME = Integer.parseInt(jsonResultset.getString("interval_time"));
                            } else {

                            }

                            if (jsonResultset.getString("time_slot_type").length() > 0) {
//get json interval data
                                try {
                                    time_slot_type = Integer.parseInt(jsonResultset.getString("time_slot_type"));
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
//                                intervaltime = 15;
                                GlobalValues.TIME_SLOT_TYPE = time_slot_type;
                            }
                            if (jsonResultset.getString("cut_off").length() > 0) {

                                int cutoff = Integer.parseInt(jsonResultset.getString("cut_off"));
                                GlobalValues.CUT_OFF = jsonResultset.getString("cut_off");
                                //cutOffTime = Integer.parseInt(jsonResultset.getString("cut_off"));
                                //Utility.writeToSharedPreference(mContext, GlobalValues.CUT_OFF, String.valueOf(cutOffTime));
                            } else {

                                GlobalValues.CUT_OFF = "0";
                            }

                            mMinDate = Integer.parseInt(jsonResultset.getString("minimum_date"));
                            mMaxDate = Integer.parseInt(jsonResultset.getString("maximum_date"));

                            JSONObject jsonSlot = jsonResultset.getJSONObject("slot");


                            Iterator<String> iterator = jsonSlot.keys();

                            slotDaysList = new ArrayList<>();

                            while (iterator.hasNext()) {

                                String key = iterator.next();


                                slotDaysList.add(key);

                                if (jsonSlot.getJSONArray(key) instanceof JSONArray) {

                                    if (key.equalsIgnoreCase("sun")) {

                                        JSONArray sunArray = jsonSlot.getJSONArray("sun");

                                        if (sunArray.length() > 0) {

                                            sundayArrayList = new ArrayList<>();

                                            for (int m = 0; m < sunArray.length(); m++) {

                                                JSONObject sunJson = sunArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(sunJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(sunJson.getString("slot_time2")));

                                                sundayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("mon")) {

                                        JSONArray monArray = jsonSlot.getJSONArray("mon");

                                        if (monArray.length() > 0) {

                                            mondayArrayList = new ArrayList<>();

                                            for (int m = 0; m < monArray.length(); m++) {

                                                JSONObject monJson = monArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(monJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(monJson.getString("slot_time2")));

                                                mondayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("tue")) {

                                        JSONArray tueArray = jsonSlot.getJSONArray("tue");

                                        if (tueArray.length() > 0) {

                                            tuesdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < tueArray.length(); m++) {
                                                JSONObject tueJson = tueArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(tueJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(tueJson.getString("slot_time2")));

                                                tuesdayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("wed")) {

                                        JSONArray wedArray = jsonSlot.getJSONArray("wed");

                                        if (wedArray.length() > 0) {
                                            wednesdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < wedArray.length(); m++) {

                                                JSONObject wedJson = wedArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(wedJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(wedJson.getString("slot_time2")));

                                                wednesdayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("thu")) {

                                        JSONArray thuArray = jsonSlot.getJSONArray("thu");

                                        if (thuArray.length() > 0) {
                                            thursdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < thuArray.length(); m++) {
                                                JSONObject thuJson = thuArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(thuJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(thuJson.getString("slot_time2")));

                                                thursdayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("fri")) {

                                        JSONArray friArray = jsonSlot.getJSONArray("fri");

                                        if (friArray.length() > 0) {
                                            fridayArrayList = new ArrayList<>();

                                            for (int m = 0; m < friArray.length(); m++) {
                                                JSONObject friJson = friArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(friJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(friJson.getString("slot_time2")));

                                                fridayArrayList.add(slotTime);
                                            }
                                        }
                                    }

                                    if (key.equalsIgnoreCase("sat")) {

                                        JSONArray satArray = jsonSlot.getJSONArray("sat");

                                        if (satArray.length() > 0) {

                                            satdayArrayList = new ArrayList<>();

                                            for (int m = 0; m < satArray.length(); m++) {
                                                JSONObject satJson = satArray.getJSONObject(m);

                                                SlotTime slotTime = new SlotTime();
                                                slotTime.setmStartTime(satJson.getString("slot_time1"));
                                                slotTime.setmEndTime(Utility.checkBefore11(satJson.getString("slot_time2")));

                                                satdayArrayList.add(slotTime);
                                            }
                                        }
                                    }


                                }
                            }

                            setMinDateCalInitialCustom();
                        }
                    }


                    JSONArray holidayArray = jsonObject.optJSONArray("holidayresult");
                    holidaysList = new ArrayList<>();
                    if (holidayArray != null && holidayArray.length() > 0) {

                        for (int h = 0; h < holidayArray.length(); h++) {
                            JSONObject holidayJson = holidayArray.getJSONObject(h);

                            Holidays holidays = new Holidays();

                            holidays.setmId(holidayJson.getString("holiday_id"));
                            holidays.setmTitle(holidayJson.getString("holiday_title"));
                            holidays.setmDescription(holidayJson.getString("holiday_description"));
                            holidays.setmDate(holidayJson.getString("holiday_date"));

                            holidaysList.add(holidays);
                        }

                    } else {
                        holidaysList = new ArrayList<>();
                    }


                } else {

                    if (!oncreateTimeOnly) {
                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
//                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }
                if (oncreateTimeOnly) {
                    oncreateTimeOnly = false;


                    todayString = dateformat.format(getMinDateCal());   //                    todayString = dateformat.format(Calendar.getInstance().getTime());



                    GlobalValues.DELIVERY_DATE = todayString;

//                    mselectedDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
                    mselectedDay = toCalendar(getMinDateCal()).get(Calendar.DAY_OF_WEEK);
                    isDateDisplay = true;

                    try {
//                        currentDate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
                        currentDate = dateformat.parse(dateformat.format(getMinDateCal()));
                        //TODO CHANGES
//                        selecteddate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
                        selecteddate = dateformat.parse(dateformat.format(getMinDateCal()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    //TODO

                    setTime100(true);
                    //setTime();


                    if (time_slot_type == 2 || checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {


                        Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE, GlobalValues.DELIVERY_DATE);
                        Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME, GlobalValues.DELIVERY_TIME);
                        setDate(GlobalValues.DELIVERY_DATE);
                        setTime(GlobalValues.DELIVERY_TIME);

                    }

                    initialTimeSlotSetup();
                } else {
                    openDeliveryDateDialog(mContext);
                }


            } catch (Exception e) {
                mMinDate = 0;
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }


    private class UpdateCartItemTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> updateParams;
        private int mPosition;

        public UpdateCartItemTask(Map<String, String> updateParams, int position) {
            this.updateParams = updateParams;
            mPosition = position;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Updating...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], updateParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                    // JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                    JSONObject jsonResult = jsonObject.getJSONObject("contents");

                    JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");

                /*    if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                        removePromotion();
                    }


                    if (txtRedeemApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                        removeRewardPoints();
                    }
*/
                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, jsoncartDetails.getString("cart_total_items"));

                    SubCategoryActivity.cart_sub_total = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                    txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));
                    txtSubTotalSymbol.setVisibility(View.VISIBLE);
                    r_sub_total = jsoncartDetails.getString("cart_sub_total");
                    //   double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(r_sub_total);

/*                    if (d_progress_limit > 0) {
                        txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
                    } else {
                        txtFreeDelivery.setText("FREE delivery!");
                    }*/


                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                            CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
                        /*setCustomProgress();

                        if (d_progress_limit > 0) {

                            GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                            //   txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                            txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                    Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                            txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                mGST = (mGrandTotal * 7) / 100;
                                GlobalValues.GST = mGST;
                                mGrandTotal += mGST;
                            } else {

                                int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                mGST = (mGrandTotal * gst_values) / 100;
                                GlobalValues.GST = mGST;
                                mGrandTotal += mGST;


                            }


                        } else {

                            GlobalValues.DELEIVERY_CHARGES = "0.00";
                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
                            // txtDeliveryCharge.setText("$0.00");
                            txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                    Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                            txtFreeDelivery.setText("FREE delivery!");


                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));

                            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));


                                mGST = (mGrandTotal * 7) / 100;
                                GlobalValues.GST = mGST;
                                mGrandTotal += mGST;

                                //  txtGSTLabel.setText("GST ("+"7"+"%)" );
                                //  txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

                            } else {

                                int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                mGST = (mGrandTotal * gst_values) / 100;
                                GlobalValues.GST = mGST;
                                mGrandTotal += mGST;


                            }


                        }*/
                    }
                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                        GlobalValues.DELEIVERY_CHARGES = "0.00";
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));

                    }

/*
                    if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
//                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                        if (Double.parseDouble(outletZoneJson.optString("zone_free_delivery")) > 0) {
                            progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
                            progressBar.setSecondaryProgress((int) Double.parseDouble(
                                    outletZoneJson.optString("zone_free_delivery")));
                        } else {

                            progressBar.setProgress(1000);
                            progressBar.setSecondaryProgress(1000);
                        }

                    } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                    }*/

                    // txtGST.setText("$" + String.format("%.2f", mGST));


                    JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                    Utility.writeToSharedPreference(mContext,
                            GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());



                    setCartAdapter(jsonCartItem);

                    if (foodVoucher) {
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                    }


                    if (!(jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0")
                            || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("null") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0.00"))) {
                        layoutVoucher.setVisibility(View.VISIBLE);
                        voucherPrice.setText(jsoncartDetails.getString("cart_voucher_discount_amount") + ")");
                        cartVoucherDiscountAmt = jsoncartDetails.getString("cart_voucher_discount_amount");
                        setVoucherTotal(Double.parseDouble(cartVoucherDiscountAmt));
                    } else {
                        layoutVoucher.setVisibility(View.GONE);
                        InclusiveGst(mGrandTotal);
                        txtTotal.setText(String.format("%.2f", mGrandTotal));
                        txtTotalSymbol.setVisibility(View.VISIBLE);
                    }

                    //txtTotal.setText(String.format("%.2f", gstAmount + mGrandTotal));
                    //txtTotal.setText(String.format("%.2f", mGrandTotal));
                    //InclusiveGst(mGrandTotal);
                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }


    }

    public void openDeliveryDialog(final Context mContext) {

        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_delivery_pincode_dialog);
        dialog.show();

        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
        TextView txtGoBack = (TextView) dialog.findViewById(R.id.txtGoBack);
        final EditText edtPostalCode = (EditText) dialog.findViewById(R.id.edtPostalCode);
        TextView txtContinue = (TextView) dialog.findViewById(R.id.txtContinue);

        RecyclerView address_list = (RecyclerView) dialog.findViewById(R.id.address_list);
        final ImageView imageChecked = (ImageView) dialog.findViewById(R.id.imageChecked);
        TextView textview_model = (TextView) dialog.findViewById(R.id.textview_model);
        LinearLayout hit_textview = (LinearLayout) dialog.findViewById(R.id.hit_textview);


        txtGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();

            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        try {

            if (secondaryAddressArrayList.size() > 0) {
                address_list.setVisibility(View.VISIBLE);
                textview_model.setVisibility(View.VISIBLE);
                imageChecked.setImageResource(R.drawable.asset54);
                layoutManager = new LinearLayoutManager(mContext);
                singleSelectAdapter = new SingleSelectAdapter(mContext, secondaryAddressArrayList);
                address_list.setLayoutManager(layoutManager);
                address_list.setAdapter(singleSelectAdapter);

                singleSelectAdapter.setOnItemClick(new IOnItemClick() {
                    @Override
                    public void onItemClick(View v, int position) {

                        pos = position;
                        singleSelectAdapter.updateCells(secondaryAddressArrayList);
                        edtPostalCode.setText(secondaryAddressArrayList.get(position).getPostal_code());
                        imageChecked.setImageResource(R.drawable.asset54);


                    }
                });
            } else {
                imageChecked.setImageResource(R.drawable.asset53);
                address_list.setVisibility(View.GONE);
                textview_model.setVisibility(View.GONE);

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        imageChecked.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (iEnableSecondarylist) {
                    imageChecked.setImageResource(R.drawable.asset53);
                    pos = -1;
                    singleSelectAdapter.updateCells(secondaryAddressArrayList);
                    edtPostalCode.setText("");
                } else {
                    imageChecked.setImageResource(R.drawable.asset53);
                    pos = -1;
                    singleSelectAdapter.updateCells(secondaryAddressArrayList);

                    edtPostalCode.setText("");

                }


            }
        });


        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edtPostalCode.getText().toString().length() <= 0) {

                    Toast.makeText(mContext, "Please Enter PostalCode", Toast.LENGTH_SHORT).show();

                } else {

                    if (Utility.networkCheck(mContext)) {


                        String url = GlobalUrl.FINDZONE_URL +
                                "?app_id=" + GlobalValues.APP_ID +
                                "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                "&postal_code=" + edtPostalCode.getText().toString() +
                                "&outlet_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

                        new FindZoneTask(dialog).execute(url);

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }

                }

            }
        });

    }

    private class FindZoneTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Dialog dialog;

        public FindZoneTask(Dialog dialog) {

            this.dialog = dialog;
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


                    Utility.writeToSharedPreference(mContext, GlobalValues.OUTLETZONE, jsonObject.getJSONObject("result_set").toString());
                    outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                    txtAddress.setText(outletZoneJson.optString("zone_address_line1") + "\nv Singapore " +
                            outletZoneJson.optString("zone_postal_code"));

                /*    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.DAYAVAILABLE_URL + "?app_id=" + GlobalValues.APP_ID + "&availability=" +
                                GlobalValues.CURRENT_AVAILABLITY_ID + "&outlet_id="
                                + outletList.get(moutletPosition).getmOutletId();

                        new CheckDayAvailability().execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
*/

                } else {

                    openWarningDialog(0);

                }

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {

                dialog.dismiss();
                progressDialog.dismiss();

            }
        }
    }

    private void openWarningDialog(final int from) {

        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_warning_dialog);
        dialog.show();

        TextView txtChangeAddress = (TextView) dialog.findViewById(R.id.txtChangeAddress);
        TextView txtWarning = (TextView) dialog.findViewById(R.id.txtWarning);
        final TextView txtComebackAgain = (TextView) dialog.findViewById(R.id.txtComebackAgain);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);

        if (from == 0) {
            txtComebackAgain.setVisibility(View.GONE);
        } else {
            txtComebackAgain.setVisibility(View.VISIBLE);
        }


        txtChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (from == 0) {

                    dialog.dismiss();
                    openDeliveryDialog(mContext);

                } else {
                    dialog.dismiss();
                }

            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    //Custom Calendar
    private void initializeUILayout() {

        min = Calendar.getInstance();

        if (mMinDate > 0) {
            min.add(Calendar.DATE, mMinDate);
        }
        currentday = min.getTime();

        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DATE, mMaxDate);
        maxDate = calendar.getTime();

    }

    private void setPreviousButtonClickEvent() {
        previousButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MainActivity.selected_date = null;

                mAdapter.updateView(-1);
                cal.add(Calendar.MONTH, -1);
                setUpCalendarAdapter();
            }
        });
    }

    private void setNextButtonClickEvent() {
        nextButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MainActivity.selected_date = null;

                mAdapter.updateView(-1);
                cal.add(Calendar.MONTH, 1);
                setUpCalendarAdapter();
            }
        });
    }

    public void setGridCellClickEvents(final Context context) {

        calendarGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               /* MainActivity.selected_date = dayValueInCells.get(position).toString();
                */
                mAdapter.updateView(position);

               /* Utility.selected_date=todayString;
                */

//                context.startActivity(new Intent(context, SearchListActivity.class));
                currentday = min.getTime();
                validateDate(currentday, dayValueInCells.get(position), maxDate);


            }
        });

    }

    public void validateDate(Date currentday, Date selectedDate, Date maxDate) {
        if (holidaysList != null && holidaysList.size() > 0) {

            for (int k = 0; k < holidaysList.size(); k++) {


                if (holidaysList.get(k).getmDate() != null) {

                    String d1 = java.text.DateFormat.getDateInstance(MEDIUM).format(selectedDate);

                    String d2 = java.text.DateFormat.getDateInstance(MEDIUM).
                            format(new Date(holidaysList.get(k).getmDate()));


                    if (d1.equals(d2)) {

                        isHoliday = false;
                        break;

                    } else {
                        isHoliday = true;
                    }
                }
            }
        } else {
            isHoliday = true;
        }

        if (slotDaysList != null && slotDaysList.size() > 0) {

            int isPresent = 0;

            for (int s = 0; s < slotDaysList.size(); s++) {

                Calendar daycalendar = Calendar.getInstance();
                daycalendar.setTime(selectedDate);
                mselectedDay = daycalendar.get(Calendar.DAY_OF_WEEK);

                String day = dayofWeek(mselectedDay);


                if (day.contains(slotDaysList.get(s))) {

                    isPresent++;
                }
            }

            if (isPresent == 0) {
                isSlotDay = false;

            } else {

                isSlotDay = true;
            }
        }
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");



        try {
            selectedDate = formatter.parse(formatter.format(selectedDate));
            currentday = formatter.parse(formatter.format(currentday));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        if (selectedDate.after(currentday) || currentday.toString().equals(selectedDate.toString())) {


            if (selectedDate.before(maxDate)) {
                if (isHoliday) {

                    if (isSlotDay) {

                        layoutTime.setEnabled(true);

                        Date date = new Date(selectedDate.toString());
                        todayString = date.toString();

                        isDateDisplay = true;
                        txtDeliveryTime.setText("Select Time");
                        // HomeActivity.layoutCalendar.setVisibility(View.GONE);

                        if (selectedDate.after(currentday)) {
                            GlobalValues.IS_ADVANCE_ORDER = "yes";
                        } else {
                            GlobalValues.IS_ADVANCE_ORDER = "no";
                        }


                        /*Deepika logic*/

                        try {
                            SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
                            Date currenttime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
                             Date cutofftime;

                            Date currentdate = formatter.parse(formatter.format(Calendar.getInstance().getTime()));
                            Date selecteddate = formatter.parse(formatter.format(date));

                            if (selecteddate.equals(currentdate)) {

                                if (!String.valueOf(cutOffTime).contains(":")) {
                                    cutofftime = timeformat.parse(cutOffTime + ":00");

                                } else {
                                    cutofftime = timeformat.parse(String.valueOf(cutOffTime));
                                }

                                Log.e("cut-off", String.valueOf(cutOffTime));
                                if (cutofftime.before(currenttime)) {
                                    //TODO EDITTED by Udaya
                                    //  Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();

                                    layoutTime.setEnabled(true);
                                    txtDeliveryDate.setText(formatter.format(date));
                                    layoutCalendar.setVisibility(View.GONE);
                                    setTime();
                                    //Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                                } else {
                                    //Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                                    layoutTime.setEnabled(true);
                                    txtDeliveryDate.setText(formatter.format(date));
                                    layoutCalendar.setVisibility(View.GONE);
                                    setTime();
                                }
                            } else {
                                layoutTime.setEnabled(true);
                                txtDeliveryDate.setText(formatter.format(date));
                                layoutCalendar.setVisibility(View.GONE);
                                setTime();
                            }

                        } catch (ParseException e) {
                            e.printStackTrace();
                        }

                        /*end*/


                    } else {
                        layoutTime.setEnabled(false);
                        Toast.makeText(mContext, "Date is not in Slot", Toast.LENGTH_SHORT).show();

                    }

                } else {
                    layoutTime.setEnabled(false);
                    Toast.makeText(mContext, "Holiday", Toast.LENGTH_SHORT).show();
                }

            } else {
                layoutTime.setEnabled(false);
                Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
            }


        } else {
            Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
        }


    }

    private String dayofWeek(int d) {

        String day = "";

        if (d == 1) {
            day = "sun";
        } else if (d == 2) {
            day = "mon";
        } else if (d == 3) {
            day = "tue";
        } else if (d == 4) {
            day = "wed";
        } else if (d == 5) {
            day = "thu";
        } else if (d == 6) {
            day = "fri";
        } else if (d == 7) {
            day = "sat";
        }

        return day;
    }

    private void setUpCalendarAdapter() {
        dayValueInCells = new ArrayList<Date>();
        Calendar mCal = (Calendar) cal.clone();
        mCal.set(Calendar.DAY_OF_MONTH, 1);
        int firstDayOfTheMonth = mCal.get(Calendar.DAY_OF_WEEK) - 1;
        mCal.add(Calendar.DAY_OF_MONTH, -firstDayOfTheMonth);

        while (dayValueInCells.size() < MAX_CALENDAR_COLUMN) {
            dayValueInCells.add(mCal.getTime());
            mCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        SimpleDateFormat sdformat = new SimpleDateFormat("MMMM");
        SimpleDateFormat sdf = new SimpleDateFormat("M");
        String month = sdf.format(cal.getTime());


        try {

            if (Integer.parseInt(month) < 12) {
                nextButton.setText(new DateFormatSymbols().getMonths()[Integer.parseInt(month)]);
            }

            if (Integer.parseInt(month) == 12) {
                nextButton.setText(new DateFormatSymbols().getMonths()[0]);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        try {


            if (Integer.parseInt(month) - 2 >= 0) {
                previousButton.setText(new DateFormatSymbols().getMonths()[Integer.parseInt(month) - 2]);
            }

            if (Integer.parseInt(month) - 2 == -1) {
                previousButton.setText(new DateFormatSymbols().getMonths()[11]);
            }


        } catch (Exception e) {
            e.printStackTrace();
        }

        currentDateText.setText(sdformat.format(cal.getTime()));



        mAdapter = new GridAdapter(mContext, dayValueInCells, cal, currentday);
        calendarGridView.setAdapter(mAdapter);
    }

    public static String getDate() {

        return todayString;
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

                if (fromChangeAddress == 1) {

                    Intent intent = new Intent(mContext, HomeActivity.class);
                    intent.putExtra("isChangeAddress", true);
                    startActivity(intent);

                } else if (!OutletPage) {

                    intent = new Intent(mContext, SubCategoryActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    intent.putExtra("local_order_no", mOrderNo);
                    startActivity(intent);
                    finish();

                }
            }
        }
    }

    private class AddSecondaryAddressTask extends AsyncTask<String, Void, String> {

        private Map<String, String> secondaryAddressParams;


        public AddSecondaryAddressTask(Map<String, String> params) {

            secondaryAddressParams = params;

        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(mContext, params[0], secondaryAddressParams);


            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    mSecondaryAddressId = jsonObject.getString("insert_id");
                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class CouponCodeTask extends AsyncTask<String, String, String> {

        String response, status, message, type;

        ProgressDialog progressDialog;

        Map<String, String> couponParams;

        String couponCode = "";

        Dialog mDialog;

        CouponCodeTask(Map<String, String> params, String couponCode) {
            couponParams = params;
            this.couponCode = couponCode;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

            try {


                response = WebserviceAssessor.postRequest(mContext, strings[0], couponParams);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {
                    parseCouponPointResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    private void parseCouponPointResponse(String response) throws JSONException {
        JSONObject responseObj = new JSONObject(response);

        if (responseObj.optString("status").equalsIgnoreCase("success")) {


         /*   if (txtRedeemPoints.getText().toString().length() > 0) {
                try {


                    setOverallTotal(Double.valueOf(txtDiscountTotal.getText().toString().replace("-$", "")), "REMOVE");

                    txtDiscountTotal.setText("");
                    txtRedeemApply.setText("APPLY NOW");
                    txtRedeemPoints.setText("");
                    txtRedeemPoints.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }*/


            GlobalValues.DISCOUNT_APPLIED = true;
            GlobalValues.DISCOUNT_TYPE = "COUPON";
            // txtDiscountLabel.setText("DISCOUNT(Promotion)");
            Toast.makeText(mContext, "" + responseObj.optString("message"), Toast.LENGTH_SHORT).show();
            // layoutdiscount.setVisibility(View.VISIBLE);
            JSONObject resultObj = responseObj.getJSONObject("result_set");


            r_sub_total = txtSubTotal.getText().toString().replace("$", "");

            double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery"))
                    - Double.valueOf(r_sub_total);
//janaki
            setCustomProgress();

            try {

                if (resultObj.getString("promotion_delivery_charge_applied").equalsIgnoreCase("yes")) {

                    GlobalValues.DELEIVERY_CHARGES = "0.0";

                    GlobalValues.PRMOTION_DELIVERY_APPLIED = true;

                    //txtDiscountTotal.setText("-$0.00");
                    //txtDeliveryCharge.setText("$0.00");


                    if (d_progress_limit > 0) {

                        GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                        // txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

                        //  txtDiscountTotal.setText("-$" + outletZoneJson.getString("zone_delivery_charge"));

                  /*      txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                        txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
*/
                    } else {

                        GlobalValues.DELEIVERY_CHARGES = "0.00";
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
                        // txtDeliveryCharge.setText("$0.00");
                        // txtDiscountTotal.setText("-$0.00");
                       /* txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                        txtFreeDelivery.setText("FREE delivery!");*/

                    }

                    setOverallTotal(0.0, "APPLY");
                    mPromotionAmount = String.format("%.2f", 0.0);

                } else {

                    if (d_progress_limit > 0) {

                        GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                        // txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                     /*   txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                        txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
*/
                    } else {

                        GlobalValues.DELEIVERY_CHARGES = "0.00";
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
                        //txtDeliveryCharge.setText("$0.00");
                      /*  txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                        txtFreeDelivery.setText("FREE delivery!");*/

                    }

//                    GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");

//                    txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

                    GlobalValues.PRMOTION_DELIVERY_APPLIED = false;

                    // txtDiscountTotal.setText("-$" + String.format("%.2f", resultObj.optDouble("promotion_amount")));

                    setOverallTotal(resultObj.optDouble("promotion_amount"), "APPLY");
                    mPromotionAmount = String.format("%.2f", resultObj.optDouble("promotion_amount"));

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            mPromoCoupon = resultObj.optString("promotion_code");
            edtPromotion.setText(resultObj.optString("promotion_code"));
            edtPromotion.setEnabled(false);
            // txtPromoApply.setText("REMOVE");
        } else {
            Toast.makeText(mContext, responseObj.optString("message"), Toast.LENGTH_SHORT).show();
        }

    }

    private class GetListofaddressTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        GetListofaddressTask() {

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

                    secondaryAddressArrayList.clear();

                    JSONArray jsonArray_result = jsonObject.getJSONArray("result_set");


                    for (int i = 0; i < jsonArray_result.length(); i++) {

                        JSONObject jsonObject_result = jsonArray_result.getJSONObject(i);


                        SecondaryAddress secondaryAddress = new SecondaryAddress();


                        secondaryAddress.setCustomer_id(jsonObject_result.optString("customer_id"));
                        secondaryAddress.setFirst_name(jsonObject_result.optString("first_name"));
                        secondaryAddress.setLast_name(jsonObject_result.optString("last_name"));
                        secondaryAddress.setContact_no(jsonObject_result.optString("contact_no"));
                        secondaryAddress.setCheck_default(jsonObject_result.optString("check_default"));
                        secondaryAddress.setSecondary_address_id(jsonObject_result.optString("secondary_address_id"));
                        secondaryAddress.setContact_email(jsonObject_result.optString("contact_email"));
                        secondaryAddress.setAddress(jsonObject_result.optString("address"));
                        secondaryAddress.setPostal_code(jsonObject_result.optString("postal_code"));
                        secondaryAddress.setCountry(jsonObject_result.optString("Singapore"));

                        secondaryAddressArrayList.add(secondaryAddress);

                    }

                    iEnableSecondarylist = true;


                } else {

                    // Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    iEnableSecondarylist = false;

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
                iEnableSecondarylist = false;

            }
        }
    }


    private class CartTATtask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
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
                    if (jsonObject.getString("tattime").equalsIgnoreCase("null") || jsonObject.getString("tattime") == null || jsonObject.getString("tattime").equalsIgnoreCase("")) {
                        //mTatTime = 0;
                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {

                            mTatTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, OUTLET_DELIVERY_TAT));
                        } else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            mTatTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, OUTLET_PICKUP_TAT));
                        }
                    } else {

                        mTatTime = Integer.parseInt(jsonObject.getString("tattime"));
                    }

                    if (mTatTime == 0) {

                        mTatTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, OUTLET_DELIVERY_TIMING));
                    }
                } else {
                    mTatTime = 0;
                }

                /*else if(CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)){

                    GlobalValues.CUT_OFF = Utility.readFromSharedPreference(mContext, OUTLET_DELIVERY_TAT);
                }else if(CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)){

                    GlobalValues.CUT_OFF = Utility.readFromSharedPreference(mContext, OUTLET_PICKUP_TAT);
                }

                Log.e("GlobalValue", GlobalValues.CUT_OFF);

                if(GlobalValues.CUT_OFF.equalsIgnoreCase("0")){

                    GlobalValues.CUT_OFF = Utility.readFromSharedPreference(mContext, OUTLET_DELIVERY_TIMING);
                }

                Log.e("GlobalValue1", GlobalValues.CUT_OFF);*/

            } catch (Exception e) {
                e.printStackTrace();
                mTatTime = 0;
            } finally {

                progressDialog.dismiss();
//                String url = GlobalUrl.DAYAVAILABLE_URL + "?app_id=" + GlobalValues.APP_ID + "&availability=" +
//                        Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID) +
//                        "&outlet_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);
                String url = GlobalUrl.DAYAVAILABLE_URL2 + "?app_id=" + GlobalValues.APP_ID + "&availability_id=" +
                        Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID) +
                        "&outletId=" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID) +
                        "&tatTime=" + mTatTime;

                GlobalValues.CURRENT_TAT_TIME = String.valueOf(mTatTime);

                new CheckDayAvailability().execute(url);

            }
        }
    }


    public void CheckAddresList() {

        if (!isCustomerLoggedIn()) {

            iEnableSecondarylist = false;

        } else {

            iEnableSecondarylist = true;
            if (Utility.networkCheck(mContext)) {
                String url = GlobalUrl.GET_SECONDARY_ADDRESS_URL + "?app_id=" + GlobalValues.APP_ID + "&status=A" + "&refrence=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                new GetListofaddressTask().execute(url);

            } else {
                Toast.makeText(mContext, "Please check your interner connection.", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private boolean isCustomerLoggedIn() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            return true;
        } else {
            return false;
        }
    }


    public void InclusiveGst(Double GrandTotal) {
        Double Grandamt = 0.0;

//        Double Ca1 = 1.0 + Double.parseDouble(GlobalValues.GstChargers) / 100;
        Double Ca2 = Double.parseDouble(GlobalValues.GstChargers) / 100;
//        Double productc1 = GrandTotal / Ca1;
//        Grandamt = productc1 * Ca2;
//        mGrandTotalGst = Grandamt;

        Grandamt = (mGrandTotal * Ca2);

        if (Grandamt <= 0.00) {
            Grandamt = 0.00;
        }

        mGrandTotal += Grandamt;


        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
            txt_insulsive_gst.setVisibility(View.GONE);
            txt_insulsive_gstSymbol.setVisibility(View.GONE);
            layoutGst.setVisibility(View.GONE);

        } else {

            txt_insulsive_gst.setVisibility(View.VISIBLE);
            txt_insulsive_gstSymbol.setVisibility(View.VISIBLE);
            layoutGst.setVisibility(View.VISIBLE);
            //txt_insulsive_gst.setText("GST Inclusive (7%): $ " + String.format("%.2f", Grandamt));
            txt_insulsive_gst.setText(String.format("%.2f", Grandamt));
            gstAmount = Double.parseDouble(String.format("%.2f", Grandamt));
            txtTotal.setText(String.format("%.2f", Grandamt + GrandTotal));
            txtTotalSymbol.setVisibility(View.VISIBLE);
            GlobalValues.GST = Grandamt;
        }

    }

  /*  private class SetMenuProductDetailsTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<SetMenuTitle> setMenuTitleList;
        private List<SetMenuModifier> setMenuModifierList;
        private List<ModifierHeading> modifierHeadingList;
        private List<ModifiersValue> modifiersValueList;
        private SetMenuTitle setMenuTitle;
        private SetMenuModifier setMenuModifier;
        private ModifierHeading modifierHeading;
        private ModifiersValue modifiersValue;
        private String mProductId = "", mQuantity = cartList.get(CurrentPosition).getmProductQty();

        public SetMenuProductDetailsTask(String id) {
            mProductId = id;
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
            double price = 0.0;

            try {



                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    mBasePath = jsonCommon.optString("image_source");

                    JSONArray jsonResultArray = jsonObject.getJSONArray("result_set");

                    if (jsonResultArray.length() > 0) {
                        for (int r = 0; r < jsonResultArray.length(); r++) {

                            JSONObject jsonResult = jsonResultArray.getJSONObject(r);

                            setMenuProduct = new SetMenuProduct();

                            setMenuProduct.setmProductId(jsonResult.getString("product_id"));
                            setMenuProduct.setmProductName(jsonResult.getString("product_name"));
                            setMenuProduct.setmProductAliasName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            String subImageUrl=jsonResult.optString("product_thumbnail");
                            if(subImageUrl!=null && subImageUrl.length()>0) {
                            setMenuProduct.setmProductImage(mBasePath + "/" + jsonResult.getString("product_thumbnail"));}
                            setMenuProduct.setmProductStatus(jsonResult.getString("product_status"));
                            setMenuProduct.setmProductPrice(jsonResult.getString("product_price"));
                            setMenuProduct.setmApplyMinMaxSelect(Integer.parseInt(jsonResult.getString("product_apply_minmax_select")));

                            try {

                                mSetMenuBasePrice = Double.valueOf(jsonResult.getString("product_price"));
                            } catch (Exception e) {
                                mSetMenuBasePrice = 0.0;
                            }

                            mSetMenuPrice = mSetMenuBasePrice;

                            quantityCost = Double.valueOf(cartList.get(CurrentPosition).getmProductTotalPrice());


                            JSONArray setmenuArray = jsonResult.getJSONArray("set_menu_component");

                            setMenuTitleList = new ArrayList<>();

                            if (setmenuArray.length() > 0) {

                                for (int t = 0; t < setmenuArray.length(); t++) {
                                    JSONObject jsonSetmenu = setmenuArray.getJSONObject(t);

                                    setMenuTitle = new SetMenuTitle();

                                    setMenuTitle.setmTitleMenuId(jsonSetmenu.optString("menu_component_id"));
                                    setMenuTitle.setmTitleMenuName(jsonSetmenu.optString("menu_component_name"));
                                    setMenuTitle.setmAppliedPrice(jsonSetmenu.optString("menu_component_apply_price"));
                                    setMenuTitle.setmMinSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_min_select")));
                                    setMenuTitle.setmMaxSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_max_select")));
                                    setMenuTitle.setmDefaultSelectId(jsonSetmenu.optString("menu_component_default_select"));


                                    JSONArray jsonProductArray = jsonSetmenu.getJSONArray("product_details");

                                    setMenuModifierList = new ArrayList<>();

                                    if (jsonProductArray.length() > 0) {

                                        for (int p = 0; p < jsonProductArray.length(); p++) {

                                            JSONArray jsonArray = jsonProductArray.getJSONArray(p);

                                            if (jsonArray.length() > 0) {
                                                for (int p1 = 0; p1 < jsonArray.length(); p1++) {

                                                    JSONObject object = jsonArray.getJSONObject(p1);

                                                    setMenuModifier = new SetMenuModifier();

                                                    setMenuModifier.setmModifierId(object.optString("product_id"));
                                                    setMenuModifier.setmModifierName(object.optString("product_name"));
                                                    setMenuModifier.setmModifierAliasName(object.optString("product_alias"));
                                                    setMenuModifier.setmMaxSelect(object.optString("product_bagel_max_select"));
                                                    setMenuModifier.setmMinSelect(object.optString("product_bagel_min_select"));
                                                    setMenuModifier.setmModifierPrice(object.optString("product_price"));
                                                    setMenuModifier.setmModifierSku(object.optString("product_sku"));
                                                    setMenuModifier.setmModifierSlug(object.optString("product_slug"));

                                                    String ProductID = object.optString("product_id");
                                                    String ProductName = object.optString("product_alias");


                                                    //Check the Conditions Updates the Quaites
                                                    try {
                                                        if (cartList.get(CurrentPosition).getSetMenuTitleList() != null && cartList.get(CurrentPosition).getSetMenuTitleList().size() > 0) {

                                                            String name = "";

                                                            List<SetMenuTitle> setMenuTitleList = cartList.get(CurrentPosition).getSetMenuTitleList();

                                                            for (int t1 = 0; t1 < setMenuTitleList.size(); t1++) {
                                                                SetMenuTitle setMenuTitle = setMenuTitleList.get(t1);

                                                                name += setMenuTitle.getmTitleMenuName() + ":";





                                                                List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                                                                if (setMenuModifierList != null && setMenuModifierList.size() > 0) {

                                                                    for (int sm = 0; sm < setMenuTitle.getSetMenuModifierList().size(); sm++) {
                                                                        SetMenuModifier setMenuModifiers = setMenuTitle.getSetMenuModifierList().get(sm);





                                                                        if (setMenuModifiers.getmMin_Max_apply().equalsIgnoreCase("1")) {
//                                                                            if (setMenuModifiers.getmQuantity() > 0)
//                                                                            {
//
//
//                                                                                if(ProductID.equals(setMenuModifiers.getmModifierId()))
//                                                                                {
//
//                                                                                    setMenuModifier.setmQuantityUpdates(setMenuModifiers.getmQuantityUpdates());
//                                                                                    setMenuModifier.setmQuantity(0);
//
//
//
//                                                                                }
//                                                                                else
//                                                                                {
//
//                                                                                    setMenuModifier.setmQuantity(0);
//                                                                                    setMenuModifier.setmQuantityUpdates(0);
//
//                                                                                }
//
//                                                                            } else {
//
//                                                                            }
                                                                        } else {

                                                                            if (ProductName.equalsIgnoreCase(setMenuModifiers.getmModifierName())) {




                                                                                setMenuModifier.setChecked(true);


                                                                            } else {


                                                                                setMenuModifier.setChecked(false);
                                                                            }

                                                                        }


                                                                        List<ModifierHeading> modifierHeadingList = setMenuModifiers.getModifierHeadingList();

                                                                        if (modifierHeadingList != null && modifierHeadingList.size() > 0) {
                                                                            for (int h = 0; h < modifierHeadingList.size(); h++) {
                                                                                ModifierHeading modifierHeading = modifierHeadingList.get(h);

                                                                                name += modifierHeading.getmModifierHeading() + ":";

                                                                                List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                                                                if (modifiersValueList != null && modifiersValueList.size() > 0) {

                                                                                    for (int v = 0; v < modifiersValueList.size(); v++) {

                                                                                        ModifiersValue modifiersValue = new ModifiersValue();

                                                                                        name += modifiersValue.getmModifierName();

                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }


                                                        }


                                                    } catch (Exception e) {



                                                        e.printStackTrace();
                                                    }


                                                    modifierHeadingList = new ArrayList<>();

                                                    Object modifierObject = object.get("modifiers");

                                                    if (modifierObject instanceof JSONArray) {



                                                        JSONArray jsonModifiersArray = object.getJSONArray("modifiers");



                                                        if (jsonModifiersArray != null && jsonModifiersArray.length() > 0) {

                                                            setMenuModifier.setHasModifier(true);

                                                            for (int m = 0; m < jsonModifiersArray.length(); m++) {

                                                                JSONObject jsonModifier = jsonModifiersArray.getJSONObject(m);

                                                                modifierHeading = new ModifierHeading();

                                                                modifierHeading.setmModifierHeading(jsonModifier.getString("pro_modifier_name"));
                                                                modifierHeading.setmModifierHeadingId(jsonModifier.getString("pro_modifier_id"));
                                                                modifierHeading.setmModifierMinSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_min_select")));
                                                                modifierHeading.setmModifierMaxSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_max_select")));
                                                                modifierHeading.setmMaxSelected(0);

                                                                JSONArray modifierValueArray = jsonModifier.getJSONArray("modifiers_values");

                                                                modifiersValueList = new ArrayList<>();

                                                                if (modifierValueArray.length() > 0) {

                                                                    for (int v = 0; v < modifierValueArray.length(); v++) {

                                                                        JSONObject jsonModifiervalue = modifierValueArray.getJSONObject(v);

                                                                        modifiersValue = new ModifiersValue();

                                                                        modifiersValue.setmModifierId(jsonModifiervalue.getString("pro_modifier_value_id"));
                                                                        modifiersValue.setmModifierValuePrice(jsonModifiervalue.getString("pro_modifier_value_price"));
                                                                        modifiersValue.setmModifierName(jsonModifiervalue.getString("pro_modifier_value_name"));
                                                                        modifiersValue.setmModifierDefault(jsonModifiervalue.getString("pro_modifier_value_is_default"));

                                                                        if (jsonModifiervalue.getString("pro_modifier_value_is_default").equalsIgnoreCase("yes")) {

                                                                            modifiersValue.setChekced(true);
                                                                            modifierHeading.setmMaxSelected(1);

                                                                            *//*adding modifier value prices with set menu base price*//*

                                                                            try {
                                                                                price = Double.valueOf(jsonModifiervalue.optString("pro_modifier_value_price"));
                                                                            } catch (NumberFormatException e) {
                                                                                price = 0.0;
                                                                            } catch (Exception e1) {
                                                                                price = 0.0;
                                                                            }

                                                                            mSetMenuPrice = quantityCost + price;
                                                                            quantityCost = mSetMenuPrice;


                                                                        } else {

                                                                            modifiersValue.setChekced(false);
                                                                            modifierHeading.setmMaxSelected(0);

                                                                        }

                                                                        modifiersValueList.add(modifiersValue);

                                                                    }

                                                                }

                                                                modifierHeading.setModifiersList(modifiersValueList);
                                                                modifierHeadingList.add(modifierHeading);


                                                            }

                                                        } else {
                                                            setMenuModifier.setHasModifier(false);
                                                        }

                                                    } else {

                                                        setMenuModifier.setHasModifier(false);
                                                    }


                                                    *//*add set menu price for default selected products if products does
                                                    not have modifiers*//*

                                                    if (setMenuTitle.getmDefaultSelectId().equals(object.optString("product_id"))) {
                                                        setMenuModifier.setChecked(true);

                                                        if (modifierHeadingList.size() <= 0) {
                                                            try {
                                                                price = Double.valueOf(object.optString("product_price"));
                                                            } catch (NumberFormatException e) {
                                                                price = 0.0;
                                                            } catch (Exception e1) {
                                                                price = 0.0;
                                                            }

                                                            mSetMenuPrice = quantityCost + price;
                                                            quantityCost = mSetMenuPrice;


                                                        }

                                                    } else {
*//*
                                                        setMenuModifier.setChecked(false);*//*



                                                    }


                                                    setMenuModifier.setModifierHeadingList(modifierHeadingList);
                                                    setMenuModifierList.add(setMenuModifier);

                                                }
                                            }

                                        }

                                    } else {

                                    }

                                    setMenuTitle.setSetMenuModifierList(setMenuModifierList);
                                    setMenuTitleList.add(setMenuTitle);

                                }


                            } else {

                            }

                            setMenuProduct.setSetMenuTitleList(setMenuTitleList);

                        }


                        setMenuproductDetailsDialog1(mContext, mProductId, mQuantity);




                    } else {


                    }


                } else {

                    //    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();

            }


        }
    }*/

    private class SetMenuProductDetailsTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<SetMenuTitle> setMenuTitleList;
        private List<SetMenuModifier> setMenuModifierList;
        private List<ModifierHeading> modifierHeadingList;
        private List<ModifiersValue> modifiersValueList;
        private SetMenuTitle setMenuTitle;
        private SetMenuModifier setMenuModifier;
        private ModifierHeading modifierHeading;
        private ModifiersValue modifiersValue;

        private String mProductId = "", mQuantity = "1";

        public SetMenuProductDetailsTask(String id, String totalQuantity) {

            mProductId = id;
            mQuantity = totalQuantity;
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
            double price = 0.0;
            double subTotal = 0.0;
            double main_subTotal = 0.0;
            mProductFavPrimaryId = "null";
            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    mBasePath = jsonCommon.optString("image_source");
                    galleryBasePath = jsonCommon.optString("product_gallery_image_source");

                    JSONArray jsonResultArray = jsonObject.getJSONArray("result_set");

                    if (jsonResultArray.length() > 0) {
                        for (int r = 0; r < jsonResultArray.length(); r++) {

                            JSONObject jsonResult = jsonResultArray.getJSONObject(r);

                            setMenuProduct = new SetMenuProduct();

                            setMenuProduct.setmProductId(jsonResult.getString("product_id"));
                            setMenuProduct.setmProductName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmProductAliasName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmproduct_apply_minmax_select(jsonResult.getString("product_apply_minmax_select"));
                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmfav_product_primary_id(jsonResult.getString("fav_product_primary_id"));
                            setMenuProduct.setmproduct_primary_id(jsonResult.getString("product_primary_id"));
                            mProductFavPrimaryId = jsonResult.getString("fav_product_primary_id");
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            setMenuProduct.setmProductImage(mBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            setMenuProduct.setmProductStatus(jsonResult.getString("product_status"));
                            setMenuProduct.setmProductPrice(jsonResult.getString("product_price"));
                            setMenuProduct.setmApplyMinMaxSelect(Integer.parseInt(jsonResult.getString("product_apply_minmax_select")));

                            try {

                                mSetMenuBasePrice = Double.valueOf(jsonResult.getString("product_price"));
                            } catch (Exception e) {
                                mSetMenuBasePrice = 0.0;
                            }

                            mSetMenuPrice = mSetMenuBasePrice;

                            quantityCost = mSetMenuBasePrice;
                            quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);


                            String largeImage = "";
                            JSONArray image_gallery = jsonResult.getJSONArray("image_gallery");
                            for (int i = 0; i < image_gallery.length(); i++) {
                                JSONObject jsonObject1 = image_gallery.getJSONObject(i);
                                largeImage = jsonObject1.getString("pro_gallery_image");
                                if (largeImage != null && largeImage.length() > 0) {
                                    largeImage = galleryBasePath + "/" + largeImage;
                                    setMenuProduct.setmProductLargeImage(largeImage);
                                }
                            }


                            JSONArray setmenuArray = jsonResult.getJSONArray("set_menu_component");

                            setMenuTitleList = new ArrayList<>();

                            if (setmenuArray.length() > 0) {

                                for (int t = 0; t < setmenuArray.length(); t++) {
                                    JSONObject jsonSetmenu = setmenuArray.getJSONObject(t);

                                    setMenuTitle = new SetMenuTitle();

                                    setMenuTitle.setmTitleMenuId(jsonSetmenu.optString("menu_component_id"));
                                    setMenuTitle.setmTitleMenuName(jsonSetmenu.optString("menu_component_name"));
                                    setMenuTitle.setmultipleselection_apply(jsonSetmenu.optString("menu_component_multipleselection_apply"));
                                    setMenuTitle.setmenu_component_modifier_apply(jsonSetmenu.optString("menu_component_modifier_apply"));
                                    GlobalValues.MODIFIERAPPLY = jsonSetmenu.optString("menu_component_modifier_apply");
                                    GlobalValues.MULTIPLESLECTIONAPPLY = jsonSetmenu.optString("menu_component_multipleselection_apply");
                                    setMenuTitle.setmTitleMenuId(jsonSetmenu.optString("menu_component_id"));
                                    setMenuTitle.setmAppliedPrice(jsonSetmenu.optString("menu_component_apply_price"));
                                    setMenuTitle.setmMinSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_min_select")));
                                    setMenuTitle.setmMaxSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_max_select")));
                                    setMenuTitle.setmDefaultSelectId(jsonSetmenu.optString("menu_component_default_select"));

                                    setMenuTitle.setmTotalQuantity(Integer.parseInt(mQuantity));


                                    JSONArray jsonProductArray = jsonSetmenu.getJSONArray("product_details");

                                    setMenuModifierList = new ArrayList<>();

                                    if (jsonProductArray.length() > 0) {

                                        for (int p = 0; p < jsonProductArray.length(); p++) {

                                            JSONArray jsonArray = jsonProductArray.getJSONArray(p);

                                            if (jsonArray.length() > 0) {
                                                for (int p1 = 0; p1 < jsonArray.length(); p1++) {

                                                    JSONObject object = jsonArray.getJSONObject(p1);

                                                    setMenuModifier = new SetMenuModifier();

                                                    setMenuModifier.setmModifierId(object.optString("product_id"));
                                                    setMenuModifier.setmModifierName(object.optString("product_alias"));
                                                    setMenuModifier.setsub_modifier_apply(jsonSetmenu.optString("menu_component_modifier_apply"));
                                                    setMenuModifier.setsub_multipleselection_apply(jsonSetmenu.optString("menu_component_multipleselection_apply"));
                                                    setMenuModifier.setApplyPrice(jsonSetmenu.optString("menu_component_apply_price"));
                                                    setMenuModifier.setmModifierAliasName(object.optString("product_alias"));
                                                    setMenuModifier.setmMaxSelect(object.optString("product_bagel_max_select"));
                                                    setMenuModifier.setmMinSelect(object.optString("product_bagel_min_select"));
                                                    setMenuModifier.setmModifierPrice(object.optString("product_price"));
                                                    setMenuModifier.setmModifierSku(object.optString("product_sku"));
                                                    setMenuModifier.setmModifierSlug(object.optString("product_slug"));
                                                    if (GlobalValues.SELECTEDMODIFIRELIST.size() != 0) {

                                                        for (int i = 0; i < GlobalValues.SELECTEDMODIFIRELIST.size(); i++) {

                                                            if (GlobalValues.SELECTEDMODIFIRELIST.get(i).equalsIgnoreCase(object.getString("product_id"))) {
                                                                setMenuModifier.setChecked(true);
                                                                setMenuTitle.settQuantity(1);
                                                                main_subTotal = main_subTotal + Double.valueOf(object.getString("product_price"));

                                                                mSetMenuPrice = mSetMenuBasePrice + main_subTotal;

                                                                quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);
                                                            }
                                                        }


                                                    }


                                                    modifierHeadingList = new ArrayList<>();

                                                    Object modifierObject = object.get("modifiers");

                                                    if (modifierObject instanceof JSONArray) {


                                                        JSONArray jsonModifiersArray = object.getJSONArray("modifiers");


                                                        if (jsonModifiersArray != null && jsonModifiersArray.length() > 0) {

                                                            setMenuModifier.setHasModifier(true);

                                                            for (int m = 0; m < jsonModifiersArray.length(); m++) {

                                                                JSONObject jsonModifier = jsonModifiersArray.getJSONObject(m);

                                                                modifierHeading = new ModifierHeading();

                                                                modifierHeading.setmModifierHeading(jsonModifier.getString("pro_modifier_name"));
                                                                modifierHeading.setmModifierHeadingId(jsonModifier.getString("pro_modifier_id"));
                                                                modifierHeading.setmModifierMinSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_min_select")));
                                                                modifierHeading.setmModifierMaxSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_max_select")));
                                                                modifierHeading.setmMaxSelected(0);

                                                                if (GlobalValues.SELECTEDMODIFIERTITLES.size() != 0) {
                                                                    for (int h = 0; h < GlobalValues.SELECTEDMODIFIERTITLES.size(); h++) {
                                                                        if (GlobalValues.SELECTEDMODIFIERTITLES.get(h).getId().equalsIgnoreCase(jsonModifier.getString("pro_modifier_id"))) {
                                                                            /*modifiersValue.setChekced(true);
                                                                            modifierHeading.setmMaxSelected(1);
                                                                            subTotal = subTotal + Double.valueOf(jsonModifiervalue.getString("pro_modifier_value_price"));

                                                                            mSetMenuPrice = mSetMenuPrice + subTotal;

                                                                            quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);
                                                                         */

                                                                            modifierHeading.settQuantity(GlobalValues.SELECTEDMODIFIERTITLES.get(h).getQuantity());
                                                                        }
                                                                    }
                                                                }


                                                                JSONArray modifierValueArray = jsonModifier.getJSONArray("modifiers_values");

                                                                modifiersValueList = new ArrayList<>();

                                                                if (modifierValueArray.length() > 0) {

                                                                    for (int v = 0; v < modifierValueArray.length(); v++) {

                                                                        JSONObject jsonModifiervalue = modifierValueArray.getJSONObject(v);

                                                                        modifiersValue = new ModifiersValue();


                                                                        modifiersValue.setmModifierId(jsonModifiervalue.getString("pro_modifier_value_id"));
                                                                        modifiersValue.setmModifierValuePrice(jsonModifiervalue.getString("pro_modifier_value_price"));
                                                                        modifiersValue.setmModifierName(jsonModifiervalue.getString("pro_modifier_value_name"));
                                                                        modifiersValue.setmModifierDefault(jsonModifiervalue.getString("pro_modifier_value_is_default"));

                                                                        if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

                                                                            if (GlobalValues.SELECTEDMODIFIRELIST.size() != 0) {

                                                                                for (int i = 0; i < GlobalValues.SELECTEDMODIFIRELIST.size(); i++) {

                                                                                    if (GlobalValues.SELECTEDMODIFIRELIST.get(i).equalsIgnoreCase(object.getString("product_id"))) {


                                                                                        if (GlobalValues.SELECTEDMODIFIRESUBLIST.size() != 0) {

                                                                                            for (int l = 0; l < GlobalValues.SELECTEDMODIFIRESUBLIST.size(); l++) {
                                                                                                if (GlobalValues.SELECTEDMODIFIRESUBLIST.get(l).equalsIgnoreCase(jsonModifiervalue.getString("pro_modifier_value_id"))) {
                                                                                                    modifiersValue.setChekced(true);
                                                                                                    modifierHeading.setmMaxSelected(1);
                                                                                                    subTotal = subTotal + Double.valueOf(jsonModifiervalue.getString("pro_modifier_value_price"));

                                                                                                    mSetMenuPrice = mSetMenuPrice + subTotal;

                                                                                                    quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);

                                                                                                }
                                                                                            }

                                                                                        }



                                                                                    }
                                                                                }
                                                                            }
                                                                        } else {
                                                                            for (int i = 0; i < GlobalValues.SELECTEDMODIFIRELIST.size(); i++) {
                                                                                if (GlobalValues.SELECTEDMODIFIRELIST.get(i).equalsIgnoreCase(object.getString("product_id"))) {
                                                                                    if (GlobalValues.SELECTEDMODIFIRESUBLIST.size() != 0) {
                                                                                        for (int l = 0; l < GlobalValues.SELECTEDMODIFIRESUBLIST.size(); l++) {
                                                                                            if (GlobalValues.SELECTEDMODIFIRESUBLIST.get(l).equalsIgnoreCase(jsonModifiervalue.getString("pro_modifier_value_id"))) {
                                                                                                if (GlobalValues.SELECTEDMODIFIERVALUES.get(l).getModifierId().equalsIgnoreCase(jsonModifiervalue.getString("pro_modifier_value_id"))) {
                                                                                                    modifiersValue.setChekced(true);
                                                                                                    modifiersValue.setmSubModifierTotal(GlobalValues.SELECTEDMODIFIERVALUES.get(l).getModifierQty());
                                                                                                    setMenuTitle.settQuantity(1);
                                                                                                    Log.e("ModName", GlobalValues.SELECTEDMODIFIERVALUES.get(l).getModifierQty() + "--" + jsonModifiervalue.getString("pro_modifier_value_name") + "----"
                                                                                                            + setMenuTitle.getmAppliedPrice() + "----" + jsonModifiervalue.getString("pro_modifier_value_price"));

                                                                                                    if (setMenuTitle.getmAppliedPrice().equalsIgnoreCase("1")) {

                                                                                                    } else {
                                                                                                        Log.e("childbefore", String.valueOf(plusminusPrice));
                                                                                                        plusminusPrice -= (Double.parseDouble(jsonModifiervalue.getString("pro_modifier_value_price")) * GlobalValues.SELECTEDMODIFIERVALUES.get(l).getModifierQty());
                                                                                                        Log.e("childafter", String.valueOf(plusminusPrice));
                                                                                                    }

                                                                                                    //plusminusPrice += (Double.parseDouble(modifiersValue.getmModifierValuePrice()) * modifiersValue.getmSubModifierTotal());
                                                                                                }
                                                                                                    /*modifiersValue.setChekced(true);
                                                                                                    modifierHeading.setmMaxSelected(1);
                                                                                                    subTotal = subTotal + Double.valueOf(jsonModifiervalue.getString("pro_modifier_value_price"));

                                                                                                    mSetMenuPrice = mSetMenuPrice + subTotal;

                                                                                                    quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);*/

                                                                                            }
                                                                                        }
                                                                                    }



                                                                                }
                                                                            }
                                                                        }

                                                                        modifiersValueList.add(modifiersValue);

                                                                    }

                                                                }

                                                                modifierHeading.setModifiersList(modifiersValueList);
                                                                modifierHeadingList.add(modifierHeading);

                                                             }

                                                        } else {
                                                            setMenuModifier.setHasModifier(false);
                                                        }

                                                    } else {
                                                         setMenuModifier.setHasModifier(false);
                                                    }


                                                    setMenuModifier.setModifierHeadingList(modifierHeadingList);
                                                    setMenuModifierList.add(setMenuModifier);

                                                }
                                            }

                                        }

                                    } else {

                                    }

                                    setMenuTitle.setSetMenuModifierList(setMenuModifierList);
                                    setMenuTitleList.add(setMenuTitle);

                                }


                            } else {

                            }

                            setMenuProduct.setSetMenuTitleList(setMenuTitleList);

                        }
                        setMenuproductDetailsDialog1(mContext, mProductId, mQuantity);

                    } else {


                    }


                } else {

                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private void setMenuproductDetailsDialog01(final Context mContext, final String mProductId, String quantity) {

        Utility.writeToSharedPreference(mContext, GlobalValues.EDITCOUNT, quantity);
        mSetMenuQuantity = Integer.parseInt(quantity);
        mProductQuantity = quantity;

        final SpannableString cs;

        SetMenuTitleRecyclerCartAdapter setMenuTitleRecyclerAdapter;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO:Identification2
        dialog.setContentView(R.layout.layout_product_details_dialog);
        dialog.show();
        favouriteText = dialog.findViewById(R.id.favouriteText);

        favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
        notesText1 = dialog.findViewById(R.id.notesText);
        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        TextView txtDone = (TextView) dialog.findViewById(R.id.Addtocart);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

        //txtModi = (TextView) dialog.findViewById(R.id.txtPrice);
        TextView textHead = dialog.findViewById(R.id.textHead);
        // textHead.setText(subCatString.toUpperCase());
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        ImageView layoutClose = (ImageView) dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);
        txtQuantity.setText(quantity);
        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        setmenuRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(setMenuProduct.getmProductAliasName());
        txtProductDesc.setText(setMenuProduct.getmProductDesc());


        if (setMenuProduct.getmfav_product_primary_id().equalsIgnoreCase("null")) {
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

            favouriteText.setText("Add to favourite");

            StatusFav = "1";

        } else {
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

            favouriteText.setText("Remove from favourite");

            StatusFav = "0";
        }

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavouriteMethod(setMenuProduct.getmproduct_primary_id());
            }
        });

        mProductQuantity = txtQuantity.getText().toString();

        try {

            mSearchProuductprise = Double.valueOf(setMenuProduct.getmProductPrice());

        } catch (Exception e) {
            mSearchProuductprise = 0.0;

        }

        mquanititycost_src = mSetMenuPrice;




        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2) {

            //txtModi = (TextView) dialog.findViewById(R.id.txtPrice);
            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

            cs = new SpannableString(String.format("%.2f", new BigDecimal(quantityCost)));
            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
            //txtModi.setText(cs);
            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));

            if (setMenuProduct.getSetMenuTitleList() != null &&
                    setMenuProduct.getSetMenuTitleList().size() > 0) {
               /* if (setMenuProduct.getmApplyMinMaxSelect() == 1) {


                    if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                        if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {


                            setMenuTitleRecyclerAdapternew = new SetMenuTitleRecyclerAdapterNew(mContext,
                                    setMenuProduct.getSetMenuTitleList(), Integer.valueOf(setMenuProduct.getmApplyMinMaxSelect()), 2, new SetMenuTitleRecyclerAdapterNew.PasstheValue() {
                                @Override
                                public void addtoSubtotla(String productPrice) {
                                    double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);

                                    Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) + valu;

                                    SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                                    txtModi.setText(cs);
                                }

                                @Override
                                public void subtoSubtotla(String productPrice) {
                                    double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);
                                    Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) - valu;

                                    SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                                    txtModi.setText(cs);

                                }
                            });

                            setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapternew);
                            setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            setmenuRecyclerView.setNestedScrollingEnabled(false);
                            setmenuRecyclerView.setHasFixedSize(true);
                        } else {*/

                txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                SpannableString cs1 = new SpannableString(String.format("%.2f", new BigDecimal(TotalPriceSetMenu)));
                cs1.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs1.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(TotalPriceSetMenu)));

                setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerCartAdapter(mContext,
                        setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "update");
                setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                setmenuRecyclerView.setNestedScrollingEnabled(false);
                setmenuRecyclerView.setHasFixedSize(true);
                      /*  }
                    } else {

                        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                        SpannableString cs1 = new SpannableString("$" + String.format("%.2f", new BigDecimal(TotalPriceSetMenu)));
                        cs1.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        cs1.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
                        txtModifierPrice.setText(cs1);

                        setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerCartAdapter(mContext,
                                setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "update");
                        setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                        setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        setmenuRecyclerView.setNestedScrollingEnabled(false);
                        setmenuRecyclerView.setHasFixedSize(true);

                    }
                } else {


                    txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                    SpannableString cs1 = new SpannableString("$" + String.format("%.2f", new BigDecimal(TotalPriceSetMenu)));
                    cs1.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs1.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
                    txtModifierPrice.setText(cs1);

                    setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerCartAdapter(mContext,
                            setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "update");
                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);
                }*/

            }
        } /*else {
            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
            SpannableString cs1 = new SpannableString("$" + String.format("%.2f", new BigDecimal(TotalPriceSetMenu)));
            cs1.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs1.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
            txtModifierPrice.setText(cs1);

            setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerCartAdapter(mContext,
                    setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
            setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
            setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
            setmenuRecyclerView.setNestedScrollingEnabled(false);
            setmenuRecyclerView.setHasFixedSize(true);
        }*/


        if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0) {


            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);

        } else {

            if (setMenuProduct.getmProductImage() != null && setMenuProduct.getmProductImage().length() > 0) {

                Picasso.with(mContext).load(setMenuProduct.getmProductImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);

            } else {

                Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);
            }
        }
        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (setMenuProduct.getmApplyMinMaxSelect() == 1) {


                    if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                        if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            count++;
                            txtQuantity.setText(String.valueOf(count));


                            try {
                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString())));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            count++;
                            mSetMenuQuantity = count;
                            quantityCost = mSetMenuPrice * mSetMenuQuantity;

                            txtQuantity.setText(String.valueOf(count));
                            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                            mProductQuantity = txtQuantity.getText().toString();

                            mProductQuantity = txtQuantity.getText().toString();
                        }
                    } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        count++;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;

                        txtQuantity.setText(String.valueOf(count));
                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                        mProductQuantity = txtQuantity.getText().toString();

                        mProductQuantity = txtQuantity.getText().toString();
                    }


                } else {


                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    mSetMenuQuantity = count;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;

                    txtQuantity.setText(String.valueOf(count));

                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                    mProductQuantity = txtQuantity.getText().toString();


                }

            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (setMenuProduct.getmApplyMinMaxSelect() == 1) {


                    if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                        if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            if (count > 1) {
                                count--;

                                txtQuantity.setText(count + "");

                                try {
                                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString())));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                mProductQuantity = txtQuantity.getText().toString();
                            }

                        } else {
                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            if (count > 1) {
                                count--;
                                mSetMenuQuantity = count;
                                quantityCost = mSetMenuPrice * mSetMenuQuantity;

                                txtQuantity.setText(count + "");

                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                                mProductQuantity = txtQuantity.getText().toString();
                            }
                        }

                    } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {
                            count--;
                            mSetMenuQuantity = count;
                            quantityCost = mSetMenuPrice * mSetMenuQuantity;

                            txtQuantity.setText(count + "");

                            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                            mProductQuantity = txtQuantity.getText().toString();
                        }


                    }
                } else {

                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    if (count > 1) {
                        count--;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;

                        txtQuantity.setText(count + "");

                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));


                        mProductQuantity = txtQuantity.getText().toString();

                    }

                }


            }
        });


        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {

                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());

            } else {

                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        txtDone.setText("Update");
        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                validateSetMenu(setMenuProduct.getSetMenuTitleList());
                // validateSetMenuNew();

            }
        });

    }

    private void setMenuproductDetailsDialog1(final Context mContext, final String mProductId, String quantity) {

        Log.e("cost", TotalPriceSetMenu + "--" + quantityCost + "--" + plusminusPrice + "--" + mSearchProuductprise + "--" + mSetmenuoverallprices
                + "--" + SubCategoryActivity.quantityCost);
        SubCategoryActivity.quantityCost = quantityCost;
        double singleQuantityCost = quantityCost / Integer.parseInt(quantity);
        Log.e("qunatity_cost", String.valueOf(singleQuantityCost) + "----" + quantityCost);
        SubCategoryActivity subCategoryActivity = new SubCategoryActivity();
        mProductQuantity = quantity;
        SubCategoryActivity.mSetMenuQuantity = Integer.parseInt(quantity);
        SubCategoryActivity.mSetMenuPrice = singleQuantityCost;
        subModifierPrice = plusminusPrice;
        GlobalValues.modifierSelectCount = 0;
        GlobalValues.subModifierSelectCount = 0;
        GlobalValues.MODIFIER_NAME = "";

        final SpannableString cs;

        SetMenuTitleRecyclerAdapter setMenuTitleRecyclerAdapter;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO:Identification2
        dialog.setContentView(R.layout.layout_product_details_dialog);
        dialog.show();
        favouriteText = dialog.findViewById(R.id.favouriteText);
        favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
        notesText1 = dialog.findViewById(R.id.notesText);

        if (!cartSplNotes.equalsIgnoreCase("")) {
            notesText1.setText(cartSplNotes);
        }

        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        final TextView Addtocart = (TextView) dialog.findViewById(R.id.Addtocart);
        Addtocart.setText("Update Cart");
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

        Toolbar toolbar = dialog.findViewById(R.id.toolBar);
        LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
        TextView textHead = dialog.findViewById(R.id.textHead);
        textHead.setText(subCatString);
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        ImageView layoutClose = (ImageView) dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        txtQuantity.setText(quantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);

        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        setmenuRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(setMenuProduct.getmProductAliasName());
        txtProductDesc.setText(setMenuProduct.getmProductDesc());

        if (isCustomerLoggedIn()) {
            if (mProductFavPrimaryId.equalsIgnoreCase("null")) {
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

                favouriteText.setText("Add to favourite");


                StatusFav = "1";
                //heartblink_imageview.setImageResource(R.drawable.heart_favourite);
                //subCategoryActivity.CheckFacourites();
            } else {
                favouriteText.setText("Remove from favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                StatusFav = "0";
                //heartblink_imageview.setImageResource(R.drawable.heart_white);
                //subCategoryActivity.CheckFacourites();
            }
        } else {
            favouriteText.setText("Add to favourite");
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
            StatusFav = "1";
        }

       /*
        if (!(productsList.get(position).getmFaouriteStatusLable().equalsIgnoreCase(""))) {
            favouriteText.setText(productsList.get(position).getmFaouriteStatusLable());
        }

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCustomerLoggedIn()) {
                    addFavouriteMethod(mProductPrimaryId, productHolder, position);
                } else {
                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                                ((Activity) mContext).finish();
                            }
                        }
                    });
                }
            }
        });*/

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavouriteMethod(setMenuProduct.getmproduct_primary_id());
            }
        });

        mProductQuantity = txtQuantity.getText().toString();


        try {

            SubCategoryActivity.mSearchProuductprise = Double.valueOf(setMenuProduct.getmProductPrice());
            //SubCategoryActivity.mSetMenuPrice = Double.valueOf(setMenuProduct.getmProductPrice());
        } catch (Exception e) {
            SubCategoryActivity.mSearchProuductprise = 0.0;
        }
        SubCategoryActivity.mquanititycost_src = SubCategoryActivity.mSetMenuPrice;




      /*  try {
            mSetMenuPrice = Double.valueOf(setMenuProduct.getmProductPrice());
            mSetMenuBasePrice = Double.valueOf(setMenuProduct.getmProductPrice());
        }catch (NumberFormatException e)
        {
            mSetMenuPrice=0.0;
            mSetMenuBasePrice=0.0;
            e.printStackTrace();
        }*/


        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2) {

            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

            cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(SubCategoryActivity.mSearchProuductprise)));

            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
            txtModifierPrice.setText(String.format("%.2f", new BigDecimal((SubCategoryActivity.mSetMenuPrice + plusminusPrice) * Integer.parseInt(quantity))));

            if (setMenuProduct.getSetMenuTitleList() != null && setMenuProduct.getSetMenuTitleList().size() > 0) {

               /* if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {*/

                /*if ((setMenuProduct.getmApplyMinMaxSelect() == 1)
                        && setMenuProduct.getSetMenuTitleList().get(position).getmenu_component_modifier_apply().equalsIgnoreCase("0")
                        && setMenuProduct.getSetMenuTitleList().get(position).getmultipleselection_apply().equalsIgnoreCase("0")) {


                    setMenuTitleRecyclerAdapternew = new SetMenuTitleRecyclerAdapterNew(mContext,
                            setMenuProduct.getSetMenuTitleList(), Integer.valueOf(setMenuProduct.getmApplyMinMaxSelect()), 2, new SetMenuTitleRecyclerAdapterNew.PasstheValue() {
                        @Override
                        public void addtoSubtotla(String productPrice) {
                            double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);
                            Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) + valu;
                            SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));
                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
                            txtModifierPrice.setText(String.format("%.2f", finalVal));
                        }

                        @Override
                        public void subtoSubtotla(String productPrice) {
                            double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);
                            Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) - valu;
                            SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));
                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
                            txtModifierPrice.setText(String.format("%.2f", finalVal));
                        }
                    });

                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapternew);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);

                } else {*/

                txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                //txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost + plusminusPrice)));

                txtModifierPrice.setText(String.format("%.2f", new BigDecimal((SubCategoryActivity.mSetMenuPrice + plusminusPrice) * Integer.parseInt(quantity))));
                setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                        setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "update");
                setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                setmenuRecyclerView.setNestedScrollingEnabled(false);
                setmenuRecyclerView.setHasFixedSize(true);

                        /*}else{
                            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModifierPrice.setText(cs);
                            setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                                    setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create", modifierHeadingList);
                            setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                            setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            setmenuRecyclerView.setNestedScrollingEnabled(false);
                            setmenuRecyclerView.setHasFixedSize(true);
                        }
                } else {
                    txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtModifierPrice.setText(cs);
                    setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                            setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create", modifierHeadingList);
                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);
                }*/
            }
        }

        /*


        if (productsList.get(productHolder.getAdapterPosition()).getmSubCategoryGalleryImage() != null
                && productsList.get(productHolder.getAdapterPosition()).getmSubCategoryGalleryImage().length() > 0) {

            Picasso.with(mContext).load(productsList.get(productHolder.getAdapterPosition()).getmSubCategoryGalleryImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);
        } else {
            Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);
        }*/

        if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0) {


            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);

        } else {

            if (setMenuProduct.getmProductImage() != null && setMenuProduct.getmProductImage().length() > 0) {

                Picasso.with(mContext).load(setMenuProduct.getmProductImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);

            } else {

                Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);
            }
        }
//dkfasjlkdfjdsajf
        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    txtQuantity.setText(String.valueOf(count));

                    SubCategoryActivity.mSetMenuQuantity = count;
                    SubCategoryActivity.quantityCost = (SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * SubCategoryActivity.mSetMenuQuantity;

                    try {

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

                    try {
                        txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    int count = Integer.parseInt(txtQuantity.getText().toString());
                    //double modPrice = subModifierPrice / mSetMenuQuantity;
                    Log.e("count", String.valueOf(count));
                    count++;
                    SubCategoryActivity.mSetMenuQuantity = count;
                    SubCategoryActivity.quantityCost = (SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * SubCategoryActivity.mSetMenuQuantity;

                    txtQuantity.setText(String.valueOf(count));

                    if (mSetmenuoverallprices != 0.00) {
                        txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                    } else {
                        txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                    }

                    mProductQuantity = txtQuantity.getText().toString();

                }

            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if ((setMenuProduct.getmApplyMinMaxSelect() == 1)
                        && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    if (count > 1) {
                        count--;
                        SubCategoryActivity.mSetMenuQuantity = count;
                        SubCategoryActivity.quantityCost = (SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * SubCategoryActivity.mSetMenuQuantity;
                        txtQuantity.setText(count + "");

                        try {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        mProductQuantity = txtQuantity.getText().toString();
                    }

                } else {

                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    if (count > 1) {

                        count--;
                        SubCategoryActivity.mSetMenuQuantity = count;
                        double modPrice = subModifierPrice / SubCategoryActivity.mSetMenuQuantity;
                         double txtQty = Double.valueOf(txtQuantity.getText().toString()) + 1d;
                        double subModiPrice = value * count;
                        double setMenuPrice = SubCategoryActivity.mSetMenuPrice + subModiPrice;


                        SubCategoryActivity.quantityCost = (SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * SubCategoryActivity.mSetMenuQuantity;
                        //quantityCost = setMenuPrice * ((double)(mSetMenuQuantity));
                        txtQuantity.setText(count + "");

                        if (mSetmenuoverallprices != 0.00) {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                        } else {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                        }

                        mProductQuantity = txtQuantity.getText().toString();
                    }
                }
            }
        });


        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });


        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {

                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());

            } else {

                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lly_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Addtocart.performClick();
            }
        });

        Addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isCustomerLoggedIn()) {
                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                                ((Activity) mContext).finish();
                            }
                        }
                    });
                } else {

                    //validateSetMenu(setMenuProduct.getSetMenuTitleList(), productHolder);
                    validateSetMenu(setMenuProduct.getSetMenuTitleList());
                }
            }
        });

    }

    private void setMenuproductDetailsDialogNew(final Context mContext, String mProductId, String quantity) {


        this.mProductQuantity = quantity;
        mSetMenuQuantity = 1;

        SetMenuTitleRecyclerAdapter setMenuTitleRecyclerAdapter;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_search_product_details_dialog);
        dialog.show();
        final SpannableString cs;

        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        txtModi = (TextView) dialog.findViewById(R.id.txtPrice);
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);

        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        setmenuRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(setMenuProduct.getmProductAliasName());
        txtProductDesc.setText(setMenuProduct.getmProductDesc());

        txtQuantity.setText(productCartDetails.getmProductQty());

        mProductQuantity = txtQuantity.getText().toString();


        try {

            mSearchProuductprise = Double.valueOf(setMenuProduct.getmProductPrice());
        } catch (Exception e) {
            mSearchProuductprise = 0.0;
        }
        Double pQuantVal = null;
        try {
            pQuantVal = getsetMenuProductPrice() * Integer.valueOf(productCartDetails.getmProductQty());
        } catch (Exception e) {
            e.printStackTrace();
        }

        cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(pQuantVal)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModi.setText(cs);


        mquanititycost_src = mSetMenuPrice;

        txtDone.setText("Update");

      /*  try {
            mSetMenuPrice = Double.valueOf(setMenuProduct.getmProductPrice());
            mSetMenuBasePrice = Double.valueOf(setMenuProduct.getmProductPrice());
        }catch (NumberFormatException e)
        {
            mSetMenuPrice=0.0;
            mSetMenuBasePrice=0.0;
            e.printStackTrace();
        }*/



        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2) {

            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

            SpannableString cs1 = new SpannableString("$" + String.format("%.2f", new BigDecimal(mSearchProuductprise)));

            cs1.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs1.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

            txtModifierPrice.setText(cs1);

            if (setMenuProduct.getSetMenuTitleList() != null &&
                    setMenuProduct.getSetMenuTitleList().size() > 0) {

                if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

                    if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                        if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

                            setMenuTitleRecyclerAdapternew = new SetMenuTitleRecyclerAdapterNew(mContext,
                                    setMenuProduct.getSetMenuTitleList(), Integer.valueOf(setMenuProduct.getmApplyMinMaxSelect()), 2, new SetMenuTitleRecyclerAdapterNew.PasstheValue() {
                                @Override
                                public void addtoSubtotla(String productPrice) {
                                    double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);

                                    Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) + valu;

                                    SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                                    txtModifierPrice.setText(cs);
                                }

                                @Override
                                public void subtoSubtotla(String productPrice) {
                                    double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);
                                    Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) - valu;

                                    SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                                    txtModifierPrice.setText(cs);

                                }
                            });

                            setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapternew);
                            setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            setmenuRecyclerView.setNestedScrollingEnabled(false);
                            setmenuRecyclerView.setHasFixedSize(true);
                        } else {

                            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModifierPrice.setText(cs);
                            setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                                    setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                            setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                            setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            setmenuRecyclerView.setNestedScrollingEnabled(false);
                            setmenuRecyclerView.setHasFixedSize(true);
                        }
                    } else {
                        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

                        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                        txtModifierPrice.setText(cs);
                        setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                                setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                        setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                        setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        setmenuRecyclerView.setNestedScrollingEnabled(false);
                        setmenuRecyclerView.setHasFixedSize(true);
                    }


                } else {


                    txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtModifierPrice.setText(cs);
                    setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                            setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);
                }

            }
        }

        if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0) {

            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.default_image).into(imgProduct);

        } else {

            if (setMenuProduct.getmProductImage() != null && setMenuProduct.getmProductImage().length() > 0) {

                Picasso.with(mContext).load(setMenuProduct.getmProductImage()).error(R.drawable.default_image).into(imgProduct);

            } else {

                Picasso.with(mContext).load(R.drawable.default_image).into(imgProduct);
            }
        }
        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (setMenuProduct.getmApplyMinMaxSelect() == 1) {


                    if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                        if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            count++;
                            txtQuantity.setText(String.valueOf(count));


                            try {
                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString())));

                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                        } else {
                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            count++;
                            mSetMenuQuantity = count;
                            quantityCost = mSetMenuPrice * mSetMenuQuantity;

                            txtQuantity.setText(String.valueOf(count));
                            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                            mProductQuantity = txtQuantity.getText().toString();

                            mProductQuantity = txtQuantity.getText().toString();
                        }
                    } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        count++;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;

                        txtQuantity.setText(String.valueOf(count));
                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                        mProductQuantity = txtQuantity.getText().toString();

                        mProductQuantity = txtQuantity.getText().toString();
                    }


                } else {


                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    mSetMenuQuantity = count;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;

                    txtQuantity.setText(String.valueOf(count));

                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                    mProductQuantity = txtQuantity.getText().toString();




                }


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (setMenuProduct.getmApplyMinMaxSelect() == 1) {


                    if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                        if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            if (count > 1) {
                                count--;

                                txtQuantity.setText(count + "");

                                try {
                                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString())));

                                } catch (Exception e) {
                                    e.printStackTrace();
                                }


                                mProductQuantity = txtQuantity.getText().toString();
                            }

                        } else {
                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            if (count > 1) {
                                count--;
                                mSetMenuQuantity = count;
                                quantityCost = mSetMenuPrice * mSetMenuQuantity;

                                txtQuantity.setText(count + "");

                                txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                                mProductQuantity = txtQuantity.getText().toString();
                            }
                        }

                    } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {
                            count--;
                            mSetMenuQuantity = count;
                            quantityCost = mSetMenuPrice * mSetMenuQuantity;

                            txtQuantity.setText(count + "");

                            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                            mProductQuantity = txtQuantity.getText().toString();
                        }


                    }
                } else {

                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    if (count > 1) {
                        count--;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;

                        txtQuantity.setText(count + "");

                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));


                        mProductQuantity = txtQuantity.getText().toString();

                    }

                }


            }
        });


        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {

                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());

            } else {

                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //  validateSetMenuNew();
                validateSetMenu(setMenuProduct.getSetMenuTitleList());

            }
        });


    }

    private void validateSetMenuNew() {
      /*  for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
//
            if (setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() < setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect()) {
                Toast.makeText(mContext, "Minimum  " + setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect() + " not selected", Toast.LENGTH_SHORT).show();
                return;
            }

        }*/

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.ADD_CART_SET_MENU_URL;

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = "";

            } else {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                    mReferenceId = GlobalValues.DEVICE_ID;

                } catch (Exception e) {
                    GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                    mReferenceId = GlobalValues.DEVICE_ID;

                } finally {
                    mCustomerId = "";
                }
            }


            Map<String, String> mapData = new HashMap<>();
            mapData.put("reference_id", mReferenceId);
            mapData.put("customer_id", mCustomerId);
            mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
            mapData.put("app_id", GlobalValues.APP_ID);
            mapData.put("availability_id", CURRENT_AVAILABLITY_ID);
            mapData.put("product_qty", mProductQuantity);
            mapData.put("product_id", setMenuProduct.getmProductId());
            mapData.put("product_name", setMenuProduct.getmProductAliasName());
            if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                mapData.put("product_total_price", txtModi.getText().toString().replace("$", ""));


                try {




                    mapData.put("product_unit_price", String.valueOf(getsetMenuProductPrice()));

                } catch (Exception e) {
                    e.printStackTrace();
                }


            } else {
                mapData.put("product_total_price", String.valueOf(quantityCost));

                mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));

            }
            mapData.put("product_image", setMenuProduct.getmProductImage());
            mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
            mapData.put("product_sku", setMenuProduct.getmProductSku());
            mapData.put("product_type", setMenuProduct.getmProductType());

            if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
            } else {
                mapData.put("product_modifier_parent_id", "");

            }


            mapData.put("menu_set_component", constructSetMenuJson().toString());


            new AddCartTask(mapData, mProductQuantity).execute(url);

            mquanititycost_src = 0.0;

        } else {
            Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
        }
    }


    private void validateSetMenu0(List<SetMenuTitle> setMenuTitleList) {


        boolean isChildSelected = false;
        int count = 0;
        for (int j = 0; j < setMenuTitleList.size(); j++) {

            isChildSelected = false;



            if (setMenuProduct.getmApplyMinMaxSelect() == 0) {
                List<ModifierHeading> modifierHeadings = new ArrayList<>();
                if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                        && setMenuTitleList.get(j).getSetMenuModifierList().size() >= 0) {

                    for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                        if (setMenuTitleList.get(j).getSetMenuModifierList().get(k).isChecked()) {
                            count++;
                            isChildSelected = true;
                            modifierHeadings = setMenuTitleList.get(j).getSetMenuModifierList().get(k).getModifierHeadingList();

                            break;
                        } else {

                            isChildSelected = false;
                        }
                    }
                    if (isChildSelected) {
                        boolean selected = true;
                        if (modifierHeadings.size() != 0) {
                            List<ModifiersValue> modifiersValueList = modifierHeadings.get(0).getModifiersList();
                            for (int i = 0; i < modifiersValueList.size(); i++) {


                                if (modifiersValueList.get(i).getChekced()) {
                                    selected = false;
                                }

                            }
                            if (selected) {
                                Toast.makeText(mContext, "Select Minimum 1", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        mValidationMessage = setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }

                }
            }
        }


        if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

            if (count == setMenuTitleList.size()) {
                if (Utility.networkCheck(mContext)) {

                    String url = GlobalUrl.ADD_CART_SET_MENU_URL;

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = Utility.getReferenceId(mContext);

                    } else {
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO:
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } catch (Exception e) {
                            GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } finally {
                            mCustomerId = "";
                        }
                    }




                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("reference_id", Utility.getReferenceId(mContext));
                    mapData.put("customer_id", mCustomerId);
                    mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                    mapData.put("app_id", GlobalValues.APP_ID);
                    mapData.put("availability_id", CURRENT_AVAILABLITY_ID);
                    mapData.put("product_qty", mProductQuantity);
                    mapData.put("product_id", setMenuProduct.getmProductId());
                    mapData.put("product_name", setMenuProduct.getmProductAliasName());
                    String additionalNotes = notesText1.getText().toString();
                    if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                        mapData.put("product_remarks", additionalNotes);
                    }

                    if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                        mapData.put("product_total_price", String.valueOf(mquanititycost_src));

                        mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));


                    } else {
                        mapData.put("product_total_price", String.valueOf(quantityCost));

                        mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));


                    }

                    mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));
                    mapData.put("product_image", setMenuProduct.getmProductImage());
                    mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
                    mapData.put("product_sku", setMenuProduct.getmProductSku());
                    mapData.put("product_type", setMenuProduct.getmProductType());

                    if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                        mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                    } else {
                        mapData.put("product_modifier_parent_id", "");
                    }

                    mapData.put("menu_set_component", constructSetMenuJson().toString());


                    new AddCartTask(mapData, mProductQuantity).execute(url);

                    mquanititycost_src = 0.0;

                } else {
                    Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
            }

        } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

            if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {
                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.ADD_CART_SET_MENU_URL;

                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            mReferenceId = Utility.getReferenceId(mContext);

                        } else {
                            try {
                                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                                GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                                mReferenceId = GlobalValues.DEVICE_ID;

                            } catch (Exception e) {
                                GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                                mReferenceId = GlobalValues.DEVICE_ID;

                            } finally {
                                mCustomerId = "";
                            }
                        }



                        Map<String, String> mapData = new HashMap<>();
                        mapData.put("reference_id", "");
                        mapData.put("customer_id", mCustomerId);
                        mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                        mapData.put("app_id", GlobalValues.APP_ID);
                        mapData.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        mapData.put("product_qty", mProductQuantity);
                        mapData.put("product_id", setMenuProduct.getmProductId());
                        mapData.put("product_name", setMenuProduct.getmProductAliasName());
                        mapData.put("menu_set_component", constructSetMenuJson().toString());


                        mapData.put("product_total_price", txtModifierPrice.getText().toString().replace("$", ""));

                        try {

                            mapData.put("product_unit_price", String.valueOf(getsetMenuProductPrice()));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        mapData.put("product_image", setMenuProduct.getmProductImage());
                        mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
                        mapData.put("product_sku", setMenuProduct.getmProductSku());
                        mapData.put("product_type", setMenuProduct.getmProductType());

                        if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                            mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                        } else {
                            mapData.put("product_modifier_parent_id", "");

                        }


                        new AddCartTask(mapData, mProductQuantity).execute(url);

                        mquanititycost_src = 0.0;

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    setmenuComponentMethod();
                }
            } else {
                setmenuComponentMethod();
            }

        }


    }

    private void validateSetMenu(List<SetMenuTitle> setMenuTitleList) {
        double product_unit_price = SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus;
        boolean isChildSelected = false;
        int count = 0;

        for (int j = 0; j < setMenuTitleList.size(); j++) {

            isChildSelected = false;
            List<ModifierHeading> modifierHeadings = new ArrayList<>();
            if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

                if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                        && setMenuTitleList.get(j).getSetMenuModifierList().size() >= 0) {

                    for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                        if (setMenuTitleList.get(j).getSetMenuModifierList().get(k).isChecked()) {
                            count++;
                            isChildSelected = true;
                            modifierHeadings = setMenuTitleList.get(j).getSetMenuModifierList().get(k).getModifierHeadingList();
                            break;
                        } else {
                            isChildSelected = false;
                        }
                    }

                    if (isChildSelected) {

                        boolean selected = false;
                        if (modifierHeadings.size() != 0) {

                            List<ModifiersValue> modifiersValueList = modifierHeadings.get(0).getModifiersList();
                            for (int i = 0; i < modifiersValueList.size(); i++) {


                                if (modifiersValueList.get(i).getChekced()) {
                                    selected = false;

                                }

                            }

                            if (selected) {

                                Toast.makeText(mContext, "Please Select Minimum 1 Products!", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    if (!isChildSelected) {
                        mValidationMessage = setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }
                }
            } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                List<ModifiersValue> modifiersValue = new ArrayList<>();
                for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {

                    if (setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() < setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect()) {

                       /* Toast.makeText(mContext, "Please select minimum  " + setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect() + " product for "
                                + setMenuProduct.getSetMenuTitleList().get(i).getmTitleMenuName() + "", Toast.LENGTH_SHORT).show();*/
                        if (setMenuTitleList.get(i).getmTitleMenuName().equalsIgnoreCase("Sugar Level")) {
                            Toast.makeText(mContext, "Please select sugar level", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Please select size and topping", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    } else {

                    }
                    int postion;
                    if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                            && setMenuTitleList.get(j).getSetMenuModifierList().size() > 0) {

                        if (GlobalValues.MODIFIER_NAME.equalsIgnoreCase("Medium")) {
                            postion = 0;
                        } else if (GlobalValues.MODIFIER_NAME.equalsIgnoreCase("Large")) {
                            postion = 1;
                        } else {
                            postion = 0;
                        }

                        if (setMenuTitleList.get(j).getSetMenuModifierList().size() == 1) {
                            postion = 0;
                        }

                        for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                            modifierHeadings = setMenuTitleList.get(j).getSetMenuModifierList().get(postion).getModifierHeadingList();



                            if (modifierHeadings != null && modifierHeadings.size() > 0) {
                                for (int a = 0; a < modifierHeadings.size(); a++) {
                                    modifiersValue = modifierHeadings.get(a).getModifiersValueList();
                                    for (int b = 0; b < modifiersValue.size(); b++) {


                                        if (modifierHeadings.get(a).gettQuantity() < modifierHeadings.get(a).getmModifierMinSelect()) {

                                            /*Toast.makeText(mContext, "Please select minimum  " + modifierHeadings.get(a).getmModifierMinSelect() + " product for "
                                                    + modifierHeadings.get(a).getmModifierHeading(), Toast.LENGTH_SHORT).show();*/

                                            Toast.makeText(mContext, "Please select size and topping", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }
                            } else {
                            }
                        }
                    }
                }
            }
        }

        if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

            if (count == setMenuTitleList.size()) {
                if (Utility.networkCheck(mContext)) {
                    String url = GlobalUrl.ADD_CART_SET_MENU_URL;
                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = Utility.getReferenceId(mContext);
                    } else {
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO:
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } catch (Exception e) {
                            GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } finally {
                            mCustomerId = "";
                        }
                    }

                    Log.v("final modifier", mProductQuantity);

                    Log.v("MProduct prices", "" + SubCategoryActivity.mquanititycost_src);

                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("app_id", GlobalValues.APP_ID);
                    mapData.put("product_name", setMenuProduct.getmProductAliasName());
                    mapData.put("menu_set_component", constructSetMenuJson().toString());
                    mapData.put("product_qty", mProductQuantity);
                    //mapData.put("reference_id", "");
                    mapData.put("customer_id", mCustomerId);
                    mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);

                    mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));

                    mapData.put("product_id", setMenuProduct.getmProductId());
                    mapData.put("allow_cart", "yes");
                    mapData.put("cart_source", "Mobile");

                    String additionalNotes = notesText1.getText().toString();
                    if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                        mapData.put("product_remarks", additionalNotes);
                    }

                    if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                        mapData.put("product_total_price", String.valueOf(SubCategoryActivity.mquanititycost_src));
                        mapData.put("product_unit_price", String.valueOf(product_unit_price));
                    } else {
                        mapData.put("product_total_price", String.valueOf(SubCategoryActivity.quantityCost));
                        mapData.put("product_unit_price", String.valueOf(product_unit_price));
                    }
//dkfasjlkdfjdsajf
                    mapData.put("product_unit_price", String.valueOf(product_unit_price));
                    mapData.put("product_image", setMenuProduct.getmProductImage());
                    mapData.put("price_with_modifier", String.valueOf(SubCategoryActivity.mModifierPrice));
                    mapData.put("product_sku", setMenuProduct.getmProductSku());
                    mapData.put("product_type", setMenuProduct.getmProductType());

                    if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                        mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                    } else {
                        mapData.put("product_modifier_parent_id", "");

                    }

                    new AddCartTask(mapData, mProductQuantity).execute(url);
                    SubCategoryActivity.mquanititycost_src = 0.0;
                } else {
                    Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
                //  Toast.makeText(mContext, "Please Select Minimum 1 Products!", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
            }
        } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

            if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {
                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.ADD_CART_SET_MENU_URL;

                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            mReferenceId = Utility.getReferenceId(mContext);

                        } else {
                            try {
                                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                                GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                                mReferenceId = GlobalValues.DEVICE_ID;

                            } catch (Exception e) {
                                GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                                mReferenceId = GlobalValues.DEVICE_ID;

                            } finally {
                                mCustomerId = "";
                            }
                        }

                        Log.v("final modifier", mProductQuantity);
                        Log.v("MProduct prices", "" + SubCategoryActivity.mquanititycost_src);

                        Map<String, String> mapData = new HashMap<>();
                        mapData.put("reference_id", "");
                        mapData.put("customer_id", mCustomerId);
                        mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                        mapData.put("app_id", GlobalValues.APP_ID);
                        mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                        mapData.put("product_qty", mProductQuantity);
                        mapData.put("product_id", setMenuProduct.getmProductId());
                        mapData.put("product_name", setMenuProduct.getmProductAliasName());
                        mapData.put("menu_set_component", constructSetMenuJson().toString());

                        mapData.put("product_total_price", txtModifierPrice.getText().toString().replace("$", ""));

                        try {
                            mapData.put("product_unit_price", String.valueOf(product_unit_price));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mapData.put("product_image", setMenuProduct.getmProductImage());
                        mapData.put("price_with_modifier", String.valueOf(SubCategoryActivity.mModifierPrice));
                        mapData.put("product_sku", setMenuProduct.getmProductSku());
                        mapData.put("product_type", setMenuProduct.getmProductType());

                        if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                            mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                        } else {
                            mapData.put("product_modifier_parent_id", "");
                        }

                        new AddCartTask(mapData, mProductQuantity).execute(url);

                        SubCategoryActivity.mquanititycost_src = 0.0;

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    setmenuComponentMethod();
                }
            } else {
                setmenuComponentMethod();
            }
        }
    }

    public void setmenuComponentMethod1() {


        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.ADD_CART_SET_MENU_URL;

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = Utility.getReferenceId(mContext);

            } else {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO:
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                    mReferenceId = GlobalValues.DEVICE_ID;

                } catch (Exception e) {
                    GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                    mReferenceId = GlobalValues.DEVICE_ID;

                } finally {
                    mCustomerId = "";
                }
            }

            Log.v("final modifier", mProductQuantity);

            Log.v("MProduct prices", "" + mquanititycost_src);


            Map<String, String> mapData = new HashMap<>();
            mapData.put("reference_id", Utility.getReferenceId(mContext));
            mapData.put("customer_id", mCustomerId);
            mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
            mapData.put("app_id", GlobalValues.APP_ID);
            mapData.put("availability_id", CURRENT_AVAILABLITY_ID);
            mapData.put("product_qty", mProductQuantity);
            mapData.put("product_id", setMenuProduct.getmProductId());
            mapData.put("product_name", setMenuProduct.getmProductAliasName());
            String additionalNotes = notesText1.getText().toString();
            if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                mapData.put("product_remarks", additionalNotes);
            }

              /*  if (setMenuProduct.getmApplyMinMaxSelect() == 1)
                {
                    mapData.put("product_total_price",String.valueOf(mquanititycost_src));

                    mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));


                }
                else
                {*/
            mapData.put("product_total_price", String.valueOf(quantityCost));

            mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));


            // }

            mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));
            mapData.put("product_image", setMenuProduct.getmProductImage());
            mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
            mapData.put("product_sku", setMenuProduct.getmProductSku());
            mapData.put("product_type", setMenuProduct.getmProductType());

            if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
            } else {
                mapData.put("product_modifier_parent_id", "");
            }

            mapData.put("menu_set_component", constructSetMenuJson().toString());


            new AddCartTask(mapData, mProductQuantity).execute(url);

            mquanititycost_src = 0.0;

        } else {
            Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
        }

    }

    private String constructSetMenuJson1() {
        try {

            if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

                if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                    if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

                        JSONArray menuComponentsJSONArray = new JSONArray();
                        List<SetMenuTitle> arrsetMenuComponent = setMenuProduct.getSetMenuTitleList();

                        for (int i = 0; i < arrsetMenuComponent.size(); i++) {
                            JSONObject jsonObject = new JSONObject();


                            try {
                                jsonObject.put("menu_component_id", arrsetMenuComponent.get(i).getmTitleMenuId());
                                jsonObject.put("menu_component_name", arrsetMenuComponent.get(i).getmTitleMenuName());
                                jsonObject.put("min_max_flag", "1");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            JSONArray productArray = new JSONArray();

                            List<SetMenuModifier> arrProduct = arrsetMenuComponent.get(i).getSetMenuModifierList();
                            total_unitprices = 0.0;

                            for (int i1 = 0; i1 < arrProduct.size(); i1++) {
                                JSONObject proobj = new JSONObject();

                                if (Integer.valueOf(arrProduct.get(i1).getTotalQuantity()) > 0) {
                                    try {
                                        proobj.put("product_id", arrProduct.get(i1).getmModifierId());
                                        proobj.put("product_name", arrProduct.get(i1).getmModifierAliasName());
                                        proobj.put("product_sku", arrProduct.get(i1).getmModifierSku());
                                        proobj.put("product_price", arrProduct.get(i1).getmModifierPrice());
                                        proobj.put("product_qty", arrProduct.get(i1).getTotalQuantity());


                                        if (i1 == 0) {
                                            total_unitprices = Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + mSetMenuPrice;


                                        } else if (i1 == 1) {

                                            total_unitprices = Double.parseDouble(arrProduct.get(0).getmModifierPrice()) + Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + mSetMenuPrice;

                                        }


                                        //                        proobj.put("product_meta_title",String.valueOf(arrProduct.get(i1).get(0).getTotalpPrize()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    productArray.put(proobj);
                                }


                            }


                            try {
                                jsonObject.put("product_details", productArray);

                                menuComponentsJSONArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                        return menuComponentsJSONArray.toString();
                    } else {
                        JSONArray menuComponentsJSONArray = new JSONArray();
                        List<SetMenuTitle> setMenuTitleList = setMenuProduct.getSetMenuTitleList();

                        for (int i = 0; i < setMenuTitleList.size(); i++) {  //menuCompontnt size


                            SetMenuTitle setMenuTitle = setMenuTitleList.get(i);

                            JSONObject menuComponentJSON = new JSONObject();

                            menuComponentJSON.put("menu_component_id", setMenuTitle.getmTitleMenuId());
                            menuComponentJSON.put("menu_component_name", setMenuTitle.getmTitleMenuName());

                            JSONArray productJSONArray = new JSONArray();
                            List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                            for (int j = 0; j < setMenuModifierList.size(); j++) {

                                SetMenuModifier setMenuModifier = setMenuModifierList.get(j);

                                if (setMenuModifier.isChecked()) {


                                    JSONObject productJSONObj = new JSONObject();

                                    productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                    productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                                    productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                    productJSONObj.put("product_qty", "1");

                                    if (setMenuModifier.getModifierHeadingList() != null &&
                                            setMenuModifier.getModifierHeadingList().size() > 0) {


                                        JSONArray modifierJSONArray = new JSONArray();

                                        List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();


                                        //modifierModSelectedArrayList      boolean

                                        if (modifierHeadingList.size() > 0) {      //start
                                            for (int k = 0; k < modifierHeadingList.size(); k++) {

                                                ModifierHeading modifierHeading = modifierHeadingList.get(k);

                                                JSONObject modJsonObject = new JSONObject();

                                                modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                                modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                                JSONArray modifValueJSONArray = new JSONArray();
                                                List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                                for (int l = 0; l < modifiersValueList.size(); l++) {


                                                    ModifiersValue modifierValue = modifiersValueList.get(l);

                                                    if (modifierValue.getChekced()) { //if the index is boolean is true

                                                        JSONObject modifValueJSONObj = new JSONObject();
                                                        modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                        modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                        modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                        modifValueJSONObj.put("modifier_value_qty", 1);

                                                        modifValueJSONArray.put(modifValueJSONObj);
                                                    }

                                                }

                                                modJsonObject.put("modifiers_values", modifValueJSONArray);


                                                modifierJSONArray.put(modJsonObject);

                                            }


                                            productJSONObj.put("modifiers", modifierJSONArray);


                                        } else {

                                        }


                                    } else { //No modifier for the product

                                    }

                                    productJSONArray.put(productJSONObj);


                                } else {  //Product is not selected

                                }

                            }

                            menuComponentJSON.put("product_details", productJSONArray);


                            menuComponentsJSONArray.put(menuComponentJSON);

                        }




                        return menuComponentsJSONArray.toString();

                    }
                } else {
                    JSONArray menuComponentsJSONArray = new JSONArray();
                    List<SetMenuTitle> setMenuTitleList = setMenuProduct.getSetMenuTitleList();

                    for (int i = 0; i < setMenuTitleList.size(); i++) {  //menuCompontnt size


                        SetMenuTitle setMenuTitle = setMenuTitleList.get(i);

                        JSONObject menuComponentJSON = new JSONObject();

                        menuComponentJSON.put("menu_component_id", setMenuTitle.getmTitleMenuId());
                        menuComponentJSON.put("menu_component_name", setMenuTitle.getmTitleMenuName());

                        JSONArray productJSONArray = new JSONArray();
                        List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                        for (int j = 0; j < setMenuModifierList.size(); j++) {

                            SetMenuModifier setMenuModifier = setMenuModifierList.get(j);

                            if (setMenuModifier.isChecked()) {



                                JSONObject productJSONObj = new JSONObject();

                                productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                                productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                productJSONObj.put("product_qty", "1");

                                if (setMenuModifier.getModifierHeadingList() != null &&
                                        setMenuModifier.getModifierHeadingList().size() > 0) {


                                    JSONArray modifierJSONArray = new JSONArray();

                                    List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();



                                    //modifierModSelectedArrayList      boolean

                                    if (modifierHeadingList.size() > 0) {      //start
                                        for (int k = 0; k < modifierHeadingList.size(); k++) {

                                            ModifierHeading modifierHeading = modifierHeadingList.get(k);

                                            JSONObject modJsonObject = new JSONObject();

                                            modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                            modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                            JSONArray modifValueJSONArray = new JSONArray();
                                            List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                            for (int l = 0; l < modifiersValueList.size(); l++) {


                                                ModifiersValue modifierValue = modifiersValueList.get(l);

                                                if (modifierValue.getChekced()) { //if the index is boolean is true

                                                    JSONObject modifValueJSONObj = new JSONObject();
                                                    modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                    modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                    modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                    modifValueJSONObj.put("modifier_value_qty", 1);

                                                    modifValueJSONArray.put(modifValueJSONObj);
                                                }

                                            }

                                            modJsonObject.put("modifiers_values", modifValueJSONArray);


                                            modifierJSONArray.put(modJsonObject);

                                        }


                                        productJSONObj.put("modifiers", modifierJSONArray);


                                    } else {

                                    }


                                } else { //No modifier for the product

                                }

                                productJSONArray.put(productJSONObj);


                            } else {  //Product is not selected

                            }

                        }

                        menuComponentJSON.put("product_details", productJSONArray);


                        menuComponentsJSONArray.put(menuComponentJSON);

                    }

                    return menuComponentsJSONArray.toString();

                }

            } else {

                JSONArray menuComponentsJSONArray = new JSONArray();
                List<SetMenuTitle> setMenuTitleList = setMenuProduct.getSetMenuTitleList();

                for (int i = 0; i < setMenuTitleList.size(); i++) {  //menuCompontnt size


                    SetMenuTitle setMenuTitle = setMenuTitleList.get(i);

                    JSONObject menuComponentJSON = new JSONObject();

                    menuComponentJSON.put("menu_component_id", setMenuTitle.getmTitleMenuId());
                    menuComponentJSON.put("menu_component_name", setMenuTitle.getmTitleMenuName());

                    JSONArray productJSONArray = new JSONArray();
                    List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                    for (int j = 0; j < setMenuModifierList.size(); j++) {

                        SetMenuModifier setMenuModifier = setMenuModifierList.get(j);

                        if (setMenuModifier.isChecked()) {

                            JSONObject productJSONObj = new JSONObject();

                            productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                            productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                            productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                            productJSONObj.put("product_qty", "1");

                            if (setMenuModifier.getModifierHeadingList() != null &&
                                    setMenuModifier.getModifierHeadingList().size() > 0) {


                                JSONArray modifierJSONArray = new JSONArray();

                                List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();

                                //modifierModSelectedArrayList      boolean

                                if (modifierHeadingList.size() > 0) {      //start
                                    for (int k = 0; k < modifierHeadingList.size(); k++) {

                                        ModifierHeading modifierHeading = modifierHeadingList.get(k);

                                        JSONObject modJsonObject = new JSONObject();

                                        modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                        modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                        JSONArray modifValueJSONArray = new JSONArray();
                                        List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                        for (int l = 0; l < modifiersValueList.size(); l++) {


                                            ModifiersValue modifierValue = modifiersValueList.get(l);

                                            if (modifierValue.getChekced()) { //if the index is boolean is true

                                                JSONObject modifValueJSONObj = new JSONObject();
                                                modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                modifValueJSONObj.put("modifier_value_qty", 1);

                                                modifValueJSONArray.put(modifValueJSONObj);
                                            }

                                        }

                                        modJsonObject.put("modifiers_values", modifValueJSONArray);


                                        modifierJSONArray.put(modJsonObject);

                                    }


                                    productJSONObj.put("modifiers", modifierJSONArray);



                                } else {

                                }


                            } else { //No modifier for the product

                            }

                            productJSONArray.put(productJSONObj);


                        } else {  //Product is not selected

                        }

                    }

                    menuComponentJSON.put("product_details", productJSONArray);


                    menuComponentsJSONArray.put(menuComponentJSON);

                }

                return menuComponentsJSONArray.toString();


            }


        } catch (JSONException e) {
            e.printStackTrace();

            return "";
        }

    }

    public void setmenuComponentMethod() {

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.ADD_CART_SET_MENU_URL;

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = Utility.getReferenceId(mContext);

            } else {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO:
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                    mReferenceId = GlobalValues.DEVICE_ID;

                } catch (Exception e) {
                    GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                    mReferenceId = GlobalValues.DEVICE_ID;

                } finally {
                    mCustomerId = "";
                }
            }

            Log.v("final modifier", mProductQuantity);

            Log.v("MProduct prices", "" + SubCategoryActivity.mquanititycost_src);


            Map<String, String> mapData = new HashMap<>();
            mapData.put("reference_id", "");
            mapData.put("customer_id", mCustomerId);
            mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
            mapData.put("app_id", GlobalValues.APP_ID);
            mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
            mapData.put("product_qty", mProductQuantity);
            mapData.put("product_id", setMenuProduct.getmProductId());
            mapData.put("product_name", setMenuProduct.getmProductAliasName());
            String additionalNotes = notesText1.getText().toString();
            if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                mapData.put("product_remarks", additionalNotes);
            }

              /*  if (setMenuProduct.getmApplyMinMaxSelect() == 1)
                {
                    mapData.put("product_total_price",String.valueOf(mquanititycost_src));

                    mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));


                }
                else
                {*/
            mapData.put("product_total_price", txtModifierPrice.getText().toString().replace("$", ""));//4.80

            mapData.put("product_unit_price", String.valueOf(SubCategoryActivity.mSetMenuPrice));

            // }
            double product_unit_price = SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus;
            mapData.put("product_unit_price", String.valueOf(product_unit_price)); //4.80
            mapData.put("product_image", setMenuProduct.getmProductImage());
            mapData.put("price_with_modifier", String.valueOf(SubCategoryActivity.mModifierPrice)); //3.80
            mapData.put("product_sku", setMenuProduct.getmProductSku());
            mapData.put("product_type", setMenuProduct.getmProductType());

            if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
            } else {
                mapData.put("product_modifier_parent_id", "");

            }

            mapData.put("menu_set_component", constructSetMenuJson().toString());


            new AddCartTask(mapData, mProductQuantity).execute(url);

            SubCategoryActivity.mquanititycost_src = 0.0;

        } else {
            Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
        }

    }

    private String constructSetMenuJson() {
        //mainObj
        JSONArray menuComponentsJSONArray = new JSONArray();
        List<SetMenuTitle> titleList = setMenuProduct.getSetMenuTitleList();
        for (int i = 0; i < titleList.size(); i++) {

            if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                if (titleList.get(i).getmenu_component_modifier_apply().equalsIgnoreCase("0")) {
                    if (titleList.get(i).getmultipleselection_apply().equalsIgnoreCase("0")) {
                        //modifierName
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                            jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());
                            //jsonObject.put("min_max_flag", "1");

                            //products array
                            JSONArray productArray = new JSONArray();
                            List<SetMenuModifier> arrProduct = titleList.get(i).getSetMenuModifierList();
                            total_unitprices = 0.0;
                            for (int i1 = 0; i1 < arrProduct.size(); i1++) {
                                //single product
                                JSONObject proobj = new JSONObject();
                                if (Integer.parseInt(arrProduct.get(i1).getTotalQuantity()) > 0 || arrProduct.get(i1).getmQuantity() > 0) {
                                    try {
                                        proobj.put("product_id", arrProduct.get(i1).getmModifierId());
                                        proobj.put("product_name", arrProduct.get(i1).getmModifierAliasName());
                                        proobj.put("product_sku", arrProduct.get(i1).getmModifierSku());
                                        proobj.put("product_price", arrProduct.get(i1).getmModifierPrice());
                                        proobj.put("product_qty", arrProduct.get(i1).getmQuantity());
                                        if (i1 == 0) {
                                            total_unitprices = Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + SubCategoryActivity.mSetMenuPrice;
                                        } else if (i1 == 1) {
                                            total_unitprices = Double.parseDouble(arrProduct.get(0).getmModifierPrice()) + Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + SubCategoryActivity.mSetMenuPrice;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    productArray.put(proobj);
                                }
                            }

                            try {
                                jsonObject.put("product_details", productArray);
                                menuComponentsJSONArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException je) {
                            je.printStackTrace();
                        }
                    } else {
                        //modifierName
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                            jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());

                            JSONArray productJSONArray = new JSONArray();
                            List<SetMenuModifier> setMenuModifierList = titleList.get(i).getSetMenuModifierList();
                            for (int j = 0; j < setMenuModifierList.size(); j++) {
                                SetMenuModifier setMenuModifier = setMenuModifierList.get(j);
                                if (setMenuModifier.isChecked()) {
                                    JSONObject productJSONObj = new JSONObject();
                                    productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                    productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));

                                    productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                    productJSONObj.put("product_qty", 1);

                                    if (setMenuModifier.getModifierHeadingList() != null &&
                                            setMenuModifier.getModifierHeadingList().size() > 0) {
                                        JSONArray modifierJSONArray = new JSONArray();
                                        List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();
                                        if (modifierHeadingList.size() > 0) {
                                            for (int k = 0; k < modifierHeadingList.size(); k++) {
                                                ModifierHeading modifierHeading = modifierHeadingList.get(k);
                                                JSONObject modJsonObject = new JSONObject();
                                                modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                                modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                                JSONArray modifValueJSONArray = new JSONArray();
                                                List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                                for (int l = 0; l < modifiersValueList.size(); l++) {
                                                    ModifiersValue modifierValue = modifiersValueList.get(l);
                                                    if (modifierValue.getChekced()) {
                                                        JSONObject modifValueJSONObj = new JSONObject();

                                                        if (modifierValue.getmSubModifierTotal() > 0) {
                                                            modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                            modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                            modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                            modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());
                                                            modifValueJSONArray.put(modifValueJSONObj);
                                                        }
                                                    }
                                                }

                                                modJsonObject.put("modifiers_values", modifValueJSONArray);
                                                modifierJSONArray.put(modJsonObject);
                                            }
                                            productJSONObj.put("modifiers", modifierJSONArray);
                                        } else {

                                        }

                                    } else {

                                    }
                                    productJSONArray.put(productJSONObj);
                                } else {

                                }
                            }
                            jsonObject.put("product_details", productJSONArray);
                            menuComponentsJSONArray.put(jsonObject);
                        } catch (JSONException je) {
                            je.printStackTrace();
                        }
                    }
                } else {
                    //modifierName
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                        jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());

                        JSONArray productJSONArray = new JSONArray();
                        List<SetMenuModifier> setMenuModifierList = titleList.get(i).getSetMenuModifierList();
                        for (int j = 0; j < setMenuModifierList.size(); j++) {
                            SetMenuModifier setMenuModifier = setMenuModifierList.get(j);
                            if (setMenuModifier.isChecked()) {
                                JSONObject productJSONObj = new JSONObject();
                                productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                                productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                productJSONObj.put("product_qty", 1);

                                if (setMenuModifier.getModifierHeadingList() != null &&
                                        setMenuModifier.getModifierHeadingList().size() > 0) {
                                    JSONArray modifierJSONArray = new JSONArray();
                                    List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();
                                    if (modifierHeadingList.size() > 0) {
                                        for (int k = 0; k < modifierHeadingList.size(); k++) {
                                            ModifierHeading modifierHeading = modifierHeadingList.get(k);
                                            JSONObject modJsonObject = new JSONObject();
                                            modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                            modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                            JSONArray modifValueJSONArray = new JSONArray();
                                            List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                            for (int l = 0; l < modifiersValueList.size(); l++) {
                                                ModifiersValue modifierValue = modifiersValueList.get(l);
                                                if (modifierValue.getChekced()) {
                                                    JSONObject modifValueJSONObj = new JSONObject();

                                                    if (modifierValue.getmSubModifierTotal() > 0) {
                                                        modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                        modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                        modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                        modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());
                                                        modifValueJSONArray.put(modifValueJSONObj);
                                                    }
                                                }
                                            }

                                            modJsonObject.put("modifiers_values", modifValueJSONArray);
                                            modifierJSONArray.put(modJsonObject);
                                        }
                                        productJSONObj.put("modifiers", modifierJSONArray);
                                    } else {

                                    }

                                } else {

                                }
                                productJSONArray.put(productJSONObj);
                            } else {

                            }
                        }
                        jsonObject.put("product_details", productJSONArray);
                        menuComponentsJSONArray.put(jsonObject);
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }
            } else {
                //modifierName
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                    jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());

                    JSONArray productJSONArray = new JSONArray();
                    List<SetMenuModifier> setMenuModifierList = titleList.get(i).getSetMenuModifierList();
                    for (int j = 0; j < setMenuModifierList.size(); j++) {
                        SetMenuModifier setMenuModifier = setMenuModifierList.get(j);
                        if (setMenuModifier.isChecked()) {
                            JSONObject productJSONObj = new JSONObject();
                            productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                            productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                            productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                            productJSONObj.put("product_qty", 1);

                            if (setMenuModifier.getModifierHeadingList() != null &&
                                    setMenuModifier.getModifierHeadingList().size() > 0) {
                                JSONArray modifierJSONArray = new JSONArray();
                                List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();
                                if (modifierHeadingList.size() > 0) {
                                    for (int k = 0; k < modifierHeadingList.size(); k++) {
                                        ModifierHeading modifierHeading = modifierHeadingList.get(k);
                                        JSONObject modJsonObject = new JSONObject();
                                        modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                        modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                        JSONArray modifValueJSONArray = new JSONArray();
                                        List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                        for (int l = 0; l < modifiersValueList.size(); l++) {
                                            ModifiersValue modifierValue = modifiersValueList.get(l);
                                            if (modifierValue.getChekced()) {
                                                JSONObject modifValueJSONObj = new JSONObject();

                                                if (modifierValue.getmSubModifierTotal() > 0) {
                                                    modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                    modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                    modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                    modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());
                                                    modifValueJSONArray.put(modifValueJSONObj);
                                                }
                                            }
                                        }

                                        modJsonObject.put("modifiers_values", modifValueJSONArray);
                                        modifierJSONArray.put(modJsonObject);
                                    }
                                    productJSONObj.put("modifiers", modifierJSONArray);
                                } else {

                                }

                            } else {

                            }
                            productJSONArray.put(productJSONObj);
                        } else {

                        }
                    }
                    jsonObject.put("product_details", productJSONArray);
                    menuComponentsJSONArray.put(jsonObject);
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        }

        return menuComponentsJSONArray.toString();
    }


  /*  public JSONArray constructModifierJson() {

        JSONArray modifierJSONArray = new JSONArray();

        if (modifierProduct.getModifiersList() != null) {



            try {

                List<ModifierHeading> modifiers = modifierProduct.getModifiersList();

                //modifierModSelectedArrayList      boolean



                for (int i = 0; i < modifiers.size(); i++) {

                    ModifierHeading modifierType = modifiers.get(i);

                    JSONObject jsonObject = new JSONObject();


                    jsonObject.put("modifier_name", modifierType.getmModifierHeading());
                    jsonObject.put("modifier_id", modifierType.getmModifierHeadingId());

                    JSONArray modifValueJSONArray = new JSONArray();

                    List<ModifiersValue> modifierValues = modifierType.getModifiersList();

                    for (int j = 0; j < modifierValues.size(); j++) {

                        Boolean isSelected = modifierValues.get(j).getChekced();

                        if (isSelected) { //if the index is boolean is true
                            ModifiersValue modifierValue = modifierValues.get(j);

                            JSONObject modifValueJSONObj = new JSONObject();
                            modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                            modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                            modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                            modifValueJSONObj.put("modifier_value_qty", 1);

                            modifValueJSONArray.put(modifValueJSONObj);
                        }
                    }

                    jsonObject.put("modifiers_values", modifValueJSONArray);


                    modifierJSONArray.put(jsonObject);
                }

                //


                //     formBodyBuilder.add("menu_set_component", "");//null


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return modifierJSONArray;

    }*/

    private class AddCartTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> cartparams;
        private String mQuantity;

        public AddCartTask(Map<String, String> cartparams, String quantity) {
            this.cartparams = cartparams;
            this.mQuantity = quantity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {

            System.out.print("Add to cart modifier");
            Log.v("service", params[0] + "\n" + cartparams.toString());

            String response = WebserviceAssessor.postRequest(mContext, params[0], cartparams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {
                Log.v("add cart reponse", s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    if (Integer.parseInt(mQuantity) > 0) {

                        int value = 0;
                        try {

                            value = Integer.valueOf(Integer.valueOf(mQuantity)) + databaseHandler.getQuantity(mProductId);
                            Log.v("updating database", value + "");
                            databaseHandler.updateQty(mProductId, String.valueOf(value));
                            onrefreshdtata();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    } else {
                        databaseHandler.insertProductData(mProductId, mQuantity);
                        onrefreshdtata();

                    }


                    JSONObject jsonContent = jsonObject.getJSONObject("contents");

                    JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                            jsonCartDetails.getString("cart_total_items"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());

                    Log.v("read from memory", Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE));

                    // ProductListActivity.isInvalidated = true;
//                    ((ProductListActivity) mContext).invalidateOptionsMenu();

                    //       Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    DeleteIndividualproduction(CurrentPosition);


                } else {

                    ///   Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                progressDialog.dismiss();
                if (dialog != null) {
                    dialog.dismiss();
                }
                // finish();
            }


        }
    }

    /// After Updates Carts Screen

    private void DeleteIndividualproduction(int position) {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";

        } else {
            mCustomerId = "";
            mReferenceId = Utility.getReferenceId(mContext);
        }

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.DELETE_SINGLE_CART_URL;

            Map<String, String> params = new HashMap<String, String>();
            params.put("app_id", GlobalValues.APP_ID);
            params.put("cart_item_id", cartList.get(position).getmCartItemId());
            params.put("customer_id", mCustomerId);
            params.put("reference_id", "");

            new DeleteCartItem(params, cartList.get(position).getmProductId()).execute(url);

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }


    }

    public void onrefreshdtata() {
//        try {
//            if (databaseHandler.getAllTotalData()!=null) {
//
//                ArrayList<Cart> arrCart=
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        databaseHandler = DatabaseHandler.getInstance(mContext);


    }

    public boolean indexExists(final List list, final int index) {
        return index >= 0 && index < list.size();
    }

    private boolean checkIfIdExist(String productId, List<SetMenuModifier> cartDetail) {


        for (int i = 0; i < cartDetail.size(); i++) {
            if (cartDetail.get(i).getmModifierId().equalsIgnoreCase(productId)) {
                posModifier = i;
                return true;
            }
        }


        return false;

    }

    private class DeleteCartItem extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> deleteParams;
        private String mProductId = "";

        public DeleteCartItem(Map<String, String> deleteParams, String id) {
            this.deleteParams = deleteParams;
            mProductId = id;
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

            Log.v("delete single cart", params[0]);

            String response = WebserviceAssessor.postRequest(mContext, params[0], deleteParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.v("delete cart response", s);


                JSONObject jsonObject = new JSONObject(s);

/*
                Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
*/

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    deleteCurrentQuantity(mProductId);

                    if (!jsonObject.isNull("contents")) {

                        JSONObject jsonResult = jsonObject.getJSONObject("contents");


                        JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");

/*

                        if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removePromotion();
                        }


                        if (txtRedeemApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removeRewardPoints();
                        }
*/

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, jsoncartDetails.getString("cart_total_items"));
                        SubCategoryActivity.cart_sub_total = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));
                        txtSubTotalSymbol.setVisibility(View.VISIBLE);

                        r_sub_total = jsoncartDetails.getString("cart_sub_total");


                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
                            if (foodVoucher) {
                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                            }
                            /*setCustomProgress();


                            double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery"))
                                    - Double.valueOf(r_sub_total);

                            if (d_progress_limit > 0) {

                                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");


                                // txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                             *//*   txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                                txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
*//*
                                //progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;
                                } else {


                                    GlobalValues.GstChargers = Utility.readFromSharedPreference(mContext, GlobalValues.GstCharger);

                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;

                                }


                            } else {


                                GlobalValues.DELEIVERY_CHARGES = "0.00";
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
                                //  txtDeliveryCharge.setText("$0.00");
                             *//*   txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                                txtFreeDelivery.setText("FREE delivery!");
*//*
//                            progressBar.setProgress(1000);

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                            Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));

                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;

                                    // txtGSTLabel.setText("GST ("+"7"+"%)" );
                                    // txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));


                                    // Toast.makeText(mContext,"GST Value",Toast.LENGTH_SHORT).show();
                                } else {

                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;


                                }


                            }

//                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                            Log.v("Delivery charge", GlobalValues.COMMON_DELIVERY_CHARGE + "");*/

                        } else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            GlobalValues.DELEIVERY_CHARGES = "0.00";
                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));

                        }


//                        subtotal_value = jsoncartDetails.getString("cart_sub_total");

                     /*   if (Double.parseDouble(outletZoneJson.optString("zone_free_delivery")) > 0) {

                            progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
                            progressBar.setSecondaryProgress((int) Double.parseDouble(
                                    outletZoneJson.optString("zone_free_delivery")));
                        } else {

                            progressBar.setProgress(1000);
                            progressBar.setSecondaryProgress(1000);
                        }
*/

                      /*  if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

//                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }*/
                        // txtGST.setText("$" + String.format("%.2f", mGST));
                        InclusiveGst(mGrandTotal);
                        txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
//                        txtTotal.setText(String.format("%.2f", mGrandTotal));
                        txtTotalSymbol.setVisibility(View.VISIBLE);

                        JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());

                        Log.v("read from memory", Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE));

//                        Remove applied coupon or reward points


                        //  setCartAdapter(jsonCartItem);
                    } else {

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, "");

                        Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT, "");

                        invalidateOptionsMenu();

                        try {
                            databaseHandler.deleteAllCartQuantity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                } else {

                }


            } catch (Exception e) {
                e.printStackTrace();

            }
            if (progressDialog.isShowing()) {

                progressDialog.dismiss();
            }
            Intent i = new Intent(CartActivity.this, CartActivity.class);
            mContext.startActivity(i);
            finish();
        }
    }


    private class SetMenuProductDetailsTaskNew extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<SetMenuTitle> setMenuTitleList;
        private List<SetMenuModifier> setMenuModifierList;
        private List<ModifierHeading> modifierHeadingList;
        private List<ModifiersValue> modifiersValueList;
        private SetMenuTitle setMenuTitle;
        private SetMenuModifier setMenuModifier;
        private ModifierHeading modifierHeading;
        private ModifiersValue modifiersValue;

        private String mProductId = "", mQuantity = "1";

        public SetMenuProductDetailsTaskNew(String id, String quantity) {
            mProductId = id;
            mQuantity = quantity;
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

            Log.v("Product Details Service", params[0] + "\n");

            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            double price = 0.0;
            Double totalVale = 0.00;
            mProductFavPrimaryId = "null";
            try {

                Log.v("setmenu detail resposne", s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    mBasePath = jsonCommon.optString("image_source");
                    galleryBasePath = jsonCommon.optString("product_gallery_image_source");

                    JSONArray jsonResultArray = jsonObject.getJSONArray("result_set");

                    if (jsonResultArray.length() > 0) {
                        for (int r = 0; r < jsonResultArray.length(); r++) {

                            JSONObject jsonResult = jsonResultArray.getJSONObject(r);

                            setMenuProduct = new SetMenuProduct();

                            setMenuProduct.setmProductId(jsonResult.getString("product_id"));
                            setMenuProduct.setmProductName(jsonResult.getString("product_name"));
                            setMenuProduct.setmProductAliasName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmproduct_apply_minmax_select(jsonResult.getString("product_apply_minmax_select"));
                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            setMenuProduct.setmfav_product_primary_id(jsonResult.getString("fav_product_primary_id"));
                            setMenuProduct.setmproduct_primary_id(jsonResult.getString("product_primary_id"));
                            mProductFavPrimaryId = jsonResult.getString("fav_product_primary_id");
                            String subImageUrl = jsonResult.optString("product_thumbnail");
                            if (subImageUrl != null && subImageUrl.length() > 0) {
                                setMenuProduct.setmProductImage(mBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            }
                            setMenuProduct.setmProductStatus(jsonResult.getString("product_status"));
                            setMenuProduct.setmProductPrice(jsonResult.getString("product_price"));
                            setMenuProduct.setmApplyMinMaxSelect(Integer.parseInt(jsonResult.getString("product_apply_minmax_select")));

                            try {

                                mSetMenuBasePrice = Double.valueOf(jsonResult.getString("product_price"));
                            } catch (Exception e) {
                                mSetMenuBasePrice = 0.0;
                            }

                            mSetMenuPrice = mSetMenuBasePrice;

                            quantityCost = mSetMenuBasePrice;
                            quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);



                            String largeImage = "";
                            JSONArray image_gallery = jsonResult.getJSONArray("image_gallery");
                            for (int i = 0; i < image_gallery.length(); i++) {
                                JSONObject jsonObject1 = image_gallery.getJSONObject(i);
                                largeImage = jsonObject1.getString("pro_gallery_image");
                                if (largeImage != null && largeImage.length() > 0) {
                                    largeImage = galleryBasePath + "/" + largeImage;
                                    setMenuProduct.setmProductLargeImage(largeImage);
                                }
                            }

                            JSONArray setmenuArray = jsonResult.getJSONArray("set_menu_component");

                            setMenuTitleList = new ArrayList<>();

                            if (setmenuArray.length() > 0) {

                                for (int t = 0; t < setmenuArray.length(); t++) {
                                    JSONObject jsonSetmenu = setmenuArray.getJSONObject(t);

                                    setMenuTitle = new SetMenuTitle();

                                    setMenuTitle.setmTitleMenuId(jsonSetmenu.optString("menu_component_id"));
                                    setMenuTitle.setmTitleMenuName(jsonSetmenu.optString("menu_component_name"));
                                    setMenuTitle.setmultipleselection_apply(jsonSetmenu.optString("menu_component_multipleselection_apply"));
                                    setMenuTitle.setmenu_component_modifier_apply(jsonSetmenu.optString("menu_component_modifier_apply"));
                                    GlobalValues.MULTIPLESLECTIONAPPLY = jsonSetmenu.optString("menu_component_multipleselection_apply");
                                    GlobalValues.MODIFIERAPPLY = jsonSetmenu.optString("menu_component_modifier_apply");
                                    setMenuTitle.setmAppliedPrice(jsonSetmenu.optString("menu_component_apply_price"));
                                    setMenuTitle.setmMinSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_min_select")));
                                    setMenuTitle.setmMaxSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_max_select")));
                                    setMenuTitle.setmDefaultSelectId(jsonSetmenu.optString("menu_component_default_select"));

                                    JSONArray jsonProductArray = jsonSetmenu.getJSONArray("product_details");


                                    setMenuModifierList = new ArrayList<>();


                                    if (jsonProductArray.length() > 0) {

                                        for (int p = 0; p < jsonProductArray.length(); p++) {

                                            JSONArray jsonArray = jsonProductArray.getJSONArray(p);

                                            if (jsonArray.length() > 0) {
                                                for (int p1 = 0; p1 < jsonArray.length(); p1++) {

                                                    JSONObject object = jsonArray.getJSONObject(p1);

                                                    setMenuModifier = new SetMenuModifier();

                                                    setMenuModifier.setmModifierId(object.optString("product_id"));
                                                    setMenuModifier.setmModifierName(object.optString("product_name"));
                                                    setMenuModifier.setmModifierAliasName(object.optString("product_alias"));
                                                    setMenuModifier.setmMaxSelect(object.optString("product_bagel_max_select"));
                                                    setMenuModifier.setmMinSelect(object.optString("product_bagel_min_select"));
                                                    setMenuModifier.setmModifierPrice(object.optString("product_price"));
                                                    setMenuModifier.setmModifierSku(object.optString("product_sku"));
                                                    setMenuModifier.setmModifierSlug(object.optString("product_slug"));


                                                    if (setMenuIdexist(jsonSetmenu.optString("menu_component_id"), productCartDetails.getSetMenuTitleList())) {

                                                        if (checkIfIdExist(object.optString("product_id"), productCartDetails.getSetMenuTitleList().get(posSetMenu).getSetMenuModifierList())) {
                                                            setMenuModifier.setTotalQuantity(String.valueOf(productCartDetails.getSetMenuTitleList().get(posSetMenu).getSetMenuModifierList().get(posModifier).getmQuantity()));
                                                        }
                                                    }

                                                    try {

                                                        price = Double.valueOf(object.optString("product_price"));
                                                        price = price * Double.parseDouble(setMenuModifier.getTotalQuantity());
                                                    } catch (NumberFormatException e) {
                                                        price = 0.0;
                                                    } catch (Exception e1) {
                                                        price = 0.0;
                                                    }



                                                    mSetMenuPrice = mSetMenuPrice + price;
                                                    quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);

                                                    modifierHeadingList = new ArrayList<>();

                                                    Object modifierObject = object.get("modifiers");

                                                    if (modifierObject instanceof JSONArray) {


                                                        JSONArray jsonModifiersArray = object.getJSONArray("modifiers");


                                                        if (jsonModifiersArray != null && jsonModifiersArray.length() > 0) {

                                                            setMenuModifier.setHasModifier(true);

                                                            for (int m = 0; m < jsonModifiersArray.length(); m++) {

                                                                JSONObject jsonModifier = jsonModifiersArray.getJSONObject(m);

                                                                modifierHeading = new ModifierHeading();

                                                                modifierHeading.setmModifierHeading(jsonModifier.getString("pro_modifier_name"));
                                                                modifierHeading.setmModifierHeadingId(jsonModifier.getString("pro_modifier_id"));
                                                                modifierHeading.setmModifierMinSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_min_select")));
                                                                modifierHeading.setmModifierMaxSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_max_select")));
                                                                modifierHeading.setmMaxSelected(0);

                                                                JSONArray modifierValueArray = jsonModifier.getJSONArray("modifiers_values");

                                                                modifiersValueList = new ArrayList<>();

                                                                if (modifierValueArray.length() > 0) {

                                                                    for (int v = 0; v < modifierValueArray.length(); v++) {

                                                                        JSONObject jsonModifiervalue = modifierValueArray.getJSONObject(v);

                                                                        modifiersValue = new ModifiersValue();

                                                                        modifiersValue.setmModifierId(jsonModifiervalue.getString("pro_modifier_value_id"));
                                                                        modifiersValue.setmModifierValuePrice(jsonModifiervalue.getString("pro_modifier_value_price"));
                                                                        modifiersValue.setmModifierName(jsonModifiervalue.getString("pro_modifier_value_name"));
                                                                        modifiersValue.setmModifierDefault(jsonModifiervalue.getString("pro_modifier_value_is_default"));

                                                                        if (jsonModifiervalue.getString("pro_modifier_value_is_default").
                                                                                equalsIgnoreCase("yes")) {

                                                                            modifiersValue.setChekced(true);
                                                                            modifierHeading.setmMaxSelected(1);

                                                                            /*adding modifier value prices with set menu base price*/

                                                                            try {
                                                                                price = Double.valueOf(jsonModifiervalue.optString("pro_modifier_value_price"));
                                                                            } catch (NumberFormatException e) {
                                                                                price = 0.0;
                                                                            } catch (Exception e1) {
                                                                                price = 0.0;
                                                                            }


                                                                            mSetMenuPrice = mSetMenuBasePrice + price;
                                                                            quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);
                                                                            // quantityCost = mSetMenuPrice;


                                                                        } else {

                                                                            modifiersValue.setChekced(false);
                                                                            modifierHeading.setmMaxSelected(0);

                                                                        }

                                                                        modifiersValueList.add(modifiersValue);

                                                                    }

                                                                }

                                                                modifierHeading.setModifiersList(modifiersValueList);
                                                                modifierHeadingList.add(modifierHeading);

                                                                Log.v("list sizez", modifiersValueList.size() + "\n" + modifierHeadingList.size());
                                                            }

                                                        } else {
                                                            setMenuModifier.setHasModifier(false);
                                                        }

                                                    } else {
                                                        Log.v("is object", "false");
                                                        setMenuModifier.setHasModifier(false);
                                                    }


                                                    /*add set menu price for default selected products if products does
                                                    not have modifiers*/

                                                    if (setMenuTitle.getmDefaultSelectId().equals(object.optString("product_id"))) {
                                                        setMenuModifier.setChecked(true);

                                                        if (modifierHeadingList.size() <= 0) {
                                                            try {
                                                                price = Double.valueOf(object.optString("product_price"));
                                                            } catch (NumberFormatException e) {
                                                                price = 0.0;
                                                            } catch (Exception e1) {
                                                                price = 0.0;
                                                            }

                                                            mSetMenuPrice = mSetMenuPrice + price;
                                                            quantityCost = mSetMenuPrice * Double.valueOf(mQuantity);
                                                            //quantityCost = mSetMenuPrice;
                                                        }

                                                    } else {
                                                        setMenuModifier.setChecked(false);
                                                    }


                                                    setMenuModifier.setModifierHeadingList(modifierHeadingList);
                                                    setMenuModifierList.add(setMenuModifier);

                                                }
                                            }

                                        }

                                    } else {

                                    }

                                    setMenuTitle.setSetMenuModifierList(setMenuModifierList);
                                    setMenuTitleList.add(setMenuTitle);

                                }


                            } else {

                            }

                            setMenuProduct.setSetMenuTitleList(setMenuTitleList);

                        }


                        for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
                            for (int i1 = 0; i1 < setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().size(); i1++) {


                                int tQuantity = setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() + Integer.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalQuantity());
                                setMenuProduct.getSetMenuTitleList().get(i).settQuantity(tQuantity);


                               /* if (Integer.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalQuantity())>0)

                                {

                                     Double finVal=  Integer.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalQuantity())*Double.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getmModifierPrice());

                                     totalVale=totalVale+finVal;
                                }
*/


//                                Toast.makeText(mContext, ""+setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalQuantity(), Toast.LENGTH_SHORT).show();
                            }
                        }
//TODO MODIFICATION NOW
                        setMenuproductDetailsDialog1(mContext, mProductId, mQuantity);

                    } else {


                    }


                } else {

                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();

            }


        }
    }


    public void makeAddTotalCalculation(String productPrice) {

        double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);

        Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) + valu;

        SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModi.setText(cs);
    }

    public void makeSubstTotalCalculation(String productPrice) {
        double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);
        Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) - valu;

        SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModi.setText(cs);
    }

    private Double getsetMenuProductPrice() throws Exception {

        double setPrice = 0.00;

        for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
            for (int i1 = 0; i1 < setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().size(); i1++) {

                Double qPrice = Double.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getmModifierPrice()) * Integer.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalQuantity());

                setPrice = setPrice + qPrice;
            }
        }
        return mSearchProuductprise + setPrice;

    }


    private boolean setMenuIdexist(String menuComponentId, List<SetMenuTitle> setMenuTitleList) {

        for (int i = 0; i < setMenuTitleList.size(); i++) {
            if (menuComponentId.equalsIgnoreCase(setMenuTitleList.get(i).getmTitleMenuId())) {
                posSetMenu = i;
//                Toast.makeText(mContext, ""+i, Toast.LENGTH_SHORT).show();
                return true;
            }
        }
        return false;

    }
/*
    private void openDialogbox()
    {
        *//*final AlertDialog.Builder builder;

        builder = new AlertDialog.Builder(this);
        //Uncomment the below code to Set the message and title from the strings.xml file
         setTitle("Spize");

        //Setting message manually and performing action on button click
        builder.setMessage("Minimum Qty "+ Utility.readFromSharedPreference(mContext,GlobalValues.MinimumQuality) + " allowed in shopping cart")
                .setCancelable(false)
                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.show();*//*


        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_warning_dialog);
        dialog.show();

        TextView txtChangeAddress = (TextView) dialog.findViewById(R.id.txtChangeAddress);
        TextView txtWarning = (TextView) dialog.findViewById(R.id.txtWarning);
        final TextView txtComebackAgain = (TextView) dialog.findViewById(R.id.txtComebackAgain);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);

        txtComebackAgain.setVisibility(View.GONE);



        txtWarning.setText("Minimum Qty "+ Utility.readFromSharedPreference(mContext,GlobalValues.MinimumQuality) +" allowed in shopping cart");

        txtChangeAddress.setText("Ok");


        txtChangeAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();

            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


    }*/

    public void setTime1() {

        timeList = new ArrayList<>();
        slotTimeArrayList = new ArrayList<>();

        Log.v("cartfoo", "tat time" + mTatTime);


        try {
            currentDate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
            //TODO CHANGES
            selecteddate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
        } catch (Exception e) {
            e.printStackTrace();
        }

        slotTimeArrayList = getSelectedDayTimeSlot(mselectedDay);
        try {
            for (int i = 0; i < slotTimeArrayList.size(); i++) {
                int count = 0;
                is_break = false;
                String start_time = slotTimeArrayList.get(i).getmStartTime();
                String end_time = slotTimeArrayList.get(i).getmEndTime();

                Date dstartTime = timeformat.parse(start_time);

                Date dendTime = null;
                if (mTatTime != 0) {
                    dendTime = setTatEndTime(timeformat.parse(end_time));
                } else {

                    dendTime = timeformat.parse(end_time);
                }

                int loopcount = 0;
                loop:
                while (dstartTime.before(dendTime) || dstartTime.equals(dendTime)) {
                    loopcount = loopcount + 1;
                    if (loopcount >= 100) {
                        break loop;
                    }
                    if (selecteddate.equals(currentDate)) {

                        if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dstartTime)) {
                            if (count == 0 && mTatTime != 0) {
                                start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                                count++;
                            } else {
                                timeList.add(start_time);
                            }
                        }
                    } else {
                        if (count == 0 && mTatTime != 0) {
                            start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                            count++;
                        } else {
                            timeList.add(start_time);
                        }
                    }

                    if (is_break) {
                        if (timeList.size() == 1) {
                            timeList.remove(0);
                            timeList.add(start_time);
                        }
                        break;
                    }

                    dstartTime = timeformat.parse(start_time);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dstartTime);
                    cal.add(Calendar.MINUTE, intervaltime);
                    start_time = timeformat.format(cal.getTime());
                    dstartTime = timeformat.parse(start_time);


                    Date stst_static = timeformat.parse("00:00");
                    if (dstartTime.equals(stst_static)) {
//                            timeList.add(start_time);

                        break loop;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (timeList.size() > 0) {
            if (timeList.size() > 0) {
                //TODO CHANGES


                final String dateString = timeList.get(0);
                final long millisToAdd = 10_800_000; //three hours


                DateFormat format = new SimpleDateFormat("HH:mm");

                Date d = null;
                try {
                    d = format.parse(dateString);

                    d.setTime(d.getTime() + millisToAdd);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                SimpleDateFormat localDateFormat = new SimpleDateFormat("HH:mm");
                String time = localDateFormat.format(d);


                GlobalValues.DELIVERY_TIME = timeList.get(0);
                if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

                    Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE, GlobalValues.DELIVERY_DATE);
                    Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME, GlobalValues.DELIVERY_TIME);
                    setDate(GlobalValues.DELIVERY_DATE);
                    setTime(GlobalValues.DELIVERY_TIME);

                }
                // txtDeliveryTime.setText(timeList.get(0));
                isSelectTime = true;
            }
           /* LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            timeRecyclerView.setLayoutManager(layoutManager);
            TimeAdapter timeAdapter = new TimeAdapter(mContext, timeList);
            timeRecyclerView.setAdapter(timeAdapter);




            timeAdapter.SetOnItemClickListioner(new TimeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    try {
                        Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
                        Date dCutOffTime;

                        if (selecteddate.equals(currentDate)) {

                            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF).length() > 0) {
                                cutOffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));


                            } else {
                                cutOffTime = 0;
                            }

                            if (cutOffTime > 0) {

                                if (!String.valueOf(cutOffTime).contains(":")) {
                                    dCutOffTime = timeformat.parse(cutOffTime + ":00");

                                } else {
                                    dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                                }

                                if (dCutOffTime.before(dCurrentTime)) {
                                    Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                                } else {
                                    txtDeliveryTime.setText(timeList.get(position));
                                    layoutCustomTime.setVisibility(View.GONE);
                                    is_time_shown = false;
                                    isSelectTime = true;
                                }
                            } else {
                                txtDeliveryTime.setText(timeList.get(position));
                                layoutCustomTime.setVisibility(View.GONE);
                                is_time_shown = false;
                                isSelectTime = true;
                            }
                        } else {
                            txtDeliveryTime.setText(timeList.get(position));
                            layoutCustomTime.setVisibility(View.GONE);
                            isSelectTime = true;
                            is_time_shown = false;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                }
            });*/

            // txtDate.setText(todayString);

        } else {


            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setGregorianChange(Calendar.getInstance().getTime());
            Date date1 = gregorianCalendar.getGregorianChange();
            GregorianCalendar calendar1 = new GregorianCalendar();
            calendar1.set(Calendar.DATE, date1.getDate());

            calendar1.add(Calendar.DATE, 1);


            Date date = calendar1.getTime();
            todayString = dateformat.format(date);


            GlobalValues.DELIVERY_DATE = todayString;
            setDate(GlobalValues.DELIVERY_DATE);
            txtDeliveryDate.setText(todayString);


            //isDateDisplay = true;

            //TODO

            // setTime();


            if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

                Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE, GlobalValues.DELIVERY_DATE);
                Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME, GlobalValues.DELIVERY_TIME);
                setDate(GlobalValues.DELIVERY_DATE);
                setTime(GlobalValues.DELIVERY_TIME);

            }
            setTime1();

            Toast.makeText(mContext, "Slot not available for this date", Toast.LENGTH_SHORT).show();
        }
    }

    private void addFavouriteMethod(String productId) {



        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";

        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                mReferenceId = GlobalValues.DEVICE_ID;

            } catch (SecurityException e) {
                GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                mReferenceId = GlobalValues.DEVICE_ID;

            } finally {
                {
                    mCustomerId = "";
                }
            }
        }

        if (favouriteText.getText().toString().equalsIgnoreCase("Remove from favourite")) {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "No");
            param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);




            new FavouritesAddTask(param).execute(url);

            StatusFav = "0";

        } else {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "Yes");
            param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);



            new FavouritesAddTask(param).execute(url);

            StatusFav = "1";

        }

    }

    private class FavouritesAddTask extends AsyncTask<String, Void, String> {

        private Map<String, String> resetParams;
        private ProgressDialog progressDialog;

        public FavouritesAddTask(Map<String, String> param) {
            resetParams = param;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            Log.v("reset service", params[0] + "\n" + resetParams.toString());

            String response = WebserviceAssessor.postRequest(mContext, params[0], resetParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.v("Favourites response", s);

                JSONObject jsonObject = new JSONObject(s);


                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {


                    if (StatusFav.equalsIgnoreCase("1")) {
                        favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.remove_favourite_background));

                        favouriteText.setText("Remove from favourite");
                        Toast.makeText(mContext, "Added successfully", Toast.LENGTH_SHORT).show();


                    } else {
                        favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

                        favouriteText.setText("Add to favourite");

                        Toast.makeText(mContext, "Removed successfully", Toast.LENGTH_SHORT).show();


                    }


                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

            }


        }
    }

    private void setDeliveryTime() {
        timeList = new ArrayList<>();
        slotTimeArrayList = new ArrayList<>();

        Log.v("cart foo", "tat time" + mTatTime);

//        mTatTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.TAT_TIME));


        /*if (outletList.size() > 0) {
            if (!outletList.get(0).getOutlet_delivery_tat().isEmpty()) {
                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_tat());
            } else if (!outletList.get(0).getOutlet_delivery_timing().isEmpty()) {
                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_timing());
            }
        }*/

        try {
            currentDate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
            if (oncreateTimeOnly2) {
                oncreateTimeOnly2 = false;
//                selecteddate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
                currentDate = dateformat.parse(dateformat.format(getMinDateCal()));
                selecteddate = dateformat.parse(dateformat.format(getMinDateCal()));

            } else {
                selecteddate = dateformat.parse(txtDeliveryDate.getText().toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        slotTimeArrayList = getSelectedDayTimeSlot(mselectedDay);
        try {
            for (int i = 0; i < slotTimeArrayList.size(); i++) {
                int count = 0;
                is_break = false;
                String start_time = slotTimeArrayList.get(i).getmStartTime();
                String end_time = slotTimeArrayList.get(i).getmEndTime();

                Date dstartTime = timeformat.parse(start_time);

                Date dendTime = null;
                if (mTatTime != 0) {
                    dendTime = setTatEndTime(timeformat.parse(end_time));
                } else {

                    dendTime = timeformat.parse(end_time);
                }
                int loopcount = 0;
                loop:
                while (dstartTime.before(dendTime) || dstartTime.equals(dendTime)) {
                    loopcount = loopcount + 1;
                    if (loopcount >= 100) {
                        break loop;
                    }
                    if (selecteddate.equals(currentDate)) {

                        if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dstartTime)) {
                            if (count == 0 && mTatTime != 0) {
                                start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                                count++;
                            } else {
                                timeList.add(start_time);
                            }
                        }
                    } else {
                        if (count == 0 && mTatTime != 0) {
                            start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                            count++;
                        } else {
                            timeList.add(start_time);
                        }
                    }

                    if (is_break) {
                        if (timeList.size() == 1) {
                            timeList.remove(0);
                            timeList.add(start_time);
                        }
                        break;
                    }

                    dstartTime = timeformat.parse(start_time);
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(dstartTime);
                    cal.add(Calendar.MINUTE, intervaltime);
                    start_time = timeformat.format(cal.getTime());
                    dstartTime = timeformat.parse(start_time);


                    Date stst_static = timeformat.parse("00:00");
                    if (dstartTime.equals(stst_static)) {
                        timeList.add(start_time);

                        break loop;
                    }


                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (timeList.size() > 0) {
            GlobalValues.DELIVERY_TIME = timeList.get(0);
            setTime(GlobalValues.DELIVERY_TIME);

            //========================================
            /*Date dCutOffTime;
            try {
                cutOffTime= Integer.parseInt(GlobalValues.CUT_OFF);
                if (cutOffTime > 0) {

                    if (!String.valueOf(cutOffTime).contains(":")) {
                        dCutOffTime = timeformat.parse(cutOffTime + ":00");

                    } else {
                        dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                    }
                    if ((dCutOffTime.before(timeformat.parse(timeList.get(0)))) ||
                            (dCutOffTime.equals(timeformat.parse(timeList.get(0))))) {

                        mselectedDay++;
                        addnextDay(mselectedDay);
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }*/
            //=======================================
        }

    }


    private void setTime() {

        timeList = new ArrayList<>();
        slotTimeArrayList = new ArrayList<>();

        Log.v("cart foo", "tat time" + mTatTime);

//        mTatTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.TAT_TIME));


        /*if (outletList.size() > 0) {
            if (!outletList.get(0).getOutlet_delivery_tat().isEmpty()) {
                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_tat());
            } else if (!outletList.get(0).getOutlet_delivery_timing().isEmpty()) {
                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_timing());
            }
        }*/

        try {
            currentDate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
            if (oncreateTimeOnly2) {
                oncreateTimeOnly2 = false;
                selecteddate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));

            } else {
                selecteddate = dateformat.parse(txtDeliveryDate.getText().toString());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (txtDeliveryDate != null) {
            try {
                mselectedDay = toCalendar(stringToDate(getSelectedDate("dd-MM-yyyy"), "dd-MM-yyyy")).get(Calendar.DAY_OF_WEEK);
            } catch (Exception e) {
                e.printStackTrace();
                mselectedDay = toCalendar(getMinDateCal()).get(Calendar.DAY_OF_WEEK);
            }
        } else {
            mselectedDay = toCalendar(getMinDateCal()).get(Calendar.DAY_OF_WEEK);
        }
//        slotTimeArrayList = getSelectedDayTimeSlot(mselectedDay);
        slotTimeArrayList = new ArrayList<>();
        slotTimeArrayList.addAll(getSelectedDayTimeSlot(mselectedDay));
        removeCompletedSlotTimeArrayList(slotTimeArrayList);
        try {
            if (time_slot_type == 2) {
                for (int i = 0; i < slotTimeArrayList.size(); i++) {
                    timeList.add(slotTimeArrayList.get(i).getmStartTime() + " - " + slotTimeArrayList.get(i).getmEndTime());
                }

            } else {
                for (int i = 0; i < slotTimeArrayList.size(); i++) {
                    int count = 0;
                    is_break = false;
                    String start_time = slotTimeArrayList.get(i).getmStartTime();
                    String end_time = slotTimeArrayList.get(i).getmEndTime();

                    Date dstartTime = timeformat.parse(start_time);

                    Date dendTime = null;
                    if (mTatTime != 0) {
                        dendTime = setTatEndTime(timeformat.parse(end_time));
                    } else {

                        dendTime = timeformat.parse(end_time);
                    }

                    int loopcount = 0;
                    loop:
                    while (dstartTime.before(dendTime) || dstartTime.equals(dendTime)) {
                        loopcount = loopcount + 1;
                        if (loopcount >= 100) {
                            break loop;
                        }
                        if (selecteddate.equals(currentDate)) {

                            if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dstartTime)) {
                                if (count == 0 && mTatTime != 0) {
                                    start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                                    count++;
                                } else {
                                    timeList.add(start_time);
                                }
                            }
                        } else {
                            if (count == 0 && mTatTime != 0) {
                                start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                                count++;
                            } else {
                                timeList.add(start_time);
                            }
                        }

                        if (is_break) {
                            if (timeList.size() == 1) {
                                timeList.remove(0);
                                timeList.add(start_time);
                            }
                            break;
                        }

                        dstartTime = timeformat.parse(start_time);
                        Calendar cal = Calendar.getInstance();
                        cal.setTime(dstartTime);
                        cal.add(Calendar.MINUTE, intervaltime);
                        start_time = timeformat.format(cal.getTime());
                        dstartTime = timeformat.parse(start_time);


                        Date stst_static = timeformat.parse("00:00");
                        if (dstartTime.equals(stst_static)) {
                            timeList.add(start_time);

                            break loop;
                        }

                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (timeList.size() > 0) {
            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
            timeRecyclerView.setLayoutManager(layoutManager);
            TimeAdapter timeAdapter = new TimeAdapter(mContext, timeList);
            timeRecyclerView.setAdapter(timeAdapter);


            if (timeList.size() > 0) {

                txtDeliveryTime.setText(timeList.get(0));
                isSelectTime = true;
            }

            timeAdapter.SetOnItemClickListioner(new TimeAdapter.OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

                    try {
                        Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
                        Date dCutOffTime;
                        Date slottime;
                        Date slottimefinal = null;
                        if (selecteddate.equals(currentDate)) {

                           /* if (Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF).length() > 0) {
                                cutOffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));
                                Log.e("TAG","TimeAdapter_test1::"+cutOffTime);

                            } else {
                                cutOffTime = 0;
                            }*/
                            cutOffTime = Integer.parseInt(GlobalValues.CUT_OFF);
                            if (cutOffTime > 0) {

                                if (!String.valueOf(cutOffTime).contains(":")) {
                                    dCutOffTime = timeformat.parse(cutOffTime + ":00");

                                } else {
                                    dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                                }
                                slottimefinal = timeformat.parse(timeList.get(position));
                                Log.e("TAG", "TimeAdapter_test::" + slottimefinal);
                                if (dCutOffTime.before(slottimefinal) || dCutOffTime.equals(slottimefinal)) {
                                    Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                                } else {
                                    Log.e("TAG", "TimeAdapter_test1::" + dCutOffTime + "---" + slottimefinal);
                                    txtDeliveryTime.setText(timeList.get(position));
                                    layoutCustomTime.setVisibility(View.GONE);
                                    is_time_shown = false;
                                    isSelectTime = true;
                                }
                                /*if (dCutOffTime.before(dCurrentTime)) {
                                    Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                                } else {

                                    txtDeliveryTime.setText(timeList.get(position));
                                    layoutCustomTime.setVisibility(View.GONE);
                                    is_time_shown = false;
                                    isSelectTime = true;
                                }*/
                            } else {
                                Log.e("TAG", "TimeAdapter_test11::");
                                txtDeliveryTime.setText(timeList.get(position));
                                layoutCustomTime.setVisibility(View.GONE);
                                is_time_shown = false;
                                isSelectTime = true;
                            }
                        } else {
                            Log.e("TAG", "TimeAdapter_test12::");
                            txtDeliveryTime.setText(timeList.get(position));
                            layoutCustomTime.setVisibility(View.GONE);
                            isSelectTime = true;
                            is_time_shown = false;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }

                    /*try {
                        Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
                        Date dCutOffTime;

                        //Log.e("CUT-OFF", String.valueOf(Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF))));

                        if (selecteddate.equals(currentDate)) {

                            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF).length() > 0) {
                                cutOffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));


                            } else {
                                cutOffTime = 0;
                            }

                            if (cutOffTime > 0) {

                                if (!String.valueOf(cutOffTime).contains(":")) {
                                    dCutOffTime = timeformat.parse(cutOffTime + ":00");

                                } else {
                                    dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                                }

                                if (dCutOffTime.before(dCurrentTime)) {
                                    Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                                } else {
                                    txtDeliveryTime.setText(timeList.get(position));
                                    layoutCustomTime.setVisibility(View.GONE);
                                    is_time_shown = false;
                                    isSelectTime = true;
                                }
                            } else {
                                txtDeliveryTime.setText(timeList.get(position));
                                layoutCustomTime.setVisibility(View.GONE);
                                is_time_shown = false;
                                isSelectTime = true;
                            }
                        } else {
                            txtDeliveryTime.setText(timeList.get(position));
                            layoutCustomTime.setVisibility(View.GONE);
                            isSelectTime = true;
                            is_time_shown = false;
                        }

                    } catch (ParseException e) {
                        e.printStackTrace();
                    }*/

                }
            });

        } else {

            Toast.makeText(mContext, "Slot not available for this date", Toast.LENGTH_SHORT).show();
        }
    }

//
//    /*public void setTime100() {
//
//        timeList = new ArrayList<>();
//        slotTimeArrayList = new ArrayList<>();
//
//        slotTimeArrayList = getSelectedDayTimeSlot(mselectedDay);
//
//
//        if (slotTimeArrayList != null && !slotTimeArrayList.isEmpty()) {
//
//
//
//            try {
//                for (int i = 0; i < slotTimeArrayList.size(); i++) {
//                    int count = 0;
//                    is_break = false;
//                    String start_time = slotTimeArrayList.get(i).getmStartTime();
//                    String end_time = slotTimeArrayList.get(i).getmEndTime();
//
//                    Date dstartTime = timeformat.parse(start_time);
//
//                    Date dendTime = null;
//                    if (mTatTime != 0) {
//                        dendTime = setTatEndTime(timeformat.parse(end_time));
//                    } else {
//                        dendTime = timeformat.parse(end_time);
//                    }
//
//                    loop:
//                    while (dstartTime.before(dendTime) || dstartTime.equals(dendTime)) {
//
//
//                        if (selecteddate.equals(currentDate)) {
//
//
//                            *//*if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dendTime)) {
//                                if (count == 0 && mTatTime != 0) {
//
//                                    start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
//                                    count++;
//                                } else {
//
//
//                                    timeList.add(start_time);
//                                }
//
//                            }*//*if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dendTime)) {
//
//                                if (count == 0 && mTatTime != 0) {
//
//                                    start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
//                                    count++;
//                                } else {
//                                    timeList.add(start_time);
//                                }
//                                int currentPosition = 0;
//                                for (String value :timeList) {
//// do stuff
//                                    try {
//                                        String ctime = value;
//                                        Date crtime = timeformat.parse(ctime);
//                                        Date scrtime=timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
//                                        if (scrtime.equals(crtime) || scrtime.before(crtime)){
//
//                                            GlobalValues.DELIVERY_TIME = timeList.get(currentPosition);
//                                            setTime(GlobalValues.DELIVERY_TIME);
//                                            return;
//                                        }
//                                        currentPosition++;
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                                GlobalValues.DELIVERY_DATE = dateformat.format(selecteddate);
//                                setDate(GlobalValues.DELIVERY_DATE);
//
//
//                            }if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dendTime)) {
//
//                                if (count == 0 && mTatTime != 0) {
//
//                                    start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
//                                    count++;
//                                } else {
//                                    timeList.add(start_time);
//                                }
//                                int currentPosition = 0;
//                                for (String value :timeList) {
//// do stuff
//                                    try {
//                                        String ctime = value;
//                                        Date crtime = timeformat.parse(ctime);
//                                        Date scrtime=timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
//                                        if (scrtime.equals(crtime) || scrtime.before(crtime)){
//
//                                            GlobalValues.DELIVERY_TIME = timeList.get(currentPosition);
//                                            setTime(GlobalValues.DELIVERY_TIME);
//                                            return;
//                                        }
//                                        currentPosition++;
//                                    }catch (Exception e){
//                                        e.printStackTrace();
//                                    }
//
//                                }
//                                GlobalValues.DELIVERY_DATE = dateformat.format(selecteddate);
//                                setDate(GlobalValues.DELIVERY_DATE);
//
//
//                            } else {
//
//
//                                switch (mselectedDay) {
//
//                                    case 1:
//                                        mselectedDay = 2;
//                                        addnextDay(mselectedDay);
//                                        break;
//                                    case 2:
//                                        mselectedDay = 3;
//                                        addnextDay(mselectedDay);
//                                        break;
//                                    case 3:
//                                        mselectedDay = 4;
//                                        addnextDay(mselectedDay);
//                                        break;
//                                    case 4:
//                                        mselectedDay = 5;
//                                        addnextDay(mselectedDay);
//                                        break;
//                                    case 5:
//                                        mselectedDay = 6;
//                                        addnextDay(mselectedDay);
//                                        break;
//                                    case 6:
//                                        mselectedDay = 7;
//                                        addnextDay(mselectedDay);
//                                        break;
//
//                                    case 7:
//                                        mselectedDay = 1;
//                                        addnextDay(mselectedDay);
//                                        break;
//                                }
//
//
//                            }
//                        } else {
//                            if (count == 0 && mTatTime != 0) {
//                                start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
//                                count++;
//                            } else {
//                                timeList.add(start_time);
//                            }
//                        }
//
//                        if (is_break) {
//                            if (timeList.size() == 1) {
//                                timeList.remove(0);
//                                timeList.add(start_time);
//                            }
//                            break;
//                        }
//
//                        dstartTime = timeformat.parse(start_time);
//                        Calendar cal = Calendar.getInstance();
//                        cal.setTime(dstartTime);
//                        cal.add(Calendar.MINUTE, intervaltime);
//                        start_time = timeformat.format(cal.getTime());
//                        dstartTime = timeformat.parse(start_time);
//
//
//                        Date stst_static = timeformat.parse("00:00");
//                        if (dstartTime.equals(stst_static)) {
////                            timeList.add(start_time);
//
//                            break loop;
//                        }
//
//                    }
//                }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//            if (timeList.size() > 0) {
//
//
//
//                try {
//                    Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
//                    Date dCutOffTime;
//
//                    if (selecteddate.equals(currentDate)) {
//
//
//
//                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF).length() > 0) {
//                            cutOffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));
//                        } else {
//                            cutOffTime = 0;
//                        }
//
//
//
//                        if (cutOffTime > 0) {
//
//                            if (!String.valueOf(cutOffTime).contains(":")) {
//                                dCutOffTime = timeformat.parse(cutOffTime + ":00");
//
//                            } else {
//                                dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
//                            }
//
//                            if (dCutOffTime.before(dCurrentTime)) {
//                                Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
//                            } else {
//
//                                GlobalValues.DELIVERY_TIME = timeList.get(0);
//                                is_time_shown = false;
//                                isSelectTime = true;
//                            }
//                        } else {
//
//                            GlobalValues.DELIVERY_TIME = timeList.get(0);
//                            is_time_shown = false;
//                            isSelectTime = true;
//                        }
//                    } else {
//                        GlobalValues.DELIVERY_DATE = dateformat.format(selecteddate);
//
//
//                        GlobalValues.DELIVERY_TIME = timeList.get(0);
//                        isSelectTime = true;
//                        is_time_shown = false;
//                    }
//
//                } catch (ParseException e) {
//                    e.printStackTrace();
//                }
//            }
//
//        } else {
//
//
//
//            switch (mselectedDay) {
//
//                case 1:
//                    mselectedDay = 2;
//                    //    addnextDay(mselectedDay);
//                    break;
//                case 2:
//                    mselectedDay = 3;
//                    // addnextDay(mselectedDay);
//                    break;
//                case 3:
//                    mselectedDay = 4;
//                    // addnextDay(mselectedDay);
//                    break;
//                case 4:
//                    mselectedDay = 5;
//                    // addnextDay(mselectedDay);
//                    break;
//                case 5:
//                    mselectedDay = 6;
//                    // addnextDay(mselectedDay);
//                    break;
//                case 6:
//                    mselectedDay = 7;
//                    // addnextDay(mselectedDay);
//                    break;
//
//                case 7:
//                    mselectedDay = 1;
//                    // addnextDay(mselectedDay);
//                    break;
//            }
//
//
//        }
//    }*/

    public void setTime100(boolean initial) {

        timeList = new ArrayList<>();
        slotTimeArrayList = new ArrayList<>();

        slotTimeArrayList = getSelectedDayTimeSlot(mselectedDay);
        removeCompletedSlotTimeArrayList(slotTimeArrayList);

        if (slotTimeArrayList != null && !slotTimeArrayList.isEmpty()) {



            try {
                if (time_slot_type == 2) {
                    for (int i = 0; i < slotTimeArrayList.size(); i++) {
                        timeList.add(slotTimeArrayList.get(i).getmStartTime() + " - " + slotTimeArrayList.get(i).getmEndTime());
                    }

                } else {
                    for (int i = 0; i < slotTimeArrayList.size(); i++) {
                        int count = 0;
                        is_break = false;
                        String start_time = slotTimeArrayList.get(i).getmStartTime();
                        String end_time = slotTimeArrayList.get(i).getmEndTime();

                        Date dstartTime = timeformat.parse(start_time);

                        Date dendTime = null;
                        if (mTatTime != 0) {
                            dendTime = setTatEndTime(timeformat.parse(end_time));
                        } else {
                            dendTime = timeformat.parse(end_time);
                        }

                        int loopcount = 0;
                        loop:
                        while (dstartTime.before(dendTime) || dstartTime.equals(dendTime)) {
                            loopcount = loopcount + 1;
                            if (loopcount >= 100) {
                                break loop;
                            }

                            if (selecteddate.equals(currentDate)) {

                                if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dendTime)) {

                                    if (count == 0 && mTatTime != 0) {

                                        start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                                        count++;
                                    } else {
                                        timeList.add(start_time);
                                    }
                                    int currentPosition = 0;

                                /*GlobalValues.DELIVERY_DATE = dateformat.format(selecteddate);
                                setDate(GlobalValues.DELIVERY_DATE);*/

                                    for (String value : timeList) {
                                        // do stuff
                                        try {
                                            String ctime = value;
                                            Date crtime = timeformat.parse(ctime);
                                            Date scrtime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));

                                            if (scrtime.before(crtime) || scrtime.equals(crtime)) {
                                                currentPosition++;

                                                //GlobalValues.DELIVERY_TIME = timeList.get(currentPosition);
                                                setDeliveryTime();
                                                //setTime(GlobalValues.DELIVERY_TIME);
                                                GlobalValues.DELIVERY_DATE = dateformat.format(selecteddate);
                                                setDate(GlobalValues.DELIVERY_DATE);

                                                return;
                                            }
                                            Date dCutOffTime;
                                            try {
                                                cutOffTime = Integer.parseInt(GlobalValues.CUT_OFF);
                                                if (cutOffTime > 0) {

                                                    if (!String.valueOf(cutOffTime).contains(":")) {
                                                        dCutOffTime = timeformat.parse(cutOffTime + ":00");

                                                    } else {
                                                        dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                                                    }
                                                    if (dCutOffTime.before(scrtime)) {
                                                        mselectedDay++;
                                                        addnextDay(mselectedDay);
                                                    }
                                                }
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }
                                            currentPosition++;

                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }

                                    }


                                } else {


                                    switch (mselectedDay) {

                                        case 1:
                                            mselectedDay = 2;
                                            addnextDay(mselectedDay);
                                            break;
                                        case 2:
                                            mselectedDay = 3;
                                            addnextDay(mselectedDay);
                                            break;
                                        case 3:
                                            mselectedDay = 4;
                                            addnextDay(mselectedDay);
                                            break;
                                        case 4:
                                            mselectedDay = 5;
                                            addnextDay(mselectedDay);
                                            break;
                                        case 5:
                                            mselectedDay = 6;
                                            addnextDay(mselectedDay);
                                            break;
                                        case 6:
                                            mselectedDay = 7;
                                            addnextDay(mselectedDay);
                                            break;

                                        case 7:
                                            mselectedDay = 1;
                                            addnextDay(mselectedDay);
                                            break;
                                    }


                                }
                            } else {
                                if (count == 0 && mTatTime != 0) {
                                    start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
                                    count++;
                                } else {
                                    timeList.add(start_time);
                                }
                            }

                            if (is_break) {
                                if (timeList.size() == 1) {
                                    timeList.remove(0);
                                    timeList.add(start_time);
                                }
                                break;
                            }

                            dstartTime = timeformat.parse(start_time);
                            Calendar cal = Calendar.getInstance();
                            cal.setTime(dstartTime);
                            cal.add(Calendar.MINUTE, intervaltime);
                            start_time = timeformat.format(cal.getTime());
                            dstartTime = timeformat.parse(start_time);


                            Date stst_static = timeformat.parse("00:00");
                            if (dstartTime.equals(stst_static)) {
//                            timeList.add(start_time);

                                break loop;
                            }

                            if (timeList.size() > 100) {
//                            timeList.add(start_time);

                                break loop;
                            }

                        }
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (timeList.size() > 0) {


                try {
                    Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
                    Date dCutOffTime;
                    if (selecteddate.equals(currentDate)) {

                       /* if (Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF).length() > 0) {
                            cutOffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));
                            Log.e("TAG","Cut_of_time_1::"+cutOffTime);

                        } else {
                            cutOffTime = 0;
                        }*/
                        cutOffTime = Integer.parseInt(GlobalValues.CUT_OFF);
                        if (cutOffTime > 0) {
                            if (!String.valueOf(cutOffTime).contains(":")) {
                                dCutOffTime = timeformat.parse(cutOffTime + ":00");
                            } else {
                                dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                            }
                            if (dCutOffTime.before(dCurrentTime)) {

                                Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                            } else {

                                GlobalValues.DELIVERY_TIME = timeList.get(0);
                                is_time_shown = false;
                                isSelectTime = true;
                            }
                        } else {

                            GlobalValues.DELIVERY_TIME = timeList.get(0);
                            is_time_shown = false;
                            isSelectTime = true;
                        }
                    } else {
                        GlobalValues.DELIVERY_DATE = dateformat.format(selecteddate);

                        GlobalValues.DELIVERY_TIME = timeList.get(0);
                        isSelectTime = true;
                        is_time_shown = false;
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        } else {



            switch (mselectedDay) {

                case 1:
                    mselectedDay = 2;
                    //    addnextDay(mselectedDay);
                    break;
                case 2:
                    mselectedDay = 3;
                    // addnextDay(mselectedDay);
                    break;
                case 3:
                    mselectedDay = 4;
                    // addnextDay(mselectedDay);
                    break;
                case 4:
                    mselectedDay = 5;
                    // addnextDay(mselectedDay);
                    break;
                case 5:
                    mselectedDay = 6;
                    // addnextDay(mselectedDay);
                    break;
                case 6:
                    mselectedDay = 7;
                    // addnextDay(mselectedDay);
                    break;

                case 7:
                    mselectedDay = 1;
                    // addnextDay(mselectedDay);
                    break;
            }


        }
    }

    private void addnextDay(int mselectedDay) {
        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        gregorianCalendar.setGregorianChange(Calendar.getInstance().getTime());
        Date date1 = gregorianCalendar.getGregorianChange();
        GregorianCalendar calendar1 = new GregorianCalendar();
        calendar1.set(Calendar.DATE, date1.getDate());



        calendar1.add(Calendar.DATE, counting);


        Date date = calendar1.getTime();
        selecteddate = date;


        counting++;

        setTime100(false);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }


    private Date stringToDate(String aDate, String aFormat) {

        if (aDate == null) return null;
        ParsePosition pos = new ParsePosition(0);
        SimpleDateFormat simpledateformat = new SimpleDateFormat(aFormat);
        Date stringDate = simpledateformat.parse(aDate, pos);
        return stringDate;

    }

    private boolean checktimings(String time, String endtime) {

        String pattern = "HH:mm";
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);

        try {
            Date date1 = sdf.parse(time);
            Date date2 = sdf.parse(endtime);

            if (date1.before(date2)) {
                return true;
            } else {

                return false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return false;
    }

    public String getCurrentDateLastTime() {
        String lastTime = "";
        if (time_slot_type == 2) {
            lastTime = getSelectedDayTimeSlot(mselectedDay).get(0).getmStartTime();
            if (getSelectedDayTimeSlot(mselectedDay).size() == 1) {
                return lastTime;
            } else {
                for (int i = 1; i < getSelectedDayTimeSlot(mselectedDay).size(); i++) {
                    if (checktimings(lastTime, getSelectedDayTimeSlot(mselectedDay).get(i).getmStartTime())) {
                        lastTime = getSelectedDayTimeSlot(mselectedDay).get(i).getmStartTime();
                    }
                }
            }
        } else {
            lastTime = getSelectedDayTimeSlot(mselectedDay).get(0).getmEndTime();
            if (getSelectedDayTimeSlot(mselectedDay).size() == 1) {
                return lastTime;
            } else {
                for (int i = 1; i < getSelectedDayTimeSlot(mselectedDay).size(); i++) {
                    if (checktimings(lastTime, getSelectedDayTimeSlot(mselectedDay).get(i).getmEndTime())) {
                        lastTime = getSelectedDayTimeSlot(mselectedDay).get(i).getmEndTime();
                    }
                }
            }
        }
        return lastTime;

    }

    public String getCurrentDate(String format) {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MINUTE, mTatTime);
        Date c = calendar.getTime();

        System.out.println("Current time => " + c);

        SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
        String formattedDate = df.format(c);
        return formattedDate;
    }

    public boolean isSelectedDateIsSame(String s) {
        return getSelectedDate("dd-MM-yyyy").equalsIgnoreCase(getCurrentDate("dd-MM-yyyy"))
                && s.equalsIgnoreCase(getSelectedDate("EEE"));
    }

    public String getSelectedDate(String format) {
        String formattedDate = "";
        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
        Date testDate = null;
        try {
            if (flow == INITIAL) {
                testDate = sdf.parse(GlobalValues.DELIVERY_DATE);
            } else {
                if (txtDeliveryDate != null) {
                    try {
                        testDate = sdf.parse(txtDeliveryDate.getText().toString());
                    } catch (Exception e) {
                        testDate = sdf.parse(GlobalValues.DELIVERY_DATE);
                        e.printStackTrace();
                    }
                } else {
                    testDate = sdf.parse(GlobalValues.DELIVERY_DATE);
                }
            }
            SimpleDateFormat df = new SimpleDateFormat(format, Locale.getDefault());
            formattedDate = df.format(testDate);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return formattedDate;
    }


    private void removeCompletedSlotTimeArrayList(ArrayList<SlotTime> slotTimeArrayList) {
//        ArrayList<SlotTime> slotTimeArrayListTemp = new ArrayList<>();
//        slotTimeArrayListTemp.addAll(slotTimeArrayList);

        try {
            if (getSelectedDate("dd-MM-yyyy").equalsIgnoreCase(getCurrentDate("dd-MM-yyyy"))) {
                for (int i = 0; i < slotTimeArrayList.size(); i++) {
                    if (time_slot_type == 2) {
                        if (!checktimings(getCurrentDate("HH:mm"), slotTimeArrayList.get(i).getmStartTime())) {
                            slotTimeArrayList.remove(i);
                            removeCompletedSlotTimeArrayList(slotTimeArrayList);
                            break;
                        }
                    } else {
                        if (!checktimings(getCurrentDate("HH:mm"), slotTimeArrayList.get(i).getmEndTime())) {
                            slotTimeArrayList.remove(i);
                            removeCompletedSlotTimeArrayList(slotTimeArrayList);
                            break;
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public static Calendar toCalendar(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }


    public static Calendar toCalendar(String time) {
        Calendar cal = Calendar.getInstance();

        SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm", Locale.ENGLISH);
        try {
            cal.setTime(sdf.parse(GlobalValues.DELIVERY_DATE + " " + time));// all done
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return cal;
    }

    public Date getMinDateCal() {
        Calendar calendarMin = Calendar.getInstance();
        Date minDate = calendarMin.getTime();
        if (mMinDate > 0) {
            calendarMin.add(Calendar.DATE, mMinDate);
            minDate = calendarMin.getTime();
        } else {
//            try {
////                if (selecteddate.equals(currenttDate)) {
//
//                cutOffTime = Integer.parseInt(GlobalValues.CUT_OFF);
//                Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
//                Date dCutOffTime;
//                Date slottime;
//                Date slottimefinal = null;
//                if (cutOffTime > 0) {
//
//                    if (!String.valueOf(cutOffTime).contains(":")) {
//                        dCutOffTime = timeformat.parse(cutOffTime + ":00");
//
//                    } else {
//                        dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
//                    }
//
//                    if (dCutOffTime.before(minDate) || dCutOffTime.equals(minDate)) {
////                            Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
//                        calendarMin.add(Calendar.DATE, 1);
//                        minDate = calendarMin.getTime();
//                    } else {
//
//                    }
//
//                }
////            }
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
        }

        minDate = calendarMin.getTime();
        try {
            DateFormat format2 = new SimpleDateFormat("EEE");

            String weekday;

            //check for next 3 days is holiday in same method
            boolean exist = false;

            for (int j = 0; j < 3; j++) {

                weekday = format2.format(minDate);

                for (int i = 0; i < slotDaysList.size(); i++) {
                    if (slotDaysList.get(i).toLowerCase().contains(weekday.toLowerCase())) {
                        exist = true;
                    }
                }

                if (!exist) {
                    calendarMin.add(Calendar.DATE, 1);
                    minDate = calendarMin.getTime();
                } else {
                    break;
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return minDate;
    }


    public void setMinDateCalInitialCustom() {
        mselectedDay = toCalendar(getMinDateCal()).get(Calendar.DAY_OF_WEEK);
        Calendar calendarMin = Calendar.getInstance();
        Date minDate = calendarMin.getTime();
        if (mMinDate <= 0) {
            try {

                cutOffTime = Integer.parseInt(GlobalValues.CUT_OFF);
                Date dCutOffTime;
                if (cutOffTime > 0) {

                    if (!String.valueOf(cutOffTime).contains(":")) {
                        dCutOffTime = timeformat.parse(cutOffTime + ":00");

                    } else {
                        dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
                    }

                    String minTimeNow = timeformat.format(minDate);
                    Date dateMinNew = new Date();
                    dateMinNew = timeformat.parse(minTimeNow);

                    if (dCutOffTime.before(dateMinNew) || dCutOffTime.equals(dateMinNew)) {
//                            Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                        calendarMin.add(Calendar.DATE, 1);
                        minDate = calendarMin.getTime();
                        mMinDate = mMinDate + 1;
                    }

                }
//            }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            calendarMin.add(Calendar.DATE, mMinDate);
            minDate = calendarMin.getTime();
//            mMinDate = mMinDate + 1;
        }

        minDate = calendarMin.getTime();
        try {
            DateFormat format2 = new SimpleDateFormat("EEE");

            String weekday;

            //check for next 3 days is holiday in same method
            boolean exist = false;

            for (int j = 0; j < 3; j++) {

                weekday = format2.format(minDate);

                for (int i = 0; i < slotDaysList.size(); i++) {
                    if (slotDaysList.get(i).toLowerCase().contains(weekday.toLowerCase())) {
//                        if (weekday.equalsIgnoreCase(getCurrentDate("EEE"))) {
                        if ((isSelectedDateIsSame(weekday.toLowerCase()))) {
                            if (checktimings(getCurrentDateLastTime(), getCurrentDate("HH:mm"))) {
                                exist = false;
                            } else {
                                exist = true;
                            }
                        } else {
                            exist = true;
                            if (flow == DIALOG && /*time_slot_type == 2 &&*/ j == 0) {
                                calendarMin.add(Calendar.DATE, 1);
                                minDate = calendarMin.getTime();
                                mMinDate = mMinDate + 1;
                            } else {
//                                if (!getSelectedDate("dd-MM-yyyy").equalsIgnoreCase(getCurrentDate("dd-MM-yyyy")) && mMinDate == 0) {
//                                    calendarMin.add(Calendar.DATE, 1);
//                                    minDate = calendarMin.getTime();
//                                    mMinDate = mMinDate + 1;
//                                }
                            }
                        }
//                    }
                    }

                }

                if (exist) {

                    break;
                } else {
                    calendarMin.add(Calendar.DATE, 1);
                    minDate = calendarMin.getTime();
                    mMinDate = mMinDate + 1;
                }

            }
        } catch (
                Exception e) {
            e.printStackTrace();
        }
//        return minDate;
    }

    public void initialTimeSlotSetup() {
//        if (isDateDisplay)
        {

//            if (isSelectTime)
            {

//                GlobalValues.DELIVERY_DATE = txtDeliveryDate.getText().toString();
//                GlobalValues.DELIVERY_TIME = txtDeliveryTime.getText().toString();

                if (time_slot_type == 2 || checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

                    setDate(GlobalValues.DELIVERY_DATE);
                    setTime(GlobalValues.DELIVERY_TIME);

                    Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE, GlobalValues.DELIVERY_DATE);
                    Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME, GlobalValues.DELIVERY_TIME);

//                    if (CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {
//                        if (timeCalender != null) {  //n th time selection
//                            //For same day. if time is lesser than tat delay. change the time with tat delay
//                            //
//                            Calendar tempDateCheck = Calendar.getInstance(); //current day
//                            Calendar selectedDate = calTemp;     //user Selected date
////                                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
////                                    selectedDate.set(Calendar.MONTH, monthOfYear);
////                                    selectedDate.set(Calendar.YEAR, year);
//
//                            timeCalender = toCalendar(GlobalValues.DELIVERY_TIME);
//                            dateCalender = selectedDate;
//                            updateDateLabel();
//
//                            if (time_slot_type != 2)
//                                if (selectedDate.get(Calendar.DAY_OF_MONTH) == tempDateCheck.get(Calendar.DAY_OF_MONTH)
//                                        && selectedDate.get(Calendar.MONTH) == tempDateCheck.get(Calendar.MONTH)
//                                        && selectedDate.get(Calendar.YEAR) == tempDateCheck.get(Calendar.YEAR)) {
//                                    //Same day
//                                    Calendar testCalender = Calendar.getInstance();
//                                    int delayMinute = (mTatTime);
//                                    long includingTATtime = testCalender.getTimeInMillis();
//                                    includingTATtime = includingTATtime + (delayMinute) * 60 * 1000;
//
//                                    if (timeCalender.getTimeInMillis() <= includingTATtime) {  // TAT not satisfied
////                                        Toast.makeText(CartListActivityNew.this, "Time changed with delay of " + delayMinute + " Minutes.", Toast.LENGTH_LONG).show();
////                                        timeCalender.setTimeInMillis(includingTATtime);
////                                        SimpleDateFormat simpletimeFormat = new SimpleDateFormat("hh:mm aa");
////                                        currentTime.setText(simpletimeFormat.format(timeCalender.getTime()));
//                                        if (!GlobalValues.DELIVERY_DATE.equalsIgnoreCase("")) {
//                                            setDate(GlobalValues.DELIVERY_DATE);
//                                        }
//
//                                        if (!GlobalValues.DELIVERY_TIME.equalsIgnoreCase("")) {
//                                            setTime(GlobalValues.DELIVERY_TIME);
//                                        }
//                                    } else {  //Condition satisfied.
//                                        //Do Nothing
//
//                                        if (!GlobalValues.DELIVERY_DATE.equalsIgnoreCase("")) {
//                                            setDate(GlobalValues.DELIVERY_DATE);
//                                        }
//
//                                        if (!GlobalValues.DELIVERY_TIME.equalsIgnoreCase("")) {
//                                            setTime(GlobalValues.DELIVERY_TIME);
//                                        }
//                                    }
//                                } else {
//                                    //Future day
//                                }
//                        } else {  //First time slection
//                            // TODO Auto-generated method stub
//                            dateCalender = calTemp;
////                                    dateCalender.set(Calendar.YEAR, year);
////                                    dateCalender.set(Calendar.MONTH, monthOfYear);
////                                    dateCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                            updateDateLabel();
//                        }
//                    } else
                    if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
//                        if (timeCalender != null) {  //n th time selection
//                            //For same day. if time is lesser than tat delay. change the time with tat delay
//                            Calendar tempDateCheck = Calendar.getInstance(); //current day
//                            Calendar selectedDate = calTemp;     //user Selected date
//                                    selectedDate.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                                    selectedDate.set(Calendar.MONTH, monthOfYear);
//                                    selectedDate.set(Calendar.YEAR, year);
//                            timeCalender = toCalendar(GlobalValues.DELIVERY_TIME);
//                            dateCalender = selectedDate;
                        updateDateLabel();

//                            if (selectedDate.get(Calendar.DAY_OF_MONTH) == tempDateCheck.get(Calendar.DAY_OF_MONTH)
//                                    && selectedDate.get(Calendar.MONTH) == tempDateCheck.get(Calendar.MONTH)
//                                    && selectedDate.get(Calendar.YEAR) == tempDateCheck.get(Calendar.YEAR)) {
                        //Same day

//                                Calendar testCalender = Calendar.getInstance();
//                                int delayMinute = Integer.parseInt(GlobalValues.pickupOutlet.getOutlet_delivery_timing());
//                                long includingTATtime = testCalender.getTimeInMillis();
//                                includingTATtime = includingTATtime + (delayMinute) * 60 * 1000;

//                                if (timeCalender.getTimeInMillis() <= includingTATtime) {  // TAT not satisfied
//                                   /* Toast.makeText(CartListActivityNew.this, "Time changed with delay of " + delayMinute + " Minutes.", Toast.LENGTH_LONG).show();
//                                    timeCalender.setTimeInMillis(includingTATtime);
//                                    SimpleDateFormat simpletimeFormat = new SimpleDateFormat("hh:mm aa");
//                                    currentTime.setText(simpletimeFormat.format(timeCalender.getTime()));*/
//
//                                    if (!GlobalValues.DELIVERY_DATE.equalsIgnoreCase("")) {
//                                        setDate(GlobalValues.DELIVERY_DATE);
//                                    }
//
//                                    if (!GlobalValues.DELIVERY_TIME.equalsIgnoreCase("")) {
//                                        setTime(GlobalValues.DELIVERY_TIME);
//                                    }
//
//                                } else {  //Condition satisfied.
                        //Do Nothing

                        if (!GlobalValues.DELIVERY_DATE.equalsIgnoreCase("")) {
                            setDate(GlobalValues.DELIVERY_DATE);
                        }

                        if (!GlobalValues.DELIVERY_TIME.equalsIgnoreCase("")) {
                            setTime(GlobalValues.DELIVERY_TIME);
                        }
//                                }
//                            } else {
//                            //    Future day
//                            }
//                        } else {  //First time slection
//                            // TODO Auto-generated method stub
////                            dateCalender = calTemp;
////                                    dateCalender.set(Calendar.YEAR, year);
////                                    dateCalender.set(Calendar.MONTH, monthOfYear);
////                                    dateCalender.set(Calendar.DAY_OF_MONTH, dayOfMonth);
//                            updateDateLabel();
//                        }
                    }

//


                } else {
//                    if (!oncreateTimeError) {
//                        Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
//                    }
                }

            }
//            else
            {
//                Toast.makeText(mContext, "Please select Time", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void updateDateLabel() {
        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);
//        currentday = sdf.format(dateCalender.getTime());
        currentday = getMinDateCal();
// textView_date_currentevent.setText(sdf.format(dateCalender.getTime()));  //for events
//        txtDate.setText(sdf.format(dateCalender.getTime()));

        //New change for over time after slot(Setting from static variables)
        setDate(GlobalValues.DELIVERY_DATE);
        setTime(GlobalValues.DELIVERY_TIME);

//        try {
//            Calendar tempCalender = Calendar.getInstance();
//            tempCalender.set(Calendar.DAY_OF_MONTH, dateCalender.get(Calendar.DAY_OF_MONTH));
//            tempCalender.set(Calendar.MONTH, dateCalender.get(Calendar.MONTH));
//            tempCalender.set(Calendar.YEAR, dateCalender.get(Calendar.YEAR));
//            tempCalender.set(Calendar.HOUR_OF_DAY, 00);
//            tempCalender.set(Calendar.MINUTE, 00);
//
//            GlobalValues.advancedOrderDate = tempCalender.getTime();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

}
