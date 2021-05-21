package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor @Getter @Setter
public class ClientUpdateMessage extends Message {

    private static MessageType type = MessageType.CLIENT_UPDATE;
    private String serverId;
    private Integer currentPlayers;
    private Integer maxPlayers;
    private boolean isWhitelisted;

}
