package space.debian.RedisQueue.objects;

import space.debian.RedisQueue.objects.enums.QueueType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

public class ServerQueue {

    private ArrayList<Queue> queues = new ArrayList<>();
    private String serverName;

    public ServerQueue(String serverName) {
        this.serverName = serverName;
        for (QueueType queueType : QueueType.values())
            queues.add(new Queue(queueType));
    }

    public void addPlayerToQueue(String queueIdentifier, Player player) {

        Optional<Queue> queue = queues.stream().filter(queueElement -> queueElement.getType().getIdentifier() == queueIdentifier).findFirst();

        if (!queue.isPresent()) {

            //TODO: SEND ERROR MESSAGE (UNKNOWN IDENTIFIER)
            return;
        }

        queue.get().addQueuedPlayer(player);
        //TODO: SEND SUCCESSFUL QUEUE MESSAGE
    }

    public ArrayList<Queue> getQueues() {
        return queues;
    }

    public String getServerName() {
        return serverName;
    }
}
