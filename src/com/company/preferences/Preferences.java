package com.company.preferences;

/**
 * Created by jetbrains on 5/8/14.
 */


public class Preferences {

    private static final String version = "0.25";
    final static String dataPath = "./data.xml";
    public static final long deltaTime = 10000;
    public static Mode runmode = Mode.TEST;


    public static String getVersion() {
        return version;
    }

    public static String getDataPath() {
        return dataPath;
    }
}
