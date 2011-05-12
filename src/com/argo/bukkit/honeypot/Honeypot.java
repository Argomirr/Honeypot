package com.argo.bukkit.honeypot;

import com.argo.bukkit.util.BansHandler;
import java.io.File;
import org.bukkit.event.Event.Priority;
import org.bukkit.event.Event.Type;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class Honeypot extends JavaPlugin {
	private final HoneypotBlockListener blockListener = new HoneypotBlockListener(this);
	private final HoneypotPlayerListener playerListener = new HoneypotPlayerListener(this);


	public void onEnable() {
		createDirs();

		if(!Settings.load()) {
			System.out.println("Honeypot: an error occured while trying to load the properties file.");
		}
		if(!Honeyfarm.refreshData(this)) {
			System.out.println("Honeypot: an error occured while trying to load the honeypot list.");
		}
		if(!HoneypotPermissionsHandler.setupPermissions(this)) {
			System.out.println("Honeypot: Permissions plugin not found, using default.");
		} else {
			System.out.println("Honeypot: Permissions plugin found, using that.");
		}

		switch(BansHandler.setupbanHandler(this)) {
		case NONE:
			System.out.println("Honeypot: Didn't find MCBans plugin, using default.");
			break;
		case MCBANS:
			System.out.println("Honeypot: MCBans plugin found, using that.");
			break;
		case SIMPLEBAN:
			System.out.println("Honeypot: SimpleBan plugin found, using that.");
			break;
		default:
			System.out.println("Honeypot: Didn't find MCBans plugin, using default.");
			break;
		}

		PluginManager pm = getServer().getPluginManager();
		pm.registerEvent(Type.PLAYER_INTERACT, playerListener, Priority.Low, this);
		pm.registerEvent(Type.BLOCK_BREAK, blockListener, Priority.Highest, this);
		getCommand("honeypot").setExecutor(new CmdHoneypot(this));

		PluginDescriptionFile pdf = this.getDescription();
		System.out.println(pdf.getName() + " revision " + pdf.getVersion() + " by " + pdf.getAuthors().get(0) + " succesfully loaded.");
	}

	public void onDisable() {
		if(!Honeyfarm.saveData()) {
			System.out.println("Honeypot: an error occured while trying to save the honeypot list.");
		}

		PluginDescriptionFile pdf = this.getDescription();
		System.out.println(pdf.getName() + " revision " + pdf.getVersion() + " by " + pdf.getAuthors().get(0) + " succesfully disabled.");
	}

	public void createDirs() {
		new File("plugins/Honeypot").mkdir();
	}
}
