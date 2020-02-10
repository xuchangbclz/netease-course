package com.netease.course.neteasecourse.高性能编程专题.多线程并发编程.java基础.垃圾回收机制;

import java.lang.ref.SoftReference;
import java.lang.ref.WeakReference;

/**
 * 对象生命周期 : 创建阶段（Creation）、应用阶段（Using）、不可到达阶段（Unreachable）、可收集阶段（Collected）、终结阶段（Finalized）与释放阶段（Free）
 *
 *  创建阶段（Creation）：
 *      （1）为对象分配存储空间。
 *      （2）开始构造对象。
 *      （3）递归调用其父类的构造方法。
 *      （4）进行对象实例初始化与变量初始化。
 *      （5）执行构造方法体
 *
 *  应用阶段（Using）：对象的应用阶段是对象整个生命周期中证明自身“存在价值”的时期，对象具备下列特征：
 *      1.系统至少维护着对象的一个强引用（Strong Reference）；
 *      2.所有对该对象的引用全部是强引用（除非我们显式地使用了：软引用（Soft Reference）、弱引用（Weak Reference）或虚引用（Phantom Reference））。
 *
 *      介绍4中引用类型：
 *          (1) 强引用（Strong Reference）：强引用是使用最普遍的引用。如果一个对象具有强引用，那垃圾回收器绝不会回收它。当内存空间不足，Java虚拟机宁愿抛出OutOfMemoryError错误，
 *          使程序异常终止，也不会靠随意回收具有强引用的对象来解决内存不足的问题。
 *          ⑵ 软引用（Soft Reference）: 如果一个对象只具有软引用，则内存空间足够，垃圾回收器就不会回收它；
 *          如果内存空间不足了，就会回收这些对象的内存。只要垃圾回收器没有回收它，该对象就可以被程序使用。软引用可用来实现内存敏感的高速缓存。
 *          ⑶ 弱引用（Weak Reference）: 弱引用与软引用的区别在于,只具有弱引用的对象拥有更短暂的生命周期。在垃圾回收器线程扫描它所管辖的内存区域的过程中，一旦发现了只具有弱引用的对象，
 *          不管当前内存空间足够与否，都会回收它的内存。比如System.gc()时就会回收弱引用对象
 *          ⑷虚引用（Phantom Reference）：“虚引用”顾名思义，就是形同虚设，与其他几种引用都不同，虚引用并不会决定对象的生命周期。如果一个对象仅持有虚引用，
 *          那么它就和没有任何引用一样，在任何时候都可能被垃圾回收器回收。
 *
 *  不可到达阶段（Unreachable）:
 *      在虚拟机所管理的对象引用根集合中ROOT SET再也找不到直接或间接的强引用，这些对象都是要被垃圾回收器回收的预备对象
 *
 *  可收集阶段（Collected）:
 *      垃圾回收器发现该对象已经不可到达
 *
 *  终结阶段（Finalized）:
 *      finalize方法已经被执行
 *
 *  释放阶段（Free）:
 *      对象空间已被重用
 *
 *
 *
 **/
public class ObjectLifeCycle {

    public static void main(String[] args) {
        //强引用（Strong Reference）
        GC object = new GC();
        GC object1 = new GC();

        //软引用（Soft Reference）
        SoftReference<Object> softReference = new SoftReference<>(object);
        object = null;
        GC o = (GC) softReference.get();

        //弱引用（Weak Reference）
        WeakReference<Object> weakReference = new WeakReference<>(object1);
        object1 = null;
        GC o1 = (GC) weakReference.get();


    }

}
