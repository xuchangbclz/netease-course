package com.netease.course.neteasecourse.高性能编程专题.高并发网络编程.Netty实现消息推送.Netty实现SSL双向验证.client;

import lombok.Data;

import javax.net.ssl.*;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

/**
 * 客户端需要加载自己的信任证书列表
 *
 * @Author daituo
 * @Date
 **/
@Data
public class ClientSslContextFactory {

    private static final SSLContext SSL_CONTEXT_C ;

    static{
        SSLContext sslContext2 = null ;
        try {
            sslContext2 = SSLContext.getInstance("SSLv3") ;
        } catch (NoSuchAlgorithmException e1) {
            e1.printStackTrace();
        }
        try{
            if(getKeyManagersClient() != null && getTrustManagersClient() != null){
                sslContext2.init(getKeyManagersClient(), getTrustManagersClient(), null);
            }

        }catch(Exception e){
            e.printStackTrace() ;
        }
        sslContext2.createSSLEngine().getSupportedCipherSuites() ;
        SSL_CONTEXT_C = sslContext2 ;
    }

    public static SSLContext getSslContextC() {
        return SSL_CONTEXT_C;
    }

    private static TrustManager[] getTrustManagersClient(){
        InputStream is = null ;
        KeyStore ks = null ;
        TrustManagerFactory keyFac = null ;

        TrustManager[] kms = null ;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = TrustManagerFactory.getInstance("SunX509") ;
            is = ClientSslContextFactory.class.getClassLoader().getResourceAsStream("./cChat.jks");
            ks = KeyStore.getInstance("JKS") ;
            String keyStorePass = "sNetty" ;
            ks.load(is , keyStorePass.toCharArray()) ;
            keyFac.init(ks) ;
            kms = keyFac.getTrustManagers() ;
            System.out.println("-------Netty client TrustManagers " + Arrays.asList(kms));
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
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


    private static KeyManager[] getKeyManagersClient(){
        InputStream is = null ;
        KeyStore ks = null ;
        KeyManagerFactory keyFac = null ;

        KeyManager[] kms = null ;
        try {
            // 获得KeyManagerFactory对象. 初始化位默认算法
            keyFac = KeyManagerFactory.getInstance("SunX509") ;
            is = ClientSslContextFactory.class.getClassLoader().getResourceAsStream("./cChat.jks");
            ks = KeyStore.getInstance("JKS") ;
            String keyStorePass = "sNetty" ;
            ks.load(is , keyStorePass.toCharArray()) ;
            keyFac.init(ks, keyStorePass.toCharArray()) ;
            kms = keyFac.getKeyManagers() ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
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
