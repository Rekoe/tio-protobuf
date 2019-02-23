package com.onemena.game.client.pool;

import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.tio.client.ClientChannelContext;
import org.tio.client.ClientGroupContext;
import org.tio.client.TioClient;
import org.tio.core.Node;
import org.tio.core.Tio;

import com.onemena.game.client.MClientAioHander;
import com.onemena.game.client.MClientAioListener;
import com.onemena.game.proto.RegisterRoutMessage;
import com.onemena.game.socket.handler.MessagePacket;

/**
 * Created by James on 2015/11/18 0018.
 */
public class ChannelPooledFactory extends BasePooledObjectFactory<ClientChannelContext> {

	private final String host;
	private final int port;
	private final int serverId;

	public ChannelPooledFactory(String host, int port, int serverId) {
		this.host = host;
		this.port = port;
		this.serverId = serverId;
	}

	@Override
	public ClientChannelContext create() throws Exception {
		ClientGroupContext clientGroupContext = new ClientGroupContext(new MClientAioHander(), new MClientAioListener());
		clientGroupContext.setName("game-server-cliet-to-route-sid-" + this.serverId);
		TioClient tioClient = new TioClient(clientGroupContext);
		ClientChannelContext channel = tioClient.connect(new Node(host, port));
		login(channel);
		return channel;
	}

	@Override
	public PooledObject<ClientChannelContext> wrap(ClientChannelContext channel) {
		return new DefaultPooledObject<ClientChannelContext>(channel);
	}

	public void login(ClientChannelContext channel) {
		RegisterRoutMessage registerMsg = RegisterRoutMessage.newBuilder().setServerId(serverId).build();
		MessagePacket packet = new MessagePacket(registerMsg);
		Tio.bSend(channel, packet);
		Tio.bindBsId(channel, serverId + "");
	}
}
