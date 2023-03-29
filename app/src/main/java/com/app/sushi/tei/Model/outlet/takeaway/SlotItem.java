package com.app.sushi.tei.Model.outlet.takeaway;

import com.google.gson.annotations.SerializedName;
import java.io.Serializable;


public class SlotItem implements Serializable {

	@SerializedName("slottext")
	private String slottext;

	@SerializedName("days")
	private String days;

	public void setSlottext(String slottext){
		this.slottext = slottext;
	}

	public String getSlottext(){
		return slottext;
	}

	public void setDays(String days){
		this.days = days;
	}

	public String getDays(){
		return days;
	}
}