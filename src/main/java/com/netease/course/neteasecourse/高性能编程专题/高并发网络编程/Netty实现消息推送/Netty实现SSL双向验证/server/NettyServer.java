package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.Netty实现SSL双向验证.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslHandler;
import io.netty.util.CharsetUtil;

import javax.net.ssl.SSLEngine;

/**
 * 服务端
 *
 * 要使用ssl双向验证，就必须先要生成服务端和客户端的证书，并相互添加信任
 *  第一步: 生成Netty服务端私钥和证书仓库命令
 *   keytool.exe -genkey -alias securechat -keysize 2048 -validity 365 -keyalg RSA -dname "CN=localhost" -keypass sNetty -storepass sNetty -keystore D:\netty\ssl\server\sChat.jks
 *      -keysize 2048 密钥长度2048位（这个长度的密钥目前可认为无法被暴力破解）
 *      -validity 365 证书有效期365天
 *      -keyalg RSA 使用RSA非对称加密算法
 *      -dname "CN=localhost" 设置Common Name为localhost
 *      -keypass sNetty密钥的访问密码为sNetty
 *      -storepass sNetty密钥库的访问密码为sNetty（其实这两个密码也可以设置一样，通常都设置一样，方便记）
 *      -keystore sChat.jks 指定生成的密钥库文件为sChata.jks
 *
 *  第二步：生成Netty服务端自签名证书
 *      keytool -export -alias securechat -keystore D:\netty\ssl\server\sChat.jks -storepass sNetty -file D:\netty\ssl\server\sChat.cer
 *
 *  第三步：生成客户端的密钥对和证书仓库，用于将服务端的证书保存到客户端的授信证书仓库中
 *      keytool.exe -genkey -alias smcc -keysize 2048 -validity 365 -keyalg RSA -dname "CN=localhost" -keypass sNetty -storepass sNetty -keystore D:\netty\ssl\client\cChat.jks
 *
 *  第四步：将Netty服务端证书导入到客户端的证书仓库中
 *      keytool -import -trustcacerts -alias securechat -file  D:\netty\ssl\server\sChat.cer -storepass sNetty -keystore  D:\netty\ssl\client\cChat.jks
 *
 *  如果你只做单向认证，客户端认证服务端，服务端不会验证客户端，则到此就可以结束了，如果是双响认证，则还需继续往下走
 *
 *
 *
 *  第五步:生成客户端自签名证书
 *      keytool -export -alias smcc -keystore D:\netty\ssl\client\cChat.jks -storepass sNetty -file D:\netty\ssl\client\cChat.cer
 *
 *  第六步:将客户端的自签名证书导入到服务端的信任证书仓库中
 *      keytool -import -trustcacerts -alias smcc -file D:\netty\ssl\client\cChat.cer -storepass sNetty -keystore D:\netty\ssl\server\sChat.jks
 *
 *
 **/
public class NettyServer {

    private EventLoopGroup bossGroup = null ;

    private EventLoopGroup workerGroup = null ;

    public void start(){
        bossGroup = new NioEventLoopGroup() ;
        workerGroup = new NioEventLoopGroup() ;
        try{
            ServerBootstrap serverStrap = new ServerBootstrap()
                    .group(bossGroup , workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 128)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 1000 * 5 * 60)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            ChannelPipeline pie = socketChannel.pipeline() ;
                            SSLEngine engine = SSLContextFactory.getSslContext().createSSLEngine();

                            //设置为服务器模式
                            engine.setUseClientMode(false);

                            //是否需要验证客户端 。 如果是双向认证，则需要将其设置为true，同时将client证书添加到server的信任列表中
                            engine.setNeedClientAuth(false);
                            pie.addFirst("ssl", new SslHandler(engine));
                            pie.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                            pie.addLast("decoder" , new StringDecoder(CharsetUtil.UTF_8)) ;
                            pie.addLast("SslDemoServerSideInboundHandler" , new SslDemoServerSideHandler()) ;
                        }

                    });
            serverStrap.bind(9011).sync() ;
            System.out.println("------------ Netty server start on : 9011");
        }catch(Exception e){
            e.printStackTrace() ;
            bossGroup.shutdownGracefully() ;
            workerGroup.shutdownGracefully() ;
        }

    }

    public static void main(String[] args) {
        new NettyServer().start() ;
    }
}
