package space.debian.RedisQueue.managers;

import space.debian.RedisQueue.objects.Player;
import space.debian.RedisQueue.objects.Queue;
import space.debian.RedisQueue.objects.ServerQueue;

import java.util.ArrayList;
import java.util.Optional;

public class QueueManager {

    private ArrayList<ServerQueue> serverQueues = new ArrayList<>();

    public void addPlayerToQueue(String destination, String queueIdentifier, String playerName) {

        Optional<Player> player = Optional.ofNullable(ApplicationManager.get().getPlayers().get(playerName));
        if (player.isPresent() && player.get().getCurrentQueue() != null) {

            //TODO: SEND ERROR MESSAGE
            return;
        } else if (!player.isPresent()) {
            ApplicationManager.get().addPlayer(playerName);
        }

        Optional<ServerQueue> serverQueue = serverQueues.stream().filter(serverQueueElement -> serverQueueElement.getServerName().equalsIgnoreCase(destination)).findFirst();
        if (!serverQueue.isPresent())
            serverQueues.add(new ServerQueue(destination));

        serverQueue.get().addPlayerToQueue(queueIdentifier, player.get());
    }

}
