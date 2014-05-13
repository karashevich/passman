package com.company.console;

import com.company.Command;
import com.company.CommandFactory;
import com.company.preferences.Preferences;
import com.company.security.ConsolePassword;
import com.company.security.Password;
import com.company.structures.DataPassClass;
import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by jetbrains on 5/8/14.
 * Console User Interface
 */
public class CUI {

    public static void init() {

        @Nullable
        Password password = null;

        System.out.println("*******************");
        System.out.println("Hello from passman!");
        System.out.println("*******************");

        System.out.println("Version: " + Preferences.getVersion());
        System.out.println("Type '?' to ask passman how to deal with him.");
        System.out.println("*******************");

        //Loading DataPassClass
        DataPassClass dpc;
        System.out.print("Trying to remember everything...  ");
        try {

            dpc = DataPassClass.loadFromFile(Preferences.getDataPath());
            System.out.println("Ok!");


            if (dpc.isEncrypted()) {
                try {
                    password = new ConsolePassword();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else {
                password = null;
            }


            // Cannot read from file or not such file
        } catch (IOException e) {

            System.out.println("Attention! File 'data.xml' didn't find, passman will start without passwords history!");
            dpc = new DataPassClass();

        //Cannot resolve class from file
        } catch (CannotResolveClassException crce) {

            System.out.println("Attention! File 'data.xml' has wrong format, passman will start without passwords history!");
            dpc = new DataPassClass();

        }


        //Initialize BufferedReader to read from console.
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String commandLine;
        boolean quit = false;

        while (!quit) {
            System.out.print("passman $>");
            try {

                commandLine = br.readLine();
                //System.out.println(commandLine);

                if (commandLine.contains("?")){
                    man();
                } else if (commandLine.equals(":q")){

                    quit = true;

                } else {
                    if (commandLine.equals(":s")) {

                        try {

                            dpc.saveToFile(Preferences.getDataPath());

                        } catch (IOException e) {

                            e.printStackTrace();
                            System.out.println("Attention! File data.xml' didn't find. Trying to create a new one.");

                            File f = new File(Preferences.getDataPath());
                            f.createNewFile();

                            dpc.saveToFile(Preferences.getDataPath());
                        }

                    } else if (commandLine.isEmpty()) {
                        //do nothing;
                    } else {

                        CommandFactory commandFactory = new CommandFactory();
                        String commandLineArray[] = commandLine.split(" ");

                        if (commandLineArray.length >= 1) {
                            String s = commandLineArray[0].substring(1);
                            commandFactory.buildCommand(commandLineArray).execute(dpc, commandLineArray, password);

                        } else {
                            System.out.println("Unknown command, please read the help: ?");
                        }

                    }
                }


            } catch (IOException ioe) {

                System.out.println("Passman is sad: error trying to read your command.");

            }
        }
    }

    private static void man() {
        System.out.println("   ? - manual\n" +
                "   :q - quit");
    }

}
