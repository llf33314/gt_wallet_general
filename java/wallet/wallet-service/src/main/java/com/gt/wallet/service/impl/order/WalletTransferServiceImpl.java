package com.gt.wallet.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.mapper.Wrapper;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.constant.WalletLogConstants;
import com.gt.wallet.data.api.tonglian.request.ApplicationTransfer;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletPayOrder;
import com.gt.wallet.entity.WalletTransfer;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.order.WalletTransferMapper;
import com.gt.wallet.service.log.WalletApiLogService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.service.order.WalletPayOrderService;
import com.gt.wallet.service.order.WalletTransferService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.DateTimeKit;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 转账记录 服务实现类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2018-01-30
 */
@Service
@Slf4j
public class WalletTransferServiceImpl extends BaseServiceImpl<WalletTransferMapper, WalletTransfer> implements WalletTransferService {

	@Autowired
	private WalletTransferMapper walletTransferMapper;
	
	@Autowired
	private WalletMemberService walletMemberService;
	
	@Autowired
	private WalletPayOrderService walletPayOrderService;
	
	@Autowired
	private WalletApiLogService walletApiLogService;
	
	@Override
	public ServerResponse<?> addDebit(Integer busId) throws Exception {
		ServerResponse<List<WalletMember>> serverResponse=	walletMemberService.findMember(busId);
		if(serverResponse.getCode()!=0||serverResponse.getData().size()<=0){
			log.error("biz addDebit api：user is not exist");
			throw new BusinessException("fail：user is not exist!");
		}
		WalletMember walletMember=serverResponse.getData().get(0);
		if(walletMember.getStatus()!=3){
			log.error("biz addDebit api fail:会员账号异常("+CommonUtil.getMemberStatusDesc(walletMember.getStatus())+")，退款失败!");
			throw new BusinessException("会员账号异常("+CommonUtil.getMemberStatusDesc(walletMember.getStatus())+")，退款失败!");
		}
		
		Wrapper<WalletPayOrder> wrapper=new EntityWrapper<WalletPayOrder>() ;
		wrapper.where("w_member_id={0}",walletMember.getId()).and("status={0}", "success")
		.and("ctime<{0}",DateTimeKit.getDateTime(DateTimeKit.DEFAULT_DATE_FORMAT));
		List<WalletPayOrder> walletPayOrders=walletPayOrderService.selectList(wrapper);
		if(walletPayOrders.size()>0){
			Double waitBalance=0.00;
			for (WalletPayOrder walletPayOrder : walletPayOrders) {
				if(CommonUtil.isEmpty(walletPayOrder.getSysRefundNo())){//没有退款过
					waitBalance=waitBalance+(walletPayOrder.getAmount().doubleValue()-walletPayOrder.getFee().doubleValue());
				}else {
					if(walletPayOrder.getAmount()==walletPayOrder.getRefundAmount()){//全额退款
						waitBalance=waitBalance+(walletPayOrder.getAmount().doubleValue()-walletPayOrder.getRefundAmount().doubleValue());
					}else{//多次退款
						waitBalance=waitBalance+(walletPayOrder.getAmount().doubleValue()-walletPayOrder.getFee().doubleValue()-walletPayOrder.getRefundAmount().doubleValue());
					}
				}
			}
			waitBalance=CommonUtil.getdoubleTwo(waitBalance);
			String sysOrderNo="HZ"+System.currentTimeMillis();
			String desc="平台划账";
			ApplicationTransfer applicationTransfer=new ApplicationTransfer(waitBalance,sysOrderNo,desc, walletMember.getExternalNum());
			
			ServerResponse<JSONObject> response=YunSoaMemberUtil.applicationTransfer(applicationTransfer);
			/************记录日志************/
			try {
				walletApiLogService.save(JsonUtil.toJSONString(applicationTransfer), response, walletMember.getId(), null,sysOrderNo,WalletLogConstants.LOG_TRANSFER);
			} catch (Exception e) {
				log.error("biz addDebit save log fail!!!");
				e.printStackTrace();
				
			}
			/************记录日志************/
			if(ServerResponse.judgeSuccess(response)){
				WalletTransfer walletTransfer=new WalletTransfer();
				walletTransfer.setAmount(CommonUtil.toBigDecimal(waitBalance));
				walletTransfer.setBusId(busId);
				walletTransfer.setStatus(0);
				walletTransfer.setSysOrderNo(sysOrderNo);
				walletTransfer.setWDesc(desc);
				walletTransfer.setWMemberId(walletMember.getId());
				walletTransfer.setTransferNo(response.getData().getString("transferNo"));
				walletTransferMapper.insert(walletTransfer);
				return ServerResponse.createBySuccess();
			}
			return response;
		}else{
			
			return ServerResponse.createByErrorCode(WalletResponseEnums.TRANSFER_ERROR);
		}
		
	}
	
	
	
}
