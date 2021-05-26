package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PlayerOutputMessage extends Message {

    public PlayerOutputMessage(String playerName, String message) {
        this.playerName = playerName;
        this.message = message;
        this.messageType = MessageType.PLAYER_OUTPUT;
    }

    private String playerName;
    private String message;

}
