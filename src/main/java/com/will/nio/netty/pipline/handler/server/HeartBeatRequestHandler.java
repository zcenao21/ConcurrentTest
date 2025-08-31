package com.will.nio.netty.pipline.handler.server;

import com.will.nio.netty.pipline.protocal.HeartBeatRequestPacket;
import com.will.nio.netty.pipline.protocal.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatRequestHandler extends SimpleChannelInboundHandler<HeartBeatRequestPacket> {
	public HeartBeatRequestHandler(){}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatRequestPacket heartBeatRequestPacket) throws Exception {
		System.out.println("从" + heartBeatRequestPacket.getCurrClient() + "收到心跳数据");
		ctx.channel().writeAndFlush(new HeartBeatResponsePacket());
	}
}
