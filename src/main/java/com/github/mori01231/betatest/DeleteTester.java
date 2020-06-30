package com.github.mori01231.betatest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getPlayer;


public class DeleteTester implements CommandExecutor {

    private BetaTest plugin;
    public DeleteTester(BetaTest plugin){
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {


        //Store mcid of player
        String mcid = String.valueOf(args[0]);

        Player player = getPlayer(mcid);

        //Store uuid of player
        String uuid = String.valueOf(player.getUniqueId());



        //Not deleted yet.
        if (plugin.GivenBetaTesters.Contains(uuid)){
            plugin.GivenBetaTesters.Remove(uuid);
            plugin.GivenBetaTesters.Save();
            getLogger().info("Removed beta tester " + mcid + " from given list.");
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lβ特典渡し済みリストから削除しました。" ));
        }
        //Already deleted.
        else{
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lβ特典渡し済みリストに乗っていません。" ));
        }

        return true;
    }
}
