package com.will.nio.netty.pipline;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.TimeUnit;

public class NettyClient {
	private static String host = "127.0.0.1";
	private static int port = 2121;

	public static void main(String[] args) {
		NioEventLoopGroup workGroup = new NioEventLoopGroup();
		Bootstrap bootstrap = new Bootstrap();
		bootstrap
			.group(workGroup)
			.channel(NioSocketChannel.class)
			.handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel socketChannel) throws Exception {
					socketChannel.pipeline().addLast(new ClientHandler());
				}
			});

		connect(bootstrap, host, port, 5);

	}

	private static void connect(Bootstrap bootstrap, String host, int port, int retry){
		bootstrap
				.connect(host, port)
				.addListener(future -> {
					if(future.isSuccess()){
						System.out.println("连接成功");
					}else if(retry <= 0){
						System.out.println("重试次数用完！放弃连接");
					}else{
						int left = retry - 1;
						System.out.println("剩余" + left + "次重试");
						bootstrap.config().group().schedule(()->
							connect(bootstrap, host, port, left),5, TimeUnit.SECONDS);
					}
				});
	}
}
