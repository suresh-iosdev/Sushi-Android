package com.app.sushi.tei.GlobalMembers;

/**
 * Created by root on 26/3/18.
 */

public class GlobalUrl {

   /*https://venus.ninjaos.com/apiV4/guest-checkout/customer/send_customer_otp

   app_id:9962A11D-3F0D-4AB3-BB3A-4BFFF7E33E2A
   customer_phone:97775663
   verify_type:register

   https://venus.ninjaos.com/apiV4/guest-checkout/customer/customer_otp_verification

   app_id:9962A11D-3F0D-4AB3-BB3A-4BFFF7E33E2A
   customer_otp_val:946780
   customer_phone:87654343
   verify_type:register*/

//    public static String BASE_URL = "https://venus.ninjaos.com/";  //LIVE old sushi_tei

 //   public static String BASE_URL = "https://ccpl.ninjaos.com/";  //Dev Sushi tei

     public static String BASE_URL ="https://ceres.ninjaos.com/";   // 31_05_2022


//    public static String BASE_URL = "https://neptune.ninjaos.com/dev_venus/"; //DEV old sushi_tei

    //public static String BASE_URL = "https://venus.ninjaos.com/staging/";


    //public static final String BASE_URL_OLD="https://ccpl.ninjaos.com/";

    /*  app_id:9962A11D-3F0D-4AB3-BB3A-4BFFF7E33E2A
      customer_phone:97775663
      verify_type:register
   */
    public static String SEND_OTP = BASE_URL + "apiV4/guest-checkout/customer/send_customer_otp";


 /* app_id:9962A11D-3F0D-4AB3-BB3A-4BFFF7E33E2A
   customer_otp_val:946780
   customer_phone:87654343
   verify_type:register*/

    public static String OTP_VERIFICATION = BASE_URL + "apiV4/guest-checkout/customer/customer_otp_verification";


    public static String RESEND_OTP_URL = BASE_URL + "apiV4/guest-checkout/customer/resend_otp";

    public static String BANNER = "api/v1/cms/banner";
    /*Settings service
     method : get
     * params: app_id*/
    public static String SETTINGS_URL = BASE_URL + "apiv2/settings/getCommonSettings";

    /*Registration service
    method : post
    * Params:
    *       customer_first_name,customer_last_name, customer_email,customer_password,customer_phone,app_id,type(App),site_url
    * */
    public static String REGISTRATION_URL = BASE_URL + "api/customer/registration";

    public static String MEMBER_REGISTRATION_URL = BASE_URL + "api/ascentiscrm/memberRegister";
    public static String MEMBER_MOBILE_VALIDATE_URL = BASE_URL + "api/ascentiscrm/memberSendOTP";
    public static String MEMBER_FORGOTPASS_URL = BASE_URL + "api/ascentiscrm/membershipPasswordUpdate";
    public static String MEMBER_EMAIL_EXIST_URL = BASE_URL + "api/customer/email_exist";
    public static String MEMBER_MOBILE_EXIST_URL = BASE_URL + "api/customer/phoneno_exist";
    public static String MEMBER_LOGIN_URL = BASE_URL + "api/ascentiscrm/memberLogin";
    public static String MEMBER_ENQUIRY_URL = BASE_URL + "api/ascentiscrm/memberEnquiry";
    public static String MEMBER_HISTORY_URL = BASE_URL + "api/ascentiscrm/membertranshistory";
    public static String CYBER_SOURCE_FORM= BASE_URL + "api/cybersource/createCybersourceForm";



    /*Login service
    * method: Post
    * Params:
    *   app_id
        login_username
        login_password
        type=>App
    * */

    public static String LOGIN_URL = BASE_URL + "api/customer/login";


    /*Facebook login
        api/customer/fbloginapp
    * method : post
    * Params :app_id, customer_fb_id, login_username, type, login_firstname, login_lastname
    * */
    public static String FBLOGIN_URL = BASE_URL + "api/customer/fbloginapp";

    public static String LANDING_URL = BASE_URL + "api/landingpage/pages";

    /*Banner service
    api/cms/banner
    method: get
    params : app_id, availability*/
    public static String BANNER_URL = BASE_URL + "api/cms/banner";

    /*Category service
        apiv2/products/getMenuNavigation
        params:
        Method : GET
        Params: app_id,availability
        */
    public static String CATEGORY_URL = BASE_URL + "apiv2/products/getMenuNavigation";
    public static String HOT_MENU_PRODUCTS_URL = BASE_URL + "api/products/new_products_list";
//    https://ccpl.ninjaos.com/api/products/new_products_list?app_id=48F27C1E-A55A-4DCE-89EC-674F1DA5C960&outlet_id=

    /*Get Outlets:
    URL: https://ccpl.ninjaos.com/apiv2/outlets/getAllOutles/
    params: app_id, availability_id,

    */
    public static String OUTLET_URL = BASE_URL + "apiv2/outlets/getAllOutles/";

    public static String DELIVERY_OUTLET = BASE_URL + "apiv2/outlets/getAllOutles";

    public static String DELIVERY_OUTLET_URL = BASE_URL + "apiv2/outlets/findOutletZone";


    /*
    Find Zone:
    URL:    https://ccpl.ninjaos.com/apiv2/outlets/findOutletZone
    params: app_id, availability_id,postal_code,outlet_id*/

    public static String FINDZONE_URL = BASE_URL + "apiv2/outlets/findOutletZone";

    /*Day availability service:
        method : get
        URL: https://ccpl.ninjaos.com/apiv2/settings/checkDayAvailability/
        params: app_id, availability, outlet_id*/

    public static String DAYAVAILABLE_URL = BASE_URL + "apiv2/settings/checkDayAvailability/";
    public static String DAYAVAILABLE_URL2 = BASE_URL + "apiv2/settings/availableDatesAdvanced";

    /*Get All  products:
    MEthod : GET
    URL: https://ccpl.ninjaos.com/apiv2/products/getAllProducts
    Param: availability,app_id,outletId,category_slug,subcate_slug, apply_addon="yes"
    */
    public static String PRODUCT_URL = BASE_URL + "apiv2/products/getAllProducts";


    public static String ORDER_AGAIN_URL = BASE_URL + "api/orderagain";


    /*Get single  product details :
    URL: .https://ccpl.ninjaos.com/apiv2/products/getSingleProductDetails
    Method: GET
    Params : appId,availability,productId,outletId,productType*/

    public static String PRODUCT_DETAILS_URL = BASE_URL + "apiv2/products/getSingleProductDetails";

    /*Add to Cart:
    URL: https://ccpl.ninjaos.com/apiv2/cart/simpleCartInsert
    Method: POST
    Params : product_id,product_qty,app_id,reference_id,
            product_type,customer_id,availability_name,availability_id*/

    public static String ADD_CART_URL = BASE_URL + "apiv2/cart/simpleCartInsert";


    /*Add to cart set menu product*/

    public static String ADD_CART_SET_MENU_URL = BASE_URL + "api/cart/insert/";

    public static String ADD_CART_VOUCHER_URL = BASE_URL + "api/cart/is_voucher_insert/";

    public static String VOUCHER_REDEEM_URL = BASE_URL + "api/ordersv1/add_voucher_redeem";

    /*Get Cart Details:
    https://ccpl.ninjaos.com/api/cart/contents
    app_id,customer_id,reference_id,availability_id*/

    public static String CART_LIST_URL = BASE_URL + "api/cart/contents";


    /*submit order:
       api/ordersv1/submit_order/
        app_id, sub_total, grand_total, "order_source",-> "Mobile", order_status",-> "1, products,
        availability_id, customer_id, customer_email, customer_mobile_no, customer_fname, customer_lname,
        category_id, cart_quantity, order_remarks, "payment_mode",-> "1", "zero_processing", "No",
        "allow_order", "No", "device_type", "Android", validation_only", "Yes", outlet_id,
        customer_address_line1, customer_postal_code, order_tat_time, customer_unit_no1, customer_unit_no2,
        "order_is_advanced", "No", order_date, delivery_charge, "discount_applied", "No"
    */

    public static String SUBMIT_ORDER_URL = BASE_URL + "api/ordersv1/submit_order";


    //public static String OTHER_ADDRESS_URL = BASE_URL + "api/customer/get_all_user_secondary_address";


    public static String PAYMENT_LOG_URL = BASE_URL + "api/reddot/reddotPaymentLog";


    /*order  history service
        https://ccpl.ninjaos.com/api/reports/order_history
        method: get
         app_id, local_order_no, customer_id
    */
    public static String ORDER_HISTORY_URL = BASE_URL + "api/reports/order_history";

    /*destroy cart item
     * api/cart/destroy/
     * method : post
     * params: app_id, customer_id
     * */

    public static String DESTROY_CART_URL = BASE_URL + "api/cart/destroy/";

    /*delete single cart
     * api/cart/delete/
     * method: post
     * params: cart_item_id, app_id, customer_id, reference_id
     * */

    public static String DELETE_SINGLE_CART_URL = BASE_URL + "api/cart/delete/";

    /*modifier validation service
    api/products/validate_product/
    params: app_id, product_id, modifier_value_id
    method: get*/
    public static String MODIFIER_VALIDATION_URL = BASE_URL + "api/products/validate_product/";

    /*update cart
    * https://ccpl.ninjaos.com/api/cart/update
        Params: product_id, cart_item_id, product_qty,cartAction(optional), app_id,customer_id,reference_id
        method: post
    * */

    public static String UPDATE_CART_URL = BASE_URL + "api/cart/update";

    /*udpate customer info
    api/cart/update_customer_info
    method: post
    params: app_id, reference_id, customer_id, availability_id*/

    public static String UPDATE_USER_INFO_URL = BASE_URL + "api/cart/update_customer_info";


    /*active count info
     * api/reports/activity_counts
     * params: app_id,customer_id
     * method : post
     * */
    public static String ACTIVE_COUNT_URL = BASE_URL + "api/reports/activity_counts1";

     /* CustomerDetail Url
    URL: https://ccpl.ninjaos.com/api/customer/customerdetail
    Method: GET
    Params : app_id,refrence*/

    public static String DEFAULT_BILLING_URL = BASE_URL + "api/customer/customerdetail";



    /* GETADDRESS Url
        URL: https://ccpl.ninjaos.com/api/settings/get_address
        Method: GET
        Params : app_id,zip_code*/

    public static String GETADDRESS = BASE_URL + "api/settings/get_address";

    public static String ADDADDRESS_URL = BASE_URL + "api/customer/secondary_address_add";


    /* UPDATEPROFILE Url
        URL: https://ccpl.ninjaos.com/api/customer/updateprofile
        Method: POST
        Params : app_id,customer_id,customer_first_name,customer_last_name,customer_email,customer_phone,
                 customer_birthdate,customer_nick_name,customer_address_line1,customer_postal_code,
                 customer_address_name,customer_address_name2,customer_hobby,customer_gender,type,customer_photo*/

    public static String UPDATEPROFILE_URL = BASE_URL + "api/customer/updateprofile";


    /* REDEEMPOINTS Url
        URL: https://ccpl.ninjaos.com/api/reports/redeem_points_count_history/
        Method: GET
        Params : app_id,customer_id*/

    public static String REDEEMPOINTS_URL = BASE_URL + "api/reports/redeem_points_count_history/";


    /*Other address url
     * api/customer/get_user_secondary_address
     * method :  get
     * params : app_id, refrence=customr id
     *
     * */
    public static String OTHER_ADDRESS_URL = BASE_URL + "api/customer/get_all_user_secondary_address";

    /*Rewards point earned
     * api/loyalty/customer_earned_rewardpoint_histroyv1
     * method : get
     * params: status=A, app_id, customer_id, limit, offset
     *
     * */

    public static String REWARD_POINTS_EARNED_URL = BASE_URL + "api/loyalty/customer_earned_rewardpoint_histroyv1";

    /*Rewards point redeemed
     * api/loyalty/customer_redeemed_rewardpoint_histroy
     * method : get
     * params: status=A, app_id, customer_id, limit, offset
     * */

    public static String REWARD_POINTS_REDEEMED_URL = BASE_URL + "api/loyalty/customer_redeemed_rewardpoint_histroy";

    /*Promotion redeemed
     * api/promotion_api_v2/promotionlistWhitoutuniqcode
     * method: get
     * params: status=A, app_id, customer_id
     * */

    public static String PROMOTION_REDEEM_URL = BASE_URL + "api/promotion_api_v2/promotionlistWhitoutuniqcode";

    /*Promotion redeemed
     * "api/promotion_api_v2/promotion_details"
     * method: get
     * params: app_id, promotion_id
     * */
    public static String PROMOTION_VIEW_URL = BASE_URL + "api/promotion_api_v2/promotion_details";

    /*Coupon code url
        api/Promotion_api_v2/apply_promotion
    * method : post
    * params: "app_id", promo_code, availability_id, reference_id, cart_amount, cart_quantity, category_id
    * */
    public static String COUPON_CODE_URL = BASE_URL + "api/Promotion_api_v2/apply_promotion";

    public static String VOUCHER_URL = BASE_URL + "api/promotion_api_v2/vouchers";

    /*add secondary address
        This service is only for delivery type order

        * customer/secondary_address_add
        * method : post
        * params: app_id, refrence, customer_address_line1, customer_postal_code, customer_address_name,
         * customer_address_name2, created_on, customer_status="A", customer_order_status="order"
    */

    public static String ADD_SECONDARY_ADDRESS_URL = BASE_URL + "api/customer/secondary_address_add";


    /*payment service
    *  http://ccpl.ninjaos.com/api/reddot/makeReddotPayment
    *  method : post
    *  parameters: app_id,card_number,card_holder_name,expiry_month,expiry_year,cvv_number,paid_amount,app_name,pay_mode
        Response: Ok and Error
    * */

    public static String PAYMENT_URL = BASE_URL + "api/reddot/makeReddotPayment";


    public static String PAYMENT_REDIRECT_URL = BASE_URL + "api/reddot/makeReddotPayRedirct";





    /*send mail
     * api/ordersv1/order_email
     * app_id, order_id
     * */

    public static String SEND_MAIL_URL = BASE_URL + "api/ordersv1/order_email";

    /*forgot password
     * api/customer/forgot_password
     * method: post
     * app_id, email_address
     * */
    public static String FORGOT_PASSWORD_URL = BASE_URL + "api/customer/forgot_password";

    public static String MEMBER_FORGOT_PASSWORD_URL = BASE_URL + "api/customer/forgot_memberpassword";

    /*reset password
     * api/customer/resetpassword
     * method: post
     * password, confirmpassword, key, app_id
     * */
    public static String RESET_PASSWORD_URL = BASE_URL + "api/customer/resetpassword";

    public static String MEMBER_RESET_PASSWORD_URL = BASE_URL + "api/ascentiscrm/resetMembershipPassword";


    /*app activation
     * api/customer/activation
     * app_id, key
     * */
    public static String ACTIVATION_URL = BASE_URL + "api/customer/activation";

    /*tat time for cart page
    * http://ccpl.ninjaos.com/apiv2/outlets/getOutletTatSlot/
        app_id,outletId,availability
    * */
    public static String CART_TAT_URL = BASE_URL + "apiv2/outlets/getOutletTatSlot";

    /*Search
    https://ccpl.ninjaos.com/apiv2/products/search_products?pageToken=a1c60e7319120d47e788905159e21096&app_id=F60DC85C-6801-4536-8102-65D9A8666940&availability=718B1A92-5EBB-4F25-B24D-3067606F67F0&outletId=151&productSearchKey=lar
    *  method: get
    * app_id, status=A ,availability, productSearchKey, search_value, outletId
    */
    public static String SEARCH_URL = BASE_URL + "apiv2/products/search_products";

    /*set menu componenet product details
     * /api/products/products_list?
     * app_id, product_id,availability
     * method : get
     * */
    public static String SETMENU_COMPENENT_URL = BASE_URL + "api/products/products_list";


    public static String ContactUS = BASE_URL + "api/contactus/contact_us";


    /*delete secondary address
     * api/customer/secondary_address_remove
     * method : post
     * params: app_id, customer_id, secondary_address_id
     * */

    public static String DELETE_SECONDARY_ADDRESS_URL = BASE_URL + "api/customer/secondary_address_remove";


    /*Menu
     * api/menu/menu/
     * app_id, menu_slug=georges-cms-menu
     * */
    public static String MENU_URL = BASE_URL + "api/menu/menu";

    /*CMS
     * api/menu/menu/
     * app_id, search_key=cmspage_slug,search_value
     * */
    public static String CMS_URL = BASE_URL + "api/cms/page";


    /*notification list url
     * api/customer/activity_list
     * method: get
     * params: app_id, customer_id
     * */

    public static String NOTIFICATION_LIST_URL = BASE_URL + "api/customer/activity_list";

    /*read notification
     * api/customer/activity_read_status
     * method : get
     * params: app_id, customer_id, act_id
     * */

    public static String NOTIFICATION_READ_URL = BASE_URL + "api/customer/activity_read_status";


    public static String REWARD_POINT_URL = BASE_URL + "api/promotion_api_v2/claimpromo";


//    by gowtham

    public static String APPLY_REWARD_POINT_URL = BASE_URL + "api/Loyalty/apply_loyalityv1";


    /// Address Selection

/*    https://ccpl.ninjaos.com/api/customer/get_all_user_secondary_address?
    // app_id=F60DC85C-6801-4536-8102-65D9A8666940&status=A&refrence=4459*/


    public static String GET_SECONDARY_ADDRESS_URL = BASE_URL + "api/customer/get_all_user_secondary_address";

    public static String shutdDownCheckURL = BASE_URL + "api/settings/check_maintance_mode/";

    public static String appVersionURL = BASE_URL + "api/settings/get_app_version";

    public static String ReadAllNotificationUrl = BASE_URL + "api/customer/activity_read_all";

// compass outlet Url

    public static String COMPASSOUTLET_URL = BASE_URL + "api/outlets/pickup_outlets";

    public static String COMPASSCATEGORY_URL = BASE_URL + "apiv2/products/getAllProducts";


    public static String FavouriteURl = BASE_URL + "api/products/favourite_products_add";


    public static String FavouriteListURL = BASE_URL + "api/products/favourite_products_list";


    public static String FavouriteListValidationURL = BASE_URL + "api/products/favourite_product_valid";


    public static String webAuthorizeURL = BASE_URL + "api/payment/authOmise";

    public static String walletAuthorizeURL = BASE_URL + "api/payment/authOmisePoints";

    //public static String webChargeURL = baseURL + "api/paymentv5/captureAmount/";
    public static String webChargeURL = BASE_URL + "api/payment/captureAmountOmise";

    public static String webChargeRenewURL = BASE_URL + "api/payment/captureAmountOmisesubscription";

    public static String walletChargeURL = BASE_URL + "api/payment/captureAmountOmisePoints";

    //public static String webRefundURL = baseURL + "api/Paymentv5/refund/";
    public static String webRefundURL = BASE_URL + "api/payment/refund/";


    public static String webSavecardURL = BASE_URL + "api/payment/savedOmiseCards";

    public static String DELET_CARD_URL = BASE_URL + "api/payment/deleteOmiseCard";

    public static String PRICE_FOR_POINTS_URL = BASE_URL + "api/payment/priceForPoints";

    public static String OMISE_POINTS_URL = BASE_URL + "api/payment/authOmisePoints";

    public static String CAPTURE_AMOUNT_URL = BASE_URL + "api/payment/captureAmountOmisePoints";

    public static String UPDATE_NOTIFICATION_URL = BASE_URL + "api/customer/update_notification_status";

    public static String CHECK_EWALLET = BASE_URL + "api/ordersv1/checkEwalletBalance";

    public static String GENERATE_REFERRAL_CODE = BASE_URL + "api/customer/generate_refercode";

    public static final String EMAIL_VERIFICATION =  BASE_URL + "apiV4/guest-checkout/customer/corporate_customers_otp_verification";

    public static final String ABOUT_US =  "https://sushitei.com/about-us/";

    public static final String NETS_REGISTRATION = BASE_URL + "api/netsclickpay/tokenRegister";

    public static final String NETS_PURCHASE = BASE_URL + "api/netsclickpay/purchaseOrder";

    public static final String MEMBER_RENEWAL = BASE_URL + "api/ascentiscrm/memberCardRenewal";

    public static final String MEMBER_CHANGE_PASS = BASE_URL + "api/ascentiscrm/memberChangePassWord";


}
