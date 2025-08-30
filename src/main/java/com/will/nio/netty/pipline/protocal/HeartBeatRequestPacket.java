package com.will.nio.netty.pipline.protocal;

import static com.will.nio.netty.pipline.protocal.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet{
	@Override
	public Byte getCommand() {
		return HEARTBEAT_REQUEST;
	}
}
