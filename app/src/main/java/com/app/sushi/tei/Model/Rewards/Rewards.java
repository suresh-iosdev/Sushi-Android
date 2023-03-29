package com.app.sushi.tei.Model.Rewards;

import java.io.Serializable;

/**
 * Created by vishnu on 28/12/17.
 */

public class Rewards implements Serializable {

    private String mlhRedeemPoint;

    private String mOrderPrimaryId;

    private String mlhRedeemAmount;

    private String mOrderDate;

    private String mOrderId;

    private String mOrderAvailabilityName;

    private String mDiscountType="";

    private String lh_from="";

    private String lh_created_on="";

    private String lh_expiry_on="";

    private String lh_reason="";

    private String AutoID="";
    private String OrderAmount="";
    private String Points="";
    private String ReceiptNo="";
    private String Remark="";
    private String TransactDate="";
    private String TransactTime="";

    public String getAutoID() {
        return AutoID;
    }

    public void setAutoID(String autoID) {
        AutoID = autoID;
    }

    public String getOrderAmount() {
        return OrderAmount;
    }

    public void setOrderAmount(String orderAmount) {
        OrderAmount = orderAmount;
    }

    public String getPoints() {
        return Points;
    }

    public void setPoints(String points) {
        Points = points;
    }

    public String getReceiptNo() {
        return ReceiptNo;
    }

    public void setReceiptNo(String receiptNo) {
        ReceiptNo = receiptNo;
    }

    public String getRemark() {
        return Remark;
    }

    public void setRemark(String remark) {
        Remark = remark;
    }

    public String getTransactDate() {
        return TransactDate;
    }

    public void setTransactDate(String transactDate) {
        TransactDate = transactDate;
    }

    public String getTransactTime() {
        return TransactTime;
    }

    public void setTransactTime(String transactTime) {
        TransactTime = transactTime;
    }

    public String getMlhRedeemPoint() {
        return mlhRedeemPoint;
    }

    public void setMlhRedeemPoint(String mlhRedeemPoint) {
        this.mlhRedeemPoint = mlhRedeemPoint;
    }

    public String getmOrderPrimaryId() {
        return mOrderPrimaryId;
    }

    public void setmOrderPrimaryId(String mOrderPrimaryId) {
        this.mOrderPrimaryId = mOrderPrimaryId;
    }

    public String getMlhRedeemAmount() {
        return mlhRedeemAmount;
    }

    public void setMlhRedeemAmount(String mlhRedeemAmount) {
        this.mlhRedeemAmount = mlhRedeemAmount;
    }

    public String getmOrderDate() {
        return mOrderDate;
    }

    public void setmOrderDate(String mOrderDate) {
        this.mOrderDate = mOrderDate;
    }

    public String getmOrderId() {
        return mOrderId;
    }

    public void setmOrderId(String mOrderId) {
        this.mOrderId = mOrderId;
    }

    public String getmOrderAvailabilityName() {
        return mOrderAvailabilityName;
    }

    public void setmOrderAvailabilityName(String mOrderAvailabilityName) {
        this.mOrderAvailabilityName = mOrderAvailabilityName;
    }

    public String getlh_from() {
        return lh_from;
    }

    public void setlh_from(String lh_from) {
        this.lh_from = lh_from;
    }


    public String getlh_reason() {
        return lh_reason;
    }

    public void setlh_reason(String lh_reason) {
        this.lh_reason = lh_reason;
    }



    public String getlh_created_on() {
        return lh_created_on;
    }

    public void setlh_created_on(String lh_created_on) {
        this.lh_created_on = lh_created_on;
    }


    public String getLh_expiry_on() {
        return lh_expiry_on;
    }

    public void setLh_expiry_on(String lh_expiry_on) {
        this.lh_expiry_on = lh_expiry_on;
    }

    public String getmDiscountType() {
        return mDiscountType;
    }

    public void setmDiscountType(String mDiscountType) {
        this.mDiscountType = mDiscountType;
    }
}
