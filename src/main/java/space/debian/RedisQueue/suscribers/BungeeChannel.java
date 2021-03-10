package space.debian.RedisQueue.suscribers;

import redis.clients.jedis.JedisPubSub;
import space.debian.RedisQueue.Application;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

public class BungeeChannel extends JedisPubSub {

    public void onMessage(String channel, String message) {

        //TODO: do your sh*t
    }

}
