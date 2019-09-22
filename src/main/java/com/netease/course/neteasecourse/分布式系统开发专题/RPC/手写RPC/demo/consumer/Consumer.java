package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.demo.consumer;


import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.client.ClientStubProxyFactory;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.client.net.NettyNetClient;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.common.protocol.JavaSerializeMessageProtocol;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.common.protocol.MessageProtocol;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.demo.DemoService;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery.ZookeeperServiceInfoDiscoverer;

import java.util.HashMap;
import java.util.Map;

public class Consumer {
	public static void main(String[] args) throws Exception {

		ClientStubProxyFactory cspf = new ClientStubProxyFactory();
		// 设置服务发现者
		cspf.setServiceInfoDiscoverer(new ZookeeperServiceInfoDiscoverer());

		// 设置支持的协议
		Map<String, MessageProtocol> supportMessageProtocols = new HashMap<>();
		supportMessageProtocols.put("javas", new JavaSerializeMessageProtocol());
		cspf.setSupportMessageProtocols(supportMessageProtocols);

		// 设置网络层实现
		cspf.setNetClient(new NettyNetClient());

		DemoService demoService = cspf.getProxy(DemoService.class); // 获取远程服务代理
		String hello = demoService.sayHello("world"); // 执行远程方法
		System.out.println(hello); // 显示调用结果

	}
}
