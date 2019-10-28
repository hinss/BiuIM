package com.hins.utils;

import com.hins.attribute.Attributes;
import com.hins.session.Session;
import io.netty.channel.Channel;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author:Wyman
 * @Date:2019
 */
public class SessionUtils {

    // userId --> channel的映射
    private static final Map<String,Channel> userIdChannelMap = new ConcurrentHashMap<>();


    /**
     * 绑定会话关系
     * @param session
     * @param channel
     */
    public static void bindSession(Session session, Channel channel){

        userIdChannelMap.put(session.getUserId(),channel);

        //将session对象保存到channel对象的属性中
        channel.attr(Attributes.SESSION).set(session);
    }

    /**
     * 取消绑定会话信息
     * @param channel
     */
    public static void unBindSession(Channel channel) {
        if (hasLogin(channel)) {
            userIdChannelMap.remove(getSession(channel).getUserId());
            channel.attr(Attributes.SESSION).set(null);
        }
    }

    /**
     * 判断一个用户是否登录，就是判断保持连接的channel通道中是否有保存他/她的登录session信息
     * @param channel
     * @return
     */
    public static boolean hasLogin(Channel channel){

        return channel.hasAttr(Attributes.SESSION);
    }

    /**
     * 获取session
     */
    public static Session getSession(Channel channel){

        return channel.attr(Attributes.SESSION).get();
    }

    /**
     * 根据userId 获取用户长连接通道
     * @param userId
     * @return
     */
    public static Channel getChannel(String userId) {

        return userIdChannelMap.get(userId);
    }
}
