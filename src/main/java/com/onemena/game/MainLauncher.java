package com.onemena.game;

import java.util.concurrent.ExecutorService;

import org.nutz.boot.NbApp;
import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.mvc.annotation.At;
import org.nutz.mvc.annotation.Ok;

import com.onemena.game.custom.handler.HandlerPool;
import com.onemena.game.custom.handler.buff.InnerQueueBuffer;
import com.onemena.game.custom.handler.buff.MessageBuffer;

@IocBean(create = "init", depose = "depose")
public class MainLauncher {

	@Inject
	protected PropertiesProxy conf;

	@At("/")
	@Ok("->:/index.html")
	public void index() {
	}

	private ExecutorService executorService;

	public void init() {
		this.executorService = startHandler();
	}

	// 启动数据处理线程池
	private ExecutorService startHandler() {
		HandlerPool handlerPool = new HandlerPool();
		handlerPool.doHandler();
		return handlerPool.getExecutorService();
	}

	public void depose() {
		executorService.shutdown();
		MessageBuffer instance = InnerQueueBuffer.getInstance();
		while (instance.size() != 0) {
			System.out.println("wait message insert :" + instance.size());
			Lang.sleep(3000);
		}
	}

	public static void main(String[] args) throws Exception {
		new NbApp().setArgs(args).setPrintProcDoc(true).run();
	}

}
