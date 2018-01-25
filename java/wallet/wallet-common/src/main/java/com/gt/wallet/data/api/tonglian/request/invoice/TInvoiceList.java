package com.gt.wallet.data.api.tonglian.request.invoice;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2018年1月15日 下午6:48:57 
* 类说明 
*/
@Data
@ApiModel(description="税票明细")
public class TInvoiceList {
	
	/**
	 * 发票明细行号
	 */
	@ApiModelProperty(name="LineNumber",notes="发票明细行号",required=true)
	private Integer LineNumber;
	
	
	
	/**
	 * 商品编码
	 */
	@ApiModelProperty(name="CommodityCode",notes="商品编码",required=true)
	private String CommodityCode;
	
	
	
	/**
	 * 商品名称
	 */
	@ApiModelProperty(name="CommodityName",notes="商品名称",required=true)
	private String CommodityName;
	
	
	
	
	/**
	 *规格型号
	 */
	@ApiModelProperty(name="SpecificationModel",notes="规格型号",required=true)
	private String SpecificationModel;
	
	
	
	
	/**
	 *计量单位
	 */
	@ApiModelProperty(name="MeasurementUnit",notes="计量单位",required=true)
	private String MeasurementUnit;
	
	
	
	/**
	 *商品数量
	 */
	@ApiModelProperty(name="CommodityQuantity",notes="商品数量",required=true)
	private double CommodityQuantity;
	
	
	
	/**
	 *单价
	 */
	@ApiModelProperty(name="UnitPrice",notes="单价",required=true)
	private double UnitPrice;
	
	
	
	/**
	 *金额
	 */
	@ApiModelProperty(name="Amount",notes="金额",required=true)
	private double Amount;
	
	
	
	/**
	 *税额
	 */
	@ApiModelProperty(name="Tax",notes="税额",required=true)
	private double Tax;
	
	
	
	
	/**
	 *税率
	 */
	@ApiModelProperty(name="Taxrate",notes="税率",required=true)
	private double Taxrate;
	
	
	
	/**
	 *含税标志
	 */
	@ApiModelProperty(name="TaxFlag",notes="含税标志",required=true)
	private String TaxFlag;



	public TInvoiceList(Integer lineNumber, String commodityCode, String commodityName, String specificationModel,
			String measurementUnit, double commodityQuantity, double unitPrice, double amount, double tax,
			double taxrate, String taxFlag) {
		super();
		LineNumber = lineNumber;
		CommodityCode = commodityCode;
		CommodityName = commodityName;
		SpecificationModel = specificationModel;
		MeasurementUnit = measurementUnit;
		CommodityQuantity = commodityQuantity;
		UnitPrice = unitPrice;
		Amount = amount;
		Tax = tax;
		Taxrate = taxrate;
		TaxFlag = taxFlag;
	}
	
	public TInvoiceList() {
		super();
	}

}
