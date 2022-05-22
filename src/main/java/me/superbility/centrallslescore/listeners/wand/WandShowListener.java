package me.superbility.centrallslescore.listeners.wand;

import com.cryptomorin.xseries.XMaterial;
import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.customitems.wand.WandItem;
import me.superbility.centrallslescore.data.mobs.CoreMob;
import me.superbility.centrallslescore.data.mobs.MobCache;
import me.superbility.centrallslescore.utils.ChatColor;
import me.superbility.centrallslescore.utils.LocationSerialiser;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.List;

public class WandShowListener implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    private String block = plugin.getConfig().getString("wand.display.block");
    private int time = plugin.getConfig().getInt("wand.display.time");

    @EventHandler
    private void onRightClick(PlayerInteractEvent e) {
        ItemStack item = e.getItem();
        if((e.getAction().equals(Action.RIGHT_CLICK_AIR) || e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) && e.getPlayer().isSneaking()) {
            if(WandItem.isWand(item)) {
                e.setCancelled(true);

                String identifier = WandItem.getType(item);
                CoreMob mob = MobCache.mobs.get(identifier);

                Material material = XMaterial.matchXMaterial(block).get().parseMaterial();
                List<String> locations = mob.getConfig().getStringList("spawning.locations");

                if (!locations.isEmpty()) {
                    HashMap<Location, ItemStack> locationBlocks = new HashMap<>();

                    for (String s : locations) {
                        Location loc = LocationSerialiser.getLocationFromString(s);

                        ItemStack i = new ItemStack(loc.getBlock().getType());
                        i.setData(loc.getBlock().getState().getData());

                        locationBlocks.put(loc, i);
                        loc.getBlock().setType(material);
                    }

                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            for (Location loc : locationBlocks.keySet()) {
                                loc.getBlock().setType(locationBlocks.get(loc).getType());
                                loc.getBlock().setData(locationBlocks.get(loc).getData().getData());
                            }
                        }
                    }.runTaskLater(plugin, 20 * time);
                }
            }
        }
    }
}