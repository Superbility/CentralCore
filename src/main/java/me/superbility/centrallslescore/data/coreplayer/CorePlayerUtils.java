package me.superbility.centrallslescore.data.coreplayer;

import me.superbility.centrallslescore.Main;
import org.bukkit.entity.Player;

public class CorePlayerUtils {
    public static Main plugin = Main.getPlugin(Main.class);

    public static CorePlayer getCorePlayer(Player player) {
        for(CorePlayer cPlayer : plugin.onlinePlayers) {
            if(cPlayer.getPlayer().equals(player)) {
                return cPlayer;
            }
        }
        return null;
    }
}
