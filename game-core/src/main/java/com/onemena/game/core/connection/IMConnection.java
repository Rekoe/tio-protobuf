package com.onemena.game.core.connection;

import org.tio.core.ChannelContext;

public class IMConnection {

	
	private long mId;
	private volatile boolean isClosed = false;
	private ChannelContext mContext;
	private ConnectionCloseListener closeListener;

	public IMConnection(Long id, ChannelContext ctx) {
		mId = id;
		mContext = ctx;
	}

	public long getId() {
		return mId;
	}
	
	public interface ConnectionCloseListener {
		public void onClosed(IMConnection connection);
	}
}
