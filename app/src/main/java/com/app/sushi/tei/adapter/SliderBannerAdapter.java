package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.sushi.tei.Model.BannerModel;
import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.MyGlideExtension;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class SliderBannerAdapter extends PagerAdapter {

    private List<BannerModel> bannerModelList;
    private Context context;
    private int type;

    public SliderBannerAdapter(int type, List<BannerModel> bannerModelList, Context context) {
        this.type = type;
        this.bannerModelList = bannerModelList;
        this.context = context;
    }

    @Override
    public int getCount() {
        return bannerModelList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
        return view == o;
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view= layoutInflater.inflate(R.layout.view_pager_banner,null);
        ImageView imageView=view.findViewById(R.id.SlideimageView);

        if (type == 0) {
            String imageurl = bannerModelList.get(position).getBannerImage();
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            if (imageurl != null && imageurl.length() > 0) {
                /*Picasso.with(context)
                        .load(imageurl)
                        .fit()
                        .centerCrop()
                        .error(R.drawable.place_holder_sushi_tei)
                        .into(imageView);*/

                Glide.with(context)
                        .load(imageurl)
                        .apply(MyGlideExtension.roundedCorners(new RequestOptions(), context, 15))
                        .into(imageView);
            } else {
                imageView.setImageResource(R.drawable.place_holder_sushi_tei);
            }
        } else {
            imageView.setImageResource(bannerModelList.get(position).getImage());
        }
        ViewPager vp=(ViewPager)container;
        vp.addView(view,0);
        return view;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewPager vp = (ViewPager)container;
        View view = (View)object;
        vp.removeView(view);
    }
}
