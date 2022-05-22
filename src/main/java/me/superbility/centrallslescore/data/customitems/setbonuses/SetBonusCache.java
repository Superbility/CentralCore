package me.superbility.centrallslescore.data.customitems.setbonuses;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.customitems.ItemCache;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetBonusCache {
    public static List<SetBonus> bonuses = new ArrayList<>();

    public SetBonusCache(Main plugin) {
        File folder = new File(plugin.getDataFolder() + "/bonuses");

        for (File file : folder.listFiles()) {
            try {
                FileConfiguration config = YamlConfiguration.loadConfiguration(file);

                int health = config.getInt("bonuses.stats.health");
                int armor = config.getInt("bonuses.stats.armor");
                int speed = config.getInt("bonuses.stats.speed");

                List<PotionEffect> effects = new ArrayList<>();
                for(String s : config.getConfigurationSection("bonuses.potions").getKeys(false)) {
                    String type = config.getString("bonuses.potions." + s + ".effect");
                    int level = config.getInt("bonuses.potions." + s + ".level");
                    boolean particles = config.getBoolean("bonuses.potions." + s + ".particles");

                    PotionEffect effect = new PotionEffect(PotionEffectType.getByName(type), 999999999, level, particles);
                    effects.add(effect);
                }

                // GEAR
                List<ItemStack> gear = new ArrayList<>();
                List<String> gearValues = new ArrayList<>(Arrays.asList("boots", "leggings", "chestplate", "helmet"));
                for(String s : gearValues) {
                    String sGear = config.getString("gear." + s);

                    if(!sGear.equalsIgnoreCase("none")) {
                        if (Material.matchMaterial(sGear) != null) {
                            gear.add(new ItemStack(Material.matchMaterial(sGear)));
                        }
                        else gear.add(ItemCache.items.get(sGear).getItem());
                    } else {
                        gear.add(new ItemStack(Material.AIR));
                    }
                }
                // GEAR

                bonuses.add(new SetBonus(gear, effects, speed, health, armor));
            } catch(Exception e) {
                Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&cError while loading setbonus from file - &6" + file.getName()));
                e.printStackTrace();
            }
        }
    }
}
