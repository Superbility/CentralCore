package me.superbility.centrallslescore.commands;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.List;

public class MainCommand implements CommandExecutor {
    private static Main plugin = Main.getPlugin(Main.class);
    private GiveItemCommand giveItemCmd = new GiveItemCommand();
    private SpawnMobCommand spawnMobCmd = new SpawnMobCommand();
    private WandCommand wandCmd = new WandCommand();
    private ReloadCommand reloadCmd = new ReloadCommand();

    private static List<String> helpMessage = plugin.getConfig().getStringList("messages.helpCommand");

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if(cmd.getName().equalsIgnoreCase("centralcore")) {
            if(args.length > 0) {
                if(args[0].equalsIgnoreCase("giveitem")) {
                    giveItemCmd.executeCommand(sender, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("help")) {
                    sendHelpMessage(sender);
                    return true;
                }
                if(args[0].equalsIgnoreCase("spawn")) {
                    spawnMobCmd.executeCommand(sender, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("wand")) {
                    wandCmd.executeCommand(sender, args);
                    return true;
                }
                if(args[0].equalsIgnoreCase("reload")) {
                    reloadCmd.executeCommand(sender, args);
                    return true;
                }
            } else {
                sender.sendMessage("");
                sender.sendMessage(ChatColor.colorise("&6CentralCore &acreated by &9Superbility"));
                sender.sendMessage(ChatColor.colorise("&aUse &6/centralcore help &afor a full list of commands"));
                sender.sendMessage("");
            }
            return true;
        }
        return false;
    }

    public static void sendHelpMessage(CommandSender sender) {
        for(String s : helpMessage) {
            sender.sendMessage(ChatColor.colorise(s));
        }
    }
}