package com.app.sushi.tei.adapter;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.app.sushi.tei.R;

import java.util.List;

public class TimeAdapter extends RecyclerView.Adapter<TimeAdapter.ViewHolder>{

    private Context context;
    private List<String> timeList;
    static OnItemClickListener mitemclicklistener;

    public TimeAdapter(Context context, List<String> timeList) {
        this.context = context;
        this.timeList = timeList;

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_time_item,parent,false);
        TimeAdapter.ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {

        try
        {


            holder.timeText.setText(timeList.get(position));

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return timeList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener
    {

        private TextView timeText;


        public ViewHolder(View itemView) {
            super(itemView);

            timeText = (TextView) itemView.findViewById(R.id.timeText);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if (mitemclicklistener != null) {
                mitemclicklistener.onItemClick(v, getPosition());
            }

        }
    }
    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public void SetOnItemClickListioner(final OnItemClickListener mItemClickListener) {
        this.mitemclicklistener = mItemClickListener;
    }

}
