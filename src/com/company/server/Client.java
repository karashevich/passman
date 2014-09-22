package com.company.server;

import com.company.security.Password;
import com.company.security.RSAEncrypter;
import com.company.server.serverCommands.ClientCommandException;
import com.company.server.serverCommands.ServerCommandType;
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

/**
 * Created by jetbrains on 9/11/14.
 */
public class Client {


    final static private int serverPort = 1234;
    final private static String address = "127.0.0.1";

    private static JSONObject packMessage(String s, Password password) throws JSONException, ClientCommandException {

        String[] arguments = s.split(" ");

        JSONObject jsonObject = new JSONObject();

        if (arguments.length == 4) {

            if (arguments[0].equalsIgnoreCase("add")) {

                jsonObject.put("command", arguments[0]);
                jsonObject.put("data", new JSONObject().put("link", arguments[1]).put("login", arguments[2]).put("pass", arguments[3]));
                jsonObject.put("masterpassword", password);

                return jsonObject;

            }

        } else if(arguments.length == 2) {

            if (arguments[0].equalsIgnoreCase("show")) {

                jsonObject.put("command", arguments[0]);
                jsonObject.put("data", arguments[1]);
                jsonObject.put("masterpassword", password);


                return jsonObject;

            }

            if (arguments[0].equalsIgnoreCase("del")) {

                jsonObject.put("command", arguments[0]);
                jsonObject.put("data", arguments[1]);
                jsonObject.put("masterpassword", password);


                return jsonObject;

            }

            throw new ClientCommandException("Not enough arguments!");

        } else {

            if (arguments[0].equalsIgnoreCase("showall")) {

                jsonObject.put("command", arguments[0]);
                return jsonObject;

            }

            throw new ClientCommandException("Invalid command!");
        }

        return jsonObject;
    }

    public static void main(String[] args) throws JSONException {

        try {
            InetAddress ipAddress = InetAddress.getByName(address);

            Socket socket = new Socket(ipAddress, serverPort);

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

            System.out.println("Type here message: ");

            while(true){


                try {
                    byte[] originalMessage = packMessage(keyboard.readLine(), password).toString().getBytes();
                    System.err.println("message:" + new String(originalMessage));

                    //Encrypting message with RSA method

                    final byte[] cipherText = rsa.encrypt(originalMessage, publicKey);

                    //Sending message to Server
                    System.out.println("Sending message...");
                    out.writeInt(cipherText.length);
                    out.write(cipherText);
                    out.flush();

                    line = in.readUTF();
                    System.out.println("Server answer: " + line);

                } catch (ClientCommandException e) {
                    System.out.println("Error:" + e.getMessage());
                }

                System.out.println("Type here another msg: ");
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
}
