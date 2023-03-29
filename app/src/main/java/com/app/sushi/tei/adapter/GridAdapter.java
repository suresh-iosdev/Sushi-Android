package com.app.sushi.tei.adapter;

/**
 * Created by root on 30/1/18.
 */

import android.content.Context;
import android.graphics.Color;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.app.sushi.tei.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import static com.app.sushi.tei.activity.OrderSummaryActivity.currentday;
import static com.app.sushi.tei.activity.OrderSummaryActivity.maxDate;


public class GridAdapter extends ArrayAdapter {
    private static final String TAG = GridAdapter.class.getSimpleName();
    private LayoutInflater mInflater;
    private List<Date> monthlyDates;
    private Calendar currentDate;
    Date date;
    Context context;
    int pos = -1;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");


    public GridAdapter(Context context, List<Date> monthlyDates, Calendar currentDate, Date date) {
        super(context, R.layout.single_cell_layout);
        this.monthlyDates = monthlyDates;
        this.currentDate = currentDate;
        this.date = date;
        mInflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Date mDate = monthlyDates.get(position);
        Calendar dateCal = Calendar.getInstance();
        dateCal.setTime(mDate);
        int dayValue = dateCal.get(Calendar.DAY_OF_MONTH);
        int displayMonth = dateCal.get(Calendar.MONTH) + 1;
        int displayYear = dateCal.get(Calendar.YEAR);
        int currentMonth = currentDate.get(Calendar.MONTH) + 1;
        int currentYear = currentDate.get(Calendar.YEAR);
        View view = convertView;

        if (view == null) {
            view = mInflater.inflate(R.layout.single_cell_layout, parent, false);
        }

        //Add day to calendar
        TextView cellNumber = (TextView) view.findViewById(R.id.calendar_date_id);
        cellNumber.setText(String.valueOf(dayValue));
        //Add events to the calendar


        if (displayMonth == currentMonth && displayYear == currentYear) {
            //  view.setBackgroundColor(Color.parseColor("#FF5733"));
            cellNumber.setVisibility(View.VISIBLE);
            if (pos == position) {
                cellNumber.setBackgroundResource(R.drawable.selected_date_shape);
                cellNumber.setTextColor(context.getResources().getColor(R.color.colorWhite));
            } else if (date != null) {
                if (date.equals(mDate)) {
                    cellNumber.setBackgroundResource(R.drawable.selected_date_shape);
                    cellNumber.setTextColor(context.getResources().getColor(R.color.colorWhite));
                } else {
                    cellNumber.setBackground(null);
                    cellNumber.setTextColor(context.getResources().getColor(R.color.colorBlack));
                }
            } else {
                cellNumber.setBackground(null);
                cellNumber.setTextColor(context.getResources().getColor(R.color.colorBlack));
            }

        } else {
//            view.setBackgroundColor(Color.parseColor("#a0a0a0"));
            cellNumber.setVisibility(View.INVISIBLE);


        }

        try {
            Date mmonthlyDates = formatter.parse(formatter.format(monthlyDates.get(position)));
            Date mmaxDate = formatter.parse(formatter.format(maxDate));
            currentday = formatter.parse(formatter.format(currentday));

            if (mmonthlyDates.equals(currentday)) {

                if (pos != position) {
                    cellNumber.setTextColor(context.getResources().getColor(R.color.colorBlack));
                }
            } else if (mmonthlyDates.after(mmaxDate) || mmonthlyDates.before(currentday)) {
                // In between

                cellNumber.setTextColor(Color.parseColor("#a0a0a0"));
            } else {

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


        return view;
    }

    @Override
    public int getCount() {
        return monthlyDates.size();
    }

    @Nullable
    @Override
    public Object getItem(int position) {
        return monthlyDates.get(position);
    }

    @Override
    public int getPosition(Object item) {
        return monthlyDates.indexOf(item);
    }

    public void updateView(int pos) {
        this.pos = pos;
        this.date = null;
        notifyDataSetChanged();
    }
}
