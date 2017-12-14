package com.gt.wallet.service.impl.log;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseServiceImpl;
import com.gt.wallet.data.wallet.request.SearchMsgPage;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.entity.WalletMessage;
import com.gt.wallet.entity.WalletQuota;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.mapper.log.WalletMessageMapper;
import com.gt.wallet.service.log.WalletMessageService;
import com.gt.wallet.utils.CommonUtil;
import com.gt.wallet.utils.MyPageUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * <p>
 * 消息中心 服务实现类
 * </p>
 *
 * @author lifengxi(gt_sky@qq.com)
 * @since 2017-12-14
 */
@Service
@Slf4j
public class WalletMessageServiceImpl extends BaseServiceImpl<WalletMessageMapper, WalletMessage> implements WalletMessageService {
	
	@Autowired
	private WalletMessageMapper walletMessageMapper;

	@Transactional(propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT,timeout=36000,rollbackFor=Exception.class)
	@Override
	public ServerResponse<?> add(Integer wMemberId, Integer msgType, String descContent, Integer orderId)
			throws Exception {
		log.info(CommonUtil.format("biz接口:add api,params:%s,%s,%s,%s", wMemberId,msgType,descContent,orderId));
		ServerResponse<Integer> serverResponse=null;
		WalletMessage walletMessage=new WalletMessage(wMemberId, msgType, descContent, orderId);
		walletMessageMapper.insert(walletMessage);
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return ServerResponse.createBySuccess();
	}

	@Override
	public ServerResponse<?> upstate(String listStr) throws Exception {
		log.info(CommonUtil.format("biz接口:add api,params:%s", JsonUtil.toJSONString(listStr)));
		ServerResponse<Integer> serverResponse=null;
		String[] array=	listStr.split(",");
		for (String string : array) {
			if(CommonUtil.isInteger(string)&&string.equals("0")){
				WalletMessage walletMessage=new WalletMessage();
				walletMessage.setId(CommonUtil.toInteger(CommonUtil.toInteger(string)));
				//已读
				walletMessage.setState(1);
				walletMessageMapper.updateById(walletMessage);
			}
		}
		serverResponse=ServerResponse.createBySuccess();
		log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		return ServerResponse.createBySuccess();
	}
	
	@Override
	public ServerResponse<MyPageUtil<WalletMessage>> getPage(SearchMsgPage searchMsgPage) {
		log.info(CommonUtil.format("biz接口:getPage api,params:%s", JsonUtil.toJSONString(searchMsgPage)));
		EntityWrapper<WalletMessage> wrapper=new EntityWrapper<WalletMessage>() ;
		if(CommonUtil.isNotEmpty(searchMsgPage.getMsgType())){
			wrapper.where("msg_type={0}", searchMsgPage.getMsgType());			
		}
		wrapper.where("w_member_id={0}", searchMsgPage.getWmemberId());		
		Integer total=walletMessageMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)||total==0){
			throw new BusinessException(WalletResponseEnums.DATA_NULL_ERROR);
		}
		
		wrapper.orderBy("id", false);
		Page<WalletMessage> page1=new Page<WalletMessage>();
		page1.setCurrent(searchMsgPage.getCurrent());
		page1.setRecords(walletMessageMapper.selectPage(page1, wrapper));
		MyPageUtil<WalletMessage> myPageUtil=new MyPageUtil<WalletMessage>(searchMsgPage.getCurrent(), searchMsgPage.getSize());
		myPageUtil.setRecords(walletMessageMapper.selectPage(myPageUtil,wrapper),total);
		if(myPageUtil.getRecords().size()>0){
			for (int i = 0; i < myPageUtil.getRecords().size(); i++) {
				myPageUtil.getRecords().get(i).setMsgTypeDesc(CommonUtil.getMsgTypeDesc(myPageUtil.getRecords().get(i).getMsgType()));
				myPageUtil.getRecords().get(i).setStateDesc(CommonUtil.getMsgStatusDesc(myPageUtil.getRecords().get(i).getState()));
			}
		}
		log.info(CommonUtil.format("myPageUtil:%s", JsonUtil.toJSONString(myPageUtil)));
		return ServerResponse.createBySuccessCodeData(myPageUtil);
	}

	@Override
	public ServerResponse<Integer> getReadState(Integer wMemberId) throws Exception {
		log.info(CommonUtil.format("biz接口:getReadState api,params:%s", JsonUtil.toJSONString(wMemberId)));
		EntityWrapper<WalletMessage> wrapper=new EntityWrapper<WalletMessage>() ;
		wrapper.where("w_member_id={0}", wMemberId);		
		Integer total=walletMessageMapper.selectCount(wrapper);
		if(CommonUtil.isEmpty(total)){
			total=0;
		}
		log.info(CommonUtil.format("total:%s", JsonUtil.toJSONString(total)));
		return ServerResponse.createBySuccessCodeData(total);
	}
	
}
