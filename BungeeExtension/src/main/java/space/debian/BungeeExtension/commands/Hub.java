package space.debian.BungeeExtension.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import space.debian.BungeeExtension.Main;
import space.debian.RedisQueue.objects.messages.AddToQueueMessage;

public class Hub implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        Main.getInstance().getJedisManager().publish("BungeeQueue", Main.getGson().toJson(
                new AddToQueueMessage(commandSender.getName(), "game")
        ));
        return true;
    }

}
