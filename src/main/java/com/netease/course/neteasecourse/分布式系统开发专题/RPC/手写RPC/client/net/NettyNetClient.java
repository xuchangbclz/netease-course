package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.client.net;

import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery.ServiceInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

public class NettyNetClient implements NetClient {

	private static Logger logger = LoggerFactory.getLogger(NettyNetClient.class);

	@Override
	public byte[] sendRequest(byte[] data, ServiceInfo serviceInfo) throws Throwable {

		String[] addInfoArray = serviceInfo.getAddress().split(":");

		SendHandler sendHandler = new SendHandler(data);
		byte[] responseData = null;
		// 配置客户端
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap()
					.group(group)
					.channel(NioSocketChannel.class)
					.option(ChannelOption.TCP_NODELAY, true)
					.handler(new ChannelInitializer<SocketChannel>() {
						@Override
						public void initChannel(SocketChannel ch) throws Exception {
							ChannelPipeline p = ch.pipeline();
							p.addLast(sendHandler);
						}
					});

			// 启动客户端连接
			b.connect(addInfoArray[0], Integer.valueOf(addInfoArray[1])).sync();
			responseData = (byte[]) sendHandler.getResponseData();
			logger.info("sendRequest get reply: " + responseData);

		} finally {
			// 释放线程组资源
			group.shutdownGracefully();
		}
		return responseData;
	}

	private class SendHandler extends ChannelInboundHandlerAdapter {

		private CountDownLatch cdl = null;
		private Object readMsg = null;
		private byte[] data;

		public SendHandler(byte[] data) {
			cdl = new CountDownLatch(1);
			this.data = data;
		}

		@Override
		public void channelActive(ChannelHandlerContext ctx) throws Exception {
			logger.info("连接服务端成功：" + ctx);
			ByteBuf reqBuf = Unpooled.buffer(data.length);
			reqBuf.writeBytes(data);
			logger.info("客户端发送消息：" + reqBuf.toString());
			ctx.writeAndFlush(reqBuf);
		}

		public Object getResponseData() {
			try {
				cdl.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			return readMsg;
		}

		@Override
		public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
			logger.info("client read msg: " + msg.toString());
			ByteBuf msgBuf = (ByteBuf) msg;
			byte[] resp = new byte[msgBuf.readableBytes()];
			msgBuf.readBytes(resp);
			readMsg = resp;
			cdl.countDown();
		}

		@Override
		public void channelReadComplete(ChannelHandlerContext ctx) {
			ctx.flush();
		}

		@Override
		public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
			// Close the connection when an exception is raised.
			cause.printStackTrace();
			logger.error("发生异常：" + cause.getMessage());
			ctx.close();
		}
	}
}
