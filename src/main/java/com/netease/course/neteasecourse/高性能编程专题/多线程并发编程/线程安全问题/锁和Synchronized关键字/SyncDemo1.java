package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.锁和Synchronized关键字;

/**
 * 偏向锁->轻量级锁->重量级锁
 * 1.当定义了synchronized锁时，当不存在多线程竞争时，采用偏向锁
 * 2.当定义了synchronized锁时，当存在多线程竞争时，采用轻量级锁（CAS修改状态）
 * 3.当CAS自旋一定次数之后，升级为重量级锁
 *
 */
public class SyncDemo1 {

    static Object temp = new Object();

    public void test1() {
        synchronized (this) { // synchronized:类锁(class对象，静态方法)。实例锁(this，普通方法)锁的对象是类的一个实例
            try {
                System.out.println(Thread.currentThread() + " 我开始执行");
                Thread.sleep(3000L);
                System.out.println(Thread.currentThread() + " 我执行结束");
            } catch (InterruptedException e) {
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new Thread(() -> {
            new SyncDemo1().test1();
        }).start();

        Thread.sleep(1000L); // 等1秒钟,让前一个线程启动起来
        new Thread(() -> {
            new SyncDemo1().test1();
        }).start();
    }

}
