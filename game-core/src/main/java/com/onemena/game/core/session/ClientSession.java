package com.onemena.game.core.session;

import com.onemena.game.biz.bean.Presence;
import com.onemena.game.core.connection.IMConnection;

public class ClientSession {

	private IMConnection mConnection;
	private Presence mPresence;
	// private Login mLogin;

	public ClientSession(String serverUrl, IMConnection connection) {
		mConnection = connection;
		mPresence = new Presence();
		mPresence.setMode(Presence.Mode.AVAILABLE.value());
		mConnection.setServerConn(serverUrl);
	}

	public String getUin() {
		return mConnection.getServerConn();
	}

	public IMConnection getConnection() {
		return mConnection;
	}

	public void setConnection(IMConnection mConnection) {
		this.mConnection = mConnection;
	}

	public Presence getPresence() {
		return mPresence;
	}

	public void setPresence(Presence mPresence) {
		this.mPresence = mPresence;
	}

}
