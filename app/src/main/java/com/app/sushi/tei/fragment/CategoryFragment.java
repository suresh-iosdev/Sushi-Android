package com.app.sushi.tei.fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.SubCategoryActivity;
import com.app.sushi.tei.adapter.CategoryRecyclerAdapter;

import java.util.ArrayList;

/**
 * Created by root on 15/3/18.
 */

public class CategoryFragment extends Fragment {

    private Activity mContext;
    private View mView;
    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private CategoryRecyclerAdapter asianCategoryRecyclerAdapter;

    private ArrayList<String> subCategoryArrayList;
    Bundle bundle;

    public static CategoryFragment newInstance(ArrayList<String> subCategoryArrayList) {
        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();
        args.putStringArrayList("subcategory", subCategoryArrayList);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        bundle = getArguments();

        if (bundle != null) {
            subCategoryArrayList = bundle.getStringArrayList("subcategory");

        }

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        mView = inflater.inflate(R.layout.fragment_category, container, false);

        mRecyclerView = (RecyclerView) mView.findViewById(R.id.mRecyclerView);

        layoutManager = new LinearLayoutManager(mContext);
        mRecyclerView.setLayoutManager(layoutManager);

        //asianCategoryRecyclerAdapter = new CategoryRecyclerAdapter(mContext, subCategoryArrayList);
        mRecyclerView.setAdapter(asianCategoryRecyclerAdapter);
        mRecyclerView.setItemAnimator(new DefaultItemAnimator());
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setNestedScrollingEnabled(false);

        asianCategoryRecyclerAdapter.setonItemClick(new CategoryRecyclerAdapter.IOnItemClick() {
            @Override
            public void onItemClick(View v, int position)
            {
                Intent intent = new Intent(mContext, SubCategoryActivity.class);
                mContext.startActivity(intent);


//                    if(subCategoryArrayList.get(position).get)  outletid

               /*     GlobalValues.CURRENT_OUTLET_ID = Utility.readFromSharedPreference(mContext, GlobalValues.OUTLET_ID);
                    GlobalValues.CURRENT_AVAILABLITY_ID = Utility.readFromSharedPreference(mContext,
                            GlobalValues.AVALABILITY_ID);

                    mSubCategorySlug = subCategoryArrayList.get(position).getmSubCategorySlug();

                    String url = GlobalUrl.PRODUCT_URL + "?app_id=" + GlobalValues.APP_ID +
                            "&availability=" + GlobalValues.CURRENT_AVAILABLITY_ID +
                            "&outletId=" + GlobalValues.CURRENT_OUTLET_ID + "&category_slug=" + mCategorySlug +
                            "&subcate_slug=" + mSubCategorySlug;

                    Intent intent = new Intent(mContext, ProductListActivity.class);
                    intent.putExtra("product_url", url);
                    intent.putExtra("category_name", subCategoryArrayList.get(position).getmSubCategoryName());
                    intent.putExtra("subcate_slug", mSubCategorySlug);
                    intent.putExtra("subcate_image", subCategoryArrayList.get(position).getmBasePath()+
                            subCategoryArrayList.get(position).getGetmSubCategoryImage());
                    mContext.startActivity(intent);*/

                }
        });

        return mView;
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }


}


