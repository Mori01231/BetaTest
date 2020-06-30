package com.github.mori01231.betatest;

import com.github.mori01231.utils.ListStore;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class BetaTest extends JavaPlugin {

    public ListStore RecordedBetaTesters;
    public ListStore GivenBetaTesters;

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
        this.getCommand("savetester").setExecutor(new SaveTester(this));

        String pluginFolder = this.getDataFolder().getAbsolutePath();
        (new File(pluginFolder)).mkdirs();

        this.RecordedBetaTesters = new ListStore(new File(pluginFolder + File.separator + "beta-testers.txt"));
        this.RecordedBetaTesters.Load();

        this.GivenBetaTesters = new ListStore(new File(pluginFolder + File.separator + "given-beta-testers.txt"));
        this.GivenBetaTesters.Load();


        this.saveDefaultConfig();

        registerEvents();

    }


    @Override
    public void onDisable() {

        getLogger().info("BetaTest has been disabled.");
    }

    public void registerEvents(){

        PluginManager pm = getServer().getPluginManager();

        pm.registerEvents(new PlayerJoinListener(this),  this);
    }
}
