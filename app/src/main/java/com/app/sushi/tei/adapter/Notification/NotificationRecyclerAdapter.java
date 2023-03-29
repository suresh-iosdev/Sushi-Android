package com.app.sushi.tei.adapter.Notification;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.app.sushi.tei.Interface.IOnItemClick;
import com.app.sushi.tei.Model.Notification.SpizeNotification;
import com.app.sushi.tei.R;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

/**
 * Created by root on 29/5/18.
 */

public class NotificationRecyclerAdapter extends RecyclerView.Adapter<NotificationRecyclerAdapter.NotificationHolder> {

    private Context mContext;
    private List<SpizeNotification> notificationList;
    private IOnItemClick iOnItemClick;


    public NotificationRecyclerAdapter(Context mContext, List<SpizeNotification> spizeNotificationList) {
        this.mContext = mContext;
        notificationList = spizeNotificationList;
    }

    @Override
    public NotificationHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext).inflate(R.layout.layout_notification_item, parent, false);

        NotificationHolder notificationHolder = new NotificationHolder(view);

        return notificationHolder;
    }

    @Override
    public void onBindViewHolder(NotificationHolder holder, int position) {


        if (notificationList.get(position).getmReadStatus() == 0) {
            holder.gotIt.setBackgroundResource(R.drawable.asset54);
            holder.layoutParent.setBackgroundResource(R.drawable.notification_unread_shape);

        } else if (notificationList.get(position).getmReadStatus() == 1) {

             holder.layoutParent.setBackgroundResource(R.drawable.notification_background_shape);
            holder.gotIt.setBackgroundResource(R.drawable.asset_78);

        }

        String data =  notificationList.get(position).getmShortContent();

        holder.txtShortContent.setText(Html.fromHtml(data)); //

        holder.txtContent.setText(notificationList.get(position).getmContent());

        Picasso.with(mContext).load(notificationList.get(position).getmBasePath() +
                notificationList.get(position).getmImage()).error(R.drawable.default_image).into(holder.imgIcon);


        if (notificationList.get(position).getmCreatedOn() != null) {

            Calendar cal = Calendar.getInstance();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            try {

                cal.setTime(sdf.parse(notificationList.get(position).getmCreatedOn()));// all done

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MMM-yyyy");


                SimpleDateFormat simpleDatetimess = new SimpleDateFormat("hh:mm a");

                holder.txtDate.setText(simpleDateFormat.format(cal.getTime()) + " | " + simpleDatetimess.format(cal.getTime()));

            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getItemCount() {
        return notificationList.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView txtGotIt, txtShortContent, txtContent, txtDate;
        private LinearLayout layoutParent;
        private ImageView imgIcon,gotIt;

        public NotificationHolder(View itemView) {
            super(itemView);

            txtGotIt = (TextView) itemView.findViewById(R.id.txtGotIt);
            gotIt= (ImageView) itemView.findViewById(R.id.gotIt);

            txtShortContent = (TextView) itemView.findViewById(R.id.txtShortContent);
            txtContent = (TextView) itemView.findViewById(R.id.txtContent);
            txtDate = (TextView) itemView.findViewById(R.id.txtDate);
            layoutParent = (LinearLayout) itemView.findViewById(R.id.layoutParent);
            imgIcon = (ImageView) itemView.findViewById(R.id.imgIcon);

            layoutParent.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            if (iOnItemClick != null) {
                iOnItemClick.onItemClick(v, getPosition());
            }
        }
    }

    public void setOnClick(IOnItemClick click) {
        this.iOnItemClick = click;
    }
}
