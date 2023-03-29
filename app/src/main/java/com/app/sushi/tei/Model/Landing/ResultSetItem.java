package com.app.sushi.tei.Model.Landing;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class ResultSetItem implements Serializable {

	@SerializedName("landing_title")
	private String landingTitle;

	@SerializedName("landing_description")
	private String landingDescription;

	@SerializedName("landing_type")
	private String landingType;

	@SerializedName("landing_short_description")
	private String landingShortDescription;

	@SerializedName("landing_slug")
	private String landingSlug;

	@SerializedName("landing_image")
	private String landingImage;

	@SerializedName("landing_id")
	private String landingId;

	@SerializedName("type")
	private String type;

	@SerializedName("landing_sort_order")
	private Object landingSortOrder;

	public void setLandingTitle(String landingTitle){
		this.landingTitle = landingTitle;
	}

	public String getLandingTitle(){
		return landingTitle;
	}

	public void setLandingDescription(String landingDescription){
		this.landingDescription = landingDescription;
	}

	public String getLandingDescription(){
		return landingDescription;
	}

	public void setLandingType(String landingType){
		this.landingType = landingType;
	}

	public String getLandingType(){
		return landingType;
	}

	public void setLandingShortDescription(String landingShortDescription){
		this.landingShortDescription = landingShortDescription;
	}

	public String getLandingShortDescription(){
		return landingShortDescription;
	}

	public void setLandingSlug(String landingSlug){
		this.landingSlug = landingSlug;
	}

	public String getLandingSlug(){
		return landingSlug;
	}

	public void setLandingImage(String landingImage){
		this.landingImage = landingImage;
	}

	public String getLandingImage(){
		return landingImage;
	}

	public void setLandingId(String landingId){
		this.landingId = landingId;
	}

	public String getLandingId(){
		return landingId;
	}

	public void setType(String type){
		this.type = type;
	}

	public String getType(){
		return type;
	}

	public void setLandingSortOrder(Object landingSortOrder){
		this.landingSortOrder = landingSortOrder;
	}

	public Object getLandingSortOrder(){
		return landingSortOrder;
	}
}