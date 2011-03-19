
package com.argo.bukkit.honeypot;

import com.argo.util.TextFileHandler;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;

public class Honeyfarm {
    private static final String potListPath = "plugins/Honeypot/list.ncsv";
    private static final String logPath = "plugins/Honeypot/honeypot.log";

    private static List<Location> pots = new ArrayList<Location>();
    private static boolean potSelect = false;

    public static boolean refreshData(World w) {
	TextFileHandler r = new TextFileHandler(potListPath);
	pots.clear();
	try {
	    List<String> list = r.readLines();
	    String[] coord;
	    while(!list.isEmpty()) {
		coord = list.remove(0).split(",");
		if(coord.length == 3) {
		    pots.add(new Location(w, new Double(coord[0]), new Double(coord[1]), new Double(coord[2])));
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
	    tmp.add(loc.getX() + "," + loc.getY() + "," + loc.getZ());
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
	    r.appendLine(line);
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

    public static void setPotSelect(boolean state) {
	potSelect = state;
    }

    public static boolean getPotSelect() {
	return potSelect;
    }
}
