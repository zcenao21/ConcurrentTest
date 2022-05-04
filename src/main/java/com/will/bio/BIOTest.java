package com.will.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO测试
 * 测试方法：
 *   1.运行当前的服务器程序
 *   2.telnet 127.0.0.1 6666, 然后输入ctrl+], 再发送消息 send ayt即可
 */
public class BIOTest {
    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newCachedThreadPool();
        ServerSocket serverSocket = new ServerSocket(6666);
        System.out.println("Server 启动成功");
        while(true){
            Socket socket = serverSocket.accept();
            pool.execute(new Runnable() {
                @Override
                public void run() {
                    try {
                        System.out.println("新client连接，ID："+Thread.currentThread().getId()+" 线程名:"+Thread.currentThread().getName());
                        handler(socket);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }finally {
                        if(socket!=null){
                            try {
                                socket.close();
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
            });
        }

    }

    public static void handler(Socket socket) throws IOException {
        byte[] bytes = new byte[1024];
        InputStream inputStream = socket.getInputStream();
        while(true){
            int read = inputStream.read(bytes);
            if(read>0){
                String input=new String(bytes,0,read);
                input=input.replace("\r\n","");
                System.out.println(input.length());
                System.out.println("|"+input+"|");
                if(input.equals("exit")){
                    break;
                }
                System.out.println(Thread.currentThread().getId()+"-"+Thread.currentThread().getName()+":"+input);
            }else{
                break;
            }
        }
    }
}
