package com.app.sushi.tei.Model.banner;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class ResultSetItem implements Serializable {

	@SerializedName("resources_location")
	private String resourcesLocation;

	@SerializedName("banner_id")
	private String bannerId;

	@SerializedName("banner_sequence")
	private String bannerSequence;

	@SerializedName("banner_status")
	private String bannerStatus;

	@SerializedName("banner_name")
	private String bannerName;

	@SerializedName("banner_image")
	private String bannerImage;

	@SerializedName("banner_description_mobile")
	private String bannerDescriptionMobile;

	@SerializedName("banner_description")
	private String bannerDescription;

	public void setResourcesLocation(String resourcesLocation){
		this.resourcesLocation = resourcesLocation;
	}

	public String getResourcesLocation(){
		return resourcesLocation;
	}

	public void setBannerId(String bannerId){
		this.bannerId = bannerId;
	}

	public String getBannerId(){
		return bannerId;
	}

	public void setBannerSequence(String bannerSequence){
		this.bannerSequence = bannerSequence;
	}

	public String getBannerSequence(){
		return bannerSequence;
	}

	public void setBannerStatus(String bannerStatus){
		this.bannerStatus = bannerStatus;
	}

	public String getBannerStatus(){
		return bannerStatus;
	}

	public void setBannerName(String bannerName){
		this.bannerName = bannerName;
	}

	public String getBannerName(){
		return bannerName;
	}

	public void setBannerImage(String bannerImage){
		this.bannerImage = bannerImage;
	}

	public String getBannerImage(){
		return bannerImage;
	}

	public void setBannerDescriptionMobile(String bannerDescriptionMobile){
		this.bannerDescriptionMobile = bannerDescriptionMobile;
	}

	public String getBannerDescriptionMobile(){
		return bannerDescriptionMobile;
	}

	public void setBannerDescription(String bannerDescription){
		this.bannerDescription = bannerDescription;
	}

	public String getBannerDescription(){
		return bannerDescription;
	}
}