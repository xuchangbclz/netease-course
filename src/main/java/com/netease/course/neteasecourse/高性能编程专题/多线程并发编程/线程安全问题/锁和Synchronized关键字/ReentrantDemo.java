package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.锁和Synchronized关键字;

import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示可重入锁
 **/
public class ReentrantDemo {

    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) {
        lock.lock();  // block until condition holds
        try {
            System.out.println("第一次获取锁");
            System.out.println("当前线程获取锁的次数" + lock.getHoldCount());
            lock.lock(); // 可重入的概念 // 如果是不可重入，这个代码应该阻塞
            System.out.println("第二次获取锁了");
            System.out.println("当前线程获取锁的次数" + lock.getHoldCount());
        } finally {
            // lock.unlock();
            lock.unlock();
        }
        System.out.println("当前线程获取锁的次数" + lock.getHoldCount());

        // 如果锁没有释放完全，此时其他线程是拿不到锁的
        new Thread(() -> {
            System.out.println(Thread.currentThread() + " 期望抢到锁");
            lock.lock();
            System.out.println(Thread.currentThread() + " 线程拿到了锁");
        }).start();


    }

}
