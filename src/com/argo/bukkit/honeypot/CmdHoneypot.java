
package com.argo.bukkit.honeypot;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CmdHoneypot implements CommandExecutor {
    private static Honeypot plugin;

    public CmdHoneypot(Honeypot instance) {
	plugin = instance;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String cmdName, String[] args) {
	if(args.length == 0) {
	    if(sender instanceof Player) {
		Player player = (Player)sender;
		if(!player.isOp()) {
		    player.sendMessage(ChatColor.RED + "Only Op's can use this command.");
		} else {
		    if(Honeyfarm.getPotSelect()) {
			player.sendMessage(ChatColor.GREEN + "Honeypot creation cancelled.");
			Honeyfarm.setPotSelect(false);
		    } else {
			player.sendMessage(ChatColor.GREEN + "Right click a block with a " + Settings.getToolId() + " to create a honeypot.");
			Honeyfarm.setPotSelect(true);
		    }
		}
	    } else {
		sender.sendMessage("Sorry, this command can only be used by players.");
	    }
	} else if(args.length == 1) {
	    if(args[0].equalsIgnoreCase("save") || args[0].equalsIgnoreCase("s")) {
		sender.sendMessage(ChatColor.GREEN + "Saving honeypot data...");
		if(!Honeyfarm.saveData()) {
		    sender.sendMessage(ChatColor.DARK_RED + "Failed to save data.");
		}
	    }
	}
	return true;
    }

}
