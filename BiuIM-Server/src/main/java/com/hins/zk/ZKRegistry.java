package com.hins.zk;

import com.hins.config.AppConfiguration;
import com.hins.util.SpringBeanFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class ZKRegistry implements Runnable {

    private static Logger logger = LoggerFactory.getLogger(ZKRegistry.class);

    //注册服务ip
    private String ip;
    //服务注册端口
    private int serverPort;
    //biuimServer的监听端口
    private int biuImPort;

    private ZKUtil zkUtil;

    private AppConfiguration appConfiguration;

    public ZKRegistry(String ip,int biuImPort,int serverPort){

        this.ip = ip;
        this.biuImPort = biuImPort;
        this.serverPort = serverPort;
        zkUtil = SpringBeanFactory.getBean(ZKUtil.class);
        appConfiguration = SpringBeanFactory.getBean(AppConfiguration.class);
    }

    @Override
    public void run() {

        //创建父节点
        zkUtil.createRootNode();

        //是否要将自己注册到 ZK
        if(appConfiguration.isZkSwitch()){
            String path = appConfiguration.getZkRoot() + "ip-" + ip + ":" + biuImPort + ":" + serverPort;
            zkUtil.createNode(path);
            logger.info("注册zookeeper成功,msg=[{}]",path);
        }


    }
}
