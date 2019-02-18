package com.onemena.game.service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.nutz.ioc.Ioc;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.lang.Lang;
import org.nutz.lang.Strings;
import org.nutz.log.Log;
import org.nutz.log.Logs;

import com.google.common.collect.Maps;
import com.google.protobuf.MessageLite;
import com.onemena.game.annotation.HandlerMapping;
import com.onemena.game.custom.handler.DataHandler;
import com.onemena.game.custom.handler.HandlerDataModal;
import com.onemena.game.custom.handler.buff.InnerQueueBuffer;
import com.onemena.game.custom.handler.buff.MessageBuffer;
import com.onemena.game.utils.ClassUtil;

@IocBean(create = "init", depose = "depose")
public class HandlerService<T extends MessageLite> {

	private final static Log log = Logs.get();

	private final Map<String, DataHandler<T>> instanceCache = Maps.newConcurrentMap();

	private final MessageBuffer<T> buffer = new InnerQueueBuffer<T>();

	@Inject
	private Ioc ioc;

	private int THREAD_POOL_SIZE = 4;

	private final ExecutorService executorService;

	public HandlerService() {
		executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE, new ThreadFactory() {
			private AtomicInteger i = new AtomicInteger(0);

			@Override
			public Thread newThread(Runnable r) {
				Thread thread = new Thread(r);
				// 自定义线程池中的线程名
				thread.setName("hand-data-thread-" + i.addAndGet(1));
				return thread;
			}
		});

	}

	public void offer(HandlerDataModal<T> handlerDataModal) {
		this.buffer.offer(handlerDataModal);
	}
	@SuppressWarnings("unchecked")
	public void init() {
		try {
			List<Class<?>> classes = ClassUtil.getAllClassBySubClass(DataHandler.class, true, "com.onemena.game.custom.handler");
			for (Class<?> claz : classes) {
				HandlerMapping annotation = claz.getAnnotation(HandlerMapping.class);
				if (Lang.isNotEmpty(annotation)) {
					String name = Strings.lowerFirst(annotation.value() + "Handler");
					instanceCache.put(annotation.value(), ioc.get(DataHandler.class, name));
				}
			}
			System.out.println("handler init success handler Map: " + instanceCache);
		} catch (Exception e) {
			log.error(e);
		}
		doHandler();
	}

	private void doHandler() {
		for (int i = 0; i < THREAD_POOL_SIZE; i++) {
			executorService.execute(new Runnable() {
				@Override
				public void run() {
					while (!Thread.currentThread().isInterrupted()) {
						handler();
					}
				}

				private void handler() {
					HandlerDataModal<T> handlerDataModal = buffer.poll();
					if (handlerDataModal != null) {
						T messageLite = handlerDataModal.getMessageLite();
						DataHandler<T> handler = getHandlerInstance(messageLite.getClass().getSimpleName());
						handler.handler(messageLite, handlerDataModal.getCtx());
					}
				}

			});
		}

	}

	public void depose() {
		executorService.shutdown();
	}

	public DataHandler<T> getHandlerInstance(String name) {
		return instanceCache.get(name);
	}
}
