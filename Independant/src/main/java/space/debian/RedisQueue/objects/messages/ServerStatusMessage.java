package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import space.debian.RedisQueue.objects.ServerQueue;

import java.util.ArrayList;
import java.util.HashMap;

@AllArgsConstructor @Getter @Setter
public class ServerStatusMessage extends Message {

    private static MessageType type = MessageType.SERVER_STATUS;
    private HashMap<String, ArrayList<String>> queues;

    public void addServerQueue(ServerQueue queue) {
        queues.put(queue.getServerName(), new ArrayList<>());
        queue.getQueuedPlayers().forEach(p -> queues.get(queue.getServerName()).add(p.getName()));
    }

}
