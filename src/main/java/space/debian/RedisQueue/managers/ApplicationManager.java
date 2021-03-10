package space.debian.RedisQueue.managers;

import space.debian.RedisQueue.objects.Player;

import java.util.HashMap;

public class ApplicationManager {

    private static ApplicationManager instance;
    private HashMap<String, Player> players = new HashMap<>();

    public ApplicationManager() {
        instance = this;
    }

    public static ApplicationManager get() {
        return instance;
    }

    public HashMap<String, Player> getPlayers() {
        return players;
    }

    public void addPlayer(String playerName) {
        players.put(playerName, new Player(playerName));
    }
}
