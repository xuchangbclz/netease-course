package com.netease.course.neteasecourse.分布式系统开发专题.zookeeper.应用场景;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;

/**
 * watch机制监控zookeeper节点数据变化
 */
public class ZkClientWatchDemo {

	public static void main(String[] args) {
		// 创建一个zk客户端
		ZkClient client = new ZkClient("10.5.28.136:2181");
		client.setZkSerializer(new MyZkSerializer());

		client.subscribeDataChanges("/daituo", new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
				System.out.println("----收到节点被删除了-------------");
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("----收到节点数据变化：" + data + "-------------");
			}
		});

		try {
			Thread.sleep(1000 * 60 * 10);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
