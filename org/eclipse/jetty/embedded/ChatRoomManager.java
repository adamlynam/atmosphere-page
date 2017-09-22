package org.eclipse.jetty.embedded;

import java.util.HashMap;
import java.util.UUID;

public class ChatRoomManager {
    private static ChatRoomManager instance = null;

    public static ChatRoomManager getInstance() {
        if (instance == null){
            instance = new ChatRoomManager();
        }

        return instance;
    }

    private HashMap<UUID, ChatRoom> rooms = new HashMap<>();

    public ChatRoom createNewRoom() {
        UUID uuid = UUID.randomUUID();
        ChatRoom room = new ChatRoom(uuid);
        rooms.put(uuid, room);
        return room;
    }

    public ChatRoom getRoom(UUID uuid) {
        return rooms.get(uuid);
    }
}
