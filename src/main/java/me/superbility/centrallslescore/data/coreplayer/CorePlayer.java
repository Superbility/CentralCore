package me.superbility.centrallslescore.data.coreplayer;

import me.superbility.centrallslescore.data.customitems.CoreItem;
import me.superbility.centrallslescore.data.customitems.setbonuses.SetBonus;
import me.superbility.centrallslescore.data.customitems.setbonuses.SetBonusCache;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class CorePlayer {
    private Player player;
    private int armor;
    private List<SetBonus> bonuses;

    public CorePlayer(Player player) {
        this.player = player;
        this.armor = 0;
        this.bonuses = new ArrayList<SetBonus>();
        this.player.setMaxHealth(100);
        this.player.setHealthScale(40);
    }

    public void setupStats() {
        for(ItemStack item : player.getInventory().getArmorContents()) {
            if (item != null && item.getType() != Material.AIR) {

                if (CoreItem.isCoreItem(item)) {
                    CoreItem coreItem = CoreItem.getCoreItemFromItem(item);

                    int armor = coreItem.getArmor();
                    int health = coreItem.getHealth();
                    int speed = coreItem.getSpeed();

                    addArmor(armor);
                    addHealth(health);
                    addSpeed(speed);
                }
            }
        }

        for(SetBonus bonus : SetBonusCache.bonuses) {
            if(bonus.isApplicable(player)) {
                addBonus(bonus);
            }
        }
    }

    public Player getPlayer() {
        return this.player;
    }
    public int getArmor() {
        return this.armor;
    }

    public List<SetBonus> getBonuses() {
        return this.bonuses;
    }
    public void addBonus(SetBonus bonus) {
        if(!bonuses.contains(bonus)) {
            this.bonuses.add(bonus);
            bonus.addEffects(player);
        }
    }
    public void removeBonus(SetBonus bonus) {
        this.bonuses.remove(bonus);
        bonus.removeEffects(player);
    }

    public void addHealth(int amount) {
        Bukkit.getConsoleSender().sendMessage("ADDED HEALTH - " + amount); //TODO REMOVE
        player.setMaxHealth(player.getMaxHealth() + amount);
    }
    public void removeHealth(int amount) {
        Bukkit.getConsoleSender().sendMessage("REMOVED HEALTH - " + amount); //TODO REMOVE
        player.setMaxHealth(player.getMaxHealth() - amount);
    }

    public void addArmor(int amount) {
        Bukkit.getConsoleSender().sendMessage("ADDED ARMOR - " + amount); //TODO REMOVE
        this.armor = this.armor + amount;
    }
    public void removeArmor(int amount) {
        Bukkit.getConsoleSender().sendMessage("REMOVED ARMOR - " + amount); //TODO REMOVE
        this.armor = this.armor - amount;
    }

    public void addSpeed(float amount) {
        Bukkit.getConsoleSender().sendMessage("ADDED SPEED - " + amount); //TODO REMOVE
        float currentSpeed = player.getWalkSpeed() * 10;
        if(currentSpeed + amount <= 10) {
            player.setWalkSpeed((currentSpeed + amount) / 10);
        }
    }
    public void removeSpeed(float amount) {
        Bukkit.getConsoleSender().sendMessage("REMOVED SPEED - " + amount); //TODO REMOVE
        float currentSpeed = player.getWalkSpeed() * 10;
        if(currentSpeed - amount >= 1) {
            player.setWalkSpeed((currentSpeed - amount) / 10);
        }
    }
}