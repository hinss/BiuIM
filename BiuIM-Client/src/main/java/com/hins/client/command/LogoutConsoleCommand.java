package com.hins.client.command;

import com.hins.protocol.request.LogoutRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description:登出指令
 * @Author:Wyman
 * @Date:2019/11/13
 */
public class LogoutConsoleCommand implements ConsoleCommand {


    @Override
    public void exec(Scanner sc, Channel channel) {

        LogoutRequestPacket logoutRequestPacket = new LogoutRequestPacket();
        channel.writeAndFlush(logoutRequestPacket);
    }
}
