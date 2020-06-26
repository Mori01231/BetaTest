package com.github.mori01231.betatest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;


public class PlayerJoinListener implements Listener {


    private BetaTest plugin;
    public PlayerJoinListener(BetaTest plugin){
        this.plugin = plugin;
    }

    //Is the plugin in save mode or give mode?
    String mode = BetaTest.getInstance().getConfig().getString("Mode");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Store uuid of player
        String uuid = String.valueOf(player.getUniqueId());

        //Store mcid of player
        String mcid = String.valueOf(player.getName());

        //save mode
        if (mode.equalsIgnoreCase("save")){

            //Not saved as beta tester yet
            if (!plugin.betatesters.contains(uuid)){
                plugin.betatesters.add(uuid);
                plugin.betatesters.save();
                getLogger().info("Added beta tester " + mcid + " to list.");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスターとして登録されました。" ));
            }
            //Already saved as beta tester
            else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスターとして登録済みです。" ));
            }
        }

        //give mode
        else if (mode.equalsIgnoreCase("give")){

            //Not given beta tester items yet
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
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典を配布しました。" ));
            }
            else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典は配布済みです。" ));
            }
        }

    }

}
