package com.puddingkc.hopperfilter;

import com.puddingkc.hopperfilter.events.HopperFrameListener;
import com.puddingkc.hopperfilter.events.HopperMoveListener;
import org.bukkit.plugin.java.JavaPlugin;

public class HopperFilter extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new HopperMoveListener(), this);
        getServer().getPluginManager().registerEvents(new HopperFrameListener(), this);
        getLogger().info("Author: PuddingKC");
    }

}