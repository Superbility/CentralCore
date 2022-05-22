package me.superbility.centrallslescore.commands;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.customitems.CoreItem;
import me.superbility.centrallslescore.data.customitems.ItemCache;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class GiveItemCommand {
    private Main plugin = Main.getPlugin(Main.class);

    private List<String> itemGivenMessage = plugin.getConfig().getStringList("messages.giveCommand.itemGiven");
    private List<String> itemReceivedMessage = plugin.getConfig().getStringList("messages.giveCommand.itemReceived");

    public void executeCommand(CommandSender sender, String[] args) {
        if(args.length > 3) {
            String sPlayer = args[1];
            String sItem = args[2];
            String sAmount = args[3];

            Player player = Bukkit.getPlayerExact(sPlayer);
            if(player != null) {
                if(ItemCache.items.containsKey(sItem)) {
                    if(Integer.valueOf(sAmount) != null && Integer.valueOf(sAmount) > 0) {
                        CoreItem item = ItemCache.items.get(sItem);
                        int amount = Integer.valueOf(sAmount);

                        for(int i = 0; i < amount; ++i) {
                            player.getInventory().addItem(item.getItem());
                        }

                        for(String s : itemGivenMessage) {
                            sender.sendMessage(ChatColor.colorise(s.replace("{item}", item.getItem().getItemMeta().getDisplayName())
                                .replace("{receiver}", sPlayer)
                                .replace("{amount}", sAmount)));
                        }
                        for(String s : itemReceivedMessage) {
                            sender.sendMessage(ChatColor.colorise(s.replace("{item}", item.getItem().getItemMeta().getDisplayName())
                                    .replace("{sender}", sender.getName())
                                    .replace("{amount}", sAmount)));
                        }
                    } else {
                        sender.sendMessage(ChatColor.colorise("&cThe value of '[amount]' is invalid! It must be greater than 1"));
                    }
                } else {
                    sender.sendMessage(ChatColor.colorise("&cNo item with the identifier '" + sItem + "' was found!"));
                }
            } else {
                sender.sendMessage(ChatColor.colorise("&cNo player with the name '" + sPlayer + "' was found!"));
            }

        } else {
            MainCommand.sendHelpMessage(sender);
        }
    }
}
