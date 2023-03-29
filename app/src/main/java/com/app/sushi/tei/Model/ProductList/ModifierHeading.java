package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by root on 2/4/18.
 */

public class ModifierHeading implements Parcelable {

    private String mModifierHeading="";

    private String mModifierHeadingId="";

    private int mModifierMinSelect=0;
    private int mModifierMaxSelect=0;

    private int mMaxSelected=0;

    private List<ModifiersValue> modifiersValueList;

    private int tQuantity=0;

    private int modifierMinSelect;

    public static Double subModifierPrice;

    public int getModifierMinSelect() {
        return modifierMinSelect;
    }

    public void setModifierMinSelect(int modifierMinSelect) {
        this.modifierMinSelect = modifierMinSelect;
    }

    public ModifierHeading(){}

    protected ModifierHeading(Parcel in) {
        mModifierHeading = in.readString();
        mModifierHeadingId = in.readString();
        mMaxSelected=in.readInt();
        tQuantity = in.readInt();
        modifiersValueList = in.createTypedArrayList(ModifiersValue.CREATOR);
    }

    public List<ModifiersValue> getModifiersValueList() {
        return modifiersValueList;
    }

    public void setModifiersValueList(List<ModifiersValue> modifiersValueList) {
        this.modifiersValueList = modifiersValueList;
    }

    public int gettQuantity() {
        return tQuantity;
    }

    public void settQuantity(int tQuantity) {
        this.tQuantity = tQuantity;
    }

    public static Creator<ModifierHeading> getCREATOR() {
        return CREATOR;
    }

    public int getmMaxSelected() {
        return mMaxSelected;
    }

    public void setmMaxSelected(int mMaxSelected) {
        this.mMaxSelected = mMaxSelected;
    }

    public static final Creator<ModifierHeading> CREATOR = new Creator<ModifierHeading>() {
        @Override
        public ModifierHeading createFromParcel(Parcel in) {
            return new ModifierHeading(in);
        }

        @Override
        public ModifierHeading[] newArray(int size) {
            return new ModifierHeading[size];
        }
    };

    public int getmModifierMinSelect() {
        return mModifierMinSelect;
    }

    public void setmModifierMinSelect(int mModifierMinSelect) {
        this.mModifierMinSelect = mModifierMinSelect;
    }

    public int getmModifierMaxSelect() {
        return mModifierMaxSelect;
    }

    public void setmModifierMaxSelect(int mModifierMaxSelect) {
        this.mModifierMaxSelect = mModifierMaxSelect;
    }

    public String getmModifierHeading() {
        return mModifierHeading;
    }

    public void setmModifierHeading(String mModifierHeading) {
        this.mModifierHeading = mModifierHeading;
    }

    public List<ModifiersValue> getModifiersList() {
        return modifiersValueList;
    }

    public void setModifiersList(List<ModifiersValue> modifiersValueList) {
        this.modifiersValueList = modifiersValueList;
    }

    public String getmModifierHeadingId() {
        return mModifierHeadingId;
    }

    public void setmModifierHeadingId(String mModifierHeadingId) {
        this.mModifierHeadingId = mModifierHeadingId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(mModifierHeading);
        dest.writeString(mModifierHeadingId);
        dest.writeInt(mMaxSelected);
        dest.writeTypedList(modifiersValueList);
        dest.writeInt(tQuantity);
    }
}
