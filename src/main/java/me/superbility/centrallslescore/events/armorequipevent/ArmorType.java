package me.superbility.centrallslescore.events.armorequipevent;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum ArmorType {
    HELMET(5),
    CHESTPLATE(6),
    LEGGINGS(7),
    BOOTS(8);

    private final int slot;

    private ArmorType(int slot) {
        this.slot = slot;
    }

    public static final ArmorType matchType(ItemStack itemStack) {
        if (itemStack != null && !itemStack.getType().equals(Material.AIR)) {
            String type = itemStack.getType().name();
            if (!type.endsWith("_HELMET") && !type.endsWith("_SKULL")) {
                if (type.endsWith("_CHESTPLATE")) {
                    return CHESTPLATE;
                } else if (type.endsWith("_LEGGINGS")) {
                    return LEGGINGS;
                } else {
                    return type.endsWith("_BOOTS") ? BOOTS : null;
                }
            } else {
                return HELMET;
            }
        } else {
            return null;
        }
    }

    public int getSlot() {
        return this.slot;
    }
}