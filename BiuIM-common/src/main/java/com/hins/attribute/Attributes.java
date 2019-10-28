package com.hins.attribute;

import com.hins.session.Session;
import io.netty.util.AttributeKey;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public interface Attributes {

    AttributeKey<Boolean> LOGIN = AttributeKey.newInstance("login");

    AttributeKey<Session> SESSION = AttributeKey.newInstance("session");

}
