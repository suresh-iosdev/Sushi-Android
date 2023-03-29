package com.app.sushi.tei.adapter.Promotion;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.app.sushi.tei.fragment.Promotion.PromotionEarnedFragment;
import com.app.sushi.tei.fragment.Promotion.PromotionRedeemedFragment;


public class PromotionPagerAdapter extends FragmentPagerAdapter {

    private String[] promotionTabs = {"Promo Earned", "Promo Redeemed"};

    int from;

    public PromotionPagerAdapter(FragmentManager fm, int from) {
        super(fm);

        this.from = from;
    }

    @Override
    public Fragment getItem(int index) {

        switch (index) {
            case 0:
                //return new PromotionFragment(from);
                return  PromotionEarnedFragment.newInstance(from);

            case 1:
                return new PromotionRedeemedFragment();
        }

        return null;
    }

    @Override
    public int getCount() {
        return promotionTabs.length;
    }

}
