package com.app.sushi.tei.Model.AddOns;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by root on 30/3/18.
 */

public class AddOnsProducts implements Serializable, Parcelable {

    private String mProductName="";

    private String mProductId="";

    private String mProductSku="";

    private String mProductDescription="";

    private String mProductSlug="";

    private String mProductPrice="";

    private String mProductType="";

    private String mProductThumpImage="";

    private String mProductStatus="";

    private String mAvailabilityId="";

    private String mMinQuantity="";

    private String mProduct_alias="";


    public AddOnsProducts()
    {

    }

    protected AddOnsProducts(Parcel in) {
        mProductName = in.readString();
        mProductId = in.readString();
        mProductSku = in.readString();
        mProductDescription = in.readString();
        mProductSlug = in.readString();
        mProductPrice = in.readString();
        mProductStatus= in.readString();
        mProductType= in.readString();
        mProductThumpImage= in.readString();
        mAvailabilityId= in.readString();
        mMinQuantity= in.readString();
        mProduct_alias=in.readString();

    }

    public static final Creator<AddOnsProducts> CREATOR = new Creator<AddOnsProducts>() {
        @Override
        public AddOnsProducts createFromParcel(Parcel in) {
            return new AddOnsProducts(in);
        }

        @Override
        public AddOnsProducts[] newArray(int size) {
            return new AddOnsProducts[size];
        }
    };

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

    public String getmProduct_alias() {
        return mProduct_alias;
    }

    public void setmProduct_alias(String mProduct_alias) {
        this.mProduct_alias = mProduct_alias;
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

    public String getmMinQuantity() {
        return mMinQuantity;
    }

    public void setmMinQuantity(String mMinQuantity) {
        this.mMinQuantity = mMinQuantity;
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
        dest.writeString(mProductSlug);
        dest.writeString(mProductPrice);
        dest.writeString(mProductStatus);
        dest.writeString(mProductType);
        dest.writeString(mProductThumpImage);
        dest.writeString(mAvailabilityId);
        dest.writeString(mMinQuantity);
        dest.writeString(mProduct_alias);
    }
}
