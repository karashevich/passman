package com.company.server;

import com.company.UI;
import com.company.security.Password;
import com.company.security.PasswordHolder;
import com.company.security.RSAEncrypter;
import com.company.server.serverCommands.ClientCommandException;
import com.company.server.serverCommands.ServerCommandType;
import com.company.structures.Exceptions.InvalidPasswordException;
import com.company.structures.Item;
import org.json.JSONException;
import org.json.JSONObject;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PublicKey;
import java.util.HashSet;

/**
 * Created by jetbrains on 9/11/14.
 */
public class ClientRobot {


    final private static String address = "127.0.0.1";

    private static JSONObject packMessage(String s, String passwordString) throws JSONException, ClientCommandException {

        String[] arguments = s.split(" ");

        JSONObject jsonObject = new JSONObject();


        if (arguments.length == 4) {

            if (arguments[0].equalsIgnoreCase(ServerCommandType.ADD.toString())) {

                jsonObject.put(CmCnsts.command, arguments[0]);
                jsonObject.put(CmCnsts.data, new JSONObject().put(CmCnsts.link, arguments[1]).put(CmCnsts.login, arguments[2]).put(CmCnsts.pass, arguments[3]));
                jsonObject.put(CmCnsts.masterpass, passwordString);

                return jsonObject;

            }

        } else if (arguments.length == 2) {

            if (arguments[0].equalsIgnoreCase(ServerCommandType.SHOW.toString())) {

                jsonObject.put(CmCnsts.command, arguments[0]);
                jsonObject.put(CmCnsts.data, arguments[1]);
                jsonObject.put(CmCnsts.masterpass, passwordString);


                return jsonObject;

            }

            if (arguments[0].equalsIgnoreCase(ServerCommandType.DEL.toString())) {

                jsonObject.put(CmCnsts.command, arguments[0]);
                jsonObject.put(CmCnsts.data, arguments[1]);
                jsonObject.put(CmCnsts.masterpass, passwordString);


                return jsonObject;

            }

            if (arguments[0].equalsIgnoreCase(ServerCommandType.SETPASS.toString())) {

                jsonObject.put(CmCnsts.command, arguments[0]);
                jsonObject.put(CmCnsts.newpass, arguments[1]);

                if (passwordString != null) {
                    jsonObject.put(CmCnsts.masterpass, passwordString);
                }

                return jsonObject;

            }

            throw new ClientCommandException("Not enough arguments!");

        } else {

            if (arguments[0].equalsIgnoreCase(ServerCommandType.SHOWALL.toString())) {

                jsonObject.put(CmCnsts.command, arguments[0]);
                return jsonObject;

            } else if (arguments[0].equalsIgnoreCase(ServerCommandType.CHECKPASS.toString())) {

                jsonObject.put(CmCnsts.command, arguments[0]);
                jsonObject.put(CmCnsts.masterpass, passwordString);
                return jsonObject;

            } else {
                //do nothing;
            }


            throw new ClientCommandException("Invalid command!");
        }

        return jsonObject;
    }

    private static void checkPassword(String passwordString, UI clientUI, PublicKey publicKey, RSAEncrypter rsa, DataInputStream in, DataOutputStream out) throws NoSuchPaddingException, ClientCommandException, IOException, JSONException, BadPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException {

        String line;
        boolean passIsOk = false;

        while (!passIsOk) {
            System.out.print("Password is wrong, please enter new password:");

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            passwordString = keyboard.readLine();
            sendMessage(out, ServerCommandType.CHECKPASS.toString(), publicKey, rsa, passwordString);
            line = in.readUTF();

            try {
                parseAnswer(line, passwordString);
                passIsOk = true;
                System.out.println("Password is ok!");
            } catch (InvalidPasswordException e) {
                if (e.getMessage().equalsIgnoreCase(CmCnsts.unencrypted)) {
                    System.err.println("Attention! Database is not encrypted!");

                    passwordString = null;
                    passIsOk = true;
                } else {
                    System.out.println("Invalid password, please try again!");

                    //              TODO: Password check
                }
            }
        }

    }

    public static void main(String[] args) throws JSONException, ClientCommandException {

        int port = 1234;

        try {
            InetAddress ipAddress = InetAddress.getByName(address);



            System.out.println("******PASSMAN CLIENT****** ");

            System.out.print("Establishing connection to server with port: " + port + " ...");
            Socket socket = new Socket(ipAddress, port);
            System.out.println("Ok!");

            ClientUI clientUI = new ClientUI();

            InputStream sin = socket.getInputStream();
            OutputStream sout = socket.getOutputStream();

            DataInputStream in = new DataInputStream(sin);
            DataOutputStream out = new DataOutputStream(sout);

            //Initialize public key
            ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(RSAEncrypter.PUBLIC_KEY_FILE));
            final PublicKey publicKey = (PublicKey) inputStream.readObject();

            RSAEncrypter rsa = new RSAEncrypter();

            BufferedReader keyboard = new BufferedReader(new InputStreamReader(System.in));
            String line = null;

            Password password = null;

            System.out.println("Initializing completed.");

            //Initializing password;
            PasswordHolder passwordHolder = new PasswordHolder(null, clientUI);

            //checkpass;
            String passwordString = "12345678";
            checkPassword(passwordString, clientUI, publicKey, rsa, in, out);

            System.out.print(">>>");

            boolean addElement = true;
            String addString = "add temp.com user pass";
            String delString = "del temp.com";


            while (true) {


                try {
                    if (addElement) {

                        sendMessage(out, addString, publicKey, rsa, passwordString);

                        line = in.readUTF();
                        try {
                            parseAnswer(line, passwordString);
                        } catch (InvalidPasswordException e) {
                            System.out.print("Invalid password! ");
                            checkPassword(passwordString, clientUI, publicKey, rsa, in, out);
                        }
                        addElement = !addElement;
                    } else {


                        sendMessage(out, delString, publicKey, rsa, passwordString);

                        line = in.readUTF();
                        try {
                            parseAnswer(line, passwordString);
                        } catch (InvalidPasswordException e) {
                            System.out.print("Invalid password! ");
                            checkPassword(passwordString, clientUI, publicKey, rsa, in, out);
                        }

                        addElement = !addElement;
                    }

                } catch (ClientCommandException e) {
                    System.out.println("Error:" + e.getMessage());
                }

                System.out.print(">>>");
            }


        } catch (UnknownHostException e) {

            e.printStackTrace();

        } catch (IOException e) {
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
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static void sendMessage(DataOutputStream out, String input, PublicKey publicKey, RSAEncrypter rsa, String passwordString) throws JSONException, ClientCommandException, IOException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] originalMessage = packMessage(input, passwordString).toString().getBytes();

        System.err.println(new String(originalMessage));
        //System.err.println("message:" + new String(originalMessage));

        //Encrypting message with RSA method

        final byte[] cipherText = rsa.encrypt(originalMessage, publicKey);

        //Sending message to Server
        out.writeInt(cipherText.length);
        out.write(cipherText);
        out.flush();
    }

    private static void parseAnswer(String answer, String passwordString) throws JSONException, InvalidPasswordException {

        JSONObject jsonObject = new JSONObject(answer);

        if (jsonObject.has(CmCnsts.invalidPasswordError)) {
            if (jsonObject.getString(CmCnsts.invalidPasswordError).equalsIgnoreCase(CmCnsts.unencrypted)) {
                throw new InvalidPasswordException(CmCnsts.unencrypted);
            } else {
                throw new InvalidPasswordException("Invalid password error");
            }
        }

        if (jsonObject.getString(CmCnsts.answer).equals(CmCnsts.ok)) {

            if (jsonObject.has(CmCnsts.data)) {

                JSONObject data = jsonObject.getJSONObject(CmCnsts.data);
                HashSet<Item> items = new HashSet<Item>(Converter.jsonToItems(data));

                for (Item item : items) {
                    System.out.println(item);
                }

            } else if (jsonObject.has(CmCnsts.item)) {

                System.out.println(Converter.jsonToItem(jsonObject.getJSONObject(CmCnsts.item)));

            } else if (jsonObject.has(CmCnsts.passchanged)) {  //if password has been changed;

                passwordString = CmCnsts.passchanged;
            }

        } else {

            if (jsonObject.has(CmCnsts.errorType)) {

                System.out.println("Error: " + jsonObject.getString(CmCnsts.errorType));

            } else {

                System.err.println("Unreported parsing answer exception");

            }

        }


    }


}
