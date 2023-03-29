package com.app.sushi.tei.Model;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.sushi.tei.Model.Cart.CartModifier;
import com.app.sushi.tei.Model.ProductList.SetMenuTitle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 5/4/18.
 */

public class OrderHistory implements Serializable, Parcelable {

    private String mProductId = "";

    private String mProductName = "";

    private String mProductImage = "";

    private String mProductTotalPrice = "";

    private String mProductSpecification = "";

    private String mProductQty = "";

    private String mItemVoucherName = "";

    private String mItemVoucherOrderId = "";

    private String mItemVoucherFreeProduct = "";

    private List<CartModifier> cartModifierList;

    private List<SetMenuTitle> setMenuTitleList;

    public OrderHistory() {

    }

    protected OrderHistory(Parcel in) {
        mProductId = in.readString();
        mProductName = in.readString();
        mProductImage = in.readString();
        mProductTotalPrice = in.readString();
        mProductSpecification = in.readString();
        mProductQty = in.readString();
        mItemVoucherName = in.readString();
        mItemVoucherOrderId = in.readString();
        mItemVoucherFreeProduct = in.readString();
        cartModifierList = in.createTypedArrayList(CartModifier.CREATOR);
        setMenuTitleList = in.createTypedArrayList(SetMenuTitle.CREATOR);
    }

    public static final Creator<OrderHistory> CREATOR = new Creator<OrderHistory>() {
        @Override
        public OrderHistory createFromParcel(Parcel in) {
            return new OrderHistory(in);
        }

        @Override
        public OrderHistory[] newArray(int size) {
            return new OrderHistory[size];
        }
    };

    public List<CartModifier> getCartModifierList() {
        return cartModifierList;
    }

    public void setCartModifierList(List<CartModifier> cartModifierList) {
        this.cartModifierList = cartModifierList;
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

    public String getmProductImage() {
        return mProductImage;
    }

    public void setmProductImage(String mProductImage) {
        this.mProductImage = mProductImage;
    }

    public String getmProductTotalPrice() {
        return mProductTotalPrice;
    }

    public void setmProductTotalPrice(String mProductTotalPrice) {
        this.mProductTotalPrice = mProductTotalPrice;
    }

    public String getmProductSpecification() {
        return mProductSpecification;
    }

    public void setmProductSpecification(String mProductSpecification) {
        this.mProductSpecification = mProductSpecification;
    }

    public String getmProductQty() {
        return mProductQty;
    }

    public void setmProductQty(String mProductQty) {
        this.mProductQty = mProductQty;
    }

    public List<SetMenuTitle> getSetMenuTitleList() {
        return setMenuTitleList;
    }

    public void setSetMenuTitleList(List<SetMenuTitle> setMenuTitleList) {
        this.setMenuTitleList = setMenuTitleList;
    }

    public String getmItemVoucherName() {
        return mItemVoucherName;
    }

    public void setmItemVoucherName(String mItemVoucherName) {
        this.mItemVoucherName = mItemVoucherName;
    }

    public String getmItemVoucherOrderId() {
        return mItemVoucherOrderId;
    }

    public void setmItemVoucherOrderId(String mItemVoucherOrderId) {
        this.mItemVoucherOrderId = mItemVoucherOrderId;
    }

    public String getmItemVoucherFreeProduct() {
        return mItemVoucherFreeProduct;
    }

    public void setmItemVoucherFreeProduct(String mItemVoucherFreeProduct) {
        this.mItemVoucherFreeProduct = mItemVoucherFreeProduct;
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
        dest.writeString(mProductTotalPrice);
        dest.writeString(mProductSpecification);
        dest.writeString(mProductQty);
        dest.writeString(mItemVoucherName);
        dest.writeString(mItemVoucherOrderId);
        dest.writeString(mItemVoucherFreeProduct);
        dest.writeTypedList(cartModifierList);
        dest.writeTypedList(setMenuTitleList);
    }
}
