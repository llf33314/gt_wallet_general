package com.gt.wallet.config.mybatis;

import java.util.HashMap;
import java.util.Map;

import org.springframework.aop.Advisor;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.aop.support.JdkRegexpMethodPointcut;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import com.alibaba.druid.support.spring.stat.DruidStatInterceptor;
import com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean;
import com.baomidou.mybatisplus.spring.boot.starter.SpringBootVFS;

import lombok.extern.slf4j.Slf4j;


/** 
* @author lifengxi(gt_sky@qq.com)
* @version 创建时间：2017年11月17日 下午3:06:21 
* 数据源配置类，包括事务管理，dao、biz层监控
*/
@Configuration
@Slf4j
public class DruidDataSourceConfig  {

    //注册地址
    private static final String[] URL_MAPINGS      = { "/druid/*" };
    //白名单：
    private static final String   ALLOW            = "127.0.0.1,192.168.2.16";
    //IP黑名单 (存在共同时，deny优先于allow) : 如果满足deny的话提示:Sorry, you are not permitted to view this page.
    private static final String   DENY             = "192.168.2.150";
    //登录查看信息的账号密码.
    private static final String   LOGIN_USERNAME   = "admin";
    private static final String   LOGIN_PASSWORD   = "123456";
    //是否能够重置数据.
    private static final String   RESETENABLE      = "false";
    //添加过滤规则.
    private static final String   URLPATTERNS      = "/*";
    //添加排除的规则.
    private static final String   EXCLUSIONS       = "*.js,*.gif,*.jpg,*.png,*.css,*.ico,/druid/*";
    //监控规则
    private static final String[] MONITORING_RULES = {"com.gt.*.mapper..*", "com.gt.wallet.service..*"};

    @ConfigurationProperties( prefix = "datasource.write" )
    @Bean( name = "datasource", initMethod = "init", destroyMethod = "close" )
    @Primary
    public DruidDataSource getDataSource() {
	return new DruidDataSource();
    }

    
    @ConfigurationProperties( prefix = "datasource.slaver" )
    @Bean( name = "readDatasource", initMethod = "init", destroyMethod = "close" )
    public DruidDataSource getReadDataSource() {
	return new DruidDataSource();
    }
    
    
//    @Bean("dynamicDataSource")
//    public DynamicDataSource dynamicDataSource(@Qualifier("datasource") DataSource masterDataSource,
//                                               @Qualifier("readDatasource") DataSource slaverDataSource) {
//        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
//        targetDataSources.put(DataSourceEnum.master, masterDataSource);
//        targetDataSources.put(DataSourceEnum.slaver, slaverDataSource);
//
//        DynamicDataSource dataSource = new DynamicDataSource();
//        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
//        dataSource.setDefaultTargetDataSource(masterDataSource);// 默认的datasource设置为myTestDbDataSource
//
//        return dataSource;
//    }
    
    
    @Bean("dynamicDataSource")
    public DynamicDataSource dynamicDataSource() {
        Map<Object, Object> targetDataSources = new HashMap<Object, Object>();
        targetDataSources.put(DataSourceEnum.master, getDataSource());
        targetDataSources.put(DataSourceEnum.slaver, getReadDataSource());

        DynamicDataSource dataSource = new DynamicDataSource();
        dataSource.setTargetDataSources(targetDataSources);// 该方法是AbstractRoutingDataSource的方法
        dataSource.setDefaultTargetDataSource(getDataSource());// 默认的datasource设置为myTestDbDataSource

        return dataSource;
    }
    
    /**
     * 根据数据源创建SqlSessionFactory
     */
//    @Bean
//    public SqlSessionFactory sqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource,
//                                               @Value("${mybatis-plus.type-aliases-package}") String typeAliasesPackage,
//                                               @Value("${mybatis-plus.mapper-locations}") String mapperLocations) throws Exception {
////        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
//    	com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean factoryBean=new com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean();
//        factoryBean.setDataSource(dynamicDataSource);// 指定数据源(这个必须有，否则报错)
//        // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
//        factoryBean.setTypeAliasesPackage(typeAliasesPackage);// 指定基包
//        factoryBean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));//
//
//        return factoryBean.getObject();
//    }
    
    
    @Bean
    public MybatisSqlSessionFactoryBean sqlSessionFactory(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource,
          @Value("${mybatis-plus.type-aliases-package}") String typeAliasesPackage,
          @Value("${mybatis-plus.mapper-locations}") String mapperLocations) throws Exception {
//    SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
	com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean mybatisPlus=new com.baomidou.mybatisplus.spring.MybatisSqlSessionFactoryBean();
    mybatisPlus.setDataSource(dynamicDataSource);// 指定数据源(这个必须有，否则报错)
    // 下边两句仅仅用于*.xml文件，如果整个持久层操作不需要使用到xml文件的话（只用注解就可以搞定），则不加
    mybatisPlus.setTypeAliasesPackage(typeAliasesPackage);// 指定基包
    mybatisPlus.setVfs(SpringBootVFS.class);
    mybatisPlus.setMapperLocations(new PathMatchingResourcePatternResolver().getResources(mapperLocations));//
    
        return mybatisPlus;
    }
    
    
//    
    /**
     * 配置事务管理器
     */
    @Bean
    public DataSourceTransactionManager transactionManager(@Qualifier("dynamicDataSource") DynamicDataSource dynamicDataSource) throws Exception {
        return new DataSourceTransactionManager(dynamicDataSource);
    }
    
    
//    @Bean
//    public PlatformTransactionManager transactionManager() {
//	return new DataSourceTransactionManager( getDataSource() );
//    }

    /**
     * 注册一个StatViewServlet
     *
     * @return
     */
    @Bean
    public ServletRegistrationBean druidStatViewServle() {
	//org.springframework.boot.context.embedded.ServletRegistrationBean提供类的进行注册.
	ServletRegistrationBean servletRegistrationBean = new ServletRegistrationBean( new StatViewServlet(), URL_MAPINGS );
	//添加初始化参数：initParams
	log.debug( "Druid 白名单IP：{} , 黑名单IP：{} , 登录账号/密码：{}/{} , 是否开启重置数据：{} ", ALLOW, DENY, LOGIN_USERNAME, LOGIN_PASSWORD, RESETENABLE );
	servletRegistrationBean.addInitParameter( "allow", ALLOW );
	servletRegistrationBean.addInitParameter( "deny", DENY );
	servletRegistrationBean.addInitParameter( "loginUsername", LOGIN_USERNAME );
	servletRegistrationBean.addInitParameter( "loginPassword", LOGIN_PASSWORD );
	servletRegistrationBean.addInitParameter( "resetEnable", RESETENABLE );
	return servletRegistrationBean;
    }

    /**
     * 注册一个：filterRegistrationBean
     *
     * @return
     */
    @Bean
    public FilterRegistrationBean druidStatFilter() {
	FilterRegistrationBean filterRegistrationBean = new FilterRegistrationBean( new WebStatFilter() );
	filterRegistrationBean.addUrlPatterns( URLPATTERNS );
	filterRegistrationBean.addInitParameter( "exclusions", EXCLUSIONS );
	return filterRegistrationBean;
    }

    /**
     * 监听Spring
     * 1.定义拦截器
     * 2.定义切入点
     * 3.定义通知类
     *
     * @return
     */
    @Bean
    public DruidStatInterceptor druidStatInterceptor() {
	return new DruidStatInterceptor();
    }

    /**
     * 设置方法切入点
     *
     * @return
     */
    @Bean
    public JdkRegexpMethodPointcut druidStatPointcut() {
	JdkRegexpMethodPointcut druidStatPointcut = new JdkRegexpMethodPointcut();
	druidStatPointcut.setPatterns( MONITORING_RULES );
	return druidStatPointcut;
    }

    /**
     * 配置AOP
     *
     * @return
     */
    @Bean
    public Advisor druidStatAdvisor() {
	return new DefaultPointcutAdvisor( druidStatPointcut(), druidStatInterceptor() );
    }
}
