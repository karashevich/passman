package com.company.server;

import com.company.CommandFactory;
import com.company.commands.CommandException;
import com.company.preferences.Preferences;
import com.company.security.Password;
import com.company.security.PasswordHolder;
import com.company.security.RSAEncrypter;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.DatabaseLoadException;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;
import com.thoughtworks.xstream.converters.ConversionException;
import sun.print.resources.serviceui_pt_BR;

import javax.crypto.*;
import java.net.*;
import java.io.*;
import java.security.*;
import java.util.Arrays;

/**
 * Created by jetbrains on 9/11/14.
 */
public class Server {

    final static private int port = 1234;
    static DatabaseControl databaseControl;
    static PasswordHolder passwordHolder;
    
    public static void main(String[] args) throws InvalidAlgorithmParameterException, IOException {

        try {

            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client");
            
            Socket socket = ss.accept();
            System.out.println("We've got a client.");

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(RSAEncrypter.PRIVATE_KEY_FILE));
            final PrivateKey privateKey = (PrivateKey) inputStream.readObject();

            RSAEncrypter rsa = new RSAEncrypter();
            initDatabaseControl();



            while(true){

                int length = in.readInt();
                byte[] recievedMessage = null;

                //Read byte[] buffer;
                if(length > 0) {
                    recievedMessage = new byte[length];
                    in.readFully(recievedMessage, 0 ,length);
                }

                byte[] plainText = rsa.decrypt(recievedMessage, privateKey);

                //ExecuteCommand
                String result = executeCommand(plainText);

                System.out.println("I'm sending OK signal back!");

                out.writeUTF(result);
                out.flush();

                System.out.println("Waiting for the next line...");
                System.out.println();
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        }

    }

    private static String executeCommand(byte[] commandByteLine){


            String commandLine = new String(commandByteLine);

            if (commandLine.contains("?")){
                return "Here should be manual...";
            } else if (commandLine.equals(":q")){

                System.out.println("Here should be exit...");

            } else if (commandLine.equals(":l")){


                //Trying to load database here.
                System.out.println("Here should load database...");


            } else {
                if (commandLine.equals(":s")) {

                    System.out.println("Here should save database...");

                } else if (commandLine.isEmpty()) {

                    //do nothing;

                } else {

                    CommandFactory commandFactory = new CommandFactory();
                    String commandLineArray[] = commandLine.split(" ");

                    if (commandLineArray.length >= 1) {
                        String s = commandLineArray[0].substring(1);

                        try {
                            commandFactory.buildCommand(commandLineArray).execute(databaseControl, commandLineArray, passwordHolder);
                        } catch (CommandException e) {
                            return "Invalid command!";

                        } catch (InvalidPasswordException e) {

                            return  "Invalid password";

                        } catch (NoSuchItemException e) {

                            return  "No such item!";

                        } catch (ItemWIthSuchKeyExists itemWIthSuchKeyExists) {

                            return "Item with such link exists!";
                        }

                    } else {
                        return "Unknown command, please read the help: ?";
                    }

                }
            }


        return "Execution error.";

    }

    private static void initDatabaseControl(){

        databaseControl = new DatabaseControl();
        passwordHolder = new PasswordHolder(null, null);

    }
    
}
