package space.debian.BungeeExtension.listeners;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import space.debian.BungeeExtension.Main;
import space.debian.BungeeExtension.managers.JedisManager;
import space.debian.RedisQueue.objects.messages.RemoveFromQueueMessage;

public class PlayerListener implements Listener {

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        JedisManager.getInstance().publish("Extension", Main.getGson().toJson(
                new RemoveFromQueueMessage(e.getPlayer().getName())
        ));
    }

}
