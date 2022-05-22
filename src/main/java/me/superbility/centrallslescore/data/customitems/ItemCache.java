package me.superbility.centrallslescore.data.customitems;

import com.cryptomorin.xseries.XMaterial;
import de.tr7zw.changeme.nbtapi.NBTItem;
import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public class ItemCache {
    public static HashMap<String, CoreItem> items = new HashMap<>(); // Stores NBT key + item in hashmap

    public ItemCache(Main plugin) {
        File folder = new File(plugin.getDataFolder() + "/items");

        loadItems(folder);
    }

    private void loadItems(File folder) {
        for (File file : folder.listFiles()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                String identifier = file.getName().replace(".yml", "");
                ItemStack item = XMaterial.valueOf(config.getString("material")).parseItem();;
                
                if(item.getType().equals(Material.LEATHER_BOOTS) || item.getType().equals(Material.LEATHER_LEGGINGS) || item.getType().equals(Material.LEATHER_CHESTPLATE) || item.getType().equals(Material.LEATHER_HELMET)) {
                    List<String> colour = Arrays.asList(config.getString("colour").split(" "));
                    LeatherArmorMeta leatherMeta = (LeatherArmorMeta) item.getItemMeta();
                    leatherMeta.setColor(Color.fromRGB(Integer.valueOf(colour.get(0)), Integer.valueOf(colour.get(1)), Integer.valueOf(colour.get(2))));
                    item.setItemMeta(leatherMeta);
                }

                String name = ChatColor.colorise(config.getString("name"));
                List<String> lore = ChatColor.colorise(config.getStringList("lore"));
                ConfigurationSection enchants = config.getConfigurationSection("enchants");
                int armor = config.getInt("stats.armor");
                int health = config.getInt("stats.health");
                int speed = config.getInt("stats.speed");

                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(name);
                meta.setLore(lore);
                if (enchants != null) {
                    for (String sEnchant : enchants.getKeys(true)) {
                        meta.addEnchant(Enchantment.getByName(sEnchant.toUpperCase(Locale.ROOT)), enchants.getInt(sEnchant), true);
                    }
                }
                item.setItemMeta(meta);

                NBTItem nbtItem = new NBTItem(item);
                nbtItem.setString("core_item", identifier);

                item = nbtItem.getItem();

                CoreItem coreItem = new CoreItem(item, identifier, armor, health, speed);

                items.put(identifier, coreItem);

            } catch (Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("Error while loading item from file: " + file.getName()));
                e.printStackTrace();
            }
        }
    }
    public static void clear() {
        items = new HashMap<>();
    }
}