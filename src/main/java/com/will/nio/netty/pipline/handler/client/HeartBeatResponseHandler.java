package com.will.nio.netty.pipline.handler.client;

import com.will.nio.netty.pipline.protocal.HeartBeatResponsePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class HeartBeatResponseHandler extends SimpleChannelInboundHandler<HeartBeatResponsePacket> {
//	public static final HeartBeatResponseHandler INSTANCE = new HeartBeatResponseHandler();
	public HeartBeatResponseHandler(){}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, HeartBeatResponsePacket heartBeatResponsePacket) throws Exception {
		System.out.println("收到心跳回复");
	}
}
