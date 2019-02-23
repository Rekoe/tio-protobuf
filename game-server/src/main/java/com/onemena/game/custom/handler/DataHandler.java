package com.onemena.game.custom.handler;

import org.tio.core.ChannelContext;

import com.google.protobuf.MessageLite;

/**
 * 消息处理接口
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/1/29
 */
public interface DataHandler<T extends MessageLite> {

    /**
     * 消息处理方法
     * @param t   消息对象
     * @param ctx
     */
    void handler(T t, ChannelContext ctx);
}
