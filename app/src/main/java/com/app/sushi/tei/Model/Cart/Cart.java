package com.app.sushi.tei.Model.Cart;

import android.os.Parcel;
import android.os.Parcelable;

import com.app.sushi.tei.Model.ProductList.SetMenuTitle;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 3/4/18.
 */

public class Cart implements Serializable, Parcelable {

    private String mProductId = "";

    private String mProductName = "";

    private String mProductQty = "";

    private String mProductQtyPrice = "";

    private String mProductTotalPrice = "";

    private String mProductImage = "";

    private String mProductType = "";

    private String mBasePath = "";

    private String mCartItemId = "";

    private String mSpecialNotes = "";

    private String pType = "";

    private String mProductSku = "";

    private String mProductVoucherGiftName = "";

    private String mProductVoucherGiftEmail = "";

    private String mProductVoucherGiftMobile = "";

    private String mProductVoucherGiftMessage = "";

    private String mVoucherOrderItemId = "";

    private String mProductVoucher = "";

    private String mProductMinQty = "";

    private String mProductVoucherIncreaseQty = "";

    private String mCashVoucherDiscountAmount = "";

    private String mProductDiscountVoucherName = "";

    private String mCartItemVoucherId = "";

    private String mCashVoucherOrderItemId = "";

    private String mOrderItemVoucherBalanceQty = "";

    private String mCartItemVoucherProductFree = "";

    private List<CartModifier> cartModifierList;

    private List<SetMenuTitle> setMenuTitleList;

    private String cart_item_promotion_type;

    private String cart_item_promotion_discount;


    public Cart() {

    }

    public List<CartModifier> getCartModifierList() {
        return cartModifierList;
    }

    public void setCartModifierList(List<CartModifier> cartModifierList) {
        this.cartModifierList = cartModifierList;
    }

    public String getCart_item_promotion_type() {
        return cart_item_promotion_type;
    }

    public void setCart_item_promotion_type(String cart_item_promotion_type) {
        this.cart_item_promotion_type = cart_item_promotion_type;
    }

    public String getCart_item_promotion_discount() {
        return cart_item_promotion_discount;
    }

    public void setCart_item_promotion_discount(String cart_item_promotion_discount) {
        this.cart_item_promotion_discount = cart_item_promotion_discount;
    }

    public String getmProductSku() {
        return mProductSku;
    }

    public void setmProductSku(String mProductSku) {
        this.mProductSku = mProductSku;
    }

    public String getpType() {
        return pType;
    }

    public void setpType(String pType) {
        this.pType = pType;
    }

    public String getmSpecialNotes() {
        return mSpecialNotes;
    }

    public void setmSpecialNotes(String mSpecialNotes) {
        this.mSpecialNotes = mSpecialNotes;
    }

    public String getmCartItemId() {
        return mCartItemId;
    }

    public void setmCartItemId(String mCartItemId) {
        this.mCartItemId = mCartItemId;
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

    public String getmProductQty() {
        return mProductQty;
    }

    public void setmProductQty(String mProductQty) {
        this.mProductQty = mProductQty;
    }

    public String getmProductQtyPrice() {
        return mProductQtyPrice;
    }

    public void setmProductQtyPrice(String mProductQtyPrice) {
        this.mProductQtyPrice = mProductQtyPrice;
    }

    public String getmBasePath() {
        return mBasePath;
    }

    public void setmBasePath(String mBasePath) {
        this.mBasePath = mBasePath;
    }

    public String getmProductTotalPrice() {
        return mProductTotalPrice;
    }

    public void setmProductTotalPrice(String mProductTotalPrice) {
        this.mProductTotalPrice = mProductTotalPrice;
    }

    public String getmProductImage() {
        return mProductImage;
    }

    public void setmProductImage(String mProductImage) {
        this.mProductImage = mProductImage;
    }

    public String getmProductType() {
        return mProductType;
    }

    public void setmProductType(String mProductType) {
        this.mProductType = mProductType;
    }

    public List<SetMenuTitle> getSetMenuTitleList() {
        return setMenuTitleList;
    }

    public void setSetMenuTitleList(List<SetMenuTitle> setMenuTitleList) {
        this.setMenuTitleList = setMenuTitleList;
    }

    public String getmProductVoucherGiftName() {
        return mProductVoucherGiftName;
    }

    public void setmProductVoucherGiftName(String mProductVoucherGiftName) {
        this.mProductVoucherGiftName = mProductVoucherGiftName;
    }

    public String getmProductVoucherGiftEmail() {
        return mProductVoucherGiftEmail;
    }

    public void setmProductVoucherGiftEmail(String mProductVoucherGiftEmail) {
        this.mProductVoucherGiftEmail = mProductVoucherGiftEmail;
    }

    public String getmProductVoucherGiftMobile() {
        return mProductVoucherGiftMobile;
    }

    public void setmProductVoucherGiftMobile(String mProductVoucherGiftMobile) {
        this.mProductVoucherGiftMobile = mProductVoucherGiftMobile;
    }

    public String getmProductVoucherGiftMessage() {
        return mProductVoucherGiftMessage;
    }

    public void setmProductVoucherGiftMessage(String mProductVoucherGiftMessage) {
        this.mProductVoucherGiftMessage = mProductVoucherGiftMessage;
    }

    public String getmVoucherOrderItemId() {
        return mVoucherOrderItemId;
    }

    public void setmVoucherOrderItemId(String mVoucherOrderItemId) {
        this.mVoucherOrderItemId = mVoucherOrderItemId;
    }

    public String getmProductVoucher() {
        return mProductVoucher;
    }

    public void setmProductVoucher(String mProductVoucher) {
        this.mProductVoucher = mProductVoucher;
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

    public String getmCashVoucherDiscountAmount() {
        return mCashVoucherDiscountAmount;
    }

    public void setmCashVoucherDiscountAmount(String mCashVoucherDiscountAmount) {
        this.mCashVoucherDiscountAmount = mCashVoucherDiscountAmount;
    }

    public String getmProductDiscountVoucherName() {
        return mProductDiscountVoucherName;
    }

    public void setmProductDiscountVoucherName(String mProductDiscountVoucherName) {
        this.mProductDiscountVoucherName = mProductDiscountVoucherName;
    }

    public String getmCartItemVoucherId() {
        return mCartItemVoucherId;
    }

    public void setmCartItemVoucherId(String mCartItemVoucherId) {
        this.mCartItemVoucherId = mCartItemVoucherId;
    }

    public String getmCashVoucherOrderItemId() {
        return mCashVoucherOrderItemId;
    }

    public void setmCashVoucherOrderItemId(String mCashVoucherOrderItemId) {
        this.mCashVoucherOrderItemId = mCashVoucherOrderItemId;
    }

    public String getmOrderItemVoucherBalanceQty() {
        return mOrderItemVoucherBalanceQty;
    }

    public void setmOrderItemVoucherBalanceQty(String mOrderItemVoucherBalanceQty) {
        this.mOrderItemVoucherBalanceQty = mOrderItemVoucherBalanceQty;
    }

    public String getmCartItemVoucherProductFree() {
        return mCartItemVoucherProductFree;
    }

    public void setmCartItemVoucherProductFree(String mCartItemVoucherProductFree) {
        this.mCartItemVoucherProductFree = mCartItemVoucherProductFree;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mProductId);
        dest.writeString(this.mProductName);
        dest.writeString(this.mProductQty);
        dest.writeString(this.mProductQtyPrice);
        dest.writeString(this.mProductTotalPrice);
        dest.writeString(this.mProductImage);
        dest.writeString(this.mProductType);
        dest.writeString(this.mBasePath);
        dest.writeString(this.mCartItemId);
        dest.writeString(this.mSpecialNotes);
        dest.writeString(this.pType);
        dest.writeTypedList(this.cartModifierList);
        dest.writeTypedList(this.setMenuTitleList);
        dest.writeString(this.mProductSku);
        dest.writeString(this.mProductVoucherGiftName);
        dest.writeString(this.mProductVoucherGiftEmail);
        dest.writeString(this.mProductVoucherGiftMobile);
        dest.writeString(this.mProductVoucherGiftMessage);
        dest.writeString(this.mVoucherOrderItemId);
        dest.writeString(this.mProductVoucher);
        dest.writeString(mProductMinQty);
        dest.writeString(mProductVoucherIncreaseQty);
        dest.writeString(mCashVoucherDiscountAmount);
        dest.writeString(mProductDiscountVoucherName);
        dest.writeString(mCartItemVoucherId);
        dest.writeString(mCashVoucherOrderItemId);
        dest.writeString(mOrderItemVoucherBalanceQty);
        dest.writeString(mCartItemVoucherProductFree);
        dest.writeString(cart_item_promotion_type);
        dest.writeString(cart_item_promotion_discount);
    }

    protected Cart(Parcel in) {
        this.mProductId = in.readString();
        this.mProductName = in.readString();
        this.mProductQty = in.readString();
        this.mProductQtyPrice = in.readString();
        this.mProductTotalPrice = in.readString();
        this.mProductImage = in.readString();
        this.mProductType = in.readString();
        this.mBasePath = in.readString();
        this.mCartItemId = in.readString();
        this.mSpecialNotes = in.readString();
        this.pType = in.readString();
        this.cartModifierList = in.createTypedArrayList(CartModifier.CREATOR);
        this.setMenuTitleList = in.createTypedArrayList(SetMenuTitle.CREATOR);
        this.mProductQty = in.readString();
        this.mProductVoucherGiftName = in.readString();
        this.mProductVoucherGiftEmail = in.readString();
        this.mProductVoucherGiftMobile = in.readString();
        this.mProductVoucherGiftMessage = in.readString();
        this.mVoucherOrderItemId = in.readString();
        this.mProductVoucher = in.readString();
        mProductMinQty = in.readString();
        mProductVoucherIncreaseQty = in.readString();
        mCashVoucherDiscountAmount = in.readString();
        mProductDiscountVoucherName = in.readString();
        mCartItemVoucherId = in.readString();
        mCashVoucherOrderItemId = in.readString();
        mOrderItemVoucherBalanceQty = in.readString();
        mCartItemVoucherProductFree = in.readString();
        this.cart_item_promotion_type = in.readString();
        this.cart_item_promotion_discount = in.readString();
    }

    public static final Creator<Cart> CREATOR = new Creator<Cart>() {
        @Override
        public Cart createFromParcel(Parcel source) {
            return new Cart(source);
        }

        @Override
        public Cart[] newArray(int size) {
            return new Cart[size];
        }
    };

    @Override
    public String toString() {
        return "Cart{" +
                "mProductId='" + mProductId + '\'' +
                ", mProductName='" + mProductName + '\'' +
                ", mProductQty='" + mProductQty + '\'' +
                ", mProductQtyPrice='" + mProductQtyPrice + '\'' +
                ", mProductTotalPrice='" + mProductTotalPrice + '\'' +
                ", mProductImage='" + mProductImage + '\'' +
                ", mProductType='" + mProductType + '\'' +
                ", mBasePath='" + mBasePath + '\'' +
                ", mCartItemId='" + mCartItemId + '\'' +
                ", mSpecialNotes='" + mSpecialNotes + '\'' +
                ", pType='" + pType + '\'' +
                ", mProductSku='" + mProductSku + '\'' +
                ", mProductVoucherGiftName='" + mProductVoucherGiftName + '\'' +
                ", mProductVoucherGiftEmail='" + mProductVoucherGiftEmail + '\'' +
                ", mProductVoucherGiftMobile='" + mProductVoucherGiftMobile + '\'' +
                ", mProductVoucherGiftMessage='" + mProductVoucherGiftMessage + '\'' +
                ", mVoucherOrderItemId='" + mVoucherOrderItemId + '\'' +
                ", mProductVoucher='" + mProductVoucher + '\'' +
                ", mProductMinQty='" + mProductMinQty + '\'' +
                ", mProductVoucherIncreaseQty='" + mProductVoucherIncreaseQty + '\'' +
                ", mCashVoucherDiscountAmount='" + mCashVoucherDiscountAmount + '\'' +
                ", mProductDiscountVoucherName='" + mProductDiscountVoucherName + '\'' +
                ", mCartItemVoucherId='" + mCartItemVoucherId + '\'' +
                ", mCashVoucherOrderItemId='" + mCashVoucherOrderItemId + '\'' +
                ", mOrderItemVoucherBalanceQty='" + mOrderItemVoucherBalanceQty + '\'' +
                ", mCartItemVoucherProductFree='" + mCartItemVoucherProductFree + '\'' +
                ", cartModifierList=" + cartModifierList +
                ", setMenuTitleList=" + setMenuTitleList +
                ", cart_item_promotion_type='" + cart_item_promotion_type + '\'' +
                ", cart_item_promotion_discount='" + cart_item_promotion_discount + '\'' +
                '}';
    }
}
