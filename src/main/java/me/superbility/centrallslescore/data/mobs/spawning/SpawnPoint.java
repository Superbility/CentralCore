package me.superbility.centrallslescore.data.mobs.spawning;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.utils.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SpawnPoint {
    private Main plugin;
    private Location location;
    private CoreMob mob;
    private int spawnRate;
    private int checkRadius;
    private int checkCount;

    public SpawnPoint(Main plugin, Location location, CoreMob mob, int spawnRate, int checkRadius, int checkCount) {
        this.plugin = plugin;
        this.location = location;
        this.mob = mob;
        this.spawnRate = spawnRate;
        this.checkRadius = checkRadius;
        this.checkCount = checkCount;

        /*
        Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&d&lLOCATION - " + location));
        Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&d&lMOB - " + mob));
        Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&d&lRATE - " + spawnRate));
        Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&d&lRADIUS - " + checkRadius));
        Bukkit.getConsoleSender().sendMessage(ChatColor.colorise("&d&lCOUNT - " + checkCount));
         */

        startSpawning();
    }

    private void startSpawning() {
        new BukkitRunnable() {
            @Override
            public void run() {
                List<Entity> entities = new ArrayList<>(location.getWorld().getNearbyEntities(location, checkRadius, checkRadius, checkRadius));

                int amount = 0;

                if(!entities.isEmpty() && entities != null) {
                    for (Entity ent : entities) {
                        if (ent.getType() == mob.getType()) {
                            amount++;
                        }
                    }
                }

                if(amount <= checkCount) {
                    mob.spawnMob(location.clone().add(0, 2, 0));
                }
            }
        }.runTaskTimer(plugin, 20 * spawnRate, 20 * spawnRate);
    }
}
