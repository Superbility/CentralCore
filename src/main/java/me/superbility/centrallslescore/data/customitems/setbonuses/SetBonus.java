package me.superbility.centrallslescore.data.customitems.setbonuses;

import me.superbility.centrallslescore.data.coreplayer.CorePlayer;
import me.superbility.centrallslescore.data.coreplayer.CorePlayerUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SetBonus {
    public SetBonus(List<ItemStack> gear, List<PotionEffect> effects, int speed, int health, int armor) {
        this.gear = gear;
        this.effects = effects;
        this.speed = speed;
        this.health = health;
        this.armor = armor;
    }

    public List<ItemStack> getHelmet() {
        return gear;
    }
    public List<PotionEffect> getEffects() {
        return effects;
    }
    public int getSpeed() {
        return speed;
    }
    public int getHealth() {
        return health;
    }
    public int getArmor() {
        return armor;
    }

    private List<ItemStack> gear;
    private List<PotionEffect> effects;
    private int speed;
    private int health;
    private int armor;

    public void addEffects(Player player) {
        Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonus.addEffects()"); //TODO REMOVE

        CorePlayer corePlayer = CorePlayerUtils.getCorePlayer(player);

        corePlayer.addSpeed(speed);
        corePlayer.addArmor(armor);
        corePlayer.addHealth(health);

        for(PotionEffect effect : effects) {
            player.addPotionEffect(effect, true);
        }
    }

    public void removeEffects(Player player) {
        Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonus.removeEffects()"); //TODO REMOVE
        CorePlayer corePlayer = CorePlayerUtils.getCorePlayer(player);

        corePlayer.removeSpeed(speed);
        corePlayer.removeArmor(armor);
        corePlayer.removeHealth(health);

        for(PotionEffect effect : effects) {
            player.removePotionEffect(effect.getType());
        }
    }

    public boolean isApplicable(Player player) {
        List<ItemStack> gear = new ArrayList<>(Arrays.asList(player.getInventory().getArmorContents()));

        for(int i = 0; i < gear.size(); i++) {
            if(!this.gear.get(i).getType().equals(Material.AIR)) {
                if (!this.gear.get(i).isSimilar(gear.get(i))) {
                    Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonus.isApplicable() | returning false"); //TODO REMOVE
                    return false;
                }
            }
        }
        Bukkit.getConsoleSender().sendMessage("DEBUG >> Code: SetBonus.isApplicable() | returning true"); //TODO REMOVE
        return true;
    }
}