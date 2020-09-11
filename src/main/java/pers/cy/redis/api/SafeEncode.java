package pers.cy.redis.api;

public class SafeEncode {
    public static byte[] encode(String str) {
        // 将字符串转码成byte
        return str.getBytes();
    }
}
