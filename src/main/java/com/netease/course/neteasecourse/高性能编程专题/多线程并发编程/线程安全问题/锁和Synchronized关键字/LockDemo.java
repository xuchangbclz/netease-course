package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.锁和Synchronized关键字;


import java.io.IOException;

/**
 * 两个线程，对 i 变量进行递增操作
 */
public class LockDemo {

    volatile int i = 0;

    // 自己写 -- 模拟ReentrantLock
    CustomizeLock lock = new CustomizeLock(); // 基于sync关键字的原理来手写
    //Lock lock = new ReentrantLock();

    public void add() { // 方法栈帧~ 局部变量
        lock.lock(); // 如果一个线程拿到锁，其他线程会等待
        try {
            i++; // 三次操作,字节码太难懂
        } finally {
            lock.unlock();
        }
    }

    public static void main(String[] args) throws IOException {
        LockDemo ld = new LockDemo();

        for (int i = 0; i < 2; i++) { // 2w相加，20000
            new Thread(() -> {
                for (int j = 0; j < 10000; j++) {
                    ld.add();
                }
            }).start();
        }
        System.in.read(); // 输入任意键退出
        System.out.println(ld.i);
    }

}
