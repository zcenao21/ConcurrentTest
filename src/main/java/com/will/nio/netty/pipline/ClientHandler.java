package com.will.nio.netty.pipline;

import com.will.nio.netty.pipline.protocal.*;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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

		new Thread(() -> {
			while (true) {
				try {
					String s = new BufferedReader(new InputStreamReader(System.in)).readLine();
					MessageRequestPacket messageRequestPacket = new MessageRequestPacket();
					messageRequestPacket.setUserName("will");
					messageRequestPacket.setMsg(s);
					ByteBuf byteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageRequestPacket);
					ctx.channel().writeAndFlush(byteBuf);
					System.out.println("发送消息成功！MSG:" + s);
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	//接收服务端消息
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
		}else if(packet instanceof MessageResponsePacket){
			MessageResponsePacket messageResponsePacket = (MessageResponsePacket) packet;
			System.out.println(messageResponsePacket.getMsgResponse());
		}
	}

}
