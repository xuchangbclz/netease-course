package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.并发容器类;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * HashMap数据结构【JDK1.7】：
 *  1.Node<K, V>[] table,主体上由Node数组构成。
 *  2.Node<K, V> {
 *      int hash;
 *      K key;
 *      V value;
 *      Node<K, V> next;
 *      }
 *     Node里包含hash值，key,value和next,表明Node<K, V>其实又是一个链表结构。所以综合---数组+链表
 *
 *  3.Put操作时，先对key求hash唯一值，然后通过算法求出在数组中的索引，根据索引决定Put元素存放在Node<K, V>[]的位置。
 *      如果新Put的元素的hash值求索引没有冲突，则直接在数值索引位置中新建一个Node<K, V>;如果新Put的元素的hash值求索引存在冲突，
 *      表明数组当前位置已经存在Node<K, V>，此时如果新Put的元素与老元素的hash值相同，则直接覆盖老元素，否则新Put的元素会上位，
 *      然后next指向老元素Node<K, V>
 *
 *  4.Get查询时，先对key求hash唯一值，然后通过算法求出在数组中的索引，根据索引决定在Node<K, V>[]数组中哪个索引位置下取元素。
 *      此时可能当前索引位置下存在多个Node<K, V>构成的链表，因此需要遍历Node<K, V>链表找出匹配的元素。
 *
 *  5.扩容机制：加载因子loadFactor=0.75，数组初始容量initialCapacity=16，阈值threshold=loadFactor*initialCapacity=12
 *      扩容时机：1.当数组某个索引位置下的Node<K, V>链表的size>=threshold,发生扩容（2倍Capacity），因为如果链表长度太长，
 *                  影响查询效率，违背了hash数据结构的初衷。
 *      扩容判断代码：
 *          if(size >= threshold && table[i] != null) {
 *              resize();
 *          }
 *          好好理解扩容时机,需要同时满足两个条件，才会扩容。理解在不扩容情况下，HashMap最多存储元素11+16个
 *
 *
 *  HashMap数据结构【JDK1.8】
 *      与1.7区别：1.Node<K, V>链表是尾部追加，next指向新Put的元素，而1.7是头部插入
 *                2.数据结构是【数组+链表|红黑树】，而1.7是【数组+链表】
 *                3.扩容时机:只要size>threshold就会发生扩容（2倍Capacity）
 *                  扩容判断代码：
 *                  if (++size > threshold) {
 *                      resize();
 *                  }
 *                4.链表->红黑树时机：当链表size>8时，会转成红黑树，提高查询效率。
 *
 *
 **/
public class HashMapDemo {

    public static void main(String[] args) {
        HashMap hashMap = new HashMap();
    }
}
