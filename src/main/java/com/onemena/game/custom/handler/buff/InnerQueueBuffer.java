package com.onemena.game.custom.handler.buff;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.protobuf.MessageLite;
import com.onemena.game.custom.handler.HandlerDataModal;

/**
 * jvm内基于queue的缓存实现
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/3/16
 */
public class InnerQueueBuffer<T extends MessageLite> implements MessageBuffer<T> {

    private int MAX_LENGTH = 5000;

    private final BlockingQueue<HandlerDataModal<T>> queue = new LinkedBlockingQueue<>();


    @Override
    public boolean offer(HandlerDataModal<T> handlerDataModal) {
        if (queue.size() > MAX_LENGTH) {
            //todo 处理队列超过规定长度
            return false;
        }
        queue.offer(handlerDataModal);
        return false;
    }

    @Override
    public HandlerDataModal<T> poll() {
        try {
            //使用阻塞方法防止消费者线程空转
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int size() {
        return queue.size();
    }

}
