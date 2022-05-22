package me.superbility.centrallslescore.listeners;

import com.sk89q.worldguard.bukkit.RegionContainer;
import com.sk89q.worldguard.bukkit.RegionQuery;
import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;
import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.data.mobs.MobCache;
import org.bukkit.Location;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.util.BlockVector;
import org.bukkit.util.Vector;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class MobSpawnListener implements Listener {
    /*
    private RegionContainer container = WorldGuardPlugin.inst().getRegionContainer();

    @EventHandler
    private void onSpawn(CreatureSpawnEvent e) {
        if(e.getEntity() instanceof LivingEntity) {
            if(e.getSpawnReason().equals(CreatureSpawnEvent.SpawnReason.NATURAL)) {
                Location loc = e.getLocation();

                RegionQuery query = container.createQuery();
                ApplicableRegionSet set = query.getApplicableRegions(loc);

                if(!set.getRegions().isEmpty()) {
                    ProtectedRegion region = set.getRegions().iterator().next();
                    String id = region.getId();

                    List<CoreMob> spawnableMobs = new ArrayList<>();
                    for(CoreMob mob : MobCache.mobs.values()) {
                        if(mob.getRegions().isEmpty() || mob.getRegions().contains(id)) {
                            spawnableMobs.add(mob);
                        }
                    }

                    if(!spawnableMobs.isEmpty()) {
                        e.setCancelled(true);

                        if(spawnableMobs.size() > 1) {
                            CoreMob mob = spawnableMobs.get(ThreadLocalRandom.current().nextInt(0, spawnableMobs.size()));
                            mob.spawnMob(loc);
                        } else {
                            CoreMob mob = spawnableMobs.get(0);
                            mob.spawnMob(loc);
                        }
                    }
                }
            }
        }
    }
     */
}
