package com.will.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.IntBuffer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * NIO buffer测试
 */
public class NIOBufferTest {
    public static void main(String[] args){
        IntBuffer buffer = IntBuffer.allocate(5);
        for(int i=0;i<buffer.capacity();i++){
            buffer.put(i*3);
        }
        buffer.flip();
        while(buffer.hasRemaining()){
            System.out.println(buffer.get());
        }
    }
}
