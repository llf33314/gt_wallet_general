package com.gt.wallet.web.wallet;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.base.BaseController;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletApiLog;
import com.gt.wallet.enums.WalletResponseEnums;
import com.gt.wallet.exception.BusinessException;
import com.gt.wallet.exception.ResponseEntityException;
import com.gt.wallet.service.log.WalletApiLogService;

import io.swagger.annotations.Api;

/**
 * <p>
 * 调用第三方api日志表 前端控制器
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
@RequestMapping("/walletApiLog")
@RestController  
@Api(value = "walletApiLog")  
public class WalletApiLogController extends BaseController {
	
	@Autowired
	private WalletApiLogService walletApiLogMapper;
	
	
	@RequestMapping("/hello")
	public ModelAndView hello(Integer id){
		ModelAndView modelAndView = new ModelAndView();
//		try {
//			walletApiLog=walletApiLogMapper.selectById(id);
//			return JsonUtil.toJSONString(walletApiLog);
//		} catch (Exception e) {
//			walletApiLog=ServerResponse.createByErrorCode(WalletResponseEnums.SYSTEM_ERROR);
//			return JsonUtil.toJSONString(walletApiLog);
//		}
		modelAndView.setViewName("prompt/system_err");
		return modelAndView;
	}
	
	@RequestMapping("save")
	@ResponseBody
	public String save() throws Exception{
		try {
		ServerResponse<?> serverResponse=null;
		WalletApiLog walletApiLog=new WalletApiLog();
		walletApiLog.setMsg("测试");
		walletApiLog.setParamsJson("ssd");
		walletApiLog.setResult("23423423");
		walletApiLog.setStatus(5);
		walletApiLog.setType(23);
		walletApiLog.setWMemberId(12132);
		serverResponse=walletApiLogMapper.save(walletApiLog);
		return JsonUtil.toJSONString(serverResponse);
		} catch ( BusinessException e) {
			throw new ResponseEntityException(e.getCode(),e.getMessage());
		} catch ( Exception e) {
			throw new ResponseEntityException(WalletResponseEnums.SYSTEM_ERROR);
		}
	}
	
}
