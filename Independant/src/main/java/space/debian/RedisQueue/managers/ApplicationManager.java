package space.debian.RedisQueue.managers;

import space.debian.RedisQueue.objects.Player;

import java.util.HashMap;

public class ApplicationManager {

    private static ApplicationManager instance;

    /**
     * Mapping du pseudo du joueur & de son instance de Player.
     * 
     * @see Player
     * @see ApplicationManager#getPlayers() 
     * @see ApplicationManager#addPlayer(String playername) 
     */
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

    public Player addPlayer(String playerName) {
        Player res = new Player(playerName);
        players.put(playerName, res);
        return res;
    }
}
