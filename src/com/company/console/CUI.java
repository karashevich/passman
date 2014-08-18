package com.company.console;

import com.company.CommandFactory;
import com.company.UI;
import com.company.preferences.Mode;
import com.company.preferences.Preferences;
import com.company.security.*;
import com.company.structures.DataPassClass;
import com.thoughtworks.xstream.mapper.CannotResolveClassException;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.lang.reflect.Array;
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
    public byte[] readPassword() {


        if (Preferences.runmode == Mode.TEST){

            System.out.print("TESTING MODE!!! Enter your secret password:");

            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

            try {
                return br.readLine().getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            Console console = System.console();

            if (console == null) {
                System.out.println("Couldn't get Console instance");
                System.exit(0);

            }

            char passwordArray[] = new char[0];
            passwordArray = console.readPassword("Enter your secret password: ");

            while (passwordArray.length < 8) {

                console.printf("Password should have at least 8 characters. Please try again.");
                passwordArray = console.readPassword("Enter your secret password: ");

            }

            return toBytes(passwordArray);
        }

        return null;
    }

    public  void init() {

        @Nullable
        PasswordStorage ps = new PasswordStorage(null);

        System.out.println("*******************");
        System.out.println("Hello from Passman!");
        System.out.println("*******************");

        System.out.println("Version: " + Preferences.getVersion());
        System.out.println("Type '?' to ask Passman how to deal with him.");
        System.out.println("*******************");

        //Loading DataPassClass
        DataPassClass dpc;
        System.out.print("Trying to remember everything...  ");
        try {

            dpc = DataPassClass.loadFromFile(Preferences.getDataPath());
            System.out.println("Ok!");


            if (dpc.isEncrypted()) {
                try {
                    System.out.println("My mind is encrypted, please remember your password:");
                    ps = new PasswordStorage(new Password(readPassword()));

                    while(!Arrays.equals(dpc.getPassHash(), ps.getPasshash())){
                        print("Incorrect password, try again. ");
                        ps.setPassword(readPassword());
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
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
                            commandFactory.buildCommand(commandLineArray).execute(dpc, commandLineArray, ps, this);

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
