package com.app.sushi.tei.Model.location;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class StructuredFormatting{

	@SerializedName("main_text_matched_substrings")
	private List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings;

	@SerializedName("secondary_text")
	private String secondaryText;

	@SerializedName("main_text")
	private String mainText;

	public void setMainTextMatchedSubstrings(List<MainTextMatchedSubstringsItem> mainTextMatchedSubstrings){
		this.mainTextMatchedSubstrings = mainTextMatchedSubstrings;
	}

	public List<MainTextMatchedSubstringsItem> getMainTextMatchedSubstrings(){
		return mainTextMatchedSubstrings;
	}

	public void setSecondaryText(String secondaryText){
		this.secondaryText = secondaryText;
	}

	public String getSecondaryText(){
		return secondaryText;
	}

	public void setMainText(String mainText){
		this.mainText = mainText;
	}

	public String getMainText(){
		return mainText;
	}

	@Override
 	public String toString(){
		return 
			"StructuredFormatting{" + 
			"main_text_matched_substrings = '" + mainTextMatchedSubstrings + '\'' + 
			",secondary_text = '" + secondaryText + '\'' + 
			",main_text = '" + mainText + '\'' + 
			"}";
		}
}