package space.debian.RedisQueue;

import space.debian.RedisQueue.managers.ApplicationManager;
import space.debian.RedisQueue.managers.JedisManager;
import space.debian.RedisQueue.managers.QueueManager;
import space.debian.RedisQueue.suscribers.BungeeChannel;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

public class Application {

    private static JedisManager jedisManager;
    private static ApplicationManager applicationManager;
    private static QueueManager queueManager;

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

        JedisManager.getInstance().registerSuscriber(BungeeChannel.class, "BungeeQueue");

        ApplicationLogger.get().info("RedisQueue is now online and listening !");
    }

}
