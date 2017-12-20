package com.gt.wallet.service.impl.log;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.wallet.request.ReviewResult;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletQuota;
import com.gt.wallet.enums.WalletMsgEnums;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.log.WalletQuotaMapper;
import com.gt.wallet.mapper.member.WalletMemberMapper;
import com.gt.wallet.service.log.WalletMessageService;
import com.gt.wallet.service.log.WalletQuotaService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.MyPageUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 额度申请 服务实现类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-11-20
 */
@Service
@Slf4j
public class WalletQuotaServiceImpl extends BaseServiceImpl<WalletQuotaMapper, WalletQuota> implements WalletQuotaService {
	
	@Autowired
	private WalletQuotaMapper walletQuotaMapper;
	
	
	@Autowired
	private WalletMemberMapper walletMemberMapper;
	
	@Autowired
	private WalletMessageService walletMessageService;

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<Integer> add(WalletQuota walletQuota) throws Exception {
		log.info(CommonUtil.format("biz接口:新增申请额度记录,请求参数:%s", JsonUtil.toJSONString(walletQuota)));
		ServerResponse<Integer> serverResponse=null;
		if(CommonUtil.isEmpty(walletQuota)){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		if(CommonUtil.isEmpty(walletQuota.getId())){//新增
			walletQuotaMapper.insert(walletQuota);
		}else{//修改
			walletQuotaMapper.updateById(walletQuota);
		}
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return ServerResponse.createBySuccess();
	}

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> review(ReviewResult reviewResult) {
		log.info(CommonUtil.format("start biz review api params:%s", JsonUtil.toJSONString(reviewResult)));
		if(CommonUtil.isEmpty(reviewResult)){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		WalletQuota walletQuota=walletQuotaMapper.selectById(reviewResult.getId());
		if(CommonUtil.isEmpty(walletQuota)){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		if(walletQuota.getStatus()==1){
			throw new BusinessException("已审核通过");
		}
		walletQuota.setStatus(reviewResult.getStatus());
		walletQuota.setQuotaDesc(reviewResult.getQuotaDesc());
		int count=walletQuotaMapper.updateById(walletQuota);
		WalletMember walletMember=walletMemberMapper.selectById(walletQuota.getWMemberId());
		walletMember.setWithdrawQuota(walletQuota.getQuotaValue().doubleValue());
		walletMemberMapper.updateById(walletMember);
		log.info(CommonUtil.format("biz review api count:%s",count));
		try {
			walletMessageService.add(walletMember.getId(), WalletMsgEnums.MSGTYPE_QUOTAREVIEW.getCode(), reviewResult.getQuotaDesc(), walletQuota.getId());
		} catch (Exception e) {
			log.error("biz review api write msg api fail");
			e.printStackTrace();
		}
		if(count==1){
			return ServerResponse.createBySuccess();
		}else{
			log.error("biz review api system log");
			throw new BusinessException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}

	@Override
	public ServerResponse<MyPageUtil<WalletQuota>> getPage(Page<WalletQuota> page,Integer status) {
		log.info(CommonUtil.format("start biz getPage api params:%s", JsonUtil.toJSONString(page)));
		EntityWrapper<WalletQuota> wrapper=new EntityWrapper<WalletQuota>() ;
		if(CommonUtil.isNotEmpty(status)){
			wrapper.where("status={0}", status);			
		}
		Integer total=walletQuotaMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)||total==0){
			log.error(CommonUtil.format("biz getPage api fail"));
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
		wrapper.orderBy("id", false);
		Page<WalletQuota> page1=new Page<WalletQuota>();
		page1.setCurrent(page.getCurrent());
		page1.setRecords(walletQuotaMapper.selectPage(page1, wrapper));
		MyPageUtil<WalletQuota> myPageUtil=new MyPageUtil<WalletQuota>(page.getCurrent(), page.getSize());
		myPageUtil.setRecords(walletQuotaMapper.selectPage(myPageUtil,wrapper),total);
		if(myPageUtil.getRecords().size()>0){
			for (int i = 0; i < myPageUtil.getRecords().size(); i++) {
				WalletMember walletMember=	walletMemberMapper.selectById(myPageUtil.getRecords().get(i).getWMemberId());
				myPageUtil.getRecords().get(i).setMemberNum(walletMember.getMemberNum());
			}
		}
		log.info(CommonUtil.format("biz getPage api page:%s", JsonUtil.toJSONString(myPageUtil)));
		return ServerResponse.createBySuccessCodeData(myPageUtil);
	}
	
}
