package com.gmail.nossr50.commands.general;

import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.gmail.nossr50.Users;
import com.gmail.nossr50.m;
import com.gmail.nossr50.mcMMO;
import com.gmail.nossr50.commands.CommandHelper;
import com.gmail.nossr50.datatypes.PlayerProfile;
import com.gmail.nossr50.datatypes.SkillType;
import com.gmail.nossr50.locale.mcLocale;
import com.gmail.nossr50.skills.Skills;

public class AddlevelsCommand implements CommandExecutor{
    private final mcMMO plugin;

    public AddlevelsCommand(mcMMO instance) {
        this.plugin = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        OfflinePlayer modifiedPlayer;
        PlayerProfile PP;
        int levels;
        SkillType skill;
        String skillName;
        String usage = ChatColor.RED + "Proper usage is /addlevels [playername] <skill> <level>"; //TODO: Needs more locale.

        if (CommandHelper.noCommandPermissions(sender, "mcmmo.tools.mmoedit")) {
            return true;
        }

        switch (args.length) {
        case 2:
            if (sender instanceof Player) {
                if (m.isInt(args[1]) && Skills.isSkill(args[0])) {
                    modifiedPlayer = (Player) sender;
                    levels = Integer.valueOf(args[1]);
                    skill = Skills.getSkillType(args[0]);
                    PP = Users.getProfile(modifiedPlayer);

                    if (skill.equals(SkillType.ALL)) {
                        skillName = "all skills";
                    }
                    else {
                        skillName = m.getCapitalized(skill.toString());
                    }

                    PP.addLevels(skill, levels);
                    sender.sendMessage(ChatColor.GREEN + "You were awarded " + levels + " levels in " + skillName + "!"); //TODO: Needs more locale.
                }
            }
            else {
                sender.sendMessage(usage);
            }

            return true;

        case 3:
            modifiedPlayer = plugin.getServer().getOfflinePlayer(args[0]);
            String playerName = modifiedPlayer.getName();
            PP = Users.getProfile(modifiedPlayer);

            if (!PP.isLoaded()) {
                sender.sendMessage(mcLocale.getString("Commands.DoesNotExist"));
                return true;
            }

            if (m.isInt(args[2]) && Skills.isSkill(args[1])) {
                levels = Integer.valueOf(args[2]);
                skill = Skills.getSkillType(args[1]);
                String message;

                Users.getProfile(modifiedPlayer).addLevels(skill, levels);

                if (skill.equals(SkillType.ALL)) {
                    skillName = "all skills";
                    message = ChatColor.RED + "All skills have been modified for " + playerName + "."; //TODO: Use locale
                }
                else {
                    skillName = m.getCapitalized(skill.toString());
                    message = ChatColor.RED + skillName + " has been modified for " + playerName + "."; //TODO: Use locale
                }

                sender.sendMessage(message);

                if (modifiedPlayer.isOnline()) {
                    ((Player) modifiedPlayer).sendMessage(ChatColor.GREEN + "You were awarded " + levels + " levels in " + skillName + "!"); //TODO: Needs more locale.
                }
            }
            else {
                sender.sendMessage(usage);
            }

            return true;

        default:
            sender.sendMessage(usage);
            return true;
        }
    }
}
