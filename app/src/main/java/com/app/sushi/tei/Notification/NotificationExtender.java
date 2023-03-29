package com.app.sushi.tei.Notification;

import android.content.Intent;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import android.util.Log;

import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.activity.SubCategoryActivity;
/*import com.onesignal.NotificationExtenderService;
import com.onesignal.OSNotificationReceivedResult;*/


/*public class NotificationExtender extends NotificationExtenderService {
    public static final String INTENT_FILTER = "INTENT_FILTER";
    private NotificationCompat.Builder mbuilder;
    public static final String TAG = "ONESIGNAL_SERVICE";


    private LocalBroadcastManager localBroadcastManager;

    private String orderId = "1";
    private String Channel_Id = "01";
    private int notificationId = 0;
    private String orderSummary = "";


    @Override
    public void onCreate() {
        super.onCreate();


    }

    @Override
    protected boolean onNotificationProcessing(final OSNotificationReceivedResult receivedResult) {



        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        if (receivedResult != null) {




            if (receivedResult.payload != null) {


                if (Utility.readFromSharedPreference(this,"IS_ACTIVE").equalsIgnoreCase("1")) {

                    //GlobalValues.isActivr = true;
                    localBroadcastManager = LocalBroadcastManager.getInstance(this);
                    Intent onRefreshAction = new Intent(INTENT_FILTER);
                    onRefreshAction.putExtra("ORDER_ID", orderId);
                    onRefreshAction.putExtra("PRINT_CLICK", "1");
                    onRefreshAction.putExtra("FROM", "1");
                    localBroadcastManager.sendBroadcast(onRefreshAction);
                } else {

                    setSampleNotification("Sushi Tei", "New Order Received", orderId);
                }
            }
        }
        return false;
    }

    private void setSampleNotification(String title, String body, String orderId) {

        if (Utility.isCustomerLoggedIn(this)) {

            Intent pintent = new Intent(this, SubCategoryActivity.class);
            pintent.putExtra("GIF_IMAGE", "1");
            pintent.putExtra("PRINT_CLICK", "1");
            pintent.putExtra("PRINT_RESPONSE", "");
            pintent.putExtra("ORDER_ID", orderId);
            pintent.putExtra("FROM", "2");
            pintent.putExtra("ORDER_TYPE", "1");
            pintent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            pintent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(pintent);
        }
    }
}*/


//order status
//your is deliveted
