package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.client;

import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.client.net.NetClient;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.common.protocol.MessageProtocol;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.common.protocol.Request;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.common.protocol.Response;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery.ServiceInfo;
import com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery.ServiceInfoDiscoverer;
import lombok.Data;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * 客户端代理类
 */
@Data
public class ClientStubProxyFactory {

	/** 服务信息发现者 */
	private ServiceInfoDiscoverer serviceInfoDiscoverer;

	private Map<String, MessageProtocol> supportMessageProtocols;

	private NetClient netClient;

	private Map<Class<?>, Object> objectCache = new HashMap<>();

	/**
	 * 获取代理对象
	 */
	public <T> T getProxy(Class<T> interf) {
		T obj = (T) this.objectCache.get(interf);
		if (obj == null) {
			obj = (T) Proxy.newProxyInstance(interf.getClassLoader(), new Class<?>[] { interf },
					new ClientStubInvocationHandler(interf));
			this.objectCache.put(interf, obj);
		}

		return obj;
	}

	@Data
	private class ClientStubInvocationHandler implements InvocationHandler {
		private Class<?> interf;

		private Random random = new Random();

		public ClientStubInvocationHandler(Class<?> interf) {
			super();
			this.interf = interf;
		}

		/**
		 * 当调用代理对象的方法时，会进入到invoke()方法
		 */
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

			if (method.getName().equals("toString")) {
				return proxy.getClass().toString();
			}

			if (method.getName().equals("hashCode")) {
				return 0;
			}

			// 1、获得服务信息
			String serviceName = this.interf.getName();
			List<ServiceInfo> serviceInfos = serviceInfoDiscoverer.getServiceInfo(serviceName);

			if (serviceInfos == null || serviceInfos.size() == 0) {
				throw new Exception("远程服务不存在！");
			}

			// 随机选择一个服务提供者（软负载均衡）
			ServiceInfo serviceInfo = serviceInfos.get(random.nextInt(serviceInfos.size()));

			// 2、构造request对象
			Request req = new Request();
			req.setServiceName(serviceInfo.getName());
			req.setMethod(method.getName());
			req.setPrameterTypes(method.getParameterTypes());
			req.setParameters(args);

			// 3、协议层编组
			// 获得该方法对应的协议
			MessageProtocol protocol = supportMessageProtocols.get(serviceInfo.getProtocol());
			// 编组请求
			byte[] requestData = protocol.marshallingRequest(req);

			// 4、调用网络层发送请求
			byte[] responseData = netClient.sendRequest(requestData, serviceInfo);

			// 5.解组响应消息
			Response response = protocol.unmarshallingResponse(responseData);

			// 6、结果处理
			if (response.getException() != null) {
				throw response.getException();
			}

			return response.getReturnValue();
		}
	}
}
