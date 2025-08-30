package com.will.nio.netty.pipline;

import com.will.nio.netty.pipline.protocal.LoginRequestPacket;
import com.will.nio.netty.pipline.protocal.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientLoginHandler extends SimpleChannelInboundHandler<LoginResponsePacket> {
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("客户端开始登录");
		//创建登录对象
		LoginRequestPacket loginRequestPacket = new LoginRequestPacket();
		loginRequestPacket.setUserId(2);
		loginRequestPacket.setUserName("will");
		loginRequestPacket.setPassword("pwd");
		//写数据
		ctx.channel().writeAndFlush(loginRequestPacket);
	}

	@Override
	protected void channelRead0(ChannelHandlerContext channelHandlerContext, LoginResponsePacket loginResponsePacket) throws Exception {
		if(loginResponsePacket.isSuccess()) {
			System.out.println("客户端登录成功");
		}else{
			System.out.println("客户端登录失败" + loginResponsePacket.getReason());
		}
	}
}
