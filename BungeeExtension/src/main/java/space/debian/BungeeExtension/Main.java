package space.debian.BungeeExtension;

import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import space.debian.BungeeExtension.managers.JedisManager;

public class Main extends JavaPlugin {

    @Getter
    private static Main instance;

    @Getter
    private JedisManager jedisManager;

    @Override
    public void onEnable() {

        getLogger().info("Instancing the Jedis manager.");

        try {
            jedisManager = new JedisManager();
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
        }

    }

}
