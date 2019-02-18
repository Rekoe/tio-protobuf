package com.onemena.game.socket.handler;

import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;

import com.google.protobuf.MessageLite;

@IocBean
public class GameHandler extends AbstractProtoBufHandler {

	@Override
	public void doHandler(MessageLite packet, ChannelContext channelContext) throws Exception {

	}

}
