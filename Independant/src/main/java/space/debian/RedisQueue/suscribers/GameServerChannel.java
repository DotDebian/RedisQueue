package space.debian.RedisQueue.suscribers;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.typeadapters.RuntimeTypeAdapterFactory;
import redis.clients.jedis.JedisPubSub;
import space.debian.RedisQueue.Application;
import space.debian.RedisQueue.managers.QueueManager;
import space.debian.RedisQueue.objects.messages.*;

public class GameServerChannel extends JedisPubSub {

    public void onMessage(String channel, String msg) {
        Message preMessage = Application.getGson().fromJson(msg, Message.class);

        switch (preMessage.type) {

            case ADD_TO_QUEUE : {
                AddToQueueMessage message = (AddToQueueMessage) preMessage;
                Application.getQueueManager().addPlayerToQueue(message.getServerId(), message.getPlayerName());
                break;
            }

            case REMOVE_FROM_QUEUE: {
                RemoveFromQueueMessage message = (RemoveFromQueueMessage) preMessage;
                Application.getQueueManager().removePlayerFromQueue(message.getServerId(), message.getPlayerName());
                break;
            }

            case CLIENT_UPDATE: {
                ClientUpdateMessage message = (ClientUpdateMessage) preMessage;
                Application.getQueueManager().updateClientData(message);
                break;
            }

        }
    }

}
