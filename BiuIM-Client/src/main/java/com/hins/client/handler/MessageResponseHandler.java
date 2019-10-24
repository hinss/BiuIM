package com.hins.client.handler;

import com.hins.protocol.response.MessageResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class MessageResponseHandler extends SimpleChannelInboundHandler<MessageResponsePacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, MessageResponsePacket messageResponsePacket) throws Exception {

        System.out.println(new Date() + ": 收到服务端的消息: " + messageResponsePacket.getMessage());

    }
}
