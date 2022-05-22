package me.superbility.centrallslescore.data.mobs;

import de.tr7zw.changeme.nbtapi.NBTEntity;
import de.tr7zw.changeme.nbtapi.NBTList;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import io.netty.util.internal.ThreadLocalRandom;
import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.customitems.ItemCache;
import me.superbility.centrallslescore.data.customitems.wand.WandItem;
import me.superbility.centrallslescore.data.mobs.spawning.SpawnPoint;
import me.superbility.centrallslescore.utils.ChatColor;
import me.superbility.centrallslescore.utils.LocationSerialiser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Zombie;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class CoreMob {
    private Main plugin = Main.getPlugin(Main.class);

    private String identifier;
    private EntityType type;
    private String displayName;
    private String name;
    private int health;
    private double damage;
    private List<ItemStack> gear;
    private ItemStack handItem;
    private HashMap<ItemStack, Double> drops;
    private ItemStack wand;
    private File file;
    private YamlConfiguration config;

    public String getDisplayName() {
        return this.displayName;
    }

    public CoreMob(File file) {
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        this.identifier = file.getName().replace(".yml", "");
        this.file = file;
        this.config = config;

        try {
            this.type = EntityType.valueOf(config.getString("type"));
            this.name = ChatColor.colorise(config.getString("name"));
            this.displayName = ChatColor.colorise(config.getString("displayName"));
            this.health = config.getInt("health");
            this.damage = config.getDouble("damage");

            // GEAR
            String sHandItem = config.getString("gear.hand");
            if(!sHandItem.equalsIgnoreCase("none")) {
                if (Material.matchMaterial(sHandItem) != null) this.handItem = new ItemStack(Material.matchMaterial(sHandItem));
                else this.handItem = ItemCache.items.get(sHandItem).getItem();
            }

            gear = new ArrayList<>();
            List<String> gearValues = new ArrayList<>(Arrays.asList("boots", "leggings", "chestplate", "helmet"));

            for(String s : gearValues) {
                String sGear = config.getString("gear." + s);

                if(!sGear.equalsIgnoreCase("none")) {
                    if (Material.matchMaterial(sGear) != null) {
                        this.gear.add(new ItemStack(Material.matchMaterial(sGear)));
                    }
                    else this.gear.add(ItemCache.items.get(sGear).getItem());
                } else {
                    this.gear.add(new ItemStack(Material.AIR));
                }
            }
            // GEAR

            // DROPS
            this.drops = new HashMap<>();

            ConfigurationSection section = config.getConfigurationSection("drops");
            for(String s : section.getKeys(false)) {
                try {
                    String strMaterial = config.getString("drops." + s + ".item");
                    int amount = config.getInt("drops." + s + ".amount");
                    double chance = config.getDouble("drops." + s + ".chance");

                    ItemStack item;
                    if (Material.matchMaterial(strMaterial) != null)
                        item = new ItemStack(Material.matchMaterial(strMaterial));
                    else
                        item = ItemCache.items.get(strMaterial).getItem();
                    item.setAmount(amount);

                    this.drops.put(item, chance);
                } catch(Exception e) {
                    Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&cError while loading mob drop (&6" + s + "&c) from file - &6" + file.getName()));
                    e.printStackTrace();
                }
            }
            // DROPS

            // SPAWNING
            int spawnRate = config.getInt("spawning.rate");
            int checkRadius = config.getInt("spawning.checkRadius");
            int checkCount = config.getInt("spawning.checkCount");

            if(!config.getStringList("spawning.locations").isEmpty()) {
                for (String s : config.getStringList("spawning.locations")) {
                    Location loc = LocationSerialiser.getLocationFromString(s);
                    new SpawnPoint(plugin, loc, this, spawnRate, checkRadius, checkCount);
                }
            }
            // SPAWNING

            // WAND
            this.wand = WandItem.getWand(this);
            // WAND
        } catch (Exception e) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&cError while loading mob from file - &6" + file.getName()));
            e.printStackTrace();
        }
    }
    public void spawnMob(Location location) {
        LivingEntity entity = (LivingEntity) location.getWorld().spawnEntity(location, type);

        NBTEntity nbtent = new NBTEntity(entity);
        NBTList list = nbtent.getCompoundList("Attributes");
        for(int i = 0; i < list.size(); i++){
            NBTListCompound lc = (NBTListCompound) list.get(i);
            if(lc.getString("Name").equals("generic.attackDamage")){
                lc.setDouble("Base", this.damage);
            }
        }

        entity.setMaxHealth(health);
        entity.setHealth(health);
        entity.getEquipment().setArmorContents(gear.stream().toArray(ItemStack[]::new));
        if(this.handItem != null) entity.getEquipment().setItemInHand(this.handItem);
        entity.setCustomName(name);
        entity.setCustomNameVisible(false);
        entity.setRemoveWhenFarAway(false);

        entity.setMetadata("coremob", new FixedMetadataValue(plugin, identifier));

        new DisplayName(entity, displayName);
    }
    public static String getIdentifier(Entity entity) {
        if(entity.hasMetadata("coremob")) {
            return (String) entity.getMetadata("coremob").get(0).value();
        }
        return null;
    }
    public String getIdentifier() {
        return this.identifier;
    }
    public List<ItemStack> generateDrops() {
        List<ItemStack> drops = new ArrayList<>();

        for(ItemStack item : this.drops.keySet()) {
            double chance = this.drops.get(item);

            if(ThreadLocalRandom.current().nextDouble(0, 100) <= chance) {
                drops.add(item);
            }
        }

        return drops;
    }
    public ItemStack getWand() {
        return this.wand;
    }
    public EntityType getType() {
        return this.type;
    }
    public File getFile() {
        return this.file;
    }
    public YamlConfiguration getConfig() {
        return this.config;
    }
    public void saveConfig() {
        try {
            this.config.save(this.file);
        } catch(IOException e) {
            e.printStackTrace();
        }
    }
}
