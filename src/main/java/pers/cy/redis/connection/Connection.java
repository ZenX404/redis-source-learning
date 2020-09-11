package pers.cy.redis.connection;

import pers.cy.redis.protocol.Protocol;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * 传输层
 */
public class Connection {
    private Socket socket;
    // 连接地址
    private String host;
    // 端口号
    private int port;
    private OutputStream outputStream;
    private InputStream inputStream;

    public Connection(String host, int port) {
        this.host = host;
        this.port = port;
    }

    /**
     * 与redis服务器进行连接
     */
    public void connect() {
        try {
            socket = new Socket(host, port);
            inputStream = socket.getInputStream();
            outputStream = socket.getOutputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接之后，发送数据
     * @return
     */
    public Connection sendCommand(Protocol.Command command, byte[] ... args) {
        // 与redis服务器进行连接
        connect();

        // 通过消息协议层将客户端传过来的数据进行序列化处理，处理成为redis服务器能认出来的数据
        Protocol.sendCommand(command, args);
        return this;
    }


    public String getStatusReply() {
        byte[] b = new byte[1024];
        try {
            socket.getInputStream().read(b);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new String(b);
    }
}
