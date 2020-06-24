package com.github.mori01231.betatest;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerJoinEvent;


import static org.bukkit.Bukkit.getLogger;

public class PlayerJoinListener {


    private BetaTest plugin;
    public PlayerJoinListener(BetaTest plugin){
        this.plugin = plugin;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        boolean firstJoin = !player.hasPlayedBefore();
        getLogger().info("This player is on their first join: " + firstJoin);
        Bukkit.broadcastMessage(ChatColor.DARK_PURPLE + player.getName() + ChatColor.DARK_GRAY + " has joined the game and you better know it.");

        if(firstJoin){
            String uuid = String.valueOf(player.getUniqueId());
            getLogger().info("Setting string");
            plugin.betatesters.add(uuid);
            plugin.betatesters.add("String added");
            //Add to list of beta testers
        }
    }

}
