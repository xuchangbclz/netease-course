package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.并发容器类.Queue;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.*;

/**
 * ArrayBlockingQueue:它是基于数组的阻塞循环队列， 此队列按 FIFO（先进先出）原则对元素进行排序
 *
 * LinkedBlockingQueue:它是基于链表的队列，此队列按 FIFO（先进先出）排序元素。如果有阻塞需求，用这个，因为链表的扩容非常简单，
 *       只需要在尾部追加元素即可，而数组的扩容逻辑相对负责。应用场景：数据库连接池
 *
 * ConcurrentLinkedQueue:
 *      优势：无锁。
 *      坑： size()方法每次都是遍历整个链表，最好不要频繁调用
 *      如果没有阻塞要求，用这个挺好的
 *      注意：批量操作不提供原子保证  addAll, removeAll, retainAll, containsAll, equals, and toArray
 *
 * PriorityQueue：
 *      它是基于数组的队列,线程不安全
 *      是一个带优先级的队列，而不是先进先出队列，元素按优先级顺序被移除，该队列也没有上限
 *      没有容量限制的，自动扩容，虽然此队列逻辑上是无界的，但是由于资源被耗尽，所以试图执行添加操作可能会导致 OutOfMemoryError
 *      入该队列中的元素要具有比较能力
 *
 * PriorityBlockingQueue：
 *      它是基于数组的队列,线程安全
 *
 * DelayQueue：
 *
 * SynchronousQueue：不存放元素
 *
 * 查看不同Queue的take(),put(),offer(),poll()等方法源码
 *
 **/
public class QueueDemo {

    public static void main(String[] args) {
        // 构造时需要指定容量(量力而行),可以选择是否需要公平（最先进入阻塞的，先操作）
        ArrayBlockingQueue<String> arrayBlockingQueue = new ArrayBlockingQueue<>(3, false);
        // 构造时可以指定容量，默认Integer.MAX_VALUE
        LinkedBlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<String>(3);
        // 不需要指定容量
        ConcurrentLinkedQueue<String> concurrentLinkedQueue = new ConcurrentLinkedQueue<String>();
        // 可以设置比对方式
        PriorityQueue<String> priorityQueue = new PriorityQueue<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                // 实际就是 元素之间的 比对。
                return 0;
            }
        });
        PriorityBlockingQueue<String> priorityBlockingQueue = new PriorityBlockingQueue<>(2);
        SynchronousQueue<String> synchronousQueue = new SynchronousQueue<>();

    }
}
