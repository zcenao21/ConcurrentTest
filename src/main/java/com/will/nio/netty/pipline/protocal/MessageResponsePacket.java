package com.will.nio.netty.pipline.protocal;

import lombok.Data;

import static com.will.nio.netty.pipline.protocal.Command.MSG_REQUEST;
import static com.will.nio.netty.pipline.protocal.Command.MSG_RESPONSE;

@Data
public class MessageResponsePacket extends Packet{
	//定义消息
	private String msgResponse;
	private String msgReceived;
	@Override
	public Byte getCommand() {
		return MSG_RESPONSE;
	}
}
