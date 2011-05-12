package com.argo.bukkit.honeypot;

import com.argo.bukkit.util.BasePermissionsHandler;
import org.bukkit.entity.Player;

public class HoneypotPermissionsHandler extends BasePermissionsHandler {
	public static boolean canBreak(Player player) {
		return checkNode(player, "honeypot.break");
	}

	public static boolean canUseCmd(Player player) {
		return checkNode(player, "honeypot.create");
	}
}
