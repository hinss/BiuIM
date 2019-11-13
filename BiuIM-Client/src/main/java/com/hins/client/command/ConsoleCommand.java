package com.hins.client.command;

import io.netty.channel.Channel;

import java.util.Scanner;

/**
 * 客户端控制台指令接口
 * @Author:hins
 * @Date:2019/11/12
 */
public interface ConsoleCommand {

    void exec(Scanner sc, Channel channel);
}
