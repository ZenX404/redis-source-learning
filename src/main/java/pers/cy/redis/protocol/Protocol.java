package pers.cy.redis.protocol;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 *  消息协议层   将客户端传进来的数据转换成redis服务器认识的形式
 *
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
        // 按照规范好的协议将客户端传进来的数据进行处理

        StringBuffer stringBuffer = new StringBuffer();
        // 先判断传进来有多少个数据  用*
        stringBuffer.append(ASTERISK_BYTE).append(bytes.length + 1).append(BLANK_BYTE);
        // 再单独判断一下命令市set还是get 用&
        stringBuffer.append(DOLLAR_BYTE).append(command.name().length()).append(BLANK_BYTE);
        // 添加上传入的命令字符串
        stringBuffer.append(command).append(BLANK_BYTE);

        // 循环处理传入的key value
        for (byte[] arg : bytes) {
            stringBuffer.append(DOLLAR_BYTE).append(arg.length).append(BLANK_BYTE);
            stringBuffer.append(new String(arg)).append(BLANK_BYTE);

        }

        try {
            // 将处理好的数据转换成byte然后写入到output流中，传给redis
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
