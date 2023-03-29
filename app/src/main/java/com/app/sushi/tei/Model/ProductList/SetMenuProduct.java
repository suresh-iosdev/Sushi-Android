package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17/5/18.
 */

public class SetMenuProduct implements Parcelable {


    private String mProductId = "";

    private String mproduct_primary_id = "";

    private String mAliasProductPrimaryId = "";

    private String mProductName = "";

    private String mProductAliasName = "";

    private String mProductDesc = "";

    private String mProductImage = "";

    private String mProductgalleryImage = "";

    private String mProductLargeImage = "";

    private String mProductStatus = "";

    private String mProductType = "";

    private String mProductSku = "";

    private String mProductPrice = "";

    private String subcatgory_name="";

    private String product_apply_minmax_select="";

    private int mApplyMinMaxSelect=0;

    private String fav_product_primary_id="";

    private String mVoucherExpiryDate = "";

    private String mVoucherPoints = "";

    private String mProductMinQty = "";

    private String mProductVoucherIncreaseQty = "";

    private String mProductVoucher = "";

    private String mProductShortDescription = "";

    private List<SetMenuTitle> setMenuTitleList=new ArrayList<>();

    private ArrayList<TagImageModel> TagImageList=new ArrayList<>();

    public ArrayList<TagImageModel> getTagImageList() {
        return TagImageList;
    }

    public void setTagImageList(ArrayList<TagImageModel> tagImageList) {
        TagImageList = tagImageList;
    }

    public SetMenuProduct() {

    }

    protected SetMenuProduct(Parcel in) {
        mProductId = in.readString();
        mproduct_primary_id=in.readString();
        mAliasProductPrimaryId = in.readString();
        mProductName = in.readString();
        mProductAliasName = in.readString();
        product_apply_minmax_select=in.readString();
        mProductDesc = in.readString();
        mProductImage = in.readString();
        mProductgalleryImage = in.readString();
        mProductLargeImage=in.readString();
        mProductStatus = in.readString();
        mProductType = in.readString();
        mProductSku = in.readString();
        mProductPrice = in.readString();
        mApplyMinMaxSelect = in.readInt();
        subcatgory_name = in.readString();
        fav_product_primary_id=in.readString();
        mVoucherExpiryDate = in.readString();
        mVoucherPoints = in.readString();
        mProductMinQty = in.readString();
        mProductVoucherIncreaseQty = in.readString();
        mProductVoucher = in.readString();
        mProductShortDescription = in.readString();
        setMenuTitleList = in.createTypedArrayList(SetMenuTitle.CREATOR);
    }

    public static final Creator<SetMenuProduct> CREATOR = new Creator<SetMenuProduct>() {
        @Override
        public SetMenuProduct createFromParcel(Parcel in) {
            return new SetMenuProduct(in);
        }

        @Override
        public SetMenuProduct[] newArray(int size) {
            return new SetMenuProduct[size];
        }
    };

    public String getmproduct_primary_id() {
        return mproduct_primary_id;
    }
    public void setmproduct_primary_id(String mproduct_primary_id) {
        this.mproduct_primary_id = mproduct_primary_id;
    }

    public String getmProductId() {
        return mProductId;
    }


    public void setmProductId(String mProductId) {
        this.mProductId = mProductId;
    }


    public String getSubcatgory_name() {
        return subcatgory_name;
    }

    public void setSubcatgory_name(String subcatgory_name) {
        this.subcatgory_name = subcatgory_name;
    }

    public String getmAliasProductPrimaryId() {
        return mAliasProductPrimaryId;
    }

    public void setmAliasProductPrimaryId(String mAliasProductPrimaryId) {
        this.mAliasProductPrimaryId = mAliasProductPrimaryId;
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


    public String getmProductLargeImage() {
        return mProductLargeImage;
    }

    public void setmProductLargeImage(String mProductLargeImage) {
        this.mProductLargeImage = mProductLargeImage;
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

    public String getmfav_product_primary_id() {
        return fav_product_primary_id;
    }

    public void setmfav_product_primary_id(String fav_product_primary_id) {
        this.fav_product_primary_id = fav_product_primary_id;
    }

    public int getmApplyMinMaxSelect() {
        return mApplyMinMaxSelect;
    }

    public void setmApplyMinMaxSelect(int mApplyMinMaxSelect) {
        this.mApplyMinMaxSelect = mApplyMinMaxSelect;
    }

    public List<SetMenuTitle> getSetMenuTitleList() {
        return setMenuTitleList;
    }

    public void setSetMenuTitleList(List<SetMenuTitle> setMenuTitleList) {
        this.setMenuTitleList = setMenuTitleList;
    }

    public String getmProductAliasName() {
        return mProductAliasName;
    }

    public void setmProductAliasName(String mProductAliasName) {
        this.mProductAliasName = mProductAliasName;
    }


    public String getmproduct_apply_minmax_select() {
        return product_apply_minmax_select;
    }

    public void setmproduct_apply_minmax_select(String product_apply_minmax_select) {
        this.product_apply_minmax_select = product_apply_minmax_select;
    }

    public String getmProductgalleryImage() {
        return mProductgalleryImage;
    }

    public void setmProductgalleryImage(String mProductgalleryImage) {
        this.mProductgalleryImage = mProductgalleryImage;
    }

    public String getMproduct_primary_id() {
        return mproduct_primary_id;
    }

    public void setMproduct_primary_id(String mproduct_primary_id) {
        this.mproduct_primary_id = mproduct_primary_id;
    }

    public String getProduct_apply_minmax_select() {
        return product_apply_minmax_select;
    }

    public void setProduct_apply_minmax_select(String product_apply_minmax_select) {
        this.product_apply_minmax_select = product_apply_minmax_select;
    }

    public String getFav_product_primary_id() {
        return fav_product_primary_id;
    }

    public void setFav_product_primary_id(String fav_product_primary_id) {
        this.fav_product_primary_id = fav_product_primary_id;
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

    public String getmProductShortDescription() {
        return mProductShortDescription;
    }

    public void setmProductShortDescription(String mProductShortDescription) {
        this.mProductShortDescription = mProductShortDescription;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mProductId);
        dest.writeString(mAliasProductPrimaryId);
        dest.writeString(mProductName);
        dest.writeString(mProductAliasName);
        dest.writeString(product_apply_minmax_select);
        dest.writeString(mProductDesc);
        dest.writeString(mProductImage);
        dest.writeString(mProductgalleryImage);
        dest.writeString(mProductLargeImage);
        dest.writeString(mProductStatus);
        dest.writeString(mProductType);
        dest.writeString(mProductSku);
        dest.writeString(mProductPrice);
        dest.writeString(fav_product_primary_id);
        dest.writeInt(mApplyMinMaxSelect);
        dest.writeTypedList(setMenuTitleList);
        dest.writeString(subcatgory_name);
        dest.writeString(mVoucherPoints);
        dest.writeString(mVoucherExpiryDate);
        dest.writeString(mProductMinQty);
        dest.writeString(mProductVoucherIncreaseQty);
        dest.writeString(mProductVoucher);
    }
}
