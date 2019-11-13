package com.hins.protocol.request;

import com.hins.protocol.Packet;
import com.hins.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @Description: 创建群组请求数据包
 * @Author:Wyman
 * @Date:2019/11/13
 */
@Data
public class CreateGroupRequestPacket extends Packet {

    private List<String> userIdList;


    @Override
    public Byte getCommand() {

        return Command.CREATE_GROUP_REQUEST;
    }
}
