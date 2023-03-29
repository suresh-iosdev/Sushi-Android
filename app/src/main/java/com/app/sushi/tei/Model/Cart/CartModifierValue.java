package com.app.sushi.tei.Model.Cart;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by root on 11/4/18.
 */

public class CartModifierValue implements Serializable, Parcelable {

    private String mModifierValueId="";

    private String mModifierValueName="";

    private String mModifierValuePrice="";

    private String mModifierValueQuantity="";

    public CartModifierValue(){

    }

    protected CartModifierValue(Parcel in) {
        mModifierValueId = in.readString();
        mModifierValueName = in.readString();
        mModifierValuePrice = in.readString();
        mModifierValueQuantity = in.readString();
    }

    public static final Creator<CartModifierValue> CREATOR = new Creator<CartModifierValue>() {
        @Override
        public CartModifierValue createFromParcel(Parcel in) {
            return new CartModifierValue(in);
        }

        @Override
        public CartModifierValue[] newArray(int size) {
            return new CartModifierValue[size];
        }
    };

    public String getmModifierValueId() {
        return mModifierValueId;
    }

    public void setmModifierValueId(String mModifierValueId) {
        this.mModifierValueId = mModifierValueId;
    }

    public String getmModifierValueName() {
        return mModifierValueName;
    }

    public void setmModifierValueName(String mModifierValueName) {
        this.mModifierValueName = mModifierValueName;
    }

    public String getmModifierValuePrice() {
        return mModifierValuePrice;
    }

    public void setmModifierValuePrice(String mModifierValuePrice) {
        this.mModifierValuePrice = mModifierValuePrice;
    }

    public String getmModifierValueQuantity() {
        return mModifierValueQuantity;
    }

    public void setmModifierValueQuantity(String mModifierValueQuantity) {
        this.mModifierValueQuantity = mModifierValueQuantity;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mModifierValueId);
        dest.writeString(mModifierValueName);
        dest.writeString(mModifierValuePrice);
        dest.writeString(mModifierValueQuantity);
    }
}
