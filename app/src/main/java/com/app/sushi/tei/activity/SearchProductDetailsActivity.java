package com.app.sushi.tei.activity;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import com.google.android.material.tabs.TabLayout;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.SuperscriptSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifierProduct;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.Products;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuProduct;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.Model.ProductList.TagImageModel;
import com.app.sushi.tei.Model.Search.SearchProduct;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.AddOnsRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.ModifierHeadingRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuChildRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuTitleRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.TagImageAdapter;
import com.app.sushi.tei.adapter.SetMenuAdapter.SetMenuTitleRecyclerAdapterNew;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.dialog.MessageDialog;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.sushi.tei.Model.ProductList.ModifierHeading.subModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mModifierQuantity;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuBasePrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuQuantity;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetmenuoverallprices;
import static com.app.sushi.tei.activity.SubCategoryActivity.mquanititycost_src;
import static com.app.sushi.tei.activity.SubCategoryActivity.quantityCost;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.value;


public class SearchProductDetailsActivity extends AppCompatActivity {

    private Context mContext;
    public Dialog dialog;
    private DatabaseHandler databaseHandler;
    private ModifierHeadingRecyclerAdapter modifierHeadingRecyclerAdapter;
    private ModifierProduct modifierProduct;
    private String mValidationMessage = "";
    private String mBasePath = "", galleryBasePath = "";
    private String mCustomerId = "", mReferenceId = "", mProductId = "", mProductQuantity = "1";
    private SearchProduct searchProduct;
    private String searchProductPrimaryId, searchProductfavouriteId, searchProductPRICE;

    private double simpleQuantityCost = 0.0;
    private int simpleQuantity = 1;
    private SetMenuProduct setMenuProduct;
    private TextView txtModi;
    private Double mSearchProuductprise = 0.0;
    private SetMenuTitleRecyclerAdapterNew setMenuTitleRecyclerAdapternew;
    private TextView text_linefav;
    private String StatusFav = "0";
    private EditText notesText1;
    Double total_unitprices = 0.0;
    public static String mAliasProductPrimaryId = "";
    public static boolean isCorrectCombination = true;
    private LinearLayout favouriteLayout;
    private Double voucherIncreaseQty, productMinQty;

    private double mPrices = 0.0;

    LinearLayout lly_forGift;
    private EditText edtName, edtEmail, edtPhoneNumber, edtMessage;
    private TextView txtSubmit;
    private TextView txt_info;
    String caseType = "";
    private EditText notesText;
    private Products products;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_product_details);
        Log.e("TAG", "Product_Details_test::");
        mContext = SearchProductDetailsActivity.this;
        databaseHandler = DatabaseHandler.getInstance(mContext);

        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
        if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
            GlobalValues.CURRENT_AVAILABLITY_NAME = "DELVIERY";
        } else {
            GlobalValues.CURRENT_AVAILABLITY_NAME = "TAKEAWAY";
        }
        GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

        try {

            if (getIntent().getExtras() != null) {



                searchProduct = getIntent().getParcelableExtra("SEARCH_PRODUCT");
                searchProductPRICE = getIntent().getStringExtra("SEARCH_PRODUCT_PRICE");


                searchProductPrimaryId = getIntent().getStringExtra("SEARCH_PRODUCT_PrimarId");
                searchProductfavouriteId = getIntent().getStringExtra("SEARCH_PRODUCT_FavouriteId");

                mBasePath = getIntent().getStringExtra("IMAGE_BASEPATH");


                if (Integer.parseInt(searchProduct.getmProductType()) == 1) {

                    mProductId = searchProduct.getmProductId();



                    simpleProductGetData();

//                    simpleProductDetailsDialog(mContext, searchProduct);

                } else if (Integer.parseInt(searchProduct.getmProductType()) == 4) {


                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.PRODUCT_DETAILS_URL + "?appId=" + GlobalValues.APP_ID +
                                "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                "&outletId=" + GlobalValues.CURRENT_OUTLET_ID +
                                "&productId=" + searchProduct.getmProductId() +
                                "&productType=" + searchProduct.getmProductType();

                        mProductId = searchProduct.getmProductId();

                        new ProductDetailsTask(mProductId).execute(url);

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }


                } else if (Integer.parseInt(searchProduct.getmProductType()) == 2 || Integer.parseInt(searchProduct.getmProductType()) == 5) {
                    SetMenuChildRecyclerAdapter.childPlusMinus = 0.00;
                    subModifierPrice = 0.00;
                    value = 0.00;
                    if (Utility.networkCheck(mContext)) {

                        mProductId = searchProduct.getmProductId();

                        String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                                "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                "&product_id=" + mProductId;

                        new SetMenuProductDetailsTask(mProductId, searchProduct.getmProduct_primary_id()).execute(url);

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                } /*else if (Integer.parseInt(searchProduct.getmProductType()) == 5){
                    voucherDialogue(mContext, searchProduct);
                }*/
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public void onBackPressed() {


        try {
            if (dialog.isShowing()) {
                dialog.cancel();
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        finish();
    }

    public void finishSearchActivity() {

        Handler handler = new Handler();

        handler.postDelayed(new Runnable() {
            public void run() {
                finish();
            }
        }, 100);
    }


    public void onrefreshdtata() {
//        try {
//            if (databaseHandler.getAllTotalData()!=null) {
//
//                ArrayList<Cart> arrCart=
//
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
        databaseHandler = DatabaseHandler.getInstance(mContext);


    }

    private boolean isCustomerLoggedIn() {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            return true;
        } else {
            return false;
        }
    }

    private void simpleProductDetailsDialog(final Context mContext, final Products product) {
        //TODO identification
        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_search_product_details_dialog1);
        dialog.setCancelable(true);
        dialog.show();

        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        final TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView category_name = dialog.findViewById(R.id.category_name);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        final TextView txtPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        ImageView layoutClose = dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        //  LinearLayout layoutModifierParent = (LinearLayout) dialog.findViewById(R.id.layoutModifierParent);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);
        RelativeLayout layoutBottom = (RelativeLayout) dialog.findViewById(R.id.layoutBottom);
        favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
        text_linefav = (TextView) dialog.findViewById(R.id.text_linefav);
//        TextView completeTxt =(TextView)dialog.findViewById(R.id.completeTxt);
//        completeTxt.setText(setMenuProduct.getSubcatgory_name());

        ImageView user_imageview = null;
        RelativeLayout layoutSearch = null;
        RelativeLayout layoutCart = null;
        try {
            Toolbar toolbar = dialog.findViewById(R.id.toolBar);
            toolbar.setVisibility(View.GONE);
            LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
            user_imageview = toolbar.findViewById(R.id.user_imageview);
            layoutSearch = toolbar.findViewById(R.id.layoutSearch);
            layoutCart = toolbar.findViewById(R.id.layoutCart);
            TextView txtCartCount = (TextView) toolbar.findViewById(R.id.txtCartCount);
            user_imageview.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.VISIBLE);
            layoutCart.setVisibility(View.VISIBLE);

            String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

                txtCartCount.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);

            } else {
                txtCartCount.setVisibility(View.GONE);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });

        txtProductName.setText(product.getMproduct_alias().replace("\\", ""));
        txtProductDesc.setText(product.getmProductDescription());
        category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));


        mProductQuantity = txtQuantity.getText().toString();


        // layoutModifierParent.setVisibility(View.GONE);



      /*  if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0){

            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.default_image).into(imgProduct);

        }else{*/

        if (product.getmSubCategoryGalleryImage() != null && product.getmSubCategoryGalleryImage().length() > 0) {

            Picasso.with(mContext).load(product.getmSubCategoryGalleryImage()).error(R.drawable.place_holder_sushi_tei).
                    into(imgProduct);

        } else {

            Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);

        }

        if (isCustomerLoggedIn()) {
            if (searchProductfavouriteId.equalsIgnoreCase("null") || searchProductfavouriteId.isEmpty()) {

                text_linefav.setText("Add to favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                StatusFav = "1";

            } else {
                text_linefav.setText("Remove from favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

                StatusFav = "0";
            }
        } else {
            text_linefav.setText("Add to favourite");
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
            StatusFav = "1";
        }

        user_imageview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!isCustomerLoggedIn()) {
                    Intent intent = new Intent(mContext, MemberBenefitActivity.class);
                    mContext.startActivity(intent);
                } else {

                    openFiveMenuActivity(0);
                }
            }
        });

        layoutCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    if (!MinQual.equals("")) {
                        if (Integer.parseInt(MinQual) >= 1) {
                            String message = "Do you want to add more items?";
                            new CheckOutMessageDialog(mContext, message, new CheckOutMessageDialog.OnSlectedMethod() {
                                @Override
                                public void selectedAction(String action) {
                                    if (action.equalsIgnoreCase("YES")) {
                                    } else {
//                                                            Intent intent = new Intent(mContext, CartActivity.class);
                                        Intent intent = new Intent(mContext, OrderSummaryActivity.class);
                                        mContext.startActivity(intent);
                                    }
                                }
                            });
                        }
                    } else {
                        Toast.makeText(mContext, "Cart is empty. Go to ‘Order Now’ to add products!", Toast.LENGTH_SHORT).show();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        layoutSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID).length() > 0) {
                    Intent intent = new Intent(mContext, SearchActivity.class);
                    mContext.startActivity(intent);
                } else {
                    Toast.makeText(mContext, "Please select your outlet!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    addFavouriteMethod(searchProductPrimaryId);
                }
            }
        });


        if (!searchProductPRICE.equals("null")) {



            mPrices = Double.parseDouble(searchProductPRICE);

            SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mPrices)));

            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

            txtPrice.setText(String.format("%.2f", new BigDecimal(mPrices)));
        }

        try {
            if (databaseHandler.getAllTotalData(product.getmProductId()) != null) {
                Cart cart = databaseHandler.getAllTotalData(product.getmProductId());
                layoutMaxCount.setVisibility(View.VISIBLE);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());


            } else {
                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        simpleQuantityCost = mPrices;

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                count++;

                simpleQuantity = count;

                simpleQuantityCost += mPrices;


                txtQuantity.setText(String.valueOf(count));


                SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                txtPrice.setText(String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                mProductQuantity = txtQuantity.getText().toString();


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                if (count > 1) {
                    count--;

                    simpleQuantity = count;
                    simpleQuantityCost -= mPrices;

                    txtQuantity.setText(count + "");


                    SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtPrice.setText(String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                    mProductQuantity = txtQuantity.getText().toString();

                }
            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();
            }
        });

        layoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                } else {

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = "";

                    } else {
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } catch (SecurityException e) {
                            GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } finally {
                            {
                                mCustomerId = "";
                            }
                        }
                    }

                    if (text_linefav.getText().toString().equalsIgnoreCase("Remove from favourite")) {
                        String url = GlobalUrl.FavouriteURl;

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("app_id", GlobalValues.APP_ID);
                        param.put("customer_id", mCustomerId);
                        param.put("product_id", product.getmProductPrimaryId());
                        param.put("fav_flag", "No");
                        param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);



                        new FavouritesAddTask(param).execute(url);

                        StatusFav = "0";

                    } else {
                        String url = GlobalUrl.FavouriteURl;

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("app_id", GlobalValues.APP_ID);
                        param.put("customer_id", mCustomerId);
                        param.put("product_id", product.getmProductPrimaryId());
                        param.put("fav_flag", "Yes");
                        param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


                        new FavouritesAddTask(param).execute(url);
                        StatusFav = "1";
                    }
                }
            }
        });


        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {

                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());

            } else {

                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lly_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDone.performClick();
            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = "";

                    } else {
                        mReferenceId = Utility.getReferenceId(mContext);
                        mCustomerId = "";

                    }

                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.ADD_CART_URL;
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("product_id", product.getmProductId());
                        params.put("product_type", product.getmProductType());
                        params.put("product_qty", txtQuantity.getText().toString());
                        params.put("app_id", GlobalValues.APP_ID);
                        //params.put("reference_id", mReferenceId);
                        params.put("customer_id", mCustomerId);
                        params.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                        params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);

                         new AddCartTask(params, txtQuantity.getText().toString()).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private void voucherDialogue(final Context mContext, final SearchProduct searchProduct) {
        //TODO identification
        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_search_product_details_dialog1);
        dialog.setCancelable(true);
        dialog.show();

        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        final TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        TextView category_name = dialog.findViewById(R.id.category_name);
        final TextView txtPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        ImageView layoutClose = dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        TextView productDescription = dialog.findViewById(R.id.productDescription);
        productDescription.setText(searchProduct.getmProductShortDesc());
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        //  LinearLayout layoutModifierParent = (LinearLayout) dialog.findViewById(R.id.layoutModifierParent);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);
        RelativeLayout layoutBottom = (RelativeLayout) dialog.findViewById(R.id.layoutBottom);
        favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
        text_linefav = (TextView) dialog.findViewById(R.id.text_linefav);
//        TextView completeTxt =(TextView)dialog.findViewById(R.id.completeTxt);
//        completeTxt.setText(setMenuProduct.getSubcatgory_name());
        txtProductName.setText(searchProduct.getmProductName().replace("\\", ""));
        txtProductDesc.setText(searchProduct.getmProductShortDesc());
        category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));

        try {
            Toolbar toolbar = dialog.findViewById(R.id.toolBar);
            toolbar.setVisibility(View.GONE);
            LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
            ImageView user_imageview = toolbar.findViewById(R.id.user_imageview);
            RelativeLayout layoutSearch = toolbar.findViewById(R.id.layoutSearch);
            RelativeLayout layoutCart = toolbar.findViewById(R.id.layoutCart);
            TextView txtCartCount = (TextView) toolbar.findViewById(R.id.txtCartCount);
            user_imageview.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.VISIBLE);
            layoutCart.setVisibility(View.VISIBLE);

            String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

                txtCartCount.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);

            } else {
                txtCartCount.setVisibility(View.GONE);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        mProductQuantity = txtQuantity.getText().toString();


        // layoutModifierParent.setVisibility(View.GONE);



      /*  if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0){

            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.default_image).into(imgProduct);

        }else{*/

        if (searchProduct.getmProduct_galleryImage() != null && searchProduct.getmProduct_galleryImage().length() > 0) {

            Picasso.with(mContext).load(searchProduct.getmProduct_galleryImage()).error(R.drawable.place_holder_sushi_tei).
                    into(imgProduct);

        } else {

            Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);

        }

        if (isCustomerLoggedIn()) {
            if (searchProductfavouriteId.equalsIgnoreCase("null")) {

                text_linefav.setText("Add to favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                StatusFav = "1";

            } else {
                text_linefav.setText("Remove from favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                StatusFav = "0";
            }
        } else {
            text_linefav.setText("Add to favourite");
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
            StatusFav = "1";
        }

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    addFavouriteMethod(searchProductPrimaryId);
                }
            }
        });


        if (!searchProductPRICE.equals("null")) {



            mPrices = Double.parseDouble(searchProductPRICE);

            SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mPrices)));

            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

            txtPrice.setText(String.format("%.2f", new BigDecimal(mPrices)));
        }

        try {
            if (databaseHandler.getAllTotalData(searchProduct.getmProductId()) != null) {
                Cart cart = databaseHandler.getAllTotalData(searchProduct.getmProductId());
                layoutMaxCount.setVisibility(View.VISIBLE);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());


            } else {
                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        simpleQuantityCost = mPrices;

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                count++;

                simpleQuantity = count;

                simpleQuantityCost += mPrices;


                txtQuantity.setText(String.valueOf(count));


                SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                txtPrice.setText(String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                mProductQuantity = txtQuantity.getText().toString();


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                if (count > 1) {
                    count--;

                    simpleQuantity = count;
                    simpleQuantityCost -= mPrices;

                    txtQuantity.setText(count + "");


                    SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtPrice.setText(String.format("%.2f", new BigDecimal(simpleQuantityCost)));

                    mProductQuantity = txtQuantity.getText().toString();

                 }
            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();
            }
        });

        layoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                } else {

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = "";

                    } else {
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } catch (SecurityException e) {
                            GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } finally {
                            {
                                mCustomerId = "";
                            }
                        }
                    }

                    if (text_linefav.getText().toString().equalsIgnoreCase("Remove from favourite")) {
                        String url = GlobalUrl.FavouriteURl;

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("app_id", GlobalValues.APP_ID);
                        param.put("customer_id", mCustomerId);
                        param.put("product_id", searchProduct.getmProduct_primary_id());
                        param.put("fav_flag", "No");
                        param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


                        new FavouritesAddTask(param).execute(url);

                        StatusFav = "0";

                    } else {
                        String url = GlobalUrl.FavouriteURl;

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("app_id", GlobalValues.APP_ID);
                        param.put("customer_id", mCustomerId);
                        param.put("product_id", searchProduct.getmProduct_primary_id());
                        param.put("fav_flag", "Yes");
                        param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


                        new FavouritesAddTask(param).execute(url);
                        StatusFav = "1";
                    }
                }
            }
        });


        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {

                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());

            } else {

                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lly_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDone.performClick();
            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = "";

                    } else {
                        mReferenceId = Utility.getReferenceId(mContext);
                        mCustomerId = "";

                    }

                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.ADD_CART_URL;
                        Map<String, String> params = new HashMap<String, String>();

                        params.put("product_id", searchProduct.getmProductId());
                        params.put("product_type", searchProduct.getmProductType());
                        params.put("product_qty", txtQuantity.getText().toString());
                        params.put("app_id", GlobalValues.APP_ID);
                        //params.put("reference_id", mReferenceId);
                        params.put("customer_id", mCustomerId);
                        params.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                        params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);

                         new AddCartTask(params, txtQuantity.getText().toString()).execute(url);
                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private Double getsetMenuProductPrice() throws Exception {

        double setPrice = 0.00;

        for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {

            if (setMenuProduct.getSetMenuTitleList().get(i).getmAppliedPrice().equalsIgnoreCase("0")) {

                if (setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() > 10) {
                    for (int i1 = 0; i1 < setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().size(); i1++) {

                        Double qPrice = setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalpPrize();

                        setPrice = setPrice + qPrice;
                    }
                }

            } else {
                for (int i1 = 0; i1 < setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().size(); i1++) {

                    Double qPrice = Double.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getmModifierPrice()) * Integer.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalQuantity());

                    setPrice = setPrice + qPrice;
                }
            }
        }
        return mSearchProuductprise + setPrice;
    }

    private void setMenuproductDetailsDialog(final Context mContext, final String mProductId, final String mProductprimaryid, String quantity) {

        this.mProductQuantity = quantity;
        mSetMenuQuantity = 1;

        //SetMenuTitleRecyclerSearchAdapter setMenuTitleRecyclerAdapter;
        SetMenuTitleRecyclerAdapter setMenuTitleRecyclerAdapter;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_search_product_details_dialog1);
        dialog.setCancelable(true);
        dialog.show();
        final SpannableString cs;

        //TODO identification2
        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        final TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        notesText1 = dialog.findViewById(R.id.notesText);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        TextView category_name = dialog.findViewById(R.id.category_name);
        TextView completeTxt = (TextView) dialog.findViewById(R.id.completeTxt);
        completeTxt.setText(setMenuProduct.getSubcatgory_name());
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        RecyclerView tagimageRecyclerview = (RecyclerView) dialog.findViewById(R.id.tagimageRecyclerview);
        ImageView layoutClose = dialog.findViewById(R.id.layoutClose);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);
        RelativeLayout layoutBottom = (RelativeLayout) dialog.findViewById(R.id.layoutBottom);
        RelativeLayout rly_specialNotes = dialog.findViewById(R.id.rly_specialNotes);
        favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
        String additionalNotes = notesText1.getText().toString();
        text_linefav = (TextView) dialog.findViewById(R.id.text_linefav);
        RelativeLayout rly_voucherExpAva = dialog.findViewById(R.id.rly_voucherExpAva);
        TextView txt_voucherExpAva = dialog.findViewById(R.id.txt_voucherExpAva);

        try {
            Toolbar toolbar = dialog.findViewById(R.id.toolBar);
            toolbar.setVisibility(View.GONE);
            LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
            ImageView user_imageview = toolbar.findViewById(R.id.user_imageview);
            RelativeLayout layoutSearch = toolbar.findViewById(R.id.layoutSearch);
            RelativeLayout layoutCart = toolbar.findViewById(R.id.layoutCart);
            TextView txtCartCount = (TextView) toolbar.findViewById(R.id.txtCartCount);
            user_imageview.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.VISIBLE);
            layoutCart.setVisibility(View.VISIBLE);

            String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

                txtCartCount.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);

            } else {
                txtCartCount.setVisibility(View.GONE);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });

        if (isCustomerLoggedIn()) {
            if (searchProductfavouriteId.equalsIgnoreCase("null")) {

                text_linefav.setText("Add to favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

                StatusFav = "1";

            } else {
                text_linefav.setText("Remove from favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

                StatusFav = "0";
            }
        } else {
            text_linefav.setText("Add to favourite");
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
            StatusFav = "1";
        }

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {
                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    addFavouriteMethod(searchProductPrimaryId);
                }
            }
        });

        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        setmenuRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(setMenuProduct.getmProductAliasName().replace("\\", ""));
        category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));

        try {
            Log.e("TAG", "ASDkklg222::" + setMenuProduct.getTagImageList().size());
            ArrayList<TagImageModel> TagimageList = new ArrayList<>();
            for (int i = 0; i < setMenuProduct.getTagImageList().size(); i++) {
                TagImageModel model = setMenuProduct.getTagImageList().get(i);
                TagimageList.add(model);
             }
            LinearLayoutManager lrt = new LinearLayoutManager(mContext);
            tagimageRecyclerview.setLayoutManager(lrt);
            TagImageAdapter adapter = new TagImageAdapter(mContext, TagimageList);
            tagimageRecyclerview.setAdapter(adapter);
        } catch (Exception e) {
            e.printStackTrace();
        }

        mProductQuantity = txtQuantity.getText().toString();

        if (Integer.parseInt(setMenuProduct.getmProductType()) == 5) {
            txtProductDesc.setText(setMenuProduct.getmProductShortDescription());
            if (setMenuProduct.getmProductMinQty().equalsIgnoreCase("") || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0.00")
                    || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0") || setMenuProduct.getmProductMinQty().equalsIgnoreCase("null")) {
                txtQuantity.setText("1");
            } else {
                txtQuantity.setText(setMenuProduct.getmProductMinQty());
            }
            mProductQuantity = txtQuantity.getText().toString();
            favouriteLayout.setVisibility(View.GONE);
        } else {
    //        favouriteLayout.setVisibility(View.VISIBLE);
            favouriteLayout.setVisibility(View.GONE);
            txtProductDesc.setText(setMenuProduct.getmProductDesc());
        }

        try {
            mSearchProuductprise = Double.valueOf(setMenuProduct.getmProductPrice());
        } catch (Exception e) {
            mSearchProuductprise = 0.0;
        }

        mquanititycost_src = mSetMenuPrice;

       /*try {
            mSetMenuPrice = Double.valueOf(setMenuProduct.getmProductPrice());
            mSetMenuBasePrice = Double.valueOf(setMenuProduct.getmProductPrice());
        }catch (NumberFormatException e)
        {
            mSetMenuPrice=0.0;
            mSetMenuBasePrice=0.0;
            e.printStackTrace();
        }*/


        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2 || Integer.parseInt(setMenuProduct.getmProductType()) == 5) {

            if (Integer.parseInt(setMenuProduct.getmProductType()) == 5) {
                rly_specialNotes.setVisibility(View.GONE);
                //rly_voucherExpAva.setVisibility(View.VISIBLE);
                String expiryDate = "";
                SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                SimpleDateFormat dateForm = new SimpleDateFormat("dd-MMM-yyyy");
                try {
                    Date date = dateFormatter.parse(setMenuProduct.getmVoucherExpiryDate());
                    expiryDate = dateForm.format(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }

                String expiryLabel = "<font color='#c6c6c6'>Expiry</font>";
                //txt_voucherExpAva.setText(Html.fromHtml(" " + expiryLabel + " " + expiryDate));
            } else {
    //            rly_specialNotes.setVisibility(View.VISIBLE);
                rly_specialNotes.setVisibility(View.GONE);
                rly_voucherExpAva.setVisibility(View.GONE);
            }

            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

            cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mSearchProuductprise)));

            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(mSearchProuductprise)));

            if (setMenuProduct.getmProductMinQty().equalsIgnoreCase("")
                    || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0.00")
                    || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0")
                    || setMenuProduct.getmProductMinQty().equalsIgnoreCase("null")) {
                txtQuantity.setText("1");
            } else {
                txtQuantity.setText(setMenuProduct.getmProductMinQty());
            }

            if (setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("")
                    || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("0.00")
                    || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("0")
                    || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("null")) {
                voucherIncreaseQty = 1d;
            } else {
                voucherIncreaseQty = Double.valueOf(setMenuProduct.getmProductVoucherIncreaseQty());
            }

            if (setMenuProduct.getmProductMinQty().equalsIgnoreCase("") || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0")
                    || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0.00") || setMenuProduct.getmProductMinQty().equalsIgnoreCase("null")) {
                productMinQty = 1d;
            } else {
                productMinQty = Double.valueOf(setMenuProduct.getmProductMinQty());
            }

            if (setMenuProduct.getSetMenuTitleList() != null &&
                    setMenuProduct.getSetMenuTitleList().size() > 0) {

                /*if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                    setMenuTitleRecyclerAdapternew = new SetMenuTitleRecyclerAdapterNew(mContext,
                            setMenuProduct.getSetMenuTitleList(), Integer.valueOf(setMenuProduct.getmApplyMinMaxSelect()), 2, new SetMenuTitleRecyclerAdapterNew.PasstheValue() {
                        @Override
                        public void addtoSubtotla(String productPrice) {
                            double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);

                            Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) + valu;

                            SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModifierPrice.setText(String.format("%.2f", finalVal));
                        }

                        @Override
                        public void subtoSubtotla(String productPrice) {
                            double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);
                            Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) - valu;

                            SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModifierPrice.setText(String.format("%.2f", finalVal));

                        }
                    });

                    //setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapternew);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);
                } else {*/
                txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(mSearchProuductprise)));
                setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                        setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                setmenuRecyclerView.setNestedScrollingEnabled(false);
                setmenuRecyclerView.setHasFixedSize(true);
                /* }*/
                     /*}else {
                        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

                        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                        txtModifierPrice.setText(cs);
                        setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerSearchAdapter(mContext,
                                setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                        setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                        setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        setmenuRecyclerView.setNestedScrollingEnabled(false);
                        setmenuRecyclerView.setHasFixedSize(true);
                    }


                } else {


                    txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtModifierPrice.setText(cs);
                    setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerSearchAdapter(mContext,
                            setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);
                }*/
            }
        }


        if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0) {

            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);

        } else {
            if (!(Integer.parseInt(setMenuProduct.getmProductType()) == 5)) {
                if (modifierProduct.getmProductImage() != null && modifierProduct.getmProductImage().length() > 0) {
                    Picasso.with(mContext).load(mBasePath + modifierProduct.getmProductImage()).
                            error(R.drawable.place_holder_sushi_tei).into(imgProduct);
                } else {
                    Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);
                }
            }
        }

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 if (Integer.parseInt(setMenuProduct.getmProductType()) == 5) {
                    int count;
                    int voucherIncreaseQty;

                    if (setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("")
                            || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("0.00")
                            || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("0")
                            || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("null")) {
                        voucherIncreaseQty = 1;
                    } else {
                        voucherIncreaseQty = Integer.valueOf(setMenuProduct.getmProductVoucherIncreaseQty());
                    }

                                   /* if (counting == 0) {
                                        count = 0;
                                    } else {
                                        count = Integer.parseInt(txtQuantity.getText().toString());
                                    }



                                    count = count + voucherIncreaseQty;
                                    counting++;*/

                    count = Integer.parseInt(txtQuantity.getText().toString());
                    count++;
                    mSetMenuQuantity = count;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;
                    txtQuantity.setText(String.valueOf(count));
                     txtModifierPrice.setText(String.valueOf(String.format("%.2f", (mSearchProuductprise * Double.valueOf(count)))));
                } else {
                    /*if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                            && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        count++;
                        txtQuantity.setText(String.valueOf(count));


                        try {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString()))));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        count++;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;

                        txtQuantity.setText(String.valueOf(count));
                        if (mSetmenuoverallprices != 0.00) {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost + mSetmenuoverallprices)));
                        } else {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));
                        }


                        mProductQuantity = txtQuantity.getText().toString();

                        mProductQuantity = txtQuantity.getText().toString();
                    }*/

                    //27-01-2021

                    if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                            && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        count++;
                        txtQuantity.setText(String.valueOf(count));

                        mSetMenuQuantity = count;
                        quantityCost = (mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * mSetMenuQuantity;



                        try {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                    } else {

                        int count = Integer.parseInt(txtQuantity.getText().toString());
                        //double modPrice = subModifierPrice / mSetMenuQuantity;
                        count++;
                        mSetMenuQuantity = count;
                        quantityCost = (mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * mSetMenuQuantity;

                        txtQuantity.setText(String.valueOf(count));

                        if (mSetmenuoverallprices != 0.00) {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));
                        } else {
                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));
                        }

                        mProductQuantity = txtQuantity.getText().toString();

                    }
                 }
                   /* } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        count++;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;

                        txtQuantity.setText(String.valueOf(count));
                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                        mProductQuantity = txtQuantity.getText().toString();

                        mProductQuantity = txtQuantity.getText().toString();
                    }


                } else {



                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    mSetMenuQuantity = count;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;

                    txtQuantity.setText(String.valueOf(count));

                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                    mProductQuantity = txtQuantity.getText().toString();




                }*/


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Integer.parseInt(setMenuProduct.getmProductType()) == 5) {
                    int count;
                    int productMinQty;
                    int voucherIncreaseQty;

                    if (setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("")
                            || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase("0")
                            || setMenuProduct.getmProductVoucherIncreaseQty().equalsIgnoreCase(null)) {
                        voucherIncreaseQty = 1;
                    } else {
                        voucherIncreaseQty = Integer.valueOf(setMenuProduct.getmProductVoucherIncreaseQty());
                    }

                    if (setMenuProduct.getmProductMinQty().equalsIgnoreCase("")
                            || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0")
                            || setMenuProduct.getmProductMinQty().equalsIgnoreCase("0.00")
                            || setMenuProduct.getmProductMinQty().equalsIgnoreCase("null")) {
                        productMinQty = 1;
                    } else {
                        productMinQty = Integer.valueOf(setMenuProduct.getmProductMinQty());
                    }

                                    /*if (counting == 1) {
                                        count = productMinQty;
                                        counting = 0;
                                    } else {
                                        count = Integer.parseInt(txtQuantity.getText().toString());
                                    }

                                    if (count > productMinQty) {
                                        counting--;
                                        count = count - voucherIncreaseQty;
                                    }*/

                    count = Integer.parseInt(txtQuantity.getText().toString());
                    //int productMinimumQty = (int)Math.round(productMinQty);
                    if (count > productMinQty) {
                        count--;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;
                        txtQuantity.setText(count + "");
                    } else {
                        Toast.makeText(mContext, "You have reached the minimum selection", Toast.LENGTH_SHORT).show();
                    }

                    txtQuantity.setText(count + "");
                    txtModifierPrice.setText(String.valueOf(String.format("%.2f", mSearchProuductprise * Double.valueOf(count))));
                    mProductQuantity = txtQuantity.getText().toString();
                } else {
                   /* if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                            && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {
                            count--;

                            txtQuantity.setText(count + "");

                            try {
                                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString()))));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            mProductQuantity = txtQuantity.getText().toString();
                        }

                    } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {
                            count--;
                            mSetMenuQuantity = count;
                            quantityCost = mSetMenuPrice * mSetMenuQuantity;

                            txtQuantity.setText(count + "");

                            if (mSetmenuoverallprices != 0.00) {
                                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost + mSetmenuoverallprices)));
                            } else {
                                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));
                            }

                            mProductQuantity = txtQuantity.getText().toString();
                        }
                    }*/

                    //27-01-2021

                    if ((setMenuProduct.getmApplyMinMaxSelect() == 1)
                            && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                            && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {
                            count--;
                            mSetMenuQuantity = count;
                            quantityCost = (mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * mSetMenuQuantity;
                            txtQuantity.setText(count + "");

                            try {
                                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));
                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            mProductQuantity = txtQuantity.getText().toString();
                        }

                    } else {

                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {

                            count--;
                            mSetMenuQuantity = count;
                            double modPrice = subModifierPrice / mSetMenuQuantity;
                             double txtQty = Double.valueOf(txtQuantity.getText().toString()) + 1d;
                            double subModiPrice = value * count;
                            double setMenuPrice = mSetMenuPrice + subModiPrice;


                            quantityCost = (mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * mSetMenuQuantity;
                            //quantityCost = setMenuPrice * ((double)(mSetMenuQuantity));
                            txtQuantity.setText(count + "");

                            if (mSetmenuoverallprices != 0.00) {
                                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));
                            } else {
                                txtModifierPrice.setText(String.format("%.2f", new BigDecimal(quantityCost)));
                            }

                            mProductQuantity = txtQuantity.getText().toString();
                        }
                    }
                }
                   /* } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {
                            count--;
                            mSetMenuQuantity = count;
                            quantityCost = mSetMenuPrice * mSetMenuQuantity;

                            txtQuantity.setText(count + "");

                            txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                            mProductQuantity = txtQuantity.getText().toString();
                        }
                    }
                } else {
                    int count = Integer.parseInt(txtQuantity.getText().toString());
                    if (count > 1) {
                        count--;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;
                        txtQuantity.setText(count + "");
                        txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                        mProductQuantity = txtQuantity.getText().toString();
                    }
                }*/
            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                finish();
            }
        });

        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {
                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());
            } else {
                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lly_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDone.performClick();
            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    if (Integer.parseInt(setMenuProduct.getmProductType()) == 5) {
                        final double product_unit_price = mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus;
                        Log.e("product", mSetMenuQuantity + " " + product_unit_price + "--" + quantityCost);
                        showGiftOrMeDialogue();
                    } else {
                        validateSetMenu(setMenuProduct.getSetMenuTitleList());
                    }
                }
            }
        });
    }

    private void showGiftOrMeDialogue() {
        final double product_unit_price = mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus;
        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme1);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //TODO:Identification
        dialog.setContentView(R.layout.dialog_voucher_recipent);
        dialog.show();

        ImageView cancel_ImageView = dialog.findViewById(R.id.cancel_Image);
        edtName = dialog.findViewById(R.id.edtName);
        edtEmail = dialog.findViewById(R.id.edtEmail);
        edtPhoneNumber = dialog.findViewById(R.id.edtPhoneNumber);
        edtMessage = dialog.findViewById(R.id.edtMessage);
        txtSubmit = dialog.findViewById(R.id.txtSubmit);
        txt_info = dialog.findViewById(R.id.txt_info);
        TabLayout tabLayout = dialog.findViewById(R.id.tabLayout);
        lly_forGift = dialog.findViewById(R.id.lly_forGift);
        String[] ordertabs = {"AS A GIFT", "FOR MYSELF"};
        caseType = "";
        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_order);
                txtTabItem.setTextSize(15);
                txtTabItem.setTextColor(mContext.getResources().getColor(R.color.colorSelectedTab));

                switch (tab.getPosition()) {

                    case 0:
                        lly_forGift.setVisibility(View.VISIBLE);
                        caseType = "0";
                        //txt_info.setText("After purchase your recipient can get email notification and they can use above email address to sign up. Vouchers will be available within their my account");
                        txt_info.setText(R.string.gift_desc);
                        break;

                    case 1:
                        lly_forGift.setVisibility(View.GONE);
                        caseType = "1";
                        txt_info.setText(R.string.gift_myself_desc);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_order);
                txtTabItem.setTextColor(mContext.getResources().getColor(R.color.colorUnSelectedTab));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        for (int k = 0; k < ordertabs.length; k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_orders_item, null);

            TextView txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_order);

            txtTabItem.setText(ordertabs[k]);


            if (k == 1) {
                txtTabItem.setTextColor(mContext.getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(mContext.getResources().getColor(R.color.colorUnSelectedTab));
            }
            if (k == 0) {
                txtTabItem.setTextColor(mContext.getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(mContext.getResources().getColor(R.color.colorUnSelectedTab));
            }

            tabLayout.addTab(tabLayout.newTab().setCustomView(tabView));
        }


        cancel_ImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";

        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                mReferenceId = GlobalValues.DEVICE_ID;

            } catch (SecurityException e) {
                GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                mReferenceId = GlobalValues.DEVICE_ID;

            } finally {
                {
                    mCustomerId = "";
                }
            }
        }

        txtSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utility.networkCheck(mContext)) {

                    if (caseType.equalsIgnoreCase("0")) {
                        if (edtName.getText().toString().trim().equalsIgnoreCase("")) {
                            Toast.makeText(mContext, "Please Enter Name", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (edtEmail.getText().toString().trim().equalsIgnoreCase("")) {
                            Toast.makeText(mContext, "Please Enter E-mail", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (edtPhoneNumber.getText().toString().trim().equalsIgnoreCase("")) {
                            Toast.makeText(mContext, "Please Enter Mobile no.", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (edtMessage.getText().toString().trim().equalsIgnoreCase("")) {
                            Toast.makeText(mContext, "Please Enter Message", Toast.LENGTH_SHORT).show();
                            return;
                        } else if (!edtEmail.getText().toString().trim().equalsIgnoreCase("") && edtEmail.getText().toString().trim().length() > 0) {
                            if (!Utility.emailValidation(edtEmail.getText().toString())) {
                                Toast.makeText(mContext, "Please Enter valid E-mail", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        if (!edtPhoneNumber.getText().toString().trim().equalsIgnoreCase("") && edtPhoneNumber.getText().toString().trim().length() > 0) {
                            Log.e("LEN", edtPhoneNumber.getText().toString().length() + "--");
                            if (!(edtPhoneNumber.getText().toString().length() == 8)) {
                                Toast.makeText(mContext, "Please Enter valid Mobile no.", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                    }

                    dialog.dismiss();
                    String url = GlobalUrl.ADD_CART_SET_MENU_URL;
                    Map<String, String> params = new HashMap<String, String>();

                       /* mapData.put("app_id", GlobalValues.APP_ID);
                        mapData.put("product_name", setMenuProduct.getmProductAliasName());
                        mapData.put("menu_set_component", constructSetMenuJson().toString());
                        mapData.put("product_qty", mProductQuantity);
                        mapData.put("allow_cart", "Yes");
                        mapData.put("cart_source", "Mobile");
                        mapData.put("product_id", setMenuProduct.getmProductId());
                        mapData.put("product_sku", setMenuProduct.getmProductSku());
                        mapData.put("product_image", setMenuProduct.getmProductImage());
                        mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                        mapData.put("customer_id", mCustomerId);
                        //mapData.put("reference_id", Utility.getReferenceId(mContext));

                        mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);*/

                    Log.e("product", mSetMenuQuantity + " " + product_unit_price + "--" + quantityCost);
                    double total = product_unit_price * Integer.parseInt(mProductQuantity);
                    params.put("product_id", setMenuProduct.getmProductId());
                    params.put("product_type", setMenuProduct.getmProductType());
                    params.put("app_id", GlobalValues.APP_ID);
                    //params.put("reference_id", Utility.getReferenceId(mContext));
                    params.put("reference_id", "");
                    params.put("customer_id", mCustomerId);
                    if (Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID).equalsIgnoreCase(GlobalValues.DELIVERY_ID)) {
                        params.put("availability_name", "DELIVERY");
                    } else {
                        params.put("availability_name", "TAKEAWAY");
                    }
                    params.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                    params.put("product_qty", String.valueOf(mSetMenuQuantity));
                    params.put("product_name", setMenuProduct.getmProductAliasName());
                    params.put("product_total_price", String.valueOf(quantityCost));
                    params.put("product_unit_price", String.valueOf(product_unit_price));
                    params.put("price_with_modifier", "0.00");
                    params.put("product_sku", setMenuProduct.getmProductSku());
                    params.put("product_image", setMenuProduct.getmProductImage());
                    params.put("allow_cart", "yes");
                    params.put("cart_source", "Mobile");

                    if (caseType.equalsIgnoreCase("0")) {
                        params.put("voucher_gift_name", edtName.getText().toString());
                        params.put("voucher_gift_email", edtEmail.getText().toString());
                        params.put("voucher_gift_mobile", edtPhoneNumber.getText().toString());
                        params.put("voucher_gift_message", edtMessage.getText().toString());
                        params.put("voucher_for", "gift");
                    } else {
                        params.put("voucher_gift_name", Utility.readFromSharedPreference(mContext, GlobalValues.FIRSTNAME)
                                + Utility.readFromSharedPreference(mContext, GlobalValues.LASTNAME));
                        params.put("voucher_gift_email", Utility.readFromSharedPreference(mContext, GlobalValues.EMAIL));
                        params.put("voucher_gift_mobile", "");
                        params.put("voucher_gift_message", "");
                        params.put("voucher_for", "me");
                    }

                    //String additionalNotes = notesText.getText().toString();
                        /*if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                            params.put("product_remarks", additionalNotes);
                        }*/

                    new AddCartTask(params, mProductQuantity).execute(url);
                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void validateSetMenu(List<SetMenuTitle> setMenuTitleList) {
        double product_unit_price = mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus;
        boolean isChildSelected = false;
        int count = 0;
        for (int j = 0; j < setMenuTitleList.size(); j++) {

            isChildSelected = false;
            List<ModifierHeading> modifierHeadings = new ArrayList<>();


            if (setMenuProduct.getmApplyMinMaxSelect() == 0) {
                if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                        && setMenuTitleList.get(j).getSetMenuModifierList().size() >= 0) {

                    for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                        if (setMenuTitleList.get(j).getSetMenuModifierList().get(k).isChecked()) {
                            count++;
                            isChildSelected = true;
                            modifierHeadings = setMenuTitleList.get(j).getSetMenuModifierList().get(k).getModifierHeadingList();

                            break;
                        } else {

                            isChildSelected = false;
                        }
                    }
                    if (isChildSelected) {
                        boolean selected = true;
                        if (modifierHeadings.size() != 0) {
                            List<ModifiersValue> modifiersValueList = modifierHeadings.get(0).getModifiersList();
                            for (int i = 0; i < modifiersValueList.size(); i++) {


                                if (modifiersValueList.get(i).getChekced()) {
                                    selected = false;
                                }

                            }
                            if (selected) {
                                Toast.makeText(mContext, "Select Minimum 1", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }
                    }
                    if (!isChildSelected) {

                        mValidationMessage = setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }
                }
            } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                List<ModifiersValue> modifiersValue = new ArrayList<>();
                for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
                     if (setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() < setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect()) {
                         /*Toast.makeText(mContext, "Please select minimum  " + setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect() + " product for "
                                + setMenuProduct.getSetMenuTitleList().get(i).getmTitleMenuName(), Toast.LENGTH_SHORT).show();*/
                        if (setMenuTitleList.get(i).getmTitleMenuName().equalsIgnoreCase("Sugar Level")) {
                            Toast.makeText(mContext, "Please select sugar level", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(mContext, "Please select size and topping", Toast.LENGTH_SHORT).show();
                        }
                        return;
                    } else {
                     }
                    int postion;
                    if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                            && setMenuTitleList.get(j).getSetMenuModifierList().size() > 0) {

                        if (GlobalValues.MODIFIER_NAME.equalsIgnoreCase("Medium")) {
                            postion = 0;
                        } else if (GlobalValues.MODIFIER_NAME.equalsIgnoreCase("Large")) {
                            postion = 1;
                        } else {
                            postion = 0;
                        }

                        if (setMenuTitleList.get(j).getSetMenuModifierList().size() == 1) {
                            postion = 0;
                        }

                        for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                            modifierHeadings = setMenuTitleList.get(j).getSetMenuModifierList().get(postion).getModifierHeadingList();


                            if (modifierHeadings != null && modifierHeadings.size() > 0) {
                                for (int a = 0; a < modifierHeadings.size(); a++) {
                                    modifiersValue = modifierHeadings.get(a).getModifiersValueList();
                                    for (int b = 0; b < modifiersValue.size(); b++) {

                                        if (modifierHeadings.get(a).gettQuantity() < modifierHeadings.get(a).getmModifierMinSelect()) {
                                            /* Toast.makeText(mContext, "Please select  minimum  " + modifierHeadings.get(a).getmModifierMinSelect() + " product for "
                                                    + modifierHeadings.get(a).getmModifierHeading(), Toast.LENGTH_SHORT).show();*/
                                            Toast.makeText(mContext, "Please select size and topping", Toast.LENGTH_SHORT).show();
                                            return;
                                        }
                                    }
                                }
                            } else {
                            }
                        }
                    }
                }
            }
        }


        if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

            if (count == setMenuTitleList.size()) {
                if (Utility.networkCheck(mContext)) {

                    String url = GlobalUrl.ADD_CART_SET_MENU_URL;

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = Utility.getReferenceId(mContext);

                    } else {
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                                // TODO:
                                //    ActivityCompat#requestPermissions
                                // here to request the missing permissions, and then overriding
                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                //                                          int[] grantResults)
                                // to handle the case where the user grants the permission. See the documentation
                                // for ActivityCompat#requestPermissions for more details.
                                return;
                            }
                            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } catch (Exception e) {
                            GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } finally {
                            mCustomerId = "";
                        }
                    }



                    Map<String, String> mapData = new HashMap<>();
                    mapData.put("app_id", GlobalValues.APP_ID);
                    mapData.put("product_name", setMenuProduct.getmProductAliasName());
                    mapData.put("menu_set_component", constructSetMenuJson().toString());
                    mapData.put("product_qty", mProductQuantity);
                    mapData.put("allow_cart", "Yes");
                    mapData.put("cart_source", "Mobile");
                    mapData.put("product_id", setMenuProduct.getmProductId());
                    mapData.put("product_sku", setMenuProduct.getmProductSku());
                    mapData.put("product_image", setMenuProduct.getmProductImage());
                    mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                    mapData.put("customer_id", mCustomerId);
                    //mapData.put("reference_id", Utility.getReferenceId(mContext));

                    mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);


                    String additionalNotes = notesText1.getText().toString();
                    if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                        mapData.put("product_remarks", additionalNotes);
                    }

                    double total = product_unit_price * Integer.parseInt(mProductQuantity);
                    if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                        //mapData.put("product_total_price", String.valueOf(mquanititycost_src));

                        mapData.put("product_total_price", String.valueOf(total));
                        mapData.put("product_unit_price", String.valueOf(product_unit_price));


                    } else {
                        mapData.put("product_total_price", String.valueOf(total));

                        mapData.put("product_unit_price", String.valueOf(product_unit_price));


                    }

                    mapData.put("product_unit_price", String.valueOf(product_unit_price));

                    mapData.put("price_with_modifier", String.valueOf(setMenuProduct.getmProductPrice()));

                    mapData.put("product_type", setMenuProduct.getmProductType());

                    if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                        mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                    } else {
                        mapData.put("product_modifier_parent_id", "");

                    }




                    new AddCartTask(mapData, mProductQuantity).execute(url);

                    mquanititycost_src = 0.0;

                } else {
                    Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
                }

            } else {

                Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
            }

        } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

            if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {
                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.ADD_CART_SET_MENU_URL;

                        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                            mReferenceId = Utility.getReferenceId(mContext);

                        } else {
                            try {
                                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                                GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                                mReferenceId = GlobalValues.DEVICE_ID;

                            } catch (Exception e) {
                                GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                                mReferenceId = GlobalValues.DEVICE_ID;

                            } finally {
                                mCustomerId = "";
                            }
                        }



                        Map<String, String> mapData = new HashMap<>();
                        //mapData.put("reference_id", mReferenceId);
                        mapData.put("customer_id", mCustomerId);
                        mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                        mapData.put("app_id", GlobalValues.APP_ID);
                        mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                        mapData.put("product_qty", mProductQuantity);
                        mapData.put("allow_cart", "Yes");
                        mapData.put("cart_source", "Mobile");
                        mapData.put("product_id", setMenuProduct.getmProductId());
                        mapData.put("product_name", setMenuProduct.getmProductAliasName());
                        mapData.put("menu_set_component", constructSetMenuJson().toString());

                        double total = product_unit_price * Integer.parseInt(mProductQuantity);
                        mapData.put("product_total_price", String.valueOf(total));
                        //mapData.put("product_total_price", txtModifierPrice.getText().toString().replace("$", ""));

                        try {

                            mapData.put("product_unit_price", String.valueOf(product_unit_price));

                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        mapData.put("product_image", setMenuProduct.getmProductImage());
                        mapData.put("price_with_modifier", String.valueOf(setMenuProduct.getmProductPrice()));
                        mapData.put("product_sku", setMenuProduct.getmProductSku());
                        mapData.put("product_type", setMenuProduct.getmProductType());

                        if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                            mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                        } else {
                            mapData.put("product_modifier_parent_id", "");

                        }


                        new AddCartTask(mapData, mProductQuantity).execute(url);

                        mquanititycost_src = 0.0;

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
                    }


                } else {
                    setmenuComponentMethod();
                }
            } else {
                setmenuComponentMethod();
            }

        }


    }

    public void setmenuComponentMethod() {


        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.ADD_CART_SET_MENU_URL;

            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = Utility.getReferenceId(mContext);

            } else {
                try {
                    TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                        // TODO:
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                    mReferenceId = GlobalValues.DEVICE_ID;

                } catch (Exception e) {
                    GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                    mReferenceId = GlobalValues.DEVICE_ID;

                } finally {
                    mCustomerId = "";
                }
            }




            Map<String, String> mapData = new HashMap<>();
            //mapData.put("reference_id", Utility.getReferenceId(mContext));
            mapData.put("customer_id", mCustomerId);
            mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
            mapData.put("app_id", GlobalValues.APP_ID);
            mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
            mapData.put("product_qty", mProductQuantity);
            mapData.put("product_id", setMenuProduct.getmProductId());
            mapData.put("product_name", setMenuProduct.getmProductAliasName());
            mapData.put("allow_cart", "Yes");
            mapData.put("cart_source", "Mobile");
            String additionalNotes = notesText1.getText().toString();
            if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                mapData.put("product_remarks", additionalNotes);
            }

              /*  if (setMenuProduct.getmApplyMinMaxSelect() == 1)
                {
                    mapData.put("product_total_price",String.valueOf(mquanititycost_src));

                    mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));


                }
                else
                {*/
            double product_unit_price = mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus;
            double total = product_unit_price * Integer.parseInt(mProductQuantity);
            mapData.put("product_total_price", String.valueOf(total));

            mapData.put("product_unit_price", String.valueOf(product_unit_price));


            // }

            mapData.put("product_unit_price", String.valueOf(product_unit_price));
            mapData.put("product_image", setMenuProduct.getmProductImage());
            mapData.put("price_with_modifier", String.valueOf(setMenuProduct.getmProductPrice()));
            mapData.put("product_sku", setMenuProduct.getmProductSku());
            mapData.put("product_type", setMenuProduct.getmProductType());

            if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
            } else {
                mapData.put("product_modifier_parent_id", "");

            }

            mapData.put("menu_set_component", constructSetMenuJson().toString());

            new AddCartTask(mapData, mProductQuantity).execute(url);

            mquanititycost_src = 0.0;

        } else {
            Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
        }

    }

    private String constructSetMenuJson() {
        //mainObj
        JSONArray menuComponentsJSONArray = new JSONArray();
        List<SetMenuTitle> titleList = setMenuProduct.getSetMenuTitleList();
        for (int i = 0; i < titleList.size(); i++) {

            if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                if (titleList.get(i).getmenu_component_modifier_apply().equalsIgnoreCase("0")) {
                    if (titleList.get(i).getmultipleselection_apply().equalsIgnoreCase("0")) {
                        //modifierName
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                            jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());
                            //jsonObject.put("min_max_flag", "1");

                            //products array
                            JSONArray productArray = new JSONArray();
                            List<SetMenuModifier> arrProduct = titleList.get(i).getSetMenuModifierList();
                            total_unitprices = 0.0;
                            for (int i1 = 0; i1 < arrProduct.size(); i1++) {
                                //single product
                                JSONObject proobj = new JSONObject();
                                if (Integer.parseInt(arrProduct.get(i1).getTotalQuantity()) > 0 || arrProduct.get(i1).getmQuantity() > 0) {
                                    try {
                                        proobj.put("product_id", arrProduct.get(i1).getmModifierId());
                                        proobj.put("product_name", arrProduct.get(i1).getmModifierAliasName());
                                        proobj.put("product_sku", arrProduct.get(i1).getmModifierSku());
                                        proobj.put("product_price", arrProduct.get(i1).getmModifierPrice());
                                        proobj.put("product_qty", arrProduct.get(i1).getmQuantity());
                                        if (i1 == 0) {
                                            total_unitprices = Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + mSetMenuPrice;
                                        } else if (i1 == 1) {
                                            total_unitprices = Double.parseDouble(arrProduct.get(0).getmModifierPrice()) + Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + mSetMenuPrice;
                                        }
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    productArray.put(proobj);
                                }
                            }

                            try {
                                jsonObject.put("product_details", productArray);
                                menuComponentsJSONArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (JSONException je) {
                            je.printStackTrace();
                        }
                    } else {
                        //modifierName
                        JSONObject jsonObject = new JSONObject();
                        try {
                            jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                            jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());

                            JSONArray productJSONArray = new JSONArray();
                            List<SetMenuModifier> setMenuModifierList = titleList.get(i).getSetMenuModifierList();
                            for (int j = 0; j < setMenuModifierList.size(); j++) {
                                SetMenuModifier setMenuModifier = setMenuModifierList.get(j);
                                if (setMenuModifier.isChecked()) {
                                    JSONObject productJSONObj = new JSONObject();
                                    productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                    productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));

                                    productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                    productJSONObj.put("product_qty", 1);

                                    if (setMenuModifier.getModifierHeadingList() != null &&
                                            setMenuModifier.getModifierHeadingList().size() > 0) {
                                        JSONArray modifierJSONArray = new JSONArray();
                                        List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();
                                        if (modifierHeadingList.size() > 0) {
                                            for (int k = 0; k < modifierHeadingList.size(); k++) {
                                                ModifierHeading modifierHeading = modifierHeadingList.get(k);
                                                JSONObject modJsonObject = new JSONObject();
                                                modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                                modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                                JSONArray modifValueJSONArray = new JSONArray();
                                                List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                                for (int l = 0; l < modifiersValueList.size(); l++) {
                                                    ModifiersValue modifierValue = modifiersValueList.get(l);
                                                    if (modifierValue.getChekced()) {
                                                        JSONObject modifValueJSONObj = new JSONObject();

                                                        if (modifierValue.getmSubModifierTotal() > 0) {
                                                            modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                            modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                            modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                            modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());
                                                            modifValueJSONArray.put(modifValueJSONObj);
                                                        }
                                                    }
                                                }

                                                modJsonObject.put("modifiers_values", modifValueJSONArray);
                                                modifierJSONArray.put(modJsonObject);
                                            }
                                            productJSONObj.put("modifiers", modifierJSONArray);
                                        } else {

                                        }

                                    } else {

                                    }
                                    productJSONArray.put(productJSONObj);
                                } else {

                                }
                            }
                            jsonObject.put("product_details", productJSONArray);
                            menuComponentsJSONArray.put(jsonObject);
                        } catch (JSONException je) {
                            je.printStackTrace();
                        }
                    }
                } else {
                    //modifierName
                    JSONObject jsonObject = new JSONObject();
                    try {
                        jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                        jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());

                        JSONArray productJSONArray = new JSONArray();
                        List<SetMenuModifier> setMenuModifierList = titleList.get(i).getSetMenuModifierList();
                        for (int j = 0; j < setMenuModifierList.size(); j++) {
                            SetMenuModifier setMenuModifier = setMenuModifierList.get(j);
                            if (setMenuModifier.isChecked()) {
                                JSONObject productJSONObj = new JSONObject();
                                productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                                productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                productJSONObj.put("product_qty", 1);

                                if (setMenuModifier.getModifierHeadingList() != null &&
                                        setMenuModifier.getModifierHeadingList().size() > 0) {
                                    JSONArray modifierJSONArray = new JSONArray();
                                    List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();
                                    if (modifierHeadingList.size() > 0) {
                                        for (int k = 0; k < modifierHeadingList.size(); k++) {
                                            ModifierHeading modifierHeading = modifierHeadingList.get(k);
                                            JSONObject modJsonObject = new JSONObject();
                                            modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                            modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                            JSONArray modifValueJSONArray = new JSONArray();
                                            List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                            for (int l = 0; l < modifiersValueList.size(); l++) {
                                                ModifiersValue modifierValue = modifiersValueList.get(l);
                                                if (modifierValue.getChekced()) {
                                                    JSONObject modifValueJSONObj = new JSONObject();

                                                    if (modifierValue.getmSubModifierTotal() > 0) {
                                                        modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                        modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                        modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                        modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());
                                                        modifValueJSONArray.put(modifValueJSONObj);
                                                    }
                                                }
                                            }

                                            modJsonObject.put("modifiers_values", modifValueJSONArray);
                                            modifierJSONArray.put(modJsonObject);
                                        }
                                        productJSONObj.put("modifiers", modifierJSONArray);
                                    } else {

                                    }

                                } else {

                                }
                                productJSONArray.put(productJSONObj);
                            } else {

                            }
                        }
                        jsonObject.put("product_details", productJSONArray);
                        menuComponentsJSONArray.put(jsonObject);
                    } catch (JSONException je) {
                        je.printStackTrace();
                    }
                }
            } else {
                //modifierName
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("menu_component_id", titleList.get(i).getmTitleMenuId());
                    jsonObject.put("menu_component_name", titleList.get(i).getmTitleMenuName());

                    JSONArray productJSONArray = new JSONArray();
                    List<SetMenuModifier> setMenuModifierList = titleList.get(i).getSetMenuModifierList();
                    for (int j = 0; j < setMenuModifierList.size(); j++) {
                        SetMenuModifier setMenuModifier = setMenuModifierList.get(j);
                        if (setMenuModifier.isChecked()) {
                            JSONObject productJSONObj = new JSONObject();
                            productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                            productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                            productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                            productJSONObj.put("product_qty", 1);

                            if (setMenuModifier.getModifierHeadingList() != null &&
                                    setMenuModifier.getModifierHeadingList().size() > 0) {
                                JSONArray modifierJSONArray = new JSONArray();
                                List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();
                                if (modifierHeadingList.size() > 0) {
                                    for (int k = 0; k < modifierHeadingList.size(); k++) {
                                        ModifierHeading modifierHeading = modifierHeadingList.get(k);
                                        JSONObject modJsonObject = new JSONObject();
                                        modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                        modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                        JSONArray modifValueJSONArray = new JSONArray();
                                        List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                        for (int l = 0; l < modifiersValueList.size(); l++) {
                                            ModifiersValue modifierValue = modifiersValueList.get(l);
                                            if (modifierValue.getChekced()) {
                                                JSONObject modifValueJSONObj = new JSONObject();

                                                if (modifierValue.getmSubModifierTotal() > 0) {
                                                    modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                    modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                    modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                    modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());
                                                    modifValueJSONArray.put(modifValueJSONObj);
                                                }
                                            }
                                        }

                                        modJsonObject.put("modifiers_values", modifValueJSONArray);
                                        modifierJSONArray.put(modJsonObject);
                                    }
                                    productJSONObj.put("modifiers", modifierJSONArray);
                                } else {

                                }

                            } else {

                            }
                            productJSONArray.put(productJSONObj);
                        } else {

                        }
                    }
                    jsonObject.put("product_details", productJSONArray);
                    menuComponentsJSONArray.put(jsonObject);
                } catch (JSONException je) {
                    je.printStackTrace();
                }
            }
        }

        return menuComponentsJSONArray.toString();
    }

    private String constructSetMenuJson1() {
        try {

            if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

                if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                    if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

                        JSONArray menuComponentsJSONArray = new JSONArray();
                        List<SetMenuTitle> arrsetMenuComponent = setMenuProduct.getSetMenuTitleList();

                        for (int i = 0; i < arrsetMenuComponent.size(); i++) {
                            JSONObject jsonObject = new JSONObject();


                            try {
                                jsonObject.put("menu_component_id", arrsetMenuComponent.get(i).getmTitleMenuId());
                                jsonObject.put("menu_component_name", arrsetMenuComponent.get(i).getmTitleMenuName());
                                jsonObject.put("min_max_flag", "1");

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                            JSONArray productArray = new JSONArray();

                            List<SetMenuModifier> arrProduct = arrsetMenuComponent.get(i).getSetMenuModifierList();
                            total_unitprices = 0.0;

                            for (int i1 = 0; i1 < arrProduct.size(); i1++) {
                                JSONObject proobj = new JSONObject();

                                if (Integer.valueOf(arrProduct.get(i1).getTotalQuantity()) > 0) {
                                    try {
                                        proobj.put("product_id", arrProduct.get(i1).getmModifierId());
                                        proobj.put("product_name", arrProduct.get(i1).getmModifierAliasName());
                                        proobj.put("product_sku", arrProduct.get(i1).getmModifierSku());
                                        proobj.put("product_price", arrProduct.get(i1).getmModifierPrice());
                                        proobj.put("product_qty", arrProduct.get(i1).getTotalQuantity());


                                        if (i1 == 0) {
                                            total_unitprices = Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + mSetMenuPrice;


                                        } else if (i1 == 1) {

                                            total_unitprices = Double.parseDouble(arrProduct.get(0).getmModifierPrice()) + Double.parseDouble(arrProduct.get(i1).getmModifierPrice()) + mSetMenuPrice;



                                        }


                                        //                        proobj.put("product_meta_title",String.valueOf(arrProduct.get(i1).get(0).getTotalpPrize()));
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }
                                    productArray.put(proobj);
                                }


                            }


                            try {
                                jsonObject.put("product_details", productArray);

                                menuComponentsJSONArray.put(jsonObject);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }


                        return menuComponentsJSONArray.toString();
                    } else {
                        JSONArray menuComponentsJSONArray = new JSONArray();
                        List<SetMenuTitle> setMenuTitleList = setMenuProduct.getSetMenuTitleList();

                        for (int i = 0; i < setMenuTitleList.size(); i++) {  //menuCompontnt size


                            SetMenuTitle setMenuTitle = setMenuTitleList.get(i);

                            JSONObject menuComponentJSON = new JSONObject();

                            menuComponentJSON.put("menu_component_id", setMenuTitle.getmTitleMenuId());
                            menuComponentJSON.put("menu_component_name", setMenuTitle.getmTitleMenuName());

                            JSONArray productJSONArray = new JSONArray();
                            List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                            for (int j = 0; j < setMenuModifierList.size(); j++) {

                                SetMenuModifier setMenuModifier = setMenuModifierList.get(j);

                                if (setMenuModifier.isChecked()) {

                                    JSONObject productJSONObj = new JSONObject();

                                    productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                    productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                                    productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                    productJSONObj.put("product_qty", "1");

                                    if (setMenuModifier.getModifierHeadingList() != null &&
                                            setMenuModifier.getModifierHeadingList().size() > 0) {

                                        JSONArray modifierJSONArray = new JSONArray();

                                        List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();


                                        //modifierModSelectedArrayList      boolean

                                        if (modifierHeadingList.size() > 0) {      //start
                                            for (int k = 0; k < modifierHeadingList.size(); k++) {

                                                ModifierHeading modifierHeading = modifierHeadingList.get(k);

                                                JSONObject modJsonObject = new JSONObject();

                                                modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                                modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                                JSONArray modifValueJSONArray = new JSONArray();
                                                List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                                for (int l = 0; l < modifiersValueList.size(); l++) {


                                                    ModifiersValue modifierValue = modifiersValueList.get(l);

                                                    if (modifierValue.getChekced()) { //if the index is boolean is true

                                                        JSONObject modifValueJSONObj = new JSONObject();
                                                        modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                        modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                        modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                        modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());

                                                        modifValueJSONArray.put(modifValueJSONObj);
                                                    }

                                                }

                                                modJsonObject.put("modifiers_values", modifValueJSONArray);


                                                modifierJSONArray.put(modJsonObject);

                                            }


                                            productJSONObj.put("modifiers", modifierJSONArray);

                                        } else {

                                        }


                                    } else { //No modifier for the product

                                    }

                                    productJSONArray.put(productJSONObj);


                                } else {  //Product is not selected

                                }

                            }

                            menuComponentJSON.put("product_details", productJSONArray);


                            menuComponentsJSONArray.put(menuComponentJSON);

                        }

                        return menuComponentsJSONArray.toString();

                    }
                } else {
                    JSONArray menuComponentsJSONArray = new JSONArray();
                    List<SetMenuTitle> setMenuTitleList = setMenuProduct.getSetMenuTitleList();

                    for (int i = 0; i < setMenuTitleList.size(); i++) {  //menuCompontnt size


                        SetMenuTitle setMenuTitle = setMenuTitleList.get(i);

                        JSONObject menuComponentJSON = new JSONObject();

                        menuComponentJSON.put("menu_component_id", setMenuTitle.getmTitleMenuId());
                        menuComponentJSON.put("menu_component_name", setMenuTitle.getmTitleMenuName());

                        JSONArray productJSONArray = new JSONArray();
                        List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                        for (int j = 0; j < setMenuModifierList.size(); j++) {

                            SetMenuModifier setMenuModifier = setMenuModifierList.get(j);

                            if (setMenuModifier.isChecked()) {


                                JSONObject productJSONObj = new JSONObject();

                                productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                                productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                                productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                                productJSONObj.put("product_qty", "1");

                                if (setMenuModifier.getModifierHeadingList() != null &&
                                        setMenuModifier.getModifierHeadingList().size() > 0) {

                                    JSONArray modifierJSONArray = new JSONArray();

                                    List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();



                                    //modifierModSelectedArrayList      boolean

                                    if (modifierHeadingList.size() > 0) {      //start
                                        for (int k = 0; k < modifierHeadingList.size(); k++) {

                                            ModifierHeading modifierHeading = modifierHeadingList.get(k);

                                            JSONObject modJsonObject = new JSONObject();

                                            modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                            modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                            JSONArray modifValueJSONArray = new JSONArray();
                                            List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                            for (int l = 0; l < modifiersValueList.size(); l++) {


                                                ModifiersValue modifierValue = modifiersValueList.get(l);

                                                if (modifierValue.getChekced()) { //if the index is boolean is true

                                                    JSONObject modifValueJSONObj = new JSONObject();
                                                    modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                    modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                    modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                    modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());

                                                    modifValueJSONArray.put(modifValueJSONObj);
                                                }

                                            }

                                            modJsonObject.put("modifiers_values", modifValueJSONArray);


                                            modifierJSONArray.put(modJsonObject);


                                        }


                                        productJSONObj.put("modifiers", modifierJSONArray);


                                    } else {

                                    }


                                } else { //No modifier for the product

                                }

                                productJSONArray.put(productJSONObj);


                            } else {  //Product is not selected

                            }

                        }

                        menuComponentJSON.put("product_details", productJSONArray);


                        menuComponentsJSONArray.put(menuComponentJSON);

                    }

                    return menuComponentsJSONArray.toString();

                }

            } else {

                JSONArray menuComponentsJSONArray = new JSONArray();
                List<SetMenuTitle> setMenuTitleList = setMenuProduct.getSetMenuTitleList();

                for (int i = 0; i < setMenuTitleList.size(); i++) {  //menuCompontnt size


                    SetMenuTitle setMenuTitle = setMenuTitleList.get(i);

                    JSONObject menuComponentJSON = new JSONObject();

                    menuComponentJSON.put("menu_component_id", setMenuTitle.getmTitleMenuId());
                    menuComponentJSON.put("menu_component_name", setMenuTitle.getmTitleMenuName());

                    JSONArray productJSONArray = new JSONArray();
                    List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                    for (int j = 0; j < setMenuModifierList.size(); j++) {

                        SetMenuModifier setMenuModifier = setMenuModifierList.get(j);

                        if (setMenuModifier.isChecked()) {

                            JSONObject productJSONObj = new JSONObject();

                            productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                            productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                            productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());
                            productJSONObj.put("product_qty", "1");

                            if (setMenuModifier.getModifierHeadingList() != null &&
                                    setMenuModifier.getModifierHeadingList().size() > 0) {

                                JSONArray modifierJSONArray = new JSONArray();

                                List<ModifierHeading> modifierHeadingList = setMenuModifier.getModifierHeadingList();

                                //modifierModSelectedArrayList      boolean

                                if (modifierHeadingList.size() > 0) {      //start
                                    for (int k = 0; k < modifierHeadingList.size(); k++) {

                                        ModifierHeading modifierHeading = modifierHeadingList.get(k);

                                        JSONObject modJsonObject = new JSONObject();

                                        modJsonObject.put("modifier_name", modifierHeading.getmModifierHeading());
                                        modJsonObject.put("modifier_id", modifierHeading.getmModifierHeadingId());

                                        JSONArray modifValueJSONArray = new JSONArray();
                                        List<ModifiersValue> modifiersValueList = modifierHeading.getModifiersList();

                                        for (int l = 0; l < modifiersValueList.size(); l++) {


                                            ModifiersValue modifierValue = modifiersValueList.get(l);

                                            if (modifierValue.getChekced()) { //if the index is boolean is true

                                                JSONObject modifValueJSONObj = new JSONObject();
                                                modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                                                modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                                                modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                                                modifValueJSONObj.put("modifier_value_qty", modifierValue.getmSubModifierTotal());

                                                modifValueJSONArray.put(modifValueJSONObj);
                                            }

                                        }

                                        modJsonObject.put("modifiers_values", modifValueJSONArray);


                                        modifierJSONArray.put(modJsonObject);


                                    }


                                    productJSONObj.put("modifiers", modifierJSONArray);

                                } else {

                                }


                            } else { //No modifier for the product

                            }

                            productJSONArray.put(productJSONObj);


                        } else {  //Product is not selected

                        }

                    }

                    menuComponentJSON.put("product_details", productJSONArray);


                    menuComponentsJSONArray.put(menuComponentJSON);

                }

                return menuComponentsJSONArray.toString();


            }


        } catch (JSONException e) {
            e.printStackTrace();

            return "";
        }

    }


    public JSONArray constructModifierJson() {

        JSONArray modifierJSONArray = new JSONArray();

        if (modifierProduct.getModifiersList() != null) {


            try {

                List<ModifierHeading> modifiers = modifierProduct.getModifiersList();

                //modifierModSelectedArrayList      boolean

                for (int i = 0; i < modifiers.size(); i++) {

                    ModifierHeading modifierType = modifiers.get(i);

                    JSONObject jsonObject = new JSONObject();


                    jsonObject.put("modifier_name", modifierType.getmModifierHeading());
                    jsonObject.put("modifier_id", modifierType.getmModifierHeadingId());

                    JSONArray modifValueJSONArray = new JSONArray();

                    List<ModifiersValue> modifierValues = modifierType.getModifiersList();

                    for (int j = 0; j < modifierValues.size(); j++) {

                        Boolean isSelected = modifierValues.get(j).getChekced();

                        if (isSelected) { //if the index is boolean is true
                            ModifiersValue modifierValue = modifierValues.get(j);

                            JSONObject modifValueJSONObj = new JSONObject();
                            modifValueJSONObj.put("modifier_value_name", modifierValue.getmModifierName());
                            modifValueJSONObj.put("modifier_value_id", modifierValue.getmModifierId());
                            modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());
                            modifValueJSONObj.put("modifier_value_qty", 1);

                            modifValueJSONArray.put(modifValueJSONObj);
                        }
                    }

                    jsonObject.put("modifiers_values", modifValueJSONArray);


                    modifierJSONArray.put(jsonObject);
                }

                //


                //     formBodyBuilder.add("menu_set_component", "");//null


            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        return modifierJSONArray;

    }


    private void modifierproductDetailsDialog(final Context mContext, final String mProductId, String quantity) {


        mModifierPrice = 0.0;
        quantityCost = 0.0;
        mModifierQuantity = 1;


        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_search_product_details_dialog1);
        dialog.setCancelable(true);
        dialog.show();


        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        final TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        TextView category_name = dialog.findViewById(R.id.category_name);
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        RecyclerView modifierRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        ImageView layoutClose = dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        // LinearLayout layoutModifierParent = (LinearLayout) dialog.findViewById(R.id.layoutModifierParent);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);
        RelativeLayout layoutBottom = (RelativeLayout) dialog.findViewById(R.id.layoutBottom);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        text_linefav = (TextView) dialog.findViewById(R.id.text_linefav);

        try {
            Toolbar toolbar = dialog.findViewById(R.id.toolBar);
            toolbar.setVisibility(View.GONE);
            LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
            ImageView user_imageview = toolbar.findViewById(R.id.user_imageview);
            RelativeLayout layoutSearch = toolbar.findViewById(R.id.layoutSearch);
            RelativeLayout layoutCart = toolbar.findViewById(R.id.layoutCart);
            TextView txtCartCount = (TextView) toolbar.findViewById(R.id.txtCartCount);
            user_imageview.setVisibility(View.VISIBLE);
            layoutSearch.setVisibility(View.VISIBLE);
            layoutCart.setVisibility(View.VISIBLE);

            String cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {

                txtCartCount.setVisibility(View.VISIBLE);
                txtCartCount.setText(cartCount);

            } else {
                txtCartCount.setVisibility(View.GONE);
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });

                } else {
                    addFavouriteMethod(mProductId);
                }
            }
        });


        dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                finish();
            }
        });
        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        modifierRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(modifierProduct.getmProductName().replace("\\", ""));
        txtProductDesc.setText(modifierProduct.getmProductDesc());
        category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));

        //layoutModifierParent.setVisibility(View.VISIBLE);

        try {
            if (databaseHandler.getAllTotalData(modifierProduct.getmProductId()) != null) {

                Cart cart = databaseHandler.getAllTotalData(modifierProduct.getmProductId());
                layoutMaxCount.setVisibility(View.VISIBLE);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());



            } else {
                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mProductQuantity = txtQuantity.getText().toString();


        if (Integer.parseInt(modifierProduct.getmProductType()) == 4) {

            if (modifierProduct.getModifiersList().size() > 0) {

                modifierHeadingRecyclerAdapter = new ModifierHeadingRecyclerAdapter(mContext, modifierProduct.getModifiersList(),
                        this.mProductId);
                modifierRecyclerView.setAdapter(modifierHeadingRecyclerAdapter);
                modifierRecyclerView.setItemAnimator(new DefaultItemAnimator());
                modifierRecyclerView.setNestedScrollingEnabled(false);

                modifierHeadingRecyclerAdapter.checkAllModifiersSelected();

            }
        } else {

        }


        if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0) {

            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);

        } else {
            if (modifierProduct.getmProductImage() != null && modifierProduct.getmProductImage().length() > 0) {

                Picasso.with(mContext).load(mBasePath + modifierProduct.getmProductImage()).
                        error(R.drawable.place_holder_sushi_tei).into(imgProduct);

            } else {

                Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);

            }
        }

        mModifierPrice = Double.parseDouble(modifierProduct.getmProductPrice());

        SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mModifierPrice)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModifierPrice.setText(cs);

        quantityCost = mModifierPrice;

        layoutBottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (!isCustomerLoggedIn()) {

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                } else {

                    if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                        mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                        mReferenceId = "";

                    } else {
                        try {
                            TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                            GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } catch (SecurityException e) {
                            GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                            mReferenceId = GlobalValues.DEVICE_ID;

                        } finally {
                            {
                                mCustomerId = "";
                            }
                        }
                    }

                    if (text_linefav.getText().toString().equalsIgnoreCase("Remove from favourite")) {
                        String url = GlobalUrl.FavouriteURl;

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("app_id", GlobalValues.APP_ID);
                        param.put("customer_id", mCustomerId);
                        param.put("product_id", searchProduct.getmProduct_primary_id());
                        param.put("fav_flag", "No");
                        param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


                        new FavouritesAddTask(param).execute(url);

                        StatusFav = "0";

                    } else {
                        String url = GlobalUrl.FavouriteURl;

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("app_id", GlobalValues.APP_ID);
                        param.put("customer_id", mCustomerId);
                        param.put("product_id", searchProduct.getmProduct_primary_id());
                        param.put("fav_flag", "Yes");
                        param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


                        new FavouritesAddTask(param).execute(url);

                        StatusFav = "1";

                    }

                }


            }
        });

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(txtQuantity.getText().toString());

                count++;

                mModifierQuantity = count;

                quantityCost += mModifierPrice;


                txtQuantity.setText(String.valueOf(count));

                SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                txtModifierPrice.setText(cs);

                mProductQuantity = txtQuantity.getText().toString();


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                if (count > 1) {
                    count--;

                    mModifierQuantity = count;
                    quantityCost -= mModifierPrice;

                    txtQuantity.setText(count + "");


                    SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtModifierPrice.setText(cs);

                    mProductQuantity = txtQuantity.getText().toString();

                 }
            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
                finish();
            }
        });


        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {

                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());

            } else {

                layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        lly_addToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                txtDone.performClick();
            }
        });

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(intent);
                                finish();
                            }
                        }
                    });
                } else {
                    Toast.makeText(mContext, "yes", Toast.LENGTH_SHORT).show();
                    boolean isChildSelected = false, isChildMaxSelected = false;
                    //dialog.dismiss();
                    int count = 0, pos = 0;

//                                           if (isCorrectCombination) {

                    if (modifierProduct.getModifiersList() != null) {
                        if (modifierProduct.getModifiersList().size() > 0) {
                            for (int i = 0; i < modifierProduct.getModifiersList().size(); i++) {

                                isChildSelected = false;

                                if (modifierProduct.getModifiersList().get(i).getModifiersList() != null
                                        && modifierProduct.getModifiersList().get(i).getModifiersList().size() > 0) {

                                    for (int j = 0; j < modifierProduct.getModifiersList().get(i).getModifiersList().size(); j++) {
                                        if (modifierProduct.getModifiersList().get(i).getModifiersList().get(j).getChekced()) {
                                            count++;
                                            isChildSelected = true;
                                            break;
                                        } else {
                                            isChildSelected = false;
                                        }
                                    }

                                    if (!isChildSelected) {

                                        mValidationMessage = modifierProduct.getModifiersList().get(i).getmModifierHeading();

                                        break;
                                    }
                                }
                            }

//                        for (int i = 0; i < modifierProduct.getModifiersList().size(); i++) {
//
//                            isChildSelected = false;
//
//                            if (modifierProduct.getModifiersList().get(i).getModifiersList() != null
//                                    && modifierProduct.getModifiersList().get(i).getModifiersList().size() > 0) {
//
//                                if (modifierProduct.getModifiersList().get(i).getmMaxSelected() ==
//                                        modifierProduct.getModifiersList().get(i).getmModifierMaxSelect()) {
//
//                                    count++;
//                                    isChildSelected = true;
//                                    isChildMaxSelected = true;
////                            break;
//                                } else if (modifierProduct.getModifiersList().get(i).getmMaxSelected() == 0) {
//                                    isChildSelected = false;
//                                    isChildMaxSelected = false;
//                                    mValidationMessage = modifierProduct.getModifiersList().get(i).getmModifierHeading();
//                                    break;
//
//                                } else if (modifierProduct.getModifiersList().get(i).getmMaxSelected() <
//                                        modifierProduct.getModifiersList().get(i).getmModifierMaxSelect()) {
//
//                                    isChildSelected = true;
//                                    isChildMaxSelected = false;
//                                    mValidationMessage = modifierProduct.getModifiersList().get(i).getmModifierHeading();
//                                    break;
//                                }
//                            }
//                        }


                        }
                    }

                    if (count == modifierProduct.getModifiersList().size()) {

                        if (isCorrectCombination) {

                            if (Utility.networkCheck(mContext)) {

                                String url = GlobalUrl.ADD_CART_URL;

                                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                                    mReferenceId = "";

                                } else {
                                    mReferenceId = Utility.getReferenceId(mContext);
                                    mCustomerId = "";
                                }


                                Map<String, String> mapData = new HashMap<>();
                                //mapData.put("reference_id", mReferenceId);
                                mapData.put("customer_id", mCustomerId);
                                mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                                mapData.put("app_id", GlobalValues.APP_ID);
                                mapData.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                                mapData.put("product_qty", mProductQuantity);
                                mapData.put("product_id", modifierProduct.getmProductId());
                                mapData.put("product_name", modifierProduct.getmProductName());
                                mapData.put("product_total_price", String.valueOf(quantityCost));
                                mapData.put("product_unit_price", String.valueOf(mModifierPrice));
                                mapData.put("product_image", modifierProduct.getmProductImage());
                                mapData.put("price_with_modifier", String.valueOf(setMenuProduct.getmProductPrice()));
                                mapData.put("product_sku", modifierProduct.getmProductSku());
                                mapData.put("product_type", modifierProduct.getmProductType());

                                if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                                    mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                                } else {
                                    mapData.put("product_modifier_parent_id", "");

                                }

                                mapData.put("modifiers", constructModifierJson().toString());

                                new AddCartTask(mapData, mProductQuantity).execute(url);

                            } else {
                                Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
                            }
                        } else {

                            Toast.makeText(mContext, "Please select valid combination", Toast.LENGTH_SHORT).show();
                        }
                    } else {

                        if (!isChildSelected) {
                            Toast.makeText(mContext, "Please select " + mValidationMessage, Toast.LENGTH_SHORT).show();

                        } else {
                            if (!isChildMaxSelected) {

                                Toast.makeText(mContext, "Please select Minmum/Maximum options in " + mValidationMessage, Toast.LENGTH_SHORT).show();
                            }

                        }

                    }
                }/*else{

                    Toast.makeText(mContext, "Please select valid combination", Toast.LENGTH_SHORT).show();
                }*/
            }

        });


    }


    class ProductDetailsTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<ModifiersValue> modifiersValueList;
        private String mProductId = "", mQuantity = "1";

        public ProductDetailsTask(String id) {
            mProductId = id;
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

                    JSONArray jsonResultSet = jsonObject.getJSONArray("result_set");

                    mBasePath = jsonObject.getString("imageSource");

                    if (jsonResultSet.length() > 0) {

                        for (int i = 0; i < jsonResultSet.length(); i++) {

                            JSONObject jsonResult = jsonResultSet.getJSONObject(i);

                            modifierProduct = new ModifierProduct();

                            modifierProduct.setmProductId(jsonResult.getString("product_id"));
                            modifierProduct.setmProductName(jsonResult.getString("product_alias"));
                            modifierProduct.setmProductType(jsonResult.getString("product_type"));
                            modifierProduct.setmProductSku(jsonResult.getString("product_sku"));
                            modifierProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            modifierProduct.setmProductImage(jsonResult.getString("product_thumbnail"));
                            modifierProduct.setmProductStatus(jsonResult.getString("product_status"));
                            modifierProduct.setmProductPrice(jsonResult.getString("product_price"));

                            JSONArray jsonModifiersArray = jsonResult.getJSONArray("modifiers");

                            List<ModifierHeading> modifierHeadingList = new ArrayList<>();

                            if (jsonModifiersArray.length() > 0) {

                                for (int m = 0; m < jsonModifiersArray.length(); m++) {

                                    JSONObject jsonModifier = jsonModifiersArray.getJSONObject(m);

                                    ModifierHeading modifierHeading = new ModifierHeading();

                                    modifierHeading.setmModifierHeading(jsonModifier.getString("pro_modifier_name"));
                                    modifierHeading.setmModifierHeadingId(jsonModifier.getString("pro_modifier_id"));
                                    modifierHeading.setmModifierMinSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_min_select")));
                                    modifierHeading.setmModifierMaxSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_max_select")));
                                    modifierHeading.setmMaxSelected(0);

                                    JSONArray modifierValue = jsonModifier.getJSONArray("modifiers_values");

                                    List<ModifiersValue> modifiersValueList = new ArrayList<>();

                                    if (modifierValue.length() > 0) {

                                        for (int v = 0; v < modifierValue.length(); v++) {

                                            JSONObject jsonModifiervalue = modifierValue.getJSONObject(v);

                                            ModifiersValue value = new ModifiersValue();

                                            value.setmModifierId(jsonModifiervalue.getString("pro_modifier_value_id"));
                                            value.setmModifierValuePrice(jsonModifiervalue.getString("pro_modifier_value_price"));
                                            value.setmModifierName(jsonModifiervalue.getString("pro_modifier_value_name"));
                                            value.setmModifierDefault(jsonModifiervalue.getString("pro_modifier_value_is_default"));

                                            if (jsonModifiervalue.getString("pro_modifier_value_is_default").
                                                    equalsIgnoreCase("yes")) {

                                                value.setChekced(true);
                                                modifierHeading.setmMaxSelected(1);

                                            } else {

                                                value.setChekced(false);
                                                modifierHeading.setmMaxSelected(0);

                                            }

                                            modifiersValueList.add(value);

                                        }

                                    }

                                    modifierHeading.setModifiersList(modifiersValueList);
                                    modifierHeadingList.add(modifierHeading);



                                }

                            } else {

                            }
                            modifierProduct.setModifiersList(modifierHeadingList);
                        }
                    }

                    modifierproductDetailsDialog(mContext, mProductId, mQuantity);

                } else {

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }


        }
    }
    class ProductDetailsSimpleTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<ModifiersValue> modifiersValueList;
        private String mProductId = "", mQuantity = "1";

        public ProductDetailsSimpleTask(String id) {
            mProductId = id;
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

                    JSONArray jsonResultSet = jsonObject.getJSONArray("result_set");

                    String basePath = "";
                    String galleryBasePath = "";
                    String tagimagepath = "";
                    try {
                        try {
                            if (!jsonObject.isNull("imageSource")) {
                                mBasePath = jsonObject.getString("imageSource");
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        basePath = jsonObject.getJSONObject("common").optString("product_image_source");
                        galleryBasePath = jsonObject.getJSONObject("common").optString("product_gallery_image_source");
                        tagimagepath = jsonObject.getJSONObject("common").optString("tag_image_source");
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                    if (jsonResultSet.length() > 0) {

                        for (int i = 0; i < jsonResultSet.length(); i++) {

                            JSONObject jsonResult = jsonResultSet.getJSONObject(i);

//                            searchProduct = new SearchProduct();
                             products = new Products();

                            products.setmProductId(jsonResult.getString("product_id"));
                            products.setMproduct_alias(jsonResult.getString("product_alias"));
                            products.setmProductName(jsonResult.getString("product_name"));
                            products.setmProductDescription(jsonResult.getString("product_long_description"));
                            products.setmProductShortDescription(jsonResult.getString("product_short_description"));
                            products.setmProductPrice(jsonResult.getString("product_price"));
                            products.setmProductSku(jsonResult.getString("product_sku"));
                            products.setmProductSlug(jsonResult.optString("product_slug"));
                            products.setmProductStatus(jsonResult.getString("product_status"));
                            products.setmProductType(jsonResult.getString("product_type"));
                            products.setmProduct_stock(jsonResult.getString("product_stock"));
                            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                                products.setFav_product_primary_id(jsonResult.optString("fav_product_primary_id"));
                            } else {
                                products.setFav_product_primary_id("");
                            }
                            products.setmProductPrimaryId(jsonResult.getString("product_primary_id"));
                            String subImageUrl = jsonResult.optString("product_thumbnail");
                            if (subImageUrl != null && subImageUrl.length() > 0) {
                                products.setmProductThumpImage(basePath + "/" + jsonResult.optString("product_thumbnail"));
                            }
                            products.setmAvailabilityId(jsonResult.getString("product_availability_id"));
                            products.setmVoucherExpiryDate(jsonResult.optString("product_voucher_expiry_date"));
                            products.setmVoucherPoints(jsonResult.optString("product_voucher_points"));
                            products.setmProductVoucher(jsonResult.optString("product_voucher"));
                            products.setmProductMinQty(jsonResult.optString("product_minimum_quantity"));
                            products.setmProductVoucherIncreaseQty(jsonResult.optString("product_voucher_increase_qty"));

                            JSONArray tagimagearray = jsonResult.getJSONArray("product_tag");
                            ArrayList<TagImageModel> TagImageList = new ArrayList<>();
                            for (int m = 0; m < tagimagearray.length(); m++) {
                                JSONObject tagobject = tagimagearray.getJSONObject(m);
                                TagImageModel tagImageModel = new TagImageModel();
                                if (!tagobject.getString("pro_tag_image").isEmpty()) {
                                    tagImageModel.setPro_tag_image(tagimagepath + tagobject.getString("pro_tag_image"));
                                    TagImageList.add(tagImageModel);
                                    products.setTagImageList(TagImageList);
                                    Log.e("TAG", "Tag_image_test::" + tagimagepath + tagobject.getString("pro_tag_image"));
                                }
                            }

                            String galleryImageUrl = "";

                            JSONArray image_gallery = jsonResult.getJSONArray("image_gallery");
                            for (int l = 0; l < image_gallery.length(); l++) {
                                JSONObject jsonObject1 = image_gallery.getJSONObject(l);
                                galleryImageUrl = jsonObject1.getString("pro_gallery_image");

                            }
                            //products.setmSubCategoryGalleryImage(galleryBasePath + "/" + jsonResult.optString("product_thumbnail"));

                            if (galleryImageUrl != null && galleryImageUrl.length() > 0) {
                                products.setmSubCategoryGalleryImage(galleryBasePath + "/" + galleryImageUrl);
                            }

                        }
                    }

                    simpleProductDetailsDialog(mContext, products);

                } else {

                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }


        }
    }

    private class AddCartTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private Map<String, String> cartparams;
        private String mQuantity;

        public AddCartTask(Map<String, String> cartparams, String quantity) {
            this.cartparams = cartparams;
            this.mQuantity = quantity;
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

            System.out.print("Add to cart modifier");

            String response = WebserviceAssessor.postRequest(mContext, params[0], cartparams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {


//                    if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
//
//                        try {
//                            for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
//
//                                for (int i1 = 0; i1 < setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().size(); i1++) {
//                                    if (setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalpPrize() > 0.00) {
//                                        TotalPriceTable totalPriceTable = new TotalPriceTable(setMenuProduct.getSetMenuTitleList().get(i).getmTitleMenuId(),setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getmModifierId(), String.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalpPrize()));
//                                        MyApplication.get().getDB().totalPriceDao().insertData(totalPriceTable);
//                                    }
//                                }
//
//
//                            }
//                        } catch (Exception e) {
//                            e.printStackTrace();
//                        }
//                    }

                    if (Integer.parseInt(mQuantity) > 0) {

                        int value = 0;
                        try {

                            value = Integer.valueOf(Integer.valueOf(mQuantity)) + databaseHandler.getQuantity(mProductId);
                             databaseHandler.updateQty(mProductId, String.valueOf(value));
                            onrefreshdtata();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }

                    } else {
                        databaseHandler.insertProductData(mProductId, mQuantity);
                        onrefreshdtata();

                    }


                    JSONObject jsonContent = jsonObject.getJSONObject("contents");

                    JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                            jsonCartDetails.getString("cart_total_items"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());


                    // ProductListActivity.isInvalidated = true;
//                    ((ProductListActivity) mContext).invalidateOptionsMenu();

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();


                    if (dialog != null) {
                        dialog.dismiss();
                    }

                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();

            } finally {
                progressDialog.dismiss();
                if (dialog != null) {
                    dialog.dismiss();
                }
                finish();
            }

        }
    }


    private class SetMenuProductDetailsTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<SetMenuTitle> setMenuTitleList;
        private List<SetMenuModifier> setMenuModifierList;
        private List<ModifierHeading> modifierHeadingList;
        private List<ModifiersValue> modifiersValueList;
        private SetMenuTitle setMenuTitle;
        private SetMenuModifier setMenuModifier;
        private ModifierHeading modifierHeading;
        private ModifiersValue modifiersValue;

        private String mProductId = "", mQuantity = "1";
        private String mProductprimaryid = "";

        public SetMenuProductDetailsTask(String id, String Productprimaryid) {
            mProductId = id;
            mProductprimaryid = Productprimaryid;
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
            double price = 0.0;

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    mBasePath = jsonCommon.optString("image_source");
                    galleryBasePath = jsonCommon.optString("product_gallery_image_source");
                    String tagimagepath = jsonCommon.optString("tag_image_source");

                    JSONArray jsonResultArray = jsonObject.getJSONArray("result_set");

                    if (jsonResultArray.length() > 0) {
                        for (int r = 0; r < jsonResultArray.length(); r++) {

                            JSONObject jsonResult = jsonResultArray.getJSONObject(r);

                            setMenuProduct = new SetMenuProduct();

                            setMenuProduct.setmProductId(jsonResult.getString("product_id"));
                            setMenuProduct.setmProductName(jsonResult.getString("product_name"));
                            setMenuProduct.setmProductAliasName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmproduct_apply_minmax_select(jsonResult.getString("product_apply_minmax_select"));

                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            setMenuProduct.setmProductImage(mBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            setMenuProduct.setmProductgalleryImage(galleryBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            setMenuProduct.setmProductStatus(jsonResult.getString("product_status"));
                            setMenuProduct.setmProductPrice(jsonResult.getString("product_price"));
                            setMenuProduct.setmApplyMinMaxSelect(Integer.parseInt(jsonResult.getString("product_apply_minmax_select")));
                            setMenuProduct.setSubcatgory_name(jsonResult.optString("subcatgory_name"));
                            if (!jsonResult.isNull("product_voucher_expiry_date")) {
                                setMenuProduct.setmVoucherExpiryDate(jsonResult.getString("product_voucher_expiry_date"));
                            }
                            if (!jsonResult.isNull("product_voucher_points")) {
                                setMenuProduct.setmVoucherPoints(jsonResult.getString("product_voucher_points"));
                            }
                            if (!jsonResult.isNull("product_voucher_increase_qty")) {
                                setMenuProduct.setmProductVoucherIncreaseQty(jsonResult.getString("product_voucher_increase_qty"));
                            }
                            if (!jsonResult.isNull("product_short_description")) {
                                setMenuProduct.setmProductShortDescription(jsonResult.optString("product_short_description"));
                            }
                            if (!jsonResult.isNull("product_voucher")) {
                                setMenuProduct.setmProductVoucher(jsonResult.getString("product_voucher"));
                            }
                            if (!jsonResult.isNull("product_minimum_quantity")) {
                                setMenuProduct.setmProductMinQty(jsonResult.optString("product_minimum_quantity"));
                            }

                            JSONArray tagimagearray = jsonResult.getJSONArray("product_tag");
                            ArrayList<TagImageModel> TagImageList = new ArrayList<>();
                            for (int m = 0; m < tagimagearray.length(); m++) {
                                JSONObject tagobject = tagimagearray.getJSONObject(m);
                                TagImageModel tagImageModel = new TagImageModel();
                                if (!tagobject.getString("pro_tag_image").isEmpty()) {
                                    tagImageModel.setPro_tag_image(tagimagepath + tagobject.getString("pro_tag_image"));
                                    TagImageList.add(tagImageModel);
                                    setMenuProduct.setTagImageList(TagImageList);
                                    Log.e("TAG", "Tag_image_test::" + tagimagepath + tagobject.getString("pro_tag_image"));
                                }
                            }


                            try {

                                mSetMenuBasePrice = Double.valueOf(jsonResult.getString("product_price"));
                            } catch (Exception e) {
                                mSetMenuBasePrice = 0.0;
                            }

                            mSetMenuPrice = mSetMenuBasePrice;

                            quantityCost = mSetMenuBasePrice;

                            String largeImage = "";
                            JSONArray image_gallery = jsonResult.getJSONArray("image_gallery");
                            for (int i = 0; i < image_gallery.length(); i++) {
                                JSONObject jsonObject1 = image_gallery.getJSONObject(i);
                                largeImage = jsonObject1.getString("pro_gallery_image");
                                if (largeImage != null && largeImage.length() > 0) {
                                    largeImage = galleryBasePath + "/" + largeImage;
                                    setMenuProduct.setmProductLargeImage(largeImage);
                                }
                            }
                            //setMenuProduct.setmProductLargeImage(galleryBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            JSONArray setmenuArray = jsonResult.getJSONArray("set_menu_component");

                            setMenuTitleList = new ArrayList<>();

                            if (setmenuArray.length() > 0) {

                                for (int t = 0; t < setmenuArray.length(); t++) {
                                    JSONObject jsonSetmenu = setmenuArray.getJSONObject(t);

                                    setMenuTitle = new SetMenuTitle();

                                    setMenuTitle.setmTitleMenuId(jsonSetmenu.optString("menu_component_id"));
                                    setMenuTitle.setmTitleMenuName(jsonSetmenu.optString("menu_component_name"));
                                    setMenuTitle.setmultipleselection_apply(jsonSetmenu.optString("menu_component_multipleselection_apply"));
                                    setMenuTitle.setmenu_component_modifier_apply(jsonSetmenu.optString("menu_component_modifier_apply"));
                                    GlobalValues.MULTIPLESLECTIONAPPLY = jsonSetmenu.optString("menu_component_multipleselection_apply");
                                    GlobalValues.MODIFIERAPPLY = jsonSetmenu.optString("menu_component_modifier_apply");
                                    setMenuTitle.setmAppliedPrice(jsonSetmenu.optString("menu_component_apply_price"));
                                    setMenuTitle.setmMinSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_min_select")));
                                    setMenuTitle.setmMaxSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_max_select")));
                                    setMenuTitle.setmDefaultSelectId(jsonSetmenu.optString("menu_component_default_select"));

                                    JSONArray jsonProductArray = jsonSetmenu.getJSONArray("product_details");

                                    setMenuModifierList = new ArrayList<>();

                                    if (jsonProductArray.length() > 0) {

                                        for (int p = 0; p < jsonProductArray.length(); p++) {

                                            JSONArray jsonArray = jsonProductArray.getJSONArray(p);

                                            if (jsonArray.length() > 0) {
                                                for (int p1 = 0; p1 < jsonArray.length(); p1++) {

                                                    JSONObject object = jsonArray.getJSONObject(p1);

                                                    setMenuModifier = new SetMenuModifier();

                                                    setMenuModifier.setmModifierId(object.optString("product_id"));
                                                    setMenuModifier.setmModifierName(object.optString("product_name"));
                                                    setMenuModifier.setsub_modifier_apply(jsonSetmenu.optString("menu_component_modifier_apply"));
                                                    setMenuModifier.setsub_multipleselection_apply(jsonSetmenu.optString("menu_component_multipleselection_apply"));
                                                    setMenuModifier.setApplyPrice(jsonSetmenu.optString("menu_component_apply_price"));
                                                    setMenuModifier.setmModifierAliasName(object.optString("product_alias"));
                                                    setMenuModifier.setmMaxSelect(object.optString("product_bagel_max_select"));
                                                    setMenuModifier.setmMinSelect(object.optString("product_bagel_min_select"));
                                                    setMenuModifier.setmModifierPrice(object.optString("product_price"));
                                                    setMenuModifier.setmModifierSku(object.optString("product_sku"));
                                                    setMenuModifier.setmModifierSlug(object.optString("product_slug"));


                                                    modifierHeadingList = new ArrayList<>();

                                                    Object modifierObject = object.get("modifiers");

                                                    if (modifierObject instanceof JSONArray) {


                                                        JSONArray jsonModifiersArray = object.getJSONArray("modifiers");


                                                        if (jsonModifiersArray != null && jsonModifiersArray.length() > 0) {

                                                            setMenuModifier.setHasModifier(true);

                                                            for (int m = 0; m < jsonModifiersArray.length(); m++) {

                                                                JSONObject jsonModifier = jsonModifiersArray.getJSONObject(m);

                                                                modifierHeading = new ModifierHeading();

                                                                modifierHeading.setmModifierHeading(jsonModifier.getString("pro_modifier_name"));
                                                                modifierHeading.setmModifierHeadingId(jsonModifier.getString("pro_modifier_id"));
                                                                modifierHeading.setmModifierMinSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_min_select")));
                                                                modifierHeading.setmModifierMaxSelect(Integer.parseInt(jsonModifier.getString("pro_modifier_max_select")));
                                                                modifierHeading.setmMaxSelected(0);

                                                                JSONArray modifierValueArray = jsonModifier.getJSONArray("modifiers_values");

                                                                modifiersValueList = new ArrayList<>();

                                                                if (modifierValueArray.length() > 0) {

                                                                    for (int v = 0; v < modifierValueArray.length(); v++) {

                                                                        JSONObject jsonModifiervalue = modifierValueArray.getJSONObject(v);

                                                                        modifiersValue = new ModifiersValue();

                                                                        modifiersValue.setmModifierId(jsonModifiervalue.getString("pro_modifier_value_id"));
                                                                        modifiersValue.setmModifierValuePrice(jsonModifiervalue.getString("pro_modifier_value_price"));
                                                                        modifiersValue.setmModifierName(jsonModifiervalue.getString("pro_modifier_value_name"));
                                                                        modifiersValue.setmModifierDefault(jsonModifiervalue.getString("pro_modifier_value_is_default"));

                                                                        if (jsonModifiervalue.getString("pro_modifier_value_is_default").
                                                                                equalsIgnoreCase("yes")) {

                                                                            modifiersValue.setChekced(true);
                                                                            modifierHeading.setmMaxSelected(1);

                                                                            /*adding modifier value prices with set menu base price*/

                                                                            try {
                                                                                price = Double.valueOf(jsonModifiervalue.optString("pro_modifier_value_price"));
                                                                            } catch (NumberFormatException e) {
                                                                                price = 0.0;
                                                                            } catch (Exception e1) {
                                                                                price = 0.0;
                                                                            }

                                                                            mSetMenuPrice = mSetMenuBasePrice + price;
                                                                            quantityCost = mSetMenuPrice;


                                                                        } else {

                                                                            modifiersValue.setChekced(false);
                                                                            modifierHeading.setmMaxSelected(0);

                                                                        }

                                                                        modifiersValueList.add(modifiersValue);

                                                                    }

                                                                }

                                                                modifierHeading.setModifiersList(modifiersValueList);
                                                                modifierHeadingList.add(modifierHeading);

                                                             }

                                                        } else {
                                                            setMenuModifier.setHasModifier(false);
                                                        }

                                                    } else {
                                                         setMenuModifier.setHasModifier(false);
                                                    }


                                                    /*add set menu price for default selected products if products does
                                                    not have modifiers*/

                                                    if (setMenuTitle.getmDefaultSelectId().equals(object.optString("product_id"))) {
                                                        setMenuModifier.setChecked(true);

                                                        if (modifierHeadingList.size() <= 0) {
                                                            try {
                                                                price = Double.valueOf(object.optString("product_price"));
                                                            } catch (NumberFormatException e) {
                                                                price = 0.0;
                                                            } catch (Exception e1) {
                                                                price = 0.0;
                                                            }

                                                            mSetMenuPrice = mSetMenuPrice + price;
                                                            quantityCost = mSetMenuPrice;
                                                        }

                                                    } else {
                                                        setMenuModifier.setChecked(false);
                                                    }


                                                    setMenuModifier.setModifierHeadingList(modifierHeadingList);
                                                    setMenuModifierList.add(setMenuModifier);

                                                }
                                            }

                                        }

                                    } else {

                                    }

                                    setMenuTitle.setSetMenuModifierList(setMenuModifierList);
                                    setMenuTitleList.add(setMenuTitle);

                                }


                            } else {

                            }

                            setMenuProduct.setSetMenuTitleList(setMenuTitleList);

                        }
                        setMenuproductDetailsDialog(mContext, mProductId, mProductprimaryid, mQuantity);

                    } else {


                    }


                } else {

                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();

            }


        }
    }

    private void addFavouriteMethod(String productId) {


        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";

        } else {
            try {
                TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                mReferenceId = GlobalValues.DEVICE_ID;

            } catch (SecurityException e) {
                GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                mReferenceId = GlobalValues.DEVICE_ID;

            } finally {
                {
                    mCustomerId = "";
                }
            }
        }

        if (text_linefav.getText().toString().equalsIgnoreCase("Remove from favourite")) {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "No");
            param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);



            new FavouritesAddTask(param).execute(url);

            StatusFav = "0";

        } else {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "Yes");
            param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);



            new FavouritesAddTask(param).execute(url);

            StatusFav = "1";

        }

    }


    private class FavouritesAddTask extends AsyncTask<String, Void, String> {

        private Map<String, String> resetParams;
        private ProgressDialog progressDialog;

        public FavouritesAddTask(Map<String, String> param) {
            resetParams = param;
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


            String response = WebserviceAssessor.postRequest(mContext, params[0], resetParams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            try {


                JSONObject jsonObject = new JSONObject(s);


                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    if (isCustomerLoggedIn()) {
                        if (StatusFav.equalsIgnoreCase("1")) {
                            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                            text_linefav.setText("Remove from favourite");
                            Toast.makeText(mContext, "Added successfully", Toast.LENGTH_SHORT).show();


                        } else {
                            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                            text_linefav.setText("Add to favourite");

                            Toast.makeText(mContext, "Removed successfully", Toast.LENGTH_SHORT).show();
                        }
                    }
                } else {
                    text_linefav.setText("Add to favourite");
                    favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                    StatusFav = "1";
                }
                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

            }


        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE", "1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE", "1");
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

    private void openFiveMenuActivity(int position) {

        if (!isCustomerLoggedIn()) {

            new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                @Override
                public void selectedAction(String action) {

                    if (action.equalsIgnoreCase("Ok")) {
                        Intent intent = new Intent(mContext, LoginActivity.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        mContext.startActivity(intent);
//                        mContext.finish();
                    }
                }
            });

        } else {
            Intent intent = new Intent(mContext, FiveMenuActivityNew.class);
            intent.putExtra("position", position);
            intent.putExtra(GlobalValues.FROM_KEY, GlobalValues.NOT_FROM_CHECKOUT);
            mContext.startActivity(intent);
        }
    }

    public void simpleProductGetData(){
//        String url = GlobalUrl.PRODUCT_DETAILS_URL + "?appId=" + GlobalValues.APP_ID +
//                "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
//                "&outletId=" + GlobalValues.CURRENT_OUTLET_ID +
//                "&productId=" + searchProduct.getmProductId() +
//                "&productType=" + searchProduct.getmProductType();

        mProductId = searchProduct.getmProductId();

        String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                "&product_id=" + mProductId;

//        new SetMenuProductDetailsTask(mProductId, searchProduct.getmProduct_primary_id()).execute(url);

        new ProductDetailsSimpleTask(mProductId).execute(url);
    }
}