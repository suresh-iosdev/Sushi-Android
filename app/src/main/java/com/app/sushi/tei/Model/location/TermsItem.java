package com.app.sushi.tei.Model.location;


import com.google.gson.annotations.SerializedName;


public class TermsItem{

	@SerializedName("offset")
	private int offset;

	@SerializedName("value")
	private String value;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setValue(String value){
		this.value = value;
	}

	public String getValue(){
		return value;
	}

	@Override
 	public String toString(){
		return 
			"TermsItem{" + 
			"offset = '" + offset + '\'' + 
			",value = '" + value + '\'' + 
			"}";
		}
}