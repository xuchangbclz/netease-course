package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.并发容器类;

import java.util.HashSet;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Set去重机制：
 *  1.HashSet去重，【线程不安全】，HashSet底层由HashMap实现
 *      public boolean add(E var1) {
 *         return this.map.put(var1, PRESENT) == null;
 *     }
 *     HashSet去重其实是依赖HashMap的key唯一性机制
 *
 *  2. CopyOnWriteArraySet去重，【线程安全】，底层依赖CopyOnWriteArrayList。
 *      public boolean add(E var1) {
 *         return this.al.addIfAbsent(var1);
 *     }
 *
 *  3.ConcurrentSkipListSet去重，【线程安全】，底层依赖ConcurrentHashMap。
 *      public boolean add(E var1) {
 *         return this.m.putIfAbsent(var1, Boolean.TRUE) == null;
 *     }
 *
 *
 **/
public class Set去重机制 {

    public static void main(String[] args) {
        HashSet hashSet = new HashSet();
        CopyOnWriteArraySet copyOnWriteArraySet = new CopyOnWriteArraySet();
        ConcurrentSkipListSet concurrentSkipListSet = new ConcurrentSkipListSet();
    }
}
