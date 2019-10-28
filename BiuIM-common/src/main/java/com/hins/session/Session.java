package com.hins.session;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description: 会话信息对象
 * @Author:Wyman
 * @Date:2019
 */
@Data
@NoArgsConstructor
public class Session {

    //用户唯一标识
    private String userId;

    private String userName;


    public Session(String userId, String userName) {
        this.userId = userId;
        this.userName = userName;
    }

    @Override
    public String toString() {
        return "Session{" +
                "userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                '}';
    }
}
