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

    //发送至的客户端的userId
    private String toUserId;

    private String message;

    public MessageRequestPacket(String toUserId, String message) {
        this.toUserId = toUserId;
        this.message = message;
    }

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_REQUEST;
    }
}
