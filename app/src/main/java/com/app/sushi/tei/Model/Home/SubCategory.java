package com.app.sushi.tei.Model.Home;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by root on 28/3/18.
 */

public class SubCategory implements Serializable, Parcelable {

    private String mBasePath="";

    private String mSubCategoryName = "";

    private String getmSubCategoryImage = "";

    private String mSubCategorySlug="";

    private String mSubCategoryProductDescription="";

    public String getmSubCategoryProductDescription() {
        return mSubCategoryProductDescription;
    }

    public void setmSubCategoryProductDescription(String mSubCategoryProductDescription) {
        this.mSubCategoryProductDescription = mSubCategoryProductDescription;
    }

    public SubCategory() {

    }

    protected SubCategory(Parcel in) {
        mSubCategoryName = in.readString();
        getmSubCategoryImage = in.readString();
        mBasePath = in.readString();
        mSubCategorySlug = in.readString();
    }

    public String getmSubCategorySlug() {
        return mSubCategorySlug;
    }

    public void setmSubCategorySlug(String mSubCategorySlug) {
        this.mSubCategorySlug = mSubCategorySlug;
    }

    public String getmBasePath() {
        return mBasePath;
    }

    public void setmBasePath(String mBasePath) {
        this.mBasePath = mBasePath;
    }

    public static final Creator<SubCategory> CREATOR = new Creator<SubCategory>() {
        @Override
        public SubCategory createFromParcel(Parcel in) {
            return new SubCategory(in);
        }

        @Override
        public SubCategory[] newArray(int size) {
            return new SubCategory[size];
        }
    };

    public String getmSubCategoryName() {
        return mSubCategoryName;
    }

    public void setmSubCategoryName(String mSubCategoryName) {
        this.mSubCategoryName = mSubCategoryName;
    }

    public String getGetmSubCategoryImage() {
        return getmSubCategoryImage;
    }

    public void setGetmSubCategoryImage(String getmSubCategoryImage) {
        this.getmSubCategoryImage = getmSubCategoryImage;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mSubCategoryName);
        dest.writeString(getmSubCategoryImage);
        dest.writeString(mBasePath);
        dest.writeString(mSubCategorySlug);
    }
}
