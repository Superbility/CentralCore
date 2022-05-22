package me.superbility.centrallslescore.listeners;

import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import me.superbility.centrallslescore.data.coreplayer.CorePlayerUtils;
import me.superbility.centrallslescore.data.customitems.CoreItem;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemHeldEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ItemSwitchListener implements Listener {
    private List<Material> blacklistedItems = new ArrayList<>(Arrays.asList(
            Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
            Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
            Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
            Material.GOLD_HELMET, Material.GOLD_CHESTPLATE, Material.GOLD_LEGGINGS, Material.GOLD_BOOTS,
            Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS));

    @EventHandler
    private void onSwitch(PlayerItemHeldEvent e) {
        ItemStack oldItem = e.getPlayer().getInventory().getItem(e.getPreviousSlot());
        ItemStack newItem = e.getPlayer().getInventory().getItem(e.getNewSlot());

        if (oldItem != null && oldItem.getType() != Material.AIR) {
            if (!blacklistedItems.contains(oldItem.getType())) {
                if (CoreItem.isCoreItem(oldItem)) {
                    CoreItem coreItem = CoreItem.getCoreItemFromItem(oldItem);
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
        if (newItem != null && newItem.getType() != Material.AIR) {
            if (!blacklistedItems.contains(newItem.getType())) {
                if (CoreItem.isCoreItem(newItem)) {
                    CoreItem coreItem = CoreItem.getCoreItemFromItem(newItem);
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
}
