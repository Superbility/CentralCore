package me.superbility.centrallslescore.events.armorequipevent;

import me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent.EquipMethod;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event.Result;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.*;
import org.bukkit.event.inventory.InventoryType.SlotType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerItemBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Iterator;
import java.util.List;

public class ArmorListener implements Listener {
    private final List<String> blockedMaterials;

    public ArmorListener(List<String> blockedMaterials) {
        this.blockedMaterials = blockedMaterials;
    }

    @EventHandler (priority = EventPriority.HIGH)
    public final void inventoryClick(InventoryClickEvent e) {
        boolean shift = false;
        boolean numberkey = false;
        if (!e.isCancelled()) {
            if (e.getAction() != InventoryAction.NOTHING) {
                if (e.getClick().equals(ClickType.SHIFT_LEFT) || e.getClick().equals(ClickType.SHIFT_RIGHT)) {
                    shift = true;
                }

                if (e.getClick().equals(ClickType.NUMBER_KEY)) {
                    numberkey = true;
                }

                if (e.getSlotType() == SlotType.ARMOR || e.getSlotType() == SlotType.QUICKBAR || e.getSlotType() == SlotType.CONTAINER) {
                    if (e.getInventory() == null || e.getInventory().getType().equals(InventoryType.PLAYER)) {
                        if (e.getInventory().getType().equals(InventoryType.CRAFTING) || e.getInventory().getType().equals(InventoryType.PLAYER)) {
                            if (e.getWhoClicked() instanceof Player) {
                                ArmorType newArmorType = ArmorType.matchType(shift ? e.getCurrentItem() : e.getCursor());
                                if (shift || newArmorType == null || e.getRawSlot() == newArmorType.getSlot()) {
                                    if (shift) {
                                        newArmorType = ArmorType.matchType(e.getCurrentItem());
                                        if (newArmorType != null) {
                                            boolean equipping = true;
                                            if (e.getRawSlot() == newArmorType.getSlot()) {
                                                equipping = false;
                                            }

                                            label167: {
                                                if (newArmorType.equals(ArmorType.HELMET)) {
                                                    if (equipping) {
                                                        if (this.isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) {
                                                            break label167;
                                                        }
                                                    } else if (!this.isAirOrNull(e.getWhoClicked().getInventory().getHelmet())) {
                                                        break label167;
                                                    }
                                                }

                                                if (newArmorType.equals(ArmorType.CHESTPLATE)) {
                                                    if (equipping) {
                                                        if (this.isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) {
                                                            break label167;
                                                        }
                                                    } else if (!this.isAirOrNull(e.getWhoClicked().getInventory().getChestplate())) {
                                                        break label167;
                                                    }
                                                }

                                                if (newArmorType.equals(ArmorType.LEGGINGS)) {
                                                    if (equipping) {
                                                        if (this.isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) {
                                                            break label167;
                                                        }
                                                    } else if (!this.isAirOrNull(e.getWhoClicked().getInventory().getLeggings())) {
                                                        break label167;
                                                    }
                                                }

                                                if (!newArmorType.equals(ArmorType.BOOTS)) {
                                                    return;
                                                }

                                                if (equipping) {
                                                    if (!this.isAirOrNull(e.getWhoClicked().getInventory().getBoots())) {
                                                        return;
                                                    }
                                                } else if (this.isAirOrNull(e.getWhoClicked().getInventory().getBoots())) {
                                                    return;
                                                }
                                            }

                                            me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent armorEquipEvent = new me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent((Player)e.getWhoClicked(), EquipMethod.SHIFT_CLICK, newArmorType, equipping ? null : e.getCurrentItem(), equipping ? e.getCurrentItem() : null);
                                            Bukkit.getServer().getConsoleSender().sendMessage("EVENT CALLED"); //TODO REMOVE
                                            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                                            if (armorEquipEvent.isCancelled()) {
                                                e.setCancelled(true);
                                            }
                                        }
                                    } else {
                                        ItemStack newArmorPiece = e.getCursor();
                                        ItemStack oldArmorPiece = e.getCurrentItem();
                                        if (numberkey) {
                                            if (e.getInventory().getType().equals(InventoryType.PLAYER)) {
                                                ItemStack hotbarItem = e.getInventory().getItem(e.getHotbarButton());
                                                if (!this.isAirOrNull(hotbarItem)) {
                                                    newArmorType = ArmorType.matchType(hotbarItem);
                                                    newArmorPiece = hotbarItem;
                                                    oldArmorPiece = e.getInventory().getItem(e.getSlot());
                                                } else {
                                                    newArmorType = ArmorType.matchType(!this.isAirOrNull(e.getCurrentItem()) ? e.getCurrentItem() : e.getCursor());
                                                }
                                            }
                                        } else if (this.isAirOrNull(e.getCursor()) && !this.isAirOrNull(e.getCurrentItem())) {
                                            newArmorType = ArmorType.matchType(e.getCurrentItem());
                                        }

                                        if (newArmorType != null && e.getRawSlot() == newArmorType.getSlot()) {
                                            EquipMethod method = EquipMethod.PICK_DROP;
                                            if (e.getAction().equals(InventoryAction.HOTBAR_SWAP) || numberkey) {
                                                method = EquipMethod.HOTBAR_SWAP;
                                            }

                                            me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent armorEquipEvent = new me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent((Player)e.getWhoClicked(), method, newArmorType, oldArmorPiece, newArmorPiece);
                                            Bukkit.getServer().getConsoleSender().sendMessage("EVENT CALLED"); //TODO REMOVE
                                            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                                            if (armorEquipEvent.isCancelled()) {
                                                e.setCancelled(true);
                                            }
                                        }
                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
    }

    @EventHandler
    public void playerInteractEvent(PlayerInteractEvent e) {
        if (e.getAction() != Action.PHYSICAL) {
            if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                Player player = e.getPlayer();
                if (e.getClickedBlock() != null && e.getAction() == Action.RIGHT_CLICK_BLOCK) {
                    Material mat = e.getClickedBlock().getType();
                    Iterator var4 = this.blockedMaterials.iterator();

                    while(var4.hasNext()) {
                        String s = (String)var4.next();
                        if (mat.name().equalsIgnoreCase(s)) {
                            return;
                        }
                    }
                }

                ArmorType newArmorType = ArmorType.matchType(e.getItem());
                if (newArmorType != null && (newArmorType.equals(ArmorType.HELMET) && this.isAirOrNull(e.getPlayer().getInventory().getHelmet()) || newArmorType.equals(ArmorType.CHESTPLATE) && this.isAirOrNull(e.getPlayer().getInventory().getChestplate()) || newArmorType.equals(ArmorType.LEGGINGS) && this.isAirOrNull(e.getPlayer().getInventory().getLeggings()) || newArmorType.equals(ArmorType.BOOTS) && this.isAirOrNull(e.getPlayer().getInventory().getBoots()))) {
                    me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent armorEquipEvent = new me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent(e.getPlayer(), EquipMethod.HOTBAR, ArmorType.matchType(e.getItem()), (ItemStack)null, e.getItem());
                    Bukkit.getServer().getConsoleSender().sendMessage("EVENT CALLED"); //TODO REMOVE
                    Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                    if (armorEquipEvent.isCancelled()) {
                        e.setCancelled(true);
                        player.updateInventory();
                    }
                }
            }

        }
    }

    @EventHandler
    public void inventoryDrag(InventoryDragEvent event) {
        ArmorType type = ArmorType.matchType(event.getOldCursor());
        if (!event.getRawSlots().isEmpty()) {
            if (type != null && type.getSlot() == (Integer)event.getRawSlots().stream().findFirst().orElse(0)) {
                me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent armorEquipEvent = new me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent((Player)event.getWhoClicked(), EquipMethod.DRAG, type, (ItemStack)null, event.getOldCursor());
                Bukkit.getServer().getConsoleSender().sendMessage("EVENT CALLED"); //TODO REMOVE
                Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
                if (armorEquipEvent.isCancelled()) {
                    event.setResult(Result.DENY);
                    event.setCancelled(true);
                }
            }

        }
    }

    @EventHandler
    public void itemBreakEvent(PlayerItemBreakEvent e) {
        ArmorType type = ArmorType.matchType(e.getBrokenItem());
        if (type != null) {
            Player p = e.getPlayer();
            me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent armorEquipEvent = new me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent(p, EquipMethod.BROKE, type, e.getBrokenItem(), (ItemStack)null);
            Bukkit.getServer().getConsoleSender().sendMessage("EVENT CALLED"); //TODO REMOVE
            Bukkit.getServer().getPluginManager().callEvent(armorEquipEvent);
            if (armorEquipEvent.isCancelled()) {
                ItemStack i = e.getBrokenItem().clone();
                i.setAmount(1);
                i.setDurability((short)(i.getDurability() - 1));
                if (type.equals(ArmorType.HELMET)) {
                    p.getInventory().setHelmet(i);
                } else if (type.equals(ArmorType.CHESTPLATE)) {
                    p.getInventory().setChestplate(i);
                } else if (type.equals(ArmorType.LEGGINGS)) {
                    p.getInventory().setLeggings(i);
                } else if (type.equals(ArmorType.BOOTS)) {
                    p.getInventory().setBoots(i);
                }
            }
        }

    }

    @EventHandler
    public void playerDeathEvent(PlayerDeathEvent e) {
        Player p = e.getEntity();
        ItemStack[] var3 = p.getInventory().getArmorContents();
        int var4 = var3.length;

        for(int var5 = 0; var5 < var4; ++var5) {
            ItemStack i = var3[var5];
            if (!this.isAirOrNull(i)) {
                Bukkit.getServer().getConsoleSender().sendMessage("EVENT CALLED"); //TODO REMOVE
                Bukkit.getServer().getPluginManager().callEvent(new me.superbility.centrallslescore.events.armorequipevent.ArmorEquipEvent(p, EquipMethod.DEATH, ArmorType.matchType(i), i, (ItemStack)null));
            }
        }

    }

    private boolean isAirOrNull(ItemStack item) {
        return item == null || item.getType().equals(Material.AIR);
    }
}
