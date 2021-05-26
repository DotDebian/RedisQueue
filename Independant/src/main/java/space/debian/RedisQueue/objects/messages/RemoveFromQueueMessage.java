package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class RemoveFromQueueMessage extends Message {

    public RemoveFromQueueMessage(String playerName) {
        this.playerName = playerName;
        this.messageType = MessageType.REMOVE_FROM_QUEUE;
    }

    private String playerName;

}
