package com.hins.server;

import com.hins.codec.PacketDecoder;
import com.hins.codec.PacketEncoder;
import com.hins.server.handler.LoginRequestHandler;
import com.hins.server.handler.MessageRequestHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class NettyServer {

    private static final int PORT = 8000;




    public static void main(String[] args) {

        //指定处理连接的线程组
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        //指定处理IO的线程组
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        //启动类 ServerBootStrap
        final ServerBootstrap serverBootstrap = new ServerBootstrap();

        //配置
        serverBootstrap
                //配置线程组
                .group(bossGroup,workerGroup)
                //配置IO模型
                .channel(NioServerSocketChannel.class)
                .option(ChannelOption.SO_BACKLOG, 1024)
                .childOption(ChannelOption.SO_KEEPALIVE, true)
                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {

                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                     //具体处理每条连接的逻辑
                     ch.pipeline().addLast(new PacketDecoder());
                     ch.pipeline().addLast(new LoginRequestHandler());
                     ch.pipeline().addLast(new MessageRequestHandler());
                     ch.pipeline().addLast(new PacketEncoder());

                    }
                });

        //绑定端口
        bind(serverBootstrap,PORT);



    }

    private static void bind(final ServerBootstrap serverBootstrap, final int port) {
        serverBootstrap.bind(port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println("端口[" + port + "]绑定成功!");
            } else {
                System.err.println("端口[" + port + "]绑定失败!");
            }
        });
    }
}
