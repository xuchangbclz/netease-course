package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.对接保朝写的Netty服务端;

/**
 * @Author daituo
 * @Date
 **/
public enum  MonsterStatusEnum {

    inFighting(1,"战斗状态"),outOfFighting(2,"待机状态");

    private Integer code;
    private String value;

    MonsterStatusEnum(Integer code, String value) {
        this.code = code;
        this.value = value;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
