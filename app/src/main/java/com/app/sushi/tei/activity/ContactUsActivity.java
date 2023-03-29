package com.app.sushi.tei.activity;

import static com.app.sushi.tei.GlobalMembers.GlobalValues.CURRENT_AVAILABLITY_ID;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.core.content.res.ResourcesCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.NavigateMenu;
import com.app.sushi.tei.Model.outlet.takeaway.ResultSetItem;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.NavigationAdapter;
import com.app.sushi.tei.dialog.AlertDialog;
import com.app.sushi.tei.dialog.MessageDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ContactUsActivity extends AppCompatActivity {

    private ProgressBar progressBar;
    private Context mContext;
    private Toolbar toolbar;
    private LinearLayout imgBack;
    private ImageView txtTitle;
    private TextView txtSubmit;

    private EditText edtName, edtEmail, edtPhoneNumber, edtMessage, edtSubject;
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
    private TextView chooseOutletTxt;
    private List<ResultSetItem> outlets;
    private ArrayList<String> outletName;
    private ArrayAdapter<String> outletAdapter;
    private String selectedOutletId, selectedOutletName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact_us);


        mContext = ContactUsActivity.this;
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        imgBack = toolbar.findViewById(R.id.toolbarBack);
        txtTitle =  toolbar.findViewById(R.id.toolbartxtTitle);
        databaseHandler = DatabaseHandler.getInstance(mContext);
        imgBack = toolbar.findViewById(R.id.toolbarBack);
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
        chooseOutletTxt = findViewById(R.id.chooseOutlet);

        edtName = (EditText) findViewById(R.id.edtName);
        edtEmail = (EditText) findViewById(R.id.edtEmail);
        edtPhoneNumber = (EditText) findViewById(R.id.edtPhoneNumber);
        edtMessage = (EditText) findViewById(R.id.edtMessage);
        edtSubject = findViewById(R.id.edtSubject);
        txtSubmit = (TextView) findViewById(R.id.txtSubmit);

        //parseOutletJson();
        getOutletService();





        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtPhoneNumber.getText().toString().isEmpty() || edtPhoneNumber.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter Mobile no.", Toast.LENGTH_SHORT).show();
                } else if (edtPhoneNumber.getText().toString().trim().length() < 8) {
                    Toast.makeText(mContext, "Please enter Mobile no.", Toast.LENGTH_SHORT).show();
                } else if (edtName.getText().toString().isEmpty() || edtName.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter Name", Toast.LENGTH_SHORT).show();
                } else if (edtEmail.getText().toString().isEmpty() || edtEmail.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter Email", Toast.LENGTH_SHORT).show();
                } else if (!emailValidation(edtEmail.getText().toString())) {
                    Toast.makeText(mContext, "Please enter valid E-mail.", Toast.LENGTH_SHORT).show();
                }else if (edtSubject.getText().toString().isEmpty() || edtSubject.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter Subject", Toast.LENGTH_SHORT).show();
                } else if (edtMessage.getText().toString().isEmpty() || edtMessage.getText().toString().trim().length() == 0) {
                    Toast.makeText(mContext, "Please enter the message", Toast.LENGTH_SHORT).show();
                } else if(chooseOutletTxt.getText().equals("Choose outlet")){
                    Toast.makeText(mContext, "Please choose the outlet", Toast.LENGTH_SHORT).show();
                } else {
                    String url = GlobalUrl.ContactUS;
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("app_id", GlobalValues.APP_ID);
                    params.put("customer_first_name", edtName.getText().toString());
                    params.put("customer_emailaddress", edtEmail.getText().toString());
                    params.put("customer_phonenumber", edtPhoneNumber.getText().toString());
                    params.put("customer_message", edtMessage.getText().toString());
                    params.put("subject", edtSubject.getText().toString());
                    params.put("contactus_extra_field", "Contact Us Enquiry");
                    params.put("status", "A");
                    params.put("outleid", selectedOutletId);
                    params.put("outles", selectedOutletName);

                    new ContactUsTask(params).execute(url);
                }
            }
        });
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

                if (!(Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT).equals(""))) {

                }
            }
        });

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            layoutSignIn.setVisibility(View.GONE);
            txtSignedUsername.setVisibility(View.GONE);

            txtSignedUsername.setText("Signed in as " + Utility.readFromSharedPreference(mContext, GlobalValues.EMAIL));
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
                Utility.writeToSharedPreference(mContext, GlobalValues.OPENLOGIN, "Close");
                intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        layoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog(mContext, new AlertDialog.OnSlectedString() {
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

                            Utility.removeFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.FIRSTNAME);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.LASTNAME);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.EMAIL);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.CUSTOMERPHONE);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.POSTALCODE);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT);
                            Utility.removeFromSharedPreference(mContext, GlobalValues.PROMOTIONCOUNT);


                            try {
                                databaseHandler.deleteAllCartQuantity();
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                            invalidateOptionsMenu();

                            intent = new Intent(mContext, LoginActivity.class);
//                            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);
                            Toast.makeText(mContext, "Logged out successfully", Toast.LENGTH_SHORT).show();
                        }

                    }
                });


            }
        });

        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
                        || Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
                    intent = new Intent(mContext, ChooseOutletActivity.class);
                    if (CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                        intent.putExtra("availability", "delivery");
                    } else {
                        intent.putExtra("availability", "takeaway");
                    }
                    startActivity(intent);
                    finish();
                    return;
                } else {
                    intent = new Intent(mContext, MenuCategoryActivity.class);
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

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                           intent = new Intent(mContext, LoginActivity.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    Intent intent = new Intent(mContext, NotificationActivity.class);
                    startActivity(intent);
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
                drawerLayout.closeDrawers();

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null &&
                        Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                    Intent i = new Intent(mContext, SettingsActivity.class);
                    startActivity(i);
                } else {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent myAccountIntent = new Intent(mContext, LoginActivity.class);
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
                Utility.writeToSharedPreference(mContext, GlobalValues.OPENLOGIN, "Close");
                intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        chooseOutletTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showOutletDialague();
            }
        });


    }

    private void parseOutletJson() {

        if(!GlobalValues.outletsJson.equalsIgnoreCase("")){

            try{
                JSONObject jsonObject = new JSONObject(GlobalValues.outletsJson);
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    JSONArray arrayResult = jsonObject.getJSONArray("result_set");
                    if (arrayResult.length() > 0) {
                        outlets = new ArrayList<>();
                        outletName = new ArrayList<>();
                        for (int j = 0; j < arrayResult.length(); j++) {
                            JSONObject jsonOutlet = arrayResult.getJSONObject(j);
                            ResultSetItem outlet = new ResultSetItem();
                            outlet.setOaOutletId(jsonOutlet.getString("oa_outlet_id"));
                            outlet.setOutletName(jsonOutlet.optString("outlet_name"));
                            outletName.add(jsonOutlet.optString("outlet_name"));
                        }


                    }
                }
            }catch (JSONException je){
                je.printStackTrace();
            }

        }
    }

    private void openFiveMenuActivity(int position) {


        if (!isCustomerLoggedIn()) {

            new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                @Override
                public void selectedAction(String action) {

                    if (action.equalsIgnoreCase("Ok")) {
                        intent = new Intent(mContext, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }
                }
            });

        } else {
            intent = new Intent(mContext, FiveMenuActivityNew.class);
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
//        if (Utility.networkCheck(mContext)) {
//            new MenuTask().execute(menuUrl);
//        } else {
//            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
//        }

        intent = new Intent(mContext, CmsActivity.class);
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


                            NavigationAdapter navigationAdapter = new NavigationAdapter(ContactUsActivity.this, navigateMenuArrayList);

                            subRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            subRecyclerView.setAdapter(navigationAdapter);

                            mainMenuLayout.setVisibility(View.GONE);
                            subMenuLayout.setVisibility(View.VISIBLE);


                        }
                    } else {
                        Toast.makeText(mContext, responseJSONObject.getString("message"), Toast.LENGTH_LONG).show();


                    }

                } catch (Exception e) {
                    e.printStackTrace();


                } finally {
                    progressDialog.dismiss();
                }


            }
        }
    }

    private void clearCart() {
        String url1 = GlobalUrl.DESTROY_CART_URL;
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("customer_id", Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID));
        params.put("reference_id", "");

        if (Utility.networkCheck(mContext)) {

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

            String url = GlobalUrl.ACTIVE_COUNT_URL + "?app_id=" + GlobalValues.APP_ID + "&customer_id=" +
                    Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) +
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

    private class ContactUsTask extends AsyncTask<String, Void, String> {

        private Map<String, String> forgotParams;
        private ProgressDialog progressDialog;


        public ContactUsTask(Map<String, String> params) {
            forgotParams = params;
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

            String response = WebserviceAssessor.postRequest(mContext, params[0], forgotParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

                Intent start = new Intent(mContext, ContactUsActivity.class);
                startActivity(start);
                finish();

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }

        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_empty, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*if (id == R.id.action_settings) {
            return true;
        }*/

        return super.onOptionsItemSelected(item);
    }

    private void getOutletService() {
        if (Utility.networkCheck(mContext)) {
            String url = GlobalUrl.COMPASSOUTLET_URL + "?app_id=" + GlobalValues.APP_ID;

            new OutletTask().execute(url);

        } else {
            Toast.makeText(mContext, "Please check your interner connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private class OutletTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        OutletTask() {

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

                    JSONArray arrayResult = jsonObject.getJSONArray("result_set");

                    if (arrayResult.length() > 0) {
                        outlets = new ArrayList<>();
                        outletName = new ArrayList<>();
                        for (int j = 0; j < arrayResult.length(); j++) {

                            JSONObject jsonOutlet = arrayResult.getJSONObject(j);

                            ResultSetItem outlet = new ResultSetItem();
                            outlet.setOaOutletId(jsonOutlet.getString("oa_outlet_id"));
                            outlet.setOutletName(jsonOutlet.optString("outlet_name"));
                            outlets.add(outlet);
                            outletName.add(jsonOutlet.optString("outlet_name"));
                        }

                        outletAdapter = new ArrayAdapter<String>(mContext, android.R.layout.simple_spinner_dropdown_item, outletName);
                    } else {

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

    private void showOutletDialague(){
        new android.app.AlertDialog.Builder(mContext)
                .setTitle("Choose Outlet")
                .setAdapter(outletAdapter, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        chooseOutletTxt.setText(outletName.get(which));
                        selectedOutletId = outlets.get(which).getOaOutletId();
                        selectedOutletName = outletName.get(which);

                    }
                }).create().show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        getActiveCount();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
        // txtTitle.setText("Contact Us");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
        }
    }

    private boolean isCustomerLoggedIn() {


        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            return true;
        } else {
            Utility.writeToSharedPreference(mContext, GlobalValues.OPENLOGIN, "Close");
            return false;
        }
    }

    private boolean emailValidation(String Email) {
        return (!TextUtils.isEmpty(Email) && Patterns.EMAIL_ADDRESS.matcher(Email).matches());
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
