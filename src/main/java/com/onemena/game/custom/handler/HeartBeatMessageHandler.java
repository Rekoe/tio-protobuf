package com.onemena.game.custom.handler;

import org.tio.core.ChannelContext;

import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.proto.HeartBeatMessage;

/**
 * @version 1.0
 * @since 2018/1/29
 */
@HandlerMapping("HeartBeatMessage")
public class HeartBeatMessageHandler extends AbstractDataHandler<HeartBeatMessage> {

    @Override
    public void onEvent(HeartBeatMessage heartBeatMessage, ChannelContext ctx) throws Exception {

    }
}