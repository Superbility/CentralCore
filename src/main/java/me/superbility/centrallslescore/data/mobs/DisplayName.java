package me.superbility.centrallslescore.data.mobs;

import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;

public class DisplayName {
    public DisplayName(LivingEntity entity, String string) {
        ArmorStand stand = (ArmorStand) entity.getWorld().spawnEntity(entity.getLocation(), EntityType.ARMOR_STAND);
        stand.setVisible(false);
        stand.setSmall(true);
        entity.setPassenger(stand);
        stand.setCustomName(string
                .replace("{health}", String.valueOf((int) entity.getHealth()))
                .replace("{maxhealth}", String.valueOf((int) entity.getMaxHealth())));
        stand.setCustomNameVisible(true);
        stand.setInvulnerable(true);
    }
}
