package me.superbility.centrallslescore.listeners;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.superbility.centrallslescore.data.customitems.CoreItem;
import me.superbility.centrallslescore.data.customitems.rarity.RarityCache;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class RarityCheckListener implements Listener {
    @EventHandler
    private void onPickup(PlayerPickupItemEvent e) {
        ItemStack item = e.getItem().getItemStack();

        if(!RarityCache.itemHasRarityApplied(item)) {
            if (RarityCache.itemRarities.containsKey(item)) {
                String rarity = RarityCache.itemRarities.get(item);

                ItemMeta meta = item.getItemMeta();

                List<String> lore = meta.getLore();
                for(String s : RarityCache.rarityLore.get(rarity)) {
                    lore.add(s);
                }
                meta.setLore(lore);
                item.setItemMeta(meta);

                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setString("rarity", rarity);

                item = nbtItem.getItem();

                e.getItem().setItemStack(item);
            }
        }
    }
}
