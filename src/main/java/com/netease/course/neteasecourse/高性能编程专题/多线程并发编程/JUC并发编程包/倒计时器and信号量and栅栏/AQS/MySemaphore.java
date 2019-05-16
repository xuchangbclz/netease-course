package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.JUC并发编程包.倒计时器and信号量and栅栏.AQS;

/**
 * 通过AQS实现Semaphore
 **/
public class MySemaphore {

    MyAQS aqs = new MyAQS() {
        @Override
        public int tryAcquireShared() { // 信号量获取， 数量 - 1
            for(;;) {
                int count =  getState().get();
                int n = count - 1;
                if(count <= 0 || n < 0) {
                    return -1;
                }
                if(getState().compareAndSet(count, n)) {
                    return 1;
                }
            }
        }

        @Override
        public boolean tryReleaseShared() { // state + 1
            return getState().incrementAndGet() >= 0;
        }
    };

    /** 许可数量 */
    public MySemaphore(int count) {
        aqs.getState().set(count); // 设置资源的状态
    }

    public void acquire() {
        aqs.acquireShared();
    } // 获取令牌

    public void release() {
        aqs.releaseShared();
    } // 释放令牌

}
