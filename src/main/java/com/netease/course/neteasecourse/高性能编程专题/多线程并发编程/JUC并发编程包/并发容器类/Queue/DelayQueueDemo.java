package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.并发容器类.Queue;

import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

/**
 * 基于PriorityQueue来实现的,是一个存放Delayed元素的无界阻塞队列，
 * 只有在延迟期满时才能从中提取元素。如果延迟都还没有期满，则队列没有头部，并且poll将返回null。
 * 当一个元素的 getDelay(TimeUnit.NANOSECONDS) 方法返回一个小于或等于零的值时，则出现期满，poll就以移除这个元素了。
 * 此队列不允许使用 null元素。
 * 经典应用场景：线程池定时任务调度，但是ScheduledThreadPoolExecutor自己单独实现了一套DelayedWorkQueue
 **/
public class DelayQueueDemo {

    public static void main(String[] args) throws InterruptedException {
        DelayQueue<Message> delayQueue = new DelayQueue<Message>();
        // 这条消息5秒后发送
        Message message = new Message("message - 00001", new Date(System.currentTimeMillis() + 5000L));
        delayQueue.add(message);

        while (true) {
            System.out.println(delayQueue.poll());
            Thread.sleep(1000L);
        }
        // 线程池中的定时调度就是这样实现的
    }
}

// 实现Delayed接口的元素才能存到DelayQueue
class Message implements Delayed {

    String content;
    Date sendTime;

    /**
     * @param content  消息内容
     * @param sendTime 定时发送
     */
    public Message(String content, Date sendTime) {
        this.content = content;
        this.sendTime = sendTime;
    }

    // 判断当前这个元素，是不是已经到了需要被拿出来的时间
    @Override
    public long getDelay(TimeUnit unit) {
        // 默认纳秒
        long duration = sendTime.getTime() - System.currentTimeMillis();
        return TimeUnit.NANOSECONDS.convert(duration, TimeUnit.MILLISECONDS);
    }

    //用于插入Delayed元素后，按照时间优先级排序
    @Override
    public int compareTo(Delayed o) {
        return o.getDelay(TimeUnit.NANOSECONDS) > this.getDelay(TimeUnit.NANOSECONDS) ? 1 : -1;
    }


}

