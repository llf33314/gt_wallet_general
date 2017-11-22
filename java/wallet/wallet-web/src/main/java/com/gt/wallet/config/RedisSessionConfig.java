package com.gt.wallet.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;
import org.springframework.session.web.http.DefaultCookieSerializer;

import lombok.extern.slf4j.Slf4j;

/**
 * RedisSession配置Config
 *
 * @author zhangmz
 * @version 1.0.0
 * @date 2017/07/16
 */
@Configuration
@EnableRedisHttpSession(maxInactiveIntervalInSeconds = 3600)
@Slf4j
public class RedisSessionConfig {

    /** 日志 */
    // 注入配置属性 根据环境配置切换
    @Value( "${web.redisSession.cookieName}" )
    private String redisSessionCookieName;
    @Value( "${web.redisSession.cookiePath}" )
    private String redisSessionCookiePath;
    @Value( "${web.redisSession.domainName}" )
    private String redisSessionDomainName;

    /**
     * 设置Cookie作用于
     *
     * @return DefaultCookieSerializer
     */
    @Bean( name = "defaultCookieSerializer" )
    public DefaultCookieSerializer defaultCookieSerializer() {
	log.debug( " domainName:{},cookieName:{},cookiePath:{} ", redisSessionDomainName, redisSessionCookieName, redisSessionCookiePath );
	DefaultCookieSerializer cookieSerializer = new DefaultCookieSerializer();
	cookieSerializer.setDomainName( redisSessionDomainName );
	cookieSerializer.setCookieName( redisSessionCookieName );
	cookieSerializer.setCookiePath( redisSessionCookiePath );
	return cookieSerializer;
    }

}
