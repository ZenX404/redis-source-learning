package pers.cy.redis.api;

import pers.cy.redis.connection.Connection;
import pers.cy.redis.protocol.Protocol;

/**
 * 提供api接口
 * api操作层  也就是客户端
 */
public class Client {
    // 客户端使用传输层与redis服务器进行连接
    private Connection connection;

    public Client(String host, int port) {
        connection = new Connection(host, port);
    }

    /**
     * set操作
     * @param value
     * @return
     */
    public String set(final String key, String value) {
        // 发送数据
        connection.sendCommand(Protocol.Command.SET, SafeEncode.encode(key), SafeEncode.encode(value));
        return "ok";
    }

    public String get(final String key) {
        connection.sendCommand(Protocol.Command.SET, SafeEncode.encode(key));
        return connection.getStatusReply();
    }
}
