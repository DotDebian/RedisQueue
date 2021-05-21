package space.debian.RedisQueue.objects.messages;

import lombok.Getter;
import lombok.Setter;

public abstract class Message {
	@Getter @Setter
    protected MessageType messageType;
}
