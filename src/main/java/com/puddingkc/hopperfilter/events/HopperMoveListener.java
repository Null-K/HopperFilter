package com.puddingkc.hopperfilter.events;

import com.puddingkc.hopperfilter.cache.HopperFilterCache;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryMoveItemEvent;
import org.bukkit.event.inventory.InventoryPickupItemEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class HopperMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onInventoryMove(InventoryMoveItemEvent event) {
        Inventory destination = event.getDestination();
        if (destination.getType() != InventoryType.HOPPER) { return; }

        Location loc = destination.getLocation();
        if (loc == null) { return; }

        Block hopperBlock = loc.getBlock();
        ItemStack stack = event.getItem();

        if (!acceptsItem(hopperBlock, stack)) {
            event.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
    public void onHopperPickup(InventoryPickupItemEvent event) {
        if (event.getInventory().getType() != InventoryType.HOPPER) { return; }
        Location loc = event.getInventory().getLocation();
        if (loc == null) { return; }

        Block hopperBlock = loc.getBlock();
        ItemStack stack = event.getItem().getItemStack();

        if (!acceptsItem(hopperBlock, stack)) {
            event.setCancelled(true);
        }
    }

    private boolean acceptsItem(Block hopperBlock, ItemStack stack) {
        var filters = HopperFilterCache.getFilterItems(hopperBlock);

        if (filters.isEmpty()) { return true; }

        return filters.stream().anyMatch(filter ->
                filter != null && filter.getType() == stack.getType()
        );
    }

}
