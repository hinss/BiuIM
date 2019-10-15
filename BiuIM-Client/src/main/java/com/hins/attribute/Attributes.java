package com.hins.attribute;

import io.netty.util.AttributeKey;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

}
