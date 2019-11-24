package com.hins.config;

import lombok.Data;
import lombok.ToString;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @Description:读取配置文件配置
 * @Author:Wyman
 * @Date:2019/11/21
 */
@Component
@Data
@ToString
public class AppConfiguration {

    @Value("${app.zk.root}")
    private String zkRoot;

    @Value("${app.zk.addr}")
    private String zkAddr;

    @Value("${app.zk.switch}")
    private boolean zkSwitch;

    @Value("${biuim.server.port}")
    private int biuImServerPort;

    @Value("${app.zk.connect.timeout}")
    private int zkConnectTimout;

    /**
     * 测试静态属性通过非静态的方法注入属性
     */
    private static int STATIC_TEST;

    @Value("${app.zk.test.static}")
    public void setStaticTest(int staticTest){
        STATIC_TEST = staticTest;
    }


    public static int getStaticTest() {
        return STATIC_TEST;
    }
}
