package space.debian.RedisQueue.objects.messages;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ConsoleOutputMessage extends Message {

    public ConsoleOutputMessage(String serverId, String message) {
        this.serverId = serverId;
        this.message = message;
        this.messageType = MessageType.CONSOLE_OUTPUT;
    }

    private String serverId;
    private String message;

}
