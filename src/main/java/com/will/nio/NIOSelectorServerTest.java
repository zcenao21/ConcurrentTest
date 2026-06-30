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

public class NIOSelectorServerTest {
	public NIOSelectorServerTest() {
	}

	public static void main(String[] var0) throws IOException {
		ServerSocketChannel var1 = ServerSocketChannel.open();
		var1.configureBlocking(false);
		var1.bind(new InetSocketAddress("127.0.0.1", 7777));
		Selector var2 = Selector.open();
		var1.register(var2, 16);

		while(true) {
			while(var2.select(1000L) != 0) {
				Set var3 = var2.selectedKeys();
				System.out.println("size:" + var2.keys().size());

				for(Iterator var4 = var3.iterator(); var4.hasNext(); var4.remove()) {
					SelectionKey var5 = (SelectionKey)var4.next();
					SocketChannel var6;
					if (var5.isAcceptable()) {
						var6 = var1.accept();
						System.out.println("成功开启一个客户端连接：" + var6.hashCode());
						var6.configureBlocking(false);
						var6.register(var2, 1, ByteBuffer.allocate(1024));
					}

					if (var5.isReadable()) {
						var6 = (SocketChannel)var5.channel();
						ByteBuffer var7 = (ByteBuffer)var5.attachment();
						var6.read(var7);
						System.out.println("从客户端读取到：" + new String(var7.array()));
					}
				}
			}

			System.out.println(System.currentTimeMillis() + ":服务器等待了1s,没有连接");
		}
	}
}
