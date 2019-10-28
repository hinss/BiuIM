package com.hins.client;

import com.hins.client.handler.FirstClientHandler;
import com.hins.client.handler.LoginResponseHandler;
import com.hins.client.handler.MessageResponseHandler;
import com.hins.codec.PacketDecoder;
import com.hins.codec.PacketEncoder;
import com.hins.codec.Spliter;
import com.hins.protocol.PacketCodeC;
import com.hins.protocol.request.MessageRequestPacket;
import com.hins.util.LoginUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.Date;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

/**
 * @Description: 基于Netty实现的客户端
 * @Author:Wyman
 * @Date:2019
 */
public class NettyClient {

    //重试次数
    private static final int MAX_RETRY = 5;
    //服务端ip
    private static final String HOST = "127.0.0.1";
    //服务端的端口
    private static final int PORT = 8000;


    public static void main(String[] args) {

        NioEventLoopGroup workerGroup = new NioEventLoopGroup();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .option(ChannelOption.SO_KEEPALIVE, true)
                .option(ChannelOption.TCP_NODELAY, true)
                .handler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel ch) {
//                        ch.pipeline().addLast(new Spliter());
//                        ch.pipeline().addLast(new PacketDecoder());
//                        ch.pipeline().addLast(new LoginResponseHandler());
//                        ch.pipeline().addLast(new MessageResponseHandler());
//                        ch.pipeline().addLast(new PacketEncoder());
                        ch.pipeline().addLast(new FirstClientHandler());
                    }
                });

        connect(bootstrap, HOST, PORT, MAX_RETRY);
    }

    private static void connect(Bootstrap bootstrap, String host, int port, int retry) {
        bootstrap.connect(host, port).addListener(future -> {
            if (future.isSuccess()) {
                System.out.println(new Date() + ": 连接成功，启动控制台线程……");
//                Channel channel = ((ChannelFuture) future).channel();
//                startConsoleThread(channel);
            } else if (retry == 0) {
                System.err.println("重试次数已用完，放弃连接！");
            } else {
                // 第几次重连
                int order = (MAX_RETRY - retry) + 1;
                // 本次重连的间隔
                int delay = 1 << order;
                System.err.println(new Date() + ": 连接失败，第" + order + "次重连……");
                bootstrap.config().group().schedule(() -> connect(bootstrap, host, port, retry - 1), delay, TimeUnit
                        .SECONDS);
            }
        });
    }

    private static void startConsoleThread(Channel channel) {
        new Thread(() -> {
            while (!Thread.interrupted()) {
                if (LoginUtil.hasLogin(channel)) {
                    System.out.println("循环发送1000条消息至服务端: ");
                    Scanner sc = new Scanner(System.in);
                    String line = sc.nextLine();

                    for(int i = 0; i < 1000; i++){
                        MessageRequestPacket packet = new MessageRequestPacket();
                        packet.setMessage(new Date() + "Hinss现在正在发送数据啦...利物浦是冠军");
                        ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(channel.alloc().ioBuffer(), packet);
                        channel.writeAndFlush(byteBuf);
                    }


//                    packet.setMessage(line);

                }
            }
        }).start();
    }

}
