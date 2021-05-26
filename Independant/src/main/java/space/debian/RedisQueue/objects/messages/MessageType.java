package space.debian.RedisQueue.objects.messages;

public enum MessageType {
    //CLIENT -> SERVER
    ADD_TO_QUEUE,
    REMOVE_FROM_QUEUE,
    CLIENT_UPDATE,

    //SERVER -> CLIENT
    PLAYER_OUTPUT,
    CONSOLE_OUTPUT,
    PLAYER_SEND,
}
