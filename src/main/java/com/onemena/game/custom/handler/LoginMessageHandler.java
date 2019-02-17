package com.onemena.game.custom.handler;

import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.LoginMessage;
import com.onemena.game.socket.handler.MessagePacket;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
@HandlerMapping("LoginMessage")
public class LoginMessageHandler extends AbstractDataHandler<LoginMessage> {
	@Override
	public void onEvent(LoginMessage loginMessage, ChannelContext ctx) throws Exception {
		System.err.println(loginMessage.getName() + "," + loginMessage.getPassword());
		LoginMessage backMsg = LoginMessage.newBuilder().setName("Rekoe.com").setPassword("12345678").build();
		Tio.send(ctx, new MessagePacket(backMsg));
	}
}