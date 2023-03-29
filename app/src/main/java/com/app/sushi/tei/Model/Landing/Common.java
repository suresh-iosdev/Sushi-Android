package com.app.sushi.tei.Model.Landing;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Common implements Serializable {

	@SerializedName("image_source")
	private String imageSource;

	public void setImageSource(String imageSource){
		this.imageSource = imageSource;
	}

	public String getImageSource(){
		return imageSource;
	}
}