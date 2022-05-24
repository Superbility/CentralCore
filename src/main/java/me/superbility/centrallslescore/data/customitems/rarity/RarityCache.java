package me.superbility.centrallslescore.data.customitems.rarity;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.customitems.ItemCache;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.HashMap;
import java.util.List;

public class RarityCache {
    public static HashMap<String, List<String>> rarityLore = new HashMap<>();
    public static HashMap<ItemStack, String> itemRarities = new HashMap<>();

    public RarityCache(Main plugin) {
        File folder = new File(plugin.getDataFolder() + "/rarity");

        for (File file : folder.listFiles()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);
                String identifier = file.getName().replace(".yml", "");

                // Add rarity to cache so the lore can be gotten easily
                rarityLore.put(identifier, ChatColor.colorise(config.getStringList("lore")));

                for(String s : config.getStringList("items")) {
                    if(ItemCache.items.containsKey(s)) { //If it is a core item
                        itemRarities.put(ItemCache.items.get(s).getItem(), identifier);
                    } else if(XMaterial.matchXMaterial(s).isPresent()) {
                        itemRarities.put(XMaterial.matchXMaterial(s).get().parseItem(), identifier);
                    } else {
                        Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&c&lNo item found with the identifier '" + s + "' in rarity '" + identifier + "'"));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static boolean itemHasRarityApplied(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);

        return nbtItem.hasKey("rarity");
    }
}
