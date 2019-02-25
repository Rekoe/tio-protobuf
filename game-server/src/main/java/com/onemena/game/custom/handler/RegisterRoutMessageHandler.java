
package com.onemena.game.custom.handler;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.core.connection.IMConnection;
import com.onemena.game.core.session.ClientSession;
import com.onemena.game.proto.RegisterRoutMessage;
import com.onemena.game.service.ClientSessionService;
import com.onemena.game.socket.handler.MessagePacket;

/**
 * @version 1.0
 * @since 2018/1/29
 */
@IocBean
@HandlerMapping("RegisterRoutMessage")
public class RegisterRoutMessageHandler extends AbstractDataHandler<RegisterRoutMessage> {

	@Inject
	private ClientSessionService clientSessionService;

	@Override
	public void onEvent(RegisterRoutMessage registerRoutMessage, ChannelContext ctx) throws Exception {
		clientSessionService.add(new ClientSession(String.valueOf(registerRoutMessage.getServerId()), new IMConnection(registerRoutMessage.getServerId(), ctx)));
		System.err.println("resv msg :" + registerRoutMessage);
		MessagePacket packet = new MessagePacket(registerRoutMessage);
		Tio.bSend(ctx, packet);
		Tio.bindBsId(ctx, String.valueOf(registerRoutMessage.getServerId()));
	}
}