package me.superbility.centrallslescore.listeners;

import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import me.superbility.centrallslescore.data.coreplayer.CorePlayerUtils;
import me.superbility.centrallslescore.data.customitems.CoreItem;
import me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

public class ArmorUnequipListener implements Listener {

    @EventHandler
    private void onEquip(ArmorEquipEvent e) {
        Bukkit.getConsoleSender().sendMessage("D"); //TODO REMOVE
        ItemStack item = e.getOldArmorPiece();

        if (item != null && item.getType() != Material.AIR) {
            Bukkit.getConsoleSender().sendMessage("E"); //TODO REMOVE

            if (CoreItem.isCoreItem(item)) {
                Bukkit.getConsoleSender().sendMessage("F"); //TODO REMOVE
                CoreItem coreItem = CoreItem.getCoreItemFromItem(item);
                CorePlayer player = CorePlayerUtils.getCorePlayer(e.getPlayer());

                int armor = coreItem.getArmor();
                int health = coreItem.getHealth();
                int speed = coreItem.getSpeed();

                player.removeArmor(armor);
                player.removeHealth(health);
                player.removeSpeed(speed);
            }
        }
    }
}
