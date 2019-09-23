package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.protocol;

public interface MessageProtocol {

	/**
	 * 编组请求
	 */
	byte[] marshallingRequest(Request req) throws Exception;

	/**
	 * 解组请求
	 */
	Request unmarshallingRequest(byte[] data) throws Exception;

	byte[] marshallingResponse(Response rsp) throws Exception;

	Response unmarshallingResponse(byte[] data) throws Exception;
}
