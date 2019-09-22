package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.demo.provider;

import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.demo.DemoService;


public class DemoServiceImpl implements DemoService {

	@Override
	public String sayHello(String name) {
		return "Hello " + name;
	}
}
