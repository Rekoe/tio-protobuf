package com.onemena.game.worker;

import org.nutz.json.Json;
import org.nutz.lang.Lang;

import com.onemena.game.biz.bean.Presence;
import com.onemena.game.core.utils.TaskUtil;
import com.onemena.game.service.ClientSessionService;

public class PresenceWorker extends IMWorker<Presence> {

	public static final int PROCESS_DELAYER_MILLIS = 20;

	private ClientSessionService clientSessionService;
	
	public PresenceWorker(ClientSessionService clientSessionService) {
		this.clientSessionService = clientSessionService;
		TaskUtil.pool(this);
	}

	@Override
	public void process(Presence presence) {
		System.err.println(Json.toJson(presence) + "," + clientSessionService.sessions().size());
		Lang.sleep(PROCESS_DELAYER_MILLIS);
	}

}
