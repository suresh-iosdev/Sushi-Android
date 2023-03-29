package com.app.sushi.tei.Model.Notification;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 29/5/18.
 */

public class SpizeNotification  implements Parcelable {


    private String mId="";

    private String mCustomerId="";

    private int mReadStatus=0;

    private String mTitle="";

    private String mShortContent="";

    private String mContent="";

    private String mCreatedOn="";

    private String mImage="";

    private String mRedirect="";

    private String mBasePath="";


    public SpizeNotification()
    {

    }

    protected SpizeNotification(Parcel in) {
        mId = in.readString();
        mCustomerId = in.readString();
        mReadStatus = in.readInt();
        mTitle = in.readString();
        mShortContent = in.readString();
        mContent = in.readString();
        mCreatedOn = in.readString();
        mImage = in.readString();
        mRedirect = in.readString();
        mBasePath = in.readString();
    }

    public static final Creator<SpizeNotification> CREATOR = new Creator<SpizeNotification>() {
        @Override
        public SpizeNotification createFromParcel(Parcel in) {
            return new SpizeNotification(in);
        }

        @Override
        public SpizeNotification[] newArray(int size) {
            return new SpizeNotification[size];
        }
    };

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmCustomerId() {
        return mCustomerId;
    }

    public void setmCustomerId(String mCustomerId) {
        this.mCustomerId = mCustomerId;
    }

    public int getmReadStatus() {
        return mReadStatus;
    }

    public void setmReadStatus(int mReadStatus) {
        this.mReadStatus = mReadStatus;
    }

    public String getmTitle() {
        return mTitle;
    }

    public void setmTitle(String mTitle) {
        this.mTitle = mTitle;
    }

    public String getmShortContent() {
        return mShortContent;
    }

    public void setmShortContent(String mShortContent) {
        this.mShortContent = mShortContent;
    }

    public String getmContent() {
        return mContent;
    }

    public void setmContent(String mContent) {
        this.mContent = mContent;
    }

    public String getmCreatedOn() {
        return mCreatedOn;
    }

    public void setmCreatedOn(String mCreatedOn) {
        this.mCreatedOn = mCreatedOn;
    }

    public String getmImage() {
        return mImage;
    }

    public void setmImage(String mImage) {
        this.mImage = mImage;
    }

    public String getmRedirect() {
        return mRedirect;
    }

    public void setmRedirect(String mRedirect) {
        this.mRedirect = mRedirect;
    }

    public String getmBasePath() {
        return mBasePath;
    }

    public void setmBasePath(String mBasePath) {
        this.mBasePath = mBasePath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mId);
        dest.writeString(mCustomerId);
        dest.writeInt(mReadStatus);
        dest.writeString(mTitle);
        dest.writeString(mShortContent);
        dest.writeString(mContent);
        dest.writeString(mCreatedOn);
        dest.writeString(mImage);
        dest.writeString(mRedirect);
        dest.writeString(mBasePath);
    }
}


