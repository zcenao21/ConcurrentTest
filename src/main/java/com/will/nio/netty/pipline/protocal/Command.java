package com.will.nio.netty.pipline.protocal;

public interface Command {
	//定义登录请求指令和响应指令为1和2，其他指令同理如MESSAGE_REQUEST等
	Byte LOGIN_REQUEST = 1;
	Byte LOGIN_RESPONSE = 2;
	Byte MSG_REQUEST = 3;
	Byte MSG_RESPONSE = 4;
}
