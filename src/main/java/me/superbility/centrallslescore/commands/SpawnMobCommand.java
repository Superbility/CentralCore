package me.superbility.centrallslescore.commands;

import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.data.mobs.MobCache;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpawnMobCommand {
    public void executeCommand(CommandSender sender, String[] args) {
        if (args.length > 1) {
            if (sender instanceof Player) {
                String type = args[1];

                if (MobCache.mobs.get(type) != null) {
                    CoreMob mob = MobCache.mobs.get(type);
                    mob.spawnMob(((Player) sender).getLocation());
                } else {
                    sender.sendMessage(ChatColor.colorise("&cNo CoreMob with the identifier '" + type + "' was found!"));
                }
            } else {
                sender.sendMessage(ChatColor.colorise("&cYou must be a player to use this command!"));
            }
        } else {
            sender.sendMessage(ChatColor.colorise("&cUsage: /centralcore spawn {coremob}"));
        }
    }
}
