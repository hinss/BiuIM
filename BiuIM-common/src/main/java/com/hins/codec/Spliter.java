package com.hins.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class Spliter extends LengthFieldBasedFrameDecoder {

    private static final int LENGTH_FIELD_OFFSET = 7;

    private static final int LENGTH_FIELD_LENGTH = 4;

    public Spliter(){
        super(Integer.MAX_VALUE,LENGTH_FIELD_OFFSET,LENGTH_FIELD_LENGTH);
    }
}
