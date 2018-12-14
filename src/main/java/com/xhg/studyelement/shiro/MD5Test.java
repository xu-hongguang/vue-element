package com.xhg.studyelement.shiro;

import org.apache.shiro.crypto.hash.Md5Hash;

/**
 * @author 16033
 */
public class MD5Test {

    public static void main(String[] args) {
        Md5Hash hash = new Md5Hash("666666", "zhangsan", 3);
        System.out.println(hash.toHex());
    }
}
