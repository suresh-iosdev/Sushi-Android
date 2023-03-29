package com.app.sushi.tei.fragment;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.Model.Home.MainCategory;
import com.app.sushi.tei.R;

import java.util.ArrayList;


public class Fragmentpage extends Fragment {

    private Activity mContext;


    Bundle bundle;

    private String mCategoryBasePath = "";
    private String mSubCategoryBasePath = "";

    public Fragment newInstance(ArrayList<MainCategory> categoryList, int mPosition, int positions) {
        Fragmentpage fragment = new Fragmentpage();
        Bundle args = new Bundle();
        args.putParcelableArrayList("categoryList", categoryList);
        args.putInt("position", positions);
        fragment.setArguments(args);
        return fragment;

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);




    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        mContext = activity;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

      View mView = inflater.inflate(R.layout.adapterlayout, container, false);

        Bundle bundle=getArguments();
        int textValues=bundle.getInt("pageNumber");

        TextView text = mView.findViewById(R.id.values);
        text.setText("Page "+ String.valueOf(textValues));


        return mView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


    }
}

