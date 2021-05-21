package space.debian.RedisQueue.objects;

public class Player {

    private String name;
    private ServerQueue currentQueue = null;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, ServerQueue currentQueue) {
        this.name = name;
        this.currentQueue = currentQueue;
    }

    public void setCurrentQueue(ServerQueue currentQueue) {
        this.currentQueue = currentQueue;
    }

    public String getName() {
        return name;
    }

    public ServerQueue getCurrentQueue() {
        return currentQueue;
    }

    public boolean isInQueue() {
        return currentQueue != null;
    }
}
