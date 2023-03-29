package com.app.sushi.tei.Model.Cart;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

/**
 * Created by root on 11/4/18.
 */

public class CartModifier implements Serializable, Parcelable {

    private String mModifierId="";

    private String mModifierName="";

    private String nModifireMinmax="";

    private List<CartModifierValue> cartModifierValueList;

    public CartModifier(){

    }

    public String getnModifireMinmax() {
        return nModifireMinmax;
    }

    public void setnModifireMinmax(String nModifireMinmax) {
        this.nModifireMinmax = nModifireMinmax;
    }

    public String getmModifierId() {
        return mModifierId;
    }

    public void setmModifierId(String mModifierId) {
        this.mModifierId = mModifierId;
    }

    public String getmModifierName() {
        return mModifierName;
    }

    public void setmModifierName(String mModifierName) {
        this.mModifierName = mModifierName;
    }

    public List<CartModifierValue> getCartModifierValueList() {
        return cartModifierValueList;
    }

    public void setCartModifierValueList(List<CartModifierValue> cartModifierValueList) {
        this.cartModifierValueList = cartModifierValueList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mModifierId);
        dest.writeString(this.mModifierName);
        dest.writeString(this.nModifireMinmax);
        dest.writeTypedList(this.cartModifierValueList);
    }

    protected CartModifier(Parcel in) {
        this.mModifierId = in.readString();
        this.mModifierName = in.readString();
        this.nModifireMinmax = in.readString();
        this.cartModifierValueList = in.createTypedArrayList(CartModifierValue.CREATOR);
    }

    public static final Creator<CartModifier> CREATOR = new Creator<CartModifier>() {
        @Override
        public CartModifier createFromParcel(Parcel source) {
            return new CartModifier(source);
        }

        @Override
        public CartModifier[] newArray(int size) {
            return new CartModifier[size];
        }
    };
}
