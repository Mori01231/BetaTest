package com.github.mori01231.betatest;

import org.bukkit.plugin.java.JavaPlugin;

public final class BetaTest extends JavaPlugin {

    private static BetaTest instance;
    public BetaTest (){
        instance = this;
    }
    public static BetaTest getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        // Plugin startup logic
        getLogger().info("LifeCore has been enabled.");
        this.getCommand("givetester").setExecutor(new GiveTester());


        this.saveDefaultConfig();

    }



    @Override
    public void onDisable() {
        // Plugin shutdown logic
        getLogger().info("LifeCore has been disabled.");
    }
}
