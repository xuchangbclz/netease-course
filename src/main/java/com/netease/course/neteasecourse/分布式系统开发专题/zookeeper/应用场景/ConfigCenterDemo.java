package com.netease.course.neteasecourse.分布式系统开发专题.zookeeper.应用场景;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.I0Itec.zkclient.serialize.BytesPushThroughSerializer;

/**
 * 
 * zookeeper实现配置中心 demo
 *
 */
public class ConfigCenterDemo {

	// 1 将单个配置放到zookeeper上
	public void put2Zk() {
		ZkClient client = new ZkClient("10.5.28.136:2181");
		client.setZkSerializer(new MyZkSerializer());
		String configPath = "/config";
		String value = "1111111";
		if (client.exists(configPath)) {
			client.writeData(configPath, value);
		} else {
			client.createPersistent(configPath, value);
		}
		client.close();
	}

	// 将配置文件的内容存放到zk节点上
	public void putConfigFile2ZK() throws IOException {

		File f = new File(this.getClass().getResource("/zookeeper/config.xml").getFile());
		FileInputStream fin = new FileInputStream(f);
		byte[] datas = new byte[(int) f.length()];
		fin.read(datas);
		fin.close();

		ZkClient client = new ZkClient("10.5.28.136:2181");
		client.setZkSerializer(new BytesPushThroughSerializer());
		String configPath = "/configFile";
		if (client.exists(configPath)) {
			client.writeData(configPath, datas);
		} else {
			client.createPersistent(configPath, datas);
		}
		client.close();
	}

	// 需要的配置都从zk上取，并注册watch来实时获得配置更新
	public void getConfigFromZk() {
		ZkClient client = new ZkClient("10.5.28.136:2181");
		client.setZkSerializer(new MyZkSerializer());
		String configPath = "/configFile";
		String value = client.readData(configPath);
		System.out.println("从zk读到配置config的值为：" + value);
		// 监控配置的更新
		client.subscribeDataChanges(configPath, new IZkDataListener() {

			@Override
			public void handleDataDeleted(String dataPath) throws Exception {
			}

			@Override
			public void handleDataChange(String dataPath, Object data) throws Exception {
				System.out.println("获得更新的配置值：" + data);
			}
		});

		// 这里只是为演示实时获取到配置值更新而加的等待。实际项目应用中根据具体场景写（可用阻塞方式）
		try {
			Thread.sleep(10 * 60 * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) throws IOException {
		//System.out.println(ConfigCenterDemo.class.getResource(""));
		//System.out.println(ConfigCenterDemo.class.getResource("/"));
		//System.out.println(ConfigCenterDemo.class.getClassLoader().getResource(""));
		//System.out.println(ConfigCenterDemo.class.getClassLoader().getResource("/"));
		ConfigCenterDemo demo = new ConfigCenterDemo();
		demo.put2Zk();
		demo.putConfigFile2ZK();

		demo.getConfigFromZk();
	}

}
