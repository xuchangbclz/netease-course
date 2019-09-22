package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery;

import java.util.List;

public interface ServiceInfoDiscoverer {

	/**
	 * 通过服务名获取服务信息
	 */
	List<ServiceInfo> getServiceInfo(String serviceName);
}
