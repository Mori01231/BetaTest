package com.github.mori01231.betatest;


import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.*;

public class GiveTester implements CommandExecutor {

    private BetaTest plugin;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Store mcid of player
        String mcid = String.valueOf(args[0]);

        Player player = getPlayer(mcid);

        //Store uuid of player
        String uuid = String.valueOf(player.getUniqueId());

        getLogger().info("Using GiveTester command on " + mcid);



        if (plugin.betatesters.contains(uuid)){
            //give mythicmobs items
            for (String line : BetaTest.getInstance().getConfig().getStringList("GiveTester.MM")) {
                getServer().dispatchCommand(getServer().getConsoleSender(), "mm i give " + player.getName() + " " + line);
                getLogger().info("Gave beta tester " + mcid + " mythicmobs item " + line);
            }

            //give minecraft items
            for (String line : BetaTest.getInstance().getConfig().getStringList("GiveTester.MC")) {
                getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:give " + player.getName() + " " + line);
                getLogger().info("Gave beta tester " + mcid + " minecraft item " + line);
            }

            plugin.betatesters.remove(uuid);
            plugin.betatesters.save();
            getLogger().info("Removed beta tester " + mcid + " from list.");
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典を受け取りました。" ));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典を配布しました。" ));
        }
        else{
            player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典は受け取り済みです。" ));
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典は配布済みです。" ));
        }

        return true;
    }
}
