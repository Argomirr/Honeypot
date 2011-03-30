
package com.argo.bukkit.honeypot;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerListener;

public class HoneypotPlayerListener extends PlayerListener {
    private static Honeypot plugin;

    public HoneypotPlayerListener(Honeypot instance) {
	plugin = instance;
    }

    @Override
    public void onPlayerInteract(PlayerInteractEvent event) {
	if(Honeyfarm.getPotSelect()) {
	    Player player = event.getPlayer();
	    if(HoneypotPermissionsHandler.canUseCmd(player) && player.getItemInHand().getTypeId() == Settings.getToolId()) {
		if(!Honeyfarm.isPot(event.getClickedBlock().getLocation())) {
		    Honeyfarm.createPot(event.getClickedBlock().getLocation());
		    Honeyfarm.setPotSelect(false);
		    player.sendMessage(ChatColor.GREEN + "Honeypot created. Destroy the block to remove the honeypot.");
		} else {
		    player.sendMessage(ChatColor.DARK_RED + "That block is already marked as a honeypot. Honeypot creation cancelled.");
		    Honeyfarm.setPotSelect(false);
		}
	    }
	}
    }
}
