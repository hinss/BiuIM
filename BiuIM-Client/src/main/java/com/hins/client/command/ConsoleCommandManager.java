package com.hins.client.command;

import io.netty.channel.Channel;

import java.util.Map;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description: 管理控制台命令执行器
 * @Author:Wyman
 * @Date:2019/11/12
 */
public class ConsoleCommandManager implements ConsoleCommand{

    private Map<String,ConsoleCommand> consoleCommandMap = new ConcurrentHashMap<>();

    public ConsoleCommandManager() {

        consoleCommandMap.put("sendToUser",new SendToUserConsoleCommand());
        consoleCommandMap.put("logout",new LogoutConsoleCommand());
        consoleCommandMap.put("createGroup",new CreateGroupConsoleCommand());


    }

    @Override
    public void exec(Scanner sc, Channel channel) {

        //获取第一个指令
        String command = sc.next();

        ConsoleCommand command1 = consoleCommandMap.get(command);

        if(command1 != null){
            command1.exec(sc,channel);
        }else{
            System.out.println("无法识别:["+command+"]指令，请重新输入!");
        }

    }
}
