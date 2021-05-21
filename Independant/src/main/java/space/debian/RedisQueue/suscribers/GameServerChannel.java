package space.debian.RedisQueue.suscribers;

import com.google.gson.JsonSyntaxException;
import redis.clients.jedis.JedisPubSub;
import space.debian.RedisQueue.Application;
import space.debian.RedisQueue.objects.messages.*;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

public class GameServerChannel extends JedisPubSub {

    public void onMessage(String channel, String msg) {
		ApplicationLogger.get().info("Received \"" + msg + "\" coming from " + channel + ".");
        Message preMessage;
		try {
			preMessage = Application.getGson().fromJson(msg, Message.class);
		} catch (Exception e) {ApplicationLogger.get().info(e.getMessage());return;}

        switch (preMessage.getMessageType()) {

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
