package com.gmail.nossr50.commands.mc;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.getspout.spoutapi.player.SpoutPlayer;

import com.gmail.nossr50.config.LoadProperties;
import com.gmail.nossr50.locale.mcLocale;

public class McmmoCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String description = mcLocale.getString("mcMMO.Description", new Object[] { "mcc" });
        String[] mcSplit = description.split(",");

        for (String x : mcSplit) {
            sender.sendMessage(x);
        }

        if (LoadProperties.donateMessage) {
            if (LoadProperties.spoutEnabled && sender instanceof SpoutPlayer) {
                SpoutPlayer sPlayer = (SpoutPlayer) sender;

                sPlayer.sendNotification(ChatColor.YELLOW + "[mcMMO]" + ChatColor.GOLD + " Donate!", ChatColor.GREEN + "nossr50@gmail.com", Material.DIAMOND);
            }

            sender.sendMessage(ChatColor.DARK_AQUA + "Donation Info:");
            sender.sendMessage(ChatColor.GOLD + " - " + ChatColor.GREEN + "nossr50@gmail.com" + ChatColor.GOLD + " Paypal");
        }

        return true;
    }
}
