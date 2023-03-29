package com.app.sushi.tei.Model.ProductList;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 17/5/18.
 */

public class    SetMenuTitle  implements Parcelable {


    private String mTitleMenuName="";

    private String mTitleMenuId="";

    private String mAppliedPrice="";

    private String mDefaultSelectId="";

    private int mMinSelect=0;

    private int mMaxSelect=0;

    private int mTotalQuantity=0;

    private double mTempPrice=0;

    private int tQuantity=0;

    private String menu_component_multipleselection_apply="";

    private String menu_component_modifier_apply="";

    private List<SetMenuModifier> setMenuModifierList=new ArrayList<>();

    private int setMenuTitleSelectCount;

    public int getSetMenuTitleSelectCount() {
        return setMenuTitleSelectCount;
    }

    public void setSetMenuTitleSelectCount(int setMenuTitleSelectCount) {
        this.setMenuTitleSelectCount = setMenuTitleSelectCount;
    }

    public SetMenuTitle(){

    }

    public int gettQuantity() {
        return tQuantity;
    }

    public void settQuantity(int tQuantity) {
        this.tQuantity = tQuantity;
    }

    public double getmTempPrice() {
        return mTempPrice;
    }

    public void setmTempPrice(double mTempPrice) {
        this.mTempPrice = mTempPrice;
    }


    public String getmTitleMenuName() {
        return mTitleMenuName;
    }

    public void setmTitleMenuName(String mTitleMenuName) {
        this.mTitleMenuName = mTitleMenuName;
    }

    public String getmultipleselection_apply() {
        return menu_component_multipleselection_apply;
    }

    public void setmultipleselection_apply(String menu_component_multipleselection_apply) {
        this.menu_component_multipleselection_apply = menu_component_multipleselection_apply;
    }

    public String getmenu_component_modifier_apply() {
        return menu_component_modifier_apply;
    }

    public void setmenu_component_modifier_apply(String menu_component_modifier_apply) {
        this.menu_component_modifier_apply = menu_component_modifier_apply;
    }


    public String getmTitleMenuId() {
        return mTitleMenuId;
    }

    public void setmTitleMenuId(String mTitleMenuId) {
        this.mTitleMenuId = mTitleMenuId;
    }

    public String getmAppliedPrice() {
        return mAppliedPrice;
    }

    public void setmAppliedPrice(String mAppliedPrice) {
        this.mAppliedPrice = mAppliedPrice;
    }

    public String getmDefaultSelectId() {
        return mDefaultSelectId;
    }

    public void setmDefaultSelectId(String mDefaultSelectId) {
        this.mDefaultSelectId = mDefaultSelectId;
    }

    public int getmMinSelect() {
        return mMinSelect;
    }

    public void setmMinSelect(int mMinSelect) {
        this.mMinSelect = mMinSelect;
    }

    public int getmMaxSelect() {
        return mMaxSelect;
    }

    public void setmMaxSelect(int mMaxSelect) {
        this.mMaxSelect = mMaxSelect;
    }

    public List<SetMenuModifier> getSetMenuModifierList() {
        return setMenuModifierList;
    }

    public int getmTotalQuantity() {
        return mTotalQuantity;
    }

    public void setmTotalQuantity(int mTotalQuantity) {
        this.mTotalQuantity = mTotalQuantity;
    }

    public void setSetMenuModifierList(List<SetMenuModifier> setMenuModifierList) {
        this.setMenuModifierList = setMenuModifierList;
    }

    public static Creator<SetMenuTitle> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mTitleMenuName);
        dest.writeString(this.menu_component_multipleselection_apply);
        dest.writeString(this.menu_component_modifier_apply);
        dest.writeString(this.mTitleMenuId);
        dest.writeString(this.mAppliedPrice);
        dest.writeString(this.mDefaultSelectId);
        dest.writeInt(this.mMinSelect);
        dest.writeInt(this.mMaxSelect);
        dest.writeInt(this.mTotalQuantity);
        dest.writeDouble(this.mTempPrice);
        dest.writeInt(this.tQuantity);
        dest.writeTypedList(this.setMenuModifierList);
    }

    protected SetMenuTitle(Parcel in) {
        this.mTitleMenuName = in.readString();
        this.menu_component_multipleselection_apply=in.readString();
        this.menu_component_modifier_apply=in.readString();
        this.mTitleMenuId = in.readString();
        this.mAppliedPrice = in.readString();
        this.mDefaultSelectId = in.readString();
        this.mMinSelect = in.readInt();
        this.mMaxSelect = in.readInt();
        this.mTotalQuantity = in.readInt();
        this.mTempPrice = in.readDouble();
        this.tQuantity = in.readInt();
        this.setMenuModifierList = in.createTypedArrayList(SetMenuModifier.CREATOR);
    }

    public static final Creator<SetMenuTitle> CREATOR = new Creator<SetMenuTitle>() {
        @Override
        public SetMenuTitle createFromParcel(Parcel source) {
            return new SetMenuTitle(source);
        }

        @Override
        public SetMenuTitle[] newArray(int size) {
            return new SetMenuTitle[size];
        }
    };
}
