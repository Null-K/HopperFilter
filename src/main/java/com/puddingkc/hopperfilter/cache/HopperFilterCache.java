package com.puddingkc.hopperfilter.cache;

import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Hanging;
import org.bukkit.entity.ItemFrame;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class HopperFilterCache {

    private static final Map<Location, List<ItemStack>> cache = new ConcurrentHashMap<>();

    public static List<ItemStack> getFilterItems(Block hopper) {
        Location loc = hopper.getLocation();

        List<ItemStack> cached = cache.get(loc);
        if (cached != null) { return cached; }

        List<ItemStack> filters = new ArrayList<>();
        Collection<Entity> nearby = hopper.getWorld().getNearbyEntities(
                loc.clone().add(0.5, 0.5, 0.5),
                1.5, 1.5, 1.5
        );

        for (Entity entity : nearby) {
            if (!(entity instanceof ItemFrame frame)) { continue; }

            if (isFrameAttachedTo(frame, hopper)) {
                ItemStack item = frame.getItem();
                if (!item.getType().isAir()) {
                    filters.add(item.clone());
                }
            }
        }

        cache.put(loc, filters);
        return filters;
    }

    public static void invalidate(Block hopper) {
        cache.remove(hopper.getLocation());
    }

    private static boolean isFrameAttachedTo(Hanging hanging, Block block) {
        BlockFace face = hanging.getAttachedFace();
        Location attached = hanging.getLocation().getBlock().getRelative(face).getLocation();
        return attached.equals(block.getLocation());
    }

}
