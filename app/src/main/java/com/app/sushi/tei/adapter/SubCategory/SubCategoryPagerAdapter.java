package com.app.sushi.tei.adapter.SubCategory;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.app.sushi.tei.Model.Home.MainCategory;

import java.util.ArrayList;


public class SubCategoryPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private int mPosition;
    private ArrayList<MainCategory> categoryList;
    private int mNumOfTabs;



    public SubCategoryPagerAdapter(FragmentManager supportFragmentManager, Context context, ArrayList<MainCategory> categoryList, int mPosition, int tabCount) {
        super(supportFragmentManager);
        this.mContext = context;
        this.categoryList=categoryList;
        this.mPosition=mPosition;
        mNumOfTabs=tabCount;

    }


    @Override
    public Fragment getItem(int position) {

        Fragment fragment=null;

        switch (position)
        {
            default:


                       // fragment=new SubcategoryFragment().newInstance(categoryList,mPosition,position);

                }





        return fragment;


    }


    @Override
    public int getCount() {
        return categoryList.size();
    }
}
