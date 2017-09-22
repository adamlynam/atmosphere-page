package org.eclipse.jetty.embedded;

import org.eclipse.jetty.websocket.api.Session;

import java.io.IOException;
import java.util.ArrayList;
import java.util.UUID;

public class ChatRoom {
    private ArrayList<Session> clients = new ArrayList<>();
    private UUID uuid;

    public ChatRoom(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

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
