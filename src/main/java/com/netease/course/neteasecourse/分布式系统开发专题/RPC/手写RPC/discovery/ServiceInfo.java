package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery;

import lombok.Data;

@Data
public class ServiceInfo {

	private String name;

	private String protocol;

	private String address;

}
