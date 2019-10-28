package com.hins.server.handler;

import com.hins.protocol.request.MessageRequestPacket;
import com.hins.protocol.response.MessageResponsePacket;
import com.hins.session.Session;
import com.hins.utils.SessionUtils;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class MessageRequestHandler extends SimpleChannelInboundHandler<MessageRequestPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MessageRequestPacket messageRequestPacket) throws Exception {

        //1.拿到消息发送方的会话信息
        Session session = SessionUtils.getSession(ctx.channel());

        //2.通过消息发送方的会话信息构造要发送的信息
        MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
        messageResponsePacket.setFromUserId(session.getUserId());
        messageResponsePacket.setFromUserName(session.getUserName());
        messageResponsePacket.setMessage(messageRequestPacket.getMessage());

        //3.拿到消息接收方的channel
        Channel toUserChannel = SessionUtils.getChannel(messageRequestPacket.getToUserId());

        //4.将消息发送给消息接收方
        if(toUserChannel != null && SessionUtils.hasLogin(toUserChannel)){
            toUserChannel.writeAndFlush(messageResponsePacket);
        }else{
            System.err.println("[" + messageRequestPacket.getToUserId() + "] 不在线，发送失败!");
        }


    }
}
