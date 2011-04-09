
package com.argo.bukkit.honeypot;

import com.argo.bukkit.util.BansHandler;
import java.text.DateFormat;
import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;

public class HoneypotBlockListener extends BlockListener {
    private static Honeypot plugin;

    public HoneypotBlockListener(Honeypot instance) {
	plugin = instance;
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
	Block block = event.getBlock();
	if(Honeyfarm.isPot(block.getLocation())) {
	    Player player = event.getPlayer();
	    if(!HoneypotPermissionsHandler.canBreak(player)) {
		event.setCancelled(true);
		
		if(Settings.getKickFlag())
		    BansHandler.kick(player, "[Honeypot]", Settings.getPotMsg());
		else if(Settings.getBanFlag())
		    BansHandler.ban(player, "[Honeypot]", Settings.getPotMsg());

		if(Settings.getLogFlag()) {
		    String loc = block.getLocation().getBlockX() + ", " +  block.getLocation().getBlockY() + ", " +  block.getLocation().getBlockZ();
		    Honeyfarm.log("Player " + player.getName() + " was caught breaking a honeypot block at location " + loc + ".");
		}

		System.out.println("[Honeypot] Player " + player.getName() + " was caught breaking a honeypot block.");
		if(Settings.getShoutFlag())
		    plugin.getServer().broadcastMessage(ChatColor.DARK_RED + "[Honeypot]" + ChatColor.GRAY + " Player " + ChatColor.DARK_RED + player.getName() + ChatColor.GRAY + " was caught breaking a honeypot block.");
	    } else {
		player.sendMessage(ChatColor.GREEN + "Honeypot removed.");
		Honeyfarm.removePot(event.getBlock().getLocation());
	    }
	}
    }
}
