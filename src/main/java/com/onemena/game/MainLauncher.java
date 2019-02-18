package com.onemena.game;

import org.nutz.boot.NbApp;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

@IocBean(create = "init", depose = "depose")
public class MainLauncher {

	@Inject
	protected PropertiesProxy conf;

	@At("/")
	@Ok("->:/index.html")
	public void index() {
	}

	public void init() {
	}

	public static void main(String[] args) throws Exception {
		new NbApp().setArgs(args).setPrintProcDoc(true).run();
	}

}
