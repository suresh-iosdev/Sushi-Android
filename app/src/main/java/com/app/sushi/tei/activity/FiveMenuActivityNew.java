package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import com.google.android.material.bottomnavigation.BottomNavigationItemView;
import com.google.android.material.bottomnavigation.BottomNavigationMenuView;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.core.app.NotificationManagerCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
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
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.NavigationAdapter;
import com.app.sushi.tei.dialog.AlertDialog;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.MessageDialog;
import com.app.sushi.tei.fragment.FiveMenu.FiveMenuOrderFragment;
import com.app.sushi.tei.fragment.FiveMenu.FragmentReward;
import com.app.sushi.tei.fragment.FiveMenu.FragmentVoucher;
import com.app.sushi.tei.fragment.FiveMenu.MyAccountFragmentNew;
import com.app.sushi.tei.fragment.FiveMenu.PromotionFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.app.sushi.tei.activity.SubCategoryActivity.cart_sub_total;
import static com.app.sushi.tei.activity.SubCategoryActivity.resultSet;

public class FiveMenuActivityNew extends AppCompatActivity {
    private BottomNavigationView navigation;
    private Context context;
    public static String frgagment_tag = "";
    private LinearLayout indicator;
    private ImageView[] dots;
    private TabLayout tabLayoutFiveMenu;
    private View view1, view2, view3, view4, view5;
    private int mTabPosition, mFrom;
    private DatabaseHandler databaseHandler;
    private Toolbar toolbar;
    private LinearLayout imgBack;
    private View moveIcon;
    private ImageView heartblink_imageview;
    private NotificationManagerCompat notificationManagerCompat;
    public MenuItem cartWBadge;
    public MenuItem menuSearch;
    public View viewBadge;
    private RelativeLayout layoutCart, layoutSearch;
    private TextView txtCartCount, txtCardName;
    private String cartCount = "";
    private DrawerLayout drawerLayout;
    private RelativeLayout navigationView;
    private View content;
    private static final float END_SCALE = 0.9f;
    private Intent intent;
    private RelativeLayout layoutHome, layoutAccount, layoutViewOrder, layoutRewards, layoutVouchers,
            layoutPromotions, layoutNotification, layoutAbout, layoutSettings
            ,layout_terms,layout_refund,layout_privacy,layout_contact;
    private RelativeLayout aboutBackLayout, submenuTopLayout;
    private RelativeLayout mainMenuLayout, subMenuLayout;
    private RecyclerView subRecyclerView;
    private int itemId;
    public TextView txtNotificationCount, txtOrderCount, txtPromotionCount, txtVoucherCount;
    public LinearLayout layoutSignIn, layoutSignOut;
    public TextView txtSignedUsername, txtModifierPrice;
    private LinearLayout llyView;
    private View view;
    private boolean stackBackup = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fivemenu_new);
        intiView();

        try {
            mTabPosition = getIntent().getIntExtra("position", 0);
            mFrom = getIntent().getIntExtra(GlobalValues.FROM_KEY, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            stackBackup = getIntent().getBooleanExtra("stackBackup", false);
        } catch (Exception e) {
            e.printStackTrace();
        }

        navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        itemId = R.id.navigation_account;
        if (mTabPosition == 0) {
            itemId = R.id.navigation_account;
        } else if (mTabPosition == 1) {
            itemId = R.id.navigation_orders;
        } else if (mTabPosition == 2) {
            itemId = R.id.navigation_rewards;
        } else if (mTabPosition == 3) {
            itemId = R.id.navigation_voucher;
        } else if (mTabPosition == 4) {
            itemId = R.id.navigation_promotions;
        }
        navigation.setSelectedItemId(itemId);
    }

    private void intiView() {
        context = this;
        databaseHandler = DatabaseHandler.getInstance(context);
        view1 = findViewById(R.id.view1);
        view2 = findViewById(R.id.view2);
        view3 = findViewById(R.id.view3);
        view4 = findViewById(R.id.view4);
        view5 = findViewById(R.id.view5);
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        imgBack = toolbar.findViewById(R.id.toolbarBack);
        moveIcon = toolbar.findViewById(R.id.moveIcon);
        heartblink_imageview = toolbar.findViewById(R.id.heartblink_imageview);
        layoutHome = findViewById(R.id.layoutHome);
        layoutAccount = findViewById(R.id.layoutAccount);
        layoutViewOrder = findViewById(R.id.layoutViewOrder);
        layoutRewards = findViewById(R.id.layoutRewards);
        layoutVouchers = findViewById(R.id.layoutVouchers);
        layoutPromotions = findViewById(R.id.layoutPromotions);
        layoutNotification = findViewById(R.id.layoutNotification);
        layoutAbout = findViewById(R.id.layoutAbout);
        layout_terms = findViewById(R.id.layout_terms);
        layout_refund = findViewById(R.id.layout_refund);
        layout_privacy = findViewById(R.id.layout_privacy);
        layout_contact=findViewById(R.id.layout_contact);
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
        view = findViewById(R.id.view);
        llyView = findViewById(R.id.lly_view);

        heartblink_imageview.setVisibility(View.INVISIBLE);
        moveIcon.setVisibility(View.GONE);
        imgBack.setVisibility(View.GONE);
        notificationManagerCompat = NotificationManagerCompat.from(getApplicationContext());
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        content = findViewById(R.id.content);
        
        if (Utility.networkCheck(context)) {
            String app_id = "?app_id=" + GlobalValues.APP_ID;
            String url = GlobalUrl.SETTINGS_URL + app_id;
            Log.e("TAG","CoomonTest::"+url);
            new SettingsRequest().execute(url);

        } else {
            Toast.makeText(context,"You are offline please check your internet connection",Toast.LENGTH_SHORT).show();
        }
        getActiveCount();
        Log.e("TAG","MemberIdTest::"+Utility.readFromSharedPreference(context,GlobalValues.MEMBERSHIP_ID));
        if (!Utility.readFromSharedPreference(context,GlobalValues.MEMBERSHIP_ID).isEmpty()) {
            Map<String, String> params = new HashMap<>();
            params.put("app_id", GlobalValues.APP_ID);
            params.put("member_id", Utility.readFromSharedPreference(context, GlobalValues.MEMBERSHIP_ID));
            String reward_url = GlobalUrl.MEMBER_ENQUIRY_URL;

            new MemberRewards(params).execute(reward_url);
        }


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

                navigation.setVisibility(View.GONE);
                llyView.setVisibility(View.GONE);
                view.setVisibility(View.GONE);
            }

            @Override
            public void onDrawerClosed(View drawerView) {

                navigation.setVisibility(View.VISIBLE);
                llyView.setVisibility(View.VISIBLE);
                view.setVisibility(View.VISIBLE);
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
                            Utility.removeFromSharedPreference(context, GlobalValues.MEMBERSHIP_ID);
                            Utility.removeFromSharedPreference(context, GlobalValues.ASCENTIS_CARD_NO);
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
//                Intent intent = new Intent(context, LandingActivity.class);
//                startActivity(intent);

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

                } else {
                    Intent intent = new Intent(context, NotificationActivity.class);
                    startActivity(intent);
                }
               /* if(layoutSignOut.getVisibility() == View.VISIBLE){
                    layoutSignOut.performClick();
                }else{
                    layoutSignIn.performClick();
                }*/
                //openFiveMenuActivity(4);
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
                Log.e("TAG","CmspageTest00::");
                getAboutUs();
            }
        });

        layout_terms.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.e("TAG","CmspageTest::");
                tabnewadded("https://sushitei.com/terms-and-conditions/");
            }
        });

        layout_refund.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabnewadded("https://sushitei.com/terms-and-conditions/");
            }
        });

        layout_privacy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabnewadded("https://sushitei.com/privacy-policy/");
            }
        });

        layout_contact.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tabnewadded("https://sushitei.com/contact-us/");
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
                    Intent myAccountIntent = new Intent(context, LoginActivity.class);
                    myAccountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myAccountIntent);
                    finish();
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

    private void tabnewadded(String searchkey) {
        intent = new Intent(context, CmsActivity.class);
        intent.putExtra("TITLE", "Terms_and_extra");
        intent.putExtra("SEARCH_KEY", searchkey);
        intent.putExtra("landingPage", true);
        startActivity(intent);
    }


    private void getAboutUs() {
        /*String app_id = "?app_id=" + GlobalValues.APP_ID;
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

                            NavigationAdapter navigationAdapter = new NavigationAdapter(FiveMenuActivityNew.this, navigateMenuArrayList);

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


            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID) +
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

            ;
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
                    GlobalValues.PROMOTIONCOUNT = countJson.optString("promotionwithoutuqc");
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");
                  /*  double x_reedempoints = countJson.optDouble("reward_ponits");

                    GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;
                    if (!countJson.isNull("reward_ponits_monthly")) {

                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = Double.valueOf(countJson.optString("reward_ponits_monthly"));

                    } else {
                        GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = 0.00;
                    }*/

                    if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {
                        txtOrderCount.setVisibility(View.VISIBLE);
                        txtOrderCount.setText(GlobalValues.ORDERCOUNT);
                    } else {
                        txtOrderCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {

                        txtPromotionCount.setVisibility(View.VISIBLE);
                        txtPromotionCount.setText(GlobalValues.PROMOTIONCOUNT);

                    } else {
                        txtPromotionCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {

                        txtVoucherCount.setVisibility(View.VISIBLE);
                        txtVoucherCount.setText(GlobalValues.VOUCHERCOUNT);

                    } else {
                        txtVoucherCount.setVisibility(View.GONE);
                    }

                    if (GlobalValues.NOTIFYCOUNT != null && !GlobalValues.NOTIFYCOUNT.equals("0") && !GlobalValues.NOTIFYCOUNT.equalsIgnoreCase("")) {

                        txtNotificationCount.setVisibility(View.VISIBLE);
                        txtNotificationCount.setText(GlobalValues.NOTIFYCOUNT);
                    } else {
                        txtNotificationCount.setVisibility(View.GONE);
                    }

                    setTabItem();

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

    private void setTabItem() {
        for (int k = 0; k < 5; k++) {
            if (k == 1) {
                if (GlobalValues.ORDERCOUNT != null && !GlobalValues.ORDERCOUNT.equals("0") && !GlobalValues.ORDERCOUNT.equalsIgnoreCase("")) {

                    if (Integer.parseInt(GlobalValues.ORDERCOUNT) > 99) {
                        setBadge(1, "99+");
                    } else {
                        setBadge(1, GlobalValues.ORDERCOUNT);
                    }
                }
            } else if (k == 3) {
                if (GlobalValues.VOUCHERCOUNT != null && !GlobalValues.VOUCHERCOUNT.equals("0") && !GlobalValues.VOUCHERCOUNT.equalsIgnoreCase("")) {
                    setBadge(3, GlobalValues.VOUCHERCOUNT);
                }
            } else if (k == 4) {
                if (GlobalValues.PROMOTIONCOUNT != null && !GlobalValues.PROMOTIONCOUNT.equals("0") && !GlobalValues.PROMOTIONCOUNT.equalsIgnoreCase("")) {
                    setBadge(4, GlobalValues.PROMOTIONCOUNT);
                }
            }
        }
    }

    private void setBadge(int i, String s) {
        BottomNavigationMenuView menuView = (BottomNavigationMenuView) navigation.getChildAt(0);
        BottomNavigationItemView itemView = (BottomNavigationItemView) menuView.getChildAt(i);

        View notificationBadge = LayoutInflater.from(this).inflate(R.layout.layout_badge_view, menuView, false);

        TextView badgeNotification = notificationBadge.findViewById(R.id.badgeNotification);
        badgeNotification.setText(s);

        itemView.addView(notificationBadge);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @RequiresApi(api = Build.VERSION_CODES.M)
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Bundle bundle = null;
            Fragment fragment = new Fragment();
            switch (item.getItemId()) {
                case R.id.navigation_account:
                    view1.setVisibility(View.VISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    view3.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                    view5.setVisibility(View.INVISIBLE);
                    fragment = new MyAccountFragmentNew();
                    bundle = new Bundle();
                    bundle.putInt(GlobalValues.FROM_KEY, mFrom);
                    fragment.setArguments(bundle);
                    frgagment_tag = "account";
                    Utility.changeFragmentWithoutBackStack(fragment, getSupportFragmentManager(), FiveMenuActivityNew.this);
                    return true;

                case R.id.navigation_orders:
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.VISIBLE);
                    view3.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                    view5.setVisibility(View.INVISIBLE);
                    fragment = new FiveMenuOrderFragment();
                    frgagment_tag = "orders";
                    Utility.changeFragmentWithoutBackStack(fragment, getSupportFragmentManager(), FiveMenuActivityNew.this);
                    return true;

                case R.id.navigation_rewards:
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    view3.setVisibility(View.VISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                    view5.setVisibility(View.INVISIBLE);
                    fragment = new FragmentReward();
                    frgagment_tag = "rewards";
                    Utility.changeFragmentWithoutBackStack(fragment, getSupportFragmentManager(), FiveMenuActivityNew.this);
                    return true;

                case R.id.navigation_voucher:
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    view3.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.VISIBLE);
                    view5.setVisibility(View.INVISIBLE);
                    fragment = new FragmentVoucher();
                    frgagment_tag = "vouchers";
                    bundle = new Bundle();
                    bundle.putInt(GlobalValues.FROM_KEY, mFrom);
                    fragment.setArguments(bundle);
                    Utility.changeFragmentWithoutBackStack(fragment, getSupportFragmentManager(), FiveMenuActivityNew.this);
                    return true;

                case R.id.navigation_promotions:
                    view1.setVisibility(View.INVISIBLE);
                    view2.setVisibility(View.INVISIBLE);
                    view3.setVisibility(View.INVISIBLE);
                    view4.setVisibility(View.INVISIBLE);
                    view5.setVisibility(View.VISIBLE);
                    fragment = new PromotionFragment();
                    frgagment_tag = "promotions";
                    bundle = new Bundle();
                    bundle.putInt(GlobalValues.FROM_KEY, mFrom);
                    fragment.setArguments(bundle);
                    Utility.changeFragmentWithoutBackStack(fragment, getSupportFragmentManager(), FiveMenuActivityNew.this);
                    return true;

                /*case R.id.navigation_logout:

                    new AlertDialog(context, new AlertDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("Ok")) {
                                if (notificationManagerCompat != null) {
                                    notificationManagerCompat.cancelAll();
                                }
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

                                try {
                                    databaseHandler.deleteAllCartQuantity();
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }

                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                context.startActivity(intent);
                                finish();
                                Toast.makeText(context, "Logged out successfully", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });


                    return true;*/


            }
            return false;
        }
    };

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
            if (position == 0) {
                itemId = R.id.navigation_account;
            } else if (position == 1) {
                itemId = R.id.navigation_orders;
            } else if (position == 2) {
                itemId = R.id.navigation_rewards;
            } else if (position == 3) {
                itemId = R.id.navigation_voucher;
            } else if (position == 4) {
                itemId = R.id.navigation_promotions;
            }
            navigation.setSelectedItemId(itemId);
            /*intent = new Intent(context, FiveMenuActivityNew.class);
            intent.putExtra("position", position);
            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
            startActivity(intent);*/
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

                                            } else {
//                                                Intent intent = new Intent(context, CartActivity.class);
                                                Intent intent = new Intent(context, OrderSummaryActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    });
                                }
                            }
                        } else {
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
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(context, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(context, "OREO_UPDATE", "1");
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
   /*     imgLogo.setVisibility(View.VISIBLE);
        txtTitle.setVisibility(View.GONE)*/

        Utility.writeToSharedPreference(context, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(context, "OREO_UPDATE", "1");
        }

        if (Utility.networkCheck(context)) {
            String mCustomerId;
            String mReferenceId;
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




            new CartListTask().execute(url);
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


                    //if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
                    cart_sub_total = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));


                    /*} else {

                        Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                    }*/
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    @Override
    public void onBackPressed() {
        if (stackBackup) {
            super.onBackPressed();
        } else {
            if (drawerLayout.isDrawerOpen(navigationView)) {
                drawerLayout.closeDrawer(navigationView);
            } else {
                drawerLayout.openDrawer(navigationView);
            }
        }
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
            progressDialog = new ProgressDialog(context);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            Log.e("Member_reward_five::", params[0] + "\n" + countParams);

            String response = WebserviceAssessor.postRequest(context,params[0],countParams);

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
                        GlobalValues.CUSTOMER_REWARD_POINT = x_reedempoints;
                        GlobalValues.is_card_expired=rewardJson.getString("is_card_expired");
                       /* if (!countJson.isNull("reward_ponits_monthly")) {

                            GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = Double.valueOf(countJson.optString("reward_ponits_monthly"));

                        } else {
                            GlobalValues.CUSTOMER_MONTHLY_REWARD_POINT = 0.00;
                        }*/
                    }
                }else {
                    GlobalValues.CUSTOMER_REWARD_POINT = 0.0;
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

    class SettingsRequest extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(context);
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
                    Utility.writeToSharedPreference(context, GlobalValues.IS_REFERRAL_ENABLE, client_referral_enable);

//       client_mincart_quantity_bento             client_minimum_cart_quantity_enable : "1"


                    String client_mincart_quantity_bento = jsonResultSet.optString("client_mincart_quantity_bento");

                    String client_minimum_cart_quantity_enable = jsonResultSet.optString("client_minimum_cart_quantity_enable");

                    String client_tax_surcharge = jsonResultSet.optString("client_tax_surcharge_inclusive");
                    //           String client_tax_surcharge = jsonResultSet.optString("client_tax_surcharge");


                    Utility.writeToSharedPreference(context, GlobalValues.GstChargers, client_tax_surcharge);



                    Utility.writeToSharedPreference(context, GlobalValues.MinimumQuality, client_mincart_quantity_bento);
                    Utility.writeToSharedPreference(context, GlobalValues.Minimumavailable, client_minimum_cart_quantity_enable);


                    String omise_public_key = jsonResultSet.optString("omise_public_key");
                    String paymentenabled=jsonResultSet.getString("client_payments");
                    Utility.writeToSharedPreference(context, GlobalValues.PAYMENTKEY, "");
                    Utility.writeToSharedPreference(context,GlobalValues.PAYMENTKEYENABLE,paymentenabled);

                    if (!omise_public_key.equals("")) {

                        Utility.writeToSharedPreference(context, GlobalValues.PAYMENTKEY, omise_public_key);
                    }



                    if (!jsonResultSet.isNull("client_packaging_charge")) {

                        String packing_charge = jsonResultSet.optString("client_packaging_charge");
                        Utility.writeToSharedPreference(context, GlobalValues.PACKING_CHARGE, packing_charge);
                    }


                    Utility.writeToSharedPreference(context, GlobalValues.GstCharger, client_tax_surcharge);

                    Utility.writeToSharedPreference(context, GlobalValues.COD_START_TIME, time1);
                    Utility.writeToSharedPreference(context, GlobalValues.COD_END_TIME, time2);

                    Utility.writeToSharedPreference(context, GlobalValues.GSTIN, jsonResultSet.optString("client_tax_surcharge_inclusive"));

                    Utility.writeToSharedPreference(context, GlobalValues.CLIENT_ENABLE_MEMBERSHIP, jsonResultSet.optString("client_enable_membership"));




                    if (enable.equals("1")) {

                        Double delCharge = Double.parseDouble(jsonResultSet.getString("client_delivery_surcharge"));

                        Double addCharge = Double.parseDouble(jsonResultSet.getString("additional_delivery_charge"));
                        

                    } else {
                        GlobalValues.COMMON_DELIVERY_CHARGE = 0.0;
                    }

                } else {

                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

         //           showAlertDialog(jsonObject.optString("message"));
                }

            } catch (Exception e) {
                e.printStackTrace();

        //        showAlertDialog("Something went wrong. Please try again later.");
            } finally {
                //progressDialog.dismiss();

            }

        }
    }
}
