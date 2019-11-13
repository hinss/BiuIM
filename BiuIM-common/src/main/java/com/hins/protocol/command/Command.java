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

    //创建群组请求
    Byte CREATE_GROUP_REQUEST = 5;

    //创建群组回应
    Byte CREATE_GROUP_RESPONSE = 6;

    //登出请求
    Byte LOGIN_OUT_REQUEST = 7;

    //登出回应
    Byte LOGIN_OUT_RESPONSE = 8;
}
