package com.puddingkc.hopperfilter.events;

import com.puddingkc.hopperfilter.cache.HopperFilterCache;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.ItemFrame;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.hanging.HangingBreakEvent;
import org.bukkit.event.hanging.HangingPlaceEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class HopperFrameListener implements Listener {

    @EventHandler
    public void onPlace(HangingPlaceEvent event) {
        if (event.getEntity() instanceof ItemFrame frame) {
            invalidateIfHopper(frame);
        }
    }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof ItemFrame frame) {
            invalidateIfHopper(frame);
        }
    }

    @EventHandler
    public void onBreak(HangingBreakEvent event) {
        if (event.getEntity() instanceof ItemFrame frame) {
            invalidateIfHopper(frame);
        }
    }

    private void invalidateIfHopper(ItemFrame frame) {
        BlockFace face = frame.getAttachedFace();
        Block attached = frame.getLocation().getBlock().getRelative(face);
        if (attached.getType() == Material.HOPPER) {
            HopperFilterCache.invalidate(attached);
        }
    }
}
