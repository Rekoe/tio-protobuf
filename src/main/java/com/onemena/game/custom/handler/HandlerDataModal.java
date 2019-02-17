package com.onemena.game.custom.handler;

import org.tio.core.ChannelContext;

import com.google.protobuf.MessageLite;

/**
 * 处理器处理数据的封装
 *
 * @author nonpool
 * @version 1.0
 * @since 2018/3/16
 */
public class HandlerDataModal {

	public HandlerDataModal() {
	}

	public HandlerDataModal(MessageLite messageLite, ChannelContext ctx) {
		this.messageLite = messageLite;
		this.ctx = ctx;
	}

	private MessageLite messageLite;

	private ChannelContext ctx;

	public MessageLite getMessageLite() {
		return messageLite;
	}

	public void setMessageLite(MessageLite messageLite) {
		this.messageLite = messageLite;
	}

	public ChannelContext getCtx() {
		return ctx;
	}

	public void setCtx(ChannelContext ctx) {
		this.ctx = ctx;
	}

	@Override
	public String toString() {
		return "HandlerDataModal{" + "messageLite=" + messageLite + ", ctx=" + ctx + '}';
	}
}
