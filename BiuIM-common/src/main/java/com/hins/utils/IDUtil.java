package com.hins.utils;

import java.util.UUID;

/**
 * @Description: id生成工具类
 * @Author:Wyman
 * @Date:2019/11/13
 */
public class IDUtil {

    public static String randomId(){

        return UUID.randomUUID().toString().split("-")[0];
    }
}
