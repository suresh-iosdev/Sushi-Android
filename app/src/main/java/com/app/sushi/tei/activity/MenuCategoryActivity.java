package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;

import com.app.sushi.tei.fragment.FiveMenu.FragmentReward;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.view.GravityCompat;
import androidx.viewpager.widget.ViewPager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
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

import com.app.sushi.tei.CustomViews.CustomViewPager.WrapContentViewPager1;
import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.FragmentRefreshListener;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.Model.Home.SubCategory;
import com.app.sushi.tei.Model.NavigateMenu;
import com.app.sushi.tei.Model.Search.SearchProduct;
import com.app.sushi.tei.Model.BannerModel;
import com.app.sushi.tei.Model.banner.ResultSetItem;
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.CategoryNewAdapter;
import com.app.sushi.tei.adapter.NavigationAdapter;
import com.app.sushi.tei.adapter.ProductHotMenuAdapter;
import com.app.sushi.tei.adapter.SliderBannerAdapter;
import com.app.sushi.tei.adapter.SubCategory.SubCategoryPagerAdapter;
import com.app.sushi.tei.dialog.AlertDialog;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.ClearCartMessageDialog;
import com.app.sushi.tei.dialog.MessageDialog;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.DefaultSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

public class MenuCategoryActivity extends AppCompatActivity {

    private Context context;
    private Toolbar toolbar;
    private LinearLayout toolbarMenuNav;
    private View moveIconFromLeftMenuCategory;
    private LinearLayout toolbarBack;
    private TextView checkOut, itemNames;
    private TabLayout tabLayout;
    public WrapContentViewPager1 viewpager;
    private TextView txtTabItem = null;
    private String mCustomerId = "", mReferenceId = "";

    private SubCategoryPagerAdapter subCategoryPagerAdapter;
    private String categoryname = "";
    private String mCategorySlug = "";
    private String mCategoryBasePath = "", mmainImagePath = "";
    private String mSubCategoryBasePath = "";
    private ArrayList<MainCategory> CategoryList;
    //private ImageView categoryImage;
    private int mPosition;
    public MenuItem cartWBadge;
    public MenuItem menuSearch;
    public View viewBadge;
    private Double totalAmtPrice = 0.0;


    public FragmentRefreshListener getFragmentRefreshListener() {
        return fragmentRefreshListener;
    }

    public void setFragmentRefreshListener(FragmentRefreshListener fragmentRefreshListener) {
        this.fragmentRefreshListener = fragmentRefreshListener;
    }

    private FragmentRefreshListener fragmentRefreshListener;
    int no_of_categories = -1;
    private RelativeLayout layoutCart, layoutSearch;
    private TextView txtCartCount, txtCardName;
    private String cartCount = "";
    //private TextView catgery_description;
    private String pro_cat_description;
    public static TextView totalAmt;
    TextView txtView;
    public static ImageView heartblink_imageview, user_imageview;
    private View moveIcon;
    private ArrayList<Favouriteitems> favouriteitemsArrayList = new ArrayList<>();
    private DrawerLayout drawerLayout;
    private RelativeLayout navigationView;
    private View content;
    private static final float END_SCALE = 0.9f;
    private Intent intent;
    public static LinearLayout layoutSignIn, layoutSignOut;
    public static TextView txtSignedUsername;
    public static TextView txtModifierPrice;
    public static int mModifierQuantity = 1, mSetMenuQuantity = 1;
    public static Double mSearchProuductprise = 0.0, mSetmenuoverallprices = 0.0, mSetMenuPrice = 0.0, mSetMenuBasePrice = 0.0;
    public static Double mquanititycost_src = 0.00, mModifierPrice = 0.00, quantityCost = 0.00;
    public static TextView txtNotificationCount, txtOrderCount, txtPromotionCount, txtVoucherCount;
    public static Double doubletitle = 0.0;
    public static Double plusminusPriceValue = 0.0;
    public static String switchoutletfromCartActivity = "notshow";
    public static int cutOffTime;
    private LinearLayout layout_bottom;
    static boolean resultSet = true;
    private TextView outletName, txtNoRecords;
    private ArrayList<SubCategory> subCategoryList;
    private ArrayList<MainCategory> mainCategoryList;
    private LinearLayout imgBack;
    private String StatusFav = "0";
    private LinearLayout layout_takeaway_enable;
    private TextView txt_delivery_disable, txt_takeaway, txt_delivery, txt_takeaway_disable;
    private RelativeLayout layoutHome, layoutAccount, layoutViewOrder, layoutRewards,
            layoutPromotions, layoutVouchers, layoutNotification, layoutAbout,
            layoutSettings, layoutWhatshappening,layout_terms,layout_refund,layout_privacy,layout_contact;
    private DatabaseHandler databaseHandler;
    private RelativeLayout aboutBackLayout, submenuTopLayout;
    private RelativeLayout mainMenuLayout, subMenuLayout;
    private RecyclerView subRecyclerView;
    /*private ViewPager viewPager_banner;
    private ImageView[] dots;*/
    private ArrayList<ResultSetItem> bannerArrayList;
    /*private int slideCount;
    private LinearLayout sliderDotspanel;
    private SlidingImageAdapter mAdapter;*/
    //Banner slider
    private SliderLayout mDemoSlider;
    ImageView imgLeft, imgRight;
    private TextView txt_addressFrom, txt_outletAddress;
    public static double cart_sub_total = 0.00;
    public static boolean cartListTask = true;
    public static double value;


    RecyclerView recycle_category;
    RecyclerView recycle_hot_menu;
    ProductHotMenuAdapter productHotMenuAdapter;

    private List<SearchProduct> searchProductList;
    private String mBasePath = "";
    private TextView txtNoRecordsHotMenu, txt_hello_user, txt_wallet_amount, txt_login_register;


    ViewPager viewPager;
    LinearLayout SliderDots;
    private ImageView[] dots;
    private List<BannerModel> sliderBannerAdapterList;
    private SliderBannerAdapter sliderBannerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_sub_category);

        context = this;
        toolbar = findViewById(R.id.toolBar);
        toolbarMenuNav = findViewById(R.id.toolbarMenuNav);
        moveIconFromLeftMenuCategory = findViewById(R.id.moveIconFromLeftMenuCategory);
        toolbarMenuNav.setVisibility(View.VISIBLE);
        moveIconFromLeftMenuCategory.setVisibility(View.VISIBLE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(null);

        toolbarBack = findViewById(R.id.toolbarBack);
        //categoryImage = findViewById(R.id.categoryImage);
        //catgery_description = findViewById(R.id.catgegory_description);
        txt_hello_user = findViewById(R.id.txt_hello_user);
        txt_login_register = findViewById(R.id.txt_login_register);
        txt_wallet_amount = findViewById(R.id.txt_wallet_amount);
        totalAmt = findViewById(R.id.totalAmt);
        heartblink_imageview = (ImageView) toolbar.findViewById(R.id.heartblink_imageview);
        user_imageview = toolbar.findViewById(R.id.user_imageview);
        txtNoRecordsHotMenu = findViewById(R.id.txtNoRecordsHotMenu);
        recycle_category = findViewById(R.id.recycle_category);
        recycle_hot_menu = findViewById(R.id.recycle_hot_menu);
        moveIcon = toolbar.findViewById(R.id.moveIcon);
        moveIcon.setVisibility(View.VISIBLE);
        heartblink_imageview.setVisibility(View.GONE);
        checkOut = findViewById(R.id.checkOut);
        //itemNames = findViewById(R.id.itemNames);
        drawerLayout = findViewById(R.id.drawerLayout);
        navigationView = findViewById(R.id.navigationView);
        content = findViewById(R.id.content);
        layoutSignIn = findViewById(R.id.layoutSignIn);
        layoutSignOut = findViewById(R.id.layoutSignOut);
        txtSignedUsername = findViewById(R.id.txtSignedUsername);
        txtNotificationCount = findViewById(R.id.txtNotificationCount);
        txtPromotionCount = findViewById(R.id.txtPromotionCount);
        txtVoucherCount = findViewById(R.id.txtVoucherCount);
        txtOrderCount = findViewById(R.id.txtOrderCount);
        layout_bottom = findViewById(R.id.layout_bottom);
        txtNoRecords = findViewById(R.id.txtNoRecords);
        imgBack = findViewById(R.id.toolbarBack);
        imgBack.setVisibility(View.GONE);
        txt_delivery_disable = findViewById(R.id.txt_delivery_disable);
        txt_takeaway = findViewById(R.id.txt_takeaway);
        txt_delivery = findViewById(R.id.txt_delivery);
        txt_takeaway_disable = findViewById(R.id.txt_takeaway_disable);
        layout_takeaway_enable = findViewById(R.id.layout_takeaway_enable);
        layoutHome = findViewById(R.id.layoutHome);
        layoutAccount = findViewById(R.id.layoutAccount);
        layoutViewOrder = findViewById(R.id.layoutViewOrder);
        layoutRewards = findViewById(R.id.layoutRewards);
        layoutPromotions = findViewById(R.id.layoutPromotions);
        layoutVouchers = findViewById(R.id.layoutVouchers);
        layoutNotification = findViewById(R.id.layoutNotification);
        layoutAbout = findViewById(R.id.layoutAbout);
        layout_terms = findViewById(R.id.layout_terms);
        layout_refund = findViewById(R.id.layout_refund);
        layout_privacy = findViewById(R.id.layout_privacy);
        layout_contact=findViewById(R.id.layout_contact);
        layoutSettings = findViewById(R.id.layoutSettings);
        databaseHandler = DatabaseHandler.getInstance(context);
        aboutBackLayout = findViewById(R.id.aboutBackLayout);
        submenuTopLayout = findViewById(R.id.submenuTopLayout);
        mainMenuLayout = findViewById(R.id.mainMenuLayout);
        subMenuLayout = findViewById(R.id.subMenuLayout);
        subRecyclerView = findViewById(R.id.subRecyclerView);
        mDemoSlider = findViewById(R.id.slider);
        imgLeft = findViewById(R.id.imgLeft);
        imgRight = findViewById(R.id.imgRight);
        txt_addressFrom = findViewById(R.id.txt_addressFrom);
        txt_outletAddress = findViewById(R.id.txt_outletAddress);

        viewPager = findViewById(R.id.viewPager);
        SliderDots = findViewById(R.id.SliderDots);
        if (Utility.networkCheck(context)) {
            String app_id = "?app_id=" + GlobalValues.APP_ID;
            String url = GlobalUrl.SETTINGS_URL + app_id;
            Log.e("TAG","CoomonTest::"+url);
            new SettingsRequest().execute(url);

        } else {
            Toast.makeText(context,"You are offline please check your internet connection",Toast.LENGTH_SHORT).show();
        }
        getRewardsPoints();

        if (!isCustomerLoggedIn()) {
            Log.e("TAG", "Reward_test_1::" + GlobalValues.CUSTOMER_REWARD_POINT_NEW);
            Calendar c = Calendar.getInstance();
            int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

            if (timeOfDay >= 0 && timeOfDay < 12) {
                txt_hello_user.setText("Good Morning");
                //          Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
            } else if (timeOfDay >= 12 && timeOfDay < 16) {
                txt_hello_user.setText("Good Afternoon");
                //           Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
            } else if (timeOfDay >= 16 && timeOfDay < 21) {
                txt_hello_user.setText("Good Evening");
                //           Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
            } else if (timeOfDay >= 21 && timeOfDay < 24) {
                txt_hello_user.setText("Good Evening");
                //           Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
            }
            txt_login_register.setVisibility(View.VISIBLE);
            txt_wallet_amount.setVisibility(View.GONE);

        } else {
            txt_login_register.setVisibility(View.GONE);
            txt_wallet_amount.setVisibility(View.VISIBLE);
            Log.e("TAG", "Reward_test::" + GlobalValues.CUSTOMER_REWARD_POINT_NEW);
            txt_hello_user.setText("Hello! " + Utility.readFromSharedPreference(context, GlobalValues.FIRSTNAME) + " " +
                    Utility.readFromSharedPreference(context, GlobalValues.LASTNAME));
           /* if (GlobalValues.CUSTOMER_REWARD_POINT_NEW != null && !GlobalValues.CUSTOMER_REWARD_POINT_NEW.equals("0")
                    && !GlobalValues.CUSTOMER_REWARD_POINT_NEW.equals("")) {

                txt_wallet_amount.setText("ST $ " + String.format("%.2f", Double.parseDouble(GlobalValues.CUSTOMER_REWARD_POINT_NEW)));

            } else {
                txt_wallet_amount.setVisibility(View.GONE);
            }*/
        }
        txt_login_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MenuCategoryActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

        user_imageview.setVisibility(View.VISIBLE);

        user_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCustomerLoggedIn()) {
                    Intent intent = new Intent(MenuCategoryActivity.this, MemberBenefitActivity.class);
                    startActivity(intent);
                } else {
                    drawerLayout.closeDrawers();
                    openFiveMenuActivity(0);
                }
            }
        });

        setUpNewCategoryPage();

        imgLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDemoSlider.movePrevPosition();
            }
        });

        imgRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mDemoSlider.moveNextPosition();
            }
        });


        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID);

        txt_delivery_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //                hide below for not implementing delivery
                Toast.makeText(MenuCategoryActivity.this, "This feature is not available", Toast.LENGTH_SHORT).show();

//                {
//                    String minQual = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
//                    if (minQual.equalsIgnoreCase("")) {
//                        minQual = "0";
//                    }
//                    if (Integer.parseInt(minQual) >= 1) {
//                        String message = "You are about to clear your cart by changing pick-up to delivery!";
//                        new ClearCartMessageDialog(context, message, new ClearCartMessageDialog.OnSlectedMethod() {
//                            @Override
//                            public void selectedAction(String action) {
//                                if (action.equalsIgnoreCase("YES")) {
//                                    clearCart();
//                                    invalidateOptionsMenu();
//                                /*txt_delivery_disable.setVisibility(View.GONE);
//                                txt_takeaway.setVisibility(View.GONE);
//                                txt_delivery.setVisibility(View.VISIBLE);
//                                txt_takeaway_disable.setVisibility(View.VISIBLE);*/
//                                    Intent intent = new Intent(context, ChooseOutletActivity.class);
//                                    intent.putExtra("availability", "delivery");
//                                    startActivity(intent);
//                                    //finish();
//                                }
//                            }
//                        });
//                    } else {
//                    /*txt_delivery_disable.setVisibility(View.GONE);
//                    txt_takeaway.setVisibility(View.GONE);
//                    txt_delivery.setVisibility(View.VISIBLE);
//                    txt_takeaway_disable.setVisibility(View.VISIBLE);*/
//                        Intent intent = new Intent(context, ChooseOutletActivity.class);
//                        intent.putExtra("availability", "delivery");
//                        startActivity(intent);
//                        //finish();
//                    }
//                }
            }
        });

        txt_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minQual = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
                if (minQual.equalsIgnoreCase("")) {
                    minQual = "0";
                }
                if (Integer.parseInt(minQual) >= 1) {
                    String message = "You are about to clear your cart by switching from one oulet to another!";
                    new ClearCartMessageDialog(context, message, new ClearCartMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                clearCart();
                                invalidateOptionsMenu();
                                /*txt_delivery_disable.setVisibility(View.VISIBLE);
                                txt_takeaway.setVisibility(View.VISIBLE);
                                txt_delivery.setVisibility(View.GONE);
                                txt_takeaway_disable.setVisibility(View.GONE);*/
                                Intent intent = new Intent(context, ChooseOutletActivity.class);
                                intent.putExtra("availability", "delivery");
                                startActivity(intent);
                                //finish();
                            }
                        }
                    });
                } else {
                    /*txt_delivery_disable.setVisibility(View.VISIBLE);
                    txt_takeaway.setVisibility(View.VISIBLE);
                    txt_delivery.setVisibility(View.GONE);
                    txt_takeaway_disable.setVisibility(View.GONE);*/
                    Intent intent = new Intent(context, ChooseOutletActivity.class);
                    intent.putExtra("availability", "delivery");
                    startActivity(intent);
                    //finish();
                }
            }
        });

        txt_takeaway.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minQual = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
                if (minQual.equalsIgnoreCase("")) {
                    minQual = "0";
                }
                if (Integer.parseInt(minQual) >= 1) {
                    String message = "You are about to clear your cart by switching from one oulet to another!";
                    new ClearCartMessageDialog(context, message, new ClearCartMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                clearCart();
                                invalidateOptionsMenu();
                                /*txt_delivery_disable.setVisibility(View.VISIBLE);
                                txt_takeaway.setVisibility(View.VISIBLE);
                                txt_delivery.setVisibility(View.GONE);
                                txt_takeaway_disable.setVisibility(View.GONE);*/
                                Intent intent = new Intent(context, ChooseOutletActivity.class);
                                intent.putExtra("availability", "takeaway");
                                startActivity(intent);
                                //finish();
                            }
                        }
                    });
                } else {
                    /*txt_delivery_disable.setVisibility(View.VISIBLE);
                    txt_takeaway.setVisibility(View.VISIBLE);
                    txt_delivery.setVisibility(View.GONE);
                    txt_takeaway_disable.setVisibility(View.GONE);*/
                    Intent intent = new Intent(context, ChooseOutletActivity.class);
                    intent.putExtra("availability", "takeaway");
                    startActivity(intent);
                    //finish();
                }
            }
        });

        txt_takeaway_disable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String minQual = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
                if (minQual.equalsIgnoreCase("")) {
                    minQual = "0";
                }
                if (Integer.parseInt(minQual) >= 1) {
                    String message = "You are about to clear your cart by changing delivery to pick-up!";
                    new ClearCartMessageDialog(context, message, new ClearCartMessageDialog.OnSlectedMethod() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("YES")) {
                                clearCart();
                                invalidateOptionsMenu();
                                /*txt_delivery_disable.setVisibility(View.VISIBLE);
                                txt_takeaway.setVisibility(View.VISIBLE);
                                txt_delivery.setVisibility(View.GONE);
                                txt_takeaway_disable.setVisibility(View.GONE);*/
                                Intent intent = new Intent(context, ChooseOutletActivity.class);
                                intent.putExtra("availability", "takeaway");
                                startActivity(intent);
                                //finish();
                            }
                        }
                    });
                } else {
                    /*txt_delivery_disable.setVisibility(View.VISIBLE);
                    txt_takeaway.setVisibility(View.VISIBLE);
                    txt_delivery.setVisibility(View.GONE);
                    txt_takeaway_disable.setVisibility(View.GONE);*/
                    Intent intent = new Intent(context, ChooseOutletActivity.class);
                    intent.putExtra("availability", "takeaway");
                    startActivity(intent);
                    //finish();
                }
            }
        });

       /*if(Utility.readFromSharedPreference(context, GlobalValues.CURRENT_AVAILABLITY_ID).equalsIgnoreCase(GlobalValues.DELIVERY_ID)){
           Utility.writeToSharedPreference(context, GlobalValues.AVALABILITY_ID,
                   GlobalValues.DELIVERY_ID);
       }else{
           Utility.writeToSharedPreference(context, GlobalValues.AVALABILITY_ID,
                   GlobalValues.TAKEAWAY_ID);
       }*/

//        showCategory();

        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {

            txt_delivery.setVisibility(View.VISIBLE);
            txt_takeaway_disable.setVisibility(View.VISIBLE);
            txt_takeaway.setVisibility(View.GONE);
//            txt_delivery_disable.setVisibility(View.GONE);

            txt_addressFrom.setText("Delivery From");
            txt_outletAddress.setText(Utility.readFromSharedPreference(context, GlobalValues.OUTLET_NAME) + "\n" + Utility.readFromSharedPreference(context, GlobalValues.CURRENT_OUTLET_ADDRESS)
                    + " Singapore, " + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_PINCODE));

        } else {

            txt_delivery.setVisibility(View.GONE);
            txt_takeaway_disable.setVisibility(View.GONE);
            txt_takeaway.setVisibility(View.VISIBLE);
//            txt_delivery_disable.setVisibility(View.VISIBLE);
            txt_addressFrom.setText("PickUp From");
            txt_outletAddress.setText(Utility.readFromSharedPreference(context, GlobalValues.OUTLET_NAME) + "\n" + Utility.readFromSharedPreference(context, GlobalValues.CURRENT_OUTLET_ADDRESS)
                    + " Singapore, " + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_PINCODE));
        }
        /*Intent intentValues = getIntent();
        if (intentValues.getExtras() != null) {
            categoryname = intentValues.getStringExtra(GlobalValues.CATEGORY_NAME);
            pro_cat_description = intentValues.getStringExtra(GlobalValues.CATEGORY_DESCRIPTION);
            CategoryList = intentValues.getParcelableArrayListExtra(GlobalValues.CATEGORY_LIST);
            categoryname = categoryname.toUpperCase();
            String position = intentValues.getStringExtra("Position");
            mPosition = Integer.parseInt(position);
            mmainImagePath = intentValues.getStringExtra("MainImagePath");
        }*/

        heartblink_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(context, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(context, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }

                        }
                    });
                } else {
                    Intent intent = new Intent(context, FavouriteActivity.class);
                    startActivity(intent);
                }
            }
        });





        Drawable drawable = ResourcesCompat.getDrawable(getResources(), R.drawable.menu_01, getTheme());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            drawable.setTint(getResources().getColor(R.color.colorWhite));
//        }else {

//            Bitmap bitmap = null;
//            if (drawable != null) {
//                bitmap = ((BitmapDrawable) drawable).getBitmap();
//            }
//            Drawable newdrawable = new BitmapDrawable(getResources(), Bitmap.createScaledBitmap(bitmap, 60, 60, true));
//            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                newdrawable.setTint(getResources().getColor(R.color.colorWhite));
//            }
//        }
        getSupportActionBar().setHomeButtonEnabled(false);
        getSupportActionBar().setDisplayHomeAsUpEnabled(false);
//        toolbar.setNavigationIcon(drawable);
//        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (drawerLayout.isDrawerOpen(navigationView)) {
//                    drawerLayout.closeDrawer(navigationView);
//                } else {
//                    drawerLayout.openDrawer(navigationView);
//                }
//            }
//        });
        toolbarMenuNav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
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
//                layout_bottom.setVisibility(View.GONE);
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
//                    layout_bottom.setVisibility(View.VISIBLE);
                }
            }
        });

        try {

            int v = getIntent().getIntExtra("view_order", 0);

            int p = getIntent().getIntExtra(GlobalValues.FROM_NOTIFICATION_POSITION, 0);


            if (v == 1) {

                intent = new Intent(context, FiveMenuActivityNew.class);
                intent.putExtra("position", 1);
                startActivity(intent);
            } else if (p == 1) {
                openFiveMenuActivity(p);
            } else if (p == 2) {
                openFiveMenuActivity(p);
            } else if (p == 3) {
                openFiveMenuActivity(p);
            } else if (p == 4) {
                openFiveMenuActivity(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {

            layoutSignIn.setVisibility(View.GONE);
            //txtSignedUsername.setVisibility(View.VISIBLE);
            txtSignedUsername.setVisibility(View.GONE);
             //txtSignedUsername.setText("Signed in as " + Utility.readFromSharedPreference(context, GlobalValues.EMAIL));
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
                    //                  intent = new Intent(context, SubCategoryActivity.class);
                    intent = new Intent(context, MenuCategoryActivity.class);
                    startActivity(intent);
//                    finish();
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
                    Intent i = new Intent(MenuCategoryActivity.this, SettingsActivity.class);
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

        tabLayout = (TabLayout) findViewById(R.id.tablayoutFiveMenu);
        viewpager = findViewById(R.id.viewpagerFiveMenu);

//        viewpager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));

//        tabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);
//
//
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                Utility.writeToSharedPreference(context, GlobalValues.SELECTEDPOSITION, String.valueOf(tab.getPosition()));
//                View v = tab.getCustomView();
//
//                viewpager.setCurrentItem(tab.getPosition());
//
//
//
//                txtView = (TextView) v.findViewById(R.id.txtTabItem);
//                TextView txtBadge = (TextView) v.findViewById(R.id.txtBadge);
//
//                //txtView.setTextColor(getResources().getColor(R.color.colorSelectedTab));
//
//                if (tab.getPosition() == 0) {
//                    txtBadge.setVisibility(View.GONE);
//                }
//
//                if (!(CategoryList.get(tab.getPosition()).getmCategoryName().equalsIgnoreCase("")
//                        || CategoryList.get(tab.getPosition()).getmCategoryName().equalsIgnoreCase("null"))) {
//
//                    //itemNames.setText(CategoryList.get(tab.getPosition()).getmCategoryName());
//                }
//
//                if (!(CategoryList.get(tab.getPosition()).getPro_cat_description().equalsIgnoreCase("")
//                        || CategoryList.get(tab.getPosition()).getPro_cat_description().equalsIgnoreCase("null"))) {
//
//                    // catgery_description.setText(CategoryList.get(tab.getPosition()).getPro_cat_description());
//                } else {
//
//                    // catgery_description.setText("");
//                }
//
//
//              /*  if (CategoryList.get(tab.getPosition()).getmActiveImage() != null &&
//                        CategoryList.get(tab.getPosition()).getmCategoryName().length() > 0) {
//
//                    Picasso.with(context).load(mmainImagePath + CategoryList.get(tab.getPosition()).getmActiveImage()).
//                            error(R.drawable.default_image).into(categoryImage);
//
//                } else {
//
//                    Picasso.with(context).load(R.drawable.default_image).into(categoryImage);
//                }*/
//
//
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//                View v = tab.getCustomView();
//
//                TextView txtView = (TextView) v.findViewById(R.id.txtTabItem);
//                TextView txtBadge = (TextView) v.findViewById(R.id.txtBadge);
//
//                txtView.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
//
//                if (tab.getPosition() == 0) {
//                    txtBadge.setVisibility(View.GONE);
//                }
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });
//        TabLayout.Tab tab = tabLayout.getTabAt(mPosition);
//        if (tab != null) {
//            tab.select();
//        }

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        checkOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String MinQual = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
                if (!MinQual.equals("")) {
                    if (Integer.parseInt(MinQual) >= 1) {
                        String message = "Do you want to add more items?";
                        new CheckOutMessageDialog(context, message, new CheckOutMessageDialog.OnSlectedMethod() {
                            @Override
                            public void selectedAction(String action) {

                                if (action.equalsIgnoreCase("YES")) {

                                } else {
//                                    Intent intent = new Intent(context, CartActivity.class);
                                    Intent intent = new Intent(context, OrderSummaryActivity.class);
                                    startActivity(intent);
                                }
                            }
                        });
                    }
                }
            }
        });


        if (Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE).length() > 0) {

            totalAmtPrice = Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE));
            totalAmt.setText(String.valueOf(totalAmtPrice));
        }

        loadBannerImages();

//        setupSlide();//test
//
//        new Handler().postDelayed(new Runnable(){//test
//            @Override
//            public void run() {
//                /* Create an Intent that will start the Menu-Activity. */
////                Toast.makeText(MenuCategoryActivity.this, "Test Login", Toast.LENGTH_SHORT).show();
//////              Utility.removeFromSharedPreference(mContext, GlobalValues.CART_COUNT);
////                intent = new Intent(MenuCategoryActivity.this, ChooseOutletActivity.class);
////                startActivity(intent);
////                finish();
//
//                if (Utility.readFromSharedPreference(MenuCategoryActivity.this, GlobalValues.OUTLET_ID).equalsIgnoreCase("")
//                        || Utility.readFromSharedPreference(MenuCategoryActivity.this, GlobalValues.OUTLET_ID).equalsIgnoreCase("null")) {
//                    intent = new Intent(MenuCategoryActivity.this, ChooseOutletActivity.class);
//                    startActivity(intent);
//                    finish();
//                    return;
//                } else {
//                    intent = new Intent(MenuCategoryActivity.this, SubCategoryActivity.class);
//                    startActivity(intent);
//                    finish();
//                }
//            }
//        }, 2000);
//

    }

    private void getRewardsPoints() {
        Map<String, String> params = new HashMap<>();
        params.put("app_id", GlobalValues.APP_ID);
        params.put("member_id", Utility.readFromSharedPreference(context,GlobalValues.MEMBERSHIP_ID));
        String reward_url = GlobalUrl.MEMBER_ENQUIRY_URL;

        new MemberRewards(params).execute(reward_url);
    }

    public void loadBannerImages() {
        if (Utility.networkCheck(context)) {
            String URL = GlobalUrl.BANNER_URL + "?app_id=" + GlobalValues.APP_ID + "&availability=" + GlobalValues.TAKEAWAY_ID;
            new BannerTask().execute(URL);
        } else {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private class BannerTask extends AsyncTask<String, String, String> {

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

            if (s != null) {


                JSONObject jsonObject = null;
                try {

                    jsonObject = new JSONObject(s);

                    if (jsonObject.getString("status").equals("ok")) {
                        JSONObject commonJson = jsonObject.getJSONObject("common");

                        String base_imageURL = commonJson.getString("image_source") + "/";
                        bannerArrayList = new ArrayList<>();

                        JSONArray resultSetJSONArray = jsonObject.getJSONArray("result_set");
                         if (resultSetJSONArray.length() > 0) {
                             for (int i = 0; i < resultSetJSONArray.length(); i++) {
                                JSONObject bannerJson = resultSetJSONArray.getJSONObject(i);
                                ResultSetItem resultItem = new ResultSetItem();
                                resultItem.setBannerImage(bannerJson.getString("banner_image"));
                                bannerArrayList.add(resultItem);
                            }
                            setUpCustomBanner(base_imageURL, bannerArrayList);


                            int z = 0;
                            for (ResultSetItem banner : bannerArrayList.subList(0, bannerArrayList.size())) {

                                z++;
                                DefaultSliderView textSliderView = new DefaultSliderView(context);


                                // initialize a SliderLayout
                                textSliderView
                                        //  .description(name)
                                        .image(base_imageURL + banner.getBannerImage())
                                        .setScaleType(BaseSliderView.ScaleType.Fit)
                                        .setOnSliderClickListener(new BaseSliderView.OnSliderClickListener() {
                                            @Override
                                            public void onSliderClick(BaseSliderView slider) {

                                            }
                                        });

                                //add your extra information
                                textSliderView.bundle(new Bundle());
                           /* textSliderView.getBundle()
                                    .putString("extra",name);*/

                                mDemoSlider.addSlider(textSliderView);
                            }
                            // mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Accordion);
                            // mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
                            mDemoSlider.setCustomAnimation(new DescriptionAnimation());
                            mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(0xff497236, 0xff101010);
                            // mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(R.color.greendark, R.color.graydark);
                            mDemoSlider.setDuration(5000);
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
                        } else {

                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();


                } finally {
                    progressDialog.dismiss();
                }
            }
        }
    }

    private void tabnewadded(String searchkey) {
        intent = new Intent(context, CmsActivity.class);
        intent.putExtra("TITLE", "Terms_and_extra");
        intent.putExtra("SEARCH_KEY", searchkey);
        intent.putExtra("landingPage", true);
        startActivity(intent);
    }

    private void getAboutUs() {

       /* String app_id = "?app_id=" + GlobalValues.APP_ID;
        String slug = "&menu_slug=Main-menu";
        String menuUrl = GlobalUrl.MENU_URL + app_id + slug;*/
       /* String app_id = "?app_id=" + GlobalValues.APP_ID;
        String slug = "&menu_slug=mobile-app-menu";
        String menuUrl = GlobalUrl.MENU_URL + app_id + slug;
        if (Utility.networkCheck(context)) {
            new MenuTask().execute(menuUrl);
        } else {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }*/

        intent = new Intent(context, CmsActivity.class);
        intent.putExtra("TITLE", "About Us");
        intent.putExtra("SEARCH_KEY", "https://sushitei.com/about-us/");
        intent.putExtra("landingPage", true);
        startActivity(intent);

//        Uri uri = Uri.parse("https://sushitei.com/about-us/");
//        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
//        startActivity(intent);
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


                            NavigationAdapter navigationAdapter = new NavigationAdapter(MenuCategoryActivity.this, navigateMenuArrayList);

                            subRecyclerView.setLayoutManager(new LinearLayoutManager(context));
                            subRecyclerView.setAdapter(navigationAdapter);

                            mainMenuLayout.setVisibility(View.GONE);
                            subMenuLayout.setVisibility(View.VISIBLE);


                        }
                    } else {
                        Toast.makeText(MenuCategoryActivity.this, responseJSONObject.getString("message"), Toast.LENGTH_LONG).show();


                    }

                } catch (Exception e) {
                    e.printStackTrace();


                } finally {
                    progressDialog.dismiss();
                }


            }
        }

    }

    private void showCategory() {

        if (Utility.networkCheck(context)) {
            // outletName.setText(  Utility.readFromSharedPreference(context, GlobalValues.OUTLET_NAME));

            //availability=7B30BB03-14BD-47E4-B9B1-9731F9A3BC9C&outletId=151
            String url = GlobalUrl.CATEGORY_URL + "?app_id=" + GlobalValues.APP_ID + "&availability="
                    + Utility.readFromSharedPreference(context, GlobalValues.AVALABILITY_ID) + "&outletId="
                    + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID);

            new CategoryTask().execute(url);


        } else {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private void showHotMenuProducts() {

        if (Utility.networkCheck(context)) {
            // outletName.setText(  Utility.readFromSharedPreference(context, GlobalValues.OUTLET_NAME));

            //availability=7B30BB03-14BD-47E4-B9B1-9731F9A3BC9C&outletId=151
            String url = GlobalUrl.HOT_MENU_PRODUCTS_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&outlet_id=" + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID);

            new HotMenuProductsTask().execute(url);


        } else {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }

    private class HotMenuProductsTask extends AsyncTask<String, Void, String> {

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

                progressDialog.dismiss();

                JSONObject jsonObject = new JSONObject(s);

                 // String galleryPath = jsonObject.getJSONObject("common").optString("product_gallery_image_source");
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject commonJson = jsonObject.getJSONObject("common");

                    mBasePath = commonJson.getString("image_source") + "/";

                    JSONArray resultArray = jsonObject.getJSONArray("result_set");
                    //String galleryPath = jsonObject.getJSONObject("common").optString("product_gallery_image_source");
                    searchProductList = new ArrayList<>();

                    if (resultArray.length() > 0) {

                        for (int i = 0; i < resultArray.length(); i++) {
                            {

                                JSONObject jsonProduct = resultArray.getJSONObject(i);


                                SearchProduct searchProduct = new SearchProduct();
                                searchProduct.setmProductId(jsonProduct.getString("product_id"));
                                searchProduct.setProduct_alias(jsonProduct.getString("product_alias"));
                                searchProduct.setmProductType(jsonProduct.getString("product_type"));
                                searchProduct.setmProductName(jsonProduct.getString("product_alias"));
                                searchProduct.setmProductSlug(jsonProduct.getString("product_slug"));
                                searchProduct.setProductPrice(jsonProduct.getString("product_price"));

                                searchProduct.setProduct_alias(jsonProduct.getString("product_alias"));
                                searchProduct.setmProductType(jsonProduct.getString("product_type"));
                                searchProduct.setmProductName(jsonProduct.getString("product_alias"));
                                searchProduct.setmProductSlug(jsonProduct.getString("product_slug"));
                                searchProduct.setProductPrice(jsonProduct.getString("product_price"));

                                searchProduct.setmProductCategoryName(jsonProduct.getString("catgory_name"));


                                String subImageUrl=jsonProduct.optString("product_thumbnail");

                                if(jsonProduct.getString("product_thumbnail").length()>0) {
                                    searchProduct.setmProductGalleryImage(mBasePath + jsonProduct.getString("product_thumbnail"));
                                }


                                searchProduct.setmProduct_primary_id(jsonProduct.getString("product_primary_id"));
                                try {
                                    if (Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0) {
                                        if (!jsonProduct.isNull("fav_product_primary_id")) {
                                            searchProduct.setmProduct_favourite_id(jsonProduct.getString("fav_product_primary_id"));
                                        }
                                    } else {
                                        searchProduct.setmProduct_favourite_id("");
                                    }
                                } catch (JSONException e) {
                                    searchProduct.setmProduct_favourite_id("");
                                    e.printStackTrace();
                                }

                                searchProductList.add(searchProduct);

                            }
                        }
                    }

                    if (searchProductList.size() > 0) {


                        productHotMenuAdapter = new ProductHotMenuAdapter(context, searchProductList);
                        recycle_hot_menu.setLayoutManager(new LinearLayoutManager(context, LinearLayout.HORIZONTAL, false));

                        recycle_hot_menu.setVisibility(View.VISIBLE);
                        recycle_hot_menu.setAdapter(productHotMenuAdapter);

                        productHotMenuAdapter.setOnItemClick(new IOnItemClick() {
                            @Override
                            public void onItemClick(View v, int position) {
                                try {
                                    Log.e("TAG","CategorySelectedName::"+searchProductList.get(position).getmProductCategoryName());
                                    Utility.writeToSharedPreference(context, GlobalValues.PRODUCT_SELECTED, CategoryList.get(position).getmCategoryId());
                                    Utility.writeToSharedPreference(context,GlobalValues.CATEGORY_SELECTED_NAME,searchProductList.get(position).getmProductCategoryName());
                                    Intent intent = new Intent(context, SearchProductDetailsActivity.class);
                                    intent.putExtra("IMAGE_BASEPATH", mBasePath);
                                    intent.putExtra("SEARCH_PRODUCT", searchProductList.get(position));
                                    intent.putExtra("SEARCH_PRODUCT_PRICE", searchProductList.get(position).getProductPrice());
                                    intent.putExtra("SEARCH_PRODUCT_PrimarId", searchProductList.get(position).getmProduct_primary_id());
                                    intent.putExtra("SEARCH_PRODUCT_FavouriteId", searchProductList.get(position).getmProduct_favourite_id());

                                    startActivity(intent);
                                } catch (Exception exception) {
                                    exception.printStackTrace();
                                }


                            }
                        });
//                        recyclerViewSearch.setVisibility(View.VISIBLE);
//                        txtEmpty.setVisibility(View.GONE);
//
//                        searchAdapter = new SearchAdapter(mContext, searchProductList);
//
//                        recyclerViewSearch.setAdapter(searchAdapter);
                    } else {
                        recycle_hot_menu.setVisibility(View.GONE);
                        txtNoRecordsHotMenu.setVisibility(View.VISIBLE);
                    }


                    //txtText.setText("Your account has been activated successfully.");
                } else {
                    recycle_hot_menu.setVisibility(View.GONE);
                    txtNoRecordsHotMenu.setVisibility(View.VISIBLE);
                    txtNoRecordsHotMenu.setText(jsonObject.optString("message"));
                    // txtText.setText("Link Expired");
                }

            } catch (Exception e) {
                e.printStackTrace();
                recycle_hot_menu.setVisibility(View.GONE);
                txtNoRecordsHotMenu.setVisibility(View.VISIBLE);
            } finally {
                progressDialog.dismiss();
            }
        }
    }

    private class CategoryTask extends AsyncTask<String, Void, String> {

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
                Log.e("category_response::", s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject commonJson = jsonObject.getJSONObject("common");
                    mCategoryBasePath = commonJson.getString("category_image_url") + "/";
                    mSubCategoryBasePath = commonJson.getString("subcategory_image_url") + "/";

                    if (jsonObject.isNull("result_set")) {
                        resultSet = false;
                        txtNoRecords.setVisibility(View.VISIBLE);
                        //mRecyclerView.setVisibility(View.GONE);
                    } else {
                        resultSet = true;
                        txtNoRecords.setVisibility(View.GONE);
                        //mRecyclerView.setVisibility(View.VISIBLE);
                        JSONArray arrayResult = jsonObject.getJSONArray("result_set");

                        mainCategoryList = new ArrayList<>();

                        if (arrayResult.length() > 0) {

                            for (int j = 0; j < arrayResult.length(); j++) {

                                JSONObject categoryJson = arrayResult.getJSONObject(j);

                                MainCategory mainCategory = new MainCategory();

                                mainCategory.setmCategoryId(categoryJson.getString("pro_cate_primary_id"));
                                mainCategory.setmCategoryName(categoryJson.getString("category_name"));
                                mainCategory.setmActiveImage(mCategoryBasePath + categoryJson.optString("pro_cate_image"));
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

                            categoryname = mainCategoryList.get(0).getmCategoryName();
                            pro_cat_description = mainCategoryList.get(0).getPro_cat_description();
                            CategoryList = mainCategoryList;
                            categoryname = categoryname.toUpperCase();
                            mPosition = 0;
                            mmainImagePath = mCategoryBasePath;

                            //itemNames.setText(CategoryList.get(mPosition).getmCategoryName());

                            if (pro_cat_description != null) {

                                if (!pro_cat_description.isEmpty() && !pro_cat_description.equalsIgnoreCase("")) {

                                    //catgery_description.setText(pro_cat_description);
                                } else {

                                    //catgery_description.setText("");
                                }

                            } else {

                                //catgery_description.setText("");
                            }

                            if (CategoryList.get(mPosition).getmActiveImage() != null &&
                                    CategoryList.get(mPosition).getmCategoryName().length() > 0) {

                               /* Picasso.with(context).load(mmainImagePath + CategoryList.get(mPosition).getmActiveImage()).
                                        error(R.drawable.default_image).into(categoryImage);*/

                            } else {

                                // Picasso.with(context).load(R.drawable.default_image).into(categoryImage);
                            }

//                            viewpager.setOffscreenPageLimit(1);
//                            FragmentAdapter fragmentAdapter = new FragmentAdapter(getSupportFragmentManager(), MenuCategoryActivity.this, CategoryList);
//                            viewpager.setAdapter(fragmentAdapter);
//                            viewpager.setCurrentItem(0);

                            CategoryNewAdapter categoryNewAdapter = new CategoryNewAdapter(context, CategoryList);
                            recycle_category.setLayoutManager(new GridLayoutManager(context, 2));

                            recycle_category.setVisibility(View.VISIBLE);
                            recycle_category.setAdapter(categoryNewAdapter);


                            categoryNewAdapter.setOnItemClick(new IOnItemClick() {
                                @Override
                                public void onItemClick(View v, int position) {
                                    Utility.writeToSharedPreference(context, GlobalValues.CATEGORY_SELECTED, CategoryList.get(position).getmCategoryId());
                                    Utility.writeToSharedPreference(context, GlobalValues.CATEGORY_SELECTED_NAME, CategoryList.get(position).getmCategoryName());
                                    intent = new Intent(context, SubCategoryActivity.class);
                                    startActivity(intent);
                                }
                            });


                        }
                    }

                } else {

                    Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                }

            } catch (Exception e) {
                progressDialog.dismiss();
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


    public void updatecartvalues() {
        invalidateOptionsMenu();


        if (Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE).length() > 0) {

            totalAmtPrice = Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE));
            totalAmt.setText(String.valueOf(totalAmtPrice));
        }

        //  recreate();

        // finish();
        // Intent i=new Intent(context, SubCategoryActivity.class);
        //startActivity(i);

        //cartCount = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);


//        if (!cart_total_items.equalsIgnoreCase("") && !cartCount.isEmpty()) {
//
//            txtCartCount.setVisibility(View.VISIBLE);
//            txtCartCount.setText(cartCount);
//
//
//        } else {
//
//            txtCartCount.setVisibility(View.GONE);
//        }

    }


    @Override
    protected void onResume() {
        super.onResume();
        supportInvalidateOptionsMenu();
        invalidateOptionsMenu();
        getTotalAmount();
        getActiveCount();
        CheckFacourites();


        try {

            cartCount = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
     //       Log.e("TAG","CartAmounTestMenucar::"+Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE)));
            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

                txtCartCount.setVisibility(View.VISIBLE);
    // Hide 06_10_2022           layout_bottom.setVisibility(View.VISIBLE);
                layout_bottom.setVisibility(View.GONE);  // Hide 06_10_2022
                txtCartCount.setText(cartCount);
                if (Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE).length()>0) {
                    totalAmtPrice = Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE));
                    totalAmt.setText(String.valueOf(totalAmtPrice));
                }



            } else {
                layout_bottom.setVisibility(View.GONE);
                txtCartCount.setVisibility(View.GONE);

                totalAmt.setText("0");
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        if (!(Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID).length() > 0)) {
            txtOrderCount.setVisibility(View.GONE);
            txtPromotionCount.setVisibility(View.GONE);
            txtNotificationCount.setVisibility(View.GONE);
        }



        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {

            txt_delivery.setVisibility(View.VISIBLE);
            txt_takeaway_disable.setVisibility(View.VISIBLE);
            txt_takeaway.setVisibility(View.GONE);
//            txt_delivery_disable.setVisibility(View.GONE);

            txt_addressFrom.setText("Delivery From");
            txt_outletAddress.setText(Utility.readFromSharedPreference(context, GlobalValues.OUTLET_NAME) + "\n" + Utility.readFromSharedPreference(context, GlobalValues.CURRENT_OUTLET_ADDRESS)
                    + " Singapore, " + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_PINCODE));

        } else {

            txt_delivery.setVisibility(View.GONE);
            txt_takeaway_disable.setVisibility(View.GONE);
            txt_takeaway.setVisibility(View.VISIBLE);
//            txt_delivery_disable.setVisibility(View.VISIBLE);
            txt_addressFrom.setText("PickUp From");
            txt_outletAddress.setText(Utility.readFromSharedPreference(context, GlobalValues.OUTLET_NAME) + "\n" + Utility.readFromSharedPreference(context, GlobalValues.CURRENT_OUTLET_ADDRESS)
                    + " Singapore, " + Utility.readFromSharedPreference(context, GlobalValues.OUTLET_PINCODE));
        }


        Utility.writeToSharedPreference(context, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(context, "OREO_UPDATE", "1");
        }

       /* if (Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE).length() > 0) {

            totalAmtPrice = Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE));
            totalAmt.setText(String.valueOf(totalAmtPrice));
            // layout_bottom.setVisibility(View.VISIBLE);
        } else {
            totalAmt.setText("0");
            //layout_bottom.setVisibility(View.GONE);
        }*/


    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        Log.e("TAG","CartAmounTestMenucar_1::");
        try {
            cartWBadge = menu.findItem(R.id.menu_cart);
            menuSearch = menu.findItem(R.id.menu_search);

            viewBadge = menu.findItem(R.id.menu_cart).getActionView();
            View searchwBadge = menu.findItem(R.id.menu_search).getActionView();

            layoutCart = (RelativeLayout) viewBadge.findViewById(R.id.layoutCart);
            txtCartCount = (TextView) viewBadge.findViewById(R.id.txtCartCount);

            layoutSearch = searchwBadge.findViewById(R.id.layoutSearch);

            cartCount = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
            Log.e("TAG","CartAmounTestMenucar_2::"+cartCount+"\n"/*+ Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE))*/);
            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {


                txtCartCount.setVisibility(View.VISIBLE);
//                layout_bottom.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);


             /*   totalAmtPrice = Double.valueOf(Utility.readFromSharedPreference(context, GlobalValues.TOTAL_CART_PRICE));
                totalAmt.setText(String.valueOf(totalAmtPrice));*/

            } else {
//                layout_bottom.setVisibility(View.GONE);
                txtCartCount.setVisibility(View.GONE);
            }

            layoutCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    try {

                        String MinQual = Utility.readFromSharedPreference(context, GlobalValues.CART_COUNT);
                        Log.e("TAG","CartAmounTestMenucar_3::"+MinQual);
                        if (!MinQual.equals("")) {
                            if (Integer.parseInt(MinQual) >= 1) {
                                Log.e("TAG","CartAmounTestMenucar_4::"+MinQual);

                                if (Integer.parseInt(cartCount) != 0) {
                                    String message = "Do you want to add more items?";
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

                    if (Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID).length() > 0) {

                        if (resultSet) {
                            intent = new Intent(context, SearchActivity.class);
                            startActivity(intent);
                        }
                    } else {
                        Toast.makeText(context, "Please select your outlet!", Toast.LENGTH_SHORT).show();
                    }
                }
            });


            cartWBadge.setVisible(true);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return super.onPrepareOptionsMenu(menu);
    }

    public void reloadTotalAmount() {
        getTotalAmount();
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





//            if (!GlobalValues.DISCOUNT_APPLIED) {
//
//                new CartListTask().execute(url);
//            } else {
//                new CartListTask().execute(url);
//
//
//            }

        } else {

            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
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
                    GlobalValues.VOUCHERCOUNT = countJson.optString("vouchers");
                    GlobalValues.CUSTOMER_REWARD_POINT_NEW = countJson.getString("reward_ponits");

                    if (!isCustomerLoggedIn()) {
                        Log.e("TAG", "Reward_test_1::" + GlobalValues.CUSTOMER_REWARD_POINT_NEW);
                        Calendar c = Calendar.getInstance();
                        int timeOfDay = c.get(Calendar.HOUR_OF_DAY);

                        if (timeOfDay >= 0 && timeOfDay < 12) {
                            txt_hello_user.setText("Good Morning");
                            //          Toast.makeText(this, "Good Morning", Toast.LENGTH_SHORT).show();
                        } else if (timeOfDay >= 12 && timeOfDay < 16) {
                            txt_hello_user.setText("Good Afternoon");
                            //           Toast.makeText(this, "Good Afternoon", Toast.LENGTH_SHORT).show();
                        } else if (timeOfDay >= 16 && timeOfDay < 21) {
                            txt_hello_user.setText("Good Evening");
                            //           Toast.makeText(this, "Good Evening", Toast.LENGTH_SHORT).show();
                        } else if (timeOfDay >= 21 && timeOfDay < 24) {
                            txt_hello_user.setText("Good Evening");
                            //           Toast.makeText(this, "Good Night", Toast.LENGTH_SHORT).show();
                        }
                        txt_login_register.setVisibility(View.VISIBLE);
    //                    txt_wallet_amount.setVisibility(View.GONE);

                    } else {
                        txt_login_register.setVisibility(View.GONE);
   //                     txt_wallet_amount.setVisibility(View.VISIBLE);
                        Log.e("TAG", "Reward_test::" + GlobalValues.CUSTOMER_REWARD_POINT_NEW+"\n"+GlobalValues.CUSTOMER_REWARD_POINT);
                        txt_hello_user.setText("Hello! " + Utility.readFromSharedPreference(context, GlobalValues.FIRSTNAME) + " " +
                                Utility.readFromSharedPreference(context, GlobalValues.LASTNAME));
                       /* if (GlobalValues.CUSTOMER_REWARD_POINT_NEW != null &&
                                !GlobalValues.CUSTOMER_REWARD_POINT_NEW.equals("0")
                                && !GlobalValues.CUSTOMER_REWARD_POINT_NEW.equals("")) {
                           *//* if (GlobalValues.CUSTOMER_REWARD_POINT != null &&
                                    !GlobalValues.CUSTOMER_REWARD_POINT.equals("0")
                                    && !GlobalValues.CUSTOMER_REWARD_POINT.equals("")) {*//*

                            txt_wallet_amount.setText("ST $ " + String.format("%.2f", GlobalValues.CUSTOMER_REWARD_POINT_NEW));

                        } else {
                            txt_wallet_amount.setVisibility(View.GONE);
                        }*/
                    }

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

    public void CheckFacourites() {

        if (Utility.networkCheck(context)) {

            mCustomerId = Utility.readFromSharedPreference(context, GlobalValues.CUSTOMERID);

            String app_id = "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + mCustomerId;

            String availability_id = "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;

            GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(context, GlobalValues.OUTLET_ID);

            String outlet_id = "&outlet_id=" + GlobalValues.CURRENT_OUTLET_ID;

            String Url = GlobalUrl.FavouriteListURL + app_id + availability_id + outlet_id;


            try {
                new FavouriteListTask().execute(Url);

            } catch (Exception e) {
                e.printStackTrace();
            }


        } else {
            Toast.makeText(context, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

    }

    private class FavouriteListTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(context);
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

                //  String basePath = jsonObject.getJSONObject("common").optString("subcategory_image_url");


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

                        heartblink_imageview.setImageResource(R.drawable.heart_favourite);

                      /*  LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(70, 70);
                        heartblink_imageview.setLayoutParams(layoutParams);
*/

                    } else {

                        heartblink_imageview.setImageResource(R.drawable.heart_white);


                    }


                } else {

                    heartblink_imageview.setImageResource(R.drawable.heart_white);


                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                heartblink_imageview.setImageResource(R.drawable.heart_white);


                progressDialog.dismiss();

            }

        }
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
                progressDialog.dismiss();
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

            } /*finally {
                progressDialog.dismiss();


            }*/
        }
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
    protected void onStop() {
        super.onStop();

    }


    @Override
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(context, "IS_ACTIVE", "0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(context, "OREO_UPDATE", "1");
        }
    }


    public void setMenuList() {

    }

    public void setupSlide() {
        SliderLayout mDemoSlider;
        mDemoSlider = findViewById(R.id.slider);

        for (int i = 0; i < 3; i++) {

            DefaultSliderView textSliderView = new DefaultSliderView(MenuCategoryActivity.this);

            // initialize a SliderLayout
            textSliderView
                    //  .description(name)
                    .image("https://ccpl.ninjaos.com/media/dev_team/products/main-image/7e2cf4acd7889c69dcb203bae4bcf9fb.png")
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
        mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(0xff497236, 0xff101010);
        // mDemoSlider.getPagerIndicator().setDefaultIndicatorColor(R.color.greendark, R.color.graydark);
        mDemoSlider.setDuration(5000);
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


    public void setUpNewCategoryPage() {
//        CategoryNewAdapter
        Utility.writeToSharedPreference(context, GlobalValues.CATEGORY_SELECTED, "");
        Utility.writeToSharedPreference(context, GlobalValues.CATEGORY_SELECTED_NAME,"");
        showCategory();
        showHotMenuProducts();

//        CategoryNewAdapter categoryNewAdapter = new CategoryNewAdapter(context, predictionsItemArrayList);
//        recycle_category.setLayoutManager(new LinearLayoutManager(context));
//
//        recycle_category.setVisibility(View.VISIBLE);
//        recycle_category.setAdapter(categoryNewAdapter);
//
//        categoryNewAdapter.setOnItemClick(new IOnItemClick() {
//            @Override
//            public void onItemClick(View v, int position) {
//
//            }
//        });
    }

    private void setUpCustomBanner(String base_imageURL, ArrayList<ResultSetItem> bannerArrayList) {
        sliderBannerAdapterList = new ArrayList<>();
        for (ResultSetItem banner : bannerArrayList) {
            BannerModel bm = new BannerModel();
            bm.setBannerImage(base_imageURL + banner.getBannerImage());
            sliderBannerAdapterList.add(bm);
        }
        sliderBannerAdapter = new SliderBannerAdapter(0, sliderBannerAdapterList, context);
        viewPager.setAdapter(sliderBannerAdapter);
        dots = new ImageView[sliderBannerAdapterList.size()];

        for (int j = 0; j < sliderBannerAdapterList.size(); j++) {
            dots[j] = new ImageView(MenuCategoryActivity.this);
            dots[j].setImageDrawable(ContextCompat.getDrawable(MenuCategoryActivity.this, R.drawable.non_active_dot));
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(8, 0, 8, 0);
            SliderDots.addView(dots[j], params);
        }
        dots[0].setImageDrawable(ContextCompat.getDrawable(MenuCategoryActivity.this, R.drawable.active_dot));
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new MyTimerTask(), 2000, 3000);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {
            }

            @Override
            public void onPageSelected(int i) {
                for (int k = 0; k < sliderBannerAdapterList.size(); k++) {
                    dots[k].setImageDrawable(ContextCompat.getDrawable(MenuCategoryActivity.this, R.drawable.non_active_dot));
                }
                dots[i].setImageDrawable(ContextCompat.getDrawable(MenuCategoryActivity.this, R.drawable.active_dot));
            }

            @Override
            public void onPageScrollStateChanged(int i) {
            }
        });
    }

    public class MyTimerTask extends TimerTask {
        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    if (viewPager.getCurrentItem() < sliderBannerAdapter.getCount() - 1) {
                        viewPager.setCurrentItem(viewPager.getCurrentItem() + 1);
                    } else {
                        viewPager.setCurrentItem(0);
                    }
                }
            });
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

            Log.e("Member_reward_service::", params[0] + "\n" + countParams);

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
                        txt_wallet_amount.setText("ST$ "+GlobalValues.CUSTOMER_REWARD_POINT);
            //            txtRewardPoint.setText("" + String.valueOf(x_reedempoints) + "  Points");
                    }
                }else {
                    txt_wallet_amount.setText("ST$ 0");
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






