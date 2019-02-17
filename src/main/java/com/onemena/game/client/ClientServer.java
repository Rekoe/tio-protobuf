package com.onemena.game.client;

import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.TioClient;
import org.tio.core.Node;
import org.tio.core.Tio;

import com.onemena.game.proto.LoginMessage;
import com.onemena.game.socket.handler.MessagePacket;

public class ClientServer {

	public static void main(String[] args) throws Exception {
		ClientGroupContext clientGroupContext = new ClientGroupContext(new MClientAioHander(), new MClientAioListener());
		TioClient tioClient = new TioClient(clientGroupContext);
		ClientChannelContext clientChannelContext = tioClient.connect(new Node("127.0.0.1", 9420));
		LoginMessage backMsg = LoginMessage.newBuilder().setName("Rekoe.com").setPassword("1234xxxx").build();
		Tio.send(clientChannelContext, new MessagePacket(backMsg));
	}

}
