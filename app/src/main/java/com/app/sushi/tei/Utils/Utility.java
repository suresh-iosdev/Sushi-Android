package com.app.sushi.tei.Utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.R;
import com.google.firebase.iid.FirebaseInstanceId;

import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static com.app.sushi.tei.activity.FiveMenuActivityNew.frgagment_tag;
import static com.facebook.FacebookSdk.getApplicationContext;


public class Utility {

    public static void changeFragmentWithoutBackStack(Fragment fragment, FragmentManager supportFragmentManager, Activity context) {
        while (supportFragmentManager.getBackStackEntryCount() > 0) {
            supportFragmentManager.popBackStackImmediate();
        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.frame_container, fragment, frgagment_tag)
                .commit();
    }

    public static void changeFragmentWitBackStack(Fragment fragment, FragmentTransaction transaction) {

        transaction.replace(R.id.frame_container, fragment, frgagment_tag)
                .addToBackStack(null)
                .commit();
    }


    public static boolean networkCheck(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        return isConnected;
    }

    public static String getValidString(String data) {
        if (data == null)
            return "";

        if (data.equals("null"))
            return "";

        return data;
    }


    public static String getKeyHash() {
        PackageInfo packageInfo;
        String key = null;
        try {
            // getting application package name, as defined in manifest
            String packageName = getApplicationContext()
                    .getPackageName();

            // Retriving package info
            packageInfo = getApplicationContext().getPackageManager().getPackageInfo(
                    packageName, PackageManager.GET_SIGNATURES);

            for (Signature signature : packageInfo.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                key = new String(Base64.encode(md.digest(), 0));
                System.out.println("HASHKEY====" + key);
            }
        } catch (PackageManager.NameNotFoundException e1) {
            Log.e("Name not found", e1.toString());
            key = null;
        } catch (NoSuchAlgorithmException e) {
            Log.e("No such an algorithm", e.toString());
            key = null;
        } catch (Exception e) {
            Log.e("Exception", e.toString());
            key = null;
        }

        return key;
    }

    public static SpannableString setPriceSpan(Context context, double cost) {

        SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(cost)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        return cs;
    }


    public static String getDeviceId(Context context) {

        ArrayList<Integer> integerArrayList = new ArrayList<>();

     /*   int rows = 5, k = 0;

        for(int i = 1; i <= rows; ++i, k = 0)
        {
            for(int space = 1; space <= rows - i; ++space)
            {
                System.out.print("  ");
            }

            while(k != 2 * i - 1)
            {
                System.out.print("* ");
                ++k;
            }

            System.out.println();
        }*/


        boolean isDeviceId;
        String refreshedToken = "";

        try {
            refreshedToken = FirebaseInstanceId.getInstance().getToken();



            if (!refreshedToken.isEmpty()) {
                isDeviceId = true;
            } else {
                isDeviceId = false;
            }
        } catch (Exception e) {
            e.printStackTrace();

            isDeviceId = false;
        }

        return refreshedToken;
    }


    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId,
                                                         int reqWidth, int reqHeight) {

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }


    public static int calculateInSampleSize(
            BitmapFactory.Options options, int reqWidth, int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) >= reqHeight
                    && (halfWidth / inSampleSize) >= reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }

    public static void writeToSharedPreference(Context context, String key, String value) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalValues.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static boolean isCustomerLoggedIn(Context context) {

        if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static String getCurrentTime() {

        try {
            Calendar calendar = Calendar.getInstance();

            SimpleDateFormat df = new SimpleDateFormat("yyyy:MM:dd HH:mm:ss");

            Date d = calendar.getTime();
            String s = df.format(d);

            System.out.println("current time" + calendar.getTime().toString());

            return s;

        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String readFromSharedPreference(Context context, String key) {
        String value = "";

        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalValues.SP_NAME, Context.MODE_PRIVATE);
        value = sharedPreferences.getString(key, "");
        return value;
    }

    public static void removeFromSharedPreference(Context context, String key) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(GlobalValues.SP_NAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.commit();
    }

    public static String convertDate(String order_date) {

        String convertedDate = "";

        SimpleDateFormat fromFormat = new SimpleDateFormat("dd-MM-yyyy");
        SimpleDateFormat toFormat = new SimpleDateFormat("dd-MM-yyyy");

        try {

            Date date = fromFormat.parse(order_date);
            String formattedDate = toFormat.format(date);

            Log.e("converted_date", formattedDate);

           /* Date dtStartOK = toFormat.parse(formattedDate);

            String stringDate = toFormat.format(dtStartOK);

            System.out.println(stringDate);*/

            convertedDate = formattedDate;


        } catch (ParseException e) {
            //Handle exception here, most of the time you will just log it.
            e.printStackTrace();
            convertedDate = "";
            return convertedDate;
        } catch (Exception e) {
            e.printStackTrace();
            return convertedDate;
        }


        return convertedDate;
    }

    public static String convertTime(String time) {
        String convertedTime = "";

        try {

            SimpleDateFormat displayFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat parseFormat = new SimpleDateFormat("hh:mm a");

            Date x = null;

            x = displayFormat.parse(time);

            convertedTime = parseFormat.format(x);

            System.out.println(convertedTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedTime;
    }

    public static String convertTimeTo24hrFormat(String time) {
        String convertedTime = "";

        try {


            SimpleDateFormat parseFormat = new SimpleDateFormat("HH:mm");
            SimpleDateFormat displayFormat = new SimpleDateFormat("hh:mm a");

            Date x = null;

            x = displayFormat.parse(time);

            convertedTime = parseFormat.format(x);

            System.out.println(convertedTime);

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return convertedTime;
    }

    @SuppressLint("MissingPermission")
    public static String getReferenceId(Context context) {

        String mReferenceId = "";

        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
            mReferenceId = GlobalValues.DEVICE_ID;

        } catch (Exception e) {
            GlobalValues.DEVICE_ID = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            mReferenceId = GlobalValues.DEVICE_ID;

        }
        return mReferenceId;
    }

    public static int getCartCount(Context mContext) {
        int value = 0;
        try {
            value = Integer.parseInt(Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT));

        } catch (Exception e) {
            e.printStackTrace();
            value = 0;
        }
        return value;
    }

    public static String getCartAvailabilityId(Context context) {
        String id = "";


        if (readFromSharedPreference(context, GlobalValues.CART_RESPONSE).length() > 0) {
            try {
                JSONObject jsonObject = new JSONObject(readFromSharedPreference(context, GlobalValues.CART_RESPONSE));


                JSONObject jsonCartDetails = jsonObject.getJSONObject("cart_details");

                id = jsonCartDetails.optString("cart_availability_id");


            } catch (JSONException e) {
                e.printStackTrace();
                id = "";
            }

        } else {
            id = "";
        }

        return id;


    }

    public static int getAvailabilityIndex(String id) {
        int position = 0;

        if (id.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
            position = 1;
        } else if (id.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
            position = 2;
        }


        return position;
    }

    public static String getProductName(SetMenuModifier setMenuModifier) {


        if (setMenuModifier == null)
            return "";


        if (setMenuModifier.getmModifierAliasName() != null
                && !setMenuModifier.getmModifierAliasName().equals("") &&
                !setMenuModifier.getmModifierAliasName().equals("null")) {

            return setMenuModifier.getmModifierAliasName();

        }

        if (setMenuModifier.getmModifierName() != null && !setMenuModifier.getmModifierName().equals("null")) {

            return setMenuModifier.getmModifierName();

        } else {
            return "";
        }
    }


    public static String checkBefore22(String endtime) {

        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");

        String time = "";

        try {
            Date dstart = timeformat.parse("14:00");
            Date dend = timeformat.parse(endtime);

            if (dend.after(dstart) || dend.equals(dstart)) {
                 time = "14:00";
                return time;
            } else {
                 time = endtime;
                return time;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endtime;

    }


    public static String checkBefore11(String endtime) {

        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");
        SimpleDateFormat twelvetimeformat = new SimpleDateFormat("hh:mm");

        String time = "";


        try {
            Date dstart = timeformat.parse("23:59");
            Date dstartmi = twelvetimeformat.parse("12:00");
//            Date dstartltd = timeformat.parse("00:00");
            Date dend = timeformat.parse(endtime);


            if (dend.after(dstart) || dend.equals(dstart)) {

//
                Calendar calltd = Calendar.getInstance();
                calltd.setTime(dstartmi);
                calltd.add(Calendar.MINUTE, -GlobalValues.INERVAL_TIME);


                GlobalValues.SUBSTRATED_TIME = timeformat.format(calltd.getTime());
                time = timeformat.format(calltd.getTime());


//                time = "23:45";


                return time;
            } else {
                 time = endtime;
                return time;
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }

        return endtime;

    }

    public static String checkAfter11(String endtime) {

        SimpleDateFormat timeformat = new SimpleDateFormat("HH:mm");

        String time = "";

        try {
            Date dstart = timeformat.parse("23:30");
            Date dend = timeformat.parse(endtime);

            if (dend.after(dstart) || dend.equals(dstart)) {
                 time = "23:00";
                return time;
            } else {
                 time = endtime;
                return time;
            }

        } catch (ParseException e) {
            e.printStackTrace();
            return "23:00";
        }
    }

    public Double InclusiveGst(Double GrandTotal) {
        Double Grandamt = 0.0;

        if (GrandTotal > 0.0) {
            int Ca1 = 1 + (7 / 100);
            int Ca2 = 7 / 100;
            Double productc1 = GrandTotal / Ca1;
            Grandamt = productc1 * Ca2;
        }

        return Grandamt;
    }

    public static boolean emailValidation(String Email) {
        return (!TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches());
    }

}
