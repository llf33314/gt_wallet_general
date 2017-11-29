package com.gt.wallet;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import lombok.extern.slf4j.Slf4j;

/**
 * 单元测试基类
 *
 * @author zhangmz
 * @create 2017/6/16
 */
@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
@Slf4j
public class BasicTest {

    // 开始时间
    private Long start_time;

    @Before
    public void start() {
		start_time = System.currentTimeMillis();
		log.info( "=======================================  单元测试Start =======================================" );
    }

    @After
    public void end() {
    	log.info( "执行结束，方法执行 {} 毫秒", ( System.currentTimeMillis() - start_time ) );
    	log.info( "=======================================  单元测试End =======================================" );
    }

}
