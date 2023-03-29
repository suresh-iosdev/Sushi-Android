package com.app.sushi.tei.Model.location;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class LocationResponse{

	@SerializedName("predictions")
	private List<PredictionsItem> predictions;

	@SerializedName("status")
	private String status;

	public void setPredictions(List<PredictionsItem> predictions){
		this.predictions = predictions;
	}

	public List<PredictionsItem> getPredictions(){
		return predictions;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	@Override
 	public String toString(){
		return 
			"LocationResponse{" + 
			"predictions = '" + predictions + '\'' + 
			",status = '" + status + '\'' + 
			"}";
		}
}