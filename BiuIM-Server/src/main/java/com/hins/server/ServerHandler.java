package com.hins.server;

import com.hins.protocol.Packet;
import com.hins.protocol.PacketCodeC;
import com.hins.protocol.request.LoginRequestPacket;
import com.hins.protocol.request.MessageRequestPacket;
import com.hins.protocol.response.LoginResponsePacket;
import com.hins.protocol.response.MessageResponsePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Date;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class ServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf requestByteBuf = (ByteBuf) msg;

        Packet packet = PacketCodeC.INSTANCE.decode(requestByteBuf);

        //处理登录指令
        if(packet instanceof LoginRequestPacket){
            System.out.println(new Date() + ": 收到客户端登录请求...");

            LoginRequestPacket loginRequestPacket = (LoginRequestPacket)packet;

            //响应数据包
            LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
            loginResponsePacket.setVersion(packet.getVersion());

            if(valid(loginRequestPacket)){
                //登录成功
                loginResponsePacket.setSuccess(true);
                System.out.println(new Date() + "登录成功!");
            }else{
                loginResponsePacket.setReason("账户名称和密码校验失败!");
                loginResponsePacket.setSuccess(false);
                System.out.println(new Date() + "登录失败!");
            }

            //返回登录响应
            ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), loginResponsePacket);
            ctx.writeAndFlush(buf);

        }else if(packet instanceof MessageRequestPacket){
            //消息指令

            // 客户端发来消息
            MessageRequestPacket messageRequestPacket = ((MessageRequestPacket) packet);

            MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
            System.out.println(new Date() + ": 收到客户端消息: " + messageRequestPacket.getMessage());
            messageResponsePacket.setMessage("服务端回复【" + messageRequestPacket.getMessage() + "】");
            ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc().ioBuffer(), messageResponsePacket);
            ctx.channel().writeAndFlush(responseByteBuf);
        }
    }

    private boolean valid(LoginRequestPacket loginRequestPacket) {
        return true;
    }
}
