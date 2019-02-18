package com.onemena.game.client;

import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import com.google.protobuf.MessageLite;
import com.onemena.game.proto.HeartBeatMessage;
import com.onemena.game.socket.handler.AbstractProtoBufHandler;
import com.onemena.game.socket.handler.MessagePacket;

public class MClientAioHander extends AbstractProtoBufHandler implements ClientAioHandler {

	@Override
	public Packet heartbeatPacket() {
		HeartBeatMessage backMsg = HeartBeatMessage.newBuilder().setMessage("ping").build();
		return new MessagePacket(backMsg);
	}

	@Override
	public void doHandler(MessageLite packet, ChannelContext channelContext) throws Exception {
		System.out.println("MClientAioHander : " + packet);
	}

}
