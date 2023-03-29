package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 30/3/18.
 */

public class ProductSubCategory implements Serializable, Parcelable {

    private String mSubCategoryName="";

    private String mSubcategoryId="";

    private String mSubCategorySlug="";

    private String mCategorySlug="";

    private String mBasePath="";

    private String mSubCategoryImage = "";



    private List<Products> productsList;

    public ProductSubCategory(){}

    protected ProductSubCategory(Parcel in) {
        mSubCategoryName = in.readString();
        mSubcategoryId = in.readString();
        mSubCategorySlug = in.readString();
        mCategorySlug = in.readString();
        mBasePath = in.readString();
        mSubCategoryImage = in.readString();

        productsList = in.createTypedArrayList(com.app.sushi.tei.Model.ProductList.Products.CREATOR);
    }

    public static final Creator<ProductSubCategory> CREATOR = new Creator<ProductSubCategory>() {
        @Override
        public ProductSubCategory createFromParcel(Parcel in) {
            return new ProductSubCategory(in);
        }

        @Override
        public ProductSubCategory[] newArray(int size) {
            return new ProductSubCategory[size];
        }
    };

    public String getmCategorySlug() {
        return mCategorySlug;
    }

    public void setmCategorySlug(String mCategorySlug) {
        this.mCategorySlug = mCategorySlug;
    }


    public String getmSubCategoryName() {
        return mSubCategoryName;
    }

    public void setmSubCategoryName(String mSubCategoryName) {
        this.mSubCategoryName = mSubCategoryName;
    }

    public String getmSubcategoryId() {
        return mSubcategoryId;
    }

    public void setmSubcategoryId(String mSubcategoryId) {
        this.mSubcategoryId = mSubcategoryId;
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

    public String getmSubCategoryImage() {
        return mSubCategoryImage;
    }

    public void setmSubCategoryImage(String mSubCategoryImage) {
        this.mSubCategoryImage = mSubCategoryImage;
    }



    public List<Products> getProductsList() {
        return productsList;
    }

    public void setProductsList(List<Products> productsList) {
        this.productsList = productsList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mSubCategoryName);
        dest.writeString(mSubcategoryId);
        dest.writeString(mSubCategorySlug);
        dest.writeString(mCategorySlug);
        dest.writeString(mBasePath);
        dest.writeString(mSubCategoryImage);
        dest.writeTypedList(productsList);
    }
}
