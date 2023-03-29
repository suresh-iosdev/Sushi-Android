package com.app.sushi.tei.adapter.favouriteAdapter;

import android.Manifest;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.provider.Settings;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
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
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Cart.Cart;
import com.app.sushi.tei.Model.ProductList.ModifierHeading;
import com.app.sushi.tei.Model.ProductList.ModifierProduct;
import com.app.sushi.tei.Model.ProductList.ModifiersValue;
import com.app.sushi.tei.Model.ProductList.SetMenuModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuProduct;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.activity.FavouriteActivity;
import com.app.sushi.tei.activity.FiveMenuActivityNew;
import com.app.sushi.tei.adapter.AddOnsRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuChildRecyclerAdapter;
import com.app.sushi.tei.adapter.Products.SetMenuTitleRecyclerAdapter;
import com.app.sushi.tei.adapter.SetMenuAdapter.SetMenuTitleRecyclerAdapterNew;
import com.app.sushi.tei.fragment.FavouriteFragment;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.app.sushi.tei.Model.ProductList.ModifierHeading.subModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mModifierQuantity;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuBasePrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.mSetMenuQuantity;
import static com.app.sushi.tei.activity.SubCategoryActivity.quantityCost;
import static com.app.sushi.tei.activity.SubCategoryActivity.txtModifierPrice;
import static com.app.sushi.tei.activity.SubCategoryActivity.value;

public class FavouriteAdapter extends RecyclerView.Adapter<FavouriteAdapter.OtherHolder> {

    private Context mContext;
    private ModifierProduct modifierProduct;
    private SetMenuProduct setMenuProduct;
    private String mBasePath = "", galleryBasePath = "";
    private ArrayList<Favouriteitems> favouriteitemsArrayList;
    public IOnItemClick iOnItemClick;
    public int CurrentPostion;
    private String mCustomerId = "", mReferenceId = "", mProductId = "", mProductQuantity = "1";
    public static boolean isCorrectCombination = true;
    public Dialog dialog;
    private DatabaseHandler databaseHandler;


    private ArrayList<Favouriteitems> Favlistarraylist = new ArrayList<>();


    private String mValidationMessage = "";
    public static String mAliasProductPrimaryId = "";

    private LinearLayout favouriteLayout;
    private TextView favouriteText;
    private TextView txtModi;
    private EditText notesText, notesText1;
    private String mProductFavPrimaryId = "null", subCatString;
    ;
    private String StatusFav = "0";
    private Double mSearchProuductprise = 0.0;
    public static Double mquanititycost_src = 0.00, mSetmenuoverallprices = 0.0;
    Double total_unitprices = 0.0;
    private SetMenuTitleRecyclerAdapterNew setMenuTitleRecyclerAdapternew;

    public FavouriteAdapter(Context mContext, ArrayList<Favouriteitems> favouriteitems) {
        this.mContext = mContext;
        this.favouriteitemsArrayList = favouriteitems;
        databaseHandler = DatabaseHandler.getInstance(mContext);
    }

    @Override
    public FavouriteAdapter.OtherHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.fav_layout_items, parent, false);

        OtherHolder otherHolder = new OtherHolder(view);

        return otherHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull final OtherHolder holder, final int position) {

        final Favouriteitems favouriteitems = favouriteitemsArrayList.get(position);


        holder.txtProductName.setText("" + favouriteitems.getProduct_alias());


        if (favouriteitems.getProduct_alias() != null && !favouriteitems.getProduct_alias().equalsIgnoreCase("null")) {


            holder.txtProductName.setText(favouriteitems.getProduct_alias().replace("\\", ""));

        } else {

            holder.txtProductName.setText(favouriteitems.getProduct_name().replace("\\", ""));

        }

        holder.txtProductDesc.setText("" + favouriteitems.getProduct_short_description());

        String imageurl = favouriteitems.getProduct_thumbnail();

        if (imageurl != null && imageurl.length() > 0) {

            Picasso.with(mContext).load(imageurl).
                    error(R.drawable.place_holder_sushi_tei).into(holder.productImageView);
        } else {

            holder.productImageView.setVisibility(View.GONE);
        /*    LinearLayout.LayoutParams param = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    1f
            );

            holder.parentLayout.setWeightSum(1f);
            holder.productNameLayout.setLayoutParams(param);*/
        }


        holder.txtPrice.setText(favouriteitems.getProduct_price());
        holder.txtAddCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                CurrentPostion = position;

                FavouriteValidation(position);

            }
        });


        holder.closeImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


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


                String url = GlobalUrl.FavouriteURl;

                Map<String, String> param = new HashMap<String, String>();

                param.put("app_id", GlobalValues.APP_ID);
                param.put("customer_id", mCustomerId);
                param.put("product_id", favouriteitems.getFav_product_primary_id());
                param.put("fav_flag", "No");
                param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);


                new FavouritesRemove(param).execute(url);


            }
        });

    }

    @Override
    public int getItemCount() {
        return favouriteitemsArrayList != null ? favouriteitemsArrayList.size() : 0;
    }

    public void notifyAdapter() {

        notifyDataSetChanged();

    }


    public void FavouriteValidation(int position) {
        final Favouriteitems favouriteitems = favouriteitemsArrayList.get(position);
/*
        app_id:F2442DB2-9852-4B33-AF11-B96DB1CD2D44
        availability:EF9FB350-4FD4-4894-9381-3E859AB74019
        outletId:138
        product_id:5C1CF3DA-D7C5-4E7A-9A57-AD1FE2769970

        passing parameter is
https://ccpl.ninjaos.com/api/products/favourite_product_valid?app_id=
F2442DB2-9852-4B33-AF11-B96DB1CD2D44&
product_id=D9253554-51BD-4E17-A82D-885F8A5CF1C8
&availability_id=718B1A92-5EBB-4F25-B24D-3067606F67F0&outlet_id=208
                        "fav_app_id": "F2442DB2-9852-4B33-AF11-B96DB1CD2D44",
                        "product_id": "92870F6A-8427-4494-AD81-1F37D8125911",


        */
        if (Utility.networkCheck(mContext)) {

            String app_id = "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + mCustomerId;
            String availability_id = "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;
            GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);
            String productid = "&product_id=" + favouriteitems.getProduct_id();
            String outlet_id = "&outlet_id=" + GlobalValues.CURRENT_OUTLET_ID;



            String Url = GlobalUrl.FavouriteListValidationURL + app_id + productid + availability_id + outlet_id;

            new FavouriteValdiationTask().execute(Url);


        } else {


        }


    }


    private class FavouritesRemove extends AsyncTask<String, Void, String> {

        private Map<String, String> resetParams;
        private ProgressDialog progressDialog;

        public FavouritesRemove(Map<String, String> param) {
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


                    if (GlobalValues.FavouriteCheck.equalsIgnoreCase("1")) {

                        ReSetAdapter();
                     } else {
                        ((FavouriteActivity) mContext).RefreshRemoved();
                         notifyAdapter();
                    }


                    Toast.makeText(mContext, "Removed Successfully..", Toast.LENGTH_SHORT).show();


                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

            }


        }
    }

    private void ReSetAdapter() {

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


            String app_id = "?app_id=" + GlobalValues.APP_ID + "&customer_id=" + mCustomerId;

            String availability_id = "&availability_id=" + GlobalValues.CURRENT_AVAILABLITY_ID;

            GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);


            String outlet_id = "&outlet_id=" + GlobalValues.CURRENT_OUTLET_ID;


            String Url = GlobalUrl.FavouriteListURL + app_id + availability_id + outlet_id;


            new FavouriteListTask().execute(Url);

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }
    }


    public class OtherHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView closeImageView, productImageView;

        private TextView txtProductName, txtProductDesc, priceCurrencyTextView, txtPrice, txtAddCart;
        private LinearLayout productNameLayout, parentLayout;

        public OtherHolder(View itemView) {
            super(itemView);

            closeImageView = (ImageView) itemView.findViewById(R.id.closeImageView);

            productImageView = (ImageView) itemView.findViewById(R.id.productImageView);

            txtProductName = (TextView) itemView.findViewById(R.id.txtProductName);

            txtProductDesc = (TextView) itemView.findViewById(R.id.txtProductDesc);

            priceCurrencyTextView = (TextView) itemView.findViewById(R.id.priceCurrencyTextView);

            txtPrice = (TextView) itemView.findViewById(R.id.txtPrice);

            txtAddCart = (TextView) itemView.findViewById(R.id.txtAddCart);

            productNameLayout = itemView.findViewById(R.id.productNameLayout);
            parentLayout = itemView.findViewById(R.id.parentLayout);


        }

        @Override
        public void onClick(View v) {

            if (iOnItemClick != null) {
                iOnItemClick.onItemClick(v, getPosition());
            }

        }
    }

    public void setOnItemClick(IOnItemClick click) {
        iOnItemClick = click;
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

                String basePath = jsonObject.getJSONObject("common").optString("subcategory_image_url");


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

                        Favlistarraylist.add(favouriteitems);


                    }


                    if (Favlistarraylist.size() > 0) {


                        if (GlobalValues.FavouriteCheck.equalsIgnoreCase("1")) {

                            FavouriteFragment.recyclerviewfavourite.setVisibility(View.VISIBLE);
                            FavouriteFragment.txtEmpty.setVisibility(View.GONE);

                            FavouriteAdapter favouriteAdapter = new FavouriteAdapter(mContext, Favlistarraylist);
                            FavouriteFragment.layoutManager = new LinearLayoutManager(mContext);
                            FavouriteFragment.recyclerviewfavourite.setLayoutManager(FavouriteFragment.layoutManager);
                            FavouriteFragment.recyclerviewfavourite.setAdapter(favouriteAdapter);
                            FavouriteFragment.recyclerviewfavourite.setItemAnimator(new DefaultItemAnimator());


                        } else {

                            FavouriteActivity.recyclerviewfavourite.setVisibility(View.VISIBLE);
                            FavouriteActivity.txtEmpty.setVisibility(View.GONE);

                            FavouriteAdapter favouriteAdapter = new FavouriteAdapter(mContext, Favlistarraylist);
                            FavouriteActivity.layoutManager = new LinearLayoutManager(mContext);
                            FavouriteActivity.recyclerviewfavourite.setLayoutManager(FavouriteFragment.layoutManager);
                            FavouriteActivity.recyclerviewfavourite.setAdapter(favouriteAdapter);
                            FavouriteActivity.recyclerviewfavourite.setItemAnimator(new DefaultItemAnimator());


                        }


                    } else {


                        if (GlobalValues.FavouriteCheck.equalsIgnoreCase("1")) {

                            FavouriteFragment.recyclerviewfavourite.setVisibility(View.GONE);
                            FavouriteFragment.txtEmpty.setVisibility(View.VISIBLE);
                        } else {
                            FavouriteActivity.recyclerviewfavourite.setVisibility(View.GONE);
                            FavouriteActivity.txtEmpty.setVisibility(View.VISIBLE);

                        }


                    }


                } else {


                    if (GlobalValues.FavouriteCheck.equalsIgnoreCase("1")) {

                        FavouriteFragment.recyclerviewfavourite.setVisibility(View.GONE);
                        FavouriteFragment.txtEmpty.setVisibility(View.VISIBLE);
                    } else {
                        FavouriteActivity.recyclerviewfavourite.setVisibility(View.GONE);
                        FavouriteActivity.txtEmpty.setVisibility(View.VISIBLE);

                    }

                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();

                if (GlobalValues.FavouriteCheck.equalsIgnoreCase("1")) {

                    FavouriteFragment.recyclerviewfavourite.setVisibility(View.GONE);
                    FavouriteFragment.txtEmpty.setVisibility(View.VISIBLE);
                } else {
                    FavouriteActivity.recyclerviewfavourite.setVisibility(View.GONE);
                    FavouriteActivity.txtEmpty.setVisibility(View.VISIBLE);

                }

                progressDialog.dismiss();

            }

        }
    }


    private class FavouriteValdiationTask extends AsyncTask<String, Void, String> {

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

                if (jsonObject.optString("status").equalsIgnoreCase("ok")) {


                    Favouriteitems favouriteitems = favouriteitemsArrayList.get(CurrentPostion);


                    if (favouriteitems.getProduct_type().equalsIgnoreCase("1")) {

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

                            params.put("product_id", favouriteitems.getProduct_id());
                            params.put("product_type", favouriteitems.getProduct_type());
                            params.put("app_id", GlobalValues.APP_ID);
                            params.put("reference_id", mReferenceId);
                            params.put("customer_id", mCustomerId);
                            params.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                            params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                            params.put("product_qty", "1");


                            SimpleProductDialog(favouriteitemsArrayList.get(CurrentPostion));

                            //  new AddCartTask(params, holder, holder.txtQuantity.getText().toString()).execute(url);

                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }

                    } else if (favouriteitems.getProduct_type().equalsIgnoreCase("2")) {
                        if (Utility.networkCheck(mContext)) {
                           /* String url = GlobalUrl.PRODUCT_DETAILS_URL + "?appId=" + GlobalValues.APP_ID +
                                    "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                    "&outletId=" + GlobalValues.CURRENT_OUTLET_ID +
                                    "&productId=" +favouriteitems.getProduct_id() +
                                    "&productType=" + favouriteitems.getProduct_type();*/
                            SetMenuChildRecyclerAdapter.childPlusMinus = 0.00;
                            subModifierPrice = 0.00;
                            value = 0.00;
                            String url = GlobalUrl.SETMENU_COMPENENT_URL + "?app_id=" + GlobalValues.APP_ID +
                                    "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                    "&product_id=" + favouriteitems.getProduct_id();


                            mProductId = favouriteitems.getProduct_id();
                            new SetMenuProductDetailsTask(favouriteitems.getProduct_id(), favouriteitems.getProduct_primary_id()).execute(url);
                            //  new ModifierProductDetailsTask(favouriteitems.getProduct_id()).execute(url);

                        } else {
                            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                        }


                    }


                } else {


                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();

                // FavouriteFragment.recyclerviewfavourite.setVisibility(View.GONE);
                // FavouriteFragment.txtEmpty.setVisibility(View.VISIBLE);

                progressDialog.dismiss();

            }

        }
    }

    private void SimpleProductDialog(final Favouriteitems products) {

        mModifierPrice = 0.0;
        quantityCost = 1.0;

        dialog = new Dialog(mContext, R.style.custom_dialog_theme);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_search_product_details_simple);
        dialog.show();
        Toolbar toolBar = dialog.findViewById(R.id.toolBar);
        ImageView imgBack = toolBar.findViewById(R.id.img_back);
        favouriteLayout = dialog.findViewById(R.id.favouriteLayout);
        favouriteText = dialog.findViewById(R.id.favouriteText);
        ImageView imgProduct = (ImageView) dialog.findViewById(R.id.imgProduct);
        final TextView txtDone = (TextView) dialog.findViewById(R.id.txtDone);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        final TextView txtPrice = (TextView) dialog.findViewById(R.id.txtPrice);
        ImageView layoutClose = (ImageView) dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);


        TextView completeTxt = (TextView) dialog.findViewById(R.id.completeTxt);


        final EditText productInstructionsEditText = (EditText) dialog.findViewById(R.id.productInstructionsEditText);

        //TODO
        // RelativeLayout layoutBottom =(RelativeLayout)dialog.findViewById(R.id.layoutBottom);

        //layoutBottom.setVisibility(View.GONE);

        // completeTxt.setText(ProductListActivity.mCategoryName);
        txtProductName.setText(products.getProduct_alias());
        txtProductDesc.setText(products.getProduct_long_description());
        txtQuantity.setText("1");

        mProductQuantity = txtQuantity.getText().toString();


        if (products.getProduct_galleryImage() != null && products.getProduct_galleryImage().length() > 0) {

            Picasso.with(mContext).load(products.getProduct_galleryImage()).error(R.drawable.place_holder_sushi_tei).
                    into(imgProduct);

        } else {

            Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);

        }

        if (products.getFav_product_primary_id().equalsIgnoreCase("null")) {

            favouriteText.setText("Add to favourite");
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

            StatusFav = "1";

        } else {
            favouriteText.setText("Remove from favourite");
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

            StatusFav = "0";


        }
        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavouriteMethod(products.getFav_product_primary_id());
            }
        });


        final double price = Double.valueOf(products.getProduct_price());


        try {

            quantityCost = Double.valueOf(products.getProduct_price());


        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(price)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);
        String css = String.format("%.2f", new BigDecimal(price));


        txtPrice.setText(css);


        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int count = Integer.parseInt(txtQuantity.getText().toString());


                if (count <= 1000) {

                    count++;


                    quantityCost += price;


                    txtQuantity.setText(String.valueOf(count));

                }
                SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(quantityCost)));

                cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                String css = String.format("%.2f", new BigDecimal(quantityCost));


                txtPrice.setText(css);


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
                    String css = String.format("%.2f", new BigDecimal(quantityCost));


                    txtPrice.setText(css);


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

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        try {
            if (databaseHandler.getAllTotalData(mProductId) != null) {
/*
                layoutMaxCount.setVisibility(View.VISIBLE);
                Cart cart = databaseHandler.getAllTotalData(mProductId);
                txtCurrentCartQuantity.setText("X" + cart.getmProductQty());*/

            } else {}
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

                //  openBeverageOptionDialog();

                dialog.dismiss();

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

                    params.put("product_id", products.getProduct_id());
                    params.put("product_type", products.getProduct_type());
                    params.put("app_id", GlobalValues.APP_ID);
                    //params.put("reference_id", mReferenceId);
                    params.put("customer_id", mCustomerId);
                    params.put("availability_name", GlobalValues.CURRENT_AVAILABLITY_NAME);
                    params.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                    params.put("product_qty", txtQuantity.getText().toString());
                    params.put("product_name", products.getProduct_name());
                    params.put("product_total_price", String.valueOf(quantityCost));
                    params.put("product_unit_price", products.getProduct_price());
                    params.put("price_with_modifier", "0.00");
                    params.put("product_sku", products.getProduct_sku());
                    params.put("product_image", "");
                    params.put("product_remarks", productInstructionsEditText.getText().toString());


                    params.put("allow_cart", "yes");
                    params.put("cart_source", "Mobile");

                    params.put("product_qty", txtQuantity.getText().toString());
                    new AddCartTask(params, txtQuantity.getText().toString()).execute(url);


                } else {
                    Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                }

            }
        });
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

        private String productPrimaryId = "";

        public SetMenuProductDetailsTask(String id, String productPrimaryIds) {

            mProductId = id;
            productPrimaryId = productPrimaryIds;
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
                            setMenuProduct.setmProductgalleryImage(galleryBasePath + "/" + jsonResult.getString("product_thumbnail"));
                            setMenuProduct.setmProductStatus(jsonResult.getString("product_status"));
                            setMenuProduct.setmProductPrice(jsonResult.getString("product_price"));
                            setMenuProduct.setmApplyMinMaxSelect(Integer.parseInt(jsonResult.getString("product_apply_minmax_select")));

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
                                                    setMenuModifier.setmModifierName(object.optString("product_alias"));
                                                    setMenuModifier.setsub_modifier_apply(jsonSetmenu.optString("menu_component_modifier_apply"));
                                                    setMenuModifier.setsub_multipleselection_apply(jsonSetmenu.optString("menu_component_multipleselection_apply"));
                                                    setMenuModifier.setApplyPrice(jsonSetmenu.optString("menu_component_apply_price"));
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
                        setMenuproductDetailsDialog(mContext, mProductId, productPrimaryId, mQuantity);

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

    private void setMenuproductDetailsDialog(final Context mContext, final String mProductId, final String mProductPrimaryId, String quantity) {

        mProductQuantity = quantity;
        mSetMenuQuantity = 1;
        final SpannableString cs;
        mProductFavPrimaryId = mProductPrimaryId;
        //SetMenuTitleRecyclerFavouriteAdapter setMenuTitleRecyclerAdapter;
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
        final TextView txtDone = (TextView) dialog.findViewById(R.id.Addtocart);
        TextView txtProductName = (TextView) dialog.findViewById(R.id.txtProductName);
        TextView txtProductDesc = (TextView) dialog.findViewById(R.id.txtProductDesc);
        LinearLayout lly_addToCart = dialog.findViewById(R.id.lly_addToCart);
        txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

        txtModi = (TextView) dialog.findViewById(R.id.txtPrice);
        TextView textHead = dialog.findViewById(R.id.textHead);
        //textHead.setText(subCatString.toUpperCase());
        RecyclerView setmenuRecyclerView = (RecyclerView) dialog.findViewById(R.id.modifierRecyclerView);
        RecyclerView addonsRecycerView = (RecyclerView) dialog.findViewById(R.id.addonsRecycerView);
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

        if (mProductFavPrimaryId.equalsIgnoreCase("null")) {
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));

            favouriteText.setText("Add to favourite");


            StatusFav = "1";

        } else {
            favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
            favouriteText.setText("Remove from favourite");

            StatusFav = "0";


        }

        favouriteLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFavouriteMethod(mProductPrimaryId);
            }
        });

        mProductQuantity = txtQuantity.getText().toString();

        try {

            mSearchProuductprise = Double.valueOf(setMenuProduct.getmProductPrice());
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

            if (setMenuProduct.getSetMenuTitleList() != null &&
                    setMenuProduct.getSetMenuTitleList().size() > 0) {

               /* if (setMenuProduct.getmApplyMinMaxSelect() == 1) {

                    if (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0")) {

                        if (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0")) {

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
                        } else {

                            txtModifierPrice = (TextView) dialog.findViewById(R.id.txtPrice);

                            cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                            cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal(mSearchProuductprise)));
                            setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerFavouriteAdapter(mContext,
                                    setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                            setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                            setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                            setmenuRecyclerView.setNestedScrollingEnabled(false);
                            setmenuRecyclerView.setHasFixedSize(true);
                        }
                    } else {
                        txtModifierPrice =  dialog.findViewById(R.id.txtPrice);

                        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                        txtModifierPrice.setText(String.format("%.2f", new BigDecimal(mSearchProuductprise)));
                        setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerFavouriteAdapter(mContext,
                                setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                        setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                        setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                        setmenuRecyclerView.setNestedScrollingEnabled(false);
                        setmenuRecyclerView.setHasFixedSize(true);
                    }


                } else {*/


                    txtModifierPrice = dialog.findViewById(R.id.txtPrice);

                    cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
                    cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

                    txtModifierPrice.setText(String.format("%.2f", new BigDecimal(mSearchProuductprise)));
                    setMenuTitleRecyclerAdapter = new SetMenuTitleRecyclerAdapter(mContext,
                            setMenuProduct.getSetMenuTitleList(), setMenuProduct.getmApplyMinMaxSelect(), "create");
                    setmenuRecyclerView.setAdapter(setMenuTitleRecyclerAdapter);
                    setmenuRecyclerView.setItemAnimator(new DefaultItemAnimator());
                    setmenuRecyclerView.setNestedScrollingEnabled(false);
                    setmenuRecyclerView.setHasFixedSize(true);
                //}

            }
        }


        if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0) {

            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.place_holder_sushi_tei).into(imgProduct);

        } else {

            if (modifierProduct.getmProductImage() != null && modifierProduct.getmProductImage().length() > 0) {

                Picasso.with(mContext).load(mBasePath + modifierProduct.getmProductImage()).into(imgProduct);

            } else {

                Picasso.with(mContext).load(R.drawable.place_holder_sushi_tei).into(imgProduct);

            }
        }

        imgIncreement.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

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
                        }
                   *//* } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        count++;
                        mSetMenuQuantity = count;
                        quantityCost = mSetMenuPrice * mSetMenuQuantity;

                        txtQuantity.setText(String.valueOf(count));
                        txtModifierPrice.setText(String.format("%.2f", new BigDecimal( quantityCost)));

                        mProductQuantity = txtQuantity.getText().toString();

                        mProductQuantity = txtQuantity.getText().toString();
                    }


                } else {



                    int count = Integer.parseInt(txtQuantity.getText().toString());

                    count++;
                    mSetMenuQuantity = count;
                    quantityCost = mSetMenuPrice * mSetMenuQuantity;

                    txtQuantity.setText(String.valueOf(count));

                    txtModifierPrice.setText(String.format("%.2f", new BigDecimal( quantityCost)));

                    mProductQuantity = txtQuantity.getText().toString();




                }*//*
*/
                //27-1-2021
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

                /*if ((setMenuProduct.getmApplyMinMaxSelect() == 1) && (GlobalValues.MODIFIERAPPLY.equalsIgnoreCase("0"))
                        && (GlobalValues.MULTIPLESLECTIONAPPLY.equalsIgnoreCase("0"))) {

                            int count = Integer.parseInt(txtQuantity.getText().toString());

                            if (count > 1) {
                                count--;

                                txtQuantity.setText(count + "");

                                try {
                                    txtModifierPrice.setText(String.format("%.2f", new BigDecimal( getsetMenuProductPrice() * Integer.valueOf(txtQuantity.getText().toString()))));

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
                        }

                   *//* } else {
                        int count = Integer.parseInt(txtQuantity.getText().toString());

                        if (count > 1) {
                            count--;
                            mSetMenuQuantity = count;
                            quantityCost = mSetMenuPrice * mSetMenuQuantity;

                            txtQuantity.setText(count + "");

                            txtModifierPrice.setText(String.format("%.2f", new BigDecimal( quantityCost)));

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

                        txtModifierPrice.setText(String.format("%.2f", new BigDecimal( quantityCost)));


                        mProductQuantity = txtQuantity.getText().toString();

                    }

                }*//*
*/
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
                        double subModiPrice = value * count  ;
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
                validateSetMenu(setMenuProduct.getSetMenuTitleList());
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
                    } if (!isChildSelected) {

                        mValidationMessage = setMenuProduct.getSetMenuTitleList().get(j).getmTitleMenuName();
                        break;
                    }
                }
            } else if (setMenuProduct.getmApplyMinMaxSelect() == 1) {
                List<ModifiersValue> modifiersValue = new ArrayList<>();
                for (int i = 0; i < setMenuProduct.getSetMenuTitleList().size(); i++) {
                     if (setMenuProduct.getSetMenuTitleList().get(i).gettQuantity() < setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect()) {
                         /*Toast.makeText(mContext, "Please select minimum  " + setMenuProduct.getSetMenuTitleList().get(i).getmMinSelect() + " product for "
                                +  setMenuProduct.getSetMenuTitleList().get(i).getmTitleMenuName() , Toast.LENGTH_SHORT).show();*/
                        if(setMenuTitleList.get(i).getmTitleMenuName().equalsIgnoreCase("Sugar Level")){
                            Toast.makeText(mContext, "Please select sugar level", Toast.LENGTH_SHORT).show();
                        }else{
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

                        if(setMenuTitleList.get(j).getSetMenuModifierList().size() == 1){
                            postion = 0;
                        }

                        for (int k = 0; k < setMenuTitleList.get(j).getSetMenuModifierList().size(); k++) {
                            modifierHeadings = setMenuTitleList.get(j).getSetMenuModifierList().get(postion).getModifierHeadingList();


                            if (modifierHeadings != null && modifierHeadings.size() > 0) {
                                for (int a = 0; a < modifierHeadings.size(); a++) {
                                    modifiersValue = modifierHeadings.get(a).getModifiersValueList();
                                    for (int b = 0; b < modifiersValue.size(); b++) {

                                        if (modifierHeadings.get(a).gettQuantity() < modifierHeadings.get(a).getmModifierMinSelect()) {
                                             /*Toast.makeText(mContext, "Please select  minimum  " + modifierHeadings.get(a).getmModifierMinSelect() + " product for " +
                                                    modifierHeadings.get(a).getmModifierHeading(), Toast.LENGTH_SHORT).show();*/
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
                    mapData.put("product_unit_price", String.valueOf(product_unit_price));
                    Log.e("pridfjd", String.valueOf(product_unit_price));
                    mapData.put("availability_id", GlobalValues.TAKEAWAY_ID);
                    mapData.put("customer_id", mCustomerId);
                    mapData.put("price_with_modifier", String.valueOf(mquanititycost_src));
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
                        //mapData.put("product_total_price", String.valueOf(quantityCost));
                        mapData.put("product_total_price", String.valueOf(total));
                        mapData.put("product_unit_price", String.valueOf(product_unit_price));


                    }





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
                        mapData.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        mapData.put("product_qty", mProductQuantity);
                        mapData.put("product_id", setMenuProduct.getmProductId());
                        mapData.put("product_name", setMenuProduct.getmProductAliasName());
                        mapData.put("menu_set_component", constructSetMenuJson().toString());
                        mapData.put("allow_cart", "yes");
                        mapData.put("cart_source", "Mobile");

                        double total = product_unit_price * Integer.parseInt(mProductQuantity);
                        //mapData.put("product_total_price", txtModifierPrice.getText().toString().replace("$", ""));
                        mapData.put("product_total_price", String.valueOf(total));

                        try {



                            mapData.put("product_unit_price", String.valueOf(product_unit_price));


                        } catch (Exception e) {
                            e.printStackTrace();
                        }


                        mapData.put("product_image", setMenuProduct.getmProductImage());
                        mapData.put("price_with_modifier", String.valueOf(mquanititycost_src));
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
            mapData.put("availability_id", GlobalValues.TAKEAWAY_ID);
            mapData.put("product_qty", mProductQuantity);
            mapData.put("product_id", setMenuProduct.getmProductId());
            mapData.put("product_name", setMenuProduct.getmProductAliasName());
            mapData.put("allow_cart", "yes");
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
            //mapData.put("product_total_price", String.valueOf(quantityCost));
            double total = product_unit_price * Integer.parseInt(mProductQuantity);
            mapData.put("product_total_price", String.valueOf(total));

            mapData.put("product_unit_price", String.valueOf(product_unit_price));


            // }

            mapData.put("product_unit_price", String.valueOf(product_unit_price));
            mapData.put("product_image", setMenuProduct.getmProductImage());
            mapData.put("price_with_modifier", String.valueOf(mquanititycost_src));
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

    private String constructSetMenuJson(){
        //mainObj
        JSONArray menuComponentsJSONArray = new JSONArray();
        List<SetMenuTitle> titleList = setMenuProduct.getSetMenuTitleList();
        for(int i=0; i<titleList.size(); i++){

            if(setMenuProduct.getmApplyMinMaxSelect() == 1){
                if(titleList.get(i).getmenu_component_modifier_apply().equalsIgnoreCase("0")){
                    if(titleList.get(i).getmultipleselection_apply().equalsIgnoreCase("0")){
                        //modifierName
                        JSONObject jsonObject = new JSONObject();
                        try{
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
                        }catch (JSONException je){
                            je.printStackTrace();
                        }
                    }
                    else{
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

                                                        if(modifierValue.getmSubModifierTotal() > 0){
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
                                        }else {

                                        }

                                    }else {

                                    }
                                    productJSONArray.put(productJSONObj);
                                }else {

                                }
                            }
                            jsonObject.put("product_details", productJSONArray);
                            menuComponentsJSONArray.put(jsonObject);
                        }catch (JSONException je){
                            je.printStackTrace();
                        }
                    }
                }
                else{
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
                                                    if(modifierValue.getmSubModifierTotal() > 0){
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
                                    }else {

                                    }

                                }else {

                                }
                                productJSONArray.put(productJSONObj);
                            }else {

                            }
                        }
                        jsonObject.put("product_details", productJSONArray);
                        menuComponentsJSONArray.put(jsonObject);
                    }catch (JSONException je){
                        je.printStackTrace();
                    }
                }
            }
            else{
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
                                                if(modifierValue.getmSubModifierTotal() > 0){
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
                                }else {

                                }

                            }else {
                            }
                            productJSONArray.put(productJSONObj);
                        }else {

                        }
                    }
                    jsonObject.put("product_details", productJSONArray);
                    menuComponentsJSONArray.put(jsonObject);
                }catch (JSONException je){
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

    private class ModifierProductDetailsTask extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;
        private List<ModifiersValue> modifiersValueList;

        private String mProductId = "", mQuantity = "1";

        public ModifierProductDetailsTask(String id) {
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
                            modifierProduct.setmProduct_alias(jsonResult.getString("product_alias"));
                            modifierProduct.setmProductPrimaryId(jsonResult.getString("product_primary_id"));


                            JSONArray jsonModifiersArray = jsonResult.getJSONArray("modifiers");
                            List<ModifierHeading> modifierHeadingList = new ArrayList<>();


                            if (jsonModifiersArray != null) {


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


                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();


                    databaseHandler.insertProductData(favouriteitemsArrayList.get(CurrentPostion).getProduct_id(), mQuantity);


                    JSONObject jsonContent = jsonObject.getJSONObject("contents");

                    JSONObject jsonCartDetails = jsonContent.getJSONObject("cart_details");

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_COUNT,
                            jsonCartDetails.getString("cart_total_items"));

                    Utility.writeToSharedPreference(mContext, GlobalValues.CART_RESPONSE, jsonObject.getJSONObject("contents").toString());



                    ((FavouriteActivity) mContext).invalidateOptionsMenu();
                    if (GlobalValues.FavouriteCheck.equalsIgnoreCase("1")) {
                        ((FiveMenuActivityNew) mContext).invalidateOptionsMenu();


                    } else {

                        ((FavouriteActivity) mContext).invalidateOptionsMenu();


                    }

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


    private void modifierproductDetailsDialog(final Context mContext
            , String mProductId, String quantity) {

        this.mProductQuantity = quantity;
        mModifierPrice = 0.0;
        quantityCost = 0.0;
        mModifierQuantity = 1;

        ModifierHeadingRecyclerAdapterFav modifierHeadingRecyclerAdapter;

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
        ImageView layoutClose = dialog.findViewById(R.id.layoutClose);
        ImageView imgDecreement = (ImageView) dialog.findViewById(R.id.imgDecreement);
        ImageView imgIncreement = (ImageView) dialog.findViewById(R.id.imgIncreement);
        final TextView txtQuantity = (TextView) dialog.findViewById(R.id.txtQuantity);
        RelativeLayout layoutMaxCount = (RelativeLayout) dialog.findViewById(R.id.layoutMaxCount);
        TextView txtCurrentCartQuantity = (TextView) dialog.findViewById(R.id.txtCurrentCartQuantity);

        TextView completeTxt = (TextView) dialog.findViewById(R.id.completeTxt);

        RelativeLayout layoutBottom = (RelativeLayout) dialog.findViewById(R.id.layoutBottom);

        layoutBottom.setVisibility(View.GONE);


//TODO
        //completeTxt.setText(ProductListActivity.mCategoryName);


        RecyclerView.LayoutManager addonslayoutManager = new LinearLayoutManager(mContext);
        RecyclerView.LayoutManager modifierlayoutManager = new LinearLayoutManager(mContext);

        modifierRecyclerView.setLayoutManager(modifierlayoutManager);
        addonsRecycerView.setLayoutManager(addonslayoutManager);

        AddOnsRecyclerAdapter addOnsRecyclerAdapter = new AddOnsRecyclerAdapter(mContext);
        addonsRecycerView.setAdapter(addOnsRecyclerAdapter);
        addonsRecycerView.setItemAnimator(new DefaultItemAnimator());
        addonsRecycerView.setNestedScrollingEnabled(false);

        txtProductName.setText(modifierProduct.getmProduct_alias());
        txtProductDesc.setText(modifierProduct.getmProductDesc());

        mProductQuantity = txtQuantity.getText().toString();


        if (Integer.parseInt(modifierProduct.getmProductType()) == 2) {

            if (modifierProduct.getModifiersList().size() > 0) {


                modifierHeadingRecyclerAdapter = new ModifierHeadingRecyclerAdapterFav(mContext, modifierProduct.getModifiersList(),
                        this.mProductId);
                modifierRecyclerView.setAdapter(modifierHeadingRecyclerAdapter);
                modifierRecyclerView.setItemAnimator(new DefaultItemAnimator());
                modifierRecyclerView.setNestedScrollingEnabled(false);

                modifierHeadingRecyclerAdapter.checkAllModifiersSelected();

            }
        } else {

        }

        if (setMenuProduct.getmProductLargeImage() != null && setMenuProduct.getmProductLargeImage().length() > 0) {

            Picasso.with(mContext).load(setMenuProduct.getmProductLargeImage()).error(R.drawable.default_image).into(imgProduct);

        } else {

            if (modifierProduct.getmProductImage() != null && modifierProduct.getmProductImage().length() > 0) {

                Picasso.with(mContext).load(mBasePath + modifierProduct.getmProductImage()).into(imgProduct);

            } else {

                Picasso.with(mContext).load(R.drawable.default_image).into(imgProduct);

            }
        }
        mModifierPrice = Double.parseDouble(modifierProduct.getmProductPrice());

        SpannableString cs = new SpannableString("$" + String.format("%.2f", new BigDecimal(mModifierPrice)));

        cs.setSpan(new SuperscriptSpan(), 0, 1, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        cs.setSpan(new RelativeSizeSpan(.7f), 0, 1, 0);

        String css = String.format("%.2f", new BigDecimal(mModifierPrice));


        txtModifierPrice.setText(css);

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

                String css = String.format("%.2f", new BigDecimal(quantityCost));


                txtModifierPrice.setText(css);

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

                    String css = String.format("%.2f", new BigDecimal(quantityCost));


                    txtModifierPrice.setText(css);

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


       /* if(productsList.equalsIgnoreCase("null"))
        {

            text_linefav.setText("Add to favourite");



            StatusFav="1";

        }
        else
        {
            text_linefav.setText("Remove from favourite");



            StatusFav="0";



        }*/


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
                            mapData.put("price_with_modifier", String.valueOf(mquanititycost_src));
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

        if (favouriteText.getText().toString().equalsIgnoreCase("Remove from favourite")) {
            String url = GlobalUrl.FavouriteURl;

            Map<String, String> param = new HashMap<String, String>();

            param.put("app_id", GlobalValues.APP_ID);
            param.put("customer_id", mCustomerId);
            param.put("product_id", productId);
            param.put("fav_flag", "No");
            param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
            param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);




            new FavouritesAddTask(param, productId).execute(url);

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


            new FavouritesAddTask(param, productId).execute(url);

            StatusFav = "1";

        }

    }

    private class FavouritesAddTask extends AsyncTask<String, Void, String> {

        private Map<String, String> resetParams;
        private ProgressDialog progressDialog;
        private String productIds;

        public FavouritesAddTask(Map<String, String> param, String productId) {
            resetParams = param;
            productIds = productId;

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
                        favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                        favouriteText.setText("Remove from favourite");
                        Toast.makeText(mContext, "Added successfully", Toast.LENGTH_SHORT).show();


                    } else {
                        favouriteLayout.setBackground(ContextCompat.getDrawable(mContext, R.drawable.add_favourite_background));
                        favouriteText.setText("Add to favourite");

                        Toast.makeText(mContext, "Removed successfully", Toast.LENGTH_SHORT).show();

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


                        String url = GlobalUrl.FavouriteURl;

                        Map<String, String> param = new HashMap<String, String>();

                        param.put("app_id", GlobalValues.APP_ID);
                        param.put("customer_id", mCustomerId);
                        param.put("product_id", productIds);
                        param.put("fav_flag", "No");
                        param.put("availability_id", GlobalValues.CURRENT_AVAILABLITY_ID);
                        param.put("outlet_id", GlobalValues.CURRENT_OUTLET_ID);




                        new FavouritesRemove(param).execute(url);


                    }
                    notifyAdapter();

                }

                progressDialog.dismiss();


            } catch (Exception e) {
                e.printStackTrace();
                progressDialog.dismiss();

            }


        }
    }


}
