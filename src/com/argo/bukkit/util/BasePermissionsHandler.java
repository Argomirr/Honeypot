
package com.argo.bukkit.util;

import com.argo.bukkit.honeypot.Honeypot;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.nijiko.permissions.PermissionHandler;
import com.nijikokun.bukkit.Permissions.Permissions;


public class BasePermissionsHandler {
    private static PermissionHandler permissions;

    public static boolean setupPermissions(Honeypot plugin) {
        Plugin test = plugin.getServer().getPluginManager().getPlugin("Permissions");
        if (test != null) {
            permissions = ((Permissions)test).getHandler();
	    return true;
        } else {
	    return false;
	}
    }

    protected static boolean checkNode(Player p, String node){
        if(permissions != null) {
            if(!permissions.permission(p, node))
                return false;
            else
                return true;
        } else {
	    return p.isOp();
	}
    }
}
