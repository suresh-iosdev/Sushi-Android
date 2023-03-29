package com.app.sushi.tei.Model.location;


import com.google.gson.annotations.SerializedName;


public class MatchedSubstringsItem{

	@SerializedName("offset")
	private int offset;

	@SerializedName("length")
	private int length;

	public void setOffset(int offset){
		this.offset = offset;
	}

	public int getOffset(){
		return offset;
	}

	public void setLength(int length){
		this.length = length;
	}

	public int getLength(){
		return length;
	}

	@Override
 	public String toString(){
		return 
			"MatchedSubstringsItem{" + 
			"offset = '" + offset + '\'' + 
			",length = '" + length + '\'' + 
			"}";
		}
}