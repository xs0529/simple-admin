package xyz.iotcode.simpleadmin;

import cn.hutool.crypto.SecureUtil;

public class Test {

    public static void main(String[] args) {
        System.out.println(SecureUtil.sha256("123456"));
    }
}
