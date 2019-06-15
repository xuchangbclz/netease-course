package com.netease.course.neteasecourse.中间件专题.缓存中间件.Guava;

/**
 *
 **/
public class User {

    private String userName;
    private String userId;

    public User(String userName, String userId) {
        this.userName = userName;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    @Override
    public String toString() {
        return userId + " --- " + userName;
    }

}
