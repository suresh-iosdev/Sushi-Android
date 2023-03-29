package com.app.sushi.tei.Model.Search;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 14/5/18.
 */

public class SearchProduct implements Parcelable {


    private String mProductId="";

    private String mProductName="";

    private String mProductImage="";

    private String mProductGalleryImage;

    private String mProduct_galleryImage = "";

    private String mProductShortDesc="";

    private String mProductType="";

    private String mCategorySlug="";

    private String mProductSlug="";

    private String mProductCategoryName="";

    private String mProductSubCategoryName="";

    private String mProductPrice="";

    private String product_alias="";

    private String mProduct_primary_id="";

    private String mProduct_favourite_id="";



    public SearchProduct()
    {

    }

    protected SearchProduct(Parcel in) {
        mProductId = in.readString();
        mProductName = in.readString();
        mProductImage = in.readString();
        mProductGalleryImage = in.readString();
        mProduct_galleryImage = in.readString();
        mProductShortDesc = in.readString();
        mProductType = in.readString();
        mCategorySlug = in.readString();
        mProductSlug = in.readString();
        mProductCategoryName = in.readString();
        mProductSubCategoryName = in.readString();
        product_alias=in.readString();
        mProduct_primary_id=in.readString();
        mProduct_favourite_id=in.readString();
        mProductPrice = in.readString();
    }

    public static final Creator<SearchProduct> CREATOR = new Creator<SearchProduct>() {
        @Override
        public SearchProduct createFromParcel(Parcel in) {
            return new SearchProduct(in);
        }

        @Override
        public SearchProduct[] newArray(int size) {
            return new SearchProduct[size];
        }
    };
    public String getProduct_alias() {
        return product_alias;
    }

    public void setProduct_alias(String product_alias) {
        this.product_alias = product_alias;
    }


    public String getmProduct_primary_id() {
        return mProduct_primary_id;
    }

    public void setmProduct_primary_id(String mProduct_primary_id) {
        this.mProduct_primary_id = mProduct_primary_id;
    }


    public String getmProductGalleryImage() {
        return mProductGalleryImage;
    }

    public void setmProductGalleryImage(String mProductGalleryImage) {
        this.mProductGalleryImage = mProductGalleryImage;
    }

    public String getmProduct_galleryImage() {
        return mProduct_galleryImage;
    }

    public void setmProduct_galleryImage(String mProduct_galleryImage) {
        this.mProduct_galleryImage = mProduct_galleryImage;
    }

    public String getmProduct_favourite_id() {
        return mProduct_favourite_id;
    }

    public void setmProduct_favourite_id(String mProduct_favourite_id) {
        this.mProduct_favourite_id = mProduct_favourite_id;
    }
    public String getmCategorySlug() {
        return mCategorySlug;
    }

    public void setmCategorySlug(String mCategorySlug) {
        this.mCategorySlug = mCategorySlug;
    }

    public String getmProductSlug() {
        return mProductSlug;
    }

    public String getProductPrice() {
        return mProductPrice;
    }

    public void setProductPrice(String mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public void setmProductSlug(String mProductSlug) {
        this.mProductSlug = mProductSlug;
    }

    public String getmProductCategoryName() {
        return mProductCategoryName;
    }

    public void setmProductCategoryName(String mProductCategoryName) {
        this.mProductCategoryName = mProductCategoryName;
    }

    public String getmProductSubCategoryName() {
        return mProductSubCategoryName;
    }

    public void setmProductSubCategoryName(String mProductSubCategoryName) {
        this.mProductSubCategoryName = mProductSubCategoryName;
    }

    public String getmProductImage() {
        return mProductImage;
    }

    public void setmProductImage(String mProductImage) {
        this.mProductImage = mProductImage;
    }

    public String getmProductShortDesc() {
        return mProductShortDesc;
    }

    public void setmProductShortDesc(String mProductShortDesc) {
        this.mProductShortDesc = mProductShortDesc;
    }

    public String getmProductType() {
        return mProductType;
    }

    public void setmProductType(String mProductType) {
        this.mProductType = mProductType;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeString(mProductId);
        dest.writeString(mProductName);
        dest.writeString(mProductImage);
        dest.writeString(mProductGalleryImage);
        dest.writeString(mProduct_galleryImage);
        dest.writeString(mProductShortDesc);
        dest.writeString(mProductType);
        dest.writeString(mCategorySlug);
        dest.writeString(mProductSlug);
        dest.writeString(mProductCategoryName);
        dest.writeString(mProductSubCategoryName);
        dest.writeString(mProductPrice);
        dest.writeString(product_alias);
        dest.writeString(mProduct_primary_id);
        dest.writeString(mProduct_favourite_id);

    }
}
