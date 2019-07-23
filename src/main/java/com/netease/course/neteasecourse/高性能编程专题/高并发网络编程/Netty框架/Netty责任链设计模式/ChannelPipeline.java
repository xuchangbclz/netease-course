package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty框架.Netty责任链设计模式;

/**
 *  -----链表形式调用------netty就是类似的这种形式责任链
 **/
public class ChannelPipeline {

    /**
     * 初始化的时候造一个head，作为责任链的开始，但是并没有具体的处理
     */
    public ChannelHandlerContext head = new ChannelHandlerContext(new AbstractHandler() {
        @Override
        void doHandler(ChannelHandlerContext channelHandlerContext, Object arg0) {
            channelHandlerContext.runNext(arg0);
        }
    });

    public void requestProcess(Object arg0) {
        this.head.handler(arg0);
    }

    /**
     * 维护链表关系
     * @param handler
     */
    public void addLast(AbstractHandler handler) {
        ChannelHandlerContext context = head;
        while (context.next != null) {
            context = context.next;
        }
        context.next = new ChannelHandlerContext(handler);
    }


    public static void main(String[] args) {
        ChannelPipeline channelPipeline = new ChannelPipeline();
        channelPipeline.addLast(new OutBoundHandler());
        channelPipeline.addLast(new InBoundHandler());
        channelPipeline.addLast(new InBoundHandler());
        channelPipeline.addLast(new OutBoundHandler());

        // 发起请求
        channelPipeline.requestProcess("火车呜呜呜~~");

    }

}

/**
 * handler上下文，主要负责维护链，和链的执行
 */
class ChannelHandlerContext {
    ChannelHandlerContext next; // 下一个节点
    AbstractHandler handler;

    public ChannelHandlerContext(AbstractHandler handler) {
        this.handler = handler;
    }

    void handler(Object arg0) {
        this.handler.doHandler(this, arg0);
    }

    /**
     * 继续执行下一个
     */
    void runNext(Object arg0) {
        if (this.next != null) {
            this.next.handler(arg0);
        }
    }
}

// 处理器抽象类
abstract class AbstractHandler {
    /**
     * 处理器，这个处理器就做一件事情，在传入的字符串中增加一个尾巴..
     */
    abstract void doHandler(ChannelHandlerContext channelHandlerContext, Object arg0); // handler方法
}

// 处理器具体实现类
class InBoundHandler extends AbstractHandler {
    @Override
    void doHandler(ChannelHandlerContext channelHandlerContext, Object arg0) {
        arg0 = arg0.toString() + "..handler1的小尾巴.....";
        System.out.println("我是Handler1的实例，我在处理：" + arg0);
        // 继续执行下一个
        channelHandlerContext.runNext(arg0);
    }
}

// 处理器具体实现类
class OutBoundHandler extends AbstractHandler {
    @Override
    void doHandler(ChannelHandlerContext channelHandlerContext, Object arg0) {
        arg0 = arg0.toString() + "..handler2的小尾巴.....";
        System.out.println("我是Handler2的实例，我在处理：" + arg0);
        // 继续执行下一个
        channelHandlerContext.runNext(arg0);
    }
}

