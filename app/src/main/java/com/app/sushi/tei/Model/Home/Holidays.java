package com.app.sushi.tei.Model.Home;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by root on 29/3/18.
 */

public class Holidays implements Serializable, Parcelable {

    private String mId = "";

    private String mTitle = "";

    private String mDescription = "";

    private String mDate;

    public Holidays()
    {

    }

    protected Holidays(Parcel in) {
        mId = in.readString();
        mTitle = in.readString();
        mDescription = in.readString();
//        mDate = (java.util.Date) in.readSerializable();
        mDate = in.readString();

    }

    public static final Creator<Holidays> CREATOR = new Creator<Holidays>() {
        @Override
        public Holidays createFromParcel(Parcel in) {
            return new Holidays(in);
        }

        @Override
        public Holidays[] newArray(int size) {
            return new Holidays[size];
        }
    };

    public String getmId() {
        return mId;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmDescription() {
        return mDescription;
    }

    public void setmDescription(String mDescription) {
        this.mDescription = mDescription;
    }

    public String getmDate() {
        return mDate;
    }

    public void setmDate(String mDate) {

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        try {

            date=formatter.parse(mDate);
            mDate=date.toString();


        } catch (ParseException e) {

            e.printStackTrace();

        }
        this.mDate = mDate;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mTitle);
        dest.writeString(mDescription);
        dest.writeSerializable(mDate);
    }
}
