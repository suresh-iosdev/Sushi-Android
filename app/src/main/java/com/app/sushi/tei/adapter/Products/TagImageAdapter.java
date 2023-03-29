package com.app.sushi.tei.adapter.Products;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.app.sushi.tei.Model.ProductList.TagImageModel;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class TagImageAdapter extends RecyclerView.Adapter<TagImageAdapter.TagHolder> {
    private Context context;
    ArrayList<TagImageModel> tagimageList;

    public TagImageAdapter(Context context, ArrayList<TagImageModel> tagimageList) {
        this.context=context;
        this.tagimageList=tagimageList;
    }

    @NonNull
    @Override
    public TagHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewtype) {
        View view=LayoutInflater.from(context).inflate(R.layout.tagimageview,parent,false);
        return new TagHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TagHolder tagHolder, int pos) {
        String imageurl = tagimageList.get(pos).getPro_tag_image();

        if (imageurl != null && imageurl.length() > 0) {

            Picasso.with(context).load(imageurl).into(tagHolder.tagimage);

        }
    }

    @Override
    public int getItemCount() {
        return tagimageList.size();
    }

    public class TagHolder extends RecyclerView.ViewHolder {
        ImageView tagimage;
        public TagHolder(@NonNull View itemView) {
            super(itemView);

             tagimage=itemView.findViewById(R.id.tagimage);
        }
    }
}
