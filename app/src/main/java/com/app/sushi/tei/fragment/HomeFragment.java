package com.app.sushi.tei.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.R;
import com.app.sushi.tei.adapter.CategoryViewPagerAdapter;

import java.util.ArrayList;


public class HomeFragment extends Fragment {


    private Activity mContext;
    private View mView;


    private ViewPager categoryViewPager;
    private TabLayout categoryTabLayout;

    private CategoryViewPagerAdapter categoryViewPagerAdapter;

    private ArrayList<String> mainCategoryList;
    private ArrayList<String> subCategoryList;
    private String mCategoryBasePath = "";
    private String mSubCategoryBasePath = "";


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_home, container, false);

        categoryViewPager = (ViewPager) mView.findViewById(R.id.categoryViewPager);
        categoryTabLayout = (TabLayout) mView.findViewById(R.id.categoryTabLayout);

        mainCategoryList = new ArrayList<>();
        mainCategoryList.add("STARTERS");
        mainCategoryList.add("MAIN ITEMS");
        mainCategoryList.add("DESSERT");
        mainCategoryList.add("BEVERAGE");
        subCategoryList=new ArrayList<>();

        subCategoryList.add("Burger");
        subCategoryList.add("Sides");
        subCategoryList.add("Soup");
        subCategoryList.add("Fries");
        subCategoryList.add("Burger");


        categoryTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        categoryViewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(categoryTabLayout));

        categoryTabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                categoryViewPager.setCurrentItem(tab.getPosition());

                View v = tab.getCustomView();

                TextView txtView = (TextView) v.findViewById(R.id.txtTabItem);
                //ImageView imageView = (ImageView) v.findViewById(R.id.imgTabItem);

             /*   Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(tab.getPosition()).
                        getmActiveImage()).into(imageView);*/
                txtView.setTextColor(getResources().getColor(R.color.colorBlack));

            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View v = tab.getCustomView();

                TextView txtView = (TextView) v.findViewById(R.id.txtTabItem);
                //ImageView imageView = (ImageView) v.findViewById(R.id.imgTabItem);

               /* Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(tab.getPosition())
                        .getmDefaultImage()).into(imageView);*/
                txtView.setTextColor(getResources().getColor(R.color.colorTabText));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        setView();
        return mView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {


        inflater.inflate(R.menu.menu_home, menu);

        super.onCreateOptionsMenu(menu, inflater);
    }





    private void setView() {

        categoryTabLayout.setTabGravity(TabLayout.GRAVITY_CENTER);

        for (int k = 0; k < mainCategoryList.size(); k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_item, null);

            TextView txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem);
           // ImageView imgTabItem = (ImageView) tabView.findViewById(R.id.imgTabItem);
          //  View tabItemLIne = tabView.findViewById(R.id.tabItemLIne);

            txtTabItem.setText(mainCategoryList.get(k));
           // Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(k).getmDefaultImage()).into(imgTabItem);
            txtTabItem.setTextColor(getResources().getColor(R.color.colorTabText));


            if (k == 0) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorPrimary));
                //Picasso.with(mContext).load(mCategoryBasePath + mainCategoryList.get(k).getmActiveImage()).into(imgTabItem);

            }

            categoryTabLayout.addTab(categoryTabLayout.newTab().setCustomView(tabView));

        }


        categoryViewPagerAdapter = new CategoryViewPagerAdapter(getChildFragmentManager(), mContext, mainCategoryList);
        categoryViewPager.setAdapter(categoryViewPagerAdapter);

    }
}
