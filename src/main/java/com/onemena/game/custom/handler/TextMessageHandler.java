package com.onemena.game.custom.handler;

import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.TextMessage;

/**
 * @version 1.0
 * @since 2018/1/29
 */
@IocBean
@HandlerMapping("TextMessage")
public class TextMessageHandler extends AbstractDataHandler<TextMessage> {

    @Override
    public void onEvent(TextMessage textMessage, ChannelContext ctx) throws Exception {

    }
}