package com.will.nio.netty.simple;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.concurrent.EventExecutorGroup;

import java.nio.charset.Charset;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端发送消息");
		ByteBuf buf = getByteBuf(ctx);
		ctx.channel().writeAndFlush(buf);
	}

	private ByteBuf getByteBuf(ChannelHandlerContext ctx) {
		ByteBuf buffer = ctx.alloc().buffer();
		String sendMsg = "我是客户端1，您好";
		byte[] bytes = sendMsg.getBytes(Charset.forName("utf-8"));
		buffer.writeBytes(bytes);
		return buffer;
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		System.out.println(buf.toString(Charset.forName("utf-8")));
	}

}
