package space.debian.BungeeExtension;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Getter;
import org.bukkit.plugin.java.JavaPlugin;
import space.debian.BungeeExtension.commands.Hub;
import space.debian.BungeeExtension.managers.JedisManager;

import java.io.*;

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

    @Override
    public void onEnable() {

        getLogger().info("Instancing the Jedis manager.");

        try {
            jedisManager = new JedisManager();
        } catch (Exception e) {
            getLogger().severe(e.getMessage());
        }

        configuration = initConfig();

        if (configuration.isHub)
            getCommand("hub").setExecutor(new Hub());
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
