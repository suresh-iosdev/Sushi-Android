package com.app.sushi.tei.fcm;

import android.app.Notification;
import android.app.NotificationManager;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sushi.tei.R;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;



/**
 * Created by gowtham on 11/13/2017.
 */

public class FirebaseMessages extends FirebaseMessagingService {

    private final String TAG = "FirebaseMesagingService";
    private NotificationCompat.Builder notificationBuilder;
    //    private Prefs prefs;
    public static final String INTENT_FILTER = "INTENT_FILTER";
    int notifyID = 1;
    String CHANNEL_ID = "my_channel_01";// The id of the channel.
    //    CharSequence name = getString(R.string.channel_name);// The user-visible name of the channel.
    int importance = NotificationManager.IMPORTANCE_HIGH;
    private LocalBroadcastManager localBroadcastManager;

    //SessionManager session;

    @Override
    public void onCreate() {
        super.onCreate();
         FirebaseInstanceId.getInstance().getToken();

        //session = new SessionManager(this);
    }

    /*@Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        String refreshedToken = FirebaseInstanceId.getInstance().getToken();

//        GlobalValues.DeviceToken=refreshedToken;
        //prefs.setString("DEVICE_TOKEN",refreshedToken);

    }*/

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);

         String title = remoteMessage.getData().get("title");
        String body = remoteMessage.getData().get("body");
        String icon = remoteMessage.getData().get("icon");
        String objectId = remoteMessage.getData().get("object_id");
        String objectType = remoteMessage.getData().get("objectType");
        Notification notification = new NotificationCompat.Builder(this)
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.drawable.app_icon)
                .build();


        NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
        manager.notify(/*notification id*/0, notification);
        /*if (remoteMessage != null) {

            if (remoteMessage.getData() != null) {


                if (prefs.getString("IS_ACTIVE").equalsIgnoreCase("1")) {

                    //GlobalValues.isActivr = true;

                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent onRefreshAction = new Intent(INTENT_FILTER);
                    localBroadcastManager.sendBroadcast(onRefreshAction);

                } else {

                    setSampleNotification("OkeyChef", "New Order Received");


                }
            }


        }*/
    }

    private void setSampleNotification(String title, String body) {

        // String response="{\"status\":\"ok\",\"result_set\":[{\"order_customer_id\":\"314\",\"customer_name\":\"Yi qi Lee\",\"order_customer_email\":\"yiqi87@gmail.com\",\"order_customer_mobile_no\":\"81890668\",\"order_customer_unit_no1\":\"\",\"order_customer_unit_no2\":\"\",\"order_customer_address_line1\":\"\",\"order_customer_address_line2\":\"\",\"order_customer_city\":\"\",\"order_customer_state\":\"\",\"order_customer_country\":\"\",\"order_customer_postal_code\":\"\",\"order_customer_created_on\":\"2019-10-25 13:28:30\",\"customer_unique_id\":null,\"membership_type\":\"\",\"status_name\":\"Completed\",\"order_agent\":null,\"order_method_name\":\"Credit card\\/Debit card\",\"assigned_order_rider_id\":null,\"rider_fname\":null,\"rider_lname\":null,\"rider_mobile_no\":null,\"padd_api_type\":null,\"padd_api_order_id\":null,\"padd_api_driver_id\":null,\"padd_api_driver_name\":null,\"padd_api_driver_phone\":null,\"outlet_name\":\"Cafe Connect\",\"outlet_smartsend_id\":\"0\",\"order_primary_id\":\"1307\",\"order_tat_time\":\"15 Mins\",\"order_id\":\"4749D2C7-F113-4DBB-9D29-639C3AD9756D\",\"order_outlet_id\":\"5\",\"order_delivery_charge\":\"0.00\",\"order_tax_charge\":\"0.00\",\"order_discount_applied\":\"No\",\"order_discount_amount\":\"0\",\"order_discount_type\":\"\",\"order_promocode_name\":null,\"order_sub_total\":\"3.50\",\"order_total_amount\":\"3.50\",\"order_payment_mode\":\"3\",\"order_payment_getway_type\":\"omise\",\"order_payment_retrieved\":\"Yes\",\"order_cash_payment\":\"Unpaid\",\"order_date\":\"2019-10-25 13:45:00\",\"order_status\":\"4\",\"order_availability_id\":\"718B1A92-5EBB-4F25-B24D-3067606F67F0\",\"order_availability_name\":\"Pickup\",\"order_pickup_time\":\"0000-00-00 00:00:00\",\"order_pickup_outlet_id\":\"0\",\"order_source\":\"Mobile\",\"order_callcenter_admin_id\":\"0\",\"order_local_no\":\"191025.M1011\",\"order_remarks\":\"\",\"order_table_number\":\"\",\"order_additional_delivery\":\"0.00\",\"order_tax_calculate_amount\":\"0.00\",\"order_tax_charge_inclusive\":\"0.00\",\"order_tax_calculate_amount_inclusive\":\"0.00\",\"order_service_charge\":\"0.00\",\"order_service_charge_amount\":\"0.00\",\"order_special_discount_amount\":\"0.00\",\"order_special_discount_type\":\"\",\"order_redeemed_points\":null,\"order_carpal_api_order_id\":null,\"order_delivary_type\":\"own driver\",\"order_smart_send_id\":\"0\",\"order_smartsend_accesstoken\":null,\"order_pos_log_count\":0,\"order_payment_statustext\":\"Success\",\"order_type\":\"current\",\"order_date_before_30minit\":\"25-10-2019 01:15 PM\",\"items\":[{\"item_id\":\"1788\",\"item_order_primary_id\":\"1307\",\"item_order_id\":\"4749D2C7-F113-4DBB-9D29-639C3AD9756D\",\"item_product_id\":\"082ED123-5BEB-4B80-96D1-AAD5F8D6A593\",\"item_name\":\"Flat White\",\"item_image\":\"https:\\/\\/venus.ninjaos.com\\/media\\/dev_team\\/products\\/main-image\\/3f9299cd016fe7476027d428ea8e1226.jpg\",\"item_sku\":\"CC-Flat White\",\"item_specification\":\"\",\"item_qty\":\"1\",\"item_unit_price\":\"3.50\",\"item_original_unit_price\":\"3.50\",\"item_total_amount\":\"3.50\",\"item_special_amount\":null,\"item_user_rating\":null,\"item_breaktime_indexflag\":null,\"item_breaktime_started\":null,\"item_breaktime_ended\":null,\"item_user_rating_message\":null,\"item_created_on\":\"2019-10-25 13:28:30\",\"item_placed_on\":\"2019-10-25 13:28:30\",\"item_kitchen_status\":\"0\",\"modifiers\":[],\"extra_modifiers\":[],\"set_menu_component\":[{\"menu_component_id\":\"262\",\"menu_component_name\":\"Choose Size\",\"product_details\":[{\"menu_primary_id\":\"1708\",\"menu_product_id\":\"4CDEB6F5-16E4-48A4-9B19-A29EDB0461D5\",\"menu_product_name\":\"12oz - Regular\",\"menu_product_qty\":\"1\",\"menu_product_sku\":\"Coffee- 12oz - Regular\",\"modifiers\":[]}]},{\"menu_component_id\":\"264\",\"menu_component_name\":\"Choose Milk\",\"product_details\":[{\"menu_primary_id\":\"1709\",\"menu_product_id\":\"75094109-C803-4310-99B2-77E9C1040F2B\",\"menu_product_name\":\"Skinny Milk\",\"menu_product_qty\":\"1\",\"menu_product_sku\":\"CC-Flat White- Skinny Milk\",\"modifiers\":[]}]}]}]}]}";
/*        if (session.isLoggedIn()) {
            Intent pintent = new Intent(this, SubCategoryActivity.class);
            pintent.putExtra("GIF_IMAGE","1");
            pintent.putExtra("PRINT_CLICK","1");
            pintent.putExtra("PRINT_RESPONSE","");
            pintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pintent);
        }*/



    }
}
