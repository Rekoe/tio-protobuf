package com.onemena.game.socket.handler;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;
import org.tio.core.intf.Packet;
import org.tio.server.intf.ServerAioListener;

import com.onemena.game.service.ConnectionManagerService;

@IocBean(name = "serverAioListener")
public class GameListener implements ServerAioListener {

	@Inject
	private ConnectionManagerService connectionManagerService;

	@Override
	public void onAfterConnected(ChannelContext channelContext, boolean isConnected, boolean isReconnect) throws Exception {
		channelContext.setAttribute("Connected", true);
		connectionManagerService.create(channelContext);
	}

	@Override
	public void onAfterDecoded(ChannelContext channelContext, Packet packet, int packetSize) throws Exception {

	}

	@Override
	public void onAfterReceivedBytes(ChannelContext channelContext, int receivedBytes) throws Exception {

	}

	@Override
	public void onAfterSent(ChannelContext channelContext, Packet packet, boolean isSentSuccess) throws Exception {

	}

	@Override
	public void onAfterHandled(ChannelContext channelContext, Packet packet, long cost) throws Exception {

	}

	@Override
	public void onBeforeClose(ChannelContext channelContext, Throwable throwable, String remark, boolean isRemove) throws Exception {
		connectionManagerService.remove(channelContext);
	}

}
