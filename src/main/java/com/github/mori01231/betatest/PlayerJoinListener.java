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
        String mcid = player.getName();


        //save mode (removed for now)
        if (mode.equalsIgnoreCase("save")){

            //Not saved as beta tester yet
            if (!plugin.RecordedBetaTesters.Contains(uuid)){
                plugin.RecordedBetaTesters.Add(uuid);
                plugin.RecordedBetaTesters.Save();
                getLogger().info("Added beta tester " + mcid + " to list.");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスターとして登録されました。" ));
            }
            //Already saved as beta tester
            else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスターとして登録済みです。" ));
            }
        }


        //give mode
        if (mode.equalsIgnoreCase("give")){

            getLogger().info("Giving tester " + mcid + " beta tester items using PlayerJoinListener.");
            //Not given beta tester items yet
            if (plugin.RecordedBetaTesters.Contains(uuid)){
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

                plugin.RecordedBetaTesters.Remove(uuid);
                plugin.RecordedBetaTesters.Save();

                plugin.GivenBetaTesters.Add(uuid);
                plugin.GivenBetaTesters.Save();
                getLogger().info("Removed beta tester " + mcid + " from list.");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典を配布しました。" ));
            }
            else if(plugin.GivenBetaTesters.Contains(uuid)){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典は配布済みです。" ));
            }else{
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lあなたはベータテスターではありません。" ));
            }
        }


        else{
            getLogger().info("Invalid mode");
        }

    }

}
