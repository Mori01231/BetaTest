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
            }
            getServer().dispatchCommand(getServer().getConsoleSender(), "savetester " + uuid);
            //Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + "Saving BetaTester");

        }

        else if (mode.equalsIgnoreCase("give")){
            if (plugin.betatesters.contains(uuid)){
                getServer().dispatchCommand(getServer().getConsoleSender(), "givetester " + player.getName());
                plugin.betatesters.remove(uuid);
                plugin.betatesters.save();
            }
        }
        /*if(firstJoin){


        }*/
    }

}
