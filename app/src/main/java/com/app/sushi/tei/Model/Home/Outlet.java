package com.app.sushi.tei.Model.Home;

import java.io.Serializable;

/**
 * Created by root on 29/3/18.
 */

public class Outlet implements Serializable {

    private String mOutletId="";

    private String mOutletName="";

    private String mOutletDeliveryTime="";

    private String mCurrentTAT="";

    private String mOutletAddress="";

    private String mOutletPostalCode="";

    private String mMarketLatitud="";

    private String mMarkerLongtitude="";

    private String mDineTat="";

    private String mDeliveryTat="";

    private String mPickupTat="";

    private String mZoneId="";

    private String mZoneName="";


    public String getmZoneName() {
        return mZoneName;
    }

    public void setmZoneName(String mZoneName) {
        this.mZoneName = mZoneName;
    }

    public String getmZoneId() {
        return mZoneId;
    }

    public void setmZoneId(String mZoneId) {
        this.mZoneId = mZoneId;
    }

    public String getmCurrentTAT() {
        return mCurrentTAT;
    }

    public void setmCurrentTAT(String mCurrentTAT) {
        this.mCurrentTAT = mCurrentTAT;
    }

    public String getmDineTat() {
        return mDineTat;
    }

    public void setmDineTat(String mDineTat) {
        this.mDineTat = mDineTat;
    }

    public String getmDeliveryTat() {
        return mDeliveryTat;
    }

    public void setmDeliveryTat(String mDeliveryTat) {
        this.mDeliveryTat = mDeliveryTat;
    }

    public String getmPickupTat() {
        return mPickupTat;
    }

    public void setmPickupTat(String mPickupTat) {
        this.mPickupTat = mPickupTat;
    }

    public String getmOutletId() {
        return mOutletId;
    }

    public void setmOutletId(String mOutletId) {
        this.mOutletId = mOutletId;
    }

    public String getmOutletName() {
        return mOutletName;
    }

    public void setmOutletName(String mOutletName) {
        this.mOutletName = mOutletName;
    }

    public String getmOutletDeliveryTime() {
        return mOutletDeliveryTime;
    }

    public void setmOutletDeliveryTime(String mOutletDeliveryTime) {
        this.mOutletDeliveryTime = mOutletDeliveryTime;
    }

    public String getmOutletAddress() {
        return mOutletAddress;
    }

    public void setmOutletAddress(String mOutletAddress) {
        this.mOutletAddress = mOutletAddress;
    }

    public String getmOutletPostalCode() {
        return mOutletPostalCode;
    }

    public void setmOutletPostalCode(String mOutletPostalCode) {
        this.mOutletPostalCode = mOutletPostalCode;
    }

    public String getmMarketLatitud() {
        return mMarketLatitud;
    }

    public void setmMarketLatitud(String mMarketLatitud) {
        this.mMarketLatitud = mMarketLatitud;
    }

    public String getmMarkerLongtitude() {
        return mMarkerLongtitude;
    }

    public void setmMarkerLongtitude(String mMarkerLongtitude) {
        this.mMarkerLongtitude = mMarkerLongtitude;
    }
}
