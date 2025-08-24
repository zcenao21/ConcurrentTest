package com.will.nio.netty.pipline;

import com.will.nio.netty.pipline.protocal.LoginRequestPacket;
import com.will.nio.netty.pipline.protocal.LoginResponsePacket;
import com.will.nio.netty.pipline.protocal.Packet;
import com.will.nio.netty.pipline.protocal.PacketCodeC;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.nio.charset.Charset;
import java.util.Random;

public class ClientHandler extends ChannelInboundHandlerAdapter {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端开始登录");
		//创建登录对象
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
		loginRequestPacket.setUserId(new Random().nextInt(10000));
		loginRequestPacket.setUserName("will");
		loginRequestPacket.setPassword("pwd");
		//编码
		ByteBuf buf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginRequestPacket);
		//写数据
		ctx.channel().writeAndFlush(buf);
	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		Packet packet = PacketCodeC.INSTANCE.decode(buf);
		//如果是登录类型，就进行登录判断
		if (packet instanceof LoginResponsePacket) {
			LoginResponsePacket loginResponsePacket = (LoginResponsePacket) packet;
			if (loginResponsePacket.isSuccess()) {
				System.out.println("登录成功");
			} else {
				System.out.println("登录失败,原因为："+loginResponsePacket.getReason());
			}
		}
	}

}
