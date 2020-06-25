package com.github.mori01231.betatest;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import static org.bukkit.Bukkit.getServer;

public class GiveTester implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        for (String line : BetaTest.getInstance().getConfig().getStringList("GiveTester")) {
            getServer().dispatchCommand(getServer().getConsoleSender(), "mm i give " + args[0] + " " + line);
        }

        return true;
    }
}
