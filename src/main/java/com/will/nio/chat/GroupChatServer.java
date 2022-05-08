package com.will.nio.chat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.Channel;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * NIO群聊程序
 * 服务端接受消息并群发
 * 客户端发送消息和接受消息
 */

public class GroupChatServer {
    private Selector selector;
    private ServerSocketChannel listenChannel;
    private static final int PORT = 6667;

    public GroupChatServer(){
        try{
            selector = Selector.open();
            listenChannel = ServerSocketChannel.open();
            listenChannel.configureBlocking(false);
            listenChannel.socket().bind(new InetSocketAddress(PORT));
            listenChannel.register(selector, SelectionKey.OP_ACCEPT);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void listen(){
        try {
            while(true){
                int cnt = selector.select(2000);
                if(cnt>0){
                    Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
                    while(iterator.hasNext()){
                        SelectionKey key = iterator.next();
                        if(key.isAcceptable()){
                            SocketChannel sc = listenChannel.accept();
                            sc.configureBlocking(false);
                            sc.register(selector,SelectionKey.OP_READ);
                            System.out.println(sc.getRemoteAddress()+"上线");
                        }
                        if(key.isReadable()){
                            readData(key);
                        }
                        // 当前的key删除
                        iterator.remove();
                    }
                }else{
                    System.out.println("等待中...");
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {

        }
    }

    private void readData(SelectionKey key){
        SocketChannel channel = null;
        try{
            channel = (SocketChannel) key.channel();
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            int count = channel.read(buffer);
            if(count>0){
                String msg = new String(buffer.array());
                System.out.println("from 客户端:"+ msg);

                // 向其他客户端转发消息
                sendInfo2OtherClient(msg, channel);
            }
        }catch (Exception e){
            try {
                System.out.println(channel.getRemoteAddress()+" 离线了");
                key.cancel();
                channel.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void sendInfo2OtherClient(String msg, SocketChannel self) throws IOException {
        System.out.println("服务器转发消息中");
        for(SelectionKey key:selector.keys()){
            Channel targetChannel = key.channel();
            if(targetChannel instanceof SocketChannel && targetChannel!=self){
                SocketChannel dest = (SocketChannel) targetChannel;
                ByteBuffer buffer = ByteBuffer.wrap(msg.getBytes());
                dest.write(buffer);
            }
        }
    }

    public static void main(String[] args) {
        GroupChatServer server = new GroupChatServer();
        server.listen();
    }
}
