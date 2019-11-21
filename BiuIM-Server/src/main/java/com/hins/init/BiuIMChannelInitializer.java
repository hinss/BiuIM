package com.hins.init;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;

/**
 * @Description: 设置channel连接后要绑定的pipeline
 * @Author:Wyman
 * @Date:2019/11/21
 */
public class BiuIMChannelInitializer extends ChannelInitializer<Channel> {


    /**
     * This method will be called once the Channel was registered.
     * @param ch
     * @throws Exception
     */
    @Override
    protected void initChannel(Channel ch) throws Exception {

        // ch.pipeline()
        // todo 绑定协议相关的解码器


    }
}
