package com.will.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * NIO buffer测试
 *
 * 命令行测试方法:
 * 1.javac NIOSelectorServerTest.java得到class文件
 * 2.回退到文件夹/com/will/xxx的最上层
 * 3.执行java NIOSelectorServerTest
 */
public class NIOSelectorServerTest {
    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 7777));

        // 注册Selector
        Selector selector=Selector.open();
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while(true){
            if(selector.select(1000)==0){
                System.out.println(System.currentTimeMillis()+":服务器等待了1s,没有连接");
                continue;
            }
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            System.out.println("size:"+selector.keys().size());
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while(iterator.hasNext()) {
                SelectionKey key = iterator.next();
                if (key.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    System.out.println("成功开启一个客户端连接：" + socketChannel.hashCode());
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                }
                if (key.isReadable()) {
                    SocketChannel channel = (SocketChannel) key.channel();
                    ByteBuffer buffer = (ByteBuffer)key.attachment();
                    channel.read(buffer);
                    System.out.println("从客户端读取到：" + new String(buffer.array()));
                }
                iterator.remove();
            }
        }

    }
}
