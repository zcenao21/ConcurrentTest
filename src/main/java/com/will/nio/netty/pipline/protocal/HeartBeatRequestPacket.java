package com.will.nio.netty.pipline.protocal;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Random;

import static com.will.nio.netty.pipline.protocal.Command.HEARTBEAT_REQUEST;

public class HeartBeatRequestPacket extends Packet {
	@Getter
	@Setter
	public String currClient;

	@Override
	public Byte getCommand() {
		return HEARTBEAT_REQUEST;
	}
}
