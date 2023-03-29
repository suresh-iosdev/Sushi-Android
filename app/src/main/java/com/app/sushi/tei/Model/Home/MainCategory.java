package com.app.sushi.tei.Model.Home;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 28/3/18.
 */

public class MainCategory  implements Serializable, Parcelable {

    private String mCategoryId="";

    private String mCategoryName="";

    private String pro_cate_image="";

    private String pro_cat_description="";

    public String getPro_cat_description() {
        return pro_cat_description;
    }

    public void setPro_cat_description(String pro_cat_description) {
        this.pro_cat_description = pro_cat_description;
    }

    private String mCategorySlug="";

    private ArrayList<SubCategory> subCategoryList;

    public MainCategory()
    {

    }

    protected MainCategory(Parcel in) {
        mCategoryId = in.readString();
        mCategoryName = in.readString();
        pro_cate_image = in.readString();

        mCategorySlug = in.readString();

        if (in.readByte() == 0x01) {
            subCategoryList = new ArrayList<SubCategory>();
            in.readList(subCategoryList, SubCategory.class.getClassLoader());
        } else {
            subCategoryList = null;
        }
    }

    public String getmCategorySlug() {
        return mCategorySlug;
    }

    public void setmCategorySlug(String mCategorySlug) {
        this.mCategorySlug = mCategorySlug;
    }

    public static final Creator<MainCategory> CREATOR = new Creator<MainCategory>() {
        @Override
        public MainCategory createFromParcel(Parcel in) {
            return new MainCategory(in);
        }

        @Override
        public MainCategory[] newArray(int size) {
            return new MainCategory[size];
        }
    };

    public String getmCategoryId() {
        return mCategoryId;
    }

    public void setmCategoryId(String mCategoryId) {
        this.mCategoryId = mCategoryId;
    }

    public String getmCategoryName() {
        return mCategoryName;
    }

    public void setmCategoryName(String mCategoryName) {
        this.mCategoryName = mCategoryName;
    }

    public String getmActiveImage() {
        return pro_cate_image;
    }

    public void setmActiveImage(String mActiveImage) {
        this.pro_cate_image = mActiveImage;
    }


    public ArrayList<SubCategory> getSubCategoryList() {
        return subCategoryList;
    }

    public void setSubCategoryList(ArrayList<SubCategory> subCategoryList) {
        this.subCategoryList = subCategoryList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mCategoryId);
        dest.writeString(mCategoryName);
        dest.writeString(pro_cate_image);
        dest.writeString(mCategorySlug);

        if (subCategoryList == null) {
            dest.writeByte((byte) (0x00));
        } else {
            dest.writeByte((byte) (0x01));
            dest.writeList(subCategoryList);
        }    }
}
