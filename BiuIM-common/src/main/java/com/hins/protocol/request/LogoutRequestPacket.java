package com.hins.protocol.request;

import com.hins.protocol.Packet;
import com.hins.protocol.command.Command;
import lombok.Data;

/**
 * @Description: 登出指令数据包
 * @Author:Wyman
 * @Date:2019/11/13
 */
@Data
public class LogoutRequestPacket extends Packet {
    @Override
    public Byte getCommand() {
        return Command.LOGIN_OUT_REQUEST;
    }
}
