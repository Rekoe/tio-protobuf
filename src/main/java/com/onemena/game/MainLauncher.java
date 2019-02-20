package com.onemena.game;

import org.nutz.boot.NbApp;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.onemena.game.websocket.SocketServer;

@IocBean(create = "init")
public class MainLauncher {

	@Inject
	protected PropertiesProxy conf;

	@At("/")
	@Ok("->:/index.html")
	public void index() {
	}

	@Inject
	private SocketServer socketServer;

	public void init() {
		socketServer.start();
	}

	public static void main(String[] args) throws Exception {
		new NbApp().setArgs(args).setPrintProcDoc(true).run();
	}

}
