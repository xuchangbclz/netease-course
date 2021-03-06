package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.线程安全原子操作.CAS;


import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 两个线程，对 i 变量进行递增操作
 */
public class LockAtomicDemo {

    // 记录操作次数（统计代码性能指标、大数据处理的过程、多线程运算操作）
    volatile AtomicInteger i = new AtomicInteger();

    public void add() { // 方法栈帧~ 局部变量
        // i++; // 三次操作,字节码太难懂
        i.incrementAndGet();
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        LockAtomicDemo ld = new LockAtomicDemo();

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
