package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register;


import cn.hutool.json.JSONUtil;
import com.alibaba.fastjson.JSON;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery.ServiceInfo;
import org.I0Itec.zkclient.ZkClient;

import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLEncoder;

/**
 * Zookeeper方式获取远程服务信息类。
 * 
 * ZookeeperServiceInfoDiscoverer
 */
public class ZookeeperExportServiceRegister extends DefaultServiceRegister implements ServiceRegister {

	private ZkClient client;

	private String centerRootPath = "/Rpc-framework";

	public ZookeeperExportServiceRegister() {
		String addr = "10.5.28.136:2181";
		client = new ZkClient(addr);
		client.setZkSerializer(new MyZkSerializer());
	}

	@Override
	public void register(ServiceObject so, String protocolName, int port) throws Exception {
		super.register(so, protocolName, port);
		ServiceInfo serviceInfo = new ServiceInfo();

		String host = InetAddress.getLocalHost().getHostAddress();
		String address = host + ":" + port;
		serviceInfo.setAddress(address);
		serviceInfo.setName(so.getInterf().getName());
		serviceInfo.setProtocol(protocolName);
		this.exportService(serviceInfo);

	}

	private void exportService(ServiceInfo serviceInfo) {
		String serviceName = serviceInfo.getName();
		String uri = JSONUtil.toJsonStr(serviceInfo);
		try {
			uri = URLEncoder.encode(uri, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		String servicePath = centerRootPath + "/" + serviceName + "/service";
		if (!client.exists(servicePath)) {
			client.createPersistent(servicePath, true);
		}
		String uriPath = servicePath + "/" + uri;
		if (client.exists(uriPath)) {
			client.delete(uriPath);
		}
		client.createEphemeral(uriPath);
	}
}
