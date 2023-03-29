package com.app.sushi.tei.Model.location;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class PredictionsItem{

	@SerializedName("reference")
	private String reference;

	@SerializedName("types")
	private List<String> types;

	@SerializedName("matched_substrings")
	private List<MatchedSubstringsItem> matchedSubstrings;

	@SerializedName("terms")
	private List<TermsItem> terms;

	@SerializedName("structured_formatting")
	private StructuredFormatting structuredFormatting;

	@SerializedName("description")
	private String description;

	@SerializedName("id")
	private String id;

	@SerializedName("place_id")
	private String placeId;

	public void setReference(String reference){
		this.reference = reference;
	}

	public String getReference(){
		return reference;
	}

	public void setTypes(List<String> types){
		this.types = types;
	}

	public List<String> getTypes(){
		return types;
	}

	public void setMatchedSubstrings(List<MatchedSubstringsItem> matchedSubstrings){
		this.matchedSubstrings = matchedSubstrings;
	}

	public List<MatchedSubstringsItem> getMatchedSubstrings(){
		return matchedSubstrings;
	}

	public void setTerms(List<TermsItem> terms){
		this.terms = terms;
	}

	public List<TermsItem> getTerms(){
		return terms;
	}

	public void setStructuredFormatting(StructuredFormatting structuredFormatting){
		this.structuredFormatting = structuredFormatting;
	}

	public StructuredFormatting getStructuredFormatting(){
		return structuredFormatting;
	}

	public void setDescription(String description){
		this.description = description;
	}

	public String getDescription(){
		return description;
	}

	public void setId(String id){
		this.id = id;
	}

	public String getId(){
		return id;
	}

	public void setPlaceId(String placeId){
		this.placeId = placeId;
	}

	public String getPlaceId(){
		return placeId;
	}

	@Override
 	public String toString(){
		return 
			"PredictionsItem{" + 
			"reference = '" + reference + '\'' + 
			",types = '" + types + '\'' + 
			",matched_substrings = '" + matchedSubstrings + '\'' + 
			",terms = '" + terms + '\'' + 
			",structured_formatting = '" + structuredFormatting + '\'' + 
			",description = '" + description + '\'' + 
			",id = '" + id + '\'' + 
			",place_id = '" + placeId + '\'' + 
			"}";
		}
}