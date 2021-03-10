package space.debian.RedisQueue.objects;

import space.debian.RedisQueue.objects.enums.QueueType;

import java.util.ArrayList;

public class Queue {

    private QueueType type;
    private ArrayList<Player> queuedPlayers = new ArrayList<>();

    public Queue(QueueType type) {
        this.type = type;
    }

    public QueueType getType() {
        return type;
    }

    public ArrayList<Player> getQueuedPlayers() {
        return queuedPlayers;
    }

    public void addQueuedPlayer(Player player) {
        queuedPlayers.add(player);
        player.setCurrentQueue(this);
    }

    public void removeQueuedPlayer(Player player) {
        queuedPlayers.remove(player);
        player.setCurrentQueue(null);
    }
}
