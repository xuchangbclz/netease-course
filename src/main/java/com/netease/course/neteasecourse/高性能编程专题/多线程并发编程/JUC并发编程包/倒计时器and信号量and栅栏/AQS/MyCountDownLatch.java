package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.倒计时器and信号量and栅栏.AQS;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * 通过AQS实现CountDownLatch
 **/
public class MyCountDownLatch {

    // AQS 具体实现对象（state、queue、owner）
    MyAQS aqSdemo = new MyAQS() {
        @Override
        public int tryAcquireShared() { // 如果非等于0，代表当前还有线程没准备就绪，则认为需要等待
            return this.getState().get() == 0 ? 1 : -1;
        }

        @Override
        public boolean tryReleaseShared() { // 如果非等于0，代表当前还有线程没准备就绪，则不会通知继续执行
            return this.getState().decrementAndGet() == 0;
        }
    };

    public MyCountDownLatch(int count) {
        aqSdemo.setState(new AtomicInteger(count));
    }

    public void await() {
        aqSdemo.acquireShared();
    }

    public void countDown() {
        aqSdemo.releaseShared();
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
                System.out.println("我是" + Thread.currentThread() + ".我执行接口-" + finalI +"调用了");
                cdLdemo.countDown(); // 参与计数
                // 不影响后续操作
            }).start();
        }

        cdLdemo.await(); // 等待计数器为0
        System.out.println("全部执行完毕.我来召唤神龙");
    }

}
