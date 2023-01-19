package manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.google.gson.Gson;

@RestController
@CrossOrigin("*")
public class SocketHandler extends TextWebSocketHandler {
	Gson gson = new Gson();
	Map<String, CopyOnWriteArrayList<WebSocketSession>> sessionMap = new HashMap<String, CopyOnWriteArrayList<WebSocketSession>>();
	List<RoomDto> roomList = new CopyOnWriteArrayList<>();
	
	@PostMapping("/here")
	void here(@RequestBody String here) {
		System.out.println(here);
	}
	
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
		System.out.println();
	}

	@Override
	public void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		Map<String, String> data = gson.fromJson(message.getPayload(), Map.class);
		switch(data.get("event")) {
		case "makeRoom":
			for (WebSocketSession webSocketSession : sessions) {
				if (webSocketSession.isOpen()/* && !session.getId().equals(webSocketSession.getId()) */) {
					roomList.add(new RoomDto(data.get("roomName"), data.get("nickName"), new ArrayList<String>()));
					webSocketSession.sendMessage(new TextMessage(gson.toJson(roomList)));
				}
			}
			break;
		}
		System.out.println();
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
		System.out.println();
	}	
	
}
