package me.superbility.centrallslescore.data.customitems;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.inventory.ItemStack;

public class CoreItem {
    private ItemStack item;
    private int armor;
    private int health;
    private int speed;
    private String identifier;

    public CoreItem(ItemStack item, String identifier, int armor, int health, int speed) {
        this.item = item;
        this.identifier = identifier;
        this.armor = armor;
        this.health = health;
        this.speed = speed;
    }

    public ItemStack getItem() {
        return item;
    }
    public int getArmor() {
        return armor;
    }
    public int getHealth() {
        return health;
    }
    public int getSpeed() {
        return speed;
    }
    public String getIdentifier() {
        return identifier;
    }

    public static CoreItem getCoreItemFromItem(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if(nbtItem.hasKey("core_item")) {
            return ItemCache.items.get(nbtItem.getString("core_item"));
        } else {
            return null;
        }
    }
    public static boolean isCoreItem(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if(nbtItem.hasKey("core_item")) {
            return true;
        }
        return false;
    }
    public static String getItemIdentifier(ItemStack item) {
        NBTItem nbtItem = new NBTItem(item);
        if(nbtItem.hasKey("core_item")) {
            return nbtItem.getString("core_item");
        }
        return null;
    }
}
