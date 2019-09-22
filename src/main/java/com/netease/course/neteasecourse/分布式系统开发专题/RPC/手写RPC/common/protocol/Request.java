package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.common.protocol;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class Request implements Serializable {

	private static final long serialVersionUID = -5200571424236772650L;

	private String serviceName;

	private String method;

	private Map<String, String> headers = new HashMap<String, String>();

	private Class<?>[] prameterTypes;

	private Object[] parameters;

}
