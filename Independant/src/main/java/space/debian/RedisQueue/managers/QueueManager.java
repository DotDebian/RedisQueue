package space.debian.RedisQueue.managers;

import space.debian.RedisQueue.Application;
import space.debian.RedisQueue.objects.Player;
import space.debian.RedisQueue.objects.ServerQueue;
import space.debian.RedisQueue.objects.messages.ClientUpdateMessage;
import space.debian.RedisQueue.objects.messages.PlayerOutputMessage;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

import java.util.ArrayList;
import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

import static java.lang.Runtime.getRuntime;

public class QueueManager {

    private ArrayList<ServerQueue> serverQueues = new ArrayList<>();

    public QueueManager() {
        Timer queueTimer = new Timer();
        queueTimer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                serverQueues.forEach(ServerQueue::sendPlayer);
            }
        }, 1000L, 1000L);
        getRuntime().addShutdownHook(new Thread(queueTimer::cancel));
    }

    public void addPlayerToQueue(String destination, String playerName) {
        Optional<Player> player = Optional.ofNullable(ApplicationManager.get().getPlayers().get(playerName));

        if (!player.isPresent())
            player = Optional.of(ApplicationManager.get().addPlayer(playerName));

        Optional<ServerQueue> serverQueue = serverQueues.stream().filter(serverQueueElement -> serverQueueElement.getServerName().equalsIgnoreCase(destination)).findFirst();
        if (!serverQueue.isPresent()) {
            ServerQueue toAdd = new ServerQueue(destination);
            serverQueues.add(toAdd);
            serverQueue = Optional.of(toAdd);
        }

        serverQueue.get().addPlayerToQueue(player.get());
    }

    public void removePlayerFromQueue(String playerName) {
        Optional<Player> player = Optional.ofNullable(ApplicationManager.get().getPlayers().get(playerName));

        if (player.isPresent() && player.get().isInQueue()) {
            player.get().getCurrentQueue().removeQueuedPlayer(player.get());
        } else {
            Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerOutputMessage(playerName, "§c§lErreur §f§l» §eVous n'êtes pas en queue pour ce serveur.")));
        }
    }

    public void updateClientData(ClientUpdateMessage message) {
        Optional<ServerQueue> serverQueue = serverQueues.stream().filter(serverQueueElement -> serverQueueElement.getServerName().equalsIgnoreCase(message.getServerId())).findFirst();
        if (!serverQueue.isPresent()) {
            ServerQueue queue = new ServerQueue(message.getServerId());
            serverQueues.add(queue);
            serverQueue = Optional.of(queue);
        }
        serverQueue.get().setWhitelisted(message.isWhitelisted());
        serverQueue.get().setCurrentPlayers(message.getCurrentPlayers());
        serverQueue.get().setMaxPlayers(message.getMaxPlayers());
        ApplicationLogger.get().info("Received update from " + message.getServerId());
    }

}
