package com.github.mori01231.betatest;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;



public class SaveTester implements CommandExecutor {

    private BetaTest plugin;
    public SaveTester(BetaTest plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        plugin.betatesters.add(args[0]);
        plugin.betatesters.save();

        return true;
    }
}
