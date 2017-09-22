package org.eclipse.jetty.embedded;
    
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@WebSocket(maxTextMessageSize = 10000000, maxBinaryMessageSize = 10000000)
public class WebSocketManager {

    private Session session;
    private ChatRoomManager roomManager = ChatRoomManager.getInstance();
    private ChatRoom room;

    @OnWebSocketClose
    public void onClose(int statusCode, String reason) {
        room.removeClient(session);
    }

    @OnWebSocketError
    public void onError(Throwable t) {
        System.out.println("Error: " + t.getMessage());
    }

    @OnWebSocketConnect
    public void onConnect(Session session) {
        this.session = session;
        if (session.getUpgradeRequest().getRequestURI().getPath().contains("/room/")) {
            // member path
            room = roomManager.getRoom(UUID.fromString(session.getUpgradeRequest().getRequestURI().getPath().split("/")[2]));
        }
        else {
            // host path
            room = roomManager.createNewRoom();
        }
        room.addClient(this.session);
        room.messageAllClients("{\"type\": \"room\", \"data\": \"" + room.getUuid().toString() + "\"}");
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        room.messageAllClients(message);
    }
}