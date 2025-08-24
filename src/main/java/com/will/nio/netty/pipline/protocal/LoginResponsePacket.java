package com.will.nio.netty.pipline.protocal;

import lombok.Data;

import static com.will.nio.netty.pipline.protocal.Command.LOGIN_REQUEST;
import static com.will.nio.netty.pipline.protocal.Command.LOGIN_RESPONSE;

@Data
public class LoginResponsePacket extends Packet{
	//是否成功登录
	private boolean success;

	//如果失败，返回的信息
	private String reason;
	@Override
	public Byte getCommand() {
		return LOGIN_RESPONSE;
	}
}
