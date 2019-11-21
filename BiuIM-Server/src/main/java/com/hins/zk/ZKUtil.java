package com.hins.zk;

import com.hins.config.AppConfiguration;

import org.I0Itec.zkclient.ZkClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @Description:zookeeper工具
 * @Author:Wyman
 * @Date:2019/11/21
 */
@Component
public class ZKUtil {

    private static Logger logger = LoggerFactory.getLogger(ZKUtil.class);

    @Autowired
    private ZkClient zkClient;

    @Autowired
    private AppConfiguration appConfiguration;

    /**
     * 创建父级节点
     */
    public void createRootNode(){

        boolean exists = zkClient.exists(appConfiguration.getZkRoot());
        if(exists){
            return ;
        }

        //创建 root
        zkClient.createPersistent(appConfiguration.getZkRoot());
    }

    /**
     * 写入指定节点 临时节点
     * @param path
     */
    public void createNode(String path){ zkClient.createEphemeral(path); }


}
