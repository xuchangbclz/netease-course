package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.Netty实现SSL双向验证.server;

import lombok.Data;

import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.util.Arrays;

/**
 * 初始化服务端的ssl context
 **/
@Data
public class SSLContextFactory {

    private static final SSLContext SSL_CONTEXT_S ;

    static{
        SSLContext sslContext = null ;
        try {
            sslContext = SSLContext.getInstance("SSLv3") ;

            if(getKeyManagersServer() != null){
                /**
                 * 参数一：认证的密钥
                 * 参数二：双向信任认证，如果双向认证就写成tf.getTrustManagers()
                 * 参数三：伪随机数生成器,由于单向认证，服务端不用验证客户端，所以第二个参数为null
                 */
                sslContext.init(getKeyManagersServer(), null, null);
            }
        } catch (Exception e){
            e.printStackTrace() ;
        }

        String[] supportedCipherSuites = sslContext.createSSLEngine().getSupportedCipherSuites();
        System.out.println("-----> Server supportedCipherSuites " + Arrays.asList(supportedCipherSuites));
        SSL_CONTEXT_S = sslContext ;
    }

    public static SSLContext getSslContext(){
        return SSL_CONTEXT_S ;
    }



    /**
     * 获取服务端 密钥管理器
     */
    private static KeyManager[] getKeyManagersServer(){
        InputStream is = null ;
        KeyStore ks = null ;
        KeyManagerFactory keyFac = null ;

        KeyManager[] kms = null ;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = KeyManagerFactory.getInstance("SunX509");

            // 加载服务端密钥库
            is = SSLContextFactory.class.getClassLoader().getResourceAsStream("./sChat.jks");

            // 加载服务端的KeyStore,  该密钥库的密码storepass,storepass指定密钥库的密码(获取keystore信息所需的密码)
            ks = KeyStore.getInstance("JKS") ;
            String keyStorePass = "sNetty" ;
            ks.load(is , keyStorePass.toCharArray()) ;
            keyFac.init(ks, keyStorePass.toCharArray()) ;
            kms = keyFac.getKeyManagers() ;

        } catch (Exception e) {
            e.printStackTrace();

        } finally{
            if(is != null ){
                try {
                    is.close() ;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return kms ;
    }


}
