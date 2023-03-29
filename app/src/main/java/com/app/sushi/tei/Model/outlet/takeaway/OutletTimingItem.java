package com.app.sushi.tei.Model.outlet.takeaway;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class OutletTimingItem implements Serializable {

	@SerializedName("slot")
	private List<SlotItem> slot;

	@SerializedName("title")
	private String title;

	public void setSlot(List<SlotItem> slot){
		this.slot = slot;
	}

	public List<SlotItem> getSlot(){
		return slot;
	}

	public void setTitle(String title){
		this.title = title;
	}

	public String getTitle(){
		return title;
	}
}