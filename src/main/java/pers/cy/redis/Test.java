package pers.cy.redis;

import pers.cy.redis.api.Client;

public class Test {
    public static void main(String[] args) {
        Client jedis = new Client("192.168.0.31", 6379);
        jedis.set("cy", "19981226");
        String value = jedis.get("cy");
        System.out.println(value);
    }
}
