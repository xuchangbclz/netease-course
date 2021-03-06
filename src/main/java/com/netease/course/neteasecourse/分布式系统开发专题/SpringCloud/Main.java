package com.netease.course.neteasecourse.分布式系统开发专题.SpringCloud;

/**
 * Ribbon负载均衡的惊为天人的算法：WeightedResponseTimeRule这个策略每30秒计算一次服务器响应时间，以响应时间作为权重，响应时间越短的服务器被选中的概率越大,该策略是对RoundRobinRule的扩展
 *  该函数主要分为两个步骤
     * 1 根据LoadBalancerStats中记录的每个实例的统计信息，累计所有实例的平均响应时间，
     * 得到总的平均响应时间totalResponseTime，该值用于后面的计算。
     * 2 为负载均衡器中维护的实例清单逐个计算权重（从第一个开始），计算规则为：
     * weightSoFar+totalResponseTime-实例平均相应时间，其中weightSoFar初始化为0，并且
     * 每计算好一个权重需要累加到weightSoFar上供下一次计算使用。
     * 示例：4个实例A、B、C、D，它们的平均响应时间为10,40,80,100，所以总的响应时间为
     * 230，每个实例的权重为总响应时间与实例自身的平均响应时间的差的累积所得，所以实例A
     * B，C，D的权重分别为：
     * A：230-10=220
     * B：220+230-40=410
     * C：410+230-80=560
     * D：560+230-100=690
     * 需要注意的是，这里的权重值只是表示各实例权重区间的上限，并非某个实例的优先级，所以不
     * 是数值越大被选中的概率就越大。而是由实例的权重区间来决定选中的概率和优先级。
     * A：[0,220]
     * B：(220,410]
     * C：(410,560]
     * D：(560,690)
     * 实际上每个区间的宽度就是：总的平均响应时间-实例的平均响应时间，所以实例的平均响应时间越短
     * ，权重区间的宽度越大，而权重区间宽度越大被选中的概率就越大
 *
 *
 *  Hystrix高级用法：合并请求
 *
 *
 *  读多写少 -> 缓存
 *  写多读少 -> MQ，削峰
 *
 *  解决高并发场景3大武器：缓存，MQ削峰，请求合并
 *
 **/
public class Main {
}
