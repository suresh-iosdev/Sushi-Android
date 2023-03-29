package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by root on 17/5/18.
 */

public class SetMenuModifier implements Parcelable {


    private String mModifierId="";

    private String mModifierName="";

    private String mModifierAliasName="";

    private String mModifierSku="";

    private String mMinSelect="";

    private String mMaxSelect="";

    private String mModifierSlug="";

    private String mModifierPrice ="";

    private boolean isDefault=false;

    private boolean isChecked=false;

    private boolean hasModifier=false;

    private int mQuantity=0;

    private int mQuantityUpdates=0;

    private String mMin_Max_apply ="";

    private Double totalpPrize=0.00;

    private String totalQuantity="0";

    private String sub_multipleselection_apply="";

    private String sub_modifier_apply="";

    private List<ModifierHeading> modifierHeadingList;

    private int modifierSelectCount;

    private String applyPrice;

    public SetMenuModifier()
    {

    }

    public int getModifierSelectCount() {
        return modifierSelectCount;
    }

    public void setModifierSelectCount(int modifierSelectCount) {
        this.modifierSelectCount = modifierSelectCount;
    }

    public Double getTotalpPrize() {
        return totalpPrize;
    }

    public void setTotalpPrize(Double totalpPrize) {
        this.totalpPrize = totalpPrize;
    }

    public String getTotalQuantity() {
        return totalQuantity;
    }

    public void setTotalQuantity(String totalQuantity) {
        this.totalQuantity = totalQuantity;
    }

    public boolean isDefault() {
        return isDefault;
    }

    public void setDefault(boolean aDefault) {
        isDefault = aDefault;
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

    public String getmModifierSku() {
        return mModifierSku;
    }

    public void setmModifierSku(String mModifierSku) {
        this.mModifierSku = mModifierSku;
    }

    public String getmMinSelect() {
        return mMinSelect;
    }

    public void setmMinSelect(String mMinSelect) {
        this.mMinSelect = mMinSelect;
    }

    public String getmMaxSelect() {
        return mMaxSelect;
    }

    public void setmMaxSelect(String mMaxSelect) {
        this.mMaxSelect = mMaxSelect;
    }

    public String getmModifierSlug() {
        return mModifierSlug;
    }

    public void setmModifierSlug(String mModifierSlug) {
        this.mModifierSlug = mModifierSlug;
    }

    public String getmModifierPrice() {
        return mModifierPrice;
    }

    public void setmModifierPrice(String mModifierPrice) {
        this.mModifierPrice = mModifierPrice;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public boolean getHasModifier() {
        return hasModifier;
    }

    public void setHasModifier(boolean hasModifier) {
        this.hasModifier = hasModifier;
    }




    public String getsub_multipleselection_apply() {
        return sub_multipleselection_apply;
    }

    public void setsub_multipleselection_apply(String sub_multipleselection_apply) {
        this.sub_multipleselection_apply = sub_multipleselection_apply;
    }

    public String getsub_modifier_apply() {
        return sub_modifier_apply;
    }

    public void setsub_modifier_apply(String sub_modifier_apply) {
        this.sub_modifier_apply = sub_modifier_apply;
    }

    public List<ModifierHeading> getModifierHeadingList() {
        return modifierHeadingList;
    }

    public void setModifierHeadingList(List<ModifierHeading> modifierHeadingList) {
        this.modifierHeadingList = modifierHeadingList;
    }

    public String getmModifierAliasName() {
        return mModifierAliasName;
    }

    public void setmModifierAliasName(String mModifierAliasName) {
        this.mModifierAliasName = mModifierAliasName;
    }

    public int getmQuantity() {
        return mQuantity;
    }

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public int getmQuantityUpdates() {
        return mQuantityUpdates;
    }

    public void setmQuantityUpdates(int mQuantityUpdates) {
        this.mQuantityUpdates = mQuantityUpdates;
    }

    public String getmMin_Max_apply() {
        return mMin_Max_apply;
    }

    public void setmMin_Max_apply(String mMin_Max_apply) {
        this.mMin_Max_apply = mMin_Max_apply;
    }

    public String getApplyPrice() {
        return applyPrice;
    }

    public void setApplyPrice(String applyPrice) {
        this.applyPrice = applyPrice;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mModifierId);
        dest.writeString(this.mModifierName);
        dest.writeString(this.mModifierAliasName);
        dest.writeString(this.mModifierSku);
        dest.writeString(this.mMinSelect);
        dest.writeString(this.mMaxSelect);
        dest.writeString(this.mModifierSlug);
        dest.writeString(this.mModifierPrice);
        dest.writeByte(this.isDefault ? (byte) 1 : (byte) 0);
        dest.writeByte(this.isChecked ? (byte) 1 : (byte) 0);
        dest.writeByte(this.hasModifier ? (byte) 1 : (byte) 0);
        dest.writeInt(this.mQuantity);
        dest.writeInt(this.mQuantityUpdates);
        dest.writeString(this.mMin_Max_apply);
        dest.writeValue(this.totalpPrize);
        dest.writeString(this.totalQuantity);
        dest.writeString(this.sub_modifier_apply);
        dest.writeString(this.sub_multipleselection_apply);
        dest.writeTypedList(this.modifierHeadingList);
        dest.writeString(this.applyPrice);
    }

    protected SetMenuModifier(Parcel in) {
        this.mModifierId = in.readString();
        this.mModifierName = in.readString();
        this.mModifierAliasName = in.readString();
        this.mModifierSku = in.readString();
        this.mMinSelect = in.readString();
        this.mMaxSelect = in.readString();
        this.mModifierSlug = in.readString();
        this.mModifierPrice = in.readString();
        this.isDefault = in.readByte() != 0;
        this.isChecked = in.readByte() != 0;
        this.hasModifier = in.readByte() != 0;
        this.mQuantity = in.readInt();
        this.mQuantityUpdates = in.readInt();
        this.mMin_Max_apply = in.readString();
        this.totalpPrize = (Double) in.readValue(Double.class.getClassLoader());
        this.totalQuantity = in.readString();
        this.sub_multipleselection_apply = in.readString();
        this.sub_modifier_apply = in.readString();
        this.applyPrice = in.readString();
        this.modifierHeadingList = in.createTypedArrayList(ModifierHeading.CREATOR);
    }

    public static final Creator<SetMenuModifier> CREATOR = new Creator<SetMenuModifier>() {
        @Override
        public SetMenuModifier createFromParcel(Parcel source) {
            return new SetMenuModifier(source);
        }

        @Override
        public SetMenuModifier[] newArray(int size) {
            return new SetMenuModifier[size];
        }
    };
}
