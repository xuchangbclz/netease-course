package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register.MyZkSerializer;
import org.I0Itec.zkclient.ZkClient;

import com.alibaba.fastjson.JSON;

public class ZookeeperServiceInfoDiscoverer implements ServiceInfoDiscoverer {

	ZkClient client;

	private String centerRootPath = "/Rpc-framework";

	public ZookeeperServiceInfoDiscoverer() {
		client = new ZkClient("127.0.0.1:2181");
		client.setZkSerializer(new MyZkSerializer());
	}

	@Override
	public List<ServiceInfo> getServiceInfo(String name) {
		String servicePath = centerRootPath + "/" + name + "/service";
		List<String> children = client.getChildren(servicePath);
		List<ServiceInfo> resources = new ArrayList<ServiceInfo>();
		for (String ch : children) {
			try {
				String deCh = URLDecoder.decode(ch, "UTF-8");
				ServiceInfo serviceInfo = JSON.parseObject(deCh, ServiceInfo.class);
				resources.add(serviceInfo);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return resources;
	}

}
