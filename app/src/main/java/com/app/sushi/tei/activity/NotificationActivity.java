package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.NavigateMenu;
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.NavigationAdapter;
import com.app.sushi.tei.dialog.AlertDialog;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.MessageDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.sushi.tei.activity.SubCategoryActivity.resultSet;

public class NotificationActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private LinearLayout toolbarBack;
    private ImageView heartblink_imageview;
    private View moveIcon;
    public MenuItem cartWBadge;
    public MenuItem menuSearch;
    public View viewBadge;
    private RelativeLayout layoutCart, layoutSearch;
    private TextView txtCartCount;
    private String cartCount = "";
    private Double totalAmtPrice = 0.0;
    private String mCustomerId = "", mReferenceId = "";
    private ArrayList<Favouriteitems> favouriteitemsArrayList = new ArrayList<>();
    private LinearLayout imgBack;
    private DrawerLayout drawerLayout;
    private RelativeLayout navigationView;
    private View content;
    private static final float END_SCALE = 0.9f;
    private Intent intent;
    private RelativeLayout layoutHome, layoutAccount, layoutViewOrder, layoutRewards,
            layoutPromotions, layoutVouchers, layoutNotification, layoutAbout, layoutSettings;
    private RelativeLayout aboutBackLayout, submenuTopLayout;
    private RelativeLayout mainMenuLayout, subMenuLayout;
    private RecyclerView subRecyclerView;
    private int itemId;
    public TextView txtNotificationCount, txtOrderCount, txtPromotionCount, txtVoucherCount;
    public LinearLayout layoutSignIn, layoutSignOut;
    public TextView txtSignedUsername, txtModifierPrice;
    private DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);

        context = this;
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        toolbarBack = findViewById(R.id.toolbarBack);
        databaseHandler = DatabaseHandler.getInstance(context);
        moveIcon = toolbar.findViewById(R.id.moveIcon);
        imgBack = toolbar.findViewById(R.id.toolbarBack);
        heartblink_imageview = toolbar.findViewById(R.id.heartblink_imageview);
        layoutHome = findViewById(R.id.layoutHome);
        layoutAccount = findViewById(R.id.layoutAccount);
        layoutViewOrder = findViewById(R.id.layoutViewOrder);
        layoutRewards = findViewById(R.id.layoutRewards);
        layoutVouchers = findViewById(R.id.layoutVouchers);
        layoutPromotions = findViewById(R.id.layoutPromotions);
        layoutNotification = findViewById(R.id.layoutNotification);
        layoutAbout = findViewById(R.id.layoutAbout);
        layoutSettings = findViewById(R.id.layoutSettings);
        aboutBackLayout = findViewById(R.id.aboutBackLayout);
        submenuTopLayout = findViewById(R.id.submenuTopLayout);
        mainMenuLayout = findViewById(R.id.mainMenuLayout);
        subMenuLayout = findViewById(R.id.subMenuLayout);
        subRecyclerView = findViewById(R.id.subRecyclerView);
        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignOut = findViewById(R.id.layoutSignOut);
        txtSignedUsername = findViewById(R.id.txtSignedUsername);
        txtNotificationCount = findViewById(R.id.txtNotificationCount);
        txtPromotionCount = findViewById(R.id.txtPromotionCount);
        txtVoucherCount = findViewById(R.id.txtVoucherCount);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        content = findViewById(R.id.content);

        heartblink_imageview.setVisibility(View.INVISIBLE);
        moveIcon.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);

        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_right_arrow, getTheme());
/*        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 50, 50, true));*/
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(drawable);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(navigationView)) {
                    drawerLayout.closeDrawer(navigationView);
                } else {
                    drawerLayout.openDrawer(navigationView);
                }
            }
        });

        drawerLayout.setScrimColor(Color.TRANSPARENT);
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawer, float slideOffset) {
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                content.setScaleX(offsetScale);
                content.setScaleY(offsetScale);
                // Translate the View, accounting for the scaled width
                final float xOffset = drawer.getWidth() * slideOffset;
                final float xOffsetDiff = drawer.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                content.setTranslationX(xTranslation);

            }

            @Override
            public void onDrawerClosed(View drawerView) {

                if (!(Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT).equals(""))) {

                }
            }
        });

        if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {

            layoutSignIn.setVisibility(View.GONE);
            txtSignedUsername.setVisibility(View.GONE);
             txtSignedUsername.setText("Signed in as " + Utility.readFromSharedPreference(context, GlobalValues.EMAIL));
            layoutSignOut.setVisibility(View.VISIBLE);

        } else {

            layoutSignIn.setVisibility(View.VISIBLE);
            txtSignedUsername.setVisibility(View.GONE);
            layoutSignOut.setVisibility(View.GONE);
        }

        layoutSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
                Utility.writeToSharedPreference(context, GlobalValues.OPENLOGIN, "Close");
                intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        layoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog(context, new AlertDialog.OnSlectedString() {
                    @Override
                    public void selectedAction(String action) {

                        if (action.equalsIgnoreCase("Ok")) {
                            drawerLayout.closeDrawers();
                            layoutSignIn.setVisibility(View.VISIBLE);
                            txtSignedUsername.setVisibility(View.GONE);
                            layoutSignOut.setVisibility(View.GONE);

                            txtOrderCount.setVisibility(View.GONE);
                            txtPromotionCount.setVisibility(View.GONE);
                            txtVoucherCount.setVisibility(View.GONE);
                            txtNotificationCount.setVisibility(View.GONE);
                            clearCart();

                            Utility.removeFromSharedPreference(context, GlobalValues.CUSTOMERID);
                            Utility.removeFromSharedPreference(context, GlobalValues.FIRSTNAME);
                            Utility.removeFromSharedPreference(context, GlobalValues.LASTNAME);
                            Utility.removeFromSharedPreference(context, GlobalValues.EMAIL);
                            Utility.removeFromSharedPreference(context, GlobalValues.CUSTOMERPHONE);
                            Utility.removeFromSharedPreference(context, GlobalValues.POSTALCODE);
                            Utility.removeFromSharedPreference(context, GlobalValues.CART_COUNT);
                            Utility.removeFromSharedPreference(context, GlobalValues.BENTO_CART_COUNT);
                            Utility.removeFromSharedPreference(context, GlobalValues.BENTO_CART_COUNT);
                            Utility.removeFromSharedPreference(context, GlobalValues.PROMOTIONCOUNT);
                            Utility.removeFromSharedPreference(context,GlobalValues.MEMBERSHIP_ID);
                            Utility.removeFromSharedPreference(context,GlobalValues.PACKING_CHARGE);

                            try {
                                databaseHandler.deleteAllCartQuantity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            invalidateOptionsMenu();

                            intent = new Intent(context, LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
            }
        });

        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                        || Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                    intent = new Intent(context, ChooseOutletActivity.class);
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    intent = new Intent(context, MenuCategoryActivity.class);
                    startActivity(intent);
                    finish();
                }
                drawerLayout.closeDrawers();
            }
        });


        layoutPromotions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
                openFiveMenuActivity(4);

            }
        });

        layoutVouchers.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
                openFiveMenuActivity(3);

            }
        });


        layoutViewOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
                openFiveMenuActivity(1);
            }
        });


        layoutAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
                openFiveMenuActivity(0);

            }
        });

        layoutRewards.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();


                openFiveMenuActivity(2);
            }
        });

        layoutNotification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();


                if (!isCustomerLoggedIn()) {

                    new MessageDialog(context, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                }
            }
        });

        submenuTopLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aboutBackLayout.performClick();
            }
        });

        aboutBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuLayout.setVisibility(View.VISIBLE);
                subMenuLayout.setVisibility(View.GONE);
            }
        });

        layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getAboutUs();
            }
        });

        layoutSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawer(GravityCompat.START);

                if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID) != null &&
                        Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
                    Intent i = new Intent(context, SettingsActivity.class);
                    startActivity(i);
                } else {

                    new MessageDialog(context, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent myAccountIntent = new Intent(context, LoginActivity.class);
                                myAccountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(myAccountIntent);
                                finish();
                            }
                        }
                    });

                }
            }
        });

        layoutSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                drawerLayout.closeDrawers();
                Utility.writeToSharedPreference(context, GlobalValues.OPENLOGIN, "Close");
                intent = new Intent(context, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });
    }

    private void openFiveMenuActivity(int position) {


        if (!isCustomerLoggedIn()) {

            new MessageDialog(context, new MessageDialog.OnSlectedString() {
                @Override
                public void selectedAction(String action) {

                    if (action.equalsIgnoreCase("Ok")) {
                        intent = new Intent(context, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        } else {
            intent = new Intent(context, FiveMenuActivityNew.class);
            intent.putExtra("position", position);
            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
            startActivity(intent);
        }
    }

    private void getAboutUs() {

       /* String app_id = "?app_id=" + GlobalValues.APP_ID;
        String slug = "&menu_slug=Main-menu";
        String menuUrl = GlobalUrl.MENU_URL + app_id + slug;*/
        String app_id = "?app_id=" + GlobalValues.APP_ID;
        String slug = "&menu_slug=mobile-app-menu";
        String menuUrl = GlobalUrl.MENU_URL + app_id + slug;
//        if (Utility.networkCheck(context)) {
//            new MenuTask().execute(menuUrl);
//        } else {
//            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//        }
        intent = new Intent(context, CmsActivity.class);
        intent.putExtra("TITLE", "About Us");
        intent.putExtra("SEARCH_KEY", "https://sushitei.com/about-us/");
        intent.putExtra("landingPage", true);
        startActivity(intent);
    }

    private class MenuTask extends AsyncTask<String, String, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
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
        public boolean equals(Object obj) {
            return super.equals(obj);
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);


            if (s != null) {


                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(s);

                    if (responseJSONObject.getString("status").equals("ok")) {    //Success

                        JSONArray resultSetJSONArray = responseJSONObject.getJSONArray("result_set");
                        ArrayList<NavigateMenu> navigateMenuArrayList = new ArrayList<>();

                        for (int i = 0; i < resultSetJSONArray.length(); i++) {

                            JSONObject jsonObject = resultSetJSONArray.getJSONObject(i);

                            String nav_pages_mobile = null;
                            if (jsonObject.has("nav_pages_mobiles"))
                                nav_pages_mobile = jsonObject.getString("nav_pages_mobiles");

                            NavigateMenu navigateMenu = new NavigateMenu();
                            navigateMenu.setNav_id(jsonObject.getString("nav_id"));
                            navigateMenu.setNav_group(jsonObject.getString("nav_group"));
                            navigateMenu.setNav_title(jsonObject.getString("nav_title"));
                            navigateMenu.setNav_parent_title(jsonObject.getString("nav_parent_title"));
                            navigateMenu.setNav_title_slug(jsonObject.getString("nav_title_slug"));
                            navigateMenu.setNav_type(jsonObject.getString("nav_type"));
                            navigateMenu.setNav_pages(jsonObject.getString("nav_pages"));
                            navigateMenu.setNav_pages_mobile(nav_pages_mobile);

                            navigateMenu.setNav_category(jsonObject.getString("nav_category"));
                            navigateMenu.setNav_subcategory(jsonObject.getString("nav_subcategory"));
                            navigateMenu.setNav_icon(jsonObject.getString("nav_icon"));
                            navigateMenu.setNav_company_id(jsonObject.getString("nav_company_id"));
                            navigateMenu.setNav_app_id(jsonObject.getString("nav_app_id"));
                            navigateMenu.setNav_link_type(jsonObject.getString("nav_link_type"));
                            navigateMenu.setNav_status(jsonObject.getString("nav_status"));
                            navigateMenu.setNav_created_on(jsonObject.getString("nav_created_on"));
                            navigateMenu.setNav_updated_on(jsonObject.getString("nav_updated_on"));

                            navigateMenuArrayList.add(navigateMenu);

//                            for (NavigateMenu remove : navigateMenuArrayList) {
//                                if (remove.getNav_title().contains("Gallery") || remove.getNav_title().contains("News and events")) {
//                                    navigateMenuArrayList.remove(remove.getNav_title());
//                                }


                            NavigationAdapter navigationAdapter = new NavigationAdapter(NotificationActivity.this, navigateMenuArrayList);

                            subRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                            subRecyclerView.setAdapter(navigationAdapter);

                            mainMenuLayout.setVisibility(View.GONE);
                            subMenuLayout.setVisibility(View.VISIBLE);


                        }
                    } else {
                        Toast.makeText(context, responseJSONObject.getString("message"), Toast.LENGTH_LONG).show();
                    }

                } catch (Exception e) {
                    e.printStackTrace();


                } finally {
                    progressDialog.dismiss();
                }


            }
        }

    }


    @Override
    protected void onResume() {
        super.onResume();

        Utility.writeToSharedPreference(context, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(context, "OREO_UPDATE","1");
        }

        supportInvalidateOptionsMenu();
        invalidateOptionsMenu();
        getTotalAmount();
        getActiveCount();
        //CheckFacourites();

        if (!(Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0)) {
            txtOrderCount.setVisibility(View.GONE);
            txtPromotionCount.setVisibility(View.GONE);
            txtNotificationCount.setVisibility(View.GONE);
            txtVoucherCount.setVisibility(View.GONE);
        }

        if (Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE).length() > 0) {

            totalAmtPrice = Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE));
            //totalAmt.setText(String.valueOf(totalAmtPrice));
        } else {
           // totalAmt.setText("0");
        }


    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(context, "IS_ACTIVE","0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(context, "OREO_UPDATE","1");
        }
    }

    private void clearCart() {
        String url1 = GlobalUrl.DESTROY_CART_URL;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));
        params.put("reference_id", "");

        if (Utility.networkCheck(context)) {

            new DestroyCartTask(params).execute(url1);
        }
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

            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {


            String response = WebserviceAssessor.postRequest(context, params[0], destroyParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject destroyJson = new JSONObject(s);

                if (destroyJson.getString("status").equalsIgnoreCase("ok")) {
                    Utility.removeFromSharedPreference(context, GlobalValues.CART_COUNT);
                    Utility.removeFromSharedPreference(context, GlobalValues.CART_RESPONSE);
                    Utility.removeFromSharedPreference(context, GlobalValues.BENTO_CART_COUNT);


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


            }
        }
    }

    public void getTotalAmount() {
        if (Utility.networkCheck(context)) {
            if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
                mCustomerId = Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID);
                mReferenceId = "";
            } else {
                mCustomerId = "";
                mReferenceId = Utility.getReferenceId(context);
            }

            GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID);

            String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + mCustomerId +
                    "&reference_id=" + "" +
                    "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;


            if (!GlobalValues.DISCOUNT_APPLIED) {

                new CartListTask().execute(url);
            } else {
                new CartListTask().execute(url);


            }

        } else {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private class CartListTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
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


                    if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
                        Double mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));


                        //totalAmt.setText(String.format("%.2f", mGrandTotal));


                    } else {

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private void getActiveCount() {

        if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
            //Promotion count and Reawrd points
            Map<String, String> mapData = new HashMap<>();
            mapData.put("app_id", GlobalValues.APP_ID);

            if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
                mapData.put("customer_id", Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID));

            } else {
                mapData.put("customer_id", Utility.getReferenceId(context));
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
                    Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID) +
                    "&act_arr=" + jsonArray.toString();

            new ActivCountTask(mapData).execute(url);

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

            progressDialog = new ProgressDialog(context);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        try {
            cartWBadge = menu.findItem(R.id.menu_cart);
            menuSearch = menu.findItem(R.id.menu_search);

            viewBadge = menu.findItem(R.id.menu_cart).getActionView();
            View searchwBadge = menu.findItem(R.id.menu_search).getActionView();

            layoutCart = (RelativeLayout) viewBadge.findViewById(R.id.layoutCart);
            txtCartCount = (TextView) viewBadge.findViewById(R.id.txtCartCount);

            layoutSearch = searchwBadge.findViewById(R.id.layoutSearch);

            cartCount = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);

            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

                txtCartCount.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);

            } else {

                txtCartCount.setVisibility(View.GONE);
            }

            layoutCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        String MinQual = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
                        if (!MinQual.equals("")) {
                            if (Integer.parseInt(MinQual) >= 1) {

                                if (Integer.parseInt(cartCount) != 0) {
                                    String message = "Do you want to add more items!";
                                    new CheckOutMessageDialog(context, message, new CheckOutMessageDialog.OnSlectedMethod() {
                                        @Override
                                        public void selectedAction(String action) {

                                            if (action.equalsIgnoreCase("YES")) {
                                                Intent intent = new Intent(context, SubCategoryActivity.class);
                                                startActivity(intent);
                                            } else {
//                                                Intent intent = new Intent(context, CartActivity.class);
                                                Intent intent = new Intent(context, OrderSummaryActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        }else{
                            Toast.makeText(context, "Cart is empty. Go to ‘Order Now’ to add products!", Toast.LENGTH_SHORT).show();
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            });

            layoutSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if (!isCustomerLoggedIn()) {

                        new MessageDialog(context, new MessageDialog.OnSlectedString() {
                            @Override
                            public void selectedAction(String action) {

                                if (action.equalsIgnoreCase("Ok")) {
                                    Intent intent = new Intent(context, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }

                            }
                        });
                    } else {

                        if (Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID).length() > 0) {

                            if (resultSet) {
                                Intent intent = new Intent(context, SearchActivity.class);
                                startActivity(intent);
                            }
                        } else {
                            Toast.makeText(context, "Please select your outlet!", Toast.LENGTH_SHORT).show();
                        }


                    }

                }
            });


            cartWBadge.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onPrepareOptionsMenu(menu);
    }

    private boolean isCustomerLoggedIn() {


        if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {

            return true;
        } else {
            Utility.writeToSharedPreference(context, GlobalValues.OPENLOGIN, "Close");
            return false;
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(navigationView)) {
            drawerLayout.closeDrawer(navigationView);
        } else {
            drawerLayout.openDrawer(navigationView);
        }
    }
}
