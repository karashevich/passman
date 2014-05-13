package com.company.preferences;

/**
 * Created by jetbrains on 5/8/14.
 */
public class Preferences {

    private static final String version = "0.24";
    final static String dataPath = "./data.xml";

    public static String getVersion() {
        return version;
    }

    public static String getDataPath() {
        return dataPath;
    }
}
