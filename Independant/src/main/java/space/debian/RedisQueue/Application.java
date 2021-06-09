package space.debian.RedisQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import lombok.Getter;
import space.debian.RedisQueue.managers.ApplicationManager;
import space.debian.RedisQueue.managers.JedisManager;
import space.debian.RedisQueue.managers.QueueManager;
import space.debian.RedisQueue.objects.messages.*;
import space.debian.RedisQueue.suscribers.GameServerChannel;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

import java.io.*;
import java.util.Properties;

public class Application {

	@Getter
	private static Properties config;
	@Getter
	private static JedisManager jedisManager;
	@Getter
    private static ApplicationManager applicationManager;
	@Getter
    private static QueueManager queueManager;
	@Getter
    private static final Gson gson = new GsonBuilder()
            .setPrettyPrinting()
            .serializeNulls()
            .disableHtmlEscaping()
            .registerTypeAdapterFactory(RuntimeTypeAdapterFactory
                    .of(Message.class)
                    .registerSubtype(AddToQueueMessage.class)
                    .registerSubtype(ClientUpdateMessage.class)
                    .registerSubtype(ConsoleOutputMessage.class)
                    .registerSubtype(PlayerOutputMessage.class)
                    .registerSubtype(PlayerSendMessage.class)
                    .registerSubtype(RemoveFromQueueMessage.class)
            ).create();

    public static void main(String[] args) {
		InputStream input;
		config = new Properties();
		try {
			input = new FileInputStream("config.properties");
			config.load(input);
		} catch (FileNotFoundException e) {
			config.setProperty("redisHost", "localhost");
			config.setProperty("redisPort", "6379");
			config.setProperty("queueSpeed", "2000");
			try {
				config.store(new FileOutputStream("config.properties"), null);
			} catch (IOException ioException) {
				ioException.printStackTrace();
			}
		} catch (IOException ioException) {
			ioException.printStackTrace();
		}

		if (!config.containsKey("redisHost"))
			config.setProperty("redisHost", "127.0.0.1");
		if (!config.containsKey("redisPort"))
			config.setProperty("redisPort", "6379");
		if (!config.containsKey("queueSpeed"))
			config.setProperty("queueSpeed", "2000");


		ApplicationLogger.get().info("Instancing the Jedis manager.");

        try {
            jedisManager = new JedisManager();
        } catch (Exception e) {
            ApplicationLogger.get().error(e.getMessage());
            e.printStackTrace();
            return;
        }

        ApplicationLogger.get().info("Instancing the Application manager.");
        applicationManager = new ApplicationManager();
        ApplicationLogger.get().info("Application manager successfully instantiated.");

        ApplicationLogger.get().info("Instancing the Queue manager.");
        queueManager = new QueueManager();
        ApplicationLogger.get().info("Queue manager successfully instantiated.");

        JedisManager.getInstance().registerSuscriber(GameServerChannel.class, "Extension");

        ApplicationLogger.get().info("RedisQueue is now online and listening !");
    }

}
