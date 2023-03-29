package com.app.sushi.tei.adapter.Order;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.fragment.Order.CurrentOrderFragments;
import com.app.sushi.tei.fragment.Order.PastOrderFragments;

public class BentoOrderAdapter extends FragmentPagerAdapter {

    private String[] ordertabs = {"Current Orders", "Past Orders"};

    public BentoOrderAdapter(FragmentManager fm) {
        super(fm);

    }



    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:

                return CurrentOrderFragments.newInstance("C", GlobalValues.BENTO_ID);
            case 1:
                return  PastOrderFragments.newInstance("P", GlobalValues.BENTO_ID);
        }

        return null;
    }

    @Override
    public int getCount() {
        return ordertabs.length;
    }

}
