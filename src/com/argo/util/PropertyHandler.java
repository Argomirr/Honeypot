
package com.argo.util;

import java.util.Properties;

public class PropertyHandler extends Properties {
    public int getInt(String key, int def) {
	return new Integer(getProperty(key, String.valueOf(def)));
    }

    public long getLong(String key, long def) {
	return new Long(getProperty(key, String.valueOf(def)));
    }

    public double getDouble(String key, double def) {
	return new Double(getProperty(key, String.valueOf(def)));
    }

    public String getString(String key, String def) {
	return getProperty(key, def);
    }

    public boolean getBoolean(String key, boolean def) {
	return Boolean.valueOf(getProperty(key, String.valueOf(def)));
    }

    public void setInt(String key, int val) {
	setProperty(key, String.valueOf(val));
    }

    public void setLong(String key, long val) {
	setProperty(key, String.valueOf(val));
    }

    public void setDouble(String key, double val) {
	setProperty(key, String.valueOf(val));
    }

    public void setString(String key, String val) {
	setProperty(key, val);
    }

    public void setBoolean(String key, boolean val) {
	setProperty(key, String.valueOf(val));
    }
}
