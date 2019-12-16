package biz.demo;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.annotation.OnConnect;
import com.corundumstudio.socketio.annotation.OnDisconnect;
import com.corundumstudio.socketio.annotation.OnEvent;

import lombok.extern.slf4j.Slf4j;
/**
 * 这个里面就维护设备连接
 * @author 泽朋
 *
 */
@Component
@Slf4j
public class SocketServer {

	/*
	 * @Value("${my.server.host}") private String host;
	 * 
	 * @Value("${my.server.port}") private Long port;
	 */

	private static Map<Long, SocketIOClient> noClientMap = new HashMap<Long, SocketIOClient>();;

	@OnConnect
	public void onConnect(SocketIOClient client) {
		String no0 = client.getHandshakeData().getSingleUrlParam("no");
		if (no0 == null) {
			return;
		}
		log.info("编号为no = {}的用户建立WebSocket连接", no0);
		long no = 0;
		try {
			no = Long.parseLong(no0);
		} catch (Exception e) {
			log.error("建立WebSocket连接，获取编号信息异常", e);
		}
		if (no != 0) {
			noClientMap.put(no, client);
		}
	}

	@OnDisconnect
	public void onDisConnect(SocketIOClient client) {
		String no0 = client.getHandshakeData().getSingleUrlParam("no");

		log.info("编号为no = {}的用户断开WebSocket连接", no0);

		long no = 0;
		try {
			no = Long.parseLong(no0);
		} catch (Exception e) {
			log.error("建立WebSocket连接，获取编号信息异常");
		}
		if (no != 0) {
			noClientMap.remove(no, client);
		}

	}

	@OnEvent(value = "noEvent")
	public void onEvent(SocketIOClient client, Long data, AckRequest request) {
		log.info("编号no = {}的用户推送消息", data);
		if (data != null && data > 0) {
			noClientMap.put(data, client);
		}
	}

	public void toOne(Long no, String eventName, Object data) {
		SocketIOClient socketIOClient = noClientMap.get(no);
		if (socketIOClient != null) {
			try {
				// 推送消息即为调用SocketIOClient的sendEvent方法
				socketIOClient.sendEvent(eventName, data);
			} catch (Exception e) {
				log.info("推送消息给编号为no = {}的用户异常", no, e.getMessage());
			}
		}

	}

	public void toAll(String eventName, Object data) {
		for (Long no : noClientMap.keySet()) {
			toOne(no, eventName, data);
			System.out.println("推送" + no);
		}
	}
}
