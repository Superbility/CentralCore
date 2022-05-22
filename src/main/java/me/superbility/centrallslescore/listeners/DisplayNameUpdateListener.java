package me.superbility.centrallslescore.listeners;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.data.mobs.MobCache;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;

public class DisplayNameUpdateListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void onDamage(EntityDamageEvent e) {
        if (CoreMob.getIdentifier(e.getEntity()) != null) {
            LivingEntity entity = (LivingEntity) e.getEntity();

            String identifier = CoreMob.getIdentifier(entity);
            String displayName = MobCache.mobs.get(identifier).getDisplayName();
            entity.getPassenger().setCustomName(displayName
                    .replace("{health}", String.valueOf((int) entity.getHealth()))
                    .replace("{maxhealth}", String.valueOf((int) entity.getMaxHealth())));
        }
    }
    @EventHandler
    private void onDeath(EntityDeathEvent e) {
        if (CoreMob.getIdentifier(e.getEntity()) != null) {
            LivingEntity entity = e.getEntity();

            entity.getPassenger().remove();

            CoreMob coreMob = MobCache.mobs.get(CoreMob.getIdentifier(entity));

            e.getDrops().clear();
            e.getDrops().addAll(coreMob.generateDrops());

            entity.removeMetadata("coremob", plugin);
        }
    }
}
