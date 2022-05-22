package me.superbility.centrallslescore.listeners;

import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import me.superbility.centrallslescore.data.coreplayer.CorePlayerUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class PlayerDamageListener implements Listener {

    @EventHandler
    private void onPlayerDamaged(EntityDamageEvent e) {
        if(e.getEntity() instanceof Player) {
            double damage = e.getDamage() * 5;

            CorePlayer cPlayer = CorePlayerUtils.getCorePlayer((Player) e.getEntity());

            int armor = cPlayer.getArmor();
            if(armor > 0) {
                damage = damage * (armor / (armor + 100));
            }

            e.setDamage(damage);
        }
    }
}
