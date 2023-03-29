package com.app.sushi.tei.activity;

import android.content.Context;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import android.util.Log;

import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.fragment.subcategory.SubcategoryFragment;

import java.util.ArrayList;

public class FragmentAdapter extends FragmentStatePagerAdapter {

    ArrayList<MainCategory> categoryList;
    private Context mContext;
    private String mCategorySlug;


    public FragmentAdapter(FragmentManager supportFragmentManager, Context context, ArrayList<MainCategory> categoryList) {
        super(supportFragmentManager);
        this.mContext = context;
        this.categoryList = categoryList;
    }


    @Override
    public Fragment getItem(int position) {

        Fragment fragment = null;
        switch (position) {

            default:
                mCategorySlug = categoryList.get(position).getmCategorySlug();
                fragment = new SubcategoryFragment();
                Bundle bundle4 = new Bundle();
                bundle4.putString("categorySlug", mCategorySlug);
                bundle4.putParcelableArrayList("arrayList", categoryList);
                fragment.setArguments(bundle4);

                break;


        }


        return fragment;


    }


    @Override
    public int getCount() {
        return categoryList.size();
    }

//    public Fragment getVisibleFragment(){
//        FragmentManager fragmentManager = MainActivity.this.getSupportFragmentManager();
//        List<Fragment> fragments = fragmentManager.getFragments();
//        if(fragments != null){
//            for(Fragment fragment : fragments){
//                if(fragment != null && fragment.isVisible())
//                    return fragment;
//            }
//        }
//        return null;
//    }
}


