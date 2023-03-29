package com.app.sushi.tei.Model.outlet.takeaway;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;


public class OutletTakeawayModel implements Serializable {

	@SerializedName("status")
	private String status;

	@SerializedName("result_set")
	private List<ResultSetItem> resultSet;

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