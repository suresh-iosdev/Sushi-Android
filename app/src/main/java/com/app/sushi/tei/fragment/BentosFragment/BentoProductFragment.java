package com.app.sushi.tei.fragment.BentosFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Model.Home.SubCategory;
import com.app.sushi.tei.Model.ProductList.ProductSubCategory;
import com.app.sushi.tei.Model.ProductList.Products;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Bento.BentoProductRecyclerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class BentoProductFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private TextView txtTitle, txtHint, txtEmpty;
    private RecyclerView recyclerviewProduct;
    private RecyclerView.LayoutManager layoutManager;


    private Bundle bundle;
    private ArrayList<SubCategory> subCategoryArrayList;
    private String mCategorySlug = "", mSubCategorySlug = "";
    private List<Products> productsArrayList;
    private BentoProductRecyclerAdapter productListRecyclerAdapter;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


    public static BentoProductFragment newInstance(ArrayList<SubCategory> subCategoryArrayList, String mainSlug) {
        BentoProductFragment fragment = new BentoProductFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("subcategory", subCategoryArrayList);
        args.putString("mainSlug", mainSlug);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();

        if (bundle != null) {
            subCategoryArrayList = bundle.getParcelableArrayList("subcategory");
            mCategorySlug = bundle.getString("mainSlug");
        }

    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_bento_product, container, false);

        txtTitle = (TextView) mView.findViewById(R.id.txtTitle);
        txtHint = (TextView) mView.findViewById(R.id.txtHint);
        txtEmpty = (TextView) mView.findViewById(R.id.txtEmpty);
        recyclerviewProduct = (RecyclerView) mView.findViewById(R.id.recyclerviewProduct);

        txtTitle.setText(mCategorySlug);

        GlobalValues.CURRENT_OUTLET_ID=Utility.readFromSharedPreference(mContext,GlobalValues.OUTLET_ID);
        GlobalValues.CURRENT_AVAILABLITY_ID=Utility.readFromSharedPreference(mContext,GlobalValues.AVALABILITY_ID);

        if (Utility.networkCheck(mContext)) {


            if (subCategoryArrayList != null && subCategoryArrayList.size() > 0) {

                mSubCategorySlug = subCategoryArrayList.get(0).getmSubCategorySlug();

                String url = GlobalUrl.PRODUCT_URL + "?app_id=" + GlobalValues.APP_ID +
                        "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                        "&outletId=" + GlobalValues.CURRENT_OUTLET_ID + "&category_slug=" + mCategorySlug +
                        "&subcate_slug=" + mSubCategorySlug;

                new ProductListTask().execute(url);
            } else {
                recyclerviewProduct.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
            }
        } else {
            Toast.makeText(mContext, "Please check your internet connection.", Toast.LENGTH_SHORT).show();
        }

        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
    }

    private class ProductListTask extends AsyncTask<String, Void, String> {

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

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    productsArrayList = new ArrayList<>();

                    JSONArray jsonResult = jsonObject.getJSONArray("result_set");

                    if (jsonResult.length() > 0) {

                        for (int i = 0; i < jsonResult.length(); i++) {

                            JSONArray jsonSubArray = jsonResult.getJSONObject(i).getJSONArray("subcategorie");

                            if (jsonSubArray.length() > 0) {

                                for (int k = 0; k < jsonSubArray.length(); k++) {


                                    JSONObject jsonSubcategory = jsonSubArray.getJSONObject(k);

                                    ProductSubCategory productSubCategory = new ProductSubCategory();

                                    productSubCategory.setmSubCategoryName(jsonSubcategory.getString("pro_subcate_name"));
                                    productSubCategory.setmSubcategoryId(jsonSubcategory.getString("pro_subcate_id"));
                                    productSubCategory.setmSubCategorySlug(jsonSubcategory.getString("pro_subcate_slug"));
                                    productSubCategory.setmSubCategorySlug(jsonSubcategory.getString("pro_cate_slug"));

                                    txtTitle.setText(jsonSubcategory.optString("pro_cate_name"));

                                    JSONArray jsonProductArray = jsonSubcategory.getJSONArray("products");

                                    productsArrayList = new ArrayList<>();

                                    if (jsonProductArray.length() > 0) {

                                        for (int j = 0; j < jsonProductArray.length(); j++) {

                                            JSONObject jsonProduct = jsonProductArray.getJSONObject(j);

                                            Products products = new Products();

                                            products.setmProductId(jsonProduct.getString("product_id"));
                                            products.setmProductName(jsonProduct.getString("product_name"));
                                            products.setmProductDescription(jsonProduct.getString("product_long_description"));
                                            products.setmProductPrice(jsonProduct.getString("product_price"));
                                            products.setmProductSku(jsonProduct.getString("product_sku"));
                                            products.setmProductSlug(jsonProduct.getString("product_slug"));
                                            products.setmProductStatus(jsonProduct.getString("product_status"));
                                            products.setmProduct_stock(jsonProduct.getString("product_stock"));
                                            products.setmProductType(jsonProduct.getString("product_type"));
                                            products.setmProductThumpImage(basePath + "/" +
                                                    jsonProduct.optString("product_thumbnail"));
                                            products.setmAvailabilityId(jsonProduct.getString("product_availability_id"));
                                            products.setmMinQuantity(jsonProduct.optString("product_minimum_quantity"));

                                            txtHint.setText("* You must have an order with a minimum quantity of "+"5"+""+
                                                    " quantity to place your order");

                                            productsArrayList.add(products);

                                        }
                                    } else {
                                        productsArrayList = new ArrayList<>();
                                    }

                                }
                            }

                        }

                        setProductList(productsArrayList);

                    } else {
                        setProductList(productsArrayList);
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

    private void setProductList(final List<Products> productList) {

        if (productList.size() > 0) {

            if (productList.size() > 0) {

//                Picasso.with(mContext).load().error(R.drawable.img_placeholder).into(imgCategorImage);

                try {

                    txtEmpty.setVisibility(View.GONE);
                    recyclerviewProduct.setVisibility(View.VISIBLE);

                    if (productList != null &&
                            productList.size() > 0) {
                        productListRecyclerAdapter = new BentoProductRecyclerAdapter(mContext, productList);
                        GridLayoutManager gridLayoutManager=new GridLayoutManager(getActivity(),2);
                        recyclerviewProduct.setLayoutManager(gridLayoutManager);

                        //adding item decoration
                        //TODO
                    /*    int spacing= getActivity().getResources().getDimensionPixelSize(R.dimen.spacing);
                        GridSpacingItemDecoration gridSpacingItemDecoration=new GridSpacingItemDecoration(2,spacing,true);
                        recyclerviewProduct.addItemDecoration(gridSpacingItemDecoration);
                        recyclerviewProduct.setAdapter(productListRecyclerAdapter);
                        recyclerviewProduct.setNestedScrollingEnabled(false);*/
                    } else {
                        txtEmpty.setVisibility(View.VISIBLE);
                        recyclerviewProduct.setVisibility(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    txtEmpty.setVisibility(View.VISIBLE);
                    recyclerviewProduct.setVisibility(View.GONE);
                }

            } else {

                recyclerviewProduct.setVisibility(View.GONE);
                txtEmpty.setVisibility(View.VISIBLE);
            }


        } else {

            recyclerviewProduct.setVisibility(View.GONE);
            txtEmpty.setVisibility(View.VISIBLE);
        }
    }
}
