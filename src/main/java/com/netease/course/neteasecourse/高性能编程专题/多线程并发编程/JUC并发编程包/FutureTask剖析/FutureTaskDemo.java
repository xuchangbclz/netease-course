package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.FutureTask剖析;

import java.util.concurrent.*;
import java.util.concurrent.locks.LockSupport;

/**
 * submit()提交时，线程池最终会封装成FutureTask执行
 *
 * 了解FutureTask源码后，手写FutureTask
 *
 * 应用场景：当有多线程异步执行逻辑时，使用FutureTask获取异步执行结果
 *
 **/
public class FutureTaskDemo<T> implements Runnable {
    //public static void main(String[] args) {
    //    ExecutorService executorService = Executors.newSingleThreadExecutor();
    //    Future future = executorService.submit(() -> {});
    //}

    Callable<T> callable; //  业务逻辑在callable里面
    T result = null;
    volatile String state = "NEW";  // task执行状态
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();// 定义一个存储等待者的集合

    public FutureTaskDemo(Callable<T> callable) {
        this.callable = callable;
    }

    @Override
    public void run() {
        try {
            result = callable.call();
        } catch (Exception e) {
            e.printStackTrace();
            // result = exception
        } finally {
            state = "END";
        }

        // 唤醒等待者
        Thread waiter = waiters.poll();
        while (waiter != null) {
            LockSupport.unpark(waiter);

            waiter = waiters.poll(); // 继续取出队列中的等待者
        }
    }

    // 返回结果,
    public T get() {
        if ("END".equals(state)) {
            return result;
        }

        waiters.offer(Thread.currentThread()); // 加入到等待队列,线程不继续往下执行

        while (!"END".equals(state)) {
            LockSupport.park(); // 线程通信的知识点
        }
        // 如果没有结束,那么调用get方法的线程,就应该进入等待
        return result;
    }

}
