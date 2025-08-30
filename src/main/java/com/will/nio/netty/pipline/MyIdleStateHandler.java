package com.will.nio.netty.pipline;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

public class MyIdleStateHandler extends IdleStateHandler {
	//读空闲时间，多久没读到新数据
	private static final int READER_IDLE_TIME_SECONDS = 15;
	public MyIdleStateHandler() {
		super(READER_IDLE_TIME_SECONDS, 0, 0, TimeUnit.SECONDS);
	}

	@Override
	protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
		System.out.println(READER_IDLE_TIME_SECONDS + "秒内未读到数据，关闭连接");
		ctx.channel().close();
	}
}
