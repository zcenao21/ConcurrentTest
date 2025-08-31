package com.will.nio.netty.pipline.handler.client;

import com.will.nio.netty.pipline.protocal.HeartBeatRequestPacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

import java.util.concurrent.TimeUnit;

public class HeartBeatTimerHandler extends ChannelInboundHandlerAdapter {
	//心跳数据包发送时间间隔，这里设定成5s
	private static final int HEARTBEAT_INTERVAL = 5;
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		scheduleSendHeartBeat(ctx);
		super.channelActive(ctx);
	}

	private void scheduleSendHeartBeat(ChannelHandlerContext ctx) {
		ctx.executor().schedule(() -> {
			if (ctx.channel().isActive()) {
				HeartBeatRequestPacket heartBeatRequestPacket = new HeartBeatRequestPacket();
				heartBeatRequestPacket.setCurrClient(Thread.currentThread().toString());
				ctx.channel().writeAndFlush(heartBeatRequestPacket);
				scheduleSendHeartBeat(ctx);
			}
			System.out.println(Thread.currentThread().toString() + "发送心跳数据");
		},HEARTBEAT_INTERVAL , TimeUnit.SECONDS);
	}
}
