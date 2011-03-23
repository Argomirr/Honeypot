
package com.argo.bukkit.honeypot;

import com.argo.bukkit.util.BansHandler;
import java.text.DateFormat;
import java.util.Date;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockListener;
import org.bukkit.event.block.BlockRightClickEvent;

public class HoneypotBlockListener extends BlockListener {
    private static Honeypot plugin;

    public HoneypotBlockListener(Honeypot instance) {
	plugin = instance;
    }

    @Override
    public void onBlockRightClick(BlockRightClickEvent event) {
	if(Honeyfarm.getPotSelect()) {
	    Player player = event.getPlayer();
	    if(HoneypotPermissionsHandler.canUseCmd(player) && player.getItemInHand().getTypeId() == Settings.getToolId()) {
		if(!Honeyfarm.isPot(event.getBlock().getLocation())) {
		    Honeyfarm.createPot(event.getBlock().getLocation());
		    Honeyfarm.setPotSelect(false);
		    player.sendMessage(ChatColor.GREEN + "Honeypot created. Destroy the block to remove the honeypot.");
		} else {
		    player.sendMessage(ChatColor.DARK_RED + "That block is already marked as a honeypot. Honeypot creation cancelled.");
		    Honeyfarm.setPotSelect(false);
		}
	    }
	}
    }

    @Override
    public void onBlockBreak(BlockBreakEvent event) {
	if(Honeyfarm.isPot(event.getBlock().getLocation())) {
	    Player player = event.getPlayer();
	    if(!HoneypotPermissionsHandler.canBreak(player)) {
		if(Settings.getKickFlag())
		    BansHandler.kick(player, "[Honeypot]", Settings.getPotMsg());
		else if(Settings.getBanFlag())
		    BansHandler.ban(player, "[Honeypot]", Settings.getPotMsg());

		if(Settings.getLogFlag())
		    Honeyfarm.log("[" + DateFormat.getTimeInstance().format(new Date()) + "] Player " + player.getName() + " was caught breaking a honeypot block.");

		System.out.println("[Honeypot] Player " + player.getName() + " was caught breaking a honeypot block.");
		plugin.getServer().broadcastMessage(ChatColor.DARK_RED + "[Honeypot]" + ChatColor.GRAY + " Player " + ChatColor.DARK_RED + player.getName() + ChatColor.GRAY + " was caught breaking a honeypot block.");
		event.setCancelled(true);
	    } else {
		player.sendMessage(ChatColor.GREEN + "Honeypot removed.");
		Honeyfarm.removePot(event.getBlock().getLocation());
	    }
	}
    }
}
