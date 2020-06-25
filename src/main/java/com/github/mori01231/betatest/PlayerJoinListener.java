package com.github.mori01231.betatest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import static org.bukkit.Bukkit.getServer;


public class PlayerJoinListener implements Listener {


    private BetaTest plugin;
    public PlayerJoinListener(BetaTest plugin){
        this.plugin = plugin;
    }

    String mode = BetaTest.getInstance().getConfig().getString("Mode");

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean firstJoin = !player.hasPlayedBefore();
        String uuid = String.valueOf(player.getUniqueId());

        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "This player is on their first join: " + firstJoin + " UUID: " + uuid + " MODE: " + mode);
        if (mode.equalsIgnoreCase("save")){
            if (!plugin.betatesters.contains(uuid)){
                plugin.betatesters.add(uuid);
                plugin.betatesters.save();
                //Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Saving BetaTester");
            }



        }

        else if (mode.equalsIgnoreCase("give")){
            if (plugin.betatesters.contains(uuid)){
                //give mythicmobs items
                for (String line : BetaTest.getInstance().getConfig().getStringList("GiveTester.MM")) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "mm i give " + player.getName() + " " + line);
                }

                //give minecraft items
                for (String line : BetaTest.getInstance().getConfig().getStringList("GiveTester.MC")) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:give " + player.getName() + " " + line);
                }

                plugin.betatesters.remove(uuid);
                plugin.betatesters.save();
                //Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Saving BetaTester");
            }
        }
        /*if(firstJoin){


        }*/
    }

}
