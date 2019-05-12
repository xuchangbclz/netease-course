package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.锁和Synchronized关键字;

/**
 * 可重入锁
 */
public class SyncDemo2 {

    public synchronized void test1(Object arg) {
        // 继续执行，保证能读取到之前的修改 JMM
        System.out.println(Thread.currentThread() + " 我开始执行 " + arg);
        if (arg == null) {
            test1(new Object());
        }
        System.out.println(Thread.currentThread() + " 我执行结束" + arg);
    }

    public static void main(String[] args) throws InterruptedException {
        new SyncDemo2().test1(null);
    }


}
