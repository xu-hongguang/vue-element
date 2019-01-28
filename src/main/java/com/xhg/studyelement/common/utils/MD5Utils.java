package com.xhg.studyelement.common.utils;

import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;

/**
 * @author Eddy.Xu
 */
public class MD5Utils {
    private static final Logger logger = LoggerFactory.getLogger(MD5Utils.class);

    public static final String ALGORITHM_NAME = "md5";

    public static final int HASH_ITERATIONS = 3;

    private MD5Utils() {
        throw new IllegalStateException("MD5Utils");
    }

    /**
     * MD5 encode
     *
     * @param password
     * @return
     */
    public static String encode(String password) {
        char[] hexDigits = {
                '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F'
        };
        try {
            byte[] btInput = password.getBytes("UTF-8");
            // 获得MD5摘要算法的 MessageDigest 对象
            MessageDigest mdInst = MessageDigest.getInstance("MD5");
            // 使用指定的字节更新摘要
            mdInst.update(btInput);
            // 获得密文
            byte[] md = mdInst.digest();
            // 把密文转换成十六进制的字符串形式
            int j = md.length;
            char[] str = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            logger.error("{} 加密失败。", password, e);
            return null;
        }
    }

    /**
     * 以下两种加密方式一样(Md5Hash继承SimpleHash)
     *
     * @param pwd
     * @param name
     * @return
     */
    public static String md5(String pwd, String name) {
        return new Md5Hash(pwd, name, HASH_ITERATIONS).toHex();
    }

    private static String md5Encode(String pwd, String name) {
        return new SimpleHash(ALGORITHM_NAME, pwd, ByteSource.Util.bytes(name), HASH_ITERATIONS).toHex();
    }


    public static void main(String[] args) {
        String name = "xhg";
        String password = "666666";

        System.out.println(md5(password, name));
        System.out.println(md5Encode(password, name));
    }
}
