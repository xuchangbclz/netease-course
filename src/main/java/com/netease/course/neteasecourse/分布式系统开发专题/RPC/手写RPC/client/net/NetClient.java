package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.client.net;


import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery.ServiceInfo;

/**
 * 网络层，发送请求数据，得到相应数据
 */
public interface NetClient {
	byte[] sendRequest(byte[] data, ServiceInfo serviceInfo) throws Throwable;
}
