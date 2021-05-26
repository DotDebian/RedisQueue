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
public class Application {

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

        ApplicationLogger.get().info("Instancing the Jedis manager.");

        try {
            jedisManager = new JedisManager();
        } catch (Exception e) {
            ApplicationLogger.get().error(e.getMessage());
            return;
        }

        ApplicationLogger.get().info("Instancing the Application manager.");
        applicationManager = new ApplicationManager();
        ApplicationLogger.get().info("Application manager successfully instantiated.");

        ApplicationLogger.get().info("Instancing the Queue manager.");
        queueManager = new QueueManager();
        ApplicationLogger.get().info("Queue manager successfully instantiated.");

        JedisManager.getInstance().registerSuscriber(GameServerChannel.class, "BungeeQueue");

        ApplicationLogger.get().info("RedisQueue is now online and listening !");
    }

}
