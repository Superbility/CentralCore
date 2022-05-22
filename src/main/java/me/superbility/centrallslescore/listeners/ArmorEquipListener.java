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

public class ArmorEquipListener implements Listener {

    @EventHandler
    private void onEquip(ArmorEquipEvent e) {
        Bukkit.getConsoleSender().sendMessage("A"); //TODO REMOVE
        ItemStack item = e.getNewArmorPiece();

        if (item != null && item.getType() != Material.AIR) {
            Bukkit.getConsoleSender().sendMessage("B"); //TODO REMOVE

            if (CoreItem.isCoreItem(item)) {
                Bukkit.getConsoleSender().sendMessage("C"); //TODO REMOVE
                CoreItem coreItem = CoreItem.getCoreItemFromItem(item);
                CorePlayer player = CorePlayerUtils.getCorePlayer(e.getPlayer());

                int armor = coreItem.getArmor();
                int health = coreItem.getHealth();
                int speed = coreItem.getSpeed();

                player.addArmor(armor);
                player.addHealth(health);
                player.addSpeed(speed);
            }
        }
    }
}
