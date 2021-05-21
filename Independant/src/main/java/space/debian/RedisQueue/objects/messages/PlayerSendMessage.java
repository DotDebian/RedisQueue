package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class PlayerSendMessage extends Message {

    private static MessageType type = MessageType.PLAYER_SEND;
    private String playerName;
    private String serverId;

}
