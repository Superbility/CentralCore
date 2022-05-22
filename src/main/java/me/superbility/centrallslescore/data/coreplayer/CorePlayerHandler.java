package me.superbility.centrallslescore.data.coreplayer;

import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class CorePlayerHandler implements Listener {
    private Main plugin = Main.getPlugin(Main.class);

    @EventHandler
    private void onJoin(PlayerJoinEvent e) {
        CorePlayer player = new CorePlayer(e.getPlayer());
        plugin.onlinePlayers.add(player);

        new BukkitRunnable() {
            @Override
            public void run() {
                player.setupStats();
            }
        }.runTaskLater(plugin, 20);
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent e) {
        CorePlayer player = CorePlayerUtils.getCorePlayer(e.getPlayer());
        plugin.onlinePlayers.remove(player);
    }
}
