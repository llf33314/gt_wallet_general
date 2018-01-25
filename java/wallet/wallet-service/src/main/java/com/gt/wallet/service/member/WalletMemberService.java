package com.gt.wallet.service.member;

import java.util.List;

import com.baomidou.mybatisplus.plugins.Page;
import com.gt.api.bean.session.BusUser;
import com.gt.wallet.base.BaseService;
import com.gt.wallet.data.wallet.request.SetcashbackPercent;
import com.gt.wallet.data.wallet.request.WalletSet;
import com.gt.wallet.dto.ServerResponse;
import com.gt.wallet.entity.WalletMember;
import com.gt.wallet.utils.MyPageUtil;

/**
 * <p>
 * 多粉钱包会员 服务类
 * </p>
 *
 * @author lifengxi
 * @since 2017-10-23
 */
public interface WalletMemberService extends BaseService<WalletMember> {
	
	/**
	 * 保存会员信息
	 * @param walletIndividual 会员信息
	 * @return
	 */
	public ServerResponse<Integer> save(WalletMember walletMember) throws Exception;
	
	/**
	 * 根据商家id查询
	 * @param budId 商家id
	 * @return
	 */
	public ServerResponse<List<WalletMember>> findMember(Integer budId) throws Exception;
	
	
	/**
	 * 判断商家是否开通多粉钱包
	 * @param budId 商家id
	 * @return 0 :已开通, 1:未开通
	 */
	public ServerResponse<Integer> isPassWallet(Integer busId) throws Exception;
	
	
	/**
	 * 判断商家是否开通多粉钱包
	 * @param budId 商家id
	 * @return 0 :已开通, 1:未开通
	 */
	public ServerResponse<?> isOpen(Integer busId) throws Exception;
	
	
	/**
	 * 开通会员
	 * @param memberType 会员类型(3:个人会员 2:企业会员)
	 * @param ip 客户端ip
	 * @return data:会员id
	 * @throws Exception
	 */
	public ServerResponse<Integer> register(Integer memberType,String ip,Integer busId) throws Exception;
	
	
	/**
	 * 钱包设置
	 * @param walletSet 需要设置的信息
	 * @param busUser 商家信息
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> bindingPhone(WalletSet walletSet,BusUser busUser) throws Exception;
	
	
	/**
	 * 发送短信验证码
	 * @param budId 商家id
	 * @param phone 手机号码
	 * @param wmemberId 会员id
	 * @param verificationCodeType 发送类型
	 * @return
	 * @throws Exception
	 */
	public ServerResponse<?> sendVerificationCode(Integer budId,String phone,Integer wmemberId,Integer verificationCodeType) throws Exception;
	
	
	
	/**
	 * 锁定用户
	 * @param wmemberId
	 * @return
	 */
	public ServerResponse<?> lockMember(Integer wmemberId)throws Exception;
	
	
	
	/**
	 * 解锁用户 ok
	 * @param wmemberId
	 * @return
	 */
	public ServerResponse<?> unlockMember(Integer wmemberId)throws Exception;
	
	
	
	/**
	 * 分页查询
	 * @param page
	 * @return
	 */
	public ServerResponse<MyPageUtil<WalletMember>> getPage(Page<WalletMember> page,Integer status,String phone, Integer memberType)throws Exception;
	
	
	/**
	 * 重置手机
	 * @param busId 商家id
	 * @param newPhone 新号码
	 * @param verificationCode 新手机验证码
	 * @param wmemberId 钱包会员id
	 * @return
	 */
	ServerResponse<?> reset(Integer busId,String newPhone,String verificationCode,Integer wmemberId)throws Exception;
	
	
	/**
	 * 返现百分比设置
	 * @param wmemberId
	 * @return
	 */
	public ServerResponse<?> setcashbackPercent(SetcashbackPercent setcashbackPercent)throws Exception;
	
	
	/**
	 * 获取会员认证类型
	 * @param budId 商家id
	 * @return 3 :个人, 1:企业
	 */
	public ServerResponse<Integer> getMemberAuth(Integer busId) throws Exception;
}
