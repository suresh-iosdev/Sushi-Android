package com.app.sushi.tei.GlobalMembers;


import com.app.sushi.tei.Model.Account.AccountDetail;
import com.app.sushi.tei.Model.ProductList.SelectedModifierHeading;
import com.app.sushi.tei.Model.ProductList.SelectedModifierValue;

import java.util.ArrayList;

/**
 * Created by surya on 03/08/20.
 */

public class GlobalValues {

    //public static final String APP_ID = "4B4297A4-04ED-403F-BE56-1B106081D8E7";
    //public static final String APP_ID = "D9B5CFCB-4CC0-4655-A494-6B85D3DF7B7E";
    public static String ACCESS_TOKEN_KEY="ACCESS_TOKEN_KEY";
    public static String ACCESS_TOKEN="";
    public static final String APP_ID = "48F27C1E-A55A-4DCE-89EC-674F1DA5C960";//Sushi tei
    public static final String DELIVERY_ID = "634E6FA8-8DAF-4046-A494-FFC1FCF8BD11";
    public static final String DELIVERY = "634E6FA8-8DAF-4046-A494-FFC1FCF8BD11";
    public static final String TAKEAWAY_ID = "718B1A92-5EBB-4F25-B24D-3067606F67F0";
    public static final String BENTO_ID = "7B30BB03-14BD-47E4-B9B1-9731F9A3BC9C";
    public static final String RESERVATION_ID = "";
    public static final String PAYMENT_MODE = "test";
    public static final String PAYMENT_MODE_LIVE = "live";
    public static final String COD_START_TIME = "COD_START_TIME";
    public static final String COD_END_TIME = "COD_END_TIME";
    public static final String IS_REFERRAL_ENABLE = "IS_REFERRAL_ENABLE";
    public static String ENVIR="test";

    public static final String GSTIN ="GSTIN";

    public static final String CLIENT_ENABLE_MEMBERSHIP = "CLIENT_ENABLE_MEMBERSHIP";

    public static final int CUSTOM_VERSION_CODE = 1; //29-12-2020

    public static int INERVAL_TIME = 0;
    public static int TIME_SLOT_TYPE = 0;
    public static String SUBSTRATED_TIME = "";
    public static int time_slot_type =0;

    //Bento Minimum Quality

    public static final String MinimumQuality="minimumqut";

    public static final String Minimumavailable="minimumavailable";



    /// Minimum

    public static final String GstCharger = "gstcharges";
    public  static  final Double GstAmount = 0.0;

    public static final String SP_NAME = "SPIZE";
    public static final String FROM_NOTIFICATION_POSITION = "FROM_NOTIFICATION_POSITION";
    public static final String OUTLET_ID="OUTLET_ID";
    public static final String OUTLET_NAME="OUTLET_Name";
    public static final String CATEGORY_NAME="CATEGORY_NAME";
    public static final String CATEGORY_DESCRIPTION="CATEGORY_DESCRIPTION";
    public static final String CATEGORY_LIST="CATEGORY_LIST";
    public static final String MPOTISION="MPOTISION";
    public static final String SELECTEDPOSITION = "position";
    public static final String ZONE_ID="ZONE_ID";
    public static String CUT_OFF="CUT_OFF";
    public static final String AVALABILITY_ID="AVALABILITY_ID";
//    public static final String DELIVERY = "DELIVERY";
    public static final String RESERVATION = "RESERVATION";
    public static final String TAKEAWAY = "TAKEAWAY";
    public static final String CATERING = "CATERING";
    public static final String BENTO = "BENTO";
    public static final String FIRSTNAME = "FIRSTNAME";
    public static final String LASTNAME = "LASTNAME";
    public static final String EMAIL = "EMAIL";
    public static final String CUSTOMERPHONE = "CUSTOMERPHONE";
    public static final String MEMBERSHIP_ID = "MEMBERSHIP_ID";
    public static final String ASCENTIS_CARD_NO = "ASCENTIS_CARD_NO";
    public static final String CUSTOMERID = "CUSTOMERID";
    public static final String ORDER_DELIVERY_DATE = "ORDER_DELIVERY_DATE";
    public static final String ORDER_DELIVERY_TIME = "ORDER_DELIVERY_TIME";
    public static final String POSTALCODE = "POSTALCODE";
    public static final String CURRENT_OUTLET_ADDRESS = "OUTLET_ADDRESS";
    public static final String OUTLET_PINCODE = "OUTLET_PINCODE";
    public static final String OUTLET_UNITNO1 = "OUTLET_UNITNO1";
    public static final String OUTLET_UNITNO2 = "OUTLET_UNITNO2";
    public static final String CART_COUNT = "CARTCOUNT";
    public static final String OUTLETZONE = "OUTLETZONE";
    public static final String CART_RESPONSE = "CART_RESPONSE";
    public static final String PaymentSuccess="PaymentSuccess";
    public static String MEMBER_CUSTOMER_ID="";
    public static final String FROM_KEY = "FROMKEY";
    public static final String TO_KEY="TOKEY";
    public static final String TOTAL_CART_PRICE="CART_TOTAL_PRICE";
    public static final int REQUEST_WRITE_PERMISSION = 250;
    public static final int PROMO_REQUEST_CODE = 7;
    public static final int REWARDS_REQUEST_CODE=8;
    public static final int FROM_CHECKOUT = 1;
    public static final int FROM_RESET = 1;
    public static final int TO_CHECKOUT=2;
    public static final int NOT_FROM_RESET = 2;
    public static final int NOT_FROM_CHECKOUT = 2;
    public final  static String TYPE="mobile";
    public static final String TAT_TIME = "TAT_TIME";
    public static final String FB_LOGIN = "FB_LOGIN";
    public static final String CUSTOMER_INFO = "CUSTOMER_INFO";
    public static final String NOTIFICATION_STATUS = "NOTIFICATION_STATUS";
    public static final String EMAIL_STATUS = "EMAIL_STATUS";
    public static final String IMG_HEART = "IMG_HEART";

    public static final String OUTLET_DELIVERY_TAT = "OUTLET_DELIVERY_TAT";
    public static final String OUTLET_PICKUP_TAT = "OUTLET_PICKUP_TAT";
    public static final String OUTLET_DELIVERY_TIMING = "OUTLET_DELIVERY_TIMING";
    public static final String CATEGORY_SELECTED = "CATEGORY_SELECTED";
    public static final String PRODUCT_SELECTED = "CATEGORY_SELECTED";
    public static final String CATEGORY_SELECTED_NAME = "CATEGORY_SELECTED_NAME";

    public static boolean isFoodVoucher = false;

    public static  boolean DELIVERYCHARGEDISCOUNT = false;
    public static String CURRENT_AVAILABLITY_ID = "";
    public static String CURRENT_AVAILABLITY_NAME = "";
    public static String CURRENT_OUTLET_ID = "";
    public static String DELIVERY_DATE = "";
    public static String DELIVERY_TIME = "";
    public static String DEVICE_ID = "";
    public static String IS_ADVANCE_ORDER = "no";

    public static String ZoneID="";

    public static String GstChargers="";


    //home badge count
    public static String NOTIFYCOUNT= "";
    public static String ORDERCOUNT = "";
    public static String PROMOTIONCOUNT= "";
    public static String VOUCHERCOUNT = "";
    public static String REWARDSCOUNT = "";
    public static String PAYMENT_TYPE = "";
    public static String CateringCount = "";
    public static String ReservationCount = "";
    public static String CUSTOMER_REWARD_POINT_NEW="";

    public static AccountDetail ACCOUNT_DETAIL;
    public static Double ADDITIONAL_DELIVERY_CHARGE=0.0;
    public static Double CUSTOMER_REWARD_POINT=0.0;
    public static String is_card_expired="";
    public static Double CUSTOMER_REWARD_POINT_UPDATE=0.0;

    public static Double CUSTOMER_MONTHLY_REWARD_POINT=0.0;
    public static String REWARDS_STATUS;
    public static Double COMMON_DELIVERY_CHARGE=0.0;
    public static String DISCOUNT_CODE="";
    public static boolean DISCOUNT_APPLIED=false;
    public static String DISCOUNT_TYPE="";
    public static String CURRENT_TAT_TIME="";

    public static String DELEIVERY_CHARGES= "";
    public static String ADDITIONAL_DELEIVERY_CHARGES = "";
    public static boolean PRMOTION_DELIVERY_APPLIED=false;

    public static String BILLING_ADDRESS="";
    public static String BILLING_PINCODE="";
    public static String BILLING_UNITNO1="";
    public static String BILLING_UNITNO2="";
    public static boolean BILLING_CHECKED= true;
    public static String Unit_no_1 ="";
    public static String Unit_no_2="";

    public static double GST=0.0;


    // Success screen iShare Values :
    public static final String SU_ORDER_COMMENTS = "mOrderComments";
    public static final String SU_ORDER_SUBTOTAL = "sub_total";
    public static final String SU_ORDER_GRANDTOTAL = "grand_total";
    public static final String SU_ORDER_UNITNO_ONE = "mUnitNo1";
    public static final String SU_ORDER_UNITNO_TWO = "mUnitNo2";
    public static final String SU_ORDER_RAPPLIED = "r_applied";
    public static final String SU_ORDER_RPOINT = "r_point";
    public static final String SU_ORDER_RAMOUNT = "r_amount";
    public static final String SU_ORDER_PCODE = "p_code";
    public static final String SU_ORDER_PAMOUNT = "p_amount";
    public static final String SU_ORDER_BILLADRESS = "billing_address";
    public static final String SU_ORDER_BILLPINCODE = "billing_pincode";
    public static final String SU_ORDER_BILUNITNO1 = "billing_unit_no1";
    public static final String SU_ORDER_BILLUNITNO2 = "billing_unit_no2";

    public static final String PRODUCT_LEAD_TIME ="product_leadtime";



    /// Bento Cart mainatances

    public  static final String BENTO_CART_COUNT = "BentoCart";



 public static String FavouriteCheck="1";

 public static final String EDITCOUNT = "EditCount";

 public static String SETMENUMODIFIREVALUE = "setmenuValue";

 public static ArrayList<String> SELECTEDMODIFIRELIST = new ArrayList<>();
 public static ArrayList<String> SELECTEDMODIFIRESUBLIST = new ArrayList<>();
 public static ArrayList<SelectedModifierValue> SELECTEDMODIFIERVALUES = new ArrayList<>();
 public static ArrayList<SelectedModifierHeading> SELECTEDMODIFIERTITLES = new ArrayList<>();

 public static String SELECTEDMODIFIRE = "slectedModifire";
 public static String SELECTEDMODIFIRESUB = "slectedModifireSub";

 public static String MAXIMUMSELECTED = "MaximumSelected";

 public static String MULTIPLESLECTIONAPPLY = "multipleselection_apply";

 public static String MODIFIERAPPLY = "modifier_apply";

 public static String MODIFIERAPPLYPRICE = "0";

 public static final String PAYMENTKEY="omise_public_key";

    public static final String PAYMENTKEYENABLE="PAYMENTKEYENABLE";

 public static final String PACKING_CHARGE="PACKING_CHARGE";
    public static String PACKING_CHARGE_SHOW="";

 public static final String OPENLOGIN="openLogin";


 public static int setMenuTitleSelectCount = 0;

 public static int modifierSelectCount = 0;

 public static String MODIFIER_NAME;

 public static int modifierMinSelect = 0;

 public static int subModifierSelectCount = 0;


 public static String LATITUDE;

 public static String LONGITUDE;

 public static String Availability_id = "";

 public static String eWalletAmount = "";

 public static String promoID = "";

 public static String outletsJson = "";

 public static boolean isPromoAdded = false;

 public static boolean isFirstTimePromo = false;

 public static String firstTimePromoCode = "";

 public static String couponCodeFromFiveMenu = "";

 public static String used_eWalletAmount = "";

 public static boolean isWalletApplied = false;

 public static String paymentType = "";

    public static boolean is_cutlery_checked = false;

    public static String galleryBasePath = "";


    public static final String INITIALIZE = "INITIALIZE";

    public static final String REGISTER = "REGISTER";

    public static final String DE_REGISTER = "DE-REGISTER";

    public static final String PURCHASE = "PURCHASE";

    public static final String PURCHASE_WITH_PIN = "PURCHASE_WITH_PIN";

    public static final String NETS_REGISTERED = "NETS_REGISTERED";

    public static final String NETS_REGISTERED_NEW = "NETS_REGISTERED_NEW";

    public static final String NETS_CARD_NUMBER = "NETS_CARD_NUMBER";

    public static final String NETS_CARD_NUMBER_NEW = "NETS_CARD_NUMBER_NEW";

    public static final String NETS_EXPIRY = "NETS_EXPIRY";

    public static final String NETS_EXPIRY_NEW = "NETS_EXPIRY_NEW";

    public static final String TRANS_REF_NO = "TRANS_REF_NO";

    public static final String TRANS_REF_NO_REGISTER = "TRANS_REF_NO_REGISTER";

    public static final String CARD_TYPE = "CARD_TYPE";

    public static final String CARD_TYPE_NEW = "CARD_TYPE_NEW";

}