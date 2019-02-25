package com.onemena.game.service;

import java.util.Map;

import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.google.common.collect.Maps;
import com.onemena.game.biz.bean.Presence;
import com.onemena.game.core.connection.IMConnection;
import com.onemena.game.core.session.ClientSession;
import com.onemena.game.event.PresenceEventDispatcher;

@IocBean
public class ClientSessionService {

	private final Map<String, ClientSession> mSessions = Maps.newConcurrentMap();

	@Inject
	private PresenceEventDispatcher presenceEventDispatcher;

	public void add(final ClientSession session) {
		mSessions.put(session.getUin(), session);
		presenceEventDispatcher.availableSession(session);
		session.getConnection().registerCloseListener(new IMConnection.ConnectionCloseListener() {
			public void onClosed(IMConnection connection) {
				System.out.println(mSessions.size());
				mSessions.remove(session.getUin());
				System.out.println(mSessions.size());
				session.getPresence().setMode(Presence.Mode.UNAVAILABLE.value());
				presenceEventDispatcher.unavailableSession(session);
			}
		});
	}

	public ClientSession get(String uin) {
		return mSessions.get(uin);
	}

	public Map<String, ClientSession> sessions() {
		return mSessions;
	}

}
