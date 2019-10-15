package com.hins.protocol.request;

import com.hins.protocol.Packet;
import com.hins.protocol.command.Command;
import lombok.Data;

/**
 * @Description: 登录的请求数据包
 * @Author:Wyman
 * @Date:2019
 */
@Data
public class LoginRequestPacket extends Packet {

    //用户Id
    private String userId;

    //用户名称
    private String username;

    //密码
    private String password;


    @Override
    public Byte getCommand() {
        return Command.LOGIN_REQUEST;
    }
}
