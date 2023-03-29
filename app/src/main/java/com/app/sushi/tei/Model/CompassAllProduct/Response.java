package com.app.sushi.tei.Model.CompassAllProduct;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Response implements Parcelable {

	public Response(){

	}

	@SerializedName("common")
	private Common common;

	@SerializedName("status")
	private String status;

	@SerializedName("result_set")
	private List<ResultSetItem> resultSet;

	protected Response(Parcel in) {
		common = in.readParcelable(Common.class.getClassLoader());
		status = in.readString();
	}

	public static final Creator<Response> CREATOR = new Creator<Response>() {
		@Override
		public Response createFromParcel(Parcel in) {
			return new Response(in);
		}

		@Override
		public Response[] newArray(int size) {
			return new Response[size];
		}
	};

	public void setCommon(Common common){
		this.common = common;
	}

	public Common getCommon(){
		return common;
	}

	public void setStatus(String status){
		this.status = status;
	}

	public String getStatus(){
		return status;
	}

	public void setResultSet(List<ResultSetItem> resultSet){
		this.resultSet = resultSet;
	}

	public List<ResultSetItem> getResultSet(){
		return resultSet;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeParcelable(common, flags);
		dest.writeString(status);
	}
}