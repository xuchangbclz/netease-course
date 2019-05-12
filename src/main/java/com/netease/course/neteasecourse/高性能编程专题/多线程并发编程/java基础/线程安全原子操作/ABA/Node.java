package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.线程安全原子操作.ABA;

/**
 * 存储在栈里面元素 -- 对象
 */
public class Node {
    public final String item;
    public Node next;
    public Node(String item){
        this.item = item;
    }

}
