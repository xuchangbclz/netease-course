package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.锁和Synchronized关键字;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 演示 锁的可响应中断
 **/
public class LockInterruptiblyDemo {

    private Lock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
        LockInterruptiblyDemo demo1 = new LockInterruptiblyDemo();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    demo1.test(Thread.currentThread());
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        };
        Thread thread0 = new Thread(runnable);
        Thread thread1 = new Thread(runnable);
        thread0.start();
        Thread.sleep(500); // 等待0.5秒，让thread0先执行

        thread1.start();
        Thread.sleep(2000); // 两秒后，中断thread1

        thread1.interrupt(); // 应该被中断
    }

    public void test(Thread thread) throws InterruptedException {
        System.out.println(Thread.currentThread().getName() + "， 想获取锁");
        //lock.lock();
        lock.lockInterruptibly();   //注意，如果需要正确中断等待锁的线程，必须将获取锁放在外面，然后将InterruptedException抛出
        try {
            System.out.println(thread.getName() + "~~运行了~~");
            Thread.sleep(10000); // 抢到锁，10秒不释放
        } finally {
            System.out.println(Thread.currentThread().getName() + "执行finally");
            lock.unlock();
            System.out.println(thread.getName() + "释放了锁");
        }
    }

}
