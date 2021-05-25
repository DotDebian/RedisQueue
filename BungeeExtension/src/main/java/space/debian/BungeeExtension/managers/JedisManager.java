package space.debian.BungeeExtension.managers;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import space.debian.BungeeExtension.Main;

public class JedisManager {

    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    @Getter
    JedisPool jedisPool;

    @Getter
    private static JedisManager instance;

    public JedisManager() throws Exception {

        instance = this;
        jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        try {
            Jedis jedis = jedisPool.getResource();
            Main.getInstance().getLogger().info("Successfully connected to Jedis.");
        } catch (JedisConnectionException e) {
            throw new Exception("Redis server is unavailable.");
        }
    }

    public void registerSuscriber(Class<? extends JedisPubSub> subscriber, String... channels) {
        try {

            JedisPubSub suscriberInstance = subscriber.newInstance();
            String threadName = subscriber.getSimpleName() + "SubscriberThread";

            Main.getInstance().getLogger().info("Redis channel \"" + channels[0] + "\" successfully suscribed.");
            new Thread(() -> jedisPool.getResource().subscribe(suscriberInstance, channels), Character.toLowerCase(threadName.charAt(0)) + threadName.substring(1)).start();
        } catch (InstantiationException | IllegalAccessException | JedisConnectionException e) {

            Main.getInstance().getLogger().warning("An error occured while instancing " + subscriber.getSimpleName() + " suscriber.");
            Main.getInstance().getLogger().warning(e.getMessage());
        }
    }

    public void publish(String channel, String message) {
        try {
            jedisPool.getResource().publish(channel, message);
        } catch (JedisConnectionException e) {

            Main.getInstance().getLogger().warning("An error occured while publishing to " + channel + ".");
            Main.getInstance().getLogger().warning(e.getMessage());
        }
    }
}
