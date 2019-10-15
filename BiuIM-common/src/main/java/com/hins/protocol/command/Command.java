package com.hins.protocol.command;

/**
 * 指令集
 */
public interface Command {

    //登录请求
    Byte LOGIN_REQUEST = 1;

    //登录回应
    Byte LOGIN_RESPONSE = 2;

    //消息请求
    Byte MESSAGE_REQUEST = 3;

    //消息回复
    Byte MESSAGE_RESPONSE = 4;
}
