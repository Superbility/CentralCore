package me.superbility.centrallslescore.data.customitems.wand;

import de.tr7zw.changeme.nbtapi.NBTItem;
import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class WandItem {
    private static Main plugin = Main.getPlugin(Main.class);

    public static ItemStack getWand(CoreMob mob) {
        ItemStack wand = new ItemStack(Material.BLAZE_ROD);
        wand.addUnsafeEnchantment(Enchantment.LURE, 1);
        ItemMeta meta = wand.getItemMeta();
        meta.setDisplayName(ChatColor.colorise(plugin.getConfig().getString("wand.name")).replace("[type]", mob.getIdentifier()));

        List<String> lore = new ArrayList<>();
        for(String s : plugin.getConfig().getStringList("wand.lore")) {
            lore.add(ChatColor.colorise(s).replace("[type]", mob.getIdentifier()));
        }
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        wand.setItemMeta(meta);

        NBTItem nbtItem = new NBTItem(wand);
        nbtItem.setString("wand", mob.getIdentifier());

        return nbtItem.getItem();
    }
    public static boolean isWand(ItemStack item) {
        if(item == null) return false;
        if(item.getType().equals(Material.AIR)) return false;
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.hasKey("wand");
    }
    public static String getType(ItemStack item) {
        if(item == null) return null;
        if(item.getType().equals(Material.AIR)) return null;
        NBTItem nbtItem = new NBTItem(item);
        return nbtItem.getString("wand");
    }
}
