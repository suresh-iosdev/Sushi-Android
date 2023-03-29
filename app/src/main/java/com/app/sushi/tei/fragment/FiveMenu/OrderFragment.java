package com.app.sushi.tei.fragment.FiveMenu;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import com.google.android.material.tabs.TabLayout;
import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.R;
import com.app.sushi.tei.adapter.Order.BentoOrderAdapter;
import com.app.sushi.tei.adapter.Order.OrderAdapter;

public class OrderFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private ViewPager orderviewPager, reservationPager, cateringPager;
    private ViewPager bentoviewPager;
    private TabLayout ordertabLayout, reservationTabLayout, cateringTabLayout;
    private TabLayout bentotabLayout;
    private OrderAdapter orderAdapter;
    private BentoOrderAdapter bentoOrderAdapter;
    private LinearLayout layoutBentoParent, layoutDelivery;
    private TextView txtCartCount;
    private String[] ordertabs = {"Current Orders", "Past Orders"};


    public RelativeLayout orderlayout, layoutBento, carteinglayout, reseravationlayout,
            orderDetailLayout, reservationDetailLayout, cateringDetailLayout,
            cateringOrderBadgeLayout, reservationOrderBadgeLayout, orderBadgeLayout;

    ImageView orderimg, reservationImg, cateringImg, backImg;
    private View view1, view3;
    private View bentoview1, bentoview3;
    TextView cateringtxt, reseravationtxt, ordertxt;
    public static TextView orderCountTextView, txtBentoCount, reservationOrderCountTextView, cateringOrderCountTextView;
    private String cartCount = "";
    private Intent intent;
    private TextView txtTabItem;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mView = inflater.inflate(R.layout.fragment_order, container, false);

        orderlayout =  mView.findViewById(R.id.orderlayout);
        layoutDelivery =  mView.findViewById(R.id.layoutDelivery);

        orderDetailLayout =  mView.findViewById(R.id.orderDetailLayout);
        orderCountTextView =  mView.findViewById(R.id.orderCountTextView);
        orderBadgeLayout =  mView.findViewById(R.id.orderBadgeLayout);

        orderviewPager =  mView.findViewById(R.id.pager);
        ordertabLayout =  mView.findViewById(R.id.tabLayout);

        setTabItem();

        orderviewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(ordertabLayout));


        ordertabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_order);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
                orderviewPager.setCurrentItem(tab.getPosition(), true);

                switch (tab.getPosition()) {

                    case 0:

                        view1.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.GONE);
                        break;

                    case 1:

                        view1.setVisibility(View.GONE);
                        view3.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                View view = tab.getCustomView();
                assert view != null;
                TextView txtTabItem = view.findViewById(R.id.txtTabItem_order);
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });

        orderviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {

                    case 0:
                        view1.setVisibility(View.VISIBLE);
                        view3.setVisibility(View.GONE);
                        break;

                    case 1:
                        view1.setVisibility(View.GONE);
                        view3.setVisibility(View.VISIBLE);
                        break;
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });


        return mView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    private void setTabItem() {

        for (int k = 0; k < ordertabs.length; k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_orders_item, null);

            txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_order);

            txtTabItem.setText(ordertabs[k]);

            if (k == 1) {
                txtTabItem.setTextColor(getResources().getColor(R.color.text_color));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.text_color));
            }
            if (k == 0) {
                txtTabItem.setTextColor(getResources().getColor(R.color.text_color));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.text_color));
            }
            ordertabLayout.addTab(ordertabLayout.newTab().setCustomView(tabView));
        }

        orderAdapter = new OrderAdapter(getChildFragmentManager());
        orderviewPager.setAdapter(orderAdapter);

    }

    @Override
    public void onResume() {
        super.onResume();

        orderAdapter.notifyDataSetChanged();
        //bentoOrderAdapter.notifyDataSetChanged();
    }

}
