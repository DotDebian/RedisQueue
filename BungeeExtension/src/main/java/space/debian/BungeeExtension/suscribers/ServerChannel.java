package space.debian.BungeeExtension.suscribers;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import redis.clients.jedis.JedisPubSub;
import space.debian.BungeeExtension.Main;
import space.debian.RedisQueue.Application;
import space.debian.RedisQueue.objects.messages.*;
import space.debian.RedisQueue.utils.logging.ApplicationLogger;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.util.Optional;

public class ServerChannel extends JedisPubSub {

    public void onMessage(String channel, String msg) {
        ApplicationLogger.get().info("Received \"" + msg + "\" coming from " + channel + ".");
        Message preMessage;
        try {
            preMessage = Application.getGson().fromJson(msg, Message.class);
        } catch (Exception e) {ApplicationLogger.get().info(e.getMessage());return;}

        switch (preMessage.getMessageType()) {

            case PLAYER_OUTPUT: {
                PlayerOutputMessage message = (PlayerOutputMessage) preMessage;
                Optional<Player> player = Optional.ofNullable(Bukkit.getPlayer(message.getPlayerName()));
                player.ifPresent((p) -> p.sendMessage(message.getMessage()));
                break;
            }

            case CONSOLE_OUTPUT: {
                ConsoleOutputMessage message = (ConsoleOutputMessage) preMessage;
                if (Main.getInstance().getConfiguration().serverId.equalsIgnoreCase(message.getServerId()))
                    Main.getInstance().getLogger().info(message.getMessage());
                break;
            }

            case PLAYER_SEND: {
            	PlayerSendMessage message = (PlayerSendMessage) preMessage;
				Optional<Player> player = Optional.ofNullable(Bukkit.getPlayer(message.getPlayerName()));
				if (!player.isPresent())
					return;
				try {
					ByteArrayOutputStream b = new ByteArrayOutputStream();
					DataOutputStream out = new DataOutputStream(b);
					out.writeUTF("Connect");
					out.writeUTF(message.getServerId());
					player.get().sendPluginMessage(Main.getInstance(), "BungeeCord", b.toByteArray());
					b.close();
					out.close();
				} catch (Exception e) {
					Main.getInstance().getLogger().severe("Failed to send " + player.get().getName() + " to " + message.getServerId() + ".");
				}
            }

        }
    }

}
