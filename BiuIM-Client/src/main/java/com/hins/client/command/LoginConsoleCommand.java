package com.hins.client.command;

import com.hins.protocol.request.LoginRequestPacket;
import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * @Description: 登录控制台指令
 * @Author:Wyman
 * @Date:2019/11/13
 */
public class LoginConsoleCommand implements ConsoleCommand {


    @Override
    public void exec(Scanner sc, Channel channel) {

        LoginRequestPacket loginRequestPacket = new LoginRequestPacket();

        System.out.println("输入用户名登录:");
        loginRequestPacket.setUsername(sc.nextLine());
        loginRequestPacket.setPassword("pwd");

        //发送数据包
        channel.writeAndFlush(loginRequestPacket);

        waitForLoginResponse();

    }

    private void waitForLoginResponse() {

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }
    }
}
