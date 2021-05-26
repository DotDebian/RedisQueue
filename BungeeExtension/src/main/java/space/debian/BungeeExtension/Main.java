package space.debian.BungeeExtension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import space.debian.BungeeExtension.commands.Hub;
import space.debian.BungeeExtension.managers.JedisManager;
import space.debian.BungeeExtension.suscribers.ServerChannel;
import space.debian.RedisQueue.objects.messages.ClientUpdateMessage;

import java.io.*;
import java.util.Timer;
import java.util.TimerTask;

@Getter
public class Main extends JavaPlugin {

    @Getter
    private static Main instance;
    @Getter
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .create();

    private JedisManager jedisManager;
    private Config configuration;
    private Timer dataUpdateTimer;

    @Override
    public void onEnable() {

        getLogger().info("Instancing the Jedis manager.");

        try {
            jedisManager = new JedisManager();
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
        }

        getLogger().info("Loading configuration.");
        configuration = initConfig();

        if (configuration.isHub) {
            getLogger().info("Instancing the Hub command since the server is defined as a hub.");
            getCommand("hub").setExecutor(new Hub());
        } else {
            getLogger().info("Instancing the ClientData update timer since the server is not defined as a Hub.");
            dataUpdateTimer = new Timer();
            dataUpdateTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    getJedisManager().publish("BungeeQueue", Main.getGson().toJson(
                            new ClientUpdateMessage(configuration.serverId, getServer().getMaxPlayers(), getServer().getOnlinePlayers().size(), getServer().hasWhitelist())
                    ));
                }
            }, 15000L, 15000L);
        }

        getLogger().info("Suscribing to the server data message channel.");
        jedisManager.registerSuscriber(ServerChannel.class, "server_data");
    }

    public Config initConfig() {
        Config res;
        getDataFolder().mkdir();
        String path = getDataFolder() + File.separator + "config.json";
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            getLogger().severe("Creating a new configuration file.");
            res = new Config();
            try {
                gson.toJson(res, new FileWriter(path));
            } catch (IOException ioException) {
                getLogger().severe("Can't write configuration file to data folder.");
            }
            getLogger().severe("Configuration file saved.");
            return res;
        }

        res = gson.fromJson(bufferedReader, Config.class);
        return res;
    }


}