package com.github.mori01231.betatest;

import com.github.mori01231.utils.ListStore;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BetaTest extends JavaPlugin {

    public ListStore betatesters;

    private static BetaTest instance;
    public BetaTest (){
        instance = this;
    }
    public static BetaTest getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {

        getLogger().info("BetaTest has been enabled.");
        this.getCommand("givetester").setExecutor(new GiveTester());

        String pluginFolder = this.getDataFolder().getAbsolutePath();
        (new File(pluginFolder)).mkdirs();
        this.betatesters = new ListStore(new File(pluginFolder + File.separator + "beta-testers.txt"));
        this.betatesters.load();

        this.saveDefaultConfig();

    }


    @Override
    public void onDisable() {

        getLogger().info("BetaTest has been disabled.");
    }
}
