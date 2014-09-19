package com.company.console;

import com.company.CommandFactory;
import com.company.UI;
import com.company.commands.CommandException;
import com.company.preferences.Mode;
import com.company.preferences.Preferences;
import com.company.security.*;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.DatabaseLoadException;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import com.thoughtworks.xstream.converters.ConversionException;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * Created by jetbrains on 5/8/14.
 * Console User Interface
 */
public class CUI implements UI{

    private byte[] toBytes(char[] chars) {
        CharBuffer charBuffer = CharBuffer.wrap(chars);
        ByteBuffer byteBuffer = Charset.forName("UTF-8").encode(charBuffer);
        byte[] bytes = Arrays.copyOfRange(byteBuffer.array(),
                byteBuffer.position(), byteBuffer.limit());
        Arrays.fill(charBuffer.array(), '\u0000'); // clear sensitive data
        Arrays.fill(byteBuffer.array(), (byte) 0); // clear sensitive data
        return bytes;
    }

    @Override
    public byte[] readPassword(String prefix) {


        if (Preferences.runmode == Mode.TEST){

            System.out.print(prefix);
            System.out.print("TESTING MODE!!! Enter your secret password:");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                return br.readLine().getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {

            Console console = System.console();
            console.printf(prefix);

            if (console == null) {
                System.out.println("Couldn't get Console instance");
                System.exit(0);

            }

            char passwordArray[];
            passwordArray = console.readPassword("Enter your secret password: ");

            while (passwordArray.length < 8) {

                console.printf("Password should have at least 8 characters. Please try again.");
                passwordArray = console.readPassword("Enter your secret password: ");

            }

            return toBytes(passwordArray);
        }

        return null;
    }

    public void init() {

        @Nullable
        PasswordHolder passwordHolder = new PasswordHolder(null, this);

        System.out.println("*******************");
        System.out.println("Hello from Passman!");
        System.out.println("*******************");

        System.out.println("Version: " + Preferences.getVersion());
        System.out.println("Type '?' to ask Passman how to deal with him.");
        System.out.println("*******************");

        //Loading DatabaseImpl
        DatabaseControl databaseControl = new DatabaseControl();

//        System.out.print("Trying to remember everything...  ");
//
//        database = new DatabaseImpl();
//        System.out.println("Ok!");


//        if (database.isEncrypted()) {
//            try {
//                System.out.println("My mind is encrypted, please remember your password:");
//                passwordHolder = new PasswordHolder(new Password(readPassword()));
//
//                while(!Arrays.equals(database.getPassHash(), passwordHolder.getPasshash())){
//                    print("Incorrect password, try again. ");
//                    passwordHolder.setPassword(readPassword());
//                }
//
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        }

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

                } else if (commandLine.equals(":l")){


                    //Trying to load database here.
                    System.out.println("Trying to load database...");

                    try{
                        databaseControl.loadDatabase(Preferences.getDataPath());
                        System.out.println("Database has been successfully loaded.");
                        //initialize PassHolder

                        passwordHolder.setHashPassword(databaseControl.getHash());
                        passwordHolder.getPassword();

                    } catch (DatabaseLoadException dle) {
                        System.err.println("Cannot load database: " + dle.getMessage());
                    } catch (ConversionException ce) {
                        System.err.println("Database file is corrupted!");
                    }

                } else {
                    if (commandLine.equals(":s")) {

                        try {

                            databaseControl.saveToFile(Preferences.getDataPath());

                        } catch (Exception e) {

                            e.printStackTrace();
                            System.out.println("Attention! File data.xml' didn't find. Trying to create a new one.");

                            File f = new File(Preferences.getDataPath());
                            f.createNewFile();

                            //database.saveToFile();
                        }

                    } else if (commandLine.isEmpty()) {
                        //do nothing;
                    } else {

                        CommandFactory commandFactory = new CommandFactory();
                        String commandLineArray[] = commandLine.split(" ");

                        if (commandLineArray.length >= 1) {

                            try {
                                commandFactory.buildCommand(commandLineArray).execute(databaseControl, commandLineArray, passwordHolder);
                            } catch (CommandException e) {
                                print("Invalid command!\n");

                            } catch (InvalidPasswordException e) {
                                print("Invalid password!\n");
                            } catch (NoSuchItemException e) {

                                print("No such item!\n");
                            } catch (ItemWIthSuchKeyExists itemWIthSuchKeyExists) {

                                print("Item with key \"" + itemWIthSuchKeyExists.getMessage() + "\" exists!)");
                            }

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

    public void print(String in){
        System.out.print(in);
    }

    private static void man() {
        System.out.println("   ? - manual\n" +
                "   :q - quit");
    }

}
