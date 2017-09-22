package org.eclipse.jetty.embedded;
    
import org.eclipse.jetty.websocket.api.Session;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketClose;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketConnect;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketError;
import org.eclipse.jetty.websocket.api.annotations.OnWebSocketMessage;
import org.eclipse.jetty.websocket.api.annotations.WebSocket;

@WebSocket(maxTextMessageSize = 10000000, maxBinaryMessageSize = 10000000)
public class WebSocketManager {

    private Session session;
    private ChatRoom room = ChatRoom.getInstance();

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
        room.addClient(this.session);
    }

    @OnWebSocketMessage
    public void onMessage(String message) {
        room.messageAllClients(message);
    }
}