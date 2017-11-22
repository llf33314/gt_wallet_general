package com.gt.wallet.service.impl.member;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.wallet.request.WalletIndividualAdd;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletBank;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.member.WalletBankMapper;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletBankServiceImpl extends BaseServiceImpl<WalletBankMapper, WalletBank> implements WalletBankService {

	@Autowired
	private WalletBankMapper walletBankMapper;
	
	@Autowired
	private WalletMemberMapper walletMemberMapper;
	
	@Override
	public ServerResponse<List<WalletBank>> getWalletBanksByMemberId(Integer wmemberId) {
		if(CommonUtil.isEmpty(wmemberId)){
			log.error(CommonUtil.format("biz接口:获取会员银行卡列表异常:%s",WalletResponseEnums.CODE_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.CODE_ERROR);
		}
		EntityWrapper<WalletBank> wrapper=new EntityWrapper<WalletBank>();
		wrapper.where("w_member_id={0}", wmemberId);
		List<WalletBank> list=	walletBankMapper.selectList(wrapper);
		if(CommonUtil.isNotEmpty(list)&&list.size()>0){
			return ServerResponse.createBySuccessCodeData(list);
		}else{
			log.error(CommonUtil.format("biz接口:获取会员银行卡列表异常:%s",WalletResponseEnums.DATA_NULL_ERROR.getDesc()));
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
	}


	@Override
	public ServerResponse<Integer> add(WalletIndividualAdd walletIndividualAdd)throws Exception {
		log.info(CommonUtil.format("biz接口:绑定银行卡,请求参数:%s", JsonUtil.toJSONString(walletIndividualAdd)));
		/****************************************银行卡操作********************************************/
		ServerResponse<com.alibaba.fastjson.JSONObject>  serverResponseBin=YunSoaMemberUtil.getBankCardBin(walletIndividualAdd.getCardNo());
		JSONObject jsonObject=serverResponseBin.getData();
		if(serverResponseBin.getCode()!=0){//
			throw new BusinessException(serverResponseBin.getCode(),serverResponseBin.getMsg());
		}
		WalletMember walletMember=walletMemberMapper.selectById(walletIndividualAdd.getMemberId());
		String bankCode ="";
		Long cardType =0L;
		Integer cardLenth =0;
		Integer cardState =0;
		bankCode =jsonObject.getString("bankCode");
		cardType =jsonObject.getLong("cardType");
		cardLenth =jsonObject.getInteger("cardLenth");
		cardState =jsonObject.getInteger("cardState");
		
		if(cardType!=1){//
			log.error("biz接口:银行卡类型异常,请填写借记卡");
			throw new BusinessException("银行卡类型异常,请填写借记卡");
		}
		if(cardState!=1){//
			log.error("biz接口:银行卡类型异常,银行卡已失效");
			throw new BusinessException("银行卡类型异常,银行卡已失效");
		}
		ServerResponse<com.alibaba.fastjson.JSONObject> serverResponseBind=YunSoaMemberUtil.applyBindBankCard(walletIndividualAdd, walletMember.getMemberNum(), true, bankCode, cardType);;
		if(serverResponseBind.getCode()!=0){
			log.error("biz接口:"+serverResponseBind.getMsg());
			throw new BusinessException(serverResponseBind.getCode(),serverResponseBind.getMsg());
		}
		String tranceNum=serverResponseBind.getData().getString("tranceNum");//流水号
		String transDate=serverResponseBind.getData().getString("transDate");//申请时间
		String bankName=serverResponseBind.getData().getString("bankName");//银行名称
		/********************************新增银行卡*********************************/
		WalletBank walletBank=new WalletBank();;
	
		walletBank.setName(walletIndividualAdd.getBankName());
		walletBank.setWMemberId(walletIndividualAdd.getMemberId());
		walletBank.setMemberNum(walletMember.getMemberNum());
		String carNoMi=YunSoaMemberUtil.rsaEncrypt(walletIndividualAdd.getCardNo());
		walletBank.setBankCode(bankCode);
		walletBank.setCardNo(carNoMi);
		walletBank.setCardCheck(2);
		walletBank.setPhone(YunSoaMemberUtil.rsaEncrypt(walletIndividualAdd.getPhone()));
		walletBank.setCardClass(1);
		walletBank.setCardType(1);
		walletBank.setIdentityNo(YunSoaMemberUtil.rsaEncrypt(walletIndividualAdd.getIdentityNo()));
		walletBank.setIsSafeCard(0);
		//支付行号
//		walletBank.setUnionBank(unionBank);
		walletBank.setTranceNum(tranceNum);
		walletBank.setTransDate(transDate);
		walletBank.setBankName(bankName);
		walletBank.setCardLenth(cardLenth);
		walletBank.setCardState(cardState);
		if(CommonUtil.isEmpty(walletBank.getId())||walletBank.getId()==0){
			walletBankMapper.insert(walletBank);
		}else{
			walletBankMapper.updateById(walletBank);
		}
		/********************************新增银行卡*********************************/
		/****************************************银行卡操作********************************************/
		ServerResponse<Integer> response=	ServerResponse.createBySuccessCodeData(walletBank.getId());
		log.info("biz接口:获取会员银行卡:"+JsonUtil.toJSONString(response));
		
		return response;
	}
	
	
	@Override
	public ServerResponse<?> bindBankCard(Integer busId,Integer id,String verificationCode) throws Exception {
		log.info(CommonUtil.format("biz接口:获取会员银行卡:%s",id));
		WalletBank walletBank=walletBankMapper.selectById(id);
		if(CommonUtil.isEmpty(walletBank)){
			throw new BusinessException("请先填写银行卡信息");
		}
		WalletMember walletMember=walletMemberMapper.selectById(walletBank.getWMemberId());
		if(walletMember.getMemberClass()==1&&walletMember.getMemberId()!=busId){
			throw new BusinessException("操作异常，此钱包会员不属于当前登录商家");
		}
		YunSoaMemberUtil.bindBankCard(walletMember.getMemberNum(), walletBank.getTranceNum(), walletBank.getTransDate(), walletBank.getPhone(), verificationCode);
		ServerResponse<?> response=	ServerResponse.createBySuccess();
		log.info("biz接口:确认绑定银行卡:"+JsonUtil.toJSONString(response));
		return null;
	}
}
