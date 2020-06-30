package com.github.mori01231.betatest;

import com.github.mori01231.utils.GetOpenInventorySlots;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import static org.bukkit.Bukkit.getLogger;
import static org.bukkit.Bukkit.getServer;


public class PlayerJoinListener implements Listener {

    public GetOpenInventorySlots GetSlots;

    private BetaTest plugin;
    public PlayerJoinListener(BetaTest plugin){
        this.plugin = plugin;
    }

    //Is the plugin in save mode or give mode?
    String mode = BetaTest.getInstance().getConfig().getString("Mode");

    String RequiredSlots = BetaTest.getInstance().getConfig().getString("RequiredSlots");

    int RequiredSlotsNumber;

    public int AvailableSlots(Player player){
        //get player inventory.
        Inventory inv = player.getInventory();

        //initializing counter for slots.
        int slots = 0;

        //counting the number of available slots.
        for (ItemStack item: inv.getContents()) {
            if(item == null) {
                slots++;
            }
        }

        getLogger().info(String.valueOf(slots));

        //return the number of available slots.
        return slots;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        //Store uuid of player
        String uuid = String.valueOf(player.getUniqueId());

        //Store mcid of player
        String mcid = player.getName();

        try {
            RequiredSlotsNumber = Integer.parseInt(RequiredSlots);
            RequiredSlotsNumber += 5;
        }
        catch (NumberFormatException e)
        {
            RequiredSlotsNumber = 100;
        }

        //save mode
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

            if(RequiredSlotsNumber > 36){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&l設定ファイルのRequiredSlotsの値が大きすぎます。鯖主に報告してください。" ));
            }

            else if(AvailableSlots(player) < RequiredSlotsNumber){
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典を受け取るにはインベントリに &b&l" + RequiredSlotsNumber + " &3&lスロット以上の空きを作ったうえで再度ログインしてください。" ));
            }

            else{
                if(!plugin.GivenBetaTesters.Contains(uuid) && !plugin.RecordedBetaTesters.Contains(uuid)){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lあなたはベータテスターではありません。" ));
                }
                else if(plugin.GivenBetaTesters.Contains(uuid)){
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典は配布済みです。" ));
                }
                else if(plugin.RecordedBetaTesters.Contains(uuid)){
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
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&',"&b&lベータテスター特典を配布しました。" ));
                }
            }
        }


        else{
            getLogger().info("Invalid mode");
        }

    }

}
