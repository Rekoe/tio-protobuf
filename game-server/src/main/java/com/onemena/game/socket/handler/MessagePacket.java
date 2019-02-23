package com.onemena.game.socket.handler;

import org.tio.core.intf.Packet;

import com.google.protobuf.MessageLite;

public class MessagePacket extends Packet {

	private static final long serialVersionUID = -3847245090135844678L;

	private MessageLite messageLite;

	public MessagePacket() {

	}

	public MessagePacket(MessageLite messageLite) {
		this.messageLite = messageLite;
	}

	public Class<?> getMessageClass() {
		return messageLite.getClass();
	}

	public MessageLite getMessageLite() {
		return messageLite;
	}

	public void setMessageLite(MessageLite messageLite) {
		this.messageLite = messageLite;
	}

}
