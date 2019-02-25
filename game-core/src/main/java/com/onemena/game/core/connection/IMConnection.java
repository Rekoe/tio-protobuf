package com.onemena.game.core.connection;

import org.tio.core.ChannelContext;

public class IMConnection {

	public static final String ATTR_CONN_ID = "connection_id";
	public static final String ATTR_CONN_UIN = "connection_uin";
	public static final String ATTR_CONN_ACCOUNT = "connection_account";
	public static final String ATTR_CONN_SERVER = "connection_server";

	private long mId;
	
	private volatile boolean isClosed = false;
	
	private ChannelContext mContext;
	
	private ConnectionCloseListener closeListener;

	public IMConnection(Long id, ChannelContext ctx) {
		mId = id;
		mContext = ctx;
	}

	public long getUin() {
		return (long) mContext.getAttribute(IMConnection.ATTR_CONN_UIN);
	}

	public void setUin(long mUIN) {
		mContext.setAttribute(IMConnection.ATTR_CONN_UIN, mUIN);
	}

	public String getAccount() {
		return (String) mContext.getAttribute(IMConnection.ATTR_CONN_ACCOUNT);
	}

	public void setAccount(String account) {
		mContext.setAttribute(IMConnection.ATTR_CONN_ACCOUNT, account);
	}

	public void setServerConn(String serverConn) {
		mContext.setAttribute(IMConnection.ATTR_CONN_SERVER, serverConn);
	}

	public String getServerConn() {
		return (String) mContext.getAttribute(IMConnection.ATTR_CONN_SERVER);
	}

	public long getId() {
		return mId;
	}

	public void notifyRemoved() {
		if (closeListener != null) {
			closeListener.onClosed(this);
		}
		isClosed = true;
		mContext = null;
		closeListener = null;
	}

	public boolean validate() {
		if (isClosed()) {
			return false;
		}
		// 发送一个心跳包，同步等待success
		return true;
	}

	public boolean isActive() {
		return mContext != null && mContext.isVirtual;
	}

	public boolean isClosed() {
		return isClosed || !isActive();
	}

	public void registerCloseListener(ConnectionCloseListener listener) {
		if (closeListener != null) {
			throw new IllegalStateException("Close listener already configured");
		}
		if (isClosed()) {
			listener.onClosed(this);
		} else {
			closeListener = listener;
		}
	}

	public void removeCloseListener(ConnectionCloseListener listener) {
		if (closeListener == listener) {
			closeListener = null;
		}
	}

	public interface ConnectionCloseListener {
		public void onClosed(IMConnection connection);
	}
}
