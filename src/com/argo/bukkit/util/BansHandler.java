package com.argo.bukkit.util;

import com.firestar.mcbans.mcbans;
import com.firestar.mcbans.mcbans_handler;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public class BansHandler {
	private static mcbans_handler mcb;
	private static BansMethod bmethod = BansMethod.NONE;

	public static BansMethod setupbanHandler(JavaPlugin plugin) {
		Plugin test = plugin.getServer().getPluginManager().getPlugin("mcbans");
		if(test == null) //Compatibility for older MCBans releases
			test = plugin.getServer().getPluginManager().getPlugin("MCBans");

		if (test != null) {
			mcb = ((mcbans) test).mcb_handler;
			bmethod = BansMethod.MCBANS;
		} else {
			bmethod = BansMethod.NONE;
		}
		return bmethod;
	}

	public static void ban(Player p, String sender, String reason) {
		switch(bmethod) {
		case NONE:
			p.kickPlayer(reason);
			break;
		case MCBANS:
			MCBan(p, sender, reason, "");
			break;
		case SIMPLEBAN:
			break;
		default: //NONE
			p.kickPlayer(reason);
			break;
		}
	}

	public static void kick(Player p, String sender, String reason) {
		switch(bmethod) {
		case NONE:
			p.kickPlayer(reason);
			break;
		case MCBANS:
			MCBanKick(p, sender, reason);
			break;
		case SIMPLEBAN:
			break;
		default: //NONE
			p.kickPlayer(reason);
			break;
		}
	}

	private static void MCBan(Player player, String sender, String reason, String type) {
		player.kickPlayer(reason); //kick for good measure
		mcb.ban(player.getName(), sender, reason, type);
	}

	private static void MCBanKick(Player player, String sender, String reason) {
		mcb.kick(player.getName(), sender, reason);
	}
}
