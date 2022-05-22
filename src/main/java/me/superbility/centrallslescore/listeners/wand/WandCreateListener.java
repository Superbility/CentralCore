package me.superbility.centrallslescore.listeners.wand;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.customitems.wand.WandItem;
import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.data.mobs.MobCache;
import me.superbility.centrallslescore.utils.ChatColor;
import me.superbility.centrallslescore.utils.LocationSerialiser;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class WandCreateListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    private List<String> locationExistsMessage = plugin.getConfig().getStringList("messages.wand.locationExists");
    private List<String> locationAddedMessage = plugin.getConfig().getStringList("messages.wand.locationCreated");

    @EventHandler
    private void onRightClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if(WandItem.isWand(item)) {
                e.setCancelled(true);

                String identifier = WandItem.getType(item);
                CoreMob mob = MobCache.mobs.get(identifier);
                String location = LocationSerialiser.getStringFromLocation(e.getClickedBlock().getLocation());

                List<String> locations = mob.getConfig().getStringList("spawning.locations");

                if(locations.contains(location)) {
                    for(String s : locationExistsMessage) {
                        e.getPlayer().sendMessage(ChatColor.colorise(s));
                    }
                    return;
                }

                locations.add(location);

                mob.getConfig().set("spawning.locations", locations);
                mob.saveConfig();

                for(String s : locationAddedMessage) {
                    e.getPlayer().sendMessage(ChatColor.colorise(s));
                }
                return;
            }
        }
    }
}