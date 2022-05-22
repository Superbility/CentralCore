package me.superbility.centrallslescore.utils;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PlayerUtils {
    public static boolean isInventoryFull(Player player) {
        for(ItemStack item : player.getInventory().getContents()){
            if(item == null){
                return false;
            }
        }
        return true;
    }
}
