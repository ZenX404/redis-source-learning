package pers.cy.redis.protocol;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  *3 数组3
 *  $3 字符串3  cyh
 *  $6 字符串6  cytuhd
 *  $8 字符串8  2 3 5 4 3 7 9 8
 */
public class Protocol {
    // $表示一个字符串
    public static final String DOLLAR_BYTE = "$";
    // *表示一个数组
    public static final String ASTERISK_BYTE = "*";
    // 用来区分一条命令
    public static final String BLANK_BYTE = "\r\n";

    // 将客户端传过来的数据处理成redis认识的数据，并将其发送
    public static void sendCommand(OutputStream outputStream, Command command, byte[]... bytes) {
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(ASTERISK_BYTE).append(bytes.length + 1).append(BLANK_BYTE);
        stringBuffer.append(DOLLAR_BYTE).append(command.name().length()).append(BLANK_BYTE);
        stringBuffer.append(command).append(BLANK_BYTE);

        for (byte[] arg : bytes) {
            stringBuffer.append(DOLLAR_BYTE).append(arg.length).append(BLANK_BYTE);
            stringBuffer.append(new String(arg)).append(BLANK_BYTE);

        }

        try {
            outputStream.write(stringBuffer.toString().getBytes());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 定义一个枚举类型，用来规定api层的命令是那些
    public static enum Command {
        SET,GET
    }

}
