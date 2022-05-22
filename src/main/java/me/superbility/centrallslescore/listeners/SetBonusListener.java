package me.superbility.centrallslescore.listeners;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import me.superbility.centrallslescore.data.coreplayer.CorePlayerUtils;
import me.superbility.centrallslescore.data.customitems.setbonuses.SetBonus;
import me.superbility.centrallslescore.data.customitems.setbonuses.SetBonusCache;
import me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.List;

public class SetBonusListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void onEquip(ArmorEquipEvent e) {
        CorePlayer cPlayer = CorePlayerUtils.getCorePlayer(e.getPlayer());
        Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonusListener - Activation"); //TODO REMOVE
        new BukkitRunnable() {
            @Override
            public void run() {
                List<SetBonus> removedBonuses = new ArrayList<>();
                for(SetBonus bonus : cPlayer.getBonuses()) {
                    if(!bonus.isApplicable(e.getPlayer())) {
                        Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonusListener - RemovedBonuses added " + bonus); //TODO REMOVE
                        removedBonuses.add(bonus);
                    }
                }
                if(!removedBonuses.isEmpty()) {
                    for (SetBonus bonus : removedBonuses) {
                        Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonusListener - Removed bonus " + bonus); //TODO REMOVE
                        cPlayer.removeBonus(bonus);
                    }
                }

                List<SetBonus> newBonuses = new ArrayList<>();
                for(SetBonus bonus : SetBonusCache.bonuses) {
                    if(bonus.isApplicable(e.getPlayer())) {
                        Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonusListener - NewBonuses added " + bonus); //TODO REMOVE
                        newBonuses.add(bonus);
                    }
                }
                for(SetBonus bonus : newBonuses) {
                    Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonusListener - Added bonus " + bonus); //TODO REMOVE
                    cPlayer.addBonus(bonus);
                }
            }
        }.runTaskLater(plugin, 2);
    }
}
