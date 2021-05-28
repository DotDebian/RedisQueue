package space.debian.RedisQueue.objects;

import lombok.Getter;
import lombok.Setter;
import space.debian.RedisQueue.Application;
import space.debian.RedisQueue.managers.ApplicationManager;
import space.debian.RedisQueue.objects.messages.PlayerOutputMessage;
import space.debian.RedisQueue.objects.messages.PlayerSendMessage;

import java.util.ArrayList;

@Getter
@Setter
public class ServerQueue {

    private final ArrayList<Player> queuedPlayers = new ArrayList<>();
    private final String serverName;
    private boolean whitelisted;
    private int currentPlayers;
    private int maxPlayers;

    /**
     * ServerQueue is representing one queue to a specific server.
     * @param serverName The server you wanna create a queue for
     */
    public ServerQueue(String serverName) {
        this.serverName = serverName;
    }

    public void addPlayerToQueue(Player player) {
        if (getQueuedPlayers().contains(player)) {
            removeQueuedPlayer(player);
            Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerOutputMessage(player.getName(), "§a§lSuccès §f§l» §eVous avez quitté la queue pour le serveur " + serverName)));
            return;
        }

        if (player.isInQueue()) {
            Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerOutputMessage(player.getName(), "§c§lErreur §f§l» §eVous êtes déjà dans la queue pour le serveur " + player.getCurrentQueue().getServerName())));
            return;
        }

        addQueuedPlayer(player);
        Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerOutputMessage(player.getName(), "§a§lSuccès §f§l» §eVous avez rejoins la queue pour le serveur " + serverName)));
    }

    public void sendPlayer() {
        if (getCurrentPlayers() >= getMaxPlayers()) {
            queuedPlayers.forEach((p) -> Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerOutputMessage(p.getName(), "§6§lQueue §f§l» §eLe serveur " + getServerName() + " est plein."))));
            return;
        } else if (queuedPlayers.isEmpty()) {
            return;
        } else if (isWhitelisted()) {
            queuedPlayers.forEach((p) -> Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerOutputMessage(p.getName(), "§6§lQueue §f§l» §eLe serveur " + getServerName() + " est sous whitelist."))));
            return;
        }

        queuedPlayers.forEach((p) -> Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerOutputMessage(p.getName(), "§6§lQueue §f§l» §eVous êtes à la position " + (queuedPlayers.indexOf(p) + 1) + "/" + queuedPlayers.size() + "."))));

        Player toSend = queuedPlayers.get(0);
        toSend.setCurrentQueue(null);
        queuedPlayers.remove(toSend);
        ApplicationManager.get().removePlayer(toSend);

        Application.getJedisManager().publish("server_data", Application.getGson().toJson(new PlayerSendMessage(toSend.getName(), getServerName())));
    }

    public void addQueuedPlayer(Player player) {
        queuedPlayers.add(player);
        player.setCurrentQueue(this);
    }

    public void removeQueuedPlayer(Player player) {
        queuedPlayers.remove(player);
        ApplicationManager.get().removePlayer(player);
    }
}
