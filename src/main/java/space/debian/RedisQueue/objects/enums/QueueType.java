package space.debian.RedisQueue.objects.enums;

public enum QueueType {

    NormalQueue("normal", 0),
    PremiumQueue("premium", 10),
    PriorityQueue("priority", 20),
    StaffQueue("staff", 30),
    AdminQueue("admin", 40);

    private final String identifier;
    private final int priority;

    private QueueType(String identifier, int priority) {
        this.identifier = identifier;
        this.priority = priority;
    }

    public String getIdentifier() {
        return identifier;
    }

    public int getPriority() {
        return priority;
    }
}
