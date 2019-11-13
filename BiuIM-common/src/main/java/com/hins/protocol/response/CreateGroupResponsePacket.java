package com.hins.protocol.response;

import com.hins.protocol.Packet;
import com.hins.protocol.command.Command;
import lombok.Data;

import java.util.List;

/**
 * @Description: 创建群组回应数据包
 * @Author:Wyman
 * @Date:2019
 */
@Data
public class CreateGroupResponsePacket extends Packet {

    private boolean success;

    //群组id
    private String groupId;

    //群成员姓名集合
    private List<String> userNameList;


    @Override
    public Byte getCommand() {
        return Command.CREATE_GROUP_RESPONSE;
    }
}
