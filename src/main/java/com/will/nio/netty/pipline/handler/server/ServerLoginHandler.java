package com.will.nio.netty.pipline.handler.server;

import com.will.nio.netty.pipline.protocal.LoginRequestPacket;
import com.will.nio.netty.pipline.protocal.LoginResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerLoginHandler extends SimpleChannelInboundHandler<LoginRequestPacket> {
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, LoginRequestPacket loginRequestPacket) throws Exception {
		//服务端登录逻辑
		ctx.channel().writeAndFlush(login(loginRequestPacket));
	}

	private LoginResponsePacket login(LoginRequestPacket loginRequestPacket) {
		LoginResponsePacket loginResponsePacket = new LoginResponsePacket();
		loginResponsePacket.setVersion(loginRequestPacket.getVersion());
		if (valid(loginRequestPacket)) {
			loginResponsePacket.setSuccess(true);
			System.out.println("登录成功");
		} else {
			loginResponsePacket.setSuccess(false);
			loginResponsePacket.setReason("账号密码校验失败");
			System.out.println("登录失败");
		}
		return loginResponsePacket;

	}

	private boolean valid(LoginRequestPacket loginRequestPacket) {
		if(loginRequestPacket.getUserId() == 2){
			return true;
		}
		return false;
	}
}
