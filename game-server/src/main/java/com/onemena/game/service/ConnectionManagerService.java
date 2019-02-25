package com.onemena.game.service;

import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.nutz.ioc.loader.annotation.IocBean;
import org.tio.core.ChannelContext;

import com.google.common.collect.Maps;
import com.onemena.game.core.connection.IMConnection;

/**
 * 长连接管理器
 */
@IocBean
public class ConnectionManagerService {

	private final AtomicInteger mUniqueGenerator = new AtomicInteger(0);
	private final Map<Integer, IMConnection> mConnections = Maps.newConcurrentMap();

	public IMConnection create(ChannelContext ctx) {
		int id = mUniqueGenerator.incrementAndGet();
		IMConnection conn = new IMConnection(id, ctx);
		mConnections.put(id, conn);
		ctx.setAttribute(IMConnection.ATTR_CONN_ID, id);
		return conn;
	}

	public void remove(int id) {
		IMConnection conn = mConnections.remove(id);
		if (conn != null) {
			conn.notifyRemoved();
		}
	}

	public void remove(IMConnection conn) {
		if (conn != null) {
			remove(conn.getId());
		}
	}

	public void remove(ChannelContext ctx) {
		remove(find(ctx));
	}

	public IMConnection get(int id) {
		return mConnections.get(id);
	}

	public IMConnection find(ChannelContext ctx) {
		return get((int) ctx.getAttribute((IMConnection.ATTR_CONN_ID)));
	}

	/**
	 * mark 不要使用Map里面的size()，每次获取都需要计算
	 *
	 * @return
	 */
	public int count() {
		return mConnections.size();
	}
}
