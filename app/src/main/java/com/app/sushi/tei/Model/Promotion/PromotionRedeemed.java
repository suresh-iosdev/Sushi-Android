package com.app.sushi.tei.Model.Promotion;

/**
 * Created by vishnu on 2/1/18.
 */

public class PromotionRedeemed
{

/*          "promotion_title": "GE10",
                  "promotion_id": "66",
                  "promotion_image": null,
                  "promo_code": "GE10"*/


    String promotionTitle;
    String promotionId;
    String promotionImage;
    String promotionCode;
    String PromotDiscription;
    String promotionPercentage;
    String promoDaysleft;
    String promoMaxAmt;
    String promoType;

    public String getPromotDiscription() {
        return PromotDiscription;
    }

    public void setPromotDiscription(String promotDiscription) {
        PromotDiscription = promotDiscription;
    }

    public String getPromotionTitle() {
        return promotionTitle;
    }

    public void setPromotionTitle(String promotionTitle) {
        this.promotionTitle = promotionTitle;
    }

    public String getPromotionId() {
        return promotionId;
    }

    public void setPromotionId(String promotionId) {
        this.promotionId = promotionId;
    }

    public String getPromotionImage() {
        return promotionImage;
    }

    public void setPromotionImage(String promotionImage) {
        this.promotionImage = promotionImage;
    }

    public String getPromotionCode() {
        return promotionCode;
    }

    public void setPromotionCode(String promotionCode) {
        this.promotionCode = promotionCode;
    }

    public String getPromotionPercentage() {
        return promotionPercentage;
    }

    public void setPromotionPercentage(String promotionPercentage) {
        this.promotionPercentage = promotionPercentage;
    }

    public String getPromoDaysleft() {
        return promoDaysleft;
    }

    public void setPromoDaysleft(String promoDaysleft) {
        this.promoDaysleft = promoDaysleft;
    }

    public String getPromoMaxAmt() {
        return promoMaxAmt;
    }

    public void setPromoMaxAmt(String promoMaxAmt) {
        this.promoMaxAmt = promoMaxAmt;
    }

    public String getPromoType() {
        return promoType;
    }

    public void setPromoType(String promoType) {
        this.promoType = promoType;
    }
}

