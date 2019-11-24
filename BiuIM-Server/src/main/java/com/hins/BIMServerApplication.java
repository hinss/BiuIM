package com.hins;


import com.hins.config.AppConfiguration;
import com.hins.zk.ZKRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.net.InetAddress;

/**
 * @author hins
 */
@SpringBootApplication
public class BIMServerApplication implements CommandLineRunner {

    private final static Logger LOGGER = LoggerFactory.getLogger(BIMServerApplication.class);

    @Value("${server.port}")
    private int serverPort;

    @Autowired
    private AppConfiguration appConfiguration ;


    public static void main( String[] args )
    {
        SpringApplication.run(BIMServerApplication.class);
        LOGGER.info("===启动 Server 成功===");

    }

    @Override
    public void run(String... args) throws Exception {

        LOGGER.info("===开始将服务注册到zookeeper===");
        //获取本机的ip地址
        String addr = InetAddress.getLocalHost().getHostAddress();

        //开启一条线程
        Thread thread = new Thread(new ZKRegistry(addr,appConfiguration.getBiuImServerPort(),serverPort));
        thread.setName("zkRegistry");
        thread.start();

        LOGGER.info("===输出静态变量:" + AppConfiguration.getStaticTest() + "===");


    }
}
