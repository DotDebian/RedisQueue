package space.debian.BungeeExtension.managers;

import lombok.Getter;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;
import redis.clients.jedis.exceptions.JedisConnectionException;
import space.debian.BungeeExtension.Main;

import java.util.ArrayList;

public class JedisManager {

    final JedisPoolConfig poolConfig = new JedisPoolConfig();
    @Getter
    JedisPool jedisPool;
    @Getter
    ArrayList<Thread> suscribersThreads = new ArrayList<>();

    @Getter
    private static JedisManager instance;

    public JedisManager() throws Exception {

        instance = this;
        poolConfig.setMaxWaitMillis(2000);
        if (Main.getInstance().getConfiguration().getRedisPassword() != null)
            jedisPool = new JedisPool(poolConfig, Main.getInstance().getConfiguration().redisHost, Main.getInstance().getConfiguration().redisPort, 2000, Main.getInstance().getConfiguration().redisPassword);
        else
            jedisPool = new JedisPool(poolConfig, Main.getInstance().getConfiguration().redisHost, Main.getInstance().getConfiguration().redisPort);
        try {
            Jedis jedis = jedisPool.getResource();
            Main.getInstance().getLogger().info("Successfully connected to Jedis.");
            jedis.close();
        } catch (JedisConnectionException e) {
            throw new Exception("Redis server is unavailable.");
        }
    }

    public void registerSuscriber(Class<? extends JedisPubSub> subscriber, String... channels) {
        try {

            JedisPubSub suscriberInstance = subscriber.newInstance();
            String threadName = subscriber.getSimpleName() + "SubscriberThread";

            Main.getInstance().getLogger().info("Redis channel \"" + channels[0] + "\" successfully suscribed.");
            Thread t = new Thread(() -> jedisPool.getResource().subscribe(suscriberInstance, channels));
            suscribersThreads.add(t);
            t.start();
        } catch (InstantiationException | IllegalAccessException | JedisConnectionException e) {

            Main.getInstance().getLogger().warning("An error occured while instancing " + subscriber.getSimpleName() + " suscriber.");
            Main.getInstance().getLogger().warning(e.getMessage());
        }
    }

    public void publish(String channel, String message) {
        try {
            Jedis j = jedisPool.getResource();
            j.publish(channel, message);
            j.close();
        } catch (JedisConnectionException e) {

            Main.getInstance().getLogger().warning("An error occured while publishing to " + channel + ".");
            Main.getInstance().getLogger().warning(e.getMessage());
        }
    }
}
