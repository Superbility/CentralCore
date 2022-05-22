package me.superbility.centrallslescore.actionbar;

import com.leonardobishop.quests.bukkit.BukkitQuestsPlugin;
import com.leonardobishop.quests.common.player.QPlayer;
import com.leonardobishop.quests.common.player.QPlayerManager;
import me.clip.placeholderapi.PlaceholderAPI;
import me.superbility.centrallslescore.Main;
import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import me.superbility.centrallslescore.utils.Actionbar;
import me.superbility.centrallslescore.utils.ChatColor;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class ActionbarHandler {
    private Main plugin;

    private String questMessage;
    private String noQuestMessage;
    private int refresh;

    public ActionbarHandler(Main plugin) {
        this.plugin = plugin;

        questMessage = ChatColor.colorise(plugin.getConfig().getString("actionbar.quest"));
        noQuestMessage = ChatColor.colorise(plugin.getConfig().getString("actionbar.noQuest"));
        refresh = plugin.getConfig().getInt("actionbar.refresh");

        startActionbar();
    }

    public void startActionbar() {
        BukkitQuestsPlugin questsPlugin = (BukkitQuestsPlugin) plugin.getServer().getPluginManager().getPlugin("Quests");
        QPlayerManager questsPluginManager = questsPlugin.getPlayerManager();

        new BukkitRunnable() {
            @Override
            public void run() {
                for(CorePlayer cPlayer : plugin.onlinePlayers) {
                    Player player = cPlayer.getPlayer();

                    int health = (int) player.getHealth();
                    int maxHealth = (int) player.getMaxHealth();
                    int armor = cPlayer.getArmor();

                    QPlayer qPlayer = questsPluginManager.getPlayer(player.getUniqueId());
                    String quest = qPlayer.getPlayerPreferences().getTrackedQuestId();

                    String tempMessage;
                    if(quest != null) {
                        tempMessage = questMessage
                                .replace("{health}", String.valueOf(health))
                                .replace("{max_health}", String.valueOf(maxHealth))
                                .replace("{armor}", String.valueOf(armor))
                                .replace("{quest}", quest);
                    } else {
                        tempMessage = noQuestMessage
                                .replace("{health}", String.valueOf(health))
                                .replace("{max_health}", String.valueOf(maxHealth))
                                .replace("{armor}", String.valueOf(armor));
                    }
                    tempMessage = PlaceholderAPI.setPlaceholders(cPlayer.getPlayer(), tempMessage);

                    Actionbar.sendActionText(player, tempMessage);
                }
            }
        }.runTaskTimer(plugin, refresh, refresh);
    }
}
