package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 2/4/18.
 */

public class ModifierProduct implements Serializable, Parcelable {

    private String mProductId="";

    private String mAliasProductPrimaryId="";

    private String mProductName="";

    private String mProductDesc="";

    private String mProductImage="";

    private String mProductStatus="";

    private String mProductType="";

    private String mProductSku="";

    private String mProductPrice="";

    private String mproduct_alias="";

    private List<ModifierHeading> modifiersList;

    public ModifierProduct(){

    }

    protected ModifierProduct(Parcel in) {
        mProductId = in.readString();
        mProductName = in.readString();
        mProductDesc = in.readString();
        mProductImage = in.readString();
        mProductStatus = in.readString();
        mProductType = in.readString();
        mProductSku = in.readString();
        mProductPrice = in.readString();
        mproduct_alias=in.readString();
        mAliasProductPrimaryId = in.readString();
        modifiersList = in.createTypedArrayList(ModifierHeading.CREATOR);
    }

    public static final Creator<ModifierProduct> CREATOR = new Creator<ModifierProduct>() {
        @Override
        public ModifierProduct createFromParcel(Parcel in) {
            return new ModifierProduct(in);
        }

        @Override
        public ModifierProduct[] newArray(int size) {
            return new ModifierProduct[size];
        }
    };

    public String getmProductPrimaryId() {
        return mAliasProductPrimaryId;
    }

    public void setmProductPrimaryId(String mProductPrimaryId) {
        this.mAliasProductPrimaryId = mProductPrimaryId;
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

    public String getmProductDesc() {
        return mProductDesc;
    }

    public void setmProductDesc(String mProductDesc) {
        this.mProductDesc = mProductDesc;
    }

    public String getmProductImage() {
        return mProductImage;
    }

    public void setmProductImage(String mProductImage) {
        this.mProductImage = mProductImage;
    }

    public String getmProductStatus() {
        return mProductStatus;
    }

    public void setmProductStatus(String mProductStatus) {
        this.mProductStatus = mProductStatus;
    }

    public String getmProductType() {
        return mProductType;
    }

    public void setmProductType(String mProductType) {
        this.mProductType = mProductType;
    }

    public String getmProductSku() {
        return mProductSku;
    }

    public void setmProductSku(String mProductSku) {
        this.mProductSku = mProductSku;
    }

    public String getmProductPrice() {
        return mProductPrice;
    }

    public void setmProductPrice(String mProductPrice) {
        this.mProductPrice = mProductPrice;
    }
    public String getmProduct_alias() {
        return mproduct_alias;
    }

    public void setmProduct_alias(String mproduct_alias) {
        this.mproduct_alias = mproduct_alias;
    }

    public List<ModifierHeading> getModifiersList() {
        return modifiersList;
    }

    public void setModifiersList(List<ModifierHeading> modifiersList) {
        this.modifiersList = modifiersList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProductId);
        dest.writeString(mProductName);
        dest.writeString(mProductDesc);
        dest.writeString(mProductImage);
        dest.writeString(mProductStatus);
        dest.writeString(mProductType);
        dest.writeString(mProductSku);
        dest.writeString(mProductPrice);
        dest.writeString(mproduct_alias);
        dest.writeString(mAliasProductPrimaryId);
        dest.writeTypedList(modifiersList);
    }
}
