
package com.argo.bukkit.honeypot;

import com.argo.util.PropertyHandler;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class Settings {
    private static final String propertiesPath = "plugins/Honeypot/honeypot.properties";

    private static String honeypotMsg = "[Honeypot] You have been caught destroying a honeypot block.";
    private static int toolID = 271;
    private static boolean doLog = true;
    private static boolean doKick = true;
    private static boolean doBan = false;
    private static boolean doShout = true;

    public static boolean load() {
	PropertyHandler props = new PropertyHandler();

	try {
	    if(!new File(propertiesPath).exists()) {
		new File(propertiesPath).createNewFile();

		props.setInt("toolID", toolID);
		props.setString("honeypot-kick-msg", honeypotMsg);
		props.setBoolean("log-to-file", doLog);
		props.setBoolean("kick", doKick);
		props.setBoolean("ban", doBan);
		props.setBoolean("notify-online-players", doShout);

		props.store(new FileOutputStream(propertiesPath), null);
	    } else {
		props.load(new FileInputStream(propertiesPath));
		toolID = props.getInt("toolID", toolID);
		honeypotMsg = props.getString("honeypot-kick-msg", honeypotMsg);
		doLog = props.getBoolean("log-to-file", doLog);
		doKick = props.getBoolean("kick", doKick);
		doBan = props.getBoolean("ban", doBan);
		doShout = props.getBoolean("notify-online-players", doShout);
	    }
	} catch (Exception ex) {
	    return false;
	}

	
	return true;
    }

    public static String getPotMsg() {
	return honeypotMsg;
    }

    public static int getToolId() {
	return toolID;
    }

    public static boolean getKickFlag() {
	return doKick;
    }

    public static boolean getBanFlag() {
	return doBan;
    }

    public static boolean getLogFlag() {
	return doLog;
    }

    public static boolean getShoutFlag() {
	return doShout;
    }
}
