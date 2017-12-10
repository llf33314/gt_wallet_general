package com.gt.wallet.service.impl.order;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.api.tonglian.request.TWithdrawOrder;
import com.gt.wallet.data.wallet.request.SearchPayOrderPage;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletBank;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletMoney;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.order.WalletMoneyMapper;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.service.order.WalletMoneyService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.DateTimeKit;
import com.gt.wallet.utils.MyPageUtil;
import com.gt.wallet.utils.yun.YunSoaMemberUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 提现记录表 服务实现类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@Service
@Slf4j
public class WalletMoneyServiceImpl extends BaseServiceImpl<WalletMoneyMapper, WalletMoney> implements WalletMoneyService {
	
	@Autowired
	private WalletMoneyMapper walletMoneyMapper;
	
	@Autowired
	private WalletMemberService walletMemberService;
	
	@Autowired
	private WalletBankService walletBankService;

	@Override
	public ServerResponse<MyPageUtil<WalletMoney>> getPage(Page<?> page, SearchPayOrderPage searchPayOrderPage) {
		log.info(CommonUtil.format("biz接口:分页查询,请求参数:%s", JsonUtil.toJSONString(page)));
		EntityWrapper<WalletMoney> wrapper=new EntityWrapper<WalletMoney>() ;
		wrapper.where("status={0}",searchPayOrderPage.getStatus());		
		wrapper.where("w_member_id={0}",searchPayOrderPage.getWmemberId());
		if(CommonUtil.isNotEmpty(searchPayOrderPage.getStartTime())&&CommonUtil.isEmpty(searchPayOrderPage.getEndTime())){
			wrapper.between("ctime", searchPayOrderPage.getStartTime(), DateTimeKit.getNowDate());
		}else if(CommonUtil.isNotEmpty(searchPayOrderPage.getEndTime())&&CommonUtil.isEmpty(searchPayOrderPage.getStartTime())){
			wrapper.where("ctime<{0}", searchPayOrderPage.getEndTime());
		}else if(CommonUtil.isNotEmpty(searchPayOrderPage.getEndTime())&&CommonUtil.isNotEmpty(searchPayOrderPage.getStartTime())){
			wrapper.between("ctime", searchPayOrderPage.getStartTime(), searchPayOrderPage.getEndTime());
		}
		Integer total=walletMoneyMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)||total==0){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
		wrapper.orderBy("id", false);
		Page<WalletMoney> page1=new Page<WalletMoney>();
		page1.setCurrent(page.getCurrent());
		page1.setRecords(walletMoneyMapper.selectPage(page1, wrapper));
		MyPageUtil<WalletMoney> myPageUtil=new MyPageUtil<WalletMoney>(page.getCurrent(), page.getSize());
		myPageUtil.setRecords(walletMoneyMapper.selectPage(myPageUtil,wrapper),total);
		log.info(CommonUtil.format("page:%s", JsonUtil.toJSONString(page)));
		return ServerResponse.createBySuccessCodeData(myPageUtil);
	}

	@Override
	public ServerResponse<IndexStatistics> getTotal(Integer wmemberId) {
		log.info(CommonUtil.format("biz接口:获取首页统计数据(实时),请求参数:%s", JsonUtil.toJSONString(wmemberId)));
		IndexStatistics indexStatistics=walletMoneyMapper.getStatisticsByWmemberId(wmemberId);
		log.info(CommonUtil.format("biz接口:indexStatistics,结果:%s", JsonUtil.toJSONString(indexStatistics)));
		return ServerResponse.createBySuccessCodeData(indexStatistics);
	}

	@Override
	public ServerResponse<?> withdrawApply(Integer busId, double money, Integer bankId) throws Exception{
		log.info(CommonUtil.format("biz接口:withdrawApply,请求参数:%s,%s,%s",busId,money,bankId));
		if(money<=0){
			throw new BusinessException("请输入大于0的金额");
		}
		ServerResponse<List<WalletMember>> responseMember=walletMemberService.findMember(busId);
		if(ServerResponse.judgeSuccess(responseMember)==false||responseMember.getData().size()<=0){
			throw new BusinessException("请先注册多粉钱包会员");
		}
		WalletMember walletMember=responseMember.getData().get(0);
		if(walletMember.getIsBindingPhone()==0){
			throw new BusinessException("请先绑定手机号码");
		}else if(walletMember.getStatus()!=3){
			String desc="";
			switch (walletMember.getStatus()) {
			//会员状态(-2:删除,-1:锁定用户,0:创建,1:审核中,3:正常使用)
			case -2:
				desc="删除";
				break;
			case -1:
				desc="锁定用户";
				break;
			case -0:
				desc="创建中";
				break;
			default:
				break;
			}
			throw new BusinessException("账号状态("+desc+")异常,禁止提现");
		}
		if(walletMember.getMemberType()==3&&(CommonUtil.isEmpty(walletMember.getWalletIndividual())||walletMember.getWalletIndividual().getIdentityChecked()==0)){//个人未实名认证
			throw new BusinessException("请先实名认证");
		}else if(walletMember.getMemberType()==3&&(CommonUtil.isEmpty(walletMember.getWalletCompany()))){//企业
			throw new BusinessException("请先完成企业认证");
		}
		WalletBank walletBank=	walletBankService.selectById(bankId);
		if(CommonUtil.isEmpty(walletBank)){
			throw new BusinessException("银行卡不存在");
		}else if(walletBank.getCardState()==0){
			throw new BusinessException("银行卡无效");
		}else if(walletBank.getStatus()!=1){
			throw new BusinessException("此银行卡还没绑定");
		}
		String bizOrderNo="TX"+System.currentTimeMillis();
		Integer bankCardPro=0;
		if(walletBank.getCardClass()==2){
			bankCardPro=1;
		}
		TWithdrawOrder consumeOrder=new TWithdrawOrder(bizOrderNo, walletMember.getMemberNum(), money, 2.0, walletBank.getCardNo(), bankCardPro, "T0", "商家提现");
		ServerResponse<?>  serverResponse=YunSoaMemberUtil.applyWithdraw(consumeOrder);
		log.info(CommonUtil.format("biz接口:withdrawApply,请求参数:%s,%s,%s",busId,money,bankId));
		return null;
	}
	
}
