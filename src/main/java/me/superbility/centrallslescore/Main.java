package me.superbility.centrallslescore;

import me.superbility.centrallslescore.actionbar.ActionbarHandler;
import me.superbility.centrallslescore.commands.MainCommand;
import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import me.superbility.centrallslescore.data.coreplayer.CorePlayerHandler;
import me.superbility.centrallslescore.data.customitems.ItemCache;
import me.superbility.centrallslescore.data.customitems.rarity.RarityCache;
import me.superbility.centrallslescore.data.customitems.setbonuses.SetBonusCache;
import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.data.mobs.MobCache;
import me.superbility.centrallslescore.events.armorequipevent.ArmorListener;
import me.superbility.centrallslescore.listeners.*;
import me.superbility.centrallslescore.listeners.wand.WandCreateListener;
import me.superbility.centrallslescore.listeners.wand.WandRemoveListener;
import me.superbility.centrallslescore.listeners.wand.WandShowListener;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Main extends JavaPlugin {
    public List<CorePlayer> onlinePlayers;

    @Override
    public void onEnable() {
        loadConfigs();

        onlinePlayers = new ArrayList<>();

        getServer().getPluginManager().registerEvents(new CorePlayerHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerDamageListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorListener(blockedMaterials()), this);
        getServer().getPluginManager().registerEvents(new ArmorEquipListener(), this);
        getServer().getPluginManager().registerEvents(new ArmorUnequipListener(), this);
        getServer().getPluginManager().registerEvents(new DisplayNameUpdateListener(), this);
        //getServer().getPluginManager().registerEvents(new MobSpawnListener(), this);
        getServer().getPluginManager().registerEvents(new ItemSwitchListener(), this);
        getServer().getPluginManager().registerEvents(new WandCreateListener(), this);
        getServer().getPluginManager().registerEvents(new WandRemoveListener(), this);
        getServer().getPluginManager().registerEvents(new WandShowListener(), this);
        getServer().getPluginManager().registerEvents(new RarityCheckListener(), this);
        getServer().getPluginManager().registerEvents(new SetBonusListener(), this);

        getCommand("centralcore").setExecutor(new MainCommand());

        new ItemCache(this);
        new MobCache(this);
        new RarityCache(this);
        new SetBonusCache(this);

        if(getServer().getPluginManager().isPluginEnabled("Quests")) {
            new ActionbarHandler(this);
        }
    }

    @Override
    public void onDisable() {
        for(World world : Bukkit.getWorlds()) {
            for(Entity ent : world.getEntities()) {
                if(ent instanceof LivingEntity) {
                    if (CoreMob.getIdentifier(ent) != null) {
                        ent.getPassenger().remove();
                        ((LivingEntity) ent).damage(999999999);
                    }
                }
            }
        }
    }

    private void loadConfigs() {
        saveDefaultConfig();

        File folder = new File(getDataFolder() + "/items");
        if(!folder.exists()) {
            if (!folder.mkdir())
                throw new RuntimeException("Could not create items folder");
        }
        File mobFolder = new File(getDataFolder() + "/mobs");
        if(!mobFolder.exists()) {
            if (!mobFolder.mkdir())
                throw new RuntimeException("could not create mobs folder");
        }
        File rarityFolder = new File(getDataFolder() + "/rarity");
        if(!rarityFolder.exists()) {
            if (!rarityFolder.mkdir())
                throw new RuntimeException("could not create rarity folder");
        }
        File bonusesFolder = new File(getDataFolder() + "/bonuses");
        if(!bonusesFolder.exists()) {
            if (!bonusesFolder.mkdir())
                throw new RuntimeException("could not create bonuses folder");
        }

        saveResource("items/exampleItem.yml", false);
        saveResource("items/exampleHelmet.yml", false);
        saveResource("items/GoldenZombieHelmet.yml", false);
        saveResource("items/GoldenZombieChestplate.yml", false);
        saveResource("items/GoldenZombieLeggings.yml", false);
        saveResource("items/GoldenZombieBoots.yml", false);
        saveResource("mobs/brute.yml", false);
        saveResource("blockedMaterials.yml", false);
        saveResource("rarity/common.yml", false);
        saveResource("bonuses/goldenZombieBonus.yml", false);
    }
    private List<String> blockedMaterials() {
        File file = new File(getDataFolder() + "/blockedMaterials.yml");
        YamlConfiguration config = YamlConfiguration.loadConfiguration(file);

        return config.getStringList("blocked-clicks");
    }
}
