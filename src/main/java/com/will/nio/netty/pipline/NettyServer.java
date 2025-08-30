package com.will.nio.netty.pipline;

import com.will.nio.netty.pipline.protocal.PacketDecoder;
import com.will.nio.netty.pipline.protocal.PacketEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class NettyServer {
	public static void main(String[] args) {
		NioEventLoopGroup bossGroup = new NioEventLoopGroup();
		NioEventLoopGroup workerGroup = new NioEventLoopGroup();
		ServerBootstrap serverBootstrap = new ServerBootstrap();
		serverBootstrap
				.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.childHandler(new ChannelInitializer<NioSocketChannel>() {
					@Override
					protected void initChannel(NioSocketChannel ch) throws Exception {
						ch.pipeline().addLast(new MyIdleStateHandler());
						ch.pipeline().addLast(new PacketDecoder());
						ch.pipeline().addLast(new ServerLoginHandler());
						ch.pipeline().addLast(new HeartBeatRequestHandler());
						ch.pipeline().addLast(new PacketEncoder());
					}
				});
		serverBootstrap.bind(2121);
	}
}
