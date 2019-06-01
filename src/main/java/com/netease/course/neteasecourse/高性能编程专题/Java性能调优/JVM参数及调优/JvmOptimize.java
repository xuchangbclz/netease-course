package com.netease.course.neteasecourse.高性能编程专题.Java性能调优.JVM参数及调优;

/**
 * JVM参数调优
 * -server :服务器模式启动JVM
 * -Xms4g -Xmx4g  :最小、大堆内存，一般设置-Xms4g == Xmx4g，是为了避免GC时后，把空闲内存返回给操作系统，当堆内存不够时又重新像操作系统申请内存
 * -XX：+AlwaysPreTouch  :一启动JVM就从操作系统中直接申请最小堆内存
 * -Xmn2g  :永久代内存
 * -Xss768m  :线程栈内存，默认1M，减小线程栈大小，可以创建更多的线程
 * -XX:+DisableExplicitGC  :禁用显示gc,代码里的System.gc()失效
 * -verbose:gc -XX:+PrintGCDetail -Xloggc:xx/logs/gc.log -XX:+PrintGCDateStamps  :开启gc日志，控制台打印gc详情，日志文件记录gc,打印gc时间戳
 * -XX:+HeapDumpOnOutOfMemoryError  -XX:HeapDumpPath=./log :开启堆OOM异常时的快照存储,快照存储路径
 *
 *
 **/
public class JvmOptimize {
}
