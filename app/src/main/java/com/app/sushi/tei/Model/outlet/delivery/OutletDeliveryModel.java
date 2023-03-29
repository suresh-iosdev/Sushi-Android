package com.app.sushi.tei.Model.outlet.delivery;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class OutletDeliveryModel implements Serializable {

	@SerializedName("status")
	private String status;

	@SerializedName("result_set")
	private ResultSet resultSet;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setResultSet(ResultSet resultSet){
		this.resultSet = resultSet;
	}

	public ResultSet getResultSet(){
		return resultSet;
	}
}