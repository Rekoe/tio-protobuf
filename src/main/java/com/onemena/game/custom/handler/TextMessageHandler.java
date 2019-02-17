package com.onemena.game.custom.handler;

import org.tio.core.ChannelContext;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.TextMessage;

/**
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
@HandlerMapping("TextMessage")
public class TextMessageHandler extends AbstractDataHandler<TextMessage> {
    @Override
    public void onEvent(TextMessage textMessage, ChannelContext ctx) throws Exception {

    }
}