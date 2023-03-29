package com.app.sushi.tei.adapter.Bento;

import android.content.Context;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.fragment.BentosFragment.BentoProductFragment;

import java.util.ArrayList;


public class BentoCategoryViewPagerAdapter extends FragmentStatePagerAdapter {

    private Context mContext;
    private ArrayList<MainCategory> mainCategoryList;

    public BentoCategoryViewPagerAdapter(FragmentManager fm, Context context, ArrayList<MainCategory> mainCategoryList) {
        super(fm);
        mContext=context;
        this.mainCategoryList= (ArrayList<MainCategory>) mainCategoryList;
    }


    @Override
    public Fragment getItem(int position) {


        Fragment fragment=null;

        switch (position)
        {
            default:

                fragment=new BentoProductFragment().newInstance(mainCategoryList.get(position).getSubCategoryList(),
                                    mainCategoryList.get(position).getmCategorySlug());


        }

        return fragment;

    }

    @Override
    public int getCount() {
        return mainCategoryList.size();
    }
}
