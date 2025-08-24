package com.will.nio.netty.pipline;

import com.will.nio.netty.pipline.protocal.Packet;
import com.will.nio.netty.pipline.protocal.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;

public class ServerHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		Packet packet = PacketCodeC.INSTANCE.decode(buf);
		//判断是否请求登录数据包
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
