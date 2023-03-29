package com.app.sushi.tei.Model.Voucher;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class Vouchers implements Serializable {

	@SerializedName("common")
	private Common common;

	@SerializedName("message")
	private String message;

	@SerializedName("status")
	private String status;

	@SerializedName("result_set")
	private ResultSet resultSet;

	public void setCommon(Common common){
		this.common = common;
	}

	public Common getCommon(){
		return common;
	}

	public void setMessage(String message){
		this.message = message;
	}

	public String getMessage(){
		return message;
	}

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