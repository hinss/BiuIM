package com.hins.serialize.impl;

import org.I0Itec.zkclient.exception.ZkMarshallingError;
import org.I0Itec.zkclient.serialize.ZkSerializer;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class MySerializer implements ZkSerializer {

    private static final String CHARSET = "UTF-8";


    @Override
    public byte[] serialize(Object o) throws ZkMarshallingError {
        try {
            return String.valueOf(o).getBytes(CHARSET);
        } catch (UnsupportedEncodingException e) {
           throw new ZkMarshallingError(e);
        }
    }

    @Override
    public Object deserialize(byte[] bytes) throws ZkMarshallingError {


        try {
            return new String(bytes,CHARSET);
        } catch (UnsupportedEncodingException e) {
            throw new ZkMarshallingError(e);
        }
    }
}
