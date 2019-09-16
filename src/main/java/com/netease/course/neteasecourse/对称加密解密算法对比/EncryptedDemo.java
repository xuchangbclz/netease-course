package com.netease.course.neteasecourse.对称加密解密算法对比;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.io.*;
import java.security.SecureRandom;

/**
 * @Description TODO
 * @Author daituo
 * @Date
 **/
public class EncryptedDemo {

    private static String keyStr = "xLER7aR5TCbryeJujMJHNw==";

    public static SecretKey AES_key;
    public static SecretKey DES_key;
    public static SecretKey DESede_key;
    public static SecretKey Blowfish_key;


    //算法
    public static final String AES = "AES";
    public static final String DES = "DES";
    public static final String DESede = "DESede";
    public static final String Blowfish = "Blowfish";


    public static byte[] initBytes = null;

    static {
        initBytes = getFileBytes("D:\\应用服务部署说明文档.doc");
        System.out.println("初始化byte数组长度：" + initBytes.length);
    }
    //private static final String address = "广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省" +
    //        "广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河" +
    //        "区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广" +
    //        "州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区" +
    //        "广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州" +
    //        "市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广" +
    //        "东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市" +
    //        "天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东" +
    //        "省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天" +
    //        "河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省" +
    //        "广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河" +
    //        "区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广" +
    //        "州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区" +
    //        "广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州" +
    //        "市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广" +
    //        "东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市" +
    //        "天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东" +
    //        "省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天" +
    //        "河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省广州市天河区广东省" +
    //        "广州市天河区广东省广州市天河区";


    public static void main(String[] args) {

        //DES
        long time1 = System.currentTimeMillis();
        byte[] encryptedByDES = encryptedDES(initBytes);
        long time2 = System.currentTimeMillis();
        System.out.println("DES加密后byte数组长度：" + encryptedByDES.length + ",加密耗时：" + (time2 - time1));
        byte[] decryptedByDES = decryptedDES(encryptedByDES);
        long time3 = System.currentTimeMillis();
        System.out.println("DES解密后byte数组长度：" + decryptedByDES.length + ",解密耗时：" + (time3 - time2));
        System.out.println("DES加密解密总耗时：" + (time3 - time1) + "ms");


        //AES,用来替代DES
        long time4 = System.currentTimeMillis();
        byte[] encryptedByAES = encryptedAES(initBytes);
        System.out.println("AES加密后byte数组长度：" + encryptedByAES.length);
        byte[] decryptedByAES = decryptedAES(encryptedByAES);
        System.out.println("AES解密后byte数组长度：" + decryptedByAES.length);
        long time5 = System.currentTimeMillis();
        System.out.println("AES加密解密总耗时：" + (time5 - time4) + "ms");


        //Blowfish
        long time6 = System.currentTimeMillis();
        byte[] encryptedByBlowfish = encryptedBlowfish(initBytes);
        System.out.println("Blowfish加密后byte数组长度：" + encryptedByBlowfish.length);
        byte[] decryptedByBlowfish = decryptedBlowfish(encryptedByBlowfish);
        System.out.println("Blowfish解密后byte数组长度：" + decryptedByBlowfish.length);
        long time7 = System.currentTimeMillis();
        System.out.println("Blowfish加密解密总耗时：" + (time7 - time6) + "ms");


        //DESede,DES的加强版
        long time8 = System.currentTimeMillis();
        byte[] encryptedByDESede = encryptedDESede(initBytes);
        System.out.println("DESede加密后byte数组长度：" + encryptedByDESede.length);
        long time9 = System.currentTimeMillis();
        byte[] decryptedByDESede = decryptedDESede(encryptedByAES);
        System.out.println("DESede解密后byte数组长度：" + decryptedByDESede.length);
        System.out.println("DESede加密解密总耗时：" + (time9 - time8) + "ms");
    }



    /**
     * AES对称加密
     */
    private static byte[] encryptedBlowfish(byte[] str) {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(Blowfish);
            keyGenerator.init(128);
            Blowfish_key = keyGenerator.generateKey();
            Cipher c = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
            c.init(Cipher.ENCRYPT_MODE, Blowfish_key);
            // 加密，保存到enc
            return c.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * AES对称解密
     */
    private static byte[] decryptedBlowfish(byte[] enc) {
        Cipher c;
        try {
            c = Cipher.getInstance("Blowfish/ECB/PKCS5Padding");
            c.init(Cipher.DECRYPT_MODE, Blowfish_key);
            // 解密,保存到dec
            return c.doFinal(enc);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * AES对称加密
     */
    private static byte[] encryptedAES(byte[] str) {
        try {
            //这个是密匙生成器，多种算法通用的。

            KeyGenerator keyGen = KeyGenerator.getInstance(AES);

            // 使用用户输入的key，按照长度128初始化密匙生成器

            keyGen.init(128, new SecureRandom(keyStr.getBytes()));
            AES_key = keyGen.generateKey();
            byte[] enc = null;
            Cipher c;
            c = Cipher.getInstance(AES);
            c.init(Cipher.ENCRYPT_MODE, AES_key);
            // 加密，保存到enc
            return c.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * AES对称解密
     */
    private static byte[] decryptedAES(byte[] enc) {
        byte[] dec = null;
        Cipher c;
        try {
            c = Cipher.getInstance(AES);
            c.init(Cipher.DECRYPT_MODE, AES_key);
            // 解密,保存到dec
            dec = c.doFinal(enc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c = null;
        }
        return dec;
    }



    /**
     * DES对称加密
     */
    private static byte[] encryptedDES(byte[] str) {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(DES);

            // 使用用户输入的key，按照长度64初始化密匙生成器

            keyGen.init(56, new SecureRandom(keyStr.getBytes()));

            DES_key = keyGen.generateKey();

            byte[] enc = null;
            Cipher c;
            c = Cipher.getInstance(DES);
            c.init(Cipher.ENCRYPT_MODE, DES_key);
            // 加密，保存到enc
            return c.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * DES对称解密
     */
    private static byte[] decryptedDES(byte[] enc) {
        byte[] dec = null;
        Cipher c;
        try {
            c = Cipher.getInstance(DES);
            c.init(Cipher.DECRYPT_MODE, DES_key);
            // 解密,保存到dec
            dec = c.doFinal(enc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c = null;
        }
        return dec;
    }


    /**
     * DESede对称加密
     */
    private static byte[] encryptedDESede(byte[] str) {
        try {
            //返回生成指定算法的秘密密钥的 KeyGenerator 对象
            KeyGenerator kg = KeyGenerator.getInstance(DESede);
            //初始化此密钥生成器，使其具有确定的密钥大小
            kg.init(168);
            //生成一个密钥
            SecretKey secretKey = kg.generateKey();
            byte[] key = secretKey.getEncoded();
            //实例化DES密钥规则
            DESedeKeySpec dks = new DESedeKeySpec(key);
            //实例化密钥工厂
            SecretKeyFactory skf = SecretKeyFactory.getInstance(DESede);
            //生成密钥
            DESede_key = skf.generateSecret(dks);
            Cipher c = Cipher.getInstance("DESede/ECB/ISO10126Padding");
            c.init(Cipher.ENCRYPT_MODE, DESede_key);
            // 加密，保存到enc
            return c.doFinal(str);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }



    /**
     * DESede对称解密
     */
    private static byte[] decryptedDESede(byte[] enc) {
        byte[] dec = null;
        Cipher c;
        try {
            c = Cipher.getInstance("DESede/ECB/ISO10126Padding");
            c.init(Cipher.DECRYPT_MODE, DESede_key);
            // 解密,保存到dec
            dec = c.doFinal(enc);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            c = null;
        }
        return dec;
    }


    /**
     * 读取文件到byte[]
     */
    public static byte[] getFileBytes(String file) {
        try {
            File f = new File(file);
            int length = (int) f.length();
            byte[] data = new byte[length];
            new FileInputStream(f).read(data);
            return data;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
