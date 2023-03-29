package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.app.sushi.tei.fragment.CategoryFragment;

import java.util.ArrayList;

public class CategoryViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private ArrayList<String> mainCategoryList;

    public CategoryViewPagerAdapter(FragmentManager fm, Context context, ArrayList<String> mainCategoryList) {
        super(fm);
        mContext=context;
        this.mainCategoryList= mainCategoryList;

    }


    @Override
    public Fragment getItem(int position) {


        Fragment fragment=null;

        switch (position)
        {
            default:

                fragment=new CategoryFragment().newInstance(mainCategoryList);


        }

        return fragment;

    }

    @Override
    public int getCount() {
        return mainCategoryList.size();
    }
}
