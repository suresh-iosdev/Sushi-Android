package com.app.sushi.tei.adapter.Products;

import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.provider.Settings;
import com.google.android.material.tabs.TabLayout;
import androidx.core.content.ContextCompat;
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
import android.view.ViewGroup;
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
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
//import com.app.sushi.tei.activity.CartActivity;
import com.app.sushi.tei.activity.FiveMenuActivityNew;
import com.app.sushi.tei.activity.LoginActivity;
import com.app.sushi.tei.activity.MemberBenefitActivity;
import com.app.sushi.tei.activity.OrderSummaryActivity;
import com.app.sushi.tei.activity.SearchActivity;
import com.app.sushi.tei.activity.SubCategoryActivity;
import com.app.sushi.tei.adapter.AddOnsRecyclerAdapter;
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
import static com.app.sushi.tei.activity.SubCategoryActivity.heartblink_imageview;
import static com.app.sushi.tei.activity.SubCategoryActivity.mModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mModifierQuantity;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSearchProuductprise;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuBasePrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuQuantity;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetmenuoverallprices;
import static com.app.sushi.tei.activity.SubCategoryActivity.mquanititycost_src;
import static com.app.sushi.tei.activity.SubCategoryActivity.quantityCost;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.value;


public class ProductListRecyclerAdapter extends RecyclerView.Adapter<ProductListRecyclerAdapter.ProductHolder> {

    private Context mContext;
    public List<Products> productsList = new ArrayList<>();
    private ModifierProduct modifierProduct;
    private SetMenuProduct setMenuProduct;
    private String mBasePath = "", mBase_path = "";
    private String galleryBasePath = "";
    private String mCustomerId = "", mReferenceId = "", mProductId = "", mProductPrimaryId = "null", mProductFavPrimaryId = "null", mProductQuantity = "1";
    private String mValidationMessage = "";
    public static String mAliasProductPrimaryId = "";


    public static boolean isCorrectCombination = true;
    public Dialog dialog;
    private String iProductStock = "", subCatString;
    private DatabaseHandler databaseHandler;
    private int pos;
    private EditText notesText, notesText1;


    private SetMenuTitleRecyclerAdapterNew setMenuTitleRecyclerAdapternew;

    Double total_unitprices = 0.0;
    public LinearLayout favouriteLayout;
    public TextView favouriteText;

    private String StatusFav = "0";
    private String favourite = "";
    LinearLayout lly_forGift;
    private EditText edtName, edtEmail, edtPhoneNumber, edtMessage;
    private TextView txtSubmit;
    private TextView txt_info;
    String caseType = "";
    int counting;
    private Double voucherIncreaseQty, productMinQty;
    private JSONArray setmenuArray;

    private List<Products> showingProductList = new ArrayList<>();
    public List<Products> allProductList = new ArrayList<>();

    public int page = 0, limit = 2;

//TOdo surya

    public ProductListRecyclerAdapter(Context mContext, List<Products> productsList, String subCatString) {
        this.mContext = mContext;
        this.productsList = productsList;
//        this.allProductList = productsList;
        this.subCatString = subCatString;
        databaseHandler = DatabaseHandler.getInstance(mContext);

//        try {
//            for (int i = 0; i < 10; i++) {
//                if (allProductList.size() > i) {
//                    this.productsList.add(allProductList.get(i));
//                }
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {


        View view = null;

        view = LayoutInflater.from(mContext).inflate(R.layout.layout_product_item, parent, false);

        ProductHolder productHolder = new ProductHolder(view);

        return productHolder;
    }

    public void onRefresh() {
        databaseHandler = DatabaseHandler.getInstance(mContext);
//        this.arrcart = arrcart;
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(final ProductHolder holder, final int position) {

        try {
            if (databaseHandler.getAllTotalData(productsList.get(position).getmProductId()) != null) {
                Cart cart = databaseHandler.getAllTotalData(productsList.get(position).getmProductId());
                // holder.layoutMaxCount.setVisibility(View.VISIBLE);



                //  holder.txtCurrentCartQuantity.setText("X" + cart.getmProductQty());
//                if (holder.tv_price.getText().length() > 0) {
////                    int value = 0;
////                    try {
////                        value = Integer.valueOf(holder.tv_price.getText().toString().replace("X", "")) + Integer.valueOf(cart.getCart_item_qty());
////                        holder.tv_price.setText("X" + String.valueOf(value));
////                    } catch (NumberFormatException e) {
////                        e.printStackTrace();
////                    }
//                } else {
//                    holder.tv_price.setText("X" + cart.getCart_item_qty());
//                }
            } else {
                // holder.layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (productsList.get(position).getmProductType().equalsIgnoreCase("5")) {
            holder.parentLayout.setVisibility(View.GONE);
            holder.rly_voucherLayout.setVisibility(View.VISIBLE);

            holder.txt_avaCoupons.setText(productsList.get(position).getmProduct_stock());
            holder.txt_drinksQty.setText(productsList.get(position).getmProductSlug());
            //holder.txt_expAva.setText("Expiry " + productsList.get(position).getmVoucherExpiryDate() + " | " + productsList.get(position).getmProduct_stock() + " Available");
            SimpleDateFormat placedDateFormatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = null;
            SimpleDateFormat dateoutput = null;
            try {
                date = placedDateFormatter.parse(productsList.get(position).getmVoucherExpiryDate());
                dateoutput = new SimpleDateFormat("dd-MMM-yyyy");

                holder.txt_expAva.setText(dateoutput.format(date));
            } catch (ParseException e) {
                e.printStackTrace();
            }
            holder.txt_description.setText(productsList.get(position).getmProductShortDescription());
            holder.txt_voucherPrice.setText(productsList.get(position).getmProductPrice());

            if (productsList.get(position).getmProduct_stock().equals("0")) {
                holder.txt_OrderNow.setText("Out of stock");
                holder.txt_OrderNow.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                holder.txt_OrderNow.setBackground(mContext.getResources().getDrawable(R.drawable.outstock_shape));
            } else {
                holder.txt_OrderNow.setText("Add To Cart");
                holder.txt_OrderNow.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                holder.txt_OrderNow.setBackground(mContext.getResources().getDrawable(R.drawable.checkout_paynow_background));

            }

            holder.txt_OrderNow.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    holder.txtAddCart.performClick();
                }
            });

        } else {
            holder.parentLayout.setVisibility(View.VISIBLE);
            holder.rly_voucherLayout.setVisibility(View.GONE);
        }

        String imageurl = productsList.get(position).getmProductThumpImage();

        if (imageurl != null && imageurl.length() > 0) {


            Picasso.with(mContext).load(imageurl).
                    error(R.drawable.place_holder_sushi_tei).into(holder.subImage);

            if (productsList.get(position).getmProductType().equalsIgnoreCase("5")) {
                Picasso.with(mContext).load(imageurl).
                        error(R.drawable.voucher_cardview).into(holder.img_voucher);
            }
        } else {

            holder.subImage.setVisibility(View.GONE);
            LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            );

            holder.lly_subImg.setVisibility(View.GONE);

           /* holder.parentLayout.setWeightSum(1f);
            holder.productNameLayout.setLayoutParams(param);*/
        }

        try {
             ArrayList<TagImageModel> TagimageList = new ArrayList<>();
            for (int i = 0; i < productsList.get(position).getTagImageList().size(); i++) {
                TagImageModel model = productsList.get(position).getTagImageList().get(i);
                TagimageList.add(model);
             }
            LinearLayoutManager lrt = new LinearLayoutManager(mContext);
            holder.tagimageRecyclerview.setLayoutManager(lrt);
            TagImageAdapter adapter = new TagImageAdapter(mContext, TagimageList);
            holder.tagimageRecyclerview.setAdapter(adapter);

        } catch (Exception e) {
            e.printStackTrace();
        }

        holder.txtProductName.setText(productsList.get(position).getMproduct_alias().replace("\\", ""));
        holder.txtProductDesc.setText(productsList.get(position).getmProductDescription());
        holder.txtPrice.setText(productsList.get(position).getmProductPrice());

        if (productsList.get(position).getFav_product_primary_id().equalsIgnoreCase("") || productsList.get(position).getFav_product_primary_id().equalsIgnoreCase("null")) {
            holder.img_heart.setImageResource(R.drawable.heart_unselect);
            productsList.get(position).setmFavouriteHeartclickable("ADD");
        } else {
            holder.img_heart.setImageResource(R.drawable.heart_select);
            productsList.get(position).setmFavouriteHeartclickable("REMOVE");
        }

        holder.img_heart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                                ((Activity) mContext).finish();
                            }

                        }
                    });
                } else {
                    addFavouriteMethod2(productsList.get(position).getmProductPrimaryId(), holder, position);
                }
            }
        });
//Todo surya
        try {
            iProductStock = productsList.get(position).getmProduct_stock();

        } catch (Exception e) {
            e.printStackTrace();

            iProductStock = "0";

        }


        try {


            if (productsList.get(position).getmProduct_stock().equals("0")) {
                holder.txtAddCart.setText("Out of stock");
                holder.txtAddCart.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                holder.txtAddCart.setBackground(mContext.getResources().getDrawable(R.drawable.outstock_shape));
            } else {


                holder.txtAddCart.setText("Add To Cart");
                holder.txtAddCart.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                holder.txtAddCart.setBackground(mContext.getResources().getDrawable(R.drawable.checkout_paynow_background_round));

            } /*
            if (productsList.get(position).getmProduct_stock() != null && !productsList.get(position).getmProduct_stock().equals("null"))

            else {

                holder.txtAddCart.setText("Out of stock");
                holder.txtAddCart.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                holder.txtAddCart.setBackground(mContext.getResources().getDrawable(R.drawable.outstock_shape));



            }*/

        } catch (Exception e) {
            e.printStackTrace();
        }
     /*   GradientDrawable bgShape = (GradientDrawable) holder.layoutQuantityParent.getBackground();
        bgShape.setColor(mContext.getResources().getColor(R.color.colorBottomShape));*/

        if (productsList.get(position).getmAvailabilityId().equals(GlobalValues.BENTO_ID)) {

            // holder.txtQuantity.setText(productsList.get(position).getmMinQuantity());

        } else {
            //  holder.txtQuantity.setText("1");
        }

        holder.txtAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               /* if (!isCustomerLoggedIn()) {

                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {

                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                                ((Activity) mContext).finish();
                            }

                        }
                    });
                } else {*/

                if (productsList.get(position).getmProduct_stock().equals("0")) {

                    Toast.makeText(mContext, "Out of stock", Toast.LENGTH_SHORT).show();

                } else {


                    pos = position;


                    if (Integer.parseInt(productsList.get(position).getmProductType()) == 1) {

                        if (productsList.get(position).getmAvailabilityId().equals(GlobalValues.BENTO_ID)) {

                            bentoProductDialog(productsList.get(position));

                        } else {
                            mProductId = productsList.get(position).getmProductId();

                            mModifierPrice = 0.0;
                            quantityCost = 0.0;
                            dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            //TODO:Identification
                            dialog.setContentView(R.layout.dialog_add_product);
                            dialog.show();

                            Toolbar toolbar = dialog.findViewById(R.id.toolBar);
                            LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
                            ImageView user_imageview = toolbar.findViewById(R.id.user_imageview);
                            RelativeLayout layoutSearch = toolbar.findViewById(R.id.layoutSearch);
                            RelativeLayout layoutCart = toolbar.findViewById(R.id.layoutCart);
                            TextView txtCartCount = (TextView) toolbar.findViewById(R.id.txtCartCount);
                            TextView category_name=dialog.findViewById(R.id.category_name);

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

                            category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));


                            favouriteText = dialog.findViewById(R.id.favouriteText);
                            favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
                            notesText = dialog.findViewById(R.id.notesText);
                            RecyclerView tagrecy = dialog.findViewById(R.id.tagrecy);
                            TextView textHead = dialog.findViewById(R.id.textHead);
                            ImageView imageViews = (ImageView) dialog.findViewById(R.id.imageViews);
                            ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imageDegreement);
                            ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imageIngreement);
                            ImageView cancel_Image = dialog.findViewById(R.id.cancel_Image);
                            final TextView AddtoCartBtn = dialog.findViewById(R.id.AddtoCart);
                            TextView productName = dialog.findViewById(R.id.productName);
                            LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
                            TextView productDescription = dialog.findViewById(R.id.productDescription);
                            final TextView productPriceText = dialog.findViewById(R.id.productPriceText);
                            final TextView txtQuantity = dialog.findViewById(R.id.totalcount);
                            textHead.setText(subCatString);

                            try {
                                Log.e("TAG", "ASDkklg222::" + productsList.get(position).getTagImageList().size());
                                ArrayList<TagImageModel> TagimageList = new ArrayList<>();
                                for (int i = 0; i < productsList.get(position).getTagImageList().size(); i++) {
                                    TagImageModel model = productsList.get(position).getTagImageList().get(i);
                                    TagimageList.add(model);
                                 }
                                if (TagimageList.size() > 0) {
                                    tagrecy.setVisibility(View.VISIBLE);
                                    LinearLayoutManager lrt = new LinearLayoutManager(mContext);
                                    tagrecy.setLayoutManager(lrt);
                                    TagImageAdapter adapter = new TagImageAdapter(mContext, TagimageList);
                                    tagrecy.setAdapter(adapter);
                                } else {
                                    tagrecy.setVisibility(View.GONE);
                                }


                            } catch (Exception e) {
                                e.printStackTrace();
                            }


                            String galleryImageurl = productsList.get(position).getmSubCategoryGalleryImage();

                             if (galleryImageurl != null && galleryImageurl.length() > 0) {
                                 Picasso.with(mContext).load(galleryImageurl).
                                        error(R.drawable.place_holder_sushi_tei).into(imageViews);

                            } else {
                                Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imageViews);
                            }

                            SubCategoryActivity subCategoryActivity = new SubCategoryActivity();
                            if (isCustomerLoggedIn()) {
                                if (productsList.get(position).getFav_product_primary_id().equalsIgnoreCase("null")) {

                                    favouriteText.setText("Add to favourite");
                                    favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                                    StatusFav = "1";
                                    //heartblink_imageview.setImageResource(R.drawable.heart_white);
                                    //subCategoryActivity.CheckFacourites();
                                } else {
                                    favouriteText.setText("Remove from favourite");
                                    favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                                    StatusFav = "0";
                                    //heartblink_imageview.setImageResource(R.drawable.heart_favourite);
                                    //subCategoryActivity.CheckFacourites();
                                }
                            } else {
                                favouriteText.setText("Add to favourite");
                                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                                StatusFav = "1";
                            }

                            if (!(productsList.get(position).getmFaouriteStatusLable().equalsIgnoreCase(""))) {
                                favouriteText.setText(productsList.get(position).getmFaouriteStatusLable());
                            }

                            productName.setText(productsList.get(position).getMproduct_alias().replace("\\", ""));
                            productDescription.setText(productsList.get(position).getmProductDescription());
                            productPriceText.setText(productsList.get(position).getmProductPrice());
                            final Double productCost = Double.valueOf(productsList.get(position).getmProductPrice());

                            imgBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                            });

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
                            cancel_Image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                            });

                            favouriteLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isCustomerLoggedIn()) {
                                        addFavouriteMethod(productsList.get(position).getmProductPrimaryId(), holder, position);
                                    } else {
                                        new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                                            @Override
                                            public void selectedAction(String action) {
                                                if (action.equalsIgnoreCase("Ok")) {
                                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    mContext.startActivity(intent);
                                                    ((Activity) mContext).finish();
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                            imgIncreement.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int count = Integer.parseInt(txtQuantity.getText().toString());

                                    count++;

                                    txtQuantity.setText(String.valueOf(count));


                                    productPriceText.setText(String.valueOf(String.format("%.2f", productCost * Double.valueOf(count))));
                                }
                            });


                            imgDecreement.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    int count = Integer.parseInt(txtQuantity.getText().toString());

                                    if (count > 1) {
                                        count--;
                                        txtQuantity.setText(count + "");
                                    }
                                    productPriceText.setText(String.valueOf(String.format("%.2f", productCost * Double.valueOf(count))));

                                }
                            });

                            lly_addToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AddtoCartBtn.performClick();
                                }
                            });

                            AddtoCartBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                    try {
                                        if (!isCustomerLoggedIn()) {

                                            new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                                                @Override
                                                public void selectedAction(String action) {

                                                    if (action.equalsIgnoreCase("Ok")) {
                                                        Intent intent = new Intent(mContext, LoginActivity.class);
                                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                        mContext.startActivity(intent);
                                                        ((Activity) mContext).finish();
                                                    }

                                                }
                                            });
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

                                            if (Utility.networkCheck(mContext)) {
                                                String url = GlobalUrl.ADD_CART_URL;
                                                Map<String, String> params = new HashMap<String, String>();

                                                params.put("product_id", productsList.get(position).getmProductId());
                                                params.put("product_type", productsList.get(position).getmProductType());
                                                params.put("app_id", GlobalValues.APP_ID);
                                                //params.put("reference_id", Utility.getReferenceId(mContext));
                                                if (mCustomerId.isEmpty()) {
                                                    params.put("reference_id", mReferenceId);
                                                } else {
                                                    params.put("reference_id", "");
                                                }
                                                params.put("customer_id", mCustomerId);
                                                params.put("availability_name", "TAKEAWAY");
                                                params.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                                                params.put("product_qty", txtQuantity.getText().toString());
                                                params.put("product_name", productsList.get(position).getmProductName());
                                                params.put("product_total_price", String.valueOf(productPriceText.getText().toString()));
                                                params.put("product_unit_price", productsList.get(position).getmProductPrice());
                                                params.put("price_with_modifier", "0.00");
                                                params.put("product_sku", productsList.get(position).getmProductSku());
                                                params.put("product_image", "");
                                                params.put("allow_cart", "yes");
                                                params.put("cart_source", "Mobile");

                                                String additionalNotes = notesText.getText().toString();
                                                if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                                                    params.put("product_remarks", additionalNotes);
                                                }





                                                new AddCartTask(params, txtQuantity.getText().toString()).execute(url);

                                            } else {
                                                Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    } catch (Exception exception) {
                                        exception.printStackTrace();
                                        Toast.makeText(mContext, exception.getMessage(), Toast.LENGTH_SHORT).show();
                                        try {
                                            dialog.dismiss();
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
                        }
                    }
                    else if (Integer.parseInt(productsList.get(position).getmProductType()) == 4) {

                        if (Utility.networkCheck(mContext)) {
                            String url = GlobalUrl.PRODUCT_DETAILS_URL + "?appId=" + GlobalValues.APP_ID +
                                    "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                    "&outletId=" + GlobalValues.CURRENT_OUTLET_ID +
                                    "&productId=" + productsList.get(position).getmProductId() +
                                    "&productType=" + productsList.get(position).getmProductType();

                            mProductId = productsList.get(position).getmProductId();

                            new ModifierProductDetailsTask(holder, productsList.get(position).getmProductId()).execute(url);

                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }
                    }
                    else if (Integer.parseInt(productsList.get(position).getmProductType()) == 2) {
                        SetMenuChildRecyclerAdapter.childPlusMinus = 0.00;
                        subModifierPrice = 0.00;
                        value = 0.00;

                        if (Utility.networkCheck(mContext)) {
                            String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                                    "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                    "&product_id=" + productsList.get(position).getmProductId() +
                                    "&fav_cust_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                             mProductId = productsList.get(position).getmProductId();
                            mProductPrimaryId = productsList.get(position).getmProductPrimaryId();
                            mProductFavPrimaryId = productsList.get(position).getFav_product_primary_id();
                            new SetMenuProductDetailsTask(holder, position, productsList.get(position).getmProductId()).execute(url);
                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (Integer.parseInt(productsList.get(position).getmProductType()) == 5) {
                        if (productsList.get(position).getmAvailabilityId().equals(GlobalValues.BENTO_ID)) {

                            bentoProductDialog(productsList.get(position));

                        } else {
                            mProductId = productsList.get(position).getmProductId();

                            mModifierPrice = 0.0;
                            quantityCost = 0.0;
                            dialog = new Dialog(mContext, R.style.custom_dialog_theme);
                            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                            //TODO:Identification
                            dialog.setContentView(R.layout.dialog_add_product);
                            dialog.show();

                            Toolbar toolbar = dialog.findViewById(R.id.toolBar);
                            LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
                            favouriteText = dialog.findViewById(R.id.favouriteText);
                            favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
                            favouriteLayout.setVisibility(View.GONE);
                            notesText = dialog.findViewById(R.id.notesText);
                            TextView textHead = dialog.findViewById(R.id.textHead);
                            ImageView imageViews = (ImageView) dialog.findViewById(R.id.imageViews);
                            ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imageDegreement);
                            ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imageIngreement);
                            ImageView cancel_Image = dialog.findViewById(R.id.cancel_Image);
                            final TextView AddtoCartBtn = dialog.findViewById(R.id.AddtoCart);
                            TextView productName = dialog.findViewById(R.id.productName);
                            LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
                            TextView productDescription = dialog.findViewById(R.id.productDescription);
                            RelativeLayout rly_specialNotes = dialog.findViewById(R.id.rly_specialNotes);
                            RelativeLayout rly_voucherExpAva = dialog.findViewById(R.id.rly_voucherExpAva);
                            TextView txt_voucherExpAva = dialog.findViewById(R.id.txt_voucherExpAva);
                            final TextView productPriceText = dialog.findViewById(R.id.productPriceText);
                            final TextView txtQuantity = dialog.findViewById(R.id.totalcount);
                            textHead.setText(subCatString);

                            if (productsList.get(position).getmProductMinQty().equalsIgnoreCase("") || productsList.get(position).getmProductMinQty().equalsIgnoreCase("0.00")
                                    || productsList.get(position).getmProductMinQty().equalsIgnoreCase("0") || productsList.get(position).getmProductMinQty().equalsIgnoreCase("null")) {
                                txtQuantity.setText("1");
                            } else {
                                txtQuantity.setText(productsList.get(position).getmProductMinQty());
                            }

                            TextView category_name=dialog.findViewById(R.id.category_name);
                            category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));

                            String galleryImageurl = productsList.get(position).getmSubCategoryGalleryImage();

                            rly_specialNotes.setVisibility(View.GONE);
                            //rly_voucherExpAva.setVisibility(View.VISIBLE);

                            String expiryDate = "";
                            SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd");
                            SimpleDateFormat dateForm = new SimpleDateFormat("dd-MMM-yyyy");
                            try {
                                Date date = dateFormatter.parse(productsList.get(position).getmVoucherExpiryDate());
                                expiryDate = dateForm.format(date);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }

                            String expiryLabel = "<font color='#c6c6c6'>Expiry</font>";
                            String availableLabel = "<font color='#c6c6c6'>Available</font>";
                            final String divider = "<font color='#c6c6c6'>|</font>";
                            /*txt_voucherExpAva.setText(Html.fromHtml(" " + expiryLabel + " " + expiryDate
                                    + " " + divider + " " + productsList.get(position).getmProduct_stock() + " " + availableLabel));*/
                            //txt_voucherExpAva.setText(Html.fromHtml(" " + expiryLabel + " " + expiryDate));
                             if (galleryImageurl != null && galleryImageurl.length() > 0) {
                                 Picasso.with(mContext).load(galleryImageurl).
                                        error(R.drawable.place_holder_sushi_tei).into(imageViews);

                            } else {
                                Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imageViews);
                            }

                            SubCategoryActivity subCategoryActivity = new SubCategoryActivity();
                            if (isCustomerLoggedIn()) {

                                if (productsList.get(position).getFav_product_primary_id().equalsIgnoreCase("null")) {

                                    favouriteText.setText("Add to favourite");
                                    favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                                    StatusFav = "1";
                                    //heartblink_imageview.setImageResource(R.drawable.heart_white);
                                    //subCategoryActivity.CheckFacourites();
                                } else {
                                    favouriteText.setText("Remove from favourite");
                                    favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));


                                    StatusFav = "0";
                                    //heartblink_imageview.setImageResource(R.drawable.heart_favourite);
                                    //subCategoryActivity.CheckFacourites();
                                }
                            } else {
                                favouriteText.setText("Add to favourite");
                                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                                StatusFav = "1";
                            }
                            if (!(productsList.get(position).getmFaouriteStatusLable().equalsIgnoreCase(""))) {
                                favouriteText.setText(productsList.get(position).getmFaouriteStatusLable());
                            }

                            if (productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("")
                                    || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0.00")
                                    || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0")
                                    || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("null")) {
                                voucherIncreaseQty = 1d;
                            } else {
                                voucherIncreaseQty = Double.valueOf(productsList.get(position).getmProductVoucherIncreaseQty());
                            }

                            if (productsList.get(position).getmProductMinQty().equalsIgnoreCase("") || productsList.get(position).getmProductMinQty().equalsIgnoreCase("0")
                                    || productsList.get(position).getmProductMinQty().equalsIgnoreCase("0.00") || productsList.get(position).getmProductMinQty().equalsIgnoreCase("null")) {
                                productMinQty = 1d;
                            } else {
                                productMinQty = Double.valueOf(productsList.get(position).getmProductMinQty());
                            }

                            productName.setText(productsList.get(position).getMproduct_alias().replace("\\", ""));
                            productDescription.setText(productsList.get(position).getmProductShortDescription());
                            productPriceText.setText(String.format("%.2f", Double.valueOf(productsList.get(position).getmProductPrice()) * productMinQty));
                            final Double productCost = Double.valueOf(productsList.get(position).getmProductPrice());

                            imgBack.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                            });

                            cancel_Image.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (dialog.isShowing())
                                        dialog.dismiss();
                                }
                            });

                            favouriteLayout.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if (isCustomerLoggedIn()) {
                                        addFavouriteMethod(productsList.get(position).getmProductPrimaryId(), holder, position);
                                    } else {
                                        new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                                            @Override
                                            public void selectedAction(String action) {
                                                if (action.equalsIgnoreCase("Ok")) {
                                                    Intent intent = new Intent(mContext, LoginActivity.class);
                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                    mContext.startActivity(intent);
                                                    ((Activity) mContext).finish();
                                                }
                                            }
                                        });
                                    }
                                }
                            });

                            counting = 0;

                            imgIncreement.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int count;
                                    int voucherIncreaseQty;

                                    if (productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("")
                                            || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0.00")
                                            || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0")
                                            || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("null")) {
                                        voucherIncreaseQty = 1;
                                    } else {
                                        voucherIncreaseQty = Integer.valueOf(productsList.get(position).getmProductVoucherIncreaseQty());
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
                                    txtQuantity.setText(String.valueOf(count));
                                     productPriceText.setText(String.valueOf(String.format("%.2f", (productCost * Double.valueOf(count)))));
                                }
                            });


                            imgDecreement.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    int count;
                                    int productMinQty;
                                    int voucherIncreaseQty;

                                    if (productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("")
                                            || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase("0") || productsList.get(position).getmProductVoucherIncreaseQty().equalsIgnoreCase(null)) {
                                        voucherIncreaseQty = 1;
                                    } else {
                                        voucherIncreaseQty = Integer.valueOf(productsList.get(position).getmProductVoucherIncreaseQty());
                                    }

                                    if (productsList.get(position).getmProductMinQty().equalsIgnoreCase("") || productsList.get(position).getmProductMinQty().equalsIgnoreCase("0")
                                            || productsList.get(position).getmProductMinQty().equalsIgnoreCase("0.00") || productsList.get(position).getmProductMinQty().equalsIgnoreCase("null")) {
                                        productMinQty = 1;
                                    } else {
                                        productMinQty = Integer.valueOf(productsList.get(position).getmProductMinQty());
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
                                        txtQuantity.setText(count + "");
                                    } else {
                                        Toast.makeText(mContext, "You have reached the minimum selection", Toast.LENGTH_SHORT).show();
                                    }

                                    txtQuantity.setText(count + "");
                                    productPriceText.setText(String.valueOf(String.format("%.2f", productCost * Double.valueOf(count))));
                                }
                            });

                            lly_addToCart.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    AddtoCartBtn.performClick();
                                }
                            });

                            AddtoCartBtn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                    if (!isCustomerLoggedIn()) {
//
//                                        new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
//                                            @Override
//                                            public void selectedAction(String action) {
//
//                                                if (action.equalsIgnoreCase("Ok")) {
//                                                    Intent intent = new Intent(mContext, LoginActivity.class);
//                                                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
//                                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                                                    mContext.startActivity(intent);
//                                                    ((Activity) mContext).finish();
//                                                }
//                                            }
//                                        });
//                                    } else
                                    {
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
                                                if (isLoggedIn()) {
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

                                                        params.put("product_id", productsList.get(position).getmProductId());
                                                        params.put("product_type", productsList.get(position).getmProductType());
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
                                                        params.put("product_qty", txtQuantity.getText().toString());
                                                        params.put("product_name", productsList.get(position).getmProductName());
                                                        params.put("product_total_price", String.valueOf(productPriceText.getText().toString()));
                                                        params.put("product_unit_price", productsList.get(position).getmProductPrice());
                                                        params.put("price_with_modifier", "0.00");
                                                        params.put("product_sku", productsList.get(position).getmProductSku());
                                                        params.put("product_image", productsList.get(position).getmProductThumpImage());
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

                                                        String additionalNotes = notesText.getText().toString();
                                                        if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                                                            params.put("product_remarks", additionalNotes);
                                                        }

                                                        new AddCartTask(params, txtQuantity.getText().toString()).execute(url);
                                                    } else {
                                                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                                                    }
                                                } else {
//
                                                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                                                        @Override
                                                        public void selectedAction(String action) {

                                                            if (action.equalsIgnoreCase("Ok")) {
                                                                Intent intent = new Intent(mContext, LoginActivity.class);
                                                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                                                mContext.startActivity(intent);
                                                                ((Activity) mContext).finish();
                                                            }
                                                        }
                                                    });
                                                }
                                            }
                                        });
                                    }
                                }
                            });
                        }
                    }
                }

                  /*  if (productsList.get(position).getmProduct_stock() != null && !productsList.get(position).getmProduct_stock().equals("null"))
                    else {
                        Toast.makeText(mContext, "Out of stock", Toast.LENGTH_SHORT).show();
                    }*/
            }
        });

  /*      holder.layoutDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                pos = position;

                holder.layoutQuantityChild.setVisibility(View.GONE);
              *//*  GradientDrawable bgShape = (GradientDrawable) holder.layoutQuantityParent.getBackground();
                bgShape.setColor(mContext.getResources().getColor(R.color.colorBottomShape));*//*

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

                if (Utility.networkCheck(mContext)) {
                    String url = GlobalUrl.ADD_CART_URL;
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("product_id", productsList.get(position).getmProductId());
                    params.put("product_type", productsList.get(position).getmProductType());
                    params.put("app_id", GlobalValues.APP_ID);
                    params.put("reference_id", mReferenceId);
                    params.put("customer_id", mCustomerId);
                    params.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                    params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                    params.put("product_qty", holder.txtQuantity.getText().toString());
                    new AddCartTask(params, holder, holder.txtQuantity.getText().toString()).execute(url);

                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

                *//*} else {

                    Intent intent = new Intent(mContext, LoginActivity.class);
                    mContext.startActivity(intent);

                }
*//*
            }
        });
*/
     /*   holder.layoutProductDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOnsActivity.class);
                mContext.startActivity(intent);
            }
        });*/


    }

    /*this function is used to show bento product dialog and related functionalities. */

    private void bentoProductDialog(final Products products) {

        mModifierPrice = 0.0;
        quantityCost = 0.0;


        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_product_details_dialog);
        dialog.show();


        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        final TextView txtPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        RecyclerView modifierRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        RelativeLayout layoutMealwith = (RelativeLayout) dialog.findViewById(R.id.layoutMealwith);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);



        txtProductName.setText(products.getmProductName());
        txtProductDesc.setText(products.getmProductDescription());
        txtQuantity.setText(products.getmMinQuantity());

        TextView category_name=dialog.findViewById(R.id.category_name);
        category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));


        mProductQuantity = txtQuantity.getText().toString();



        modifierRecyclerView.setVisibility(View.GONE);
        layoutMealwith.setVisibility(View.GONE);


        if (products.getmProductThumpImage() != null && products.getmProductThumpImage().length() > 0) {

            Picasso.with(mContext).load(products.getmProductThumpImage()).error(R.drawable.asset36).
                    into(imgProduct);

        } else {

            Picasso.with(mContext).load(R.drawable.asset36).into(imgProduct);

        }

        final double price = Double.valueOf(products.getmProductPrice());
        quantityCost = Double.valueOf(products.getmProductPrice()) * Integer.parseInt(products.getmMinQuantity());

        SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtPrice.setText(cs);

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                count++;


                quantityCost += price;


                txtQuantity.setText(String.valueOf(count));


                SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                txtPrice.setText(cs);

                mProductQuantity = txtQuantity.getText().toString();


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                if (count > 1) {
                    count--;

                    quantityCost -= price;

                    txtQuantity.setText(count + "");


                    SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtPrice.setText(cs);

                    mProductQuantity = txtQuantity.getText().toString();

                 }
            }
        });

        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


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


        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBeverageOptionDialog();

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                    mReferenceId = "";

                } else {
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                            // TODO:
//                            //    ActivityCompat#requestPermissions
//                            // here to request the missing permissions, and then overriding
//                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                            //                                          int[] grantResults)
//                            // to handle the case where the user grants the permission. See the documentation
//                            // for ActivityCompat#requestPermissions for more details.
//                            return;
//                        }
                        GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                        mReferenceId = GlobalValues.DEVICE_ID;

                    } catch (Exception e) {
                        GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                        mReferenceId = GlobalValues.DEVICE_ID;

                    } finally {
                        {
                            mCustomerId = "";
                        }
                    }
                }

                if (Utility.networkCheck(mContext)) {
                    String url = GlobalUrl.ADD_CART_URL;
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("product_id", products.getmProductId());
                    params.put("product_type", products.getmProductType());
                    params.put("app_id", GlobalValues.APP_ID);
                    params.put("reference_id", mReferenceId);
                    params.put("customer_id", mCustomerId);
                    params.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                    params.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                    params.put("product_qty", txtQuantity.getText().toString());


                    if (Integer.parseInt(txtQuantity.getText().toString()) >= Integer.parseInt(products.getmMinQuantity())) {

                        params.put("product_qty", txtQuantity.getText().toString());
//                              new AddCartTask(params, holder, holder.txtQuantity.getText().toString()).execute(url);

                    } else {

                        Toast.makeText(mContext, "Please add minimum " + products.getmMinQuantity() + "  quantity in cart", Toast.LENGTH_SHORT).show();
                    }

                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void openBeverageOptionDialog() {


        final Dialog dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_beverage_confirmation_dialog);
        dialog.show();

        TextView txtNo = (TextView) dialog.findViewById(R.id.txtNo);
        TextView txtYes = (TextView) dialog.findViewById(R.id.txtYes);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);

        txtYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                openBeverageDialog();

            }
        });


        txtNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });


        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    private void openBeverageDialog() {

    }

    @Override
    public int getItemCount() {
         return productsList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        private TextView txtAddCart;


        private ImageView imgProduct, subImage;
        private LinearLayout lly_subImg;
        private TextView txtProductDesc, txtProductName, txtPrice, txt_expiryDate, txt_available;
        private ImageView img_heart;
        private LinearLayout productNameLayout, parentLayout;
        private RelativeLayout rly_voucherLayout;
        private ImageView img_availableVoucher;
        private TextView txt_avaCoupons, txt_drinksQty, txt_expAva, txt_description, txt_voucherPrice, txt_OrderNow;
        private ImageView img_voucher;
        private RecyclerView tagimageRecyclerview;

        public ProductHolder(View itemView) {
            super(itemView);
            tagimageRecyclerview = itemView.findViewById(R.id.tagimageRecyclerview);
            txtAddCart = (TextView) itemView.findViewById(R.id.txtAddCart);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtProductDesc = (TextView) itemView.findViewById(R.id.txtProductDesc);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            img_heart = itemView.findViewById(R.id.img_heart);
            img_voucher = itemView.findViewById(R.id.img_voucher);
            subImage = itemView.findViewById(R.id.subImage);
            productNameLayout = itemView.findViewById(R.id.productNameLayout);
            parentLayout = itemView.findViewById(R.id.parentLayout);
            lly_subImg = itemView.findViewById(R.id.lly_subImg);
            txt_expiryDate = itemView.findViewById(R.id.txt_expiryDate);
            txt_available = itemView.findViewById(R.id.txt_available);
            txt_avaCoupons = itemView.findViewById(R.id.txt_avaCoupons);
            txt_drinksQty = itemView.findViewById(R.id.txt_drinksQty);
            txt_expAva = itemView.findViewById(R.id.txt_expAva);
            txt_description = itemView.findViewById(R.id.txt_description);
            txt_voucherPrice = itemView.findViewById(R.id.txt_voucherPrice);
            txt_OrderNow = itemView.findViewById(R.id.txt_OrderNow);
            rly_voucherLayout = itemView.findViewById(R.id.rly_voucherLayout);

        }
    }

    private void modifierproductDetailsDialog(final Context mContext, final ProductHolder productHolder
            , String mProductId, String quantity) {

        this.mProductQuantity = quantity;
        mModifierPrice = 0.0;
        quantityCost = 0.0;
        mModifierQuantity = 1;

        ModifierHeadingRecyclerAdapter modifierHeadingRecyclerAdapter;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_product_details_dialog);
        dialog.show();


        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        RecyclerView modifierRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);

        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        modifierRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(modifierProduct.getmProductName());
        txtProductDesc.setText(modifierProduct.getmProductDesc());

        mProductQuantity = txtQuantity.getText().toString();


        TextView category_name=dialog.findViewById(R.id.category_name);
        category_name.setText(Utility.readFromSharedPreference(mContext,GlobalValues.CATEGORY_SELECTED_NAME));

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


        if (modifierProduct.getmProductImage() != null && modifierProduct.getmProductImage().length() > 0) {

            Picasso.with(mContext).load(mBase_path + modifierProduct.getmProductImage()).into(imgProduct);

        } else {

            Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);

        }

        mModifierPrice = Double.parseDouble(modifierProduct.getmProductPrice());

        SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mModifierPrice)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModifierPrice.setText(cs);

        quantityCost = mModifierPrice;

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

        txtDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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

                                                     /*  for (int i = 0; i < modifierProduct.getModifiersList().size(); i++) {

                                                           isChildSelected = false;

                                                           if (modifierProduct.getModifiersList().get(i).getModifiersList() != null
                                                                   && modifierProduct.getModifiersList().get(i).getModifiersList().size() > 0) {

                                                               if (modifierProduct.getModifiersList().get(i).getmMaxSelected() ==
                                                                       modifierProduct.getModifiersList().get(i).getmModifierMaxSelect()) {

                                                                   count++;
                                                                   isChildSelected = true;
                                                                   isChildMaxSelected = true;
//                            break;
                                                               } else if (modifierProduct.getModifiersList().get(i).getmMaxSelected() == 0) {
                                                                   isChildSelected = false;
                                                                   isChildMaxSelected = false;
                                                                   mValidationMessage = modifierProduct.getModifiersList().get(i).getmModifierHeading();
                                                                   break;

                                                               } else if (modifierProduct.getModifiersList().get(i).getmMaxSelected() <
                                                                       modifierProduct.getModifiersList().get(i).getmModifierMaxSelect()) {

                                                                   isChildSelected = true;
                                                                   isChildMaxSelected = false;
                                                                   mValidationMessage = modifierProduct.getModifiersList().get(i).getmModifierHeading();
                                                                   break;
                                                               }
                                                           }
                                                       }*/


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
                                try {
                                    TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
//                                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                                        // TODO:
//                                        //    ActivityCompat#requestPermissions
//                                        // here to request the missing permissions, and then overriding
//                                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                        //                                          int[] grantResults)
//                                        // to handle the case where the user grants the permission. See the documentation
//                                        // for ActivityCompat#requestPermissions for more details.
//                                        return;
//                                    }
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
                            mapData.put("reference_id", "");
                            mapData.put("customer_id", mCustomerId);
                            mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                            mapData.put("app_id", GlobalValues.APP_ID);
                            mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                            mapData.put("product_qty", mProductQuantity);
                            mapData.put("product_id", modifierProduct.getmProductId());
                            mapData.put("product_name", modifierProduct.getmProductName());
                            mapData.put("product_total_price", String.valueOf(quantityCost));
                            mapData.put("product_unit_price", String.valueOf(mModifierPrice));
                            mapData.put("product_image", modifierProduct.getmProductImage());
                            mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
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
                                          /* } else {

                                               Toast.makeText(mContext, "Please select valid combination", Toast.LENGTH_SHORT).show();
                                           }*/
            }
        });

    }

    private void setMenuproductDetailsDialog(final Context mContext, final ProductHolder productHolder, final int position, final String mProductId,
                                             String quantity, List<ModifierHeading> modifierHeadingList, List<SetMenuTitle> setMenuTitleList) {
        SubCategoryActivity subCategoryActivity = new SubCategoryActivity();
        mProductQuantity = quantity;
        mSetMenuQuantity = 1;
        mSetMenuPrice = 0.0;
        GlobalValues.modifierSelectCount = 0;
        GlobalValues.subModifierSelectCount = 0;
        GlobalValues.MODIFIER_NAME = "";

        final SpannableString cs;

        SetMenuTitleRecyclerAdapter setMenuTitleRecyclerAdapter;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //TODO:Identification2
        dialog.setContentView(R.layout.layout_product_details_dialog);
        dialog.show();
        favouriteText = dialog.findViewById(R.id.favouriteText);
        favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
        notesText1 = dialog.findViewById(R.id.notesText);
        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        final TextView Addtocart = (TextView) dialog.findViewById(R.id.Addtocart);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

        Toolbar toolbar = dialog.findViewById(R.id.toolBar);
        LinearLayout imgBack = toolbar.findViewById(R.id.toolbarBack);
        TextView textHead = dialog.findViewById(R.id.textHead);
        textHead.setText(subCatString);
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        RecyclerView tagimageRecyclerview = (RecyclerView) dialog.findViewById(R.id.tagimageRecyclerview);
        ImageView layoutClose = (ImageView) dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);

        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        setmenuRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(setMenuProduct.getmProductAliasName());
        txtProductDesc.setText(setMenuProduct.getmProductDesc());

        TextView category_name=dialog.findViewById(R.id.category_name);
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

        if (isCustomerLoggedIn()) {
            if (mProductFavPrimaryId.equalsIgnoreCase("null")) {
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

                favouriteText.setText("Add to favourite");


                StatusFav = "1";
                //heartblink_imageview.setImageResource(R.drawable.heart_favourite);
                //subCategoryActivity.CheckFacourites();
            } else {
                favouriteText.setText("Remove from favourite");
                favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

                StatusFav = "0";
                //heartblink_imageview.setImageResource(R.drawable.heart_white);
                //subCategoryActivity.CheckFacourites();
            }
        } else {
            favouriteText.setText("Add to favourite");
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
            StatusFav = "1";
        }

         if (!(productsList.get(position).getmFaouriteStatusLable().equalsIgnoreCase(""))) {
            favouriteText.setText(productsList.get(position).getmFaouriteStatusLable());
        }

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isCustomerLoggedIn()) {
                    addFavouriteMethod(mProductPrimaryId, productHolder, position);
                } else {
                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                                ((Activity) mContext).finish();
                            }
                        }
                    });
                }
            }
        });

        mProductQuantity = txtQuantity.getText().toString();


        try {

            mSearchProuductprise = Double.valueOf(setMenuProduct.getmProductPrice());
            mSetMenuPrice = Double.valueOf(setMenuProduct.getmProductPrice());
        } catch (Exception e) {
            mSearchProuductprise = 0.0;
        }
        mquanititycost_src = mSetMenuPrice;




      /*  try {
            mSetMenuPrice = Double.valueOf(setMenuProduct.getmProductPrice());
            mSetMenuBasePrice = Double.valueOf(setMenuProduct.getmProductPrice());
        }catch (NumberFormatException e)
        {
            mSetMenuPrice=0.0;
            mSetMenuBasePrice=0.0;
            e.printStackTrace();
        }*/


        if (Integer.parseInt(setMenuProduct.getmProductType()) == 2) {

            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

            cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mSearchProuductprise)));

            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(mSearchProuductprise)));

            if (setMenuProduct.getSetMenuTitleList() != null && setMenuProduct.getSetMenuTitleList().size() > 0) {
                /* if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {*/

                /*if ((setMenuProduct.getmApplyMinMaxSelect() == 1)
                        && setMenuProduct.getSetMenuTitleList().get(position).getmenu_component_modifier_apply().equalsIgnoreCase("0")
                        && setMenuProduct.getSetMenuTitleList().get(position).getmultipleselection_apply().equalsIgnoreCase("0")) {


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

                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapternew);
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

                        /*}else{
                            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModifierPrice.setText(cs);
                            setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                                    setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create", modifierHeadingList);
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
                    setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                            setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create", modifierHeadingList);
                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);
                }*/
            }
        }



        if (productsList.get(productHolder.getAdapterPosition()).getmSubCategoryGalleryImage() != null
                && productsList.get(productHolder.getAdapterPosition()).getmSubCategoryGalleryImage().length() > 0) {

            Picasso.with(mContext).load(productsList.get(productHolder.getAdapterPosition()).getmSubCategoryGalleryImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);
        } else {
            Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);
        }
//dkfasjlkdfjdsajf
        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    txtQuantity.setText(String.valueOf(count));

                    mSetMenuQuantity = count;
                    quantityCost = (mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus) * mSetMenuQuantity;

                    try {

                    } catch (Exception e) {
                        e.printStackTrace();

                    }

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
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        });


        layoutClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
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
                Addtocart.performClick();
            }
        });

        Addtocart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!isCustomerLoggedIn()) {
                    new MessageDialog(mContext, new MessageDialog.OnSlectedString() {
                        @Override
                        public void selectedAction(String action) {
                            if (action.equalsIgnoreCase("Ok")) {
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                mContext.startActivity(intent);
                                ((Activity) mContext).finish();
                            }
                        }
                    });
                } else {

                    validateSetMenu(setMenuProduct.getSetMenuTitleList(), productHolder);
                }
            }
        });

    }


    private void validateSetMenu(List<SetMenuTitle> setMenuTitleList, ProductHolder productHolder) {
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

                        boolean selected = false;
                        if (modifierHeadings.size() != 0) {

                            List<ModifiersValue> modifiersValueList = modifierHeadings.get(0).getModifiersList();
                            for (int i = 0; i < modifiersValueList.size(); i++) {


                                if (modifiersValueList.get(i).getChekced()) {
                                    selected = false;

                                }

                            }

                            if (selected) {

                                Toast.makeText(mContext, "Please Select Minimum 1 Products!", Toast.LENGTH_SHORT).show();
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

                       /* Toast.makeText(mContext, "Please select minimum  " + setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect() + " product for "
                                + setMenuProduct.getSetMenuTitleList().get(i).getmTitleMenuName() + "", Toast.LENGTH_SHORT).show();*/
                        if (setMenuTitleList.get(i).getmTitleMenuName().equalsIgnoreCase("Sugar Level")) {
                            Toast.makeText(mContext, "Please select sugar level", Toast.LENGTH_SHORT).show();
                        } else {
//                            Toast.makeText(mContext, "Please select size and topping", Toast.LENGTH_SHORT).show();
                            Toast.makeText(mContext, "Please select " + setMenuTitleList.get(i).getmTitleMenuName(), Toast.LENGTH_SHORT).show();
                        }
                        return;
                    } else {

                    }
                    int postion;
                    if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                            && setMenuTitleList.get(j).getSetMenuModifierList().size() > 0) {

                        /*Log.e("menuname", setMenuTitleList.get(j).getmTitleMenuName());

                        for(int l=0; l<setMenuTitleList.get(j).getSetMenuModifierList().size(); l++){
                            SetMenuModifier smm = setMenuTitleList.get(j).getSetMenuModifierList().get(l);
                            Log.e("smm", smm.getmModifierName() + "--" + smm.isChecked() + "--" + l);
                        }*/

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

                                            /*Toast.makeText(mContext, "Please select minimum  " + modifierHeadings.get(a).getmModifierMinSelect() + " product for "
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
//                            if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                                // TODO:
//                                //    ActivityCompat#requestPermissions
//                                // here to request the missing permissions, and then overriding
//                                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                                //                                          int[] grantResults)
//                                // to handle the case where the user grants the permission. See the documentation
//                                // for ActivityCompat#requestPermissions for more details.
//                                return;
//                            }
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
                    //mapData.put("reference_id", "");
                    mapData.put("customer_id", mCustomerId);
                    mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);

                    mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));

                    mapData.put("product_id", setMenuProduct.getmProductId());
                    mapData.put("allow_cart", "yes");
                    mapData.put("cart_source", "Mobile");

                    String additionalNotes = notesText1.getText().toString();
                    if (!additionalNotes.equals("") && !additionalNotes.isEmpty() && !additionalNotes.equals("null")) {
                        mapData.put("product_remarks", additionalNotes);
                    }

                    if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                        mapData.put("product_total_price", String.valueOf(mquanititycost_src));
                        mapData.put("product_unit_price", String.valueOf(product_unit_price));
                    } else {
                        mapData.put("product_total_price", String.valueOf(quantityCost));
                        mapData.put("product_unit_price", String.valueOf(product_unit_price));
                    }
//dkfasjlkdfjdsajf
                    mapData.put("product_unit_price", String.valueOf(product_unit_price));
                    mapData.put("product_image", setMenuProduct.getmProductImage());
                    mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
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

                Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
                //  Toast.makeText(mContext, "Please Select Minimum 1 Products!", Toast.LENGTH_SHORT).show();
                //  Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
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
                        mapData.put("reference_id", "");
                        mapData.put("customer_id", mCustomerId);
                        mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                        mapData.put("app_id", GlobalValues.APP_ID);
                        mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
                        mapData.put("product_qty", mProductQuantity);
                        mapData.put("product_id", setMenuProduct.getmProductId());
                        mapData.put("product_name", setMenuProduct.getmProductAliasName());
                        mapData.put("menu_set_component", constructSetMenuJson().toString());

                        mapData.put("product_total_price", txtModifierPrice.getText().toString().replace("$", ""));

                        try {

                            mapData.put("product_unit_price", String.valueOf(product_unit_price));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                        mapData.put("product_image", setMenuProduct.getmProductImage());
                        mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
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
//                    if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//                        // TODO:
//                        //    ActivityCompat#requestPermissions
//                        // here to request the missing permissions, and then overriding
//                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                        //                                          int[] grantResults)
//                        // to handle the case where the user grants the permission. See the documentation
//                        // for ActivityCompat#requestPermissions for more details.
//                        return;
//                    }
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
            mapData.put("reference_id", "");
            mapData.put("customer_id", mCustomerId);
            mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
            mapData.put("app_id", GlobalValues.APP_ID);
            mapData.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
            mapData.put("product_qty", mProductQuantity);
            mapData.put("product_id", setMenuProduct.getmProductId());
            mapData.put("product_name", setMenuProduct.getmProductAliasName());
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
            mapData.put("product_total_price", txtModifierPrice.getText().toString().replace("$", ""));//4.80

            mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));

            // }
            double product_unit_price = mSetMenuPrice + subModifierPrice + SetMenuChildRecyclerAdapter.childPlusMinus;
            mapData.put("product_unit_price", String.valueOf(product_unit_price)); //4.80
            mapData.put("product_image", setMenuProduct.getmProductImage());
            mapData.put("price_with_modifier", String.valueOf(mModifierPrice)); //3.80
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

                                if (Integer.parseInt(arrProduct.get(i1).getTotalQuantity()) > 0 || arrProduct.get(i1).getmQuantity() > 0) {

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
                                    productJSONObj.put("product_qty", 1);

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
                                                        modifValueJSONObj.put("modifier_value_price", modifierValue.getmModifierValuePrice());// +1(honey)
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

    private class ModifierProductDetailsTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<ModifiersValue> modifiersValueList;
        ProductHolder holder;
        private String mProductId = "", mQuantity = "1";

        public ModifierProductDetailsTask(ProductHolder holder, String id) {
            this.holder = holder;
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
                    mBase_path = jsonObject.getString("product_gallery_image_source");

                    if (jsonResultSet.length() > 0) {

                        for (int i = 0; i < jsonResultSet.length(); i++) {

                            JSONObject jsonResult = jsonResultSet.getJSONObject(i);

                            modifierProduct = new ModifierProduct();

                            modifierProduct.setmProductId(jsonResult.getString("product_id"));
                            modifierProduct.setmProductName(jsonResult.getString("product_name"));
                            modifierProduct.setmProductType(jsonResult.getString("product_type"));
                            modifierProduct.setmProductSku(jsonResult.getString("product_sku"));
                            modifierProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            modifierProduct.setmProductImage(jsonResult.getString("product_thumbnail"));
                            modifierProduct.setmProductStatus(jsonResult.getString("product_status"));
                            modifierProduct.setmProductPrice(jsonResult.getString("product_price"));


                            String largeImage = "";
                            JSONArray image_gallery = jsonResult.getJSONArray("image_gallery");
                            for (int k = 0; k < image_gallery.length(); k++) {
                                JSONObject jsonObject1 = image_gallery.getJSONObject(k);
                                largeImage = jsonObject1.getString("pro_gallery_image");
                                if (largeImage != null && largeImage.length() > 0) {
                                    largeImage = galleryBasePath + "/" + largeImage;
                                    setMenuProduct.setmProductLargeImage(largeImage);
                                }
                            }


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

                    modifierproductDetailsDialog(mContext, holder, mProductId, mQuantity);

                } else {

                }

            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
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

        ProductHolder holder;
        private int position;
        private String mProductId = "", mQuantity = "1";

        public SetMenuProductDetailsTask(ProductHolder holder, int position, String id) {
            this.holder = holder;
            this.position = position;
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
            double price = 0.0;

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    mBasePath = jsonCommon.optString("image_source");

                    mBase_path = jsonCommon.getString("product_gallery_image_source");
                    galleryBasePath = jsonCommon.optString("product_gallery_image_source");
                    String tagimagepath = jsonCommon.optString("tag_image_source");

                    JSONArray jsonResultArray = jsonObject.getJSONArray("result_set");

                    if (jsonResultArray.length() > 0) {
                        for (int r = 0; r < jsonResultArray.length(); r++) {

                            JSONObject jsonResult = jsonResultArray.getJSONObject(r);

                            setMenuProduct = new SetMenuProduct();

                            setMenuProduct.setmProductId(jsonResult.getString("product_id"));
                            setMenuProduct.setmProductName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmProductAliasName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmproduct_apply_minmax_select(jsonResult.getString("product_apply_minmax_select"));
                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            setMenuProduct.setmProductImage(mBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            setMenuProduct.setmProductgalleryImage(mBase_path + "/" + jsonResult.getString("product_thumbnail"));
                            setMenuProduct.setmProductStatus(jsonResult.getString("product_status"));
                            setMenuProduct.setmProductPrice(jsonResult.getString("product_price"));
                            setMenuProduct.setmApplyMinMaxSelect(Integer.parseInt(jsonResult.getString("product_apply_minmax_select")));

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


                            setmenuArray = jsonResult.getJSONArray("set_menu_component");

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
                                    GlobalValues.MODIFIERAPPLYPRICE = jsonSetmenu.optString("menu_component_apply_price");
                                    setMenuTitle.setmMinSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_min_select")));
                                    setMenuTitle.setmMaxSelect(Integer.parseInt(jsonSetmenu.optString("menu_component_max_select")));
                                    setMenuTitle.setmDefaultSelectId(jsonSetmenu.optString("menu_component_default_select"));
                                    setMenuTitle.setmTotalQuantity(0);


                                    JSONArray jsonProductArray = jsonSetmenu.getJSONArray("product_details");

                                    setMenuModifierList = new ArrayList<>();

                                    if (jsonProductArray.length() > 0) {

                                        for (int p = 0; p < jsonProductArray.length(); p++) {

                                            JSONArray jsonArray = jsonProductArray.getJSONArray(p);

                                            if (jsonArray.length() > 0) {
                                                for (int p1 = 0; p1 < jsonArray.length(); p1++) {

                                                    JSONObject object = jsonArray.getJSONObject(p1);

                                                    setMenuModifier = new SetMenuModifier();

                                                    setMenuModifier.setsub_modifier_apply(jsonSetmenu.optString("menu_component_modifier_apply"));
                                                    setMenuModifier.setsub_multipleselection_apply(jsonSetmenu.optString("menu_component_multipleselection_apply"));
                                                    setMenuModifier.setApplyPrice(jsonSetmenu.optString("menu_component_apply_price"));
                                                    setMenuModifier.setmModifierId(object.optString("product_id"));
                                                    setMenuModifier.setmModifierName(object.optString("product_alias"));
                                                    setMenuModifier.setmModifierAliasName(object.optString("product_alias"));
                                                    setMenuModifier.setmMaxSelect(object.optString("product_bagel_max_select"));
                                                    setMenuModifier.setmMinSelect(object.optString("product_bagel_min_select"));
                                                    setMenuModifier.setmModifierPrice(object.optString("product_price"));
                                                    setMenuModifier.setmModifierSku(object.optString("product_sku"));
                                                    setMenuModifier.setmModifierSlug(object.optString("product_slug"));
                                                    setMenuModifier.setmQuantity(0);


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

                                                                        if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

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
                        setMenuproductDetailsDialog(mContext, holder, position, mProductId, mQuantity, modifierHeadingList, setMenuTitleList);

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

            String response = WebserviceAssessor.postRequest(mContext, params[0], cartparams);

            return response;
        }

        @Override
        protected void onPostExecute(String s) {

            super.onPostExecute(s);

            try {

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                    ((SubCategoryActivity) mContext).reloadTotalAmount();
//TODO
                 /*     if (productHolder.txtCurrentCartQuantity.getText().length() > 0)
                  {

                        int value = 0;
                        try {
                            value = Integer.valueOf(productHolder.txtCurrentCartQuantity.getText().toString().replace("X", "")) + Integer.valueOf(mQuantity);

                            ;
                            databaseHandler.updateQty(productsList.get(pos).getmProductId(), String.valueOf(value));
                            //((ProductListActivity) mContext).onrefreshdtata();
                        } catch (NumberFormatException e)
                        {
                            e.printStackTrace();
                        }


                    } else {
                        databaseHandler.insertProductData(productsList.get(pos).getmProductId(),""+Integer.valueOf(mQuantity));
                     //   ((ProductListActivity) mContext).onrefreshdtata();

                    }*/


                    JSONObject jsonContent = jsonObject.getJSONObject("contents");

                    JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");


                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                            jsonCartDetails.getString("cart_total_items"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());

                    Utility.writeToSharedPreference(mContext, GlobalValues.PRODUCT_LEAD_TIME, jsonContent.getString("product_lead_time"));



                    // ProductListActivity.isInvalidated = true;
                    ((SubCategoryActivity) mContext).invalidateOptionsMenu();
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
            }

        }
    }

    private Double getsetMenuProductPrice() throws Exception {

        double setPrice = 0.00;

        for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
            for (int i1 = 0; i1 < setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().size(); i1++) {

                Double qPrice = Double.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getmModifierPrice()) * Integer.valueOf(setMenuProduct.getSetMenuTitleList().get(i).getSetMenuModifierList().get(i1).getTotalQuantity());

                setPrice = setPrice + qPrice;
             }
        }

         return mSearchProuductprise + setPrice;

    }


    public void makeAddTotalCalculation(String productPrice) {

        double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);

        Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) + valu;

        SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModifierPrice.setText(cs);
    }

    public void makeSubstTotalCalculation(String productPrice) {
        double valu = Double.valueOf(productPrice) * Integer.valueOf(mProductQuantity);
        Double finalVal = Double.valueOf(txtModifierPrice.getText().toString().replace("$", "")) - valu;

        SpannableString cs = new SpannableString("$" + String.format("%.2f", finalVal));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModifierPrice.setText(cs);
    }


    private void addFavouriteMethod(String productId, ProductHolder holder, int position) {

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

        if (favouriteText.getText().toString().equalsIgnoreCase("Remove from favourite")) {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "No");
            param.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


            new FavouritesAddTask(param, holder, position).execute(url);

            StatusFav = "0";

        } else {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "Yes");
            param.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


            new FavouritesAddTask(param, holder, position).execute(url);

            StatusFav = "1";

        }

    }

    private void addFavouriteMethod2(String productId, ProductHolder holder, int position) {

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

        if (!(productsList.get(position).getmFavouriteHeartclickable().equalsIgnoreCase("ADD"))) {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "No");
            param.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);



            new FavouritesAddTask(param, holder, position).execute(url);

            StatusFav = "0";

        } else {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "Yes");
            param.put("availability_id", Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID));
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


            new FavouritesAddTask(param, holder, position).execute(url);
            StatusFav = "1";
        }
    }

    private class FavouritesAddTask extends AsyncTask<String, Void, String> {

        private Map<String, String> resetParams;
        private ProgressDialog progressDialog;
        private ProductHolder holder;
        private int postion;


        public FavouritesAddTask(Map<String, String> param, ProductHolder holder, int position) {
            resetParams = param;
            this.holder = holder;
            this.postion = position;
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


                    if (StatusFav.equalsIgnoreCase("1")) {
                        Toast.makeText(mContext, "Added to Favourites!", Toast.LENGTH_SHORT).show();
                        holder.img_heart.setImageResource(R.drawable.heart_select);
                        productsList.get(postion).setmFavouriteHeartclickable("REMOVE");
                        productsList.get(postion).setmFaouriteStatusLable("Remove from favourite");
                        try {
                            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                            favouriteText.setText("Remove from favourite");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Utility.writeToSharedPreference(mContext, GlobalValues.IMG_HEART, "no");


                        //heartblink_imageview.setImageResource(R.drawable.heart_favourite);
                        CheckFacourites();
                    } else {
                        Toast.makeText(mContext, "Removed successfully", Toast.LENGTH_SHORT).show();
                        holder.img_heart.setImageResource(R.drawable.heart_unselect);
                        productsList.get(postion).setmFavouriteHeartclickable("ADD");
                        productsList.get(postion).setmFaouriteStatusLable("Add to favourite");

                        try {
                            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                            favouriteText.setText("Add to favourite");
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        //Utility.writeToSharedPreference(mContext, GlobalValues.IMG_HEART, "yes");

                         // heartblink_imageview.setImageResource(R.drawable.heart_white);
                        CheckFacourites();
                    }


                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

            }


        }

    }


    public void CheckFacourites() {

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
                 ArrayList<Favouriteitems> favouriteitemsArrayList = new ArrayList<>();
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

                        // Utility.writeToSharedPreference(mContext, GlobalValues.IMG_HEART, "no");
                    }

                } else {

                    heartblink_imageview.setImageResource(R.drawable.heart_white);
                    //Utility.writeToSharedPreference(mContext, GlobalValues.IMG_HEART, "no");

                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                heartblink_imageview.setImageResource(R.drawable.heart_white);


                progressDialog.dismiss();

            }

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

    public void onScrolledToBottom() {
        if (productsList.size() < allProductList.size()) {
            int x, y;
            if ((allProductList.size() - productsList.size()) >= 10) {
                x = productsList.size();
                y = x + 10;
            } else {
                x = productsList.size();
                y = x + allProductList.size() - productsList.size();
            }
            for (int i = x; i < y; i++) {
                productsList.add(allProductList.get(i));
            }
            notifyDataSetChanged();
        }

    }


    public void loadMoreData() {
//        if (!isLoading) {
//            isLoading = true;
        for (int i = 0; i < 10; i++) {
            if (allProductList.size() > productsList.size()) {
                productsList.add(allProductList.get(productsList.size()));
                notifyItemInserted(productsList.size() - 1);
            }
        }
        notifyDataSetChanged();
//            contactUserAdapter.
//        notifyItemRangeChanged(productsList.size() - 11, 10);
//        notifyItemRangeInserted(productsList.size() - 11, 10);
//            isLoading = false;
//        }
    }

    public void loadAllMoreData() {
//        if (!isLoading) {
//            isLoading = true;
        int psize = productsList.size();
        int asize = allProductList.size();
        for (int i = psize - 1; i < asize - 1; i++) {
            if (allProductList.size() > productsList.size()) {
                productsList.add(allProductList.get(productsList.size()));
                notifyItemInserted(productsList.size() - 1);
            }
        }
        notifyDataSetChanged();
//            contactUserAdapter.
//        notifyItemRangeChanged(productsList.size() - 11, 10);
//        notifyItemRangeInserted(productsList.size() - 11, 10);
//            isLoading = false;
//        }
    }

    private boolean isLoggedIn() {
        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
            return true;
        } else {
            return false;
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
}
