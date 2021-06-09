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
        instance = this;

        getLogger().info("Loading configuration.");
        configuration = initConfig();

        getLogger().info("Instancing the Jedis manager.");

        try {
            jedisManager = new JedisManager();
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (configuration.isHub) {
            getLogger().info("Instancing the Hub command since the server is defined as a hub.");
            getCommand("hub").setExecutor(new Hub());
			getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        } else {
            getLogger().info("Instancing the ClientData update timer since the server is not defined as a Hub.");
            dataUpdateTimer = new Timer();
            dataUpdateTimer.scheduleAtFixedRate(new TimerTask() {
                @Override
                public void run() {
                    getJedisManager().publish("Extension", Main.getGson().toJson(
                            new ClientUpdateMessage(configuration.serverId, getServer().getOnlinePlayers().size(), getServer().getMaxPlayers(), getServer().hasWhitelist())
                    ));
                }
            }, 10000L, 10000L);
        }

        getLogger().info("Suscribing to the server data message channel.");
        jedisManager.registerSuscriber(ServerChannel.class, "server_data");
    }

    @Override
    public void onDisable() {
        JedisManager.getInstance().getSuscribersThreads().forEach(Thread::stop);
    }

    public Config initConfig() {
        Config res;
        getDataFolder().mkdir();
        String path = getDataFolder() + File.separator + "config.json";
        BufferedReader bufferedReader;
        try {
            bufferedReader = new BufferedReader(new FileReader(path));
        } catch (FileNotFoundException e) {
            getLogger().info("Creating a new configuration file.");
            res = new Config();
            try {
                Writer writer = new FileWriter(path);
                gson.toJson(res, writer);
                writer.close();
            } catch (IOException ioException) {
                getLogger().severe("Can't write configuration file to data folder.");
            }
            getLogger().info("Configuration file saved.");
            return res;
        }

        res = gson.fromJson(bufferedReader, Config.class);
        return res;
    }


}
