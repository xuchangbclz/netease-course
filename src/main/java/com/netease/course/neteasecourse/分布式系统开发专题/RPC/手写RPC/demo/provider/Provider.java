package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.demo.provider;


import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.protocol.JavaSerializeMessageProtocol;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.demo.DemoService;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.NettyRpcServer;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.RequestHandler;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.RpcServer;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register.ServiceObject;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register.ServiceRegister;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register.ZookeeperExportServiceRegister;

public class Provider {
	public static void main(String[] args) throws Exception {

		int port = 19000;
		String protocol = "javas";

		// 服务注册
		ServiceRegister serviceRegister = new ZookeeperExportServiceRegister();
		DemoService demoService = new DemoServiceImpl();
		ServiceObject so = new ServiceObject(DemoService.class.getName(), DemoService.class, demoService);
		serviceRegister.register(so, protocol, port);

		RequestHandler reqHandler = new RequestHandler(new JavaSerializeMessageProtocol(), serviceRegister);

		RpcServer server = new NettyRpcServer(port, protocol, reqHandler);
		server.start();
		System.in.read(); // 按任意键退出
		server.stop();
	}
}
