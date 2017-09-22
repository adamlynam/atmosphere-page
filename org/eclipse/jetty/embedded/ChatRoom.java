package org.eclipse.jetty.embedded;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;

public class ChatRoom {
    private static ChatRoom instance = null;

    public static ChatRoom getInstance() {
        if (instance == null){
            instance = new ChatRoom();
        }

        return instance;
    }

    ArrayList<Session> clients = new ArrayList<>();

    public void messageAllClients(String message) {
        for (Session client : clients) {
            if (client.isOpen()) {
                try {
                    client.getRemote().sendString(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                client.close();
            }
        }
    }

    public void addClient(Session remote) {
        clients.add(remote);
    }

    public void removeClient(Session remote) {
        clients.remove(remote);
    }
}
