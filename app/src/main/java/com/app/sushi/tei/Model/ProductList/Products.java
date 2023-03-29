package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by root on 30/3/18.
 */

public class Products implements Serializable, Parcelable {

    private String mProductName="";

    private String mProductId="";

    private String mProductSku="";

    private String mProductDescription="";

    private String mProductShortDescription = "";

    private String mProductSlug="";

    private String mProductPrice="";

    private String mProductType="";

    private String mProductThumpImage="";

    private String mProductStatus="";

    private String mAvailabilityId="";

    private String mMinQuantity="";

    private String mProduct_stock="";

    private String mproduct_alias="";

    private String mProductPrimaryId="";

    private String fav_product_primary_id;

    private String mSubCategoryGalleryImage = "";

    private String mFavouriteHeartclickable;

    private String mFaouriteStatusLable = "";

    private String mVoucherExpiryDate = "";

    private String mVoucherPoints = "";

    private String mProductMinQty = "";

    private String mProductVoucherIncreaseQty = "";

    private String mProductVoucher = "";

    private ArrayList<TagImageModel> TagImageList=new ArrayList<>();

    public ArrayList<TagImageModel> getTagImageList() {
        return TagImageList;
    }

    public void setTagImageList(ArrayList<TagImageModel> tagImageList) {
        TagImageList = tagImageList;
    }


    public Products()
    {

    }

    protected Products(Parcel in) {
        mProductName = in.readString();
        mProductId = in.readString();
        mProductSku = in.readString();
        mProductDescription = in.readString();
        mProductShortDescription = in.readString();
        mProductSlug = in.readString();
        mProductPrice = in.readString();
        mProductStatus= in.readString();
        mProductType= in.readString();
        mProductThumpImage= in.readString();
        mAvailabilityId= in.readString();
        mMinQuantity= in.readString();
        mProduct_stock = in.readString();
        mproduct_alias =in.readString();
        mProductPrimaryId=in.readString();
        fav_product_primary_id=in.readString();
        mSubCategoryGalleryImage= in.readString();
        mFavouriteHeartclickable = in.readString();
        mFaouriteStatusLable = in.readString();
        mVoucherExpiryDate = in.readString();
        mVoucherPoints = in.readString();
        mProductMinQty = in.readString();
        mProductVoucherIncreaseQty = in.readString();
        mProductVoucher = in.readString();
    }

    public static final Creator<Products> CREATOR = new Creator<Products>() {
        @Override
        public Products createFromParcel(Parcel in) {
            return new Products(in);
        }

        @Override
        public Products[] newArray(int size) {
            return new Products[size];
        }
    };

    public String getMproduct_alias() {
        return mproduct_alias;
    }

    public void setMproduct_alias(String mproduct_alias) {
        this.mproduct_alias = mproduct_alias;
    }

    public String getmProductPrimaryId() {
        return mProductPrimaryId;
    }

    public void setmProductPrimaryId(String mProductPrimaryId) {
        this.mProductPrimaryId = mProductPrimaryId;
    }

    public String getFav_product_primary_id() {
        return fav_product_primary_id;
    }

    public void setFav_product_primary_id(String fav_product_primary_id) {
        this.fav_product_primary_id = fav_product_primary_id;
    }
    public String getmSubCategoryGalleryImage() {
        return mSubCategoryGalleryImage;
    }

    public void setmSubCategoryGalleryImage(String mSubCategoryGalleryImage) {
        this.mSubCategoryGalleryImage = mSubCategoryGalleryImage;
    }
    public String getmProductType() {
        return mProductType;
    }

    public void setmProductType(String mProductType) {
        this.mProductType = mProductType;
    }

    public String getmProductThumpImage() {
        return mProductThumpImage;
    }

    public void setmProductThumpImage(String mProductThumpImage) {
        this.mProductThumpImage = mProductThumpImage;
    }

    public String getmProductStatus() {
        return mProductStatus;
    }

    public void setmProductStatus(String mProductStatus) {
        this.mProductStatus = mProductStatus;
    }

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmProductId() {
        return mProductId;
    }

    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }

    public String getmProductSku() {
        return mProductSku;
    }

    public void setmProductSku(String mProductSku) {
        this.mProductSku = mProductSku;
    }

    public String getmProductDescription() {
        return mProductDescription;
    }

    public void setmProductDescription(String mProductDescription) {
        this.mProductDescription = mProductDescription;
    }

    public String getmProductShortDescription() {
        return mProductShortDescription;
    }

    public void setmProductShortDescription(String mProductShortDescription) {
        this.mProductShortDescription = mProductShortDescription;
    }

    public String getmProductSlug() {
        return mProductSlug;
    }

    public void setmProductSlug(String mProductSlug) {
        this.mProductSlug = mProductSlug;
    }

    public String getmProductPrice() {
        return mProductPrice;
    }

    public void setmProductPrice(String mProductPrice) {
        this.mProductPrice = mProductPrice;
    }

    public String getmAvailabilityId() {
        return mAvailabilityId;
    }

    public void setmAvailabilityId(String mAvailabilityId) {
        this.mAvailabilityId = mAvailabilityId;
    }


    public void setmProduct_stock(String mproductStock)
    {

        this.mProduct_stock = mproductStock;
    }

    public String getmProduct_stock()
    {

        return mProduct_stock;
    }


    public String getmMinQuantity() {
        return mMinQuantity;
    }

    public void setmMinQuantity(String mMinQuantity) {
        this.mMinQuantity = mMinQuantity;
    }

    public String getmFavouriteHeartclickable() {
        return mFavouriteHeartclickable;
    }

    public void setmFavouriteHeartclickable(String mFavouriteHeartclickable) {
        this.mFavouriteHeartclickable = mFavouriteHeartclickable;
    }

    public String getmFaouriteStatusLable() {
        return mFaouriteStatusLable;
    }

    public void setmFaouriteStatusLable(String mFaouriteStatusLable) {
        this.mFaouriteStatusLable = mFaouriteStatusLable;
    }

    public String getmVoucherExpiryDate() {
        return mVoucherExpiryDate;
    }

    public void setmVoucherExpiryDate(String mVoucherExpiryDate) {
        this.mVoucherExpiryDate = mVoucherExpiryDate;
    }

    public String getmVoucherPoints() {
        return mVoucherPoints;
    }

    public void setmVoucherPoints(String mVoucherPoints) {
        this.mVoucherPoints = mVoucherPoints;
    }

    public String getmProductMinQty() {
        return mProductMinQty;
    }

    public void setmProductMinQty(String mProductMinQty) {
        this.mProductMinQty = mProductMinQty;
    }

    public String getmProductVoucherIncreaseQty() {
        return mProductVoucherIncreaseQty;
    }

    public void setmProductVoucherIncreaseQty(String mProductVoucherIncreaseQty) {
        this.mProductVoucherIncreaseQty = mProductVoucherIncreaseQty;
    }

    public String getmProductVoucher() {
        return mProductVoucher;
    }

    public void setmProductVoucher(String mProductVoucher) {
        this.mProductVoucher = mProductVoucher;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProductName);
        dest.writeString(mProductId);
        dest.writeString(mProductSku);
        dest.writeString(mProductDescription);
        dest.writeString(mProductShortDescription);
        dest.writeString(mProductSlug);
        dest.writeString(mProductPrice);
        dest.writeString(mProductStatus);
        dest.writeString(mProductType);
        dest.writeString(mProductThumpImage);
        dest.writeString(mAvailabilityId);
        dest.writeString(mMinQuantity);
        dest.writeString(mProduct_stock);
        dest.writeString(mproduct_alias);
        dest.writeString(mProductPrimaryId);
        dest.writeString(mSubCategoryGalleryImage);
        dest.writeString(fav_product_primary_id);
        dest.writeString(mFavouriteHeartclickable);
        dest.writeString(mFaouriteStatusLable);
        dest.writeString(mVoucherPoints);
        dest.writeString(mVoucherExpiryDate);
        dest.writeString(mProductMinQty);
        dest.writeString(mProductVoucherIncreaseQty);
        dest.writeString(mProductVoucher);
    }
}
