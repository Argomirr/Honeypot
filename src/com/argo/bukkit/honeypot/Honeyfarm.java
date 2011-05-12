package com.argo.bukkit.honeypot;

import com.argo.util.TextFileHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class Honeyfarm {
	private static final String potListPath = "plugins/Honeypot/list.ncsv";
	private static final String logPath = "plugins/Honeypot/honeypot.log";

	private static List<Location> pots = new ArrayList<Location>();
	private static List<String> potSelectUsers = new ArrayList<String>();

	public static boolean refreshData(Honeypot plugin) {
		World potWorld;
		
		TextFileHandler r = new TextFileHandler(potListPath);
		pots.clear();
		try {
			List<String> list = r.readLines();
			String[] coord;
			while(!list.isEmpty()) {
				coord = list.remove(0).split(",");
				if(coord.length == 4) {
					String world = coord[0];
					if (plugin.getServer().getWorld(world) == null)
						potWorld = plugin.getServer().getWorlds().get(0);
					else
						potWorld = plugin.getServer().getWorld(world);

					pots.add(new Location(potWorld, new Double(coord[1]), new Double(coord[2]), new Double(coord[3])));
				} else {
					// Earlier than version 7 - No world set, have to assume primary world. 
					String potWorldname = plugin.getServer().getWorlds().get(0).getName();
					potWorld = plugin.getServer().getWorld(potWorldname);
					pots.add(new Location(potWorld, new Double(coord[0]), new Double(coord[1]), new Double(coord[2])));
				}
			}

		} catch (FileNotFoundException ex) {
			return false;
		} catch (IOException ex) {
			return false;
		}
		return true;
	}

	public static boolean saveData() {
		TextFileHandler r = new TextFileHandler(potListPath);

		List<String> tmp = new ArrayList<String>();

		Location loc;
		while(!pots.isEmpty()) {
			loc = pots.remove(0);
			tmp.add(loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ());
		}
		try {
			r.writeLines(tmp);
		} catch (IOException ex) {
			return false;
		}
		return true;
	}

	public static void log(String line) {
		TextFileHandler r = new TextFileHandler(logPath);
		try {
			r.appendLine("[" + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + "] " + line);
		} catch (IOException ex) {}
	}

	public static boolean isPot(Location loc) {
		if(pots.contains(loc)) {
			return true;
		} else {
			return false;
		}
	}

	public static void createPot(Location loc) {
		if(!isPot(loc)) {
			pots.add(loc);
		}
	}

	public static void removePot(Location loc) {
		pots.remove(loc);
	}

	public static void setPotSelect(Player player, boolean state) {
		if(state) {
			potSelectUsers.add(player.getName());
		} else {
			potSelectUsers.remove(player.getName());
		}
	}

	public static boolean getPotSelect(Player player) {
		if(potSelectUsers.contains(player.getName()))
			return true;
		else
			return false;
	}
}
