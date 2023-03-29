package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.viewpager.widget.PagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.app.sushi.tei.Model.banner.ResultSetItem;
import com.app.sushi.tei.R;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class SlidingImageAdapter extends PagerAdapter {
    Context mContext;
    private LayoutInflater layoutInflater;
    private ArrayList<ResultSetItem> viewpager;
    private String imageURL;

    public SlidingImageAdapter(Context context, ArrayList<ResultSetItem> viewpager, String imageUrl) {
        this.mContext = context;
        this.viewpager=viewpager;
        this.imageURL = imageUrl;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view ==  object;
    }

    @NotNull
    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        layoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = layoutInflater.inflate(R.layout.adapter_slidingimages, null);
        ImageView imageView = view.findViewById(R.id.slidingImg);
        TextView textview_viewpager = view.findViewById(R.id.textview_viewpager);
         //Glide.with(mContext).load(imageURL + viewpager.get(position).getBannerImage()).error(R.drawable.default_image).into(imageView);
      //  Picasso.with(mContext).load(imageURL + viewpager.get(position).getBannerImage()).error(R.drawable.default_image).into(imageView);
        //textview_viewpager.setText(viewpager_text[position]);
        container.addView(view, 0);
        return view;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((View) object);
    }

    @Override
    public int getCount() {
        return viewpager.size();
    }
}