package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.倒计时器and信号量and栅栏;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 同步锁本质：
 *      1.同步方式：独享锁，共享锁
 *      2.抢锁方式：公平，非公平
 *      3.没抢到锁处理：循环尝试，阻塞等待
 *      4.唤醒阻塞线程方式：全部唤醒，唤醒下一个
 *
 * 自定义CountDownLatch，共享锁
 **/
public class MyCountDownLatch {

    AtomicInteger count;
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    public MyCountDownLatch(int num) {
        this.count = new AtomicInteger(num);
    }

    public void await() {
        // 进入等待列表
        waiters.add(Thread.currentThread());
        while (this.count.get() != 0) {
            // 挂起线程
            LockSupport.park();
        }
        waiters.remove(Thread.currentThread());
    }

    public void countDown() {
        if (this.count.decrementAndGet() == 0) {
            for (Thread waiter : waiters) {
                LockSupport.unpark(waiter); // 唤醒全部 抢锁
                //Thread waiter = waiters.peek();
            }
        }
    }


    public static void main(String[] args) throws InterruptedException {
        // 一个请求，后台需要调用多个接口 查询数据
        MyCountDownLatch cdLdemo = new MyCountDownLatch(10); // 创建，计数数值
        for (int i = 0; i < 10; i++) { // 启动九个线程，最后一个两秒后启动
            int finalI = i;
            new Thread(() -> {
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("我是" + Thread.currentThread() + ".我执行接口-" + finalI + "调用了");
                cdLdemo.countDown(); // 参与计数
                // 不影响后续操作
            }).start();
        }

        cdLdemo.await(); // 等待计数器为0
        System.out.println("全部执行完毕.我来召唤神龙");
    }

}
