package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register;

public interface ServiceRegister {

	void register(ServiceObject so, String protocol, int port) throws Exception;

	ServiceObject getServiceObject(String name) throws Exception;
}
