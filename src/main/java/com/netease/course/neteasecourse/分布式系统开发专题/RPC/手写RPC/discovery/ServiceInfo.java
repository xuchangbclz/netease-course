package com.netease.course.neteasecourse.分布式系统开发专题.RPC.手写RPC.discovery;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

@Data
@Accessors(chain = true)
public class ServiceInfo implements Serializable {

	private String name;

	private String protocol;

	private String address;

}
