
package com.onemena.game.custom.handler;

import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.RegisterRoutMessage;
import com.onemena.game.socket.handler.MessagePacket;

/**
 * @version 1.0
 * @since 2018/1/29
 */
@IocBean
@HandlerMapping("RegisterRoutMessage")
public class RegisterRoutMessageHandler extends AbstractDataHandler<RegisterRoutMessage> {

	@Override
	public void onEvent(RegisterRoutMessage registerRoutMessage, ChannelContext ctx) throws Exception {
		System.err.println("resv msg :" + registerRoutMessage);
		MessagePacket packet = new MessagePacket(registerRoutMessage);
		Tio.bSend(ctx, packet);
		Tio.bindBsId(ctx, String.valueOf(registerRoutMessage.getServerId()));
	}
}