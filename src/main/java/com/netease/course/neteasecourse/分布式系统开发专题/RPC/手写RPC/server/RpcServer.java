package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server;

import lombok.Data;

@Data
public abstract class RpcServer {

	protected int port;

	protected String protocol;

	protected RequestHandler handler;

	public RpcServer(int port, String protocol, RequestHandler handler) {
		super();
		this.port = port;
		this.protocol = protocol;
		this.handler = handler;
	}

	/**
	 * 开启服务
	 */
	public abstract void start();

	/**
	 * 停止服务
	 */
	public abstract void stop();

}
