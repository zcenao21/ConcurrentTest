package com.will.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Set;

/**
 * NIO buffer测试
 */
public class NIOSelectorClientTest {
    public static void main(String[] args) throws IOException {
        SocketChannel channel = SocketChannel.open();
        channel.configureBlocking(false);

        InetSocketAddress inetSocketAddress = new InetSocketAddress("127.0.0.1", 7777);
        while(!channel.connect(inetSocketAddress)){
            while(!channel.finishConnect()){
                System.out.println("等待连接中");
            }
            break;
        }
        String sendContent = "hello, 你好~";
        ByteBuffer wrap = ByteBuffer.wrap(sendContent.getBytes());
        channel.write(wrap);
        System.in.read();
        channel.close();
    }
}
