package com.hins.server.handler;

import com.hins.protocol.request.LoginRequestPacket;
import com.hins.protocol.response.LoginResponsePacket;
import com.hins.session.Session;
import com.hins.utils.SessionUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Date;
import java.util.UUID;

/**
 * @Description: 处理登录请求的逻辑处理器
 * @Author:Wyman
 * @Date:2019/10/28
 */
public class LoginRequestHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {

        System.out.println(new Date() + ": 收到客户端登录请求...");

        LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
        loginResponsePacket.setVersion(loginRequestPacket.getVersion());
        loginResponsePacket.setUserName(loginRequestPacket.getUsername());

        if (valid(loginRequestPacket)) {
            loginResponsePacket.setSuccess(true);
            //生成userId
            String userId = randomUserId();
            //存到返回packet中
            loginResponsePacket.setUserId(userId);
            System.out.println("[" + loginRequestPacket.getUsername()+ "] 登录成功!");
            //内存中保存userId和session关系
            SessionUtils.bindSession(new Session(userId,loginRequestPacket.getUsername()),ctx.channel());


        } else {
            loginResponsePacket.setReason("账号密码校验失败");
            loginResponsePacket.setSuccess(false);
            System.out.println(new Date() + ": 登录失败!");
        }

        // 登录响应
        ctx.channel().writeAndFlush(loginResponsePacket);

    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }

    /** 使用随机生成的uuid作为userId */
    private static String randomUserId() {
        return UUID.randomUUID().toString().split("-")[0];
    }

    /**
     * 断开连接的回调，系统中清楚userId和连接的关系内存
     * @param ctx
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        SessionUtils.unBindSession(ctx.channel());
    }


    public static void main(String[] args) {

        System.out.println(randomUserId());
    }
}
