package com.gt.wallet.utils;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;


@Component
@ConfigurationProperties("web")
public class WalletWebConfig {
	
	
	/**
	 * 本站域名
	 */
	private static String domain;
	
	/**
	 * 公司官网域名
	 */
	private static String homeUrl;
	
	
	/**
	 * ftp ip
	 */
	private static String ftpIp;
	
	
	/**
	 * ftp 端口
	 */
	private static String ftpPort;
	
	/**
	 * ftp 账号
	 */
	private static String ftpUser;
	
	/**
	 * ftp 密码
	 */
	private static String ftpPassword;
	
	/**
	 * 图片存放路径
	 */
	private static String pathImage;
	
	
	/**
	 * 资源访问路径
	 */
	private static String fileDomain;
	
	/**
	 * 第三方api测试环境网关地址
	 */
	private static String yunApiDns;
	
	/**
	 * 第三方api接口地址(测试)
	 */
	private static String apiUrl;
	
	
	/**
	 * 证书路径
	 */
	private static String yunApiKeyPath;
	
	
	
	/**
	 * 商户应用编号
	 */
	private static String yunApiSysid;
	
	
	
	
	/**
	 * 证书密码
	 */
	private static String yunPwd;
	
	
	
	/**
	 * 账户集编号
	 */
	private static String yunBizUserId;
	
	private static String yunApiAlias;
	
	
	/**
	 * wxmp 签名KEY
	 */
	private static String wxmpKey;
	
	
//	 // 注入配置属性 根据环境配置切换
	public static String getDomain() {
		return domain;
	}

	public static String getHomeUrl() {
		return homeUrl;
	}

	public static void setDomain(String domain) {
		WalletWebConfig.domain = domain;
	}

	public static void setHomeUrl(String homeUrl) {
		WalletWebConfig.homeUrl = homeUrl;
	}

	public static void setFtpIp(String ftpIp) {
		WalletWebConfig.ftpIp = ftpIp;
	}

	public static void setFtpPort(String ftpPort) {
		WalletWebConfig.ftpPort = ftpPort;
	}

	public static void setFtpUser(String ftpUser) {
		WalletWebConfig.ftpUser = ftpUser;
	}

	public static void setFtpPassword(String ftpPassword) {
		WalletWebConfig.ftpPassword = ftpPassword;
	}

	public static void setPathImage(String pathImage) {
		WalletWebConfig.pathImage = pathImage;
	}

	public static void setFileDomain(String fileDomain) {
		WalletWebConfig.fileDomain = fileDomain;
	}

	public static String getFtpIp() {
		return ftpIp;
	}

	public static String getFtpPort() {
		return ftpPort;
	}

	public static String getFtpUser() {
		return ftpUser;
	}

	public static String getFtpPassword() {
		return ftpPassword;
	}

	public static String getPathImage() {
		return pathImage;
	}

	public static String getFileDomain() {
		return fileDomain;
	}

	public static String getYunApiDns() {
		return yunApiDns;
	}

	public static String getApiUrl() {
		return apiUrl;
	}

	public static String getYunApiKeyPath() {
		return yunApiKeyPath;
	}

	public static String getYunApiSysid() {
		return yunApiSysid;
	}

	public static String getYunPwd() {
		return yunPwd;
	}

	public static String getYunBizUserId() {
		return yunBizUserId;
	}

	public static String getYunApiAlias() {
		return yunApiAlias;
	}

	public static void setYunApiDns(String yunApiDns) {
		WalletWebConfig.yunApiDns = yunApiDns;
	}

	public static void setApiUrl(String apiUrl) {
		WalletWebConfig.apiUrl = apiUrl;
	}

	public static void setYunApiKeyPath(String yunApiKeyPath) {
		WalletWebConfig.yunApiKeyPath = yunApiKeyPath;
	}

	public static void setYunApiSysid(String yunApiSysid) {
		WalletWebConfig.yunApiSysid = yunApiSysid;
	}

	public static void setYunPwd(String yunPwd) {
		WalletWebConfig.yunPwd = yunPwd;
	}

	public static void setYunBizUserId(String yunBizUserId) {
		WalletWebConfig.yunBizUserId = yunBizUserId;
	}

	public static void setYunApiAlias(String yunApiAlias) {
		WalletWebConfig.yunApiAlias = yunApiAlias;
	}

	public static String getWxmpKey() {
		return wxmpKey;
	}

	public static void setWxmpKey(String wxmpKey) {
		WalletWebConfig.wxmpKey = wxmpKey;
	}



	
}
