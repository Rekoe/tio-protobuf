package com.onemena.game.custom.handler.buff;

import com.google.protobuf.MessageLite;
import com.onemena.game.custom.handler.HandlerDataModal;

/**
 * MessageLite对象缓存接口
 * 为了让所有数据处理操作不阻塞netty的handler链
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/3/16
 */
public interface MessageBuffer <T extends MessageLite>{

    /**
     * 放入一个handlerData
     */
    boolean offer(HandlerDataModal<T> handlerDataModal);

    /**
     * 取出一个handlerData
     */
    HandlerDataModal<T> poll();


    /**
     * 当前buffer中数据量
     * @return
     */
    int size();
}
