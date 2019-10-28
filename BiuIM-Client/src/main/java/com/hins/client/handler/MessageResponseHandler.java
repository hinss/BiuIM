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

        String fromUserId = messageResponsePacket.getFromUserId();
        String fromUserName = messageResponsePacket.getFromUserName();


        System.out.println(fromUserId + ": -> " + ": 收到" + fromUserName + "的消息: " + messageResponsePacket.getMessage());

    }
}
