package com.company.server;

import com.company.commands.CommandException;
import com.company.preferences.Preferences;
import com.company.security.RSAEncrypter;
import com.company.server.serverCommands.ServerCommandException;
import com.company.server.serverCommands.ServerCommandFactory;
import com.company.structures.DatabaseControl;
import com.company.structures.Exceptions.DatabaseLoadException;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Exceptions.ItemWIthSuchKeyExists;
import com.company.structures.Exceptions.NoSuchItemException;
import com.thoughtworks.xstream.converters.ConversionException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;

/**
 * Created by jetbrains on 9/11/14.
 */
public class RunnableServer implements Runnable{

    private DatabaseControl databaseControl;
    private int port;

    public RunnableServer(DatabaseControl databaseControl, int port) {

        this.databaseControl = databaseControl;
        this.port = port;
    }

    public void run(){
        try {
            startNode(port);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private static void log(String s, int port){
        System.out.println(">>" + port + ": " +s);
    }

    private static void log(String s){
        System.out.println(s);
    }


//    public static void main(String[] args) throws InvalidAlgorithmParameterException, IOException, NoSuchItemException, CommandException, InvalidPasswordException, ItemWIthSuchKeyExists, JSONException {
//
//        try {
//
//            log("******PASSMAN SERVER******");
//            initDatabaseControl();
//
//
//            startNode(1234);
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (ClassNotFoundException e) {
//            e.printStackTrace();
//        } catch (IllegalBlockSizeException e) {
//            e.printStackTrace();
//        } catch (BadPaddingException e) {
//            e.printStackTrace();
//        } catch (NoSuchPaddingException e) {
//            e.printStackTrace();
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//        } catch (InvalidKeyException e) {
//            e.printStackTrace();
//        }
//    }

    private void startNode(int port) throws  ClassNotFoundException, NoSuchPaddingException, NoSuchAlgorithmException, IllegalBlockSizeException, BadPaddingException, InvalidKeyException, JSONException {

        ServerSocket ss = null;
        Socket socket = null;

        try {
            log("Starting new server node on port: " + port, port);
            ss = new ServerSocket(port);
            log("Waiting for a clients...", port);

            socket = ss.accept();
            log("Client has been connected!", port);

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            //Initialize RSAKey
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(RSAEncrypter.PRIVATE_KEY_FILE));
            final PrivateKey privateKey = (PrivateKey) inputStream.readObject();

            RSAEncrypter rsa = new RSAEncrypter();
            String result;

            while(true){

                String message = readMessage(in, rsa, privateKey, port);
                result = executeCommand(message).toString();
                saveDC();

                log("Answering...", port);

                log("Answer: " + result, port);
                out.writeUTF(result);
                out.flush();

                System.out.println("Waiting for the next command...");
                System.out.println();
            }
        } catch (IOException e) {

            e.printStackTrace();

        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (socket != null) {
                try {
                    socket.close();
                    System.err.println("SOCKET CLOSED");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (ss != null) {
                try {
                    ss.close();
                    System.err.println("SERVERSOCKET CLOSED");
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

        }

    }


//    private static void startNode(int port){
//
//    }

    private String readMessage(DataInputStream din, RSAEncrypter rsaEncrypter, PrivateKey privateKey, int port) throws IOException, IllegalBlockSizeException, BadPaddingException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException {

        int length = din.readInt();
        byte[] recievedMessage = null;

        //Read byte[] buffer;
        if(length > 0) {
            recievedMessage = new byte[length];
            din.readFully(recievedMessage, 0 ,length);
        }
        log("Recieved message: " + new String(rsaEncrypter.decrypt(recievedMessage, privateKey)), port);

        return new String(rsaEncrypter.decrypt(recievedMessage, privateKey));


    }

    private JSONObject executeCommand(String commandString) throws JSONException {


        JSONObject jsonObject = new JSONObject(commandString);

        ServerCommandFactory scf = new ServerCommandFactory();

        try {

            return scf.buildCommand(jsonObject).execute(databaseControl, jsonObject);

        } catch (CommandException e) {
            return new JSONObject().put(CmCnsts.answer, CmCnsts.error).put(CmCnsts.errorType, "invalid command:" + e.getMessage());
        } catch (InvalidPasswordException e) {
            if (e.getMessage().equalsIgnoreCase(CmCnsts.unencrypted)) {
                return new JSONObject().put(CmCnsts.answer, CmCnsts.error).put(CmCnsts.errorType, "invalid password").put(CmCnsts.invalidPasswordError, CmCnsts.unencrypted);
            } else {
                return new JSONObject().put(CmCnsts.answer, CmCnsts.error).put(CmCnsts.errorType, "invalid password").put(CmCnsts.invalidPasswordError, "invalid password");
            }
        } catch (JSONException e) {
            return new JSONObject().put(CmCnsts.answer, CmCnsts.error).put(CmCnsts.errorType, "internal communication error" + e.getMessage());
        } catch (ItemWIthSuchKeyExists itemWIthSuchKeyExists) {
            return new JSONObject().put(CmCnsts.answer, CmCnsts.error).put(CmCnsts.errorType, "item with \"" + itemWIthSuchKeyExists.getMessage() + "\" key exists!");
        } catch (NoSuchItemException e) {
            return new JSONObject().put(CmCnsts.answer, CmCnsts.error).put(CmCnsts.errorType, "there is no item with such link!");
        } catch (ServerCommandException e) {
            return new JSONObject().put(CmCnsts.answer, CmCnsts.error).put(CmCnsts.errorType, "invalid server command: " + e.getMessage() + ": " + e.getCommandType().toString());
        }
    }

//    private void initDatabaseControl(){
//
//        log("Initializing...");
//        databaseControl = new DatabaseControl();
//        log("Database has been initialized.");
//
//
//        //Loading database
//        System.out.println("Loading database...");
//        try{
//            databaseControl.loadDatabase(Preferences.getDataPath());
//            log("Database has been successfully loaded.");
//
//        } catch (DatabaseLoadException dle) {
//            log("Cannot load database: " + dle.getMessage());
//        } catch (ConversionException ce) {
//            log("Database file is corrupted!");
//        }
//
//    }

    private void saveDC() throws IOException {

        try {

            databaseControl.saveToFile(Preferences.getDataPath());

        } catch (Exception e) {

            e.printStackTrace();
            log("Attention! File data.xml' didn't find. Trying to create a new one.");

            File f = new File(Preferences.getDataPath());
            f.createNewFile();

            databaseControl.saveToFile(Preferences.getDataPath());

        }

    }



}
