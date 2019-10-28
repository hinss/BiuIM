package com.hins.server.handler;

import com.hins.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * @Description: 登录验签逻辑处理器
 * @Author:Wyman
 * @Date:2019
 */
public class AuthHandler extends ChannelInboundHandlerAdapter {


    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        if(!SessionUtils.hasLogin(ctx.channel())){
            ctx.channel().close();
        }else{
            //如果已经登录验证过了，就接触这个逻辑处理器，以后的连接都不会再走这
            ctx.pipeline().remove(this);
            super.channelRead(ctx,msg);
        }
    }
}
