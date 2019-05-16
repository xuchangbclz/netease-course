package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.线程安全问题.线程安全原子操作.ABA;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * 解决ABA问题的栈
 **/
public class ConcurrentStack {

    AtomicStampedReference<Node> top = new AtomicStampedReference<Node>(null,0);
    public void push(Node node){
        Node oldTop;
        int v;
        do{
            v=top.getStamp();
            oldTop = top.getReference();
            node.next = oldTop;
        }
        while(!top.compareAndSet(oldTop, node,v,v+1));
        //   }while(!top.compareAndSet(oldTop, node,top.getStamp(),top.getStamp()+1));
    }
    public Node pop(int time){
        Node newTop;
        Node oldTop;
        int v;
        do{
            v=top.getStamp();
            oldTop = top.getReference();
            if(oldTop == null){
                return null;
            }
            newTop = oldTop.next;
            try {
                TimeUnit.SECONDS.sleep(time);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        while(!top.compareAndSet(oldTop, newTop,v,v+1));
        //   }while(!top.compareAndSet(oldTop, newTop,top.getStamp(),top.getStamp()));
        return oldTop;
    }
    public void get(){
        Node node = top.getReference();
        while(node!=null){
            System.out.println(node.item);
            node = node.next;
        }
    }

}
