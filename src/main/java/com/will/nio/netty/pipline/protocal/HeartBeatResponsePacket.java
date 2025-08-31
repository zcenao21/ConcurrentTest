package com.will.nio.netty.pipline.protocal;

import com.will.nio.netty.pipline.protocal.Packet;

import static com.will.nio.netty.pipline.protocal.Command.HEARTBEAT_RESPONSE;

public class HeartBeatResponsePacket extends Packet {
	@Override
	public Byte getCommand() {
		return HEARTBEAT_RESPONSE;
	}
}
