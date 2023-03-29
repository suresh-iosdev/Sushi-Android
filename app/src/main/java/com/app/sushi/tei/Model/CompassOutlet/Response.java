package com.app.sushi.tei.Model.CompassOutlet;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class Response{

	@SerializedName("status")
	private String status;

	@SerializedName("result_set")
	private List<OutletResultSetItem> resultSet;

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setResultSet(List<OutletResultSetItem> resultSet){
		this.resultSet = resultSet;
	}

	public List<OutletResultSetItem> getResultSet(){
		return resultSet;
	}
}