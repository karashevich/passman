package com.company.server;

import com.company.commands.CommandException;
import com.company.security.PasswordHolder;
import com.company.security.RSAEncrypter;
import com.company.server.serverCommands.ServerCommandException;
import com.company.server.serverCommands.ServerCommandFactory;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.*;
import java.net.*;
import java.io.*;
import java.security.*;

/**
 * Created by jetbrains on 9/11/14.
 */
public class Server {

    final static private int port = 1234;
    static DatabaseControl databaseControl;
    static PasswordHolder passwordHolder;
    
    public static void main(String[] args) throws InvalidAlgorithmParameterException, IOException, NoSuchItemException, CommandException, InvalidPasswordException, ItemWIthSuchKeyExists, JSONException {

        try {

            ServerSocket ss = new ServerSocket(port);
            System.out.println("Waiting for a client");
            
            Socket socket = ss.accept();
            System.out.println("We've got a client.");

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            //Initialize RSAKey
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(RSAEncrypter.PRIVATE_KEY_FILE));
            final PrivateKey privateKey = (PrivateKey) inputStream.readObject();

            RSAEncrypter rsa = new RSAEncrypter();
            initDatabaseControl();

            String result;

            while(true){

                result = executeCommand(readMessage(in, rsa, privateKey)).toString();

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

    private static String readMessage(DataInputStream din, RSAEncrypter rsaEncrypter, PrivateKey privateKey) throws IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        int length = din.readInt();
        byte[] recievedMessage = null;

        //Read byte[] buffer;
        if(length > 0) {
            recievedMessage = new byte[length];
            din.readFully(recievedMessage, 0 ,length);
        }

        return new String(rsaEncrypter.decrypt(recievedMessage, privateKey));


    }

    private static JSONObject executeCommand(String commandString) throws JSONException {


        JSONObject jsonObject = new JSONObject(commandString);

        ServerCommandFactory scf = new ServerCommandFactory();

        try {

            return scf.buildCommand(jsonObject).execute(databaseControl, jsonObject);

        } catch (CommandException e) {
            return new JSONObject().put("answer", "error").put("error", "invalid command:" + e.getMessage());
        } catch (InvalidPasswordException e) {
            return new JSONObject().put("answer", "error").put("error", "invalid password:");
        } catch (JSONException e) {
            return new JSONObject().put("answer", "error").put("error", "internal communication error" + e.getMessage());
        } catch (ItemWIthSuchKeyExists itemWIthSuchKeyExists) {
            return new JSONObject().put("answer", "error").put("error", "internal encryption error" + itemWIthSuchKeyExists.getMessage());
        } catch (NoSuchItemException e) {
            return new JSONObject().put("answer", "error").put("error", "there is no item with such link!");
        } catch (ServerCommandException e) {
            return new JSONObject().put("answer", "error").put("error", "invalid server command!" + e.getMessage());
        }
    }

    private static void initDatabaseControl(){

        System.out.println("******Initializing passman******");
        databaseControl = new DatabaseControl();
        passwordHolder = new PasswordHolder(null, null);
        System.out.println("Database has been initialized.");


    }
    
}
