package com.will.nio.netty.pipline.protocal;

import lombok.Data;

@Data
public abstract class Packet {
	//协议版本
	private Byte version = 1;
	//获取数据类型
	public abstract Byte getCommand();
}
