package com.will.nio.netty.pipline;

import com.will.nio.netty.pipline.protocal.*;
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
		if(packet instanceof LoginRequestPacket){
			LoginRequestPacket loginRequestPacket = (LoginRequestPacket) packet;
			LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
			loginResponsePacket.setVersion(packet.getVersion());
			//登录校验
			if(valid(loginRequestPacket)){
				//校验成功
				loginResponsePacket.setSuccess(true);
				System.out.println("客户端登录成功");
			} else {
				//校验失败
				loginResponsePacket.setReason("账号或密码错误");
				loginResponsePacket.setSuccess(false);
				System.out.println("客户端登录失败");
			}
			//编码，发送结果给客户端
			ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), loginResponsePacket);
			ctx.channel().writeAndFlush(responseByteBuf);
		}else if(packet instanceof MessageRequestPacket){
			MessageRequestPacket requestPacket = (MessageRequestPacket) packet;
			MessageResponsePacket messageResponsePacket = new MessageResponsePacket();
			messageResponsePacket.setMsgReceived(requestPacket.getMsg());
			String msgResponse = System.currentTimeMillis() + "服务端收到消息了";
			System.out.println(msgResponse);
			messageResponsePacket.setMsgResponse(msgResponse);
			ByteBuf responseByteBuf = PacketCodeC.INSTANCE.encode(ctx.alloc(), messageResponsePacket);
			ctx.channel().writeAndFlush(responseByteBuf);
		}
	}

	private boolean valid(LoginRequestPacket loginRequestPacket){
		//查询数据库，登录校验
		return true;
	}
}
