package com.app.sushi.tei.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.favourite.Favouriteitems;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.favouriteAdapter.FavouriteAdapter;
import com.app.sushi.tei.dialog.CheckOutMessageDialog;
import com.app.sushi.tei.fragment.FavouriteFragment;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;


public class FavouriteActivity extends AppCompatActivity
{
    private Toolbar toolbar;
    public static TextView ic_heart_blink,txtEmpty;
    private ImageView imgLogo,txtTitle,heartblink_imageview;
    private LinearLayout imgBack;
    private Context mContext;

    private String mCustomerId = "", mReferenceId = "", mProductId = "", mProductQuantity = "1";

    public static RelativeLayout layoutCart,layoutSearch;
    private TextView txtCartCount;
    private String cartCount = "";
    public static boolean isInvalidated = false;

    public static RecyclerView recyclerviewfavourite;

    private ArrayList<Favouriteitems> favouriteitemsArrayList = new ArrayList<>();
    public static RecyclerView.LayoutManager layoutManager;


    private FavouriteFragment favouriteFragment;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);

        InitView();




    }

    private void InitView()
    {

        mContext = FavouriteActivity.this;
        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext,GlobalValues.AVALABILITY_ID);

        favouriteFragment = new FavouriteFragment();

        toolbar = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(toolbar);
        GlobalValues.FavouriteCheck="2";

        imgBack =  toolbar.findViewById(R.id.toolbarBack);
        heartblink_imageview=(ImageView)toolbar.findViewById(R.id.heartblink_imageview);

        heartblink_imageview.setVisibility(View.GONE);
        txtTitle =  toolbar.findViewById(R.id.toolbartxtTitle);

        recyclerviewfavourite=(RecyclerView)findViewById(R.id.favouritelistRecyclerView);
        txtEmpty=(TextView)findViewById(R.id.txtEmpty);
        layoutManager = new LinearLayoutManager(mContext);
        recyclerviewfavourite.setLayoutManager(layoutManager);

        if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

            mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
            mReferenceId = "";

        }
        else
        {
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


        if (Utility.networkCheck(mContext))
        {


            String app_id="?app_id="+ GlobalValues.APP_ID +"&customer_id="+mCustomerId;

            String availability_id="&availability_id="+ Utility.readFromSharedPreference(mContext,GlobalValues.AVALABILITY_ID);

            String outlet_id="&outlet_id="+ GlobalValues.CURRENT_OUTLET_ID;


            String Url = GlobalUrl.FavouriteListURL+app_id+availability_id+outlet_id;


            new FavouriteListTask().execute(Url);

        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }



        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                finish();
            }
        });


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

                String basePath = jsonObject.getJSONObject("common").optString("product_image_source");
                String galleryPath = jsonObject.getJSONObject("common").optString("product_gallery_image_source");

                if(jsonObject.optString("status").equalsIgnoreCase("ok"))
                {

                    JSONArray jsonArray = jsonObject.getJSONArray("result_set");


                    for(int i=0;i<jsonArray.length();i++)
                    {

                        JSONObject jsonObjectfav = jsonArray.getJSONObject(i);

                        Favouriteitems favouriteitems = new Favouriteitems();


                        favouriteitems.setProduct_primary_id(jsonObjectfav.optString("product_primary_id"));
                        favouriteitems.setProduct_id(jsonObjectfav.optString("product_id"));
                        favouriteitems.setProduct_name(jsonObjectfav.optString("product_name"));
                        favouriteitems.setProduct_alias(jsonObjectfav.optString("product_alias"));
                        favouriteitems.setProduct_sku(jsonObjectfav.optString("product_sku"));
                        favouriteitems.setProduct_sequence(jsonObjectfav.optString("product_sequence"));
                        //favouriteitems.setProduct_thumbnail(jsonObjectfav.optString("product_thumbnail"));
                        String subImageUrl=jsonObjectfav.optString("product_thumbnail");
                        if(subImageUrl!=null && subImageUrl.length()>0) {
                            favouriteitems.setProduct_thumbnail(basePath + "/" + jsonObjectfav.optString("product_thumbnail"));
                            favouriteitems.setProduct_gallery_thumbanil(galleryPath + "/" + jsonObjectfav.optString("product_thumbnail"));
                        }

                        String galleryImageUrl="";

                        JSONArray image_gallery = jsonObjectfav.getJSONArray("image_gallery");
                        for (int l=0;l<image_gallery.length();l++){
                            JSONObject jsonObject1=image_gallery.getJSONObject(l);
                            galleryImageUrl=jsonObject1.getString("pro_gallery_image");

                        }
                        //products.setmSubCategoryGalleryImage(galleryBasePath + "/" + jsonProduct.optString("product_thumbnail"));

                        if(galleryImageUrl!=null && galleryImageUrl.length()>0) {
                            favouriteitems.setProduct_galleryImage(galleryPath + "/" + galleryImageUrl);
                        }

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



                    if(favouriteitemsArrayList.size()>0)
                    {
                        recyclerviewfavourite.setVisibility(View.VISIBLE);
                        txtEmpty.setVisibility(View.GONE);

                        FavouriteAdapter favouriteAdapter = new FavouriteAdapter(mContext, favouriteitemsArrayList);
                        recyclerviewfavourite.setAdapter(favouriteAdapter);
                        recyclerviewfavourite.setItemAnimator(new DefaultItemAnimator());
                    }
                    else
                    {
                        recyclerviewfavourite.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                    }




                }
                else
                {

                    recyclerviewfavourite.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.VISIBLE);


                }

                progressDialog.dismiss();






            } catch (Exception e) {
                e.printStackTrace();

                recyclerviewfavourite.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
                progressDialog.dismiss();

            }

        }
    }

    public void reloadData(){
        invalidateOptionsMenu();
    }


    @Override
    protected void onResume()
    {
        super.onResume();

        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
        }
     //   txtTitle.setVisibility(View.GONE);
        isInvalidated = false;

        supportInvalidateOptionsMenu();



    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {


        try {
            MenuItem cartWBadge = menu.findItem(R.id.menu_cart);
            MenuItem menuSearch = menu.findItem(R.id.menu_search);
            menuSearch.setVisible(false);

            View viewBadge = menu.findItem(R.id.menu_cart).getActionView();
            View searchwBadge = menu.findItem(R.id.menu_search).getActionView();

            layoutCart = (RelativeLayout) viewBadge.findViewById(R.id.layoutCart);

            txtCartCount = (TextView) viewBadge.findViewById(R.id.txtCartCount);

            cartCount = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);

            if (!cartCount.equalsIgnoreCase("") && !cartCount.isEmpty()) {


                if(cartCount.equalsIgnoreCase("0"))
                {

                    txtCartCount.setVisibility(View.GONE);


                }
                else
                {
                    txtCartCount.setVisibility(View.VISIBLE);
                    txtCartCount.setText(cartCount);


                }

                if (isInvalidated)
                {
             /*       Tooltip.make((mContext),
                            new Tooltip.Builder(101)
                                    .anchor(layoutCart, Tooltip.Gravity.BOTTOM)
                                    .closePolicy(new Tooltip.ClosePolicy()
                                            .insidePolicy(true, false)
                                            .outsidePolicy(true, false), 300000)
                                    .activateDelay(100)
                                    .showDelay(200)
                                    .text("great! product added to the cart")
                                    .withArrow(true)
                                    .withOverlay(false)
                                    .typeface(BebasNeueBoldTextView.BebasNeueBoldText)
                                    .build()
                    ).show();*/
                }

            } else {

                txtCartCount.setVisibility(View.GONE);
            }


            layoutSearch = (RelativeLayout) searchwBadge.findViewById(R.id.layoutSearch);

            layoutSearch.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   /* if (GlobalValues.CURRENT_AVAILABLITY_ID.length() <= 0 || GlobalValues.DELIVERY_DATE.length() <= 0 ||
                            GlobalValues.CURRENT_OUTLET_ID.length() <= 0) {

                        selectedCardIndex(swipedIndex);

                    } else {

                    }
*/

//ToDo
                   /* Intent intent = new Intent(mContext, SearchActivity.class);
                    startActivity(intent);*/
                }
            });

            layoutCart.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String MinQual = Utility.readFromSharedPreference(mContext, GlobalValues.CART_COUNT);
                    if (!MinQual.equals("")) {
                        if (Integer.parseInt(MinQual) >= 1) {
                            String message = "Do you want to add more items?";
                            new CheckOutMessageDialog(mContext, message, new CheckOutMessageDialog.OnSlectedMethod() {
                                @Override
                                public void selectedAction(String action) {

                                    if (action.equalsIgnoreCase("YES")) {

                                    } else {
//                                        Intent intent = new Intent(mContext, CartActivity.class);
                                        Intent intent = new Intent(mContext, OrderSummaryActivity.class);
                                        startActivity(intent);
                                    }
                                }
                            });
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
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {

        getMenuInflater().inflate(R.menu.menu_home, menu);

        return true;
    }


    public void RefreshRemoved()
    {

        Intent i = new Intent(FavouriteActivity.this, FavouriteActivity.class);
        startActivity(i);
        finish();

    }

    public void reloadTotalAmount() {
        getTotalAmount();
    }


    public void getTotalAmount() {
        if (Utility.networkCheck(mContext)) {
            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);
                mReferenceId = "";

            } else {
                mCustomerId = "";
                mReferenceId = Utility.getReferenceId(mContext);
            }


            GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);

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

            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
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

                    JSONObject jsonResult = jsonObject.getJSONObject("result_set");

                    JSONObject jsoncartDetails = jsonResult.getJSONObject("cart_details");


                    //if (GlobalValues.CURRENT_AVAILABLITY_ID.equalsIgnoreCase(GlobalValues.TAKEAWAY_ID)) {
                    Double mGrandTotal = Double.parseDouble(jsoncartDetails.getString("cart_sub_total"));

                    //totalAmt.setText(String.format("%.2f", mGrandTotal));


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
    protected void onPause() {
        super.onPause();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","0");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
        }
    }


}
