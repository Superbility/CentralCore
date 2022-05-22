package me.superbility.centrallslescore.commands;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.customitems.CoreItem;
import me.superbility.centrallslescore.data.customitems.ItemCache;
import me.superbility.centrallslescore.data.mobs.MobCache;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class ReloadCommand {
    private Main plugin = Main.getPlugin(Main.class);

    public void executeCommand(CommandSender sender, String[] args) {
        Bukkit.getScheduler().cancelTasks(plugin);

        ItemCache.clear();
        MobCache.clear();

        new ItemCache(plugin);
        new MobCache(plugin);

        sender.sendMessage(ChatColor.colorise("&aCentralCore created by Superbility has been successfully reloaded!"));
    }
}
