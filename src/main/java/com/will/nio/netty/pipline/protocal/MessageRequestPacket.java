package com.will.nio.netty.pipline.protocal;

import lombok.Data;

import static com.will.nio.netty.pipline.protocal.Command.LOGIN_REQUEST;
import static com.will.nio.netty.pipline.protocal.Command.MSG_REQUEST;

@Data
public class MessageRequestPacket extends Packet{
	//定义消息
	private String msg;
	private String userName;
	@Override
	public Byte getCommand() {
		return MSG_REQUEST;
	}
}
