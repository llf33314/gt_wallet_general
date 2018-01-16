package com.gt.wallet.data.api.tonglian.request.invoice;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月15日 下午6:43:53 
* 类说明 
*/
@Data
@ApiModel(description="开票H5调用")
public class TH5Invoice {
	
	/**
	 * 商户编号
	 */
	@ApiModelProperty(name="UserId",notes="商户编号",required=true)
	private String UserId;
	
	
	/**
	 * 发票流水号
	 */
	@ApiModelProperty(name="UserId",notes="发票流水号",required=true)
	private String InvoiceSerialNumber;
	
	
	
	/**
	 * 合计金额
	 */
	@ApiModelProperty(name="TotalAmount",notes="合计金额",required=true)
	private double TotalAmount;

	
	
	/**
	 * 合计税额
	 */
	@ApiModelProperty(name="TotalTaxAmount",notes="合计税额",required=true)
	private double TotalTaxAmount;
	
	
	
	/**
	 * 价税合计
	 */
	@ApiModelProperty(name="TotalPriceTax",notes="价税合计",required=true)
	private double TotalPriceTax;
	
	
	/***
	 * 明细
	 */
	@ApiModelProperty(name="TInvoiceList",notes="明细",required=true)
	private List<TInvoiceList> TInvoiceList;


	public TH5Invoice(String userId, String invoiceSerialNumber, double totalAmount, double totalTaxAmount,
			double totalPriceTax, List<com.gt.wallet.data.api.tonglian.request.invoice.TInvoiceList> tInvoiceList) {
		super();
		UserId = userId;
		InvoiceSerialNumber = invoiceSerialNumber;
		TotalAmount = totalAmount;
		TotalTaxAmount = totalTaxAmount;
		TotalPriceTax = totalPriceTax;
		TInvoiceList = tInvoiceList;
	}
	
	
	public TH5Invoice() {
		super();
	}
	
}
