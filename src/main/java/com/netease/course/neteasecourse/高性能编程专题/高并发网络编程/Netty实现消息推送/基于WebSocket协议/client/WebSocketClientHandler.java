package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.基于WebSocket协议.client;

import io.netty.channel.*;
import io.netty.handler.codec.http.DefaultHttpHeaders;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.net.InetSocketAddress;
import java.net.URI;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * WebSocketClientHandler 处理多个tcp连接建立之后的事件
 **/
public class WebSocketClientHandler extends SimpleChannelInboundHandler<Object> {

    private WebSocketClientHandshaker handshaker;
    private ChannelPromise handshakeFuture;

    public ChannelFuture handshakeFuture() {
        return handshakeFuture;
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        handshakeFuture = ctx.newPromise();
    }

    static AtomicInteger counter = new AtomicInteger(0);

    /**
     * TCP 连接建立之后触发
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        if (handshaker == null) {
            InetSocketAddress address = (InetSocketAddress) ctx.channel().remoteAddress();
            URI uri = null;
            try {
                //浏览器协议转换，从HTTP协议->WebSocket协议
                uri = new URI("ws://" + address.getHostString() + ":" + address.getPort() + "/websocket?userId=" + counter.incrementAndGet());
            } catch (Exception e) {
                e.printStackTrace();
            }
            handshaker = WebSocketClientHandshakerFactory.newHandshaker(
                    uri, WebSocketVersion.V13, null, true, new DefaultHttpHeaders());
        }
        handshaker.handshake(ctx.channel());
    }

    /**
     * TCP 连接关闭之后触发
     */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) {
        if ("true".equals(System.getProperty("netease.debug"))) {
            System.out.println("WebSocket Client disconnected!");
        }
    }

    /**
     * 客户端收到服务端数据后触发
     */
    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        Channel ch = ctx.channel();
        if (!handshaker.isHandshakeComplete()) {
            try {
                handshaker.finishHandshake(ch, (FullHttpResponse) msg);
                if ("true".equals(System.getProperty("netease.debug"))) {
                    System.out.println("WebSocket Client connected!");
                }
                handshakeFuture.setSuccess();
            } catch (WebSocketHandshakeException e) {
                if ("true".equals(System.getProperty("netease.debug"))) {
                    System.out.println("WebSocket Client failed to connect");
                }
                handshakeFuture.setFailure(e);
            }
            return;
        }

        //如果收到服务端msg属于Http响应
        if (msg instanceof FullHttpResponse) {
            FullHttpResponse response = (FullHttpResponse) msg;
            throw new IllegalStateException(
                    "Unexpected FullHttpResponse (getStatus=" + response.status() +
                            ", content=" + response.content().toString(CharsetUtil.UTF_8) + ')');
        }

        //如果收到服务端msg属于WebSocket响应
        WebSocketFrame frame = (WebSocketFrame) msg;
        if (frame instanceof TextWebSocketFrame) {
            TextWebSocketFrame textFrame = (TextWebSocketFrame) frame;
            if ("true".equals(System.getProperty("netease.debug"))) {
                System.out.println("WebSocket Client received message: " + textFrame.text());
            }
        } else if (frame instanceof PongWebSocketFrame) {
            if ("true".equals(System.getProperty("netease.debug"))) {
                System.out.println("WebSocket Client received pong");
            }
        } else if (frame instanceof CloseWebSocketFrame) {
            if ("true".equals(System.getProperty("netease.debug"))) {
                System.out.println("WebSocket Client received closing");
            }
            ch.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        if (!handshakeFuture.isDone()) {
            handshakeFuture.setFailure(cause);
        }
        ctx.close();
    }


}
