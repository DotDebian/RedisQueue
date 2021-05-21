package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class AddToQueueMessage extends Message {

    private static MessageType type = MessageType.ADD_TO_QUEUE;
    private String playerName;
    private String serverId;

}
