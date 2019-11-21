package com.hins.server;

import com.hins.init.BiuIMChannelInitializer;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * @Description: BiuIM服务器启动
 * @Author:Wyman
 * @Date:2019/11/21
 */
@Component
public class BiuIMServer {

    private final static Logger LOGGER = LoggerFactory.getLogger(BiuIMServer.class);

    private EventLoopGroup boss = new NioEventLoopGroup();
    private EventLoopGroup worker = new NioEventLoopGroup();

    @Value("${biuim.server.port}")
    private int nettyPort;

    /**
     * 启动BIM Server
     */
    @PostConstruct
    public void start() throws InterruptedException {

        ServerBootstrap serverBootstrap = new ServerBootstrap()
                //设置线程组
                .group(boss,worker)
                //设置线程模型
                .channel(NioServerSocketChannel.class)
                //绑定配置好的端口
                .localAddress(nettyPort)
                //保持长连接
                .childOption(ChannelOption.SO_KEEPALIVE,true)
                //设置server pinpline处理链条
                .childHandler(new BiuIMChannelInitializer());

        //绑定端口
        ChannelFuture future = serverBootstrap.bind().sync();
        if(future.isSuccess()){
            LOGGER.info("启动 biuim server 成功!");
        }
    }

    /**
     * 销毁 释放资源
     */
    @PreDestroy
    public void destroy(){

        boss.shutdownGracefully().syncUninterruptibly();
        worker.shutdownGracefully().syncUninterruptibly();
        LOGGER.info("关闭 biuim server 成功");

    }

}
