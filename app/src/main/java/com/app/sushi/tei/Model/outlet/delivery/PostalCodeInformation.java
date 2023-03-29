package com.app.sushi.tei.Model.outlet.delivery;


import com.google.gson.annotations.SerializedName;
import java.io.Serializable;

public class PostalCodeInformation implements Serializable {

	@SerializedName("zip_id")
	private String zipId;

	@SerializedName("zip_sname")
	private String zipSname;

	@SerializedName("zip_buno")
	private String zipBuno;

	@SerializedName("zip_addtype")
	private String zipAddtype;

	@SerializedName("zip_buname")
	private String zipBuname;

	@SerializedName("zip_code")
	private String zipCode;

	public void setZipId(String zipId){
		this.zipId = zipId;
	}

	public String getZipId(){
		return zipId;
	}

	public void setZipSname(String zipSname){
		this.zipSname = zipSname;
	}

	public String getZipSname(){
		return zipSname;
	}

	public void setZipBuno(String zipBuno){
		this.zipBuno = zipBuno;
	}

	public String getZipBuno(){
		return zipBuno;
	}

	public void setZipAddtype(String zipAddtype){
		this.zipAddtype = zipAddtype;
	}

	public String getZipAddtype(){
		return zipAddtype;
	}

	public void setZipBuname(String zipBuname){
		this.zipBuname = zipBuname;
	}

	public String getZipBuname(){
		return zipBuname;
	}

	public void setZipCode(String zipCode){
		this.zipCode = zipCode;
	}

	public String getZipCode(){
		return zipCode;
	}
}