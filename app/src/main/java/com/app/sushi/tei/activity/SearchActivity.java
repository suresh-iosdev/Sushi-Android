package com.app.sushi.tei.activity;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Search.SearchProduct;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Search.SearchAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends AppCompatActivity {
    private Context mContext;
    private RecyclerView recyclerViewSearch;
    private SearchAdapter searchAdapter;
    private EditText edtSearch;
    private TextView txtEmpty;
    private ImageView toolbarBack, imgSearch;
    private RelativeLayout layoutProgress;

    private List<SearchProduct> searchProductList;
    private String mBasePath = "";
    private String subCategoryName = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        mContext = this;

        GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);
        GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

        edtSearch = (EditText) findViewById(R.id.edtSearch);
        imgSearch = (ImageView) findViewById(R.id.imgSearch);
        txtEmpty = (TextView) findViewById(R.id.txtEmpty);
        layoutProgress = (RelativeLayout) findViewById(R.id.layoutProgress);
        toolbarBack = (ImageView) findViewById(R.id.toolbarBack);
        recyclerViewSearch = (RecyclerView) findViewById(R.id.recyclerViewSearch);

        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        recyclerViewSearch.setLayoutManager(layoutManager);


        edtSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (count > 2) {

                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.SEARCH_URL + "?app_id=" + GlobalValues.APP_ID +
                                "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                "&productSearchKey=" +
                                edtSearch.getText().toString() + "&outletId=" + GlobalValues.CURRENT_OUTLET_ID + "&fav_cust_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);


                        new SearchTask().execute(url);

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    txtEmpty.setVisibility(View.GONE);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });


        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edtSearch.getText().toString().length() > 2) {

                    if (Utility.networkCheck(mContext)) {

                        String url = GlobalUrl.SEARCH_URL + "?app_id=" + GlobalValues.APP_ID +
                                "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                                "&productSearchKey=" +
                                edtSearch.getText().toString() + "&outletId=" + GlobalValues.CURRENT_OUTLET_ID + "&fav_cust_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                        new SearchTask().execute(url);

                    } else {
                        Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    txtEmpty.setVisibility(View.GONE);
                }
            }
        });

        toolbarBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    private class SearchTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            layoutProgress.setVisibility(View.VISIBLE);

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

                layoutProgress.setVisibility(View.GONE);

                JSONObject jsonObject = new JSONObject(s);


               // String galleryPath = jsonObject.getJSONObject("common").optString("product_gallery_image_source");
                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {


                    JSONArray resultArray = jsonObject.getJSONArray("result_set");
                    //String galleryPath = jsonObject.getJSONObject("common").optString("product_gallery_image_source");
                    searchProductList = new ArrayList<>();

                    if (resultArray.length() > 0) {

                        for (int i = 0; i < resultArray.length(); i++) {

                            JSONArray listArray = resultArray.getJSONObject(i).getJSONArray("product_list");

                            if (listArray.length() > 0) {

                                for (int l = 0; l < listArray.length(); l++) {
                                    JSONArray sublistArray = listArray.getJSONArray(l);

                                    if (sublistArray.length() > 0) {
                                        for (int sl = 0; sl < sublistArray.length(); sl++) {
                                            JSONObject jsonSubCategory = sublistArray.getJSONObject(sl);

                                            JSONArray subCategoryArray = jsonSubCategory.getJSONArray("subcategorie");

                                            if (subCategoryArray.length() > 0) {

                                                for (int sa = 0; sa < subCategoryArray.length(); sa++) {
                                                    JSONObject jsonSub = subCategoryArray.getJSONObject(sa);


                                                    JSONArray productArray = jsonSub.getJSONArray("products");

                                                    if (productArray.length() > 0) {

                                                        for (int p = 0; p < productArray.length(); p++) {

                                                            JSONObject jsonProduct = productArray.getJSONObject(p);


                                                            SearchProduct searchProduct = new SearchProduct();
                                                            searchProduct.setmProductId(jsonProduct.getString("product_id"));
                                                            searchProduct.setProduct_alias(jsonProduct.getString("product_alias"));
                                                            searchProduct.setmProductType(jsonProduct.getString("product_type"));
                                                            searchProduct.setmProductName(jsonProduct.getString("product_alias"));
                                                            searchProduct.setmProductSlug(jsonProduct.getString("product_slug"));
                                                            searchProduct.setProductPrice(jsonProduct.getString("product_price"));

                                                            try {
                                                                searchProduct.setmProductImage(""+jsonProduct.getJSONArray("image_gallery").getJSONObject(0).getString("pro_gallery_image"));
                                                            } catch (Exception e) {
                                                                e.printStackTrace();
                                                            }

                                                            searchProduct.setProduct_alias(jsonProduct.getString("product_alias"));
                                                            searchProduct.setmProductType(jsonProduct.getString("product_type"));
                                                            searchProduct.setmProductName(jsonProduct.getString("product_alias"));
                                                            searchProduct.setmProductSlug(jsonProduct.getString("product_slug"));
                                                            searchProduct.setProductPrice(jsonProduct.getString("product_price"));
                                                          /*  String subImageUrl=jsonProduct.optString("product_thumbnail");
                                                            if(subImageUrl!=null && subImageUrl.length()>0) {
                                                                searchProduct.setmProductGalleryImage(galleryPath + jsonProduct.getString("product_thumbnail"));
                                                            }*/



                                                            searchProduct.setmProduct_primary_id(jsonProduct.getString("product_primary_id"));
                                                            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                                                                searchProduct.setmProduct_favourite_id(jsonProduct.getString("fav_product_primary_id"));
                                                            }else{
                                                                searchProduct.setmProduct_favourite_id("");
                                                            }

                                                            searchProductList.add(searchProduct);

                                                        }

                                                    }
                                                }

                                            }
                                        }


                                    }
                                }
                            }


                        }

                    }

                    if (searchProductList.size() > 0) {

                        recyclerViewSearch.setVisibility(View.VISIBLE);
                        txtEmpty.setVisibility(View.GONE);

                        searchAdapter = new SearchAdapter(mContext, searchProductList);

                        recyclerViewSearch.setAdapter(searchAdapter);
                    } else {
                        recyclerViewSearch.setVisibility(View.GONE);
                        txtEmpty.setVisibility(View.VISIBLE);
                    }


                    searchAdapter.setOnItemClickListener(new IOnItemClick() {
                        @Override
                        public void onItemClick(View v, int position) {
                            Intent intent = new Intent(mContext, SearchProductDetailsActivity.class);
                            intent.putExtra("IMAGE_BASEPATH", mBasePath);
                            intent.putExtra("SEARCH_PRODUCT", searchProductList.get(position));
                            intent.putExtra("SEARCH_PRODUCT_PRICE", searchProductList.get(position).getProductPrice());
                            intent.putExtra("SEARCH_PRODUCT_PrimarId", searchProductList.get(position).getmProduct_primary_id());
                            intent.putExtra("SEARCH_PRODUCT_FavouriteId", searchProductList.get(position).getmProduct_favourite_id());

                            startActivity(intent);


                        }
                    });
                    //txtText.setText("Your account has been activated successfully.");
                } else {
                    recyclerViewSearch.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.VISIBLE);
                    txtEmpty.setText(jsonObject.optString("message"));
                    // txtText.setText("Link Expired");
                }

            } catch (Exception e) {
                e.printStackTrace();
                recyclerViewSearch.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
            } finally {
                layoutProgress.setVisibility(View.GONE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utility.writeToSharedPreference(mContext, "IS_ACTIVE","1");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            Utility.writeToSharedPreference(mContext, "OREO_UPDATE","1");
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


