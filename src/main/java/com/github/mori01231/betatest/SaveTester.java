package com.github.mori01231.betatest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayer;


public class SaveTester implements CommandExecutor {

    private BetaTest plugin;
    public SaveTester(BetaTest plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        //Store mcid of player
        String mcid = String.valueOf(args[0]);

        Player player = getPlayer(mcid);

        //Store uuid of player
        String uuid = String.valueOf(player.getUniqueId());



        //Not saved as beta tester yet
        if (!plugin.RecordedBetaTesters.Contains(uuid)){
            plugin.RecordedBetaTesters.Add(uuid);
            plugin.RecordedBetaTesters.Save();
            getLogger().info("Added beta tester " + mcid + " to list.");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスターとして登録されました。" ));
        }
        //Already saved as beta tester
        else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスターとして登録済みです。" ));
        }

        return true;
    }
}
