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
import com.gt.wallet.entity.WalletQuota;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.log.WalletQuotaMapper;
import com.gt.wallet.service.log.WalletQuotaService;
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

	@Override
	public ServerResponse<?> review(ReviewResult reviewResult) {
		log.info(CommonUtil.format("biz接口:审核结果回调,请求参数:%s", JsonUtil.toJSONString(reviewResult)));
		if(CommonUtil.isEmpty(reviewResult)){
			throw new BusinessException(WalletResponseEnums.NULL_ERROR);
		}
		WalletQuota walletQuota=walletQuotaMapper.selectById(reviewResult.getId());
		if(CommonUtil.isEmpty(walletQuota)){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		walletQuota.setStatus(reviewResult.getStatus());
		walletQuota.setQuotaDesc(reviewResult.getQuotaDesc());
		int count=walletQuotaMapper.updateById(walletQuota);
		log.info(CommonUtil.format("影响行数:%s",count));
		if(count==1){
			return ServerResponse.createBySuccess();
		}else{
			throw new BusinessException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}

	@Override
	public ServerResponse<MyPageUtil<WalletQuota>> getPage(Page<WalletQuota> page,Integer status) {
		log.info(CommonUtil.format("biz接口:分页查询,请求参数:%s", JsonUtil.toJSONString(page)));
		EntityWrapper<WalletQuota> wrapper=new EntityWrapper<WalletQuota>() ;
		if(CommonUtil.isNotEmpty(status)){
			wrapper.where("status={0}", status);			
		}
		Integer total=walletQuotaMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)||total==0){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
		wrapper.orderBy("id", false);
		Page<WalletQuota> page1=new Page<WalletQuota>();
		page1.setCurrent(page.getCurrent());
		page1.setRecords(walletQuotaMapper.selectPage(page1, wrapper));
		MyPageUtil<WalletQuota> myPageUtil=new MyPageUtil<WalletQuota>(page.getCurrent(), page.getSize());
		myPageUtil.setRecords(walletQuotaMapper.selectPage(myPageUtil,wrapper),total);
//		MyPageUtil.getInit( page.getRecords().size(), page);
		log.info(CommonUtil.format("page:%s", JsonUtil.toJSONString(page)));
		return ServerResponse.createBySuccessCodeData(myPageUtil);
	}
	
}
