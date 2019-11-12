package com.hins.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Description: 解决粘包、拆包问题的长度域拆包器
 * @Author:Wyman
 * @Date:2019
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    //偏移量
    private static final int LENGTH_FIELD_OFFSET = 7;

    //真实数据长度
    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter(){
        super(Integer.MAX_VALUE,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH);
    }
}
