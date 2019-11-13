package com.hins.client.command;

import com.hins.protocol.request.MessageRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description:发送指令给用户
 * @Author:Wyman
 * @Date:2019/11/13
 */
public class SendToUserConsoleCommand implements ConsoleCommand {

    @Override
    public void exec(Scanner sc, Channel channel) {

        System.out.println("发送消息给某个用户: ");

        String toUserId = sc.next();
        String message = sc.next();

        channel.writeAndFlush(new MessageRequestPacket(toUserId,message));


    }
}
