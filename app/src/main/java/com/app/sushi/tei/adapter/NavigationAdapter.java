package com.app.sushi.tei.adapter;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.Model.NavigateMenu;
import com.app.sushi.tei.R;
import com.app.sushi.tei.activity.CmsActivity;
import com.app.sushi.tei.activity.ContactUsActivity;

import java.util.ArrayList;

/**
 * Created by root on 27/12/17.
 */

public class NavigationAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    static Activity activity;
    static OnItemClickListener mItemClickListener;
    ArrayList<NavigateMenu> navigateMenuArrayList;


    public NavigationAdapter(Activity activity, ArrayList<NavigateMenu> navigateMenuArrayList) {

        this.navigateMenuArrayList = navigateMenuArrayList;
        this.activity = activity;

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.layout_aboutus_item, parent, false);

        VHItem dataObjectHolder = new VHItem(view);

        return dataObjectHolder;


    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {


        final VHItem vhItemHolder = (VHItem) holder;

        vhItemHolder.TxtName.setText(navigateMenuArrayList.get(position).getNav_title());

        vhItemHolder.setItem(navigateMenuArrayList.get(position));


    }

    @Override
    public int getItemCount() {
        return navigateMenuArrayList.size();
    }

    public static class VHItem extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView TxtName;
        NavigateMenu navigateMenu;

        public VHItem(View itemView) {
            super(itemView);

            itemView.setOnClickListener(this);
            TxtName = (TextView) itemView.findViewById(R.id.TxtName);

        }


        public void setItem(NavigateMenu navigateMenu) {
            this.navigateMenu = navigateMenu;
        }

        @Override
        public void onClick(View v) {

            if (navigateMenu.getNav_title().equalsIgnoreCase("Contact Us")) {
                Intent intent = new Intent(activity, ContactUsActivity.class);
                activity.startActivity(intent);
            } else if (navigateMenu.getNav_type().equals("0")) {
                Intent intent = new Intent(activity, CmsActivity.class);
                intent.putExtra("TITLE", navigateMenu.getNav_title());
                intent.putExtra("SEARCH_KEY", navigateMenu.getNav_title_slug());

                activity.startActivity(intent);
            } else if (navigateMenu.getNav_type().equals("3")) {

                try {
                    String url = navigateMenu.getNav_pages_mobile();
                    if (url != null) {
                        Intent i = new Intent(Intent.ACTION_VIEW);
                        i.setData(Uri.parse(url));
                        activity.startActivity(i);
                    } else {

                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }


            if (mItemClickListener != null) {
                mItemClickListener.onItemClick(v, getPosition());
            }
        }

    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


}
