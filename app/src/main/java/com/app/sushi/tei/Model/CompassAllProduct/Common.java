package com.app.sushi.tei.Model.CompassAllProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

public class Common implements Parcelable {

	public Common() {

	}

	@SerializedName("product_image_source")
	private String productImageSource;

	@SerializedName("subcategory_image_url")
	private String subcategoryImageUrl;

	protected Common(Parcel in) {
		productImageSource = in.readString();
		subcategoryImageUrl = in.readString();
	}

	public static final Creator<Common> CREATOR = new Creator<Common>() {
		@Override
		public Common createFromParcel(Parcel in) {
			return new Common(in);
		}

		@Override
		public Common[] newArray(int size) {
			return new Common[size];
		}
	};

	public void setProductImageSource(String productImageSource){
		this.productImageSource = productImageSource;
	}

	public String getProductImageSource(){
		return productImageSource;
	}

	public void setSubcategoryImageUrl(String subcategoryImageUrl){
		this.subcategoryImageUrl = subcategoryImageUrl;
	}

	public String getSubcategoryImageUrl(){
		return subcategoryImageUrl;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeString(productImageSource);
		dest.writeString(subcategoryImageUrl);
	}
}