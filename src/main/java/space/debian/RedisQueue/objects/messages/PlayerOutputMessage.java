package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class PlayerOutputMessage extends Message {

    private static MessageType type = MessageType.PLAYER_OUTPUT;
    private String playerName;
    private String message;

}
