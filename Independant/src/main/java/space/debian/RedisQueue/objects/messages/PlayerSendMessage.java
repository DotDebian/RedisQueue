package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlayerSendMessage extends Message {

    public PlayerSendMessage(String playerName, String serverId) {
        this.playerName = playerName;
        this.serverId = serverId;
        this.messageType = MessageType.PLAYER_SEND;
    }

    private String playerName;
    private String serverId;

}
