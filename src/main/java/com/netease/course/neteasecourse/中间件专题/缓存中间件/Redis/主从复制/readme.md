搭建redis主从复制,master正常启动，slave启动时需要在redis.conf配置文件中增加如下配置：
```$xslt
# 指定从属于那个master
slaveof|replicaof [ip] [port] 

# 从服务器是否只读（默认yes）
slave-read-only yes 
```
## redis命令
```$xslt
# 查看主从节点
info replication 
 
# 监控redis，返回服务器处理的每个命令
monitor
```
##
主从复制注意事项：

1.读写分离场景：数据复制延时，单点故障

2.全量复制：第一次建立主从和故障转移时会出现全量复制

3.写能力有限，主从复制只有一台master




