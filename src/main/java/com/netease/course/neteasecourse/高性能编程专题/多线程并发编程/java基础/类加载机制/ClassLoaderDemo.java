package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.类加载机制;

import java.io.IOException;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.concurrent.TimeUnit;

/**
 * 类加载器
 *  1.一个java程序至少有3个类加载器实例，负责不同的类加载
 *      1.1 Bootstrap classloader:核心类库加载器，加载JRE_HOME/jre/lib目录，比如JDK核心类库rt.jar
 *      1.2 Extension classloader:扩展类库加载器，加载JRE_HOME/jre/lib/ext目录，JDK扩展包
 *      1.3 application classloader:应用程序类加载器，加载classpath指定目录
 *
 **/
public class ClassLoaderDemo {

    public static void main(String[] args) throws Exception {

        /** 1.查看类加载器实例*/
        viewClassLoader();

        /** 2.jvm怎么知道要加载的类在哪里呢？
         *      AppClassLoader 通过读取'java.class.path'配置，指定去哪里加载类，而一般的开发已经工具都已经配置了'java.class.path'地址
         *      验证：jps 查询本机java进程，jcmd 查看运行时配置 VM.system_properties
         *      jcmd pid VM.system_properties
         *
         */
        //helloWord();

        /** 3. 类加载时唯一性，同一个类加载实例下的同一个packet下的同一个类只会加载一次*/
        //classLoaderUnique();

        /** 4. 类的卸载：a 该class所有实例都已经被GC; b 加载该类的classloader实例已经被GC。满足ab时，类才会卸载*/

        /** 5.类加载的双亲委派模型：为了避免重复加载，最底层的类加载器首先不会自己去加载类，而是把请求委派给父类加载器，由下到上诸暨委派，
         * 只有当父类加载器无法完成类加载请求时，子类加载器才会自己去加载。App Classloader --> Ext Classloader --> Bootstrap Classloader*/

    }

    private static void classLoaderUnique() throws Exception {
        //告诉jvm 类的位置
        URL url = new URL("file:e:\\");
        while (true) {
            //每次新建一个类加载器加载HelloService
            URLClassLoader urlClassLoader = new URLClassLoader(new URL[]{url});
            Class<?> aClass = urlClassLoader.loadClass("HelloService");
            System.out.println("HelloService的类加载器：" + aClass.getClassLoader());
            Object instance = aClass.newInstance();
            Object value = aClass.getMethod("test").invoke(instance);
            System.out.println("调用HelloService的test()，返回值：" + value);
            TimeUnit.SECONDS.sleep(5);
        }
    }

    private static void helloWord() throws IOException {
        System.out.println("hello word");
        System.in.read();
    }

    private static void viewClassLoader() throws ClassNotFoundException {
        System.out.println("核心类库加载器：" + ClassLoaderDemo.class.getClassLoader().loadClass("java.lang.String").getClassLoader());
        System.out.println("扩展类库加载器：" + ClassLoaderDemo.class.getClassLoader().loadClass("com.sun.nio.zipfs.ZipCoder").getClassLoader());
        System.out.println("应用程序类加载器：" + ClassLoaderDemo.class.getClassLoader());

        /** 类加载的双亲委派模型*/
        System.out.println("应用程序类加载器的父 类加载器：" + ClassLoaderDemo.class.getClassLoader().getParent());
        System.out.println("应用程序类加载器的父 类加载器的父 " +
                "类加载器：" + ClassLoaderDemo.class.getClassLoader().getParent().getParent());

    }

}
