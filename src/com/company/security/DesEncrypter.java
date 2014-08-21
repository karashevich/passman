package com.company.security;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

public class DesEncrypter {


    Cipher ecipher;
    Cipher dcipher;

    public DesEncrypter(byte[] password) throws Exception {

        DESKeySpec dks = new DESKeySpec(password);
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);

        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, desKey);
        dcipher.init(Cipher.DECRYPT_MODE, desKey);
    }

    public DesEncrypter(@Nullable Password password) throws Exception {

        DESKeySpec dks = new DESKeySpec(password.getPassword());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);

        ecipher = Cipher.getInstance("DES");
        dcipher = Cipher.getInstance("DES");
        ecipher.init(Cipher.ENCRYPT_MODE, desKey);
        dcipher.init(Cipher.DECRYPT_MODE, desKey);
    }

    public boolean keyIsOkey(String key){

        if(key.length() >= 8) {
            return true;
        } else {
            return false;
        }

    }

    public String encrypt(String str) throws Exception {

        // Encode the string into bytes using utf-8
        byte[] utf8 = str.getBytes("UTF8");

        // Encrypt
        byte[] enc = ecipher.doFinal(utf8);

        // Encode bytes to base64 to get a string
        return new sun.misc.BASE64Encoder().encode(enc);
    }

    public String decrypt(String str) throws Exception {

        // Decode base64 to get bytes
        byte[] dec = new sun.misc.BASE64Decoder().decodeBuffer(str);

        byte[] utf8 = dcipher.doFinal(dec);

        // Decode using utf-8
        return new String(utf8, "UTF8");
    }

    public static void main(String[] argv) throws Exception {

        String password1 = "12345678";
        String password2 = "56782134";

        DESKeySpec dks = new DESKeySpec(password1.getBytes());
        SecretKeyFactory skf = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = skf.generateSecret(dks);


        DesEncrypter encrypter1 = new DesEncrypter(password1.getBytes());
        DesEncrypter encrypter2 = new DesEncrypter(password2.getBytes());

        String message = "pass1";
        String encrypted = encrypter1.encrypt(message);
        String decrypted = encrypter2.decrypt(encrypted);

        System.out.println(message);
        System.out.println(encrypted);
        System.out.println(decrypted);
    }
}