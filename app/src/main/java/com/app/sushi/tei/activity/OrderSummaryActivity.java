package com.app.sushi.tei.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;

import com.app.sushi.tei.fragment.FiveMenu.FragmentReward;
import com.app.sushi.tei.netsclicktest.NetsHome;
import com.app.sushi.tei.netsclicktest.Table53;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.Html;
import android.text.InputFilter;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextWatcher;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import com.app.sushi.tei.Model.AddOns.AddOnsProducts;
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
import com.app.sushi.tei.Model.Promotion.Promotion;
import com.app.sushi.tei.Model.Promotion.PromotionRedeemed;
import com.app.sushi.tei.Model.Voucher.VoucherListItem;
import com.app.sushi.tei.Model.Voucher.VoucherUsedList;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.AddOnsRecyclerAdapter;
import com.app.sushi.tei.adapter.Cart.CartRecyclerAdapterSummary;
import com.app.sushi.tei.adapter.EwalletAdapter;
import com.app.sushi.tei.adapter.GridAdapter;
import com.app.sushi.tei.adapter.MyAccount.SingleSelectAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuChildRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuTitleRecyclerAdapter;
import com.app.sushi.tei.adapter.Promotion.PromotionRecyclerAdapter;
import com.app.sushi.tei.adapter.Promotion.PromotionRedeemRecyclerAdapter;
import com.app.sushi.tei.adapter.SetMenuAdapter.SetMenuTitleRecyclerAdapterNew;
import com.app.sushi.tei.adapter.TimeAdapter;
import com.app.sushi.tei.adapter.Vouchers.VoucherUsedAdapter;
import com.app.sushi.tei.adapter.Vouchers.VouchersAdapter;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.ClearCartMessageDialog;
import com.app.sushi.tei.dialog.MessageDialog;


import com.nets.nofsdk.exception.CardNotRegisteredException;
import com.nets.nofsdk.exception.ServiceAlreadyInitializedException;
import com.nets.nofsdk.exception.ServiceNotInitializedException;
import com.nets.nofsdk.model.Table53Data;
import com.nets.nofsdk.request.Debit;
import com.nets.nofsdk.request.Deregistration;
import com.nets.nofsdk.request.NofService;
import com.nets.nofsdk.request.Registration;
import com.nets.nofsdk.request.StatusCallback;
import com.nets.nofsdk.request.VGuardService;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.DateFormat;
import java.text.DateFormatSymbols;
import java.text.DecimalFormat;
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
import java.util.concurrent.ExecutionException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.BILLING_CHECKED;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.CURRENT_AVAILABLITY_ID;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.DE_REGISTER;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.OUTLET_DELIVERY_TAT;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.OUTLET_DELIVERY_TIMING;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.OUTLET_PICKUP_TAT;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.PURCHASE;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.REGISTER;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.firstTimePromoCode;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.isWalletApplied;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.is_cutlery_checked;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.paymentType;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.time_slot_type;
import static com.app.sushi.tei.GlobalMembers.GlobalValues.used_eWalletAmount;
import static com.app.sushi.tei.Model.ProductList.ModifierHeading.subModifierPrice;
//import static com.app.sushi.tei.activity.CartActivity.foodVoucher;
import static com.app.sushi.tei.activity.HomeActivity.mModifierPrice;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuBasePrice;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuPrice;
import static com.app.sushi.tei.activity.HomeActivity.mSetMenuQuantity;
import static com.app.sushi.tei.activity.HomeActivity.mSetmenuoverallprices;
import static com.app.sushi.tei.activity.HomeActivity.mquanititycost_src;
import static com.app.sushi.tei.activity.HomeActivity.quantityCost;
import static com.app.sushi.tei.activity.HomeActivity.txtModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.cartListTask;
import static com.app.sushi.tei.activity.SubCategoryActivity.cart_sub_total;
import static com.app.sushi.tei.activity.SubCategoryActivity.value;
import static java.text.DateFormat.MEDIUM;


public class OrderSummaryActivity extends AppCompatActivity implements View.OnClickListener {


    public static final int TO_SUBCATEGORY = 0;
    public static final int TO_CHOOSE_OUTLET = 1;
    public static final int TO_THANKS = 2;

    public static final int INITIAL = 0;
    public static final int DIALOG = 1;
    public int flow = INITIAL;
    private String trans_reference_number;
    private int posSetMenu = 0, posModifier = 0;
    private int promoCount, voucherCount;
    private SetMenuTitleRecyclerAdapterNew setMenuTitleRecyclerAdapternew;
    private Cart productCartDetails;
    private ProgressBar progressBar;
    private Context mContext;
    private Toolbar toolbar;
    private ImageView imgChecked;
    private LinearLayout imgBack;
    private ImageView txtTitle;
    private EditText edtComments, edtBillingAddress, edtPincode, edtBillingUnitNo1, edtBillingUnitNo2;
    private LinearLayout layoutProgress;
    private RecyclerView orderRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CartRecyclerAdapterSummary cartAdapter;
    private TextView txtDate, txtTime, txtChangeAddress, txtChangeAddress_pickup, txtDeliveryCharge,
            txtOutletName, txtAddress, txtFreeDelivery, txtEmpty, txtTakeawayName, txtDiscountLabel, txtGST;
    private EditText edtUnitNo2, edtUnitNo1, edtPromotion;
    public TextView txtSubTotal, txtTotal,txtpacking_charge;
    private RelativeLayout layoutDeliveryCharge, layoutBack, layoutSubTotal, layoutGrandTotal,layoutpackingcharge;
    private LinearLayout layoutCartDate, layoutCartTime;
    private TextView layoutContinue;
    private SpannableString spannableDate, spannableTime, promotionspannableString;
    private boolean isSelectTime = false, is_time_shown = false, is_date_shown = false;
    private RecyclerView timeRecyclerView;
    private Intent intent;
    private RelativeLayout layoutCustomTime, layoutAdditionalDeliveryCharge, layoutSameDeliveryAddress,
            layoutGST, layoutGSTlabel;
    private LinearLayout layoutRewards, layoutTakeaway, layoutDelivery, layoutFreeDelivery, lyoutBilling,
            layoutBillingAddress;
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
    private String mUnitNo1 = "", mUnitNo2 = "", cartCount = "";
    public static double mGrandTotal = 0.0;
    public double mGST = 0.0;
    private JSONObject outletZoneJson;
    private DatabaseHandler databaseHandler;
    public int cutOffTime;
    private int mTatTime = 0;
    private boolean is_break = false;
    private String r_applied,
            r_point,
            r_amount;
    private double cart_deleivery_charge, cart_adddeleivery_charge,packing_ser_charge;
    private String subTotal;

    private SimpleDateFormat twelvetimeformat = new SimpleDateFormat("hh:mm");


    //Calendar
    TextView previousButton, nextButton;
    private TextView currentDateText;
    private GridView calendarGridView;
    private static final int MAX_CALENDAR_COLUMN = 42;
    private int month, year;
    //private SimpleDateFormat formatter = new SimpleDateFormat("MMMM yyyy", Locale.ENGLISH);
    private SimpleDateFormat formatter = new SimpleDateFormat("yyyy", Locale.ENGLISH);
    private Calendar cal = Calendar.getInstance(Locale.ENGLISH);
    private GridAdapter mAdapter;

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
    private TextView txtRedeem, txtPromotions, txtDiscountTotal, txtDiscountTotalRedeem, txtDiscountTotalVouchers,
            txtRedeemApply, txtPromoCode, txtPromoApply;
    private EditText txtRedeemPoints;
    private RelativeLayout layoutdiscount, layoutdiscountRedeem, layoutdiscountVouchers;
    private String subtotal_value;
    public String r_sub_total;
    private String mSecondaryAddressId = "";
    private String mPromoCoupon = "", mPromoCouponResponse = "", mOrderNo = "", unitNo1 = "", unitNo2 = "", couponResponseFromFiveMenu = "";
    public static String mPromotionAmount = "";
    private TextView txtAdditionalDeliveryCharge;
    private double mrewardPoint = 0.0;
    private double mCarttotal = 0.0;
    private JSONObject jsonCartObject;
    private int fromChangeAddress = TO_THANKS;
    private ArrayList<AddOnsProducts> addOnsProductsList;
    private TextView txtGSTLabel, txt_insulsive_gst,txt_insulsive_gst1;
    public static Double gstAmount;

    SingleSelectAdapter singleSelectAdapter;
    ArrayList<SecondaryAddress> secondaryAddressArrayList = new ArrayList<>();
    public static int pos = -1;

    Boolean iEnableSecondarylist = false;

    private String mProductId = "", mProductQuantity = "1";

    private SetMenuProduct setMenuProduct;
    public Dialog dialog;
     BottomSheetDialog paymentpopup;
    private String mBasePath = "";

    private int CurrentPosition = 0;

    private String mValidationMessage = "";

    private TextView txtModi;
    private Double mSearchProuductprise = 0.0;
    public RelativeLayout layoutTime;
    public RelativeLayout layoutCalendar;
    public TextView txtDeliveryDate, txtDeliveryTime;
    public boolean isDateDisplay = false;
    private String customerNote = "";
    private String cutleryOption = "";
    private TextView txtNotes, outletText, addMore;
    private ImageView img_orderForSomeone;
    private int count = 1;
    private LinearLayout lly_orderforSomeone, layout_addMore;
    private RelativeLayout layout_spclInstruction;
    private String redeemPointsValue, promoPointsValue, voucherPointsValue, walletBalance;
    private TextView enterPoints, txtRewardPoint;
    private TextView txt_ewallet;
    private TextView redeemPoints;
    private TextView txt_doyouwnattoReddem;
    private RecyclerView recyclerView_promotionBanner, recyclerViewPromotions, recyclerviewPromotionRedeem, recyclerViewVouchers, recyclerviewVouchersUsed;
    private PromotionRecyclerAdapter promotionBannerAdapter;
    private RelativeLayout rly_promotions;
    ArrayList<Promotion> promotionmodels;
    public String promotionCode;
    public static String promotionID;
    private TextView txt_delivery_disable, txt_takeaway, txt_delivery, txt_takeaway_disable, txtGstLabel;
    private int clickCount = 0;
    private LinearLayout layout_delivery_enable, layout_takeaway_enable;
    private String rewardStatus = "APPLY NOW";
    private String rewardPoints = "";
    private String rewardPointsupdate = "";
    private Dialog dialogRedeem, dialogPromo;
    private Dialog dialogVoucher;
    public static boolean isApplyPromo = false, isApplyRedeem = false;
    private String reward_subTotal = "", promo_subTotal = "";
    private int promotionPosition;
    public static double promotionAmount = 0.00;
    private ImageView img_removeRedeem, img_removePromo;
    TextView txtEmptyEarned, txtEmptyRedeemed, txtEmptyVoucher, txtEmptyUsedVoucher;
    private TextView txtRedeemAvailablePoints,txtRedeemAvailablePoints_new, txtRedeemClick,
            txtVoucherAvailablePoints, txtVoucherClick, txtPromoAvailablePoints,
            txtPromoClick;
     LinearLayout txtRedeemClick_new,txtVoucherClick_new;
    ArrayList<PromotionRedeemed> promotionRedeemedmodelArrayList;
    private PromotionRedeemed promotionRedeemedmodel;
    private PromotionRedeemRecyclerAdapter redeemRecyclerAdapter;
    private ArrayList<VoucherListItem> voucherList;
    private ArrayList<VoucherUsedList> voucherUsedList;
    private String voucherCaseType = "0";
    private String ProductVoucherGiftName = "", ProductVoucherGiftEmail = "", ProductVoucherGiftMobile = "", ProductVoucherGiftMsg = "";
    private String mProductVoucher = "";
    private String cartVoucherDiscountAmt = "0", orderItemId = "", cartItemVoucherId = "";
    Map<String, String> params;
    private TextView txt_cartTimeLabel, txt_cartDateLabel;
    private String mPromocode = "";
    //18th Feb 2021
    private LinearLayout use_wallet_lyt;
    private CheckBox use_waller_amount;
    private TextView wallet_text;

    private RelativeLayout layoutwallet;
    private TextView txtwalletLabel, txtWalletTotal;
    private ImageView img_removeWallet;

    private LinearLayout payByeWallet, payByCard, payment_layout;
    private TextView eWalletTitle, cardTitle;
    private ImageView img_chooseOutlet;
    private boolean oncreateTimeOnly = true, oncreateTimeOnly2 = true;
    public int counting = 1;
    androidx.appcompat.app.AlertDialog alertDialog;
    private int tQuantity;
    private double plusminusPrice;
    private String TotalPriceSetMenu;
    private String mProductFavPrimaryId = "null";
    private String galleryBasePath = "";
    private LinearLayout favouriteLayout;
    private TextView favouriteText, txt_address;
    private EditText notesText, notesText1;
    private String StatusFav = "0", subCatString;
    Double total_unitprices = 0.0;
    public static String mAliasProductPrimaryId = "";
    private String cartSplNotes;

    private EwalletAdapter ewalletAdapter;
    public static EditText edt_enterAmount;
    private ToggleButton cutlery_check;

    public static boolean foodVoucher = false;
    private TextView clearcart;

    public static String BASE_URL = "https://ceres.ninjaos.com/";  //LIVE

    public static final String NETS_REGISTRATION = BASE_URL + "api/netsclickpay/tokenRegister";

    public static final String NETS_PURCHASE = BASE_URL + "api/netsclickpay/purchaseOrder";
    private ProgressDialog progress;

    private View moveIconFromLeft;
    private LinearLayout nets_pay_parent;
    private LinearLayout   nets_pay_lyt,
            nets_pay_lyt_register, corporate_wallet_lyt, nets_pay_lyt_deregister, card_details;
    private ImageView credit_debit_check_box, shopee_pay_check_box, nets_pay_check_box, corporate_wallet_check_box;
    private TextView card_number, de_register, exp_date;
    public static final String NETS_PAYMENT = "NETS_PAY";


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_summary);

        mContext = OrderSummaryActivity.this;
        databaseHandler = DatabaseHandler.getInstance(mContext);
        GlobalValues.DISCOUNT_APPLIED = false;
        GlobalValues.PRMOTION_DELIVERY_APPLIED = false;

        isWalletApplied = false;
        used_eWalletAmount = "";

        paymentType = "";


        toolbar = (Toolbar) findViewById(R.id.toolBar);
//        moveIconFromLeft = findViewById(R.id.moveIconFromLeft);
//        moveIconFromLeft.setVisibility(View.VISIBLE);
        ImageView toolbartxtTitleSummary;
        ImageView toolbartxtTitle;
        toolbartxtTitleSummary = findViewById(R.id.toolbartxtTitleSummary);
        toolbartxtTitle = findViewById(R.id.toolbartxtTitle);
        toolbartxtTitleSummary.setVisibility(View.VISIBLE);
        toolbartxtTitle.setVisibility(View.GONE);

        setSupportActionBar(toolbar);
        cutlery_check = findViewById(R.id.cutlery_check);
//        cutlery_check.setChecked(is_cutlery_checked);
        is_cutlery_checked = false;

        flow = INITIAL;



        txt_address = findViewById(R.id.txt_address);
        imgBack = toolbar.findViewById(R.id.toolbarBack);
        txt_cartTimeLabel = findViewById(R.id.txt_cartTimeLabel);
        txt_cartDateLabel = findViewById(R.id.txt_cartDateLabel);
        txtTitle = toolbar.findViewById(R.id.toolbartxtTitle);
        txt_insulsive_gst = (TextView) findViewById(R.id.txt_insulsive_gst);
        txt_insulsive_gst1=findViewById(R.id.txt_insulsive_gst1);
        layoutdiscount = (RelativeLayout) findViewById(R.id.layoutdiscount);
        layoutdiscountRedeem = findViewById(R.id.layoutdiscountRedeem);
        layoutdiscountVouchers = findViewById(R.id.layoutdiscountVouchers);
        txtDiscountTotal = (TextView) findViewById(R.id.txtDiscountTotal);
        txtDiscountTotalRedeem = (TextView) findViewById(R.id.txtDiscountTotalRedeem);
        txtDiscountTotalVouchers = findViewById(R.id.txtDiscountTotalVouchers);
        txtRedeemPoints = findViewById(R.id.txtRedeemPoints);
        txtRedeemApply = (TextView) findViewById(R.id.txtRedeemApply);
        txtPromoCode = (TextView) findViewById(R.id.txtPromoCode);
        txtPromoApply = (TextView) findViewById(R.id.txtPromoApply);
        txtNotes = findViewById(R.id.txtNotes);
        txtGST = (TextView) findViewById(R.id.txtGST);
        txtGSTLabel = (TextView) findViewById(R.id.txtGSTLabel);
        layoutGST = (RelativeLayout) findViewById(R.id.layoutGST);
        layoutGSTlabel = (RelativeLayout) findViewById(R.id.layoutGSTlabel);
        layoutBillingAddress = (LinearLayout) findViewById(R.id.layoutBillingAddress);
        lyoutBilling = (LinearLayout) findViewById(R.id.lyoutBilling);
        layoutProgress = (LinearLayout) findViewById(R.id.layoutProgress);
        layoutRewards = (LinearLayout) findViewById(R.id.layoutRewards);
        layoutDelivery = (LinearLayout) findViewById(R.id.layoutDelivery);
        layoutTakeaway = (LinearLayout) findViewById(R.id.layoutTakeaway);
        layoutFreeDelivery = (LinearLayout) findViewById(R.id.layoutFreeDelivery);
        orderRecyclerView = (RecyclerView) findViewById(R.id.ordersRecyclerView);
        layoutContinue = findViewById(R.id.layoutContinue);
        layoutBack = (RelativeLayout) findViewById(R.id.layoutBack);
        layoutGrandTotal = (RelativeLayout) findViewById(R.id.layoutGrandTotal);
        layoutSameDeliveryAddress = (RelativeLayout) findViewById(R.id.layoutSameDeliveryAddress);
        layoutDeliveryCharge = (RelativeLayout) findViewById(R.id.layoutDeliveryCharge);
        layoutSubTotal = (RelativeLayout) findViewById(R.id.layoutSubTotal);
        layoutpackingcharge=findViewById(R.id.layoutpackingcharge);
        layoutCartDate = (LinearLayout) findViewById(R.id.layoutCartDate);
        layoutCartTime = (LinearLayout) findViewById(R.id.layoutCartTime);
        layoutAdditionalDeliveryCharge = (RelativeLayout) findViewById(R.id.layoutAdditionalDeliveryCharge);
        txtRedeem = (TextView) findViewById(R.id.txtRedeem);
        txtPromotions = (TextView) findViewById(R.id.txtPromotions);
        txtChangeAddress = (TextView) findViewById(R.id.txtChangeAddress);
        txtChangeAddress_pickup = (TextView) findViewById(R.id.txtChangeAddress_pickup);
        txtSubTotal = (TextView) findViewById(R.id.txtSubTotal);
        txtpacking_charge=findViewById(R.id.txtpacking_charge);
        txtDeliveryCharge = (TextView) findViewById(R.id.txtDeliveryCharge);
        txtTotal = (TextView) findViewById(R.id.txtTotal);
        txtOutletName = (TextView) findViewById(R.id.txtOutletName);
        txtTakeawayName = (TextView) findViewById(R.id.txtTakeawayName);
        txtAddress = (TextView) findViewById(R.id.txtAddress);
        txtFreeDelivery = (TextView) findViewById(R.id.txtFreeDelivery);
        txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        edtComments = (EditText) findViewById(R.id.edtComments);
        edtBillingAddress = (EditText) findViewById(R.id.edtBillingAddress);
        edtPincode = (EditText) findViewById(R.id.edtPincode);
        edtUnitNo1 = (EditText) findViewById(R.id.edtUnitNo1);
        edtUnitNo2 = (EditText) findViewById(R.id.edtUnitNo2);
        edtBillingUnitNo1 = (EditText) findViewById(R.id.edtBillingUnitNo1);
        edtBillingUnitNo2 = (EditText) findViewById(R.id.edtBillingUnitNo2);
        edtBillingUnitNo2 = (EditText) findViewById(R.id.edtBillingUnitNo2);
        edtPromotion = (EditText) findViewById(R.id.edtPromotion);
        txtAdditionalDeliveryCharge = (TextView) findViewById(R.id.txtAdditionalDeliveryCharge);
        txtDiscountLabel = (TextView) findViewById(R.id.txtDiscountLabel);
        imgChecked = (ImageView) findViewById(R.id.imgChecked);
        img_orderForSomeone = findViewById(R.id.img_orderForSomeone);
        addMore = findViewById(R.id.addMore);
        lly_orderforSomeone = findViewById(R.id.lly_orderforSomeone);
        layout_spclInstruction = findViewById(R.id.layout_spclInstruction);
        layout_addMore = findViewById(R.id.layout_addMore);
        txt_doyouwnattoReddem = findViewById(R.id.txt_doyouwnattoReddem);
        recyclerView_promotionBanner = findViewById(R.id.recyclerView_promotionBanner);
        rly_promotions = findViewById(R.id.rly_promotions);
        txt_takeaway = findViewById(R.id.txt_takeaway);
        txt_delivery = findViewById(R.id.txt_delivery);
        txt_takeaway_disable = findViewById(R.id.txt_takeaway_disable);
        txt_delivery_disable = findViewById(R.id.txt_delivery_disable);
        layout_delivery_enable = findViewById(R.id.layout_delivery_enable);
        layout_takeaway_enable = findViewById(R.id.layout_takeaway_enable);
        outletText = findViewById(R.id.outletText);
        txtGstLabel = findViewById(R.id.txtGstLabel);
        img_removeRedeem = findViewById(R.id.img_removeRedeem);
        img_removePromo = findViewById(R.id.img_removePromo);
        txtRedeemAvailablePoints = findViewById(R.id.txtRedeemAvailablePoints);
        txtRedeemAvailablePoints_new=findViewById(R.id.txtRedeemAvailablePoints_new);
        txtRedeemClick = findViewById(R.id.txtRedeemClick);
        txtRedeemClick_new=findViewById(R.id.txtRedeemClick_new);
        txtVoucherAvailablePoints = findViewById(R.id.txtVoucherAvailablePoints);
        txtVoucherClick = findViewById(R.id.txtVoucherClick);
        txtVoucherClick_new=findViewById(R.id.txtVoucherClick_new);
        txtPromoAvailablePoints = findViewById(R.id.txtPromoAvailablePoints);
        txtPromoClick = findViewById(R.id.txtPromoClick);

        use_wallet_lyt = findViewById(R.id.use_wallet_lyt);
        use_waller_amount = findViewById(R.id.use_waller_amount);
        wallet_text = findViewById(R.id.wallet_text);

        layoutwallet = findViewById(R.id.layoutwallet);
        txtwalletLabel = findViewById(R.id.txtwalletLabel);
        txtWalletTotal = findViewById(R.id.txtWalletTotal);
        img_removeWallet = findViewById(R.id.img_removeWallet);

        payment_layout = findViewById(R.id.payment_layout);
        payByeWallet = findViewById(R.id.payByeWallet);
        payByCard = findViewById(R.id.payByCard);
        eWalletTitle = findViewById(R.id.eWalletTitle);
        cardTitle = findViewById(R.id.cardTitle);
        img_chooseOutlet = findViewById(R.id.img_chooseOutlet);
        clearcart = findViewById(R.id.clearallcartitems);

        CheckAddresList();
        getRewardsPoints();

        txtRedeemApply.setOnClickListener(this);
        txtPromoApply.setOnClickListener(this);
        txtRedeem.setOnClickListener(this);
        txtPromotions.setOnClickListener(this);

        txt_address.setText(Utility.readFromSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS) + ", Singapore, " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_PINCODE));

        cutlery_check.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                is_cutlery_checked = b;
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
//                                Intent intent = new Intent(mContext, ChooseOutletActivity.class);
//                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
//                                    intent.putExtra("availability", "delivery");
//                                } else {
//                                    intent.putExtra("availability", "takeaway");
//                                }
//                                startActivity(intent);
//                                finish();

                                {//new

                                    String url1 = GlobalUrl.DESTROY_CART_URL;
                                    Map<String, String> params = new HashMap<>();
                                    params.put("app_id", GlobalValues.APP_ID);
                                    params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                    params.put("reference_id", "");

                                    if (Utility.networkCheck(mContext)) {
                                        fromChangeAddress = TO_CHOOSE_OUTLET;
                                        new DestroyCartTask(params).execute(url1);
                                    }
                                }
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
                                fromChangeAddress = TO_SUBCATEGORY;
                                new DestroyCartTask(params).execute(url1);
                            }
                        }
                    }
                });
            }
        });

        txt_delivery_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                hide below for not implementing delivery
                Toast.makeText(OrderSummaryActivity.this, "This feature is not available", Toast.LENGTH_SHORT).show();

//                {
//                    String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//                    if (minQual.equalsIgnoreCase("")) {
//                        minQual = "0";
//                    }
//                    if (Integer.parseInt(minQual) >= 1) {
//                        String message = "You are about to clear your cart by switching from one oulet to another!";
//                        new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
//                            @Override
//                            public void selectedAction(String action) {
//                                if (action.equalsIgnoreCase("YES")) {
//                                    txt_delivery_disable.setVisibility(View.GONE);
//                                    txt_takeaway.setVisibility(View.GONE);
//                                    txt_delivery.setVisibility(View.VISIBLE);
//                                    txt_takeaway_disable.setVisibility(View.VISIBLE);
//                                    Intent intent = new Intent(mContext, ChooseOutletActivity.class);
//                                    startActivity(intent);
//                                    finish();
//                                }
//                            }
//                        });
//                    } else {
//                        txt_delivery_disable.setVisibility(View.GONE);
//                        txt_takeaway.setVisibility(View.GONE);
//                        txt_delivery.setVisibility(View.VISIBLE);
//                        txt_takeaway_disable.setVisibility(View.VISIBLE);
//                        Intent intent = new Intent(mContext, ChooseOutletActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
//                }
            }
        });

        txt_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_delivery_disable.performClick();
            }
        });

        txt_takeaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txt_takeaway_disable.performClick();
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
                    String message = "You are about to clear your cart by switching from one oulet to another!";
                    new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                txt_delivery_disable.setVisibility(View.VISIBLE);
                                txt_takeaway.setVisibility(View.VISIBLE);
                                txt_delivery.setVisibility(View.GONE);
                                txt_takeaway_disable.setVisibility(View.GONE);
//                                Intent intent = new Intent(mContext, ChooseOutletActivity.class);
//                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
//                                    intent.putExtra("availability", "delivery");
//                                } else {
//                                    intent.putExtra("availability", "takeaway");
//                                }
//                                startActivity(intent);
//                                finish();

                                {//new

                                    String url1 = GlobalUrl.DESTROY_CART_URL;
                                    Map<String, String> params = new HashMap<>();
                                    params.put("app_id", GlobalValues.APP_ID);
                                    params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                    params.put("reference_id", "");

                                    if (Utility.networkCheck(mContext)) {
                                        fromChangeAddress = TO_CHOOSE_OUTLET;
                                        new DestroyCartTask(params).execute(url1);
                                    }
                                }
                            }
                        }
                    });
                } else {
                    txt_delivery_disable.setVisibility(View.VISIBLE);
                    txt_takeaway.setVisibility(View.VISIBLE);
                    txt_delivery.setVisibility(View.GONE);
                    txt_takeaway_disable.setVisibility(View.GONE);
//                    Intent intent = new Intent(mContext, ChooseOutletActivity.class);
//                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
//                        intent.putExtra("availability", "delivery");
//                    } else {
//                        intent.putExtra("availability", "takeaway");
//                    }
//                    startActivity(intent);
//                    finish();
                    {//new

                        String url1 = GlobalUrl.DESTROY_CART_URL;
                        Map<String, String> params = new HashMap<>();
                        params.put("app_id", GlobalValues.APP_ID);
                        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                        params.put("reference_id", "");

                        if (Utility.networkCheck(mContext)) {
                            fromChangeAddress = TO_CHOOSE_OUTLET;
                            new DestroyCartTask(params).execute(url1);
                        }
                    }
                }
            }
        });

        //Card Payment
        payByCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!(paymentType.equalsIgnoreCase(""))) {
                    if (paymentType.equalsIgnoreCase("WALLET")) {
                        eWalletTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                        payByeWallet.setBackgroundResource(R.drawable.payment_outline);

                        //Removing ewallet amount
                        if (layoutwallet.getVisibility() == View.VISIBLE) {
                            layoutwallet.setVisibility(View.GONE);
                            use_waller_amount.setChecked(false);
                            updateWallet(Double.parseDouble(used_eWalletAmount), "REMOVE");
                            isWalletApplied = false;
                        }
                    }
                }

                paymentType = "CARD";
                cardTitle.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                payByCard.setBackgroundResource(R.drawable.payment_selected);
            }
        });

        //eWallet Payment
        payByeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!(paymentType.equalsIgnoreCase(""))) {
                    if (paymentType.equalsIgnoreCase("CARD")) {
                        cardTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                        payByCard.setBackgroundResource(R.drawable.payment_outline);
                    }
                }

                paymentType = "WALLET";
                eWalletTitle.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                payByeWallet.setBackgroundResource(R.drawable.payment_selected);
                walletDialogue();

            }
        });

        txt_doyouwnattoReddem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRedeem.performClick();
            }
        });

        layoutManager = new LinearLayoutManager(mContext);
        orderRecyclerView.setLayoutManager(layoutManager);
        intent = getIntent();

        if (getIntent().getExtras() != null) {
            customerNote = intent.getStringExtra("notes");
            cutleryOption = intent.getStringExtra("cutlery");

            mPromoCouponResponse = intent.getStringExtra("PROMO_RESPONSE");

            //couponResponseFromFiveMenu = intent.getStringExtra("PROMO_RESPONSE");
            if (Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID).equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                if (intent.getStringExtra("unitno1") != null) {
                    unitNo1 = intent.getStringExtra("unitno1");
                    unitNo2 = intent.getStringExtra("unitno2");

                    if (!unitNo1.equalsIgnoreCase("")) {
                        edtUnitNo1.setText(unitNo1);
                    }

                    if (!unitNo2.equalsIgnoreCase("")) {
                        edtUnitNo2.setText(unitNo2);
                    }
                }
            }
        }

        if (CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {
            txt_cartTimeLabel.setText("DELIVERY TIME");
            txt_cartDateLabel.setText("DATE OF DELIVERY");
        } else {
            //      txt_cartTimeLabel.setText("PICKUP TIME");
            txt_cartTimeLabel.setText("COLLECTION TIME");
            txt_cartDateLabel.setText("DATE OF PICKUP");
        }






        /*if (mPromoCouponResponse != null && mPromoCouponResponse.length() > 0) {
            layoutdiscount.setVisibility(View.VISIBLE);
            try {
                parseCouponPointResponse(mPromoCouponResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }*/
        //setDeliveryAddress();

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

            setDate(GlobalValues.DELIVERY_DATE);
            setTime(GlobalValues.DELIVERY_TIME);
//            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        GlobalValues.GstChargers = Utility.readFromSharedPreference(mContext, GlobalValues.GstCharger);


        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
            lyoutBilling.setVisibility(View.GONE);
        } else {
            lyoutBilling.setVisibility(View.GONE);
        }

        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
            //  layoutGST.setVisibility(View.VISIBLE);
        } else {
            // layoutGST.setVisibility(View.VISIBLE);
        }


        if (GlobalValues.GstChargers.equals("0")) {

            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                txtGSTLabel.setText("GST (" + "7" + "%)");

                // layoutGST.setVisibility(View.VISIBLE);
            } else {
                txtGSTLabel.setText("GST (" + GlobalValues.GstChargers + "%)");
//                txtGstLabel.setText("Gst Charges(" + GlobalValues.GstChargers + "%)");
                txtGstLabel.setText("GST Inclusive(" + GlobalValues.GstChargers + "%)");
                layoutGST.setVisibility(View.GONE);
                layoutGSTlabel.setVisibility(View.GONE);         //Hide
            }
        } else {

            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                txtGSTLabel.setText("GST (" + "7" + "%)");
                // layoutGST.setVisibility(View.VISIBLE);
            } else {


                txtGSTLabel.setText("GST (" + GlobalValues.GstChargers + "%)");
                // layoutGST.setVisibility(View.VISIBLE);
            }


        }

        txtGstLabel.setText("GST Inclusive (" + GlobalValues.GstChargers + "%): ");

        txtDate = (TextView) findViewById(R.id.txtDate);

        String date = GlobalValues.DELIVERY_DATE;
        setDate(date);


        txtTime = (TextView) findViewById(R.id.txtTime);
        String time = GlobalValues.DELIVERY_TIME;
        setTime(time);


/*        edtUnitNo1.setText(GlobalValues.Unit_no_1);

        edtUnitNo2.setText(GlobalValues.Unit_no_2);*/


        edtPincode.addTextChangedListener(new TextWatcher() {
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


        img_removeRedeem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Are you sure want to remove redeem";
                new CheckOutMessageDialog(mContext, "Cancel", message, new CheckOutMessageDialog.OnSlectedMethod() {
                    @Override
                    public void selectedAction(String action) {
                        if (action.equalsIgnoreCase("YES")) {
                            removeRewardPoints();
                        }
                    }
                });
            }
        });

        img_removePromo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 String message = "Are you sure want to remove promotion";
                new CheckOutMessageDialog(mContext, "Cancel", message, new CheckOutMessageDialog.OnSlectedMethod() {
                    @Override
                    public void selectedAction(String action) {
                        if (action.equalsIgnoreCase("YES")) {
                            removePromotion();
                        }
                    }
                });
            }
        });

        img_removeWallet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String message = "Are you sure want to remove wallet";
                new CheckOutMessageDialog(mContext, "Cancel", message, new CheckOutMessageDialog.OnSlectedMethod() {
                    @Override
                    public void selectedAction(String action) {
                        if (action.equalsIgnoreCase("YES")) {
                            if (layoutwallet.getVisibility() == View.VISIBLE) {
                                layoutwallet.setVisibility(View.GONE);
                                use_waller_amount.setChecked(false);
                                updateWallet(Double.parseDouble(used_eWalletAmount), "REMOVE");
                                isWalletApplied = false;
                                eWalletTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                                payByeWallet.setBackgroundResource(R.drawable.payment_outline);
                                paymentType = "";
                            }
                        }
                    }
                });

            }
        });

        mPromotion = txtPromotions.getText().toString();

        setPromotionSpan(mPromotion);


        promotionspannableString = new SpannableString(txtChangeAddress.getText().toString());
        promotionspannableString.setSpan(new UnderlineSpan(), 0, txtChangeAddress.getText().toString().length(), 0);
        /*spannableString.setSpan(new StyleSpan(Typeface.BOLD), 45, 69, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 74, mPolicy.length()-1, 0);*/
        promotionspannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)),
                9, txtChangeAddress.getText().toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        txtChangeAddress.setText(promotionspannableString);


        promotionspannableString = new SpannableString(txtChangeAddress_pickup.getText().toString());
        promotionspannableString.setSpan(new UnderlineSpan(), 0, txtChangeAddress_pickup.getText().toString().length(), 0);
        /*spannableString.setSpan(new StyleSpan(Typeface.BOLD), 45, 69, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 74, mPolicy.length()-1, 0);*/
        promotionspannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.colorBlack)),
                9, txtChangeAddress_pickup.getText().toString().length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        txtChangeAddress_pickup.setText(promotionspannableString);

        layout_addMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, SubCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        });

        lly_orderforSomeone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                count++;
                if (count % 2 == 0) {
                    img_orderForSomeone.setImageResource(R.drawable.order_for_some_selected);
                    layout_spclInstruction.setVisibility(View.VISIBLE);
                    txtNotes.setVisibility(View.VISIBLE);
                } else {
                    img_orderForSomeone.setImageResource(R.drawable.order_for_some_unselected);
                    layout_spclInstruction.setVisibility(View.GONE);
                    txtNotes.setVisibility(View.GONE);

                }
            }
        });

        layoutContinue.setOnClickListener(new View.OnClickListener() {
                                              @Override
                                              public void onClick(View v) {
                                                  try {
                                                      mCarttotal = Double.parseDouble(txtTotal.getText().toString().replace("$", ""));
                                                  } catch (NumberFormatException e) {
                                                      e.printStackTrace();
                                                  }

                                                  if (mCarttotal > 0) {
                                                       paymentpopup = new BottomSheetDialog(mContext, R.style.custom_dialog_theme);
                                                      paymentpopup.requestWindowFeature(Window.FEATURE_NO_TITLE);
                                                      paymentpopup.setContentView(R.layout.payment_type_popup);
                                                      paymentpopup.setCancelable(true);
                                                      paymentpopup.show();
                                                      final String[] selectedPaymentMode = {""};

                                                      final RelativeLayout rel_wallet = paymentpopup.findViewById(R.id.rel_wallet);
                                                      final LinearLayout rel_card = paymentpopup.findViewById(R.id.rel_card);
                                                      final RelativeLayout rel_nets = paymentpopup.findViewById(R.id.rel_nets);
                                                      final RelativeLayout rel_uob  = paymentpopup.findViewById(R.id.rel_uob);

                                                      if (Utility.readFromSharedPreference(mContext,GlobalValues.PAYMENTKEYENABLE).contains("OMISE")){
                                                          rel_card.setVisibility(View.VISIBLE);
                                                      }else {
                                                          rel_card.setVisibility(View.GONE);
                                                      }

                                           //           nets_pay_parent = paymentpopup.findViewById(R.id.nets_pay_parent);
                                                      nets_pay_lyt = paymentpopup.findViewById(R.id.nets_pay_lyt);
                                                      nets_pay_lyt_register = paymentpopup.findViewById(R.id.nets_pay_lyt_register);
                                                      nets_pay_lyt_deregister = paymentpopup.findViewById(R.id.nets_pay_lyt_deregister);

                      //                                nets_pay_check_box =paymentpopup.findViewById(R.id.nets_pay_check_box);

                                                      card_number = paymentpopup.findViewById(R.id.card_number);
                                                      de_register = paymentpopup.findViewById(R.id.de_register);
                                                      exp_date = paymentpopup.findViewById(R.id.exp_date);
                                                      card_details = paymentpopup.findViewById(R.id.card_details);

                                                      TextView layoutContinue = paymentpopup.findViewById(R.id.layoutContinue);
                                                      ImageView img_close = paymentpopup.findViewById(R.id.img_close);

                                                      Log.e("TAG","InitializeTest::"+NofService.isInitialized()+"\n"+isRegistrationSuccess());
                                                      updateUI( );
                                                     /* if(isRegistrationSuccess()) {
                                                          nets_pay_lyt_register.setVisibility(View.GONE);
                                                          nets_pay_lyt_deregister.setVisibility(View.VISIBLE);
                                                          String card_num = getCardType(Utility.readFromSharedPreference(mContext, GlobalValues.CARD_TYPE)) + "(•••• " + Utility.readFromSharedPreference(mContext, GlobalValues.NETS_CARD_NUMBER) + ")";
                                                          exp_date.setText("Valid until " + formatDate(Utility.readFromSharedPreference(mContext, GlobalValues.NETS_EXPIRY)));
                                                          card_number.setText(card_num);
                                                      }else {
                                                          nets_pay_lyt_register.setVisibility(View.VISIBLE);
                                                          nets_pay_lyt_deregister.setVisibility(View.GONE);
                                                      }*/

                                                      nets_pay_lyt.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              rel_nets.performClick();
                                                              nets_pay_lyt_register.performClick();

                                               //               set_Selected_payment_method(NETS_PAYMENT);
                                                          }
                                                      });

                                                     /* nets_pay_parent.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              rel_nets.performClick();
                                                              Log.e("TAG","InitializeTest::"+NofService.isInitialized());
                                                              set_Selected_payment_method(NETS_PAYMENT);
                                                          }
                                                      });*/

                                                      card_details.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              GlobalValues.PAYMENT_TYPE = "NETS";
                                                              if(NofService.isInitialized()){
                                                                  checkAndProceedNetsPay(paymentpopup);
                                                              }else {
                                                                  initializeNOF(PURCHASE,paymentpopup);
                                                              }
                                                          }
                                                      });

                                                      de_register.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              if(NofService.isInitialized()){
                                                                  showAlertDialogDelete();
                                                              }else {
                                                                  initializeNOF(DE_REGISTER,paymentpopup);
                                                              }
                                                          }
                                                      });

                                                      nets_pay_lyt_register.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              rel_nets.setBackgroundResource(R.drawable.payment_border_green);
                                                              rel_card.setBackgroundResource(R.drawable.payment_border);
                                                              rel_uob.setBackgroundResource(R.drawable.payment_border);
                                                              rel_wallet.setBackgroundResource(R.drawable.payment_border);
                                                              GlobalValues.PAYMENT_TYPE = "NETS";
                                                              Log.e("TAG","InitializeTest_1::"+NofService.isInitialized());
                                                              if(NofService.isInitialized()){
                                                                  checkAndProceedNetsPay(paymentpopup);
                                                              }else {
                                                                  initializeNOF(REGISTER,paymentpopup);
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
                                                              rel_uob.setBackgroundResource(R.drawable.payment_border);
                                                              selectedPaymentMode[0] = "wallet";
                                                          }
                                                      });

                                                      rel_card.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              rel_card.setBackgroundResource(R.drawable.payment_border_green);
                                                              rel_wallet.setBackgroundResource(R.drawable.payment_border);
                                                              rel_nets.setBackgroundResource(R.drawable.payment_border);
                                                              rel_uob.setBackgroundResource(R.drawable.payment_border);
                                                              selectedPaymentMode[0] = "card";
                                                              GlobalValues.PAYMENT_TYPE = "CARD";
                                                          }
                                                      });

                                                      rel_nets.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View v) {
                                                              rel_nets.setBackgroundResource(R.drawable.payment_border_green);
                                                              rel_card.setBackgroundResource(R.drawable.payment_border);
                                                              rel_uob.setBackgroundResource(R.drawable.payment_border);
                                                              rel_wallet.setBackgroundResource(R.drawable.payment_border);
                                                              selectedPaymentMode[0] = "Nets";
                                                          }
                                                      });

                                                      rel_uob.setOnClickListener(new View.OnClickListener() {
                                                          @Override
                                                          public void onClick(View view) {
                                                              rel_uob.setBackgroundResource(R.drawable.payment_border_green);
                                                              rel_card.setBackgroundResource(R.drawable.payment_border);
                                                              rel_nets.setBackgroundResource(R.drawable.payment_border);
                                                              rel_wallet.setBackgroundResource(R.drawable.payment_border);
                                                              selectedPaymentMode[0] = "UOB";
                                                              GlobalValues.PAYMENT_TYPE = "UOB";
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

                                                              if (selectedPaymentMode[0].equalsIgnoreCase("UOB")) {
                                                                  paymentpopup.dismiss();
                                                                  placeCashOnDeliveryOrder();
                                                                  return;
                                                              }

                                                              if (selectedPaymentMode[0].equalsIgnoreCase("card")) {
                                                                  paymentpopup.dismiss();
                                                           //       validateAndPlaceOrder();
                                                                  placeCashOnDeliveryOrder();
//                                                              {
//                                                              dialog.dismiss();
//
//
//                                                              if (!Utility.isCustomerLoggedIn(mContext)) {
//
//                                                                  intent = new Intent(mContext, LoginActivity.class);
//                                                                  startActivity(intent);
//
//                                                              } else {
//
//                                                      /*if(paymentType.equalsIgnoreCase("")){
//                                                          if(mGrandTotal <= 0){
//
//                                                          }else{
//                                                              if(!(walletBalance.equalsIgnoreCase("null")  ||  walletBalance.equalsIgnoreCase(""))){
//                                                                  if(Double.parseDouble(walletBalance) > 0){
//                                                                      Toast.makeText(mContext, "Please Select Payment Type", Toast.LENGTH_SHORT).show();
//                                                                      return;
//                                                                  }
//                                                              }else{
//                                                                 paymentType = "CARD";
//                                                              }
//
//                                                          }
//                                                      }*/
//
//                                                                  double minAmount = 0.0;
//
//
//                                                                  try {
//                                                                      outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
//                                                                      minAmount = Double.parseDouble(outletZoneJson.getString("zone_min_amount"));
//
//                                                                      r_sub_total = txtSubTotal.getText().toString().replace("$", "");
//                                                                  } catch (Exception e) {
//                                                                      minAmount = 0.0;
//                                                                  }
//
//                                                                  if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {
//
//
//
//                                                                      if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).toString().isEmpty()) {
//
//
//                                                                          final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
//
//                                                                          alertDialog.setMessage("Please update phone number. ");
//                                                                          alertDialog.setTitle("Message");
//
//                                                                          // Setting OK Button
//                                                                          alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                                                              @Override
//                                                                              public void onClick(DialogInterface dialog, int which) {
//                                                                                  dialog.dismiss();
//                                                                                  openFiveMenuActivity(0);
//                                                                              }
//                                                                          });
//
//                                                                          // Showing Alert Message
//                                                                          alertDialog.show();
//
//                                                                      } else {
//
//
//                                                                          if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//
//
//                                                                              if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//
//                                                                                  cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);
//
//                                                                                  String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.MinimumQuality);
//
//                                                                                  if (Integer.parseInt(MinQual) <= Integer.parseInt(cartCount)) {
//
//
//
//                                                                                      if (edtUnitNo1.getText().toString().isEmpty() && edtUnitNo2.getText().toString().isEmpty()) {
//
//                                                                                          // True
//
//                                                                                          Toast.makeText(mContext, "Please enter your unit number.", Toast.LENGTH_SHORT).show();
//
//
//                                                                                      } else {
//                                                                                          //fasle
//
//                                                                                          if (minAmount <= Double.valueOf(r_sub_total)) {
//
//                                                                                              placeCashOnDeliveryOrder();
//                                                                                          } else if (Double.valueOf(r_sub_total) == 0.0) {
//
//                                                                                              placeCashOnDeliveryOrder();
//                                                                                          } else {
//                                                                                              Toast.makeText(mContext, " You must order minimum of " + minAmount +
//                                                                                                      "to place your order,your current order total is" + r_sub_total, Toast.LENGTH_SHORT).show();
//                                                                                          }
//                                                                                      }
//
//
//                                                                                  } else {
//
//                                                                                      // openDialogbox();
//
//                                                                                  }
//
//                                                                              } else {
//
//
//                                                                                  if (edtUnitNo1.getText().toString().isEmpty() && edtUnitNo2.getText().toString().isEmpty()) {
//
//                                                                                      // True
//
//                                                                                      Toast.makeText(mContext, "Please enter your unit number.", Toast.LENGTH_SHORT).show();
//
//
//                                                                                  } else {
//                                                                                      //fasle
//
//                                                                                      if (minAmount <= Double.valueOf(r_sub_total)) {
//
//                                                                                          placeCashOnDeliveryOrder();
//                                                                                      } else if (Double.valueOf(r_sub_total) == 0.0) {
//
//                                                                                          placeCashOnDeliveryOrder();
//                                                                                      } else {
//                                                                                          Toast.makeText(mContext, " You must order minimum of " + minAmount +
//                                                                                                  "to place your order,your current order total is" + r_sub_total, Toast.LENGTH_SHORT).show();
//                                                                                      }
//                                                                                  }
//
//
//                                                                              }
//
//                                                                          } else {
//
//
//                                                                              if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
//
//                                                                                  if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).toString().isEmpty()) {
//
//                                                                                      final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext, R.style.AlertDialogTheme);
//
//                                                                                      alertDialog.setMessage("Please update phone number. ");
//                                                                                      alertDialog.setTitle("Message");
//
//                                                                                      // Setting OK Button
//                                                                                      alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                                                                          @Override
//                                                                                          public void onClick(DialogInterface dialog, int which) {
//                                                                                              dialog.dismiss();
//                                                                                              openFiveMenuActivity(0);
//                                                                                          }
//                                                                                      });
//
//                                                                                      // Showing Alert Message
//                                                                                      alertDialog.show();
//
//                                                                                  } else {
//
//                                                                                      placeCashOnDeliveryOrder();
//
//                                                                                  }
//                                                                              }
//                                                                          }
//
//
//                                                                      }
//
//
//                                                                  } else {
//
//                                                                      Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
//                                                                  }
//                                                              }
//                                                          }
                                                              }
                                                          }
                                                      });

                                                  }
                                                  else {
                                                      validateAndPlaceOrder();
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

        txtChangeAddress.setOnClickListener(new View.OnClickListener() {
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
                        fromChangeAddress = TO_CHOOSE_OUTLET;
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
        });

        txtChangeAddress_pickup.setOnClickListener(new View.OnClickListener() {
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
                        fromChangeAddress = TO_CHOOSE_OUTLET;
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
        });


        imgBack.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View v) {
                                           /*Intent intent = new Intent(mContext, SubCategoryActivity.class);
                                           startActivity(intent);*/
                                           finish();
                                       }
                                   }

        );


        layoutSameDeliveryAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!BILLING_CHECKED) {
                    imgChecked.setImageResource(R.drawable.asset54);
                    BILLING_CHECKED = true;

                  /*  GradientDrawable bgShape = (GradientDrawable) edtBillingAddress.getBackground();
                    bgShape.setColor(mContext.getResources().getColor(R.color.colorWhite));

                    GradientDrawable bgShape1 = (GradientDrawable) edtPincode.getBackground();
                    bgShape1.setColor(mContext.getResources().getColor(R.color.colorWhite));*/

                    layoutBillingAddress.setVisibility(View.VISIBLE);

                } else {
                    imgChecked.setImageResource(R.drawable.asset53);
                    BILLING_CHECKED = false;
/*
                    GradientDrawable bgShape = (GradientDrawable) edtBillingAddress.getBackground();
                    bgShape.setColor(Color.parseColor("#efeeee"));

                    GradientDrawable bgShape1 = (GradientDrawable) edtPincode.getBackground();
                    bgShape1.setColor(Color.parseColor("#efeeee"));*/

                    layoutBillingAddress.setVisibility(View.GONE);
                }
            }
        });

        layoutBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (GlobalValues.DELIVERY_ID.equalsIgnoreCase(CURRENT_AVAILABLITY_ID) || GlobalValues.BENTO_ID.equalsIgnoreCase(CURRENT_AVAILABLITY_ID)) {

            addSecondaryAddress();

        }
        txtRedeemClick_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtRedeemClick.performClick();
            }
        });

        txtRedeemClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                moveonRewardAppliedScreen();
            }
        });

        txtVoucherClick_new.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtVoucherClick.performClick();
            }
        });

        txtVoucherClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Double.parseDouble(voucherPointsValue) > 0) {
                    dialogVouchers();
                } else {
                    Toast.makeText(mContext, "You have no vouchers to redeem", Toast.LENGTH_SHORT).show();
                }
                //dialogVouchers();
            }
        });

        txtPromoClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Double.parseDouble(promoPointsValue) > 0) {

                    dialogPromotions();
                } else {
                    Toast.makeText(mContext, "You have no promotions to redeem", Toast.LENGTH_SHORT).show();
                }
            }
        });

        use_waller_amount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (use_waller_amount.isChecked()) {
                    //remove wallet amount
                    Log.e("add", String.valueOf(cart_sub_total) + "---" + walletBalance);
                    //updateWallet(Double.parseDouble(walletBalance), "APPLY");
                    walletDialogue();
                } else {
                    //add wallet amount
                    Log.e("remove", String.valueOf(cart_sub_total) + "----" + walletBalance);
                    if (layoutwallet.getVisibility() == View.VISIBLE) {
                        layoutwallet.setVisibility(View.GONE);
                        updateWallet(Double.parseDouble(used_eWalletAmount), "REMOVE");
                        isWalletApplied = false;
                    }
                    //updateWallet(Double.parseDouble(walletBalance), "REMOVE");
                }
            }
        });

        use_wallet_lyt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (isWalletApplied) {
                    //remove and update new
                    //updateWallet(Double.parseDouble(used_eWalletAmount), "REMOVE");
                    walletDialogue();
                } else {
                    //open wallet dialague
                    walletDialogue();
                }
            }
        });
        getTAT();
    }

    private void serviceChargeMethod() {
        layoutpackingcharge.setVisibility(View.VISIBLE);
        txtpacking_charge.setText(Utility.readFromSharedPreference(mContext,GlobalValues.PACKING_CHARGE));

        if (txtpacking_charge.getText().toString().length() != 0) {

            double packing_char = 0.0;

            if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {
                packing_char = 0.0;
            } else {
                packing_char = Double.valueOf(txtpacking_charge.getText().toString().replace("$", ""));
            }

            if (packing_char > 0) {
                packing_ser_charge = Double.valueOf(txtpacking_charge.getText().toString().replace("$", ""));
            } else {
                packing_ser_charge = 0.0;
            }

        } else {

            packing_ser_charge = 0.0;
        }


        Log.e("calculation_test:", String.valueOf(packing_ser_charge));
        mGrandTotal = cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge + packing_ser_charge;

        if (isWalletApplied) {
            mGrandTotal = mGrandTotal - Double.parseDouble(used_eWalletAmount);

        }



        txtGST.setText("$" + String.format("%.2f", mGST));
        if (Double.valueOf(mGrandTotal) < 0.00) {
            InclusiveGst(0.00);
        } else {
            InclusiveGst(mGrandTotal);
        }

        txtSubTotal.setText(subTotal);
        Log.e("dsjhahgfgdfg", String.valueOf(mGrandTotal));
        if (Double.valueOf(mGrandTotal) < 0.00 || Double.valueOf(mGrandTotal) == 0.00) {
            txtTotal.setText(String.format("%.2f", 0.00));
            //use_wallet_lyt.setVisibility(View.GONE);
            payByeWallet.setVisibility(View.GONE);
            payment_layout.setVisibility(View.GONE);
        } else {
//                txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
            txtTotal.setText(String.format("%.2f", mGrandTotal));
            //use_wallet_lyt.setVisibility(View.VISIBLE);
            Log.e("TAG","sdfaefdafchghdsa::"+ walletBalance);

        }
    }

    private void getRewardsPoints() {
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("member_id", Utility.readFromSharedPreference(mContext,GlobalValues.MEMBERSHIP_ID));
        String reward_url = GlobalUrl.MEMBER_ENQUIRY_URL;

        new MemberRewards(params).execute(reward_url);
    }

  /*  private void set_Selected_payment_method (String payment_method) {
        switch (payment_method) {

            case NETS_PAYMENT:
                enableOrDisableNets(true);
                break;

        }
    }*/
  /*  private void enableOrDisableNets (boolean shouldEnable) {
        if(shouldEnable) {
            nets_pay_check_box.setBackgroundResource(R.drawable.asset53);
            nets_pay_lyt.setVisibility(View.VISIBLE);
            updateUI();
        } else {
            nets_pay_check_box.setBackgroundResource(R.drawable.asset54);
            nets_pay_lyt.setVisibility(View.GONE);
        }
    }*/

    private void NetsPayservice(BottomSheetDialog dialog) {

        if(NofService.isInitialized()){
            checkAndProceedNetsPay(dialog);
        }else {
            initializeNOF(REGISTER,dialog);
        }
    }

    @SuppressLint("SuspiciousIndentation")
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
                            VGuardService.getINSTANCE().clearVos();
                            System.exit(1);
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
      //              showDialogue("Deregistration success: ", "Result: " + timeStamp);
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

    private void hideProgressDialogue() {
        if(progress != null && progress.isShowing())
            progress.dismiss();
    }

    private void showProgressDialogue() {
        progress = new ProgressDialog(mContext);
        progress.setMessage("Loading...");
        progress.show();
    }

    private void checkAndProceedNetsPay(BottomSheetDialog dialog) {
        if(isRegistrationSuccess()) {
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
        Debit debit = new Debit(formatAmountInCents(txtTotal.getText().toString().replace("$", "")));
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
        params.put("muid", formatCustomerId(Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID)));
        params.put("mid", NETSServices.MID);
        params.put("t0205", token);
        params.put("amt", formatAmountInCents(txtTotal.getText().toString().replace("$", "")));
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
        Log.e("TAG","PUrchaseRefTest::"+Utility.readFromSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER)
                +"\n"+is_pin_pad_request+"\n"+trans_reference_number+"\n"+Utility.readFromSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER));
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
                            placeCashOnDeliveryOrder();
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
                "CUSTOM : " +"Amount:"+formatAmountInCents(txtTotal.getText().toString().replace("$", ""))+"\n"+"ResponceCode"
                +responseCode+"\n"+"Crypto:"+table53.getData());
        Log.e("TAG","CryptoTest::"+table53);

        Debit debit = new Debit(formatAmountInCents(txtTotal.getText().toString()), responseCode, table53.getData());
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

    private void showDialogue(String title, String message, boolean show_message) {
        Handler handler = new Handler(Looper.getMainLooper());
        handler.post(() -> {
            androidx.appcompat.app.AlertDialog.Builder builder = new androidx.appcompat.app.AlertDialog.Builder(mContext)
                    .setTitle("NETS Bank Card")
                    .setMessage(show_message ? message : "Unexpected error has occurred.")
                    .setPositiveButton("ok", (dialog, which) -> dialog.dismiss());
            androidx.appcompat.app.AlertDialog dialog = builder.create();
            dialog.show();
        });
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

    public void doRegistration( ) {

        Registration registration = new Registration(NETSServices.MID, Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
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
        //params.put("t0102", otp);
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
        params.put("order_amount", "00");
        params.put("gmt", token);
        params.put("muid", formatCustomerId(Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID)));
        //params.put("muuid", MUUID = (MUUID == null || MUUID.isEmpty()) ? generateMUUID() : MUUID);
        params.put("mid", NETSServices.MID);
        Log.e("TAG", "Register_params : " + params.toString());
        //new NETSCardRegistration(params).execute(GlobalUrl.NETS_REGISTRATION);
        new NETSCardRegistration(params).execute(NETS_REGISTRATION);
    }

    public String formatCustomerId (String customer_id) {
        //return "000000000000".substring(0,12-customer_id.length()) + customer_id;
        return customer_id;
    }

    private boolean isRegistrationSuccess () {

        if((Utility.readFromSharedPreference(mContext, GlobalValues.NETS_REGISTERED) == null || Utility.readFromSharedPreference(mContext, GlobalValues.NETS_REGISTERED).isEmpty() ||
                Utility.readFromSharedPreference(mContext, GlobalValues.NETS_REGISTERED).equalsIgnoreCase("0")))
            return false;

        if(!(Utility.readFromSharedPreference(mContext, GlobalValues.TRANS_REF_NO) == null && Utility.readFromSharedPreference(mContext, GlobalValues.TRANS_REF_NO).isEmpty()) &&
                !(Utility.readFromSharedPreference(mContext, GlobalValues.NETS_CARD_NUMBER) == null && Utility.readFromSharedPreference(mContext, GlobalValues.NETS_CARD_NUMBER).isEmpty()) &&
                !(Utility.readFromSharedPreference(mContext, GlobalValues.NETS_EXPIRY) == null && Utility.readFromSharedPreference(mContext, GlobalValues.NETS_EXPIRY).isEmpty()))
            return true;
        else
            return false;
    }

    private int getCaResource() {
        return this.getResources().getIdentifier("netspay_nets_com_sg",
                "raw", this.getPackageName());
    }
   /* private int getCaResource() {
        return this.getResources().getIdentifier("ca_uat",
                "raw", this.getPackageName());
    }*/

    @Override
    protected void onResume() {
        super.onResume();

        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }

        layoutRewards.setVisibility(View.GONE);


        if (!GlobalValues.DELIVERY_DATE.equalsIgnoreCase("")) {
            setDate(GlobalValues.DELIVERY_DATE);
        }

        if (!GlobalValues.DELIVERY_TIME.equalsIgnoreCase("")) {
            setTime(GlobalValues.DELIVERY_TIME);
        }

        if (Utility.networkCheck(mContext)) {

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                layoutRewards.setVisibility(View.VISIBLE);
                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = "";
            } else {
                layoutRewards.setVisibility(View.GONE);
                mCustomerId = "";
                mReferenceId = Utility.getReferenceId(mContext);
            }

            CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
            String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + mCustomerId +
                    "&reference_id=" + mReferenceId +
                    "&availability_id=" + CURRENT_AVAILABLITY_ID;

            try {
                new CartListTask().execute(url).get();
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                getActiveCount();
            }

                /*new PromotionInfoTask(null).execute(GlobalUrl.PROMOTION_REDEEM_URL + "?app_id=" + GlobalValues.APP_ID +
                        "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID)
                        + "&availability_id=" + CURRENT_AVAILABLITY_ID);*/

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private class VouchersTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";

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
                        JSONObject jsonCommon = responseJSONObject.getJSONObject("common");

                        String imageUrl = jsonCommon.getString("image_source");

                        JSONObject jsonresultSet = responseJSONObject.getJSONObject("result_set");

                        JSONArray jsonArrayresult = jsonresultSet.getJSONArray("voucher_list");

                        JSONArray jsonUsedvoucherlist = jsonresultSet.getJSONArray("used_vouchers");

                        if (voucherCaseType.equalsIgnoreCase("0")) {
                            if (jsonArrayresult.length() > 0) {

                                voucherList = new ArrayList<>();

                                for (int i = 0; i < jsonArrayresult.length(); i++) {

                                    JSONObject jsonObject = jsonArrayresult.getJSONObject(i);

                                    VoucherListItem vouchers = new VoucherListItem();

                                    vouchers.setOrderItemProductId(jsonObject.getString("order_item_product_id"));
                                    vouchers.setOrderItemId(jsonObject.getString("order_item_id"));
                                    vouchers.setVoucherReedmed(jsonObject.getString("voucher_reedmed"));
                                    vouchers.setExpiryDate(jsonObject.getString("expiry_date"));
                                    vouchers.setItemName(jsonObject.getString("item_name"));
                                    vouchers.setItemQty(jsonObject.getString("item_qty"));
                                    vouchers.setItemUnitPrice(jsonObject.optString("item_unit_price"));
                                    vouchers.setItemSku(jsonObject.optString("item_sku"));
                                    vouchers.setItemProductId(jsonObject.optString("item_product_id"));
                                    vouchers.setOrderAvailabilityId(jsonObject.optString("order_availability_id"));
                                    vouchers.setOrderOutletId(jsonObject.optString("order_outlet_id"));
                                    vouchers.setProductThumbnail(jsonObject.optString("product_thumbnail"));
                                    vouchers.setProductShortDescription(jsonObject.optString("product_short_description"));
                                    vouchers.setProductLongDescription(jsonObject.optString("product_long_description"));
                                    vouchers.setProductVoucherPoints(jsonObject.optString("product_voucher_points"));
                                    vouchers.setProductVoucher(jsonObject.optString("product_voucher"));
                                    vouchers.setProductType(jsonObject.optString("product_type"));
                                    vouchers.setProductStatus(jsonObject.optString("product_status"));
                                    vouchers.setOrderItemVoucherBalanceQty(jsonObject.optString("order_item_voucher_balance_qty"));
                                    voucherList.add(vouchers);
                                }

                                VouchersAdapter mVoucherAdapter = new VouchersAdapter(OrderSummaryActivity.this, voucherList, imageUrl, "2");
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                                recyclerViewVouchers.setLayoutManager(mLayoutManager);
                                recyclerViewVouchers.setItemAnimator(new DefaultItemAnimator());
                                recyclerViewVouchers.setAdapter(mVoucherAdapter);
                                txtEmptyVoucher.setVisibility(View.GONE);
                                txtEmptyUsedVoucher.setVisibility(View.GONE);
                                recyclerViewVouchers.setVisibility(View.VISIBLE);
                                recyclerviewVouchersUsed.setVisibility(View.GONE);

                                mVoucherAdapter.SetOnItemClickListener(new VouchersAdapter.VoucherItemClick() {
                                    @Override
                                    public void onItemClick(View v, String productQty, final int i) {

                                        if (voucherList.get(i).getProductVoucher().equalsIgnoreCase("f")) {

                                            final String url = GlobalUrl.ADD_CART_VOUCHER_URL;
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
                                            params = new HashMap<String, String>();

                                            if (Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID).equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                                                params.put("availability_name", "DELIVERY");
                                            } else {
                                                params.put("availability_name", "TAKEAWAY");
                                            }

                                            params.put("order_availability_id", voucherList.get(i).getOrderAvailabilityId());
                                            params.put("order_item_id", voucherList.get(i).getOrderItemId());
                                            params.put("order_outlet_id", voucherList.get(i).getOrderOutletId());
                                            params.put("app_id", GlobalValues.APP_ID);
                                            params.put("product_id", voucherList.get(i).getItemProductId());
                                            //params.put("product_type", voucherList.get(i).getProductType());
                                            params.put("product_name", voucherList.get(i).getItemName());
                                            params.put("product_sku", voucherList.get(i).getItemSku());
                                            params.put("product_image", voucherList.get(i).getProductThumbnail());
                                            params.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                                            params.put("product_unit_price", "0");
                                            params.put("product_qty", productQty);
                                            params.put("product_total_price", "0");
                                            params.put("voucher_gift_name", "");
                                            params.put("voucher_gift_email", "");
                                            params.put("voucher_gift_message", "");
                                            params.put("product_voucher_points", voucherList.get(i).getProductVoucherPoints());
                                            params.put("reference_id", "");
                                            params.put("customer_id", mCustomerId);

                                            /*if (isApplyRedeem || isApplyPromo) {
                                                String message = "You are about to clear your Reward and Promotion discounts while redeem your voucher";

                                                new CheckOutMessageDialog(mContext, "Cancel", message, new CheckOutMessageDialog.OnSlectedMethod() {
                                                    @Override
                                                    public void selectedAction(String action) {
                                                        if (action.equalsIgnoreCase("YES")) {
                                                            new VoucherListTask(params, i).execute(url);
                                                        }
                                                    }
                                                });
                                            } else {
                                                new VoucherListTask(params, i).execute(url);
                                            }*/
                                            new VoucherListTask(params, i).execute(url);
                                        } else {

                                            String url = GlobalUrl.VOUCHER_REDEEM_URL;
                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("app_id", GlobalValues.APP_ID);
                                            params.put("product_qty", productQty);
                                            params.put("product_voucher_points", voucherList.get(i).getProductVoucherPoints());
                                            params.put("customer_id", mCustomerId);
                                            params.put("order_item_id", voucherList.get(i).getOrderItemId());
                                            new VoucherListTask(params, i).execute(url);
                                        }
                                    }
                                });
                            } else {
                                txtEmptyVoucher.setVisibility(View.VISIBLE);
                                txtEmptyUsedVoucher.setVisibility(View.GONE);
                                recyclerViewVouchers.setVisibility(View.GONE);
                                recyclerviewVouchersUsed.setVisibility(View.GONE);
                            }
                        } else {
                            if (jsonUsedvoucherlist.length() > 0) {

                                voucherUsedList = new ArrayList<>();

                                for (int i = 0; i < jsonUsedvoucherlist.length(); i++) {

                                    JSONObject jsonObject = jsonUsedvoucherlist.getJSONObject(i);

                                    VoucherUsedList vouchers = new VoucherUsedList();

                                    vouchers.setOrderItemProductId(jsonObject.getString("order_item_product_id"));
                                    vouchers.setOrderItemId(jsonObject.getString("order_item_id"));
                                    vouchers.setVoucherReedmed(jsonObject.getString("voucher_reedmed"));
                                    vouchers.setExpiryDate(jsonObject.getString("expiry_date"));
                                    vouchers.setItemName(jsonObject.getString("item_name"));
                                    vouchers.setItemQty(jsonObject.getString("item_qty"));
                                    vouchers.setItemUnitPrice(jsonObject.optString("item_unit_price"));
                                    vouchers.setItemSku(jsonObject.optString("item_sku"));
                                    vouchers.setItemProductId(jsonObject.optString("item_product_id"));
                                    vouchers.setOrderAvailabilityId(jsonObject.optString("order_availability_id"));
                                    vouchers.setOrderOutletId(jsonObject.optString("order_outlet_id"));
                                    vouchers.setProductThumbnail(jsonObject.optString("product_thumbnail"));
                                    vouchers.setProductShortDescription(jsonObject.optString("product_short_description"));
                                    vouchers.setProductLongDescription(jsonObject.optString("product_long_description"));
                                    vouchers.setProductVoucherPoints(jsonObject.optString("product_voucher_points"));
                                    vouchers.setProductVoucher(jsonObject.optString("product_voucher"));
                                    vouchers.setProductType(jsonObject.optString("product_type"));
                                    vouchers.setProductStatus(jsonObject.optString("product_status"));
                                    vouchers.setVoucherUsedCount(jsonObject.optString("voucher_used_count"));
                                    vouchers.setOrderItemVoucherBalanceQty(jsonObject.optString("order_item_voucher_balance_qty"));
                                    voucherUsedList.add(vouchers);
                                }

                                VoucherUsedAdapter mVoucherAdapter = new VoucherUsedAdapter(OrderSummaryActivity.this, voucherUsedList, imageUrl);
                                RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(mContext);
                                recyclerviewVouchersUsed.setLayoutManager(mLayoutManager);
                                recyclerviewVouchersUsed.setItemAnimator(new DefaultItemAnimator());
                                recyclerviewVouchersUsed.setAdapter(mVoucherAdapter);
                                txtEmptyVoucher.setVisibility(View.GONE);
                                txtEmptyUsedVoucher.setVisibility(View.GONE);
                                recyclerViewVouchers.setVisibility(View.GONE);
                                recyclerviewVouchersUsed.setVisibility(View.VISIBLE);
                            } else {
                                txtEmptyVoucher.setVisibility(View.GONE);
                                txtEmptyUsedVoucher.setVisibility(View.VISIBLE);
                                recyclerViewVouchers.setVisibility(View.GONE);
                                recyclerviewVouchersUsed.setVisibility(View.GONE);
                            }
                        }
                    } else {
                        message = responseJSONObject.getString("message");
                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart
                        recyclerViewVouchers.setVisibility(View.GONE);
                        recyclerviewVouchersUsed.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {

            }
        }
    }

    public class VoucherListTask extends AsyncTask<String, Void, String> {
        private ProgressDialog progressDialog;
        private Map<String, String> voucherParams;
        private int i;

        public VoucherListTask(Map<String, String> voucherParams, int i) {
            this.voucherParams = voucherParams;
            this.i = i;

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
            System.out.print("Voucher redeem");

            String response = WebserviceAssessor.postRequest(mContext, params[0], voucherParams);
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {
                if (dialogVoucher.isShowing()) {
                    dialogVoucher.dismiss();
                }

                JSONObject jsonObject = new JSONObject(s);
                Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

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
                        "&reference_id=" + mReferenceId +
                        "&availability_id=" + CURRENT_AVAILABLITY_ID;
                new CartListTask().execute(url);
                getActiveCount();

                if (voucherList.get(i).getProductVoucher().equalsIgnoreCase("f")) {

                    if (isApplyPromo) {
                        //removePromotion();

                    }
                    if (isApplyRedeem) {
                        //removeRewardPoints();

                    }
                    setOverallTotal(Double.valueOf(cartVoucherDiscountAmt), "APPLY", "V");
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private class PromotionInfoTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";

        ProgressDialog progressDialog;
        Dialog dialogPromo;

        public PromotionInfoTask(Dialog dialogPromo) {
            if (dialogPromo != null)
                this.dialogPromo = dialogPromo;
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
                        //Success

                        JSONObject resultcommon = responseJSONObject.getJSONObject("common");

                        String CommonImageUrl = resultcommon.getString("promo_image_source");

                        JSONObject resultjsonobject = responseJSONObject.getJSONObject("result_set");

                        JSONArray jsonArraymypromo = resultjsonobject.getJSONArray("my_promo");

                        promotionmodels = new ArrayList<>();

                        if (jsonArraymypromo.length() > 0) {

                            for (int i = 0; i < jsonArraymypromo.length(); i++) {

                                JSONObject jsonObjectpromo = jsonArraymypromo.getJSONObject(i);

                                Promotion promotionmodel = new Promotion();
                                promotionmodel.setmPromotionId(jsonObjectpromo.optString("promotion_id"));
                                promotionmodel.setmPromoCode(jsonObjectpromo.optString("promo_code"));

                                promotionmodel.setmPromoDaysleft(jsonObjectpromo.optString("promo_days_left"));

                                promotionmodel.setmPromotionImage(CommonImageUrl + "/" + jsonObjectpromo.getString("promotion_image"));

                                promotionmodel.setmPromotionTitle(jsonObjectpromo.optString("promotion_title"));

                                promotionmodel.setmPromotDiscription(jsonObjectpromo.getString("promo_desc"));

                                promotionmodel.setmPromotionPercentage(jsonObjectpromo.getString("promotion_percentage"));

                                promotionmodel.setmPromotionEndDate(jsonObjectpromo.getString("promotion_end_date"));

                                promotionmodel.setmPromodeshowText(jsonObjectpromo.getString("promo_desc_showtext"));

                                promotionmodel.setmPromotionMaxAmt(jsonObjectpromo.getString("promotion_max_amt"));

                                promotionmodel.setmPromotionType(jsonObjectpromo.getString("promotion_type"));


                                promotionmodels.add(promotionmodel);

                                Utility.writeToSharedPreference(mContext, GlobalValues.PROMOTIONCOUNT, "1");
                            }
                        }



                        if (promotionmodels.size() > 0) {
                            promotionBannerAdapter = new PromotionRecyclerAdapter(OrderSummaryActivity.this, OrderSummaryActivity.this,
                                    promotionmodels, GlobalValues.FROM_CHECKOUT, dialogPromo);
                            layoutManager = new LinearLayoutManager(mContext);
                            recyclerViewPromotions.setLayoutManager(layoutManager);
                            recyclerViewPromotions.setHasFixedSize(true);
                            recyclerViewPromotions.setItemAnimator(new DefaultItemAnimator());
                            recyclerViewPromotions.setAdapter(promotionBannerAdapter);
                            recyclerViewPromotions.setNestedScrollingEnabled(false);
                            recyclerViewPromotions.setVisibility(View.VISIBLE);
                            recyclerviewPromotionRedeem.setVisibility(View.GONE);
                            txtEmptyEarned.setVisibility(View.GONE);
                            txtEmptyRedeemed.setVisibility(View.GONE);
                           /* promotionBannerAdapter.setOnItemClick(new IOnItemClick() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    promotionCode = promotionmodels.get(position).getmPromoCode();
                                    promotionID = promotionmodels.get(position).getmPromotionId();
                                    promotionPosition = position;
                                    txtPromoApply.performClick();
                                    promotionBannerAdapter.notifyDataSetChanged();
                                }
                            });*/
                        } else {
                            recyclerViewPromotions.setVisibility(View.VISIBLE);
                            recyclerviewPromotionRedeem.setVisibility(View.GONE);
                            txtEmptyEarned.setVisibility(View.GONE);
                            txtEmptyRedeemed.setVisibility(View.GONE);
                        }
                    } else {
                        recyclerViewPromotions.setVisibility(View.GONE);
                        recyclerviewPromotionRedeem.setVisibility(View.GONE);
                        txtEmptyEarned.setVisibility(View.VISIBLE);
                        txtEmptyRedeemed.setVisibility(View.GONE);
                        message = responseJSONObject.getString("message");
                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            } else {
                recyclerViewPromotions.setVisibility(View.GONE);
                recyclerviewPromotionRedeem.setVisibility(View.GONE);
                txtEmptyEarned.setVisibility(View.VISIBLE);
                txtEmptyRedeemed.setVisibility(View.GONE);

            }
        }
    }

    public void promotionApply(String response) {
        if (dialogPromo != null && dialogPromo.isShowing()) {
            dialogPromo.dismiss();
        }
        mPromoCouponResponse = response;
         if (mPromoCouponResponse != null && mPromoCouponResponse.length() > 0) {
            layoutdiscount.setVisibility(View.VISIBLE);
            try {
                parseCouponPointResponse(mPromoCouponResponse);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private class PromotionRedeemedTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";
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
                        //Success

                        JSONObject resultjsonobject = responseJSONObject.getJSONObject("result_set");

                        JSONObject resultcommon = responseJSONObject.getJSONObject("common");


                        String CommonImageUrl = resultcommon.getString("promo_image_source");


                        JSONArray jsonArraymycat = resultjsonobject.getJSONArray("promo_history");

                        promotionRedeemedmodelArrayList = new ArrayList<>();

                        if (jsonArraymycat.length() > 0) {


                            for (int j = 0; j < jsonArraymycat.length(); j++) {

                                JSONObject jsonObjectcat = jsonArraymycat.getJSONObject(j);

                                promotionRedeemedmodel = new PromotionRedeemed();


                                promotionRedeemedmodel.setPromotionCode(jsonObjectcat.getString("promo_code"));

                                promotionRedeemedmodel.setPromotionId(jsonObjectcat.getString("promotion_id"));
                                promotionRedeemedmodel.setPromotionImage(CommonImageUrl + "/" + jsonObjectcat.getString("promotion_image"));
                                promotionRedeemedmodel.setPromotionTitle(jsonObjectcat.getString("promotion_title"));
                                promotionRedeemedmodel.setPromotionPercentage(jsonObjectcat.getString("promotion_percentage"));
                                promotionRedeemedmodel.setPromotDiscription(jsonObjectcat.getString("promo_desc"));
                                promotionRedeemedmodel.setPromoDaysleft(jsonObjectcat.optString("promo_days_left"));
                                promotionRedeemedmodel.setPromoMaxAmt(jsonObjectcat.getString("promotion_max_amt"));
                                promotionRedeemedmodel.setPromoType(jsonObjectcat.optString("promotion_type"));
                                promotionRedeemedmodelArrayList.add(promotionRedeemedmodel);
                            }
                        }


                        if (promotionRedeemedmodelArrayList.size() > 0) {

                            redeemRecyclerAdapter = new PromotionRedeemRecyclerAdapter(OrderSummaryActivity.this, promotionRedeemedmodelArrayList);
                            //GridLayoutManager promotionGridlay = new GridLayoutManager(getActivity(), 2, GridLayoutManager.VERTICAL, false);
                            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                            recyclerviewPromotionRedeem.setLayoutManager(layoutManager);
                            recyclerviewPromotionRedeem.setHasFixedSize(true);
                            recyclerviewPromotionRedeem.setItemAnimator(new DefaultItemAnimator());
                            recyclerviewPromotionRedeem.setAdapter(redeemRecyclerAdapter);
                            recyclerviewPromotionRedeem.setNestedScrollingEnabled(false);
                            recyclerViewPromotions.setVisibility(View.GONE);
                            recyclerviewPromotionRedeem.setVisibility(View.VISIBLE);
                            txtEmptyEarned.setVisibility(View.GONE);
                            txtEmptyRedeemed.setVisibility(View.GONE);
                        } else {
                            recyclerViewPromotions.setVisibility(View.GONE);
                            recyclerviewPromotionRedeem.setVisibility(View.GONE);
                            txtEmptyEarned.setVisibility(View.GONE);
                            txtEmptyRedeemed.setVisibility(View.VISIBLE);
                        }
                    } else {
                        message = responseJSONObject.getString("message");
                        recyclerViewPromotions.setVisibility(View.GONE);
                        recyclerviewPromotionRedeem.setVisibility(View.GONE);
                        txtEmptyEarned.setVisibility(View.GONE);
                        txtEmptyRedeemed.setVisibility(View.VISIBLE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void setPromotionSpan(String mPromotion) {
        promotionspannableString = new SpannableString(mPromotion);
        promotionspannableString.setSpan(new UnderlineSpan(), 0, mPromotion.length(), 0);
        /*spannableString.setSpan(new StyleSpan(Typeface.BOLD), 45, 69, 0);
        spannableString.setSpan(new StyleSpan(Typeface.BOLD), 74, mPolicy.length()-1, 0);*/
        promotionspannableString.setSpan(new ForegroundColorSpan(getResources().getColor(R.color.greendark)),
                0, mPromotion.length(), Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        //txtPromotions.setText("You have "+promotionspannableString+" Available");
        //txtPromotions.setText(promotionspannableString);
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
//        txtTime.setText(spannableTime);
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
        txtDate.setText(spannableDate);
    }

    private void addSecondaryAddress() {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
        } else {

            mCustomerId = Utility.getReferenceId(mContext);
        }

        try {
            JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

            JSONObject jsonPostalCodeInfo = outletJson.getJSONObject("postal_code_information");


            String url = GlobalUrl.ADD_SECONDARY_ADDRESS_URL;

            Map<String, String> params = new HashMap<>();
            params.put("app_id", GlobalValues.APP_ID);
            params.put("refrence", mCustomerId);
            params.put("customer_address_line1", jsonPostalCodeInfo.optString("zip_buno"));
            params.put("customer_postal_code", jsonPostalCodeInfo.optString("zip_code"));
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
        params.put("reference_id", mReferenceId);

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
                    //Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
                    Toast.makeText(mContext, "Invalid Date & Time", Toast.LENGTH_SHORT).show();
                    is_success = false;
                } else {
//                    Toast.makeText(mContext, "Success", Toast.LENGTH_SHORT).show();
                    if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).after(SelectedTime)) {
                        Toast.makeText(mContext, "Time exired", Toast.LENGTH_SHORT).show();
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

        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

            try {

                layoutDelivery.setVisibility(View.VISIBLE);
                layoutTakeaway.setVisibility(View.GONE);
                txt_delivery.setVisibility(View.VISIBLE);
                txt_takeaway_disable.setVisibility(View.VISIBLE);
                lly_orderforSomeone.setVisibility(View.VISIBLE);
                layout_delivery_enable.setVisibility(View.VISIBLE);
                layout_takeaway_enable.setVisibility(View.GONE);
                //layoutFreeDelivery.setVisibility(View.VISIBLE);


                txtOutletName.setText(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME) + Utility.readFromSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS)
                        + "\n" + "Singapore, " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_PINCODE) + "\n" + "#" +
                        Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_UNITNO1)
                        + "-" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_UNITNO2));

                outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");

               /* txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));*/


                txtAddress.setText(jsonPostalCodeInfo.optString("zip_buno") + ","
                        + jsonPostalCodeInfo.optString("zip_sname") + "\n Singapore, " +
                        jsonPostalCodeInfo.optString("zip_code")
                );

               /* edtBillingAddress.setText(GlobalValues.BILLING_ADDRESS);
                edtPincode.setText(GlobalValues.BILLING_PINCODE);
                edtBillingUnitNo1.setText(GlobalValues.BILLING_UNITNO1);
                edtBillingUnitNo2.setText(GlobalValues.BILLING_UNITNO2);*/


                if (outletZoneJson.getString("zone_delivery_charge").equalsIgnoreCase("null")
                        || outletZoneJson.getString("zone_delivery_charge").equalsIgnoreCase("0.00")) {
                    layoutDeliveryCharge.setVisibility(View.GONE);
                } else {
                    layoutDeliveryCharge.setVisibility(View.VISIBLE);
                    txtDeliveryCharge.setText(String.format("%.2f", Double.valueOf(outletZoneJson.getString("zone_delivery_charge"))));
                }


                if (outletZoneJson.getString("zone_additional_delivery_charge").equalsIgnoreCase("null") ||
                        outletZoneJson.getString("zone_additional_delivery_charge").equalsIgnoreCase("0.00")) {
                    layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                } else {
                    layoutAdditionalDeliveryCharge.setVisibility(View.VISIBLE);
                    txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f", Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                }

                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                if (foodVoucher) {
                    layoutDeliveryCharge.setVisibility(View.GONE);
                    layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                    txtDeliveryCharge.setText("0.00");
                    txtAdditionalDeliveryCharge.setText("0.00");
                }
               /* if (outletZoneJson.getString("zone_free_delivery").equalsIgnoreCase("0.00") ||
                        outletZoneJson.getString("zone_free_delivery").equalsIgnoreCase("null") ||
                        outletZoneJson.getString("zone_free_delivery").length() <= 0) {

                    layoutFreeDelivery.setVisibility(View.GONE);

                } else {
                    txtFreeDelivery.setText("$" + outletZoneJson.getString("zone_free_delivery") + " more to FREE delivery!");
                }*/

            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

            //layoutFreeDelivery.setVisibility(View.GONE);
            layoutDelivery.setVisibility(View.GONE);
            layoutTakeaway.setVisibility(View.VISIBLE);
            layoutDeliveryCharge.setVisibility(View.GONE);
            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
            txt_delivery.setVisibility(View.GONE);
            txt_takeaway_disable.setVisibility(View.GONE);
            txt_takeaway.setVisibility(View.VISIBLE);
//            txt_delivery_disable.setVisibility(View.VISIBLE);
            lly_orderforSomeone.setVisibility(View.GONE);
            layout_delivery_enable.setVisibility(View.GONE);
            layout_takeaway_enable.setVisibility(View.VISIBLE);


            outletText.setText(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));

        }
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

    private void setCustomProgress() {

        //  outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

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


        LinearLayout.LayoutParams layout_params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                15);

        progressBar = new ProgressBar(this, null, android.R.attr.progressBarStyleHorizontal);
        progressBar.setIndeterminate(false);

        r_sub_total = txtSubTotal.getText().toString().replace("$", "");

           /* if (Double.parseDouble(outletZoneJson.optString("zone_free_delivery")) > 0) {
                progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
                progressBar.setSecondaryProgress((int) Double.parseDouble
                        (outletZoneJson.optString("zone_free_delivery")));
            } else {*/

        progressBar.setProgress(1000);
        progressBar.setSecondaryProgress(1000);



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
        progressBar.setLayoutParams(layout_params);

        progressBar.setProgressDrawable(getResources().getDrawable(R.drawable.custom_progress_shape_bronze));

        if (layoutProgress != null) {
            layoutProgress.removeAllViews();
        }

        layoutProgress.addView(progressBar);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.txtRedeemApply:
                if (txtRedeemApply.getText().toString().equalsIgnoreCase("APPLY NOW")) {

                    if (txtRedeemPoints != null && txtRedeemPoints.length() > 0) {
                        try {
                            if (rewardStatus.equalsIgnoreCase("REMOVE")) {
                                removeRewardPoints();
                            }

                            Map<String, String> rewardsParams = new HashMap<>();
//                          FormBody.Builder formBuilder = new FormBody.Builder()
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
                } /*else if (txtRedeemApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                    removeRewardPoints();
                }*/
                break;

            case R.id.txtPromoApply:
                 if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID)) || (GlobalValues.promoID.equalsIgnoreCase(promotionID)) || GlobalValues.promoID.equalsIgnoreCase("")) {
                     if (promotionCode != null && promotionCode.length() > 0) {
                        if (GlobalValues.promoID.equalsIgnoreCase(promotionID)) {
                            removePromotion();
                        }
                        try {

                            String url = GlobalUrl.COUPON_CODE_URL;

                            JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext,
                                    GlobalValues.CART_RESPONSE).toString());

                            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

                            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");
                            String cartSubTotal;

                            if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID)) && !(GlobalValues.promoID.equalsIgnoreCase(""))) {
                                 cartSubTotal = String.valueOf(cart_sub_total + promotionAmount);
                            } else {
                                cartSubTotal = String.valueOf(cart_sub_total);
                            }/* else {

                                cartSubTotal = String.valueOf(Double.parseDouble(txtSubTotal.getText().toString().replace("$", "")));
                            }*/

                            try {
                                StringBuilder stringBuilder = new StringBuilder();
                                for (int i = 0; i < cartJsonArray.length(); i++) {
                                    JSONObject cartJson = cartJsonArray.getJSONObject(i);
                                    stringBuilder.append(cartJson.getString("cart_item_product_id") + "|" +
                                            cartJson.getString("cart_item_total_price") + "|" +
                                            cartJson.getString("cart_item_qty"));
                                }

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("promo_code", promotionCode);
                                params.put("availability_id", CURRENT_AVAILABLITY_ID);
                                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                //params.put("cart_amount", txtSubTotal.getText().toString().replace("$", "")); //cart_sub_total
                                 params.put("cart_amount", cartSubTotal);
                                params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
                                params.put("category_id", stringBuilder.toString());  //list of product ids
                                //    try {
                                //run(formBody);
                                new CouponCodeTask(params, edtPromotion.getText().toString().trim()).execute(url);

                            } catch (Exception e) {
                                e.printStackTrace();

                                Map<String, String> params = new HashMap<String, String>();
                                params.put("app_id", GlobalValues.APP_ID);
                                params.put("promo_code", promotionCode);
                                params.put("availability_id", CURRENT_AVAILABLITY_ID);
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
                }/* else {
                    removePromotion();
                }*/

                break;

            case R.id.txtRedeemClick:
//                openFiveMenuActivity(2);
                moveonRewardAppliedScreen();
                break;

            case R.id.txtPromotions:
                removePromotion();
                openFiveMenuActivity(4);
                break;


        }
    }

    public void removePromotion() {

        GlobalValues.PRMOTION_DELIVERY_APPLIED = false;
        GlobalValues.DISCOUNT_APPLIED = false;
        isApplyPromo = false;
        promo_subTotal = "";
        GlobalValues.promoID = "";
       /* new PromotionInfoTask(null).execute(GlobalUrl.PROMOTION_REDEEM_URL + "?app_id=" + GlobalValues.APP_ID +
                "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID)
                + "&availability_id=" + CURRENT_AVAILABLITY_ID);*/
        try {


        /*    outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

            double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(this.r_sub_total);

            if (d_progress_limit > 0) {

                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");

                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

            } else {
                GlobalValues.DELEIVERY_CHARGES = "0.00";

                txtDeliveryCharge.setText("$0.00");
            }*/


            setOverallTotal(Double.valueOf(txtDiscountTotal.getText().toString().replace("-$", "").replace(")", "")), "REMOVE", "P");
            //setOverallTotal(Double.valueOf(promotionmodels.get(promotionPosition).getmPromotionPercentage()), "REMOVE");


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
    }

    public void removeRewardPoints() {
        GlobalValues.DISCOUNT_APPLIED = false;
        isApplyRedeem = false;
        reward_subTotal = "";
        Log.e("TAG","RewardRemove::");


        r_applied = "No";
        r_point = "";
 //       r_amount = "";
        layoutdiscountRedeem.setVisibility(View.GONE);
        //enterPoints.setEnabled(true);
        txtDiscountTotalRedeem.setText("");
        GlobalValues.CUSTOMER_REWARD_POINT_UPDATE=0.0;
        redeemPointsValue= String.valueOf(GlobalValues.CUSTOMER_REWARD_POINT);
        txtRedeemAvailablePoints.setText(String.format("%.2f", GlobalValues.CUSTOMER_REWARD_POINT));
        txtRedeemAvailablePoints_new.setText("ST$ "+String.format("%.2f", GlobalValues.CUSTOMER_REWARD_POINT));
        txtRewardPoint.setText(String.format("%.2f",GlobalValues.CUSTOMER_REWARD_POINT) + " Points");
        Log.e("TAG","RewardRemove_1::");
        rewardPoints = "";
        rewardPointsupdate="";
        redeemPoints.setText("APPLY NOW");
        rewardStatus = "APPLY NOW";

        try {

      //      setOverallTotal(Double.valueOf(txtDiscountTotalRedeem.getText().toString().replace("-$", "").replace(")", "")), "REMOVE", "R");
            setOverallTotal(0.0, "REMOVE", "R");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void moveonRewardAppliedScreen() {

        if (!Utility.isCustomerLoggedIn(mContext)) {

            intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);

        } else {
            Log.e("TAG", "RedeemPointsTest::" + redeemPointsValue);
            if (!redeemPointsValue.equalsIgnoreCase("null")){
                if (Double.parseDouble(redeemPointsValue) > 0) {

                    rewardPoints = "You have " + redeemPointsValue + " points available";

                    dialogRewards();
                } else {
                    Toast.makeText(mContext, "You have 0 points to redeem", Toast.LENGTH_SHORT).show();
                }
        }

        }
    }

    private void dialogRewards() {
        dialogRedeem = new Dialog(mContext);
        dialogRedeem.setContentView(R.layout.dialog_rewardpoints);
        if (!dialogRedeem.isShowing())
            dialogRedeem.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogRedeem.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity = Gravity.CENTER;
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = ViewGroup.LayoutParams.FILL_PARENT;
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            window.setBackgroundDrawable(inset);
            window.setAttributes(lp);

            TextView txt_availableRewardPoints = dialogRedeem.findViewById(R.id.txt_availableRewardPoints);
            txtRewardPoint = dialogRedeem.findViewById(R.id.txtRewardPoint);
            enterPoints = dialogRedeem.findViewById(R.id.txtRedeemPoints);
            redeemPoints = dialogRedeem.findViewById(R.id.txtRedeemApply);
            ImageView img_close = dialogRedeem.findViewById(R.id.img_close);
            txt_ewallet = dialogRedeem.findViewById(R.id.txt_ewallet);


            enterPoints.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});
            if (!rewardPointsupdate.isEmpty()) {
                enterPoints.setText(rewardPointsupdate + "");
                redeemPoints.setText("REMOVE");
            }else {
                enterPoints.setHint(rewardPoints + "");
            }
            Log.e("TAG","Update_RewardTest::"+GlobalValues.CUSTOMER_REWARD_POINT_UPDATE+"\n"+redeemPointsValue);
            if (GlobalValues.CUSTOMER_REWARD_POINT_UPDATE>0) {
                txtRewardPoint.setText(String.format("%.2f",GlobalValues.CUSTOMER_REWARD_POINT_UPDATE)  + " Points");
                redeemPointsValue= String.valueOf(GlobalValues.CUSTOMER_REWARD_POINT_UPDATE);

            }else {
                txtRewardPoint.setText(redeemPointsValue + " Points");
            }

            txt_ewallet.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    openEwalletDialog();
                }
            });

            Double reward_point= Double.valueOf(redeemPointsValue);

            //redeemPoints.setText(rewardStatus);
            redeemPoints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Double enter_rew= Double.valueOf(enterPoints.getText().toString());
                    Float points_val= Float.valueOf(enterPoints.getText().toString());


                    if (reward_point<enter_rew){
                        Toast.makeText(mContext,"You have " + redeemPointsValue + " points available",Toast.LENGTH_SHORT).show();
                        return;
                    }
                    if (redeemPoints.getText().toString().equalsIgnoreCase("APPLY NOW")) {

                        Double rew_bal= Double.valueOf(String.format("%.2f", points_val));
                        if (cart_sub_total<rew_bal){
                            points_val=Float.valueOf(String.valueOf(cart_sub_total));
                //            Toast.makeText(mContext,"Your cart value " + String.format("%.2f", cart_sub_total)+ " only",Toast.LENGTH_SHORT).show();
                //            return;
                        }

                        if (enterPoints != null && enterPoints.length() > 0) {
                            if (rewardStatus.equalsIgnoreCase("REMOVE")) {
                                removeRewardPoints();
                            }


                         //   Double value=points_val * 100/100;
                            Log.e("TAG","RewardPointsTest::"+points_val+"\n"+cart_sub_total+"\n");

                            GlobalValues.DISCOUNT_APPLIED = true;
                            GlobalValues.DISCOUNT_TYPE = "REWARD";
                            isApplyRedeem = true;

                            Double total_rew=GlobalValues.CUSTOMER_REWARD_POINT;

                            GlobalValues.CUSTOMER_REWARD_POINT_UPDATE = total_rew - points_val;

                            if (GlobalValues.CUSTOMER_REWARD_POINT_UPDATE>0) {
                                txtRewardPoint.setText(String.format("%.2f",GlobalValues.CUSTOMER_REWARD_POINT_UPDATE)  + " Points");
                                redeemPointsValue= String.valueOf(GlobalValues.CUSTOMER_REWARD_POINT_UPDATE);

                            }else {
                                txtRewardPoint.setText(redeemPointsValue + " Points");
                            }

                            layoutdiscountRedeem.setVisibility(View.VISIBLE);
                            txtDiscountTotalRedeem.setText(String.format("%.2f", points_val) + ")");
                            reward_subTotal = txtSubTotal.getText().toString().replace("$", "");

                            dialogRedeem.dismiss();
                            r_applied = "Yes";
                            r_point = String.valueOf(points_val);
                            enterPoints.setText(points_val + "");
                            rewardPoints = points_val + "";
                            rewardPointsupdate= String.valueOf(points_val);
                            r_amount = String.format("%.2f", points_val);



                            txtRedeemAvailablePoints.setText(String.format("%.2f", GlobalValues.CUSTOMER_REWARD_POINT_UPDATE));
                            txtRedeemAvailablePoints_new.setText("ST$ "+String.format("%.2f", GlobalValues.CUSTOMER_REWARD_POINT_UPDATE));
                            rewardStatus = "REMOVE";
                            redeemPoints.setText("REMOVE");

                            try {
                                setOverallTotal(points_val, "APPLY", "R");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }

                        } else {
                            Toast.makeText(mContext, "Please enter points", Toast.LENGTH_SHORT).show();
                        }
                    }else {

                        removeRewardPoints();
                    }
                }
            });

            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogRedeem.dismiss();
                }
            });
        }
    }


    @SuppressLint("SetTextI18n")
    private void walletDialogue() {
        dialogRedeem = new Dialog(mContext);
        dialogRedeem.setContentView(R.layout.dialog_wallet);
        if (!dialogRedeem.isShowing())
            dialogRedeem.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogRedeem.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity = Gravity.CENTER;
            lp.width = ViewGroup.LayoutParams.MATCH_PARENT;
            lp.height = ViewGroup.LayoutParams.WRAP_CONTENT;
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            window.setBackgroundDrawable(inset);
            window.setAttributes(lp);

            TextView txt_availableRewardPoints = dialogRedeem.findViewById(R.id.txt_availableRewardPoints);
            txtRewardPoint = dialogRedeem.findViewById(R.id.txtRewardPoint);
            enterPoints = dialogRedeem.findViewById(R.id.txtRedeemPoints);
            redeemPoints = dialogRedeem.findViewById(R.id.txtRedeemApply);
            ImageView img_close = dialogRedeem.findViewById(R.id.img_close);
            enterPoints.setHint("You have $" + walletBalance + " available");
            txtRewardPoint.setText("Wallet Balance : $" + walletBalance);
            enterPoints.setFilters(new InputFilter[]{new DecimalDigitsInputFilter(5, 2)});
            //redeemPoints.setText(rewardStatus);
            redeemPoints.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    if (enterPoints != null && enterPoints.length() > 0) {

                        if (isWalletApplied) {
                            //remove and update new
                            updateWallet(Double.parseDouble(used_eWalletAmount), "REMOVE");
                        }
                        try {
                            Map<String, String> wallet_params = new HashMap<>();
                            wallet_params.put("app_id", GlobalValues.APP_ID);
                            wallet_params.put("ewallet_amount", enterPoints.getText().toString());
                            wallet_params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                            //used_eWalletAmount = enterPoints.getText().toString();


                            String url = GlobalUrl.CHECK_EWALLET;
                            //rewardsParams.put("cart_amount", txtSubTotal.getText().toString().replace("$", ""));  //cart_sub_total
                            if (Utility.networkCheck(mContext)) {
                                dialogRedeem.dismiss();
                                hideKeyboard(OrderSummaryActivity.this);
                                new WalletTask(wallet_params).execute(url);
                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(mContext, "Please enter amount", Toast.LENGTH_SHORT).show();
                    }

                    /*if (redeemPoints.getText().toString().equalsIgnoreCase("APPLY NOW")) {

                        if (enterPoints != null && enterPoints.length() > 0) {
                            if (rewardStatus.equalsIgnoreCase("REMOVE")) {
                                removeRewardPoints();
                            }
                            try {
                                Map<String, String> rewardsParams = new HashMap<>();
                                rewardsParams.put("app_id", GlobalValues.APP_ID);
                                rewardsParams.put("redeem_point", enterPoints.getText().toString());
                                rewardsParams.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                rewardsParams.put("cart_amount", txtSubTotal.getText().toString().replace("$", ""));  //cart_sub_total
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
                            Toast.makeText(mContext, "Please enter points", Toast.LENGTH_SHORT).show();
                        }
                    } *//*else if (redeemPoints.getText().toString().equalsIgnoreCase("REMOVE")) {
                        removeRewardPoints();
                    }*/
                }
            });

            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialogRedeem.dismiss();
                    use_waller_amount.setChecked(false);

                    if (!isWalletApplied) {
                        eWalletTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                        payByeWallet.setBackgroundResource(R.drawable.payment_outline);
                        paymentType = "";
                    }

                }
            });
        }
    }

    private class WalletTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog progressDialog;
        private Map<String, String> Params;

        WalletTask(Map<String, String> Params) {
            this.Params = Params;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();


            progressDialog = new ProgressDialog(OrderSummaryActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
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


                JSONObject responseObj = null;
                try {
                    responseObj = new JSONObject(response);
                    if (responseObj.optString("status").equalsIgnoreCase("ok")) {
                        //set wallet amount
                        layoutwallet.setVisibility(View.VISIBLE);
                        used_eWalletAmount = responseObj.getString("ewallet_amount");

                        txtWalletTotal.setText(String.format("%.2f", Double.parseDouble(used_eWalletAmount)) + ")");
                        updateWallet(Double.parseDouble(used_eWalletAmount), "APPLY");
                        Toast.makeText(mContext, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                        isWalletApplied = true;
                    } else {
                        used_eWalletAmount = "";
                        isWalletApplied = false;
                        use_waller_amount.setChecked(false);

                        eWalletTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                        payByeWallet.setBackgroundResource(R.drawable.payment_outline);
                        paymentType = "";

                        Toast.makeText(mContext, responseObj.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                /*try {
                    parseRewardPointResponse(response);
                } catch (JSONException e) {
                    e.printStackTrace();
                }*/


            }

//

        }
    }

    private void dialogVouchers() {
        TabLayout tabLayoutVouchers;
        String[] voucherTabs = {"E-Vouchers", "Used E-Vouchers"};
        final View view1, view3;
        ImageView img_close;
        dialogVoucher = new Dialog(mContext);
        dialogVoucher.setContentView(R.layout.dialog_voucher);
        if (!dialogVoucher.isShowing())
            dialogVoucher.show();
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogVoucher.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity = Gravity.CENTER;
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = ViewGroup.LayoutParams.FILL_PARENT;
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            window.setBackgroundDrawable(inset);
            window.setAttributes(lp);

            tabLayoutVouchers = dialogVoucher.findViewById(R.id.tabLayoutVouchers);
            txtEmptyVoucher = dialogVoucher.findViewById(R.id.txtEmptyVoucher);
            txtEmptyUsedVoucher = dialogVoucher.findViewById(R.id.txtEmptyUsedVoucher);
            recyclerViewVouchers = dialogVoucher.findViewById(R.id.recyclerViewVouchers);
            recyclerviewVouchersUsed = dialogVoucher.findViewById(R.id.recyclerviewVouchersUsed);
            img_close = dialogVoucher.findViewById(R.id.img_close);

            view1 = dialogVoucher.findViewById(R.id.view1);
            view3 = dialogVoucher.findViewById(R.id.view3);
            view1.setVisibility(View.VISIBLE);

            if (networkCheck()) {
                new VouchersTask().execute(GlobalUrl.VOUCHER_URL + "?app_id=" +
                        GlobalValues.APP_ID + "&customer_id=" +
                        Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                voucherCaseType = "0";
            } else {
                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
            }

            for (int k = 0; k < voucherTabs.length; k++) {
                View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tablayout_item_promotion, null);
                TextView txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_promotion);
                txtTabItem.setText(voucherTabs[k]);
                // txtTabItem.setTextColor(getResources().getColor(R.color.colorBlack));
                txtTabItem.setText(voucherTabs[k]);
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
                tabLayoutVouchers.addTab(tabLayoutVouchers.newTab().setCustomView(tabView));
            }

            tabLayoutVouchers.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    assert view != null;
                    TextView txtTabItem = view.findViewById(R.id.txtTabItem_promotion);
                    txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));

                    switch (tab.getPosition()) {
                        case 0:
                            view1.setVisibility(View.VISIBLE);
                            view3.setVisibility(View.GONE);
                            voucherCaseType = "0";
                            if (networkCheck()) {
                                new VouchersTask().execute(GlobalUrl.VOUCHER_URL + "?app_id=" +
                                        GlobalValues.APP_ID + "&customer_id=" +
                                        Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                            } else {
                                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                            }
                            break;

                        case 1:
                            view1.setVisibility(View.GONE);
                            view3.setVisibility(View.VISIBLE);
                            voucherCaseType = "1";
                            if (networkCheck()) {
                                new VouchersTask().execute(GlobalUrl.VOUCHER_URL + "?app_id=" +
                                        GlobalValues.APP_ID + "&customer_id=" +
                                        Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                            } else {
                                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                            }
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    assert view != null;

                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogVoucher.dismiss();
                    voucherCaseType = "0";
                }
            });
        }
    }

    private void dialogPromotions() {
        TabLayout tabLayoutPromotion;
        String[] promotionTabs = {"Promo Earned", "Promo Redeemed"};
        final View view1, view3;
        ImageView img_close;

        dialogPromo = new Dialog(mContext);
        dialogPromo.setContentView(R.layout.dialog_fragment);
        try {
            if (!(dialogPromo.isShowing())) {
                dialogPromo.show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
        Window window = dialogPromo.getWindow();

        if (window != null) {
            lp.copyFrom(window.getAttributes());

            lp.gravity = Gravity.CENTER;
            lp.width = android.view.ViewGroup.LayoutParams.FILL_PARENT;
            lp.height = ViewGroup.LayoutParams.FILL_PARENT;
            ColorDrawable back = new ColorDrawable(Color.TRANSPARENT);
            InsetDrawable inset = new InsetDrawable(back, 20);
            window.setBackgroundDrawable(inset);
            window.setAttributes(lp);


            final EditText edtPromotion = dialogPromo.findViewById(R.id.edtPromotion);
            RelativeLayout layoutApply = dialogPromo.findViewById(R.id.layoutApply);

            layoutApply.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (edtPromotion.getText().toString().length() > 0) {
                        mPromocode = edtPromotion.getText().toString();

                        if (networkCheck()) {
                            applyCoupon(mPromocode);
                            //applyCoupon(productdatas.get(position).getmPromoCode());
                            //promotionID = productdatas.get(position).getmPromotionId();
                            /*if (GlobalValues.promoID.equalsIgnoreCase(promotionID)) {
                                orderSummaryActivity.removePromotion();
                            }*/
                        } else {
                            Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                        }
                    } else {
                        Toast.makeText(mContext, "Please enter your  Promo/Invite code !", Toast.LENGTH_LONG).show();
                    }
                }
            });

            tabLayoutPromotion = dialogPromo.findViewById(R.id.tabLayoutPromotion);
            txtEmptyEarned = dialogPromo.findViewById(R.id.txtEmptyEarned);
            txtEmptyRedeemed = dialogPromo.findViewById(R.id.txtEmptyRedeemed);
            recyclerViewPromotions = dialogPromo.findViewById(R.id.recyclerViewPromotions);
            recyclerviewPromotionRedeem = dialogPromo.findViewById(R.id.recyclerviewPromotionRedeem);
            img_close = dialogPromo.findViewById(R.id.img_close);

            view1 = (View) dialogPromo.findViewById(R.id.view1);
            view3 = (View) dialogPromo.findViewById(R.id.view3);
            view1.setVisibility(View.VISIBLE);

            new PromotionInfoTask(dialogPromo).execute(GlobalUrl.PROMOTION_REDEEM_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) + "&availability_id=");

            for (int k = 0; k < promotionTabs.length; k++) {
                View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tablayout_item_promotion, null);
                TextView txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_promotion);
                txtTabItem.setText(promotionTabs[k]);
                // txtTabItem.setTextColor(getResources().getColor(R.color.colorBlack));
                txtTabItem.setText(promotionTabs[k]);
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
                tabLayoutPromotion.addTab(tabLayoutPromotion.newTab().setCustomView(tabView));
            }

            tabLayoutPromotion.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    assert view != null;
                    TextView txtTabItem = view.findViewById(R.id.txtTabItem_promotion);
                    txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));

                    switch (tab.getPosition()) {
                        case 0:
                            view1.setVisibility(View.VISIBLE);
                            view3.setVisibility(View.GONE);
                            if (networkCheck()) {
                                new PromotionInfoTask(dialogPromo).execute(GlobalUrl.PROMOTION_REDEEM_URL + "?app_id=" + GlobalValues.APP_ID +
                                        "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) + "&availability_id=");
                            } else {
                                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();
                            }
                            break;

                        case 1:
                            view1.setVisibility(View.GONE);
                            view3.setVisibility(View.VISIBLE);
                            if (networkCheck()) {

                                new PromotionRedeemedTask().execute(GlobalUrl.PROMOTION_REDEEM_URL + "?status=A&app_id=" + GlobalValues.APP_ID
                                        + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

                            } else {

                                Toast.makeText(mContext, "No Internet Connection", Toast.LENGTH_LONG).show();

                            }
                            break;
                    }
                }

                @Override
                public void onTabUnselected(TabLayout.Tab tab) {
                    View view = tab.getCustomView();
                    assert view != null;
                    TextView txtTabItem = view.findViewById(R.id.txtTabItem_promotion);
                    txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
                }

                @Override
                public void onTabReselected(TabLayout.Tab tab) {

                }
            });

            img_close.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    dialogPromo.dismiss();
                }
            });
        }
    }

    public void applyCoupon(String couponCode) {

        try {

            String url = GlobalUrl.COUPON_CODE_URL;

            JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE).toString());

            JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

            JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

            try {
                StringBuilder stringBuilder = new StringBuilder();
                for (int i = 0; i < cartJsonArray.length(); i++) {
                    JSONObject cartItem = cartJsonArray.getJSONObject(i);
                    if (i != (cartJsonArray.length() - 1))
                        stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty") + ";");
                    else {
                        stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty"));
                    }
                }

                Map<String, String> params = new HashMap<String, String>();
                params.put("app_id", GlobalValues.APP_ID);
                params.put("promo_code", couponCode);
                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

                String cartSubTotal = "";

                if (GlobalValues.promoID.equalsIgnoreCase("") && isApplyRedeem) {
                    if (mPromotionAmount.equalsIgnoreCase("")) {
                        cartSubTotal = String.valueOf(cart_sub_total);
                    } else {
                        cartSubTotal = String.valueOf(cart_sub_total) + Double.parseDouble(mPromotionAmount);
                    }

                } else if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID)) && !(GlobalValues.promoID.equalsIgnoreCase(""))) {
                     cartSubTotal = String.valueOf(cart_sub_total + promotionAmount);
                } else {
                     cartSubTotal = jsonCartDetails.getString("cart_sub_total");
                }

                //cartSubTotal = jsonCartDetails.getString("cart_sub_total");

                params.put("cart_amount", cartSubTotal); //cart_sub_total
                params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
                params.put("category_id", stringBuilder.toString());
                params.put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));//list of product ids
                //    try {

                //run(formBody);

                new CouponCodeTask(params, couponCode).execute(url);

            } catch (Exception e) {
                e.printStackTrace();

                Map<String, String> params = new HashMap<String, String>();
                params.put("app_id", GlobalValues.APP_ID);
                params.put("promo_code", couponCode);
                params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                params.put("cart_amount", ""); //cart_sub_total
                params.put("cart_quantity", "");
                params.put("category_id", "");  //list of product ids

                new CouponCodeTask(params, couponCode).execute(url);

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


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
                    cartListTask = false;
                    mPromoCouponResponse = data.getStringExtra("PROMO_RESPONSE");

                    if (mPromoCouponResponse != null && mPromoCouponResponse.length() > 0) {

                        layoutdiscount.setVisibility(View.VISIBLE);
                        /*if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID)) || GlobalValues.promoID.equalsIgnoreCase("")) {

                        } else {

                        }*/
                        try {
                            //removePromotion();
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


            progressDialog = new ProgressDialog(OrderSummaryActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {

            Log.e("Apply_reward_service:", strings[0] + "\n" + Params);

            String response = WebserviceAssessor.postRequest(mContext, strings[0], Params);

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {
                Log.e("RewarPoint_Response:", response);

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

            /*if (edtPromotion.getText().toString().length() > 0) {
                try {
                    setOverallTotal(Double.valueOf(txtDiscountTotal.getText().toString().replace("-$", "").replace(")","")), "REMOVE");
                    txtDiscountTotal.setText("");
                    txtPromoApply.setText("APPLY NOW");
                    edtPromotion.setText("");
                    edtPromotion.setEnabled(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }*/

            GlobalValues.DISCOUNT_APPLIED = true;
            GlobalValues.DISCOUNT_TYPE = "REWARD";
            isApplyRedeem = true;

            //txtDiscountLabel.setText("Redeem points");

            Toast.makeText(mContext, "" + responseObj.optString("message"), Toast.LENGTH_SHORT).show();
            layoutdiscountRedeem.setVisibility(View.VISIBLE);
            JSONObject resultObj = responseObj.getJSONObject("result_set");
            txtDiscountTotalRedeem.setText(String.format("%.2f", resultObj.optDouble("points_amount")) + ")");
            reward_subTotal = resultObj.optString("cart_amount");
            /*InclusiveGst(Double.parseDouble(String.valueOf(resultObj.optDouble("points_amount")))  +  Double.parseDouble(txtSubTotal.getText().toString()));
            txtTotal.setText(String.format("%.2f", mGrandTotal+gstAmount));*/
            try {
                setOverallTotal(resultObj.optDouble("points_amount"), "APPLY", "R");
            } catch (Exception e) {
                e.printStackTrace();
            }
            dialogRedeem.dismiss();
            r_applied = "Yes";
            r_point = resultObj.optString("points_used");
            enterPoints.setText(resultObj.optDouble("points_used") + "");
            rewardPoints = resultObj.optDouble("points_used") + "";
            r_amount = String.valueOf(resultObj.optDouble("points_amount"));
            //enterPoints.setEnabled(false);
            //redeemPoints.setText("REMOVE");
            rewardStatus = "REMOVE";
            hideKeyboard(OrderSummaryActivity.this);
        } else {
            Toast.makeText(mContext, responseObj.optString("message"), Toast.LENGTH_SHORT).show();
        }
    }

    private void updateWallet01(double wallet_amount, String value) {


        if (value.equalsIgnoreCase("APPLY")) {
            if (cart_sub_total != 0.00) {
                cart_sub_total = cart_sub_total - wallet_amount;

            } else {
                cart_sub_total = 0.0;
            }
        } else {
            cart_sub_total = cart_sub_total + wallet_amount;

            used_eWalletAmount = "";
            isWalletApplied = false;
        }

        if (cart_sub_total < 0.00 || cart_sub_total == 0.00) {

            txtTotal.setText(String.format("%.2f", 0.00));
            //use_wallet_lyt.setVisibility(View.GONE);
            payByeWallet.setVisibility(View.GONE);
        } else {

            txtTotal.setText(String.format("%.2f", cart_sub_total));
            //use_wallet_lyt.setVisibility(View.VISIBLE);
            payByeWallet.setVisibility(View.VISIBLE);
        }

        txtGST.setText("$" + String.format("%.2f", mGST));
        if (cart_sub_total < 0.00) {

            InclusiveGst(0.00);
        } else {

            InclusiveGst(cart_sub_total);
        }
    }

//    private void updateWallet00(double wallet_amount, String value) {
//
//        double cartGrandTotal = 0.0;
//
//        //=======================================
//        if (txtDeliveryCharge.getText().toString().length() != 0) {
//
//            double delivery = 0.0;
//
//            if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {
//                delivery = 0.0;
//            } else {
//                delivery = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
//            }
//
//            if (delivery > 0) {
//                cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
//            } else {
//                cart_deleivery_charge = 0.0;
//            }
//
//        } else {
//
//            cart_deleivery_charge = 0.0;
//        }
//
//
//        if (txtAdditionalDeliveryCharge.getText().toString().length() != 0) {
//            cart_adddeleivery_charge = Double.valueOf(txtAdditionalDeliveryCharge.getText().toString().replace("$", ""));
//        } else {
//            cart_adddeleivery_charge = 0.0;
//        }
//
//        if (foodVoucher) {
//            cart_deleivery_charge = 0.0;
//            cart_adddeleivery_charge = 0.0;
//            layoutDeliveryCharge.setVisibility(View.GONE);
//            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
//        }
//
//
//        cartGrandTotal = cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge;
//        //=======================================
//
//
//        if (value.equalsIgnoreCase("APPLY")) {
//            if (cartGrandTotal != 0.00) {
//                cartGrandTotal = cartGrandTotal - wallet_amount;
//
//            } else {
//                cartGrandTotal = 0.0;
//            }
//        } else {
//            cartGrandTotal = cartGrandTotal - wallet_amount;
//            cartGrandTotal = cartGrandTotal + wallet_amount;
//
//            used_eWalletAmount = "";
//            isWalletApplied = false;
//        }
//
//        if (cartGrandTotal < 0.00 || cartGrandTotal == 0.00) {
//
//            txtTotal.setText(String.format("%.2f", 0.00));
//            //use_wallet_lyt.setVisibility(View.GONE);
//            payByeWallet.setVisibility(View.GONE);
//        } else {
//
//            txtTotal.setText(String.format("%.2f", cartGrandTotal));
//            //use_wallet_lyt.setVisibility(View.VISIBLE);
//            payByeWallet.setVisibility(View.VISIBLE);
//        }
//
//        txtGST.setText("$" + String.format("%.2f", mGST));
//        if (cartGrandTotal < 0.00) {
//
//            InclusiveGst(0.00);
//        } else {
//
//            InclusiveGst(cartGrandTotal);
//        }
//    }

    private void updateWallet(double wallet_amount, String value) {


        if (value.equalsIgnoreCase("APPLY")) {
            if (mGrandTotal != 0.00) {
                mGrandTotal = mGrandTotal - wallet_amount;

            } else {
                mGrandTotal = 0.0;
            }
        } else {
            mGrandTotal = mGrandTotal + wallet_amount;

            used_eWalletAmount = "";
            isWalletApplied = false;
        }

        if (mGrandTotal < 0.00 || mGrandTotal == 0.00) {

            txtTotal.setText(String.format("%.2f", 0.00));
            //use_wallet_lyt.setVisibility(View.GONE);
            payByeWallet.setVisibility(View.GONE);
            payment_layout.setVisibility(View.GONE);
        } else {

            txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
//            txtTotal.setText(String.format("%.2f", mGrandTotal));
            //use_wallet_lyt.setVisibility(View.VISIBLE);
            payment_layout.setVisibility(View.VISIBLE);
            payByeWallet.setVisibility(View.VISIBLE);
        }

        txtGST.setText("$" + String.format("%.2f", mGST));
        if (mGrandTotal < 0.00) {

            InclusiveGst(0.00);
        } else {

            InclusiveGst(mGrandTotal);
        }
    }

    private void setOverallTotal(double points_amount, String val, String discountType) throws Exception {

        //couponResponseFromFiveMenu = "";
        if (val.equalsIgnoreCase("APPLY")) {


            if (cart_sub_total != 0.00) {
                cart_sub_total = cart_sub_total - points_amount;
                /*cart_sub_total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) - points_amount;
              */
                //cart_sub_total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) - points_amount;
                //txtSubTotal.setText(String.format("%.2f", cart_sub_total));
            } else {
                cart_sub_total = 0.0;
            }

            if (txtDeliveryCharge.getText().toString().length() != 0) {

                double delivery = 0.0;

                if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {
                    delivery = 0.0;
                } else {
                    delivery = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
                }

                if (delivery > 0) {
                    cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
                } else {
                    cart_deleivery_charge = 0.0;
                }

            } else {

                cart_deleivery_charge = 0.0;
            }




            if (txtAdditionalDeliveryCharge.getText().toString().length() != 0) {
                cart_adddeleivery_charge = Double.valueOf(txtAdditionalDeliveryCharge.getText().toString().replace("$", ""));
            } else {
                cart_adddeleivery_charge = 0.0;
            }

            if (foodVoucher) {
                cart_deleivery_charge = 0.0;
                cart_adddeleivery_charge = 0.0;
                layoutDeliveryCharge.setVisibility(View.GONE);
                layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
            }

            Log.e("calculation_test:", String.valueOf(cart_sub_total + " " + cart_deleivery_charge +
                    " " + cart_adddeleivery_charge+" "+packing_ser_charge));
            mGrandTotal = cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge+packing_ser_charge;

            if (isWalletApplied) {
                mGrandTotal = mGrandTotal - Double.parseDouble(used_eWalletAmount);

            }



            txtGST.setText("$" + String.format("%.2f", mGST));
            if (Double.valueOf(mGrandTotal) < 0.00) {
                InclusiveGst(0.00);
            } else {
                InclusiveGst(mGrandTotal);
            }

            txtSubTotal.setText(subTotal);
            //txtTotal.setText(String.format("%.2f", mGrandTotal+gstAmount));
             if (Double.valueOf(mGrandTotal) < 0.00 || Double.valueOf(mGrandTotal) == 0.00) {
                txtTotal.setText(String.format("%.2f", 0.00));
                //use_wallet_lyt.setVisibility(View.GONE);
                payByeWallet.setVisibility(View.GONE);
                payment_layout.setVisibility(View.GONE);
            } else {
//                txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
                txtTotal.setText(String.format("%.2f", mGrandTotal));
                //use_wallet_lyt.setVisibility(View.VISIBLE);
                Log.e("TAG","sdfaefdafchgh::"+ walletBalance);
                if (!walletBalance.isEmpty()) {
                    if (Double.parseDouble(walletBalance) > 0) {
                        payByeWallet.setVisibility(View.VISIBLE);
                        payment_layout.setVisibility(View.VISIBLE);
                    }
                }
            }


        } else if (val.equalsIgnoreCase("REMOVE")) {

            Double con= Double.valueOf(r_amount);
            cart_sub_total =cart_sub_total + con;
            Log.e("TAG","RewardRemove::"+cart_sub_total+"\n"+r_amount);
            r_amount = "";
            if (txtSubTotal.getText().toString().length() != 0) {
                if (!(GlobalValues.promoID.equalsIgnoreCase(""))) {
                    if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID))) {
                         //cart_sub_total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) + promotionAmount;
                        cart_sub_total = cart_sub_total + promotionAmount;
                    } else {
                         //cart_sub_total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) + points_amount;
                        cart_sub_total = cart_sub_total + points_amount;
                    }
                } else {

                    //cart_sub_total = Double.valueOf(txtSubTotal.getText().toString().replace("$", "")) + points_amount;
                    cart_sub_total = cart_sub_total + points_amount;
                }

                //cart_sub_total = Double.valueOf(subTotal);
                //txtSubTotal.setText( String.format("%.2f", cart_sub_total));

            } else {
                cart_sub_total = 0.0;
            }

            if (txtDeliveryCharge.getText().toString().length() != 0) {

                double delivery = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));

                outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(this.r_sub_total);

                if (d_progress_limit > 0) {

                    cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
                } else {

                    if (delivery > 0) {

                        cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));
//                        cart_deleivery_charge = 0.0;

                    } else {

                        cart_deleivery_charge = 0.0;

                    }

                }


//                cart_deleivery_charge = Double.valueOf(txtDeliveryCharge.getText().toString().replace("$", ""));

            } else {

                cart_deleivery_charge = 0.0;
            }


            if (txtAdditionalDeliveryCharge.getText().toString().length() != 0) {
                cart_adddeleivery_charge = Double.valueOf(txtAdditionalDeliveryCharge.getText().toString().replace("$", ""));

            } else {

                cart_adddeleivery_charge = 0.0;
            }

            if (foodVoucher) {
                cart_deleivery_charge = 0.0;
                cart_adddeleivery_charge = 0.0;
                layoutDeliveryCharge.setVisibility(View.GONE);
                layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
            }


            mGrandTotal = cart_sub_total + cart_deleivery_charge + cart_adddeleivery_charge+packing_ser_charge;

            if (isWalletApplied) {
                mGrandTotal = mGrandTotal - Double.parseDouble(used_eWalletAmount);

            }

        /*    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                mGST = (mGrandTotal * 7) / 100;
                GlobalValues.GST = mGST;
                mGrandTotal += mGST;
            } else {

                int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                mGST = (mGrandTotal * gst_values) / 100;
                GlobalValues.GST = mGST;
                mGrandTotal += mGST;
            }*/

            txtGST.setText("$" + String.format("%.2f", mGST));
            txtSubTotal.setText(subTotal);
            if (Double.valueOf(mGrandTotal) < 0.00) {
                InclusiveGst(0.00);
            } else {
                InclusiveGst(mGrandTotal);
            }

            //txtTotal.setText(String.format("%.2f", mGrandTotal+gstAmount));
            if (Double.valueOf(mGrandTotal) < 0.00 || Double.valueOf(mGrandTotal) == 0.00) {
                txtTotal.setText(String.format("%.2f", 0.00));
                //use_wallet_lyt.setVisibility(View.GONE);
                payByeWallet.setVisibility(View.GONE);
                payment_layout.setVisibility(View.GONE);
            } else {
//                txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
                txtTotal.setText(String.format("%.2f", mGrandTotal));
                //use_wallet_lyt.setVisibility(View.VISIBLE);
                if (!walletBalance.isEmpty()) {
                    if (Double.parseDouble(walletBalance) > 0) {
                        payByeWallet.setVisibility(View.VISIBLE);
                        payment_layout.setVisibility(View.VISIBLE);
                    }
                }
            }
        }

        if (discountType.equalsIgnoreCase("P")) {
            promotionAmount = points_amount;
        }
        r_sub_total = txtSubTotal.getText().toString().replace("$", "");
        //setCustomProgress();
        updateProgress(r_sub_total);
    }

    private void updateProgress(String r_sub_total) {

        try {

            // outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

            // double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(this.r_sub_total);

/*                    if (d_progress_limit > 0) {
                        txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
                    } else {
                        txtFreeDelivery.setText("FREE delivery!");
                    }*/


       /*     if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                    CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                setCustomProgress();

                if (d_progress_limit > 0) {

                   *//* if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {


                        GlobalValues.DELEIVERY_CHARGES = "0.0";
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                        txtDeliveryCharge.setText("$0.00");
                        txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                        txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");

                        mGrandTotal = Double.parseDouble(r_sub_total) +
                                Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                            mGST = (mGrandTotal * 7) / 100;
                            GlobalValues.GST = mGST;
                            mGrandTotal += mGST;
                        }

                    } else {

                        GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                        txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                        txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                        txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");

                        mGrandTotal = Double.parseDouble(r_sub_total) +
                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                            mGST = (mGrandTotal * 7) / 100;
                            GlobalValues.GST = mGST;
                            mGrandTotal += mGST;
                        }
//                    }*//*

                } else {

                    GlobalValues.DELEIVERY_CHARGES = "0.00";
                    GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
                    txtDeliveryCharge.setText("$0.00");
                    txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                            Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                    txtFreeDelivery.setText("FREE delivery!");

                    mGrandTotal = Double.parseDouble(r_sub_total) +
                            Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));

                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                        mGrandTotal = Double.parseDouble(outletZoneJson.getString("cart_sub_total")) +
                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));


                        mGST = (mGrandTotal * 7) / 100;
                        GlobalValues.GST = mGST;
                        mGrandTotal += mGST;
                        txtGSTLabel.setText("GST (" + "7" + "%)");
                        txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

                        GlobalValues.DELEIVERY_CHARGES = "" + outletZoneJson.getString("zone_delivery_charge");

                    } else {
                        int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                        mGST = (mGrandTotal * gst_values) / 100;
                        GlobalValues.GST = mGST;
                        mGrandTotal += mGST;

                    }

                }
            } else*/
            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                GlobalValues.DELEIVERY_CHARGES = "0.00";
                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";


                // mGrandTotal = Double.parseDouble(r_sub_total);
          /*      int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();

                mGST = (mGrandTotal * gst_values) / 100;
                GlobalValues.GST = mGST;
                mGrandTotal += mGST;*/
            }


        } catch (Exception e) {
            e.printStackTrace();
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

                    if (jsonObject.has("result_set")) {

                        JSONObject jsonResult = jsonObject.getJSONObject("result_set");

                        JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");


                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                                jsoncartDetails.optString("cart_total_items"));


                        txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));
                        r_sub_total = jsoncartDetails.getString("cart_sub_total");
                        subTotal = jsoncartDetails.getString("cart_sub_total");
                         /*if (cartListTask) {
                            cart_sub_total = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }*/
                        cart_sub_total = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        setDeliveryAddress();

                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                           /* int gst_values = 0;
                            if (GlobalValues.GstChargers.equalsIgnoreCase("")) {
                                gst_values = 0;
                            } else {
                                gst_values = Integer.valueOf(GlobalValues.GstChargers);
                            }*/

                            // mGST = (mGrandTotal * gst_values) / 100;
                            //  GlobalValues.GST = mGST;
                            /*   mGrandTotal += mGST;*/
                        } else {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
                        }

                        JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("result_set").toString());


                        setCartAdapter(jsonCartItem);

                        if (foodVoucher) {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }

                        if (!(isApplyPromo || isApplyRedeem)) {
                            //txtGST.setText("$" + String.format("%.2f", mGST));
                            InclusiveGst(mGrandTotal);
//                            txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
                            txtTotal.setText(String.format("%.2f", mGrandTotal));
                        }

                        if (!(jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0")
                                || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("null") || jsoncartDetails.getString("cart_voucher_discount_amount").equalsIgnoreCase("0.00"))) {
                            layoutdiscountVouchers.setVisibility(View.VISIBLE);
                            txtDiscountTotalVouchers.setText(jsoncartDetails.getString("cart_voucher_discount_amount") + ")");
                            cartVoucherDiscountAmt = jsoncartDetails.getString("cart_voucher_discount_amount");
                            setOverallTotal(Double.valueOf(cartVoucherDiscountAmt), "APPLY", "V");
                         } else {
                            layoutdiscountVouchers.setVisibility(View.GONE);
                        }
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
                if (mPromoCouponResponse != null && mPromoCouponResponse.length() > 0) {

                    //layoutdiscount.setVisibility(View.VISIBLE);

                    try {
                        //parseCouponPointResponse(mPromoCouponResponse);
                               /* txtDiscountTotal.setText("-$" + String.format("%.2f", Double.valueOf(mPromotionAmount)));
                                setOverallTotal(Double.valueOf(mPromotionAmount), "APPLY");*/

                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (isApplyPromo) {
                    //removePromotion();

                    setOverallTotal(Double.parseDouble(mPromotionAmount), "APPLY", "P");
                }
                Log.e("TAG","ReedemApplyTest::"+isApplyRedeem);
                if (isApplyRedeem) {
                    //removeRewardPoints();

                    setOverallTotal(Double.parseDouble(r_amount), "APPLY", "R");
                }

                if (Utility.readFromSharedPreference(mContext,GlobalValues.PACKING_CHARGE).length()>0) {
                    //removeRewardPoints();
   //                 setOverallTotal(Double.parseDouble(r_amount), "APPLY", "R");
                    serviceChargeMethod();
                }else {
                    layoutpackingcharge.setVisibility(View.GONE);
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
                if (GlobalValues.isPromoAdded) {
                    if (mPromoCouponResponse != null && mPromoCouponResponse.length() > 0) {
                        if (isApplyPromo) {
                            try {
                                setOverallTotal(Double.parseDouble(mPromotionAmount), "REMOVE", "P");
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        }
                        layoutdiscount.setVisibility(View.VISIBLE);

                        try {
                            parseCouponPointResponse(mPromoCouponResponse);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
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

        @SuppressLint("NewApi")
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObjects = new JSONObject(s);

                if (jsonObjects.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObjects.getJSONObject("result_set");
                    mrewardPoint = countJson.optDouble("reward_ponits");
                    txtRedeem.setText("You have " + String.valueOf(countJson.optDouble("reward_ponits")) + " points available");

        //            redeemPointsValue = String.valueOf(countJson.optDouble("reward_ponits"));

                    promoPointsValue = String.valueOf(countJson.optDouble("promotionwithoutuqc"));
                    voucherPointsValue = String.valueOf(countJson.optDouble("vouchers"));

                  /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtRedeemAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + redeemPointsValue + "</font> points", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                    } else {
                        txtRedeemAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + redeemPointsValue + "</font> points"), TextView.BufferType.SPANNABLE);
                    }*/
                    walletBalance = String.valueOf(countJson.optDouble("wallet_balnace"));

                    if (walletBalance.equalsIgnoreCase("NaN")) {

                        walletBalance = "";
                    } else if (walletBalance.equalsIgnoreCase("")) {

                    } else if (walletBalance.equalsIgnoreCase("null")) {

                    }

                    txtRedeemPoints.setHint("You can Redeem " + String.valueOf(countJson.optDouble("reward_ponits")) + " points");
                    int promo_point = (int) Math.round(Double.parseDouble(promoPointsValue));
                    int voucher_points = (int) Math.round(Double.parseDouble(voucherPointsValue));
                    promoCount = promo_point;
                    voucherCount = voucher_points;
//                    txtPromoAvailablePoints.setText("You have " + promo_point + " promotions");
//                    txtVoucherAvailablePoints.setText("You have " + voucher_points + " vouchers");
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtPromoAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + promo_point + "</font> promotions", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                        txtVoucherAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + voucher_points + "</font> vouchers", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                    } else {
                        txtPromoAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + promo_point + "</font> promotions"), TextView.BufferType.SPANNABLE);
                        txtVoucherAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + voucher_points + "</font> vouchers"), TextView.BufferType.SPANNABLE);
                    }

                    if (!(walletBalance == null || walletBalance.equalsIgnoreCase(""))) {
                        if (Double.parseDouble(walletBalance) > 0 && mGrandTotal > 0) {
                            //use_wallet_lyt.setVisibility(View.VISIBLE);
                            payByeWallet.setVisibility(View.VISIBLE);
                            payment_layout.setVisibility(View.VISIBLE);
                            //wallet_text.setText("Use Wallet Balance (" + walletBalance + " POINTS)");
                            wallet_text.setText("Balance $" + walletBalance);
                        } else {
                            //use_wallet_lyt.setVisibility(View.GONE);
                            payByeWallet.setVisibility(View.GONE);
                            payment_layout.setVisibility(View.GONE);
                        }
                    } else {
                        //use_wallet_lyt.setVisibility(View.GONE);
                        payByeWallet.setVisibility(View.GONE);
                        payment_layout.setVisibility(View.GONE);
                    }

                    if (isWalletApplied) {
                        updateWallet(Double.parseDouble(used_eWalletAmount), "APPLY");
                    }

                    //txtPromotions.setText("You have " + String.valueOf(countJson.optInt("promotionwithoutuqc")) + " Promotions Available");

                    setPromotionSpan(" " + countJson.optString("promotionwithoutuqc") + " Promotions");

                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");

                    JSONObject promoObject = countJson.getJSONObject("promo");

                    String firstPromotion = promoObject.getString("promo_apply");
                    String promo_code = promoObject.getString("promo_code");

                    if (firstPromotion.equalsIgnoreCase("Yes")) {
                        GlobalValues.isFirstTimePromo = true;
                        GlobalValues.firstTimePromoCode = promo_code;

                        edtPromotion.setText(promo_code);

                        if (isApplyPromo && GlobalValues.firstTimePromoCode.equalsIgnoreCase(mPromoCoupon)) {


                        } else if (mPromoCouponResponse != null && mPromoCouponResponse.length() > 0) {

                        } else {
                            if (txtPromoApply.getText().toString().equalsIgnoreCase("APPLY NOW")) {
                                if (edtPromotion != null && edtPromotion.getText().toString().length() > 0) {
                                    try {

                                        String url = GlobalUrl.COUPON_CODE_URL;

                                        JSONObject jsonObject = new JSONObject(Utility.readFromSharedPreference(mContext,
                                                GlobalValues.CART_RESPONSE).toString());

                                        JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

                                        JSONArray cartJsonArray = jsonObject.getJSONArray("cart_items");

                                        try {
                                            StringBuilder stringBuilder = new StringBuilder();
                                            for (int i = 0; i < cartJsonArray.length(); i++) {
                                                JSONObject cartItem = cartJsonArray.getJSONObject(i);
                                                if (i != (cartJsonArray.length() - 1))
                                                    stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty") + ";");
                                                else {
                                                    stringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
                                                            cartItem.getString("cart_item_total_price") + "|" + cartItem.getString("cart_item_qty"));
                                                }
                                            }

                                            Map<String, String> params = new HashMap<String, String>();
                                            params.put("app_id", GlobalValues.APP_ID);
                                            params.put("promo_code", edtPromotion.getText().toString().trim());
                                            params.put("availability_id", CURRENT_AVAILABLITY_ID);
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
                                            params.put("availability_id", CURRENT_AVAILABLITY_ID);
                                            params.put("reference_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
                                            params.put("cart_amount", ""); //cart_sub_total
                                            params.put("cart_quantity", "");
                                            params.put("category_id", "");  //list of product ids

                                            new CouponCodeTask(params, edtPromotion.getText().toString().trim()).execute(url);

                                        }
                                    } catch (Exception e) {
                                        e.printStackTrace();
                                    }
                                }
                            }
                        }

                        /*if(couponResponseFromFiveMenu != null && !couponResponseFromFiveMenu.equalsIgnoreCase("")){
                         *//* layoutdiscount.setVisibility(View.VISIBLE);
                            try {
                                parseCouponPointResponse(mPromoCouponResponse);
                            } catch (Exception e) {
                                e.printStackTrace();
                            }*//*
                        }else{*/

                        //}

                    } else {

                    }
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


            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&availability_id=" + CURRENT_AVAILABLITY_ID + "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            new ActivCountTask(mapData).execute(url);

        }
    }


    private void setCartAdapter(JSONArray jsonCartItem) {

        try {
            cartList = new ArrayList<>();
            String Matched = "";
            String Added = "";

            if (jsonCartItem.length() > 0) {
                txtEmpty.setVisibility(View.GONE);
                orderRecyclerView.setVisibility(View.VISIBLE);


                int BENTO_Count = 0;

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

                    mProductVoucher = jsonItem.getString("cart_product_voucher");
                    cartItemVoucherId = jsonItem.getString("cart_item_voucher_id");

                    if (jsonItem.getString("cart_item_product_voucher_gift_name") != null) {
                        ProductVoucherGiftName = jsonItem.getString("cart_item_product_voucher_gift_name");
                    }
                    if (jsonItem.getString("cart_item_product_voucher_gift_email") != null) {
                        ProductVoucherGiftEmail = jsonItem.getString("cart_item_product_voucher_gift_email");
                    }
                    if (jsonItem.getString("cart_item_product_voucher_gift_mobile") != null) {
                        ProductVoucherGiftMobile = jsonItem.getString("cart_item_product_voucher_gift_mobile");
                    }
                    if (jsonItem.getString("cart_item_product_voucher_gift_message") != null) {
                        ProductVoucherGiftMsg = jsonItem.getString("cart_item_product_voucher_gift_message");
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
                            int add = Integer.valueOf(jsonItem.getString("cart_item_qty")) + Integer.valueOf(Added);
                            updateCurrentCartQuantity(jsonItem.getString("cart_item_product_id"), "" + add);
                        }
                        updateCurrentCartQuantity(jsonItem.getString("cart_item_product_id"), jsonItem.getString("cart_item_qty"));
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
                            setMenuTitle.setmTitleMenuId(setmenuObject.optString("menu_component_id"));
                            setMenuTitle.setmenu_component_modifier_apply(setmenuObject.optString("menu_component_modifier_apply"));
                            GlobalValues.MULTIPLESLECTIONAPPLY = setmenuObject.optString("menu_component_multipleselection_apply");
                            GlobalValues.MODIFIERAPPLY = setmenuObject.optString("menu_component_modifier_apply");
                            setMenuTitle.setmultipleselection_apply(setmenuObject.optString("menu_component_multipleselection_apply"));
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
                                                    modifiersValue.setmSubModifierTotal(Integer.parseInt(valueObject.optString("cart_modifier_qty")));

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
                cartAdapter = new CartRecyclerAdapterSummary(mContext, cartList);
                orderRecyclerView.setAdapter(cartAdapter);
                orderRecyclerView.setItemAnimator(new DefaultItemAnimator());
                orderRecyclerView.setNestedScrollingEnabled(false);

                cartAdapter.setOnDeleteClickListener(new ICartItemClick() {
                    @Override
                    public void updateOverallCartItems(View view, int position, Cart cart) {
                        SubCategoryActivity.mSetmenuoverallprices = 0.00;
                        tQuantity = 0;
                        plusminusPrice = 0.00;
                        cartSplNotes = "";
                       /* if (foodVoucher) {
                            layoutDeliveryCharge.setVisibility(View.GONE);
                            layoutAdditionalDeliveryCharge.setVisibility(View.GONE);
                            txtDeliveryCharge.setText("0.00");
                            deliveryCharges = 0.00;
                            txtAdditionalDeliveryCharge.setText("0.00");
                            additionalDeliveryCharges = 0.00;
                        }*/
                        CurrentPosition = position;
                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            mReferenceId = "";
                        } else {
                            mCustomerId = "";
                            mReferenceId = Utility.getReferenceId(mContext);
                        }



                        if (cartList.get(position).getmProductType().equals("Component")) {

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

                                                    Log.e("plusminu", String.valueOf(plusminusPrice));
                                                }
                                            }
                                        }
                                    }
                                }

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

                        CurrentPosition = position;

                        //  makeUpdateApiCall(productId,cartProduct,type);
                    }

                    @Override
                    public void deleteCartItem(View view, int position) {

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
                            params.put("reference_id", mReferenceId);

                            new DeleteCartItemTask(params, cartList.get(position).getmProductId()).execute(url);

                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void updateCartItem(View view, int position, int quantity) {


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
                            params.put("reference_id", mReferenceId);
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
                finish();
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }


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
            params.put("order_remarks", customerNote);
            if (cutlery_check.isChecked()) {
                params.put("cutleryOption", "YES");
                GlobalValues.is_cutlery_checked = true;
            } else {
                params.put("cutleryOption", "NO");
                GlobalValues.is_cutlery_checked = false;
            }

            params.put("order_someone_remarks", txtNotes.getText().toString());
            params.put("product_leadtime", Utility.readFromSharedPreference(mContext, GlobalValues.PRODUCT_LEAD_TIME));
            params.put("order_voucher_discount_amount", cartVoucherDiscountAmt);

            try {
                mCarttotal = Double.parseDouble(txtTotal.getText().toString().replace("$", ""));
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }



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
            params.put("outlet_id", Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID));

            params.put("reward_point_status", "Yes");


            if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                JSONObject jsonPostalCodeInfo = outletJson.getJSONObject("postal_code_information");

                params.put("customer_address_line1", jsonPostalCodeInfo.optString("zip_buno"));
                params.put("customer_postal_code", jsonPostalCodeInfo.optString("zip_code"));
                params.put("billing_address_line1", edtBillingAddress.getText().toString());
                params.put("billing_postal_code", edtPincode.getText().toString());
                params.put("billing_unit_no1", edtBillingUnitNo1.getText().toString());
                params.put("billing_unit_no2", edtBillingUnitNo2.getText().toString());
                params.put("order_tat_time", mTatTime + "");
                params.put("customer_unit_no1", unitNo1);
                params.put("customer_mobile_no", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));
                params.put("customer_unit_no2", unitNo1);


            }

            params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);
            params.put("delivery_charge", String.valueOf(GlobalValues.COMMON_DELIVERY_CHARGE));
            params.put("additional_delivery", GlobalValues.ADDITIONAL_DELEIVERY_CHARGES);



            if (GlobalValues.DISCOUNT_APPLIED) {

                params.put("discount_applied", "Yes");
                params.put("discount_amount", r_amount);


                if (isApplyPromo) {

                    if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {

                        params.put("promotion_delivery_charge_applied", "Yes");
                       /* params.put("promo_code", p_code);
                        params.put("coupon_amount", GlobalValues.DELEIVERY_CHARGES);*/

                    } else {

                        params.put("promo_code", mPromoCoupon);
                        params.put("coupon_amount", mPromotionAmount);
                        params.put("coupon_applied", "Yes");
                        params.put("order_promo_sub_total", promo_subTotal);
                        Log.e("mPromoAmount", mPromotionAmount);
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

            if (isWalletApplied) {

                params.put("ewallet_amount", used_eWalletAmount);
            } else {
                params.put("ewallet_amount", "");
            }

            params.put("tax_charge", GlobalValues.GstChargers);
            params.put("order_tax_calculate_amount", String.valueOf(gstAmount));

            params.put("packaging_charge", Utility.readFromSharedPreference(mContext,GlobalValues.PACKING_CHARGE));




            if (Utility.networkCheck(mContext)) {
                String url = GlobalUrl.SUBMIT_ORDER_URL;
                if (Double.valueOf(txtTotal.getText().toString().replace("$", "")) <= 0d) {
                    Log.e("TAG", "PAYMENTTESTNEW::"+txtTotal.getText().toString());
                    //Toast.makeText(mContext, "Minimum order value shoud be greater than $1", Toast.LENGTH_SHORT).show();
                    new CashOnDeliveryValidateOrderTask(params).execute(url);
                } else {
                    Log.e("TAG", "PAYMENTTESTNEW_1::"+txtTotal.getText().toString());
                    new CashOnDeliveryValidateOrderTask(params).execute(url);
                }

                /*if (Double.valueOf(txtTotal.getText().toString().replace("$", "")) <= 0.00 || Double.valueOf(txtTotal.getText().toString().replace("$", "")) <= 0
                        || Double.valueOf(txtTotal.getText().toString().replace("$", "")) <= 0d) {

                    new CashOnDeliveryValidateOrderTask(params).execute(url);
                }*/
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

            Log.e("cash_on_delivery::", params[0] + "\n" + orderParams.toString());

            String response = WebserviceAssessor.postRequest(mContext, params[0], orderParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG","cash_delivery_response::"+s);
                try {

                    JSONObject jsonObject = new JSONObject(s);

                    // JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    if (mCarttotal > 0) {

                        if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                            String additionalNotes = txtNotes.getText().toString();
                            if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {

                                additionalNotes = additionalNotes;
                            } else {

                                additionalNotes = "";
                            }

                           /* if (GlobalValues.PAYMENT_TYPE.equalsIgnoreCase("NETS")){

                                  *//* String url = GlobalUrl.DESTROY_CART_URL;
                                    Map<String, String> params = new HashMap<>();
                                    params.put("app_id", GlobalValues.APP_ID);
                                    params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

//                                  Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                                    if (Utility.networkCheck(mContext)) {
                            fromChangeAddress = TO_THANKS;
                            new DestroyCartTask(params).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }*//*
                                placeCashOnDeliveryOrder();
                        return;

                            }*/


                            intent = new Intent(mContext, PaymentActivity.class);
                            intent.putExtra("from","");
                            intent.putExtra("sub_total", jsonCartObject.getJSONObject("cart_details").getString("cart_sub_total"));
                            intent.putExtra("total_price", String.valueOf(txtTotal.getText().toString().replace("$", "")));
                            intent.putExtra("unit_no1", edtUnitNo1.getText().toString());
                            intent.putExtra("PAYMENT_TYPE",GlobalValues.PAYMENT_TYPE);
                            intent.putExtra("unit_no2", edtUnitNo2.getText().toString());
                            intent.putExtra("billing_address", edtBillingAddress.getText().toString());
                            intent.putExtra("billing_pincode", edtPincode.getText().toString());
                            intent.putExtra("billing_unit_no1", edtBillingUnitNo1.getText().toString());
                            intent.putExtra("billing_unit_no2", edtBillingUnitNo2.getText().toString());
                            intent.putExtra("order_remarks", customerNote);
                            intent.putExtra("cutleryOption", cutleryOption);
                            intent.putExtra("REDEEM_APPLIED", r_applied);
                            intent.putExtra("REDEEM_POINT", r_point);
                            intent.putExtra("redeem_amount", r_amount);
                            intent.putExtra("promo_code", mPromoCoupon);
                            intent.putExtra("promo_amount", mPromotionAmount);
                            intent.putExtra("reward_subTotal", reward_subTotal);
                            intent.putExtra("promo_subTotal", promo_subTotal);
                            intent.putExtra("AdditionalNotes", txtNotes.getText().toString());
                            intent.putExtra("cartVoucherDiscountAmt", cartVoucherDiscountAmt);

                            if (isWalletApplied) {

                                intent.putExtra("ewallet_amount", used_eWalletAmount);
                            } else {
                                intent.putExtra("ewallet_amount", "");
                            }

                            startActivity(intent);

                        } else {

                            Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                        }
                    }
                    else {
                        if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                            JSONObject jsonCommon = jsonObject.getJSONObject("common");

                            mOrderNo = jsonCommon.getString("local_order_no");

                            String url = GlobalUrl.DESTROY_CART_URL;
                            Map<String, String> params = new HashMap<>();
                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));

//                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                            if (Utility.networkCheck(mContext)) {

                                fromChangeAddress = TO_THANKS;
                                new DestroyCartTask(params).execute(url);
                            } else {
                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
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

                Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    /*deleteCurrentQuantity(mProductId);
                    if (!jsonObject.isNull("contents")) {

                        JSONObject jsonResult = jsonObject.getJSONObject("contents");


                        JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");


                        if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removePromotion();
                        }


                        if (redeemPoints.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removeRewardPoints();
                        }

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                                jsoncartDetails.getString("cart_total_items"));


                        txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));

                        r_sub_total = jsoncartDetails.getString("cart_sub_total");


                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                                CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                            *//*setCustomProgress();


                            double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery"))
                                    - Double.valueOf(r_sub_total);

                            if (d_progress_limit > 0) {

                                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                                txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");

                                //progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                            Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));


                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;

                                    txtGSTLabel.setText("GST (" + "7" + "%)");
                                    txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                                    GlobalValues.DELEIVERY_CHARGES = "" + outletZoneJson.getString("zone_delivery_charge");

                                } else {

                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;
                                }


                            } else {

                                GlobalValues.DELEIVERY_CHARGES = "0.00";
                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
                                txtDeliveryCharge.setText("$0.00");
                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                                txtFreeDelivery.setText("FREE delivery!");

//                            progressBar.setProgress(1000);

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                            Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));


                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;

                                    txtGSTLabel.setText("GST (" + "7" + "%)");
                                    txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

                                    GlobalValues.DELEIVERY_CHARGES = "" + outletZoneJson.getString("zone_delivery_charge");
                                } else {
                                    int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                    mGST = (mGrandTotal * gst_values) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;
                                }

                            }

//                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                          *//*

                        } else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            GlobalValues.DELEIVERY_CHARGES = "0.00";
                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";


                            int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                            mGST = (mGrandTotal * gst_values) / 100;
                            GlobalValues.GST = mGST;
                            mGrandTotal += mGST;

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));

                        }


//                        subtotal_value = jsoncartDetails.getString("cart_sub_total");

                     *//*   if (Double.parseDouble(outletZoneJson.optString("zone_free_delivery")) > 0) {

                            progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
                            progressBar.setSecondaryProgress((int) Double.parseDouble(
                                    outletZoneJson.optString("zone_free_delivery")));
                        } else {

                            progressBar.setProgress(1000);
                            progressBar.setSecondaryProgress(1000);
                        }
*//*

                     *//*  if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

//                                    Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));

                        } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }*//*

                        txtGST.setText("$" + String.format("%.2f", mGST));


                        JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());



//                        Remove applied coupon or reward points


                        setCartAdapter(jsonCartItem);

                        if (foodVoucher) {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }

                        InclusiveGst(mGrandTotal);
                        //txtTotal.setText(String.format("%.2f", mGrandTotal+gstAmount));
                        txtTotal.setText(String.format("%.2f", mGrandTotal));

                    }
                    else {

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, "");

                        invalidateOptionsMenu();

                        try {
                            databaseHandler.deleteAllCartQuantity();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        finish();
                    }*/

                    if (isApplyPromo) {
                        removePromotion();
                    }

                    if (isApplyRedeem) {
                        removeRewardPoints();
                    }

                    if (isWalletApplied) {
                        if (layoutwallet.getVisibility() == View.VISIBLE) {
                            layoutwallet.setVisibility(View.GONE);
                            use_waller_amount.setChecked(false);
                            updateWallet(Double.parseDouble(used_eWalletAmount), "REMOVE");
                            isWalletApplied = false;
                            eWalletTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                            payByeWallet.setBackgroundResource(R.drawable.payment_outline);
                            paymentType = "";
                        }
                    }

                    onResume();
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

    private void deleteCurrentQuantity(String mProductId) {

        try {
            databaseHandler.deleteCartQuantity(mProductId);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
//----------date time methods start


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

                if (jsonObject.getString("status").equalsIgnoreCase("success") ||
                        jsonObject.getString("status").equalsIgnoreCase("ok")) {

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

//    public void openDeliveryDateDialog1(final Context context) {
//        // Create custom dialog object
//
//
//        isSelectTime = false;
//        is_time_shown = false;
//        is_date_shown = false;
//
//        final Dialog dialog = new Dialog(context, R.style.custom_dialog_theme);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        // Include dialog.xml file
//        dialog.setContentView(R.layout.layout_delivery_date_dialog);
//        dialog.show();
//
//        txtDeliveryDate = (TextView) dialog.findViewById(R.id.txtDeliveryDate);
//        txtDeliveryTime = (TextView) dialog.findViewById(R.id.txtDeliveryTime);
//        TextView selectdataandtimeinput = (TextView) dialog.findViewById(R.id.selectdataandtimeinput);
//        ImageView image_log = (ImageView) dialog.findViewById(R.id.image_log);
//
//        final TextView txtContinue = (TextView) dialog.findViewById(R.id.txtContinue);
//        layoutCalendar = (RelativeLayout) dialog.findViewById(R.id.layoutCalendar);
//        final RelativeLayout layoutDate = (RelativeLayout) dialog.findViewById(R.id.layoutDate);
//        TextView txtGoBack = (TextView) dialog.findViewById(R.id.txtGoBack);
//        TextView txtDeliveryAddress = (TextView) dialog.findViewById(R.id.txtDeliveryAddress);
//        layoutTime = (RelativeLayout) dialog.findViewById(R.id.layoutTime);
//        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
//        TextView txtDeliveryAddressLabel = (TextView) dialog.findViewById(R.id.txtDeliveryAddressLabel);
//
//
//        LinearLayout layoutDeliveryAddress = (LinearLayout) dialog.findViewById(R.id.layoutDeliveryAddress);
//
//
//        layoutCustomTime = (RelativeLayout) dialog.findViewById(R.id.layoutCustomTime);
//        timeRecyclerView = (RecyclerView) dialog.findViewById(R.id.timeRecyclerView);
//
//        previousButton = (TextView) dialog.findViewById(R.id.PreviousMonth);
//        nextButton = (TextView) dialog.findViewById(R.id.nextMonth);
//        currentDateText = (TextView) dialog.findViewById(R.id.currentMonth);  /*display_current_date*/
//        calendarGridView = (GridView) dialog.findViewById(R.id.calendar_grid);
//
//        todayString = dateformat.format(Calendar.getInstance().getTime());
//        txtDeliveryDate.setText(todayString);
//        mselectedDay = Calendar.getInstance().get(Calendar.DAY_OF_WEEK);
//        isDateDisplay = true;
//        setTime();
//
//
//        try {
//            JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
//            txtDeliveryAddress.setText(outletZoneJson.getString("zone_address_line1") + ","
//                    + outletZoneJson.getString("zone_postal_code"));
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//        if (GlobalValues.CURRENT_AVAILABLITY_NAME.equalsIgnoreCase("delivery")) {
//            layoutDeliveryAddress.setVisibility(View.VISIBLE);
//            try {
//                txtDeliveryAddressLabel.setText("Your Delivery Address :");
//                JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
//                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");
//
//
//                txtDeliveryAddress.setText(jsonPostalCodeInfo.optString("zip_buno") + ","
//                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
//                        jsonPostalCodeInfo.optString("zip_buname") + "," +
//                        jsonPostalCodeInfo.optString("zip_code")
//                );
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        } else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//            layoutDeliveryAddress.setVisibility(View.VISIBLE);
//            try {
//                txtDeliveryAddressLabel.setText("Your Delivery Address :");
//                JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
//                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");
//
//
//                txtDeliveryAddress.setText(jsonPostalCodeInfo.optString("zip_buno") + ","
//                        + jsonPostalCodeInfo.optString("zip_sname") + "," +
//                        jsonPostalCodeInfo.optString("zip_buname") + "," +
//                        jsonPostalCodeInfo.optString("zip_code")
//                );
//
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//
//
//        } else if (GlobalValues.CURRENT_AVAILABLITY_NAME.equalsIgnoreCase("takeaway")) {
//
//            try {
//                JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//
//            image_log.setImageResource(R.drawable.thumbs_aag);
//
//            txtDeliveryAddressLabel.setText("Your Pickup Location is :");
//
//            selectdataandtimeinput.setText("Select Your Pickup Date & Time");
//
//            txtDeliveryAddress.setText(Utility.readFromSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS) + ", Singapore, " + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_PINCODE));
//        }
//
//        layoutDate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                is_time_shown = false;
//                layoutCustomTime.setVisibility(View.GONE);
//
//                if (is_date_shown) {
//                    is_date_shown = false;
//                    layoutCalendar.setVisibility(View.GONE);
//                } else {
//                    is_date_shown = true;
//                    layoutCalendar.setVisibility(View.VISIBLE);
////                    layoutCustomTime.setVisibility(View.GONE);
//                }
//
//               /* if (!isDateDisplay) {
//                    isDateDisplay = true;
//                    txtContinue.setEnabled(false);
//
//                } else {
//                    isDateDisplay = false;
//                    txtContinue.setEnabled(true);
//                    layoutCalendar.setVisibility(View.GONE);
//                }*/
//            }
//        });
//
//        txtContinue.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                //openWarningDialog(1);
//
//                if (isDateDisplay) {
//
//                    if (isSelectTime) {
//
//                        GlobalValues.DELIVERY_DATE = txtDeliveryDate.getText().toString();
//                        GlobalValues.DELIVERY_TIME = txtDeliveryTime.getText().toString();
//
//                        if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {
//
//                            setDate(GlobalValues.DELIVERY_DATE);
//                            setTime(GlobalValues.DELIVERY_TIME);
//
//                            Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_DATE, GlobalValues.DELIVERY_DATE);
//                            Utility.writeToSharedPreference(mContext, GlobalValues.ORDER_DELIVERY_TIME, GlobalValues.DELIVERY_TIME);
//
//
//                            dialog.dismiss();
//
//                        } else {
//                            Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
//                        }
//
//                    } else {
//                        Toast.makeText(mContext, "Please select Time", Toast.LENGTH_SHORT).show();
//                    }
//
//                } else {
//
//                    Toast.makeText(mContext, "Please select Date", Toast.LENGTH_SHORT).show();
//                }
//
//            }
//        });
//
//        txtGoBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//
//              /*  if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
//                    openDeliveryDialog(mContext, moutletPosition);
//                } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
//
//                    openOutletSelectionDialog(mContext);
//                }*/
//            }
//        });
//
//        layoutTime.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                is_date_shown = false;
//                layoutCalendar.setVisibility(View.GONE);
//
//                if (isDateDisplay) {
//
//                    try {
//                        if (is_time_shown) {
//                            is_time_shown = false;
//                            layoutCustomTime.setVisibility(View.GONE);
//                        } else {
//                            setTime();
//                            is_time_shown = true;
//                            layoutCustomTime.setVisibility(View.VISIBLE);
//                        }
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
//
//                } else {
//                    Toast.makeText(mContext, "Please select Date", Toast.LENGTH_SHORT).show();
//                }
//
//
//            }
//        });
//
//        layoutClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialog.dismiss();
//            }
//        });
//
//        initializeUILayout();
//        setUpCalendarAdapter();
//        setPreviousButtonClickEvent();
//        setNextButtonClickEvent();
//        setGridCellClickEvents(mContext);
//
//
//        setMargin();
//
//    }

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

//    private void setTime1() {
//
//        timeList = new ArrayList<>();
//        slotTimeArrayList = new ArrayList<>();
//
//
//
////        mTatTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.TAT_TIME));
//
//
//        /*if (outletList.size() > 0) {
//            if (!outletList.get(0).getOutlet_delivery_tat().isEmpty()) {
//                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_tat());
//            } else if (!outletList.get(0).getOutlet_delivery_timing().isEmpty()) {
//                mTatTime = Integer.parseInt(outletList.get(0).getOutlet_delivery_timing());
//            }
//        }*/
//
//        try {
//            currentDate = dateformat.parse(dateformat.format(Calendar.getInstance().getTime()));
//            selecteddate = dateformat.parse(txtDeliveryDate.getText().toString());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        slotTimeArrayList = getSelectedDayTimeSlot(mselectedDay);
//        try {
//            for (int i = 0; i < slotTimeArrayList.size(); i++) {
//                int count = 0;
//                is_break = false;
//                String start_time = slotTimeArrayList.get(i).getmStartTime();
//                String end_time = slotTimeArrayList.get(i).getmEndTime();
//
//                Date dstartTime = timeformat.parse(start_time);
//                Date dendTime = null;
//                if (mTatTime != 0) {
//                    dendTime = setTatEndTime(timeformat.parse(end_time));
//                } else {
//
//                    dendTime = timeformat.parse(end_time);
//                }
//
//                loop:
//                while (dstartTime.before(dendTime) || dstartTime.equals(dendTime)) {
//
//                    if (selecteddate.equals(currentDate)) {
//
//                        if ((timeformat.parse(timeformat.format(Calendar.getInstance().getTime()))).before(dstartTime)) {
//                            if (count == 0 && mTatTime != 0) {
//                                start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
//                                count++;
//                            } else {
//                                timeList.add(start_time);
//                            }
//                        }
//                    } else {
//                        if (count == 0 && mTatTime != 0) {
//                            start_time = setTatTime(dstartTime, timeformat.parse(end_time), setTatEndTime(timeformat.parse(end_time)));
//                            count++;
//                        } else {
//                            timeList.add(start_time);
//                        }
//                    }
//
//                    if (is_break) {
//                        if (timeList.size() == 1) {
//                            timeList.remove(0);
//                            timeList.add(start_time);
//                        }
//                        break;
//                    }
//
//                    dstartTime = timeformat.parse(start_time);
//                    Calendar cal = Calendar.getInstance();
//                    cal.setTime(dstartTime);
//                    cal.add(Calendar.MINUTE, intervaltime);
//                    start_time = timeformat.format(cal.getTime());
//                    dstartTime = timeformat.parse(start_time);
//
//                    Date stst_static = timeformat.parse("00:00");
//                    if (dstartTime.equals(stst_static)) {
////                            timeList.add(start_time);
//
//                        break loop;
//                    }
//
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (timeList.size() > 0) {
//            LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
//            timeRecyclerView.setLayoutManager(layoutManager);
//            TimeAdapter timeAdapter = new TimeAdapter(mContext, timeList);
//            timeRecyclerView.setAdapter(timeAdapter);
//
//
//            if (timeList.size() > 0) {
//
//                txtDeliveryTime.setText(timeList.get(0));
//                isSelectTime = true;
//            }
//
//            timeAdapter.SetOnItemClickListioner(new TimeAdapter.OnItemClickListener() {
//                @Override
//                public void onItemClick(View view, int position) {
//
//                    try {
//                        Date dCurrentTime = timeformat.parse(timeformat.format(Calendar.getInstance().getTime()));
//                        Date dCutOffTime;
//
//                        if (selecteddate.equals(currentDate)) {
//
//                            cutOffTime = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CUT_OFF));
//                            if (cutOffTime > 0) {
//
//                                if (!String.valueOf(cutOffTime).contains(":")) {
//                                    dCutOffTime = timeformat.parse(cutOffTime + ":00");
//
//                                } else {
//                                    dCutOffTime = timeformat.parse(String.valueOf(cutOffTime));
//                                }
//
//                                if (dCutOffTime.before(dCurrentTime)) {
//                                    Toast.makeText(mContext, "Shop closed", Toast.LENGTH_SHORT).show();
//                                } else {
//                                    txtDeliveryTime.setText(timeList.get(position));
//                                    layoutCustomTime.setVisibility(View.GONE);
//                                    is_time_shown = false;
//                                    isSelectTime = true;
//                                }
//                            } else {
//                                txtDeliveryTime.setText(timeList.get(position));
//                                layoutCustomTime.setVisibility(View.GONE);
//                                is_time_shown = false;
//                                isSelectTime = true;
//                            }
//                        } else {
//                            txtDeliveryTime.setText(timeList.get(position));
//                            layoutCustomTime.setVisibility(View.GONE);
//                            isSelectTime = true;
//                            is_time_shown = false;
//                        }
//
//                    } catch (ParseException e) {
//                        e.printStackTrace();
//                    }
//
//                }
//            });
//
//        } else {
//
//            Toast.makeText(mContext, "Slot not available for this date", Toast.LENGTH_SHORT).show();
//        }
//    }

    private void setTime() {

        timeList = new ArrayList<>();
        slotTimeArrayList = new ArrayList<>();



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

    private void setDeliveryTime() {
        timeList = new ArrayList<>();
        slotTimeArrayList = new ArrayList<>();



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
//----------date time methods end----------

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


                        edtBillingAddress.setText(zip_buno + " " + zip_sname);


                    } else {

                        edtBillingAddress.setText("");

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

                    if (isApplyPromo) {
                        removePromotion();
                    }

                    if (isApplyRedeem) {
                        removeRewardPoints();
                    }

                    if (isWalletApplied) {
                        if (layoutwallet.getVisibility() == View.VISIBLE) {
                            layoutwallet.setVisibility(View.GONE);
                            use_waller_amount.setChecked(false);
                            updateWallet(Double.parseDouble(used_eWalletAmount), "REMOVE");
                            isWalletApplied = false;
                            eWalletTitle.setTextColor(mContext.getResources().getColor(R.color.black));
                            payByeWallet.setBackgroundResource(R.drawable.payment_outline);
                            paymentType = "";
                        }
                    }

                    onResume();

                   /* JSONObject outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));

                    JSONObject jsonResult = jsonObject.getJSONObject("contents");

                    JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");

                    if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                        removePromotion();
                    }


                    if (redeemPoints.getText().toString().equalsIgnoreCase("REMOVE")) {
                        removeRewardPoints();
                    }

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                            jsoncartDetails.getString("cart_total_items"));


                    txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));
                    r_sub_total = jsoncartDetails.getString("cart_sub_total");
                    double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(r_sub_total);*/

/*                    if (d_progress_limit > 0) {
                        txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
                    } else {
                        txtFreeDelivery.setText("FREE delivery!");
                    }*/


                   /* if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) ||
                            CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
                                Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));

                        *//*setCustomProgress();

                        if (d_progress_limit > 0) {

                            GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");

                            txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
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
                            txtDeliveryCharge.setText("$0.00");
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

                                txtGSTLabel.setText("GST (" + "7" + "%)");
                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

                            } else {
                                int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                                mGST = (mGrandTotal * gst_values) / 100;
                                GlobalValues.GST = mGST;
                                mGrandTotal += mGST;
                            }

                        }*//*
                    }
                    else if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                        GlobalValues.DELEIVERY_CHARGES = "0.00";
                        GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = "0.00";

                        int gst_values = Integer.valueOf(GlobalValues.GstChargers).intValue();


                        mGST = (mGrandTotal * gst_values) / 100;
                        GlobalValues.GST = mGST;
                        mGrandTotal += mGST;
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));

                    }*/

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

                    //========================================================

                   /* txtGST.setText("$" + String.format("%.2f", mGST));

                    JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());




                    setCartAdapter(jsonCartItem);

                    if (foodVoucher) {
                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                    }

                    InclusiveGst(mGrandTotal);
                    //txtTotal.setText(String.format("%.2f", mGrandTotal+gstAmount));
                    txtTotal.setText(String.format("%.2f", mGrandTotal));*/

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


        try {

            if (secondaryAddressArrayList.size() > 0) {
                address_list.setVisibility(View.VISIBLE);
                hit_textview.setVisibility(View.VISIBLE);
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
                hit_textview.setVisibility(View.GONE);


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

        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (edtPostalCode.getText().toString().length() <= 0) {

                    Toast.makeText(mContext, "Please Enter PostalCode", Toast.LENGTH_SHORT).show();

                } else {

                    if (Utility.networkCheck(mContext)) {



                        String url = GlobalUrl.FINDZONE_URL +
                                "?app_id=" + GlobalValues.APP_ID +
                                "&availability_id=" + CURRENT_AVAILABLITY_ID +
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
                            Log.e("curTime", String.valueOf(currenttime));
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

                Log.e("destroy_response:", s);

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

                if (fromChangeAddress == TO_SUBCATEGORY) {

                    Intent intent = new Intent(mContext, SubCategoryActivity.class);
                    intent.putExtra("isChangeAddress", true);
                    startActivity(intent);

                } else if (fromChangeAddress == TO_CHOOSE_OUTLET) {
                    Intent intent = new Intent(mContext, ChooseOutletActivity.class);
                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                        intent.putExtra("availability", "delivery");
                    } else {
                        intent.putExtra("availability", "takeaway");
                    }
                    startActivity(intent);
                    finish();

                } else {
                    intent = new Intent(mContext, ThanksActivity.class);
                    intent.putExtra("local_order_no", mOrderNo);
                    startActivity(intent);

                }
                finish();
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

                    responseJSONObject = new JSONObject(response);
                    status = responseJSONObject.getString("status");
                    if (status.equals("success")) {
                        //Success
                        message = responseJSONObject.getString("message");
/*
                        type = responseJSONObject.getString("type");
*/
                        JSONObject resultSetJSONObj = responseJSONObject.getJSONObject("result_set");

                        promotionID = resultSetJSONObj.getString("promotion_id");
                        if (GlobalValues.promoID.equalsIgnoreCase(promotionID)) {

                            removePromotion();
                        }

                        //    JSONObject resultSetJSONObj = resultSetJSONArray.getJSONObject(0);

                        String deliveryChargeDiscount = resultSetJSONObj.getString("promotion_delivery_charge_applied");

                        //CommonClass.discountCode=couponCodeEditText.getText().toString();
                        GlobalValues.DISCOUNT_CODE = resultSetJSONObj.getString("promotion_code");


                        if (deliveryChargeDiscount.equals("Yes")) {

                            if (GlobalValues.CURRENT_AVAILABLITY_ID.equals(GlobalValues.DELIVERY_ID)) {

                                GlobalValues.DISCOUNT_APPLIED = true;
                                GlobalValues.DISCOUNT_TYPE = "COUPON";

                                GlobalValues.DELIVERYCHARGEDISCOUNT = true;
                                GlobalValues.COMMON_DELIVERY_CHARGE = 0.0;

                                Toast.makeText(mContext, message, Toast.LENGTH_LONG).show();

//                                CallMessagenew(message, "Thanks!", GlobalValues.DISCOUNT_CODE, resultSetJSONObj.getString("promotion_amount"));
                                CallMessagenew(message, "Thanks!", responseJSONObject.toString());

                                //CallMessage(message);

                            } else {
                                Toast.makeText(mContext, "Coupon valid only for Delivery.", Toast.LENGTH_LONG).show();
                            }
                        } else {
                            GlobalValues.DISCOUNT_APPLIED = true;
                            GlobalValues.DISCOUNT_TYPE = "COUPON";

                            GlobalValues.DELIVERYCHARGEDISCOUNT = false;

                            String discountAmount = resultSetJSONObj.getString("promotion_amount");

                            //GlobalValues.promotionDiscountAmount = Double.parseDouble(discountAmount);

                            //  Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//

//                            CallMessagenew(message, "Thanks!", GlobalValues.DISCOUNT_CODE,resultSetJSONObj.getString("promotion_amount"));
                            CallMessagenew(message, "Thanks!", responseJSONObject.toString());
                            // FinishedActivity();
                        }
                    } else {
                        if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals("")) {

                        }

                        message = responseJSONObject.getString("message");
//                        Toast.makeText(getActivity(), message, Toast.LENGTH_LONG).show();//

                        String form_error = responseJSONObject.optString("form_error");

                        String messageCom = message + "" + Html.fromHtml(form_error);

                        CallMessage(messageCom, "OK");

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals("")) {
                    }
                }

            } else {

                if (GlobalValues.DISCOUNT_CODE == null || GlobalValues.DISCOUNT_CODE.equals("")) {
                }

                CallMessage(message, "OK");
            }
        }
    }

    public void CallMessage(String message, final String msg) {
        Log.e("msg", message);
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);

        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {


                if (msg.equalsIgnoreCase("OK")) {

                    if (alertDialog != null) {
                        alertDialog.dismiss();
                    }

                    //mContext.finish();
                    if (dialogPromo != null) {
                        dialogPromo.dismiss();
                    }

                } else {
                    alertDialog.dismiss();
                }

            }
        });
        alertDialog = alertDialogBuilder.create();

        alertDialog.show();


    }

    public void CallMessagenew(final String message, final String msg, final String response) {
        androidx.appcompat.app.AlertDialog.Builder alertDialogBuilder = new androidx.appcompat.app.AlertDialog.Builder(mContext);
        alertDialogBuilder.setMessage(message);

        alertDialogBuilder.setNegativeButton(msg, new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                //promotionApply(response);
                if (dialogPromo != null && dialogPromo.isShowing()) {
                    dialog.dismiss();
                }
                alertDialog.dismiss();
            }

        });

        promotionApply(response);

        alertDialog = alertDialogBuilder.create();

        alertDialog.show();
    }

    private class CouponCodeTask1 extends AsyncTask<String, String, String> {

        String response, status, message, type;

        ProgressDialog progressDialog;

        Map<String, String> couponParams;

        String couponCode = "";

        Dialog mDialog;

        CouponCodeTask1(Map<String, String> params, String couponCode) {
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

    private synchronized void parseCouponPointResponse(String response) throws JSONException {
        JSONObject responseObj = new JSONObject(response);

        if (responseObj.optString("status").equalsIgnoreCase("success")) {

            /*if(isApplyPromo){
                removePromotion();
            }*/

            if (!(GlobalValues.promoID.equalsIgnoreCase(promotionID)) && !(GlobalValues.promoID.equalsIgnoreCase(""))) {
                 removePromotion();
            }

            /*if(GlobalValues.firstTimePromoCode.equalsIgnoreCase(GlobalValues.couponCodeFromFiveMenu)){
                removePromotion();
            }*/

            GlobalValues.DISCOUNT_APPLIED = true;
            GlobalValues.DISCOUNT_TYPE = "COUPON";

            Toast.makeText(mContext, "" + responseObj.optString("message"), Toast.LENGTH_SHORT).show();
            layoutdiscount.setVisibility(View.VISIBLE);
            JSONObject resultObj = responseObj.getJSONObject("result_set");

            r_sub_total = txtSubTotal.getText().toString().replace("$", "");

            try {
                if (resultObj.getString("promotion_delivery_charge_applied").equalsIgnoreCase("yes")) {
                 } else {
                     layoutdiscount.setVisibility(View.VISIBLE);

                    GlobalValues.PRMOTION_DELIVERY_APPLIED = false;

                    txtDiscountTotal.setText(String.format("%.2f", resultObj.optDouble("promotion_amount")) + ")");

                    mPromotionAmount = String.format("%.2f", resultObj.optDouble("promotion_amount"));
                    setOverallTotal(resultObj.optDouble("promotion_amount"), "APPLY", "P");

                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            mPromoCoupon = resultObj.optString("promotion_code");
            edtPromotion.setText(resultObj.optString("promotion_code"));
            txtDiscountLabel.setText("Promotion" + "(" + resultObj.optString("promotion_code") + ")");
            edtPromotion.setEnabled(false);
            txtPromoApply.setText("REMOVE");
            isApplyPromo = true;
            promo_subTotal = resultObj.optString("cart_amount");
            GlobalValues.promoID = resultObj.optString("promotion_id");
            /*new PromotionInfoTask(null).execute(GlobalUrl.PROMOTION_REDEEM_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID)
                    + "&availability_id=" + CURRENT_AVAILABLITY_ID);*/
        } else {
            layoutdiscount.setVisibility(View.GONE);
            Toast.makeText(mContext, responseObj.optString("message"), Toast.LENGTH_SHORT).show();
        }

        if (GlobalValues.isPromoAdded) {
            GlobalValues.isPromoAdded = false;
        }
    }

//    private class CartTATtask1 extends AsyncTask<String, Void, String> {
//
//        private ProgressDialog progressDialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(mContext);
//            progressDialog.setMessage("Please wait...");
//            progressDialog.setCancelable(false);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//
//
//            String response = WebserviceAssessor.getData(params[0]);
//
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//
//                JSONObject jsonObject = new JSONObject(s);
//
//
//
//                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
//                    if (jsonObject.getString("tattime").equalsIgnoreCase("null") || jsonObject.getString("tattime") == null) {
//                        mTatTime = 0;
//                    } else {
//                        mTatTime = Integer.parseInt(jsonObject.getString("tattime"));
//                    }
//                } else {
//                    mTatTime = 0;
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//                mTatTime = 0;
//            } finally {
//
//                progressDialog.dismiss();
//                String url = GlobalUrl.DAYAVAILABLE_URL2 + "?app_id=" + GlobalValues.APP_ID + "&availability=" +
//                        Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID) +
//                        "&outlet_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);
//
//                GlobalValues.CURRENT_TAT_TIME = String.valueOf(mTatTime);
//
//                new CheckDayAvailability().execute(url);
//
//            }
//        }
//    }


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

    public void InclusiveGst(Double GrandTotal) {
        //GST Calculation
        Double Grandamt = 0.0;
        GlobalValues.GstChargers = Utility.readFromSharedPreference(mContext, GlobalValues.GstCharger);
//        Double Ca1 = 1.0 + Double.parseDouble(GlobalValues.GstChargers) / 100;
        Double Ca2 = Double.parseDouble(GlobalValues.GstChargers) / 100;
//        Double productc1 = GrandTotal / Ca1;
//        Grandamt = productc1 * Ca2;

        Grandamt = (mGrandTotal * Ca2);

        Double test=(cart_sub_total * Ca2);

        Log.e("TAG","GSTTest::"+mGrandTotal+"\n"+Ca2+"\n"+Grandamt+"\n"+test);

        if (Grandamt <= 0.00) {
            Grandamt = 0.00;
        }

    //    mGrandTotal += Grandamt;



//        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//            txt_insulsive_gst.setVisibility(View.GONE);
//
//        } else {
        if(Grandamt == 0.00){
            txt_insulsive_gst1.setVisibility(View.GONE);
        }else {
            layoutGSTlabel.setVisibility(View.GONE);         //Hide
            txt_insulsive_gst1.setVisibility(View.VISIBLE);
            String tot=String.format("%.2f", Grandamt);
            txt_insulsive_gst1.setText("GST Inclusive ("+GlobalValues.GstChargers + "%) : $ " +tot);
        }

        txt_insulsive_gst.setVisibility(View.GONE);//  Hide

        //txt_insulsive_gst.setText("GST Inclusive (7%): $ " + String.format("%.2f", Grandamt));
        txt_insulsive_gst.setText(String.format("%.2f", Grandamt));
        gstAmount = Double.parseDouble(String.format("%.2f", Grandamt));
        txtTotal.setText(String.format("%.2f",/* Grandamt +*/ GrandTotal));
        GlobalValues.GST = Grandamt;
//        }
    }

    private class SetMenuProductDetailsTask0 extends AsyncTask<String, Void, String> {

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

        public SetMenuProductDetailsTask0(String id) {
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
                            setMenuProduct.setmproduct_apply_minmax_select(jsonResult.getString("product_apply_minmax_select"));

                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
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

                            quantityCost = Double.valueOf(cartList.get(CurrentPosition).getmProductTotalPrice());


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
                                                    String ProductID = object.optString("product_id");

                                                    String ProductName = object.optString("product_alias");

                                                    try {
                                                        if (cartList.get(CurrentPosition).getSetMenuTitleList() != null && cartList.get(CurrentPosition).getSetMenuTitleList().size() > 0) {

                                                            String name = "";

                                                            List<SetMenuTitle> setMenuTitleList = cartList.get(CurrentPosition).getSetMenuTitleList();

                                                            for (int t1 = 0; t1 < setMenuTitleList.size(); t1++) {
                                                                SetMenuTitle setMenuTitle = setMenuTitleList.get(t1);


                                                                List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                                                                if (setMenuModifierList != null && setMenuModifierList.size() > 0) {

                                                                    for (int sm = 0; sm < setMenuTitle.getSetMenuModifierList().size(); sm++) {
                                                                        SetMenuModifier setMenuModifiers = setMenuTitle.getSetMenuModifierList().get(sm);

                                                                        if (setMenuModifiers.getmMin_Max_apply().equalsIgnoreCase("1")) {
                                                                            if (setMenuModifiers.getmQuantity() > 0) {
                                                                                name += "" + setMenuModifiers.getmQuantity() + "x";


                                                                                if (ProductID.equals(setMenuModifiers.getmModifierId())) {

                                                                                    setMenuModifier.setmQuantityUpdates(setMenuModifiers.getmQuantityUpdates());
                                                                                    setMenuModifier.setmQuantity(0);


                                                                                } else {

                                                                                    setMenuModifier.setmQuantity(0);
                                                                                    setMenuModifier.setmQuantityUpdates(0);

                                                                                }
                                                                            } else {

                                                                            }
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

                                                        Log.v("is object", "true");

                                                        JSONArray jsonModifiersArray = object.getJSONArray("modifiers");

                                                        Log.v("mod foo size", jsonModifiersArray.length() + "");

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
                                                            quantityCost = mSetMenuPrice;
                                                        }


                                                    } else {
/*
                                                        setMenuModifier.setChecked(false);
*/
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
                        setMenuproductDetailsDialog(mContext, mProductId, mQuantity);

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

            Log.v("Product Details Service", params[0] + "\n");

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

                                                        Log.v("is object", "true");

                                                        JSONArray jsonModifiersArray = object.getJSONArray("modifiers");

                                                        Log.v("mod foo size", jsonModifiersArray.length() + "");

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
                                                                                                    Log.e("ModName", GlobalValues.SELECTEDMODIFIERVALUES.get(l).getModifierQty() + "--" + jsonModifiervalue.getString("pro_modifier_value_name"));

                                                                                                    Log.e("ModName", GlobalValues.SELECTEDMODIFIERVALUES.get(l).getModifierQty() + "--" + jsonModifiervalue.getString("pro_modifier_value_name") + "----"
                                                                                                            + setMenuTitle.getmAppliedPrice() + "----" + jsonModifiervalue.getString("pro_modifier_value_price"));

                                                                                                    if (setMenuTitle.getmAppliedPrice().equalsIgnoreCase("1")) {

                                                                                                    } else {
                                                                                                        Log.e("childbefore", String.valueOf(plusminusPrice));
                                                                                                        plusminusPrice -= (Double.parseDouble(jsonModifiervalue.getString("pro_modifier_value_price")) * GlobalValues.SELECTEDMODIFIERVALUES.get(l).getModifierQty());
                                                                                                        Log.e("childafter", String.valueOf(plusminusPrice));
                                                                                                    }
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

                                                                Log.v("list sizez", modifiersValueList.size() + "\n" + modifierHeadingList.size());
                                                            }

                                                        } else {
                                                            setMenuModifier.setHasModifier(false);
                                                        }

                                                    } else {
                                                        Log.v("is object", "false");
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

    private void setMenuproductDetailsDialog1(final Context mContext, final String mProductId, String quantity) {
        Log.e("cost", TotalPriceSetMenu + "--" + quantityCost + "--" + plusminusPrice + "--" + mSearchProuductprise + "--" + SubCategoryActivity.mSetmenuoverallprices);
        SubCategoryActivity.quantityCost = quantityCost;
        double singleQuantityCost = quantityCost / Integer.parseInt(quantity);
        SubCategoryActivity subCategoryActivity = new SubCategoryActivity();
        mProductQuantity = quantity;
        SubCategoryActivity.mSetMenuQuantity = Integer.parseInt(quantity);
        //SubCategoryActivity.mSetMenuPrice = quantityCost;
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
        SubCategoryActivity.txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

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

       /* Log.v("Agedae", productsList.get(position).getmFaouriteStatusLable() + "............");
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


        Log.v("modifier quantity", mProductQuantity);

        Log.v("modifier quantity", mProductQuantity);

        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2) {

            SubCategoryActivity.txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

            cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(SubCategoryActivity.mSearchProuductprise)));

            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
            //SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost + plusminusPrice)));
            SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal((SubCategoryActivity.mSetMenuPrice + plusminusPrice) * Integer.parseInt(quantity))));

            if (setMenuProduct.getSetMenuTitleList() != null && setMenuProduct.getSetMenuTitleList().size() > 0) {
                //Log.v("Afdf", String.valueOf(position));
               /* if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {*/
                /*Log.v("adfsd", setMenuProduct.getSetMenuTitleList().get(position).getmenu_component_modifier_apply() + "..." +
                        setMenuProduct.getSetMenuTitleList().get(position).getmultipleselection_apply() + "...." + position);*/
                /*if ((setMenuProduct.getmApplyMinMaxSelect() == 1)
                        && setMenuProduct.getSetMenuTitleList().get(position).getmenu_component_modifier_apply().equalsIgnoreCase("0")
                        && setMenuProduct.getSetMenuTitleList().get(position).getmultipleselection_apply().equalsIgnoreCase("0")) {

                    Log.v("skljdfkldsf", "plusMinus");
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

                SubCategoryActivity.txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                //SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost + plusminusPrice)));
                SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal((SubCategoryActivity.mSetMenuPrice + plusminusPrice) * Integer.parseInt(quantity))));
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

        /*Log.v("set menuimage", productsList.get(productHolder.getAdapterPosition()).getmSubCategoryGalleryImage() + "\nsize" +
                setMenuProduct.getSetMenuTitleList().size());
        Log.v("setImagePosition", String.valueOf(productHolder.getAdapterPosition()));

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
                        SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));

                    } catch (Exception e) {
                        e.printStackTrace();
                    }


                } else {

                    int count = Integer.parseInt(txtQuantity.getText().toString());
                    //double modPrice = subModifierPrice / mSetMenuQuantity;
                    count++;
                    SubCategoryActivity.mSetMenuQuantity = count;
                    SubCategoryActivity.quantityCost = (SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * SubCategoryActivity.mSetMenuQuantity;

                    txtQuantity.setText(String.valueOf(count));

                    if (SubCategoryActivity.mSetmenuoverallprices != 0.00) {
                        SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                    } else {
                        SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                    }

                    mProductQuantity = txtQuantity.getText().toString();
                    Log.v("dkafjdsfa", count + "...." + SubCategoryActivity.quantityCost + " = " + SubCategoryActivity.mSetMenuPrice + " * " + SubCategoryActivity.mSetMenuQuantity + "...." + mProductQuantity);

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
                            SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
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
                        Log.v("sdafdfg", String.valueOf(modPrice));
                        double txtQty = Double.valueOf(txtQuantity.getText().toString()) + 1d;
                        double subModiPrice = value * count;
                        double setMenuPrice = SubCategoryActivity.mSetMenuPrice + subModiPrice;

                        //Log.v("dkslagda", mSetMenuPrice + " + " + subModifierPrice + " / " + Double.valueOf(txtQuantity.getText().toString()) + " * " + mSetMenuQuantity);
                        SubCategoryActivity.quantityCost = (SubCategoryActivity.mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * SubCategoryActivity.mSetMenuQuantity;
                        //quantityCost = setMenuPrice * ((double)(mSetMenuQuantity));
                        txtQuantity.setText(count + "");

                        if (SubCategoryActivity.mSetmenuoverallprices != 0.00) {
                            SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
                        } else {
                            SubCategoryActivity.txtModifierPrice.setText(String.format("%.2f", new BigDecimal(SubCategoryActivity.quantityCost)));
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

    private void setMenuproductDetailsDialog(final Context mContext, String mProductId, String quantity) {

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
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
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


        txtQuantity.setText("" + quantity);


        txtQuantity.setText("" + mProductQuantity);

        cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModifierPrice.setText(cs);

        mquanititycost_src = mSetMenuPrice;

      /*  try {
            mSetMenuPrice = Double.valueOf(setMenuProduct.getmProductPrice());
            mSetMenuBasePrice = Double.valueOf(setMenuProduct.getmProductPrice());
        }catch (NumberFormatException e)
        {
            mSetMenuPrice=0.0;
            mSetMenuBasePrice=0.0;
            e.printStackTrace();
        }*/


        Log.v("modifier quantity", mProductQuantity);

        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2) {

            if (setMenuProduct.getSetMenuTitleList() != null &&
                    setMenuProduct.getSetMenuTitleList().size() > 0) {

                setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                        setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "update");
                setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                setmenuRecyclerView.setNestedScrollingEnabled(false);
                setmenuRecyclerView.setHasFixedSize(true);
            }
        } else {

        }

        Log.v("image path", mBasePath + setMenuProduct.getmProductImage() + "\nsize" +
                setMenuProduct.getSetMenuTitleList().size());

        if (setMenuProduct.getmProductImage() != null && setMenuProduct.getmProductImage().length() > 0) {
            if (setMenuProduct.getmProductImage().contains(".jpg") || setMenuProduct.getmProductImage().contains(".jpeg")
                    || setMenuProduct.getmProductImage().contains(".png") || setMenuProduct.getmProductImage().contains(".gif")) {
                Picasso.with(mContext).load(setMenuProduct.getmProductImage()).
                        error(R.drawable.place_holder_sushi_tei).into(imgProduct);
            } else {
                imgProduct.setVisibility(View.GONE);
            }
        } else {
            imgProduct.setVisibility(View.GONE);
            // Picasso.with(mContext).load(R.drawable.default_image).into(imgProduct);
        }

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

                    Log.v("set type", "" + setMenuProduct.getmApplyMinMaxSelect());
                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    mSetMenuQuantity = count;


                    quantityCost = mSetMenuQuantity * mSetMenuBasePrice;

                    Double quantityCost_menu = quantityCost + mSetmenuoverallprices;

                    mquanititycost_src = quantityCost_menu;


                    Log.v(" quantity cost", quantityCost + "\n" + txtModifierPrice.getText().toString());


                    txtQuantity.setText(String.valueOf(count));


                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost_menu));


                    mProductQuantity = txtQuantity.getText().toString();

                    Log.v("setmenu quantity inc", mProductQuantity + "\n" + txtModifierPrice.getText().toString());

                } else {
                    Log.v("set type", "" + setMenuProduct.getmApplyMinMaxSelect());

                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    mSetMenuQuantity = count;
                    quantityCost += mSetMenuPrice;

                    txtQuantity.setText(String.valueOf(count));

                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                    mProductQuantity = txtQuantity.getText().toString();

                    Log.v("setmenu quantity inc", mProductQuantity + "\n" + txtModifierPrice.getText().toString());


                }


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    if (count > 1) {
                        count--;
                        mSetMenuQuantity = count;

                        Log.v("Count :", mSetMenuQuantity + "" + count);


                        Log.v("Prices :", mSetmenuoverallprices + "  base Price:" + mSetMenuBasePrice);

                        quantityCost = mSetMenuQuantity * mSetMenuBasePrice;


                        Double quantityCost_menu = quantityCost + mSetmenuoverallprices;

                        mquanititycost_src = quantityCost_menu;

                        txtQuantity.setText(count + "");

                        Log.v("quantity cost", quantityCost + "");

                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost_menu));

                        mProductQuantity = txtQuantity.getText().toString();

                        // Toast.makeText(mContext,""+count,Toast.LENGTH_SHORT).show();


                        Log.v("setmenu quantity dec", mProductQuantity + "\n" + txtModifierPrice.getText().toString());
                    } else {

               /*   txtModifierPrice.setText(cs);

                    mProductQuantity = txtQuantity.getText().toString();*/

                    }
                } else {
                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    if (count > 1) {
                        count--;
                        mSetMenuQuantity = count;
                        quantityCost -= mSetMenuPrice;

                        txtQuantity.setText(count + "");

                        Log.v("quantity cost", quantityCost + "");

                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                        mProductQuantity = txtQuantity.getText().toString();

                        Log.v("setmenu quantity dec", mProductQuantity + "\n" + txtModifierPrice.getText().toString());
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

                validateSetMenu(setMenuProduct.getSetMenuTitleList());

            }
        });

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
                    Log.v("kldsjakljgerea", String.valueOf(setMenuProduct.getSetMenuTitleList().get(i).gettQuantity()));
                    Log.v("kldsjakljgerea2", String.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect()));
                    if (setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() < setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect()) {
                        Log.v("dakfjdlagdfad", "dskfaldkfad12");
                       /* Toast.makeText(mContext, "Please select minimum  " + setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect() + " product for "
                                + setMenuProduct.getSetMenuTitleList().get(i).getmTitleMenuName() + "", Toast.LENGTH_SHORT).show();*/
                        if (setMenuTitleList.get(i).getmTitleMenuName().equalsIgnoreCase("Sugar Level")) {
                            Toast.makeText(mContext, "Please select sugar level", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Please select size and topping", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    } else {
                        Log.v("dakfjdlagdfad22", "dskfaldkfad");
                    }
                    int postion;
                    if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                            && setMenuTitleList.get(j).getSetMenuModifierList().size() >= 0) {

                        if (GlobalValues.MODIFIER_NAME.equalsIgnoreCase("Medium")) {
                            postion = 0;
                        } else if (GlobalValues.MODIFIER_NAME.equalsIgnoreCase("Large")) {
                            postion = 1;
                        } else {
                            postion = 0;
                        }

                        for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                            modifierHeadings = setMenuTitleList.get(j).getSetMenuModifierList().get(postion).getModifierHeadingList();

                            Log.v("dsakjhehkjhdafdag", String.valueOf(modifierHeadings.size()));
                            Log.v("dsakjhehkjhdafdag1", String.valueOf(postion));

                            if (modifierHeadings != null && modifierHeadings.size() > 0) {
                                for (int a = 0; a < modifierHeadings.size(); a++) {
                                    modifiersValue = modifierHeadings.get(a).getModifiersValueList();
                                    for (int b = 0; b < modifiersValue.size(); b++) {
                                        Log.v("dsaksdjfalkgjake", String.valueOf(modifierHeadings.get(a).gettQuantity()));
                                        Log.v("dsaksdjfalkgjake1", String.valueOf(modifierHeadings.get(a).getmModifierMinSelect()));
                                        Log.v("namnfd", modifierHeadings.get(a).getmModifierHeading());

                                        if (modifierHeadings.get(a).gettQuantity() < modifierHeadings.get(a).getmModifierMinSelect()) {
                                            Log.v("dsadfg", "adgfdasf");
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

                        mapData.put("product_total_price", SubCategoryActivity.txtModifierPrice.getText().toString().replace("$", ""));

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
            mapData.put("product_total_price", SubCategoryActivity.txtModifierPrice.getText().toString().replace("$", ""));//4.80

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

    private void validateSetMenu0(List<SetMenuTitle> setMenuTitleList) {

        boolean isChildSelected = false;
        int count = 0;
        for (int j = 0; j < setMenuTitleList.size(); j++) {

            isChildSelected = false;

            if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

                if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                        && setMenuTitleList.get(j).getSetMenuModifierList().size() >= 0) {

                    for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                        if (setMenuTitleList.get(j).getSetMenuModifierList().get(k).isChecked()) {
                            count++;
                            isChildSelected = true;
                            break;
                        } else {
                            isChildSelected = false;
                        }
                    }
                    if (!isChildSelected) {
                        mValidationMessage = setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }

                }
            } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

                if (setMenuTitleList.get(j).getSetMenuModifierList() != null && setMenuTitleList.get(j).getSetMenuModifierList().size() > 0) {

                    String msg = "";

                    for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {

                        if (setMenuTitleList.get(j).getSetMenuModifierList().get(k).getmQuantity() >= 0) {

                            if (setMenuTitleList.get(j).getmTotalQuantity() >= setMenuTitleList.get(j).getmMinSelect()) {

                                count++;
                                isChildSelected = true;
                                break;
                            } else {

                                isChildSelected = false;
                                msg = "Please select minimum quantity in ";
                                break;
                            }

                        } else {
                            isChildSelected = false;
                            msg = "Please select option in ";
                        }
                    }
                    if (!isChildSelected) {
                        mValidationMessage = msg + setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }

                }


            }
        }


        if (count == setMenuTitleList.size()) {
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


                Log.v("final modifier", mProductQuantity);

                Log.v("MProduct prices", "" + mquanititycost_src);


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
                    mapData.put("product_total_price", String.valueOf(mquanititycost_src));

                    mapData.put("product_unit_price", String.valueOf(mquanititycost_src));


                } else {
                    mapData.put("product_total_price", String.valueOf(quantityCost));

                    mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));

                }
                mapData.put("product_image", setMenuProduct.getmProductImage());
                mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
                mapData.put("product_sku", setMenuProduct.getmProductSku());
                mapData.put("product_type", setMenuProduct.getmProductType());

              /*  if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                    mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                } else {
                    mapData.put("product_modifier_parent_id", "");

                }*/
                mapData.put("product_modifier_parent_id", "");


                mapData.put("menu_set_component", constructSetMenuJson().toString());


                new AddCartTask(mapData, mProductQuantity).execute(url);

                mquanititycost_src = 0.0;

            } else {
                Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText(mContext, "Please select one option in " + mValidationMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private String constructSetMenuJson0() {
        try {

            if (setMenuProduct.getmApplyMinMaxSelect() == 1) {  //New quantity type product

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

                    for (int i1 = 0; i1 < arrProduct.size(); i1++) {
                        JSONObject proobj = new JSONObject();

                        if (Integer.valueOf(arrProduct.get(i1).getTotalQuantity()) > 0) {
                            try {
                                proobj.put("product_id", arrProduct.get(i1).getmModifierId());
                                proobj.put("product_name", arrProduct.get(i1).getmModifierAliasName());
                                proobj.put("product_sku", arrProduct.get(i1).getmModifierSku());
                                proobj.put("product_price", arrProduct.get(i1).getmModifierPrice());
                                proobj.put("product_qty", arrProduct.get(i1).getTotalQuantity());
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
//                mapData.put("menu_set_component", menuComponentsJSONArray.toString());

            }

            //        formBodyBuilder.add("modifiers", "null");//null

        } catch (JSONException e) {
            e.printStackTrace();

            return "";
        }
    }

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

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                    if (dialog != null) {
                        dialog.dismiss();
                    }


                    DeleteIndividualproduction(CurrentPosition);


                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                progressDialog.dismiss();
                if (dialog != null) {
                    dialog.dismiss();
                }
                finish();
            }

        }
    }

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
            params.put("reference_id", mReferenceId);

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


                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    deleteCurrentQuantity(mProductId);

                    if (!jsonObject.isNull("contents")) {

                        JSONObject jsonResult = jsonObject.getJSONObject("contents");


                        JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");


                        if (txtPromoApply.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removePromotion();
                        }


                        if (redeemPoints.getText().toString().equalsIgnoreCase("REMOVE")) {
                            removeRewardPoints();
                        }

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, jsoncartDetails.getString("cart_total_items"));


                        txtSubTotal.setText(jsoncartDetails.getString("cart_sub_total"));

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


                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));

                                txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");

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
                                txtDeliveryCharge.setText("$0.00");
                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
                                txtFreeDelivery.setText("FREE delivery!");

//                            progressBar.setProgress(1000);

                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"));

                                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
                                            Double.valueOf(outletZoneJson.getString("zone_delivery_charge"));


                                    mGST = (mGrandTotal * 7) / 100;
                                    GlobalValues.GST = mGST;
                                    mGrandTotal += mGST;

                                    txtGSTLabel.setText("GST (" + "7" + "%)");
                                    txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));

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
                        txtGST.setText("$" + String.format("%.2f", mGST));


                        JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());

                        Log.v("read from memory", Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE));

//                        Remove applied coupon or reward points


                        setCartAdapter(jsonCartItem);

                        if (foodVoucher) {
                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
                        }

                        InclusiveGst(mGrandTotal);
//                        txtTotal.setText(String.format("%.2f", mGrandTotal + gstAmount));
                        txtTotal.setText(String.format("%.2f", mGrandTotal));

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

                        //   Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }

                } else {
                    //Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                e.printStackTrace();
            }


            ReCallCarlistServices();


        }
    }

    private void ReCallCarlistServices() {
    /*    if (Utility.networkCheck(mContext))
        {

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                layoutRewards.setVisibility(View.VISIBLE);

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = "";

            } else {

                layoutRewards.setVisibility(View.GONE);

                mCustomerId = "";
                mReferenceId = Utility.getReferenceId(mContext);
            }

            GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);

            String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + mCustomerId +
                    "&reference_id=" + mReferenceId +
                    "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;



                new CartListTask().execute(url);


        } else {

            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }*/


        Intent i = new Intent(OrderSummaryActivity.this, OrderSummaryActivity.class);
        mContext.startActivity(i);
        finish();

    }


    public void makeUpdateApiCall(String productId, Cart cartProduct, String type) {
        productCartDetails = cartProduct;

        if (Utility.networkCheck(mContext)) {

            mProductId = productId;

            String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&availability=" + CURRENT_AVAILABLITY_ID +
                    "&product_id=" + mProductId;

            new SetMenuProductDetailsTaskNew(mProductId).execute(url);

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
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

        private String mProductId = "", mQuantity = cartList.get(CurrentPosition).getmProductQty();

        public SetMenuProductDetailsTaskNew(String id) {
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

            Log.v("Product Details Service", params[0] + "\n");

            String response = WebserviceAssessor.getData(params[0]);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            double price = 0.0;
            Double totalVale = 0.00;
            try {

                Log.v("setmenu detail resposne", s);

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
                            setMenuProduct.setmproduct_apply_minmax_select(jsonResult.getString("product_apply_minmax_select"));
                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
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

                                                    modifierHeadingList = new ArrayList<>();

                                                    Object modifierObject = object.get("modifiers");

                                                    if (modifierObject instanceof JSONArray) {

                                                        Log.v("is object", "true");

                                                        JSONArray jsonModifiersArray = object.getJSONArray("modifiers");

                                                        Log.v("mod foo size", jsonModifiersArray.length() + "");

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
                                                            quantityCost = mSetMenuPrice;
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

                        setMenuproductDetailsDialogNew(mContext, mProductId, mQuantity, totalVale);

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

    private void setMenuproductDetailsDialogNew(final Context mContext, String mProductId, String quantity, Double totalVale) {


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
        try {

            mSearchProuductprise = Double.valueOf(setMenuProduct.getmProductPrice());
        } catch (Exception e) {
            mSearchProuductprise = 0.0;
        }
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


        Log.v("modifier quantity", mProductQuantity);

        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2) {

            if (setMenuProduct.getSetMenuTitleList() != null &&
                    setMenuProduct.getSetMenuTitleList().size() > 0) {

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

            }
        } else {

        }

        Log.v("image path", mBasePath + setMenuProduct.getmProductImage() + "\nsize" +
                setMenuProduct.getSetMenuTitleList().size());

        if (setMenuProduct.getmProductImage() != null && setMenuProduct.getmProductImage().length() > 0) {

            Picasso.with(mContext).load(setMenuProduct.getmProductImage()).
                    error(R.drawable.place_holder_sushi_tei).into(imgProduct);

        } else {

            Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);
        }

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(txtQuantity.getText().toString());

                count++;
                txtQuantity.setText(String.valueOf(count));


//                    Double incprice = Double.valueOf(txtModi.getText().toString().replace("$", "")) + mSearchProuductprise;
                try {
                    txtModi.setText(Utility.setPriceSpan(mContext, getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString())));
//                        getsetMenuProductPrice();
                } catch (Exception e) {
                    e.printStackTrace();
                }

//
                mProductQuantity = txtQuantity.getText().toString();


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                int count = Integer.parseInt(txtQuantity.getText().toString());

                if (count > 1) {
                    count--;

//                        Double incprice = Double.valueOf(txtModi.getText().toString().replace("$", "")) - mSearchProuductprise;


                    txtQuantity.setText(count + "");

                    try {
                        txtModi.setText(Utility.setPriceSpan(mContext, getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString())));
//                        getsetMenuProductPrice();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
//                        txtModi.setText(Utility.setPriceSpan(mContext, incprice));
//
                    mProductQuantity = txtQuantity.getText().toString();
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
                validateSetMenuNew();
//                validateSetMenu(setMenuProduct.getSetMenuTitleList());

            }
        });

    }

    private void validateSetMenuNew() {
        for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
//
            if (setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() < setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect()) {
                Toast.makeText(mContext, "Minimum  " + setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect() + " not selected", Toast.LENGTH_SHORT).show();
                return;
            }

        }

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

            Log.v("final modifier", mProductQuantity);

            Log.v("MProduct prices", "" + mquanititycost_src);

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
/*
                    if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                        mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                    } else {
                        mapData.put("product_modifier_parent_id", "");

                    }*/
            mapData.put("product_modifier_parent_id", "");

            mapData.put("menu_set_component", constructSetMenuJson().toString());


            new AddCartTask(mapData, mProductQuantity).execute(url);

            mquanititycost_src = 0.0;

        } else {
            Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
        }
    }

    public void makeAddTotalCalculation(String productPrice) {

        Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) + Double.valueOf(productPrice);

        SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModi.setText(cs);
    }

    public void makeSubstTotalCalculation(String productPrice) {
        Double finalVal = Double.valueOf(txtModi.getText().toString().replace("$", "")) - Double.valueOf(productPrice);

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

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    public static void hideKeyboard(Activity activity) {
        Log.v("dsfjkg", "Dsfa");
        InputMethodManager imm = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        //Find the currently focused view, so we can grab the correct window token from it.
        View view = activity.getCurrentFocus();
        //If no view currently has focus, create a new one, just so we can grab a window token from it
        if (view == null) {
            view = new View(activity);
        }
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

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

//    /*  private void openDialogbox()
//          {
//        *//*final AlertDialog.Builder builder;
//
//        builder = new AlertDialog.Builder(this);
//        //Uncomment the below code to Set the message and title from the strings.xml file
//         setTitle("Spize");
//
//        //Setting message manually and performing action on button click
//        builder.setMessage("Minimum Qty "+ Utility.readFromSharedPreference(mContext,GlobalValues.MinimumQuality) + " allowed in shopping cart")
//                .setCancelable(false)
//                .setPositiveButton("Ok", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
//                                Toast.LENGTH_SHORT).show();
//                    }
//                });
//        //Creating dialog box
//        AlertDialog alert = builder.create();
//        //Setting the title manually
//        alert.show();*//*
//
//
//                final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.layout_warning_dialog);
//                dialog.show();
//
//                TextView txtChangeAddress = (TextView) dialog.findViewById(R.id.txtChangeAddress);
//                TextView txtWarning = (TextView) dialog.findViewById(R.id.txtWarning);
//                final TextView txtComebackAgain = (TextView) dialog.findViewById(R.id.txtComebackAgain);
//                RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
//
//                txtComebackAgain.setVisibility(View.GONE);
//
//
//
//                txtWarning.setText("Minimum Qty "+ Utility.readFromSharedPreference(mContext,GlobalValues.MinimumQuality) +" allowed in shopping cart");
//
//                txtChangeAddress.setText("Ok");
//
//
//                txtChangeAddress.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//
//                        dialog.dismiss();
//
//                    }
//                });
//
//                layoutClose.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//
//            }*/
//
//
//
//
// /*   private RecyclerView recyclerview_CartItem;
//    private ChoosedCartItemAdapter cartItemAdapter;
//    private Context context;
//    private Toolbar toolbar;
//    private TextView paynow;
//    private LinearLayout imgBack;
//    private ArrayList<Cart> cartArrayList;
//    private JSONObject jsonCartObject;
//    private RelativeLayout layoutCart;
//    private TextView txtCartCount;
//    private ArrayList<SlotTime> slotTimeArrayList;
//    private ArrayList<SlotTime> sundayArrayList;
//    private ArrayList<SlotTime> mondayArrayList;
//    private ArrayList<SlotTime> tuesdayArrayList;
//    private ArrayList<SlotTime> wednesdayArrayList;
//    private ArrayList<SlotTime> thursdayArrayList;
//    private ArrayList<SlotTime> fridayArrayList;
//    private ArrayList<SlotTime> satdayArrayList;
//    private List<Cart> cartList;
//    private List<String> timeList;
//    private Date currentDate, selecteddate;
//    private SimpleDateFormat dateformat = new SimpleDateFormat("dd-MM-yyyy");
//    private SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
//    int intervaltime;
//    private String mCustomerId = "", mReferenceId = "";
//    private String mOrderDate = "";
//    private String mUnitNo1 = "", mUnitNo2 = "", cartCount = "";
//    public static double mGrandTotal = 0.0;
//    public double mGST = 0.0;
//    private JSONObject outletZoneJson;
//    private DatabaseHandler databaseHandler;
//    public static int cutOffTime;
//    private int mTatTime = 0;
//    private boolean is_break = false;
//    private String r_applied,
//            r_point,
//            r_amount;
//    private double cart_sub_total, cart_deleivery_charge, cart_adddeleivery_charge;
//
//    private SimpleDateFormat twelvetimeformat = new SimpleDateFormat("hh:mm");
//    private Double mCarttotal = 0.0;
//    Intent intent;
//    String kitchennote;
//    String subtotal, toatl;
//    private TextView kitchennotetextview;
//    private TextView subtotaltextview, total;
//
//
//
//
//
//
//
//*/
//    /*    context = this;
//        intent = getIntent();
//
//        if (intent != null) {
//
//            //kitchennote=intent.getStringExtra("notes");
//            subtotal = intent.getStringExtra("sub");
//            toatl = intent.getStringExtra("total");
//
//        }
//        cartArrayList = new ArrayList<>();
//        toolbar = (Toolbar) findViewById(R.id.toolBar);
//        setSupportActionBar(toolbar);
//        imgBack = findViewById(R.id.toolbarBack);
//        paynow = findViewById(R.id.paynow);
//        // kitchennotetextview=findViewById(R.id.kichennotefinal);
//        recyclerview_CartItem = findViewById(R.id.recyclerview_CartItem);
//        subtotaltextview = findViewById(R.id.subtotaltextviewsummary);
//        total = findViewById(R.id.totalsummary);
//
//        subtotaltextview.setText(subtotal);
//        total.setText(toatl);
//
//
//        // kitchennotetextview.setText(kitchennote);
//        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID);
//
//        String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
//                "&customer_id=" + Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID) +
//                "&reference_id=" + Utility.getReferenceId(context) +
//                "&availability=" + GlobalValues.AVALABILITY_ID;
//
//        if (!GlobalValues.DISCOUNT_APPLIED) {
//
//            new CartListTask().execute(url);
//        } else {
//
//            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//
//        }
//
//
//        paynow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                // placeCashOnDeliveryOrder();
//                Intent intent = new Intent(context, ThanksActivity.class);
//                startActivity(intent);
//            }
//        });
//        imgBack.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//
//    }*/
//
//   /* private void placeCashOnDeliveryOrder() {
//
//        try {
//            jsonCartObject = new JSONObject(Utility.readFromSharedPreference(context, GlobalValues.CART_RESPONSE).toString());
//
//
//            JSONObject jsonCartDetails = jsonCartObject.getJSONObject("cart_details");
//
//            JSONArray cartJsonArray = jsonCartObject.getJSONArray("cart_items");
//
//            StringBuilder productIdsStringBuilder = new StringBuilder();
//
//            for (int i = 0; i < cartJsonArray.length(); i++) {
//                JSONObject cartItem = cartJsonArray.getJSONObject(i);
//                if (i != (cartJsonArray.length() - 1))
//                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
//                            cartItem.getString("cart_item_total_price") + ";");
//                else {
//                    productIdsStringBuilder.append(cartItem.getString("cart_item_product_id") + "|" +
//                            cartItem.getString("cart_item_total_price"));
//                }
//            }
//
//           *//* try {
//                mGrandTotal = Double.parseDouble(jsonCartDetails.getString("cart_sub_total")) +
//                        Double.parseDouble(outletJson.getString("zone_delivery_charge"));
//            } catch (Exception e) {
//                mGrandTotal = 0.0;
//                e.printStackTrace();
//            }*//*
//
//            Log.v("date", GlobalValues.DELIVERY_DATE + "\t" + GlobalValues.DELIVERY_TIME);
//
//
//            try {
//                String d[] = GlobalValues.DELIVERY_DATE.split("-");
//                String date = d[2] + "-" + d[1] + "-" + d[0] + " " + GlobalValues.DELIVERY_TIME;
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
//
//                Date d1 = simpleDateFormat.parse(date);
//
//                mOrderDate = simpleDateFormat.format(d1);
//                Log.v("order date", mOrderDate);
//
//            } catch (ParseException e) {
//                e.printStackTrace();
//            }
//
//            Map<String, String> params = new HashMap<>();
//            params.put("app_id", GlobalValues.APP_ID);
//
//            params.put("order_source", "Mobile");
//            params.put("order_status", "1");
//            params.put("products", getProductJSONArray(cartJsonArray).toString());
//            params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
//            params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
//            params.put("customer_email", Utility.readFromSharedPreference(context, GlobalValues.EMAIL));
//            params.put("customer_mobile_no", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERPHONE));
//            params.put("customer_fname", Utility.readFromSharedPreference(context, GlobalValues.FIRSTNAME));
//            params.put("customer_lname", Utility.readFromSharedPreference(context, GlobalValues.LASTNAME));
//            params.put("category_id", productIdsStringBuilder.toString());
//            params.put("cart_quantity", jsonCartDetails.getString("cart_total_items"));
//            //params.put("order_remarks", edtComments.getText().toString());
//            params.put("product_leadtime", Utility.readFromSharedPreference(context, GlobalValues.PRODUCT_LEAD_TIME));
//
//            //mCarttotal = Double.parseDouble(txtTotal.getText().toString().replace("$", ""));
//
//            //Log.v("grand tooootal", mCarttotal + "");
//
//            if (mCarttotal > 0) {
//
//                params.put("zero_processing", "No");
//                params.put("allow_order", "No");
//                params.put("validation_only", "Yes");
//                params.put("payment_mode", "1");
//                params.put("sub_total", jsonCartDetails.getString("cart_sub_total"));
//                params.put("grand_total", String.valueOf(mCarttotal));
//                // params.put("grand_total", txtTotal.getText().toString().replace("$", ""));
//            } else {
//                params.put("zero_processing", "Yes");
//                params.put("allow_order", "Yes");
//                params.put("validation_only", "No");
//                params.put("payment_mode", "4");
//                params.put("sub_total", jsonCartDetails.getString("cart_sub_total"));
//                params.put("grand_total", String.valueOf(mCarttotal));
//                // params.put("grand_total", txtTotal.getText().toString().replace("$", ""));
//            }
//
//            params.put("device_type", "Android");
//            params.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);
//
//            params.put("reward_point_status", "Yes");
//
//
//            if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
//
//                JSONObject outletJson = new JSONObject(Utility.readFromSharedPreference(context, GlobalValues.OUTLETZONE));
//
//
//
//
//                JSONObject jsonPostalCodeInfo = outletZoneJson.getJSONObject("postal_code_information");
//
//
//                params.put("customer_address_line1", jsonPostalCodeInfo.optString("zip_buno"));
//                params.put("customer_postal_code", jsonPostalCodeInfo.optString("zip_code"));
//
////                params.put("billing_address_line1", edtBillingAddress.getText().toString());
////                params.put("billing_postal_code", edtPincode.getText().toString());
////                params.put("billing_unit_no1", edtBillingUnitNo1.getText().toString());
////                params.put("billing_unit_no2", edtBillingUnitNo2.getText().toString());
//
//                params.put("order_tat_time", mTatTime + "");
//                params.put("customer_unit_no1", mUnitNo1);
//                params.put("customer_mobile_no", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERPHONE));
//                params.put("customer_unit_no2", mUnitNo2);
//
//            }
//
//            params.put("order_is_advanced", GlobalValues.IS_ADVANCE_ORDER);
//            params.put("delivery_charge", String.valueOf(GlobalValues.COMMON_DELIVERY_CHARGE));
//            params.put("additional_delivery", GlobalValues.ADDITIONAL_DELEIVERY_CHARGES);
//
//            Log.v("foooooo", GlobalValues.DISCOUNT_APPLIED + "");
//
//            if (GlobalValues.DISCOUNT_APPLIED) {
//
//
//                params.put("discount_applied", "Yes");
//
//                if (GlobalValues.DISCOUNT_TYPE.equalsIgnoreCase("COUPON")) {
//
//                    if (GlobalValues.PRMOTION_DELIVERY_APPLIED) {
//
//                        params.put("promotion_delivery_charge_applied", "Yes");
//
//                    }
//
//                } else if (GlobalValues.DISCOUNT_TYPE.equalsIgnoreCase("REWARD")) {
//                    params.put("redeem_applied", "Yes");
//                    params.put("redeem_point", r_point);
//                    params.put("redeem_amount", r_amount);
//
//                }
//            } else {
//                params.put("discount_applied", "No");
//            }
//
//
//            if (GlobalValues.IS_ADVANCE_ORDER.equalsIgnoreCase("no")) {
//                params.put("order_date", mOrderDate);
//
//            } else {
//                params.put("order_date", mOrderDate);
//                params.put("order_advanced_date", mOrderDate);
//            }
//
////            Log.v("submit order params", params.toString());
//            System.out.print(params.toString());
//
//            if (Utility.networkCheck(context)) {
//
//                String url = GlobalUrl.SUBMIT_ORDER_URL;
//
//                new CashOnDeliveryValidateOrderTask(params).execute(url);
//            } else {
//                Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//            }
//
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//
//
//    }*/
//
//
//   /* public JSONArray getProductJSONArray(JSONArray cartJSONArray) {
//
//        JSONArray submitOrderJSONArray = new JSONArray();
//
//        try {
//
//            for (int i = 0; i < cartJSONArray.length(); i++) {
//
//                JSONObject cartJSONObject = cartJSONArray.getJSONObject(i);
//                JSONObject productJSONObject = new JSONObject();
//
//
//
//                String cart_item_id = cartJSONObject.getString("cart_item_id");
//                String cart_item_product_id = cartJSONObject.getString("cart_item_product_id");
//                String cart_item_product_name = cartJSONObject.getString("cart_item_product_name");
//                String cart_item_product_sku = cartJSONObject.getString("cart_item_product_sku");
//                String cart_item_product_image = cartJSONObject.getString("cart_item_product_image");
//                String cart_item_qty = cartJSONObject.getString("cart_item_qty");
//                String cart_item_unit_price = cartJSONObject.getString("cart_item_unit_price");
//                String cart_item_total_price = cartJSONObject.getString("cart_item_total_price");
//                String cart_item_type = cartJSONObject.getString("cart_item_type");
//
//                String cart_item_special_notes = cartJSONObject.getString("cart_item_special_notes");
//
//                //kaki discount
//                try {
//
//                    String cart_item_promotion_discount = cartJSONObject.getString("cart_item_promotion_discount");
//                    productJSONObject.put("product_special_amount", cart_item_promotion_discount);
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                //Modifiers
//
//                JSONArray cartModifierJSONArray = cartJSONObject.getJSONArray("modifiers");
//                JSONArray reviewModifierJSONArray = new JSONArray();
//                //  if (cart_item_type.equals("Modifier")) {
//                try {
//                    if (cartModifierJSONArray != null && cartModifierJSONArray.length() > 0) {
//
//                        for (int j = 0; j < cartModifierJSONArray.length(); j++) {
//
//                            JSONObject cartModifierJSONObject = cartModifierJSONArray.getJSONObject(j);
//                            JSONObject modifierJSONObject = new JSONObject();
//
//
//                            String cart_modifier_id = cartModifierJSONObject.getString("cart_modifier_id");
//                            String cart_modifier_name = cartModifierJSONObject.getString("cart_modifier_name");
//
//                            JSONArray cartModifierValuesJSONArray = cartModifierJSONObject.getJSONArray("modifiers_values");
//                            JSONArray reviewModifierValuesJSONArray = new JSONArray();
//
//                            for (int k = 0; k < cartModifierValuesJSONArray.length(); k++) {
//
//                                JSONObject cartModifierSingleValueJObject = cartModifierValuesJSONArray.getJSONObject(k);
//                                JSONObject modifierSingleValueJObject = new JSONObject();
//
//                                String cart_modifiervalue_id = cartModifierSingleValueJObject.getString("cart_modifier_id");
//                                String cart_modifiervalue_name = cartModifierSingleValueJObject.getString("cart_modifier_name");
//                                String cart_modifiervalue_qty = cartModifierSingleValueJObject.getString("cart_modifier_qty");
//                                String cart_modifiervalue_price = cartModifierSingleValueJObject.getString("cart_modifier_price");
//
//                                modifierSingleValueJObject.put("modifier_value_id", cart_modifiervalue_id);
//                                modifierSingleValueJObject.put("modifier_value_name", cart_modifiervalue_name);
//                                modifierSingleValueJObject.put("modifier_value_qty", cart_modifiervalue_qty);
//                                modifierSingleValueJObject.put("modifier_value_price", cart_modifiervalue_price);
//
//                                reviewModifierValuesJSONArray.put(modifierSingleValueJObject);
//
//                            }
//
//                            modifierJSONObject.put("modifier_id", cart_modifier_id);
//                            modifierJSONObject.put("modifier_name", cart_modifier_name);
//                            modifierJSONObject.put("modifiers_values", reviewModifierValuesJSONArray);
//
//                            reviewModifierJSONArray.put(modifierJSONObject);
//                        }
//                    }
//                } catch (Exception e) {
//
//                    e.printStackTrace();
//                }
//                //modifiers end
//
//                //Submenu Component
//                JSONArray cartSubMenuCompJSONArray = cartJSONObject.getJSONArray("set_menu_component");
//                JSONArray reviewSubMenuCompJSONArray = new JSONArray();
//
//
//                for (int j = 0; j < cartSubMenuCompJSONArray.length(); j++) {
//
//
//                    JSONObject cartMenuComponentJSONObj = cartSubMenuCompJSONArray.getJSONObject(j);
//                    JSONObject reviewMenuComponentJSONObj = new JSONObject();
//
//                    String menu_component_id = cartMenuComponentJSONObj.getString("menu_component_id");
//                    String menu_component_name = cartMenuComponentJSONObj.getString("menu_component_name");
//
//
//                    JSONArray product_detailsJSONArray = cartMenuComponentJSONObj.getJSONArray("product_details");
//                    JSONArray reviewProduct_detailsJSONArray = new JSONArray();
//
//                    for (int k = 0; k < product_detailsJSONArray.length(); k++) {
//
//                        JSONObject subProductJSONObject = product_detailsJSONArray.getJSONObject(k);
//                        JSONObject reviewSubProductJSONObject = new JSONObject();
//
//                        String product_id = subProductJSONObject.getString("cart_menu_component_product_id");
//                        String product_name = subProductJSONObject.getString("cart_menu_component_product_name");
//                        String product_sku = subProductJSONObject.getString("cart_menu_component_product_sku");
//
//                        String product_qty = subProductJSONObject.getString("cart_menu_component_product_qty");
//                        String product_price = subProductJSONObject.getString("cart_menu_component_product_price");
//
//                        //  JSONArray subModifiersJSONArray =subProductJSONObject.getJSONArray("modifiers");
//
//
//                        //Modifiers
//                        JSONArray subModifiersJSONArray = subProductJSONObject.getJSONArray("modifiers");
//                        JSONArray reviewsubModifierJSONArray = new JSONArray();
//                        for (int l = 0; l < subModifiersJSONArray.length(); l++) {
//
//                            JSONObject cartModifierJSONObject = subModifiersJSONArray.getJSONObject(l);
//                            JSONObject modifierJSONObject = new JSONObject();
//
//
//                            String cart_modifier_id = cartModifierJSONObject.getString("cart_modifier_id");
//                            String cart_modifier_name = cartModifierJSONObject.getString("cart_modifier_name");
//
//                            JSONArray cartModifierValuesJSONArray = cartModifierJSONObject.getJSONArray("modifiers_values");
//                            JSONArray reviewModifierValuesJSONArray = new JSONArray();
//
//                            for (int m = 0; m < cartModifierValuesJSONArray.length(); m++) {
//
//                                JSONObject cartModifierSingleValueJObject = cartModifierValuesJSONArray.getJSONObject(m);
//                                JSONObject modifierSingleValueJObject = new JSONObject();
//                                String cart_modifiervalue_id = cartModifierSingleValueJObject.getString("cart_modifier_id");
//                                String cart_modifiervalue_name = cartModifierSingleValueJObject.getString("cart_modifier_name");
//                                String cart_modifiervalue_qty = cartModifierSingleValueJObject.getString("cart_modifier_qty");
//                                String cart_modifiervalue_price = cartModifierSingleValueJObject.getString("cart_modifier_price");
//                                modifierSingleValueJObject.put("modifier_value_id", cart_modifiervalue_id);
//                                modifierSingleValueJObject.put("modifier_value_name", cart_modifiervalue_name);
//                                modifierSingleValueJObject.put("modifier_value_qty", cart_modifiervalue_qty);
//                                modifierSingleValueJObject.put("modifier_value_price", cart_modifiervalue_price);
//                                reviewModifierValuesJSONArray.put(modifierSingleValueJObject);
//
//                            }
//
//                            modifierJSONObject.put("modifier_id", cart_modifier_id);   //key dount modifier
//                            modifierJSONObject.put("modifier_name", cart_modifier_name);
//                            modifierJSONObject.put("modifiers_values", reviewModifierValuesJSONArray);
//
//                            reviewsubModifierJSONArray.put(modifierJSONObject);
//                        }
//                        //modifiers end
//
//
//                        reviewSubProductJSONObject.put("product_id", product_id);
//                        reviewSubProductJSONObject.put("product_name", product_name);
//                        reviewSubProductJSONObject.put("product_sku", product_sku);
//                        reviewSubProductJSONObject.put("product_qty", product_qty);
//                        reviewSubProductJSONObject.put("product_price", product_price);
//                        reviewSubProductJSONObject.put("modifiers", reviewsubModifierJSONArray);
//                        reviewProduct_detailsJSONArray.put(reviewSubProductJSONObject);
//                    }
//
//                    reviewMenuComponentJSONObj.put("menu_component_id", menu_component_id);
//                    reviewMenuComponentJSONObj.put("menu_component_name", menu_component_name);
//                    reviewMenuComponentJSONObj.put("product_details", reviewProduct_detailsJSONArray);
//                    //menu_component_min_max_appy
//                    if (cartMenuComponentJSONObj.has("cart_menu_component_min_max_appy") &&
//                            cartMenuComponentJSONObj.getString("cart_menu_component_min_max_appy") != null) {
//                        reviewMenuComponentJSONObj.put("menu_component_min_max_appy", cartMenuComponentJSONObj.getString("cart_menu_component_min_max_appy"));
//
//                    }
//
//
//                    reviewSubMenuCompJSONArray.put(reviewMenuComponentJSONObj);
//
//                }
//
//
//                //submenu component end
//
//
//                //Condiments
//
//                JSONArray condimentsJSONArray = cartJSONObject.getJSONArray("condiments");
//                JSONArray reviewCondimentsJSONArray = new JSONArray();
//
//                for (int j = 0; j < condimentsJSONArray.length(); j++) {
//
//                    JSONObject condimentJSONObject = condimentsJSONArray.getJSONObject(j);
//                    JSONObject newCondimentJSONObject = new JSONObject();
//
//
//                    String product_id = condimentJSONObject.getString("product_id");
//                    String product_name = condimentJSONObject.getString("product_name").replace("\\", "");
//                    String product_qty = condimentJSONObject.getString("product_qty");
//                    String product_sku = condimentJSONObject.getString("product_sku");
//
//                    Integer productQuantity = Integer.parseInt(product_qty);
//                    Integer cartItemQuantity = Integer.parseInt(cart_item_qty);
//
//                    newCondimentJSONObject.put("product_id", product_id);
//                    newCondimentJSONObject.put("product_name", product_name);
//                    newCondimentJSONObject.put("product_qty", productQuantity * cartItemQuantity);
//                    newCondimentJSONObject.put("product_sku", product_sku);
//                    newCondimentJSONObject.put("product_price", 0.00);
//
//                    reviewCondimentsJSONArray.put(newCondimentJSONObject);
//
//                }
//                //condiments end
//
//
//                productJSONObject.put("product_id", cart_item_product_id);
//                productJSONObject.put("product_name", cart_item_product_name);
//                productJSONObject.put("product_image", cart_item_product_image);
//                productJSONObject.put("product_sku", cart_item_product_sku);
//                productJSONObject.put("product_qty", cart_item_qty);
//                productJSONObject.put("product_unit_price", cart_item_unit_price);
//                productJSONObject.put("product_total_amount", cart_item_total_price);
//                productJSONObject.put("modifiers", reviewModifierJSONArray);
//                productJSONObject.put("menu_set_components", reviewSubMenuCompJSONArray);
//                productJSONObject.put("condiments", reviewCondimentsJSONArray);
//
//                productJSONObject.put("product_special_notes", cart_item_special_notes);
//
//
//                submitOrderJSONArray.put(productJSONObject);
//            }
//
//        } catch (Exception e) {
//
//            e.printStackTrace();
//        }
//
//        return submitOrderJSONArray;
//
//    }
//*/
//
//   /* private class CashOnDeliveryValidateOrderTask extends AsyncTask<String, Void, String> {
//
//        private ProgressDialog progressDialog;
//        private Map<String, String> orderParams;
//
//        public CashOnDeliveryValidateOrderTask(Map<String, String> orderParams) {
//            this.orderParams = orderParams;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            Log.v("cash on delivery", params[0] + "\n" + orderParams.toString());
//
//            String response = WebserviceAssessor.postRequest(context, params[0], orderParams);
//
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//
//                Log.v("cash delivery response", s);
//                try {
//
//                    JSONObject jsonObject = new JSONObject(s);
//
//                    // JSONObject jsonCommon = jsonObject.getJSONObject("common");
//
//                    if (mCarttotal > 0) {
//                        if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
////                            Intent intent = new Intent(mContext, PaymentActivity.class);
////                            intent.putExtra("sub_total", jsonCartObject.getJSONObject("cart_details").getString("cart_sub_total"));
////                            intent.putExtra("total_price", String.valueOf(txtTotal.getText().toString().replace("$", "")));
////                            intent.putExtra("unit_no1", edtUnitNo1.getText().toString());
////                            intent.putExtra("unit_no2", edtUnitNo2.getText().toString());
////                            intent.putExtra("billing_address", edtBillingAddress.getText().toString());
////                            intent.putExtra("billing_pincode", edtPincode.getText().toString());
////                            intent.putExtra("billing_unit_no1", edtBillingUnitNo1.getText().toString());
////                            intent.putExtra("billing_unit_no2", edtBillingUnitNo2.getText().toString());
////                            intent.putExtra("order_remarks", edtComments.getText().toString());
////                            intent.putExtra("REDEEM_APPLIED", r_applied);
////                            intent.putExtra("REDEEM_POINT", r_point);
////                            intent.putExtra("redeem_amount", r_amount);
////                            intent.putExtra("promo_code", mPromoCoupon);
////                            intent.putExtra("promo_amount", mPromotionAmount);
//
//                            //startActivity(intent);
//
//                        } else {
//
//                            Toast.makeText(context, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
//                        }
//                    } else {
//
//                        JSONObject jsonCommon = jsonObject.getJSONObject("common");
//
//                        String mOrderNo = jsonCommon.getString("local_order_no");
//
//                        String url = GlobalUrl.DESTROY_CART_URL;
//                        Map<String, String> params = new HashMap<>();
//                        params.put("app_id", GlobalValues.APP_ID);
//                        params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
//
////                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
//
//                        if (Utility.networkCheck(context)) {
//
//                            //fromChangeAddress = 0;
//                            new DestroyCartTask(params).execute(url);
//                        } else {
//                            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//                        }
//                    }
//
//                    //    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
//
//                } catch (Exception e) {
//                    e.printStackTrace();
//                    progressDialog.dismiss();
//                }
//
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                {
//                    progressDialog.dismiss();
//                }
//            }
//        }
//    }*/
//
//
//  /*  private class DestroyCartTask extends AsyncTask<String, Void, String> {
//
//        private ProgressDialog progressDialog;
//        Map<String, String> params1;
//
//        public DestroyCartTask(Map<String, String> params) {
//            this.params1 = params;
//        }
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            Log.v("card list service", params[0]);
//
//            String response = WebserviceAssessor.postRequest(context, params[0], params1);
//
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//
//                Log.v("card list response", s);
//
//                JSONObject jsonObject = new JSONObject(s);
//
//                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
//
//
//                    Toast.makeText(context, "Cart Emptied", Toast.LENGTH_SHORT).show();
//                    cartArrayList.clear();
//                    cartItemAdapter.notifyDataSetChanged();
//                }
//            } catch (JSONException e1) {
//                e1.printStackTrace();
//            }
//
//            progressDialog.dismiss();
//        }
//    }
//
//*/
//   /* @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//
//        getMenuInflater().inflate(R.menu.menu_home, menu);
//
//        return super.onCreateOptionsMenu(menu);
//    }
//
//
//    private class CartListTask extends AsyncTask<String, Void, String> {
//
//        private ProgressDialog progressDialog;
//        Map<String, String> params;
//
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            progressDialog = new ProgressDialog(context);
//            progressDialog.setMessage("Loading...");
//            progressDialog.setCancelable(false);
//            progressDialog.setCanceledOnTouchOutside(false);
//            progressDialog.show();
//        }
//
//        @Override
//        protected String doInBackground(String... params) {
//
//            Log.v("card list service", params[0]);
//
//            String response = WebserviceAssessor.getData(params[0]);
//
//            return response;
//        }
//
//        @Override
//        protected void onPostExecute(String s) {
//            super.onPostExecute(s);
//
//            try {
//
//                Log.v("card list response", s);
//
//                JSONObject jsonObject = new JSONObject(s);
//
//                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
//
//                    JSONObject jsonResult = jsonObject.getJSONObject("result_set");
//
//                    JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");
//                    JSONArray jsonArray = jsonResult.optJSONArray("cart_items");
//                    Utility.writeToSharedPreference(context, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("result_set").toString());
//
//                    for (int y = 0; y < jsonArray.length(); y++) {
//
//                        JSONObject cartobject = jsonArray.getJSONObject(y);
//                        Cart products = new Cart();
//                        products.setmCartItemId(cartobject.getString("cart_item_id"));
//                        products.setmProductName(cartobject.getString("cart_item_product_name"));
//                        products.setmProductQty(cartobject.getString("cart_item_qty"));
//                        products.setmProductTotalPrice(cartobject.getString("cart_item_total_price"));
//                        cartArrayList.add(products);
//
//
//                    }
//
//
//                    LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                    recyclerview_CartItem.setLayoutManager(linearLayoutManager);
//                    cartItemAdapter = new ChoosedCartItemAdapter(context, cartArrayList);
//                    recyclerview_CartItem.setAdapter(cartItemAdapter);
//                    recyclerview_CartItem.setNestedScrollingEnabled(false);
//
//
//                } else {
//
//
//                }
//
//
////                    }else {
////
////                        Cart products=new Cart();
////                        products.setmCartItemId(cartobject.getString("cart_item_id"));
////                        products.setmProductName(cartobject.getString("cart_item_product_name"));
////                        products.setmProductQty(cartobject.getString("cart_item_qty"));
////                        products.setmProductTotalPrice(cartobject.getString("cart_item_total_price"));
//////                        cartlist.add(products);
//
//
//                LinearLayoutManager linearLayoutManager = new LinearLayoutManager(context);
//                recyclerview_CartItem.setLayoutManager(linearLayoutManager);
//                cartItemAdapter = new ChoosedCartItemAdapter(context, cartArrayList);
//                recyclerview_CartItem.setAdapter(cartItemAdapter);
//                recyclerview_CartItem.setNestedScrollingEnabled(false);
//*/
//
//// Utility.writeToSharedPreference(context, GlobalValues.CART_COUNT, jsoncartDetails.optString("cart_total_items"));
//
//
////                    //txtSubTotal.setText("$" + jsoncartDetails.getString("cart_sub_total"));
////                  //  r_sub_total = jsoncartDetails.getString("cart_sub_total");
////
////                    if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID))
////                    {
////
////                        //setCustomProgress();
////
////                        double d_progress_limit = Double.valueOf(outletZoneJson.getString("zone_free_delivery")) - Double.valueOf(r_sub_total);
////
////                        if (d_progress_limit > 0)
////                        {
////
////                            GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
////
////
////                            /*Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();*/
////
////
////                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
////
////                            if (GlobalValues.PRMOTION_DELIVERY_APPLIED)
////                            {
////
////                                GlobalValues.DELEIVERY_CHARGES = "0.00";
////                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
////
////
////                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
////                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
////
////                                txtDeliveryCharge.setText("$0.00");
////
////                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
////                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
////
////                                if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {
////                                    mGST = (mGrandTotal * 7) / 100;
////                                    GlobalValues.GST=mGST;
////                                    mGrandTotal+=mGST;
////
////
////                                }
////                                else
////                                {
////
////                                    int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
////                                    mGST = (mGrandTotal * gst_values) / 100;
////                                    GlobalValues.GST=mGST;
////                                    mGrandTotal+=mGST;
////
////
////                                }
////
////
////                            } else {
////
////
////
////                                GlobalValues.DELEIVERY_CHARGES = outletZoneJson.getString("zone_delivery_charge");
////                                GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
////
////                                txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
////                                        Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
////
////                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
////
////                                // Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();
////
////
////
////                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
////                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge")) +
////                                        Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
////
////                                if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID))
////                                {
////
////                                    mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
////                                            Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));
////
////
////                                    mGST = (mGrandTotal * 7) / 100;
////                                    GlobalValues.GST=mGST;
////                                    mGrandTotal+=mGST;
////                                    txtGSTLabel.setText("GST ("+"7"+"%)" );
////                                    txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
////
////
////                                }
////                                else
////                                {
////
////                                    int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
////
////                                    mGST = (mGrandTotal * gst_values) / 100;
////                                    GlobalValues.GST=mGST;
////                                    mGrandTotal+=mGST;
////                                    txtGSTLabel.setText("GST ("+"7"+"%)" );
////
////
////                                }
////
////                            }
////
////                            txtFreeDelivery.setText("$" + String.format("%.2f", d_progress_limit) + " more to FREE delivery!");
////
////                            //progressBar.setProgress(Double.valueOf(r_sub_total.trim()).intValue());
////
////
////                        } else {
////
////                            GlobalValues.DELEIVERY_CHARGES = "0.00";
////                            GlobalValues.ADDITIONAL_DELEIVERY_CHARGES = outletZoneJson.getString("zone_additional_delivery_charge");
////
////                            txtAdditionalDeliveryCharge.setText("$" + String.format("%.2f",
////                                    Double.valueOf(outletZoneJson.getString("zone_additional_delivery_charge"))));
////                            txtDeliveryCharge.setText("$0.00");
////                            txtFreeDelivery.setText("FREE delivery!");
////
////                            // Toast.makeText(mContext, ""+outletZoneJson.getString("zone_additional_delivery_charge"), Toast.LENGTH_SHORT).show();
////
////
//////                            progressBar.setProgress(1000);
////
////                            mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
////                                    Double.parseDouble(outletZoneJson.getString("zone_additional_delivery_charge"));
////
////                            if( GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID))
////                            {
////                                mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total")) +
////                                        Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));
////
////
////                                mGST = (mGrandTotal * 7) / 100;
////                                GlobalValues.GST=mGST;
////                                mGrandTotal+=mGST;
////                                txtGSTLabel.setText("GST ("+"7"+"%)" );
////                                txtDeliveryCharge.setText("$" + outletZoneJson.getString("zone_delivery_charge"));
////
////
////
////
////                            }
////                            else
////                            {
////
////                                int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
////
////
////                                mGST = (mGrandTotal * gst_values) / 100;
////                                GlobalValues.GST=mGST;
////                                mGrandTotal+=mGST;
////
////
////                            }
////
////
////                        }
////
//////                                Double.parseDouble(outletZoneJson.getString("zone_delivery_charge"));
////
////                        Log.v("Delivery charge", GlobalValues.COMMON_DELIVERY_CHARGE + "");
////
////                    } else if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
////                        mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));
////
////                        int gst_values = Integer.valueOf( GlobalValues.GstChargers).intValue();
////
////
////                        mGST = (mGrandTotal * gst_values) / 100;
////                        GlobalValues.GST=mGST;
////                        mGrandTotal+=mGST;
////
////                    }
////
////                    txtGST.setText("$" + String.format("%.2f", mGST));
////                    txtTotal.setText("$" + String.format("%.2f", mGrandTotal));
////
////                    InclusiveGst(mGrandTotal);
////
////
////                    JSONArray jsonCartItem = jsonResult.getJSONArray("cart_items");
////
////                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("result_set").toString());
////
////                    Log.v("read from memory", Utility.readFromSharedPreference(mContext, GlobalValues.CART_RESPONSE));
////
////                    setCartAdapter(jsonCartItem);
////
////
////                } else {
////
////                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
////
////                }
//
//
//      /*          invalidateOptionsMenu();
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//                progressDialog.dismiss();
//            }
//        }
//
//
//    }*/
//
//  /*  @Override
//    public boolean onPrepareOptionsMenu(Menu menu) {
//
//
//        try {
//            MenuItem cartWBadge = menu.findItem(R.id.menu_cart);
//            MenuItem menuSearch = menu.findItem(R.id.menu_search);
//
//            menuSearch.setVisible(false);
//
//            View viewBadge = menu.findItem(R.id.menu_cart).getActionView();
//
//            layoutCart = (RelativeLayout) viewBadge.findViewById(R.id.layoutCart);
//            txtCartCount = (TextView) viewBadge.findViewById(R.id.txtCartCount);
//
//            cartCount = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
//
//            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {
//
//
//                // updatecartvalues();
//                txtCartCount.setVisibility(View.VISIBLE);
//                txtCartCount.setText(cartCount);
//
//
//            *//*    if (isInvalidated) {
//                    Tooltip.make((context),
//                            new Tooltip.Builder(101)
//                                    .anchor(layoutCart, Tooltip.Gravity.BOTTOM)
//                                    .closePolicy(new Tooltip.ClosePolicy()
//                                            .insidePolicy(true, false)
//                                            .outsidePolicy(true, false), 300000)
//                                    .activateDelay(100)
//                                    .showDelay(200)
//                                    .text("great! product added to the cart")
//                                    .withArrow(true)
//                                    .withOverlay(false)
//                                    .typeface(BebasNeueBoldTextView.BebasNeueBoldText)
//                                    .build()
//                    ).show();
//                }*//*
//
//            } else {
//
//                txtCartCount.setVisibility(View.GONE);
//            }
//
//
//            cartWBadge.setVisible(true);
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        return super.onPrepareOptionsMenu(menu);
//    }
//}*/

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
                pos = 10;
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
//                    new FragmentReward.PriceForPoints().execute(GlobalUrl.PRICE_FOR_POINTS_URL + "?app_id=" +
//                            GlobalValues.APP_ID + "&loyality_points=" + GlobalValues.eWalletAmount.replace("$",""));
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

    public void zeroTotal() {


        double minAmount = 0.0;
        Log.v("fb fooo", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));

        try {
            outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
            minAmount = Double.parseDouble(outletZoneJson.getString("zone_min_amount"));

            r_sub_total = txtSubTotal.getText().toString().replace("$", "");
        } catch (Exception e) {
            minAmount = 0.0;
        }

        if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

            Log.v("pohne fooo", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).toString().isEmpty()) {


                final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

                alertDialog.setMessage("Please update phone number. ");
                alertDialog.setTitle("Message");

                // Setting OK Button
                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        openFiveMenuActivity(0);
                    }
                });

                // Showing Alert Message
                alertDialog.show();

            } else {


                if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {


                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                        cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);

                        String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.MinimumQuality);

                        if (Integer.parseInt(MinQual) <= Integer.parseInt(cartCount)) {


                            if (edtUnitNo1.getText().toString().isEmpty() && edtUnitNo2.getText().toString().isEmpty()) {

                                // True

                                Toast.makeText(mContext, "Please enter your unit number.", Toast.LENGTH_SHORT).show();


                            } else {
                                //fasle

                                if (minAmount <= Double.valueOf(r_sub_total)) {

                                    placeCashOnDeliveryOrder();
                                } else if (Double.valueOf(r_sub_total) == 0.0) {

                                    placeCashOnDeliveryOrder();
                                } else {
                                    Toast.makeText(mContext, " You must order minimum of " + minAmount +
                                            "to place your order,your current order total is" + r_sub_total, Toast.LENGTH_SHORT).show();
                                }
                            }


                        } else {

                            // openDialogbox();

                        }

                    } else {


                        if (edtUnitNo1.getText().toString().isEmpty() && edtUnitNo2.getText().toString().isEmpty()) {

                            // True

                            Toast.makeText(mContext, "Please enter your unit number.", Toast.LENGTH_SHORT).show();


                        } else {
                            //fasle

                            if (minAmount <= Double.valueOf(r_sub_total)) {

                                placeCashOnDeliveryOrder();
                            } else if (Double.valueOf(r_sub_total) == 0.0) {

                                placeCashOnDeliveryOrder();
                            } else {
                                Toast.makeText(mContext, " You must order minimum of " + minAmount +
                                        "to place your order,your current order total is" + r_sub_total, Toast.LENGTH_SHORT).show();
                            }
                        }


                    }

                } else {


                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).toString().isEmpty()) {

                            final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

                            alertDialog.setMessage("Please update phone number. ");
                            alertDialog.setTitle("Message");

                            // Setting OK Button
                            alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                    openFiveMenuActivity(0);
                                }
                            });

                            // Showing Alert Message
                            alertDialog.show();

                        } else {

                            placeCashOnDeliveryOrder();

                        }
                    }
                }


            }


        } else {

            Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
        }
    }

    private void validateAndPlaceOrder() {

        if (!Utility.isCustomerLoggedIn(mContext)) {

            intent = new Intent(mContext, LoginActivity.class);
            startActivity(intent);

        } else {

/*if(paymentType.equalsIgnoreCase("")){
  if(mGrandTotal <= 0){

  }else{
      if(!(walletBalance.equalsIgnoreCase("null")  ||  walletBalance.equalsIgnoreCase(""))){
          if(Double.parseDouble(walletBalance) > 0){
              Toast.makeText(mContext, "Please Select Payment Type", Toast.LENGTH_SHORT).show();
              return;
          }
      }else{
         paymentType = "CARD";
      }

  }
}*/

            double minAmount = 0.0;
            Log.v("fb fooo", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));

            try {
                outletZoneJson = new JSONObject(Utility.readFromSharedPreference(mContext, GlobalValues.OUTLETZONE));
                minAmount = Double.parseDouble(outletZoneJson.getString("zone_min_amount"));

                r_sub_total = txtSubTotal.getText().toString().replace("$", "");
            } catch (Exception e) {
                minAmount = 0.0;
            }

            if (checkTime(GlobalValues.DELIVERY_DATE, GlobalValues.DELIVERY_TIME)) {

                Log.v("pohne fooo", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE));

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).toString().isEmpty()) {


                    final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

                    alertDialog.setMessage("Please update phone number. ");
                    alertDialog.setTitle("Message");

                    // Setting OK Button
                    alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            openFiveMenuActivity(0);
                        }
                    });

                    // Showing Alert Message
                    alertDialog.show();

                } else {


                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID) || CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {


                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.BENTO_ID)) {

                            cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);

                            String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.MinimumQuality);

                            if (Integer.parseInt(MinQual) <= Integer.parseInt(cartCount)) {



                                if (edtUnitNo1.getText().toString().isEmpty() && edtUnitNo2.getText().toString().isEmpty()) {

                                    // True

                                    Toast.makeText(mContext, "Please enter your unit number.", Toast.LENGTH_SHORT).show();


                                } else {
                                    //fasle

                                    if (minAmount <= Double.valueOf(r_sub_total)) {

                                        placeCashOnDeliveryOrder();
                                    } else if (Double.valueOf(r_sub_total) == 0.0) {

                                        placeCashOnDeliveryOrder();
                                    } else {
                                        Toast.makeText(mContext, " You must order minimum of " + minAmount +
                                                "to place your order,your current order total is" + r_sub_total, Toast.LENGTH_SHORT).show();
                                    }
                                }


                            } else {

                                // openDialogbox();

                            }

                        } else {


                            if (edtUnitNo1.getText().toString().isEmpty() && edtUnitNo2.getText().toString().isEmpty()) {

                                // True

                                Toast.makeText(mContext, "Please enter your unit number.", Toast.LENGTH_SHORT).show();


                            } else {
                                //fasle

                                if (minAmount <= Double.valueOf(r_sub_total)) {

                                    placeCashOnDeliveryOrder();
                                } else if (Double.valueOf(r_sub_total) == 0.0) {

                                    placeCashOnDeliveryOrder();
                                } else {
                                    Toast.makeText(mContext, " You must order minimum of " + minAmount +
                                            "to place your order,your current order total is" + r_sub_total, Toast.LENGTH_SHORT).show();
                                }
                            }


                        }

                    } else {


                        if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {

                            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE).toString().isEmpty()) {

                                final AlertDialog.Builder alertDialog = new android.app.AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

                                alertDialog.setMessage("Please update phone number. ");
                                alertDialog.setTitle("Message");

                                // Setting OK Button
                                alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                        openFiveMenuActivity(0);
                                    }
                                });

                                // Showing Alert Message
                                alertDialog.show();

                            } else {

                                placeCashOnDeliveryOrder();

                            }
                        }
                    }


                }


            } else {

                Toast.makeText(mContext, "Please select valid date", Toast.LENGTH_SHORT).show();
            }
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
            Log.e("TAG", "NETS-Registration : " + params[0] + "\n" + cardDetails.toString());
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
                                Utility.writeToSharedPreference(mContext,GlobalValues.NETS_CARD_NUMBER, txn_data.optString("last_4_digits_fpan"));
                                Utility.writeToSharedPreference(mContext, GlobalValues.NETS_EXPIRY, txn_data.optString("mtoken_expiry_date"));
                                Utility.writeToSharedPreference(mContext, GlobalValues.CARD_TYPE, txn_data.optString("issuer_short_name")); //bank_fiid
                                Utility.writeToSharedPreference(mContext, GlobalValues.NETS_REGISTERED, "1");
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

    @SuppressLint("SetTextI18n")
    private void updateUI( ) {

        Log.e("TAG","RegisterpopupTest::"+isRegistrationSuccess());
        if(isRegistrationSuccess()) {
            nets_pay_lyt_deregister.setVisibility(View.VISIBLE);
            nets_pay_lyt_register.setVisibility(View.GONE);
            String card_num = getCardType(Utility.readFromSharedPreference(mContext, GlobalValues.CARD_TYPE)) + "(•••• " + Utility.readFromSharedPreference(mContext, GlobalValues.NETS_CARD_NUMBER) + ")";
            exp_date.setText("Valid until " + formatDate(Utility.readFromSharedPreference(mContext, GlobalValues.NETS_EXPIRY)));
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

    private void clearRegistration () {
        Utility.writeToSharedPreference(mContext,GlobalValues.NETS_CARD_NUMBER, "");
        Utility.writeToSharedPreference(mContext, GlobalValues.NETS_EXPIRY, "");
        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO, ""); //
        Utility.writeToSharedPreference(mContext,GlobalValues.TRANS_REF_NO_REGISTER, ""); //
        Utility.writeToSharedPreference(mContext,GlobalValues.NETS_REGISTERED, "0");
    }

    private class MemberRewards extends AsyncTask<String, Void, String> {

        private Map<String, String> countParams;

        private ProgressDialog progressDialog;

        public MemberRewards(Map<String, String> mapData) {
            countParams = mapData;

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            ;
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("Member_reward_service::", params[0] + "\n" + countParams);

            String response = WebserviceAssessor.postRequest(mContext,params[0],countParams);

            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {

                Log.e("TAG","Member_reward_response::"+ s);

                JSONObject jsonObject = new JSONObject(s);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    JSONObject rewardJson = jsonObject.getJSONObject("result_set");
                    JSONArray CardListArray=rewardJson.getJSONArray("CardLists");
                    for (int i=0;i<CardListArray.length();i++){
                        JSONObject cardlistobj=CardListArray.getJSONObject(i);

                        double x_reedempoints = cardlistobj.optDouble("TotalPointsBAL");
                        redeemPointsValue = String.valueOf(cardlistobj.optDouble("TotalPointsBAL"));
                        GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;

                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        txtRedeemAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + x_reedempoints + "</font> points", Html.FROM_HTML_MODE_LEGACY), TextView.BufferType.SPANNABLE);
                    } else {
                        txtRedeemAvailablePoints.setText(Html.fromHtml("You have <font color='#015B26'>" + x_reedempoints + "</font> points"), TextView.BufferType.SPANNABLE);
                    }
                        txtRedeemAvailablePoints_new.setText("ST$ "+String.format("%.2f",x_reedempoints));
                    }
                }else {
                    redeemPointsValue ="0";
                    GlobalValues.CUSTOMER_REWARD_POINT = 0.0;
                }
               /* if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject countJson = jsonObject.getJSONObject("result_set");
                    GlobalValues.ORDERCOUNT = countJson.getString("order");
                    GlobalValues.NOTIFYCOUNT = countJson.getString("notify");
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");

                    double x_reedempoints = countJson.optDouble("reward_ponits");

                    GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;

                    txtRewardPoint.setText("" + String.valueOf(x_reedempoints) + "  Points");

                    if (!countJson.isNull("reward_ponits_monthly")) {

                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = Double.valueOf(countJson.optString("reward_ponits_monthly"));

                    } else {
                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = 0.00;
                    }

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

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.VISIBLE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }

                } else {

                }*/
                progressDialog.dismiss();

            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }
}

class DecimalDigitsInputFilter implements InputFilter {
    private Pattern mPattern;

    DecimalDigitsInputFilter(int digitsBeforeZero, int digitsAfterZero) {
        mPattern = Pattern.compile("[0-9]{0," + (digitsBeforeZero - 1) + "}+((\\.[0-9]{0," + (digitsAfterZero - 1) + "})?)||(\\.)?");
    }

    @Override
    public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
        Matcher matcher = mPattern.matcher(dest);
        if (!matcher.matches())
            return "";
        return null;
    }


}