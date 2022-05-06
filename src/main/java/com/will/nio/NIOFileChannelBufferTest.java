package com.will.nio;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.nio.channels.FileChannel;

/**
 * NIO buffer测试，先写入数据到buffer，再从buffer写入channel
 */
public class NIOFileChannelBufferTest {
    public static void main(String[] args) throws IOException {
        String say = "top chao!";
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(say.getBytes());
        buffer.flip();
        FileOutputStream stream = new FileOutputStream(new File("/home/will/out.txt"));
        FileChannel channel = stream.getChannel();
        channel.write(buffer);
        stream.close();
    }
}
