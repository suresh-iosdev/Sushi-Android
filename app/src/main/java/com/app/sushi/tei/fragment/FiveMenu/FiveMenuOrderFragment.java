package com.app.sushi.tei.fragment.FiveMenu;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.app.sushi.tei.GlobalMembers.GlobalValues;
import com.app.sushi.tei.Utils.Utility;
import com.app.sushi.tei.adapter.Order.BentoOrderAdapter;
import com.app.sushi.tei.adapter.Order.OrderAdapter;
import com.app.sushi.tei.R;

/**
 * Created by root on 18/4/18.
 */

public class FiveMenuOrderFragment extends Fragment {

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

        orderlayout = (RelativeLayout) mView.findViewById(R.id.orderlayout);
        layoutBento = (RelativeLayout) mView.findViewById(R.id.layoutBento);
        layoutBentoParent = (LinearLayout) mView.findViewById(R.id.layoutBentoParent);
        layoutDelivery = (LinearLayout) mView.findViewById(R.id.layoutDelivery);

        orderDetailLayout = (RelativeLayout) mView.findViewById(R.id.orderDetailLayout);
        orderCountTextView = (TextView) mView.findViewById(R.id.orderCountTextView);
        txtBentoCount = (TextView) mView.findViewById(R.id.txtBentoCount);
        orderBadgeLayout = (RelativeLayout) mView.findViewById(R.id.orderBadgeLayout);

        orderviewPager = (ViewPager) mView.findViewById(R.id.pager);
        ordertabLayout = (TabLayout) mView.findViewById(R.id.tabLayout);

        bentoviewPager = (ViewPager) mView.findViewById(R.id.bentopager);
        bentotabLayout = (TabLayout) mView.findViewById(R.id.bentotabLayout);


        view1 = (View) mView.findViewById(R.id.view1);
        view3 = (View) mView.findViewById(R.id.view3);
        view1.setVisibility(View.VISIBLE);


        bentoview1 = (View) mView.findViewById(R.id.bentoview1);
        bentoview3 = (View) mView.findViewById(R.id.bentoview3);
        bentoview1.setVisibility(View.VISIBLE);

        setTabItem();
        setBentoTabItem();

        orderviewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(ordertabLayout));
        bentoviewPager.setOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(bentotabLayout));

//        ordertabLayout.setupWithViewPager(orderviewPager);

        orderlayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                orderDetailLayout.setVisibility(View.VISIBLE);

                orderlayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));
                layoutBento.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrderunselection));

                //reservationImg.setImageResource(R.drawable.cal_grey);
                reseravationtxt.setTextColor(Color.parseColor("#ACA9A9"));
                reseravationlayout.setBackgroundColor(Color.parseColor("#EEECED"));

                //cateringImg.setImageResource(R.drawable.cat_grey);
                cateringtxt.setTextColor(Color.parseColor("#ACA9A9"));
                carteinglayout.setBackgroundColor(Color.parseColor("#EEECED"));

                orderviewPager.setVisibility(View.VISIBLE);
                ordertabLayout.setVisibility(View.VISIBLE);

                layoutDelivery.setVisibility(View.VISIBLE);
                layoutBentoParent.setVisibility(View.GONE);


            }
        });

        layoutBento.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                orderlayout.setBackgroundColor(mContext.getResources().getColor(R.color.colorOrderunselection));
                layoutBento.setBackgroundColor(mContext.getResources().getColor(R.color.colorBlack));

                layoutDelivery.setVisibility(View.GONE);
                layoutBentoParent.setVisibility(View.VISIBLE);


            }
        });


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
            public void onTabReselected(TabLayout.Tab tab) {

            }
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

        bentotabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {

                bentoviewPager.setCurrentItem(tab.getPosition(), true);

                switch (tab.getPosition()) {

                    case 0:
                        txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
                        bentoview1.setVisibility(View.VISIBLE);
                        bentoview3.setVisibility(View.GONE);
                        break;

                    case 1:
                        txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
                        bentoview1.setVisibility(View.GONE);
                        bentoview3.setVisibility(View.VISIBLE);
                        break;

                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });


        bentoviewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                switch (position) {

                    case 0:
                        bentoview1.setVisibility(View.VISIBLE);
                        bentoview3.setVisibility(View.GONE);
                        break;

                    case 1:
                        bentoview1.setVisibility(View.GONE);
                        bentoview3.setVisibility(View.VISIBLE);
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
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }
            if (k == 0) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }


            ordertabLayout.addTab(ordertabLayout.newTab().setCustomView(tabView));
        }

        orderAdapter = new OrderAdapter(getChildFragmentManager());
        orderviewPager.setAdapter(orderAdapter);

    }


    private void setBentoTabItem() {

        for (int k = 0; k < ordertabs.length; k++) {

            View tabView = LayoutInflater.from(mContext).inflate(R.layout.layout_tab_orders_item, null);

            txtTabItem = (TextView) tabView.findViewById(R.id.txtTabItem_order);

            txtTabItem.setText(ordertabs[k]);
            if (k == 0) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }

            if (k == 1) {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorSelectedTab));
            } else {
                txtTabItem.setTextColor(getResources().getColor(R.color.colorUnSelectedTab));
            }

            bentotabLayout.addTab(bentotabLayout.newTab().setCustomView(tabView));
        }

        bentoOrderAdapter = new BentoOrderAdapter(getChildFragmentManager());
        bentoviewPager.setAdapter(bentoOrderAdapter);


    }

    @Override
    public void onResume() {
        super.onResume();

        orderAdapter.notifyDataSetChanged();
        bentoOrderAdapter.notifyDataSetChanged();
    }
}