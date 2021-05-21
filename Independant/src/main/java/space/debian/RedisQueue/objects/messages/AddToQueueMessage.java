package space.debian.RedisQueue.objects.messages;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter
public class AddToQueueMessage extends Message {

	public AddToQueueMessage(String playerName, String serverId) {
		this.playerName = playerName;
		this.serverId = serverId;
		this.messageType = MessageType.ADD_TO_QUEUE;
	}

    private String playerName;
    private String serverId;

}
