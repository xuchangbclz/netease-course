package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server;

import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.protocol.MessageProtocol;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.protocol.Request;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.protocol.Response;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.protocol.Status;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register.ServiceObject;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.server.register.ServiceRegister;
import lombok.Data;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Data
public class RequestHandler {
	private MessageProtocol protocol;

	private ServiceRegister serviceRegister;

	public RequestHandler(MessageProtocol protocol, ServiceRegister serviceRegister) {
		super();
		this.protocol = protocol;
		this.serviceRegister = serviceRegister;
	}

	public byte[] handleRequest(byte[] data) throws Exception {
		// 1、解组消息
		Request req = this.protocol.unmarshallingRequest(data);

		// 2、查找服务对象
		ServiceObject serviceObject = this.serviceRegister.getServiceObject(req.getServiceName());

		Response response = null;

		if (serviceObject == null) {
			response = new Response(Status.NOT_FOUND);
		} else {
			// 3、反射调用对应的过程方法
			try {
				Method method = serviceObject.getInterf().getMethod(req.getMethod(), req.getPrameterTypes());
				Object returnValue = method.invoke(serviceObject.getObj(), req.getParameters());
				response = new Response(Status.SUCCESS);
				response.setReturnValue(returnValue);
			} catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException
					| InvocationTargetException e) {
				response = new Response(Status.ERROR);
				response.setException(e);
			}
		}

		// 4、编组响应消息
		return this.protocol.marshallingResponse(response);
	}

}
