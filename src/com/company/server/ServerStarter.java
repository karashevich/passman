package com.company.server;

import com.company.preferences.Preferences;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.DatabaseLoadException;
import com.thoughtworks.xstream.converters.ConversionException;

import java.io.EOFException;

/**
 * Created by jetbrains on 9/29/14.
 */
public class ServerStarter {

    private static DatabaseControl databaseControl;

    private static void log(String s, int port){
        System.out.println(">>" + port + ": " +s);
    }

    private static void log(String s){
        System.out.println(s);
    }

    private static void initDatabaseControl(){

        log("Initializing...");
        databaseControl = new DatabaseControl();
        log("Database has been initialized.");


        //Loading database
        System.out.println("Loading database...");
        try{
            databaseControl.loadDatabase(Preferences.getDataPath());
            log("Database has been successfully loaded.");

        } catch (DatabaseLoadException dle) {
            log("Cannot load database: " + dle.getMessage());
        } catch (ConversionException ce) {
            log("Database file is corrupted!");
        }

    }

    public static void main(String[] args) {
        log("*******PASSMAN SERVER STARTER******");
        initDatabaseControl();
        log("Database is initialized.");


        int initialPort = 1234;
        RunnableServer[] runnableServer = new RunnableServer[4];
        Thread[] threads = new Thread[runnableServer.length];

        for (int i = 0; i < runnableServer.length; i++) {
            runnableServer[i] = new RunnableServer(databaseControl, (initialPort + i));
            threads[i] = new Thread(runnableServer[i]);
        }

        for (Thread thread : threads) {
            thread.start();
        }



    }
}
