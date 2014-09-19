package com.company.server;

import com.company.security.RSAEncrypter;

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

    public static void main(String[] args) {

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

            System.out.println("Type here message: ");

            while(true){

                byte[] originalMessage = keyboard.readLine().getBytes();

                //Encrypting message with RSA method

                final byte[] cipherText = rsa.encrypt(originalMessage, publicKey);

                //Sending message to Server
                System.out.println("Sending message...");
                out.writeInt(cipherText.length);
                out.write(cipherText);
                out.flush();

                line = in.readUTF();
                System.out.println("Server answer: " + line);

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
