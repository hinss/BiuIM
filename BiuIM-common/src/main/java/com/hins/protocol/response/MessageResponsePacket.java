package com.hins.protocol.response;

import com.hins.protocol.Packet;
import com.hins.protocol.command.Command;
import lombok.Data;

/**
 * @Description: 消息回复数据包
 * @Author:Wyman
 * @Date:2019
 */
@Data
public class MessageResponsePacket extends Packet {

    private String fromUserId;

    private String fromUserName;

    private String message;

    @Override
    public Byte getCommand() {
        return Command.MESSAGE_RESPONSE;
    }
}
