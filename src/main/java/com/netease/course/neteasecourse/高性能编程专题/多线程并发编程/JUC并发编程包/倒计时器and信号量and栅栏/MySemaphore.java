package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.倒计时器and信号量and栅栏;

import java.util.Random;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.LockSupport;

/**
 * 自定义Semaphore，共享锁
 **/
public class MySemaphore {

    AtomicInteger count = null;
    // 需要锁池
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    public MySemaphore(int num) {
        this.count = new AtomicInteger(num); // 令牌数量 数值
    }

    public void acquire() { // 获取令牌,没有令牌就等待
        // 进入等待列表
        waiters.add(Thread.currentThread());
        for (; ; ) {
            int current = count.get();
            int n = current - 1; // 发出一个令牌
            if (current <= 0 || n < 0) {
                // 挂起线程
                LockSupport.park();
            }
            if (count.compareAndSet(current, n)) {
                break;
            }
        }
        waiters.remove(Thread.currentThread());
    }

    public void release() { // 释放令牌 -- 令牌数量+1
        if (this.count.incrementAndGet() > 0) {
            // 释放锁之后，要唤醒其他等待的线程--惊群效应
            for (Thread waiter : waiters) {
                LockSupport.unpark(waiter);
            }
        }
    }

    public static void main(String[] args) {
        int N = 9;            // 客人数量
        MySemaphore semaphore = new MySemaphore(5); // 手牌数量，限制请求数量
        for (int i = 0; i < N; i++) {
            String vipNo = "vip-00" + i;
            new Thread(() -> {
                try {
                    semaphore.acquire(); // 获取令牌,没拿到的就等
                    //System.out.println(semaphore.count);
                    semaphore.service(vipNo); // 实现了怼service方法的限流

                    semaphore.release(); // 释放令牌,令牌数+1
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }).start();
        }
    }

    // 限流 控制5个线程 同时访问
    public void service(String vipNo) throws InterruptedException {
        System.out.println("楼上出来迎接贵宾一位，贵宾编号" + vipNo + "，...");
        Thread.sleep(new Random().nextInt(3000));
        System.out.println("欢送贵宾出门，贵宾编号" + vipNo);
    }


}
