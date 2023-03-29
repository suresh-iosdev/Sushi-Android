package com.app.sushi.tei.activity;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.CompassOutlet.OutletResultSetItem;
import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.Model.Home.SubCategory;
import com.app.sushi.tei.Model.NavigateMenu;
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.CategoryRecyclerAdapter;
import com.app.sushi.tei.adapter.NavigationAdapter;
import com.app.sushi.tei.adapter.OutletListAdapter;
import com.app.sushi.tei.dialog.ClearCartMessageDialog;
import com.app.sushi.tei.dialog.MessageDialog;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HomeActivity extends AppCompatActivity {
    private static final float END_SCALE = 0.9f;
    public static LinearLayout layoutSignIn, layoutSignOut;
    public static TextView txtSignedUsername;
    public static TextView txtModifierPrice;
    public static int mModifierQuantity = 1, mSetMenuQuantity = 1;
    public static Double mSearchProuductprise = 0.0,mSetmenuoverallprices = 0.0, mSetMenuPrice = 0.0, mSetMenuBasePrice = 0.0;
    public static Double mquanititycost_src = 0.00, mModifierPrice = 0.00, quantityCost = 0.00;
    public static TextView txtNotificationCount, txtOrderCount, txtPromotionCount;
    public static Double doubletitle=0.0;

    public static String switchoutletfromCartActivity="notshow";

    String cart_availability_id = "", cart_availability_name = "";
    Boolean iEnableSecondarylist = false;
    private Fragment mFragment;
    private FragmentManager fragmentManager;
    private FragmentTransaction fragmentTransaction;
    private Toolbar toolbar;
    private DrawerLayout drawerLayout;
    private RelativeLayout navigationView;
    private View content;
    private Context mContext;
    private LinearLayout imgBack;
    private Intent intent;
    private RelativeLayout layoutHome, layoutAccount, layoutViewOrder, layoutRewards,
            layoutPromotions, layoutNotification, layoutAbout, layoutSettings, layoutWhatshappening
            ,layout_terms,layout_refund,layout_privacy,layout_contact;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryRecyclerAdapter asianCategoryRecyclerAdapter;
    private LinearLayout takeAwayLayout;
    private ArrayList<MainCategory> mainCategoryList;
    private ArrayList<SubCategory> subCategoryList;
    private String mCategoryBasePath = "";
    private String mSubCategoryBasePath = "";
    private RelativeLayout layoutCart, layoutSearch;
    private TextView txtCartCount, txtCardName;
    private String cartCount = "";
    private DatabaseHandler databaseHandler;
    private RecyclerView subRecyclerView;
    private RelativeLayout mainMenuLayout, subMenuLayout, submenuTopLayout;
    private String mCustomerId = "", mReferenceId = "";
    private RelativeLayout aboutBackLayout;
    private String promocount;
    public static int cutOffTime;

    private List<OutletResultSetItem> outletList;
    private RecyclerView recycle_outletList;
    private OutletListAdapter outletListAdapter;
    public Dialog dialog;
    private TextView outletName,txtNoRecords;
    private ImageView heartblink_imageview;

    private boolean resultSet=true;

    private ArrayList<Favouriteitems> favouriteitemsArrayList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mContext = this;
        mContext = this;
        toolbar = findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);

        heartblink_imageview = (ImageView) toolbar.findViewById(R.
                id.heartblink_imageview);
        heartblink_imageview.setVisibility(View.VISIBLE);
        databaseHandler = DatabaseHandler.getInstance(mContext);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        content = findViewById(R.id.content);
        imgBack = findViewById(R.id.toolbarBack);
        imgBack.setVisibility(View.GONE);
        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignOut = findViewById(R.id.layoutSignOut);
        txtSignedUsername = findViewById(R.id.txtSignedUsername);
        txtNotificationCount = findViewById(R.id.txtNotificationCount);
        txtPromotionCount = findViewById(R.id.txtPromotionCount);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        layoutHome = findViewById(R.id.layoutHome);
        layoutAccount = findViewById(R.id.layoutAccount);
        layoutViewOrder = findViewById(R.id.layoutViewOrder);
        layoutRewards = findViewById(R.id.layoutRewards);
        layoutPromotions = findViewById(R.id.layoutPromotions);
        layoutNotification = findViewById(R.id.layoutNotification);
        //aboutBackLayout = findViewById(R.id.aboutBackLayout);
        layoutAbout = findViewById(R.id.layoutAbout);
        layout_terms = findViewById(R.id.layout_terms);
        layout_refund = findViewById(R.id.layout_refund);
        layout_privacy = findViewById(R.id.layout_privacy);
        layout_contact=findViewById(R.id.layout_contact);
        layoutSettings = findViewById(R.id.layoutSettings);
        layoutWhatshappening = findViewById(R.id.layoutWhatsHappening);
        mainMenuLayout = findViewById(R.id.mainMenuLayout);
        subMenuLayout = findViewById(R.id.subMenuLayout);
        takeAwayLayout = findViewById(R.id.takeAwayLayout);
        subRecyclerView = findViewById(R.id.subRecyclerView);
        outletName=findViewById(R.id.ouletName);
        txtNoRecords=findViewById(R.id.txtNoRecords);

        if (Utility.networkCheck(mContext)) {

            String app_id = "?app_id=" + GlobalValues.APP_ID;

            String url = GlobalUrl.SETTINGS_URL + app_id;

            new SettingsRequest().execute(url);

        } else {
            Toast.makeText(mContext,"You are offline please check your internet connection",Toast.LENGTH_SHORT).show();
        }


        takeAwayLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getOutletService();
            }
        });

       /* aboutBackLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mainMenuLayout.setVisibility(View.VISIBLE);
                subMenuLayout.setVisibility(View.GONE);
            }
        });*/

        layoutAbout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) != null &&
                        Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                    Intent i = new Intent(HomeActivity.this, SettingsActivity.class);
                    startActivity(i);
                } else {

                    Intent myAccountIntent = new Intent(HomeActivity.this, LoginActivity.class);
                    myAccountIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(myAccountIntent);
                    finish();

                }
            }
        });


        heartblink_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {

                                intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
//                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                }else {

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).length() > 0) {

                        if(resultSet){
                        Intent intent = new Intent(mContext, FavouriteActivity.class);
                        startActivity(intent);
                        }


                    } else {
                        Toast.makeText(mContext, "Please select your outlet!", Toast.LENGTH_SHORT).show();
                    }

                }
            }
        });
        CheckFacourites();

      /*  new LoginDialog(mContext, new LoginDialog.OnSlectedString() {
            @Override
            public void selectedAction(String action) {
                if(action.equalsIgnoreCase("Register")){
                    new RegisterDialog(mContext);
                }

            }
        });*/

        GlobalValues.CURRENT_AVAILABLITY_ID= GlobalValues.TAKEAWAY_ID;
        GlobalValues.CURRENT_AVAILABLITY_NAME= GlobalValues.TAKEAWAY;
        promocount = Utility.readFromSharedPreference(mContext, GlobalValues.PROMOTIONCOUNT);


        if (promocount != null) {

            if (!promocount.isEmpty() && promocount.length() > 0) {

                txtPromotionCount.setVisibility(View.VISIBLE);
                txtPromotionCount.setText(promocount);

            } else {
                txtPromotionCount.setVisibility(View.GONE);
            }
        } else {

            txtPromotionCount.setVisibility(View.GONE);
        }


        Utility.writeToSharedPreference(mContext, GlobalValues.AVALABILITY_ID,
                GlobalValues.TAKEAWAY_ID);
        showCategory();
        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.icon_drawer, getTheme());
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
                                           }
                                       }
        );

        try {

            int v = getIntent().getIntExtra("view_order", 0);

            int p = getIntent().getIntExtra(GlobalValues.FROM_NOTIFICATION_POSITION, 0);



            if (v == 1) {

                intent = new Intent(mContext, FiveMenuActivity.class);
                intent.putExtra("position", 1);
                intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
                startActivity(intent);
            } else if (p == 1) {
                openFiveMenuActivity(p);
            } else if (p == 2) {
                openFiveMenuActivity(p);
            } else if (p == 3) {
                openFiveMenuActivity(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


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
                Utility.writeToSharedPreference(mContext, GlobalValues.OPENLOGIN,"Close");
                intent = new Intent(mContext, LoginActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            }
        });

        layoutSignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.closeDrawers();
                layoutSignIn.setVisibility(View.VISIBLE);
                txtSignedUsername.setVisibility(View.GONE);
                layoutSignOut.setVisibility(View.GONE);

                txtOrderCount.setVisibility(View.GONE);
                txtPromotionCount.setVisibility(View.GONE);
                txtNotificationCount.setVisibility(View.GONE);
                clearCart();
                // resideMenu.closeMenu();
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
              //  Utility.removeFromSharedPreference(mContext, GlobalValues.OUTLET_ID);
               // Utility.removeFromSharedPreference(mContext, GlobalValues.OUTLET_NAME);


                //secondaryAddressArrayList.clear();

                iEnableSecondarylist = false;

                try {
                    databaseHandler.deleteAllCartQuantity();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                invalidateOptionsMenu();

                intent = new Intent(mContext, LoginActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                startActivity(intent);
                Toast.makeText(mContext, "Logged out successfully", Toast.LENGTH_SHORT).show();
            }
        });


        layoutHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                drawerLayout.closeDrawers();
            }
        });


        layoutPromotions.setOnClickListener(new View.OnClickListener() {
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
                openFiveMenuActivity(4);
            }
        });


        mRecyclerView = findViewById(R.id.mRecyclerView);




     /*   mFragment = new HomeFragment();
        fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, mFragment);
        fragmentTransaction.commit();*/




        String outId = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

        if (outId != null && !outId.isEmpty() && !outId.equals("null")) {

        } else {
            getOutletService();
        }

    }

    private void tabnewadded(String searchkey) {
        intent = new Intent(mContext, CmsActivity.class);
        intent.putExtra("TITLE", "Terms_and_extra");
        intent.putExtra("SEARCH_KEY", searchkey);
        intent.putExtra("landingPage", true);
        startActivity(intent);
    }

    private void getAboutUs() {

       /* String app_id = "?app_id=" + GlobalValues.APP_ID;
        String slug = "&menu_slug=Main-menu";
        String menuUrl = GlobalUrl.MENU_URL + app_id + slug;*/


        String app_id = "?app_id=" + GlobalValues.APP_ID;
        String slug = "&menu_slug=Main-menu";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }

    private void openFiveMenuActivity(int position) {

        if (!isCustomerLoggedIn()) {

            new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                @Override
                public void selectedAction(String action) {

                    if(action.equalsIgnoreCase("Ok")){
                        intent = new Intent(mContext, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    }

                }
            });



        } else {

            intent = new Intent(mContext, FiveMenuActivity.class);
            intent.putExtra("position", position);
            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
            startActivity(intent);
        }
    }

    private void showCategory() {

        if (Utility.networkCheck(mContext)) {
            outletName.setText(  Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_NAME));

            //availability=7B30BB03-14BD-47E4-B9B1-9731F9A3BC9C&outletId=151
            String url = GlobalUrl.CATEGORY_URL + "?app_id=" + GlobalValues.APP_ID + "&availability="
                    + Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID) + "&outletId="
            + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

            new CategoryTask().execute(url);
        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(switchoutletfromCartActivity.equalsIgnoreCase("show")){
            switchoutletfromCartActivity="notshow";
            getOutletService();
        }
//        txtTitle.setVisibility(View.GONE);
//        imgLogo.setVisibility(View.VISIBLE);
//        toolbarBack.setVisibility(View.GONE);

        invalidateOptionsMenu();

        getCartCount();

        getActiveCount();
        CheckFacourites();

    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {

        try {
            MenuItem cartWBadge = menu.findItem(R.id.menu_cart);
//            MenuItem menuSearch = menu.findItem(R.id.menu_search);

            View viewBadge = menu.findItem(R.id.menu_cart).getActionView();
            View searchwBadge = menu.findItem(R.id.menu_search).getActionView();

            layoutCart = viewBadge.findViewById(R.id.layoutCart);
            txtCartCount = viewBadge.findViewById(R.id.txtCartCount);

            layoutSearch = searchwBadge.findViewById(R.id.layoutSearch);

            cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);


            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

                txtCartCount.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);

            } else {

                txtCartCount.setVisibility(View.GONE);
            }

            layoutCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).length() > 0) {
                    try {


                        String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);

                        if (Integer.parseInt(MinQual) >= 1) {


                            if (Integer.parseInt(cartCount) != 0) {
                                if(resultSet){

//                                intent = new Intent(mContext, CartActivity.class);
                                intent = new Intent(mContext, OrderSummaryActivity.class);
                                startActivity(intent);
                                }
                            }


                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    }else {
                        Toast.makeText(mContext, "Please select your outlet!", Toast.LENGTH_SHORT).show();
                    }

                }
            });


            layoutSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



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
                    }else {

                        if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).length() > 0) {

                            if(resultSet){
                            intent = new Intent(mContext, SearchActivity.class);
                            startActivity(intent);
                            }
                        } else {
                            Toast.makeText(mContext, "Please select your outlet!", Toast.LENGTH_SHORT).show();
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

    private void  getCartCount() {

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";
        } else {
            mCustomerId = "";
            mReferenceId = Utility.getReferenceId(mContext);
        }

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.CART_LIST_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&customer_id=" + mCustomerId +
                    "&reference_id=" + "" +
                    "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;


            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            new CartListTask().execute(url);}

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
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


                            NavigationAdapter navigationAdapter = new NavigationAdapter(HomeActivity.this, navigateMenuArrayList);

                            subRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
                            subRecyclerView.setAdapter(navigationAdapter);

                            mainMenuLayout.setVisibility(View.GONE);
                            subMenuLayout.setVisibility(View.VISIBLE);


                        }
                    } else {
                        Toast.makeText(HomeActivity.this, responseJSONObject.getString("message"), Toast.LENGTH_LONG).show();


                    }

                } catch (Exception e) {
                    e.printStackTrace();


                } finally {
                    progressDialog.dismiss();
                }


            }
        }

    }

    private class CategoryTask extends AsyncTask<String, Void, String> {

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

                    JSONObject commonJson = jsonObject.getJSONObject("common");

                    mCategoryBasePath = commonJson.getString("category_image_url") + "/";
                    mSubCategoryBasePath = commonJson.getString("subcategory_image_url") + "/";

                    if ( jsonObject.isNull( "result_set" )){
                        resultSet=false;
                        txtNoRecords.setVisibility(View.VISIBLE);
                        mRecyclerView.setVisibility(View.GONE);
                    } else{
                        resultSet=true;
                        txtNoRecords.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.VISIBLE);
                        JSONArray arrayResult = jsonObject.getJSONArray("result_set");

                    mainCategoryList = new ArrayList<>();

                    if (arrayResult.length() > 0) {

                        for (int j = 0; j < arrayResult.length(); j++) {

                            JSONObject categoryJson = arrayResult.getJSONObject(j);

                            MainCategory mainCategory = new MainCategory();

                            mainCategory.setmCategoryId(categoryJson.getString("pro_cate_primary_id"));
                            mainCategory.setmCategoryName(categoryJson.getString("category_name"));
                            mainCategory.setmActiveImage(mCategoryBasePath+categoryJson.optString("pro_cate_image"));
                            mainCategory.setmCategorySlug(categoryJson.optString("pro_cate_slug"));
                            mainCategory.setPro_cat_description(categoryJson.optString("pro_cate_short_description"));

                            subCategoryList = new ArrayList<>();

                            if (categoryJson.get("subcat_list_arr") instanceof JSONObject) {

                                JSONObject subJson = categoryJson.getJSONObject("subcat_list_arr");

                                JSONArray subArray = subJson.getJSONArray("sub_result_set");

                                subCategoryList = new ArrayList<>();

                                if (subArray.length() > 0) {

                                    for (int k = 0; k < subArray.length(); k++) {
                                        SubCategory subCategory = new SubCategory();

                                        JSONObject subCatJson = subArray.getJSONObject(k);
                                        subCategory.setmSubCategoryName(subCatJson.getString("pro_subcate_name"));
                                        subCategory.setGetmSubCategoryImage(subCatJson.getString("pro_subcate_image"));
                                        // subCategory.setmBasePath(mSubCategoryBasePath);
                                        subCategory.setmSubCategorySlug(subCatJson.getString("pro_subcate_slug"));
                                        subCategory.setmSubCategoryProductDescription("pro_subcate_short_description");

                                        subCategoryList.add(subCategory);

                                    }
                                } else {

                                }

                                mainCategory.setSubCategoryList(subCategoryList);
                                mainCategoryList.add(mainCategory);

                            } else {
                                mainCategory.setSubCategoryList(subCategoryList);
                                mainCategoryList.add(mainCategory);
                            }
                        }

                        layoutManager = new LinearLayoutManager(mContext);
                        mRecyclerView.setLayoutManager(layoutManager);
                        asianCategoryRecyclerAdapter = new CategoryRecyclerAdapter(mContext, mCategoryBasePath, mainCategoryList);
                        mRecyclerView.setAdapter(asianCategoryRecyclerAdapter);
                        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        mRecyclerView.setHasFixedSize(true);
                        mRecyclerView.setNestedScrollingEnabled(false);

                        asianCategoryRecyclerAdapter.setonItemClick(new CategoryRecyclerAdapter.IOnItemClick() {
                            @Override
                            public void onItemClick(View v, int position) {
                                String outId = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

                                if (outId != null && !outId.isEmpty() && !outId.equals("null")) {
                                    Intent intent = new Intent(mContext, SubCategoryActivity.class);
                                    intent.putExtra(GlobalValues.CATEGORY_NAME, mainCategoryList.get(position).getmCategoryName());
                                    intent.putExtra(GlobalValues.CATEGORY_DESCRIPTION, mainCategoryList.get(position).getPro_cat_description());
                                    intent.putExtra("Position", String.valueOf(position));
                                    intent.putExtra("MainImagePath", mCategoryBasePath);
                                    intent.putParcelableArrayListExtra(GlobalValues.CATEGORY_LIST, mainCategoryList);
                                    mContext.startActivity(intent);
                                } else {
                                    getOutletService();
                                }
                            }
                        });
                    }
                }

                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }


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
                        JSONObject jsonContent = jsonObject.getJSONObject("result_set");

                        JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");



                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                                jsonCartDetails.optString("cart_total_items"));

                        if (Integer.valueOf(jsonCartDetails.optString("cart_total_items")) > 0) {
                            Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonContent.toString());

                        } else {
                            Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");
                        }

                        cart_availability_id = jsonCartDetails.optString("cart_availability_id");

                        cart_availability_name = jsonCartDetails.optString("cart_availability_name");


                        //ProductListActivity.isInvalidated = false;

                        invalidateOptionsMenu();

                        JSONArray jsonCartItem = jsonContent.getJSONArray("cart_items");

                        if (jsonCartItem.length() > 0) {

                            for (int c = 0; c < jsonCartItem.length(); c++) {

                                JSONObject jsonItem = null;
                                jsonItem = jsonCartItem.getJSONObject(c);

                                databaseHandler.updateQty(jsonItem.getString("cart_item_product_id"), jsonItem.getString("cart_item_qty"));

                            }
                        }

                    } else {

                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT, "");
                        Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, "");
                        Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT, "");


                        //ProductListActivity.isInvalidated = false;
                        invalidateOptionsMenu();

                        // Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

                // slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.COLLAPSED);


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();


                String id = Utility.getCartAvailabilityId(mContext);


             /*   if (id.length() > 0) {

                    swipedIndex = Utility.getAvailabilityIndex(id);

                    Picasso.with(mContext).load(cardIconsList[swipedIndex]).into(imgCardImage);
                    txtCardName.setText(cardNamesList.get(swipedIndex));

                } else {

                    Picasso.with(mContext).load(cardIconsList[0]).into(imgCardImage);
                    txtCardName.setText(cardNamesList.get(0));
                }*/

              /*
                mCardStack.setVisibleCardNum(swipedIndex);

                int count = 1;

                count = swipedIndex;



                for (int k = 0; k < HomeActivity.cardList.size(); k++) {


                    if (k == 0) {
                        HomeActivity.imgCard1.setImageBitmap(
                                Utility.decodeSampledBitmapFromResource(getResources(),
                                        HomeActivity.cardList.get(count), 110, 170));
                    } else if (k == 1) {
                        HomeActivity.imgCard2.setImageBitmap(
                                Utility.decodeSampledBitmapFromResource(getResources(),
                                        HomeActivity.cardList.get(count), 110, 170));
                    } else if (k == 2) {
                        HomeActivity.imgCard3.setImageBitmap(
                                Utility.decodeSampledBitmapFromResource(getResources(),
                                        HomeActivity.cardList.get(count), 110, 170));
                    }
                    count++;

                    if (count > 3) {
                        count = 0;
                    }
                }
*/

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
                        outletList = new ArrayList<>();

                        for (int j = 0; j < arrayResult.length(); j++) {

                            JSONObject jsonOutlet = arrayResult.getJSONObject(j);

                            OutletResultSetItem outlet = new OutletResultSetItem();

                            outlet.setOaOutletId(jsonOutlet.getString("oa_outlet_id"));
                            outlet.setOaAvailabilityId(jsonOutlet.optString("oa_availability_id"));
                            outlet.setOutletName(jsonOutlet.optString("outlet_name"));
                            outlet.setOutletId(jsonOutlet.getString("outlet_id"));
                            outlet.setOutletPhone(jsonOutlet.getString("outlet_phone"));
                            outlet.setOutletDeliveryTiming(jsonOutlet.getString("outlet_delivery_timing"));
                            outlet.setOutletUnitNumber1(jsonOutlet.getString("outlet_unit_number1"));
                            outlet.setOutletUnitNumber2(jsonOutlet.getString("outlet_unit_number2"));
                            outlet.setOutletAddressLine1(jsonOutlet.getString("outlet_address_line1"));
                            outlet.setOutletAddressLine2(jsonOutlet.getString("outlet_address_line2"));
                            outlet.setOutletPostalCode(jsonOutlet.getString("outlet_postal_code"));
                            outlet.setOutletDefalutValue(jsonOutlet.getString("outlet_defalut_value"));
                            outlet.setOutletOpenDate(jsonOutlet.getString("outlet_open_date"));
                            outlet.setOutletCloseDate(jsonOutlet.getString("outlet_close_date"));
                            outlet.setOutletOpenTime(jsonOutlet.getString("outlet_open_time"));
                            outlet.setOutletCloseTime(jsonOutlet.getString("outlet_close_time"));
                            outlet.setOutletRouteId(jsonOutlet.getString("outlet_route_id"));
                            outlet.setOutletMarkerLatitude(jsonOutlet.getString("outlet_marker_latitude"));
                            outlet.setOutletMarkerLongitude(jsonOutlet.getString("outlet_marker_longitude"));
                            outlet.setOutletDineTat(jsonOutlet.getString("outlet_dine_tat"));
                            outlet.setOutletDeliveryTat(jsonOutlet.getString("outlet_delivery_tat"));
                            outlet.setOutletPickupTat(jsonOutlet.getString("outlet_pickup_tat"));
                            outlet.setOutletServiceCharge(jsonOutlet.getString("outlet_service_charge"));
                            outletList.add(outlet);




                        }
                        showOutletPopup(outletList);




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

    private void showOutletPopup(final List<OutletResultSetItem> outletList) {
        dialog = new Dialog(mContext, R.style.custom_dialog_theme1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.activity_outlet);
        dialog.setCanceledOnTouchOutside(false);
        dialog.show();
        recycle_outletList =dialog.findViewById(R.id.recycle_outletList);
        ImageView closeOutlet=dialog.findViewById(R.id.closeOutlet);

        closeOutlet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (dialog.isShowing())
                    dialog.dismiss();
            }
        });
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        recycle_outletList.setLayoutManager(linearLayoutManager);
        outletListAdapter = new OutletListAdapter(mContext, outletList);
        recycle_outletList.setAdapter(outletListAdapter);


        outletListAdapter.setOnClickListeners(new OutletListAdapter.OnOutletClick() {
            @Override
            public void OnClick(View v, final int position) {

                String minQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                if(minQual.equalsIgnoreCase("")){
                    minQual="0";
                }
                if (Integer.parseInt(minQual) >= 1) {

                String message = "You are about to clear your cart by switching from one oulet to another!";
                new ClearCartMessageDialog(mContext, message, new ClearCartMessageDialog.OnSlectedMethod() {
                    @Override
                    public void selectedAction(String action) {

                        if (action.equalsIgnoreCase("YES")) {

                            Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_ID, outletList.get(position).getOaOutletId());
                            Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_NAME, outletList.get(position).getOutletName());
                            Utility.writeToSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS, outletList.get(position).getOutletAddressLine1());
                            GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

                            showCategory();
                            clearCart();
                            CheckFacourites();
                            invalidateOptionsMenu();
                            getCartCount();
                            getActiveCount();
                            if (dialog.isShowing())
                                dialog.dismiss();
                        } else {

                        }

                    }
                });
            }else{


                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_ID, outletList.get(position).getOaOutletId());
                Utility.writeToSharedPreference(mContext, GlobalValues.OUTLET_NAME, outletList.get(position).getOutletName());
                Utility.writeToSharedPreference(mContext, GlobalValues.CURRENT_OUTLET_ADDRESS, outletList.get(position).getOutletAddressLine1());
                GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

                showCategory();
                clearCart();
                CheckFacourites();
                invalidateOptionsMenu();
                getCartCount();
                getActiveCount();
                if (dialog.isShowing())
                    dialog.dismiss();
            }
            }

        });

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


    private void CheckFacourites()  {

            if (Utility.networkCheck(mContext)) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                String app_id = "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + mCustomerId;

                String availability_id = "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;

                GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

                String outlet_id = "&outlet_id=" + GlobalValues.CURRENT_OUTLET_ID;


                String Url = GlobalUrl.FavouriteListURL + app_id + availability_id + outlet_id;


                try {
                    new FavouriteListTask().execute(Url);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
            }
    }

    private class FavouriteListTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading");
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

               // String basePath = jsonObject.getJSONObject("common").optString("subcategory_image_url");


                if (jsonObject.optString("status").equalsIgnoreCase("ok")) {

                    JSONArray jsonArray = jsonObject.getJSONArray("result_set");


                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject jsonObjectfav = jsonArray.getJSONObject(i);

                        Favouriteitems favouriteitems = new Favouriteitems();


                        favouriteitems.setProduct_primary_id(jsonObjectfav.optString("product_primary_id"));
                        favouriteitems.setProduct_id(jsonObjectfav.optString("product_id"));
                        favouriteitems.setProduct_name(jsonObjectfav.optString("product_name"));
                        favouriteitems.setProduct_alias(jsonObjectfav.optString("product_alias"));
                        favouriteitems.setProduct_sku(jsonObjectfav.optString("product_sku"));
                        favouriteitems.setProduct_sequence(jsonObjectfav.optString("product_sequence"));
                        favouriteitems.setProduct_thumbnail(jsonObjectfav.optString("product_thumbnail"));
                        favouriteitems.setProduct_short_description(jsonObjectfav.optString("product_short_description"));
                        favouriteitems.setProduct_long_description(jsonObjectfav.optString("product_long_description"));
                        favouriteitems.setProduct_status(jsonObjectfav.optString("product_status"));
                        favouriteitems.setProduct_slug(jsonObjectfav.optString("product_slug"));
                        favouriteitems.setProduct_price(jsonObjectfav.optString("product_price"));
                        favouriteitems.setProduct_type(jsonObjectfav.optString("product_type"));
                        favouriteitems.setProduct_stock(jsonObjectfav.optString("product_stock"));
                        favouriteitems.setProduct_minimum_quantity(jsonObjectfav.optString("product_minimum_quantity"));
                        favouriteitems.setFav_id(jsonObjectfav.optString("fav_id"));
                        favouriteitems.setFav_product_primary_id(jsonObjectfav.optString("fav_product_primary_id"));
                        favouriteitems.setFav_customer_id(jsonObjectfav.optString("fav_customer_id"));
                        favouriteitems.setFav_app_id(jsonObjectfav.optString("fav_app_id"));
                        favouriteitems.setFav_created_on(jsonObjectfav.optString("fav_created_on"));

                        favouriteitemsArrayList.add(favouriteitems);


                    }


                    if (favouriteitemsArrayList.size() > 0) {

                        heartblink_imageview.setImageResource(R.drawable.ic_heart_blink1);

                      /*  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                        heartblink_imageview.setLayoutParams(layoutParams);
*/

                    } else {

                        heartblink_imageview.setImageResource(R.drawable.asset34);


                    }


                } else {

                    heartblink_imageview.setImageResource(R.drawable.asset34);


                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                heartblink_imageview.setImageResource(R.drawable.asset34);


                progressDialog.dismiss();

            }

        }
    }

    private boolean isCustomerLoggedIn() {



        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            return true;
        } else {
            Utility.writeToSharedPreference(mContext, GlobalValues.OPENLOGIN,"Close");
            return false;
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

//       client_mincart_quantity_bento             client_minimum_cart_quantity_enable : "1"


                    String client_mincart_quantity_bento = jsonResultSet.optString("client_mincart_quantity_bento");

                    String client_minimum_cart_quantity_enable = jsonResultSet.optString("client_minimum_cart_quantity_enable");

                    String client_tax_surcharge = jsonResultSet.optString("client_tax_surcharge_inclusive");
                    //           String client_tax_surcharge = jsonResultSet.optString("client_tax_surcharge");


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


                    } else {
                        GlobalValues.COMMON_DELIVERY_CHARGE = 0.0;
                    }

                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

            //        showAlertDialog(jsonObject.optString("message"));
                }

            } catch (Exception e) {
                e.printStackTrace();

          //      showAlertDialog("Something went wrong. Please try again later.");
            } finally {
                //progressDialog.dismiss();

            }

        }
    }
}
