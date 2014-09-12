package com.company.server;

import com.company.security.RSAEncrypter;
import com.sun.tools.doclets.internal.toolkit.util.SourceToHTMLConverter;
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
            Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1PADDING");
            cipher.init(Cipher.DECRYPT_MODE, privateKey);




            while(true){
                int length = in.readInt();
                byte[] recievedMessage = null;
                if(length > 0) {
                    recievedMessage = new byte[length];
                    in.readFully(recievedMessage, 0 ,length);
                }


                byte[] plainText = cipher.doFinal(recievedMessage);

                System.out.println("The client just sent me message: " + new String(plainText));
                System.out.println("I'm sending OK signal back!");

                out.writeUTF("OK");
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
    
    
}
