package com.hins.client.command;

import com.hins.protocol.request.CreateGroupRequestPacket;
import io.netty.channel.Channel;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * @Description: 客户端 创建群组命令
 * @Author:Wyman
 * @Date:2019/11/13
 */
public class CreateGroupConsoleCommand implements ConsoleCommand {

    private static final String USER_ID_SPLITER = ",";


    @Override
    public void exec(Scanner sc, Channel channel) {

      CreateGroupRequestPacket createGroupRequestPacket = new CreateGroupRequestPacket();


      System.out.println("[拉人群聊] 输入 userId列表,userId 之间英文逗号隔开: ");
      String userIds = sc.next();

      createGroupRequestPacket.setUserIdList(Arrays.asList(userIds.split(USER_ID_SPLITER)));

      channel.writeAndFlush(createGroupRequestPacket);

    }
}
