package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.锁和Synchronized关键字;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.LockSupport;

/**
 * 自定义lock,模拟ReentrantLock,独享锁-- 资源只能被一个线程占有
 **/
public class CustomizeLock {

    // 锁的拥有者
    AtomicReference<Thread> owner = new AtomicReference<>(); // 独享锁 -- 资源只能被一个线程占有
    // 需要锁池
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    public void lock() { // 没拿到锁的线程运行这个方法
        // TODO 拿到锁，等待
        waiters.add(Thread.currentThread());
        // CAS -- 此处直接CAS，是一种非公平的实现
        while (!owner.compareAndSet(null, Thread.currentThread())) {
            LockSupport.park(); // 挂起，等待被唤醒...
        }
        waiters.remove(Thread.currentThread());
    }

    public void unlock() { // 拿到锁的线程运行这个方法
        if (owner.compareAndSet(Thread.currentThread(), null)) {
            // 释放锁之后，要唤醒线程(所有 -- 惊群效应)
            for (Thread waiter : waiters) {
                LockSupport.unpark(waiter);
            }
        }
    }


}
