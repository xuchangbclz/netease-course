package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.垃圾回收机制;

import java.util.concurrent.TimeUnit;

/**
 * GC 垃圾回收机制
 * 1.GC通过可达性分析算法，判断对象是否需要被回收。可达性分析：通过GC Root查询对象引用链路，对象引用链路不可达的对象，就会被GC回收
 * 2.回收算法
 *  2.1 标记-清除。存在内存碎片化问题
 *  2.2 复制。需要两块内存大小一模一样的内存区域s0,s1，回收时把s0中存活的对象复制到s1中，然后清空s0,当s1内存不足时，又把s1中存活的对象复制到s0,
 *      然后清空s1。存在浪费内存问题
 *  2.3 标记-整理，在2.1的基础上，对清空之后的内存进行整理，保证连续的内存空间
 *  2.4 分代收集【主流】。
 *      新生代--eden,s0,s1。内存比例1:1.8。新生代采用复制算法回收，当回收次数达到阈值时依然存活的对象移入老年代，针对大的对象直接放入老年代
 *      老年代--tenured。老年代采用标记-整理算法
 *
 * FullGC----stop the world
 *  频繁的FullGC会出现卡顿现象,很多人都会建议的规避System.gc()带来的FullGC风险  -XX:+DisableExplicitGC 禁止程序显示调用gc方法
 *
 **/
public class GC {

    //-Xms512m -Xmx512m -verbose:gc -XX:+PrintGCDetails
    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 1000; i++) {
            byte[] bytes = new byte[1024 * 1024 * 256]; //256M
            TimeUnit.SECONDS.sleep(1L);
        }
    }
}
