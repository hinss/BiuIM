package com.hins.protocol.response;

import com.hins.protocol.Packet;
import com.hins.protocol.command.Command;
import lombok.Data;

/**
 * @Description: 登出
 * @Author:Wyman
 * @Date:2019
 */
@Data
public class LogoutResponsePacket extends Packet {

    @Override
    public Byte getCommand() {

        return Command.LOGIN_OUT_RESPONSE;
    }
}
