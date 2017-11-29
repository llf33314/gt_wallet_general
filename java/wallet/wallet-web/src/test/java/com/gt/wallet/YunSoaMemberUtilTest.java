package com.gt.wallet;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.gt.api.bean.session.BusUser;
import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.wallet.request.WalletCompanyAdd;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.service.member.WalletBankService;
import com.gt.wallet.service.member.WalletCompanyService;
import com.gt.wallet.service.member.WalletMemberService;
import com.gt.wallet.utils.CommonUtil;

import lombok.extern.slf4j.Slf4j;

/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月27日 下午2:29:21 
* 类说明 
*/
@Slf4j
public class YunSoaMemberUtilTest extends BasicTest {
	
	@Autowired
	private WalletMemberService walletMemberService;
	
	@Autowired
	private WalletCompanyService walletCompanyService; 
	
	
	@Autowired
	private WalletBankService walletBankService;
	
	@Test
	public void createMember(){
		log.info(" start test createMember api");
		try {
			ServerResponse<Integer> serverResponse=	walletMemberService.register(2, "127.0.0.1", 35);
			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("test createMember api error");
		}
		log.info(" end test createMember api");
	}
	
	
	/**
	 * 设置企业会员信息 ok
	 */
	@Test
	public void setCompanyInfo(){
		WalletCompanyAdd walletCompanyAdd=new WalletCompanyAdd(10, "广东谷通科技有限公司", "惠城区惠州大道20号赛格大厦10层07号", "91440300335398348T", "13528307867", "盖茨", "1", "13528307867", "6228481139158261672", "中国农业银行", "", "");
		walletCompanyAdd.setDoBusinessUrl("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
		walletCompanyAdd.setIdentitycardUrl1("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
		walletCompanyAdd.setIdentitycardUrl2("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
		walletCompanyAdd.setLicenseUrl("http://maint.deeptel.com.cn/upload/image/2/wallet/null/1511838821858.png");
		walletCompanyAdd.setProvince("440000");
		walletCompanyAdd.setArea("441300");
		log.info(" start test setCompanyInfo api");
		BusUser busUser=new BusUser();
		busUser.setId(35);
		busUser.setName("gt123");
		try {
			ServerResponse<?> serverResponse=	walletCompanyService.save(walletCompanyAdd,busUser);
			if(serverResponse.getCode()==0){
				serverResponse=walletBankService.addPublic(walletCompanyAdd);
			}
			log.info(CommonUtil.format("serverResponse:%s", JsonUtil.toJSONString(serverResponse)));
		} catch (Exception e) {
			e.printStackTrace();
			log.error("test setCompanyInfo api error");
		}
		log.info(" end test setCompanyInfo api");
	}

}
