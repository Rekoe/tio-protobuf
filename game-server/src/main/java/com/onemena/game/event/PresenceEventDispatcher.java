package com.onemena.game.event;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;

import com.onemena.game.biz.bean.Presence;
import com.onemena.game.core.session.ClientSession;
import com.onemena.game.service.ClientSessionService;
import com.onemena.game.worker.PresenceWorker;

@IocBean
public class PresenceEventDispatcher extends EventDispatcher<PresenceEventDispatcher.PresenceEventListener> {

	@Inject("refer:$ioc")
	protected Ioc ioc;

	@Inject
	private PresenceWorker presenceWorker;

	@IocBean(name = "presenceWorker")
	public PresenceWorker createPresenceWorker() {
		return new PresenceWorker(ioc.get(ClientSessionService.class));
	}

	public void availableSession(ClientSession session) {
		if (!listeners.isEmpty()) {
			for (PresenceEventListener listener : listeners) {
				listener.availableSession(session);
			}
		}
		presenceChanged(session);
	}

	public void unavailableSession(ClientSession session) {
		if (!listeners.isEmpty()) {
			for (PresenceEventListener listener : listeners) {
				listener.unavailableSession(session);
			}
		}
		//presenceChanged(session);
	}

	private void presenceChanged(ClientSession session) {
		if (!listeners.isEmpty()) {
			for (PresenceEventListener listener : listeners) {
				listener.presenceChanged(session, session.getPresence());
			}
		}
		System.out.println("presenceChanged ... start");
		presenceWorker.push(session.getPresence());
		System.out.println("presenceChanged ... end");
	}

	public interface PresenceEventListener extends EventListener {
		public void availableSession(ClientSession session);

		public void unavailableSession(ClientSession session);

		public void presenceChanged(ClientSession session, Presence presence);

	}
}
