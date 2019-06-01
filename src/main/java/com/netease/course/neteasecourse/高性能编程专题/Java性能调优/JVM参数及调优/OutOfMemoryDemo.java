package com.netease.course.neteasecourse.高性能编程专题.Java性能调优.JVM参数及调优;

import java.util.ArrayList;

/**
 * 资源占用过多或者资源未释放，内存溢出
 *
 * OOM异常排除：
 *  1.事先定义OOM内存快照
 *  2.通过插件工具分析快照文件
 *
 **/
public class OutOfMemoryDemo {

    static ArrayList<Object> space = new ArrayList<Object>();

    //-Xms512m -Xmx512m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=.
    public static void main(String[] args) throws Exception {
        // 内存泄漏 最终会导致  内存溢出
        for (int i = 0; i < 1000; i++) {
            space.add(new byte[1024 * 1024 * 64]); // 64兆
        }
    }

}
