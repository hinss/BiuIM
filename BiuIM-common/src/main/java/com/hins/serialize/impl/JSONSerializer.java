package com.hins.serialize.impl;

import com.alibaba.fastjson.JSON;
import com.hins.serialize.Serializer;
import com.hins.serialize.SerializerAlgorithm;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class JSONSerializer implements Serializer {


    @Override
    public byte getSerializerAlogrithm() {
        return SerializerAlgorithm.JSON;
    }

    @Override
    public byte[] serialize(Object object)  {
        return JSON.toJSONBytes(object);
    }

    @Override
    public <T> T deserialize(Class<T> clazz, byte[] bytes) {
        return JSON.parseObject(bytes, clazz);
    }
}
