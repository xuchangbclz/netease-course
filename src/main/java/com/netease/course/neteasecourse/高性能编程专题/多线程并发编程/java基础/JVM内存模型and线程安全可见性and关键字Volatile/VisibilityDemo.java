package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.JVM内存模型and线程安全可见性and关键字Volatile;

/**
 * 在多线程读写共享变量时，会出现变量可见性问题
 *
 * 原因：1.工作内存与主内存缓存机制
 *      2.指令重排序，本意是对java代码在运行期的优化处理，但是会带来多线程下变量不可见问题
 *
 * 根据JMM同步原则和happens-before原则
 * 解决：1.volatile关键字，保证没有缓存，共享变量对其他线程立马可见
 *      2.加锁，单线程运行，不会出现变量可见性问题
 *
 **/
public class VisibilityDemo {
}
