package space.debian.RedisQueue.managers;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

public class JedisManager {

    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    JedisPool jedisPool;

    private static JedisManager instance;

    public JedisManager() throws Exception {

        instance = this;
        jedisPool = new JedisPool(poolConfig, "localhost", 6379, 0);
        try {
            Jedis jedis = jedisPool.getResource();
            ApplicationLogger.get().info("Successfully connected to Jedis.");
        } catch (JedisConnectionException e) {
            throw new Exception("Redis server is unavailable.");
        }
    }

    public void registerSuscriber(Class<? extends JedisPubSub> subscriber, String... channels) {
        try {

            JedisPubSub suscriberInstance = subscriber.newInstance();
            String threadName = subscriber.getSimpleName() + "SubscriberThread";

            ApplicationLogger.get().info("Redis channel \"" + channels[0] + "\" successfully suscribed.");
            new Thread(() -> jedisPool.getResource().subscribe(suscriberInstance, channels), Character.toLowerCase(threadName.charAt(0)) + threadName.substring(1)).start();
        } catch (InstantiationException | IllegalAccessException | JedisConnectionException e) {

            ApplicationLogger.get().error("An error occured while instancing " + subscriber.getSimpleName() + " suscriber.");
            ApplicationLogger.get().error(e.getMessage());
        }
    }

    public void publish(String channel, String message) {
        try {
            jedisPool.getResource().publish(channel, message);
        } catch (JedisConnectionException e) {

            ApplicationLogger.get().error("An error occured while publishing to " + channel + ".");
            ApplicationLogger.get().error(e.getMessage());
        }
    }

    public JedisPool getJedisPool() {
        return jedisPool;
    }

    public static JedisManager getInstance() {
        return instance;
    }
}
