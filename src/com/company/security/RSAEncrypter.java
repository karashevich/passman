package com.company.security;


import com.sun.org.apache.xml.internal.security.algorithms.JCEMapper;

import javax.crypto.*;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.*;
import java.util.Arrays;

/**
 * Created by jetbrains on 9/11/14.
 */
public class RSAEncrypter {
    
    public static final String ALGORITHM = "RSA/ECB/PKCS1PADDING";
    
    public static final String PRIVATE_KEY_FILE = "./keys/private.key";
    public static final String PUBLIC_KEY_FILE = "./keys/public.key";

    private Cipher cipher;
    private AlgorithmParameters algorithmParameters;
    
    public static void generateKey() throws IOException, NoSuchAlgorithmException {
        
        final KeyPairGenerator keyGen = KeyPairGenerator.getInstance(ALGORITHM);
        keyGen.initialize(1024);
        final KeyPair key = keyGen.generateKeyPair();

        File privateKeyFile = new File(PRIVATE_KEY_FILE);
        File publicKeyFile = new File(PUBLIC_KEY_FILE);

        //Creating new files and directories
        if (privateKeyFile.getParentFile() != null) privateKeyFile.getParentFile().mkdirs();
        privateKeyFile.createNewFile();


        if (publicKeyFile.getParentFile() != null) publicKeyFile.getParentFile().mkdirs();
        publicKeyFile.createNewFile();

        ObjectOutputStream publicKeyOS = new ObjectOutputStream(new FileOutputStream(publicKeyFile));
        publicKeyOS.writeObject(key.getPublic());
        publicKeyOS.close();


        ObjectOutputStream privateKeyOS = new ObjectOutputStream(new FileOutputStream(privateKeyFile));
        privateKeyOS.writeObject(key.getPrivate());
        publicKeyOS.close();
    }

    public byte[] encrypt(byte[] input, PublicKey key) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, BadPaddingException, IllegalBlockSizeException {

        byte[] cipherText = null;

        cipher = Cipher.getInstance(ALGORITHM);

        cipher.init(Cipher.ENCRYPT_MODE, key);
        cipherText = cipher.doFinal(input);

        return cipherText;

    }


    public Cipher getCipher() {
        return cipher;
    }


    public RSAEncrypter() throws NoSuchPaddingException, NoSuchAlgorithmException {

        cipher = Cipher.getInstance(ALGORITHM);

    }

    public byte[] decrypt(byte[] input, PrivateKey key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException {

        byte[] decryptedText = null;


        cipher.init(Cipher.DECRYPT_MODE, key);
        decryptedText = cipher.doFinal(input);

        return decryptedText;

    }

    public byte[] decryptWithAlgParams(byte[] input, PrivateKey key) throws InvalidKeyException, BadPaddingException, IllegalBlockSizeException, NoSuchPaddingException, NoSuchAlgorithmException, InvalidAlgorithmParameterException {

        byte[] decryptedText = null;


        cipher.init(Cipher.DECRYPT_MODE, key, algorithmParameters);
        decryptedText = cipher.doFinal(input);

        return decryptedText;

    }


    public static boolean areKeysPresent() {

        File publicKey = new File(PUBLIC_KEY_FILE);
        File privateKey = new File(PRIVATE_KEY_FILE);

        if (privateKey.exists() && publicKey.exists()) return true;

        return false;

    }

    public RSAEncrypter(byte[] encodedAlgParameters) throws NoSuchPaddingException, NoSuchAlgorithmException, IOException {

        //get object for password-based encryption
        AlgorithmParameters algorithmParameters;
        algorithmParameters = AlgorithmParameters.getInstance(RSAEncrypter.ALGORITHM);

        //initialize with parameter encoding from above
        algorithmParameters.init(encodedAlgParameters);

    }

    public byte[] getEncodedParameters() throws IOException {

        return cipher.getParameters().getEncoded();

    }

    public static void main(String[] args) throws IOException, ClassNotFoundException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException, NoSuchAlgorithmException, NoSuchPaddingException {

        if (!areKeysPresent()) {
            generateKey();
        }

        final String originalText = "hey";
        ObjectInputStream inputStream = null;

        RSAEncrypter rsaEncrypter = new RSAEncrypter();

        inputStream = new ObjectInputStream(new FileInputStream(PUBLIC_KEY_FILE));
        final PublicKey publicKey = (PublicKey) inputStream.readObject();
        final byte[] cipherText = rsaEncrypter.encrypt(originalText.getBytes(), publicKey);



        inputStream = new ObjectInputStream(new FileInputStream(PRIVATE_KEY_FILE));
        final PrivateKey privateKey = (PrivateKey) inputStream.readObject();
        final byte[] plainText = rsaEncrypter.decrypt(cipherText, privateKey);

        System.out.println("Original: " + originalText);
        System.out.println("Encrypted: " + Arrays.toString(cipherText));
        System.out.println("Decrypted: " + new String(plainText));

    }



}
