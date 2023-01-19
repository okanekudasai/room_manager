package manager;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

@Component
public class SocketHandler extends TextWebSocketHandler {

	List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();
	List<RoomDto> roomList = new CopyOnWriteArrayList<>();
	
	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {
		System.out.println("세션열림 : " + session.toString());
		sessions.add(session);
		System.out.print("세션목록 : ");
		for (WebSocketSession s : sessions) {
			System.out.print(sessions.indexOf(s) + " ");
		}
		System.out.println();
		System.out.println("이번세션 : " + sessions.indexOf(session));
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		System.out.println(message.getPayload());
		Gson gson = new Gson();
//		HashMap<String, String> jsonObject = (JSONObject) parser.parse(message.getPayload());
//		System.out.println(jsonObject);
//		switch(data.get("event")) {
//		case "makeRoom":
//			for (WebSocketSession webSocketSession : sessions) {
//				if (webSocketSession.isOpen() && !session.getId().equals(webSocketSession.getId())) {
//					webSocketSession.sendMessage(message);
//				}
//			}
//			break;
//		}
	}
	
	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
		System.out.println("세션종료 : " + session.toString());
		System.out.println("이번세션 : " + sessions.indexOf(session));
		sessions.remove(session);
		System.out.print("세션목록 : ");
		for (WebSocketSession s : sessions) {
			System.out.print(sessions.indexOf(s) + " ");
		}
		System.out.println();
	}	
	
}
