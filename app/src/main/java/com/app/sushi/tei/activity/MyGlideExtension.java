package com.app.sushi.tei.activity;

import android.content.Context;
import androidx.annotation.NonNull;
import android.util.DisplayMetrics;

import com.bumptech.glide.annotation.GlideExtension;
import com.bumptech.glide.annotation.GlideOption;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestOptions;

@GlideExtension
public class MyGlideExtension {

    private MyGlideExtension() {}

   /* @NonNull
    @GlideOption
    public static RequestOptions roundedCorners(RequestOptions options, @NonNull Context context, int cornerRadius) {
        int px = Math.round(cornerRadius * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
        if (px==0){
            px=1;
        }
        return options.transforms(new RoundedCorners(px));
    }*/

    @NonNull
    @GlideOption
    public static BaseRequestOptions<?> roundedCorners(BaseRequestOptions<?> options, @NonNull Context context, int cornerRadius) {
        int px = Math.round(cornerRadius * (context.getResources().getDisplayMetrics().xdpi / DisplayMetrics.DENSITY_DEFAULT));
        if (px==0){
            px=1;
        }
        return options.transforms(new RoundedCorners(px));
    }
}
