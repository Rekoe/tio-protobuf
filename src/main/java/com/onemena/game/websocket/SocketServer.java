package com.onemena.game.websocket;

import java.io.IOException;
import java.nio.ByteBuffer;

import org.nutz.ioc.impl.PropertiesProxy;
import org.nutz.ioc.loader.annotation.Inject;
import org.nutz.ioc.loader.annotation.IocBean;
import org.nutz.log.Log;
import org.nutz.log.Logs;
import org.tio.core.ChannelContext;
import org.tio.core.Tio;
import org.tio.http.common.HttpRequest;
import org.tio.http.common.HttpResponse;
import org.tio.server.ServerGroupContext;
import org.tio.websocket.common.WsRequest;
import org.tio.websocket.common.WsResponse;
import org.tio.websocket.server.WsServerStarter;
import org.tio.websocket.server.handler.IWsMsgHandler;

import com.google.protobuf.CodedInputStream;
import com.google.protobuf.MessageLite;
import com.onemena.game.proto.CommonProtocol;

@IocBean(name = "socketServer")
public class SocketServer {

	private final static Log log = Logs.get();

	@Inject
	private PropertiesProxy conf;

	public static ServerGroupContext groupContext = null;
	
	private final MessageLite prototype = CommonProtocol.getDefaultInstance();

	public void start() {
		int port = conf.getInt("websocket.port", 5210);
		try {
			WsServerStarter wsServerStarter = new WsServerStarter(port, new IWsMsgHandler() {
				@Override
				public Object onText(WsRequest wsRequest, String text, ChannelContext channelContext) throws Exception {
					return null;
				}

				@Override
				public Object onClose(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {

					return null;
				}

				@Override
				public Object onBytes(WsRequest wsRequest, byte[] bytes, ChannelContext channelContext) throws Exception {
					ByteBuffer buffer = ByteBuffer.wrap(bytes);
					CodedInputStream cis = CodedInputStream.newInstance(buffer.array(), buffer.position() + buffer.arrayOffset(), buffer.remaining());
					try {
						// cis.pushLimit(cis.readRawVarint32());
						MessageLite msg = prototype.getParserForType().parseFrom(cis);
						// cis.checkLastTagWas(0);
						System.err.println(msg);
						Tio.send(channelContext, WsResponse.fromBytes(msg.toByteArray()));
						return msg;
					} catch (Exception e) {
						log.error(e);
					} finally {
						buffer.position(buffer.position() + cis.getTotalBytesRead());
					}
					return null;
				}

				@Override
				public void onAfterHandshaked(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {

				}

				@Override
				public HttpResponse handshake(HttpRequest httpRequest, HttpResponse httpResponse, ChannelContext channelContext) throws Exception {
					return httpResponse;
				}
			});
			groupContext = wsServerStarter.getServerGroupContext();
			wsServerStarter.start();
		} catch (IOException e) {
			log.error(e);
		}
	}
	public static void main(String[] args) {
		new SocketServer().start();
	}
}
