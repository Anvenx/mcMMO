package com.gmail.nossr50.commands.general;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.m;
import com.gmail.nossr50.commands.CommandHelper;
import com.gmail.nossr50.config.LoadProperties;
import com.gmail.nossr50.locale.mcLocale;

public class XprateCommand implements CommandExecutor {
    private static int oldrate = LoadProperties.xpGainMultiplier;
    public static boolean xpevent = false;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String usage1 = mcLocale.getString("Commands.xprate.proper");
        String usage2 = mcLocale.getString("Commands.xprate.proper2");
        String usage3 = mcLocale.getString("Commands.xprate.proper3");

        if (CommandHelper.noCommandPermissions(sender, "mcmmo.admin")) {
            return true;
        }

        switch (args.length) {
        case 1:
            if (args[0].equalsIgnoreCase("reset")) {
                if (xpevent) {
                    for (Player x : Bukkit.getServer().getOnlinePlayers()) {
                        x.sendMessage(mcLocale.getString("Commands.xprate.over"));
                    }

                    xpevent = !xpevent;
                    LoadProperties.xpGainMultiplier = oldrate;
                }
                else {
                    LoadProperties.xpGainMultiplier = oldrate;
                }
            }
            else if (m.isInt(args[0])) {
                sender.sendMessage(usage3);
            }
            else {
                sender.sendMessage(usage2);
            }

            return true;

        case 2:
            if (m.isInt(args[0])) {
                oldrate = LoadProperties.xpGainMultiplier;

                if (args[1].equalsIgnoreCase("true") || args[1].equalsIgnoreCase("false")) {
                    xpevent = Boolean.valueOf(args[1]);
                }
                else {
                    sender.sendMessage(usage3);
                }

                LoadProperties.xpGainMultiplier = m.getInt(args[0]);

                if (xpevent) {
                    for (Player x : Bukkit.getServer().getOnlinePlayers()) {
                        x.sendMessage(mcLocale.getString("Commands.xprate.started"));
                        x.sendMessage(mcLocale.getString("Commands.xprate.started2", new Object[] {LoadProperties.xpGainMultiplier}));
                    }
                }
                else {
                    sender.sendMessage("The XP RATE was modified to " + LoadProperties.xpGainMultiplier); //TODO: Locale
                }
            }
            else {
                sender.sendMessage(usage1);
                sender.sendMessage(usage2);
            }

            return true;

        default:
            sender.sendMessage(usage1);
            sender.sendMessage(usage2);
            return true;
        }
    }
}
