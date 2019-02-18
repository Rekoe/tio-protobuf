package com.onemena.game.socket.handler;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;

import com.google.protobuf.MessageLite;
import com.onemena.game.custom.handler.HandlerDataModal;
import com.onemena.game.service.HandlerService;

@IocBean(name = "serverAioHandler")
public class GameHandler<T extends MessageLite> extends AbstractProtoBufHandler {

	@Inject
	private HandlerService<T> handlerService;
	
	@Override
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void doHandler(MessageLite packet, ChannelContext channelContext) throws Exception {
		handlerService.offer(new HandlerDataModal(packet, channelContext));
	}

}
