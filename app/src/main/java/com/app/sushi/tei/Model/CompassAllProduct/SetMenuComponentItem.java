package com.app.sushi.tei.Model.CompassAllProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class SetMenuComponentItem implements Parcelable {

	public SetMenuComponentItem(){

	}

	@SerializedName("menu_set_component_name")
	private String menuSetComponentName;

	@SerializedName("menu_component_name")
	private String menuComponentName;

	@SerializedName("menu_component_sequence")
	private String menuComponentSequence;

	@SerializedName("menu_component_apply_price")
	private String menuComponentApplyPrice;

	@SerializedName("menu_set_component_id")
	private String menuSetComponentId;

	@SerializedName("menu_component_min_select")
	private String menuComponentMinSelect;

	@SerializedName("menu_component_id")
	private String menuComponentId;

	@SerializedName("menu_component_default_select")
	private String menuComponentDefaultSelect;

	@SerializedName("menu_component_pieces_count")
	private String menuComponentPiecesCount;

	@SerializedName("menu_component_max_select")
	private String menuComponentMaxSelect;

	@SerializedName("product_details")
	private List<List<ProductDetailsItemItem>> productDetails;

	@SerializedName("menu_component_modifier_apply")
	private String menuComponentModifierApply;

    protected SetMenuComponentItem(Parcel in) {
        menuSetComponentName = in.readString();
        menuComponentName = in.readString();
        menuComponentSequence = in.readString();
        menuComponentApplyPrice = in.readString();
        menuSetComponentId = in.readString();
        menuComponentMinSelect = in.readString();
        menuComponentId = in.readString();
        menuComponentDefaultSelect = in.readString();
        menuComponentPiecesCount = in.readString();
        menuComponentMaxSelect = in.readString();
        menuComponentModifierApply = in.readString();
    }

    public static final Creator<SetMenuComponentItem> CREATOR = new Creator<SetMenuComponentItem>() {
        @Override
        public SetMenuComponentItem createFromParcel(Parcel in) {
            return new SetMenuComponentItem(in);
        }

        @Override
        public SetMenuComponentItem[] newArray(int size) {
            return new SetMenuComponentItem[size];
        }
    };

    public void setMenuSetComponentName(String menuSetComponentName){
		this.menuSetComponentName = menuSetComponentName;
	}

	public String getMenuSetComponentName(){
		return menuSetComponentName;
	}

	public void setMenuComponentName(String menuComponentName){
		this.menuComponentName = menuComponentName;
	}

	public String getMenuComponentName(){
		return menuComponentName;
	}

	public void setMenuComponentSequence(String menuComponentSequence){
		this.menuComponentSequence = menuComponentSequence;
	}

	public String getMenuComponentSequence(){
		return menuComponentSequence;
	}

	public void setMenuComponentApplyPrice(String menuComponentApplyPrice){
		this.menuComponentApplyPrice = menuComponentApplyPrice;
	}

	public String getMenuComponentApplyPrice(){
		return menuComponentApplyPrice;
	}

	public void setMenuSetComponentId(String menuSetComponentId){
		this.menuSetComponentId = menuSetComponentId;
	}

	public String getMenuSetComponentId(){
		return menuSetComponentId;
	}

	public void setMenuComponentMinSelect(String menuComponentMinSelect){
		this.menuComponentMinSelect = menuComponentMinSelect;
	}

	public String getMenuComponentMinSelect(){
		return menuComponentMinSelect;
	}

	public void setMenuComponentId(String menuComponentId){
		this.menuComponentId = menuComponentId;
	}

	public String getMenuComponentId(){
		return menuComponentId;
	}

	public void setMenuComponentDefaultSelect(String menuComponentDefaultSelect){
		this.menuComponentDefaultSelect = menuComponentDefaultSelect;
	}

	public String getMenuComponentDefaultSelect(){
		return menuComponentDefaultSelect;
	}

	public void setMenuComponentPiecesCount(String menuComponentPiecesCount){
		this.menuComponentPiecesCount = menuComponentPiecesCount;
	}

	public String getMenuComponentPiecesCount(){
		return menuComponentPiecesCount;
	}

	public void setMenuComponentMaxSelect(String menuComponentMaxSelect){
		this.menuComponentMaxSelect = menuComponentMaxSelect;
	}

	public String getMenuComponentMaxSelect(){
		return menuComponentMaxSelect;
	}

	public void setProductDetails(List<List<ProductDetailsItemItem>> productDetails){
		this.productDetails = productDetails;
	}

	public List<List<ProductDetailsItemItem>> getProductDetails(){
		return productDetails;
	}

	public void setMenuComponentModifierApply(String menuComponentModifierApply){
		this.menuComponentModifierApply = menuComponentModifierApply;
	}

	public String getMenuComponentModifierApply(){
		return menuComponentModifierApply;
	}

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(menuSetComponentName);
        dest.writeString(menuComponentName);
        dest.writeString(menuComponentSequence);
        dest.writeString(menuComponentApplyPrice);
        dest.writeString(menuSetComponentId);
        dest.writeString(menuComponentMinSelect);
        dest.writeString(menuComponentId);
        dest.writeString(menuComponentDefaultSelect);
        dest.writeString(menuComponentPiecesCount);
        dest.writeString(menuComponentMaxSelect);
        dest.writeString(menuComponentModifierApply);
    }
}