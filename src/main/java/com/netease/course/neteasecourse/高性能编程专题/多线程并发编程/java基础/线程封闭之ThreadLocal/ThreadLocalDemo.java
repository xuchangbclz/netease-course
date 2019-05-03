package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.线程封闭之ThreadLocal;

/** 线程封闭示例 */
public class ThreadLocalDemo {
	/** threadLocal变量，每个线程都有一个副本，互不干扰 */
	public static ThreadLocal<String> value = new ThreadLocal<>();

	/**
	 * threadlocal测试
	 *
	 * @throws Exception
	 */
	public void threadLocalTest() throws Exception {

		// threadlocal线程封闭示例
		value.set("这是主线程设置的123"); // 主线程设置值
		String v = value.get();
		System.out.println("线程1执行之前，主线程取到的值：" + v);

		new Thread(() -> {
			String v1 = value.get();
			System.out.println("线程1取到的值：" + v1);
			// 设置 threadLocal
			value.set("这是线程1设置的456");

			v1 = value.get();
			System.out.println("重新设置之后，线程1取到的值：" + v1);
			System.out.println("线程1执行结束");
		}).start();

		Thread.sleep(5000L); // 等待所有线程执行结束

		v = value.get();
		System.out.println("线程1执行之后，主线程取到的值：" + v);

	}

	public static void main(String[] args) throws Exception {
		new ThreadLocalDemo().threadLocalTest();
	}
}
