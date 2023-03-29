package com.app.sushi.tei.adapter.Bento;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.Database.DatabaseHandler;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.AddOns.AddOnsProducts;
import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifierProduct;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.Products;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuProduct;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.AddOnsRecyclerAdapter;
import com.app.sushi.tei.adapter.Products1.ModifierHeadingRecyclerAdapter2;
import com.app.sushi.tei.adapter.Products1.SetMenuTitleRecyclerAdapter2;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BentoProductRecyclerAdapter extends RecyclerView.Adapter<BentoProductRecyclerAdapter.ProductHolder> {

    private Context mContext;
    private List<Products> productsList;
    private ModifierProduct modifierProduct;
    private SetMenuProduct setMenuProduct;
    private String mBasePath = "";
    private String mCustomerId = "", mReferenceId = "", mProductId = "", mProductQuantity = "1";
    private String mValidationMessage = "";
    public static String mAliasProductPrimaryId = "";
    public static Double mModifierPrice = 0.00, quantityCost = 0.00, mSetMenuPrice = 0.0, mSetMenuBasePrice = 0.0;
    public static int mModifierQuantity = 1, mSetMenuQuantity = 1;
    public static TextView txtModifierPrice;
    public static boolean isCorrectCombination = true;
    public Dialog dialog;
    private DatabaseHandler databaseHandler;
    private int pos;
    private int iProductStock;
    double price = 0.0;
    int minimumQu = 1;

    public BentoProductRecyclerAdapter(Context mContext, List<Products> productsList) {
        this.mContext = mContext;
        this.productsList = productsList;
        databaseHandler = DatabaseHandler.getInstance(mContext);
    }

    @Override
    public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = null;

        view = LayoutInflater.from(mContext).inflate(R.layout.layout_bento_item, parent, false);

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
                holder.layoutMaxCount.setVisibility(View.VISIBLE);
                holder.txtCurrentCartQuantity.setText("X" + cart.getmProductQty());
//                if (holder.tv_price.getText().length() > 0) {
////                    int value = 0;
////                    try {
////                        value = Integer.valueOf(holder.tv_price.getText().toString().replace("X", "")) + Integer.valueOf(cart.getCart_item_qty());
////                        holder.tv_price.setText("X" + String.valueOf(value));
////
////                    } catch (NumberFormatException e) {
////                        e.printStackTrace();
////                    }
//                } else {
//
//                    holder.tv_price.setText("X" + cart.getCart_item_qty());
//                }
//
            } else {
                holder.layoutMaxCount.setVisibility(View.GONE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        iProductStock = Integer.parseInt(productsList.get(position).getmProduct_stock());


        try {


            if (productsList.get(position).getmProduct_stock().equalsIgnoreCase("0")) {

                holder.txtAddCart.setText("Out of stock");
                holder.txtAddCart.setTextColor(mContext.getResources().getColor(R.color.colorBlack));
                //TODO
                // holder.txtAddCart.setBackground(mContext.getResources().getDrawable(R.drawable.outstock_shape));
            } else {

                holder.txtAddCart.setText("Add to Cart");
                holder.txtAddCart.setTextColor(mContext.getResources().getColor(R.color.colorWhite));
                //TODO
                // holder.txtAddCart.setBackground(mContext.getResources().getDrawable(R.drawable.common_button_shape));


            }


        } catch (Exception e) {
            e.printStackTrace();
        }


        holder.txtProductName.setText(productsList.get(position).getmProductName().replace("\\", ""));
        holder.txtPrice.setText("$ " + productsList.get(position).getmProductPrice());

        //TODO
      /*  Picasso.with(mContext).load(productsList.get(position).getmProductThumpImage()).
                error(R.drawable.img_placeholder).into(holder.imgProduct);*/


        holder.txtAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                pos = position;


                if (productsList.get(position).getmProduct_stock().equalsIgnoreCase("0")) {


                } else {


                    bentoProductDialog(productsList.get(position), holder);


                }

            }
        });


     /*   holder.layoutProductDescription.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, AddOnsActivity.class);
                mContext.startActivity(intent);
            }
        });*/


    }

    /*this function is used to show bento product dialog and related functionalities. */

    private void bentoProductDialog(final Products products, final ProductHolder holder) {

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
        txtQuantity.setText("1");

        mProductQuantity = txtQuantity.getText().toString();

        modifierRecyclerView.setVisibility(View.GONE);
        layoutMealwith.setVisibility(View.GONE);




        if (products.getmProductThumpImage() != null && products.getmProductThumpImage().length() > 0) {
            //TODO
           /* Picasso.with(mContext).load(products.getmProductThumpImage()).error(R.drawable.img_placeholder).
                    into(imgProduct);*/

        } else {
            //TODO
            // Picasso.with(mContext).load(R.drawable.img_placeholder).into(imgProduct);

        }



/*
        if(!products.getmMinQuantity().equalsIgnoreCase("null"))
        {


        }
        else
        {


            price = Double.valueOf(products.getmProductPrice());
            quantityCost = Double.valueOf(products.getmProductPrice());

            minimumQu =Integer.parseInt(products.getmMinQuantity());


        }*/

       /* price = Double.valueOf(products.getmProductPrice());
        quantityCost = Double.valueOf(products.getmProductPrice()) * Integer.parseInt(products.getmMinQuantity());
        minimumQu =Integer.parseInt(products.getmMinQuantity());
*/

        price = Double.valueOf(products.getmProductPrice());
        quantityCost = Double.valueOf(products.getmProductPrice());

        minimumQu = 1;


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

                if (count > minimumQu) {
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

                if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                    mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                    mReferenceId = "";

                } else {
                    try {
                        TelephonyManager telephonyManager = (TelephonyManager) mContext.getSystemService(Context.TELEPHONY_SERVICE);
                        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
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
                    params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                    params.put("product_qty", txtQuantity.getText().toString());
                    params.put("isAddonProduct","No");

                    if (Integer.parseInt(txtQuantity.getText().toString()) >= Integer.parseInt(products.getmMinQuantity())) {

                        params.put("product_qty", txtQuantity.getText().toString());

                              new AddCartTask(params, holder, txtQuantity.getText().toString()).execute(url);

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

               getAddOns();

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

    private void openBeverageDialog(Context mContext, List<AddOnsProducts> addOnsProductsList) {
        final Dialog dialog = new Dialog(this.mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_beverage_dialog);
        dialog.show();

        RecyclerView recyclerviewBeverage= (RecyclerView) dialog.findViewById(R.id.recyclerviewBeverage);
        TextView txtGoBack= (TextView) dialog.findViewById(R.id.txtGoBack);
        TextView txtContinue= (TextView) dialog.findViewById(R.id.txtContinue);

        RecyclerView.LayoutManager layoutManager=new LinearLayoutManager(this.mContext);
        recyclerviewBeverage.setLayoutManager(layoutManager);

        txtGoBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();
            }
        });


        txtContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

    }

    @Override
    public int getItemCount() {
        return productsList.size();
    }

    public class ProductHolder extends RecyclerView.ViewHolder {

        private TextView txtAddCart;
        private RelativeLayout layoutQuantityParent, layoutDone, layoutQuantityChild;
        private LinearLayout layoutProductDescription;
        private RelativeLayout layoutMaxCount;
        private ImageView imgDecreement, imgIncreement, imgProduct;
        private TextView txtQuantity, txtProductDesc, txtProductName, txtPrice, txtCurrentCartQuantity;


        public ProductHolder(View itemView) {
            super(itemView);

            txtAddCart = (TextView) itemView.findViewById(R.id.txtAddCart);
            //TODO
         //   layoutProductDescription = (LinearLayout) itemView.findViewById(R.id.layoutProductDescription);
            imgProduct = (ImageView) itemView.findViewById(R.id.imgProduct);
            txtQuantity = (TextView) itemView.findViewById(R.id.txtQuantity);
            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);
            txtCurrentCartQuantity = (TextView) itemView.findViewById(R.id.txtCurrentCartQuantity);
            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);
            layoutMaxCount = (RelativeLayout) itemView.findViewById(R.id.layoutMaxCount);

        }
    }


    private void modifierproductDetailsDialog(final Context mContext, final ProductHolder productHolder
            , String mProductId, String quantity) {

        this.mProductQuantity = quantity;
        mModifierPrice = 0.0;
        quantityCost = 0.0;
        mModifierQuantity = 1;

        ModifierHeadingRecyclerAdapter2 modifierHeadingRecyclerAdapter;

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







        if (Integer.parseInt(modifierProduct.getmProductType()) == 4) {

            if (modifierProduct.getModifiersList().size() > 0) {

                modifierHeadingRecyclerAdapter = new ModifierHeadingRecyclerAdapter2(mContext, modifierProduct.getModifiersList(),
                        this.mProductId);
                modifierRecyclerView.setAdapter(modifierHeadingRecyclerAdapter);
                modifierRecyclerView.setItemAnimator(new DefaultItemAnimator());
                modifierRecyclerView.setNestedScrollingEnabled(false);

                modifierHeadingRecyclerAdapter.checkAllModifiersSelected();

            }
        } else {

        }



        if (modifierProduct.getmProductImage() != null && modifierProduct.getmProductImage().length() > 0) {

            Picasso.with(mContext).load(mBasePath + modifierProduct.getmProductImage()).into(imgProduct);

        } else {
//TODO
         //   Picasso.with(mContext).load(R.drawable.img_placeholder).into(imgProduct);

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
                                    GlobalValues.DEVICE_ID = telephonyManager.getDeviceId();
                                    mReferenceId = GlobalValues.DEVICE_ID;

                                } catch (SecurityException e) {
                                    GlobalValues.DEVICE_ID = Settings.Secure.getString(mContext.getContentResolver(), Settings.Secure.ANDROID_ID);
                                    mReferenceId = GlobalValues.DEVICE_ID;

                                } finally {
                                    mCustomerId = "";
                                }
                            }


                            Map<String, String> mapData = new HashMap<>();
                            mapData.put("reference_id", mReferenceId);
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
                            mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
                            mapData.put("product_sku", modifierProduct.getmProductSku());
                            mapData.put("product_type", modifierProduct.getmProductType());

                            if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                                mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                            } else {
                                mapData.put("product_modifier_parent_id", "");

                            }

                            mapData.put("modifiers", constructModifierJson().toString());

                            new AddCartTask(mapData, productHolder, mProductQuantity).execute(url);

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


    private void setMenuproductDetailsDialog(final Context mContext, final ProductHolder productHolder
            , String mProductId, String quantity) {

        this.mProductQuantity = quantity;
        mSetMenuQuantity = 1;

        SetMenuTitleRecyclerAdapter2 setMenuTitleRecyclerAdapter;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_product_details_dialog);
        dialog.show();


        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
        RelativeLayout layoutClose = (RelativeLayout) dialog.findViewById(R.id.layoutClose);
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

        mProductQuantity = txtQuantity.getText().toString();

        SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mSetMenuPrice)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        txtModifierPrice.setText(cs);

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

            if (setMenuProduct.getSetMenuTitleList() != null &&
                    setMenuProduct.getSetMenuTitleList().size() > 0)
            {

                setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter2(mContext,
                        setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(),"create");
                setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                setmenuRecyclerView.setNestedScrollingEnabled(false);
                setmenuRecyclerView.setHasFixedSize(true);
            }
        } else {

        }


        if (setMenuProduct.getmProductImage() != null && setMenuProduct.getmProductImage().length() > 0) {
            //TODO
           // Picasso.with(mContext).load(setMenuProduct.getmProductImage()).error(R.drawable.img_placeholder).into(imgProduct);

        } else {
            //TODO
           // Picasso.with(mContext).load(R.drawable.img_placeholder).into(imgProduct);
        }


        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                count++;
                mSetMenuQuantity = count;
                quantityCost += mSetMenuPrice;

                txtQuantity.setText(String.valueOf(count));

                txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

                mProductQuantity = txtQuantity.getText().toString();


            }
        });

        imgDecreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());

                if (count > 1) {
                    count--;
                    mSetMenuQuantity = count;
                    quantityCost -= mSetMenuPrice;

                    txtQuantity.setText(count + "");


                    txtModifierPrice.setText(Utility.setPriceSpan(mContext, quantityCost));

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

                validateSetMenu(setMenuProduct.getSetMenuTitleList(), productHolder);

            }
        });

    }

    private void validateSetMenu(List<SetMenuTitle> setMenuTitleList, ProductHolder productHolder) {

        boolean isChildSelected = false;
        int count = 0;
        for (int j = 0; j < setMenuTitleList.size(); j++) {

            isChildSelected = false;

            if (setMenuProduct.getmApplyMinMaxSelect() == 0) {

                if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                        && setMenuTitleList.get(j).getSetMenuModifierList().size() > 0) {

                    for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                        if (setMenuTitleList.get(j).getSetMenuModifierList().get(k).isChecked()) {
                            count++;
                            isChildSelected = true;
                            break;
                        } else {
                            isChildSelected = false;
                        }
                    }
                    if (!isChildSelected) {
                        mValidationMessage = setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }

                }
            } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

                if (setMenuTitleList.get(j).getSetMenuModifierList() != null
                        && setMenuTitleList.get(j).getSetMenuModifierList().size() > 0) {

                    String msg = "";

                    for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {

                        if (setMenuTitleList.get(j).getSetMenuModifierList().get(k).getmQuantity() > 0) {

                            if (setMenuTitleList.get(j).getmTotalQuantity() >= setMenuTitleList.get(j).getmMinSelect()) {

                                count++;
                                isChildSelected = true;
                                break;
                            } else {

                                isChildSelected = false;
                                msg = "Please select minimum quantity in ";
                                break;
                            }

                        } else {
                            isChildSelected = false;
                            msg = "Please select option in ";
                        }
                    }
                    if (!isChildSelected) {
                        mValidationMessage = msg + setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }

                }


            }
        }

        if (count == setMenuTitleList.size()) {
            if (Utility.networkCheck(mContext)) {

                String url = GlobalUrl.ADD_CART_SET_MENU_URL;

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
                        mCustomerId = "";
                    }
                }


                Map<String, String> mapData = new HashMap<>();
                mapData.put("reference_id", mReferenceId);
                mapData.put("customer_id", mCustomerId);
                mapData.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                mapData.put("app_id", GlobalValues.APP_ID);
                mapData.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                mapData.put("product_qty", mProductQuantity);
                mapData.put("product_id", setMenuProduct.getmProductId());
                mapData.put("product_name", setMenuProduct.getmProductName());
                mapData.put("product_total_price", String.valueOf(quantityCost));
                mapData.put("product_unit_price", String.valueOf(mSetMenuPrice));
                mapData.put("product_image", setMenuProduct.getmProductImage());
                mapData.put("price_with_modifier", String.valueOf(mModifierPrice));
                mapData.put("product_sku", setMenuProduct.getmProductSku());
                mapData.put("product_type", setMenuProduct.getmProductType());

                if (mAliasProductPrimaryId != null && mAliasProductPrimaryId.length() > 0) {
                    mapData.put("product_modifier_parent_id", mAliasProductPrimaryId);
                } else {
                    mapData.put("product_modifier_parent_id", "");

                }

                mapData.put("menu_set_component", constructSetMenuJson().toString());

                new AddCartTask(mapData, productHolder, mProductQuantity).execute(url);

            } else {
                Toast.makeText(mContext, "Please check your internet connection. ", Toast.LENGTH_SHORT).show();
            }

        } else {

            Toast.makeText(mContext, mValidationMessage, Toast.LENGTH_SHORT).show();
        }
    }

    private String constructSetMenuJson() {
        try {

            if (setMenuProduct.getmApplyMinMaxSelect() == 1) {  //New quantity type product

                JSONArray menuComponentsJSONArray = new JSONArray();
                List<SetMenuTitle> setMenuTitleList = setMenuProduct.getSetMenuTitleList();

                for (int i = 0; i < setMenuTitleList.size(); i++) {  //menuCompontnt size

                    SetMenuTitle setMenuTitle = setMenuTitleList.get(i);

                    JSONObject menuComponentJSON = new JSONObject();

                    menuComponentJSON.put("menu_component_id", setMenuTitle.getmTitleMenuId());
                    menuComponentJSON.put("menu_component_name", setMenuTitle.getmTitleMenuName());
                    menuComponentJSON.put("min_max_flag", "1");

                    JSONArray productJSONArray = new JSONArray();
                    List<SetMenuModifier> setMenuModifierList = setMenuTitle.getSetMenuModifierList();

                    for (int j = 0; j < setMenuModifierList.size(); j++) {

                        SetMenuModifier setMenuModifier = setMenuModifierList.get(j);

                        if (setMenuModifier.getmQuantity() > 0) {


                            JSONObject productJSONObj = new JSONObject();

                            productJSONObj.put("product_id", setMenuModifier.getmModifierId());
                            productJSONObj.put("product_name", Utility.getProductName(setMenuModifier));
                            productJSONObj.put("product_sku", setMenuModifier.getmModifierSku());

                            productJSONObj.put("product_price", setMenuModifier.getmModifierPrice() + "");
                            productJSONObj.put("product_qty", setMenuModifier.getmQuantity() + "");

                            productJSONArray.put(productJSONObj);


                        } /*else {  //Product is not selected

                                }*/

                    }

                    menuComponentJSON.put("product_details", productJSONArray);
                    menuComponentsJSONArray.put(menuComponentJSON);

                }

//                mapData.put("menu_set_component", menuComponentsJSONArray.toString());


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
                                                modifValueJSONObj.put("modifier_value_qty", 1);

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
//                mapData.put("menu_set_component", menuComponentsJSONArray.toString());

            }

            //        formBodyBuilder.add("modifiers", "null");//null

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
        private String mProductId = "", mQuantity = "1";

        public SetMenuProductDetailsTask(ProductHolder holder, String id) {
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
            double price = 0.0;

            try {


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject jsonCommon = jsonObject.getJSONObject("common");

                    mBasePath = jsonCommon.optString("image_source");

                    JSONArray jsonResultArray = jsonObject.getJSONArray("result_set");

                    if (jsonResultArray.length() > 0) {
                        for (int r = 0; r < jsonResultArray.length(); r++) {

                            JSONObject jsonResult = jsonResultArray.getJSONObject(r);

                            setMenuProduct = new SetMenuProduct();

                            setMenuProduct.setmProductId(jsonResult.getString("product_id"));
                            setMenuProduct.setmProductName(jsonResult.getString("product_name"));
                            setMenuProduct.setmProductAliasName(jsonResult.getString("product_alias"));
                            setMenuProduct.setmProductType(jsonResult.getString("product_type"));
                            setMenuProduct.setmProductSku(jsonResult.getString("product_sku"));
                            setMenuProduct.setmProductDesc(jsonResult.getString("product_short_description"));
                            setMenuProduct.setmProductImage(mBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            setMenuProduct.setmProductStatus(jsonResult.getString("product_status"));
                            setMenuProduct.setmProductPrice(jsonResult.getString("product_price"));
                            setMenuProduct.setmApplyMinMaxSelect(Integer.parseInt(jsonResult.getString("product_apply_minmax_select")));

                            try {

                                mSetMenuBasePrice = Double.valueOf(jsonResult.getString("product_price"));
                            } catch (Exception e) {
                                mSetMenuBasePrice = 0.0;
                            }

                            mSetMenuPrice = mSetMenuBasePrice;

                            JSONArray setmenuArray = jsonResult.getJSONArray("set_menu_component");

                            setMenuTitleList = new ArrayList<>();

                            if (setmenuArray.length() > 0) {

                                for (int t = 0; t < setmenuArray.length(); t++) {
                                    JSONObject jsonSetmenu = setmenuArray.getJSONObject(t);

                                    setMenuTitle = new SetMenuTitle();

                                    setMenuTitle.setmTitleMenuId(jsonSetmenu.optString("menu_component_id"));
                                    setMenuTitle.setmTitleMenuName(jsonSetmenu.optString("menu_component_name"));
                                    setMenuTitle.setmAppliedPrice(jsonSetmenu.optString("menu_component_apply_price"));
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

                                                    setMenuModifier.setmModifierId(object.optString("product_id"));
                                                    setMenuModifier.setmModifierName(object.optString("product_name"));
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
                        setMenuproductDetailsDialog(mContext, holder, mProductId, mQuantity);

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
        private ProductHolder productHolder;
        private String mQuantity;

        public AddCartTask(Map<String, String> cartparams, ProductHolder productHolder, String quantity) {
            this.cartparams = cartparams;
            this.productHolder = productHolder;
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

                    if (productHolder.txtCurrentCartQuantity.getText().length() > 0) {

                        int value = 0;
                        try {
                            value = Integer.valueOf(productHolder.txtCurrentCartQuantity.getText().toString().replace("X", ""))
                                    + Integer.valueOf(mQuantity);
                             ;
                            databaseHandler.updateQty(productsList.get(pos).getmProductId(), String.valueOf(value));
//                        ((ProductListActivity) mContext).onrefreshdtata();
                        } catch (NumberFormatException e) {
                            e.printStackTrace();
                        }


                    } else {
                        //databaseHandler.insertProductData(productsList.get(pos).getmProductId(), productHolder.txtQuantity.getText().toString());
//                        ((ProductListActivity) mContext).onrefreshdtata();

                    }


                    JSONObject jsonContent = jsonObject.getJSONObject("contents");

                    JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,jsonCartDetails.getString("cart_total_items"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());

                    Utility.writeToSharedPreference(mContext, GlobalValues.BENTO_CART_COUNT,jsonCartDetails.getString("cart_total_items"));








                  /*  ProductListActivity.isInvalidated = true;
                    ((ProductListActivity) mContext).invalidateOptionsMenu();*/
                  //  ((HomeActivity) mContext).invalidateOptionsMenu();

                    if (dialog != null) {
                        dialog.dismiss();
                    }

                    //openBeverageOptionDialog();

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

    private void getAddOns() {
        if (Utility.networkCheck(mContext)) {

            GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
            GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

            String url = GlobalUrl.PRODUCT_URL + "?app_id=" + GlobalValues.APP_ID +
                    "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                    "&outletId=" + GlobalValues.CURRENT_OUTLET_ID +
                    "&apply_addon=Yes";

            new AddOnsTask().execute(url);


        } else {

            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();

        }


    }

    public class AddOnsTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        List<AddOnsProducts> addOnsProductsList;

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

                addOnsProductsList = new ArrayList<>();

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {


                    JSONArray resultArray = jsonObject.getJSONArray("result_set");


                    if (resultArray.length() > 0) {

                        for (int i = 0; i < resultArray.length(); i++) {

                            JSONObject object = resultArray.getJSONObject(i);

                            JSONArray jsonSubCategory = object.getJSONArray("subcategorie");

                            if (jsonSubCategory.length() > 0) {


                                for (int c = 0; c < jsonSubCategory.length(); c++) {
                                    JSONObject category = jsonSubCategory.getJSONObject(c);

                                    JSONArray productArray = category.getJSONArray("products");


                                    if (productArray.length() > 0) {

                                        for (int p = 0; p < productArray.length(); p++) {

                                            AddOnsProducts addOnsProducts = new AddOnsProducts();

                                            JSONObject product = productArray.getJSONObject(p);

                                            addOnsProducts.setmProductId(product.getString("product_id"));
                                            addOnsProducts.setmProductName(product.getString("product_name"));
                                            addOnsProducts.setmProductType(product.getString("product_type"));
                                            addOnsProducts.setmProductPrice(product.getString("product_price"));
                                            addOnsProducts.setmProductSku(product.getString("product_sku"));
                                            addOnsProducts.setmProductSlug(product.getString("product_slug"));
                                            addOnsProducts.setmProductStatus(product.getString("product_status"));

                                            addOnsProductsList.add(addOnsProducts);
                                        }

                                    }
                                }
                            }
                        }
                    }

                } else {
                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                if (progressDialog != null) {
                    progressDialog.dismiss();
                }


                openBeverageDialog(mContext, addOnsProductsList);
            }

        }
    }
}
