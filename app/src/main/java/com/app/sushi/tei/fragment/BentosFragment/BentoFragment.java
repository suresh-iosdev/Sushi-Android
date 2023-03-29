package com.app.sushi.tei.fragment.BentosFragment;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.GlobalMembers.GlobalUrl;
import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.Model.Home.SubCategory;
import com.app.sushi.tei.R;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.WebService.WebserviceAssessor;
import com.app.sushi.tei.adapter.Bento.BentoCategoryViewPagerAdapter;

import org.json.JSONArray;
import org.json.JSONObject;

import java.lang.reflect.Field;
import java.util.ArrayList;


public class BentoFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private ImageView imgBack;
    private TabLayout categoryTabLayout;
    private ViewPager categoryViewPager;

    private BentoCategoryViewPagerAdapter categoryViewPagerAdapter;
    private ArrayList<MainCategory> mainCategoryList;
    private ArrayList<SubCategory> subCategoryList;
    private String mCategoryBasePath = "";
    private String mSubCategoryBasePath = "";

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext=activity;
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView=inflater.inflate(R.layout.fragment_bento,container,false);

        imgBack= (ImageView) mView.findViewById(R.id.imgBack);
        categoryTabLayout= (TabLayout) mView.findViewById(R.id.categoryTabLayout);
        categoryViewPager= (ViewPager) mView.findViewById(R.id.categoryViewPager);

        addTabItem();

        setUpIndicatorWidth(categoryTabLayout);

        categoryTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        categoryViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(categoryTabLayout));

        categoryTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                categoryViewPager.setCurrentItem(tab.getPosition());

                View v = tab.getCustomView();

                TextView txtView = (TextView) v.findViewById(R.id.txtTabItem);
                //TODO
          /*    ImageView imageView = (ImageView) v.findViewById(R.id.imgTabItem);


                Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(tab.getPosition()).
                        getmActiveImage()).into(imageView);*/
                txtView.setTextColor(getResources().getColor(R.color.colorSelectedTab));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v = tab.getCustomView();

                TextView txtView = (TextView) v.findViewById(R.id.txtTabItem);
                //TODO
         /*  ImageView imageView = (ImageView) v.findViewById(R.id.imgTabItem);

                Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(tab.getPosition())
                        .getmDefaultImage()).into(imageView);*/
                txtView.setTextColor(getResources().getColor(R.color.colorWhite));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });





        return mView;


    }


    @Override
    public void onResume() {
        super.onResume();
    }

    private void addTabItem() {

        if (Utility.networkCheck(mContext)) {

            //availability=7B30BB03-14BD-47E4-B9B1-9731F9A3BC9C&outletId=151

            GlobalValues.CURRENT_AVAILABLITY_ID=Utility.readFromSharedPreference(mContext, GlobalValues.AVALABILITY_ID);

            String url = GlobalUrl.CATEGORY_URL + "?app_id=" + GlobalValues.APP_ID + "&availability="
                    + GlobalValues.CURRENT_AVAILABLITY_ID + "&outletId=" +
                    Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);

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


                JSONObject jsonObject = new JSONObject(s);

                if (jsonObject.getString("status").equalsIgnoreCase("ok")) {

                    JSONObject commonJson = jsonObject.getJSONObject("common");

                    mCategoryBasePath = commonJson.getString("category_image_url") + "/";
                    mSubCategoryBasePath = commonJson.getString("subcategory_image_url") + "/";


                    JSONArray arrayResult = jsonObject.getJSONArray("result_set");

                    mainCategoryList = new ArrayList<>();

                    if (arrayResult.length() > 0) {

                        for (int j = 0; j < arrayResult.length(); j++) {

                            JSONObject categoryJson = arrayResult.getJSONObject(j);

                            MainCategory mainCategory = new MainCategory();

                            mainCategory.setmCategoryId(categoryJson.getString("pro_cate_primary_id"));
                            mainCategory.setmCategoryName(categoryJson.getString("category_name"));
                           // mainCategory.setmDefaultImage(categoryJson.optString("pro_cate_default_image"));
                            mainCategory.setmActiveImage(mCategoryBasePath+categoryJson.optString("pro_cate_active_image"));
                            mainCategory.setmCategorySlug(categoryJson.optString("pro_cate_slug"));

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
                                        subCategory.setmBasePath(mSubCategoryBasePath);
                                        subCategory.setmSubCategorySlug(subCatJson.getString("pro_subcate_slug"));
                                        subCategoryList.add(subCategory);

                                    }
                                } else {

                                }

                                mainCategory.setSubCategoryList(subCategoryList);
                                mainCategoryList.add(mainCategory);

                            }else{
                                mainCategory.setSubCategoryList(subCategoryList);
                                mainCategoryList.add(mainCategory);
                            }
                        }

                        setView();

                    } else {

                    }


                } else {

                    Toast.makeText(mContext, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                   // slidingUpPanelLayout.setPanelState(SlidingUpPanelLayout.PanelState.EXPANDED);
                }

            } catch (Exception e) {
                progressDialog.dismiss();
                e.printStackTrace();
            } finally {
                progressDialog.dismiss();
            }


        }
    }

    private void setView() {

        categoryTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        for (int k = 0; k < mainCategoryList.size(); k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_item, null);

            TextView txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem);
            //TODO
         /*   ImageView imgTabItem = (ImageView) tabView.findViewById(R.id.imgTabItem);
            View tabItemLIne = tabView.findViewById(R.id.tabItemLIne);

            txtTabItem.setText(mainCategoryList.get(k).getmCategoryName());
            Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(k).getmDefaultImage()).into(imgTabItem);
            txtTabItem.setTextColor(getResources().getColor(R.color.colorWhite));


            if (k == 0) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
                Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(k).getmActiveImage()).into(imgTabItem);

            }
*/
            categoryTabLayout.addTab(categoryTabLayout.newTab().setCustomView(tabView));

        }


        categoryViewPagerAdapter = new BentoCategoryViewPagerAdapter(getChildFragmentManager(), mContext, mainCategoryList);
        categoryViewPager.setAdapter(categoryViewPagerAdapter);

    }

    private void setUpIndicatorWidth(TabLayout tabLayout) {
        Class<?> tabLayoutClass = tabLayout.getClass();
        Field tabStrip = null;
        try {
            tabStrip = tabLayoutClass.getDeclaredField("mTabStrip");
            tabStrip.setAccessible(true);
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }

        LinearLayout layout = null;
        try {
            if (tabStrip != null) {
                layout = (LinearLayout) tabStrip.get(tabLayout);
            }
            if (layout != null) {
                for (int i = 0; i < layout.getChildCount(); i++) {
                    View child = layout.getChildAt(i);
                    child.setPadding(0, 0, 0, 0);
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(0, LinearLayout.LayoutParams.MATCH_PARENT, 1);
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                        params.setMarginStart(18);
                        params.setMarginEnd(18);
                    }
                    child.setLayoutParams(params);
                    child.invalidate();
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
