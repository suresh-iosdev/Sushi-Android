package com.app.sushi.tei.fragment.subcategory;

import android.app.Activity;
import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.app.sushi.tei.R;


public class Fragmentpage1 extends Fragment {

    private Activity mContext;
    ListView listView;
    TextView textView;
    String[] mobileArray = {"Android","IPhone","WindowsMobile","Blackberry",
            "WebOS","Ubuntu","Windows7","Max OS X"};


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

      View mView = inflater.inflate(R.layout.adapterlayout1, container, false);

        Bundle bundle=getArguments();
        int textValues=bundle.getInt("pageNumber");

        ArrayAdapter adapter = new ArrayAdapter<String>(getActivity(),
               android.R.layout.simple_expandable_list_item_1, mobileArray);

        ListView listView = (ListView)mView.findViewById(R.id.listView);
        listView.setAdapter(adapter);

        return mView;
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);


    }
}

