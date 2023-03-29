package com.app.sushi.tei.Model.Landing;

import java.io.Serializable;
import java.util.List;

import com.google.gson.annotations.SerializedName;

public class LandingModel implements Serializable {

	@SerializedName("common")
	private Common common;

	@SerializedName("status")
	private String status;

	@SerializedName("result_set")
	private List<ResultSetItem> resultSet;

	public void setCommon(Common common){
		this.common = common;
	}

	public Common getCommon(){
		return common;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setResultSet(List<ResultSetItem> resultSet){
		this.resultSet = resultSet;
	}

	public List<ResultSetItem> getResultSet(){
		return resultSet;
	}
}