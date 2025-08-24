package com.will.nio.netty.pipline;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		System.out.println("服务端读到数据：" + buf.toString(Charset.forName("utf-8")));
		ByteBuf bufOut = getBufOut(ctx);
		ctx.channel().writeAndFlush(bufOut);
	}

	private ByteBuf getBufOut(ChannelHandlerContext ctx) {
		String msg = "我是服务器，收到你的消息了！";
		byte[] bytes = msg.getBytes(Charset.forName("utf-8"));
		ByteBuf buffer = ctx.alloc().buffer();
		buffer.writeBytes(bytes);
		System.out.println("服务端开始发送消息");
		return buffer;
	}

}
