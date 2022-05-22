package me.superbility.centrallslescore.commands;

import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.data.mobs.MobCache;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WandCommand {
    public void executeCommand(CommandSender sender, String[] args) {
        if (sender instanceof Player) {
            if (args.length > 1) {
                String type = args[1];

                if(MobCache.mobs.get(type) != null) {
                    CoreMob mob = MobCache.mobs.get(type);

                    Player player = (Player) sender;
                    player.setItemInHand(mob.getWand());
                } else {
                    sender.sendMessage(ChatColor.colorise("&cNo mob with the identifier '" + type + "' was found!"));
                }
            } else {
                sender.sendMessage(ChatColor.colorise("&cUsage: /core wand [type]"));
            }
        } else {
            sender.sendMessage(ChatColor.colorise("&cYou must be a player to use that command!"));
        }
    }
}
