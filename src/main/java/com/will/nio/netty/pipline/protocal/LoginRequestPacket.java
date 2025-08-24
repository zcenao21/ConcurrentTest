package com.will.nio.netty.pipline.protocal;

import lombok.Data;

import static com.will.nio.netty.pipline.protocal.Command.LOGIN_REQUEST;

@Data
public class LoginRequestPacket extends Packet{
	//定义用户信息
	private Integer userId;
	private String userName;
	private String password;
	@Override
	public Byte getCommand() {
		return LOGIN_REQUEST;
	}
}
