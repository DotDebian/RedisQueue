package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ClientUpdateMessage extends Message {

    public ClientUpdateMessage(String serverId, Integer currentPlayers, Integer maxPlayers, boolean isWhitelisted) {
        this.serverId = serverId;
        this.currentPlayers = currentPlayers;
        this.maxPlayers = maxPlayers;
        this.isWhitelisted = isWhitelisted;
        this.messageType = MessageType.CLIENT_UPDATE;
    }

    private String serverId;
    private Integer currentPlayers;
    private Integer maxPlayers;
    private boolean isWhitelisted;

}
