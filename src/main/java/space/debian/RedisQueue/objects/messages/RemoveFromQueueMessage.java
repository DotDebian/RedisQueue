package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class RemoveFromQueueMessage extends Message {

    private static MessageType type = MessageType.REMOVE_FROM_QUEUE;
    private String playerName;
    private String serverId;

}
