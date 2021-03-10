package space.debian.RedisQueue.objects;

public class Player {

    private String name;
    private Queue currentQueue;

    public Player(String name) {
        this.name = name;
    }

    public Player(String name, Queue currentQueue) {
        this.name = name;
        this.currentQueue = currentQueue;
    }

    public void setCurrentQueue(Queue currentQueue) {
        this.currentQueue = currentQueue;
    }

    public String getName() {
        return name;
    }

    public Queue getCurrentQueue() {
        return currentQueue;
    }
}
