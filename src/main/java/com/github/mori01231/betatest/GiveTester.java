package com.github.mori01231.betatest;


import com.github.mori01231.utils.GetOpenInventorySlots;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static org.bukkit.Bukkit.*;

public class GiveTester implements CommandExecutor {

    private BetaTest plugin;

    public GetOpenInventorySlots GetSlots;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        //Store mcid of player
        String mcid = String.valueOf(args[0]);

        Player player = getPlayer(mcid);

        //Store uuid of player
        String uuid;
        try{
            uuid = String.valueOf(player.getUniqueId());
        }catch(NullPointerException n){
            return false;
        }



        String RequiredSlots = BetaTest.getInstance().getConfig().getString("RequiredSlots");

        int RequiredSlotsNumber;


        try {
            RequiredSlotsNumber = Integer.parseInt(RequiredSlots);
        } catch (NumberFormatException e) {
            RequiredSlotsNumber = 100;
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l最大スロット数が数値になっていません。"));
        }catch (NullPointerException n) {
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l最大スロット数が設定されていません。"));
            return false;
        }

        getLogger().info("Using GiveTester command on " + mcid);

        if (RequiredSlotsNumber > 36) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&l設定ファイルのRequiredSlotsの値が大きすぎます。鯖主に報告してください。"));
        } else if (GetSlots.AvailableSlots(player) < RequiredSlotsNumber) {
            player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lベータテスター特典を受け取るにはインベントリに &b&l" + RequiredSlots + " &3&lスロット以上の空きを作ったうえで再度ログインしてください。"));
        } else {
            if (!plugin.GivenBetaTesters.Contains(uuid) && !plugin.RecordedBetaTesters.Contains(uuid)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lあなたはベータテスターではありません。"));
            } else if (plugin.GivenBetaTesters.Contains(uuid)) {
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lベータテスター特典は配布済みです。"));
            } else {
                //Logging how the beta tester was given the items.
                getLogger().info("Giving tester " + mcid + " beta tester items using PlayerJoinListener.");

                //give MythicMobs items
                for (String line : BetaTest.getInstance().getConfig().getStringList("GiveTester.MM")) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "mm i give " + player.getName() + " " + line);
                    getLogger().info("Gave beta tester " + mcid + " mythicmobs item " + line);
                }

                //give Minecraft items
                for (String line : BetaTest.getInstance().getConfig().getStringList("GiveTester.MC")) {
                    getServer().dispatchCommand(getServer().getConsoleSender(), "minecraft:give " + player.getName() + " " + line);
                    getLogger().info("Gave beta tester " + mcid + " minecraft item " + line);
                }

                plugin.RecordedBetaTesters.Remove(uuid);
                plugin.RecordedBetaTesters.Save();

                plugin.GivenBetaTesters.Add(uuid);
                plugin.GivenBetaTesters.Save();
                getLogger().info("Removed beta tester " + mcid + " from list.");
                player.sendMessage(ChatColor.translateAlternateColorCodes('&', "&b&lベータテスター特典を配布しました。"));
            }
        }
        return true;
    }
}
