package com.gmail.nossr50.commands.mc;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.Users;
import com.gmail.nossr50.commands.CommandHelper;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.locale.mcLocale;

public class McabilityCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (CommandHelper.noConsoleUsage(sender)) {
            return true;
        }

        if (CommandHelper.noCommandPermissions(sender, "mcmmo.admin")) {
            return true;
        }

        PlayerProfile PP = Users.getProfile((Player) sender);

        if (PP.getAbilityUse()) {
            sender.sendMessage(mcLocale.getString("mcPlayerListener.AbilitiesOff"));
        }
        else {
            sender.sendMessage(mcLocale.getString("mcPlayerListener.AbilitiesOn"));
        }

        PP.toggleAbilityUse();

        return true;
    }
}
