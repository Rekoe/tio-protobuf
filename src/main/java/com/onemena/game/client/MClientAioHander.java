package com.onemena.game.client;

import org.tio.client.intf.ClientAioHandler;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;

import com.google.protobuf.MessageLite;
import com.onemena.game.socket.handler.GameHandler;
import com.onemena.game.socket.handler.MessagePacket;

public class MClientAioHander extends GameHandler implements ClientAioHandler {

	@Override
	public void handler(Packet packet, ChannelContext channelContext) throws Exception {
		//MessageLite messageLite = ((MessagePacket) packet).getMessageLite();
		//System.err.println("Client ... " + messageLite);
		// Tio.send(channelContext, packet);
	}

	@Override
	public Packet heartbeatPacket() {
		return null;// new ClientPacket();
	}

}
