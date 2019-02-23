package com.onemena.game.client.pool;

import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.tio.client.ClientChannelContext;

public class S2RChannelFactory {

	private final static Log log = Logs.get();

	private GenericObjectPool<ClientChannelContext> pool;

	private GenericObjectPoolConfig conf = new GenericObjectPoolConfig();

	private S2RChannelFactory(String host, int port, int serverId, int maxTotal, int maxIdle) {
		conf.setMaxTotal(maxTotal);
		conf.setMaxIdle(maxIdle);
		pool = new GenericObjectPool<ClientChannelContext>(new ChannelPooledFactory(host, port, serverId), conf);
	}

	public GenericObjectPool<ClientChannelContext> getPool() {
		return pool;
	}

	private static class S2RChannelFactoryHolder {
		public S2RChannelFactory s2RHolder = new S2RChannelFactory("127.0.0.1", 9420, 1, 20, 10);
	}

	public ClientChannelContext getChannel() {
		try {
			return pool.borrowObject();
		} catch (Exception e) {
			log.error(e);
		}
		return null;
	}

	public void returnChannel(ClientChannelContext channel) {
		pool.returnObject(channel);
	}

	public static final S2RChannelFactory getS2RChannelInstance() {
		return new S2RChannelFactoryHolder().s2RHolder;
	}

	public static void main(String[] args) {
		S2RChannelFactory.getS2RChannelInstance().getChannel();
		
	}
}
