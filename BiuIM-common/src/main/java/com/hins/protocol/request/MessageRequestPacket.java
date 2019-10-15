package com.hins.protocol.request;

import com.hins.protocol.Packet;
import com.hins.protocol.command.Command;
import lombok.Data;

/**
 * @Description: 消息请求数据包
 * @Author:Wyman
 * @Date:2019
 */
@Data
public class MessageRequestPacket extends Packet {

    private String message;


    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
