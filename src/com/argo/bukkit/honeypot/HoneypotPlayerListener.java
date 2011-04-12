
package com.argo.bukkit.honeypot;

import org.bukkit.event.block.Action;
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
	Player player = event.getPlayer();
	if(Honeyfarm.getPotSelect(player) && event.getAction() == Action.RIGHT_CLICK_BLOCK){
	    if(HoneypotPermissionsHandler.canUseCmd(player) && player.getItemInHand().getTypeId() == Settings.getToolId()) {
		if(!Honeyfarm.isPot(event.getClickedBlock().getLocation())) {
		    Honeyfarm.createPot(event.getClickedBlock().getLocation());
		    player.sendMessage(ChatColor.GREEN + "Honeypot created. Destroy the block to remove the honeypot.");
		} else {
		    player.sendMessage(ChatColor.DARK_RED + "That block is already marked as a honeypot.");
		}
	    }
	}
    }
}
