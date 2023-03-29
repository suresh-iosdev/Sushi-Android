package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by root on 2/4/18.
 */

public class ModifiersValue implements Parcelable {

    private String mModifierName = "";

    private String mModifierId = "";

    private String mModifierDefault = "";

    private String mModifierValuePrice= "";

    private boolean isChekced = false;

    private String mProductModifierValueId = "";

    private int mSubModifierTotal;




    public ModifiersValue() {

    }

    public boolean isChekced() {
        return isChekced;
    }

    public int  getmSubModifierTotal() {
        return mSubModifierTotal;
    }

    public void setmSubModifierTotal(int mSubModifierTotal) {
        this.mSubModifierTotal = mSubModifierTotal;
    }

    public static Creator<ModifiersValue> getCREATOR() {
        return CREATOR;
    }

    protected ModifiersValue(Parcel in) {
        mModifierName = in.readString();
        mModifierId = in.readString();
        mModifierDefault = in.readString();
        mProductModifierValueId = in.readString();
        mModifierValuePrice = in.readString();
        mSubModifierTotal = in.readInt();
        isChekced = in.readByte() != 0;
    }

    public String getmProductModifierValueId() {
        return mProductModifierValueId;
    }

    public String getmModifierValuePrice() {
        return mModifierValuePrice;
    }

    public void setmModifierValuePrice(String mModifierValuePrice) {
        this.mModifierValuePrice = mModifierValuePrice;
    }

    public void setmProductModifierValueId(String mProductModifierValueId) {
        this.mProductModifierValueId = mProductModifierValueId;
    }

    public static final Creator<ModifiersValue> CREATOR = new Creator<ModifiersValue>() {
        @Override
        public ModifiersValue createFromParcel(Parcel in) {
            return new ModifiersValue(in);
        }

        @Override
        public ModifiersValue[] newArray(int size) {
            return new ModifiersValue[size];
        }
    };

    public boolean getChekced() {
        return isChekced;
    }

    public void setChekced(boolean isChekced) {
        this.isChekced = isChekced;
    }

    public String getmModifierName() {
        return mModifierName;
    }

    public void setmModifierName(String mModifierName) {
        this.mModifierName = mModifierName;
    }

    public String getmModifierId() {
        return mModifierId;
    }

    public void setmModifierId(String mModifierId) {
        this.mModifierId = mModifierId;
    }

    public String getmModifierDefault() {
        return mModifierDefault;
    }

    public void setmModifierDefault(String mModifierDefault) {
        this.mModifierDefault = mModifierDefault;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mModifierName);
        dest.writeString(mModifierId);
        dest.writeString(mModifierDefault);
        dest.writeString(mProductModifierValueId);
        dest.writeString(mModifierValuePrice);
        dest.writeInt(mSubModifierTotal);
        dest.writeByte((byte) (isChekced ? 1 : 0));
    }
}
