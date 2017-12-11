package com.gt.wallet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.gt.api.util.httpclient.JsonUtil;
import com.gt.wallet.data.wallet.response.IndexStatistics;
import com.gt.wallet.mapper.order.WalletMoneyMapper;

import lombok.extern.slf4j.Slf4j;


@RunWith( SpringJUnit4ClassRunner.class )
@SpringBootTest
@Slf4j
public class DaoTest extends BasicTest {
	@Autowired
    private WalletMoneyMapper walletMoneyMapper; //自动注入的Bean
//
    @Value("${spring.mail.username}")
    private String Sender; //读取配置文件中的参数

    @Test
    public void sendSimpleMail() throws Exception {
        try {
        	IndexStatistics indexStatistics=	walletMoneyMapper.getStatisticsByWmemberId(7);
        	log.info("indexStatistics:"+JsonUtil.toJSONString(indexStatistics));
        } catch (Exception e){
            e.printStackTrace();
        }
    }

}
