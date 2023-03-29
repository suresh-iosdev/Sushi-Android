package com.app.sushi.tei.Model.Voucher;

import java.io.Serializable;
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class ResultSet implements Serializable {

	@SerializedName("voucher_list")
	private List<VoucherListItem> voucherList;

	@SerializedName("used_vouchers")
	private List<VoucherUsedList> usedVouchers;

	public void setVoucherList(List<VoucherListItem> voucherList){
		this.voucherList = voucherList;
	}

	public List<VoucherListItem> getVoucherList(){
		return voucherList;
	}

	public List<VoucherUsedList> getUsedVouchers() {
		return usedVouchers;
	}

	public void setUsedVouchers(List<VoucherUsedList> usedVouchers) {
		this.usedVouchers = usedVouchers;
	}
}