package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.线程安全原子操作.ABA;


import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 实现一个栈（后进先出）
 */
public class Stack {

    // top cas无锁修改
    AtomicReference<Node> top = new AtomicReference<Node>();

    public void push(Node node){ // 入栈
        Node oldTop;
        do{
            oldTop = top.get();
            node.next = oldTop;
        }
        while(!top.compareAndSet(oldTop, node)); // CAS 替换栈顶
    }
    // 为了演示ABA效果， 增加一个CAS操作的延时
    public Node pop(int time) throws InterruptedException { // 出栈 -- 取出栈顶
        Node newTop;
        Node oldTop;
        do{
            oldTop = top.get();
            if(oldTop == null){
                return null;
            }
            newTop = oldTop.next;
            TimeUnit.SECONDS.sleep(time); // 休眠指定的时间
        }
        while(!top.compareAndSet(oldTop, newTop));
        return oldTop;
    }

}
