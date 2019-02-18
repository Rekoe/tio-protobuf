package com.onemena.game.client;

import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import com.google.protobuf.MessageLite;
import com.onemena.game.socket.handler.AbstractProtoBufHandler;

public class MClientAioHander extends AbstractProtoBufHandler implements ClientAioHandler {

	@Override
	public Packet heartbeatPacket() {
		return null;// new ClientPacket();
	}

	@Override
	public void doHandler(MessageLite packet, ChannelContext channelContext) throws Exception {

	}

}
