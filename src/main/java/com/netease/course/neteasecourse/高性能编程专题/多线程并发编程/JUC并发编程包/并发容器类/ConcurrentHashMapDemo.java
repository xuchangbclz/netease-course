package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.并发容器类;

import java.util.concurrent.ConcurrentHashMap;

/**
 * ConcurrentHashMap数据结构【JDK1.7】
 *    1.Segment[]数组，Segment类实现了ReentrantLock接口，同时Segment类内部又维护了一个Entry<K,V>[]数组
 *      public class Segment implement ReentrantLock {
 *          Entry<K,V>[] table;
 *      }
 *      public class Entry<K,V> {
 *          int hash;
 *          K k;
 *          V value;
 *          Entry<K,V> next;
 *      }
 *      可以认为Segment=锁+HashMap结构
 *
 *     2.并发等级ConcurrentLevel=16，表明ConcurrentHashMap中Segment[]数组size=16,16把锁。因为存在分段锁机制，
 *       保证了多线程下并发安全性。如果依然存在并发性能瓶颈，可以提高ConcurrentLevel，一旦初始化被设定，就不能更改。
 *
 *     3.Put操作时，先根据key取hash值，然后计算出index=Segment[i],应该存放在Segment[]数组的那个索引位置下，由于存在
 *       锁机制，只有获取到锁之后，才能进行后续的Entry<K,V>[]数组操作,后续Put操作与HashMap类似。
 *
 *
 * ConcurrentHashMap数据结构【JDK1.8】
 *     1.底层数据结构与HashMap【JDK1.8】一致，但是增加了CAS无锁操作
 *
 *     2.Put操作时，先根据hash(key)得到Node<K, V>[i]位置，此时存在CAS无锁操作，当Node<K, V>[i]位置不存在元素时，直接插入，
 *       当已存在元素时，才会使用synchronized关键字加锁，锁更加细粒度，只会针对某个Node<K, V>[i]有元素冲突时加锁。
 *
 *     3.相比于1.7版本，只要插入元素时，都需要获取Segment锁。1.8版本使用的CAS无锁操作，性能进一步提升。
 *
 *
 **/
public class ConcurrentHashMapDemo {

    public static void main(String[] args) {
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();
    }

}
