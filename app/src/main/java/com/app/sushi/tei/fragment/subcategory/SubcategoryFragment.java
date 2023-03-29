package com.app.sushi.tei.fragment.subcategory;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.CompassAllProduct.ProductDetailsItemItem;
import com.app.sushi.tei.Model.CompassAllProduct.ProductsItem;
import com.app.sushi.tei.Model.CompassAllProduct.ResultSetItem;
import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.Model.ProductList.ProductSubCategory;
import com.app.sushi.tei.Model.ProductList.Products;
import com.app.sushi.tei.Model.ProductList.TagImageModel;
import com.app.sushi.tei.Model.Rewards.Rewards;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.SessionData;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Rewards.RewardsAdapters;
import com.app.sushi.tei.adapter.SubCategory.SubcategoryAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class SubcategoryFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private RecyclerView recyclerview;
    private ArrayList<Rewards> rewardsearnedlist;

    private RewardsAdapters mRewardsAdapters;
    private SubcategoryAdapter subcategoryAdapter;
    private ArrayList<ResultSetItem> mainCategoryList;
    private ArrayList<ProductsItem> subCategoryList;
    private List<List<ProductDetailsItemItem>> productDetails;
    private List<ProductDetailsItemItem> productDetails1;
    private ArrayList<String> SubcategoryList;
    private ArrayList<String> showingSubcategoryList;
    private ArrayList<MainCategory> CategoryList;
    private int positions;

    private String mCategorySlug = "";

    Bundle bundle;

    private String mCategoryBasePath = "";
    private String mSubCategoryBasePath = "";


    private List<ProductSubCategory> productSubCategoryList;
    private List<ProductSubCategory> showingProductSubCategoryList;
    private int mPosition = -1;

    private String mSubCategorySlug = "";
    private boolean canLoad = false;
    private boolean isLoaded = false;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();

        if (bundle != null) {
            CategoryList = bundle.getParcelableArrayList("arrayList");
            mCategorySlug = bundle.getString("categorySlug");
        }
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_subcategory, container, false);

        SessionData.getInstance().setSubcategoryFragment(SubcategoryFragment.this);

        recyclerview = mView.findViewById(R.id.recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(mContext);
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerview.setLayoutManager(linearLayoutManager);
        recyclerview.setNestedScrollingEnabled(false);

//        recyclerview.addOnScrollListener(new RecyclerView.OnScrollListener() {
//            @Override
//            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
//                super.onScrolled(recyclerView, dx, dy);
//                if (!recyclerView.canScrollVertically(1))
//                    onScrolledToBottom();
//            }
//        });


//        getCategory(mCategorySlug);

        return mView;
    }

    public void getCategory(String categorySlug) {

        if (Utility.networkCheck(mContext)) {

            String url = GlobalUrl.COMPASSCATEGORY_URL + "?app_id=" + GlobalValues.APP_ID + "&availability="
                    + Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID) + "&category_slug=" + categorySlug + "&subcate_slug=" + "&outletId=" + Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID) + "&fav_cust_id=" + Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID) + "&list_only=yes";


            new CategoryTask().execute(url);
        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
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
                Log.e("product_response::", s);

                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    String basePath = jsonObject.getJSONObject("common").optString("product_image_source");
                    String galleryBasePath = jsonObject.getJSONObject("common").optString("product_gallery_image_source");
                    String tagimagepath = jsonObject.getJSONObject("common").optString("tag_image_source");
                    mSubCategoryBasePath = basePath;

                    GlobalValues.galleryBasePath = galleryBasePath;
                    productSubCategoryList = new ArrayList<>();


                    JSONArray jsonResult = jsonObject.getJSONArray("result_set");

                    if (jsonResult.length() > 0) {

                        for (int i = 0; i < jsonResult.length(); i++) {

                            JSONArray jsonSubArray = jsonResult.getJSONObject(i).getJSONArray("subcategorie");

                            if (jsonSubArray.length() > 0) {
                                SubcategoryList = new ArrayList<>();
                                for (int k = 0; k < jsonSubArray.length(); k++) {


                                    JSONObject jsonSubcategory = jsonSubArray.getJSONObject(k);

                                    ProductSubCategory productSubCategory = new ProductSubCategory();

                                    productSubCategory.setmSubCategoryName(jsonSubcategory.getString("pro_subcate_name"));
                                    productSubCategory.setmSubcategoryId(jsonSubcategory.getString("pro_subcate_id"));
                                    productSubCategory.setmSubCategorySlug(jsonSubcategory.getString("pro_subcate_slug"));
                                    SubcategoryList.add(jsonSubcategory.getString("pro_subcate_name"));

                                    productSubCategory.setmSubCategorySlug(jsonSubcategory.getString("pro_cate_slug"));
                                    productSubCategory.setmSubCategoryImage(basePath + "/" + jsonSubcategory.optString("pro_subcate_image"));

                                    if (mSubCategorySlug.equalsIgnoreCase(jsonSubcategory.getString("pro_subcate_slug"))) {
                                        mPosition = k;

                                    }

                                    JSONArray jsonProductArray = jsonSubcategory.getJSONArray("products");

                                    ArrayList<Products> productsArrayList = new ArrayList<>();

                                    if (jsonProductArray.length() > 0) {

                                        for (int j = 0; j < jsonProductArray.length(); j++) {

                                            JSONObject jsonProduct = jsonProductArray.getJSONObject(j);

                                            Products products = new Products();


                                            products.setmProductId(jsonProduct.getString("product_id"));
                                            products.setMproduct_alias(jsonProduct.getString("product_alias"));
                                            products.setmProductName(jsonProduct.getString("product_name"));
                                            products.setmProductDescription(jsonProduct.getString("product_long_description"));
                                            products.setmProductShortDescription(jsonProduct.getString("product_short_description"));
                                            products.setmProductPrice(jsonProduct.getString("product_price"));
                                            products.setmProductSku(jsonProduct.getString("product_sku"));
                                            products.setmProductSlug(jsonProduct.getString("product_slug"));
                                            products.setmProductStatus(jsonProduct.getString("product_status"));
                                            products.setmProductType(jsonProduct.getString("product_type"));
                                            products.setmProduct_stock(jsonProduct.getString("product_stock"));
                                            if (Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID).length() > 0) {
                                                products.setFav_product_primary_id(jsonProduct.getString("fav_product_primary_id"));
                                            } else {
                                                products.setFav_product_primary_id("");
                                            }
                                            products.setmProductPrimaryId(jsonProduct.getString("product_primary_id"));
                                            String subImageUrl = jsonProduct.optString("product_thumbnail");
                                            if (subImageUrl != null && subImageUrl.length() > 0) {
                                                products.setmProductThumpImage(basePath + "/" + jsonProduct.optString("product_thumbnail"));
                                            }
                                            products.setmAvailabilityId(jsonProduct.getString("product_availability_id"));
                                            products.setmVoucherExpiryDate(jsonProduct.optString("product_voucher_expiry_date"));
                                            products.setmVoucherPoints(jsonProduct.optString("product_voucher_points"));
                                            products.setmProductVoucher(jsonProduct.optString("product_voucher"));
                                            products.setmProductMinQty(jsonProduct.optString("product_minimum_quantity"));
                                            products.setmProductVoucherIncreaseQty(jsonProduct.optString("product_voucher_increase_qty"));

                                            JSONArray tagimagearray = jsonProduct.getJSONArray("product_tag");
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

                                            JSONArray image_gallery = jsonProduct.getJSONArray("image_gallery");
                                            for (int l = 0; l < image_gallery.length(); l++) {
                                                JSONObject jsonObject1 = image_gallery.getJSONObject(l);
                                                galleryImageUrl = jsonObject1.getString("pro_gallery_image");

                                            }
                                            //products.setmSubCategoryGalleryImage(galleryBasePath + "/" + jsonProduct.optString("product_thumbnail"));

                                            if (galleryImageUrl != null && galleryImageUrl.length() > 0) {
                                                products.setmSubCategoryGalleryImage(galleryBasePath + "/" + galleryImageUrl);
                                            }

                                            productsArrayList.add(products);

                                        }
                                    } else {
                                        productsArrayList = new ArrayList<>();
                                    }

                                    productSubCategory.setProductsList(productsArrayList);
                                    productSubCategoryList.add(productSubCategory);
                                }
                            }
                        }

                        setProductList(productSubCategoryList, SubcategoryList);

                    } else {
                        setProductList(productSubCategoryList, SubcategoryList);
                    }

                } else {
                    Toast.makeText(mContext, jsonObject.optString("message"), Toast.LENGTH_SHORT).show();
                }


            } catch (Exception e) {

                progressDialog.dismiss();
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }


        }
    }

    private void setProductList(final List<ProductSubCategory> productSubCategoryList, List<String> SubcategoryList) {


        subcategoryAdapter = new SubcategoryAdapter(mContext, productSubCategoryList, SubcategoryList, SubcategoryFragment.this);

        recyclerview.setAdapter(subcategoryAdapter);


        /*if(mPosition ==-1)
        {


        }
        else
        {
            if (productSubCategoryList.size() > 0) {

                if (productSubCategoryList.size() > 0) {

                    Picasso.with(mContext).load(mSubCategoryImage).error(R.drawable.img_placeholder).into(imgCategorImage);

                    try {

                        txtEmpty.setVisibility(View.GONE);
                        productlistRecyclerView.setVisibility(View.VISIBLE);

                        if (productSubCategoryList.get(mPosition).getProductsList() != null && productSubCategoryList.get(mPosition).getProductsList().size() > 0)
                        {
                            productListRecyclerAdapter = new ProductListRecyclerAdapter(mContext, productSubCategoryList.get(mPosition).getProductsList());
                            productlistRecyclerView.setAdapter(productListRecyclerAdapter);
                        } else {
                            txtEmpty.setVisibility(View.VISIBLE);
                            productlistRecyclerView.setVisibility(View.GONE);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        txtEmpty.setVisibility(View.VISIBLE);
                        productlistRecyclerView.setVisibility(View.GONE);
                    }

                } else {

                    productlistRecyclerView.setVisibility(View.GONE);
                    txtEmpty.setVisibility(View.VISIBLE);
                }

                layoutManager = new LinearLayoutManager(mContext);
                subCategoryMenuRecyclerview.setLayoutManager(layoutManager);
                subCategoryMenuRecyclerAdapter = new SubCategoryMenuRecyclerAdapter(mContext, productSubCategoryList);
                subCategoryMenuRecyclerview.setAdapter(subCategoryMenuRecyclerAdapter);
                subCategoryMenuRecyclerview.setItemAnimator(new DefaultItemAnimator());
                subCategoryMenuRecyclerview.setNestedScrollingEnabled(false);
                txtTitle.setText(productSubCategoryList.get(mPosition).getmSubCategoryName());

                subCategoryMenuRecyclerAdapter.updateCatagoryTxtView(mPosition);

                subCategoryMenuRecyclerAdapter.setOnClickListener(new IOnItemClick() {
                    @Override
                    public void onItemClick(View v, int position) {
                        subCategoryMenuRecyclerAdapter.updateCatagoryTxtView(position);


                        layoutMenu.performClick();
                        txtTitle.setText(productSubCategoryList.get(position).getmSubCategoryName());

                        productListRecyclerAdapter = new ProductListRecyclerAdapter(mContext, productSubCategoryList.get(position).getProductsList());
                        productlistRecyclerView.setAdapter(productListRecyclerAdapter);
                        progressDialog = new ProgressDialog(mContext);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(false);
                        progressDialog.setCanceledOnTouchOutside(false);
                        progressDialog.show();

                        Picasso.with(mContext).load(
                                productSubCategoryList.get(position).getmSubCategoryImage()).error(R.drawable.img_placeholder).into(imgCategorImage);

                        Handler handler = new Handler();
                        handler.postDelayed(new Runnable() {
                            @Override
                            public void run() {

                                progressDialog.dismiss();
                            }
                        }, 400);

                    }
                });


            } else {

                layoutMenu.setVisibility(View.GONE);
                productlistRecyclerView.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
            }
*/
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getActivity() != null) {
//            if (isVisibleToUser) {
//                canLoad = true;
//                if (!isLoaded){
//                    getCategory(mCategorySlug);
//                }
//            } else {
//                canLoad = false;
//            }
        }
        //TODO
          /*  if (networkCheck()) {

                mCustomerId = Utility.readFromSharedPreference(mContext, GlobalValues.CUSTOMERID);

                new RewardsRedeenedTask().execute(GlobalUrl.REWARD_POINTS_REDEEMED_URL + "?status=A&app_id=" +
                        GlobalValues.APP_ID + "&customer_id=" + mCustomerId);

            } else {

                Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_LONG).show();

            }
        }*/

    }

    @Override
    public void onResume() {
        super.onResume();
//        if (canLoad) {
        getCategory(mCategorySlug);
//        }
        recyclerview.setAdapter(subcategoryAdapter);

        //subcategoryAdapter.notifyDataSetChanged();
    /*    String posi=Utility.readFromSharedPreference(mContext, GlobalValues.SELECTEDPOSITION);

        if(!posi.equalsIgnoreCase("")){
        int Currentpostision= Integer.parseInt(posi);
        if(positions!=Currentpostision){
            getCategory(Currentpostision);
        }
        }else{
            getCategory(positions);
        }*/

    }

    public boolean networkCheck() {

        ConnectivityManager cm =
                (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();
        return isConnected;

    }


    private class RewardsRedeenedTask extends AsyncTask<String, String, String> {

        String response, status, message, commonImageurl = "";

        ProgressDialog progressDialog;


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(mContext);
            progressDialog.setMessage("Loading...");
            progressDialog.setCancelable(false);
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... params) {

            try {

                response = WebserviceAssessor.getData(params[0]);

            } catch (Exception e) {
                e.printStackTrace();
            }

            return response;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (progressDialog != null && progressDialog.isShowing())
                progressDialog.dismiss();

            if (response != null) {

                JSONObject responseJSONObject = null;
                try {

                    responseJSONObject = new JSONObject(response);

                    status = responseJSONObject.getString("status");


                    if (status.equals("ok")) {
                        //Success
                        rewardsearnedlist = new ArrayList<>();
                        JSONArray jsonArrayresult = responseJSONObject.getJSONArray("result_set");

                        if (jsonArrayresult.length() > 0) {

                            for (int i = 0; i < jsonArrayresult.length(); i++) {

                                JSONObject jsonObject = jsonArrayresult.getJSONObject(i);

                                Rewards mRewards = new Rewards();

                                mRewards.setmOrderPrimaryId(jsonObject.getString("order_local_no"));
                                mRewards.setmOrderDate(jsonObject.getString("order_date"));
                                mRewards.setMlhRedeemAmount(jsonObject.getString("order_total_amount"));
                                mRewards.setMlhRedeemPoint(jsonObject.getString("lh_redeem_point"));
                                mRewards.setmOrderId(jsonObject.getString("order_id"));
                                mRewards.setmOrderAvailabilityName(jsonObject.getString("order_availability_name"));
                                mRewards.setmDiscountType(jsonObject.optString("lh_redeem_status"));

                                rewardsearnedlist.add(mRewards);

                            }

                            mRewardsAdapters = new RewardsAdapters(getActivity(), rewardsearnedlist, RewardsAdapters.REWARD_TYPE_REDEEMED);
                            RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getActivity());
                            recyclerview.setLayoutManager(mLayoutManager);
                            recyclerview.setItemAnimator(new DefaultItemAnimator());
                            recyclerview.setAdapter(mRewardsAdapters);


                            recyclerview.setVisibility(View.VISIBLE);

                        } else {


                            recyclerview.setVisibility(View.GONE);
                        }

                    } else {

                        message = responseJSONObject.getString("message");
                        //  Toast.makeText(getActivity(), Html.fromHtml(message), Toast.LENGTH_LONG).show();
                        //No need to run cartDetailTask here //hence "else" will enter when there is no item in cart


                        recyclerview.setVisibility(View.GONE);


                    }


                } catch (JSONException e) {
                    e.printStackTrace();


                }


            } else {


            }


        }
    }

    private void onScrolledToBottom() {
        if (showingSubcategoryList.size() < SubcategoryList.size()) {
            int x, y;
            if ((SubcategoryList.size() - showingSubcategoryList.size()) >= 50) {
                x = showingSubcategoryList.size();
                y = x + 50;
            } else {
                x = showingSubcategoryList.size();
                y = x + SubcategoryList.size() - showingSubcategoryList.size();
            }
            for (int i = x; i < y; i++) {
                showingSubcategoryList.add(SubcategoryList.get(i));
            }
            subcategoryAdapter.notifyDataSetChanged();
        }

    }
}
 /*

          JSONObject jsonObject = new JSONObject(s);

          if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

          JSONObject commonJson = jsonObject.getJSONObject("common");

          mCategoryBasePath = commonJson.getString("product_image_source") + "/";
          mSubCategoryBasePath = commonJson.getString("subcategory_image_url") + "/";


          JSONArray arrayResult = jsonObject.getJSONArray("result_set");

          mainCategoryList = new ArrayList<>();

        if (arrayResult.length() > 0) {

        for (int j = 0; j < arrayResult.length(); j++) {
        ResultSetItem mainCategory=new ResultSetItem();
        JSONObject category = arrayResult.getJSONObject(j);
        mainCategory.setProCateName(category.getString("pro_cate_name"));
        mainCategory.setProCateSlug(category.getString("pro_subcate_slug"));
        JSONArray subArray = category.getJSONArray("products");


        subCategoryList = new ArrayList<>();

        if (subArray.length() > 0) {

        for (int k = 0; k < subArray.length(); k++) {
        ProductsItem subCategory = new ProductsItem();

        JSONObject subCatJson = subArray.getJSONObject(k);
        subCategory.setProductName(subCatJson.getString("product_alias"));
        subCategory.setProductPrice(subCatJson.getString("product_price"));
        subCategory.setProductId(subCatJson.getString("product_id"));
        subCategory.setProductType(subCatJson.getString("product_type"));
        subCategory.setProductStock(subCatJson.getString("product_stock"));
        subCategory.setProductSku(subCatJson.getString("product_sku"));
        subCategory.setProductShortDescription(subCatJson.getString("product_short_description"));
        JSONArray setMenuComponentArray = subCatJson.getJSONArray("set_menu_component");



        setmenuComponentList= new ArrayList<>();

        if (setMenuComponentArray.length() > 0) {

        for (int i = 0; i < setMenuComponentArray.length(); i++) {
        SetMenuComponentItem setMenuComponentItem=new SetMenuComponentItem();

        JSONObject menuComponentJson = setMenuComponentArray.getJSONObject(i);
        setMenuComponentItem.setMenuComponentName(menuComponentJson.getString("menu_component_name"));
        setMenuComponentItem.setMenuComponentId(menuComponentJson.getString("menu_component_id"));
        setMenuComponentItem.setMenuComponentDefaultSelect(menuComponentJson.getString("menu_component_default_select"));


        productDetails= new ArrayList<>();

        JSONArray product_detailsArray = menuComponentJson.getJSONArray("product_details");


        if (product_detailsArray.length() > 0) {

        for (int p = 0; p < product_detailsArray.length(); p++) {

        JSONArray subproduct_detailsArray = product_detailsArray.getJSONArray(p);
        productDetails1 = new ArrayList<>();
        if (subproduct_detailsArray.length() > 0) {

        for (int m = 0; m < subproduct_detailsArray.length(); m++) {
        ProductDetailsItemItem productDetailsItemItem = new ProductDetailsItemItem();
        productDetailsItemItem.setHasModifier(true);
        JSONObject productDetailsItem = subproduct_detailsArray.getJSONObject(m);

        productDetailsItemItem.setCatgoryName(productDetailsItem.getString("catgory_name"));
        productDetailsItemItem.setProductPrice(productDetailsItem.getString("product_price"));
        productDetailsItemItem.setProductName(productDetailsItem.getString("product_name"));
        productDetailsItemItem.setSubcatgoryName(productDetailsItem.getString("subcatgory_name"));
        productDetailsItemItem.setProductPrimaryId(productDetailsItem.getString("product_primary_id"));
        productDetailsItemItem.setProductAlias(productDetailsItem.getString("product_alias"));
                                       productDetailsItemItem.setProductType(productDetailsItem.getString("product_type"));

        productDetails1.add(productDetailsItemItem);


        }

        }
        productDetails.add(productDetails1);
        setMenuComponentItem.setProductDetails(productDetails);

        }
        }

        setmenuComponentList.add(setMenuComponentItem);

        subCategory.setSetMenuComponent(setmenuComponentList);
        }
        }

        subCategoryList.add(subCategory);

        }
        }

        mainCategory.setProducts(subCategoryList);
        mainCategoryList.add(mainCategory);



        }


        }
        subcategoryAdapter=new SubcategoryAdapter(mContext,mainCategoryList,mSubCategoryBasePath);

        recyclerview.setAdapter(subcategoryAdapter);

        } else {

        Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

        }*/
