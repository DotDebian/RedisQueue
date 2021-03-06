package space.debian.RedisQueue.suscribers;

import redis.clients.jedis.JedisPubSub;
import space.debian.RedisQueue.Application;
import space.debian.RedisQueue.objects.messages.AddToQueueMessage;
import space.debian.RedisQueue.objects.messages.ClientUpdateMessage;
import space.debian.RedisQueue.objects.messages.Message;
import space.debian.RedisQueue.objects.messages.RemoveFromQueueMessage;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

public class GameServerChannel extends JedisPubSub {

    public void onMessage(String channel, String msg) {
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
                Application.getQueueManager().removePlayerFromQueue(message.getPlayerName());
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
