package com.app.sushi.tei.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.appcompat.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.BannerModel;
import com.app.sushi.tei.Model.Landing.ResultSetItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.SliderBannerAdapter;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.google.android.gms.common.GooglePlayServicesNotAvailableException;
import com.google.android.gms.common.GooglePlayServicesRepairableException;
import com.google.android.gms.security.ProviderInstaller;


import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static com.app.sushi.tei.Utils.Utility.getDeviceId;

public class SplashActivity extends AppCompatActivity {

    private Context mContext;
    private TextView txtGetStarted;
    private TextView btnLogin;
    private TextView btnRegister;
    private Intent intent;
    private LocationManager locationManager;

    //private ExoVideoView exoVideoView;
    private ArrayList<ResultSetItem> landinglist;
    private TextView LoginLater;

    private SliderBannerAdapter sliderBannerAdapter;
    ViewPager viewPager;
    LinearLayout SliderDots;
    private ImageView[] dots;
    private List<BannerModel> sliderBannerAdapterList;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT);
        mContext = SplashActivity.this;
        //exoVideoView = findViewById(R.id.videoView);
         getDeviceId(mContext);

         locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);

        try {
            ProviderInstaller.installIfNeeded(getApplicationContext());
        } catch (GooglePlayServicesRepairableException e) {
            e.printStackTrace();
        } catch (GooglePlayServicesNotAvailableException e) {
            e.printStackTrace();
        }

        Utility.writeToSharedPreference(mContext, GlobalValues.OPENLOGIN, "open");
        txtGetStarted = (TextView) findViewById(R.id.txtGetStarted);
        btnLogin = findViewById(R.id.btn_login);
        btnRegister = findViewById(R.id.btn_register);

        LoginLater = findViewById(R.id.LoginLater);

        viewPager = findViewById(R.id.viewPager);
        SliderDots = findViewById(R.id.SliderDots);

//        SpannableString content = new SpannableString("I'II log in later");
//        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
//        LoginLater.setText(content);



        GlobalValues.ACCESS_TOKEN=Utility.readFromSharedPreference(mContext,GlobalValues.ACCESS_TOKEN_KEY);

        LoginLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                if (disableLoginLater) {
//                    Toast.makeText(mContext, "Please Login to Continue", Toast.LENGTH_SHORT).show();
//                } else {
                Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                intent = new Intent(mContext, ChooseOutletActivity.class);
                startActivity(intent);
                finish();


//                    Toast.makeText(LoginActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
//                    Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//                    intent = new Intent(mContext, MenuCategoryActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
            }
        });

        txtGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                    intent = new Intent(mContext, SubCategoryActivity.class);
                    startActivity(intent);
                    finish();
                }else {
                    intent = new Intent(mContext, LoginActivity.class);
                    startActivity(intent);
                    finish();
                }*/

//                intent = new Intent(mContext, LandingActivity.class);
//                startActivity(intent);
//                finish();

                goDirectlyToOutletOrProducts();
            }
        });
//test
//        Toast.makeText(SplashActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
//        Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
//        intent = new Intent(mContext, ChooseOutletActivity.class);
//        startActivity(intent);
//        finish();

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra("isFromSplash", true);
                startActivity(intent);
                finish();
            }
        });
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                intent = new Intent(mContext, LoginActivity.class);
                intent.putExtra("isRegister", true);
                intent.putExtra("isFromSplash", true);
                startActivity(intent);
                finish();
            }
        });


        requestPhotoPermission();
        //checkLocationPermission();


      /*  GlobalValues.CURRENT_AVAILABLITY_ID="";
        GlobalValues.CURRENT_AVAILABLITY_NAME="";
        GlobalValues.CURRENT_OUTLET_ID="";
        GlobalValues.DELIVERY_DATE="";
        GlobalValues.DELIVERY_TIME="";*/

        try {
            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
        } catch (SecurityException e) {
            GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
        }

        if (Utility.networkCheck(mContext)) {

            String app_id = "?app_id=" + GlobalValues.APP_ID;

            String url = GlobalUrl.SETTINGS_URL + app_id;

            new SettingsRequest().execute(url);

        } else {
            Toast.makeText(mContext,"You are offline please check your internet connection",Toast.LENGTH_SHORT).show();
        }




        if (Utility.networkCheck(mContext)) {
            new ShutDownCheckTask().execute();
        } else {
            Toast.makeText(SplashActivity.this, "Please check your internet connection.", Toast.LENGTH_LONG).show();
        }



//        if (Utility.networkCheck(mContext)) {
//            String app_id = "?app_id=" + GlobalValues.APP_ID;
//            String url = GlobalUrl.LANDING_URL + app_id;
//            new LandingRequestTask().execute(url);
//        } else {
//            Toast.makeText(mContext, "You are offline please check your internet connection", Toast.LENGTH_SHORT).show();
//        }

//        setupSlide();
        setUpSlider(); //custom slider
    }

    public void requestPhotoPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            requestPermissions(new String[]{android.Manifest.permission.WRITE_EXTERNAL_STORAGE}, GlobalValues.REQUEST_WRITE_PERMISSION);
        } else {

        }
    }


    class SettingsRequest extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.setCancelable(false);
            //progressDialog.show();
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

                    JSONObject jsonResultSet = jsonObject.getJSONObject("result_set");

                    String enable = jsonResultSet.getString("client_delivery_enable");

                    String time1 = jsonResultSet.optString("client_cod_start_time");
                    String time2 = jsonResultSet.optString("client_cod_end_time");

                    //client referral code.
                    String client_referral_enable = jsonResultSet.optString("client_referral_enable");
                    Utility.writeToSharedPreference(mContext, GlobalValues.IS_REFERRAL_ENABLE, client_referral_enable);
                    String client_mincart_quantity_bento = jsonResultSet.optString("client_mincart_quantity_bento");
                    String client_minimum_cart_quantity_enable = jsonResultSet.optString("client_minimum_cart_quantity_enable");
                    String client_tax_surcharge = jsonResultSet.optString("client_tax_surcharge_inclusive");
                    Utility.writeToSharedPreference(mContext, GlobalValues.GstChargers, client_tax_surcharge);
                    Utility.writeToSharedPreference(mContext, GlobalValues.MinimumQuality, client_mincart_quantity_bento);
                    Utility.writeToSharedPreference(mContext, GlobalValues.Minimumavailable, client_minimum_cart_quantity_enable);

                    String omise_public_key = jsonResultSet.optString("omise_public_key");
                    String paymentenabled=jsonResultSet.getString("client_payments");
                    Utility.writeToSharedPreference(mContext, GlobalValues.PAYMENTKEY, "");
                    Utility.writeToSharedPreference(mContext,GlobalValues.PAYMENTKEYENABLE,paymentenabled);

                    if (!omise_public_key.equals("")) {

                        Utility.writeToSharedPreference(mContext, GlobalValues.PAYMENTKEY, omise_public_key);
                    }



                    if (!jsonResultSet.isNull("client_packaging_charge")) {

                        String packing_charge = jsonResultSet.optString("client_packaging_charge");
                        Utility.writeToSharedPreference(mContext, GlobalValues.PACKING_CHARGE, packing_charge);
                    }


                    Utility.writeToSharedPreference(mContext, GlobalValues.GstCharger, client_tax_surcharge);

                    Utility.writeToSharedPreference(mContext, GlobalValues.COD_START_TIME, time1);
                    Utility.writeToSharedPreference(mContext, GlobalValues.COD_END_TIME, time2);

                    Utility.writeToSharedPreference(mContext, GlobalValues.GSTIN, jsonResultSet.optString("client_tax_surcharge_inclusive"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CLIENT_ENABLE_MEMBERSHIP, jsonResultSet.optString("client_enable_membership"));




                    if (enable.equals("1")) {

                        Double delCharge = Double.parseDouble(jsonResultSet.getString("client_delivery_surcharge"));

                        Double addCharge = Double.parseDouble(jsonResultSet.getString("additional_delivery_charge"));


//                        GlobalValues.ADDITIONAL_DELIVERY_CHARGE = addCharge;



//                        GlobalValues.COMMON_DELIVERY_CHARGE = delCharge + addCharge;

                    } else {
                        GlobalValues.COMMON_DELIVERY_CHARGE = 0.0;
                    }

                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    showAlertDialog(jsonObject.optString("message"));
                }

            } catch (Exception e) {
                e.printStackTrace();

                showAlertDialog("Something went wrong. Please try again later.");
            } finally {
                //progressDialog.dismiss();

            }

        }
    }

    private void showAlertDialog(String s) {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogTheme);

        alertDialog.setMessage(s);
        alertDialog.setTitle("Warning");

        // Setting OK Button
        alertDialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                finish();
            }
        });

        // Showing Alert Message
        alertDialog.show();
    }


    OkHttpClient appVersionClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public String appVersionRun(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = appVersionClient.newCall(request).execute();
        return response.body().string();
    }

    private class AppVersionTask extends AsyncTask<String, String, String> {

        String response, status, message;
        ProgressDialog availibilityDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            availibilityDialog = new ProgressDialog(SplashActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            availibilityDialog.setMessage("Loading...");
            availibilityDialog.setCancelable(false);
            //availibilityDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                String appVersionURL = GlobalUrl.appVersionURL + "?app_id=" + GlobalValues.APP_ID;

                response = appVersionRun(appVersionURL);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            // availibilityDialog.dismiss();

            if (response != null) {
                Log.e("App_Version_Response", response);

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);
                    status = responseJSONObject.getString("status");

                    if (status.equals("ok")) {    //Success

                        JSONObject resultSetJSONObj = responseJSONObject.getJSONObject("result_set");
                        Integer androidCurrentVersion = resultSetJSONObj.getInt("android");



                        if (GlobalValues.CUSTOM_VERSION_CODE < androidCurrentVersion) {

                            final AlertDialog.Builder alertDialog = new AlertDialog.Builder(SplashActivity.this);
                            alertDialog.setMessage("A newer version of App is available.");
                            alertDialog.setTitle("Update");
                            alertDialog.setPositiveButton("UPDATE", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    alertDialog.show();
                                    final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
                                    try {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
                                    } catch (android.content.ActivityNotFoundException anfe) {
                                        startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=" + appPackageName)));
                                    }
                                }
                            });
                            try {
                                alertDialog.setCancelable(false);
                                alertDialog.show();
                            }
                            catch (WindowManager.BadTokenException e) {
                                //use a log message
                            }

                        }else {
                            goDirectlyToOutletOrProducts();
                        }
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    OkHttpClient shutDownCheckClient = new OkHttpClient.Builder()
            .connectTimeout(10, TimeUnit.SECONDS)
            .writeTimeout(10, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .build();

    public String shutDownCheckRun(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = shutDownCheckClient.newCall(request).execute();
        return response.body().string();
    }


    private class ShutDownCheckTask extends AsyncTask<String, String, String> {

        String response, status, message;

        ProgressDialog availibilityDialog;

        int type;

        ShutDownCheckTask() {

        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            availibilityDialog = new ProgressDialog(SplashActivity.this, ProgressDialog.THEME_HOLO_LIGHT);
            availibilityDialog.setMessage("Loading...");
            availibilityDialog.setCancelable(false);
            //availibilityDialog.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            try {


                String app_id = "?app_id=" + GlobalValues.APP_ID;
                String mode = "&mode=" + "live";


                String deviceType = "&request_type=android";

                String url = GlobalUrl.shutdDownCheckURL + app_id + deviceType + mode;

                response = shutDownCheckRun(url);

            } catch (IOException e) {
                e.printStackTrace();
            }
            return response;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            //availibilityDialog.dismiss();

            if (response != null) {

                Log.e("ShutDownCheck_Response:", response);

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);
                    status = responseJSONObject.getString("status");

                    JSONObject resultJSON = responseJSONObject.getJSONObject("result_set");

                    if (status.equals("ok")) {    //Success

                        String mode = resultJSON.getString("mode");

                  //      String mode = "Yes";

                        String messgae_title = resultJSON.getString("messgae_title");
                        String messgae_desc = resultJSON.getString("messgae_desc");
                        if (mode.equals("Yes")) {
                            Intent intent = new Intent(SplashActivity.this, MaintenanceActivity.class);
                            intent.putExtra("messgae_title", messgae_title);
                            intent.putExtra("messgae_desc", messgae_desc);
                            startActivity(intent);
                            finish();
                        } else {
                            //Check Now
                            if (Utility.networkCheck(mContext)) {
                                new AppVersionTask().execute();
                            } else {
                                Toast.makeText(SplashActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                            }

                        }
                    } else {
                        //Check Now
                        if (Utility.networkCheck(mContext)) {
                            new AppVersionTask().execute();
                        } else {
                            Toast.makeText(SplashActivity.this, "No Internet Connection", Toast.LENGTH_SHORT).show();

                        }

                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocationPermission() {

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(mContext, Manifest.permission.ACCESS_COARSE_LOCATION)
                != PackageManager.PERMISSION_GRANTED) {
            if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_FINE_LOCATION)) {
                requestPermissions(

                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 101);
             } else {
                requestPermissions(
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        101);
             }
        } else {
         }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 101) {
            isLocationEnabled();
        } else if (requestCode == GlobalValues.REQUEST_WRITE_PERMISSION) {
            checkLocationPermission();
        }
    }

    private void isLocationEnabled() {
         if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(mContext, R.style.AlertDialogCustom);
            alertDialog.setTitle("Enable Location");
            alertDialog.setMessage("Your locations setting is not enabled. Please enabled it in settings menu.");
            alertDialog.setPositiveButton("Location Settings", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                    startActivityForResult(intent, 100);
                }
            });
            alertDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            AlertDialog alert = alertDialog.create();
            alert.show();
        } else {
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


    public void goDirectlyToOutletOrProducts() {
        if (isCustomerLoggedIn()) {
            if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                    || Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                intent = new Intent(mContext, ChooseOutletActivity.class);
                startActivity(intent);
                finish();
                return;
            } else {
//                intent = new Intent(mContext, SubCategoryActivity.class);
                intent = new Intent(mContext, MenuCategoryActivity.class);
                startActivity(intent);
                finish();
            }
        }
//        if (landinglist != null) {
//            if (landinglist.get(0).getLandingType().equalsIgnoreCase("1")) {
//                if (Patterns.WEB_URL.matcher(landinglist.get(0).getLandingSlug()).matches()) {
//                    intent = new Intent(mContext, CmsActivity.class);
//                    intent.putExtra("TITLE", landinglist.get(0).getLandingTitle());
//                    intent.putExtra("SEARCH_KEY", landinglist.get(0).getLandingSlug());
//                    startActivity(intent);
//                    finish();
//                }
//            } else if (landinglist.get(0).getLandingType().equalsIgnoreCase("2")) {
//                final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
//                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//                dialog.setContentView(R.layout.dialog_landing_description);
//                dialog.show();
//
//                TextView txt_title = dialog.findViewById(R.id.txt_title);
//                TextView txt_description = dialog.findViewById(R.id.txt_description);
//                ImageView img_exit = dialog.findViewById(R.id.img_exit);
//
//                txt_title.setText(landinglist.get(0).getLandingTitle());
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//                    txt_description.setText(Html.fromHtml(landinglist.get(0).getLandingDescription(), Html.FROM_HTML_MODE_COMPACT));
//                } else {
//                    txt_description.setText(Html.fromHtml(landinglist.get(0).getLandingDescription()));
//                }
//
//                img_exit.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View v) {
//                        dialog.dismiss();
//                    }
//                });
//
//            } else if (landinglist.get(0).getLandingType().equalsIgnoreCase("3")) {
//                if (Patterns.WEB_URL.matcher(landinglist.get(0).getLandingSlug()).matches()) {
//                    intent = new Intent(mContext, CmsActivity.class);
//                    intent.putExtra("TITLE", landinglist.get(0).getLandingTitle());
//                    intent.putExtra("SEARCH_KEY", landinglist.get(0).getLandingSlug());
//                    startActivity(intent);
//                    finish();
//                } else {
//                    intent = new Intent(mContext, SubCategoryActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        }
//        }else {
//            intent = new Intent(mContext, LoginActivity.class);
//            //intent.putExtra("disableLoginLater", true);
//            startActivity(intent);
//        }
    }

    class LandingRequestTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        public LandingRequestTask() {

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
                    JSONObject commonJSON = jsonObject.getJSONObject("common");
                    landinglist = new ArrayList<>();
//                    commonURL = commonJSON.getString("image_source");
                    JSONArray resultSetJSONArray = jsonObject.getJSONArray("result_set");
                    /*txt_OrderNow.setVisibility(View.VISIBLE);
                    txt_myAcc.setVisibility(View.VISIBLE);
                    txt_promotion.setVisibility(View.VISIBLE);
                    txt_about.setVisibility(View.VISIBLE);*/
                    if (resultSetJSONArray.length() > 0) {
                        for (int i = 0; i < resultSetJSONArray.length(); i++) {
                            JSONObject landingJSON = resultSetJSONArray.getJSONObject(i);
                            ResultSetItem resultSetItem = new ResultSetItem();
                            resultSetItem.setLandingId(landingJSON.getString("landing_id"));
                            resultSetItem.setLandingTitle(landingJSON.getString("landing_title"));
                            resultSetItem.setLandingType(landingJSON.getString("landing_type"));
                            resultSetItem.setLandingSlug(landingJSON.getString("landing_slug"));
//                            resultSetItem.setLandingImage(commonURL + landingJSON.getString("landing_image"));
                            resultSetItem.setLandingSortOrder(landingJSON.getString("landing_sort_order"));
                            resultSetItem.setLandingDescription(landingJSON.getString("landing_description"));
                            resultSetItem.setLandingShortDescription(landingJSON.getString("landing_short_description"));
                            resultSetItem.setType(landingJSON.getString("type"));

                            landinglist.add(resultSetItem);
                        }

//                        for (int j = 0; j < resultSetJSONArray.length(); j++) {
//                            if (landinglist.get(j).getType().equalsIgnoreCase("image")) {
//                                int cornerRadius;
//                                if (landinglist.get(j).getLandingImage().contains("gif")) {
//                                    cornerRadius = 3;
//                                } else {
//                                    cornerRadius = 10;
//                                }
//                                if (j == 0) {
//                                    if (landinglist.get(0).getType().equalsIgnoreCase("image")) {
//                                        img_orderNow.setVisibility(View.VISIBLE);
//                                        vdo_orderNow.setVisibility(View.GONE);
//                                        Glide.with(mContext)
//                                                .load(landinglist.get(0).getLandingImage())
//                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
//                                                .into(img_orderNow);
//                                    } else {
//                                        img_orderNow.setVisibility(View.GONE);
//                                        vdo_orderNow.setVisibility(View.VISIBLE);
//                                        vdo_orderNow.setVideoPath(landinglist.get(0).getLandingImage());
//                                        vdo_orderNow.requestFocus();
//                                        vdo_orderNow.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                            @Override
//                                            public void onPrepared(MediaPlayer mp) {
//                                                mp.setLooping(true);
//                                            }
//                                        });
//                                        vdo_orderNow.start();
//                                    }
//                                }
//
//                                if (j == 1) {
//                                    if (landinglist.get(1).getType().equalsIgnoreCase("image")) {
//                                        img_myAcc.setVisibility(View.VISIBLE);
//                                        vdo_myAcc.setVisibility(View.GONE);
//                                        Glide.with(mContext)
//                                                .load(landinglist.get(1).getLandingImage())
//                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
//                                                .into(img_myAcc);
//                                    } else {
//                                        img_myAcc.setVisibility(View.GONE);
//                                        vdo_myAcc.setVisibility(View.VISIBLE);
//                                        vdo_myAcc.setVideoPath(landinglist.get(1).getLandingImage());
//                                        vdo_myAcc.requestFocus();
//                                        vdo_myAcc.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                            @Override
//                                            public void onPrepared(MediaPlayer mp) {
//                                                mp.setLooping(true);
//                                            }
//                                        });
//                                        vdo_myAcc.start();
//                                    }
//                                }
//
//                                if (j == 2) {
//                                    if (landinglist.get(2).getType().equalsIgnoreCase("image")) {
//                                        img_promotion.setVisibility(View.VISIBLE);
//                                        vdo_promotion.setVisibility(View.GONE);
//                                        Glide.with(mContext)
//                                                .load(landinglist.get(2).getLandingImage())
//                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
//                                                .into(img_promotion);
//                                    } else {
//                                        img_promotion.setVisibility(View.GONE);
//                                        vdo_promotion.setVisibility(View.VISIBLE);
//                                        vdo_promotion.setVideoPath(landinglist.get(2).getLandingImage());
//                                        vdo_promotion.requestFocus();
//                                        vdo_promotion.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                            @Override
//                                            public void onPrepared(MediaPlayer mp) {
//                                                mp.setLooping(true);
//                                            }
//                                        });
//                                        vdo_promotion.start();
//                                    }
//                                }
//
//                                if (j == 3) {
//                                    if (landinglist.get(3).getType().equalsIgnoreCase("image")) {
//                                        img_about.setVisibility(View.VISIBLE);
//                                        vdo_about.setVisibility(View.GONE);
//                                        Glide.with(mContext)
//                                                .load(landinglist.get(3).getLandingImage())
//                                                .apply(MyGlideExtension.roundedCorners(new RequestOptions(), mContext, cornerRadius))
//                                                .into(img_about);
//                                    } else {
//                                        img_about.setVisibility(View.GONE);
//                                        vdo_about.setVisibility(View.VISIBLE);
//                                        vdo_about.setVideoPath(landinglist.get(3).getLandingImage());
//                                        vdo_about.requestFocus();
//                                        vdo_about.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
//                                            @Override
//                                            public void onPrepared(MediaPlayer mp) {
//                                                mp.setLooping(true);
//                                            }
//                                        });
//                                        vdo_about.start();
//                                    }
//                                }
//                            }
//                        }
                    } else {
                        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    public void setupSlide() {
        SliderLayout mDemoSlider;
        mDemoSlider = findViewById(R.id.slider);

        for (int i = 0; i < 3; i++) {

            DefaultSliderView textSliderView = new DefaultSliderView(SplashActivity.this);

            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    // hide 25_01_2022  .image("https://ccpl.ninjaos.com/media/dev_team/products/main-image/7e2cf4acd7889c69dcb203bae4bcf9fb.png")
                    .image(R.drawable.splash_content_img)
                    .setScaleType(BaseSliderView.ScaleType.Fit)

                    .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                        @Override
                        public void onSliderClick(BaseSliderView slider) {

                        }
                    });

            //add your extra information
            textSliderView.bundle(new Bundle());
            mDemoSlider.addSlider(textSliderView);
        }

        // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
        // mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(0xff497236, R.color.colorWhite);
//         mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(R.color.com_facebook_blue, R.color.graydark);
        //mDemoSlider.setDuration(5000);
        mDemoSlider.stopAutoCycle();
        mDemoSlider.addOnPageChangeListener(new ViewPagerEx.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            }

            @Override
            public void onPageSelected(int position) {
            }

            @Override
            public void onPageScrollStateChanged(int state) {
            }

        });
    }

    private boolean isCustomerLoggedIn() {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void setUpSlider() {
        sliderBannerAdapterList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            BannerModel bm = new BannerModel();
            bm.setImage(R.drawable.splash_content_img);
            sliderBannerAdapterList.add(bm);
        }
        sliderBannerAdapter = new SliderBannerAdapter(1, sliderBannerAdapterList, mContext);
        viewPager.setAdapter(sliderBannerAdapter);
        dots = new ImageView[sliderBannerAdapterList.size()];
        for (int j = 0; j < sliderBannerAdapterList.size(); j++) {
            dots[j] = new ImageView(SplashActivity.this);
            dots[j].setImageDrawable(ContextCompat.getDrawable(SplashActivity.this, R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            SliderDots.addView(dots[j], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(SplashActivity.this, R.drawable.active_dot));
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                for (int k = 0; k < sliderBannerAdapterList.size(); k++) {
                    dots[k].setImageDrawable(ContextCompat.getDrawable(SplashActivity.this, R.drawable.non_active_dot));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(SplashActivity.this, R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }
}
